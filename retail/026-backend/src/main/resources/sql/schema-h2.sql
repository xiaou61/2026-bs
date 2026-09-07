DROP TABLE IF EXISTS withdraw;
DROP TABLE IF EXISTS message;
DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS payment;
DROP TABLE IF EXISTS `order`;
DROP TABLE IF EXISTS demand;
DROP TABLE IF EXISTS portfolio;
DROP TABLE IF EXISTS artist;
DROP TABLE IF EXISTS user;

CREATE TABLE IF NOT EXISTS user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    avatar VARCHAR(255),
    nickname VARCHAR(50),
    bio CLOB,
    role VARCHAR(20) DEFAULT 'USER',
    status VARCHAR(20) DEFAULT 'ACTIVE',
    balance DECIMAL(10, 2) DEFAULT 0.00,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS artist (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    real_name VARCHAR(50),
    id_card VARCHAR(18),
    style VARCHAR(255),
    price_min DECIMAL(10, 2),
    price_max DECIMAL(10, 2),
    delivery_days INT,
    accept_types VARCHAR(255),
    status VARCHAR(20) DEFAULT 'PENDING',
    rating DECIMAL(3, 2) DEFAULT 5.00,
    order_count INT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS portfolio (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    artist_id BIGINT NOT NULL,
    title VARCHAR(100) NOT NULL,
    description CLOB,
    image_url VARCHAR(255) NOT NULL,
    category VARCHAR(50),
    tags VARCHAR(255),
    is_featured TINYINT DEFAULT 0,
    view_count INT DEFAULT 0,
    like_count INT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS demand (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(100) NOT NULL,
    description CLOB NOT NULL,
    type VARCHAR(50),
    size VARCHAR(50),
    style VARCHAR(100),
    budget_min DECIMAL(10, 2),
    budget_max DECIMAL(10, 2),
    deadline DATE,
    ref_images CLOB,
    target_artist_id BIGINT,
    status VARCHAR(20) DEFAULT 'OPEN',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS `order` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_no VARCHAR(32) NOT NULL UNIQUE,
    demand_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    artist_id BIGINT NOT NULL,
    title VARCHAR(100) NOT NULL,
    description CLOB,
    total_price DECIMAL(10, 2) NOT NULL,
    deposit DECIMAL(10, 2) NOT NULL,
    final_payment DECIMAL(10, 2) NOT NULL,
    status VARCHAR(32) DEFAULT 'PENDING_DEPOSIT',
    draft_url VARCHAR(255),
    final_url VARCHAR(255),
    revise_count INT DEFAULT 0,
    max_revise INT DEFAULT 2,
    commission_rate DECIMAL(5, 2) DEFAULT 8.00,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS payment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    type VARCHAR(20) NOT NULL,
    status VARCHAR(20) DEFAULT 'SUCCESS',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS review (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    from_user_id BIGINT NOT NULL,
    to_user_id BIGINT NOT NULL,
    rating INT NOT NULL,
    quality_rating INT,
    attitude_rating INT,
    speed_rating INT,
    content CLOB,
    tags VARCHAR(255),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS message (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(100) NOT NULL,
    content CLOB NOT NULL,
    type VARCHAR(20) DEFAULT 'SYSTEM',
    related_id BIGINT,
    is_read TINYINT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS withdraw (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    account_type VARCHAR(20),
    account_no VARCHAR(100),
    status VARCHAR(20) DEFAULT 'PENDING',
    remark VARCHAR(255),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
