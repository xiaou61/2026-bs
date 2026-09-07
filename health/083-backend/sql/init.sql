DROP DATABASE IF EXISTS eldercare_db;
CREATE DATABASE eldercare_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE eldercare_db;

CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    real_name VARCHAR(50),
    phone VARCHAR(20),
    role VARCHAR(20) NOT NULL,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
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
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE elder_medical_history (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    elder_id BIGINT NOT NULL,
    allergy_history VARCHAR(500),
    past_history VARCHAR(1000),
    family_history VARCHAR(1000),
    medication_history VARCHAR(1000),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE elder_contact (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    elder_id BIGINT NOT NULL,
    contact_name VARCHAR(50) NOT NULL,
    relation VARCHAR(30),
    contact_phone VARCHAR(20),
    address VARCHAR(255),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
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
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE check_package (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    package_name VARCHAR(100) NOT NULL,
    package_code VARCHAR(50) NOT NULL UNIQUE,
    price DECIMAL(10,2) DEFAULT 0,
    description VARCHAR(1000),
    suitable_crowd VARCHAR(100),
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
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
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
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
    check_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE abnormal_warning (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    result_id BIGINT NOT NULL,
    elder_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    warning_level VARCHAR(20),
    warning_content VARCHAR(500),
    status INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
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
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE system_notice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    type VARCHAR(30),
    status INT DEFAULT 0,
    publisher_id BIGINT,
    publish_time DATETIME,
    view_count INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO sys_user (username, password, real_name, phone, role, status) VALUES
('admin', '123456', '系统管理员', '13800000001', 'admin', 1),
('doctor', '123456', '王医生', '13800000002', 'doctor', 1),
('nurse', '123456', '张护士', '13800000003', 'nurse', 1),
('reception', '123456', '李前台', '13800000004', 'reception', 1);

INSERT INTO elder_profile (name, gender, age, id_card, phone, address, blood_type, chronic_disease, status) VALUES
('陈建国', '男', 68, '110101195801011234', '13911110001', '朝阳区建国路88号', 'A', '高血压', 1),
('刘桂芳', '女', 72, '110101195402023456', '13911110002', '海淀区知春路66号', 'B', '糖尿病', 1),
('王国强', '男', 65, '110101196001033333', '13911110003', '丰台区东大街12号', 'O', '无', 1);

INSERT INTO elder_medical_history (elder_id, allergy_history, past_history, family_history, medication_history) VALUES
(1, '青霉素过敏', '2018年冠脉支架', '父亲高血压', '缬沙坦每日一次'),
(2, '无', '2型糖尿病10年', '母亲糖尿病', '二甲双胍每日两次');

INSERT INTO elder_contact (elder_id, contact_name, relation, contact_phone, address) VALUES
(1, '陈晓明', '儿子', '13888880001', '朝阳区建国路88号'),
(2, '周婷', '女儿', '13888880002', '海淀区知春路66号');

INSERT INTO check_item (item_code, item_name, unit, low_limit, high_limit, warning_level, status) VALUES
('GLU', '空腹血糖', 'mmol/L', 3.90, 6.10, 'high', 1),
('SBP', '收缩压', 'mmHg', 90.00, 140.00, 'high', 1),
('DBP', '舒张压', 'mmHg', 60.00, 90.00, 'medium', 1),
('BMI', '体质指数', 'kg/m2', 18.50, 24.00, 'low', 1);

INSERT INTO check_package (package_name, package_code, price, description, suitable_crowd, status) VALUES
('基础老年体检套餐', 'PKG_BASIC', 199.00, '常规血压、血糖、身高体重检查', '60岁以上老人', 1),
('慢病专项筛查套餐', 'PKG_CHRONIC', 399.00, '适用于高血压糖尿病慢病人群', '慢病老人', 1);

INSERT INTO check_package_item (package_id, item_id, sort) VALUES
(1, 1, 1),
(1, 2, 2),
(1, 3, 3),
(1, 4, 4),
(2, 1, 1),
(2, 2, 2),
(2, 3, 3);

INSERT INTO check_appointment (appointment_no, elder_id, package_id, appointment_date, slot_time, source, status, remark, create_by) VALUES
('AP20260310001', 1, 1, '2026-03-10', '上午', '线下窗口', 2, '需优先安排', 4),
('AP20260310002', 2, 2, '2026-03-10', '下午', '电话预约', 1, '家属陪同', 4),
('AP20260311001', 3, 1, '2026-03-11', '上午', '社区活动', 0, '', 4);

INSERT INTO check_result (appointment_id, elder_id, item_id, item_value, abnormal_flag, conclusion, doctor_id, check_time) VALUES
(1, 1, 1, '7.2', 1, '血糖偏高，建议复查', 2, '2026-03-10 09:30:00'),
(1, 1, 2, '145', 1, '血压偏高，建议监测', 2, '2026-03-10 09:35:00'),
(1, 1, 3, '88', 0, '正常范围', 2, '2026-03-10 09:35:00'),
(2, 2, 1, '6.0', 0, '正常范围', 2, '2026-03-10 15:10:00');

INSERT INTO abnormal_warning (result_id, elder_id, item_id, warning_level, warning_content, status) VALUES
(1, 1, 1, 'high', '空腹血糖指标异常: 7.2', 1),
(2, 1, 2, 'high', '收缩压指标异常: 145', 0);

INSERT INTO follow_up_record (warning_id, elder_id, follow_date, follow_method, follow_content, doctor_id, status, next_follow_date) VALUES
(1, 1, '2026-03-10', '电话', '建议减少高糖饮食，一周后复检血糖', 2, 1, '2026-03-17');

INSERT INTO system_notice (title, content, type, status, publisher_id, publish_time, view_count) VALUES
('三月老年健康义诊通知', '本周六上午开展老年健康义诊活动，欢迎预约参与。', 'activity', 1, 1, '2026-03-09 10:00:00', 25),
('体检系统升级公告', '系统将于周日凌晨进行升级维护。', 'system', 1, 1, '2026-03-09 18:00:00', 18);
