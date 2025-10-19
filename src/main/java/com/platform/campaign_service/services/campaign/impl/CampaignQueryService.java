package com.platform.campaign_service.services.campaign.impl;

import com.platform.campaign_service.entities.CampaignCategoryEntity;
import com.platform.campaign_service.entities.CampaignEntity;
import com.platform.campaign_service.entities.CampaignTagEntity;
import com.platform.campaign_service.enums.CampaignState;
import com.platform.campaign_service.services.campaign.ICampaignQueryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
/**
 * Service implementation for querying campaigns based on various filters.
 */
@Service
@RequiredArgsConstructor
public class CampaignQueryService implements ICampaignQueryService {
    /** Logger instance for logging purposes. */
    private static final Logger LOG = LoggerFactory.getLogger(CampaignQueryService.class);
    /** EntityManager for interacting with the persistence context. */
    private final EntityManager entityManager;
    /**
     * Retrieves a list of CampaignEntity objects based on the provided filters.
     *
     * @param campaignState  The state of the campaigns to filter by.
     * @param categoryIds    A set of category IDs to filter the campaigns.
     * @param tags           A set of tags to filter the campaigns.
     * @param organizationId The ID of the organization to which the campaigns belong.
     * @return A list of CampaignEntity objects that match the provided filters.
     */
    @Override
    public List<CampaignEntity> getCampaigns(CampaignState campaignState, Set<UUID> categoryIds, Set<String> tags, UUID organizationId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CampaignEntity> query = cb.createQuery(CampaignEntity.class);
        Root<CampaignEntity> root = query.from(CampaignEntity.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.isTrue(root.get("enabled")));

        if (campaignState != null) {
            predicates.add(cb.equal(root.get("state"), campaignState));
        }

        if (organizationId != null) {
            predicates.add(cb.equal(root.get("organizationId"), organizationId));
        }

        if (categoryIds != null && !categoryIds.isEmpty()) {
            Join<CampaignEntity, CampaignCategoryEntity> catJoin = root.join("categories", JoinType.INNER);
            predicates.add(catJoin.get("category").get("categoryId").in(categoryIds));
        }
        if (tags != null && !tags.isEmpty()) {
            Join<CampaignEntity, CampaignTagEntity> tagJoin = root.join("tags", JoinType.INNER);
            predicates.add(tagJoin.get("id").get("tag").in(tags));
        }
        query.select(root).distinct(true);
        query.where(cb.and(predicates.toArray(new Predicate[0])));

        List<CampaignEntity> campaigns = entityManager.createQuery(query).getResultList();
        LOG.info("Found {} campaigns matching the criteria.", campaigns.size());
        return campaigns;
    }
}
