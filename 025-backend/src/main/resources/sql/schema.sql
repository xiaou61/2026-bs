CREATE DATABASE IF NOT EXISTS `025_community` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `025_community`;

-- 1. 用户表 (User)
CREATE TABLE IF NOT EXISTS `user` (
    `id` INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    `username` VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    `password` VARCHAR(100) NOT NULL COMMENT '密码',
    `role` VARCHAR(20) NOT NULL DEFAULT 'OWNER' COMMENT '角色: ADMIN, OWNER',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) COMMENT '用户表';

-- 2. 业主表 (Owner)
CREATE TABLE IF NOT EXISTS `owner` (
    `id` INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    `user_id` INT NOT NULL COMMENT '关联用户ID',
    `name` VARCHAR(50) NOT NULL COMMENT '姓名',
    `phone` VARCHAR(20) NOT NULL COMMENT '联系电话',
    `building` VARCHAR(20) COMMENT '楼栋号',
    `unit` VARCHAR(20) COMMENT '单元号',
    `room` VARCHAR(20) COMMENT '房号',
    `check_in_time` DATE COMMENT '入住时间',
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`)
) COMMENT '业主表';

-- 3. 费用表 (Fee)
CREATE TABLE IF NOT EXISTS `fee` (
    `id` INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    `owner_id` INT NOT NULL COMMENT '业主ID',
    `amount` DECIMAL(10, 2) NOT NULL COMMENT '金额',
    `type` VARCHAR(50) NOT NULL COMMENT '费用类型: PROPERTY(物业费), PARKING(停车费)',
    `status` VARCHAR(20) DEFAULT 'UNPAID' COMMENT '状态: UNPAID, PAID',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `pay_time` DATETIME COMMENT '支付时间',
    FOREIGN KEY (`owner_id`) REFERENCES `owner`(`id`)
) COMMENT '费用表';

-- 4. 报修表 (Repair)
CREATE TABLE IF NOT EXISTS `repair` (
    `id` INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    `owner_id` INT NOT NULL COMMENT '业主ID',
    `content` TEXT NOT NULL COMMENT '报修内容',
    `image` VARCHAR(255) COMMENT '图片URL',
    `status` VARCHAR(20) DEFAULT 'PENDING' COMMENT '状态: PENDING(待处理), PROCESSING(处理中), COMPLETED(已完成)',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '提交时间',
    `finish_time` DATETIME COMMENT '完成时间',
    FOREIGN KEY (`owner_id`) REFERENCES `owner`(`id`)
) COMMENT '报修表';

-- 5. 公告表 (Notice)
CREATE TABLE IF NOT EXISTS `notice` (
    `id` INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    `title` VARCHAR(100) NOT NULL COMMENT '标题',
    `content` TEXT NOT NULL COMMENT '内容',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间'
) COMMENT '公告表';

-- 6. 访客表 (Visitor)
CREATE TABLE IF NOT EXISTS `visitor` (
    `id` INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    `owner_id` INT NOT NULL COMMENT '关联业主ID',
    `visitor_name` VARCHAR(50) NOT NULL COMMENT '访客姓名',
    `visit_time` DATETIME NOT NULL COMMENT '访问时间',
    `plate_number` VARCHAR(20) COMMENT '车牌号',
    `status` VARCHAR(20) DEFAULT 'PENDING' COMMENT '状态: PENDING, APPROVED, LEFT',
    FOREIGN KEY (`owner_id`) REFERENCES `owner`(`id`)
) COMMENT '访客表';

-- 7. 车位表 (Parking)
CREATE TABLE IF NOT EXISTS `parking` (
    `id` INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    `spot_number` VARCHAR(20) NOT NULL UNIQUE COMMENT '车位号',
    `status` VARCHAR(20) DEFAULT 'FREE' COMMENT '状态: FREE(空闲), SOLD(已售), RENTED(已租)',
    `owner_id` INT COMMENT '关联业主ID (如果已售/已租)',
    FOREIGN KEY (`owner_id`) REFERENCES `owner`(`id`)
) COMMENT '车位表';

-- 初始数据
INSERT INTO `user` (`username`, `password`, `role`) VALUES ('admin', '123456', 'ADMIN');
