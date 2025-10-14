package com.platform.campaign_service.services.category;

import com.platform.campaign_service.entities.CategoryEntity;

import java.util.List;
/**
 * Service interface for retrieving categories.
 */
public interface IGetCategoryService {
    /**
     * Retrieves all active categories.
     *
     * @return a list of active CategoryEntity objects
     */
    List<CategoryEntity> getAllCategoriesActive();
}
