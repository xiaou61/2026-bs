DROP DATABASE IF EXISTS bike_system;
CREATE DATABASE bike_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE bike_system;

CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    real_name VARCHAR(50),
    avatar VARCHAR(255),
    role VARCHAR(20) NOT NULL DEFAULT 'user',
    credit_score INT NOT NULL DEFAULT 100,
    balance DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    deposit_paid TINYINT NOT NULL DEFAULT 0,
    status TINYINT NOT NULL DEFAULT 1,
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE station (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    longitude DECIMAL(10,6),
    latitude DECIMAL(10,6),
    capacity INT NOT NULL DEFAULT 50,
    current_count INT NOT NULL DEFAULT 0,
    status TINYINT NOT NULL DEFAULT 1,
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE bike (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    bike_no VARCHAR(50) NOT NULL UNIQUE,
    type TINYINT NOT NULL DEFAULT 1,
    status TINYINT NOT NULL DEFAULT 1,
    station_id BIGINT,
    battery_level INT DEFAULT 100,
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE ride_order (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_no VARCHAR(50) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    bike_id BIGINT NOT NULL,
    start_station_id BIGINT,
    end_station_id BIGINT,
    start_time DATETIME NOT NULL,
    end_time DATETIME,
    duration INT DEFAULT 0,
    distance DECIMAL(10,2) DEFAULT 0.00,
    amount DECIMAL(10,2) DEFAULT 0.00,
    status TINYINT NOT NULL DEFAULT 1,
    pay_status TINYINT NOT NULL DEFAULT 0,
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE pricing_rule (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    bike_type TINYINT NOT NULL,
    base_price DECIMAL(10,2) NOT NULL,
    base_duration INT NOT NULL DEFAULT 30,
    extra_price DECIMAL(10,2) NOT NULL,
    daily_cap DECIMAL(10,2) NOT NULL,
    status TINYINT NOT NULL DEFAULT 1,
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE wallet_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    type TINYINT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    balance_after DECIMAL(10,2) NOT NULL,
    description VARCHAR(255),
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE fault_report (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    bike_id BIGINT NOT NULL,
    type TINYINT NOT NULL DEFAULT 1,
    description VARCHAR(500),
    status TINYINT NOT NULL DEFAULT 0,
    handler_id BIGINT,
    handle_result VARCHAR(500),
    handle_time DATETIME,
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE credit_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    type TINYINT NOT NULL,
    score_change INT NOT NULL,
    score_after INT NOT NULL,
    description VARCHAR(255),
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE announcement (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    type TINYINT NOT NULL DEFAULT 1,
    status TINYINT NOT NULL DEFAULT 1,
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE feedback (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    type TINYINT NOT NULL DEFAULT 1,
    content VARCHAR(500) NOT NULL,
    reply VARCHAR(500),
    status TINYINT NOT NULL DEFAULT 0,
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO user (username, password, phone, real_name, role, credit_score, balance, deposit_paid, status) VALUES
('admin', '123456', '13800000001', '系统管理员', 'admin', 100, 10000.00, 0, 1),
('operator', '123456', '13800000002', '运维张三', 'operator', 100, 0.00, 0, 1),
('user', '123456', '13800000003', '用户李四', 'user', 100, 200.00, 1, 1),
('test', '123456', '13800000004', '测试王五', 'user', 100, 50.00, 0, 1);

INSERT INTO station (name, address, longitude, latitude, capacity, current_count, status) VALUES
('天安门站', '北京市东城区天安门广场', 116.397128, 39.916527, 60, 15, 1),
('王府井站', '北京市东城区王府井大街', 116.417480, 39.917840, 40, 12, 1),
('西单站', '北京市西城区西单北大街', 116.373340, 39.913900, 50, 18, 1),
('国贸站', '北京市朝阳区国贸桥', 116.461220, 39.908720, 45, 10, 1),
('中关村站', '北京市海淀区中关村大街', 116.316700, 39.981900, 55, 20, 1),
('三里屯站', '北京市朝阳区三里屯路', 116.454050, 39.933180, 35, 8, 1),
('望京站', '北京市朝阳区望京西路', 116.480200, 39.998100, 40, 14, 1),
('五道口站', '北京市海淀区五道口', 116.338000, 39.992600, 30, 11, 1),
('奥林匹克公园站', '北京市朝阳区奥林匹克公园', 116.395600, 40.003500, 50, 16, 1),
('后海站', '北京市西城区后海', 116.383900, 39.942300, 35, 9, 1),
('朝阳公园站', '北京市朝阳区朝阳公园', 116.473800, 39.944500, 45, 13, 1),
('颐和园站', '北京市海淀区颐和园', 116.275400, 39.999800, 40, 7, 2);

INSERT INTO bike (bike_no, type, status, station_id, battery_level) VALUES
('BK20240001', 1, 1, 1, 100),
('BK20240002', 1, 1, 1, 100),
('BK20240003', 2, 1, 1, 95),
('BK20240004', 1, 1, 2, 100),
('BK20240005', 2, 1, 2, 88),
('BK20240006', 1, 1, 3, 100),
('BK20240007', 1, 1, 3, 100),
('BK20240008', 2, 1, 3, 76),
('BK20240009', 1, 1, 4, 100),
('BK20240010', 2, 1, 4, 92),
('BK20240011', 1, 1, 5, 100),
('BK20240012', 1, 1, 5, 100),
('BK20240013', 2, 1, 5, 85),
('BK20240014', 1, 1, 6, 100),
('BK20240015', 2, 1, 6, 90),
('BK20240016', 1, 1, 7, 100),
('BK20240017', 1, 1, 7, 100),
('BK20240018', 2, 1, 8, 70),
('BK20240019', 1, 3, 9, 100),
('BK20240020', 2, 3, 10, 60),
('BK20240021', 1, 1, 9, 100),
('BK20240022', 1, 1, 10, 100),
('BK20240023', 2, 1, 11, 82),
('BK20240024', 1, 1, 11, 100),
('BK20240025', 1, 4, NULL, 0);

INSERT INTO pricing_rule (name, bike_type, base_price, base_duration, extra_price, daily_cap, status) VALUES
('普通单车标准计费', 1, 1.50, 30, 1.00, 20.00, 1),
('电动单车标准计费', 2, 2.25, 30, 1.50, 30.00, 1);

INSERT INTO ride_order (order_no, user_id, bike_id, start_station_id, end_station_id, start_time, end_time, duration, distance, amount, status, pay_status) VALUES
('RD20240101001', 3, 1, 1, 2, '2024-12-01 08:30:00', '2024-12-01 09:00:00', 30, 3.50, 1.50, 3, 1),
('RD20240101002', 3, 5, 2, 3, '2024-12-01 14:00:00', '2024-12-01 15:10:00', 70, 5.20, 3.50, 3, 1),
('RD20240102001', 3, 11, 5, 8, '2024-12-02 09:00:00', '2024-12-02 09:25:00', 25, 2.80, 1.50, 3, 1),
('RD20240103001', 4, 6, 3, 1, '2024-12-03 10:00:00', '2024-12-03 10:45:00', 45, 4.10, 2.50, 3, 1),
('RD20240104001', 3, 14, 6, 4, '2024-12-04 16:00:00', '2024-12-04 16:35:00', 35, 3.00, 2.50, 3, 1);

INSERT INTO wallet_record (user_id, type, amount, balance_after, description) VALUES
(3, 1, 200.00, 200.00, '账户充值'),
(3, 2, -1.50, 198.50, '骑行扣费-RD20240101001'),
(3, 2, -3.50, 195.00, '骑行扣费-RD20240101002'),
(3, 3, -99.00, 96.00, '缴纳押金'),
(4, 1, 50.00, 50.00, '账户充值');

INSERT INTO fault_report (user_id, bike_id, type, description, status, handler_id, handle_result, handle_time) VALUES
(3, 19, 2, '前轮胎漏气，无法正常骑行', 2, 2, '已更换前轮内胎，修复完成', '2024-12-02 14:30:00'),
(4, 20, 1, '车锁无法打开，扫码没有反应', 1, NULL, NULL, NULL);

INSERT INTO credit_record (user_id, type, score_change, score_after, description) VALUES
(3, 1, 2, 102, '正常还车奖励'),
(3, 1, 2, 104, '正常还车奖励'),
(3, 1, 2, 106, '正常还车奖励'),
(4, 1, 2, 102, '正常还车奖励');

INSERT INTO announcement (title, content, type, status) VALUES
('共享单车系统正式上线', '欢迎使用共享单车系统！新用户注册即享首单免费骑行优惠。', 1, 1),
('春节骑行优惠活动', '2025年春节期间，所有骑行费用享8折优惠，活动时间：1月28日-2月4日。', 2, 1),
('颐和园站点维护通知', '颐和园站点将于2025年1月15日进行设备维护，届时暂停服务，预计维护时间2天。', 3, 1);

INSERT INTO feedback (user_id, type, content, reply, status) VALUES
(3, 1, '希望增加更多电动单车投放点', '感谢您的建议，我们将在后续规划中考虑增加电动单车投放站点。', 1),
(4, 2, '客服电话一直打不通', NULL, 0);
