package com.platform.campaign_service.services.campaign.impl;

import com.platform.campaign_service.context.IContextService;
import com.platform.campaign_service.controllers.manageExceptions.CustomException;
import com.platform.campaign_service.dtos.campaign.CampaignUpdateRequestDto;
import com.platform.campaign_service.dtos.donation.DonationMessageDto;
import com.platform.campaign_service.dtos.ngo.NgoDto;
import com.platform.campaign_service.entities.CampaignCategoryEntity;
import com.platform.campaign_service.entities.CampaignEntity;
import com.platform.campaign_service.entities.CampaignImageEntity;
import com.platform.campaign_service.entities.CampaignTagEntity;
import com.platform.campaign_service.entities.CategoryEntity;
import com.platform.campaign_service.entities.embeddable.CampaignCategoryId;
import com.platform.campaign_service.entities.embeddable.CampaignTagId;
import com.platform.campaign_service.enums.CampaignState;
import com.platform.campaign_service.enums.DonationStatus;
import com.platform.campaign_service.repositories.CampaignRepository;
import com.platform.campaign_service.services.campaign.IGetCampaignService;
import com.platform.campaign_service.services.campaign.IUpdateCampaignService;
import com.platform.campaign_service.services.category.ICategoryService;
import com.platform.campaign_service.services.ngo.IGetNgoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import java.util.Set;
import java.util.UUID;

/**
 * Implementation of the IUpdateCampaignService interface
 * for updating campaign information.
 */
@Service
@RequiredArgsConstructor
public class UpdateCampaignService implements IUpdateCampaignService {
    /**
     * Logger for logging information and errors.
     */
    private static final Logger LOG = LoggerFactory.getLogger(UpdateCampaignService.class);
    /**
     * Service for retrieving NGO information.
     */
    private final IGetNgoService getNgoService;
    /**
     * Repository for accessing campaign data.
     */
    private final CampaignRepository campaignRepository;
    /**
     * Service for managing context-related operations.
     */
    private final IContextService contextService;
    /**
     * Service for handling category-related operations.
     */
    private final ICategoryService categoryService;
    /**
     * Service for retrieving campaign information.
     */
    private final IGetCampaignService getCampaignService;

