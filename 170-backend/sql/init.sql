DROP DATABASE IF EXISTS elder_care_170;
CREATE DATABASE elder_care_170 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE elder_care_170;

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
('admin', '$2a$12$XSruOTU6svqvpxQNjLnVfe3HKdoy9E5BKZAtWfG/b7m9L3Fa.kYXC', '系统管理员', 'ADMIN', '养老机构照护中心', '13917000001', 'admin@elder170.local', 1, NOW(), NOW()),
('admission', '$2a$12$XSruOTU6svqvpxQNjLnVfe3HKdoy9E5BKZAtWfG/b7m9L3Fa.kYXC', '入住管理员', 'ADMISSION', '养老机构照护中心', '13917000002', 'admission@elder170.local', 1, NOW(), NOW()),
('nursing', '$2a$12$XSruOTU6svqvpxQNjLnVfe3HKdoy9E5BKZAtWfG/b7m9L3Fa.kYXC', '护理主管', 'NURSING', '养老机构照护中心', '13917000003', 'nursing@elder170.local', 1, NOW(), NOW()),
('caregiver', '$2a$12$XSruOTU6svqvpxQNjLnVfe3HKdoy9E5BKZAtWfG/b7m9L3Fa.kYXC', '护理人员', 'CAREGIVER', '养老机构照护中心', '13917000004', 'caregiver@elder170.local', 1, NOW(), NOW()),
('family', '$2a$12$XSruOTU6svqvpxQNjLnVfe3HKdoy9E5BKZAtWfG/b7m9L3Fa.kYXC', '家属用户', 'FAMILY', '养老机构照护中心', '13917000005', 'family@elder170.local', 1, NOW(), NOW()),
('director', '$2a$12$XSruOTU6svqvpxQNjLnVfe3HKdoy9E5BKZAtWfG/b7m9L3Fa.kYXC', '院长管理员', 'DIRECTOR', '养老机构照护中心', '13917000006', 'director@elder170.local', 1, NOW(), NOW());

CREATE TABLE care_area (
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
INSERT INTO care_area (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('170-01-001', '护理区域示例一', '区域类型', '负责人员A', '2026-05-16 09:00', 'BOOKED', '区域编号、区域名称、区域类型、负责人员、启用时间和区域状态维护', NOW(), NOW()),
('170-01-002', '护理区域示例二', '区域类型', '负责人员B', '2026-05-17 14:00', 'VERIFIED', '护理区域演示数据二', NOW(), NOW());

CREATE TABLE room_profile (
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
INSERT INTO room_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('170-02-001', '房间档案示例一', '房间类型', '负责人员A', '2026-05-16 09:00', 'SCHEDULED', '房间编号、房间名称、房间类型、负责人员、启用时间和房间状态维护', NOW(), NOW()),
('170-02-002', '房间档案示例二', '房间类型', '负责人员B', '2026-05-17 14:00', 'PROCESSING', '房间档案演示数据二', NOW(), NOW());

CREATE TABLE bed_profile (
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
INSERT INTO bed_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('170-03-001', '床位档案示例一', '床位类型', '管理人员A', '2026-05-16 09:00', 'VERIFIED', '床位编号、床位名称、床位类型、管理人员、投用时间和床位状态维护', NOW(), NOW()),
('170-03-002', '床位档案示例二', '床位类型', '管理人员B', '2026-05-17 14:00', 'FINISHED', '床位档案演示数据二', NOW(), NOW());

CREATE TABLE elder_profile (
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
INSERT INTO elder_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('170-04-001', '老人档案示例一', '照护等级', '责任人员A', '2026-05-16 09:00', 'PROCESSING', '老人编号、老人姓名、照护等级、责任人员、建档时间和老人状态维护', NOW(), NOW()),
('170-04-002', '老人档案示例二', '照护等级', '责任人员B', '2026-05-17 14:00', 'WARNING', '老人档案演示数据二', NOW(), NOW());

CREATE TABLE family_profile (
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
INSERT INTO family_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('170-05-001', '家属档案示例一', '关联老人', '联系人员A', '2026-05-16 09:00', 'FINISHED', '家属编号、家属姓名、关联老人、联系人员、建档时间和家属状态维护', NOW(), NOW()),
('170-05-002', '家属档案示例二', '关联老人', '联系人员B', '2026-05-17 14:00', 'PUBLISHED', '家属档案演示数据二', NOW(), NOW());

CREATE TABLE checkin_assessment (
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
INSERT INTO checkin_assessment (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('170-06-001', '入住评估示例一', '评估类型', '评估人员A', '2026-05-16 09:00', 'WARNING', '评估编号、评估老人、评估类型、评估人员、评估时间和评估状态维护', NOW(), NOW()),
('170-06-002', '入住评估示例二', '评估类型', '评估人员B', '2026-05-17 14:00', 'ACTIVE', '入住评估演示数据二', NOW(), NOW());

CREATE TABLE bed_allocation (
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
INSERT INTO bed_allocation (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('170-07-001', '床位分配示例一', '分配类型', '办理人员A', '2026-05-16 09:00', 'PUBLISHED', '分配编号、分配床位、分配类型、办理人员、分配时间和分配状态维护', NOW(), NOW()),
('170-07-002', '床位分配示例二', '分配类型', '办理人员B', '2026-05-17 14:00', 'BOOKED', '床位分配演示数据二', NOW(), NOW());

CREATE TABLE care_plan (
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
INSERT INTO care_plan (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('170-08-001', '护理计划示例一', '计划类型', '护理主管A', '2026-05-16 09:00', 'ACTIVE', '计划编号、计划老人、计划类型、护理主管、计划时间和计划状态维护', NOW(), NOW()),
('170-08-002', '护理计划示例二', '计划类型', '护理主管B', '2026-05-17 14:00', 'SCHEDULED', '护理计划演示数据二', NOW(), NOW());

CREATE TABLE care_record (
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
INSERT INTO care_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('170-09-001', '护理记录示例一', '护理类型', '护理人员A', '2026-05-16 09:00', 'BOOKED', '记录编号、护理老人、护理类型、护理人员、护理时间和护理状态维护', NOW(), NOW()),
('170-09-002', '护理记录示例二', '护理类型', '护理人员B', '2026-05-17 14:00', 'VERIFIED', '护理记录演示数据二', NOW(), NOW());

CREATE TABLE family_query (
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
INSERT INTO family_query (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('170-10-001', '家属查询示例一', '查询类型', '家属人员A', '2026-05-16 09:00', 'SCHEDULED', '查询编号、查询老人、查询类型、家属人员、查询时间和查询状态维护', NOW(), NOW()),
('170-10-002', '家属查询示例二', '查询类型', '家属人员B', '2026-05-17 14:00', 'PROCESSING', '家属查询演示数据二', NOW(), NOW());

CREATE TABLE exception_report (
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
INSERT INTO exception_report (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('170-11-001', '异常上报示例一', '异常类型', '上报人员A', '2026-05-16 09:00', 'VERIFIED', '异常编号、异常老人、异常类型、上报人员、上报时间和异常状态维护', NOW(), NOW()),
('170-11-002', '异常上报示例二', '异常类型', '上报人员B', '2026-05-17 14:00', 'FINISHED', '异常上报演示数据二', NOW(), NOW());

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
('170-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'PROCESSING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('170-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'WARNING', '操作日志演示数据二', NOW(), NOW());
