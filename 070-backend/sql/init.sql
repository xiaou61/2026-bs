DROP DATABASE IF EXISTS ticket_system_070;
CREATE DATABASE ticket_system_070 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE ticket_system_070;

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
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    KEY idx_user_role (role),
    KEY idx_user_status (status)
);

CREATE TABLE movie (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    type VARCHAR(20),
    category VARCHAR(50),
    duration INT,
    director VARCHAR(100),
    actors TEXT,
    poster VARCHAR(255),
    description TEXT,
    rating DECIMAL(3,1) DEFAULT 0,
    comment_count INT DEFAULT 0,
    status VARCHAR(20) DEFAULT 'ON',
    is_recommend TINYINT DEFAULT 0,
    release_date DATE,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    KEY idx_movie_title (title),
    KEY idx_movie_status (status),
    KEY idx_movie_recommend (is_recommend)
);

CREATE TABLE cinema (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(20),
    business_hours VARCHAR(50),
    facilities TEXT,
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    KEY idx_cinema_name (name),
    KEY idx_cinema_status (status)
);

CREATE TABLE hall (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    cinema_id BIGINT NOT NULL,
    name VARCHAR(50) NOT NULL,
    type VARCHAR(20),
    total_seats INT,
    seat_rows INT DEFAULT 8,
    seat_cols INT DEFAULT 10,
    seat_layout TEXT,
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    KEY idx_hall_cinema (cinema_id),
    KEY idx_hall_status (status)
);

CREATE TABLE showtime (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    movie_id BIGINT NOT NULL,
    hall_id BIGINT NOT NULL,
    cinema_id BIGINT NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) DEFAULT 'SELLING',
    available_seats INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    KEY idx_showtime_movie (movie_id),
    KEY idx_showtime_hall (hall_id),
    KEY idx_showtime_cinema (cinema_id),
    KEY idx_showtime_start (start_time),
    KEY idx_showtime_status (status)
);

CREATE TABLE seat (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    showtime_id BIGINT NOT NULL,
    row_num INT NOT NULL,
    col_num INT NOT NULL,
    seat_type VARCHAR(20) DEFAULT 'NORMAL',
    price DECIMAL(10,2),
    status VARCHAR(20) DEFAULT 'AVAILABLE',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_showtime_position (showtime_id, row_num, col_num),
    KEY idx_seat_showtime (showtime_id),
    KEY idx_seat_status (status)
);

CREATE TABLE ticket_order (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_no VARCHAR(50) UNIQUE NOT NULL,
    user_id BIGINT NOT NULL,
    showtime_id BIGINT NOT NULL,
    movie_title VARCHAR(100),
    cinema_name VARCHAR(100),
    hall_name VARCHAR(50),
    show_time DATETIME,
    seat_info VARCHAR(255),
    total_amount DECIMAL(10,2),
    discount_amount DECIMAL(10,2) DEFAULT 0,
    pay_amount DECIMAL(10,2),
    coupon_id BIGINT,
    status VARCHAR(20) DEFAULT 'WAIT_PAY',
    pay_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    KEY idx_order_no (order_no),
    KEY idx_order_user (user_id),
    KEY idx_order_status (status),
    KEY idx_order_create (create_time)
);

CREATE TABLE ticket (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    ticket_no VARCHAR(50) UNIQUE NOT NULL,
    qr_code VARCHAR(255),
    status VARCHAR(20) DEFAULT 'UNUSED',
    use_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    KEY idx_ticket_order (order_id),
    KEY idx_ticket_status (status)
);

CREATE TABLE payment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT,
    pay_no VARCHAR(50) UNIQUE NOT NULL,
    pay_type VARCHAR(20),
    pay_amount DECIMAL(10,2),
    status VARCHAR(20) DEFAULT 'WAIT',
    pay_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    KEY idx_payment_order (order_id),
    KEY idx_payment_status (status)
);

CREATE TABLE comment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    movie_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    order_id BIGINT,
    rating INT,
    content TEXT,
    status VARCHAR(20) DEFAULT 'PENDING',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    KEY idx_comment_movie (movie_id),
    KEY idx_comment_user (user_id),
    KEY idx_comment_status (status)
);

CREATE TABLE coupon (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(20),
    discount_type VARCHAR(20),
    discount_value DECIMAL(10,2),
    min_amount DECIMAL(10,2) DEFAULT 0,
    total_count INT DEFAULT 0,
    used_count INT DEFAULT 0,
    start_time DATETIME,
    end_time DATETIME,
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    KEY idx_coupon_status (status),
    KEY idx_coupon_type (type)
);

CREATE TABLE user_coupon (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    coupon_id BIGINT NOT NULL,
    status VARCHAR(20) DEFAULT 'UNUSED',
    use_time DATETIME,
    expire_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    KEY idx_user_coupon_user (user_id),
    KEY idx_user_coupon_coupon (coupon_id),
    KEY idx_user_coupon_status (status)
);

INSERT INTO user (username, password, nickname, phone, email, balance, role, status) VALUES
('admin', '123456', '系统管理员', '13800000001', 'admin@ticket.com', 5000.00, 'ADMIN', 1),
('user', '123456', '普通用户', '13800000002', 'user@ticket.com', 500.00, 'USER', 1),
('test', '123456', '测试用户', '13800000003', 'test@ticket.com', 300.00, 'USER', 1);

