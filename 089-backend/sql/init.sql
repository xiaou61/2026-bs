DROP DATABASE IF EXISTS railway_ticket_089;
CREATE DATABASE railway_ticket_089 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE railway_ticket_089;

CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50),
    phone VARCHAR(20) UNIQUE,
    email VARCHAR(100),
    avatar VARCHAR(255),
    balance DECIMAL(10,2) DEFAULT 0,
    role VARCHAR(20) DEFAULT 'USER',
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE station (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    station_code VARCHAR(20) UNIQUE NOT NULL,
    station_name VARCHAR(50) UNIQUE NOT NULL,
    city VARCHAR(50),
    province VARCHAR(50),
    address VARCHAR(255),
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE train_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    train_code VARCHAR(20) UNIQUE NOT NULL,
    train_name VARCHAR(100) NOT NULL,
    train_type VARCHAR(30) NOT NULL,
    carriage_count INT DEFAULT 1,
    seat_count INT DEFAULT 0,
    cover_url VARCHAR(255),
    description TEXT,
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE carriage_template (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    template_name VARCHAR(50) UNIQUE NOT NULL,
    seat_type VARCHAR(30) NOT NULL,
    coach_count INT DEFAULT 1,
    seat_rows INT DEFAULT 10,
    seat_cols INT DEFAULT 5,
    price_factor DECIMAL(10,2) DEFAULT 1.00,
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE train_schedule (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    train_id BIGINT NOT NULL,
    carriage_id BIGINT NOT NULL,
    departure_station_id BIGINT NOT NULL,
    arrival_station_id BIGINT NOT NULL,
    travel_date DATE NOT NULL,
    departure_time DATETIME NOT NULL,
    arrival_time DATETIME NOT NULL,
    base_price DECIMAL(10,2) NOT NULL,
    total_seats INT DEFAULT 0,
    available_seats INT DEFAULT 0,
    sale_status VARCHAR(30) DEFAULT 'ON_SALE',
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE schedule_seat (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    schedule_id BIGINT NOT NULL,
    coach_no INT NOT NULL,
    row_num INT NOT NULL,
    col_num INT NOT NULL,
    seat_no VARCHAR(10) NOT NULL,
    seat_type VARCHAR(30),
    price DECIMAL(10,2),
    status VARCHAR(30) DEFAULT 'AVAILABLE',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE passenger_profile (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    passenger_name VARCHAR(50) NOT NULL,
    id_card VARCHAR(30) UNIQUE NOT NULL,
    phone VARCHAR(20),
    passenger_type VARCHAR(20) DEFAULT 'ADULT',
    is_default TINYINT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE ticket_order (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_no VARCHAR(50) UNIQUE NOT NULL,
    user_id BIGINT NOT NULL,
    schedule_id BIGINT NOT NULL,
    train_code VARCHAR(20),
    departure_station VARCHAR(50),
    arrival_station VARCHAR(50),
    departure_time DATETIME,
    arrival_time DATETIME,
    seat_info TEXT,
    passenger_names VARCHAR(255),
    passenger_info TEXT,
    contact_phone VARCHAR(20),
    total_amount DECIMAL(10,2),
    pay_amount DECIMAL(10,2),
    status VARCHAR(30) DEFAULT 'WAIT_PAY',
    pay_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE train_ticket (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    ticket_no VARCHAR(50) UNIQUE NOT NULL,
    passenger_name VARCHAR(50),
    id_card VARCHAR(30),
    seat_label VARCHAR(50),
    qr_code VARCHAR(100),
    status VARCHAR(30) DEFAULT 'UNUSED',
    verify_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE payment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT,
    pay_no VARCHAR(50) UNIQUE NOT NULL,
    pay_type VARCHAR(30),
    pay_amount DECIMAL(10,2),
    status VARCHAR(30) DEFAULT 'WAIT',
    pay_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE after_sale_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    service_type VARCHAR(20) NOT NULL,
    target_schedule_id BIGINT,
    target_schedule_info VARCHAR(255),
    reason VARCHAR(255),
    review_status VARCHAR(20) DEFAULT 'PENDING',
    review_remark VARCHAR(255),
    review_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE system_notice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    publish_status TINYINT DEFAULT 1,
    sort_no INT DEFAULT 0,
    publish_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO user (username, password, nickname, phone, email, balance, role, status) VALUES
('admin', '123456', '系统管理员', '13800000001', 'admin@railway.com', 5000.00, 'ADMIN', 1),
('dispatcher', '123456', '调度员', '13800000002', 'dispatcher@railway.com', 2000.00, 'DISPATCHER', 1),
('user', '123456', '普通乘客', '13800000003', 'user@railway.com', 800.00, 'USER', 1);

INSERT INTO station (station_code, station_name, city, province, address, status) VALUES
('BJP', '北京南', '北京', '北京', '丰台区永外车站路12号', 1),
('TJP', '天津西', '天津', '天津', '红桥区西站前街1号', 1),
('JNP', '济南西', '济南', '山东', '槐荫区齐州路6号', 1),
('SHH', '上海虹桥', '上海', '上海', '闵行区申虹路1500号', 1);

INSERT INTO train_info (train_code, train_name, train_type, carriage_count, seat_count, cover_url, description, status) VALUES
('G101', '京沪高速线', '高铁', 4, 80, 'https://picsum.photos/640/320?railway-1', '北京南至上海虹桥高铁线路', 1),
('G125', '京津济联程线', '高铁', 3, 60, 'https://picsum.photos/640/320?railway-2', '北京南经天津西至济南西高速线路', 1),
('D221', '华东城际线', '动车', 2, 40, 'https://picsum.photos/640/320?railway-3', '济南西至上海虹桥城际动车', 1);

INSERT INTO carriage_template (template_name, seat_type, coach_count, seat_rows, seat_cols, price_factor, status) VALUES
('高铁二等座模板', '二等座', 4, 10, 5, 1.00, 1),
('高铁商务座模板', '商务座', 2, 8, 4, 1.60, 1),
('动车二等座模板', '二等座', 2, 10, 4, 0.88, 1);

INSERT INTO train_schedule (train_id, carriage_id, departure_station_id, arrival_station_id, travel_date, departure_time, arrival_time, base_price, total_seats, available_seats, sale_status, status) VALUES
(1, 1, 1, 4, '2026-03-20', '2026-03-20 08:00:00', '2026-03-20 12:38:00', 553.50, 200, 198, 'ON_SALE', 1),
(2, 1, 1, 3, '2026-03-20', '2026-03-20 09:20:00', '2026-03-20 11:45:00', 214.00, 200, 200, 'ON_SALE', 1),
(3, 3, 3, 4, '2026-03-20', '2026-03-20 14:00:00', '2026-03-20 17:50:00', 298.00, 80, 80, 'ON_SALE', 1);

INSERT INTO passenger_profile (user_id, passenger_name, id_card, phone, passenger_type, is_default) VALUES
(3, '张三', '370101199901010011', '13800000003', 'ADULT', 1),
(3, '李四', '370101200001010022', '13800000004', 'ADULT', 0);

INSERT INTO ticket_order (order_no, user_id, schedule_id, train_code, departure_station, arrival_station, departure_time, arrival_time, seat_info, passenger_names, passenger_info, contact_phone, total_amount, pay_amount, status, pay_time) VALUES
('RT202603150001', 3, 1, 'G101', '北京南', '上海虹桥', '2026-03-20 08:00:00', '2026-03-20 12:38:00', '1:1车1排A座', '张三', '张三|370101199901010011', '13800000003', 553.50, 553.50, 'PAID', '2026-03-15 18:20:00');

INSERT INTO train_ticket (order_id, ticket_no, passenger_name, id_card, seat_label, qr_code, status) VALUES
(1, 'TK202603150001', '张三', '370101199901010011', '1车1排A座', 'QRCODE-TK202603150001', 'UNUSED');

INSERT INTO payment (order_id, pay_no, pay_type, pay_amount, status, pay_time) VALUES
(1, 'PAY202603150001', 'BALANCE', 553.50, 'SUCCESS', '2026-03-15 18:20:00');

INSERT INTO after_sale_record (order_id, user_id, service_type, target_schedule_id, target_schedule_info, reason, review_status) VALUES
(1, 3, 'CHANGE', 2, 'G125 2026-03-20T09:20', '想改签到济南西', 'PENDING');

INSERT INTO system_notice (title, content, publish_status, sort_no, publish_time) VALUES
('春运出行提醒', '春运高峰期间请至少提前30分钟到站候车，携带有效身份证件进站。', 1, 10, '2026-03-14 09:00:00'),
('改签退票规则说明', '开车前均可在线提交退改签申请，审核结果会同步到订单中心。', 1, 9, '2026-03-14 11:00:00');
