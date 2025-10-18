package com.platform.campaign_service.dtos.campaign;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignUpdateRequestDto {
    /** Title of the campaign. */
    @JsonProperty("title")
    private String title;
    /** Description of the campaign. */
    @JsonProperty("description")
    private String description;
    /** Goal amount for the campaign. */
    @Positive(message = "Goal amount must be at least 1")
    @JsonProperty("goalAmount")
    private BigDecimal goalAmount;
    /** End date and time for the campaign. */
    @Future(message = "End date and time must be in the future")
    @JsonProperty("endDateTime")
    private LocalDateTime endDateTime;
    /** Set of category IDs associated with the campaign. */
    @NotEmpty(message = "At least one category ID is required")
    @JsonProperty("categoryIds")
    private Set<UUID> categoryIds;
    /** Set of tags associated with the campaign. */
    @JsonProperty("tags")
    private Set<String> tags;
    /** List of image IDs associated with the campaign. */
    @JsonProperty("imageIds")
    private List<UUID> imageIds;
}
