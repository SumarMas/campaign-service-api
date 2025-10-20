package com.platform.campaign_service.dtos.campaign.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object representing a comment made on a campaign.
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CommentCampaignDto {
    /** Unique identifier for the comment comment. */
    @JsonProperty("comment_id")
    private String commentId;
    /** User identifier of the comment author. */
    @JsonProperty("user_id")
    private String userId;
    /** Content of the comment. */
    @JsonProperty("content")
    private String content;
    /** Creation date and time of the comment. */
    @JsonProperty("create_date_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDateTime;
}
