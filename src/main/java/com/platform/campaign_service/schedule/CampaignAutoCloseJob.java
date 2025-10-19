package com.platform.campaign_service.schedule;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.QueryTimeoutException;
import jakarta.persistence.LockTimeoutException;

import org.hibernate.exception.JDBCConnectionException;
/**
 * Scheduled job to automatically close
 * campaigns that have reached their end date or met their goal.
 */
@Component
@RequiredArgsConstructor
public class CampaignAutoCloseJob {
    /** Time interval for the scheduled job in milliseconds (10 seconds). */
    private static final long TEN_SECONDS = 10_000L;
    /** Logger instance for logging purposes. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CampaignAutoCloseJob.class);
    /** EntityManager for interacting with the persistence context. */
    private final EntityManager entityManager;

    /**
     * Closes campaigns that have reached their end date or met their goal.
     * Runs every 10 seconds.
     */
    @Transactional
    @Scheduled(fixedRate = TEN_SECONDS) // every 10 seconds
    public void closeFinishedCampaigns() {
        LOGGER.info("Start to close finished campaigns");
        String sql = """
            UPDATE campaigns
            SET state = 'CLOSED',
                last_updated_datetime = NOW()
            WHERE enabled = TRUE
              AND state = 'ACTIVE'
              AND (end_datetime <= NOW() OR current_amount >= goal_amount)
            """;
        try {
            int updatedCount = entityManager.createNativeQuery(sql).executeUpdate();
            LOGGER.info("Number of campaigns closed: {}", updatedCount);
        } catch (QueryTimeoutException | LockTimeoutException e) {
            LOGGER.warn("Campaign auto-close query timed out: {}", e.getMessage());
        } catch (JDBCConnectionException e) {
            LOGGER.error("Lost DB connection while closing campaigns: {}", e.getMessage());
        } catch (PersistenceException e) {
            LOGGER.error("JPA persistence error closing campaigns: {}", e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Unexpected error in auto-close job", e);
        }
        LOGGER.info("End to close finished campaigns");
    }
}
