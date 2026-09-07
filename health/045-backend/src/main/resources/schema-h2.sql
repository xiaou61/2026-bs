CREATE TABLE sys_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    real_name VARCHAR(50),
    phone VARCHAR(20),
    avatar VARCHAR(255),
    role TINYINT DEFAULT 0,
    elder_id BIGINT,
    status TINYINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);

CREATE TABLE building (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    floors INT DEFAULT 1,
    description VARCHAR(255),
    status TINYINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);

CREATE TABLE room (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    building_id BIGINT NOT NULL,
    room_no VARCHAR(20) NOT NULL,
    floor INT DEFAULT 1,
    room_type TINYINT DEFAULT 1,
    bed_count INT DEFAULT 1,
    price DECIMAL(10,2) DEFAULT 0,
    status TINYINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);

CREATE TABLE bed (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    room_id BIGINT NOT NULL,
    bed_no VARCHAR(20) NOT NULL,
    status TINYINT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);

CREATE TABLE elder (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    gender TINYINT DEFAULT 1,
    birthday DATE,
    id_card VARCHAR(18),
    phone VARCHAR(20),
    photo VARCHAR(255),
    bed_id BIGINT,
    care_level TINYINT DEFAULT 1,
    health_status VARCHAR(500),
    allergies VARCHAR(255),
    emergency_contact VARCHAR(50),
    emergency_phone VARCHAR(20),
    check_in_date DATE,
    check_out_date DATE,
    status TINYINT DEFAULT 1,
    remark VARCHAR(500),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);

CREATE TABLE health_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    elder_id BIGINT NOT NULL,
    record_date DATE NOT NULL,
    blood_pressure VARCHAR(20),
    heart_rate INT,
    temperature DECIMAL(3,1),
    blood_sugar DECIMAL(4,2),
    weight DECIMAL(5,2),
    sleep_hours DECIMAL(3,1),
    appetite TINYINT,
    mental_state TINYINT,
    medication VARCHAR(500),
    symptoms VARCHAR(500),
    recorder_id BIGINT,
    remark VARCHAR(500),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);

CREATE TABLE care_plan (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    elder_id BIGINT NOT NULL,
    plan_name VARCHAR(100) NOT NULL,
    care_content CLOB,
    frequency VARCHAR(50),
    start_date DATE,
    end_date DATE,
    status TINYINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);

CREATE TABLE care_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    elder_id BIGINT NOT NULL,
    care_plan_id BIGINT,
    care_type VARCHAR(50),
    care_content VARCHAR(500),
    care_time TIMESTAMP,
    caregiver_id BIGINT,
    remark VARCHAR(500),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);

CREATE TABLE fee_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    category TINYINT DEFAULT 1,
    price DECIMAL(10,2) DEFAULT 0,
    unit VARCHAR(20),
    description VARCHAR(255),
    status TINYINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);

CREATE TABLE bill (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    bill_no VARCHAR(50) NOT NULL UNIQUE,
    elder_id BIGINT NOT NULL,
    bill_month VARCHAR(7) NOT NULL,
    bed_fee DECIMAL(10,2) DEFAULT 0,
    care_fee DECIMAL(10,2) DEFAULT 0,
    meal_fee DECIMAL(10,2) DEFAULT 0,
    other_fee DECIMAL(10,2) DEFAULT 0,
    total_amount DECIMAL(10,2) DEFAULT 0,
    paid_amount DECIMAL(10,2) DEFAULT 0,
    status TINYINT DEFAULT 0,
    due_date DATE,
    remark VARCHAR(500),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);

CREATE TABLE payment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    payment_no VARCHAR(50) NOT NULL UNIQUE,
    bill_id BIGINT NOT NULL,
    elder_id BIGINT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    pay_method TINYINT DEFAULT 1,
    pay_time TIMESTAMP,
    operator_id BIGINT,
    remark VARCHAR(500),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);

CREATE TABLE visit (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    elder_id BIGINT NOT NULL,
    visitor_name VARCHAR(50) NOT NULL,
    visitor_phone VARCHAR(20),
    relationship VARCHAR(20),
    visit_date DATE NOT NULL,
    visit_time VARCHAR(20),
    visitor_count INT DEFAULT 1,
    purpose VARCHAR(255),
    status TINYINT DEFAULT 0,
    apply_user_id BIGINT,
    approve_user_id BIGINT,
    approve_time TIMESTAMP,
    approve_remark VARCHAR(255),
    remark VARCHAR(500),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);

CREATE TABLE notice (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    content CLOB,
    type TINYINT DEFAULT 1,
    target TINYINT DEFAULT 0,
    publisher_id BIGINT,
    publish_time TIMESTAMP,
    status TINYINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);
