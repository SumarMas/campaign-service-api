package com.platform.campaign_service.services.message;

import com.platform.campaign_service.dtos.campaign.message.MessageCampaignDto;
import com.platform.campaign_service.dtos.campaign.message.MessageCreateDto;

import java.util.List;
import java.util.UUID;

/** Service interface for managing campaign messages. */
public interface IMessageCampService {
    /** Creates a new campaign message associated with a specific campaign.
     *
     * @param campaignId The unique identifier of the campaign.
     * @param messageCreateDto The DTO containing message creation details.
     */
    void createCampaignMessage(UUID campaignId, MessageCreateDto messageCreateDto);

    /** Deletes a campaign message by its unique identifier.
     *
     * @param messageCampaignId The UUID of the message campaign to be deleted.
     */
    void deleteCampaignMessage(UUID messageCampaignId);

    /** Retrieves a list of message campaigns associated with a specific campaign ID.
     *
     * @param campaignId The UUID of the campaign.
     * @return A list of MessageCampaignDto objects.
     */
    List<MessageCampaignDto> getMessageCampaigns(UUID campaignId);
}
