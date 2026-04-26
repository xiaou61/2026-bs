DROP TABLE IF EXISTS notice;
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS favorite;
DROP TABLE IF EXISTS guide_booking;
DROP TABLE IF EXISTS reservation;
DROP TABLE IF EXISTS exhibition;
DROP TABLE IF EXISTS research;
DROP TABLE IF EXISTS relic_image;
DROP TABLE IF EXISTS relic;
DROP TABLE IF EXISTS exhibition_hall;
DROP TABLE IF EXISTS dynasty;
DROP TABLE IF EXISTS relic_category;
DROP TABLE IF EXISTS sys_user;

CREATE TABLE sys_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    real_name VARCHAR(50),
    phone VARCHAR(20),
    email VARCHAR(100),
    avatar VARCHAR(255),
    role INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE relic_category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(500),
    sort INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE dynasty (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    start_year VARCHAR(20),
    end_year VARCHAR(20),
    description VARCHAR(500),
    sort INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE exhibition_hall (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    floor INT,
    area DECIMAL(10,2),
    capacity INT,
    description VARCHAR(500),
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE relic (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    category_id BIGINT,
    dynasty_id BIGINT,
    hall_id BIGINT,
    relic_no VARCHAR(50),
    material VARCHAR(100),
    size VARCHAR(100),
    weight VARCHAR(50),
    origin VARCHAR(200),
    discovery_date DATE,
    level INT DEFAULT 3,
    image VARCHAR(255),
    model_url VARCHAR(255),
    audio_url VARCHAR(255),
    description TEXT,
    historical_value TEXT,
    view_count INT DEFAULT 0,
    like_count INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE relic_image (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    relic_id BIGINT NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    description VARCHAR(200),
    sort INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE research (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    relic_id BIGINT,
    author_id BIGINT,
    content TEXT,
    summary VARCHAR(500),
    publish_date DATE,
    file_url VARCHAR(255),
    view_count INT DEFAULT 0,
    status INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE exhibition (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    hall_id BIGINT,
    cover_image VARCHAR(255),
    start_date DATE,
    end_date DATE,
    description TEXT,
    ticket_price DECIMAL(10,2) DEFAULT 0,
    view_count INT DEFAULT 0,
    status INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE reservation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_no VARCHAR(50) NOT NULL,
    user_id BIGINT NOT NULL,
    exhibition_id BIGINT,
    visit_date DATE NOT NULL,
    time_slot VARCHAR(50),
    visitor_count INT DEFAULT 1,
    contact_name VARCHAR(50),
    contact_phone VARCHAR(20),
    total_price DECIMAL(10,2) DEFAULT 0,
    status INT DEFAULT 0,
    remark VARCHAR(200),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE guide_booking (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_no VARCHAR(50) NOT NULL,
    user_id BIGINT NOT NULL,
    guide_id BIGINT,
    visit_date DATE NOT NULL,
    start_time TIME,
    duration INT DEFAULT 60,
    visitor_count INT DEFAULT 1,
    language VARCHAR(20) DEFAULT '中文',
    price DECIMAL(10,2) DEFAULT 0,
    status INT DEFAULT 0,
    remark VARCHAR(200),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE favorite (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    relic_id BIGINT NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_user_relic UNIQUE (user_id, relic_id)
);

CREATE TABLE comment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    relic_id BIGINT,
    exhibition_id BIGINT,
    content VARCHAR(500) NOT NULL,
    rating INT DEFAULT 5,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE notice (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    type INT DEFAULT 1,
    publish_time DATETIME,
    status INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);
