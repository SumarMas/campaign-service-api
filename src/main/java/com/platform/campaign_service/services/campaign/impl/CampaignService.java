package com.platform.campaign_service.services.campaign.impl;

import com.platform.campaign_service.dtos.campaign.CampaignCreateRequestDto;
import com.platform.campaign_service.dtos.campaign.CampaignUpdateRequestDto;
import com.platform.campaign_service.services.campaign.ICampaignService;
import com.platform.campaign_service.services.campaign.ICreateCampaignService;
import com.platform.campaign_service.services.campaign.IUpdateCampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service implementation for handling campaign-related operations.
 */
@Service
@RequiredArgsConstructor
public class CampaignService implements ICampaignService {
    /** Logger for logging information and errors. */
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(CampaignService.class);
    /** Service for handling campaign creation logic. */
    private final ICreateCampaignService createCampaignService;
    /** Service for handling campaign update logic. */
    private final IUpdateCampaignService updateCampaignService;

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

    /**
     * Updates an existing campaign with the provided request data.
     *
     * @param campaignId the unique identifier of the campaign to be updated
     * @param requestDto the data transfer object containing campaign update details
     */
    @Override
    public void updateCampaign(UUID campaignId, CampaignUpdateRequestDto requestDto) {
        LOG.trace("updateCampaign");
        updateCampaignService.updateCampaign(campaignId, requestDto);
    }
}
