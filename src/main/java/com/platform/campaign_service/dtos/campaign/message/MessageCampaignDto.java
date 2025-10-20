package com.platform.campaign_service.dtos.campaign.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;
/**
 * Data Transfer Object (DTO) for campaign messages.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageCampaignDto {
    /** Unique identifier for the campaign message. */
    @JsonProperty("messageCampaignId")
    private String messageCampaignId;
    /** Title of the campaign message. */
    @JsonProperty("title")
    private String title;
    /** Description of the campaign message. */
    @JsonProperty("description")
    private String description;
    /** Identifier of the file associated with the message. */
    @JsonProperty("fileId")
    private UUID fileId;
    /** Creation date and time of the campaign message. */
    @JsonProperty("creationDateTime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDateTime;
}
