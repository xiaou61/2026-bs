DROP DATABASE IF EXISTS ticket_system;
CREATE DATABASE ticket_system CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE ticket_system;

CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50),
    phone VARCHAR(20) UNIQUE,
    email VARCHAR(100),
    avatar VARCHAR(255),
    balance DECIMAL(10,2) DEFAULT 0.00,
    role VARCHAR(20) DEFAULT 'user',
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_username (username),
    INDEX idx_phone (phone),
    INDEX idx_role (role)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
    rating DECIMAL(3,1) DEFAULT 0.0,
    comment_count INT DEFAULT 0,
    status VARCHAR(20) DEFAULT 'upcoming',
    is_recommend TINYINT DEFAULT 0,
    release_date DATE,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_title (title),
    INDEX idx_status (status),
    INDEX idx_recommend (is_recommend)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
    INDEX idx_name (name),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE hall (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    cinema_id BIGINT NOT NULL,
    name VARCHAR(50) NOT NULL,
    type VARCHAR(20),
    total_seats INT,
    seat_layout JSON,
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_cinema_id (cinema_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE showtime (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    movie_id BIGINT NOT NULL,
    hall_id BIGINT NOT NULL,
    cinema_id BIGINT NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) DEFAULT 'available',
    available_seats INT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_movie_id (movie_id),
    INDEX idx_hall_id (hall_id),
    INDEX idx_start_time (start_time),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE seat (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    showtime_id BIGINT NOT NULL,
    row_num INT NOT NULL,
    col_num INT NOT NULL,
    seat_type VARCHAR(20) DEFAULT 'normal',
    price DECIMAL(10,2),
    status VARCHAR(20) DEFAULT 'available',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_showtime_id (showtime_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE orders (
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
    discount_amount DECIMAL(10,2) DEFAULT 0.00,
    pay_amount DECIMAL(10,2),
    coupon_id BIGINT,
    status VARCHAR(20) DEFAULT 'unpaid',
    pay_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_order_no (order_no),
    INDEX idx_user_id (user_id),
    INDEX idx_status (status),
    INDEX idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE ticket (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    ticket_no VARCHAR(50) UNIQUE NOT NULL,
    qr_code VARCHAR(255),
    status VARCHAR(20) DEFAULT 'unused',
    use_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_ticket_no (ticket_no),
    INDEX idx_order_id (order_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE payment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    pay_no VARCHAR(50) UNIQUE NOT NULL,
    pay_type VARCHAR(20),
    pay_amount DECIMAL(10,2),
    status VARCHAR(20) DEFAULT 'pending',
    pay_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_pay_no (pay_no),
    INDEX idx_order_id (order_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE comment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    movie_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    order_id BIGINT,
    rating INT,
    content TEXT,
    status VARCHAR(20) DEFAULT 'approved',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_movie_id (movie_id),
    INDEX idx_user_id (user_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE coupon (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(20),
    discount_type VARCHAR(20),
    discount_value DECIMAL(10,2),
    min_amount DECIMAL(10,2),
    total_count INT,
    used_count INT DEFAULT 0,
    start_time DATETIME,
    end_time DATETIME,
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_type (type),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE user_coupon (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    coupon_id BIGINT NOT NULL,
    status VARCHAR(20) DEFAULT 'unused',
    use_time DATETIME,
    expire_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_user_id (user_id),
    INDEX idx_coupon_id (coupon_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO user (username, password, nickname, phone, email, balance, role, status) VALUES
('admin', '$2a$10$N.ZOn9G6/YLFixAOPMg/h.z7pCu6v2XyFDtC4q9jxrD1QDxrJI8Pi', '管理员', '13800000000', 'admin@ticket.com', 10000.00, 'admin', 1),
('user', '$2a$10$N.ZOn9G6/YLFixAOPMg/h.z7pCu6v2XyFDtC4q9jxrD1QDxrJI8Pi', '普通用户', '13800000001', 'user@ticket.com', 500.00, 'user', 1),
('test', '$2a$10$N.ZOn9G6/YLFixAOPMg/h.z7pCu6v2XyFDtC4q9jxrD1QDxrJI8Pi', '测试用户', '13800000002', 'test@ticket.com', 200.00, 'user', 1),
('zhangsan', '$2a$10$N.ZOn9G6/YLFixAOPMg/h.z7pCu6v2XyFDtC4q9jxrD1QDxrJI8Pi', '张三', '13900000001', 'zhangsan@ticket.com', 300.00, 'user', 1),
('lisi', '$2a$10$N.ZOn9G6/YLFixAOPMg/h.z7pCu6v2XyFDtC4q9jxrD1QDxrJI8Pi', '李四', '13900000002', 'lisi@ticket.com', 150.00, 'user', 1);

INSERT INTO movie (title, type, category, duration, director, actors, poster, description, rating, comment_count, status, is_recommend, release_date) VALUES
('流浪地球3', '电影', '科幻', 140, '郭帆', '吴京,刘德华,李雪健', 'https://example.com/poster1.jpg', '太阳即将毁灭，人类在地球表面建造出巨大的推进器，寻找新的家园。', 9.2, 15680, 'showing', 1, '2026-02-01'),
('长安三万里', '电影', '动画', 168, '谢君伟', '杨天翔,凌振赫', 'https://example.com/poster2.jpg', '盛唐时期，诗人高适回忆起自己与李白的过往。', 8.5, 8920, 'showing', 1, '2026-01-25'),
('消失的她', '电影', '悬疑', 126, '崔睿', '朱一龙,倪妮', 'https://example.com/poster3.jpg', '一场旅行中，妻子神秘失踪，丈夫陷入重重谜团。', 8.0, 6540, 'showing', 0, '2026-02-10'),
('坚如磐石', '电影', '剧情', 135, '张艺谋', '雷佳音,张国立,于和伟', 'https://example.com/poster4.jpg', '一个关于正义与腐败的较量故事。', 7.8, 4320, 'upcoming', 0, '2026-03-01'),
('奥本海默', '电影', '传记', 180, '诺兰', '基里安·墨菲,小罗伯特·唐尼', 'https://example.com/poster5.jpg', '原子弹之父奥本海默的传奇一生。', 9.0, 12450, 'showing', 1, '2026-01-20');

INSERT INTO cinema (name, address, phone, business_hours, facilities, status) VALUES
('万达影城（中关村店）', '北京市海淀区中关村大街1号', '010-12345678', '09:00-23:00', 'IMAX,杜比全景声,4DX,VIP厅', 1),
('CGV影城（朝阳大悦城店）', '北京市朝阳区朝阳北路101号', '010-87654321', '09:30-23:30', 'IMAX,激光厅,情侣座', 1),
('博纳国际影城（王府井店）', '北京市东城区王府井大街88号', '010-66668888', '10:00-24:00', '杜比影院,LUXE巨幕,VIP厅', 1);

INSERT INTO hall (cinema_id, name, type, total_seats, seat_layout, status) VALUES
(1, '1号厅', 'IMAX', 280, '{"rows": 14, "cols": 20}', 1),
(1, '2号厅', '普通厅', 180, '{"rows": 12, "cols": 15}', 1),
(1, '3号厅', 'VIP厅', 60, '{"rows": 5, "cols": 12}', 1),
(2, '1号厅', 'IMAX', 300, '{"rows": 15, "cols": 20}', 1),
(2, '2号厅', '激光厅', 200, '{"rows": 13, "cols": 16}', 1),
(3, '1号厅', '杜比影院', 220, '{"rows": 11, "cols": 20}', 1);

INSERT INTO showtime (movie_id, hall_id, cinema_id, start_time, end_time, price, status, available_seats) VALUES
(1, 1, 1, '2026-02-24 10:30:00', '2026-02-24 12:50:00', 65.00, 'available', 280),
(1, 1, 1, '2026-02-24 14:00:00', '2026-02-24 16:20:00', 65.00, 'available', 280),
(1, 1, 1, '2026-02-24 19:30:00', '2026-02-24 21:50:00', 75.00, 'available', 280),
(2, 2, 1, '2026-02-24 11:00:00', '2026-02-24 13:48:00', 45.00, 'available', 180),
(2, 2, 1, '2026-02-24 15:30:00', '2026-02-24 18:18:00', 45.00, 'available', 180),
(3, 4, 2, '2026-02-24 10:00:00', '2026-02-24 12:06:00', 55.00, 'available', 300),
(3, 4, 2, '2026-02-24 18:00:00', '2026-02-24 20:06:00', 60.00, 'available', 300),
(5, 6, 3, '2026-02-24 13:00:00', '2026-02-24 16:00:00', 70.00, 'available', 220),
(5, 6, 3, '2026-02-24 20:00:00', '2026-02-24 23:00:00', 80.00, 'available', 220);

INSERT INTO seat (showtime_id, row_num, col_num, seat_type, price, status) VALUES
(1, 1, 1, 'normal', 65.00, 'available'),
(1, 1, 2, 'normal', 65.00, 'available'),
(1, 1, 3, 'normal', 65.00, 'available'),
(1, 5, 10, 'vip', 85.00, 'available'),
(1, 5, 11, 'vip', 85.00, 'available'),
(1, 7, 10, 'couple', 130.00, 'available'),
(2, 1, 1, 'normal', 65.00, 'available'),
(2, 5, 10, 'vip', 85.00, 'available'),
(3, 1, 1, 'normal', 75.00, 'available'),
(3, 5, 10, 'vip', 95.00, 'available');

INSERT INTO coupon (name, type, discount_type, discount_value, min_amount, total_count, used_count, start_time, end_time, status) VALUES
('新用户专享券', 'new_user', 'amount', 10.00, 30.00, 1000, 150, '2026-02-01 00:00:00', '2026-12-31 23:59:59', 1),
('周末观影券', 'weekend', 'amount', 15.00, 50.00, 500, 80, '2026-02-01 00:00:00', '2026-12-31 23:59:59', 1),
('满100减20', 'general', 'amount', 20.00, 100.00, 2000, 320, '2026-02-01 00:00:00', '2026-12-31 23:59:59', 1),
('8折优惠券', 'general', 'percent', 0.80, 50.00, 1500, 200, '2026-02-01 00:00:00', '2026-12-31 23:59:59', 1),
('情人节特惠', 'special', 'amount', 25.00, 80.00, 800, 0, '2026-02-10 00:00:00', '2026-02-20 23:59:59', 1);

INSERT INTO user_coupon (user_id, coupon_id, status, expire_time) VALUES
(2, 1, 'unused', '2026-12-31 23:59:59'),
(2, 3, 'unused', '2026-12-31 23:59:59'),
(3, 1, 'unused', '2026-12-31 23:59:59'),
(3, 2, 'unused', '2026-12-31 23:59:59'),
(4, 3, 'used', '2026-12-31 23:59:59'),
(5, 4, 'unused', '2026-12-31 23:59:59');

INSERT INTO orders (order_no, user_id, showtime_id, movie_title, cinema_name, hall_name, show_time, seat_info, total_amount, discount_amount, pay_amount, status, pay_time) VALUES
('ORD202602230001', 2, 1, '流浪地球3', '万达影城（中关村店）', '1号厅', '2026-02-24 10:30:00', '5排10座,5排11座', 170.00, 20.00, 150.00, 'paid', '2026-02-23 10:30:00'),
('ORD202602230002', 3, 4, '长安三万里', '万达影城（中关村店）', '2号厅', '2026-02-24 11:00:00', '3排5座', 45.00, 0.00, 45.00, 'paid', '2026-02-23 14:20:00'),
('ORD202602230003', 4, 6, '消失的她', 'CGV影城（朝阳大悦城店）', '1号厅', '2026-02-24 10:00:00', '7排12座,7排13座', 110.00, 10.00, 100.00, 'paid', '2026-02-23 16:45:00');

INSERT INTO ticket (order_id, ticket_no, qr_code, status) VALUES
(1, 'TKT202602230001001', 'QR_TKT202602230001001', 'unused'),
(1, 'TKT202602230001002', 'QR_TKT202602230001002', 'unused'),
(2, 'TKT202602230002001', 'QR_TKT202602230002001', 'unused'),
(3, 'TKT202602230003001', 'QR_TKT202602230003001', 'unused'),
(3, 'TKT202602230003002', 'QR_TKT202602230003002', 'unused');

INSERT INTO payment (order_id, pay_no, pay_type, pay_amount, status, pay_time) VALUES
(1, 'PAY202602230001', 'balance', 150.00, 'success', '2026-02-23 10:30:00'),
(2, 'PAY202602230002', 'balance', 45.00, 'success', '2026-02-23 14:20:00'),
(3, 'PAY202602230003', 'balance', 100.00, 'success', '2026-02-23 16:45:00');

INSERT INTO comment (movie_id, user_id, order_id, rating, content, status) VALUES
(1, 2, 1, 5, '特效太震撼了！IMAX效果非常棒，值得一看！', 'approved'),
(1, 3, NULL, 5, '剧情紧凑，演员演技在线，强烈推荐！', 'approved'),
(1, 4, NULL, 4, '整体不错，就是有些地方节奏有点慢', 'approved'),
(2, 2, NULL, 5, '画面太美了，每一帧都是壁纸级别', 'approved'),
(2, 5, NULL, 4, '很有诗意的一部动画电影', 'approved'),
(5, 3, NULL, 5, '诺兰的又一力作，值得细品', 'approved');
