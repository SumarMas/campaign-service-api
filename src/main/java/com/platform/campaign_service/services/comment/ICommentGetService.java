package com.platform.campaign_service.services.comment;

import com.platform.campaign_service.dtos.campaign.comment.CommentCampaignDto;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for retrieving comments.
 */
public interface ICommentGetService {
    /**
     * Retrieves all comments for a specific campaign.
     *
     * @param campaignId the ID of the campaign whose comments are to be retrieved
     * @return a list of CommentCampaignDto representing the comments
     */
    List<CommentCampaignDto> getComments(UUID campaignId);
}
