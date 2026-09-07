DROP DATABASE IF EXISTS culture_venue_151;
CREATE DATABASE culture_venue_151 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE culture_venue_151;

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
('admin', '123456', '系统管理员', 'ADMIN', '文旅场馆运营中心', '13915100001', 'admin@culture151.local', 1, NOW(), NOW()),
('manager', '123456', '场馆主管', 'MANAGER', '文旅场馆运营中心', '13915100002', 'manager@culture151.local', 1, NOW(), NOW()),
('guide', '123456', '讲解员', 'GUIDE', '文旅场馆运营中心', '13915100003', 'guide@culture151.local', 1, NOW(), NOW()),
('checker', '123456', '票务核销员', 'CHECKER', '文旅场馆运营中心', '13915100004', 'checker@culture151.local', 1, NOW(), NOW()),
('visitor', '123456', '预约游客', 'VISITOR', '文旅场馆运营中心', '13915100005', 'visitor@culture151.local', 1, NOW(), NOW());

CREATE TABLE venue_info (
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
INSERT INTO venue_info (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('151-01-001', '场馆档案示例一', '开放区域', '负责人A', '2026-05-16 09:00', 'BOOKED', '场馆编码、场馆名称、开放区域、负责人、开放时间和状态维护', NOW(), NOW()),
('151-01-002', '场馆档案示例二', '开放区域', '负责人B', '2026-05-17 14:00', 'VERIFIED', '场馆档案演示数据二', NOW(), NOW());

CREATE TABLE ticket_product (
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
INSERT INTO ticket_product (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('151-02-001', '票种产品示例一', '适用人群', '配置人A', '2026-05-16 09:00', 'SCHEDULED', '票种编码、票种名称、适用人群、配置人、可售时段和上架状态维护', NOW(), NOW()),
('151-02-002', '票种产品示例二', '适用人群', '配置人B', '2026-05-17 14:00', 'PROCESSING', '票种产品演示数据二', NOW(), NOW());

CREATE TABLE ticket_order (
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
INSERT INTO ticket_order (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('151-03-001', '票务预约示例一', '票种类型', '预约人A', '2026-05-16 09:00', 'VERIFIED', '预约单号、参观场馆、票种类型、预约人、参观时间和预约状态维护', NOW(), NOW()),
('151-03-002', '票务预约示例二', '票种类型', '预约人B', '2026-05-17 14:00', 'FINISHED', '票务预约演示数据二', NOW(), NOW());

CREATE TABLE guide_profile (
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
INSERT INTO guide_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('151-04-001', '讲解员档案示例一', '语种等级', '所属团队A', '2026-05-16 09:00', 'PROCESSING', '讲解员工号、姓名、语种等级、所属团队、可服务时间和在岗状态维护', NOW(), NOW()),
('151-04-002', '讲解员档案示例二', '语种等级', '所属团队B', '2026-05-17 14:00', 'WARNING', '讲解员档案演示数据二', NOW(), NOW());

CREATE TABLE guide_schedule (
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
INSERT INTO guide_schedule (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('151-05-001', '讲解排期示例一', '场次类型', '讲解员A', '2026-05-16 09:00', 'FINISHED', '排期编号、讲解路线、场次类型、讲解员、场次时间和排期状态维护', NOW(), NOW()),
('151-05-002', '讲解排期示例二', '场次类型', '讲解员B', '2026-05-17 14:00', 'PUBLISHED', '讲解排期演示数据二', NOW(), NOW());

CREATE TABLE guide_booking (
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
INSERT INTO guide_booking (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('151-06-001', '讲解预约示例一', '团队类型', '联系人A', '2026-05-16 09:00', 'WARNING', '讲解预约单、讲解主题、团队类型、联系人、预约时间和确认状态维护', NOW(), NOW()),
('151-06-002', '讲解预约示例二', '团队类型', '联系人B', '2026-05-17 14:00', 'ACTIVE', '讲解预约演示数据二', NOW(), NOW());

CREATE TABLE ticket_verification (
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
INSERT INTO ticket_verification (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('151-07-001', '扫码核销示例一', '核销渠道', '核销员A', '2026-05-16 09:00', 'PUBLISHED', '核销码、关联订单、核销渠道、核销员、核销时间和核销状态维护', NOW(), NOW()),
('151-07-002', '扫码核销示例二', '核销渠道', '核销员B', '2026-05-17 14:00', 'BOOKED', '扫码核销演示数据二', NOW(), NOW());

CREATE TABLE crowd_flow_record (
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
INSERT INTO crowd_flow_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('151-08-001', '客流统计示例一', '客流类型', '统计员A', '2026-05-16 09:00', 'ACTIVE', '统计编号、场馆区域、客流类型、统计员、统计时段和预警状态维护', NOW(), NOW()),
('151-08-002', '客流统计示例二', '客流类型', '统计员B', '2026-05-17 14:00', 'SCHEDULED', '客流统计演示数据二', NOW(), NOW());

CREATE TABLE visitor_feedback (
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
INSERT INTO visitor_feedback (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('151-09-001', '游客评价示例一', '评价类型', '游客姓名A', '2026-05-16 09:00', 'BOOKED', '评价编号、评价对象、评价类型、游客姓名、评价时间和处理状态维护', NOW(), NOW()),
('151-09-002', '游客评价示例二', '评价类型', '游客姓名B', '2026-05-17 14:00', 'VERIFIED', '游客评价演示数据二', NOW(), NOW());

CREATE TABLE marketing_activity (
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
INSERT INTO marketing_activity (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('151-10-001', '文旅活动示例一', '活动类型', '策划人A', '2026-05-16 09:00', 'SCHEDULED', '活动编号、活动名称、活动类型、策划人、活动时间和发布状态维护', NOW(), NOW()),
('151-10-002', '文旅活动示例二', '活动类型', '策划人B', '2026-05-17 14:00', 'PROCESSING', '文旅活动演示数据二', NOW(), NOW());

CREATE TABLE venue_notice (
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
INSERT INTO venue_notice (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('151-11-001', '场馆公告示例一', '公告类型', '发布人A', '2026-05-16 09:00', 'VERIFIED', '公告编号、公告标题、公告类型、发布人、发布时间和公告状态维护', NOW(), NOW()),
('151-11-002', '场馆公告示例二', '公告类型', '发布人B', '2026-05-17 14:00', 'FINISHED', '场馆公告演示数据二', NOW(), NOW());

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
('151-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'PROCESSING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('151-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'WARNING', '操作日志演示数据二', NOW(), NOW());
