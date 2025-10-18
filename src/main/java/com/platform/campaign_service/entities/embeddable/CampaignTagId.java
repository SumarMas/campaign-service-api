package com.platform.campaign_service.entities.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Composite primary key for CampaignTagEntity (campaignId + tag).
 */
@Data
@Embeddable
public class CampaignTagId implements Serializable {
    /** Maximum length for the tag. */
    private static final int LENGTH_TAG = 100;
    /** Length of UUID string representation. */
    private static final int LENGTH_UUID = 36;
    /**
     * Serial version UID for serialization.
     */
    private static  final long serialVersionUID = 1L;

    /**
     * Unique identifier for the campaign.
     */
    @Column(name = "campaign_id", nullable = false, length = LENGTH_UUID)
    private UUID campaignId;
    /**
     * Tag associated with the campaign.
     */
    @Column(name = "tag_name", nullable = false, length = LENGTH_TAG)
    private String tag;

    /**
     * Default constructor.
     */
    public CampaignTagId() {
        // This constructor is intentionally empty. Nothing special is needed here.
    }
    /**
     * Parameterized constructor.
     *
     * @param campaignIdParam ID of the campaign.
     * @param tagParam        Tag associated with the campaign.
     */
    public CampaignTagId(UUID campaignIdParam, String tagParam) {
        this.campaignId = campaignIdParam;
        this.tag = tagParam;
    }
    /**
     * Compares this composite key with another object for equality.
     *
     * @param o The object to compare with.
     * @return True if both objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CampaignTagId that)) {
            return false;
        }
        return campaignId.equals(that.campaignId) && tag.equals(that.tag);
    }
    /**
     * Generates a hash code for the composite key.
     *
     * @return Hash code based on campaignId and tag.
     */
    @Override
    public int hashCode() {
        return Objects.hash(campaignId, tag);
    }
}
