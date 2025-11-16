-- 创建数据库
CREATE DATABASE IF NOT EXISTS study_room CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE study_room;

-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    `username` VARCHAR(50) UNIQUE NOT NULL COMMENT '学号',
    `password` VARCHAR(255) NOT NULL COMMENT '密码(MD5加密)',
    `real_name` VARCHAR(50) NOT NULL COMMENT '真实姓名',
    `department` VARCHAR(100) COMMENT '院系',
    `grade` VARCHAR(20) COMMENT '年级',
    `phone` VARCHAR(20) COMMENT '手机号',
    `credit_score` INT DEFAULT 100 COMMENT '信用分数',
    `status` TINYINT DEFAULT 1 COMMENT '状态：1-正常，0-禁用',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除'
) COMMENT '用户表';

-- 自习室表
CREATE TABLE IF NOT EXISTS `study_room` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '自习室ID',
    `room_name` VARCHAR(100) NOT NULL COMMENT '自习室名称',
    `floor_number` INT NOT NULL COMMENT '楼层',
    `capacity` INT NOT NULL COMMENT '容纳人数',
    `open_time` TIME NOT NULL COMMENT '开放时间',
    `close_time` TIME NOT NULL COMMENT '关闭时间',
    `description` TEXT COMMENT '描述信息',
    `status` TINYINT DEFAULT 1 COMMENT '状态：1-开放，0-关闭',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除'
) COMMENT '自习室表';

-- 座位表
CREATE TABLE IF NOT EXISTS `seat` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '座位ID',
    `room_id` BIGINT NOT NULL COMMENT '自习室ID',
    `seat_number` VARCHAR(20) NOT NULL COMMENT '座位编号',
    `seat_type` TINYINT DEFAULT 1 COMMENT '座位类型：1-普通，2-电源，3-静音区',
    `seat_status` TINYINT DEFAULT 1 COMMENT '座位状态：1-空闲，2-占用，3-维修',
    `x_coordinate` DECIMAL(10,2) COMMENT 'X坐标',
    `y_coordinate` DECIMAL(10,2) COMMENT 'Y坐标',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
    FOREIGN KEY (`room_id`) REFERENCES `study_room`(`id`)
) COMMENT '座位表';

-- 预约记录表
CREATE TABLE IF NOT EXISTS `reservation` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '预约记录ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `seat_id` BIGINT NOT NULL COMMENT '座位ID',
    `reservation_time` DATETIME NOT NULL COMMENT '预约时间',
    `start_time` DATETIME NOT NULL COMMENT '使用开始时间',
    `end_time` DATETIME NOT NULL COMMENT '使用结束时间',
    `check_in_time` DATETIME COMMENT '实际签到时间',
    `actual_end_time` DATETIME COMMENT '实际结束时间',
    `status` TINYINT DEFAULT 1 COMMENT '状态：1-已预约，2-已签到，3-已完成，4-已取消，5-违约',
    `qrcode_content` VARCHAR(255) COMMENT '二维码内容',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
    FOREIGN KEY (`seat_id`) REFERENCES `seat`(`id`)
) COMMENT '预约记录表';

