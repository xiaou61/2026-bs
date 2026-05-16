DROP DATABASE IF EXISTS outpatient_exam_150;
CREATE DATABASE outpatient_exam_150 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE outpatient_exam_150;

CREATE TABLE sys_user (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(60) NOT NULL UNIQUE,
  password VARCHAR(100) NOT NULL,
  nickname VARCHAR(80),
  role VARCHAR(40),
  department VARCHAR(100),
  phone VARCHAR(30),
  email VARCHAR(100),
  status INT DEFAULT 1,
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO sys_user (username, password, nickname, role, department, phone, email, status, created_time, updated_time) VALUES
('admin', '123456', '系统管理员', 'ADMIN', '门诊管理部', '13915000001', 'admin@outpatient.local', 1, NOW(), NOW()),
('doctor', '123456', '王晨医生', 'DOCTOR', '内科门诊', '13915000002', 'doctor@outpatient.local', 1, NOW(), NOW()),
('tech', '123456', '刘洋技师', 'TECHNICIAN', '影像科', '13915000003', 'tech@outpatient.local', 1, NOW(), NOW()),
('patient', '123456', '张敏患者', 'PATIENT', '患者服务', '13915000004', 'patient@outpatient.local', 1, NOW(), NOW());

CREATE TABLE exam_item (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  item_no VARCHAR(120),
  item_name VARCHAR(120),
  category VARCHAR(120),
  department_name VARCHAR(120),
  duration_minutes INT,
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO exam_item (item_no, item_name, category, department_name, duration_minutes, status, created_time, updated_time) VALUES
('EXM-150-001', '胸部CT', 'CT检查', '内科门诊', 30, 'ACTIVE', NOW(), NOW()),
('EXM-150-002', '腹部彩超', '超声检查', '消化内科', 20, 'ACTIVE', NOW(), NOW());

CREATE TABLE patient_profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  patient_no VARCHAR(120),
  patient_name VARCHAR(120),
  id_card VARCHAR(40),
  phone VARCHAR(30),
  visit_card_no VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO patient_profile (patient_no, patient_name, id_card, phone, visit_card_no, status, created_time, updated_time) VALUES
('PAT-150-001', '张敏', '320101199312120021', '13800000001', 'CARD-202605-001', 'NORMAL', NOW(), NOW()),
('PAT-150-002', '李强', '320101198904170034', '13800000002', 'CARD-202605-002', 'NORMAL', NOW(), NOW());

CREATE TABLE doctor_profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  doctor_no VARCHAR(120),
  doctor_name VARCHAR(120),
  phone VARCHAR(30),
  department_name VARCHAR(120),
  title_level VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO doctor_profile (doctor_no, doctor_name, phone, department_name, title_level, status, created_time, updated_time) VALUES
('DOC-150-001', '王晨', '13800000011', '内科门诊', '主治医师', 'ACTIVE', NOW(), NOW()),
('DOC-150-002', '周宁', '13800000012', '消化内科', '副主任医师', 'ACTIVE', NOW(), NOW());

CREATE TABLE exam_appointment (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  appointment_no VARCHAR(120),
  item_name VARCHAR(120),
  appointment_date VARCHAR(40),
  time_slot VARCHAR(80),
  doctor_name VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO exam_appointment (appointment_no, item_name, appointment_date, time_slot, doctor_name, status, created_time, updated_time) VALUES
('APT-150-001', '胸部CT', '2026-05-16', '09:00-09:30', '王晨', 'SUBMITTED', NOW(), NOW()),
('APT-150-002', '腹部彩超', '2026-05-16', '10:30-11:00', '周宁', 'APPROVED', NOW(), NOW());

CREATE TABLE exam_department (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  department_no VARCHAR(120),
  department_name VARCHAR(120),
  device_name VARCHAR(120),
  open_time VARCHAR(120),
  waiting_area VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO exam_department (department_no, department_name, device_name, open_time, waiting_area, status, created_time, updated_time) VALUES
('DEP-150-001', '影像科', 'CT一号机', '08:00-17:30', 'A区候诊', 'ACTIVE', NOW(), NOW()),
('DEP-150-002', '超声科', '彩超二号机', '08:00-17:30', 'B区候诊', 'ACTIVE', NOW(), NOW());

CREATE TABLE checkin_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  checkin_no VARCHAR(120),
  patient_name VARCHAR(120),
  checkin_method VARCHAR(120),
  checkin_window VARCHAR(120),
  checkin_time VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO checkin_record (checkin_no, patient_name, checkin_method, checkin_window, checkin_time, status, created_time, updated_time) VALUES
('CHK-150-001', '张敏', '自助机签到', '1号窗口', '2026-05-16 08:50', 'SUBMITTED', NOW(), NOW()),
('CHK-150-002', '李强', '人工窗口', '2号窗口', '2026-05-16 10:20', 'APPROVED', NOW(), NOW());

CREATE TABLE exam_report (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  report_no VARCHAR(120),
  patient_name VARCHAR(120),
  conclusion VARCHAR(255),
  completed_time VARCHAR(120),
  technician_name VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO exam_report (report_no, patient_name, conclusion, completed_time, technician_name, status, created_time, updated_time) VALUES
('REP-150-001', '张敏', '肺部影像未见明显异常', '2026-05-16 10:05', '刘洋', 'PROCESSING', NOW(), NOW()),
('REP-150-002', '李强', '提示轻度脂肪肝，建议随诊', '2026-05-16 11:10', '陈琳', 'FINISHED', NOW(), NOW());

CREATE TABLE abnormal_alert (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  alert_no VARCHAR(120),
  patient_name VARCHAR(120),
  alert_type VARCHAR(120),
  alert_time VARCHAR(120),
  handler_name VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO abnormal_alert (alert_no, patient_name, alert_type, alert_time, handler_name, status, created_time, updated_time) VALUES
('ALT-150-001', '张敏', '危急值提醒', '2026-05-16 10:08', '王晨', 'WARNING', NOW(), NOW()),
('ALT-150-002', '李强', '复查提醒', '2026-05-16 11:15', '周宁', 'PROCESSING', NOW(), NOW());

CREATE TABLE report_delivery (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  delivery_no VARCHAR(120),
  patient_name VARCHAR(120),
  delivery_method VARCHAR(120),
  delivery_time VARCHAR(120),
  receiver_name VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO report_delivery (delivery_no, patient_name, delivery_method, delivery_time, receiver_name, status, created_time, updated_time) VALUES
('DLV-150-001', '张敏', '短信通知', '2026-05-16 10:20', '张敏', 'PROCESSING', NOW(), NOW()),
('DLV-150-002', '李强', '自助打印', '2026-05-16 11:25', '李强', 'FINISHED', NOW(), NOW());

CREATE TABLE revisit_advice (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  advice_no VARCHAR(120),
  patient_name VARCHAR(120),
  advice_subject VARCHAR(120),
  advice_type VARCHAR(120),
  advice_time VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO revisit_advice (advice_no, patient_name, advice_subject, advice_type, advice_time, status, created_time, updated_time) VALUES
('ADV-150-001', '张敏', '肺部复查', '复诊提醒', '2026-05-16 10:30', 'PUBLISHED', NOW(), NOW()),
('ADV-150-002', '李强', '饮食控制', '生活干预', '2026-05-16 11:40', 'FINISHED', NOW(), NOW());

CREATE TABLE queue_call (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  queue_no VARCHAR(120),
  patient_name VARCHAR(120),
  call_time VARCHAR(120),
  waiting_status VARCHAR(120),
  room_no VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO queue_call (queue_no, patient_name, call_time, waiting_status, room_no, status, created_time, updated_time) VALUES
('QUE-150-001', '张敏', '2026-05-16 09:05', '候诊中', '101诊室', 'OPEN', NOW(), NOW()),
('QUE-150-002', '李强', '2026-05-16 10:35', '已叫号', '202诊室', 'PROCESSING', NOW(), NOW());

CREATE TABLE hospital_notice (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  notice_no VARCHAR(120),
  patient_name VARCHAR(120),
  notice_type VARCHAR(120),
  notice_content VARCHAR(255),
  receiver_name VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO hospital_notice (notice_no, patient_name, notice_type, notice_content, receiver_name, status, created_time, updated_time) VALUES
('NOT-150-001', '张敏', '检查提醒', '请提前15分钟到院签到并准备就诊卡', '张敏', 'PUBLISHED', NOW(), NOW()),
('NOT-150-002', '李强', '报告提醒', '检查报告已生成，可前往自助机打印', '李强', 'FINISHED', NOW(), NOW());

CREATE TABLE operation_log (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  operator_name VARCHAR(120),
  module_name VARCHAR(120),
  action_type VARCHAR(120),
  target_name VARCHAR(120),
  detail_info VARCHAR(255),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO operation_log (operator_name, module_name, action_type, target_name, detail_info, status, created_time, updated_time) VALUES
('系统管理员', '检查项目', '新增', '胸部CT', '新增门诊检查项目 EXM-150-001', 'SUCCESS', NOW(), NOW()),
('刘洋技师', '检查报告', '完成', 'REP-150-002', '完成李强的检查报告并回传患者端', 'SUCCESS', NOW(), NOW());
