DROP DATABASE IF EXISTS scenic_lost_found_161;
CREATE DATABASE scenic_lost_found_161 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE scenic_lost_found_161;

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
('admin', '123456', '系统管理员', 'ADMIN', '景区游客服务中心', '13916100001', 'admin@scenic161.local', 1, NOW(), NOW()),
('service', '123456', '游客服务员', 'SERVICE', '景区游客服务中心', '13916100002', 'service@scenic161.local', 1, NOW(), NOW()),
('security', '123456', '安保巡查员', 'SECURITY', '景区游客服务中心', '13916100003', 'security@scenic161.local', 1, NOW(), NOW()),
('scenic', '123456', '景区运营员', 'SCENIC', '景区游客服务中心', '13916100004', 'scenic@scenic161.local', 1, NOW(), NOW()),
('broadcast', '123456', '通知协同员', 'BROADCAST', '景区游客服务中心', '13916100005', 'broadcast@scenic161.local', 1, NOW(), NOW()),
('visitor', '123456', '游客用户', 'VISITOR', '景区游客服务中心', '13916100006', 'visitor@scenic161.local', 1, NOW(), NOW());

CREATE TABLE scenic_area (
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
INSERT INTO scenic_area (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('161-01-001', '景区区域示例一', '区域类型', '负责人A', '2026-05-16 09:00', 'BOOKED', '区域编号、区域名称、区域类型、负责人、启用时间和区域状态维护', NOW(), NOW()),
('161-01-002', '景区区域示例二', '区域类型', '负责人B', '2026-05-17 14:00', 'VERIFIED', '景区区域演示数据二', NOW(), NOW());

CREATE TABLE lost_item (
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
INSERT INTO lost_item (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('161-02-001', '失物登记示例一', '失物类型', '登记人A', '2026-05-16 09:00', 'SCHEDULED', '失物编号、失物名称、失物类型、登记人、丢失时间和失物状态维护', NOW(), NOW()),
('161-02-002', '失物登记示例二', '失物类型', '登记人B', '2026-05-17 14:00', 'PROCESSING', '失物登记演示数据二', NOW(), NOW());

CREATE TABLE found_report (
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
INSERT INTO found_report (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('161-03-001', '拾物上报示例一', '拾物类型', '上报人A', '2026-05-16 09:00', 'VERIFIED', '拾物编号、拾物名称、拾物类型、上报人、发现时间和拾物状态维护', NOW(), NOW()),
('161-03-002', '拾物上报示例二', '拾物类型', '上报人B', '2026-05-17 14:00', 'FINISHED', '拾物上报演示数据二', NOW(), NOW());

CREATE TABLE claim_application (
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
INSERT INTO claim_application (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('161-04-001', '游客认领示例一', '认领类型', '申请人A', '2026-05-16 09:00', 'PROCESSING', '认领编号、认领物品、认领类型、申请人、申请时间和认领状态维护', NOW(), NOW()),
('161-04-002', '游客认领示例二', '认领类型', '申请人B', '2026-05-17 14:00', 'WARNING', '游客认领演示数据二', NOW(), NOW());

CREATE TABLE identity_verify (
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
INSERT INTO identity_verify (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('161-05-001', '身份核验示例一', '核验类型', '核验人A', '2026-05-16 09:00', 'FINISHED', '核验编号、核验对象、核验类型、核验人、核验时间和核验状态维护', NOW(), NOW()),
('161-05-002', '身份核验示例二', '核验类型', '核验人B', '2026-05-17 14:00', 'PUBLISHED', '身份核验演示数据二', NOW(), NOW());

CREATE TABLE location_trace (
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
INSERT INTO location_trace (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('161-06-001', '位置追踪示例一', '追踪类型', '巡查人A', '2026-05-16 09:00', 'WARNING', '追踪编号、关联物品、追踪类型、巡查人、定位时间和追踪状态维护', NOW(), NOW()),
('161-06-002', '位置追踪示例二', '追踪类型', '巡查人B', '2026-05-17 14:00', 'ACTIVE', '位置追踪演示数据二', NOW(), NOW());

CREATE TABLE custody_record (
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
INSERT INTO custody_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('161-07-001', '暂存保管示例一', '保管类型', '保管人A', '2026-05-16 09:00', 'PUBLISHED', '保管编号、保管物品、保管类型、保管人、入库时间和保管状态维护', NOW(), NOW()),
('161-07-002', '暂存保管示例二', '保管类型', '保管人B', '2026-05-17 14:00', 'BOOKED', '暂存保管演示数据二', NOW(), NOW());

CREATE TABLE notice_broadcast (
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
INSERT INTO notice_broadcast (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('161-08-001', '通知协同示例一', '通知类型', '发布人A', '2026-05-16 09:00', 'ACTIVE', '通知编号、通知对象、通知类型、发布人、发布时间和通知状态维护', NOW(), NOW()),
('161-08-002', '通知协同示例二', '通知类型', '发布人B', '2026-05-17 14:00', 'SCHEDULED', '通知协同演示数据二', NOW(), NOW());

CREATE TABLE handover_record (
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
INSERT INTO handover_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('161-09-001', '归还交接示例一', '交接类型', '交接人A', '2026-05-16 09:00', 'BOOKED', '交接编号、交接物品、交接类型、交接人、交接时间和交接状态维护', NOW(), NOW()),
('161-09-002', '归还交接示例二', '交接类型', '交接人B', '2026-05-17 14:00', 'VERIFIED', '归还交接演示数据二', NOW(), NOW());

CREATE TABLE search_task (
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
INSERT INTO search_task (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('161-10-001', '寻回任务示例一', '任务类型', '执行人A', '2026-05-16 09:00', 'SCHEDULED', '任务编号、任务物品、任务类型、执行人、执行时间和任务状态维护', NOW(), NOW()),
('161-10-002', '寻回任务示例二', '任务类型', '执行人B', '2026-05-17 14:00', 'PROCESSING', '寻回任务演示数据二', NOW(), NOW());

CREATE TABLE feedback_record (
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
INSERT INTO feedback_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('161-11-001', '回访评价示例一', '回访类型', '回访人A', '2026-05-16 09:00', 'VERIFIED', '回访编号、回访游客、回访类型、回访人、回访时间和回访状态维护', NOW(), NOW()),
('161-11-002', '回访评价示例二', '回访类型', '回访人B', '2026-05-17 14:00', 'FINISHED', '回访评价演示数据二', NOW(), NOW());

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
('161-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'PROCESSING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('161-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'WARNING', '操作日志演示数据二', NOW(), NOW());
