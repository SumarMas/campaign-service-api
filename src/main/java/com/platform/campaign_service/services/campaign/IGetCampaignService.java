package com.platform.campaign_service.services.campaign;

import com.platform.campaign_service.dtos.campaign.CampaignDto;
import com.platform.campaign_service.entities.CampaignEntity;
import com.platform.campaign_service.enums.CampaignState;

import java.util.List;
import java.util.Set;
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
}
