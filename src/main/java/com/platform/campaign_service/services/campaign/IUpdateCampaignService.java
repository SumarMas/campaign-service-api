package com.platform.campaign_service.services.campaign;

import com.platform.campaign_service.dtos.campaign.CampaignUpdateRequestDto;

import java.util.UUID;
/**
 * Service interface for updating campaign information.
 */
public interface IUpdateCampaignService {
    /**
     * Updates the information of an existing campaign.
     *
     * @param campaignId The unique identifier of the campaign to be updated.
     * @param request    The DTO containing the updated campaign information.
     */
    void  updateCampaign(UUID campaignId, CampaignUpdateRequestDto request);
}
