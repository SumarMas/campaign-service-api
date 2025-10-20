package com.platform.campaign_service.repositories;

import com.platform.campaign_service.entities.CampaignMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
/**
 * Repository interface for managing CampaignMessageEntity instances.
 */
@Repository
public interface CampaignMessageRepository extends JpaRepository<CampaignMessageEntity, UUID> {
    /** Finds campaign messages by campaign ID.
     *
     * @param campaignId The UUID of the campaign.
     * @return A list of CampaignMessageEntity
     * objects associated with the given campaign ID.
     */
    @Query("SELECT cme FROM CampaignMessageEntity cme "
            + "WHERE cme.campaign.campaignId = :campaignId"
            + " AND cme.enabled = true")
    List<CampaignMessageEntity> findByCampaignIdAndEnable(UUID campaignId);
}
