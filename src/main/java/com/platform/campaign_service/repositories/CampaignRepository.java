package com.platform.campaign_service.repositories;

import com.platform.campaign_service.entities.CampaignEntity;
import com.platform.campaign_service.enums.CampaignState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
/**
 * Repository interface for managing CampaignEntity instances.
 */
@Repository
public interface CampaignRepository extends JpaRepository<CampaignEntity, UUID> {
    /** Finds campaigns that need to be closed based
     * on their state, end datetime, and funding goal.
     *
     * @param state the current state of the campaigns to check
     * @param now   the current datetime for comparison
     * @return a list of campaigns that should be closed
     */
    @Query("""
        SELECT c FROM CampaignEntity c
        WHERE c.enabled = TRUE
          AND c.state = :state
          AND (c.endDatetime <= :now OR c.currentAmount >= c.goalAmount)
        """)
    List<CampaignEntity> findCampaignsToClose(CampaignState state, LocalDateTime now);
}
