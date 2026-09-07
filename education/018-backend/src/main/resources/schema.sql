CREATE DATABASE IF NOT EXISTS campus_recruitment DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE campus_recruitment;

CREATE TABLE IF NOT EXISTS `user` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `username` VARCHAR(50) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    `real_name` VARCHAR(50),
    `email` VARCHAR(100),
    `phone` VARCHAR(20),
    `avatar` VARCHAR(255),
    `role` VARCHAR(20) NOT NULL DEFAULT 'student',
    `status` INT NOT NULL DEFAULT 1,
    `company_id` BIGINT,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_username (`username`),
    INDEX idx_role (`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `company` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    `logo` VARCHAR(255),
    `industry` VARCHAR(50),
    `scale` VARCHAR(50),
    `location` VARCHAR(100),
    `description` TEXT,
    `website` VARCHAR(255),
    `status` INT NOT NULL DEFAULT 0,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_status (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `job` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `company_id` BIGINT NOT NULL,
    `title` VARCHAR(100) NOT NULL,
    `job_type` VARCHAR(20) NOT NULL,
    `category` VARCHAR(50),
    `location` VARCHAR(100),
    `salary_min` INT,
    `salary_max` INT,
    `requirement` TEXT,
    `description` TEXT,
    `major` VARCHAR(100),
    `skills` VARCHAR(255),
    `education` VARCHAR(20),
    `headcount` INT DEFAULT 1,
    `status` INT NOT NULL DEFAULT 1,
    `views` INT DEFAULT 0,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_company_id (`company_id`),
    INDEX idx_status (`status`),
    INDEX idx_job_type (`job_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `resume` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL,
    `name` VARCHAR(50) NOT NULL,
    `gender` VARCHAR(10),
    `birth_date` DATE,
    `phone` VARCHAR(20),
    `email` VARCHAR(100),
    `university` VARCHAR(100),
    `major` VARCHAR(50),
    `education` VARCHAR(20),
    `graduation_date` DATE,
    `skills` VARCHAR(255),
    `internship_experience` TEXT,
    `project_experience` TEXT,
    `self_introduction` TEXT,
    `attachment` VARCHAR(255),
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_user_id (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `application` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL,
    `job_id` BIGINT NOT NULL,
    `resume_id` BIGINT NOT NULL,
    `status` VARCHAR(20) NOT NULL DEFAULT 'pending',
    `remark` VARCHAR(255),
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_user_id (`user_id`),
    INDEX idx_job_id (`job_id`),
    INDEX idx_status (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `interview` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `application_id` BIGINT NOT NULL,
    `user_id` BIGINT NOT NULL,
    `job_id` BIGINT NOT NULL,
    `interview_type` VARCHAR(20),
    `interview_time` DATETIME,
    `location` VARCHAR(255),
    `interviewer` VARCHAR(50),
    `status` VARCHAR(20) NOT NULL DEFAULT 'scheduled',
    `feedback` TEXT,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_user_id (`user_id`),
    INDEX idx_application_id (`application_id`),
    INDEX idx_status (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `experience` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL,
    `company_name` VARCHAR(100),
    `job_title` VARCHAR(100),
    `type` VARCHAR(20) NOT NULL,
    `title` VARCHAR(200) NOT NULL,
    `content` TEXT,
    `tags` VARCHAR(255),
    `likes` INT DEFAULT 0,
    `views` INT DEFAULT 0,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_user_id (`user_id`),
    INDEX idx_type (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `referral` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL,
    `company_name` VARCHAR(100) NOT NULL,
    `job_title` VARCHAR(100) NOT NULL,
    `description` TEXT,
    `requirement` TEXT,
    `referral_code` VARCHAR(50),
    `contact_way` VARCHAR(255),
    `status` INT NOT NULL DEFAULT 1,
    `views` INT DEFAULT 0,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_user_id (`user_id`),
    INDEX idx_status (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `user` (`username`, `password`, `real_name`, `role`, `status`) VALUES
('admin', 'e10adc3949ba59abbe56e057f20f883e', '管理员', 'admin', 1),
('student1', 'e10adc3949ba59abbe56e057f20f883e', '张三', 'student', 1),
('company1', 'e10adc3949ba59abbe56e057f20f883e', '李经理', 'company', 1);

INSERT INTO `company` (`name`, `logo`, `industry`, `scale`, `location`, `description`, `status`) VALUES
('字节跳动', '', '互联网', '10000人以上', '北京', '字节跳动是一家技术公司', 1),
('腾讯', '', '互联网', '10000人以上', '深圳', '腾讯是一家互联网公司', 1);

UPDATE `user` SET `company_id` = 1 WHERE `username` = 'company1';

