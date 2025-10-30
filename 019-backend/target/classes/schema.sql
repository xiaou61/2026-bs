CREATE DATABASE IF NOT EXISTS campus_sport DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE campus_sport;

CREATE TABLE `user` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `username` VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    `password` VARCHAR(200) NOT NULL COMMENT '密码',
    `nickname` VARCHAR(50) COMMENT '昵称',
    `avatar` VARCHAR(200) COMMENT '头像',
    `gender` VARCHAR(10) COMMENT '性别',
    `phone` VARCHAR(20) COMMENT '手机号',
    `email` VARCHAR(50) COMMENT '邮箱',
    `role` VARCHAR(20) DEFAULT 'student' COMMENT '角色',
    `height` DECIMAL(5,2) COMMENT '身高cm',
    `weight` DECIMAL(5,2) COMMENT '体重kg',
    `points` INT DEFAULT 0 COMMENT '积分',
    `status` INT DEFAULT 0 COMMENT '状态',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted` INT DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

CREATE TABLE `sport_record` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `sport_type` VARCHAR(20) NOT NULL COMMENT '运动类型',
    `duration` INT DEFAULT 0 COMMENT '时长分钟',
    `distance` DECIMAL(8,2) DEFAULT 0 COMMENT '距离公里',
    `calories` INT DEFAULT 0 COMMENT '卡路里',
    `steps` INT DEFAULT 0 COMMENT '步数',
    `avg_speed` DECIMAL(5,2) COMMENT '平均配速',
    `points_earned` INT DEFAULT 0 COMMENT '获得积分',
    `remark` VARCHAR(500) COMMENT '备注',
    `sport_date` DATE NOT NULL COMMENT '运动日期',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted` INT DEFAULT 0,
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_sport_date` (`sport_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='运动记录表';

