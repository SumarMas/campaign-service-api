package com.platform.campaign_service.map.impl;

import com.platform.campaign_service.dtos.campaign.CampaignDto;
import com.platform.campaign_service.dtos.categories.CategoryDto;
import com.platform.campaign_service.entities.CampaignCategoryEntity;
import com.platform.campaign_service.entities.CampaignEntity;
import com.platform.campaign_service.entities.CampaignImageEntity;
import com.platform.campaign_service.entities.CampaignTagEntity;
import com.platform.campaign_service.entities.CategoryEntity;
import com.platform.campaign_service.map.IMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
/**
 * Mapper implementation for CampaignEntity to CampaignDto.
 */
@Service
@RequiredArgsConstructor
public class MapperCampaign implements IMapper<CampaignDto, CampaignEntity> {
    /** Mapper for CategoryEntity to CategoryDto. */
    private final IMapper<CategoryDto, CategoryEntity> mapperCategory;
    /**
     * Maps an entity to a DTO.
     *
     * @param entity the entity to map
     * @return the mapped DTO
     */
    @Override
    public CampaignDto mapToDto(CampaignEntity entity) {
        return CampaignDto.builder()
                .id(entity.getCampaignId().toString())
                .title(entity.getTitle())
                .goalAmount(entity.getGoalAmount())
                .currentAmount(entity.getCurrentAmount())
                .description(entity.getDescription())
                .endDateTime(entity.getEndDatetime())
                .createDateTime(entity.getCreatedDatetime())
                .campaignState(entity.getState())
                .tags(mapTags(entity.getTags()))
                .images(mapImages(entity.getImages()))
                .categories(mapCategories(entity.getCategories()))
                .build();
    }

    private List<String> mapTags(List<CampaignTagEntity> tagsEntities) {
        List<String> tags = new ArrayList<>();
        for (CampaignTagEntity tagEntity : tagsEntities.stream().filter(CampaignTagEntity::getEnabled).toList()) {
            tags.add(tagEntity.getTag());
        }
        return tags;
    }

    private List<String> mapImages(List<CampaignImageEntity> imageEntities) {
        List<String> images = new ArrayList<>();
        for (CampaignImageEntity imageEntity : imageEntities.stream().filter(CampaignImageEntity::getEnabled).toList()) {
            images.add(imageEntity.getFileId().toString());
        }
        return images;
    }

    private List<CategoryDto> mapCategories(List<CampaignCategoryEntity> campaignCategoryEntities) {
        List<CategoryDto> categories = new ArrayList<>();
        for (CampaignCategoryEntity campaignCategoryEntity : campaignCategoryEntities
                .stream().filter(CampaignCategoryEntity::getEnabled).toList()) {
            categories.add(mapperCategory.mapToDto(campaignCategoryEntity.getCategory()));
        }
        return categories;
    }
}
