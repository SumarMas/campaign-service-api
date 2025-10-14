package com.platform.campaign_service.dtos;

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
    private String title;
    private String description;
    private BigDecimal goalAmount;
    private LocalDateTime endDateTime;
    private Set<UUID> categoryIds;
    private Set<String> tags;
    private List<UUID> imageIds;
}
