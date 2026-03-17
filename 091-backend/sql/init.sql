DROP DATABASE IF EXISTS cinema_member_system_091;
CREATE DATABASE cinema_member_system_091 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE cinema_member_system_091;

CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    member_no VARCHAR(40) UNIQUE NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50),
    phone VARCHAR(20) UNIQUE,
    email VARCHAR(100),
    avatar VARCHAR(255),
    balance DECIMAL(10,2) DEFAULT 0,
    member_level VARCHAR(20) DEFAULT 'SILVER',
    points INT DEFAULT 0,
    total_recharge DECIMAL(10,2) DEFAULT 0,
    total_consume DECIMAL(10,2) DEFAULT 0,
    role VARCHAR(20) DEFAULT 'MEMBER',
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    KEY idx_user_role (role),
    KEY idx_user_member_level (member_level),
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
    user_id BIGINT NOT NULL,
    order_id BIGINT,
    pay_no VARCHAR(50) UNIQUE NOT NULL,
    pay_type VARCHAR(20),
    pay_amount DECIMAL(10,2),
    status VARCHAR(20) DEFAULT 'WAIT',
    pay_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    KEY idx_payment_user (user_id),
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

INSERT INTO user (member_no, username, password, nickname, phone, email, balance, member_level, points, total_recharge, total_consume, role, status) VALUES
('M202603170001', 'admin', '123456', '平台管理员', '13800000001', 'admin@cinema.com', 8000.00, 'ADMIN', 5800, 12000.00, 5800.00, 'ADMIN', 1),
('M202603170002', 'staff', '123456', '门店运营', '13800000002', 'staff@cinema.com', 1800.00, 'STAFF', 960, 3000.00, 960.00, 'STAFF', 1),
('M202603170003', 'member', '123456', '白银会员', '13800000003', 'member@cinema.com', 420.00, 'SILVER', 860, 1200.00, 860.00, 'MEMBER', 1),
('M202603170004', 'vip01', '123456', '黄金会员', '13800000004', 'vip01@cinema.com', 980.00, 'GOLD', 2640, 3800.00, 2640.00, 'MEMBER', 1),
('M202603170005', 'diamond', '123456', '钻石会员', '13800000005', 'diamond@cinema.com', 5000.00, 'DIAMOND', 6180, 8000.00, 6180.00, 'MEMBER', 1);

INSERT INTO movie (title, type, category, duration, director, actors, poster, description, rating, comment_count, status, is_recommend, release_date) VALUES
('流浪地心计划', 'MOVIE', '科幻', 132, '韩延', '吴京,刘昊然', 'https://picsum.photos/280/380?cinema091-1', '适合会员包场活动的年度科幻大片。', 9.1, 3, 'ON', 1, '2026-03-01'),
('喜剧合伙人', 'MOVIE', '喜剧', 118, '闫非', '沈腾,马丽', 'https://picsum.photos/280/380?cinema091-2', '适合家庭会员观影的合家欢喜剧。', 8.4, 2, 'ON', 1, '2026-02-20'),
('深海行动', 'MOVIE', '动作', 126, '林超贤', '彭于晏,王一博', 'https://picsum.photos/280/380?cinema091-3', '高口碑动作商业片。', 8.7, 1, 'ON', 0, '2026-03-05'),
('艺术放映厅：经典修复', 'MOVIE', '文艺', 110, '电影资料馆', '修复专场', 'https://picsum.photos/280/380?cinema091-4', '会员专享艺术电影展映场。', 9.3, 1, 'ON', 1, '2026-03-10');

INSERT INTO cinema (name, address, phone, business_hours, facilities, status) VALUES
('耀光影城中心店', '滨江大道88号三层', '010-88881111', '09:00-23:30', 'IMAX,杜比全景声,会员休息区,停车场', 1),
('星河影城大学城店', '大学城文创广场二层', '010-88882222', '10:00-23:00', 'VIP厅,自助取票机,主题餐饮区', 1),
('云幕影城科技园店', '科技园创新中心负一层', '010-88883333', '09:30-22:30', '巨幕厅,咖啡吧,停车场', 1);

INSERT INTO hall (cinema_id, name, type, total_seats, seat_rows, seat_cols, status) VALUES
(1, 'IMAX厅', 'IMAX', 120, 10, 12, 1),
(1, '杜比厅', 'DOLBY', 80, 8, 10, 1),
(2, 'VIP厅', 'VIP', 60, 6, 10, 1),
(3, '巨幕厅', 'GIANT', 140, 10, 14, 1);

