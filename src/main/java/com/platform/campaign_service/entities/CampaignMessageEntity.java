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
 * Entity representing a message associated with a campaign.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "campaign_messages")
public class CampaignMessageEntity extends AuditEntity {

    /** Unique identifier for the campaign message. */
    @Id
    @Column(name = "campaign_message_id", columnDefinition = "BINARY(16)")
    private UUID campaignMessageId;

    /** Associated campaign for the message. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id", nullable = false, columnDefinition = "BINARY(16)")
    private CampaignEntity campaign;

    /** Title of the campaign message. */
    @Column(nullable = false, name = "title")
    private String title;

    /** Description of the campaign message. */
    @Column(name = "description", columnDefinition = "LONGTEXT", nullable = false)
    private String description;
    /** Identifier of the file associated with the message. */
    @Column(name = "file_id", columnDefinition = "BINARY(16)")
    private UUID fileId;
}
