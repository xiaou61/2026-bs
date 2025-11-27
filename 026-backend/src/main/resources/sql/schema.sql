-- 026 画师接稿平台数据库初始化脚本

CREATE DATABASE IF NOT EXISTS 026_artist_platform CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE 026_artist_platform;

-- 用户表
CREATE TABLE `user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` VARCHAR(50) NOT NULL COMMENT '用户名',
  `password` VARCHAR(255) NOT NULL COMMENT '密码',
  `email` VARCHAR(100) COMMENT '邮箱',
  `phone` VARCHAR(20) COMMENT '手机号',
  `avatar` VARCHAR(255) COMMENT '头像',
  `nickname` VARCHAR(50) COMMENT '昵称',
  `bio` TEXT COMMENT '个人简介',
  `role` VARCHAR(20) DEFAULT 'USER' COMMENT '角色 USER/ARTIST/ADMIN',
  `status` VARCHAR(20) DEFAULT 'ACTIVE' COMMENT '状态 ACTIVE/BANNED',
  `balance` DECIMAL(10,2) DEFAULT 0.00 COMMENT '余额',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 画师信息表
CREATE TABLE `artist` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '画师ID',
  `user_id` BIGINT NOT NULL COMMENT '关联用户ID',
  `real_name` VARCHAR(50) COMMENT '真实姓名',
  `id_card` VARCHAR(18) COMMENT '身份证号',
  `style` VARCHAR(255) COMMENT '擅长风格',
  `price_min` DECIMAL(10,2) COMMENT '最低价格',
  `price_max` DECIMAL(10,2) COMMENT '最高价格',
  `delivery_days` INT COMMENT '预计交稿天数',
  `accept_types` VARCHAR(255) COMMENT '接受类型(逗号分隔)',
  `status` VARCHAR(20) DEFAULT 'PENDING' COMMENT '认证状态 PENDING/APPROVED/REJECTED',
  `rating` DECIMAL(3,2) DEFAULT 5.00 COMMENT '评分',
  `order_count` INT DEFAULT 0 COMMENT '完成订单数',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='画师信息表';

-- 作品集表
CREATE TABLE `portfolio` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '作品ID',
  `artist_id` BIGINT NOT NULL COMMENT '画师ID',
  `title` VARCHAR(100) NOT NULL COMMENT '作品标题',
  `description` TEXT COMMENT '作品描述',
  `image_url` VARCHAR(255) NOT NULL COMMENT '作品图片URL',
  `category` VARCHAR(50) COMMENT '作品分类',
  `tags` VARCHAR(255) COMMENT '标签(逗号分隔)',
  `is_featured` TINYINT(1) DEFAULT 0 COMMENT '是否精选',
  `view_count` INT DEFAULT 0 COMMENT '浏览次数',
  `like_count` INT DEFAULT 0 COMMENT '点赞数',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_artist_id` (`artist_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='作品集表';

-- 需求/委托表
CREATE TABLE `demand` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '需求ID',
  `user_id` BIGINT NOT NULL COMMENT '发布用户ID',
  `title` VARCHAR(100) NOT NULL COMMENT '需求标题',
  `description` TEXT NOT NULL COMMENT '需求描述',
  `type` VARCHAR(50) COMMENT '作品类型',
  `size` VARCHAR(50) COMMENT '尺寸规格',
  `style` VARCHAR(100) COMMENT '风格要求',
  `budget_min` DECIMAL(10,2) COMMENT '预算下限',
  `budget_max` DECIMAL(10,2) COMMENT '预算上限',
  `deadline` DATE COMMENT '截止日期',
  `ref_images` TEXT COMMENT '参考图片URLs(逗号分隔)',
  `target_artist_id` BIGINT COMMENT '指定画师ID(空表示公开招标)',
  `status` VARCHAR(20) DEFAULT 'OPEN' COMMENT '状态 OPEN/BIDDING/ACCEPTED/CLOSED',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_target_artist` (`target_artist_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='需求委托表';

