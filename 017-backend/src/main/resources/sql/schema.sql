-- 创建数据库
CREATE DATABASE IF NOT EXISTS campus_ordering DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE campus_ordering;

-- 用户表
CREATE TABLE `user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `student_id` VARCHAR(30) NOT NULL COMMENT '学号',
    `username` VARCHAR(50) NOT NULL COMMENT '用户名',
    `phone` VARCHAR(20) NOT NULL COMMENT '手机号',
    `password` VARCHAR(100) NOT NULL COMMENT '密码',
    `real_name` VARCHAR(50) NOT NULL COMMENT '真实姓名',
    `avatar` VARCHAR(255) COMMENT '头像',
    `department` VARCHAR(100) COMMENT '院系',
    `major` VARCHAR(100) COMMENT '专业',
    `dormitory` VARCHAR(100) COMMENT '宿舍',
    `balance` DECIMAL(10,2) DEFAULT 0.00 COMMENT '余额',
    `status` TINYINT DEFAULT 0 COMMENT '状态 0正常 1禁用',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_student_id` (`student_id`),
    UNIQUE KEY `uk_phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 商家表
CREATE TABLE `merchant` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `merchant_name` VARCHAR(100) NOT NULL COMMENT '商家名称',
    `license_no` VARCHAR(50) COMMENT '营业执照号',
    `contact_person` VARCHAR(50) NOT NULL COMMENT '联系人',
    `contact_phone` VARCHAR(20) NOT NULL COMMENT '联系电话',
    `password` VARCHAR(100) NOT NULL COMMENT '密码',
    `address` VARCHAR(200) COMMENT '商家地址',
    `location` VARCHAR(100) COMMENT '位置',
    `category` VARCHAR(50) COMMENT '经营类别',
    `image` VARCHAR(255) COMMENT '店铺图片',
    `description` TEXT COMMENT '店铺介绍',
    `notice` VARCHAR(500) COMMENT '店铺公告',
    `business_hours` VARCHAR(100) COMMENT '营业时间',
    `delivery_fee` DECIMAL(10,2) DEFAULT 0.00 COMMENT '配送费',
    `min_price` DECIMAL(10,2) DEFAULT 0.00 COMMENT '起送价',
    `avg_prepare_time` INT DEFAULT 30 COMMENT '平均出餐时间',
    `rating` DECIMAL(3,2) DEFAULT 5.00 COMMENT '评分',
    `total_sales` INT DEFAULT 0 COMMENT '总销量',
    `month_sales` INT DEFAULT 0 COMMENT '月销量',
    `status` TINYINT DEFAULT 0 COMMENT '状态 0待审核 1营业中 2打烊 3已禁用',
    `is_busy` TINYINT DEFAULT 0 COMMENT '是否繁忙 0否 1是',
    `audit_status` TINYINT DEFAULT 0 COMMENT '审核状态 0待审核 1已通过 2已拒绝',
    `audit_remark` VARCHAR(200) COMMENT '审核备注',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_contact_phone` (`contact_phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商家表';

-- 菜品分类表
CREATE TABLE `category` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `category_name` VARCHAR(50) NOT NULL COMMENT '分类名称',
    `parent_id` BIGINT DEFAULT 0 COMMENT '父分类ID',
    `sort` INT DEFAULT 0 COMMENT '排序',
    `icon` VARCHAR(255) COMMENT '图标',
    `status` TINYINT DEFAULT 0 COMMENT '状态 0正常 1禁用',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜品分类表';

-- 菜品表
CREATE TABLE `dish` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `merchant_id` BIGINT NOT NULL COMMENT '商家ID',
    `dish_name` VARCHAR(100) NOT NULL COMMENT '菜品名称',
    `category_id` BIGINT NOT NULL COMMENT '分类ID',
    `image` VARCHAR(255) COMMENT '菜品图片',
    `description` VARCHAR(500) COMMENT '菜品描述',
    `price` DECIMAL(10,2) NOT NULL COMMENT '现价',
    `original_price` DECIMAL(10,2) COMMENT '原价',
    `stock` INT DEFAULT 0 COMMENT '库存',
    `sales` INT DEFAULT 0 COMMENT '销量',
    `month_sales` INT DEFAULT 0 COMMENT '月销量',
    `rating` DECIMAL(3,2) DEFAULT 5.00 COMMENT '评分',
    `is_recommend` TINYINT DEFAULT 0 COMMENT '是否推荐 0否 1是',
    `status` TINYINT DEFAULT 0 COMMENT '状态 0下架 1上架',
    `specs` TEXT COMMENT '规格选项JSON',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_merchant_id` (`merchant_id`),
    KEY `idx_category_id` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜品表';

-- 订单表
CREATE TABLE `orders` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `order_no` VARCHAR(32) NOT NULL COMMENT '订单编号',
    `pickup_no` VARCHAR(20) COMMENT '取餐号',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `merchant_id` BIGINT NOT NULL COMMENT '商家ID',
    `merchant_name` VARCHAR(100) NOT NULL COMMENT '商家名称',
    `dish_amount` DECIMAL(10,2) NOT NULL COMMENT '菜品金额',
    `delivery_fee` DECIMAL(10,2) DEFAULT 0.00 COMMENT '配送费',
    `discount_amount` DECIMAL(10,2) DEFAULT 0.00 COMMENT '优惠金额',
    `total_amount` DECIMAL(10,2) NOT NULL COMMENT '订单总额',
    `pay_amount` DECIMAL(10,2) NOT NULL COMMENT '实付金额',
    `pickup_type` TINYINT DEFAULT 1 COMMENT '取餐方式 1堂食 2打包',
    `pickup_time` DATETIME COMMENT '预约取餐时间',
    `user_remark` VARCHAR(200) COMMENT '用户备注',
    `status` TINYINT DEFAULT 0 COMMENT '状态 0待支付 1待接单 2制作中 3待取餐 4已完成 5已取消 6已退款',
    `pay_time` DATETIME COMMENT '支付时间',
    `accept_time` DATETIME COMMENT '接单时间',
    `finish_time` DATETIME COMMENT '完成时间',
    `cancel_time` DATETIME COMMENT '取消时间',
    `cancel_reason` VARCHAR(200) COMMENT '取消原因',
    `is_refund` TINYINT DEFAULT 0 COMMENT '是否退款 0否 1是',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_order_no` (`order_no`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_merchant_id` (`merchant_id`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 订单明细表
CREATE TABLE `order_detail` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `order_id` BIGINT NOT NULL COMMENT '订单ID',
    `order_no` VARCHAR(32) NOT NULL COMMENT '订单编号',
    `dish_id` BIGINT NOT NULL COMMENT '菜品ID',
    `dish_name` VARCHAR(100) NOT NULL COMMENT '菜品名称',
    `dish_image` VARCHAR(255) COMMENT '菜品图片',
    `price` DECIMAL(10,2) NOT NULL COMMENT '单价',
    `quantity` INT NOT NULL COMMENT '数量',
    `amount` DECIMAL(10,2) NOT NULL COMMENT '小计',
    `specs` VARCHAR(200) COMMENT '规格',
    PRIMARY KEY (`id`),
    KEY `idx_order_id` (`order_id`),
    KEY `idx_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单明细表';

-- 评价表
CREATE TABLE `review` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `order_id` BIGINT NOT NULL COMMENT '订单ID',
    `order_no` VARCHAR(32) NOT NULL COMMENT '订单编号',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `merchant_id` BIGINT NOT NULL COMMENT '商家ID',
    `merchant_rating` INT NOT NULL COMMENT '商家评分',
    `dish_rating` INT NOT NULL COMMENT '菜品评分',
    `tags` VARCHAR(200) COMMENT '评价标签',
    `content` TEXT COMMENT '评价内容',
    `images` TEXT COMMENT '图片JSON',
    `is_anonymous` TINYINT DEFAULT 0 COMMENT '是否匿名 0否 1是',
    `merchant_reply` VARCHAR(500) COMMENT '商家回复',
    `reply_time` DATETIME COMMENT '回复时间',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_merchant_id` (`merchant_id`),
    KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评价表';

-- 收藏表
CREATE TABLE `favorite` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `target_type` TINYINT NOT NULL COMMENT '收藏类型 1商家 2菜品',
    `target_id` BIGINT NOT NULL COMMENT '目标ID',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_target` (`user_id`, `target_type`, `target_id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收藏表';

-- 购物车表
CREATE TABLE `cart` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `merchant_id` BIGINT NOT NULL COMMENT '商家ID',
    `dish_id` BIGINT NOT NULL COMMENT '菜品ID',
    `dish_name` VARCHAR(100) NOT NULL COMMENT '菜品名称',
    `dish_image` VARCHAR(255) COMMENT '菜品图片',
    `price` DECIMAL(10,2) NOT NULL COMMENT '价格',
    `quantity` INT NOT NULL COMMENT '数量',
    `specs` VARCHAR(200) COMMENT '规格',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_merchant_id` (`merchant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物车表';

-- 优惠券表
CREATE TABLE `coupon` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `coupon_name` VARCHAR(100) NOT NULL COMMENT '优惠券名称',
    `coupon_type` TINYINT NOT NULL COMMENT '类型 1满减 2折扣',
    `discount_amount` DECIMAL(10,2) COMMENT '优惠金额',
    `discount_rate` DECIMAL(3,2) COMMENT '折扣率',
    `min_amount` DECIMAL(10,2) DEFAULT 0.00 COMMENT '最低消费',
    `max_discount` DECIMAL(10,2) COMMENT '最高优惠',
    `total_quantity` INT NOT NULL COMMENT '发行总量',
    `received_quantity` INT DEFAULT 0 COMMENT '已领取数量',
    `merchant_id` BIGINT DEFAULT 0 COMMENT '商家ID 0为平台券',
    `valid_days` INT NOT NULL COMMENT '有效天数',
    `status` TINYINT DEFAULT 0 COMMENT '状态 0禁用 1启用',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='优惠券表';

-- 用户优惠券表
CREATE TABLE `user_coupon` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `coupon_id` BIGINT NOT NULL COMMENT '优惠券ID',
    `coupon_name` VARCHAR(100) NOT NULL COMMENT '优惠券名称',
    `coupon_type` TINYINT NOT NULL COMMENT '类型',
    `discount_amount` DECIMAL(10,2) COMMENT '优惠金额',
    `discount_rate` DECIMAL(3,2) COMMENT '折扣率',
    `min_amount` DECIMAL(10,2) COMMENT '最低消费',
    `merchant_id` BIGINT COMMENT '商家ID',
    `status` TINYINT DEFAULT 0 COMMENT '状态 0未使用 1已使用 2已过期',
    `expire_time` DATETIME COMMENT '过期时间',
    `use_time` DATETIME COMMENT '使用时间',
    `receive_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '领取时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户优惠券表';

-- 管理员表
CREATE TABLE `admin` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `username` VARCHAR(50) NOT NULL COMMENT '用户名',
    `password` VARCHAR(100) NOT NULL COMMENT '密码',
    `real_name` VARCHAR(50) NOT NULL COMMENT '真实姓名',
    `phone` VARCHAR(20) COMMENT '手机号',
    `role` TINYINT DEFAULT 2 COMMENT '角色 1超级管理员 2普通管理员',
    `status` TINYINT DEFAULT 0 COMMENT '状态 0正常 1禁用',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员表';

