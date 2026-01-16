CREATE DATABASE IF NOT EXISTS psychology_system CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE psychology_system;

CREATE TABLE `user` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
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
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `counselor` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
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
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `scale` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `category` VARCHAR(50),
  `description` TEXT,
  `question_count` INT DEFAULT 0,
  `duration` INT DEFAULT 15,
  `status` VARCHAR(20) DEFAULT 'ACTIVE',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `question` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `scale_id` BIGINT NOT NULL,
  `content` TEXT NOT NULL,
  `type` VARCHAR(20) DEFAULT 'SINGLE',
  `options` TEXT,
  `score_rule` TEXT,
  `order_num` INT,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `assessment_record` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `scale_id` BIGINT NOT NULL,
  `answers` TEXT,
  `total_score` INT,
  `result_level` VARCHAR(50),
  `report` TEXT,
  `status` VARCHAR(20) DEFAULT 'COMPLETED',
  `started_at` DATETIME,
  `completed_at` DATETIME,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `time_slot` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `counselor_id` BIGINT NOT NULL,
  `slot_date` DATE NOT NULL,
  `slot_time` TIME NOT NULL,
  `duration` INT DEFAULT 60,
  `status` VARCHAR(20) DEFAULT 'AVAILABLE',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `appointment` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `counselor_id` BIGINT NOT NULL,
  `time_slot_id` BIGINT,
  `requirement` TEXT,
  `price` DECIMAL(10,2),
  `status` VARCHAR(20) DEFAULT 'PENDING',
  `payment_status` VARCHAR(20) DEFAULT 'UNPAID',
  `chat_room_id` VARCHAR(100),
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `consult_record` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `appointment_id` BIGINT NOT NULL,
  `counselor_id` BIGINT NOT NULL,
  `user_id` BIGINT NOT NULL,
  `record_content` TEXT,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `review` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `appointment_id` BIGINT NOT NULL,
  `user_id` BIGINT NOT NULL,
  `counselor_id` BIGINT NOT NULL,
  `rating` INT,
  `comment` TEXT,
  `is_anonymous` BOOLEAN DEFAULT FALSE,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `article` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
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
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `article_interaction` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `article_id` BIGINT NOT NULL,
  `user_id` BIGINT NOT NULL,
  `type` VARCHAR(20),
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `chat_message` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `appointment_id` BIGINT NOT NULL,
  `sender_id` BIGINT NOT NULL,
  `sender_role` VARCHAR(20),
  `content` TEXT,
  `message_type` VARCHAR(20) DEFAULT 'TEXT',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `notification` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `type` VARCHAR(50),
  `title` VARCHAR(200),
  `content` TEXT,
  `is_read` BOOLEAN DEFAULT FALSE,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO `user` (username, password, nickname, role, status) VALUES
('admin', '$2a$10$X5wFvL8P1Hz.ZVmqaXGkiukE4ggaVuW7pSzLuEZ2dK/.xGgRPEQRK', '系统管理员', 'ADMIN', 'ACTIVE'),
('counselor1', '$2a$10$X5wFvL8P1Hz.ZVmqaXGkiukE4ggaVuW7pSzLuEZ2dK/.xGgRPEQRK', '李心理', 'COUNSELOR', 'ACTIVE'),
('user1', '$2a$10$X5wFvL8P1Hz.ZVmqaXGkiukE4ggaVuW7pSzLuEZ2dK/.xGgRPEQRK', '测试用户', 'USER', 'ACTIVE');

INSERT INTO `counselor` (user_id, real_name, certificate, certificate_no, specialties, introduction, style, price, rating, status) VALUES
(2, '李心理', '国家二级心理咨询师', 'PSY2023001', '焦虑,抑郁,压力管理', '从业8年，擅长认知行为疗法', '温和包容,专业严谨', 300.00, 4.95, 'APPROVED');

INSERT INTO `scale` (name, category, description, question_count, duration, status) VALUES
('焦虑自评量表(SAS)', '焦虑量表', '用于评估焦虑症状的严重程度', 20, 10, 'ACTIVE'),
('抑郁自评量表(SDS)', '抑郁量表', '用于评估抑郁症状的严重程度', 20, 10, 'ACTIVE'),
('压力感知量表(PSS)', '压力量表', '评估个体感知压力的程度', 14, 8, 'ACTIVE');

INSERT INTO `question` (scale_id, content, type, options, score_rule, order_num) VALUES
(1, '我觉得比平常容易紧张和着急', 'SINGLE', '1=没有或很少时间;2=小部分时间;3=相当多时间;4=绝大部分或全部时间', '{"1":1,"2":2,"3":3,"4":4}', 1),
(1, '我无缘无故地感到害怕', 'SINGLE', '1=没有或很少时间;2=小部分时间;3=相当多时间;4=绝大部分或全部时间', '{"1":1,"2":2,"3":3,"4":4}', 2),
(2, '我感到情绪沮丧，郁闷', 'SINGLE', '1=没有或很少时间;2=小部分时间;3=相当多时间;4=绝大部分或全部时间', '{"1":1,"2":2,"3":3,"4":4}', 1),
(2, '我感到早晨心情最好', 'SINGLE', '1=没有或很少时间;2=小部分时间;3=相当多时间;4=绝大部分或全部时间', '{"1":4,"2":3,"3":2,"4":1}', 2);

INSERT INTO `article` (title, category, content, tags, author_id, status, is_recommended) VALUES
('如何缓解焦虑情绪', '情绪管理', '焦虑是现代人常见的情绪问题...本文将介绍几种有效的缓解方法。', '焦虑,情绪管理', 1, 'PUBLISHED', TRUE),
('正念冥想的力量', '自我成长', '正念冥想是一种有效的心理调节方法...', '正念,冥想,成长', 1, 'PUBLISHED', TRUE);

INSERT INTO `time_slot` (counselor_id, slot_date, slot_time, duration, status) VALUES
(1, '2026-01-20', '09:00:00', 60, 'AVAILABLE'),
(1, '2026-01-20', '10:00:00', 60, 'AVAILABLE'),
(1, '2026-01-20', '14:00:00', 60, 'AVAILABLE');
