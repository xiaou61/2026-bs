CREATE DATABASE IF NOT EXISTS lost_found_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE lost_found_db;

CREATE TABLE IF NOT EXISTS `user` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `username` VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    `password` VARCHAR(100) NOT NULL COMMENT '密码',
    `real_name` VARCHAR(50) NOT NULL COMMENT '真实姓名',
    `student_id` VARCHAR(30) COMMENT '学号/工号',
    `phone` VARCHAR(20) COMMENT '手机号',
    `email` VARCHAR(100) COMMENT '邮箱',
    `role` VARCHAR(20) NOT NULL DEFAULT 'user' COMMENT '角色：user/admin',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1正常 0禁用',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_username (`username`),
    INDEX idx_student_id (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

CREATE TABLE IF NOT EXISTS `category` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(50) NOT NULL COMMENT '分类名称',
    `icon` VARCHAR(100) COMMENT '图标',
    `sort` INT DEFAULT 0 COMMENT '排序',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1启用 0禁用',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分类表';

CREATE TABLE IF NOT EXISTS `lost_item` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `user_id` BIGINT NOT NULL COMMENT '发布用户ID',
    `category_id` BIGINT NOT NULL COMMENT '分类ID',
    `title` VARCHAR(200) NOT NULL COMMENT '标题',
    `description` TEXT COMMENT '详细描述',
    `lost_time` DATETIME COMMENT '丢失时间',
    `lost_location` VARCHAR(200) COMMENT '丢失地点',
    `contact_name` VARCHAR(50) COMMENT '联系人',
    `contact_phone` VARCHAR(20) COMMENT '联系电话',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态：0寻找中 1已找到',
    `views` INT DEFAULT 0 COMMENT '浏览次数',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_user_id (`user_id`),
    INDEX idx_category_id (`category_id`),
    INDEX idx_status (`status`),
    INDEX idx_created_at (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='失物表';

CREATE TABLE IF NOT EXISTS `found_item` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `user_id` BIGINT NOT NULL COMMENT '发布用户ID',
    `category_id` BIGINT NOT NULL COMMENT '分类ID',
    `title` VARCHAR(200) NOT NULL COMMENT '标题',
    `description` TEXT COMMENT '详细描述',
    `found_time` DATETIME COMMENT '拾到时间',
    `found_location` VARCHAR(200) COMMENT '拾到地点',
    `contact_name` VARCHAR(50) COMMENT '联系人',
    `contact_phone` VARCHAR(20) COMMENT '联系电话',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态：0待认领 1已归还',
    `views` INT DEFAULT 0 COMMENT '浏览次数',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_user_id (`user_id`),
    INDEX idx_category_id (`category_id`),
    INDEX idx_status (`status`),
    INDEX idx_created_at (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='招领表';

CREATE TABLE IF NOT EXISTS `item_image` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `item_type` VARCHAR(10) NOT NULL COMMENT '物品类型：lost/found',
    `item_id` BIGINT NOT NULL COMMENT '物品ID',
    `image_url` VARCHAR(500) NOT NULL COMMENT '图片路径',
    `sort` INT DEFAULT 0 COMMENT '排序',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '上传时间',
    INDEX idx_item (`item_type`, `item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物品图片表';

CREATE TABLE IF NOT EXISTS `claim` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `item_type` VARCHAR(10) NOT NULL COMMENT '物品类型：lost/found',
    `item_id` BIGINT NOT NULL COMMENT '物品ID',
    `claimer_id` BIGINT NOT NULL COMMENT '认领人ID',
    `owner_id` BIGINT NOT NULL COMMENT '失主/拾物者ID',
    `verify_info` TEXT COMMENT '验证信息',
    `reason` TEXT COMMENT '认领说明',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态：0待审核 1通过 2拒绝',
    `reply` VARCHAR(500) COMMENT '审核回复',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_claimer_id (`claimer_id`),
    INDEX idx_owner_id (`owner_id`),
    INDEX idx_item (`item_type`, `item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='认领申请表';

CREATE TABLE IF NOT EXISTS `favorite` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `item_type` VARCHAR(10) NOT NULL COMMENT '物品类型：lost/found',
    `item_id` BIGINT NOT NULL COMMENT '物品ID',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
    UNIQUE KEY uk_user_item (`user_id`, `item_type`, `item_id`),
    INDEX idx_user_id (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收藏表';

CREATE TABLE IF NOT EXISTS `notification` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `user_id` BIGINT NOT NULL COMMENT '接收用户ID',
    `type` VARCHAR(20) NOT NULL COMMENT '类型：claim_apply/claim_result/system',
    `title` VARCHAR(200) NOT NULL COMMENT '标题',
    `content` TEXT COMMENT '内容',
    `link_type` VARCHAR(10) COMMENT '关联类型：lost/found/claim',
    `link_id` BIGINT COMMENT '关联ID',
    `is_read` TINYINT NOT NULL DEFAULT 0 COMMENT '是否已读：0未读 1已读',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_user_id (`user_id`),
    INDEX idx_is_read (`is_read`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='通知表';

INSERT INTO `user` (`username`, `password`, `real_name`, `student_id`, `phone`, `email`, `role`, `status`) VALUES
('admin', '21232f297a57a5a743894a0e4a801fc3', '管理员', 'A001', '13800000000', 'admin@school.com', 'admin', 1),
('user1', 'e10adc3949ba59abbe56e057f20f883e', '张三', '2024001', '13800000001', 'user1@school.com', 'user', 1),
('user2', 'e10adc3949ba59abbe56e057f20f883e', '李四', '2024002', '13800000002', 'user2@school.com', 'user', 1);

INSERT INTO `category` (`name`, `icon`, `sort`, `status`) VALUES
('证件类', 'id-card', 1, 1),
('电子产品', 'phone', 2, 1),
('书籍资料', 'book', 3, 1),
('钥匙类', 'key', 4, 1),
('卡类', 'credit-card', 5, 1),
('生活用品', 'shopping-bag', 6, 1),
('其他', 'question', 7, 1);

INSERT INTO `lost_item` (`user_id`, `category_id`, `title`, `description`, `lost_time`, `lost_location`, `contact_name`, `contact_phone`, `status`, `views`) VALUES
(2, 1, '丢失校园卡', '红色卡套，卡号202400101，上面有照片', '2025-10-08 14:30:00', '图书馆三楼阅览室', '张三', '13800000001', 0, 23),
(2, 2, '遗失黑色耳机', 'AirPods Pro，黑色充电盒', '2025-10-09 10:00:00', '第一教学楼201教室', '张三', '13800000001', 0, 15);

INSERT INTO `found_item` (`user_id`, `category_id`, `title`, `description`, `found_time`, `found_location`, `contact_name`, `contact_phone`, `status`, `views`) VALUES
(3, 3, '捡到高数教材', '同济版高等数学第七版，内有笔记', '2025-10-09 16:00:00', '操场看台', '李四', '13800000002', 0, 18),
(3, 4, '拾到钥匙一串', '三把钥匙，带蓝色钥匙扣', '2025-10-10 08:30:00', '食堂二楼', '李四', '13800000002', 0, 12);

INSERT INTO `item_image` (`item_type`, `item_id`, `image_url`, `sort`) VALUES
('lost', 1, '/images/sample1.jpg', 1),
('found', 1, '/images/sample2.jpg', 1);

