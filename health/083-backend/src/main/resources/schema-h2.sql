CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    real_name VARCHAR(50),
    phone VARCHAR(20),
    role VARCHAR(20) NOT NULL,
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE elder_profile (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    gender VARCHAR(10),
    age INT,
    id_card VARCHAR(30),
    phone VARCHAR(20),
    address VARCHAR(255),
    blood_type VARCHAR(10),
    chronic_disease VARCHAR(500),
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE elder_medical_history (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    elder_id BIGINT NOT NULL,
    allergy_history VARCHAR(500),
    past_history VARCHAR(1000),
    family_history VARCHAR(1000),
    medication_history VARCHAR(1000),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE elder_contact (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    elder_id BIGINT NOT NULL,
    contact_name VARCHAR(50) NOT NULL,
    relation VARCHAR(30),
    contact_phone VARCHAR(20),
    address VARCHAR(255),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE check_item (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    item_code VARCHAR(50) NOT NULL UNIQUE,
    item_name VARCHAR(100) NOT NULL,
    unit VARCHAR(20),
    low_limit DECIMAL(10,2),
    high_limit DECIMAL(10,2),
    warning_level VARCHAR(20) DEFAULT 'medium',
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE check_package (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    package_name VARCHAR(100) NOT NULL,
    package_code VARCHAR(50) NOT NULL UNIQUE,
    price DECIMAL(10,2) DEFAULT 0,
    description VARCHAR(1000),
    suitable_crowd VARCHAR(100),
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE check_package_item (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    package_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    sort INT DEFAULT 0
);

CREATE TABLE check_appointment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    appointment_no VARCHAR(50) NOT NULL UNIQUE,
    elder_id BIGINT NOT NULL,
    package_id BIGINT NOT NULL,
    appointment_date DATE NOT NULL,
    slot_time VARCHAR(30),
    source VARCHAR(30),
    status INT DEFAULT 0,
    remark VARCHAR(500),
    create_by BIGINT,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE check_result (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    appointment_id BIGINT NOT NULL,
    elder_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    item_value VARCHAR(100),
    abnormal_flag INT DEFAULT 0,
    conclusion VARCHAR(500),
    doctor_id BIGINT,
    check_time TIMESTAMP,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE abnormal_warning (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    result_id BIGINT NOT NULL,
    elder_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    warning_level VARCHAR(20),
    warning_content VARCHAR(500),
    status INT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE follow_up_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    warning_id BIGINT,
    elder_id BIGINT NOT NULL,
    follow_date DATE,
    follow_method VARCHAR(30),
    follow_content VARCHAR(1000),
    doctor_id BIGINT,
    status INT DEFAULT 0,
    next_follow_date DATE,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE system_notice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content CLOB,
    type VARCHAR(30),
    status INT DEFAULT 0,
    publisher_id BIGINT,
    publish_time TIMESTAMP,
    view_count INT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
