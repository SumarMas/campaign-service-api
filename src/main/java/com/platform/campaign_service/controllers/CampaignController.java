package com.platform.campaign_service.controllers;

import com.platform.campaign_service.dtos.CampaignCreateRequestDto;
import com.platform.campaign_service.services.campaign.ICampaignService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/campaigns")
@RequiredArgsConstructor
public class CampaignController {
    /** Logger for logging information and errors. */
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CampaignController.class);
    /** Service for handling campaign-related operations. */
    private final ICampaignService campaignService;

    @PostMapping()
    public ResponseEntity<Void> createCampaign(@RequestBody @Valid CampaignCreateRequestDto requestDto) {
        logger.info("Creating a new campaign");
        campaignService.createCampaign(requestDto);
        return ResponseEntity.ok().build();
    }
}
