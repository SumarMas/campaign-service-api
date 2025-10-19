package com.platform.campaign_service.services.campaign;

import com.platform.campaign_service.entities.CampaignEntity;
import com.platform.campaign_service.enums.CampaignState;

import java.util.List;
import java.util.Set;
import java.util.UUID;
/**
 * Service interface for querying campaigns based on various filters.
 */
public interface ICampaignQueryService {

    /**
     * Retrieves a list of CampaignEntity objects based on the provided filters.
     *
     * @param campaignState The state of the campaigns to filter by.
     * @param categoryIds   A set of category IDs to filter the campaigns.
     * @param tags          A set of tags to filter the campaigns.
     * @param organizationId The ID of the organization to which the campaigns belong.
     * @return A list of CampaignEntity objects that match the provided filters.
     */
    List<CampaignEntity> getCampaigns(CampaignState campaignState, Set<UUID> categoryIds,
                                      Set<String> tags, UUID organizationId);
}
