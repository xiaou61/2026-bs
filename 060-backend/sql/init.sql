DROP DATABASE IF EXISTS movie_ticket;
CREATE DATABASE movie_ticket DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE movie_ticket;

CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50),
    avatar VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(100),
    role VARCHAR(20) DEFAULT 'user',
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE movie_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    sort INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE movie (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    poster VARCHAR(255),
    director VARCHAR(50),
    actors VARCHAR(255),
    category_id BIGINT,
    duration INT,
    release_date DATE,
    description TEXT,
    score DECIMAL(3,1) DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE cinema (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(20),
    description VARCHAR(500),
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE hall (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    cinema_id BIGINT NOT NULL,
    name VARCHAR(50) NOT NULL,
    seat_rows INT DEFAULT 8,
    seat_cols INT DEFAULT 10,
    hall_type VARCHAR(20) DEFAULT 'normal',
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE showtime (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    movie_id BIGINT NOT NULL,
    cinema_id BIGINT NOT NULL,
    hall_id BIGINT NOT NULL,
    show_date DATE NOT NULL,
    start_time VARCHAR(10) NOT NULL,
    end_time VARCHAR(10) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    available_seats INT DEFAULT 80,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE ticket_order (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_no VARCHAR(50) UNIQUE NOT NULL,
    user_id BIGINT NOT NULL,
    showtime_id BIGINT NOT NULL,
    seats VARCHAR(255) NOT NULL,
    seat_count INT NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    status INT DEFAULT 0,
    pay_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE review (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    movie_id BIGINT NOT NULL,
    rating INT NOT NULL,
    content TEXT,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE favorite (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    movie_id BIGINT NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_user_movie (user_id, movie_id)
);

CREATE TABLE announcement (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    content TEXT,
    admin_id BIGINT,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO user (username, password, nickname, role, phone, email) VALUES
('admin', '123456', '系统管理员', 'admin', '13800000001', 'admin@movie.com'),
('user1', '123456', '张三', 'user', '13800000002', 'user1@movie.com'),
('user2', '123456', '李四', 'user', '13800000003', 'user2@movie.com');

INSERT INTO movie_category (name, sort) VALUES
('动作', 1),
('喜剧', 2),
('科幻', 3),
('爱情', 4),
('恐怖', 5),
('动画', 6),
('纪录片', 7);

INSERT INTO movie (title, director, actors, category_id, duration, release_date, description, score, status) VALUES
('星际迷航：新纪元', '詹姆斯·卡梅隆', '克里斯·帕拉特,佐伊·索尔达娜', 3, 148, '2026-01-15', '一部震撼人心的太空冒险巨作，讲述人类探索未知星系的壮阔旅程。', 8.5, 1),
('笑傲江湖2026', '周星驰', '沈腾,马丽,贾玲', 2, 120, '2026-02-01', '一部融合古装与现代笑点的喜剧大作，爆笑连连。', 7.8, 1),
('暗影追踪', '克里斯托弗·诺兰', '莱昂纳多·迪卡普里奥,汤姆·哈迪', 1, 135, '2026-01-20', '一部扣人心弦的动作悬疑片，多重反转令人叹为观止。', 8.9, 1),
('初恋那件小事', '陈凯歌', '刘亦菲,王一博', 4, 110, '2026-02-14', '一段发生在大学校园里的纯真爱情故事。', 7.2, 1),
('深海惊魂', '温子仁', '杰森·莫玛,安伯·赫德', 5, 105, '2026-01-25', '一部深海探险恐怖片，令人窒息的水下惊悚体验。', 7.5, 1),
('熊猫总动员', '宫崎骏', '配音团队', 6, 95, '2026-02-05', '一部温馨治愈的动画电影，讲述小熊猫的成长冒险故事。', 9.0, 1);

INSERT INTO cinema (name, address, phone, description) VALUES
('万达影城（中心店）', '市中心商业广场3楼', '010-88881111', '城市中心旗舰影城，配备IMAX巨幕'),
('金逸影城（大学城店）', '大学城商业街B区2楼', '010-88882222', '大学城周边最受欢迎的影城'),
('CGV影城（科技园店）', '科技园创业大厦负一楼', '010-88883333', '高科技体验影城，杜比全景声');

INSERT INTO hall (cinema_id, name, seat_rows, seat_cols, hall_type) VALUES
(1, '1号厅', 10, 12, 'IMAX'),
(1, '2号厅', 8, 10, 'normal'),
(2, '1号厅', 8, 10, 'normal'),
(2, '2号厅', 6, 8, 'VIP'),
(3, '1号厅', 10, 14, 'giant'),
(3, '2号厅', 8, 10, 'normal');

INSERT INTO showtime (movie_id, cinema_id, hall_id, show_date, start_time, end_time, price, available_seats) VALUES
(1, 1, 1, '2026-02-10', '10:00', '12:28', 79.90, 120),
(1, 1, 2, '2026-02-10', '14:00', '16:28', 59.90, 80),
(2, 2, 3, '2026-02-10', '10:30', '12:30', 45.00, 80),
(2, 2, 4, '2026-02-10', '15:00', '17:00', 68.00, 48),
(3, 3, 5, '2026-02-10', '13:00', '15:15', 89.00, 140),
(3, 1, 1, '2026-02-10', '19:00', '21:15', 99.00, 120),
(4, 2, 3, '2026-02-14', '18:00', '19:50', 55.00, 80),
(6, 3, 6, '2026-02-10', '09:30', '11:05', 39.90, 80);

INSERT INTO ticket_order (order_no, user_id, showtime_id, seats, seat_count, total_price, status, pay_time) VALUES
('ORD20260208001', 2, 1, '3排5座,3排6座', 2, 159.80, 1, '2026-02-08 10:30:00'),
('ORD20260208002', 2, 5, '5排7座', 1, 89.00, 1, '2026-02-08 11:00:00'),
('ORD20260208003', 3, 3, '4排3座,4排4座,4排5座', 3, 135.00, 0, NULL),
('ORD20260208004', 3, 7, '2排4座,2排5座', 2, 110.00, 1, '2026-02-08 14:00:00'),
('ORD20260208005', 2, 8, '1排1座,1排2座', 2, 79.80, 2, NULL);

INSERT INTO review (user_id, movie_id, rating, content) VALUES
(2, 1, 9, '特效非常震撼，剧情也很紧凑，强烈推荐！'),
(2, 3, 10, '诺兰又一神作，烧脑程度满分！'),
(3, 2, 8, '笑得停不下来，适合全家观看'),
(3, 6, 9, '画面太美了，治愈系动画的巅峰之作'),
(2, 6, 10, '宫崎骏永远的神！每一帧都是壁纸'),
(3, 1, 8, '科幻迷必看，宇宙场景太壮观了');

INSERT INTO favorite (user_id, movie_id) VALUES
(2, 1),
(2, 3),
(2, 6),
(3, 2),
(3, 6);

INSERT INTO announcement (title, content, admin_id) VALUES
('春节档电影上映通知', '2026年春节档多部大片即将上映，敬请期待！', 1),
('会员充值优惠活动', '即日起充值满200送50，满500送150，活动截止至2月底。', 1),
('影城设备升级公告', '万达影城中心店1号厅已升级为IMAX激光厅，欢迎体验。', 1);
