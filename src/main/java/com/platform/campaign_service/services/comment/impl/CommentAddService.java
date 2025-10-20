package com.platform.campaign_service.services.comment.impl;

import com.platform.campaign_service.context.IContextService;
import com.platform.campaign_service.controllers.manageExceptions.CustomException;
import com.platform.campaign_service.dtos.campaign.comment.CommentCreateDto;
import com.platform.campaign_service.entities.CampaignCommentEntity;
import com.platform.campaign_service.entities.CampaignEntity;
import com.platform.campaign_service.repositories.CampaignCommentRepository;
import com.platform.campaign_service.services.campaign.IGetCampaignService;
import com.platform.campaign_service.services.comment.ICommentAddService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
/**
 * Service implementation for adding comments to campaigns.
 */
@Service
@RequiredArgsConstructor
public class CommentAddService implements ICommentAddService {
    /** Logger instance for logging information and errors. */
    private static final Logger LOG = LoggerFactory.getLogger(CommentAddService.class);
    /** Context service for retrieving user and request context information. */
    private final IContextService contextService;
    /** Repository for managing campaign comments in the database. */
    private final CampaignCommentRepository commentRepository;
    /** Service for validating campaign existence. */
    private final IGetCampaignService getCampaignService;
    /**
     * Adds a comment to a campaign.
     *
     * @param campaignId       the ID of the campaign to which
     *                         the comment is to be added
     * @param commentCreateDto the DTO containing the comment details
     */
    @Override
    @Transactional
    public void addComment(UUID campaignId, CommentCreateDto commentCreateDto) {
        LOG.trace("Entering addComment");
        CampaignEntity campaignEntity = validateCampaignExists(campaignId);
        CampaignCommentEntity commentEntity = buildCommentEntity(commentCreateDto, campaignEntity);
        try {
            commentRepository.save(commentEntity);
        } catch (DataAccessException ex) {
            LOG.error("Error saving comment to the database", ex);
            throw new CustomException("Failed to add comment. Please try again later.",
                    HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
        LOG.trace("Comment added successfully");
    }

    private UUID getUserId() {
        return contextService.getUserId();
    }

    private CampaignEntity validateCampaignExists(UUID campaignId) {
        // This will throw an exception if the campaign does not exist
        return getCampaignService.getCampaignEntityById(campaignId);
    }

    private CampaignCommentEntity buildCommentEntity(CommentCreateDto commentCreateDto, CampaignEntity campaignEntity) {
        UUID userId = getUserId();
        return CampaignCommentEntity.builder()
                .campaignCommentId(UUID.randomUUID())
                .campaign(campaignEntity)
                .userId(userId)
                .content(commentCreateDto.getContent())
                .createdUser(userId)
                .build();
    }
}
