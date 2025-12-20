-- 数据库：rice_harvest
CREATE DATABASE IF NOT EXISTS rice_harvest DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE rice_harvest;

CREATE TABLE IF NOT EXISTS user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(64) NOT NULL UNIQUE,
    password VARCHAR(128) NOT NULL,
    phone VARCHAR(32),
    nickname VARCHAR(64),
    role TINYINT NOT NULL DEFAULT 1 COMMENT '1农户 2机手 9管理员',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '0禁用 1正常',
    created_at DATETIME,
    updated_at DATETIME,
    deleted TINYINT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS farm_plot (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    area DECIMAL(10,2) NOT NULL,
    crop_variety VARCHAR(100),
    growth_stage VARCHAR(100),
    location VARCHAR(255),
    latitude DOUBLE,
    longitude DOUBLE,
    photos TEXT,
    remark VARCHAR(255),
    created_at DATETIME,
    updated_at DATETIME,
    deleted TINYINT DEFAULT 0,
    INDEX idx_plot_user (user_id)
);

CREATE TABLE IF NOT EXISTS machine (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    owner_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    model VARCHAR(100),
    width DECIMAL(10,2),
    power DECIMAL(10,2),
    price_per_hour DECIMAL(12,2),
    price_per_mu DECIMAL(12,2),
    service_radius_km INT,
    status TINYINT DEFAULT 1,
    remark VARCHAR(255),
    created_at DATETIME,
    updated_at DATETIME,
    deleted TINYINT DEFAULT 0,
    INDEX idx_machine_owner (owner_id)
);

CREATE TABLE IF NOT EXISTS booking (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    farmer_id BIGINT NOT NULL,
    machine_id BIGINT,
    plot_id BIGINT NOT NULL,
    expect_date DATE,
    time_window VARCHAR(50),
    area DECIMAL(10,2),
    status VARCHAR(20) DEFAULT 'PENDING',
    quote_amount DECIMAL(12,2),
    settle_amount DECIMAL(12,2),
    address VARCHAR(255),
    latitude DOUBLE,
    longitude DOUBLE,
    remark VARCHAR(255),
    created_at DATETIME,
    updated_at DATETIME,
    deleted TINYINT DEFAULT 0,
    INDEX idx_booking_farmer (farmer_id),
    INDEX idx_booking_plot (plot_id)
);

CREATE TABLE IF NOT EXISTS booking_status_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    booking_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL,
    note VARCHAR(255),
    created_at DATETIME,
    updated_at DATETIME,
    deleted TINYINT DEFAULT 0,
    INDEX idx_bsl_booking (booking_id)
);

CREATE TABLE IF NOT EXISTS dispatch_task (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    booking_id BIGINT NOT NULL,
    machine_id BIGINT,
    driver_id BIGINT,
    assigned_by BIGINT,
    status VARCHAR(20),
    created_at DATETIME,
    updated_at DATETIME,
    deleted TINYINT DEFAULT 0,
    INDEX idx_dispatch_booking (booking_id)
);

CREATE TABLE IF NOT EXISTS work_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    booking_id BIGINT NOT NULL,
    start_time DATETIME,
    end_time DATETIME,
    real_area DECIMAL(10,2),
    photos TEXT,
    note VARCHAR(255),
    created_at DATETIME,
    updated_at DATETIME,
    deleted TINYINT DEFAULT 0,
    INDEX idx_work_booking (booking_id)
);

CREATE TABLE IF NOT EXISTS review (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    booking_id BIGINT NOT NULL,
    rating TINYINT,
    content VARCHAR(500),
    created_at DATETIME,
    updated_at DATETIME,
    deleted TINYINT DEFAULT 0,
    INDEX idx_review_booking (booking_id)
);

CREATE TABLE IF NOT EXISTS notice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    created_at DATETIME,
    updated_at DATETIME,
    deleted TINYINT DEFAULT 0
);

-- 初始化管理员
INSERT INTO user (username, password, phone, nickname, role, status, created_at, updated_at, deleted)
VALUES ('admin', '$2a$10$wMxsQObRPI9MzxuxFazf2eG5ZpFbaXoxK2l2KnmY1qQJFwVdFhFWO', '13800000000', '管理员', 9, 1, NOW(), NOW(), 0)
ON DUPLICATE KEY UPDATE username = username;
-- 密码为 bcrypt("123456") 占位
