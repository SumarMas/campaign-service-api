-- ============================================================
-- DATABASE: campaign_service
-- Description: Schema, audit tables and triggers for campaign-service
-- ============================================================

CREATE DATABASE IF NOT EXISTS campaign_service;
USE campaign_service;

-- ============================================================
-- 1. TABLE: categories
-- ============================================================
CREATE TABLE IF NOT EXISTS categories (
    category_id BINARY(16) NOT NULL,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255),
    enabled BOOLEAN DEFAULT TRUE,
    created_datetime DATETIME DEFAULT NOW(),
    created_user BINARY(16),
    last_updated_datetime DATETIME DEFAULT NOW(),
    last_updated_user BINARY(16),
    PRIMARY KEY (category_id)
);

CREATE TABLE IF NOT EXISTS categories_audit LIKE categories;
ALTER TABLE categories_audit
    ADD COLUMN version INT NOT NULL,
    DROP PRIMARY KEY,
    ADD PRIMARY KEY (category_id, version);

DELIMITER $$
CREATE TRIGGER after_insert_categories
AFTER INSERT ON categories
FOR EACH ROW
BEGIN
    INSERT INTO categories_audit SELECT *, 1 FROM categories WHERE category_id = NEW.category_id;
END$$

CREATE TRIGGER before_update_categories
BEFORE UPDATE ON categories
FOR EACH ROW
BEGIN
    DECLARE last_version INT;
    SET NEW.last_updated_datetime = NOW();
    SET NEW.created_user = OLD.created_user;
    SELECT COALESCE(MAX(version), 0) INTO last_version FROM categories_audit WHERE category_id = NEW.category_id;
    INSERT INTO categories_audit SELECT *, last_version + 1 FROM categories WHERE category_id = NEW.category_id;
END$$
DELIMITER ;

-- ============================================================
-- 2. TABLE: campaigns
-- ============================================================
CREATE TABLE IF NOT EXISTS campaigns (
    campaign_id BINARY(16) NOT NULL,
    organization_id BINARY(16) NOT NULL,
    title VARCHAR(255) NOT NULL,
    goal_amount DECIMAL(12,2) NOT NULL,
    current_amount DECIMAL(12,2) DEFAULT 0.00,
    description TEXT,
    end_datetime DATETIME NOT NULL,
    state ENUM('ACTIVE','CLOSED') DEFAULT 'ACTIVE',
    enabled BOOLEAN DEFAULT TRUE,
    created_datetime DATETIME DEFAULT NOW(),
    created_user BINARY(16),
    last_updated_datetime DATETIME DEFAULT NOW(),
    last_updated_user BINARY(16),
    PRIMARY KEY (campaign_id)
);

CREATE TABLE IF NOT EXISTS campaigns_audit LIKE campaigns;
ALTER TABLE campaigns_audit
    ADD COLUMN version INT NOT NULL,
    DROP PRIMARY KEY,
    ADD PRIMARY KEY (campaign_id, version);

DELIMITER $$
CREATE TRIGGER after_insert_campaigns
AFTER INSERT ON campaigns
FOR EACH ROW
BEGIN
    INSERT INTO campaigns_audit SELECT *, 1 FROM campaigns WHERE campaign_id = NEW.campaign_id;
END$$

CREATE TRIGGER before_update_campaigns
BEFORE UPDATE ON campaigns
FOR EACH ROW
BEGIN
    DECLARE last_version INT;
    SET NEW.last_updated_datetime = NOW();
    SET NEW.created_user = OLD.created_user;
    SELECT COALESCE(MAX(version), 0) INTO last_version FROM campaigns_audit WHERE campaign_id = NEW.campaign_id;
    INSERT INTO campaigns_audit SELECT *, last_version + 1 FROM campaigns WHERE campaign_id = NEW.campaign_id;
END$$
DELIMITER ;

-- ============================================================
-- 3. TABLE: campaign_category (N:M)
-- ============================================================
CREATE TABLE IF NOT EXISTS campaign_category (
    campaign_id BINARY(16) NOT NULL,
    category_id BINARY(16) NOT NULL,
    enabled BOOLEAN DEFAULT TRUE,
    created_datetime DATETIME DEFAULT NOW(),
    created_user BINARY(16),
    last_updated_datetime DATETIME DEFAULT NOW(),
    last_updated_user BINARY(16),
    PRIMARY KEY (campaign_id, category_id)
);

