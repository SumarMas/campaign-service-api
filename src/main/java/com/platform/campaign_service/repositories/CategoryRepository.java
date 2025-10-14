package com.platform.campaign_service.repositories;

import com.platform.campaign_service.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for accessing category data.
 */
@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {
    /**
     * Finds all categories that are enabled, ordered by name in ascending order.
     *
     * @return a list of enabled CategoryEntity objects ordered by name
     */
    List<CategoryEntity> findAllByEnabledIsTrueOrderByNameAsc();
}
