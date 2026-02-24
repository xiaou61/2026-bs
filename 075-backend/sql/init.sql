DROP DATABASE IF EXISTS auto_repair_service;
CREATE DATABASE auto_repair_service DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE auto_repair_service;

CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50),
    avatar VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(100),
    role VARCHAR(20) DEFAULT 'USER',
    status INT DEFAULT 1,
    balance DECIMAL(10,2) DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE repair_category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    icon VARCHAR(255),
    sort INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE repair_service (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_id BIGINT,
    seller_id BIGINT,
    title VARCHAR(120) NOT NULL,
    service_name VARCHAR(80) NOT NULL,
    store_name VARCHAR(80),
    vehicle_type VARCHAR(40),
    price DECIMAL(10,2) NOT NULL,
    stock INT DEFAULT 0,
    cover VARCHAR(255),
    description TEXT,
    booking_type VARCHAR(30),
    view_count INT DEFAULT 0,
    booked_count INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_repair_service_category(category_id),
    INDEX idx_repair_service_seller(seller_id)
);

CREATE TABLE repair_appointment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_no VARCHAR(50) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    service_id BIGINT NOT NULL,
    seller_id BIGINT NOT NULL,
    quantity INT DEFAULT 1,
    total_price DECIMAL(10,2),
    status INT DEFAULT 0,
    remark VARCHAR(255),
    appointment_date DATE NOT NULL,
    appointment_time TIME NOT NULL,
    plate_no VARCHAR(20) NOT NULL,
    vehicle_model VARCHAR(80) NOT NULL,
    fault_desc VARCHAR(500),
    pay_time DATETIME,
    finish_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_repair_appointment_user(user_id),
    INDEX idx_repair_appointment_service(service_id)
);

