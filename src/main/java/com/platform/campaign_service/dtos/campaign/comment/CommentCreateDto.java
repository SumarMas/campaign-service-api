package com.platform.campaign_service.dtos.campaign.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Data Transfer Object for creating a new comment on a campaign.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateDto {
    /** Text content of the comment. */
    @JsonProperty("content")
    private String content;
}
