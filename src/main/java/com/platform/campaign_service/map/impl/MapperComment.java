package com.platform.campaign_service.map.impl;

import com.platform.campaign_service.dtos.campaign.comment.CommentCampaignDto;
import com.platform.campaign_service.entities.CampaignCommentEntity;
import com.platform.campaign_service.map.IMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Mapper implementation for CampaignCommentEntity and CommentCampaignDto.
 */
@Service
@RequiredArgsConstructor
public class MapperComment implements IMapper<CommentCampaignDto, CampaignCommentEntity> {
    /**
     * Maps an entity to a DTO.
     *
     * @param entity the entity to map
     * @return the mapped DTO
     */
    @Override
    public CommentCampaignDto mapToDto(CampaignCommentEntity entity) {
        if (entity == null) {
            return null;
        }
        return CommentCampaignDto.builder()
                .commentId(entity.getCampaignCommentId().toString())
                .content(entity.getContent())
                .userId(entity.getUserId().toString())
                .createDateTime(entity.getCreatedDatetime())
                .build();
    }
}
