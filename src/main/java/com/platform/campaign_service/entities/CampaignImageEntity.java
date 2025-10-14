package com.platform.campaign_service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
 * Entity representing an image associated with a campaign.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "campaign_images")
public class CampaignImageEntity extends AuditEntity {
    /**
     * Unique identifier for the campaign image.
     */
    @Id
    @Column(name = "campaign_image_id", columnDefinition = "BINARY(16)")
    private UUID campaignImageId;

    /** Identifier of the associated campaign. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id", nullable = false, columnDefinition = "BINARY(16)")
    private CampaignEntity campaign;

    /** Identifier of the associated file in the media service. */
    @Column(name = "file_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID fileId;

    /** Order index of the image for display purposes. */
    @Column(name = "order_index")
    private Integer orderIndex;
}
