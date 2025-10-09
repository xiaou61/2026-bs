CREATE DATABASE IF NOT EXISTS farm_platform DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE farm_platform;

DROP TABLE IF EXISTS `comment`;
DROP TABLE IF EXISTS `order_item`;
DROP TABLE IF EXISTS `order`;
DROP TABLE IF EXISTS `product`;
DROP TABLE IF EXISTS `category`;
DROP TABLE IF EXISTS `notice`;
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` VARCHAR(50) NOT NULL COMMENT '用户名',
  `password` VARCHAR(100) NOT NULL COMMENT '密码',
  `role` VARCHAR(20) NOT NULL COMMENT '角色: farmer/buyer/admin',
  `real_name` VARCHAR(50) DEFAULT NULL COMMENT '真实姓名',
  `phone` VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
  `address` VARCHAR(255) DEFAULT NULL COMMENT '地址',
  `status` TINYINT DEFAULT 1 COMMENT '状态: 0禁用 1启用',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除: 0未删除 1已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

CREATE TABLE `category` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `name` VARCHAR(50) NOT NULL COMMENT '分类名称',
  `parent_id` BIGINT DEFAULT 0 COMMENT '父级分类ID, 0为顶级分类',
  `sort_order` INT DEFAULT 0 COMMENT '排序序号',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除: 0未删除 1已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='农产品分类表';

CREATE TABLE `product` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '产品ID',
  `name` VARCHAR(100) NOT NULL COMMENT '产品名称',
  `category_id` BIGINT NOT NULL COMMENT '分类ID',
  `price` DECIMAL(10,2) NOT NULL COMMENT '单价',
  `stock` INT NOT NULL DEFAULT 0 COMMENT '库存数量',
  `cover_url` VARCHAR(255) DEFAULT NULL COMMENT '产品封面图片URL',
  `description` TEXT COMMENT '产品描述',
  `farmer_id` BIGINT NOT NULL COMMENT '发布者(农户)ID',
  `status` VARCHAR(20) DEFAULT 'pending' COMMENT '审核状态: pending/approved/rejected',
  `region` VARCHAR(50) DEFAULT NULL COMMENT '产地',
  `unit` VARCHAR(10) DEFAULT '斤' COMMENT '单位',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除: 0未删除 1已删除',
  PRIMARY KEY (`id`),
  KEY `idx_farmer_id` (`farmer_id`),
  KEY `idx_category_id` (`category_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='农产品表';

