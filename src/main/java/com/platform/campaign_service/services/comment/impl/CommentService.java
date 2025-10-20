package com.platform.campaign_service.services.comment.impl;

import com.platform.campaign_service.dtos.campaign.comment.CommentCampaignDto;
import com.platform.campaign_service.dtos.campaign.comment.CommentCreateDto;
import com.platform.campaign_service.services.comment.ICommentAddService;
import com.platform.campaign_service.services.comment.ICommentDeleteService;
import com.platform.campaign_service.services.comment.ICommentGetService;
import com.platform.campaign_service.services.comment.ICommentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
/**
 * Service implementation for managing comments related to campaigns.
 */
@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService {
    /** Logger for logging information and errors. */
    private static final Logger LOG = LoggerFactory.getLogger(CommentService.class);
    /** Service for adding comments. */
    private final ICommentAddService commentAddService;
    /** Service for retrieving comments. */
    private final ICommentGetService commentGetService;
    /** Service for deleting comments. */
    private final ICommentDeleteService commentDeleteService;

    /**
     * Adds a comment to a campaign.
     *
     * @param campaignId       the ID of the campaign to which
     *                         the comment is to be added
     * @param commentCreateDto the DTO containing the comment details
     */
    @Override
    public void addComment(UUID campaignId, CommentCreateDto commentCreateDto) {
        LOG.trace("Adding comment");
        commentAddService.addComment(campaignId, commentCreateDto);
    }

    /**
     * Deletes a comment by its ID.
     *
     * @param commentId the ID of the comment to be deleted
     */
    @Override
    public void deleteComment(UUID commentId) {
        LOG.trace("Deleting comment");
        commentDeleteService.deleteComment(commentId);
    }

    /**
     * Retrieves all comments for a specific campaign.
     *
     * @param campaignId the ID of the campaign whose comments are to be retrieved
     * @return a list of CommentCampaignDto representing the comments
     */
    @Override
    public List<CommentCampaignDto> getComments(UUID campaignId) {
        LOG.trace("Getting comments");
        return commentGetService.getComments(campaignId);
    }
}
