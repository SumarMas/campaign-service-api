package com.platform.campaign_service.schedule;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;

/**
 * Scheduled job to automatically close
 * campaigns that have reached their end date or met their goal.
 */
@Component
@RequiredArgsConstructor
public class CampaignAutoCloseJob {
    /** Logger instance for logging purposes. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CampaignAutoCloseJob.class);
    /** EntityManager for interacting with the persistence context. */
    private final EntityManager entityManager;


    /**
     * Closes campaigns that have reached their end date or met their goal.
     * Runs every 10 seconds.
     */
    @Transactional
    @Scheduled(fixedRateString =  "${scheduler.campaign-auto-close.rate-ms}") // every 10 seconds
    public void closeFinishedCampaigns() {
        LOGGER.debug("Start to close finished campaigns");
        String sql = """
            UPDATE campaigns
            SET state = 'CLOSED',
                last_updated_datetime = CURRENT_TIMESTAMP
            WHERE enabled = TRUE
              AND state = 'ACTIVE'
              AND (end_datetime <= CURRENT_TIMESTAMP OR current_amount >= goal_amount)
            """;
        try {
            int updatedCount = entityManager.createNativeQuery(sql).executeUpdate();
            if (updatedCount > 0) {
                LOGGER.info("Closed {} finished campaigns", updatedCount);
            } else {
                LOGGER.debug("No campaigns to close at this time");
            }
        }  catch (DataAccessException e) {
            LOGGER.error("Database access error: {}", e.getMessage(), e);
        }
        LOGGER.debug("End to close finished campaigns");
    }
}
