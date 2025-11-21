package com.platform.campaign_service.controllers;

import com.platform.campaign_service.dtos.campaign.message.MessageCampaignDto;
import com.platform.campaign_service.dtos.campaign.message.MessageCreateDto;
import com.platform.campaign_service.services.message.IMessageCampService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
/**
 * Controller for managing message campaigns.
 */
@RestController
@RequestMapping("/api/v1/message-campaigns")
@RequiredArgsConstructor
public class MessageCampaignController {
    /** Logger instance for logging information and errors. */
    private static final Logger LOG = LoggerFactory.getLogger(MessageCampaignController.class);
    /** Service for handling message campaign operations. */
    private final IMessageCampService messageCampaignService;

    /**
     * Endpoint to send a message campaign.
     *
     * @param campaignId The ID of the campaign to which the message belongs.
     * @param messageCreateDto The DTO containing message creation details.
     * @return A ResponseEntity indicating the result of the operation.
     */
    @PostMapping("/{campaignId}/send")
    public ResponseEntity<Void> sendMessageCampaign(@PathVariable("campaignId") UUID campaignId,
                                                    @RequestBody @Valid MessageCreateDto messageCreateDto) {
        LOG.info("Received request to send message campaign.");
        messageCampaignService.createCampaignMessage(campaignId, messageCreateDto);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint to retrieve message campaigns for a specific campaign ID.
     *
     * @param campaignId The ID of the campaign.
     * @return A ResponseEntity containing a list of MessageCampaignDto objects.
     */
    @GetMapping("/{campaignId}")
    public ResponseEntity<List<MessageCampaignDto>> getMessageCampaign(@PathVariable("campaignId") UUID campaignId) {
        LOG.info("Received request to get message campaigns for campaign ID: {}", campaignId);
        List<MessageCampaignDto> messageCampaigns = messageCampaignService.getMessageCampaigns(campaignId);
        return ResponseEntity.ok(messageCampaigns);
    }
    /**
     * Endpoint to delete a message campaign by its ID.
     *
     * @param messageCampaignId The ID of the message campaign to be deleted.
     * @return A ResponseEntity indicating the result of the deletion operation.
     */
    @DeleteMapping("/{messageCampaignId}/delete")
    public ResponseEntity<Void> deleteMessageCampaign(@PathVariable("messageCampaignId") UUID messageCampaignId) {
        LOG.info("Received request to delete message campaign with ID: {}", messageCampaignId);
        messageCampaignService.deleteCampaignMessage(messageCampaignId);
        return ResponseEntity.ok().build();
    }
}
