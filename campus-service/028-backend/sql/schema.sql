-- 校园共享自行车租赁系统数据库脚本
-- 创建数据库
CREATE DATABASE IF NOT EXISTS campus_bike DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE campus_bike;

-- 1. 用户表
CREATE TABLE IF NOT EXISTS `user` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    `username` VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    `password` VARCHAR(255) NOT NULL COMMENT '密码（加密）',
    `real_name` VARCHAR(50) COMMENT '真实姓名',
    `phone` VARCHAR(20) COMMENT '手机号',
    `student_id` VARCHAR(30) COMMENT '学号/工号',
    `id_card` VARCHAR(30) COMMENT '身份证号',
    `avatar` VARCHAR(255) COMMENT '头像URL',
    `gender` TINYINT DEFAULT 0 COMMENT '性别：0未知 1男 2女',
    `email` VARCHAR(100) COMMENT '邮箱',
    `credit_score` INT DEFAULT 100 COMMENT '信用分',
    `status` TINYINT DEFAULT 1 COMMENT '状态：0禁用 1正常',
    `auth_status` TINYINT DEFAULT 0 COMMENT '认证状态：0未认证 1待审核 2已认证 3认证失败',
    `auth_image` VARCHAR(255) COMMENT '认证图片（学生证/工作证）',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除：0正常 1删除',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_phone` (`phone`),
    INDEX `idx_student_id` (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 2. 管理员表
CREATE TABLE IF NOT EXISTS `admin` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    `username` VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    `password` VARCHAR(255) NOT NULL COMMENT '密码',
    `real_name` VARCHAR(50) COMMENT '真实姓名',
    `phone` VARCHAR(20) COMMENT '手机号',
    `avatar` VARCHAR(255) COMMENT '头像',
    `role` TINYINT DEFAULT 1 COMMENT '角色：1普通管理员 2超级管理员 3运维人员',
    `status` TINYINT DEFAULT 1 COMMENT '状态：0禁用 1正常',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员表';

-- 3. 停车点表
CREATE TABLE IF NOT EXISTS `station` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    `station_name` VARCHAR(100) NOT NULL COMMENT '停车点名称',
    `address` VARCHAR(255) COMMENT '详细地址',
    `longitude` DECIMAL(10,7) COMMENT '经度',
    `latitude` DECIMAL(10,7) COMMENT '纬度',
    `capacity` INT DEFAULT 50 COMMENT '最大容量',
    `current_count` INT DEFAULT 0 COMMENT '当前车辆数',
    `status` TINYINT DEFAULT 1 COMMENT '状态：0停用 1正常',
    `description` VARCHAR(500) COMMENT '描述',
    `image` VARCHAR(255) COMMENT '停车点图片',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='停车点表';

-- 4. 自行车表
CREATE TABLE IF NOT EXISTS `bicycle` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    `bike_no` VARCHAR(30) NOT NULL UNIQUE COMMENT '车辆编号',
    `qr_code` VARCHAR(255) COMMENT '二维码内容',
    `bike_type` TINYINT DEFAULT 1 COMMENT '车辆类型：1普通单车 2电动助力车',
    `status` TINYINT DEFAULT 0 COMMENT '状态：0空闲 1使用中 2维修中 3报废',
    `station_id` BIGINT COMMENT '当前停车点ID',
    `longitude` DECIMAL(10,7) COMMENT '经度',
    `latitude` DECIMAL(10,7) COMMENT '纬度',
    `total_mileage` DECIMAL(10,2) DEFAULT 0 COMMENT '总里程（km）',
    `total_orders` INT DEFAULT 0 COMMENT '总订单数',
    `last_maintenance` DATETIME COMMENT '上次维护时间',
    `purchase_date` DATE COMMENT '购入日期',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_station` (`station_id`),
    INDEX `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='自行车表';

-- 5. 租借订单表
CREATE TABLE IF NOT EXISTS `rental_order` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    `order_no` VARCHAR(50) NOT NULL UNIQUE COMMENT '订单编号',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `bike_id` BIGINT NOT NULL COMMENT '车辆ID',
    `start_station_id` BIGINT COMMENT '起始停车点ID',
    `end_station_id` BIGINT COMMENT '结束停车点ID',
    `start_time` DATETIME NOT NULL COMMENT '开始时间',
    `end_time` DATETIME COMMENT '结束时间',
    `duration` INT DEFAULT 0 COMMENT '骑行时长（分钟）',
    `distance` DECIMAL(10,2) DEFAULT 0 COMMENT '骑行距离（km）',
    `amount` DECIMAL(10,2) DEFAULT 0 COMMENT '订单金额',
    `status` TINYINT DEFAULT 0 COMMENT '状态：0进行中 1已完成 2异常 3已取消',
    `pay_status` TINYINT DEFAULT 0 COMMENT '支付状态：0未支付 1已支付',
    `return_photo` VARCHAR(255) COMMENT '还车照片',
    `remark` VARCHAR(500) COMMENT '备注',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_user` (`user_id`),
    INDEX `idx_bike` (`bike_id`),
    INDEX `idx_status` (`status`),
    INDEX `idx_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='租借订单表';

-- 6. 用户钱包表
CREATE TABLE IF NOT EXISTS `user_wallet` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    `user_id` BIGINT NOT NULL UNIQUE COMMENT '用户ID',
    `balance` DECIMAL(10,2) DEFAULT 0 COMMENT '余额',
    `deposit` DECIMAL(10,2) DEFAULT 0 COMMENT '押金',
    `deposit_status` TINYINT DEFAULT 0 COMMENT '押金状态：0未缴纳 1已缴纳 2退还中 3已退还',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户钱包表';

-- 7. 充值记录表
CREATE TABLE IF NOT EXISTS `recharge_record` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `amount` DECIMAL(10,2) NOT NULL COMMENT '充值金额',
    `type` TINYINT DEFAULT 1 COMMENT '类型：1余额充值 2押金缴纳',
    `pay_method` TINYINT DEFAULT 1 COMMENT '支付方式：1微信 2支付宝 3银行卡',
    `status` TINYINT DEFAULT 1 COMMENT '状态：0待支付 1已完成 2已取消',
    `transaction_no` VARCHAR(50) COMMENT '交易流水号',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX `idx_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='充值记录表';

-- 8. 消费记录表
CREATE TABLE IF NOT EXISTS `consumption_record` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `order_id` BIGINT COMMENT '关联订单ID',
    `amount` DECIMAL(10,2) NOT NULL COMMENT '消费金额',
    `type` TINYINT DEFAULT 1 COMMENT '类型：1骑行消费 2退款',
    `balance_after` DECIMAL(10,2) COMMENT '消费后余额',
    `description` VARCHAR(255) COMMENT '描述',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX `idx_user` (`user_id`),
    INDEX `idx_order` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消费记录表';

-- 9. 故障上报表
CREATE TABLE IF NOT EXISTS `fault_report` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    `bike_id` BIGINT NOT NULL COMMENT '车辆ID',
    `user_id` BIGINT NOT NULL COMMENT '上报用户ID',
    `fault_type` TINYINT NOT NULL COMMENT '故障类型：1刹车故障 2车铃故障 3轮胎故障 4车锁故障 5座椅故障 6其他',
    `description` VARCHAR(500) COMMENT '故障描述',
    `images` VARCHAR(1000) COMMENT '故障图片（多个逗号分隔）',
    `status` TINYINT DEFAULT 0 COMMENT '状态：0待处理 1处理中 2已完成 3已关闭',
    `handler_id` BIGINT COMMENT '处理人ID',
    `handle_time` DATETIME COMMENT '处理时间',
    `handle_result` VARCHAR(500) COMMENT '处理结果',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_bike` (`bike_id`),
    INDEX `idx_user` (`user_id`),
    INDEX `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='故障上报表';

-- 10. 信用记录表
CREATE TABLE IF NOT EXISTS `credit_record` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `change_value` INT NOT NULL COMMENT '变动值',
    `change_type` TINYINT NOT NULL COMMENT '变动类型：1正常还车 2规范停车 3上报故障 4超时未还 5违规停车 6损坏车辆 7管理员调整',
    `reason` VARCHAR(255) COMMENT '变动原因',
    `order_id` BIGINT COMMENT '关联订单ID',
    `score_after` INT COMMENT '变动后信用分',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX `idx_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='信用记录表';

-- 11. 订单申诉表
CREATE TABLE IF NOT EXISTS `order_appeal` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    `order_id` BIGINT NOT NULL COMMENT '订单ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `appeal_type` TINYINT DEFAULT 1 COMMENT '申诉类型：1费用异常 2还车异常 3其他',
    `content` VARCHAR(500) NOT NULL COMMENT '申诉内容',
    `images` VARCHAR(1000) COMMENT '申诉图片',
    `status` TINYINT DEFAULT 0 COMMENT '状态：0待处理 1处理中 2已完成 3已拒绝',
    `handler_id` BIGINT COMMENT '处理人ID',
    `handle_time` DATETIME COMMENT '处理时间',
    `handle_result` VARCHAR(500) COMMENT '处理结果',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_order` (`order_id`),
    INDEX `idx_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单申诉表';

-- 12. 系统配置表
CREATE TABLE IF NOT EXISTS `system_config` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    `config_key` VARCHAR(100) NOT NULL UNIQUE COMMENT '配置键',
    `config_value` VARCHAR(500) COMMENT '配置值',
    `description` VARCHAR(255) COMMENT '配置描述',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置表';

-- 13. 操作日志表
CREATE TABLE IF NOT EXISTS `operation_log` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    `admin_id` BIGINT COMMENT '操作人ID',
    `admin_name` VARCHAR(50) COMMENT '操作人名称',
    `module` VARCHAR(50) COMMENT '操作模块',
    `operation` VARCHAR(100) COMMENT '操作内容',
    `method` VARCHAR(200) COMMENT '请求方法',
    `params` TEXT COMMENT '请求参数',
    `ip` VARCHAR(50) COMMENT 'IP地址',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX `idx_admin` (`admin_id`),
    INDEX `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';
