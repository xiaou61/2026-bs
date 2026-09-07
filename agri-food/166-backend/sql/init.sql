DROP DATABASE IF EXISTS market_trace_166;
CREATE DATABASE market_trace_166 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE market_trace_166;

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
('admin', '123456', '系统管理员', 'ADMIN', '农贸市场监管中心', '13916600001', 'admin@market166.local', 1, NOW(), NOW()),
('market', '123456', '市场管理员', 'MARKET', '农贸市场监管中心', '13916600002', 'market@market166.local', 1, NOW(), NOW()),
('inspector', '123456', '巡检员', 'INSPECTOR', '农贸市场监管中心', '13916600003', 'inspector@market166.local', 1, NOW(), NOW()),
('vendor', '123456', '摊位商户', 'VENDOR', '农贸市场监管中心', '13916600004', 'vendor@market166.local', 1, NOW(), NOW()),
('sampler', '123456', '抽检员', 'SAMPLER', '农贸市场监管中心', '13916600005', 'sampler@market166.local', 1, NOW(), NOW()),
('regulator', '123456', '监管审核员', 'REGULATOR', '农贸市场监管中心', '13916600006', 'regulator@market166.local', 1, NOW(), NOW());

CREATE TABLE market_area (
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
INSERT INTO market_area (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('166-01-001', '市场区域示例一', '经营品类', '区域负责人A', '2026-05-16 09:00', 'BOOKED', '区域编号、区域名称、经营品类、区域负责人、开放时间和区域状态维护', NOW(), NOW()),
('166-01-002', '市场区域示例二', '经营品类', '区域负责人B', '2026-05-17 14:00', 'VERIFIED', '市场区域演示数据二', NOW(), NOW());

CREATE TABLE stall_profile (
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
INSERT INTO stall_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('166-02-001', '摊位档案示例一', '经营类型', '摊主姓名A', '2026-05-16 09:00', 'SCHEDULED', '摊位编号、摊位名称、经营类型、摊主姓名、入驻时间和摊位状态维护', NOW(), NOW()),
('166-02-002', '摊位档案示例二', '经营类型', '摊主姓名B', '2026-05-17 14:00', 'PROCESSING', '摊位档案演示数据二', NOW(), NOW());

CREATE TABLE vendor_profile (
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
INSERT INTO vendor_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('166-03-001', '商户档案示例一', '证照类型', '联系人A', '2026-05-16 09:00', 'VERIFIED', '商户编号、商户名称、证照类型、联系人、登记时间和经营状态维护', NOW(), NOW()),
('166-03-002', '商户档案示例二', '证照类型', '联系人B', '2026-05-17 14:00', 'FINISHED', '商户档案演示数据二', NOW(), NOW());

CREATE TABLE product_trace (
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
INSERT INTO product_trace (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('166-04-001', '商品溯源示例一', '商品类别', '供货商A', '2026-05-16 09:00', 'PROCESSING', '溯源码、商品名称、商品类别、供货商、入场时间和溯源状态维护', NOW(), NOW()),
('166-04-002', '商品溯源示例二', '商品类别', '供货商B', '2026-05-17 14:00', 'WARNING', '商品溯源演示数据二', NOW(), NOW());

CREATE TABLE inspection_task (
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
INSERT INTO inspection_task (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('166-05-001', '摊位巡检示例一', '巡检类型', '巡检员A', '2026-05-16 09:00', 'FINISHED', '巡检编号、巡检摊位、巡检类型、巡检员、巡检时间和巡检状态维护', NOW(), NOW()),
('166-05-002', '摊位巡检示例二', '巡检类型', '巡检员B', '2026-05-17 14:00', 'PUBLISHED', '摊位巡检演示数据二', NOW(), NOW());

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
('166-06-001', '问题整改示例一', '问题类型', '责任商户A', '2026-05-16 09:00', 'WARNING', '整改编号、问题摊位、问题类型、责任商户、整改时间和整改状态维护', NOW(), NOW()),
('166-06-002', '问题整改示例二', '问题类型', '责任商户B', '2026-05-17 14:00', 'ACTIVE', '问题整改演示数据二', NOW(), NOW());

CREATE TABLE sample_record (
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
INSERT INTO sample_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('166-07-001', '抽检记录示例一', '抽检类型', '抽检人员A', '2026-05-16 09:00', 'PUBLISHED', '抽检编号、抽检商品、抽检类型、抽检人员、抽检时间和抽检状态维护', NOW(), NOW()),
('166-07-002', '抽检记录示例二', '抽检类型', '抽检人员B', '2026-05-17 14:00', 'BOOKED', '抽检记录演示数据二', NOW(), NOW());

CREATE TABLE test_result (
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
INSERT INTO test_result (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('166-08-001', '检测结果示例一', '检测项目', '检测机构A', '2026-05-16 09:00', 'ACTIVE', '检测编号、检测样品、检测项目、检测机构、出具时间和检测状态维护', NOW(), NOW()),
('166-08-002', '检测结果示例二', '检测项目', '检测机构B', '2026-05-17 14:00', 'SCHEDULED', '检测结果演示数据二', NOW(), NOW());

CREATE TABLE source_ledger (
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
INSERT INTO source_ledger (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('166-09-001', '进货台账示例一', '来源类型', '供货单位A', '2026-05-16 09:00', 'BOOKED', '台账编号、进货商品、来源类型、供货单位、进货时间和台账状态维护', NOW(), NOW()),
('166-09-002', '进货台账示例二', '来源类型', '供货单位B', '2026-05-17 14:00', 'VERIFIED', '进货台账演示数据二', NOW(), NOW());

CREATE TABLE disposal_record (
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
INSERT INTO disposal_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('166-10-001', '处置记录示例一', '处置类型', '处置人员A', '2026-05-16 09:00', 'SCHEDULED', '处置编号、处置对象、处置类型、处置人员、处置时间和处置状态维护', NOW(), NOW()),
('166-10-002', '处置记录示例二', '处置类型', '处置人员B', '2026-05-17 14:00', 'PROCESSING', '处置记录演示数据二', NOW(), NOW());

CREATE TABLE risk_alert (
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
INSERT INTO risk_alert (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('166-11-001', '风险预警示例一', '预警类型', '处置负责人A', '2026-05-16 09:00', 'VERIFIED', '预警编号、预警对象、预警类型、处置负责人、预警时间和预警状态维护', NOW(), NOW()),
('166-11-002', '风险预警示例二', '预警类型', '处置负责人B', '2026-05-17 14:00', 'FINISHED', '风险预警演示数据二', NOW(), NOW());

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
('166-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'PROCESSING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('166-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'WARNING', '操作日志演示数据二', NOW(), NOW());
