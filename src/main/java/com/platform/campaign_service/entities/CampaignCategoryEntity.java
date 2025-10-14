package com.platform.campaign_service.entities;

import com.platform.campaign_service.entities.embeddable.CampaignCategoryId;
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
@Table(name = "campaign_category")
public class CampaignCategoryEntity extends AuditEntity {

    @EmbeddedId
    private CampaignCategoryId id;

    @ManyToOne()
    @MapsId("campaignId") // enlaza el campo campaignId de CampaignCategoryId
    @JoinColumn(name = "campaign_id", nullable = false)
    private CampaignEntity campaign;

    @ManyToOne
    @MapsId("categoryId") // enlaza el campo categoryId de CampaignCategoryId
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;
}
