-- 创建数据库
CREATE DATABASE IF NOT EXISTS hair_salon DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE hair_salon;

-- 1. 用户表
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    `phone` VARCHAR(11) NOT NULL UNIQUE COMMENT '手机号',
    `password` VARCHAR(255) NOT NULL COMMENT '密码（BCrypt加密）',
    `nickname` VARCHAR(50) DEFAULT NULL COMMENT '昵称',
    `avatar` VARCHAR(255) DEFAULT 'https://via.placeholder.com/150' COMMENT '头像URL',
    `gender` TINYINT DEFAULT 0 COMMENT '性别（0女1男）',
    `points` INT DEFAULT 0 COMMENT '积分',
    `level` VARCHAR(20) DEFAULT '普通会员' COMMENT '会员等级',
    `balance` DECIMAL(10,2) DEFAULT 0.00 COMMENT '余额',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_phone` (`phone`),
    INDEX `idx_level` (`level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 2. 门店表
DROP TABLE IF EXISTS `store`;
CREATE TABLE `store` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '门店ID',
    `name` VARCHAR(100) NOT NULL COMMENT '门店名称',
    `address` VARCHAR(255) NOT NULL COMMENT '详细地址',
    `phone` VARCHAR(20) NOT NULL COMMENT '联系电话',
    `open_time` TIME NOT NULL DEFAULT '09:00:00' COMMENT '营业开始时间',
    `close_time` TIME NOT NULL DEFAULT '21:00:00' COMMENT '营业结束时间',
    `intro` TEXT COMMENT '门店介绍',
    `images` TEXT COMMENT '门店图片（JSON数组）',
    `rating` DECIMAL(3,2) DEFAULT 5.00 COMMENT '评分',
    `longitude` DECIMAL(10,7) DEFAULT NULL COMMENT '经度',
    `latitude` DECIMAL(10,7) DEFAULT NULL COMMENT '纬度',
    `status` TINYINT DEFAULT 1 COMMENT '状态（0停业1营业）',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX `idx_status` (`status`),
    INDEX `idx_rating` (`rating`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='门店表';

-- 3. 理发师表
DROP TABLE IF EXISTS `hairdresser`;
CREATE TABLE `hairdresser` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '理发师ID',
    `store_id` BIGINT NOT NULL COMMENT '所属门店ID',
    `name` VARCHAR(50) NOT NULL COMMENT '姓名',
    `gender` TINYINT DEFAULT 1 COMMENT '性别（0女1男）',
    `work_no` VARCHAR(20) NOT NULL COMMENT '工号',
    `avatar` VARCHAR(255) DEFAULT 'https://via.placeholder.com/150' COMMENT '照片',
    `skills` VARCHAR(500) DEFAULT NULL COMMENT '技能标签（JSON数组）',
    `work_years` INT DEFAULT 0 COMMENT '从业年限',
    `intro` TEXT COMMENT '个人简介',
    `rating` DECIMAL(3,2) DEFAULT 5.00 COMMENT '评分',
    `status` TINYINT DEFAULT 1 COMMENT '状态（0休息1在岗2请假）',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`store_id`) REFERENCES `store`(`id`) ON DELETE CASCADE,
    INDEX `idx_store_id` (`store_id`),
    INDEX `idx_rating` (`rating`),
    INDEX `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='理发师表';

-- 4. 服务项目表
DROP TABLE IF EXISTS `service`;
CREATE TABLE `service` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '服务项目ID',
    `store_id` BIGINT NOT NULL COMMENT '所属门店ID',
    `category` VARCHAR(20) NOT NULL COMMENT '分类',
    `name` VARCHAR(100) NOT NULL COMMENT '项目名称',
    `price` DECIMAL(10,2) NOT NULL COMMENT '价格',
    `duration` INT NOT NULL DEFAULT 30 COMMENT '时长（分钟）',
    `description` TEXT COMMENT '描述',
    `image` VARCHAR(255) DEFAULT NULL COMMENT '效果图',
    `status` TINYINT DEFAULT 1 COMMENT '状态（0下架1上架）',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`store_id`) REFERENCES `store`(`id`) ON DELETE CASCADE,
    INDEX `idx_store_id` (`store_id`),
    INDEX `idx_category` (`category`),
    INDEX `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务项目表';

-- 5. 预约表
DROP TABLE IF EXISTS `appointment`;
CREATE TABLE `appointment` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '预约ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `store_id` BIGINT NOT NULL COMMENT '门店ID',
    `hairdresser_id` BIGINT NOT NULL COMMENT '理发师ID',
    `service_id` BIGINT NOT NULL COMMENT '服务项目ID',
    `appointment_date` DATE NOT NULL COMMENT '预约日期',
    `appointment_time` TIME NOT NULL COMMENT '预约时间',
    `status` VARCHAR(20) DEFAULT '待确认' COMMENT '状态',
    `cancel_reason` VARCHAR(255) DEFAULT NULL COMMENT '取消原因',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`store_id`) REFERENCES `store`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`hairdresser_id`) REFERENCES `hairdresser`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`service_id`) REFERENCES `service`(`id`) ON DELETE CASCADE,
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_status` (`status`),
    INDEX `idx_appointment_date` (`appointment_date`),
    INDEX `idx_hairdresser_date_time` (`hairdresser_id`, `appointment_date`, `appointment_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='预约表';

