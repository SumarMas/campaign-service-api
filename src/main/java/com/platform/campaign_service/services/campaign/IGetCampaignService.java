package com.platform.campaign_service.services.campaign;

import com.platform.campaign_service.entities.CampaignEntity;

import java.util.UUID;
/**
 * Service interface for retrieving campaign information.
 */
public interface IGetCampaignService {
    /**
     * Retrieves a CampaignEntity by its unique identifier.
     *
     * @param campaignId The unique identifier of the campaign.
     * @return The CampaignEntity corresponding to the provided ID.
     */
    CampaignEntity getCampaignEntityById(UUID campaignId);
}
