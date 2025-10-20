package com.platform.campaign_service.services.message.impl;

import com.platform.campaign_service.context.IContextService;
import com.platform.campaign_service.controllers.manageExceptions.CustomException;
import com.platform.campaign_service.dtos.ngo.NgoDto;
import com.platform.campaign_service.entities.CampaignEntity;
import com.platform.campaign_service.entities.CampaignMessageEntity;
import com.platform.campaign_service.repositories.CampaignMessageRepository;
import com.platform.campaign_service.repositories.CampaignRepository;
import com.platform.campaign_service.services.message.IDeleteMessageCampService;
import com.platform.campaign_service.services.ngo.IGetNgoService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Service implementation for deleting message campaigns.
 */
@Service
@RequiredArgsConstructor
public class DeleteMessageCampService implements IDeleteMessageCampService {
    /** Logger instance for logging information and errors. */
    private static final Logger LOG = LoggerFactory.getLogger(DeleteMessageCampService.class);
    /** Repository for accessing campaign data. */
    private final CampaignRepository campaignRepository;
    /** Repository for accessing campaign message data. */
    private final CampaignMessageRepository campaignMessageRepository;
    /** Service for retrieving NGO information. */
    private final IGetNgoService getNgoService;
    /** Service for accessing user context information. */
    private final IContextService contextService;
    /**
     * Deletes a message campaign by its ID.
     *
     * @param messageCampaignId The UUID of the
     *                          message campaign to be deleted.
     */
    @Override
    @Transactional
    public void deleteMessageCampaign(UUID messageCampaignId) {
        LOG.trace("Delete message campaign {}", messageCampaignId);
        UUID userId = getUserId();
        CampaignMessageEntity campaignMessageEntity = getMessageCampaignById(messageCampaignId);
        validateUserAuthorization(userId, campaignMessageEntity);

        deleteCampaignMessageEntity(campaignMessageEntity, userId);
        LOG.trace("Message campaign with ID {} has been deleted by user with ID {}.", messageCampaignId, userId);
    }

    private UUID getUserId() {
        return contextService.getUserId();
    }

    private NgoDto getNgoUserContext() {
        return getNgoService.getNgoUserContext();
    }

    private boolean isAdmin() {
        return contextService.isAdmin();
    }

    private CampaignMessageEntity getMessageCampaignById(UUID messageCampaignId) {
        return campaignMessageRepository.findById(messageCampaignId)
                .orElseThrow(() -> {
                    LOG.warn("Message campaign with ID {} not found.", messageCampaignId);
                    return new CustomException("Message campaign not found", HttpStatus.NOT_FOUND);
                });
    }

    private void validateUserAuthorization(UUID userId, CampaignMessageEntity campaignMessageEntity) {
        if (isAdmin()) {
            return;
        }
        NgoDto ngoUserContext = getNgoUserContext();
        CampaignEntity campaignEntity = campaignMessageEntity.getCampaign();
        if (!campaignEntity.getOrganizationId().toString().equals(ngoUserContext.getId())) {
            LOG.warn("User with ID {} is not authorized to delete message campaign with ID {}.",
                    userId, campaignMessageEntity.getCampaignMessageId());
            throw new CustomException("User is not authorized to delete this message campaign", HttpStatus.FORBIDDEN);
        }
        if (!contextService.isThisUserId(ngoUserContext.getUserCreator().getId())) {
            LOG.warn("User with ID {} is not the owner of the NGO with ID {}.",
                    userId, ngoUserContext.getId());
            throw new CustomException("User is not the owner of the NGO", HttpStatus.FORBIDDEN);
        }
    }

    private void deleteCampaignMessageEntity(CampaignMessageEntity campaignMessageEntity, UUID userId) {
        try {
            campaignMessageEntity.setEnabled(false);
            campaignMessageEntity.setLastUpdatedUser(userId);
            campaignMessageRepository.save(campaignMessageEntity);
        } catch (DataAccessException ex) {
            LOG.error("Error deleting message campaign with ID {}: {}",
                    campaignMessageEntity.getCampaignMessageId(), ex.getMessage());
            throw new CustomException("Error deleting message campaign", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
