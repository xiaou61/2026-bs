CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    real_name VARCHAR(50),
    phone VARCHAR(20),
    avatar VARCHAR(255),
    role VARCHAR(20) NOT NULL DEFAULT 'REPORTER',
    status TINYINT NOT NULL DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE disaster (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    type VARCHAR(20) NOT NULL,
    level TINYINT NOT NULL DEFAULT 1,
    province VARCHAR(50),
    city VARCHAR(50),
    district VARCHAR(50),
    address VARCHAR(200),
    longitude DECIMAL(10,6),
    latitude DECIMAL(10,6),
    affected_count INT DEFAULT 0,
    description TEXT,
    images VARCHAR(1000),
    status TINYINT NOT NULL DEFAULT 0,
    reporter_id BIGINT,
    report_time TIMESTAMP,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE material_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    icon VARCHAR(255),
    sort INT DEFAULT 0,
    status TINYINT NOT NULL DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE material (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    category_id BIGINT,
    name VARCHAR(100) NOT NULL,
    spec VARCHAR(100),
    unit VARCHAR(20),
    image VARCHAR(255),
    safe_stock INT DEFAULT 0,
    description VARCHAR(500),
    status TINYINT NOT NULL DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE warehouse (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50),
    address VARCHAR(200),
    longitude DECIMAL(10,6),
    latitude DECIMAL(10,6),
    capacity INT DEFAULT 0,
    manager_id BIGINT,
    phone VARCHAR(20),
    status TINYINT NOT NULL DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE stock (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    warehouse_id BIGINT NOT NULL,
    material_id BIGINT NOT NULL,
    quantity INT DEFAULT 0,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_warehouse_material UNIQUE (warehouse_id, material_id)
);

CREATE TABLE stock_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    warehouse_id BIGINT NOT NULL,
    material_id BIGINT NOT NULL,
    type TINYINT NOT NULL,
    quantity INT NOT NULL,
    before_quantity INT DEFAULT 0,
    after_quantity INT DEFAULT 0,
    source VARCHAR(200),
    operator_id BIGINT,
    remark VARCHAR(500),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE dispatch (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    dispatch_no VARCHAR(50) NOT NULL UNIQUE,
    disaster_id BIGINT,
    from_warehouse_id BIGINT,
    to_address VARCHAR(200),
    to_longitude DECIMAL(10,6),
    to_latitude DECIMAL(10,6),
    priority TINYINT DEFAULT 1,
    status TINYINT DEFAULT 0,
    applicant_id BIGINT,
    approver_id BIGINT,
    approve_time TIMESTAMP,
    approve_remark VARCHAR(500),
    complete_time TIMESTAMP,
    remark VARCHAR(500),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE dispatch_item (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    dispatch_id BIGINT NOT NULL,
    material_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE rescue_task (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    task_no VARCHAR(50) NOT NULL UNIQUE,
    disaster_id BIGINT,
    title VARCHAR(100) NOT NULL,
    content TEXT,
    priority TINYINT DEFAULT 1,
    status TINYINT DEFAULT 0,
    assignee_id BIGINT,
    publisher_id BIGINT,
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    feedback TEXT,
    feedback_images VARCHAR(1000),
    feedback_time TIMESTAMP,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE notice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    content TEXT,
    type TINYINT DEFAULT 1,
    status TINYINT DEFAULT 0,
    publisher_id BIGINT,
    publish_time TIMESTAMP,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE operation_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    username VARCHAR(50),
    module VARCHAR(50),
    operation VARCHAR(100),
    method VARCHAR(200),
    params TEXT,
    ip VARCHAR(50),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
