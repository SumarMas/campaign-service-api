package com.platform.campaign_service.entities;

import com.platform.campaign_service.entities.embeddable.CampaignTagId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "campaign_tags")
public class CampaignTagEntity extends AuditEntity{
    @EmbeddedId
    private CampaignTagId id;

    @ManyToOne()
    @MapsId("campaignId") // enlaza el campo campaignId de CampaignCategoryId
    @JoinColumn(name = "campaign_id", nullable = false)
    private CampaignEntity campaign;

    /**
     * Gets the tag from the composite key.
     *
     * @return The tag.
     */
    public String getTag() {
        return id != null ? id.getTag() : null;
    }
    /**
     * Sets the tag in the composite key.
     *
     * @param tag The tag to set.
     */
    public void setTag(String tag) {
        if (id == null) {
            id = new CampaignTagId();
        }
        id.setTag(tag);
    }
}
