DROP DATABASE IF EXISTS housekeeping_197;
CREATE DATABASE housekeeping_197 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE housekeeping_197;

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
('admin', '123456', '系统管理员', 'ADMIN', '社区家政服务站', '13919700001', 'admin@housekeeping197.local', 1, NOW(), NOW()),
('agency', '123456', '服务站管理员', 'AGENCY', '社区家政服务站', '13919700002', 'agency@housekeeping197.local', 1, NOW(), NOW()),
('dispatch', '123456', '派单调度', 'DISPATCH', '社区家政服务站', '13919700003', 'dispatch@housekeeping197.local', 1, NOW(), NOW()),
('worker', '123456', '家政人员', 'WORKER', '社区家政服务站', '13919700004', 'worker@housekeeping197.local', 1, NOW(), NOW()),
('quality', '123456', '品控专员', 'QUALITY', '社区家政服务站', '13919700005', 'quality@housekeeping197.local', 1, NOW(), NOW()),
('resident', '123456', '社区居民', 'RESIDENT', '社区家政服务站', '13919700006', 'resident@housekeeping197.local', 1, NOW(), NOW());

CREATE TABLE service_station (
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
INSERT INTO service_station (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('197-01-001', '服务站点示例一', '服务范围', '负责人A', '2026-05-16 09:00', 'BOOKING', '站点编号、站点名称、服务范围、负责人、建档时间和站点状态维护', NOW(), NOW()),
('197-01-002', '服务站点示例二', '服务范围', '负责人B', '2026-05-17 14:00', 'SERVING', '服务站点演示数据二', NOW(), NOW());

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
('197-02-001', '居民档案示例一', '服务需求', '联系人员A', '2026-05-16 09:00', 'DISPATCHING', '档案编号、居民姓名、服务需求、联系人员、建档时间和档案状态维护', NOW(), NOW()),
('197-02-002', '居民档案示例二', '服务需求', '联系人员B', '2026-05-17 14:00', 'EVALUATING', '居民档案演示数据二', NOW(), NOW());

CREATE TABLE worker_profile (
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
INSERT INTO worker_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('197-03-001', '人员档案示例一', '技能类型', '管理人员A', '2026-05-16 09:00', 'SERVING', '人员编号、人员姓名、技能类型、管理人员、入职时间和人员状态维护', NOW(), NOW()),
('197-03-002', '人员档案示例二', '技能类型', '管理人员B', '2026-05-17 14:00', 'COMPLAINING', '人员档案演示数据二', NOW(), NOW());

CREATE TABLE service_catalog (
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
INSERT INTO service_catalog (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('197-04-001', '服务项目示例一', '服务类型', '维护人员A', '2026-05-16 09:00', 'EVALUATING', '项目编号、项目名称、服务类型、维护人员、启用时间和项目状态维护', NOW(), NOW()),
('197-04-002', '服务项目示例二', '服务类型', '维护人员B', '2026-05-17 14:00', 'SETTLED', '服务项目演示数据二', NOW(), NOW());

CREATE TABLE service_booking (
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
INSERT INTO service_booking (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('197-05-001', '服务预约示例一', '服务类型', '申请人员A', '2026-05-16 09:00', 'COMPLAINING', '预约编号、居民姓名、服务类型、申请人员、预约时间和预约状态维护', NOW(), NOW()),
('197-05-002', '服务预约示例二', '服务类型', '申请人员B', '2026-05-17 14:00', 'CLOSED', '服务预约演示数据二', NOW(), NOW());

CREATE TABLE booking_review (
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
INSERT INTO booking_review (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('197-06-001', '预约审核示例一', '审核类型', '审核人员A', '2026-05-16 09:00', 'SETTLED', '审核编号、服务预约、审核类型、审核人员、审核时间和审核状态维护', NOW(), NOW()),
('197-06-002', '预约审核示例二', '审核类型', '审核人员B', '2026-05-17 14:00', 'REGISTERED', '预约审核演示数据二', NOW(), NOW());

CREATE TABLE schedule_dispatch (
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
INSERT INTO schedule_dispatch (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('197-07-001', '人员排班示例一', '排班类型', '调度人员A', '2026-05-16 09:00', 'CLOSED', '排班编号、服务预约、排班类型、调度人员、排班时间和排班状态维护', NOW(), NOW()),
('197-07-002', '人员排班示例二', '排班类型', '调度人员B', '2026-05-17 14:00', 'BOOKING', '人员排班演示数据二', NOW(), NOW());

CREATE TABLE service_record (
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
INSERT INTO service_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('197-08-001', '上门记录示例一', '服务类型', '家政人员A', '2026-05-16 09:00', 'REGISTERED', '记录编号、服务预约、服务类型、家政人员、上门时间和服务状态维护', NOW(), NOW()),
('197-08-002', '上门记录示例二', '服务类型', '家政人员B', '2026-05-17 14:00', 'DISPATCHING', '上门记录演示数据二', NOW(), NOW());

CREATE TABLE credit_evaluation (
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
INSERT INTO credit_evaluation (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('197-09-001', '信用评价示例一', '评价类型', '评价人员A', '2026-05-16 09:00', 'BOOKING', '评价编号、上门记录、评价类型、评价人员、评价时间和评价状态维护', NOW(), NOW()),
('197-09-002', '信用评价示例二', '评价类型', '评价人员B', '2026-05-17 14:00', 'SERVING', '信用评价演示数据二', NOW(), NOW());

CREATE TABLE complaint_handling (
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
INSERT INTO complaint_handling (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('197-10-001', '投诉处理示例一', '投诉类型', '处理人员A', '2026-05-16 09:00', 'DISPATCHING', '投诉编号、上门记录、投诉类型、处理人员、投诉时间和处理状态维护', NOW(), NOW()),
('197-10-002', '投诉处理示例二', '投诉类型', '处理人员B', '2026-05-17 14:00', 'EVALUATING', '投诉处理演示数据二', NOW(), NOW());

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
('197-11-001', '费用结算示例一', '结算类型', '经办人员A', '2026-05-16 09:00', 'SERVING', '结算编号、服务记录、结算类型、经办人员、结算时间和结算状态维护', NOW(), NOW()),
('197-11-002', '费用结算示例二', '结算类型', '经办人员B', '2026-05-17 14:00', 'COMPLAINING', '费用结算演示数据二', NOW(), NOW());

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
('197-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'EVALUATING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('197-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'SETTLED', '操作日志演示数据二', NOW(), NOW());
