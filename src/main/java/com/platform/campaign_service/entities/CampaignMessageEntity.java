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

@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "campaign_messages")
public class CampaignMessageEntity extends AuditEntity{
    @Id
    @Column(name = "campaign_message_id", columnDefinition = "BINARY(16)")
    private UUID campaignMessageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id", nullable = false, columnDefinition = "BINARY(16)")
    private CampaignEntity campaign;

    @Column(nullable = false, name = "title")
    private String title;

    @Column(name = "description", columnDefinition = "LONGTEXT", nullable = false)
    private String description;

    @Column(name = "file_id", columnDefinition = "BINARY(16)")
    private UUID fileId;
}
