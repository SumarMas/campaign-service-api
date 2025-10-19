package com.platform.campaign_service.map.impl;

import com.platform.campaign_service.dtos.categories.CategoryDto;
import com.platform.campaign_service.entities.CategoryEntity;
import com.platform.campaign_service.map.IMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Mapper implementation for CategoryEntity and CategoryDto.
 */
@Service
@RequiredArgsConstructor
public class MapperCategory implements IMapper<CategoryDto, CategoryEntity> {
    /**
     * Maps an entity to a DTO.
     *
     * @param entity the entity to map
     * @return the mapped DTO
     */
    @Override
    public CategoryDto mapToDto(CategoryEntity entity) {
        return CategoryDto.builder()
                .id(entity.getCategoryId().toString())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }
}
