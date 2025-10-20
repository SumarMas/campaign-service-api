package com.platform.campaign_service.repositories;

import com.platform.campaign_service.entities.CampaignCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
/**
 * Repository interface for managing CampaignCommentEntity instances.
 */
@Repository
public interface CampaignCommentRepository extends JpaRepository<CampaignCommentEntity, UUID> {
    /**
     * Finds all comments associated with a specific campaign ID.
     *
     * @param campaignId the unique identifier of the campaign
     * @return a list of CampaignCommentEntity instances related to the campaign
     */
    @Query("SELECT c FROM CampaignCommentEntity c WHERE c.campaign.campaignId = :campaignId")
    List<CampaignCommentEntity> findByCampaignId(UUID campaignId);

}
