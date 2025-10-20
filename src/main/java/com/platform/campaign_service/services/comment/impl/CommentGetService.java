package com.platform.campaign_service.services.comment.impl;

import com.platform.campaign_service.dtos.campaign.comment.CommentCampaignDto;
import com.platform.campaign_service.entities.CampaignCommentEntity;
import com.platform.campaign_service.map.impl.MapperComment;
import com.platform.campaign_service.repositories.CampaignCommentRepository;
import com.platform.campaign_service.services.comment.ICommentGetService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service implementation for retrieving comments related to campaigns.
 */
@Service
@RequiredArgsConstructor
public class CommentGetService implements ICommentGetService {
    /** Logger instance for logging information and errors. */
    private static final Logger LOG = LoggerFactory.getLogger(CommentGetService.class);
    /** Repository for managing campaign comments in the database. */
    private final CampaignCommentRepository commentRepository;
    /** Mapper for converting comment entities to DTOs. */
    private final MapperComment mapperComment;

    /**
     * Retrieves all comments for a specific campaign.
     *
     * @param campaignId the ID of the campaign whose comments are to be retrieved
     * @return a list of CommentCampaignDto representing the comments
     */
    @Override
    public List<CommentCampaignDto> getComments(UUID campaignId) {
        LOG.trace("Entering getComments");
        List<CampaignCommentEntity> commentEntities = getCampaignComments(campaignId);
        List<CommentCampaignDto> commentDtos = mapToDtoList(commentEntities);
        LOG.trace("Retrieved {} comments for campaign {}", commentDtos.size(), campaignId);
        return commentDtos;
    }

    private List<CampaignCommentEntity> getCampaignComments(UUID campaignId) {
        try {
            return commentRepository.findByCampaignId(campaignId);
        } catch (DataAccessException ex) {
            LOG.error("Error retrieving comments from the database", ex);
            return List.of();
        }
    }

    private List<CommentCampaignDto> mapToDtoList(List<CampaignCommentEntity> commentEntities) {
        return commentEntities.stream()
                .map(mapperComment::mapToDto)
                .toList();
    }
}
