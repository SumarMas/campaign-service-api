package com.platform.campaign_service.entities.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Composite primary key for CampaignCategoryEntity (campaignId + categoryId).
 */
@Data
@Embeddable
public class CampaignCategoryId implements Serializable {
    /**
     * Serial version UID for serialization.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Unique identifier for the campaign.
     */
    @Column(name = "campaign_id", nullable = false, length = 36)
    private UUID campaignId;
    /**
     * Unique identifier for the category.
     */
    @Column(name = "category_id", nullable = false, length = 36)
    private UUID categoryId;

    /**
     * Default constructor.
     */
    public CampaignCategoryId() {
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    /**
     * Parameterized constructor.
     *
     * @param campaignIdParam ID of the campaign.
     * @param categoryIdParam ID of the category.
     */
    public CampaignCategoryId(UUID campaignIdParam, UUID categoryIdParam) {
        this.campaignId = campaignIdParam;
        this.categoryId = categoryIdParam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CampaignCategoryId that)) {
            return false;
        }
        return Objects.equals(campaignId, that.campaignId) && Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(campaignId, categoryId);
    }
}
