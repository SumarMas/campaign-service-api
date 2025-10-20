package com.platform.campaign_service.services.message;

import com.platform.campaign_service.dtos.campaign.message.MessageCampaignDto;

import java.util.List;
import java.util.UUID;

/** Service interface for retrieving message campaigns. */
public interface IGetMessageCampService {
    /** Retrieves a list of message
     * campaigns associated with a specific campaign ID.
     *
     * @param campaignId The UUID of the campaign.
     * @return A list of MessageCampaignDto objects.
     */
    List<MessageCampaignDto> getMessageCampaigns(UUID campaignId);
}