CREATE TABLE IF NOT EXISTS campaign_category_audit LIKE campaign_category;
ALTER TABLE campaign_category_audit
    ADD COLUMN version INT NOT NULL,
    DROP PRIMARY KEY,
    ADD PRIMARY KEY (campaign_id, category_id, version);

DELIMITER $$
CREATE TRIGGER after_insert_campaign_category
AFTER INSERT ON campaign_category
FOR EACH ROW
BEGIN
    INSERT INTO campaign_category_audit SELECT *, 1 FROM campaign_category 
    WHERE campaign_id = NEW.campaign_id AND category_id = NEW.category_id;
END$$

CREATE TRIGGER before_update_campaign_category
BEFORE UPDATE ON campaign_category
FOR EACH ROW
BEGIN
    DECLARE last_version INT;
    SET NEW.last_updated_datetime = NOW();
    SET NEW.created_user = OLD.created_user;
    SELECT COALESCE(MAX(version), 0) INTO last_version FROM campaign_category_audit 
    WHERE campaign_id = NEW.campaign_id AND category_id = NEW.category_id;
    INSERT INTO campaign_category_audit SELECT *, last_version + 1 FROM campaign_category 
    WHERE campaign_id = NEW.campaign_id AND category_id = NEW.category_id;
END$$
DELIMITER ;

-- ============================================================
-- 4. TABLE: campaign_images
-- ============================================================
CREATE TABLE IF NOT EXISTS campaign_images (
    campaign_image_id BINARY(16) NOT NULL,
    campaign_id BINARY(16) NOT NULL,
    file_id BINARY(16) NOT NULL,
    order_index INT DEFAULT 0,
    enabled BOOLEAN DEFAULT TRUE,
    created_datetime DATETIME DEFAULT NOW(),
    created_user BINARY(16),
    last_updated_datetime DATETIME DEFAULT NOW(),
    last_updated_user BINARY(16),
    PRIMARY KEY (campaign_image_id)
);

CREATE TABLE IF NOT EXISTS campaign_images_audit LIKE campaign_images;
ALTER TABLE campaign_images_audit
    ADD COLUMN version INT NOT NULL,
    DROP PRIMARY KEY,
    ADD PRIMARY KEY (campaign_image_id, version);

DELIMITER $$
CREATE TRIGGER after_insert_campaign_images
AFTER INSERT ON campaign_images
FOR EACH ROW
BEGIN
    INSERT INTO campaign_images_audit SELECT *, 1 FROM campaign_images WHERE campaign_image_id = NEW.campaign_image_id;
END$$

CREATE TRIGGER before_update_campaign_images
BEFORE UPDATE ON campaign_images
FOR EACH ROW
BEGIN
    DECLARE last_version INT;
    SET NEW.last_updated_datetime = NOW();
    SET NEW.created_user = OLD.created_user;
    SELECT COALESCE(MAX(version), 0) INTO last_version FROM campaign_images_audit WHERE campaign_image_id = NEW.campaign_image_id;
    INSERT INTO campaign_images_audit SELECT *, last_version + 1 FROM campaign_images WHERE campaign_image_id = NEW.campaign_image_id;
END$$
DELIMITER ;

-- ============================================================
-- 5. TABLE: campaign_comments
-- ============================================================
CREATE TABLE IF NOT EXISTS campaign_comments (
    campaign_comment_id BINARY(16) NOT NULL,
    campaign_id BINARY(16) NOT NULL,
    user_id BINARY(16) NOT NULL,
    content TEXT NOT NULL,
    enabled BOOLEAN DEFAULT TRUE,
    created_datetime DATETIME DEFAULT NOW(),
    created_user BINARY(16),
    last_updated_datetime DATETIME DEFAULT NOW(),
    last_updated_user BINARY(16),
    PRIMARY KEY (campaign_comment_id)
);

CREATE TABLE IF NOT EXISTS campaign_comments_audit LIKE campaign_comments;
ALTER TABLE campaign_comments_audit
    ADD COLUMN version INT NOT NULL,
    DROP PRIMARY KEY,
    ADD PRIMARY KEY (campaign_comment_id, version);

DELIMITER $$
CREATE TRIGGER after_insert_campaign_comments
AFTER INSERT ON campaign_comments
FOR EACH ROW
BEGIN
    INSERT INTO campaign_comments_audit SELECT *, 1 FROM campaign_comments WHERE campaign_comment_id = NEW.campaign_comment_id;
END$$

