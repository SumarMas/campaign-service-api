package com.platform.campaign_service.entities;

import com.platform.campaign_service.enums.CampaignState;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Entity representing a campaign in the system.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "campaigns")
public class CampaignEntity extends AuditEntity {
    /** Precision for monetary values. */
    private static final int PRECISION = 12;
    /** Scale for monetary values. */
    private static final int SCALE = 2;
    /** Maximum length for the campaign description. */
    private static final int LENGTH_DESCRIPTION = 500;

    /** Unique identifier for the campaign. */
    @Id
    @Column(name = "campaign_id", columnDefinition = "BINARY(16)")
    private UUID campaignId;

    /** Organization ID that owns the campaign. */
    @Column(name = "organization_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID organizationId;

    /** Title of the campaign. */
    @Column(nullable = false, name = "title")
    private String title;

    /** Goal amount for the campaign. */
    @Column(name = "goal_amount", nullable = false, precision = PRECISION, scale = SCALE)
    private BigDecimal goalAmount;

    /** Current amount raised for the campaign. */
    @Column(name = "current_amount", precision = PRECISION, scale = SCALE)
    private BigDecimal currentAmount = BigDecimal.ZERO;

    /** Description of the campaign. */
    @Column(nullable = false, length = LENGTH_DESCRIPTION, name = "description")
    @Lob
    private String description;

    /** End date and time of the campaign. */
    @Column(name = "end_datetime", nullable = false)
    private LocalDateTime endDatetime;

    /** State of the campaign (e.g., ACTIVE, CLOSED). */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "state")
    private CampaignState state = CampaignState.ACTIVE;

    /** List of categories associated with the campaign. */
    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CampaignCategoryEntity> categories;

    /** List of tags associated with the campaign. */
    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CampaignTagEntity> tags;

    /** List of images associated with the campaign. */
    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CampaignImageEntity> images;

}