CREATE TABLE `sport_track` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `record_id` BIGINT NOT NULL COMMENT '运动记录ID',
    `latitude` DECIMAL(10,7) NOT NULL COMMENT '纬度',
    `longitude` DECIMAL(10,7) NOT NULL COMMENT '经度',
    `altitude` DECIMAL(8,2) COMMENT '海拔',
    `speed` DECIMAL(5,2) COMMENT '速度',
    `track_time` DATETIME NOT NULL COMMENT '轨迹时间',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX `idx_record_id` (`record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='运动轨迹表';

CREATE TABLE `fitness_plan` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `coach_id` BIGINT COMMENT '教练ID',
    `plan_name` VARCHAR(100) NOT NULL COMMENT '计划名称',
    `plan_type` VARCHAR(20) NOT NULL COMMENT '计划类型',
    `target_desc` VARCHAR(500) COMMENT '目标描述',
    `duration_days` INT DEFAULT 0 COMMENT '计划天数',
    `plan_content` TEXT COMMENT '计划内容',
    `start_date` DATE NOT NULL COMMENT '开始日期',
    `end_date` DATE NOT NULL COMMENT '结束日期',
    `completed_days` INT DEFAULT 0 COMMENT '完成天数',
    `status` VARCHAR(20) DEFAULT 'active' COMMENT '状态',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted` INT DEFAULT 0,
    INDEX `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='健身计划表';

CREATE TABLE `team_activity` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `creator_id` BIGINT NOT NULL COMMENT '发起人ID',
    `activity_name` VARCHAR(100) NOT NULL COMMENT '活动名称',
    `sport_type` VARCHAR(20) NOT NULL COMMENT '运动类型',
    `venue_id` BIGINT COMMENT '场馆ID',
    `activity_time` DATETIME NOT NULL COMMENT '活动时间',
    `max_participants` INT DEFAULT 10 COMMENT '最大人数',
    `current_participants` INT DEFAULT 1 COMMENT '当前人数',
    `description` VARCHAR(500) COMMENT '描述',
    `level_requirement` VARCHAR(20) COMMENT '水平要求',
    `status` VARCHAR(20) DEFAULT 'recruiting' COMMENT '状态',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted` INT DEFAULT 0,
    INDEX `idx_creator` (`creator_id`),
    INDEX `idx_activity_time` (`activity_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='约球活动表';

CREATE TABLE `activity_participant` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `activity_id` BIGINT NOT NULL COMMENT '活动ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `join_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '加入时间',
    `status` VARCHAR(20) DEFAULT 'joined' COMMENT '状态',
    `rating` INT COMMENT '评分',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX `idx_activity` (`activity_id`),
    INDEX `idx_user` (`user_id`),
    UNIQUE KEY `uk_activity_user` (`activity_id`, `user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='活动参与者表';

CREATE TABLE `health_profile` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `record_date` DATE NOT NULL COMMENT '记录日期',
    `weight` DECIMAL(5,2) COMMENT '体重kg',
    `bmi` DECIMAL(4,2) COMMENT 'BMI指数',
    `body_fat` DECIMAL(4,2) COMMENT '体脂率',
    `muscle_mass` DECIMAL(5,2) COMMENT '肌肉量',
    `water_intake` INT COMMENT '饮水量ml',
    `sleep_hours` DECIMAL(3,1) COMMENT '睡眠时长',
    `diet_calories` INT COMMENT '饮食卡路里',
    `diet_record` TEXT COMMENT '饮食记录',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted` INT DEFAULT 0,
    INDEX `idx_user_date` (`user_id`, `record_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='健康档案表';

CREATE TABLE `venue` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `venue_name` VARCHAR(100) NOT NULL COMMENT '场馆名称',
    `venue_type` VARCHAR(20) NOT NULL COMMENT '场馆类型',
    `location` VARCHAR(200) COMMENT '位置',
    `capacity` INT DEFAULT 0 COMMENT '容纳人数',
    `facilities` VARCHAR(500) COMMENT '设施',
    `opening_time` TIME COMMENT '开放时间',
    `closing_time` TIME COMMENT '关闭时间',
    `price_per_hour` DECIMAL(8,2) DEFAULT 0 COMMENT '每小时价格',
    `image_url` VARCHAR(200) COMMENT '图片',
    `status` INT DEFAULT 0 COMMENT '状态',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted` INT DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='场馆表';

CREATE TABLE `venue_booking` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `venue_id` BIGINT NOT NULL COMMENT '场馆ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `booking_date` DATE NOT NULL COMMENT '预约日期',
    `start_time` TIME NOT NULL COMMENT '开始时间',
    `end_time` TIME NOT NULL COMMENT '结束时间',
    `duration_hours` DECIMAL(3,1) DEFAULT 0 COMMENT '时长',
    `total_price` DECIMAL(8,2) DEFAULT 0 COMMENT '总价',
    `companion_count` INT DEFAULT 0 COMMENT '同伴人数',
    `status` VARCHAR(20) DEFAULT 'pending' COMMENT '状态',
    `cancel_reason` VARCHAR(200) COMMENT '取消原因',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted` INT DEFAULT 0,
    INDEX `idx_venue` (`venue_id`),
    INDEX `idx_user` (`user_id`),
    INDEX `idx_booking_date` (`booking_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='场馆预约表';

INSERT INTO `user` (`username`, `password`, `nickname`, `role`, `height`, `weight`, `points`) VALUES
('admin', '$2a$10$encrypted', '管理员', 'admin', 175, 70, 0),
('coach1', '$2a$10$encrypted', '教练张三', 'coach', 180, 75, 0),
('student1', '$2a$10$encrypted', '学生李四', 'student', 170, 65, 0);

INSERT INTO `venue` (`venue_name`, `venue_type`, `location`, `capacity`, `opening_time`, `closing_time`, `price_per_hour`) VALUES
('体育馆篮球场1号', 'basketball', '体育馆1楼', 20, '08:00:00', '22:00:00', 50),
('羽毛球馆1号', 'badminton', '体育馆2楼', 4, '08:00:00', '22:00:00', 30),
('网球场1号', 'tennis', '室外网球场', 4, '06:00:00', '20:00:00', 40),
('健身房', 'gym', '体育馆3楼', 50, '06:00:00', '23:00:00', 20);

