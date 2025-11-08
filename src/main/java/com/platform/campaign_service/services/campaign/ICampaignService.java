package com.platform.campaign_service.services.campaign;

import com.platform.campaign_service.dtos.campaign.CampaignCreateRequestDto;
import com.platform.campaign_service.dtos.campaign.CampaignDto;
import com.platform.campaign_service.dtos.campaign.CampaignUpdateRequestDto;
import com.platform.campaign_service.enums.CampaignState;

import java.util.List;
import java.util.Set;
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

    /**
     * Retrieves a list of CampaignDto objects based on provided filters.
     *
     * @param campaignState   The state of the campaigns to filter by.
     * @param categoryIds     A set of category IDs to filter the campaigns.
     * @param tags            A set of tags to filter the campaigns.
     * @param organizationId  The ID of the organization to
     *                        which the campaigns belong.
     * @return A list of CampaignDto objects that match the provided filters.
     */
    List<CampaignDto> getCapaignsByFilters(CampaignState campaignState, Set<UUID> categoryIds,
                                           Set<String> tags, UUID organizationId);

    /**
     * Retrieves a CampaignDto by its unique identifier.
     * @param campaignId The unique identifier of the campaign.
     * @return The CampaignDto corresponding to the provided ID.
     */
    CampaignDto getCampaignById(UUID campaignId);

    /**
     * Retrieves a list of CampaignDto objects based on provided NGO IDs.
     *
     * @param ngosIds A set of NGO IDs to filter the campaigns.
     * @return A list of CampaignDto objects that match the provided NGO IDs.
     */
    List<CampaignDto> getCampaignsByNgosIds(Set<UUID> ngosIds);

    /**
     * Retrieves a list of CampaignDto objects based on provided campaign IDs.
     *
     * @param campaignIds A set of campaign IDs to filter the campaigns.
     * @return A list of CampaignDto objects that match the provided campaign IDs.
     */
    List<CampaignDto> getCampaignsByIds(Set<UUID> campaignIds);
}
