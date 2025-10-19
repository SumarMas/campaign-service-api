package com.platform.campaign_service.services.category.impl;

import com.platform.campaign_service.dtos.categories.CategoryDto;
import com.platform.campaign_service.entities.CategoryEntity;
import com.platform.campaign_service.services.category.ICategoryService;
import com.platform.campaign_service.services.category.IGetCategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for managing categories.
 */
@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    /** Logger for logging information and errors. */
    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(CategoryService.class);
    /** Service for retrieving category data. */
    private final IGetCategoryService getCategoryService;
    /**
     * Retrieves all active categories.
     *
     * @return a list of active CategoryEntity objects
     */
    @Override
    public List<CategoryEntity> getAllCategoriesActive() {
        LOG.trace("In getAllCategoriesActive()");
        return getCategoryService.getAllCategoriesActive();
    }

    /**
     * Retrieves all active categories as DTOs.
     *
     * @return a list of active CategoryDto objects
     */
    @Override
    public List<CategoryDto> getAllCategoriesActiveDto() {
        LOG.trace("In getAllCategoriesActiveDto()");
        return getCategoryService.getAllCategoriesActiveDto();
    }
}
