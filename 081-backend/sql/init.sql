DROP DATABASE IF EXISTS repair_db;
CREATE DATABASE repair_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE repair_db;

CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    real_name VARCHAR(50),
    phone VARCHAR(20),
    email VARCHAR(100),
    role VARCHAR(20) NOT NULL,
    avatar VARCHAR(255),
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE appliance_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    brand VARCHAR(100),
    sort INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE technician (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    name VARCHAR(50) NOT NULL,
    phone VARCHAR(20),
    skill_tags VARCHAR(255),
    service_area VARCHAR(255),
    level VARCHAR(20),
    work_status INT DEFAULT 1,
    rating DECIMAL(3,2) DEFAULT 5.00,
    order_count INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE repair_order (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_no VARCHAR(32) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    contact_name VARCHAR(50) NOT NULL,
    contact_phone VARCHAR(20) NOT NULL,
    province VARCHAR(50),
    city VARCHAR(50),
    district VARCHAR(50),
    address VARCHAR(255) NOT NULL,
    category_id BIGINT,
    brand VARCHAR(100),
    model VARCHAR(100),
    fault_desc TEXT,
    images TEXT,
    expect_time DATETIME,
    urgency INT DEFAULT 0,
    status INT DEFAULT 0,
    technician_id BIGINT,
    visit_time DATETIME,
    finish_time DATETIME,
    total_fee DECIMAL(10,2) DEFAULT 0,
    parts_fee DECIMAL(10,2) DEFAULT 0,
    labor_fee DECIMAL(10,2) DEFAULT 0,
    pay_status INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE repair_process (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    operator_id BIGINT,
    operator_role VARCHAR(20),
    node_type VARCHAR(30),
    content VARCHAR(500),
    images TEXT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE spare_part (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    part_code VARCHAR(50) NOT NULL UNIQUE,
    brand VARCHAR(100),
    specification VARCHAR(255),
    stock INT DEFAULT 0,
    unit_price DECIMAL(10,2) DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE notice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    type VARCHAR(30),
    status INT DEFAULT 0,
    publisher_id BIGINT,
    publish_time DATETIME,
    view_count INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE evaluate (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    technician_id BIGINT,
    score INT,
    attitude_score INT,
    quality_score INT,
    speed_score INT,
    content VARCHAR(500),
    images TEXT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO user (username, password, real_name, phone, email, role, status) VALUES
('admin', '123456', '系统管理员', '13800138000', 'admin@repair.com', 'admin', 1),
('tech1', '123456', '王师傅', '13800138001', 'tech1@repair.com', 'technician', 1),
('user1', '123456', '张三', '13800138002', 'user1@repair.com', 'customer', 1);

INSERT INTO appliance_category (name, brand, sort, status) VALUES
('空调', '格力', 1, 1),
('冰箱', '海尔', 2, 1),
('洗衣机', '小天鹅', 3, 1),
('热水器', '美的', 4, 1);

INSERT INTO technician (user_id, name, phone, skill_tags, service_area, level, work_status, rating, order_count) VALUES
(2, '王师傅', '13800138001', '空调,冰箱,洗衣机', '成都市高新区', '高级', 1, 4.90, 128),
(NULL, '李师傅', '13800138003', '热水器,燃气灶', '成都市锦江区', '中级', 1, 4.70, 83);

INSERT INTO spare_part (name, part_code, brand, specification, stock, unit_price, status) VALUES
('空调电容', 'PART-AC-001', '通用', '35UF', 120, 28.00, 1),
('冰箱压缩机', 'PART-FR-002', '海尔', 'R600a', 35, 560.00, 1),
('洗衣机排水泵', 'PART-WM-003', '小天鹅', '220V', 76, 95.00, 1);

INSERT INTO repair_order (order_no, user_id, contact_name, contact_phone, province, city, district, address, category_id, brand, model, fault_desc, urgency, status, technician_id, total_fee, parts_fee, labor_fee, pay_status) VALUES
('RO202603040001', 3, '张三', '13800138002', '四川省', '成都市', '高新区', '天府三街XX号', 1, '格力', 'KFR-35GW', '空调不制冷，运行有异响', 1, 2, 1, 280.00, 80.00, 200.00, 0),
('RO202603040002', 3, '张三', '13800138002', '四川省', '成都市', '高新区', '天府三街XX号', 2, '海尔', 'BCD-218', '冰箱冷藏室不制冷', 0, 0, NULL, 0.00, 0.00, 0.00, 0);

INSERT INTO repair_process (order_id, operator_id, operator_role, node_type, content) VALUES
(1, 3, 'customer', 'create', '用户提交报修工单'),
(1, 1, 'admin', 'assign', '平台已派单给王师傅'),
(1, 1, 'technician', 'status', '技师已上门完成检测，准备更换电容');

INSERT INTO notice (title, content, type, status, publisher_id, publish_time, view_count) VALUES
('春季空调维保优惠活动', '3月到4月空调清洗享8折，欢迎预约上门服务。', 'activity', 1, 1, NOW(), 36),
('报修服务时间调整通知', '工作日上门时间调整为8:30-20:00，周末不变。', 'system', 1, 1, NOW(), 52);

INSERT INTO evaluate (order_id, user_id, technician_id, score, attitude_score, quality_score, speed_score, content) VALUES
(1, 3, 1, 5, 5, 5, 4, '师傅上门准时，处理专业，态度很好。');
