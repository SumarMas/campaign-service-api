package com.platform.campaign_service.services.comment;

import com.platform.campaign_service.dtos.campaign.comment.CommentCreateDto;

import java.util.UUID;

/**
 * Service interface for adding comments to campaigns.
 */
public interface ICommentAddService {
    /**
     * Adds a comment to a campaign.
     *
     * @param campaignId        the ID of the campaign to which
     *                          the comment is to be added
     * @param commentCreateDto  the DTO containing the comment details
     */
    void addComment(UUID campaignId, CommentCreateDto commentCreateDto);
}