-- 6. 订单表
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '订单ID',
    `order_no` VARCHAR(32) NOT NULL UNIQUE COMMENT '订单号',
    `appointment_id` BIGINT NOT NULL COMMENT '预约ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `store_id` BIGINT NOT NULL COMMENT '门店ID',
    `hairdresser_id` BIGINT NOT NULL COMMENT '理发师ID',
    `service_name` VARCHAR(100) NOT NULL COMMENT '服务名称',
    `original_price` DECIMAL(10,2) NOT NULL COMMENT '原价',
    `discount` DECIMAL(3,2) DEFAULT 1.00 COMMENT '折扣',
    `actual_price` DECIMAL(10,2) NOT NULL COMMENT '实付金额',
    `pay_type` VARCHAR(20) DEFAULT NULL COMMENT '支付方式',
    `pay_status` TINYINT DEFAULT 0 COMMENT '支付状态（0未支付1已支付）',
    `pay_time` DATETIME DEFAULT NULL COMMENT '支付时间',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`appointment_id`) REFERENCES `appointment`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE,
    INDEX `idx_order_no` (`order_no`),
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_pay_status` (`pay_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 7. 评价表
DROP TABLE IF EXISTS `review`;
CREATE TABLE `review` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '评价ID',
    `order_id` BIGINT NOT NULL COMMENT '订单ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `hairdresser_id` BIGINT NOT NULL COMMENT '理发师ID',
    `store_id` BIGINT NOT NULL COMMENT '门店ID',
    `rating` TINYINT NOT NULL COMMENT '评分（1-5）',
    `content` TEXT COMMENT '评价内容',
    `images` TEXT COMMENT '评价图片（JSON数组）',
    `tags` VARCHAR(255) DEFAULT NULL COMMENT '评价标签（JSON数组）',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`order_id`) REFERENCES `orders`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`hairdresser_id`) REFERENCES `hairdresser`(`id`) ON DELETE CASCADE,
    INDEX `idx_order_id` (`order_id`),
    INDEX `idx_hairdresser_id` (`hairdresser_id`),
    INDEX `idx_store_id` (`store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评价表';

-- 8. 排班表
DROP TABLE IF EXISTS `schedule`;
CREATE TABLE `schedule` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '排班ID',
    `hairdresser_id` BIGINT NOT NULL COMMENT '理发师ID',
    `work_date` DATE NOT NULL COMMENT '工作日期',
    `start_time` TIME NOT NULL COMMENT '开始时间',
    `end_time` TIME NOT NULL COMMENT '结束时间',
    `status` TINYINT DEFAULT 1 COMMENT '状态（0休息1上班）',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`hairdresser_id`) REFERENCES `hairdresser`(`id`) ON DELETE CASCADE,
    UNIQUE KEY `uk_hairdresser_date` (`hairdresser_id`, `work_date`),
    INDEX `idx_work_date` (`work_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='排班表';

-- 9. 积分记录表
DROP TABLE IF EXISTS `points_log`;
CREATE TABLE `points_log` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '记录ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `points` INT NOT NULL COMMENT '积分变动（正数增加，负数减少）',
    `reason` VARCHAR(100) NOT NULL COMMENT '变动原因',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE,
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='积分记录表';

-- 10. 余额记录表
DROP TABLE IF EXISTS `balance_log`;
CREATE TABLE `balance_log` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '记录ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `amount` DECIMAL(10,2) NOT NULL COMMENT '金额变动',
    `type` VARCHAR(20) NOT NULL COMMENT '类型（充值/消费）',
    `reason` VARCHAR(100) NOT NULL COMMENT '变动原因',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE,
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_type` (`type`),
    INDEX `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='余额记录表';

-- 11. 消息通知表
DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '通知ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `type` VARCHAR(20) NOT NULL COMMENT '类型',
    `title` VARCHAR(100) NOT NULL COMMENT '标题',
    `content` TEXT NOT NULL COMMENT '内容',
    `is_read` TINYINT DEFAULT 0 COMMENT '是否已读（0未读1已读）',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE,
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_is_read` (`is_read`),
    INDEX `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息通知表';

-- 12. 管理员表
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '管理员ID',
    `username` VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    `password` VARCHAR(255) NOT NULL COMMENT '密码（BCrypt加密）',
    `role` VARCHAR(20) DEFAULT 'ADMIN' COMMENT '角色',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX `idx_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员表';
