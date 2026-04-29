DROP TABLE IF EXISTS maintenance_record;
DROP TABLE IF EXISTS maintenance_plan;
DROP TABLE IF EXISTS quality_inspection;
DROP TABLE IF EXISTS material_stock_record;
DROP TABLE IF EXISTS material;
DROP TABLE IF EXISTS production_order;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS alert_record;
DROP TABLE IF EXISTS sensor_data;
DROP TABLE IF EXISTS equipment;
DROP TABLE IF EXISTS equipment_category;
DROP TABLE IF EXISTS user;

CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    real_name VARCHAR(50),
    phone VARCHAR(20),
    role VARCHAR(20) NOT NULL DEFAULT 'operator',
    status INT NOT NULL DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE equipment_category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE equipment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    category_id BIGINT,
    model VARCHAR(100),
    manufacturer VARCHAR(100),
    purchase_date DATE,
    location VARCHAR(200),
    status VARCHAR(20) NOT NULL DEFAULT 'stopped',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE sensor_data (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    equipment_id BIGINT NOT NULL,
    temperature DECIMAL(10,2),
    pressure DECIMAL(10,2),
    vibration DECIMAL(10,2),
    speed DECIMAL(10,2),
    collect_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE alert_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    equipment_id BIGINT NOT NULL,
    alert_type VARCHAR(50),
    alert_level VARCHAR(20) NOT NULL DEFAULT 'normal',
    content VARCHAR(500),
    status INT NOT NULL DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    handle_time DATETIME
);

CREATE TABLE product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    spec VARCHAR(200),
    unit VARCHAR(20),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE production_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_no VARCHAR(50) NOT NULL UNIQUE,
    product_id BIGINT,
    equipment_id BIGINT,
    plan_quantity INT DEFAULT 0,
    actual_quantity INT DEFAULT 0,
    status VARCHAR(20) NOT NULL DEFAULT 'pending',
    plan_start_date DATE,
    plan_end_date DATE,
    actual_start_date DATE,
    actual_end_date DATE,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE material (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    spec VARCHAR(200),
    unit VARCHAR(20),
    stock_quantity DECIMAL(10,2) DEFAULT 0,
    safe_stock DECIMAL(10,2) DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE material_stock_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    material_id BIGINT NOT NULL,
    type VARCHAR(10) NOT NULL,
    quantity DECIMAL(10,2) NOT NULL,
    reason VARCHAR(200),
    operator VARCHAR(50),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE quality_inspection (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT,
    product_id BIGINT,
    inspect_quantity INT DEFAULT 0,
    qualified_quantity INT DEFAULT 0,
    unqualified_quantity INT DEFAULT 0,
    result VARCHAR(20) DEFAULT 'passed',
    inspector VARCHAR(50),
    remark VARCHAR(500),
    inspect_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE maintenance_plan (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    equipment_id BIGINT NOT NULL,
    plan_name VARCHAR(100) NOT NULL,
    cycle_days INT DEFAULT 30,
    last_maintain_date DATE,
    next_maintain_date DATE,
    content VARCHAR(500),
    status VARCHAR(20) NOT NULL DEFAULT 'active',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE maintenance_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    plan_id BIGINT,
    equipment_id BIGINT NOT NULL,
    maintain_type VARCHAR(20) DEFAULT 'regular',
    content VARCHAR(500),
    maintainer VARCHAR(50),
    cost DECIMAL(10,2) DEFAULT 0,
    status VARCHAR(20) NOT NULL DEFAULT 'pending',
    start_time DATETIME,
    end_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);