CREATE TABLE review (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    service_id BIGINT NOT NULL,
    rating INT DEFAULT 5,
    content TEXT,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE favorite (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    service_id BIGINT NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_favorite_user_service(user_id, service_id)
);

CREATE TABLE complaint (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    target_user_id BIGINT,
    order_status_snapshot INT,
    type VARCHAR(30) DEFAULT 'ORDER',
    content TEXT,
    images VARCHAR(500),
    status INT DEFAULT 0,
    reply TEXT,
    reply_admin_id BIGINT,
    reply_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE announcement (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    content TEXT,
    admin_id BIGINT,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO user (username, password, nickname, phone, email, role, status, balance) VALUES
('admin', '123456', '平台管理员', '13800000001', 'admin@autorepair.com', 'ADMIN', 1, 99999.00),
('seller1', '123456', '极速养车中心', '13800000002', 'seller1@autorepair.com', 'USER', 1, 12600.00),
('seller2', '123456', '诚顺汽修门店', '13800000003', 'seller2@autorepair.com', 'USER', 1, 9800.00),
('buyer1', '123456', '张车主', '13800000004', 'buyer1@autorepair.com', 'USER', 1, 3600.00);

INSERT INTO repair_category (name, icon, sort, status) VALUES
('发动机养护', '/images/category/engine.png', 1, 1),
('底盘制动', '/images/category/chassis.png', 2, 1),
('空调电路', '/images/category/electrical.png', 3, 1),
('钣金喷漆', '/images/category/paint.png', 4, 1),
('轮胎保养', '/images/category/tire.png', 5, 1),
('新能源维保', '/images/category/ev.png', 6, 1);

INSERT INTO repair_service (category_id, seller_id, title, service_name, store_name, vehicle_type, price, stock, cover, description, booking_type, view_count, booked_count, status) VALUES
(1, 2, '发动机积碳深度清洗', '发动机积碳清洗', '极速养车中心', '燃油轿车/SUV', 399.00, 18, '/images/item/engine1.jpg', '包含节气门清洁、喷油嘴检测和燃烧室积碳处理。', '到店检测', 356, 42, 1),
(1, 2, '机油机滤基础保养', '小保养套餐', '极速养车中心', '全车型', 268.00, 32, '/images/item/engine2.jpg', '更换全合成机油与原厂机滤，含基础点检。', '到店保养', 428, 63, 1),
(2, 3, '刹车片更换与制动检测', '制动系统保养', '诚顺汽修门店', '轿车/SUV', 520.00, 14, '/images/item/brake1.jpg', '前后刹车系统检测，支持片盘磨损评估。', '到店检测', 291, 29, 1),
(2, 3, '底盘异响专项检修', '底盘检修', '诚顺汽修门店', '全车型', 460.00, 12, '/images/item/chassis1.jpg', '重点检查摆臂、球头、减震与连接件。', '到店检测', 207, 21, 1),
(3, 2, '汽车空调制冷修复', '空调系统维修', '极速养车中心', '全车型', 380.00, 20, '/images/item/ac1.jpg', '包含冷媒压力检测、管路检漏与压缩机工况检查。', '到店检测', 318, 34, 1),
(3, 3, '蓄电池与启动系统检测', '电路启动检测', '诚顺汽修门店', '燃油车', 180.00, 40, '/images/item/electrical1.jpg', '检测蓄电池健康度、发电机输出与启动机状态。', '到店快检', 266, 27, 1),
(4, 3, '轻微剐蹭钣金喷漆', '局部钣喷', '诚顺汽修门店', '全车型', 780.00, 10, '/images/item/paint1.jpg', '针对轻微剐蹭提供局部整形与同色喷漆。', '到店评估', 189, 16, 1),
(5, 2, '四轮定位与轮胎动平衡', '轮胎定位套餐', '极速养车中心', '轿车/SUV', 220.00, 35, '/images/item/tire1.jpg', '提高行驶稳定性，减少轮胎偏磨。', '到店保养', 374, 55, 1),
(6, 2, '新能源电池健康检测', '动力电池检测', '极速养车中心', '新能源车', 660.00, 8, '/images/item/ev1.jpg', '读取电池包数据，评估电芯一致性与衰减趋势。', '到店检测', 144, 11, 1),
(6, 3, '新能源汽车高压系统检查', '高压系统维保', '诚顺汽修门店', '新能源车', 880.00, 6, '/images/item/ev2.jpg', '检查高压线束、绝缘状态与冷却系统。', '到店检测', 98, 7, 1);

INSERT INTO repair_appointment (order_no, user_id, service_id, seller_id, quantity, total_price, status, remark, appointment_date, appointment_time, plate_no, vehicle_model, fault_desc, pay_time, finish_time, create_time) VALUES
('AR202602240001', 4, 1, 2, 1, 399.00, 3, '到店前电话联系', '2026-02-22', '10:30:00', '沪A12345', '大众朗逸 1.4T', '起步抖动，油耗偏高。', '2026-02-20 09:20:00', '2026-02-22 15:45:00', '2026-02-20 09:12:00'),
('AR202602240002', 4, 5, 2, 1, 380.00, 2, '希望上午处理', '2026-02-25', '09:30:00', '浙B8C321', '本田CR-V', '空调不制冷，开启后有异响。', '2026-02-24 11:05:00', NULL, '2026-02-24 10:58:00'),
('AR202602240003', 2, 3, 3, 1, 520.00, 1, '请先检测后报价', '2026-02-26', '14:00:00', '苏E66K99', '丰田凯美瑞', '刹车距离变长，低速有尖叫声。', '2026-02-24 14:10:00', NULL, '2026-02-24 14:02:00');

INSERT INTO review (order_id, user_id, service_id, rating, content, status, create_time) VALUES
(1, 4, 1, 5, '师傅检查很细致，处理后动力恢复明显，整体体验不错。', 1, '2026-02-22 18:10:00');

INSERT INTO favorite (user_id, service_id) VALUES
(4, 1),
(4, 5),
(4, 8),
(2, 9);

INSERT INTO complaint (order_id, user_id, target_user_id, type, content, images, status, reply, reply_admin_id, reply_time, create_time) VALUES
(2, 4, 2, 'ORDER', '预约时间已到店但等待时间较长，希望平台协调处理。', '', 1, '平台已与门店沟通并安排优先工位，后续将优化高峰预约调度。', 1, '2026-02-24 15:40:00', '2026-02-24 13:20:00');

INSERT INTO announcement (title, content, admin_id, status) VALUES
('春季保养服务上线', '即日起发动机清洗与空调检测组合套餐限时95折。', 1, 1),
('预约服务规范提醒', '请车主按预约时间提前10分钟到店，超时可能顺延。', 1, 1),
('新能源专项月活动', '新能源车主到店可享动力电池检测优惠价。', 1, 1);
