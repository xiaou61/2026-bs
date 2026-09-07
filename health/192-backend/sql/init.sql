DROP DATABASE IF EXISTS hospital_care_192;
CREATE DATABASE hospital_care_192 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE hospital_care_192;

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
('admin', '123456', '系统管理员', 'ADMIN', '医院陪护服务中心', '13919200001', 'admin@hospitalcare192.local', 1, NOW(), NOW()),
('hospital', '123456', '院区管理员', 'HOSPITAL', '医院陪护服务中心', '13919200002', 'hospital@hospitalcare192.local', 1, NOW(), NOW()),
('coordinator', '123456', '陪护协调员', 'COORDINATOR', '医院陪护服务中心', '13919200003', 'coordinator@hospitalcare192.local', 1, NOW(), NOW()),
('caregiver', '123456', '护工人员', 'CAREGIVER', '医院陪护服务中心', '13919200004', 'caregiver@hospitalcare192.local', 1, NOW(), NOW()),
('finance', '123456', '结算人员', 'FINANCE', '医院陪护服务中心', '13919200005', 'finance@hospitalcare192.local', 1, NOW(), NOW()),
('family', '123456', '患者家属', 'FAMILY', '医院陪护服务中心', '13919200006', 'family@hospitalcare192.local', 1, NOW(), NOW());

CREATE TABLE hospital_ward (
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
INSERT INTO hospital_ward (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('192-01-001', '病区档案示例一', '科室类型', '病区负责人A', '2026-05-16 09:00', 'APPLIED', '病区编号、病区名称、科室类型、病区负责人、建档时间和病区状态维护', NOW(), NOW()),
('192-01-002', '病区档案示例二', '科室类型', '病区负责人B', '2026-05-17 14:00', 'APPROVED', '病区档案演示数据二', NOW(), NOW());

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
('192-02-001', '患者档案示例一', '护理等级', '联系人A', '2026-05-16 09:00', 'PENDING_REVIEW', '档案编号、患者姓名、护理等级、联系人、入院时间和陪护状态维护', NOW(), NOW()),
('192-02-002', '患者档案示例二', '护理等级', '联系人B', '2026-05-17 14:00', 'ASSIGNED', '患者档案演示数据二', NOW(), NOW());

CREATE TABLE caregiver_profile (
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
INSERT INTO caregiver_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('192-03-001', '护工档案示例一', '技能类别', '所属机构A', '2026-05-16 09:00', 'APPROVED', '护工编号、护工姓名、技能类别、所属机构、入职时间和在岗状态维护', NOW(), NOW()),
('192-03-002', '护工档案示例二', '技能类别', '所属机构B', '2026-05-17 14:00', 'SERVING', '护工档案演示数据二', NOW(), NOW());

CREATE TABLE care_appointment (
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
INSERT INTO care_appointment (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('192-04-001', '陪护预约示例一', '陪护类型', '申请人员A', '2026-05-16 09:00', 'ASSIGNED', '预约编号、服务患者、陪护类型、申请人员、预约时间和预约状态维护', NOW(), NOW()),
('192-04-002', '陪护预约示例二', '陪护类型', '申请人员B', '2026-05-17 14:00', 'TO_EVALUATE', '陪护预约演示数据二', NOW(), NOW());

CREATE TABLE appointment_review (
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
INSERT INTO appointment_review (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('192-05-001', '预约审核示例一', '审核类型', '审核人员A', '2026-05-16 09:00', 'SERVING', '审核编号、陪护预约、审核类型、审核人员、审核时间和审核状态维护', NOW(), NOW()),
('192-05-002', '预约审核示例二', '审核类型', '审核人员B', '2026-05-17 14:00', 'SETTLED', '预约审核演示数据二', NOW(), NOW());

CREATE TABLE caregiver_schedule (
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
INSERT INTO caregiver_schedule (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('192-06-001', '护工排班示例一', '排班班次', '排班人员A', '2026-05-16 09:00', 'TO_EVALUATE', '排班编号、护工姓名、排班班次、排班人员、排班时间和排班状态维护', NOW(), NOW()),
('192-06-002', '护工排班示例二', '排班班次', '排班人员B', '2026-05-17 14:00', 'REGISTERED', '护工排班演示数据二', NOW(), NOW());

CREATE TABLE service_assignment (
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
INSERT INTO service_assignment (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('192-07-001', '服务派单示例一', '服务类型', '护工人员A', '2026-05-16 09:00', 'SETTLED', '派单编号、预约订单、服务类型、护工人员、派单时间和派单状态维护', NOW(), NOW()),
('192-07-002', '服务派单示例二', '服务类型', '护工人员B', '2026-05-17 14:00', 'APPLIED', '服务派单演示数据二', NOW(), NOW());

CREATE TABLE care_service_record (
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
INSERT INTO care_service_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('192-08-001', '服务记录示例一', '服务项目', '记录人员A', '2026-05-16 09:00', 'REGISTERED', '记录编号、服务患者、服务项目、记录人员、服务时间和服务状态维护', NOW(), NOW()),
('192-08-002', '服务记录示例二', '服务项目', '记录人员B', '2026-05-17 14:00', 'PENDING_REVIEW', '服务记录演示数据二', NOW(), NOW());

CREATE TABLE family_communication (
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
INSERT INTO family_communication (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('192-09-001', '家属沟通示例一', '沟通类型', '沟通人员A', '2026-05-16 09:00', 'APPLIED', '沟通编号、服务患者、沟通类型、沟通人员、沟通时间和沟通状态维护', NOW(), NOW()),
('192-09-002', '家属沟通示例二', '沟通类型', '沟通人员B', '2026-05-17 14:00', 'APPROVED', '家属沟通演示数据二', NOW(), NOW());

CREATE TABLE care_evaluation (
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
INSERT INTO care_evaluation (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('192-10-001', '护工评价示例一', '评价类型', '评价人员A', '2026-05-16 09:00', 'PENDING_REVIEW', '评价编号、服务记录、评价类型、评价人员、评价时间和评价状态维护', NOW(), NOW()),
('192-10-002', '护工评价示例二', '评价类型', '评价人员B', '2026-05-17 14:00', 'ASSIGNED', '护工评价演示数据二', NOW(), NOW());

CREATE TABLE settlement_record (
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
INSERT INTO settlement_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('192-11-001', '评价结算示例一', '结算类型', '结算人员A', '2026-05-16 09:00', 'APPROVED', '结算编号、服务记录、结算类型、结算人员、结算时间和结算状态维护', NOW(), NOW()),
('192-11-002', '评价结算示例二', '结算类型', '结算人员B', '2026-05-17 14:00', 'SERVING', '评价结算演示数据二', NOW(), NOW());

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
('192-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'ASSIGNED', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('192-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'TO_EVALUATE', '操作日志演示数据二', NOW(), NOW());
