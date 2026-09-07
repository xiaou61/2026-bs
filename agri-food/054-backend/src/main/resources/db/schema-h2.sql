DROP TABLE IF EXISTS notice;
DROP TABLE IF EXISTS expert_appointment;
DROP TABLE IF EXISTS consultation_answer;
DROP TABLE IF EXISTS consultation;
DROP TABLE IF EXISTS material_record;
DROP TABLE IF EXISTS agricultural_material;
DROP TABLE IF EXISTS treatment_record;
DROP TABLE IF EXISTS pest_warning;
DROP TABLE IF EXISTS pest_disease;
DROP TABLE IF EXISTS production_task;
DROP TABLE IF EXISTS production_plan;
DROP TABLE IF EXISTS knowledge;
DROP TABLE IF EXISTS crop;
DROP TABLE IF EXISTS crop_category;
DROP TABLE IF EXISTS user;

CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    real_name VARCHAR(50),
    role VARCHAR(20) DEFAULT 'FARMER',
    phone VARCHAR(20),
    email VARCHAR(100),
    avatar VARCHAR(255),
    region VARCHAR(100),
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE crop_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    parent_id BIGINT DEFAULT 0,
    sort INT DEFAULT 0,
    description TEXT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE crop (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    category_id BIGINT,
    variety VARCHAR(100),
    growth_cycle INT,
    suitable_temp VARCHAR(50),
    suitable_soil VARCHAR(100),
    planting_season VARCHAR(50),
    yield_per_mu DECIMAL(10,2),
    description TEXT,
    image VARCHAR(255),
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE knowledge (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    category VARCHAR(50),
    content TEXT,
    images TEXT,
    tags VARCHAR(200),
    author_id BIGINT,
    view_count INT DEFAULT 0,
    like_count INT DEFAULT 0,
    collect_count INT DEFAULT 0,
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE production_plan (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    plan_name VARCHAR(200) NOT NULL,
    crop_id BIGINT,
    area DECIMAL(10,2),
    expected_yield DECIMAL(10,2),
    start_date DATE,
    end_date DATE,
    status TINYINT DEFAULT 0,
    progress INT DEFAULT 0,
    creator_id BIGINT,
    remark TEXT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE production_task (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    plan_id BIGINT,
    task_name VARCHAR(200) NOT NULL,
    task_type VARCHAR(50),
    assignee_id BIGINT,
    start_date DATE,
    end_date DATE,
    status TINYINT DEFAULT 0,
    completion_time DATETIME,
    remark TEXT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE pest_disease (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    type TINYINT,
    crop_type VARCHAR(100),
    symptom TEXT,
    harm TEXT,
    prevention TEXT,
    treatment TEXT,
    images TEXT,
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE pest_warning (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    pest_disease_id BIGINT,
    region VARCHAR(100),
    crop_type VARCHAR(100),
    warning_level TINYINT,
    warning_date DATE,
    content TEXT,
    publisher_id BIGINT,
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE treatment_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    plan_id BIGINT,
    pest_disease_id BIGINT,
    treatment_date DATE,
    method TEXT,
    medicine VARCHAR(200),
    dosage VARCHAR(100),
    effect TINYINT,
    cost DECIMAL(10,2),
    operator_id BIGINT,
    remark TEXT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE agricultural_material (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(50),
    specification VARCHAR(100),
    unit VARCHAR(20),
    stock INT DEFAULT 0,
    warning_stock INT DEFAULT 0,
    unit_price DECIMAL(10,2),
    supplier VARCHAR(100),
    description TEXT,
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE material_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    material_id BIGINT,
    type TINYINT,
    quantity INT,
    unit_price DECIMAL(10,2),
    total_price DECIMAL(10,2),
    purpose VARCHAR(200),
    operator_id BIGINT,
    record_date DATE,
    remark TEXT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE consultation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    images TEXT,
    crop_type VARCHAR(100),
    tags VARCHAR(200),
    questioner_id BIGINT,
    status TINYINT DEFAULT 0,
    view_count INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE consultation_answer (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    consultation_id BIGINT,
    content TEXT,
    images TEXT,
    answerer_id BIGINT,
    is_adopted TINYINT DEFAULT 0,
    like_count INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE expert_appointment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    expert_id BIGINT,
    farmer_id BIGINT,
    appointment_date DATE,
    appointment_time VARCHAR(50),
    topic VARCHAR(200),
    description TEXT,
    status TINYINT DEFAULT 0,
    rating TINYINT,
    feedback TEXT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE notice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    type VARCHAR(50),
    publisher_id BIGINT,
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);
