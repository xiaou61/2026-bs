CREATE DATABASE IF NOT EXISTS ticket_booking DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE ticket_booking;

CREATE TABLE `user` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `username` VARCHAR(50) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    `phone` VARCHAR(20) NOT NULL,
    `email` VARCHAR(100),
    `real_name` VARCHAR(50),
    `id_card` VARCHAR(20),
    `role` VARCHAR(20) NOT NULL DEFAULT 'USER',
    `balance` DECIMAL(10, 2) DEFAULT 0.00,
    `status` VARCHAR(20) DEFAULT 'ACTIVE',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_username (`username`),
    INDEX idx_phone (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `stadium` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    `location` VARCHAR(200) NOT NULL,
    `city` VARCHAR(50) NOT NULL,
    `capacity` INT NOT NULL,
    `description` TEXT,
    `facilities` VARCHAR(500),
    `image_url` VARCHAR(500),
    `status` VARCHAR(20) DEFAULT 'ACTIVE',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_city (`city`),
    INDEX idx_name (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `team` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    `logo_url` VARCHAR(500),
    `country` VARCHAR(50),
    `city` VARCHAR(50),
    `founded_year` INT,
    `description` TEXT,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_name (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `match` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `title` VARCHAR(200) NOT NULL,
    `home_team_id` BIGINT NOT NULL,
    `away_team_id` BIGINT NOT NULL,
    `stadium_id` BIGINT NOT NULL,
    `match_date` DATETIME NOT NULL,
    `match_type` VARCHAR(50),
    `league` VARCHAR(100),
    `season` VARCHAR(50),
    `description` TEXT,
    `poster_url` VARCHAR(500),
    `status` VARCHAR(20) DEFAULT 'UPCOMING',
    `home_score` INT,
    `away_score` INT,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`home_team_id`) REFERENCES `team`(`id`),
    FOREIGN KEY (`away_team_id`) REFERENCES `team`(`id`),
    FOREIGN KEY (`stadium_id`) REFERENCES `stadium`(`id`),
    INDEX idx_match_date (`match_date`),
    INDEX idx_status (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `seat_category` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `stadium_id` BIGINT NOT NULL,
    `name` VARCHAR(50) NOT NULL,
    `description` VARCHAR(200),
    `total_seats` INT NOT NULL,
    `row_count` INT NOT NULL,
    `column_count` INT NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`stadium_id`) REFERENCES `stadium`(`id`),
    INDEX idx_stadium (`stadium_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `match_pricing` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `match_id` BIGINT NOT NULL,
    `category_id` BIGINT NOT NULL,
    `price` DECIMAL(10, 2) NOT NULL,
    `available_seats` INT NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`match_id`) REFERENCES `match`(`id`),
    FOREIGN KEY (`category_id`) REFERENCES `seat_category`(`id`),
    UNIQUE KEY uk_match_category (`match_id`, `category_id`),
    INDEX idx_match (`match_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `seat` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `category_id` BIGINT NOT NULL,
    `row_number` VARCHAR(10) NOT NULL,
    `seat_number` VARCHAR(10) NOT NULL,
    `status` VARCHAR(20) DEFAULT 'AVAILABLE',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`category_id`) REFERENCES `seat_category`(`id`),
    UNIQUE KEY uk_seat (`category_id`, `row_number`, `seat_number`),
    INDEX idx_category (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `ticket_order` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `order_no` VARCHAR(50) NOT NULL UNIQUE,
    `user_id` BIGINT NOT NULL,
    `match_id` BIGINT NOT NULL,
    `total_amount` DECIMAL(10, 2) NOT NULL,
    `payment_amount` DECIMAL(10, 2) NOT NULL,
    `discount_amount` DECIMAL(10, 2) DEFAULT 0.00,
    `status` VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    `payment_method` VARCHAR(20),
    `payment_time` TIMESTAMP NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
    FOREIGN KEY (`match_id`) REFERENCES `match`(`id`),
    INDEX idx_user (`user_id`),
    INDEX idx_order_no (`order_no`),
    INDEX idx_status (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `ticket` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `ticket_no` VARCHAR(50) NOT NULL UNIQUE,
    `order_id` BIGINT NOT NULL,
    `match_id` BIGINT NOT NULL,
    `seat_id` BIGINT NOT NULL,
    `category_id` BIGINT NOT NULL,
    `price` DECIMAL(10, 2) NOT NULL,
    `status` VARCHAR(20) NOT NULL DEFAULT 'VALID',
    `qr_code` VARCHAR(500),
    `check_in_time` TIMESTAMP NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`order_id`) REFERENCES `ticket_order`(`id`),
    FOREIGN KEY (`match_id`) REFERENCES `match`(`id`),
    FOREIGN KEY (`seat_id`) REFERENCES `seat`(`id`),
    FOREIGN KEY (`category_id`) REFERENCES `seat_category`(`id`),
    INDEX idx_order (`order_id`),
    INDEX idx_ticket_no (`ticket_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `user_favorite` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL,
    `team_id` BIGINT NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
    FOREIGN KEY (`team_id`) REFERENCES `team`(`id`),
    UNIQUE KEY uk_user_team (`user_id`, `team_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `notification` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL,
    `title` VARCHAR(200) NOT NULL,
    `content` TEXT NOT NULL,
    `type` VARCHAR(50),
    `is_read` TINYINT(1) DEFAULT 0,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
    INDEX idx_user (`user_id`),
    INDEX idx_is_read (`is_read`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
