CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50) NOT NULL,
    avatar VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(100),
    gender VARCHAR(10),
    age INT,
    role VARCHAR(20) NOT NULL,
    status INT NOT NULL DEFAULT 1,
    balance DECIMAL(10, 2) DEFAULT 0.00,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE patient_profile (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    real_name VARCHAR(50),
    id_card VARCHAR(30),
    gender VARCHAR(10),
    age INT,
    blood_type VARCHAR(20),
    allergy_history VARCHAR(255),
    emergency_contact VARCHAR(50),
    emergency_phone VARCHAR(20),
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE medical_card (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    card_no VARCHAR(50) NOT NULL UNIQUE,
    patient_name VARCHAR(50) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    id_card VARCHAR(30),
    is_default INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME
);

CREATE TABLE department_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    code VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    location VARCHAR(100),
    phone VARCHAR(20),
    sort INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE doctor_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    department_id BIGINT NOT NULL,
    doctor_name VARCHAR(50) NOT NULL,
    title VARCHAR(50),
    gender VARCHAR(10),
    specialty VARCHAR(255),
    introduction TEXT,
    avatar VARCHAR(255),
    consultation_fee DECIMAL(10, 2) DEFAULT 20.00,
    status INT DEFAULT 1,
    visit_count INT DEFAULT 0,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE doctor_schedule (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    doctor_id BIGINT NOT NULL,
    department_id BIGINT NOT NULL,
    schedule_date DATE NOT NULL,
    time_slot VARCHAR(20) NOT NULL,
    total_source INT NOT NULL,
    remaining_source INT NOT NULL,
    status INT DEFAULT 1,
    fee DECIMAL(10, 2) DEFAULT 20.00,
    clinic_room VARCHAR(50),
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE appointment_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    appointment_no VARCHAR(50) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    card_id BIGINT NOT NULL,
    department_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    schedule_id BIGINT NOT NULL,
    order_id BIGINT,
    patient_name VARCHAR(50) NOT NULL,
    contact_phone VARCHAR(20),
    remark VARCHAR(255),
    status INT DEFAULT 0,
    appointment_date DATE,
    time_slot VARCHAR(20),
    amount DECIMAL(10, 2),
    pay_time DATETIME,
    finish_time DATETIME,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE payment_order (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_no VARCHAR(50) NOT NULL UNIQUE,
    appointment_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    status INT DEFAULT 0,
    payment_method VARCHAR(50),
    pay_time DATETIME,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE doctor_review (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    appointment_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    rating INT NOT NULL,
    content VARCHAR(500),
    status INT DEFAULT 1,
    patient_name VARCHAR(50),
    create_time DATETIME
);

CREATE TABLE news_notice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    status INT DEFAULT 1,
    sort INT DEFAULT 0,
    admin_id BIGINT,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE banner_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    image_url VARCHAR(255),
    link_url VARCHAR(255),
    description VARCHAR(255),
    sort INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE operation_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    username VARCHAR(50),
    role VARCHAR(20),
    module_name VARCHAR(50),
    action_type VARCHAR(50),
    description VARCHAR(255),
    create_time DATETIME
);