    /**
     * Updates the information of an existing campaign.
     *
     * @param campaignId The unique identifier of the campaign to be updated.
     * @param request    The DTO containing the updated campaign information.
     */
    @Override
    @Transactional
    public void updateCampaign(UUID campaignId, CampaignUpdateRequestDto request) {
        LOG.trace("Updating campaign with ID: {}", campaignId);
        UUID userId = getCurrentUserId();
        NgoDto ngoUserContext = getNgoUserContext();
        CampaignEntity campaign = getCampaignEntity(campaignId);
        validateUserAuthorization(campaign, ngoUserContext);
        validateCampingData(request);
        boolean hasChanges = false;
        if (request.getTitle() != null && !request.getTitle().equals(campaign.getTitle())) {
            campaign.setTitle(request.getTitle());
            hasChanges = true;
        }
        if (request.getDescription() != null && !request.getDescription().equals(campaign.getDescription())) {
            campaign.setDescription(request.getDescription());
            hasChanges = true;
        }
        if (request.getGoalAmount() != null && request.getGoalAmount().compareTo(campaign.getGoalAmount()) != 0) {
            campaign.setGoalAmount(request.getGoalAmount());
            hasChanges = true;
        }
        if (request.getEndDateTime() != null && !request.getEndDateTime().equals(campaign.getEndDatetime())) {
            campaign.setEndDatetime(request.getEndDateTime());
            hasChanges = true;
        }
        hasChanges = updateCategories(campaign, request.getCategoryIds(), userId) || hasChanges;
        hasChanges = updateTags(campaign, request.getTags(), userId) || hasChanges;
        hasChanges = updateImages(campaign, request.getImageIds(), userId) || hasChanges;
        if (hasChanges) {
            campaign.setLastUpdatedUser(userId);
        }
        try {
            campaignRepository.save(campaign);
            LOG.info("Campaign with ID {} successfully updated by user {}", campaignId, userId);
        } catch (Exception ex) {
            LOG.error("Database error while updating Campaign with ID {}: {}", campaignId, ex.getMessage());
            throw new CustomException("An error occurred while updating Campaign.", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }

    }

    /**
     * Updates the current amount raised for a specific campaign.
     *
     * @param donationMessageDto The DTO containing donation information.
     */
    @Override
    @Transactional
    public void updateCurrentAmount(DonationMessageDto donationMessageDto) {
        // Find the campaign by ID
        CampaignEntity campaign = getCampaignEntity(donationMessageDto.getCampaignId());
        // Update the current amount depending on change of donation status
        BigDecimal amountChange = donationMessageDto.getAmount();
        DonationStatus previousStatus = donationMessageDto.getPreviousDonationStatus();
        DonationStatus newStatus = donationMessageDto.getDonationStatus();
        if ((previousStatus.equals(DonationStatus.CREATED) || previousStatus.equals(DonationStatus.CANCELLED))
                && newStatus.equals(DonationStatus.CONFIRMED)) {
            campaign.setCurrentAmount(campaign.getCurrentAmount().add(amountChange));
        } else if (previousStatus.equals(DonationStatus.CONFIRMED)
                && (newStatus.equals(DonationStatus.CANCELLED))) {
            campaign.setCurrentAmount(campaign.getCurrentAmount().subtract(amountChange));
        } else {
            LOG.info("No change in current amount for Campaign ID: {}. Previous Status: {}, New Status: {}",
                    campaign.getCampaignId(), previousStatus, newStatus);
        }
    }

    private NgoDto getNgoUserContext() {
        return getNgoService.getNgoUserContext();
    }

    private UUID getCurrentUserId() {
        return contextService.getUserId();
    }

    private void validateCampingData(CampaignUpdateRequestDto requestDto) {
        if (requestDto.getGoalAmount() != null
                && requestDto.getGoalAmount().compareTo(BigDecimal.ZERO) <= 0) {
            LOG.warn("In validateCampingData: goalAmount must be greater than zero. GoalAmount: {}",
                    requestDto.getGoalAmount());
            throw new CustomException("Goal amount must be greater than zero.", HttpStatus.BAD_REQUEST);
        }
        LocalDateTime now = LocalDateTime.now();
        if (requestDto.getEndDateTime() != null
                && requestDto.getEndDateTime().isBefore(now)) {
            LOG.warn("In validateCampingData: endDateTime must be after today. EndDateTime: {}. Now: {}",
                    requestDto.getEndDateTime(), now);
            throw new CustomException("End date must be after today.", HttpStatus.BAD_REQUEST);
        }
        if (requestDto.getCategoryIds() == null || requestDto.getCategoryIds().isEmpty()) {
            LOG.warn("In validateCampingData: At least one category must be specified. CategoryIds: {}",
                    requestDto.getCategoryIds());
            throw new CustomException("At least one category must be specified.", HttpStatus.BAD_REQUEST);
        }
    }

    private List<CategoryEntity> getAllCategoriesActive() {
        return categoryService.getAllCategoriesActive();
    }

    private CampaignEntity getCampaignEntity(UUID campaignId) {
        return getCampaignService.getCampaignEntityById(campaignId);
    }

    private void validateUserAuthorization(CampaignEntity campaign, NgoDto ngo) {
        if (campaign.getState().equals(CampaignState.CLOSED)) {
            LOG.warn("Campaign with ID: {} is closed and cannot be updated", campaign.getCampaignId());
            throw new CustomException("Closed campaigns cannot be updated.", HttpStatus.BAD_REQUEST);
        }
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(campaign.getEndDatetime())) {
            LOG.warn("Campaign with ID: {} has ended on {} and cannot be updated", campaign.getCampaignId(),
                    campaign.getEndDatetime());
            throw new CustomException("Ended campaigns cannot be updated.", HttpStatus.BAD_REQUEST);
        }
        if (!Objects.equals(campaign.getOrganizationId().toString(), ngo.getId())) {
            LOG.warn("The Campaign with ID: {} does not belong to the NGO with ID: {}",
                    campaign.getCampaignId(), ngo.getId());
            throw new CustomException("The campaign does not belong to the NGO.", HttpStatus.UNAUTHORIZED);
        }
        if (!contextService.isThisUserId(ngo.getUserCreator().getId())) {
            LOG.error("User with ID: {} is not authorized to update campaign", ngo.getUserCreator().getId());
            throw new CustomException("User is not authorized to update this campaign.", HttpStatus.UNAUTHORIZED);
        }
    }

