USE campaign_service;

-- Admin user (default)
SET @admin_user = UUID_TO_BIN('11111111-1111-1111-1111-111111111111');

-- ============================================================
-- Insert predefined categories (if not exists)
-- ============================================================

-- 1. Education
INSERT INTO categories (category_id, name, description, enabled, created_user, created_datetime, last_updated_user, last_updated_datetime)
SELECT UUID_TO_BIN(UUID()), 'Education', 'Campaigns that promote access to education, scholarships, and school supplies.',
       TRUE, @admin_user, NOW(), @admin_user, NOW()
    WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Education');

-- 2. Health
INSERT INTO categories (category_id, name, description, enabled, created_user, created_datetime, last_updated_user, last_updated_datetime)
SELECT UUID_TO_BIN(UUID()), 'Health', 'Campaigns focused on healthcare, treatments, and medical support.',
       TRUE, @admin_user, NOW(), @admin_user, NOW()
    WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Health');

-- 3. Environment
INSERT INTO categories (category_id, name, description, enabled, created_user, created_datetime, last_updated_user, last_updated_datetime)
SELECT UUID_TO_BIN(UUID()), 'Environment', 'Initiatives for environmental protection, recycling, and sustainability.',
       TRUE, @admin_user, NOW(), @admin_user, NOW()
    WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Environment');

-- 4. Animal Welfare
INSERT INTO categories (category_id, name, description, enabled, created_user, created_datetime, last_updated_user, last_updated_datetime)
SELECT UUID_TO_BIN(UUID()), 'Animal Welfare', 'Support for animal shelters, rescues, and care programs.',
       TRUE, @admin_user, NOW(), @admin_user, NOW()
    WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Animal Welfare');

-- 5. Community Development
INSERT INTO categories (category_id, name, description, enabled, created_user, created_datetime, last_updated_user, last_updated_datetime)
SELECT UUID_TO_BIN(UUID()), 'Community Development', 'Projects aimed at improving living conditions and social inclusion.',
       TRUE, @admin_user, NOW(), @admin_user, NOW()
    WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Community Development');
