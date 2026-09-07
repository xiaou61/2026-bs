DROP DATABASE IF EXISTS campus_resale_153;
CREATE DATABASE campus_resale_153 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE campus_resale_153;

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
('admin', '123456', '系统管理员', 'ADMIN', '校园二手寄卖中心', '13915300001', 'admin@resale153.local', 1, NOW(), NOW()),
('operator', '123456', '平台运营', 'OPERATOR', '校园二手寄卖中心', '13915300002', 'operator@resale153.local', 1, NOW(), NOW()),
('seller', '123456', '寄卖学生', 'SELLER', '校园二手寄卖中心', '13915300003', 'seller@resale153.local', 1, NOW(), NOW()),
('buyer', '123456', '购买学生', 'BUYER', '校园二手寄卖中心', '13915300004', 'buyer@resale153.local', 1, NOW(), NOW()),
('arbiter', '123456', '信用仲裁员', 'ARBITER', '校园二手寄卖中心', '13915300005', 'arbiter@resale153.local', 1, NOW(), NOW());

CREATE TABLE item_category (
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
INSERT INTO item_category (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('153-01-001', '物品分类示例一', '适用品类', '维护人A', '2026-05-16 09:00', 'BOOKED', '分类编码、分类名称、适用品类、维护人、更新时间和启用状态维护', NOW(), NOW()),
('153-01-002', '物品分类示例二', '适用品类', '维护人B', '2026-05-17 14:00', 'VERIFIED', '物品分类演示数据二', NOW(), NOW());

CREATE TABLE student_profile (
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
INSERT INTO student_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('153-02-001', '学生档案示例一', '学院班级', '联系方式A', '2026-05-16 09:00', 'SCHEDULED', '学号、姓名、学院班级、联系方式、认证时间和账号状态维护', NOW(), NOW()),
('153-02-002', '学生档案示例二', '学院班级', '联系方式B', '2026-05-17 14:00', 'PROCESSING', '学生档案演示数据二', NOW(), NOW());

CREATE TABLE consignment_item (
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
INSERT INTO consignment_item (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('153-03-001', '寄卖物品示例一', '物品类型', '寄卖人A', '2026-05-16 09:00', 'VERIFIED', '寄卖编号、物品名称、物品类型、寄卖人、上架时间和寄卖状态维护', NOW(), NOW()),
('153-03-002', '寄卖物品示例二', '物品类型', '寄卖人B', '2026-05-17 14:00', 'FINISHED', '寄卖物品演示数据二', NOW(), NOW());

CREATE TABLE item_audit (
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
INSERT INTO item_audit (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('153-04-001', '上架审核示例一', '审核类型', '审核人A', '2026-05-16 09:00', 'PROCESSING', '审核编号、寄卖物品、审核类型、审核人、审核时间和审核状态维护', NOW(), NOW()),
('153-04-002', '上架审核示例二', '审核类型', '审核人B', '2026-05-17 14:00', 'WARNING', '上架审核演示数据二', NOW(), NOW());

CREATE TABLE escrow_order (
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
INSERT INTO escrow_order (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('153-05-001', '担保订单示例一', '交易类型', '买家A', '2026-05-16 09:00', 'FINISHED', '订单编号、寄卖物品、交易类型、买家、下单时间和订单状态维护', NOW(), NOW()),
('153-05-002', '担保订单示例二', '交易类型', '买家B', '2026-05-17 14:00', 'PUBLISHED', '担保订单演示数据二', NOW(), NOW());

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
('153-06-001', '担保支付示例一', '支付渠道', '付款人A', '2026-05-16 09:00', 'WARNING', '支付编号、关联订单、支付渠道、付款人、支付时间和支付状态维护', NOW(), NOW()),
('153-06-002', '担保支付示例二', '支付渠道', '付款人B', '2026-05-17 14:00', 'ACTIVE', '担保支付演示数据二', NOW(), NOW());

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
('153-07-001', '交接确认示例一', '交接方式', '确认人A', '2026-05-16 09:00', 'PUBLISHED', '交接编号、关联订单、交接方式、确认人、交接时间和确认状态维护', NOW(), NOW()),
('153-07-002', '交接确认示例二', '交接方式', '确认人B', '2026-05-17 14:00', 'BOOKED', '交接确认演示数据二', NOW(), NOW());

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
('153-08-001', '信用评价示例一', '评价类型', '评价人A', '2026-05-16 09:00', 'ACTIVE', '评价编号、评价对象、评价类型、评价人、评价时间和处理状态维护', NOW(), NOW()),
('153-08-002', '信用评价示例二', '评价类型', '评价人B', '2026-05-17 14:00', 'SCHEDULED', '信用评价演示数据二', NOW(), NOW());

CREATE TABLE breach_record (
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
INSERT INTO breach_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('153-09-001', '违约记录示例一', '违约类型', '责任人A', '2026-05-16 09:00', 'BOOKED', '违约编号、关联订单、违约类型、责任人、记录时间和处置状态维护', NOW(), NOW()),
('153-09-002', '违约记录示例二', '违约类型', '责任人B', '2026-05-17 14:00', 'VERIFIED', '违约记录演示数据二', NOW(), NOW());

CREATE TABLE complaint_ticket (
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
INSERT INTO complaint_ticket (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('153-10-001', '纠纷申诉示例一', '申诉类型', '申诉人A', '2026-05-16 09:00', 'SCHEDULED', '申诉编号、关联订单、申诉类型、申诉人、提交时间和处理状态维护', NOW(), NOW()),
('153-10-002', '纠纷申诉示例二', '申诉类型', '申诉人B', '2026-05-17 14:00', 'PROCESSING', '纠纷申诉演示数据二', NOW(), NOW());

CREATE TABLE platform_notice (
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
INSERT INTO platform_notice (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('153-11-001', '平台公告示例一', '公告类型', '发布人A', '2026-05-16 09:00', 'VERIFIED', '公告编号、公告标题、公告类型、发布人、发布时间和公告状态维护', NOW(), NOW()),
('153-11-002', '平台公告示例二', '公告类型', '发布人B', '2026-05-17 14:00', 'FINISHED', '平台公告演示数据二', NOW(), NOW());

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
('153-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'PROCESSING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('153-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'WARNING', '操作日志演示数据二', NOW(), NOW());
