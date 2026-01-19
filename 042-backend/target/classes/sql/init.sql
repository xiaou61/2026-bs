-- 创建数据库
CREATE DATABASE IF NOT EXISTS house_rental DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE house_rental;

-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `username` VARCHAR(50) NOT NULL COMMENT '用户名',
    `password` VARCHAR(100) NOT NULL COMMENT '密码',
    `role` VARCHAR(20) NOT NULL DEFAULT 'tenant' COMMENT '角色：admin-管理员，landlord-房东，tenant-租客',
    `real_name` VARCHAR(50) DEFAULT NULL COMMENT '真实姓名',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
    `id_card` VARCHAR(20) DEFAULT NULL COMMENT '身份证号',
    `avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像地址',
    `balance` DECIMAL(10,2) DEFAULT 0.00 COMMENT '账户余额',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-正常',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 房源表
CREATE TABLE IF NOT EXISTS `house` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `landlord_id` BIGINT NOT NULL COMMENT '房东ID',
    `title` VARCHAR(100) NOT NULL COMMENT '房源标题',
    `description` TEXT COMMENT '详细描述',
    `province` VARCHAR(50) DEFAULT NULL COMMENT '省',
    `city` VARCHAR(50) DEFAULT NULL COMMENT '市',
    `district` VARCHAR(50) DEFAULT NULL COMMENT '区',
    `address` VARCHAR(200) DEFAULT NULL COMMENT '详细地址',
    `price` DECIMAL(10,2) NOT NULL COMMENT '月租金',
    `deposit` DECIMAL(10,2) DEFAULT NULL COMMENT '押金',
    `area` DECIMAL(10,2) DEFAULT NULL COMMENT '面积（平方米）',
    `room_type` VARCHAR(50) DEFAULT NULL COMMENT '户型',
    `floor` INT DEFAULT NULL COMMENT '楼层',
    `total_floor` INT DEFAULT NULL COMMENT '总楼层',
    `orientation` VARCHAR(20) DEFAULT NULL COMMENT '朝向',
    `decoration` VARCHAR(20) DEFAULT NULL COMMENT '装修：精装/简装/毛坯',
    `facilities` VARCHAR(500) DEFAULT NULL COMMENT '配套设施JSON',
    `images` TEXT COMMENT '图片列表JSON',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态：0-待审核，1-已上架，2-已下架，3-已出租',
    `view_count` INT DEFAULT 0 COMMENT '浏览次数',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (`id`),
    KEY `idx_landlord_id` (`landlord_id`),
    KEY `idx_status` (`status`),
    KEY `idx_city` (`city`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='房源表';

-- 房源收藏表
CREATE TABLE IF NOT EXISTS `house_favorite` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `house_id` BIGINT NOT NULL COMMENT '房源ID',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_house` (`user_id`, `house_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_house_id` (`house_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='房源收藏表';

-- 预约看房表
CREATE TABLE IF NOT EXISTS `appointment` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `house_id` BIGINT NOT NULL COMMENT '房源ID',
    `tenant_id` BIGINT NOT NULL COMMENT '租客ID',
    `landlord_id` BIGINT NOT NULL COMMENT '房东ID',
    `appointment_time` DATETIME NOT NULL COMMENT '预约时间',
    `contact_phone` VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态：0-待确认，1-已确认，2-已拒绝，3-已完成，4-已取消',
    `reject_reason` VARCHAR(200) DEFAULT NULL COMMENT '拒绝原因',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_tenant_id` (`tenant_id`),
    KEY `idx_landlord_id` (`landlord_id`),
    KEY `idx_house_id` (`house_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='预约看房表';

-- 租赁合同表
CREATE TABLE IF NOT EXISTS `contract` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `contract_no` VARCHAR(50) NOT NULL COMMENT '合同编号',
    `house_id` BIGINT NOT NULL COMMENT '房源ID',
    `landlord_id` BIGINT NOT NULL COMMENT '房东ID',
    `tenant_id` BIGINT NOT NULL COMMENT '租客ID',
    `start_date` DATE NOT NULL COMMENT '租期开始日期',
    `end_date` DATE NOT NULL COMMENT '租期结束日期',
    `monthly_rent` DECIMAL(10,2) NOT NULL COMMENT '月租金',
    `deposit` DECIMAL(10,2) DEFAULT NULL COMMENT '押金',
    `payment_day` INT DEFAULT 1 COMMENT '每月付款日',
    `payment_method` VARCHAR(20) DEFAULT '月付' COMMENT '付款方式',
    `terms` TEXT COMMENT '合同条款',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态：0-待签署，1-生效中，2-已到期，3-已终止',
    `sign_time` DATETIME DEFAULT NULL COMMENT '签署时间',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_contract_no` (`contract_no`),
    KEY `idx_tenant_id` (`tenant_id`),
    KEY `idx_landlord_id` (`landlord_id`),
    KEY `idx_house_id` (`house_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='租赁合同表';

-- 租金账单表
CREATE TABLE IF NOT EXISTS `bill` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `bill_no` VARCHAR(50) NOT NULL COMMENT '账单编号',
    `contract_id` BIGINT NOT NULL COMMENT '合同ID',
    `tenant_id` BIGINT NOT NULL COMMENT '租客ID',
    `landlord_id` BIGINT NOT NULL COMMENT '房东ID',
    `amount` DECIMAL(10,2) NOT NULL COMMENT '应付金额',
    `bill_month` VARCHAR(10) NOT NULL COMMENT '账单月份',
    `due_date` DATE NOT NULL COMMENT '应付日期',
    `paid_amount` DECIMAL(10,2) DEFAULT 0.00 COMMENT '已付金额',
    `paid_time` DATETIME DEFAULT NULL COMMENT '支付时间',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态：0-未支付，1-已支付，2-已逾期',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_bill_no` (`bill_no`),
    KEY `idx_contract_id` (`contract_id`),
    KEY `idx_tenant_id` (`tenant_id`),
    KEY `idx_landlord_id` (`landlord_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='租金账单表';

-- 报修表
CREATE TABLE IF NOT EXISTS `repair` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `house_id` BIGINT NOT NULL COMMENT '房源ID',
    `contract_id` BIGINT DEFAULT NULL COMMENT '合同ID',
    `tenant_id` BIGINT NOT NULL COMMENT '租客ID',
    `landlord_id` BIGINT NOT NULL COMMENT '房东ID',
    `type` VARCHAR(50) NOT NULL COMMENT '报修类型',
    `description` TEXT COMMENT '问题描述',
    `images` TEXT COMMENT '图片列表JSON',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态：0-待处理，1-处理中，2-已完成',
    `result` VARCHAR(500) DEFAULT NULL COMMENT '处理结果',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_tenant_id` (`tenant_id`),
    KEY `idx_landlord_id` (`landlord_id`),
    KEY `idx_house_id` (`house_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='报修表';

-- 评价表
CREATE TABLE IF NOT EXISTS `review` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `house_id` BIGINT NOT NULL COMMENT '房源ID',
    `contract_id` BIGINT DEFAULT NULL COMMENT '合同ID',
    `tenant_id` BIGINT NOT NULL COMMENT '租客ID',
    `landlord_id` BIGINT NOT NULL COMMENT '房东ID',
    `house_score` INT NOT NULL DEFAULT 5 COMMENT '房源评分1-5',
    `service_score` INT NOT NULL DEFAULT 5 COMMENT '服务评分1-5',
    `facility_score` INT NOT NULL DEFAULT 5 COMMENT '设施评分1-5',
    `content` TEXT COMMENT '评价内容',
    `images` TEXT COMMENT '评价图片JSON',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_house_id` (`house_id`),
    KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评价表';

-- 消息通知表
CREATE TABLE IF NOT EXISTS `message` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` BIGINT NOT NULL COMMENT '接收用户ID',
    `title` VARCHAR(100) NOT NULL COMMENT '消息标题',
    `content` TEXT COMMENT '消息内容',
    `type` VARCHAR(50) DEFAULT NULL COMMENT '消息类型',
    `is_read` TINYINT NOT NULL DEFAULT 0 COMMENT '是否已读：0-未读，1-已读',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_is_read` (`is_read`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息通知表';

-- 插入测试数据
-- 管理员账号 admin/admin123
INSERT INTO `user` (`username`, `password`, `role`, `real_name`, `phone`, `status`) VALUES 
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', 'admin', '系统管理员', '13800000000', 1);

-- 房东账号 landlord/123456
INSERT INTO `user` (`username`, `password`, `role`, `real_name`, `phone`, `status`) VALUES 
('landlord', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', 'landlord', '张三', '13800000001', 1);

-- 租客账号 tenant/123456
INSERT INTO `user` (`username`, `password`, `role`, `real_name`, `phone`, `balance`, `status`) VALUES 
('tenant', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', 'tenant', '李四', '13800000002', 10000.00, 1);

-- 测试房源数据
INSERT INTO `house` (`landlord_id`, `title`, `description`, `province`, `city`, `district`, `address`, `price`, `deposit`, `area`, `room_type`, `floor`, `total_floor`, `orientation`, `decoration`, `facilities`, `images`, `status`, `view_count`) VALUES 
(2, '精装修两居室 近地铁 拎包入住', '房屋位于市中心，交通便利，周边配套齐全，适合年轻白领。', '北京市', '北京市', '朝阳区', '朝阳门外大街1号', 5500.00, 5500.00, 75.00, '2室1厅1卫', 8, 18, '南', '精装', '["空调","冰箱","洗衣机","热水器","宽带"]', '["/uploads/house1_1.jpg","/uploads/house1_2.jpg"]', 1, 156),
(2, '温馨一居室 独立卫浴 采光好', '小户型精装修，适合单身人士，安静舒适。', '北京市', '北京市', '海淀区', '中关村大街2号', 3800.00, 3800.00, 45.00, '1室1厅1卫', 5, 12, '东南', '精装', '["空调","热水器","宽带"]', '["/uploads/house2_1.jpg"]', 1, 89),
(2, '整租三居室 适合家庭', '大户型三居室，空间宽敞，适合家庭居住。', '北京市', '北京市', '西城区', '西单北大街3号', 8500.00, 17000.00, 120.00, '3室2厅2卫', 15, 28, '南北通透', '精装', '["空调","冰箱","洗衣机","热水器","宽带","电视","微波炉"]', '["/uploads/house3_1.jpg","/uploads/house3_2.jpg","/uploads/house3_3.jpg"]', 1, 234);
