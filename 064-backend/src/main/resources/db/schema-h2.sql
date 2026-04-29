DROP TABLE IF EXISTS announcement;
DROP TABLE IF EXISTS favorite;
DROP TABLE IF EXISTS constitution_record;
DROP TABLE IF EXISTS service_order;
DROP TABLE IF EXISTS meal_plan;
DROP TABLE IF EXISTS formula_info;
DROP TABLE IF EXISTS ingredient;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS sys_user;

CREATE TABLE sys_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50),
    phone VARCHAR(20),
    email VARCHAR(100),
    role VARCHAR(20) DEFAULT 'USER',
    status INT DEFAULT 1,
    last_login_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    sort INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE ingredient (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category_id BIGINT NOT NULL,
    nature_taste VARCHAR(120),
    meridian VARCHAR(120),
    efficacy VARCHAR(500),
    suitable_people VARCHAR(255),
    taboo_people VARCHAR(255),
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_ingredient_category ON ingredient(category_id);
CREATE INDEX idx_ingredient_status ON ingredient(status);

CREATE TABLE formula_info (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    source VARCHAR(255),
    indication VARCHAR(500),
    composition TEXT,
    usage_method TEXT,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_formula_status ON formula_info(status);

CREATE TABLE meal_plan (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    formula_id BIGINT,
    ingredient_summary VARCHAR(500),
    suitable_constitution VARCHAR(120),
    meal_time VARCHAR(60),
    steps TEXT,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_plan_formula ON meal_plan(formula_id);
CREATE INDEX idx_plan_status ON meal_plan(status);

CREATE TABLE service_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_no VARCHAR(50) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    plan_id BIGINT NOT NULL,
    contact_name VARCHAR(50) NOT NULL,
    contact_phone VARCHAR(20) NOT NULL,
    appointment_date DATE NOT NULL,
    status INT DEFAULT 0,
    remark VARCHAR(255),
    admin_reply VARCHAR(255),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_service_user ON service_order(user_id);
CREATE INDEX idx_service_plan ON service_order(plan_id);
CREATE INDEX idx_service_status ON service_order(status);

CREATE TABLE constitution_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    constitution_type VARCHAR(100) NOT NULL,
    symptom_desc VARCHAR(500) NOT NULL,
    suggestion VARCHAR(500),
    status INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_constitution_user ON constitution_record(user_id);
CREATE INDEX idx_constitution_status ON constitution_record(status);

CREATE TABLE favorite (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    target_type VARCHAR(20) NOT NULL,
    target_id BIGINT NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX uk_favorite_user_target ON favorite(user_id, target_type, target_id);
CREATE INDEX idx_favorite_user ON favorite(user_id);

CREATE TABLE announcement (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    admin_id BIGINT,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);
