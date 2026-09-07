DROP DATABASE IF EXISTS online_hospital_registration_db;
CREATE DATABASE online_hospital_registration_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE online_hospital_registration_db;

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

INSERT INTO sys_user (id, username, password, nickname, phone, email, gender, age, role, status, balance, create_time, update_time) VALUES
(1, 'admin', '123456', '系统管理员', '13800000001', 'admin@hospital.com', '男', 35, 'ADMIN', 1, 0.00, NOW(), NOW()),
(2, 'doctor01', '123456', '王主任', '13800000002', 'doctor01@hospital.com', '男', 45, 'DOCTOR', 1, 0.00, NOW(), NOW()),
(3, 'doctor02', '123456', '李医生', '13800000003', 'doctor02@hospital.com', '女', 39, 'DOCTOR', 1, 0.00, NOW(), NOW()),
(4, 'patient01', '123456', '张小明', '13800000004', 'patient01@hospital.com', '男', 28, 'PATIENT', 1, 0.00, NOW(), NOW()),
(5, 'patient02', '123456', '刘佳', '13800000005', 'patient02@hospital.com', '女', 31, 'PATIENT', 1, 0.00, NOW(), NOW());

INSERT INTO patient_profile (user_id, real_name, id_card, gender, age, blood_type, allergy_history, emergency_contact, emergency_phone, create_time, update_time) VALUES
(4, '张小明', '370101199801010011', '男', 28, 'A', '无', '张父', '13811110001', NOW(), NOW()),
(5, '刘佳', '370101199501010022', '女', 31, 'B', '青霉素过敏', '刘母', '13811110002', NOW(), NOW());

INSERT INTO medical_card (user_id, card_no, patient_name, phone, id_card, is_default, status, create_time) VALUES
(4, 'CARD2026001', '张小明', '13800000004', '370101199801010011', 1, 1, NOW()),
(5, 'CARD2026002', '刘佳', '13800000005', '370101199501010022', 1, 1, NOW());

INSERT INTO department_info (id, name, code, description, location, phone, sort, status, create_time, update_time) VALUES
(1, '内科', 'NK001', '负责常见内科疾病初诊与复诊', '门诊楼二层', '0531-82000001', 100, 1, NOW(), NOW()),
(2, '儿科', 'EK001', '提供儿童常见病与发热门诊服务', '门诊楼三层', '0531-82000002', 90, 1, NOW(), NOW()),
(3, '口腔科', 'KQ001', '口腔检查、洁牙与牙体修复', '门诊楼一层', '0531-82000003', 80, 1, NOW(), NOW());

INSERT INTO doctor_info (id, user_id, department_id, doctor_name, title, gender, specialty, introduction, avatar, consultation_fee, status, visit_count, create_time, update_time) VALUES
(1, 2, 1, '王建国', '主任医师', '男', '高血压、糖尿病、慢病管理', '从事内科临床工作二十余年，擅长慢病诊疗与综合评估。', 'https://dummyimage.com/120x120/5b8ff9/ffffff&text=W', 30.00, 1, 128, NOW(), NOW()),
(2, 3, 2, '李雪', '副主任医师', '女', '儿童呼吸道感染、过敏性疾病', '专注儿科门诊与儿童成长健康管理。', 'https://dummyimage.com/120x120/61d9a4/ffffff&text=L', 25.00, 1, 96, NOW(), NOW());

INSERT INTO doctor_schedule (id, doctor_id, department_id, schedule_date, time_slot, total_source, remaining_source, status, fee, clinic_room, create_time, update_time) VALUES
(1, 1, 1, DATE_ADD(CURDATE(), INTERVAL 1 DAY), '上午', 20, 18, 1, 30.00, '内科201', NOW(), NOW()),
(2, 1, 1, DATE_ADD(CURDATE(), INTERVAL 2 DAY), '下午', 15, 15, 1, 30.00, '内科202', NOW(), NOW()),
(3, 2, 2, DATE_ADD(CURDATE(), INTERVAL 1 DAY), '上午', 18, 17, 1, 25.00, '儿科301', NOW(), NOW()),
(4, 2, 2, DATE_ADD(CURDATE(), INTERVAL 3 DAY), '下午', 16, 16, 1, 25.00, '儿科302', NOW(), NOW());

INSERT INTO appointment_record (id, appointment_no, user_id, card_id, department_id, doctor_id, schedule_id, order_id, patient_name, contact_phone, remark, status, appointment_date, time_slot, amount, pay_time, finish_time, create_time, update_time) VALUES
(1, 'AP20260001', 4, 1, 1, 1, 1, 1, '张小明', '13800000004', '复诊高血压', 1, DATE_ADD(CURDATE(), INTERVAL 1 DAY), '上午', 30.00, NOW(), NULL, NOW(), NOW()),
(2, 'AP20260002', 5, 2, 2, 2, 3, 2, '刘佳', '13800000005', '儿童咳嗽咨询', 3, DATE_SUB(CURDATE(), INTERVAL 1 DAY), '上午', 25.00, DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY), NOW());

INSERT INTO payment_order (id, order_no, appointment_id, user_id, amount, status, payment_method, pay_time, create_time, update_time) VALUES
(1, 'OD20260001', 1, 4, 30.00, 1, '在线支付', NOW(), NOW(), NOW()),
(2, 'OD20260002', 2, 5, 25.00, 1, '在线支付', DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY), NOW());

INSERT INTO doctor_review (appointment_id, doctor_id, user_id, rating, content, status, patient_name, create_time) VALUES
(2, 2, 5, 5, '医生问诊细致，给出的建议很实用。', 1, '刘佳', NOW());

INSERT INTO news_notice (title, content, status, sort, admin_id, create_time, update_time) VALUES
('门诊就诊须知', '请患者提前15分钟到院签到，初诊患者请携带有效身份证件。', 1, 100, 1, NOW(), NOW()),
('五一假期排班安排', '假期期间内科与儿科正常接诊，口腔科实行预约制。', 1, 90, 1, NOW(), NOW());

INSERT INTO banner_info (title, image_url, link_url, description, sort, status, create_time, update_time) VALUES
('智慧门诊挂号', 'https://dummyimage.com/900x260/5b8ff9/ffffff&text=Online+Hospital', '/doctor-market', '在线选科室、选医生、选时间，一站式完成预约。', 100, 1, NOW(), NOW()),
('儿科专家门诊', 'https://dummyimage.com/900x260/61d9a4/ffffff&text=Pediatrics', '/doctor-market', '儿童发热、咳嗽、过敏等常见问题可快速挂号。', 90, 1, NOW(), NOW());

INSERT INTO operation_log (user_id, username, role, module_name, action_type, description, create_time) VALUES
(1, 'admin', 'ADMIN', '系统', '初始化', '初始化线上医院挂号演示数据', NOW());