    private boolean updateCategories(CampaignEntity campaign, Set<UUID> newCategoryIds, final UUID currentUserId) {
        boolean hasChanges = false;
        List<CategoryEntity> allCategories = getAllCategoriesActive();
        List<CampaignCategoryEntity> categories = campaign.getCategories();
        for (CampaignCategoryEntity category : categories) {
            boolean enabled = category.getEnabled();
            boolean existsInNewList = newCategoryIds.stream()
                    .anyMatch(id -> id.equals(category.getCategory().getCategoryId()));
            category.setEnabled(existsInNewList);
            if (enabled && !existsInNewList) {
                hasChanges = true;
                category.setLastUpdatedUser(currentUserId);
            }
        }
        for (UUID id : newCategoryIds) {
            boolean exists = categories.stream()
                    .anyMatch(cat -> cat.getCategory().getCategoryId().equals(id));
            if (!exists) {
                CategoryEntity categoryEntity = allCategories.stream()
                        .filter(cat -> cat.getCategoryId().equals(id)).findFirst().orElse(null);
                if (categoryEntity == null) {
                    LOG.warn("Category with ID: {} not found among active categories", id);
                    throw new CustomException("Category with ID: " + id + " not found.", HttpStatus.BAD_REQUEST);
                }
                CampaignCategoryEntity newCampaignCategory = CampaignCategoryEntity.builder()
                        .campaign(campaign)
                        .category(categoryEntity)
                        .enabled(true)
                        .createdUser(currentUserId)
                        .id(new CampaignCategoryId(campaign.getCampaignId(), categoryEntity.getCategoryId()))
                        .build();
                categories.add(newCampaignCategory);
                hasChanges = true;
            }
        }
        return hasChanges;
    }

    private boolean updateTags(CampaignEntity campaign, Set<String> newTagsParam, final UUID currentUserId) {
        Set<String> newTags = Objects.requireNonNullElseGet(newTagsParam, Set::of);
        boolean hasChanges = false;
        List<CampaignTagEntity> tags = campaign.getTags();
        for (CampaignTagEntity tag : tags) {
            boolean enabled = tag.getEnabled();
            boolean existsInNewList = newTags.contains(tag.getId().getTag());
            tag.setEnabled(existsInNewList);
            if (enabled && !existsInNewList) {
                hasChanges = true;
                tag.setLastUpdatedUser(currentUserId);
            }
        }
        for (String tagStr : newTags) {
            boolean exists = tags.stream()
                    .anyMatch(t -> t.getId().getTag().equals(tagStr));
            if (!exists) {
                CampaignTagEntity newCampaignTag = CampaignTagEntity.builder()
                        .campaign(campaign)
                        .id(new CampaignTagId(campaign.getCampaignId(), tagStr))
                        .enabled(true)
                        .createdUser(currentUserId)
                        .build();
                tags.add(newCampaignTag);
                hasChanges = true;
            }

        }
        return hasChanges;
    }

    private boolean updateImages(CampaignEntity campaign, List<UUID> newImageIdsParam, final UUID currentUserId) {
        List<UUID> newImageIds = Objects.requireNonNullElseGet(newImageIdsParam, List::of);
        boolean hasChanges = false;
        List<CampaignImageEntity> images = campaign.getImages();
        for (CampaignImageEntity image : images) {
            boolean enabled = image.getEnabled();
            boolean existsInNewList = newImageIds.contains(image.getFileId());
            image.setEnabled(existsInNewList);
            if (enabled && !existsInNewList) {
                hasChanges = true;
                image.setLastUpdatedUser(currentUserId);
            }
        }
        for (UUID imageId : newImageIds) {
            boolean exists = images.stream()
                    .anyMatch(i -> i.getFileId().equals(imageId));
            if (!exists) {
                CampaignImageEntity newCampaignImage = CampaignImageEntity.builder()
                        .fileId(imageId)
                        .campaignImageId(UUID.randomUUID())
                        .campaign(campaign)
                        .enabled(true)
                        .createdUser(currentUserId)
                        .build();
                images.add(newCampaignImage);
                hasChanges = true;
            }

        }
        return hasChanges;
    }
}
