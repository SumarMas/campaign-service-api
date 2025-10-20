package com.platform.campaign_service.services.comment.impl;

import com.platform.campaign_service.context.IContextService;
import com.platform.campaign_service.controllers.manageExceptions.CustomException;
import com.platform.campaign_service.entities.CampaignCommentEntity;
import com.platform.campaign_service.repositories.CampaignCommentRepository;
import com.platform.campaign_service.services.comment.ICommentDeleteService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Service implementation for deleting comments from campaigns.
 */
@Service
@RequiredArgsConstructor
public class CommentDeleteService implements ICommentDeleteService {
    /** Logger instance for logging information and errors. */
    private static final Logger LOG = LoggerFactory.getLogger(CommentDeleteService.class);
    /** Context service for retrieving user and request context information. */
    private final IContextService contextService;
    /** Repository for managing campaign comments in the database. */
    private final CampaignCommentRepository commentRepository;
    /**
     * Deletes a comment by its ID.
     *
     * @param commentId the ID of the comment to be deleted
     */
    @Override
    @Transactional
    public void deleteComment(UUID commentId) {
        LOG.trace("deleteComment({})", commentId);
        UUID userId = getUserId();
        CampaignCommentEntity comment = getComment(commentId);
        validateUserAuthorization(comment, userId);
        deleteCommentEntity(comment, userId);
        LOG.trace("Exiting deleteComment");
    }

    private UUID getUserId() {
        return contextService.getUserId();
    }

    private CampaignCommentEntity getComment(UUID commentId) {
        try {
            CampaignCommentEntity comment = commentRepository.findById(commentId).orElse(null);
            if (comment == null) {
                LOG.warn("Comment with ID {} not found", commentId);
                throw new CustomException("Comment not found", HttpStatus.NOT_FOUND);
            }
            return comment;
        } catch (DataAccessException ex) {
            LOG.error("Error retrieving comment from the database", ex);
            throw new CustomException("Failed to delete comment. Please try again later.",
                    HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private void validateUserAuthorization(CampaignCommentEntity comment, UUID userId) {
        if (!comment.getUserId().equals(userId) && !contextService.isAdmin()) {
            LOG.warn("User {} is not authorized to delete comment {}", userId, comment.getCampaignCommentId());
            throw new CustomException("You are not authorized to delete this comment", HttpStatus.FORBIDDEN);
        }
    }

    private void deleteCommentEntity(CampaignCommentEntity comment, UUID userId) {
        try {
            comment.setEnabled(false);
            comment.setLastUpdatedUser(userId);
            commentRepository.save(comment);
            LOG.trace("Comment with ID {} deleted successfully", comment.getCampaignCommentId());
        } catch (DataAccessException ex) {
            LOG.error("Error deleting comment from the database", ex);
            throw new CustomException("Failed to delete comment. Please try again later.",
                    HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
