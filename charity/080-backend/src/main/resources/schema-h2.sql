DROP TABLE IF EXISTS statistics;
DROP TABLE IF EXISTS sponsor_relation;
DROP TABLE IF EXISTS announcement;
DROP TABLE IF EXISTS growth_record;
DROP TABLE IF EXISTS feedback;
DROP TABLE IF EXISTS fund_record;
DROP TABLE IF EXISTS project;
DROP TABLE IF EXISTS donation;
DROP TABLE IF EXISTS application;
DROP TABLE IF EXISTS donor;
DROP TABLE IF EXISTS child;
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

CREATE TABLE child (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    gender VARCHAR(10),
    birth_date DATE,
    id_card VARCHAR(18),
    province VARCHAR(50),
    city VARCHAR(50),
    district VARCHAR(50),
    address VARCHAR(255),
    school VARCHAR(100),
    grade VARCHAR(20),
    family_situation TEXT,
    health_status VARCHAR(100),
    photo VARCHAR(255),
    sponsor_status INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE donor (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    donor_type VARCHAR(20),
    company_name VARCHAR(100),
    contact_person VARCHAR(50),
    contact_phone VARCHAR(20),
    total_amount DECIMAL(10,2) DEFAULT 0,
    donation_count INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE application (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    child_id BIGINT NOT NULL,
    apply_reason TEXT,
    required_amount DECIMAL(10,2),
    apply_status INT DEFAULT 0,
    reviewer_id BIGINT,
    review_time DATETIME,
    review_comment TEXT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE donation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    donor_id BIGINT NOT NULL,
    child_id BIGINT,
    project_id BIGINT,
    amount DECIMAL(10,2),
    donation_type VARCHAR(20),
    material_desc TEXT,
    donation_date DATE,
    payment_method VARCHAR(20),
    certificate_no VARCHAR(50),
    status INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE project (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    project_name VARCHAR(100) NOT NULL,
    project_desc TEXT,
    target_amount DECIMAL(10,2),
    current_amount DECIMAL(10,2) DEFAULT 0,
    start_date DATE,
    end_date DATE,
    project_status INT DEFAULT 0,
    cover_image VARCHAR(255),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE fund_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    record_type VARCHAR(20) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    related_id BIGINT,
    related_type VARCHAR(20),
    purpose VARCHAR(255),
    operator_id BIGINT,
    record_date DATE,
    remark TEXT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE feedback (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    feedback_type VARCHAR(20),
    child_id BIGINT,
    donor_id BIGINT,
    content TEXT,
    images TEXT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE growth_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    child_id BIGINT NOT NULL,
    record_date DATE,
    record_type VARCHAR(20),
    content TEXT,
    images TEXT,
    recorder_id BIGINT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE announcement (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    announcement_type VARCHAR(20),
    cover_image VARCHAR(255),
    publish_status INT DEFAULT 0,
    view_count INT DEFAULT 0,
    publisher_id BIGINT,
    publish_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE sponsor_relation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    child_id BIGINT NOT NULL,
    donor_id BIGINT NOT NULL,
    sponsor_type VARCHAR(20),
    sponsor_amount DECIMAL(10,2),
    start_date DATE,
    end_date DATE,
    status INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE statistics (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    stat_date DATE NOT NULL,
    total_children INT DEFAULT 0,
    sponsored_children INT DEFAULT 0,
    total_donors INT DEFAULT 0,
    total_amount DECIMAL(10,2) DEFAULT 0,
    total_projects INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);