CREATE TRIGGER before_update_campaign_comments
BEFORE UPDATE ON campaign_comments
FOR EACH ROW
BEGIN
    DECLARE last_version INT;
    SET NEW.last_updated_datetime = NOW();
    SET NEW.created_user = OLD.created_user;
    SELECT COALESCE(MAX(version), 0) INTO last_version FROM campaign_comments_audit WHERE campaign_comment_id = NEW.campaign_comment_id;
    INSERT INTO campaign_comments_audit SELECT *, last_version + 1 FROM campaign_comments WHERE campaign_comment_id = NEW.campaign_comment_id;
END$$
DELIMITER ;

-- ============================================================
-- 6. TABLE: campaign_messages
-- ============================================================
CREATE TABLE IF NOT EXISTS campaign_messages (
    campaign_message_id BINARY(16) NOT NULL,
    campaign_id BINARY(16) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    file_id BINARY(16),
    enabled BOOLEAN DEFAULT TRUE,
    created_datetime DATETIME DEFAULT NOW(),
    created_user BINARY(16),
    last_updated_datetime DATETIME DEFAULT NOW(),
    last_updated_user BINARY(16),
    PRIMARY KEY (campaign_message_id)
);

CREATE TABLE IF NOT EXISTS campaign_messages_audit LIKE campaign_messages;
ALTER TABLE campaign_messages_audit
    ADD COLUMN version INT NOT NULL,
    DROP PRIMARY KEY,
    ADD PRIMARY KEY (campaign_message_id, version);

DELIMITER $$
CREATE TRIGGER after_insert_campaign_messages
AFTER INSERT ON campaign_messages
FOR EACH ROW
BEGIN
    INSERT INTO campaign_messages_audit SELECT *, 1 FROM campaign_messages WHERE campaign_message_id = NEW.campaign_message_id;
END$$

CREATE TRIGGER before_update_campaign_messages
BEFORE UPDATE ON campaign_messages
FOR EACH ROW
BEGIN
    DECLARE last_version INT;
    SET NEW.last_updated_datetime = NOW();
    SET NEW.created_user = OLD.created_user;
    SELECT COALESCE(MAX(version), 0) INTO last_version FROM campaign_messages_audit WHERE campaign_message_id = NEW.campaign_message_id;
    INSERT INTO campaign_messages_audit SELECT *, last_version + 1 FROM campaign_messages WHERE campaign_message_id = NEW.campaign_message_id;
END$$
DELIMITER ;

-- ============================================================
-- 7. TABLE: campaign_tags
-- ============================================================
CREATE TABLE IF NOT EXISTS campaign_tags (
    campaign_id BINARY(16) NOT NULL,
    tag_name VARCHAR(50) NOT NULL,
    enabled BOOLEAN DEFAULT TRUE,
    created_datetime DATETIME DEFAULT NOW(),
    created_user BINARY(16),
    last_updated_datetime DATETIME DEFAULT NOW(),
    last_updated_user BINARY(16),
    PRIMARY KEY (campaign_id, tag_name)
);

CREATE TABLE IF NOT EXISTS campaign_tags_audit LIKE campaign_tags;
ALTER TABLE campaign_tags_audit
    ADD COLUMN version INT NOT NULL,
    DROP PRIMARY KEY,
    ADD PRIMARY KEY (campaign_id, tag_name, version);

DELIMITER $$
CREATE TRIGGER after_insert_campaign_tags
AFTER INSERT ON campaign_tags
FOR EACH ROW
BEGIN
    INSERT INTO campaign_tags_audit SELECT *, 1 FROM campaign_tags WHERE campaign_id = NEW.campaign_id AND tag_name = NEW.tag_name;
END$$

CREATE TRIGGER before_update_campaign_tags
BEFORE UPDATE ON campaign_tags
FOR EACH ROW
BEGIN
    DECLARE last_version INT;
    SET NEW.last_updated_datetime = NOW();
    SET NEW.created_user = OLD.created_user;
    SELECT COALESCE(MAX(version), 0) INTO last_version FROM campaign_tags_audit 
    WHERE campaign_id = NEW.campaign_id AND tag_name = NEW.tag_name;
    INSERT INTO campaign_tags_audit SELECT *, last_version + 1 FROM campaign_tags 
    WHERE campaign_id = NEW.campaign_id AND tag_name = NEW.tag_name;
END$$
DELIMITER ;

ALTER TABLE campaign_messages MODIFY COLUMN description LONGTEXT NOT NULL;
ALTER TABLE campaign_messages_audit MODIFY COLUMN description LONGTEXT NOT NULL;
