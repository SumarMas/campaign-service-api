package com.platform.campaign_service.services.comment;

import java.util.UUID;
/**
 * Service interface for deleting comments.
 */
public interface ICommentDeleteService {
    /**
     * Deletes a comment by its ID.
     *
     * @param commentId the ID of the comment to be deleted
     */
    void deleteComment(UUID commentId);
}
