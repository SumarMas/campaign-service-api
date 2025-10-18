package com.platform.campaign_service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
 * Entity representing a category for campaigns.
 */
@SuppressWarnings("checkstyle:MagicNumber")
@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class CategoryEntity extends AuditEntity {
    /** Maximum length for the category name. */
    private static final int LENGTH_NAME = 100;
    /** Maximum length for the category description. */
    private static final int LENGTH_DESCRIPTION = 255;
    /**
     * Unique identifier for the category.
     */
    @Id
    @Column(name = "category_id", columnDefinition = "BINARY(16)")
    private UUID categoryId;
    /**
     * Name of the category.
     */
    @Column(nullable = false, unique = true, length = LENGTH_NAME, name = "name")
    private String name;
    /**
     * Description of the category.
     */
    @Column(length = LENGTH_DESCRIPTION, name = "description")
    private String description;
}
