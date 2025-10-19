package com.platform.campaign_service.controllers;

import com.platform.campaign_service.dtos.categories.CategoryDto;
import com.platform.campaign_service.services.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    /** Logger for logging information and errors. */
    private static final Logger LOG = LoggerFactory.getLogger(CategoryController.class);
    /** Service for handling category-related operations. */
    private final ICategoryService categoryService;

    /**
     * Handles requests to fetch all active categories.
     *
     * @return a ResponseEntity containing a list of CategoryDto objects
     */
    @GetMapping("/all")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        LOG.info("Fetching all categories");
        List<CategoryDto> categories = categoryService.getAllCategoriesActiveDto();
        return ResponseEntity.ok(categories);
    }

}
