package com.platform.campaign_service.services.campaign;

import com.platform.campaign_service.dtos.CampaignCreateRequestDto;

/**
 * Service interface for creating campaigns.
 */
public interface ICreateCampaignService {
    /**
     * Creates a new campaign based on the provided request data.
     *
     * @param requestDto the data transfer object containing campaign creation details
     */
    void createCampaign(CampaignCreateRequestDto requestDto);
}
