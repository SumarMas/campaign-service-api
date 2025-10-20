package com.platform.campaign_service.map.impl;

import com.platform.campaign_service.dtos.campaign.message.MessageCampaignDto;
import com.platform.campaign_service.entities.CampaignMessageEntity;
import com.platform.campaign_service.map.IMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
/**
 * Mapper implementation for converting CampaignMessageEntity to MessageCampaignDto.
 */
@Service
@RequiredArgsConstructor
public class MapperMessageCampaign implements IMapper<MessageCampaignDto, CampaignMessageEntity> {
    /**
     * Maps an entity to a DTO.
     *
     * @param entity the entity to map
     * @return the mapped DTO
     */
    @Override
    public MessageCampaignDto mapToDto(CampaignMessageEntity entity) {
        return MessageCampaignDto.builder()
                .messageCampaignId(entity.getCampaignMessageId().toString())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .fileId(entity.getFileId())
                .creationDateTime(entity.getCreatedDatetime())
                .build();
    }
}