-- 信用记录表
CREATE TABLE IF NOT EXISTS `credit_record` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '记录ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `score_change` INT NOT NULL COMMENT '分数变动',
    `change_reason` VARCHAR(100) NOT NULL COMMENT '变动原因',
    `related_type` VARCHAR(50) COMMENT '关联类型：reservation/check_in等',
    `related_id` BIGINT COMMENT '关联ID',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`)
) COMMENT '信用记录表';

-- 使用统计表
CREATE TABLE IF NOT EXISTS `usage_statistics` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '统计ID',
    `room_id` BIGINT NOT NULL COMMENT '自习室ID',
    `stat_date` DATE NOT NULL COMMENT '统计日期',
    `total_reservations` INT DEFAULT 0 COMMENT '总预约数',
    `actual_checkins` INT DEFAULT 0 COMMENT '实际签到数',
    `peak_hour` TIME COMMENT '高峰时段',
    `usage_rate` DECIMAL(5,2) DEFAULT 0 COMMENT '使用率',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
    FOREIGN KEY (`room_id`) REFERENCES `study_room`(`id`)
) COMMENT '使用统计表';

-- 系统配置表
CREATE TABLE IF NOT EXISTS `system_config` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '配置ID',
    `config_key` VARCHAR(100) UNIQUE NOT NULL COMMENT '配置键',
    `config_value` TEXT NOT NULL COMMENT '配置值',
    `description` VARCHAR(255) COMMENT '配置描述',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除'
) COMMENT '系统配置表';

-- 插入初始数据

-- 插入系统配置
INSERT INTO `system_config` (`config_key`, `config_value`, `description`) VALUES
('reservation_max_hours', '4', '单次预约最长时长（小时）'),
('reservation_max_days', '2', '提前预约最长天数'),
('check_in_timeout_minutes', '15', '签到超时时间（分钟）'),
('credit_score_initial', '100', '初始信用分数'),
('credit_score_checkin_reward', '2', '正常签到奖励分数'),
('credit_score_no_show_penalty', '5', '未到违约扣分'),
('credit_score_early_leave_penalty', '2', '提前离开扣分'),
('credit_score_limit_restricted', '60', '受限预约信用分阈值'),
('credit_score_limit_banned', '30', '禁止预约信用分阈值');

-- 插入自习室示例数据
INSERT INTO `study_room` (`room_name`, `floor_number`, `capacity`, `open_time`, `close_time`, `description`) VALUES
('图书馆一楼阅览室', 1, 80, '08:00:00', '22:00:00', '图书馆一楼大型阅览室，环境安静，适合长时间学习'),
('图书馆二楼电子阅览室', 2, 60, '08:00:00', '22:00:00', '配备电源插座，适合使用电子设备学习'),
('图书馆三楼自习室A区', 3, 40, '08:00:00', '22:00:00', '安静自习区域，禁止交谈'),
('图书馆三楼自习室B区', 3, 40, '08:00:00', '22:00:00', '普通自习区域，可轻声讨论'),
('图书馆四楼考研专区', 4, 50, '07:00:00', '23:00:00', '专门为考研学生准备的安静学习区域');

-- 插入座位示例数据（每个自习室插入一些示例座位）
INSERT INTO `seat` (`room_id`, `seat_number`, `seat_type`, `seat_status`, `x_coordinate`, `y_coordinate`) VALUES
-- 图书馆一楼阅览室
(1, 'A01', 1, 1, 10.5, 15.2),
(1, 'A02', 1, 1, 10.5, 25.4),
(1, 'A03', 2, 1, 10.5, 35.6),
(1, 'A04', 2, 1, 20.8, 15.2),
(1, 'A05', 2, 1, 20.8, 25.4),
(1, 'A06', 1, 1, 20.8, 35.6),
(1, 'B01', 3, 1, 31.1, 15.2),
(1, 'B02', 3, 1, 31.1, 25.4),
(1, 'B03', 3, 1, 31.1, 35.6),
(1, 'B04', 3, 1, 41.4, 15.2),

-- 图书馆二楼电子阅览室
(2, 'C01', 2, 1, 12.0, 16.0),
(2, 'C02', 2, 1, 12.0, 26.0),
(2, 'C03', 2, 1, 12.0, 36.0),
(2, 'C04', 2, 1, 22.0, 16.0),
(2, 'C05', 2, 1, 22.0, 26.0),
(2, 'C06', 2, 1, 22.0, 36.0),

-- 图书馆三楼自习室A区
(3, 'D01', 3, 1, 11.0, 15.5),
(3, 'D02', 3, 1, 11.0, 25.5),
(3, 'D03', 3, 1, 21.5, 15.5),
(3, 'D04', 3, 1, 21.5, 25.5),

-- 图书馆三楼自习室B区
(4, 'E01', 1, 1, 13.0, 17.0),
(4, 'E02', 1, 1, 13.0, 27.0),
(4, 'E03', 2, 1, 23.0, 17.0),
(4, 'E04', 2, 1, 23.0, 27.0),

-- 图书馆四楼考研专区
(5, 'F01', 3, 1, 14.0, 18.0),
(5, 'F02', 3, 1, 14.0, 28.0),
(5, 'F03', 3, 1, 24.0, 18.0),
(5, 'F04', 3, 1, 24.0, 28.0),
(5, 'F05', 2, 1, 34.0, 18.0),
(5, 'F06', 2, 1, 34.0, 28.0);

-- 创建索引以提高查询性能
CREATE INDEX idx_user_username ON `user`(username);
CREATE INDEX idx_user_credit_score ON `user`(credit_score);
CREATE INDEX idx_seat_room_id ON `seat`(room_id);
CREATE INDEX idx_seat_status ON `seat`(seat_status);
CREATE INDEX idx_reservation_user_id ON `reservation`(user_id);
CREATE INDEX idx_reservation_seat_id ON `reservation`(seat_id);
CREATE INDEX idx_reservation_start_time ON `reservation`(start_time);
CREATE INDEX idx_reservation_status ON `reservation`(status);
CREATE INDEX idx_credit_record_user_id ON `credit_record`(user_id);
CREATE INDEX idx_usage_statistics_room_date ON `usage_statistics`(room_id, stat_date);