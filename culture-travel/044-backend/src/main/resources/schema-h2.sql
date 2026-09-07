CREATE TABLE sys_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50),
    phone VARCHAR(20),
    email VARCHAR(100),
    avatar VARCHAR(255),
    role TINYINT DEFAULT 0,
    status TINYINT DEFAULT 1,
    deleted TINYINT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE homestay (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    host_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    description CLOB,
    province VARCHAR(50),
    city VARCHAR(50),
    district VARCHAR(50),
    address VARCHAR(255),
    latitude DECIMAL(10,7),
    longitude DECIMAL(10,7),
    cover_image VARCHAR(255),
    images CLOB,
    min_price DECIMAL(10,2),
    max_guests INT DEFAULT 4,
    rating DECIMAL(2,1) DEFAULT 5.0,
    review_count INT DEFAULT 0,
    booking_count INT DEFAULT 0,
    status TINYINT DEFAULT 1,
    deleted TINYINT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_homestay_host_id ON homestay(host_id);
CREATE INDEX idx_homestay_city ON homestay(city);
CREATE INDEX idx_homestay_status ON homestay(status);

CREATE TABLE room_type (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    homestay_id BIGINT NOT NULL,
    name VARCHAR(50) NOT NULL,
    description CLOB,
    price DECIMAL(10,2) NOT NULL,
    area INT,
    max_guests INT DEFAULT 2,
    bed_type VARCHAR(50),
    total_count INT DEFAULT 1,
    images CLOB,
    status TINYINT DEFAULT 1,
    deleted TINYINT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_room_type_homestay_id ON room_type(homestay_id);

CREATE TABLE facility (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    icon VARCHAR(50),
    category VARCHAR(50),
    deleted TINYINT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE homestay_facility (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    homestay_id BIGINT NOT NULL,
    facility_id BIGINT NOT NULL,
    CONSTRAINT uk_homestay_facility UNIQUE (homestay_id, facility_id)
);

CREATE TABLE booking (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_no VARCHAR(50) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    homestay_id BIGINT NOT NULL,
    room_type_id BIGINT NOT NULL,
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL,
    nights INT NOT NULL,
    guests INT DEFAULT 1,
    total_price DECIMAL(10,2) NOT NULL,
    contact_name VARCHAR(50),
    contact_phone VARCHAR(20),
    remark CLOB,
    status TINYINT DEFAULT 0,
    cancel_reason VARCHAR(255),
    deleted TINYINT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_booking_user_id ON booking(user_id);
CREATE INDEX idx_booking_homestay_id ON booking(homestay_id);
CREATE INDEX idx_booking_status ON booking(status);
CREATE INDEX idx_booking_order_no ON booking(order_no);

CREATE TABLE review (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    booking_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    homestay_id BIGINT NOT NULL,
    rating TINYINT NOT NULL,
    content CLOB,
    images CLOB,
    host_reply CLOB,
    reply_time TIMESTAMP,
    deleted TINYINT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_review_homestay_id ON review(homestay_id);
CREATE INDEX idx_review_user_id ON review(user_id);

CREATE TABLE favorite (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    homestay_id BIGINT NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_user_homestay UNIQUE (user_id, homestay_id)
);
