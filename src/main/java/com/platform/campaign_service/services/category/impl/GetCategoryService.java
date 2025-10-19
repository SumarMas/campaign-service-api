package com.platform.campaign_service.services.category.impl;

import com.platform.campaign_service.dtos.categories.CategoryDto;
import com.platform.campaign_service.entities.CategoryEntity;
import com.platform.campaign_service.map.impl.MapperCategory;
import com.platform.campaign_service.repositories.CategoryRepository;
import com.platform.campaign_service.services.category.IGetCategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
/**
 * Implementation of the IGetCategoryService interface for retrieving categories.
 */
@Service
@RequiredArgsConstructor
public class GetCategoryService implements IGetCategoryService {
    /** Logger instance for logging information and errors. */
    private static final Logger LOG = LoggerFactory.getLogger(GetCategoryService.class);
    /** Repository for accessing category data. */
    private final CategoryRepository categoryRepository;
    /** Mapper for converting between CategoryEntity and CategoryDto. */
    private final MapperCategory mapperCategory;

    /**
     * Retrieves all active categories.
     *
     * @return a list of active CategoryEntity objects
     */
    @Override
    public List<CategoryEntity> getAllCategoriesActive() {
        List<CategoryEntity> categories = new ArrayList<>();
        try {
            LOG.trace("In GetCategoryService.getAllCategoriesActive()");
            categories = categoryRepository.findAllByEnabledIsTrueOrderByNameAsc();
        } catch (DataAccessException ex) {
            LOG.error("Error retrieving active categories: {}", ex.getMessage());
        }
        return  categories;
    }

    /**
     * Retrieves all active categories as DTOs.
     *
     * @return a list of active CategoryDto objects
     */
    @Override
    public List<CategoryDto> getAllCategoriesActiveDto() {
        List<CategoryDto> categories = new ArrayList<>();
        try {
            LOG.trace("In GetCategoryService.getAllCategoriesActiveDto()");
            categories.addAll(getAllCategoriesActive().stream().map(this::mapToDto).toList());
        } catch (DataAccessException ex) {
            LOG.error("Error retrieving active category DTOs: {}", ex.getMessage());
        }
        return categories;
    }

    private CategoryDto mapToDto(CategoryEntity categoryEntity) {
        return mapperCategory.mapToDto(categoryEntity);
    }
}
