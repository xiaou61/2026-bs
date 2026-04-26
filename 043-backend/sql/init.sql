-- 宠物寄养服务系统数据库
CREATE DATABASE IF NOT EXISTS pet_boarding DEFAULT CHARSET utf8mb4;
USE pet_boarding;

-- 用户表
CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    nickname VARCHAR(50),
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20) UNIQUE,
    email VARCHAR(100),
    avatar VARCHAR(255),
    role VARCHAR(20) DEFAULT 'USER' COMMENT 'USER/PROVIDER/ADMIN',
    status TINYINT DEFAULT 1,
    deleted TINYINT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 宠物表
CREATE TABLE IF NOT EXISTS pet (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    name VARCHAR(50) NOT NULL,
    type VARCHAR(20) NOT NULL COMMENT 'DOG/CAT/OTHER',
    breed VARCHAR(50),
    age INT,
    gender VARCHAR(10),
    weight DECIMAL(5,2),
    health_status VARCHAR(500),
    vaccination VARCHAR(500),
    remark VARCHAR(500),
    avatar VARCHAR(255),
    deleted TINYINT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 服务商表
CREATE TABLE IF NOT EXISTS provider (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    business_hours VARCHAR(100),
    description TEXT,
    images TEXT COMMENT 'JSON数组',
    longitude DECIMAL(10,7),
    latitude DECIMAL(10,7),
    status VARCHAR(20) DEFAULT 'PENDING' COMMENT 'PENDING/APPROVED/REJECTED',
    rating DECIMAL(3,2) DEFAULT 5.00,
    order_count INT DEFAULT 0,
    deleted TINYINT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 服务项目表
CREATE TABLE IF NOT EXISTS provider_service (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    provider_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL COMMENT '每日价格',
    pet_types VARCHAR(100) COMMENT '支持的宠物类型',
    description TEXT,
    deleted TINYINT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 预订表
CREATE TABLE IF NOT EXISTS booking (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    provider_id BIGINT NOT NULL,
    service_id BIGINT NOT NULL,
    pet_id BIGINT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    days INT NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING' COMMENT 'PENDING/CONFIRMED/IN_PROGRESS/COMPLETED/CANCELLED',
    remark TEXT,
    deleted TINYINT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 寄养记录表
CREATE TABLE IF NOT EXISTS boarding_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    booking_id BIGINT NOT NULL UNIQUE,
    check_in_time DATETIME,
    check_out_time DATETIME,
    health_notes TEXT,
    deleted TINYINT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 每日动态表
CREATE TABLE IF NOT EXISTS daily_update (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    record_id BIGINT NOT NULL,
    content TEXT,
    images TEXT,
    health_status VARCHAR(100),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 评价表
CREATE TABLE IF NOT EXISTS review (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    booking_id BIGINT NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    provider_id BIGINT NOT NULL,
    rating INT NOT NULL,
    content TEXT,
    images TEXT,
    reply TEXT,
    deleted TINYINT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 默认账号：admin/admin123，petuser/123456，provider/123456
INSERT INTO sys_user (id, username, nickname, password, phone, role, status) VALUES
(1, 'admin', '系统管理员', '$2a$10$2mwuqL8q/iAVXzjTNhraS.TamwA18iSAuRgzAYcZ4Gcdy.ag1Vcp2', '13800000000', 'ADMIN', 1),
(2, 'petuser', '寄养用户', '$2a$10$fCxQT3EZhPQ5pk/VCcQALOyAXBj9r7bJaZfQ4T7Asp8Qp3HVAX8lm', '13800000001', 'USER', 1),
(3, 'provider', '安心寄养师', '$2a$10$fCxQT3EZhPQ5pk/VCcQALOyAXBj9r7bJaZfQ4T7Asp8Qp3HVAX8lm', '13800000002', 'PROVIDER', 1);

INSERT INTO pet (id, user_id, name, type, breed, age, gender, weight, health_status, vaccination, remark) VALUES
(1, 2, '豆包', 'DOG', '柯基', 3, '公', 11.50, '健康，性格亲人', '已接种狂犬和六联', '不吃鸡肉');

INSERT INTO provider (id, user_id, name, address, phone, business_hours, description, images, longitude, latitude, status, rating, order_count) VALUES
(1, 3, '暖爪宠物寄养中心', '杭州市西湖区文三路 88 号', '13800000002', '09:00-21:00', '家庭式宠物寄养，支持猫狗分区、每日视频反馈。', '[]', 120.1234567, 30.1234567, 'APPROVED', 4.90, 28);

INSERT INTO provider_service (id, provider_id, name, price, pet_types, description) VALUES
(1, 1, '犬类标准寄养', 88.00, 'DOG', '每日两次遛狗，提供基础喂养和照片反馈'),
(2, 1, '猫咪安静寄养', 68.00, 'CAT', '独立猫房，定时铲砂，减少应激');