CREATE TABLE `order` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `order_no` VARCHAR(50) NOT NULL COMMENT '订单号',
  `buyer_id` BIGINT NOT NULL COMMENT '买家ID',
  `total_price` DECIMAL(10,2) NOT NULL COMMENT '订单总金额',
  `status` VARCHAR(20) DEFAULT 'pending' COMMENT '订单状态: pending/paid/shipped/completed/cancelled',
  `shipping_address` VARCHAR(255) DEFAULT NULL COMMENT '收货地址',
  `receiver_name` VARCHAR(50) DEFAULT NULL COMMENT '收货人姓名',
  `receiver_phone` VARCHAR(20) DEFAULT NULL COMMENT '收货人电话',
  `remark` TEXT COMMENT '订单备注',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除: 0未删除 1已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `idx_buyer_id` (`buyer_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表';

CREATE TABLE `order_item` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '订单项ID',
  `order_id` BIGINT NOT NULL COMMENT '订单ID',
  `product_id` BIGINT NOT NULL COMMENT '商品ID',
  `product_name` VARCHAR(100) NOT NULL COMMENT '商品名称',
  `product_cover` VARCHAR(255) DEFAULT NULL COMMENT '商品图片',
  `price` DECIMAL(10,2) NOT NULL COMMENT '购买时单价',
  `quantity` INT NOT NULL COMMENT '购买数量',
  `farmer_id` BIGINT NOT NULL COMMENT '农户ID',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除: 0未删除 1已删除',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单项表';

CREATE TABLE `comment` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `product_id` BIGINT NOT NULL COMMENT '商品ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `order_id` BIGINT DEFAULT NULL COMMENT '订单ID',
  `rating` TINYINT NOT NULL COMMENT '评分: 1-5',
  `content` TEXT COMMENT '评论内容',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除: 0未删除 1已删除',
  PRIMARY KEY (`id`),
  KEY `idx_product_id` (`product_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='评论表';

CREATE TABLE `notice` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `title` VARCHAR(200) NOT NULL COMMENT '公告标题',
  `content` TEXT COMMENT '公告内容',
  `type` VARCHAR(20) DEFAULT 'news' COMMENT '类型: news/policy/announcement',
  `status` TINYINT DEFAULT 1 COMMENT '状态: 0隐藏 1发布',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除: 0未删除 1已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='公告表';

INSERT INTO `user` (`username`, `password`, `role`, `real_name`, `phone`, `address`, `status`) VALUES
('admin', '0192023a7bbd73250516f069df18b500', 'admin', '系统管理员', '13800138000', '北京市', 1),
('farmer1', '0192023a7bbd73250516f069df18b500', 'farmer', '张三', '13800138001', '山东省寿光市', 1),
('farmer2', '0192023a7bbd73250516f069df18b500', 'farmer', '李四', '13800138002', '河南省周口市', 1),
('buyer1', '0192023a7bbd73250516f069df18b500', 'buyer', '王五', '13800138003', '上海市浦东新区', 1),
('buyer2', '0192023a7bbd73250516f069df18b500', 'buyer', '赵六', '13800138004', '广州市天河区', 1);

INSERT INTO `category` (`name`, `parent_id`, `sort_order`) VALUES
('蔬菜', 0, 1),
('水果', 0, 2),
('粮食', 0, 3),
('禽蛋', 0, 4),
('玉米', 3, 1),
('大米', 3, 2),
('苹果', 2, 1),
('橙子', 2, 2),
('白菜', 1, 1),
('西红柿', 1, 2),
('鸡蛋', 4, 1),
('土鸡', 4, 2);

INSERT INTO `product` (`name`, `category_id`, `price`, `stock`, `cover_url`, `description`, `farmer_id`, `status`, `region`, `unit`) VALUES
('有机西红柿', 10, 5.50, 1000, '/images/products/tomato.jpg', '新鲜采摘的有机西红柿，无农药无催熟', 2, 'approved', '山东寿光', '斤'),
('新鲜大白菜', 9, 2.00, 2000, '/images/products/cabbage.jpg', '绿色无公害大白菜，口感鲜嫩', 2, 'approved', '山东寿光', '斤'),
('烟台红富士苹果', 7, 8.80, 500, '/images/products/apple.jpg', '烟台红富士苹果，甜脆多汁', 3, 'approved', '山东烟台', '斤'),
('东北大米', 6, 4.50, 3000, '/images/products/rice.jpg', '东北优质大米，粒粒饱满', 3, 'approved', '黑龙江', '斤'),
('农家散养鸡蛋', 11, 1.50, 5000, '/images/products/egg.jpg', '散养土鸡蛋，营养丰富', 2, 'approved', '山东', '个'),
('新鲜脐橙', 8, 6.00, 800, '/images/products/orange.jpg', '江西赣南脐橙，酸甜适中', 3, 'pending', '江西赣州', '斤');

INSERT INTO `notice` (`title`, `content`, `type`, `status`) VALUES
('平台上线公告', '助农精准扶贫平台正式上线！欢迎广大农户和消费者使用本平台进行农产品交易。', 'announcement', 1),
('农产品质量承诺', '平台所有农产品均经过严格审核，确保产地真实、质量可靠。消费者可放心购买。', 'policy', 1),
('扶贫政策解读', '国家大力推进乡村振兴与精准扶贫政策，农产品上行成为重要抓手。本平台致力于打通农户与消费者的直接交易通道。', 'news', 1),
('交易流程说明', '消费者下单后，农户会在24小时内发货。如有问题请及时联系客服。', 'announcement', 1);

