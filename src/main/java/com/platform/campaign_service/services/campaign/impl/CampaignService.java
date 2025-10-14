package com.platform.campaign_service.services.campaign.impl;

import com.platform.campaign_service.dtos.CampaignCreateRequestDto;
import com.platform.campaign_service.services.campaign.ICampaignService;
import com.platform.campaign_service.services.campaign.ICreateCampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CampaignService implements ICampaignService {
    /** Logger for logging information and errors. */
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(CampaignService.class);
    /** Service for handling campaign creation logic. */
    private final ICreateCampaignService createCampaignService;

    /**
     * Creates a new campaign based on the provided request data.
     *
     * @param requestDto the data transfer object containing campaign creation details
     */
    @Override
    public void createCampaign(CampaignCreateRequestDto requestDto) {
        LOG.trace("createCampaign");
        createCampaignService.createCampaign(requestDto);
    }
}
