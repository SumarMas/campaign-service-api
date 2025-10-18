package com.platform.campaign_service.services.campaign;

import com.platform.campaign_service.dtos.campaign.CampaignCreateRequestDto;
import com.platform.campaign_service.dtos.campaign.CampaignUpdateRequestDto;

import java.util.UUID;

/**
 * Service interface for campaign-related operations.
 */
public interface ICampaignService {
    /**
     * Creates a new campaign based on the provided request data.
     *
     * @param requestDto the data transfer object containing campaign creation details
     */
    void createCampaign(CampaignCreateRequestDto requestDto);

    /**
     * Updates an existing campaign with the provided request data.
     *
     * @param campaignId the unique identifier of the campaign to be updated
     * @param requestDto the data transfer object containing campaign update details
     */
    void updateCampaign(UUID campaignId, CampaignUpdateRequestDto requestDto);
}
