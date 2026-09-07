DROP TABLE IF EXISTS payment;
DROP TABLE IF EXISTS notification;
DROP TABLE IF EXISTS complaint;
DROP TABLE IF EXISTS delivery_record;
DROP TABLE IF EXISTS delivery_route;
DROP TABLE IF EXISTS milk_order;
DROP TABLE IF EXISTS subscription;
DROP TABLE IF EXISTS delivery_area;
DROP TABLE IF EXISTS address;
DROP TABLE IF EXISTS milk_product;
DROP TABLE IF EXISTS milk_category;
DROP TABLE IF EXISTS user;

CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50),
    phone VARCHAR(20),
    avatar VARCHAR(255),
    role VARCHAR(20) DEFAULT 'USER',
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE milk_category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    sort INT DEFAULT 0,
    status INT DEFAULT 1
);

CREATE TABLE milk_product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_id BIGINT,
    name VARCHAR(100) NOT NULL,
    image VARCHAR(255),
    price DECIMAL(10,2) NOT NULL,
    unit VARCHAR(20) DEFAULT '瓶',
    spec VARCHAR(50),
    description TEXT,
    stock INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE address (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    contact_name VARCHAR(50),
    contact_phone VARCHAR(20),
    province VARCHAR(50),
    city VARCHAR(50),
    district VARCHAR(50),
    detail VARCHAR(200),
    is_default INT DEFAULT 0
);

CREATE TABLE delivery_area (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    province VARCHAR(50),
    city VARCHAR(50),
    district VARCHAR(50),
    status INT DEFAULT 1
);

CREATE TABLE subscription (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    address_id BIGINT NOT NULL,
    type VARCHAR(20) NOT NULL,
    quantity INT DEFAULT 1,
    delivery_time VARCHAR(20) DEFAULT 'MORNING',
    week_days VARCHAR(50),
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE milk_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_no VARCHAR(50) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    subscription_id BIGINT,
    product_id BIGINT NOT NULL,
    quantity INT DEFAULT 1,
    total_price DECIMAL(10,2),
    address VARCHAR(300),
    contact_name VARCHAR(50),
    contact_phone VARCHAR(20),
    delivery_date DATE,
    delivery_time VARCHAR(20),
    status INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE delivery_route (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    area_id BIGINT,
    delivery_user_id BIGINT,
    description VARCHAR(500),
    status INT DEFAULT 1
);

CREATE TABLE delivery_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    delivery_user_id BIGINT NOT NULL,
    route_id BIGINT,
    status INT DEFAULT 0,
    remark VARCHAR(200),
    delivery_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE complaint (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    order_id BIGINT,
    type VARCHAR(20) DEFAULT 'COMPLAINT',
    content TEXT,
    images VARCHAR(500),
    status INT DEFAULT 0,
    reply TEXT,
    reply_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE notification (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(100),
    content TEXT,
    type VARCHAR(20),
    is_read INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE payment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    amount DECIMAL(10,2),
    pay_type VARCHAR(20),
    pay_no VARCHAR(100),
    status INT DEFAULT 0,
    pay_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);
