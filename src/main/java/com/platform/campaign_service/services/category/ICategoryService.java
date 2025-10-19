package com.platform.campaign_service.services.category;

import com.platform.campaign_service.dtos.categories.CategoryDto;
import com.platform.campaign_service.entities.CategoryEntity;

import java.util.List;

/**
 * Service interface for managing categories.
 */
public interface ICategoryService {
    /**
     * Retrieves all active categories.
     *
     * @return a list of active CategoryEntity objects
     */
    List<CategoryEntity> getAllCategoriesActive();

    /**
     * Retrieves all active categories as DTOs.
     *
     * @return a list of active CategoryDto objects
     */
    List<CategoryDto> getAllCategoriesActiveDto();
}
