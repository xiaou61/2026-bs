CREATE DATABASE IF NOT EXISTS chat_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE chat_system;

CREATE TABLE `user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL UNIQUE,
  `password` VARCHAR(100) NOT NULL,
  `nickname` VARCHAR(50),
  `avatar` VARCHAR(255),
  `email` VARCHAR(100),
  `phone` VARCHAR(20),
  `status` TINYINT DEFAULT 1,
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` TINYINT DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX idx_username (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `friend_group` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `group_name` VARCHAR(50) NOT NULL,
  `sort_order` INT DEFAULT 0,
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` TINYINT DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX idx_user_id (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `friend` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `friend_id` BIGINT NOT NULL,
  `group_id` BIGINT,
  `remark` VARCHAR(50),
  `status` TINYINT DEFAULT 0,
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` TINYINT DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX idx_user_id (`user_id`),
  INDEX idx_friend_id (`friend_id`),
  UNIQUE KEY uk_user_friend (`user_id`, `friend_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `chat_message` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `from_user_id` BIGINT NOT NULL,
  `to_user_id` BIGINT NOT NULL,
  `content` TEXT NOT NULL,
  `msg_type` VARCHAR(20) DEFAULT 'TEXT',
  `is_read` TINYINT DEFAULT 0,
  `read_time` DATETIME,
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` TINYINT DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX idx_from_user (`from_user_id`),
  INDEX idx_to_user (`to_user_id`),
  INDEX idx_create_time (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `notification` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `from_user_id` BIGINT,
  `to_user_id` BIGINT NOT NULL,
  `notify_type` VARCHAR(20) NOT NULL,
  `title` VARCHAR(100),
  `content` TEXT NOT NULL,
  `link_id` BIGINT,
  `is_read` TINYINT DEFAULT 0,
  `read_time` DATETIME,
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` TINYINT DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX idx_to_user (`to_user_id`),
  INDEX idx_is_read (`is_read`),
  INDEX idx_create_time (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `user` (`username`, `password`, `nickname`, `avatar`, `email`) VALUES
('admin', '123456', '管理员', 'https://ui-avatars.com/api/?name=Admin', 'admin@example.com'),
('zhangsan', '123456', '张三', 'https://ui-avatars.com/api/?name=Zhang', 'zhangsan@example.com'),
('lisi', '123456', '李四', 'https://ui-avatars.com/api/?name=Li', 'lisi@example.com'),
('wangwu', '123456', '王五', 'https://ui-avatars.com/api/?name=Wang', 'wangwu@example.com');

INSERT INTO `friend_group` (`user_id`, `group_name`, `sort_order`) VALUES
(1, '默认分组', 0),
(2, '默认分组', 0),
(3, '默认分组', 0),
(4, '默认分组', 0);

INSERT INTO `friend` (`user_id`, `friend_id`, `group_id`, `status`) VALUES
(2, 3, 2, 1),
(3, 2, 3, 1),
(2, 4, 2, 1),
(4, 2, 4, 1);

