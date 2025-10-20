package com.platform.campaign_service.dtos.campaign.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Data Transfer Object (DTO) for creating a new campaign message.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageCreateDto {
    /** Title of the campaign message. */
    @JsonProperty("title")
    @NotBlank(message = "Title is mandatory")
    private String title;
    /** Description of the campaign message. */
    @JsonProperty("description")
    @NotBlank(message = "Description is mandatory")
    private String description;
    /** Identifier of the file associated with the message. */
    @JsonProperty("fileId")
    private UUID fileId;
}
