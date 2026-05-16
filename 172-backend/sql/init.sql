DROP DATABASE IF EXISTS dental_clinic_172;
CREATE DATABASE dental_clinic_172 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE dental_clinic_172;

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
('admin', '123456', '系统管理员', 'ADMIN', '口腔门诊诊疗中心', '13917200001', 'admin@dental172.local', 1, NOW(), NOW()),
('reception', '123456', '前台导诊', 'RECEPTION', '口腔门诊诊疗中心', '13917200002', 'reception@dental172.local', 1, NOW(), NOW()),
('dentist', '123456', '口腔医生', 'DENTIST', '口腔门诊诊疗中心', '13917200003', 'dentist@dental172.local', 1, NOW(), NOW()),
('nurse', '123456', '诊疗护士', 'NURSE', '口腔门诊诊疗中心', '13917200004', 'nurse@dental172.local', 1, NOW(), NOW()),
('cashier', '123456', '收费人员', 'CASHIER', '口腔门诊诊疗中心', '13917200005', 'cashier@dental172.local', 1, NOW(), NOW()),
('patient', '123456', '患者用户', 'PATIENT', '口腔门诊诊疗中心', '13917200006', 'patient@dental172.local', 1, NOW(), NOW());

CREATE TABLE clinic_room (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  record_name VARCHAR(120),
  category VARCHAR(120),
  owner_name VARCHAR(120),
  plan_time VARCHAR(120),
  status VARCHAR(40),
  remark VARCHAR(255),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO clinic_room (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('172-01-001', '诊室档案示例一', '诊室类型', '负责人员A', '2026-05-16 09:00', 'WAITING', '诊室编号、诊室名称、诊室类型、负责人员、启用时间和诊室状态维护', NOW(), NOW()),
('172-01-002', '诊室档案示例二', '诊室类型', '负责人员B', '2026-05-17 14:00', 'PROCESSING', '诊室档案演示数据二', NOW(), NOW());

CREATE TABLE dentist_profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  record_name VARCHAR(120),
  category VARCHAR(120),
  owner_name VARCHAR(120),
  plan_time VARCHAR(120),
  status VARCHAR(40),
  remark VARCHAR(255),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO dentist_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('172-02-001', '医生档案示例一', '擅长方向', '所属诊室A', '2026-05-16 09:00', 'CHECKED_IN', '医生编号、医生姓名、擅长方向、所属诊室、建档时间和医生状态维护', NOW(), NOW()),
('172-02-002', '医生档案示例二', '擅长方向', '所属诊室B', '2026-05-17 14:00', 'BILLING', '医生档案演示数据二', NOW(), NOW());

CREATE TABLE patient_profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  record_name VARCHAR(120),
  category VARCHAR(120),
  owner_name VARCHAR(120),
  plan_time VARCHAR(120),
  status VARCHAR(40),
  remark VARCHAR(255),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO patient_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('172-03-001', '患者档案示例一', '口腔问题', '接诊人员A', '2026-05-16 09:00', 'PROCESSING', '患者编号、患者姓名、口腔问题、接诊人员、建档时间和患者状态维护', NOW(), NOW()),
('172-03-002', '患者档案示例二', '口腔问题', '接诊人员B', '2026-05-17 14:00', 'FINISHED', '患者档案演示数据二', NOW(), NOW());

CREATE TABLE treatment_catalog (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  record_name VARCHAR(120),
  category VARCHAR(120),
  owner_name VARCHAR(120),
  plan_time VARCHAR(120),
  status VARCHAR(40),
  remark VARCHAR(255),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO treatment_catalog (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('172-04-001', '治疗项目示例一', '治疗类型', '负责医生A', '2026-05-16 09:00', 'BILLING', '项目编号、项目名称、治疗类型、负责医生、启用时间和项目状态维护', NOW(), NOW()),
('172-04-002', '治疗项目示例二', '治疗类型', '负责医生B', '2026-05-17 14:00', 'WARNING', '治疗项目演示数据二', NOW(), NOW());

CREATE TABLE appointment_order (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  record_name VARCHAR(120),
  category VARCHAR(120),
  owner_name VARCHAR(120),
  plan_time VARCHAR(120),
  status VARCHAR(40),
  remark VARCHAR(255),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO appointment_order (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('172-05-001', '预约诊疗示例一', '预约类型', '预约医生A', '2026-05-16 09:00', 'FINISHED', '预约编号、预约患者、预约类型、预约医生、预约时间和预约状态维护', NOW(), NOW()),
('172-05-002', '预约诊疗示例二', '预约类型', '预约医生B', '2026-05-17 14:00', 'PUBLISHED', '预约诊疗演示数据二', NOW(), NOW());

CREATE TABLE checkin_triage (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  record_name VARCHAR(120),
  category VARCHAR(120),
  owner_name VARCHAR(120),
  plan_time VARCHAR(120),
  status VARCHAR(40),
  remark VARCHAR(255),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO checkin_triage (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('172-06-001', '签到分诊示例一', '分诊类型', '导诊人员A', '2026-05-16 09:00', 'WARNING', '分诊编号、就诊患者、分诊类型、导诊人员、签到时间和分诊状态维护', NOW(), NOW()),
('172-06-002', '签到分诊示例二', '分诊类型', '导诊人员B', '2026-05-17 14:00', 'ACTIVE', '签到分诊演示数据二', NOW(), NOW());

CREATE TABLE treatment_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  record_name VARCHAR(120),
  category VARCHAR(120),
  owner_name VARCHAR(120),
  plan_time VARCHAR(120),
  status VARCHAR(40),
  remark VARCHAR(255),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO treatment_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('172-07-001', '治疗记录示例一', '治疗类型', '治疗医生A', '2026-05-16 09:00', 'PUBLISHED', '记录编号、治疗患者、治疗类型、治疗医生、治疗时间和治疗状态维护', NOW(), NOW()),
('172-07-002', '治疗记录示例二', '治疗类型', '治疗医生B', '2026-05-17 14:00', 'WAITING', '治疗记录演示数据二', NOW(), NOW());

CREATE TABLE consumable_catalog (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  record_name VARCHAR(120),
  category VARCHAR(120),
  owner_name VARCHAR(120),
  plan_time VARCHAR(120),
  status VARCHAR(40),
  remark VARCHAR(255),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO consumable_catalog (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('172-08-001', '耗材目录示例一', '耗材类别', '管理人员A', '2026-05-16 09:00', 'ACTIVE', '耗材编号、耗材名称、耗材类别、管理人员、启用时间和耗材状态维护', NOW(), NOW()),
('172-08-002', '耗材目录示例二', '耗材类别', '管理人员B', '2026-05-17 14:00', 'CHECKED_IN', '耗材目录演示数据二', NOW(), NOW());

CREATE TABLE consumable_stock (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  record_name VARCHAR(120),
  category VARCHAR(120),
  owner_name VARCHAR(120),
  plan_time VARCHAR(120),
  status VARCHAR(40),
  remark VARCHAR(255),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO consumable_stock (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('172-09-001', '耗材库存示例一', '库存类型', '库管人员A', '2026-05-16 09:00', 'WAITING', '库存编号、库存耗材、库存类型、库管人员、入库时间和库存状态维护', NOW(), NOW()),
('172-09-002', '耗材库存示例二', '库存类型', '库管人员B', '2026-05-17 14:00', 'PROCESSING', '耗材库存演示数据二', NOW(), NOW());

CREATE TABLE consumable_usage (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  record_name VARCHAR(120),
  category VARCHAR(120),
  owner_name VARCHAR(120),
  plan_time VARCHAR(120),
  status VARCHAR(40),
  remark VARCHAR(255),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO consumable_usage (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('172-10-001', '耗材使用示例一', '使用类型', '登记人员A', '2026-05-16 09:00', 'CHECKED_IN', '使用编号、使用耗材、使用类型、登记人员、使用时间和使用状态维护', NOW(), NOW()),
('172-10-002', '耗材使用示例二', '使用类型', '登记人员B', '2026-05-17 14:00', 'BILLING', '耗材使用演示数据二', NOW(), NOW());

CREATE TABLE billing_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  record_name VARCHAR(120),
  category VARCHAR(120),
  owner_name VARCHAR(120),
  plan_time VARCHAR(120),
  status VARCHAR(40),
  remark VARCHAR(255),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO billing_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('172-11-001', '费用结算示例一', '结算类型', '收费人员A', '2026-05-16 09:00', 'PROCESSING', '结算编号、结算患者、结算类型、收费人员、结算时间和结算状态维护', NOW(), NOW()),
('172-11-002', '费用结算示例二', '结算类型', '收费人员B', '2026-05-17 14:00', 'FINISHED', '费用结算演示数据二', NOW(), NOW());

CREATE TABLE operation_log (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  record_name VARCHAR(120),
  category VARCHAR(120),
  owner_name VARCHAR(120),
  plan_time VARCHAR(120),
  status VARCHAR(40),
  remark VARCHAR(255),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO operation_log (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('172-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'BILLING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('172-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'WARNING', '操作日志演示数据二', NOW(), NOW());
