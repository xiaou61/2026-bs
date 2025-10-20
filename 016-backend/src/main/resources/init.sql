CREATE DATABASE IF NOT EXISTS campus_express DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE campus_express;

CREATE TABLE `user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `student_id` VARCHAR(30) NOT NULL COMMENT '学号',
    `username` VARCHAR(50) NOT NULL COMMENT '用户名',
    `phone` VARCHAR(20) NOT NULL COMMENT '手机号',
    `password` VARCHAR(100) NOT NULL COMMENT '密码',
    `real_name` VARCHAR(50) NOT NULL COMMENT '真实姓名',
    `avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像',
    `dormitory_building` VARCHAR(50) DEFAULT NULL COMMENT '宿舍楼栋',
    `dormitory_room` VARCHAR(20) DEFAULT NULL COMMENT '房间号',
    `credit_score` INT DEFAULT 100 COMMENT '信用分',
    `total_orders` INT DEFAULT 0 COMMENT '累计发单数',
    `total_takes` INT DEFAULT 0 COMMENT '累计接单数',
    `status` TINYINT DEFAULT 0 COMMENT '状态 0正常 1禁用',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_student_id` (`student_id`),
    UNIQUE KEY `uk_phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

CREATE TABLE `orders` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `order_no` VARCHAR(32) NOT NULL COMMENT '订单编号',
    `publisher_id` BIGINT NOT NULL COMMENT '发单人ID',
    `taker_id` BIGINT DEFAULT NULL COMMENT '接单人ID',
    `express_company` VARCHAR(50) NOT NULL COMMENT '快递公司',
    `pickup_code` VARCHAR(50) NOT NULL COMMENT '取件码',
    `item_type` VARCHAR(20) NOT NULL COMMENT '物品类型',
    `item_weight` VARCHAR(20) NOT NULL COMMENT '物品重量',
    `pickup_address` VARCHAR(200) NOT NULL COMMENT '取件地址',
    `delivery_address` VARCHAR(200) NOT NULL COMMENT '送达地址',
    `delivery_building` VARCHAR(50) NOT NULL COMMENT '宿舍楼栋',
    `delivery_room` VARCHAR(20) NOT NULL COMMENT '房间号',
    `fee` DECIMAL(10,2) NOT NULL COMMENT '跑腿费',
    `expect_time` DATETIME DEFAULT NULL COMMENT '期望送达时间',
    `contact_phone` VARCHAR(20) NOT NULL COMMENT '联系电话',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注说明',
    `pickup_image` VARCHAR(255) DEFAULT NULL COMMENT '取件凭证',
    `delivery_image` VARCHAR(255) DEFAULT NULL COMMENT '送达凭证',
    `status` TINYINT DEFAULT 0 COMMENT '状态 0待接单 1待取件 2配送中 3待确认 4已完成 5已取消 6已退款',
    `accept_time` DATETIME DEFAULT NULL COMMENT '接单时间',
    `pickup_time` DATETIME DEFAULT NULL COMMENT '取件时间',
    `delivery_time` DATETIME DEFAULT NULL COMMENT '送达时间',
    `finish_time` DATETIME DEFAULT NULL COMMENT '完成时间',
    `cancel_reason` VARCHAR(200) DEFAULT NULL COMMENT '取消原因',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_order_no` (`order_no`),
    KEY `idx_publisher_id` (`publisher_id`),
    KEY `idx_taker_id` (`taker_id`),
    KEY `idx_status` (`status`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

CREATE TABLE `review` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `order_id` BIGINT NOT NULL COMMENT '订单ID',
    `order_no` VARCHAR(32) NOT NULL COMMENT '订单编号',
    `reviewer_id` BIGINT NOT NULL COMMENT '评价人ID',
    `reviewee_id` BIGINT NOT NULL COMMENT '被评价人ID',
    `rating` INT NOT NULL COMMENT '评分 1-5',
    `tags` VARCHAR(200) DEFAULT NULL COMMENT '评价标签',
    `content` TEXT DEFAULT NULL COMMENT '评价内容',
    `is_anonymous` TINYINT DEFAULT 0 COMMENT '是否匿名 0否 1是',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_order_id` (`order_id`),
    KEY `idx_reviewee_id` (`reviewee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评价表';

CREATE TABLE `wallet` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `balance` DECIMAL(10,2) DEFAULT 0.00 COMMENT '可用余额',
    `frozen_amount` DECIMAL(10,2) DEFAULT 0.00 COMMENT '冻结金额',
    `total_income` DECIMAL(10,2) DEFAULT 0.00 COMMENT '累计收入',
    `total_expense` DECIMAL(10,2) DEFAULT 0.00 COMMENT '累计支出',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='钱包表';

CREATE TABLE `transaction` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `transaction_no` VARCHAR(32) NOT NULL COMMENT '交易流水号',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `order_id` BIGINT DEFAULT NULL COMMENT '订单ID',
    `type` TINYINT NOT NULL COMMENT '类型 1充值 2消费 3收入 4提现 5退款',
    `amount` DECIMAL(10,2) NOT NULL COMMENT '金额',
    `balance_before` DECIMAL(10,2) NOT NULL COMMENT '交易前余额',
    `balance_after` DECIMAL(10,2) NOT NULL COMMENT '交易后余额',
    `description` VARCHAR(200) DEFAULT NULL COMMENT '描述',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_transaction_no` (`transaction_no`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='交易记录表';

CREATE TABLE `notification` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` BIGINT NOT NULL COMMENT '接收用户ID',
    `type` TINYINT NOT NULL COMMENT '类型 1系统 2订单 3交易',
    `title` VARCHAR(100) NOT NULL COMMENT '标题',
    `content` VARCHAR(500) NOT NULL COMMENT '内容',
    `link_type` VARCHAR(20) DEFAULT NULL COMMENT '关联类型',
    `link_id` BIGINT DEFAULT NULL COMMENT '关联ID',
    `is_read` TINYINT DEFAULT 0 COMMENT '是否已读 0否 1是',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_is_read` (`is_read`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='通知表';

CREATE TABLE `complaint` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `order_id` BIGINT NOT NULL COMMENT '订单ID',
    `complainant_id` BIGINT NOT NULL COMMENT '投诉人ID',
    `respondent_id` BIGINT NOT NULL COMMENT '被投诉人ID',
    `type` VARCHAR(50) NOT NULL COMMENT '投诉类型',
    `reason` TEXT NOT NULL COMMENT '投诉原因',
    `evidence` TEXT DEFAULT NULL COMMENT '证据图片',
    `status` TINYINT DEFAULT 0 COMMENT '状态 0待处理 1已处理 2已驳回',
    `handle_result` TEXT DEFAULT NULL COMMENT '处理结果',
    `handle_admin_id` BIGINT DEFAULT NULL COMMENT '处理管理员ID',
    `handle_time` DATETIME DEFAULT NULL COMMENT '处理时间',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_order_id` (`order_id`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='投诉表';

CREATE TABLE `admin` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `username` VARCHAR(50) NOT NULL COMMENT '用户名',
    `password` VARCHAR(100) NOT NULL COMMENT '密码',
    `real_name` VARCHAR(50) NOT NULL COMMENT '真实姓名',
    `role` TINYINT DEFAULT 2 COMMENT '角色 1超级管理员 2普通管理员',
    `status` TINYINT DEFAULT 0 COMMENT '状态 0正常 1禁用',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员表';

INSERT INTO `admin` (`username`, `password`, `real_name`, `role`, `status`) 
VALUES ('admin', '$2a$10$YJZeZH7nQ0BnxLhKLQxKdO5KqN6b6j3vVV4nXTLPxHN3pN1rj3j9O', '系统管理员', 1, 0);

INSERT INTO `user` (`student_id`, `username`, `phone`, `password`, `real_name`, `dormitory_building`, `dormitory_room`, `credit_score`) 
VALUES 
('20210001', 'user001', '13800138001', '$2a$10$YJZeZH7nQ0BnxLhKLQxKdO5KqN6b6j3vVV4nXTLPxHN3pN1rj3j9O', '张三', '东区1号楼', '101', 100),
('20210002', 'user002', '13800138002', '$2a$10$YJZeZH7nQ0BnxLhKLQxKdO5KqN6b6j3vVV4nXTLPxHN3pN1rj3j9O', '李四', '东区2号楼', '201', 100);

INSERT INTO `wallet` (`user_id`, `balance`, `frozen_amount`, `total_income`, `total_expense`) 
VALUES 
(1, 100.00, 0.00, 0.00, 0.00),
(2, 100.00, 0.00, 0.00, 0.00);

