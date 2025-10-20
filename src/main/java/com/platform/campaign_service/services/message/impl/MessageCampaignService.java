package com.platform.campaign_service.services.message.impl;

import com.platform.campaign_service.dtos.campaign.message.MessageCampaignDto;
import com.platform.campaign_service.dtos.campaign.message.MessageCreateDto;
import com.platform.campaign_service.services.message.ICreateMessageCampService;
import com.platform.campaign_service.services.message.IDeleteMessageCampService;
import com.platform.campaign_service.services.message.IGetMessageCampService;
import com.platform.campaign_service.services.message.IMessageCampService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
/**
 * Service implementation for managing campaign messages.
 */
@Service
@RequiredArgsConstructor
public class MessageCampaignService implements IMessageCampService {
    /** Logger instance for logging purposes. */
    private static final Logger LOG = LoggerFactory.getLogger(MessageCampaignService.class);
    /** Service for creating campaign messages. */
    private final ICreateMessageCampService createMessageCampService;
    /** Service for deleting campaign messages. */
    private final IDeleteMessageCampService deleteMessageCampService;
    /** Service for retrieving campaign messages. */
    private final IGetMessageCampService getMessageCampService;
    /**
     * Creates a new campaign message associated with a specific campaign.
     *
     * @param campaignId       The unique identifier of the campaign.
     * @param messageCreateDto The DTO containing message creation details.
     */
    @Override
    public void createCampaignMessage(UUID campaignId, MessageCreateDto messageCreateDto) {
        LOG.trace("createCampaignMessage");
        createMessageCampService.createMessageCamp(campaignId, messageCreateDto);
    }
    /**
     * Deletes a campaign message by its unique identifier.
     *
     * @param messageCampaignId The UUID of the message campaign to be deleted.
     */
    @Override
    public void deleteCampaignMessage(UUID messageCampaignId) {
        LOG.trace("Delete campaign message {}", messageCampaignId);
        deleteMessageCampService.deleteMessageCampaign(messageCampaignId);
    }

    /**
     * Retrieves a list of message campaigns associated with a specific campaign ID.
     *
     * @param campaignId The UUID of the campaign.
     * @return A list of MessageCampaignDto objects.
     */
    @Override
    public List<MessageCampaignDto> getMessageCampaigns(UUID campaignId) {
        LOG.trace("getMessageCampaigns");
        return getMessageCampService.getMessageCampaigns(campaignId);
    }
}
