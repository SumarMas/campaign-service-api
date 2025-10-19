package com.platform.campaign_service.services.campaign.impl;


import com.platform.campaign_service.controllers.manageExceptions.CustomException;
import com.platform.campaign_service.entities.CampaignEntity;
import com.platform.campaign_service.repositories.CampaignRepository;
import com.platform.campaign_service.services.campaign.IGetCampaignService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Service implementation for retrieving campaign information.
 */
@Service
@RequiredArgsConstructor
public class GetCampaignService implements IGetCampaignService {
    /** Logger for logging information and errors. */
    private static final Logger LOG = LoggerFactory.getLogger(GetCampaignService.class);
    /** Repository for accessing campaign data. */
    private final CampaignRepository campaignRepository;
    /**
     * Retrieves a CampaignEntity by its unique identifier.
     *
     * @param campaignId The unique identifier of the campaign.
     * @return The CampaignEntity corresponding to the provided ID.
     */
    @Override
    public CampaignEntity getCampaignEntityById(UUID campaignId) {
        LOG.trace("Getting campaign with ID: {}", campaignId);
        try {
            Optional<CampaignEntity> campaignEntity = campaignRepository.findById(campaignId);
            if (campaignEntity.isEmpty()) {
                LOG.trace("No campaign with ID: {}", campaignId);
                throw new CustomException("Campaign not found", HttpStatus.NOT_FOUND);
            }
            return campaignEntity.get();
        } catch (DataAccessException ex) {
            LOG.error("Data access error while retrieving campaign with ID: {}", campaignId, ex);
            throw new CustomException("An error occurred while retrieving campaign", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
