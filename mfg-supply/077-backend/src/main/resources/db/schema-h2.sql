DROP TABLE IF EXISTS announcement;
DROP TABLE IF EXISTS complaint;
DROP TABLE IF EXISTS favorite;
DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS mes_work_order;
DROP TABLE IF EXISTS mes_task;
DROP TABLE IF EXISTS mes_category;
DROP TABLE IF EXISTS user;

CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50),
    avatar VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(100),
    role VARCHAR(20) DEFAULT 'USER',
    status INT DEFAULT 1,
    balance DECIMAL(10,2) DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE mes_category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    icon VARCHAR(255),
    sort INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE mes_task (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_id BIGINT,
    seller_id BIGINT,
    title VARCHAR(120) NOT NULL,
    service_name VARCHAR(80) NOT NULL,
    store_name VARCHAR(80),
    vehicle_type VARCHAR(40),
    price DECIMAL(10,2) NOT NULL,
    stock INT DEFAULT 0,
    cover VARCHAR(255),
    description TEXT,
    booking_type VARCHAR(30),
    view_count INT DEFAULT 0,
    booked_count INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_mes_task_category ON mes_task(category_id);
CREATE INDEX idx_mes_task_seller ON mes_task(seller_id);

CREATE TABLE mes_work_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_no VARCHAR(50) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    service_id BIGINT NOT NULL,
    seller_id BIGINT NOT NULL,
    quantity INT DEFAULT 1,
    total_price DECIMAL(10,2),
    status INT DEFAULT 0,
    remark VARCHAR(255),
    appointment_date DATE NOT NULL,
    appointment_time TIME NOT NULL,
    plate_no VARCHAR(20) NOT NULL,
    vehicle_model VARCHAR(80) NOT NULL,
    fault_desc VARCHAR(500),
    pay_time DATETIME,
    finish_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_mes_work_order_user ON mes_work_order(user_id);
CREATE INDEX idx_mes_work_order_task ON mes_work_order(service_id);

CREATE TABLE review (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    service_id BIGINT NOT NULL,
    rating INT DEFAULT 5,
    content TEXT,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE favorite (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    service_id BIGINT NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_favorite_user_service UNIQUE (user_id, service_id)
);

CREATE TABLE complaint (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    target_user_id BIGINT,
    order_status_snapshot INT,
    type VARCHAR(30) DEFAULT 'ORDER',
    content TEXT,
    images VARCHAR(500),
    status INT DEFAULT 0,
    reply TEXT,
    reply_admin_id BIGINT,
    reply_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE announcement (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    content TEXT,
    admin_id BIGINT,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);
