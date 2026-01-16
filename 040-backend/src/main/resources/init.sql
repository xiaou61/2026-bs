CREATE DATABASE IF NOT EXISTS parking_management DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE parking_management;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(11) NOT NULL UNIQUE,
    real_name VARCHAR(50),
    room_number VARCHAR(50),
    role VARCHAR(20) DEFAULT 'USER',
    status VARCHAR(20) DEFAULT 'ACTIVE',
    points INT DEFAULT 100,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_username (username),
    INDEX idx_phone (phone)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE vehicles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    plate_number VARCHAR(20) NOT NULL UNIQUE,
    vehicle_type VARCHAR(50),
    vehicle_color VARCHAR(50),
    vehicle_photo VARCHAR(500),
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_user_id (user_id),
    INDEX idx_plate_number (plate_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE violation_types (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    penalty_points INT DEFAULT 20,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE reports (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    report_no VARCHAR(50) NOT NULL UNIQUE,
    reporter_id BIGINT NOT NULL,
    vehicle_id BIGINT,
    plate_number VARCHAR(20),
    violation_type_id BIGINT NOT NULL,
    location VARCHAR(500),
    longitude DECIMAL(10, 6),
    latitude DECIMAL(10, 6),
    description VARCHAR(1000),
    images VARCHAR(1000),
    is_anonymous BOOLEAN DEFAULT FALSE,
    status VARCHAR(20) DEFAULT 'PENDING',
    audit_user_id BIGINT,
    audit_time DATETIME,
    audit_reason VARCHAR(500),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_reporter_id (reporter_id),
    INDEX idx_vehicle_id (vehicle_id),
    INDEX idx_status (status),
    INDEX idx_report_no (report_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE report_handlers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    report_id BIGINT NOT NULL,
    handler_id BIGINT NOT NULL,
    handler_name VARCHAR(50),
    contact_result VARCHAR(500),
    handle_description VARCHAR(1000),
    handle_images VARCHAR(1000),
    handle_status VARCHAR(20) DEFAULT 'PENDING',
    handle_time DATETIME,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_report_id (report_id),
    INDEX idx_handler_id (handler_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE appeals (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    report_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    appeal_reason VARCHAR(100),
    appeal_description VARCHAR(1000),
    appeal_images VARCHAR(1000),
    status VARCHAR(20) DEFAULT 'PENDING',
    audit_user_id BIGINT,
    audit_time DATETIME,
    audit_result VARCHAR(500),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_report_id (report_id),
    INDEX idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE parking_spaces (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    space_no VARCHAR(50) NOT NULL UNIQUE,
    space_type VARCHAR(50),
    building VARCHAR(50),
    floor VARCHAR(50),
    owner_id BIGINT,
    status VARCHAR(20) DEFAULT 'AVAILABLE',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_space_no (space_no),
    INDEX idx_owner_id (owner_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE points_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    points INT NOT NULL,
    type VARCHAR(50),
    reason VARCHAR(500),
    related_id BIGINT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE notifications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    type VARCHAR(50),
    title VARCHAR(200),
    content VARCHAR(1000),
    is_read BOOLEAN DEFAULT FALSE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_user_id (user_id),
    INDEX idx_is_read (is_read)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE announcements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    author_id BIGINT NOT NULL,
    is_top BOOLEAN DEFAULT FALSE,
    views INT DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_author_id (author_id),
    INDEX idx_is_top (is_top)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE point_exchanges (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    points_cost INT NOT NULL,
    exchange_type VARCHAR(50),
    exchange_item VARCHAR(200),
    status VARCHAR(20) DEFAULT 'COMPLETED',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE system_config (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    config_key VARCHAR(100) NOT NULL UNIQUE,
    config_value VARCHAR(500),
    description VARCHAR(500),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_config_key (config_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE operation_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    operation VARCHAR(200),
    module VARCHAR(100),
    ip VARCHAR(50),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_user_id (user_id),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO users (username, password, phone, real_name, role, status, points) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '13800138000', '系统管理员', 'ADMIN', 'ACTIVE', 1000),
('user1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '13800138001', '张三', 'USER', 'ACTIVE', 100),
('manager1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '13800138002', '李四', 'MANAGER', 'ACTIVE', 200);

INSERT INTO violation_types (name, description, penalty_points) VALUES
('占用消防通道', '车辆停放在消防通道上，影响消防安全', 30),
('占用他人车位', '车辆停放在他人专属车位上', 20),
('占用盲道', '车辆停放在盲道上，影响行人通行', 25),
('堵塞出入口', '车辆停放在小区或楼栋出入口，堵塞通行', 30),
('占用绿化带', '车辆停放在绿化带上，破坏环境', 15),
('其他违停', '其他类型的违规停车行为', 10);

INSERT INTO parking_spaces (space_no, space_type, building, floor, status) VALUES
('A101', '固定车位', 'A栋', '地下1层', 'AVAILABLE'),
('A102', '固定车位', 'A栋', '地下1层', 'OCCUPIED'),
('B201', '临时车位', 'B栋', '地下2层', 'AVAILABLE'),
('C301', '充电车位', 'C栋', '地下1层', 'AVAILABLE');

INSERT INTO system_config (config_key, config_value, description) VALUES
('report_reward_points', '10', '举报奖励积分'),
('violation_penalty_points', '20', '违停扣除积分'),
('appeal_success_restore_points', '20', '申诉成功恢复积分'),
('point_exchange_parking_ticket', '50', '兑换停车券所需积分'),
('point_exchange_property_fee', '100', '物业费抵扣所需积分');
