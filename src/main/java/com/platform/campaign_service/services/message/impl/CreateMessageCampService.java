package com.platform.campaign_service.services.message.impl;


import com.platform.campaign_service.context.IContextService;
import com.platform.campaign_service.controllers.manageExceptions.CustomException;
import com.platform.campaign_service.dtos.campaign.message.MessageCreateDto;
import com.platform.campaign_service.dtos.ngo.NgoDto;
import com.platform.campaign_service.entities.CampaignEntity;
import com.platform.campaign_service.entities.CampaignMessageEntity;
import com.platform.campaign_service.repositories.CampaignMessageRepository;
import com.platform.campaign_service.repositories.CampaignRepository;
import com.platform.campaign_service.services.message.ICreateMessageCampService;
import com.platform.campaign_service.services.ngo.IGetNgoService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateMessageCampService implements ICreateMessageCampService {
    /** Logger instance for logging information and errors. */
    private static final Logger LOG = LoggerFactory.getLogger(CreateMessageCampService.class);
    /** Repository for managing campaign messages. */
    private final CampaignMessageRepository campaignMessageRepository;
    /** Repository for managing campaigns. */
    private final CampaignRepository campaignRepository;
    /** Service for accessing context-related information. */
    private final IContextService contextService;
    /** Service for retrieving NGO information. */
    private final IGetNgoService getNgoService;
    /**
     * Creates a new campaign message based on the provided DTO.
     *
     * @param messageCreateDto Data Transfer Object containing
     *                         message creation details.
     */
    @Override
    @Transactional
    public void createMessageCamp(UUID campaignId, MessageCreateDto messageCreateDto) {
        LOG.trace("In CreateMessageCampService createMessageCamp");
        UUID userId = getUserContext();
        CampaignEntity campaignEntity = getCampaignEntity(campaignId);
        NgoDto ngoDto = getNgoUserContext();
        validateNgoOwnership(userId.toString(), ngoDto.getUserCreator().getId());
        CampaignMessageEntity campaignMessageEntity = buildCampaignMessageEntity(
                campaignEntity,
                messageCreateDto,
                userId);
        saveCampaignMessageEntity(campaignMessageEntity);
    }

    private UUID getUserContext() {
        return contextService.getUserId();
    }

    private CampaignEntity getCampaignEntity(UUID campaignId) {
        return campaignRepository.findById(campaignId).orElseThrow(() -> {
            LOG.warn("Campaign with id {} not found", campaignId);
            return new CustomException("Campaign not found", HttpStatus.NOT_FOUND);
        });
    }

    private NgoDto getNgoUserContext() {
        return getNgoService.getNgoUserContext();
    }

    private void validateNgoOwnership(String userId, String ngoOwnerId) {
        if (!userId.equals(ngoOwnerId)) {
            LOG.warn("User with id {} is not authorized to create message for this campaign", userId);
            throw new CustomException("Unauthorized to create message for this campaign", HttpStatus.FORBIDDEN);
        }
    }

    private CampaignMessageEntity buildCampaignMessageEntity(
            CampaignEntity campaignEntity,
            MessageCreateDto messageCreateDto,
            UUID userId) {
        return CampaignMessageEntity.builder()
                .campaignMessageId(UUID.randomUUID())
                .campaign(campaignEntity)
                .title(messageCreateDto.getTitle())
                .description(messageCreateDto.getDescription())
                .fileId(messageCreateDto.getFileId())
                .createdUser(userId)
                .build();
    }

    private void saveCampaignMessageEntity(CampaignMessageEntity campaignMessageEntity) {
        try {
            campaignMessageRepository.save(campaignMessageEntity);
        } catch (DataAccessException e) {
            LOG.error("Error occurred while saving campaign message: {}", e.getMessage());
            throw new CustomException("Failed to create campaign message", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
        LOG.info("Campaign message with id {} created successfully", campaignMessageEntity.getCampaignMessageId());
    }
}
