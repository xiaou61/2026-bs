CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    real_name VARCHAR(50) NOT NULL,
    phone VARCHAR(20),
    role VARCHAR(20) NOT NULL,
    status TINYINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE adopter_profile (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    gender TINYINT,
    id_card VARCHAR(30),
    marital_status VARCHAR(20),
    birth_date DATE,
    province VARCHAR(50),
    city VARCHAR(50),
    address VARCHAR(255),
    occupation VARCHAR(100),
    income_level VARCHAR(50),
    family_info TEXT,
    adoption_reason TEXT,
    status TINYINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE child_profile (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    child_no VARCHAR(32) NOT NULL UNIQUE,
    name VARCHAR(50) NOT NULL,
    gender TINYINT,
    birth_date DATE,
    age INT,
    status TINYINT DEFAULT 1,
    adoption_status TINYINT DEFAULT 0,
    avatar_url VARCHAR(255),
    summary TEXT,
    guardian_info VARCHAR(255),
    admission_date DATE,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE child_health_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    child_id BIGINT NOT NULL,
    health_status VARCHAR(100),
    blood_type VARCHAR(20),
    allergy_info VARCHAR(255),
    vaccination_info VARCHAR(255),
    medical_history TEXT,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE adoption_application (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    application_no VARCHAR(32) NOT NULL UNIQUE,
    child_id BIGINT NOT NULL,
    applicant_id BIGINT NOT NULL,
    status TINYINT DEFAULT 1,
    reason TEXT,
    expected_contact_date DATE,
    review_remark VARCHAR(255),
    submit_time TIMESTAMP,
    review_time TIMESTAMP,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE application_material (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    application_id BIGINT NOT NULL,
    material_type VARCHAR(50),
    material_name VARCHAR(100),
    file_url VARCHAR(255),
    review_status TINYINT DEFAULT 0,
    remark VARCHAR(255),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE home_visit_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    application_id BIGINT NOT NULL,
    reviewer_id BIGINT NOT NULL,
    visit_date DATE,
    visit_result VARCHAR(50),
    visit_remark TEXT,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE match_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    application_id BIGINT NOT NULL,
    child_id BIGINT NOT NULL,
    reviewer_id BIGINT NOT NULL,
    match_score INT,
    status TINYINT DEFAULT 0,
    match_remark VARCHAR(255),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE approval_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    application_id BIGINT NOT NULL,
    node_name VARCHAR(50),
    approval_status TINYINT DEFAULT 0,
    approval_remark VARCHAR(255),
    approver_id BIGINT,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE adoption_agreement (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    agreement_no VARCHAR(32) NOT NULL UNIQUE,
    application_id BIGINT NOT NULL,
    child_id BIGINT NOT NULL,
    applicant_id BIGINT NOT NULL,
    sign_status TINYINT DEFAULT 0,
    sign_date DATE,
    agreement_content TEXT,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE follow_up_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    agreement_id BIGINT NOT NULL,
    application_id BIGINT NOT NULL,
    follow_date DATE,
    follow_status VARCHAR(50),
    follow_remark TEXT,
    next_follow_date DATE,
    reviewer_id BIGINT NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE system_notice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    type VARCHAR(30),
    content TEXT,
    status TINYINT DEFAULT 1,
    publish_time TIMESTAMP,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
