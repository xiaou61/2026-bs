DROP DATABASE IF EXISTS water_patrol_176;
CREATE DATABASE water_patrol_176 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE water_patrol_176;

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
('admin', '123456', '系统管理员', 'ADMIN', '水务巡线调度中心', '13917600001', 'admin@waterpatrol176.local', 1, NOW(), NOW()),
('dispatch', '123456', '调度管理员', 'DISPATCH', '水务巡线调度中心', '13917600002', 'dispatch@waterpatrol176.local', 1, NOW(), NOW()),
('patrol', '123456', '巡线班组', 'PATROL', '水务巡线调度中心', '13917600003', 'patrol@waterpatrol176.local', 1, NOW(), NOW()),
('repair', '123456', '检修人员', 'REPAIR', '水务巡线调度中心', '13917600004', 'repair@waterpatrol176.local', 1, NOW(), NOW()),
('warehouse', '123456', '备件管理员', 'WAREHOUSE', '水务巡线调度中心', '13917600005', 'warehouse@waterpatrol176.local', 1, NOW(), NOW()),
('supervisor', '123456', '监管负责人', 'SUPERVISOR', '水务巡线调度中心', '13917600006', 'supervisor@waterpatrol176.local', 1, NOW(), NOW());

CREATE TABLE water_station (
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
INSERT INTO water_station (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('176-01-001', '水务站点示例一', '服务区域', '负责人A', '2026-05-16 09:00', 'PATROLLING', '站点编号、站点名称、服务区域、负责人、启用时间和站点状态维护', NOW(), NOW()),
('176-01-002', '水务站点示例二', '服务区域', '负责人B', '2026-05-17 14:00', 'REPAIRING', '水务站点演示数据二', NOW(), NOW());

CREATE TABLE pipeline_section (
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
INSERT INTO pipeline_section (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('176-02-001', '管线区段示例一', '管线类型', '责任班组A', '2026-05-16 09:00', 'DISPATCHING', '区段编号、区段名称、管线类型、责任班组、启用时间和区段状态维护', NOW(), NOW()),
('176-02-002', '管线区段示例二', '管线类型', '责任班组B', '2026-05-17 14:00', 'RECEIPT_PENDING', '管线区段演示数据二', NOW(), NOW());

CREATE TABLE patrol_route (
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
INSERT INTO patrol_route (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('176-03-001', '巡线路线示例一', '巡线类型', '负责人员A', '2026-05-16 09:00', 'REPAIRING', '路线编号、路线名称、巡线类型、负责人员、计划时间和路线状态维护', NOW(), NOW()),
('176-03-002', '巡线路线示例二', '巡线类型', '负责人员B', '2026-05-17 14:00', 'REVIEWED', '巡线路线演示数据二', NOW(), NOW());

CREATE TABLE valve_ledger (
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
INSERT INTO valve_ledger (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('176-04-001', '阀门台账示例一', '阀门类型', '维护人员A', '2026-05-16 09:00', 'RECEIPT_PENDING', '阀门编号、阀门名称、阀门类型、维护人员、安装时间和阀门状态维护', NOW(), NOW()),
('176-04-002', '阀门台账示例二', '阀门类型', '维护人员B', '2026-05-17 14:00', 'WARNING', '阀门台账演示数据二', NOW(), NOW());

CREATE TABLE patrol_task (
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
INSERT INTO patrol_task (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('176-05-001', '巡线任务示例一', '任务类型', '巡线人员A', '2026-05-16 09:00', 'REVIEWED', '任务编号、巡线区段、任务类型、巡线人员、计划时间和任务状态维护', NOW(), NOW()),
('176-05-002', '巡线任务示例二', '任务类型', '巡线人员B', '2026-05-17 14:00', 'FINISHED', '巡线任务演示数据二', NOW(), NOW());

CREATE TABLE patrol_record (
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
INSERT INTO patrol_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('176-06-001', '巡线记录示例一', '巡线类型', '巡线人员A', '2026-05-16 09:00', 'WARNING', '记录编号、巡线区段、巡线类型、巡线人员、巡线时间和记录状态维护', NOW(), NOW()),
('176-06-002', '巡线记录示例二', '巡线类型', '巡线人员B', '2026-05-17 14:00', 'PENDING', '巡线记录演示数据二', NOW(), NOW());

CREATE TABLE fault_report (
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
INSERT INTO fault_report (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('176-07-001', '故障上报示例一', '故障类型', '上报人员A', '2026-05-16 09:00', 'FINISHED', '故障编号、故障阀门、故障类型、上报人员、上报时间和故障状态维护', NOW(), NOW()),
('176-07-002', '故障上报示例二', '故障类型', '上报人员B', '2026-05-17 14:00', 'PATROLLING', '故障上报演示数据二', NOW(), NOW());

CREATE TABLE dispatch_order (
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
INSERT INTO dispatch_order (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('176-08-001', '故障派工示例一', '派工类型', '检修人员A', '2026-05-16 09:00', 'PENDING', '派工编号、故障事项、派工类型、检修人员、派工时间和派工状态维护', NOW(), NOW()),
('176-08-002', '故障派工示例二', '派工类型', '检修人员B', '2026-05-17 14:00', 'DISPATCHING', '故障派工演示数据二', NOW(), NOW());

CREATE TABLE maintenance_receipt (
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
INSERT INTO maintenance_receipt (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('176-09-001', '检修回执示例一', '检修类型', '检修人员A', '2026-05-16 09:00', 'PATROLLING', '回执编号、检修阀门、检修类型、检修人员、回执时间和检修状态维护', NOW(), NOW()),
('176-09-002', '检修回执示例二', '检修类型', '检修人员B', '2026-05-17 14:00', 'REPAIRING', '检修回执演示数据二', NOW(), NOW());

CREATE TABLE spare_part_ledger (
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
INSERT INTO spare_part_ledger (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('176-10-001', '备件台账示例一', '备件类型', '仓储人员A', '2026-05-16 09:00', 'DISPATCHING', '备件编号、备件名称、备件类型、仓储人员、入库时间和备件状态维护', NOW(), NOW()),
('176-10-002', '备件台账示例二', '备件类型', '仓储人员B', '2026-05-17 14:00', 'RECEIPT_PENDING', '备件台账演示数据二', NOW(), NOW());

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
('176-11-001', '风险预警示例一', '预警类型', '处置人员A', '2026-05-16 09:00', 'REPAIRING', '预警编号、预警对象、预警类型、处置人员、预警时间和预警状态维护', NOW(), NOW()),
('176-11-002', '风险预警示例二', '预警类型', '处置人员B', '2026-05-17 14:00', 'REVIEWED', '风险预警演示数据二', NOW(), NOW());

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
('176-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'RECEIPT_PENDING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('176-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'WARNING', '操作日志演示数据二', NOW(), NOW());
