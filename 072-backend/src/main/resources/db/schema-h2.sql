DROP TABLE IF EXISTS favorite;
DROP TABLE IF EXISTS announcement;
DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS travel_note;
DROP TABLE IF EXISTS activity_registration;
DROP TABLE IF EXISTS activity;
DROP TABLE IF EXISTS restaurant;
DROP TABLE IF EXISTS hotel;
DROP TABLE IF EXISTS ticket_order;
DROP TABLE IF EXISTS ticket_type;
DROP TABLE IF EXISTS route_spot;
DROP TABLE IF EXISTS route;
DROP TABLE IF EXISTS scenic_spot;
DROP TABLE IF EXISTS user;

CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50),
    avatar VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(100),
    role VARCHAR(20) DEFAULT 'user',
    balance DECIMAL(10,2) DEFAULT 0.00,
    status INT DEFAULT 1,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE scenic_spot (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    detail TEXT,
    location VARCHAR(200),
    category VARCHAR(50),
    cover_img VARCHAR(255),
    images TEXT,
    open_time VARCHAR(20),
    close_time VARCHAR(20),
    ticket_price DECIMAL(10,2),
    rating DECIMAL(2,1) DEFAULT 0.0,
    view_count INT DEFAULT 0,
    status INT DEFAULT 1,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE route (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    days INT DEFAULT 1,
    difficulty VARCHAR(20) DEFAULT 'easy',
    category VARCHAR(50),
    cover_img VARCHAR(255),
    estimated_cost DECIMAL(10,2),
    like_count INT DEFAULT 0,
    status INT DEFAULT 1,
    user_id BIGINT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE route_spot (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    route_id BIGINT NOT NULL,
    spot_id BIGINT NOT NULL,
    order_num INT DEFAULT 1,
    stay_hours DECIMAL(3,1) DEFAULT 2.0
);

CREATE TABLE ticket_type (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    spot_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    stock INT DEFAULT 100,
    max_per_order INT DEFAULT 5,
    description VARCHAR(255),
    status INT DEFAULT 1
);

CREATE TABLE ticket_order (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_no VARCHAR(50) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    spot_id BIGINT NOT NULL,
    ticket_type_id BIGINT NOT NULL,
    ticket_date DATE NOT NULL,
    quantity INT NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) DEFAULT 'pending',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE hotel (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    type VARCHAR(50),
    price_min DECIMAL(10,2),
    price_max DECIMAL(10,2),
    description TEXT,
    cover_img VARCHAR(255),
    facilities VARCHAR(500),
    rating DECIMAL(2,1) DEFAULT 0.0,
    status INT DEFAULT 1,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE restaurant (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    cuisine_type VARCHAR(50),
    price_per_person DECIMAL(10,2),
    description TEXT,
    cover_img VARCHAR(255),
    recommended_dish VARCHAR(500),
    tags VARCHAR(255),
    rating DECIMAL(2,1) DEFAULT 0.0,
    status INT DEFAULT 1,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE activity (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    location VARCHAR(200),
    start_time DATETIME,
    end_time DATETIME,
    cover_img VARCHAR(255),
    register_deadline DATETIME,
    max_participants INT DEFAULT 100,
    current_participants INT DEFAULT 0,
    status VARCHAR(20) DEFAULT 'registering',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE activity_registration (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    activity_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    register_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    status INT DEFAULT 1
);

CREATE TABLE travel_note (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    title VARCHAR(100) NOT NULL,
    content TEXT,
    cover_img VARCHAR(255),
    tags VARCHAR(255),
    spot_id BIGINT,
    like_count INT DEFAULT 0,
    view_count INT DEFAULT 0,
    status VARCHAR(20) DEFAULT 'pending',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE review (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    target_id BIGINT NOT NULL,
    target_type VARCHAR(20) NOT NULL,
    rating INT NOT NULL,
    content TEXT,
    images VARCHAR(500),
    status INT DEFAULT 1,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE announcement (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    content TEXT,
    type VARCHAR(20),
    is_top INT DEFAULT 0,
    status INT DEFAULT 1,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE favorite (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    target_id BIGINT NOT NULL,
    target_type VARCHAR(20) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (user_id, target_id, target_type)
);
