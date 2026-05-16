DROP DATABASE IF EXISTS assistive_care_191;
CREATE DATABASE assistive_care_191 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE assistive_care_191;

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
('admin', '123456', '系统管理员', 'ADMIN', '社区助残服务中心', '13919100001', 'admin@assistivecare191.local', 1, NOW(), NOW()),
('community', '123456', '社区服务员', 'COMMUNITY', '社区助残服务中心', '13919100002', 'community@assistivecare191.local', 1, NOW(), NOW()),
('aidstaff', '123456', '器具管理员', 'AIDSTAFF', '社区助残服务中心', '13919100003', 'aidstaff@assistivecare191.local', 1, NOW(), NOW()),
('therapist', '123456', '康复治疗师', 'THERAPIST', '社区助残服务中心', '13919100004', 'therapist@assistivecare191.local', 1, NOW(), NOW()),
('volunteer', '123456', '随访志愿者', 'VOLUNTEER', '社区助残服务中心', '13919100005', 'volunteer@assistivecare191.local', 1, NOW(), NOW()),
('resident', '123456', '居民用户', 'RESIDENT', '社区助残服务中心', '13919100006', 'resident@assistivecare191.local', 1, NOW(), NOW());

CREATE TABLE service_center (
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
INSERT INTO service_center (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('191-01-001', '服务站点示例一', '服务区域', '站点负责人A', '2026-05-16 09:00', 'APPLIED', '站点编号、站点名称、服务区域、站点负责人、建档时间和站点状态维护', NOW(), NOW()),
('191-01-002', '服务站点示例二', '服务区域', '站点负责人B', '2026-05-17 14:00', 'APPROVED', '服务站点演示数据二', NOW(), NOW());

CREATE TABLE resident_profile (
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
INSERT INTO resident_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('191-02-001', '居民档案示例一', '需求类别', '联系人A', '2026-05-16 09:00', 'PENDING_REVIEW', '档案编号、居民姓名、需求类别、联系人、登记时间和服务状态维护', NOW(), NOW()),
('191-02-002', '居民档案示例二', '需求类别', '联系人B', '2026-05-17 14:00', 'BORROWING', '居民档案演示数据二', NOW(), NOW());

CREATE TABLE assistive_device (
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
INSERT INTO assistive_device (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('191-03-001', '助残器具示例一', '器具类型', '保管人员A', '2026-05-16 09:00', 'APPROVED', '器具编号、器具名称、器具类型、保管人员、入库时间和器具状态维护', NOW(), NOW()),
('191-03-002', '助残器具示例二', '器具类型', '保管人员B', '2026-05-17 14:00', 'FOLLOWING', '助残器具演示数据二', NOW(), NOW());

CREATE TABLE borrow_application (
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
INSERT INTO borrow_application (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('191-04-001', '器具借用示例一', '器具类型', '申请人员A', '2026-05-16 09:00', 'BORROWING', '借用编号、借用居民、器具类型、申请人员、申请时间和借用状态维护', NOW(), NOW()),
('191-04-002', '器具借用示例二', '器具类型', '申请人员B', '2026-05-17 14:00', 'TO_RECOVER', '器具借用演示数据二', NOW(), NOW());

CREATE TABLE borrow_approval (
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
INSERT INTO borrow_approval (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('191-05-001', '借用审核示例一', '审核类型', '审核人员A', '2026-05-16 09:00', 'FOLLOWING', '审核编号、借用申请、审核类型、审核人员、审核时间和审核状态维护', NOW(), NOW()),
('191-05-002', '借用审核示例二', '审核类型', '审核人员B', '2026-05-17 14:00', 'CLOSED', '借用审核演示数据二', NOW(), NOW());

CREATE TABLE device_delivery (
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
INSERT INTO device_delivery (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('191-06-001', '器具交付示例一', '交付方式', '交付人员A', '2026-05-16 09:00', 'TO_RECOVER', '交付编号、借用申请、交付方式、交付人员、交付时间和交付状态维护', NOW(), NOW()),
('191-06-002', '器具交付示例二', '交付方式', '交付人员B', '2026-05-17 14:00', 'REGISTERED', '器具交付演示数据二', NOW(), NOW());

CREATE TABLE rehab_plan (
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
INSERT INTO rehab_plan (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('191-07-001', '康复计划示例一', '康复类型', '治疗师A', '2026-05-16 09:00', 'CLOSED', '计划编号、服务居民、康复类型、治疗师、计划时间和计划状态维护', NOW(), NOW()),
('191-07-002', '康复计划示例二', '康复类型', '治疗师B', '2026-05-17 14:00', 'APPLIED', '康复计划演示数据二', NOW(), NOW());

CREATE TABLE rehab_training (
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
INSERT INTO rehab_training (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('191-08-001', '康复训练示例一', '训练项目', '指导人员A', '2026-05-16 09:00', 'REGISTERED', '训练编号、康复计划、训练项目、指导人员、训练时间和训练状态维护', NOW(), NOW()),
('191-08-002', '康复训练示例二', '训练项目', '指导人员B', '2026-05-17 14:00', 'PENDING_REVIEW', '康复训练演示数据二', NOW(), NOW());

CREATE TABLE followup_record (
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
INSERT INTO followup_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('191-09-001', '随访记录示例一', '随访类型', '随访人员A', '2026-05-16 09:00', 'APPLIED', '随访编号、随访居民、随访类型、随访人员、随访时间和随访状态维护', NOW(), NOW()),
('191-09-002', '随访记录示例二', '随访类型', '随访人员B', '2026-05-17 14:00', 'APPROVED', '随访记录演示数据二', NOW(), NOW());

CREATE TABLE recovery_reminder (
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
INSERT INTO recovery_reminder (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('191-10-001', '回收提醒示例一', '提醒类型', '跟进人员A', '2026-05-16 09:00', 'PENDING_REVIEW', '提醒编号、借用器具、提醒类型、跟进人员、提醒时间和回收状态维护', NOW(), NOW()),
('191-10-002', '回收提醒示例二', '提醒类型', '跟进人员B', '2026-05-17 14:00', 'BORROWING', '回收提醒演示数据二', NOW(), NOW());

CREATE TABLE device_maintenance (
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
INSERT INTO device_maintenance (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('191-11-001', '器具维护示例一', '维护类型', '维护人员A', '2026-05-16 09:00', 'APPROVED', '维护编号、维护器具、维护类型、维护人员、维护时间和维护状态维护', NOW(), NOW()),
('191-11-002', '器具维护示例二', '维护类型', '维护人员B', '2026-05-17 14:00', 'FOLLOWING', '器具维护演示数据二', NOW(), NOW());

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
('191-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'BORROWING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('191-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'TO_RECOVER', '操作日志演示数据二', NOW(), NOW());
