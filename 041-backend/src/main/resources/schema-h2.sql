CREATE TABLE `user` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `username` VARCHAR(50) NOT NULL UNIQUE,
  `password` VARCHAR(255) NOT NULL,
  `nickname` VARCHAR(50),
  `avatar` VARCHAR(255),
  `gender` VARCHAR(10),
  `age` INT,
  `phone` VARCHAR(20),
  `email` VARCHAR(100),
  `role` VARCHAR(20) DEFAULT 'USER',
  `status` VARCHAR(20) DEFAULT 'ACTIVE',
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `counselor` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `user_id` BIGINT NOT NULL,
  `real_name` VARCHAR(50) NOT NULL,
  `certificate` VARCHAR(255),
  `certificate_no` VARCHAR(100),
  `specialties` VARCHAR(500),
  `introduction` TEXT,
  `style` VARCHAR(500),
  `price` DECIMAL(10,2) DEFAULT 200.00,
  `rating` DECIMAL(3,2) DEFAULT 5.00,
  `consult_count` INT DEFAULT 0,
  `status` VARCHAR(20) DEFAULT 'PENDING',
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `scale` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(100) NOT NULL,
  `category` VARCHAR(50),
  `description` TEXT,
  `question_count` INT DEFAULT 0,
  `duration` INT DEFAULT 15,
  `status` VARCHAR(20) DEFAULT 'ACTIVE',
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `question` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `scale_id` BIGINT NOT NULL,
  `content` TEXT NOT NULL,
  `type` VARCHAR(20) DEFAULT 'SINGLE',
  `options` TEXT,
  `score_rule` TEXT,
  `order_num` INT,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `assessment_record` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `user_id` BIGINT NOT NULL,
  `scale_id` BIGINT NOT NULL,
  `answers` TEXT,
  `total_score` INT,
  `result_level` VARCHAR(50),
  `report` TEXT,
  `status` VARCHAR(20) DEFAULT 'COMPLETED',
  `started_at` TIMESTAMP,
  `completed_at` TIMESTAMP,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `time_slot` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `counselor_id` BIGINT NOT NULL,
  `slot_date` DATE NOT NULL,
  `slot_time` TIME NOT NULL,
  `duration` INT DEFAULT 60,
  `status` VARCHAR(20) DEFAULT 'AVAILABLE',
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `appointment` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `user_id` BIGINT NOT NULL,
  `counselor_id` BIGINT NOT NULL,
  `time_slot_id` BIGINT,
  `requirement` TEXT,
  `price` DECIMAL(10,2),
  `status` VARCHAR(20) DEFAULT 'PENDING',
  `payment_status` VARCHAR(20) DEFAULT 'UNPAID',
  `chat_room_id` VARCHAR(100),
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `consult_record` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `appointment_id` BIGINT NOT NULL,
  `counselor_id` BIGINT NOT NULL,
  `user_id` BIGINT NOT NULL,
  `record_content` TEXT,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `review` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `appointment_id` BIGINT NOT NULL,
  `user_id` BIGINT NOT NULL,
  `counselor_id` BIGINT NOT NULL,
  `rating` INT,
  `comment` TEXT,
  `is_anonymous` BOOLEAN DEFAULT FALSE,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `article` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `title` VARCHAR(200) NOT NULL,
  `cover` VARCHAR(255),
  `category` VARCHAR(50),
  `content` TEXT,
  `tags` VARCHAR(255),
  `author_id` BIGINT,
  `view_count` INT DEFAULT 0,
  `like_count` INT DEFAULT 0,
  `collect_count` INT DEFAULT 0,
  `status` VARCHAR(20) DEFAULT 'PUBLISHED',
  `is_top` BOOLEAN DEFAULT FALSE,
  `is_recommended` BOOLEAN DEFAULT FALSE,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `article_interaction` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `article_id` BIGINT NOT NULL,
  `user_id` BIGINT NOT NULL,
  `type` VARCHAR(20),
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `chat_message` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `appointment_id` BIGINT NOT NULL,
  `sender_id` BIGINT NOT NULL,
  `sender_role` VARCHAR(20),
  `content` TEXT,
  `message_type` VARCHAR(20) DEFAULT 'TEXT',
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `notification` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `user_id` BIGINT NOT NULL,
  `type` VARCHAR(50),
  `title` VARCHAR(200),
  `content` TEXT,
  `is_read` BOOLEAN DEFAULT FALSE,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
