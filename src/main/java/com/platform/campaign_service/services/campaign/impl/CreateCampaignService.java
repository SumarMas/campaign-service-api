package com.platform.campaign_service.services.campaign.impl;

import com.platform.campaign_service.context.IContextService;
import com.platform.campaign_service.controllers.manageExceptions.CustomException;
import com.platform.campaign_service.dtos.campaign.CampaignCreateRequestDto;
import com.platform.campaign_service.dtos.ngo.NgoDto;
import com.platform.campaign_service.entities.CampaignCategoryEntity;
import com.platform.campaign_service.entities.CampaignEntity;
import com.platform.campaign_service.entities.CampaignImageEntity;
import com.platform.campaign_service.entities.CampaignTagEntity;
import com.platform.campaign_service.entities.CategoryEntity;
import com.platform.campaign_service.entities.embeddable.CampaignCategoryId;
import com.platform.campaign_service.entities.embeddable.CampaignTagId;
import com.platform.campaign_service.enums.CampaignState;
import com.platform.campaign_service.repositories.CampaignRepository;
import com.platform.campaign_service.services.campaign.ICreateCampaignService;
import com.platform.campaign_service.services.category.ICategoryService;
import com.platform.campaign_service.services.ngo.IGetNgoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service implementation for creating campaigns.
 */
@Service
@RequiredArgsConstructor
public class CreateCampaignService implements ICreateCampaignService {
    /**
     * Logger instance for logging information and errors.
     */
    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(CreateCampaignService.class);
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
     * Creates a new campaign based on the provided request data.
     *
     * @param requestDto the data transfer object containing campaign creation details
     */
    @Override
    @Transactional
    public void createCampaign(CampaignCreateRequestDto requestDto) {
        LOG.trace("In CreateCampaignService.createCampaign()");
        validateCampingData(requestDto);
        NgoDto ngoUserContext = getNgoUserContext();
        UUID userId = getCurrentUserId();
        CampaignEntity campaignEntity = buildCampaignEntity(requestDto, ngoUserContext, userId);
        List<CategoryEntity> allCategories = getAllCategoriesActive();
        campaignEntity.setCategories(buildCampaignCategoryEntities(campaignEntity, allCategories, requestDto));
        campaignEntity.setTags(buildCampaignTagEntities(campaignEntity, requestDto));
        campaignEntity.setImages(buildCampaignImageEntities(campaignEntity, requestDto));
        saveCampaign(campaignEntity);
        LOG.info("Campaign created successfully with ID: {}", campaignEntity.getCampaignId());
    }

    private NgoDto getNgoUserContext() {
        return getNgoService.getNgoUserContext();
    }

    private CampaignEntity buildCampaignEntity(CampaignCreateRequestDto requestDto,
                                               NgoDto ngoDto, UUID userId) {
        return CampaignEntity.builder()
                .campaignId(UUID.randomUUID())
                .createdUser(userId)
                .currentAmount(BigDecimal.ZERO)
                .description(requestDto.getDescription())
                .endDatetime(requestDto.getEndDateTime())
                .goalAmount(requestDto.getGoalAmount())
                .organizationId(UUID.fromString(ngoDto.getId()))
                .state(CampaignState.ACTIVE)
                .title(requestDto.getTitle())
                .build();
    }

    private List<CampaignCategoryEntity> buildCampaignCategoryEntities(
            CampaignEntity campaignEntity, List<CategoryEntity> allCategories,
            CampaignCreateRequestDto requestDto) {
        return allCategories.stream()
                .filter(category -> requestDto.getCategoryIds().contains(category.getCategoryId()))
                .map(category -> CampaignCategoryEntity.builder()
                        .campaign(campaignEntity)
                        .category(category)
                        .id(new CampaignCategoryId(campaignEntity.getCampaignId(), category.getCategoryId()))
                        .createdUser(campaignEntity.getCreatedUser())
                        .build())
                .collect(Collectors.toUnmodifiableList());
    }

    private List<CampaignTagEntity> buildCampaignTagEntities(
            CampaignEntity campaignEntity, CampaignCreateRequestDto requestDto) {
        if (requestDto.getTags() == null || requestDto.getTags().isEmpty()) {
            return List.of();
        }
        return requestDto.getTags().stream()
                .map(tag -> CampaignTagEntity.builder()
                        .campaign(campaignEntity)
                        .id(new CampaignTagId(campaignEntity.getCampaignId(), tag))
                        .createdUser(campaignEntity.getCreatedUser())
                        .build())
                .collect(Collectors.toUnmodifiableList());
    }

    private List<CampaignImageEntity> buildCampaignImageEntities(CampaignEntity campaignEntity, CampaignCreateRequestDto requestDto) {
        List<CampaignImageEntity> result = new ArrayList<>();
        int orderIndex = 0;
        for (UUID id : requestDto.getImageIds()) {
            result.add(CampaignImageEntity.builder()
                    .campaign(campaignEntity)
                    .fileId(id)
                    .createdUser(campaignEntity.getCreatedUser())
                    .campaignImageId(UUID.randomUUID())
                    .orderIndex(orderIndex)
                    .build());
            orderIndex++;
        }
        return  result;
    }

    private UUID getCurrentUserId() {
        return contextService.getUserId();
    }

    private void validateCampingData(CampaignCreateRequestDto requestDto) {
        if (requestDto.getGoalAmount().compareTo(BigDecimal.ZERO) <= 0) {
            LOG.warn("In validateCampingData: goalAmount must be greater than zero. GoalAmount: {}",
                    requestDto.getGoalAmount());
            throw new CustomException("Goal amount must be greater than zero.", HttpStatus.BAD_REQUEST);
        }
        LocalDateTime now = LocalDateTime.now();
        if (requestDto.getEndDateTime().isBefore(now)) {
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

    private void saveCampaign(CampaignEntity campaignEntity) {
        try {
            campaignRepository.save(campaignEntity);
        } catch (DataAccessException ex) {
            LOG.error("Error saving campaign: {}", ex.getMessage(), ex);
            throw new CustomException("Error saving campaign", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
