package com.platform.campaign_service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
 * Entity representing a comment made on a campaign.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "campaign_comments")
public class CampaignCommentEntity extends  AuditEntity {
    /** Unique identifier for the campaign comment. */
    @Id
    @Column(name = "campaign_comment_id", columnDefinition = "BINARY(16)")
    private UUID campaignCommentId;

    /** Identifier of the associated campaign. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id", nullable = false, columnDefinition = "BINARY(16)")
    private CampaignEntity campaign;

    /** Identifier of the user who made the comment. */
    @Column(name = "user_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID userId;

    /** Content of the comment. */
    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;
}
