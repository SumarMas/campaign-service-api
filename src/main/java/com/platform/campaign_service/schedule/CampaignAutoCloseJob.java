package com.platform.campaign_service.schedule;

import com.platform.campaign_service.dtos.campaign.CampaignClosedEventDto;
import com.platform.campaign_service.entities.CampaignEntity;
import com.platform.campaign_service.enums.CampaignState;
import com.platform.campaign_service.messaging.producer.CampaignCloseProducer;
import com.platform.campaign_service.repositories.CampaignRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;
import java.util.List;

/**
 * Scheduled job to automatically close
 * campaigns that have reached their end date or met their goal.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CampaignAutoCloseJob {
    /** Repository for accessing campaign data. */
    private final CampaignRepository campaignRepository;
    /** Producer for publishing campaign close events. */
    private final CampaignCloseProducer campaignCloseProducer;
    /**
     * Closes campaigns that have reached their end date or met their goal.
     * Runs every 10 seconds.
     */
    @Transactional
    @Scheduled(fixedRateString =  "${scheduler.campaign-auto-close.rate-ms}") // every 10 seconds
    public void closeFinishedCampaigns() {
        LocalDateTime now = LocalDateTime.now();
        List<CampaignEntity> campaignsToClose =
                campaignRepository.findCampaignsToClose(CampaignState.ACTIVE, now);
        if (campaignsToClose.isEmpty()) {
            log.info("No campaigns to close at {}", now);
            return;
        }
        for (CampaignEntity campaign : campaignsToClose) {
            try {
                String reason = campaign.getCurrentAmount().compareTo(campaign.getGoalAmount()) >= 0
                        ? "goal_reached" : "end_date_reached";

                campaign.setState(CampaignState.CLOSED);
                campaign.setLastUpdatedDatetime(now);
                campaignRepository.save(campaign);

                campaignCloseProducer.publishCampaignCloseEvent(new CampaignClosedEventDto(
                        campaign.getCampaignId(),
                        campaign.getOrganizationId(),
                        campaign.getTitle(),
                        reason
                ));

                log.info("Closed campaign {} ({})", campaign.getTitle(), reason);

            } catch (Exception ex) {
                log.error("Error closing campaign {}: {}", campaign.getCampaignId(), ex.getMessage(), ex);
            }
        }
    }
}