INSERT INTO movie (title, type, category, duration, director, actors, poster, description, rating, comment_count, status, is_recommend, release_date) VALUES
('星际迷航：新纪元', 'MOVIE', '科幻', 148, '詹姆斯·卡梅隆', '克里斯·帕拉特,佐伊·索尔达娜', 'https://picsum.photos/280/380?1', '宇宙探索题材科幻大片。', 8.8, 2, 'ON', 1, '2026-01-15'),
('笑傲江湖2026', 'MOVIE', '喜剧', 120, '周星驰', '沈腾,马丽', 'https://picsum.photos/280/380?2', '古装喜剧电影。', 7.9, 1, 'ON', 1, '2026-02-01'),
('暗影追踪', 'MOVIE', '动作', 135, '克里斯托弗·诺兰', '莱昂纳多·迪卡普里奥', 'https://picsum.photos/280/380?3', '动作悬疑类型电影。', 8.6, 1, 'ON', 0, '2026-01-20'),
('音乐会之夜', 'SHOW', '演出', 150, '国家交响乐团', '首席乐团', 'https://picsum.photos/280/380?4', '交响音乐会演出。', 9.2, 0, 'ON', 1, '2026-02-10');

INSERT INTO cinema (name, address, phone, business_hours, facilities, status) VALUES
('万达影城中心店', '市中心商业广场3楼', '010-88881111', '09:00-23:30', 'IMAX,杜比全景声,停车场', 1),
('金逸影城大学城店', '大学城商业街B区2楼', '010-88882222', '10:00-23:00', 'VIP厅,取票机,餐饮区', 1),
('CGV影城科技园店', '科技园创业大厦负一楼', '010-88883333', '09:30-22:30', '巨幕厅,停车场', 1);

INSERT INTO hall (cinema_id, name, type, total_seats, seat_rows, seat_cols, status) VALUES
(1, '1号厅', 'IMAX', 120, 10, 12, 1),
(1, '2号厅', 'NORMAL', 80, 8, 10, 1),
(2, '1号厅', 'VIP', 60, 6, 10, 1),
(3, '1号厅', 'GIANT', 140, 10, 14, 1);

INSERT INTO showtime (movie_id, hall_id, cinema_id, start_time, end_time, price, status, available_seats) VALUES
(1, 1, 1, '2026-02-24 10:00:00', '2026-02-24 12:28:00', 79.90, 'SELLING', 120),
(1, 2, 1, '2026-02-24 14:00:00', '2026-02-24 16:28:00', 59.90, 'SELLING', 80),
(2, 3, 2, '2026-02-24 10:30:00', '2026-02-24 12:30:00', 68.00, 'SELLING', 60),
(3, 4, 3, '2026-02-24 13:00:00', '2026-02-24 15:15:00', 89.00, 'SELLING', 140),
(4, 1, 1, '2026-02-25 19:00:00', '2026-02-25 21:30:00', 120.00, 'SELLING', 120);

INSERT INTO coupon (name, type, discount_type, discount_value, min_amount, total_count, used_count, start_time, end_time, status) VALUES
('新用户立减20元', 'GENERAL', 'AMOUNT', 20.00, 80.00, 1000, 2, '2026-02-01 00:00:00', '2026-03-31 23:59:59', 1),
('9折观影券', 'GENERAL', 'RATE', 0.90, 50.00, 2000, 1, '2026-02-01 00:00:00', '2026-03-15 23:59:59', 1),
('周末满200减50', 'WEEKEND', 'AMOUNT', 50.00, 200.00, 500, 0, '2026-02-20 00:00:00', '2026-03-31 23:59:59', 1);

INSERT INTO user_coupon (user_id, coupon_id, status, expire_time) VALUES
(2, 1, 'UNUSED', '2026-03-31 23:59:59'),
(2, 2, 'USED', '2026-03-15 23:59:59'),
(3, 1, 'UNUSED', '2026-03-31 23:59:59');

INSERT INTO ticket_order (order_no, user_id, showtime_id, movie_title, cinema_name, hall_name, show_time, seat_info, total_amount, discount_amount, pay_amount, coupon_id, status, pay_time) VALUES
('TK202602230001', 2, 1, '星际迷航：新纪元', '万达影城中心店', '1号厅', '2026-02-24 10:00:00', '3排5座,3排6座', 159.80, 20.00, 139.80, 1, 'PAID', '2026-02-23 12:30:00'),
('TK202602230002', 3, 3, '笑傲江湖2026', '金逸影城大学城店', '1号厅', '2026-02-24 10:30:00', '2排1座,2排2座', 136.00, 0.00, 136.00, NULL, 'WAIT_PAY', NULL);

INSERT INTO payment (order_id, pay_no, pay_type, pay_amount, status, pay_time) VALUES
(1, 'PAY202602230001', 'BALANCE', 139.80, 'SUCCESS', '2026-02-23 12:30:00'),
(2, 'PAY202602230002', 'BALANCE', 0.00, 'WAIT', NULL);

INSERT INTO ticket (order_id, ticket_no, qr_code, status) VALUES
(1, 'ET202602230001', 'QRCODE-ET202602230001', 'UNUSED'),
(1, 'ET202602230002', 'QRCODE-ET202602230002', 'UNUSED');

INSERT INTO comment (movie_id, user_id, order_id, rating, content, status) VALUES
(1, 2, 1, 9, '特效和音效都非常震撼。', 'APPROVED'),
(2, 3, NULL, 8, '笑点很多，适合周末放松。', 'PENDING');
