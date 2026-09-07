DROP DATABASE IF EXISTS hazard_waste_194;
CREATE DATABASE hazard_waste_194 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE hazard_waste_194;

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
('admin', '123456', '系统管理员', 'ADMIN', '工业园区危废监管中心', '13919400001', 'admin@hazardwaste194.local', 1, NOW(), NOW()),
('park', '123456', '园区监管', 'PARK', '工业园区危废监管中心', '13919400002', 'park@hazardwaste194.local', 1, NOW(), NOW()),
('warehouse', '123456', '暂存管理员', 'WAREHOUSE', '工业园区危废监管中心', '13919400003', 'warehouse@hazardwaste194.local', 1, NOW(), NOW()),
('transport', '123456', '转运调度', 'TRANSPORT', '工业园区危废监管中心', '13919400004', 'transport@hazardwaste194.local', 1, NOW(), NOW()),
('inspector', '123456', '环保巡查', 'INSPECTOR', '工业园区危废监管中心', '13919400005', 'inspector@hazardwaste194.local', 1, NOW(), NOW()),
('enterprise', '123456', '产废企业', 'ENTERPRISE', '工业园区危废监管中心', '13919400006', 'enterprise@hazardwaste194.local', 1, NOW(), NOW());

CREATE TABLE park_enterprise (
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
INSERT INTO park_enterprise (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('194-01-001', '园区企业示例一', '行业类别', '环保联系人A', '2026-05-16 09:00', 'STORED', '企业编号、企业名称、行业类别、环保联系人、入驻时间和监管状态维护', NOW(), NOW()),
('194-01-002', '园区企业示例二', '行业类别', '环保联系人B', '2026-05-17 14:00', 'PENDING_TRANSFER', '园区企业演示数据二', NOW(), NOW());

CREATE TABLE waste_category (
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
INSERT INTO waste_category (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('194-02-001', '危废类别示例一', '危废类别', '责任人员A', '2026-05-16 09:00', 'CHECKING', '危废编号、危废名称、危废类别、责任人员、建档时间和风险状态维护', NOW(), NOW()),
('194-02-002', '危废类别示例二', '危废类别', '责任人员B', '2026-05-17 14:00', 'TRANSFERRING', '危废类别演示数据二', NOW(), NOW());

CREATE TABLE storage_facility (
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
INSERT INTO storage_facility (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('194-03-001', '暂存设施示例一', '设施类型', '管理人员A', '2026-05-16 09:00', 'PENDING_TRANSFER', '设施编号、设施名称、设施类型、管理人员、启用时间和设施状态维护', NOW(), NOW()),
('194-03-002', '暂存设施示例二', '设施类型', '管理人员B', '2026-05-17 14:00', 'WEIGHED', '暂存设施演示数据二', NOW(), NOW());

CREATE TABLE storage_inbound (
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
INSERT INTO storage_inbound (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('194-04-001', '入库登记示例一', '来源企业', '登记人员A', '2026-05-16 09:00', 'TRANSFERRING', '入库编号、危废名称、来源企业、登记人员、入库时间和入库状态维护', NOW(), NOW()),
('194-04-002', '入库登记示例二', '来源企业', '登记人员B', '2026-05-17 14:00', 'HANDED_OVER', '入库登记演示数据二', NOW(), NOW());

CREATE TABLE storage_check (
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
INSERT INTO storage_check (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('194-05-001', '暂存巡检示例一', '巡检类型', '巡检人员A', '2026-05-16 09:00', 'WEIGHED', '巡检编号、暂存设施、巡检类型、巡检人员、巡检时间和巡检状态维护', NOW(), NOW()),
('194-05-002', '暂存巡检示例二', '巡检类型', '巡检人员B', '2026-05-17 14:00', 'CLOSED', '暂存巡检演示数据二', NOW(), NOW());

CREATE TABLE transfer_order (
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
INSERT INTO transfer_order (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('194-06-001', '转运联单示例一', '转运类型', '调度人员A', '2026-05-16 09:00', 'HANDED_OVER', '联单编号、危废批次、转运类型、调度人员、计划时间和联单状态维护', NOW(), NOW()),
('194-06-002', '转运联单示例二', '转运类型', '调度人员B', '2026-05-17 14:00', 'REGISTERED', '转运联单演示数据二', NOW(), NOW());

CREATE TABLE vehicle_dispatch (
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
INSERT INTO vehicle_dispatch (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('194-07-001', '车辆调度示例一', '车辆类型', '驾驶人员A', '2026-05-16 09:00', 'CLOSED', '调度编号、转运联单、车辆类型、驾驶人员、调度时间和调度状态维护', NOW(), NOW()),
('194-07-002', '车辆调度示例二', '车辆类型', '驾驶人员B', '2026-05-17 14:00', 'STORED', '车辆调度演示数据二', NOW(), NOW());

CREATE TABLE weighing_record (
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
INSERT INTO weighing_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('194-08-001', '称重记录示例一', '称重类型', '称重人员A', '2026-05-16 09:00', 'REGISTERED', '称重编号、转运批次、称重类型、称重人员、称重时间和称重状态维护', NOW(), NOW()),
('194-08-002', '称重记录示例二', '称重类型', '称重人员B', '2026-05-17 14:00', 'CHECKING', '称重记录演示数据二', NOW(), NOW());

CREATE TABLE disposal_handover (
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
INSERT INTO disposal_handover (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('194-09-001', '处置交接示例一', '接收单位', '交接人员A', '2026-05-16 09:00', 'STORED', '交接编号、转运联单、接收单位、交接人员、交接时间和交接状态维护', NOW(), NOW()),
('194-09-002', '处置交接示例二', '接收单位', '交接人员B', '2026-05-17 14:00', 'PENDING_TRANSFER', '处置交接演示数据二', NOW(), NOW());

CREATE TABLE risk_warning (
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
INSERT INTO risk_warning (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('194-10-001', '风险预警示例一', '预警类型', '处理人员A', '2026-05-16 09:00', 'CHECKING', '预警编号、危废批次、预警类型、处理人员、预警时间和预警状态维护', NOW(), NOW()),
('194-10-002', '风险预警示例二', '预警类型', '处理人员B', '2026-05-17 14:00', 'TRANSFERRING', '风险预警演示数据二', NOW(), NOW());

CREATE TABLE ledger_audit (
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
INSERT INTO ledger_audit (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('194-11-001', '台账审计示例一', '审计类型', '审计人员A', '2026-05-16 09:00', 'PENDING_TRANSFER', '审计编号、审计对象、审计类型、审计人员、审计时间和审计状态维护', NOW(), NOW()),
('194-11-002', '台账审计示例二', '审计类型', '审计人员B', '2026-05-17 14:00', 'WEIGHED', '台账审计演示数据二', NOW(), NOW());

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
('194-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'TRANSFERRING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('194-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'HANDED_OVER', '操作日志演示数据二', NOW(), NOW());
