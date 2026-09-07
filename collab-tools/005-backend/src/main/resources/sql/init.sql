CREATE DATABASE IF NOT EXISTS survey_db CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE survey_db;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `username` VARCHAR(50) NOT NULL UNIQUE,
  `password` VARCHAR(255) NOT NULL,
  `nickname` VARCHAR(100),
  `email` VARCHAR(100),
  `avatar` VARCHAR(255),
  `role` TINYINT DEFAULT 1 COMMENT '0-管理员 1-普通用户',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `survey`;
CREATE TABLE `survey` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `title` VARCHAR(200) NOT NULL,
  `description` TEXT,
  `creator_id` BIGINT NOT NULL,
  `status` TINYINT DEFAULT 0 COMMENT '0-草稿 1-已发布 2-已结束',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX idx_creator_id (`creator_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `survey_id` BIGINT NOT NULL,
  `type` VARCHAR(20) NOT NULL COMMENT 'radio/checkbox/input/rate/select',
  `title` VARCHAR(500) NOT NULL,
  `options` JSON COMMENT '选项内容',
  `required` TINYINT DEFAULT 1 COMMENT '1-必答 0-非必答',
  `order_no` INT DEFAULT 0,
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  INDEX idx_survey_id (`survey_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `answer`;
CREATE TABLE `answer` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `survey_id` BIGINT NOT NULL,
  `user_ip` VARCHAR(50),
  `submit_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  INDEX idx_survey_id (`survey_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `answer_detail`;
CREATE TABLE `answer_detail` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `answer_id` BIGINT NOT NULL,
  `question_id` BIGINT NOT NULL,
  `value` TEXT,
  INDEX idx_answer_id (`answer_id`),
  INDEX idx_question_id (`question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `user` (`username`, `password`, `nickname`, `email`, `role`) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '管理员', 'admin@survey.com', 0),
('user1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '张三', 'user1@survey.com', 1),
('user2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '李四', 'user2@survey.com', 1);

