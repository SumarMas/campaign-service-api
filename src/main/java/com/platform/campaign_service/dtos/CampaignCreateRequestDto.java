package com.platform.campaign_service.dtos;

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
public class CampaignCreateRequestDto {
    @NotBlank(message = "Title is mandatory")
    @JsonProperty("title")
    private String title;
    @NotBlank(message = "Description is mandatory")
    @JsonProperty("description")
    private String description;
    @Positive(message = "Goal amount must be at least 1")
    @JsonProperty("goalAmount")
    private BigDecimal goalAmount;
    @Future(message = "End date and time must be in the future")
    @JsonProperty("endDateTime")
    private LocalDateTime endDateTime;
    @NotEmpty(message = "At least one category ID is required")
    @JsonProperty("categoryIds")
    private Set<UUID> categoryIds;
    @JsonProperty("tags")
    private Set<String> tags;
    @JsonProperty("imageIds")
    private List<UUID> imageIds;
}
