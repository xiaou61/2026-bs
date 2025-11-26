-- Create Database
CREATE DATABASE IF NOT EXISTS `024_pet_adoption` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `024_pet_adoption`;

-- 1. System User Table
CREATE TABLE IF NOT EXISTS `sys_user` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'Primary Key',
    `username` VARCHAR(50) NOT NULL UNIQUE COMMENT 'Username',
    `password` VARCHAR(100) NOT NULL COMMENT 'Password (Encrypted)',
    `nickname` VARCHAR(50) COMMENT 'Nickname',
    `avatar` VARCHAR(255) COMMENT 'Avatar URL',
    `phone` VARCHAR(20) COMMENT 'Phone Number',
    `role` VARCHAR(20) NOT NULL DEFAULT 'USER' COMMENT 'Role: USER, ADMIN',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Create Time',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update Time'
) COMMENT 'System User';

-- 2. Pet Information Table
CREATE TABLE IF NOT EXISTS `pet_info` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'Primary Key',
    `name` VARCHAR(50) NOT NULL COMMENT 'Pet Name',
    `type` VARCHAR(20) NOT NULL COMMENT 'Type: Cat, Dog, Other',
    `breed` VARCHAR(50) COMMENT 'Breed',
    `age` VARCHAR(20) COMMENT 'Age',
    `gender` VARCHAR(10) COMMENT 'Gender: Male, Female',
    `status` VARCHAR(20) DEFAULT 'AVAILABLE' COMMENT 'Status: AVAILABLE, PENDING, ADOPTED',
    `description` TEXT COMMENT 'Description',
    `image_url` VARCHAR(255) COMMENT 'Main Image URL',
    `health_status` VARCHAR(100) COMMENT 'Health Status',
    `vaccine_status` VARCHAR(100) COMMENT 'Vaccine Status',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Create Time',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update Time'
) COMMENT 'Pet Information';

-- 3. Adoption Application Table
CREATE TABLE IF NOT EXISTS `adoption_application` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'Primary Key',
    `user_id` BIGINT NOT NULL COMMENT 'Applicant User ID',
    `pet_id` BIGINT NOT NULL COMMENT 'Target Pet ID',
    `status` VARCHAR(20) DEFAULT 'PENDING' COMMENT 'Status: PENDING, APPROVED, REJECTED',
    `reason` TEXT COMMENT 'Reason for Adoption',
    `experience` TEXT COMMENT 'Pet Raising Experience',
    `housing_condition` TEXT COMMENT 'Housing Condition',
    `financial_status` TEXT COMMENT 'Financial Status',
    `contact_info` VARCHAR(100) COMMENT 'Contact Info',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Create Time',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update Time',
    FOREIGN KEY (`user_id`) REFERENCES `sys_user`(`id`),
    FOREIGN KEY (`pet_id`) REFERENCES `pet_info`(`id`)
) COMMENT 'Adoption Application';

-- 4. Rescue Post Table
CREATE TABLE IF NOT EXISTS `rescue_post` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'Primary Key',
    `user_id` BIGINT NOT NULL COMMENT 'Poster User ID',
    `title` VARCHAR(100) NOT NULL COMMENT 'Title',
    `content` TEXT NOT NULL COMMENT 'Content',
    `location` VARCHAR(255) COMMENT 'Location',
    `image_url` VARCHAR(255) COMMENT 'Image URL',
    `status` VARCHAR(20) DEFAULT 'OPEN' COMMENT 'Status: OPEN, RESOLVED',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Create Time',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update Time',
    FOREIGN KEY (`user_id`) REFERENCES `sys_user`(`id`)
) COMMENT 'Rescue Post';

-- 5. Community Post Table
CREATE TABLE IF NOT EXISTS `community_post` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'Primary Key',
    `user_id` BIGINT NOT NULL COMMENT 'Author User ID',
    `title` VARCHAR(100) NOT NULL COMMENT 'Title',
    `content` TEXT NOT NULL COMMENT 'Content',
    `image_url` VARCHAR(255) COMMENT 'Image URL',
    `view_count` INT DEFAULT 0 COMMENT 'View Count',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Create Time',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update Time',
    FOREIGN KEY (`user_id`) REFERENCES `sys_user`(`id`)
) COMMENT 'Community Post';

-- 6. Comment Table
CREATE TABLE IF NOT EXISTS `comment` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'Primary Key',
    `post_id` BIGINT NOT NULL COMMENT 'Post ID',
    `user_id` BIGINT NOT NULL COMMENT 'Commenter User ID',
    `content` TEXT NOT NULL COMMENT 'Content',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Create Time',
    FOREIGN KEY (`post_id`) REFERENCES `community_post`(`id`),
    FOREIGN KEY (`user_id`) REFERENCES `sys_user`(`id`)
) COMMENT 'Comment';

-- 7. Banner Table
CREATE TABLE IF NOT EXISTS `banner` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'Primary Key',
    `title` VARCHAR(100) COMMENT 'Title',
    `image_url` VARCHAR(255) NOT NULL COMMENT 'Image URL',
    `link_url` VARCHAR(255) COMMENT 'Link URL',
    `sort_order` INT DEFAULT 0 COMMENT 'Sort Order',
    `is_active` BOOLEAN DEFAULT TRUE COMMENT 'Is Active',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Create Time'
) COMMENT 'Banner';

-- Initial Admin User (password: admin123)
INSERT INTO `sys_user` (`username`, `password`, `nickname`, `role`) VALUES ('admin', '21232f297a57a5a743894a0e4a801fc3', 'Administrator', 'ADMIN');
