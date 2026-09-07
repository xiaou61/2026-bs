DROP TABLE IF EXISTS evaluate;
DROP TABLE IF EXISTS notice;
DROP TABLE IF EXISTS spare_part;
DROP TABLE IF EXISTS repair_process;
DROP TABLE IF EXISTS repair_order;
DROP TABLE IF EXISTS technician;
DROP TABLE IF EXISTS appliance_category;
DROP TABLE IF EXISTS user;

CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    real_name VARCHAR(50),
    phone VARCHAR(20),
    email VARCHAR(100),
    role VARCHAR(20) NOT NULL,
    avatar VARCHAR(255),
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE appliance_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    brand VARCHAR(100),
    sort INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE technician (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    name VARCHAR(50) NOT NULL,
    phone VARCHAR(20),
    skill_tags VARCHAR(255),
    service_area VARCHAR(255),
    level VARCHAR(20),
    work_status INT DEFAULT 1,
    rating DECIMAL(3,2) DEFAULT 5.00,
    order_count INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE repair_order (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_no VARCHAR(32) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    contact_name VARCHAR(50) NOT NULL,
    contact_phone VARCHAR(20) NOT NULL,
    province VARCHAR(50),
    city VARCHAR(50),
    district VARCHAR(50),
    address VARCHAR(255) NOT NULL,
    category_id BIGINT,
    brand VARCHAR(100),
    model VARCHAR(100),
    fault_desc TEXT,
    images TEXT,
    expect_time DATETIME,
    urgency INT DEFAULT 0,
    status INT DEFAULT 0,
    technician_id BIGINT,
    visit_time DATETIME,
    finish_time DATETIME,
    total_fee DECIMAL(10,2) DEFAULT 0,
    parts_fee DECIMAL(10,2) DEFAULT 0,
    labor_fee DECIMAL(10,2) DEFAULT 0,
    pay_status INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE repair_process (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    operator_id BIGINT,
    operator_role VARCHAR(20),
    node_type VARCHAR(30),
    content VARCHAR(500),
    images TEXT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE spare_part (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    part_code VARCHAR(50) NOT NULL UNIQUE,
    brand VARCHAR(100),
    specification VARCHAR(255),
    stock INT DEFAULT 0,
    unit_price DECIMAL(10,2) DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE notice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    type VARCHAR(30),
    status INT DEFAULT 0,
    publisher_id BIGINT,
    publish_time DATETIME,
    view_count INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE evaluate (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    technician_id BIGINT,
    score INT,
    attitude_score INT,
    quality_score INT,
    speed_score INT,
    content VARCHAR(500),
    images TEXT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);
