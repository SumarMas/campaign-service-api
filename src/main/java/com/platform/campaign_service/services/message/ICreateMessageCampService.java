package com.platform.campaign_service.services.message;

import com.platform.campaign_service.dtos.campaign.message.MessageCreateDto;

import java.util.UUID;

public interface ICreateMessageCampService {
    /**
     * Creates a new campaign message based on the provided DTO.
     *
     * @param campaignId     Identifier of the campaign.
     * @param messageCreateDto Data Transfer Object containing
     *                         message creation details.
     */
    void createMessageCamp(UUID campaignId, MessageCreateDto messageCreateDto);
}
