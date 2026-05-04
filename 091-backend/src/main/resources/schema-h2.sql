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
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
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
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
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
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
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
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
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
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
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
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_showtime_position UNIQUE (showtime_id, row_num, col_num)
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
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE ticket (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    ticket_no VARCHAR(50) UNIQUE NOT NULL,
    qr_code VARCHAR(255),
    status VARCHAR(20) DEFAULT 'UNUSED',
    use_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
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
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
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
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
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
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_coupon (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    coupon_id BIGINT NOT NULL,
    status VARCHAR(20) DEFAULT 'UNUSED',
    use_time DATETIME,
    expire_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);
