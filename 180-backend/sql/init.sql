DROP DATABASE IF EXISTS property_repair_180;
CREATE DATABASE property_repair_180 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE property_repair_180;

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
('admin', '123456', '系统管理员', 'ADMIN', '社区物业服务中心', '13918000001', 'admin@property180.local', 1, NOW(), NOW()),
('property', '123456', '物业管理员', 'PROPERTY', '社区物业服务中心', '13918000002', 'property@property180.local', 1, NOW(), NOW()),
('owner', '123456', '业主住户', 'OWNER', '社区物业服务中心', '13918000003', 'owner@property180.local', 1, NOW(), NOW()),
('dispatch', '123456', '派单员', 'DISPATCH', '社区物业服务中心', '13918000004', 'dispatch@property180.local', 1, NOW(), NOW()),
('worker', '123456', '维修人员', 'WORKER', '社区物业服务中心', '13918000005', 'worker@property180.local', 1, NOW(), NOW()),
('finance', '123456', '财务客服', 'FINANCE', '社区物业服务中心', '13918000006', 'finance@property180.local', 1, NOW(), NOW());

CREATE TABLE community_area (
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
INSERT INTO community_area (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('180-01-001', '小区区域示例一', '区域类型', '物业人员A', '2026-05-16 09:00', 'ASSIGNED', '区域编号、区域名称、区域类型、物业人员、启用时间和区域状态维护', NOW(), NOW()),
('180-01-002', '小区区域示例二', '区域类型', '物业人员B', '2026-05-17 14:00', 'ON_SITE', '小区区域演示数据二', NOW(), NOW());

CREATE TABLE owner_profile (
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
INSERT INTO owner_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('180-02-001', '业主档案示例一', '住户类型', '服务管家A', '2026-05-16 09:00', 'ACCEPTED', '业主编号、业主姓名、住户类型、服务管家、入住时间和业主状态维护', NOW(), NOW()),
('180-02-002', '业主档案示例二', '住户类型', '服务管家B', '2026-05-17 14:00', 'WAIT_PAY', '业主档案演示数据二', NOW(), NOW());

CREATE TABLE repair_category (
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
INSERT INTO repair_category (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('180-03-001', '报修分类示例一', '维修类型', '负责班组A', '2026-05-16 09:00', 'ON_SITE', '分类编号、分类名称、维修类型、负责班组、启用时间和分类状态维护', NOW(), NOW()),
('180-03-002', '报修分类示例二', '维修类型', '负责班组B', '2026-05-17 14:00', 'PAID', '报修分类演示数据二', NOW(), NOW());

CREATE TABLE repair_order (
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
INSERT INTO repair_order (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('180-04-001', '报修工单示例一', '报修类型', '报修业主A', '2026-05-16 09:00', 'WAIT_PAY', '工单编号、报修位置、报修类型、报修业主、提交时间和工单状态维护', NOW(), NOW()),
('180-04-002', '报修工单示例二', '报修类型', '报修业主B', '2026-05-17 14:00', 'REVIEWED', '报修工单演示数据二', NOW(), NOW());

CREATE TABLE dispatch_assignment (
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
INSERT INTO dispatch_assignment (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('180-05-001', '派单分配示例一', '派单类型', '维修人员A', '2026-05-16 09:00', 'PAID', '派单编号、报修工单、派单类型、维修人员、派单时间和派单状态维护', NOW(), NOW()),
('180-05-002', '派单分配示例二', '派单类型', '维修人员B', '2026-05-17 14:00', 'CLOSED', '派单分配演示数据二', NOW(), NOW());

CREATE TABLE door_service (
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
INSERT INTO door_service (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('180-06-001', '上门处理示例一', '服务类型', '维修人员A', '2026-05-16 09:00', 'REVIEWED', '服务编号、报修工单、服务类型、维修人员、上门时间和处理状态维护', NOW(), NOW()),
('180-06-002', '上门处理示例二', '服务类型', '维修人员B', '2026-05-17 14:00', 'NEW', '上门处理演示数据二', NOW(), NOW());

CREATE TABLE material_usage (
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
INSERT INTO material_usage (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('180-07-001', '物料使用示例一', '物料类型', '领用人员A', '2026-05-16 09:00', 'CLOSED', '物料编号、维修工单、物料类型、领用人员、使用时间和物料状态维护', NOW(), NOW()),
('180-07-002', '物料使用示例二', '物料类型', '领用人员B', '2026-05-17 14:00', 'ASSIGNED', '物料使用演示数据二', NOW(), NOW());

CREATE TABLE fee_registration (
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
INSERT INTO fee_registration (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('180-08-001', '费用登记示例一', '费用类型', '登记人员A', '2026-05-16 09:00', 'NEW', '费用编号、维修工单、费用类型、登记人员、登记时间和费用状态维护', NOW(), NOW()),
('180-08-002', '费用登记示例二', '费用类型', '登记人员B', '2026-05-17 14:00', 'ACCEPTED', '费用登记演示数据二', NOW(), NOW());

CREATE TABLE payment_record (
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
INSERT INTO payment_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('180-09-001', '支付记录示例一', '支付类型', '支付业主A', '2026-05-16 09:00', 'ASSIGNED', '支付编号、费用登记、支付类型、支付业主、支付时间和支付状态维护', NOW(), NOW()),
('180-09-002', '支付记录示例二', '支付类型', '支付业主B', '2026-05-17 14:00', 'ON_SITE', '支付记录演示数据二', NOW(), NOW());

CREATE TABLE satisfaction_review (
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
INSERT INTO satisfaction_review (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('180-10-001', '满意评价示例一', '评价类型', '评价业主A', '2026-05-16 09:00', 'ACCEPTED', '评价编号、服务工单、评价类型、评价业主、评价时间和评价状态维护', NOW(), NOW()),
('180-10-002', '满意评价示例二', '评价类型', '评价业主B', '2026-05-17 14:00', 'WAIT_PAY', '满意评价演示数据二', NOW(), NOW());

CREATE TABLE complaint_followup (
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
INSERT INTO complaint_followup (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('180-11-001', '投诉回访示例一', '回访类型', '客服人员A', '2026-05-16 09:00', 'ON_SITE', '回访编号、投诉事项、回访类型、客服人员、回访时间和回访状态维护', NOW(), NOW()),
('180-11-002', '投诉回访示例二', '回访类型', '客服人员B', '2026-05-17 14:00', 'PAID', '投诉回访演示数据二', NOW(), NOW());

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
('180-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'WAIT_PAY', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('180-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'REVIEWED', '操作日志演示数据二', NOW(), NOW());
