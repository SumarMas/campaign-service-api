package com.platform.campaign_service.services.campaign.impl;

import com.platform.campaign_service.dtos.campaign.CampaignCreateRequestDto;
import com.platform.campaign_service.dtos.campaign.CampaignDto;
import com.platform.campaign_service.dtos.campaign.CampaignUpdateRequestDto;
import com.platform.campaign_service.enums.CampaignState;
import com.platform.campaign_service.services.campaign.ICampaignService;
import com.platform.campaign_service.services.campaign.ICreateCampaignService;
import com.platform.campaign_service.services.campaign.IGetCampaignService;
import com.platform.campaign_service.services.campaign.IUpdateCampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
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
    /** Service for handling campaign retrieval logic. */
    private final IGetCampaignService getCampaignService;

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

    /**
     * Retrieves a list of CampaignDto objects based on provided filters.
     *
     * @param campaignState  The state of the campaigns to filter by.
     * @param categoryIds    A set of category IDs to filter the campaigns.
     * @param tags           A set of tags to filter the campaigns.
     * @param organizationId The ID of the organization to which the campaigns belong.
     * @return A list of CampaignDto objects that match the provided filters.
     */
    @Override
    public List<CampaignDto> getCapaignsByFilters(CampaignState campaignState,
                                                  Set<UUID> categoryIds, Set<String> tags, UUID organizationId) {
        LOG.trace("getCampaignsByFilters");
        return getCampaignService.getCapaignsByFilters(campaignState, categoryIds, tags, organizationId);
    }
}
