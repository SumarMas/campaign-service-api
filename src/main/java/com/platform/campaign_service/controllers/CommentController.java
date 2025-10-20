package com.platform.campaign_service.controllers;

import com.platform.campaign_service.dtos.campaign.comment.CommentCampaignDto;
import com.platform.campaign_service.dtos.campaign.comment.CommentCreateDto;
import com.platform.campaign_service.services.comment.ICommentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    /** Logger for logging information and errors. */
    private static final Logger LOG = LoggerFactory.getLogger(CommentController.class);
    /** Service for handling comment-related operations. */
    private final ICommentService commentService;

    /**
     * Handles requests to fetch all comments for a specific campaign.
     *
     * @param campaignId the ID of the campaign whose comments are to be retrieved
     * @return a list of CommentCampaignDto representing the comments
     */
    @GetMapping("/{campaignId}")
    public ResponseEntity<List<CommentCampaignDto>> getComments(@PathVariable UUID campaignId) {
        LOG.trace("Fetching comments for campaign with ID: {}", campaignId);
        List<CommentCampaignDto> comments = commentService.getComments(campaignId);
        return ResponseEntity.ok(comments);
    }

    /**
     * Handles requests to add a comment to a specific campaign.
     *
     * @param campaignId        the ID of the campaign to which the comment is to be added
     * @param commentCreateDto  the DTO containing the comment details
     * @return a ResponseEntity indicating the result of the operation
     */
    @PostMapping("/{campaignId}/add")
    public ResponseEntity<Void> addComment(@PathVariable UUID campaignId, @RequestBody CommentCreateDto commentCreateDto) {
        LOG.trace("Adding comment to campaign with ID: {}", campaignId);
        commentService.addComment(campaignId, commentCreateDto);
        return ResponseEntity.ok().build();
    }

    /**
     * Handles requests to delete a comment by its ID.
     *
     * @param commentId the ID of the comment to be deleted
     * @return a ResponseEntity indicating the result of the operation
     */
    @DeleteMapping("/{commentId}/delete")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID commentId) {
        LOG.trace("Deleting comment with ID: {}", commentId);
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }
}
