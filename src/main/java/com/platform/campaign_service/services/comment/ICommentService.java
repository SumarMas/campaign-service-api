package com.platform.campaign_service.services.comment;

import com.platform.campaign_service.dtos.campaign.comment.CommentCampaignDto;
import com.platform.campaign_service.dtos.campaign.comment.CommentCreateDto;

import java.util.List;
import java.util.UUID;
/**
 * Service interface for managing comments related to campaigns.
 */
public interface ICommentService {
    /**
     * Adds a comment to a campaign.
     *
     * @param campaignId        the ID of the campaign to which
     *                          the comment is to be added
     * @param commentCreateDto  the DTO containing the comment details
     */
    void addComment(UUID campaignId, CommentCreateDto commentCreateDto);

    /**
     * Deletes a comment by its ID.
     *
     * @param commentId the ID of the comment to be deleted
     */
    void deleteComment(UUID commentId);

    /**
     * Retrieves all comments for a specific campaign.
     *
     * @param campaignId the ID of the campaign whose comments are to be retrieved
     * @return a list of CommentCampaignDto representing the comments
     */
    List<CommentCampaignDto> getComments(UUID campaignId);
}
