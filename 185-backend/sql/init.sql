DROP DATABASE IF EXISTS city_toilet_185;
CREATE DATABASE city_toilet_185 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE city_toilet_185;

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
('admin', '$2a$12$G1jLaJML7RMk2sfht9AuK.F0qsBrqLja78eu2TUTCJbMzFD0gKo16', '系统管理员', 'ADMIN', '城市环卫服务中心', '13918500001', 'admin@citytoilet185.local', 1, NOW(), NOW()),
('sanitation', '$2a$12$G1jLaJML7RMk2sfht9AuK.F0qsBrqLja78eu2TUTCJbMzFD0gKo16', '环卫管理员', 'SANITATION', '城市环卫服务中心', '13918500002', 'sanitation@citytoilet185.local', 1, NOW(), NOW()),
('cleaner', '$2a$12$G1jLaJML7RMk2sfht9AuK.F0qsBrqLja78eu2TUTCJbMzFD0gKo16', '保洁员', 'CLEANER', '城市环卫服务中心', '13918500003', 'cleaner@citytoilet185.local', 1, NOW(), NOW()),
('supply', '$2a$12$G1jLaJML7RMk2sfht9AuK.F0qsBrqLja78eu2TUTCJbMzFD0gKo16', '补给员', 'SUPPLY', '城市环卫服务中心', '13918500004', 'supply@citytoilet185.local', 1, NOW(), NOW()),
('inspector', '$2a$12$G1jLaJML7RMk2sfht9AuK.F0qsBrqLja78eu2TUTCJbMzFD0gKo16', '巡检员', 'INSPECTOR', '城市环卫服务中心', '13918500005', 'inspector@citytoilet185.local', 1, NOW(), NOW()),
('citizen', '$2a$12$G1jLaJML7RMk2sfht9AuK.F0qsBrqLja78eu2TUTCJbMzFD0gKo16', '市民监督', 'CITIZEN', '城市环卫服务中心', '13918500006', 'citizen@citytoilet185.local', 1, NOW(), NOW());

CREATE TABLE toilet_site (
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
INSERT INTO toilet_site (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('185-01-001', '公厕点位示例一', '区域类型', '负责人员A', '2026-05-16 09:00', 'ORDERED', '点位编号、点位名称、区域类型、负责人员、启用时间和点位状态维护', NOW(), NOW()),
('185-01-002', '公厕点位示例二', '区域类型', '负责人员B', '2026-05-17 14:00', 'COOKING', '公厕点位演示数据二', NOW(), NOW());

CREATE TABLE cleaning_route (
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
INSERT INTO cleaning_route (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('185-02-001', '保洁路线示例一', '路线类型', '保洁人员A', '2026-05-16 09:00', 'SCHEDULED', '路线编号、覆盖区域、路线类型、保洁人员、计划时间和路线状态维护', NOW(), NOW()),
('185-02-002', '保洁路线示例二', '路线类型', '保洁人员B', '2026-05-17 14:00', 'DELIVERING', '保洁路线演示数据二', NOW(), NOW());

CREATE TABLE cleaning_task (
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
INSERT INTO cleaning_task (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('185-03-001', '保洁任务示例一', '任务类型', '保洁人员A', '2026-05-16 09:00', 'COOKING', '任务编号、公厕点位、任务类型、保洁人员、派发时间和任务状态维护', NOW(), NOW()),
('185-03-002', '保洁任务示例二', '任务类型', '保洁人员B', '2026-05-17 14:00', 'SIGNED', '保洁任务演示数据二', NOW(), NOW());

CREATE TABLE cleaning_record (
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
INSERT INTO cleaning_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('185-04-001', '保洁记录示例一', '记录类型', '保洁人员A', '2026-05-16 09:00', 'DELIVERING', '记录编号、保洁任务、记录类型、保洁人员、完成时间和记录状态维护', NOW(), NOW()),
('185-04-002', '保洁记录示例二', '记录类型', '保洁人员B', '2026-05-17 14:00', 'ANALYZED', '保洁记录演示数据二', NOW(), NOW());

CREATE TABLE inspection_plan (
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
INSERT INTO inspection_plan (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('185-05-001', '巡检计划示例一', '巡检类型', '巡检人员A', '2026-05-16 09:00', 'SIGNED', '计划编号、巡检区域、巡检类型、巡检人员、计划时间和计划状态维护', NOW(), NOW()),
('185-05-002', '巡检计划示例二', '巡检类型', '巡检人员B', '2026-05-17 14:00', 'CLOSED', '巡检计划演示数据二', NOW(), NOW());

CREATE TABLE inspection_record (
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
INSERT INTO inspection_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('185-06-001', '巡检记录示例一', '巡检类型', '巡检人员A', '2026-05-16 09:00', 'ANALYZED', '记录编号、公厕点位、巡检类型、巡检人员、巡检时间和巡检状态维护', NOW(), NOW()),
('185-06-002', '巡检记录示例二', '巡检类型', '巡检人员B', '2026-05-17 14:00', 'REGISTERED', '巡检记录演示数据二', NOW(), NOW());

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
('185-07-001', '耗材库存示例一', '耗材类型', '保管人员A', '2026-05-16 09:00', 'CLOSED', '库存编号、耗材名称、耗材类型、保管人员、入库时间和库存状态维护', NOW(), NOW()),
('185-07-002', '耗材库存示例二', '耗材类型', '保管人员B', '2026-05-17 14:00', 'ORDERED', '耗材库存演示数据二', NOW(), NOW());

CREATE TABLE supply_request (
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
INSERT INTO supply_request (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('185-08-001', '补给申请示例一', '补给类型', '申请人员A', '2026-05-16 09:00', 'REGISTERED', '申请编号、公厕点位、补给类型、申请人员、申请时间和申请状态维护', NOW(), NOW()),
('185-08-002', '补给申请示例二', '补给类型', '申请人员B', '2026-05-17 14:00', 'SCHEDULED', '补给申请演示数据二', NOW(), NOW());

CREATE TABLE supply_dispatch (
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
INSERT INTO supply_dispatch (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('185-09-001', '补给调度示例一', '调度类型', '补给人员A', '2026-05-16 09:00', 'ORDERED', '调度编号、补给申请、调度类型、补给人员、调度时间和调度状态维护', NOW(), NOW()),
('185-09-002', '补给调度示例二', '调度类型', '补给人员B', '2026-05-17 14:00', 'COOKING', '补给调度演示数据二', NOW(), NOW());

CREATE TABLE complaint_report (
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
INSERT INTO complaint_report (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('185-10-001', '投诉反馈示例一', '反馈类型', '反馈人员A', '2026-05-16 09:00', 'SCHEDULED', '反馈编号、公厕点位、反馈类型、反馈人员、反馈时间和反馈状态维护', NOW(), NOW()),
('185-10-002', '投诉反馈示例二', '反馈类型', '反馈人员B', '2026-05-17 14:00', 'DELIVERING', '投诉反馈演示数据二', NOW(), NOW());

CREATE TABLE issue_rectification (
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
INSERT INTO issue_rectification (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('185-11-001', '问题整改示例一', '整改类型', '责任人员A', '2026-05-16 09:00', 'COOKING', '整改编号、巡检问题、整改类型、责任人员、整改时间和整改状态维护', NOW(), NOW()),
('185-11-002', '问题整改示例二', '整改类型', '责任人员B', '2026-05-17 14:00', 'SIGNED', '问题整改演示数据二', NOW(), NOW());

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
('185-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'DELIVERING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('185-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'ANALYZED', '操作日志演示数据二', NOW(), NOW());