INSERT INTO showtime (movie_id, hall_id, cinema_id, start_time, end_time, price, status, available_seats) VALUES
(1, 1, 1, '2026-03-18 19:20:00', '2026-03-18 21:32:00', 79.90, 'SELLING', 120),
(2, 2, 1, '2026-03-18 20:00:00', '2026-03-18 21:58:00', 59.90, 'SELLING', 80),
(3, 3, 2, '2026-03-19 18:30:00', '2026-03-19 20:36:00', 68.00, 'SELLING', 60),
(4, 4, 3, '2026-03-19 19:10:00', '2026-03-19 21:00:00', 88.00, 'SELLING', 140),
(1, 1, 1, '2026-03-20 14:00:00', '2026-03-20 16:12:00', 75.00, 'SELLING', 120);

INSERT INTO coupon (name, type, discount_type, discount_value, min_amount, total_count, used_count, start_time, end_time, status) VALUES
('新会员首单立减20元', 'NEW_MEMBER', 'AMOUNT', 20.00, 80.00, 2000, 3, '2026-03-01 00:00:00', '2026-04-30 23:59:59', 1),
('黄金会员9折券', 'LEVEL', 'RATE', 0.90, 60.00, 1500, 2, '2026-03-01 00:00:00', '2026-04-15 23:59:59', 1),
('储值满500赠50', 'RECHARGE', 'AMOUNT', 50.00, 120.00, 800, 1, '2026-03-10 00:00:00', '2026-04-30 23:59:59', 1);

INSERT INTO user_coupon (user_id, coupon_id, status, expire_time) VALUES
(3, 1, 'UNUSED', '2026-04-30 23:59:59'),
(4, 2, 'UNUSED', '2026-04-15 23:59:59'),
(5, 3, 'USED', '2026-04-30 23:59:59');

INSERT INTO ticket_order (order_no, user_id, showtime_id, movie_title, cinema_name, hall_name, show_time, seat_info, total_amount, discount_amount, pay_amount, coupon_id, status, pay_time) VALUES
('TK202603160001', 3, 1, '流浪地心计划', '耀光影城中心店', 'IMAX厅', '2026-03-18 19:20:00', NULL, 159.80, 20.00, 139.80, 1, 'PAID', '2026-03-16 20:10:00'),
('TK202603170002', 4, 3, '深海行动', '星河影城大学城店', 'VIP厅', '2026-03-19 18:30:00', NULL, 136.00, 0.00, 136.00, NULL, 'FINISHED', '2026-03-17 12:30:00'),
('TK202603170003', 5, 4, '艺术放映厅：经典修复', '云幕影城科技园店', '巨幕厅', '2026-03-19 19:10:00', NULL, 176.00, 50.00, 126.00, 3, 'WAIT_PAY', NULL);

INSERT INTO payment (user_id, order_id, pay_no, pay_type, pay_amount, status, pay_time) VALUES
(3, 1, 'PAY202603160001', 'BALANCE', 139.80, 'SUCCESS', '2026-03-16 20:10:00'),
(4, 2, 'PAY202603170002', 'BALANCE', 136.00, 'SUCCESS', '2026-03-17 12:30:00'),
(5, 3, 'PAY202603170003', 'BALANCE', 126.00, 'WAIT', NULL),
(3, NULL, 'PAY202603170101', 'RECHARGE', 500.00, 'SUCCESS', '2026-03-17 09:20:00'),
(5, NULL, 'PAY202603120201', 'RECHARGE', 2000.00, 'SUCCESS', '2026-03-12 18:00:00');

INSERT INTO ticket (order_id, ticket_no, qr_code, status) VALUES
(1, 'ET202603160001', 'QRCODE-ET202603160001', 'UNUSED'),
(2, 'ET202603170002', 'QRCODE-ET202603170002', 'USED');

INSERT INTO comment (movie_id, user_id, order_id, rating, content, status) VALUES
(1, 3, 1, 9, '会员日活动力度大，IMAX 体验也很稳。', 'APPROVED'),
(3, 4, 2, 8, 'VIP厅环境不错，适合高频会员复购。', 'APPROVED'),
(4, 5, NULL, 9, '艺术专场适合做高等级会员专享活动。', 'PENDING');
