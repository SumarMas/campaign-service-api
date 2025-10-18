package com.platform.campaign_service.repositories;

import com.platform.campaign_service.entities.CampaignEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
/**
 * Repository interface for managing CampaignEntity instances.
 */
@Repository
public interface CampaignRepository extends JpaRepository<CampaignEntity, UUID> {
}
