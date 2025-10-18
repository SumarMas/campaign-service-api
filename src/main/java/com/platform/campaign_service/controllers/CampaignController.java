package com.platform.campaign_service.controllers;

import com.platform.campaign_service.dtos.campaign.CampaignCreateRequestDto;
import com.platform.campaign_service.dtos.campaign.CampaignUpdateRequestDto;
import com.platform.campaign_service.services.campaign.ICampaignService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Controller for handling campaign-related requests.
 */
@RestController
@RequestMapping("/api/v1/campaigns")
@RequiredArgsConstructor
public class CampaignController {
    /** Logger for logging information and errors. */
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(CampaignController.class);
    /** Service for handling campaign-related operations. */
    private final ICampaignService campaignService;

    /**
     * Handles campaign creation requests.
     *
     * @param requestDto the campaign creation details
     * @return a ResponseEntity with HTTP status 200 (OK)
     * if the creation is successful
     */
    @PostMapping("/create")
    public ResponseEntity<Void> createCampaign(@RequestBody @Valid CampaignCreateRequestDto requestDto) {
        LOG.info("Creating a new campaign");
        campaignService.createCampaign(requestDto);
        return ResponseEntity.ok().build();
    }

    /**
     * Handles campaign update requests.
     *
     * @param campaignId the unique identifier of the campaign to be updated
     * @param requestDto the campaign update details
     * @return a ResponseEntity with HTTP status 200 (OK)
     * if the update is successful
     */
    @PutMapping("/{campaignId}/update")
    public ResponseEntity<Void> updateCampaign(@PathVariable UUID campaignId,
                                               @RequestBody @Valid CampaignUpdateRequestDto requestDto) {
        LOG.info("Updating campaign with ID: {}", campaignId);
        campaignService.updateCampaign(campaignId, requestDto);
        return ResponseEntity.ok().build();
    }
}