-- 订单表
CREATE TABLE `order` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `order_no` VARCHAR(32) NOT NULL COMMENT '订单编号',
  `demand_id` BIGINT NOT NULL COMMENT '需求ID',
  `user_id` BIGINT NOT NULL COMMENT '客户ID',
  `artist_id` BIGINT NOT NULL COMMENT '画师ID',
  `title` VARCHAR(100) NOT NULL COMMENT '订单标题',
  `description` TEXT COMMENT '订单描述',
  `total_price` DECIMAL(10,2) NOT NULL COMMENT '总价',
  `deposit` DECIMAL(10,2) NOT NULL COMMENT '定金',
  `final_payment` DECIMAL(10,2) NOT NULL COMMENT '尾款',
  `status` VARCHAR(20) DEFAULT 'PENDING_DEPOSIT' COMMENT '状态',
  `draft_url` VARCHAR(255) COMMENT '草图URL',
  `final_url` VARCHAR(255) COMMENT '成品URL',
  `revise_count` INT DEFAULT 0 COMMENT '已修改次数',
  `max_revise` INT DEFAULT 2 COMMENT '最大修改次数',
  `commission_rate` DECIMAL(5,2) DEFAULT 8.00 COMMENT '平台抽成比例',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_artist_id` (`artist_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 支付记录表
CREATE TABLE `payment` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '支付ID',
  `order_id` BIGINT NOT NULL COMMENT '订单ID',
  `user_id` BIGINT NOT NULL COMMENT '支付用户ID',
  `amount` DECIMAL(10,2) NOT NULL COMMENT '支付金额',
  `type` VARCHAR(20) NOT NULL COMMENT '支付类型 DEPOSIT/FINAL_PAYMENT/REFUND',
  `status` VARCHAR(20) DEFAULT 'SUCCESS' COMMENT '支付状态',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付记录表';

-- 评价表
CREATE TABLE `review` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '评价ID',
  `order_id` BIGINT NOT NULL COMMENT '订单ID',
  `from_user_id` BIGINT NOT NULL COMMENT '评价者ID',
  `to_user_id` BIGINT NOT NULL COMMENT '被评价者ID',
  `rating` INT NOT NULL COMMENT '评分(1-5)',
  `quality_rating` INT COMMENT '质量评分',
  `attitude_rating` INT COMMENT '态度评分',
  `speed_rating` INT COMMENT '速度评分',
  `content` TEXT COMMENT '评价内容',
  `tags` VARCHAR(255) COMMENT '标签(逗号分隔)',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_to_user` (`to_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评价表';

-- 消息表
CREATE TABLE `message` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `user_id` BIGINT NOT NULL COMMENT '接收用户ID',
  `title` VARCHAR(100) NOT NULL COMMENT '消息标题',
  `content` TEXT NOT NULL COMMENT '消息内容',
  `type` VARCHAR(20) DEFAULT 'SYSTEM' COMMENT '消息类型 SYSTEM/ORDER',
  `related_id` BIGINT COMMENT '关联ID(如订单ID)',
  `is_read` TINYINT(1) DEFAULT 0 COMMENT '是否已读',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息表';

-- 提现记录表
CREATE TABLE `withdraw` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '提现ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `amount` DECIMAL(10,2) NOT NULL COMMENT '提现金额',
  `account_type` VARCHAR(20) COMMENT '账户类型',
  `account_no` VARCHAR(100) COMMENT '账户号',
  `status` VARCHAR(20) DEFAULT 'PENDING' COMMENT '状态 PENDING/APPROVED/REJECTED',
  `remark` VARCHAR(255) COMMENT '备注',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='提现记录表';

-- 插入初始管理员账号
INSERT INTO `user` (`username`, `password`, `email`, `nickname`, `role`) 
VALUES ('admin', '123456', 'admin@artist.com', '管理员', 'ADMIN');
