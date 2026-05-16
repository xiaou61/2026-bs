DROP DATABASE IF EXISTS logistics_park_157;
CREATE DATABASE logistics_park_157 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE logistics_park_157;

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
('admin', '123456', '系统管理员', 'ADMIN', '物流园区调度中心', '13915700001', 'admin@park157.local', 1, NOW(), NOW()),
('dispatcher', '123456', '调度主管', 'DISPATCHER', '物流园区调度中心', '13915700002', 'dispatcher@park157.local', 1, NOW(), NOW()),
('gatekeeper', '123456', '门岗核验员', 'GATEKEEPER', '物流园区调度中心', '13915700003', 'gatekeeper@park157.local', 1, NOW(), NOW()),
('yardmaster', '123456', '场内调度员', 'YARDMASTER', '物流园区调度中心', '13915700004', 'yardmaster@park157.local', 1, NOW(), NOW()),
('carrier', '123456', '承运商代表', 'CARRIER', '物流园区调度中心', '13915700005', 'carrier@park157.local', 1, NOW(), NOW());

CREATE TABLE carrier_company (
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
INSERT INTO carrier_company (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('157-01-001', '承运商档案示例一', '业务类型', '联系人A', '2026-05-16 09:00', 'BOOKED', '承运商编号、承运商名称、业务类型、联系人、合作时间和合作状态维护', NOW(), NOW()),
('157-01-002', '承运商档案示例二', '业务类型', '联系人B', '2026-05-17 14:00', 'VERIFIED', '承运商档案演示数据二', NOW(), NOW());

CREATE TABLE vehicle_profile (
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
INSERT INTO vehicle_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('157-02-001', '车辆档案示例一', '车辆类型', '司机姓名A', '2026-05-16 09:00', 'SCHEDULED', '车辆编号、车牌号码、车辆类型、司机姓名、备案时间和车辆状态维护', NOW(), NOW()),
('157-02-002', '车辆档案示例二', '车辆类型', '司机姓名B', '2026-05-17 14:00', 'PROCESSING', '车辆档案演示数据二', NOW(), NOW());

CREATE TABLE driver_profile (
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
INSERT INTO driver_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('157-03-001', '司机档案示例一', '证件类型', '承运商A', '2026-05-16 09:00', 'VERIFIED', '司机编号、司机姓名、证件类型、承运商、备案时间和司机状态维护', NOW(), NOW()),
('157-03-002', '司机档案示例二', '证件类型', '承运商B', '2026-05-17 14:00', 'FINISHED', '司机档案演示数据二', NOW(), NOW());

CREATE TABLE gate_appointment (
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
INSERT INTO gate_appointment (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('157-04-001', '入场预约示例一', '预约类型', '预约人A', '2026-05-16 09:00', 'PROCESSING', '预约编号、预约车辆、预约类型、预约人、预约时间和预约状态维护', NOW(), NOW()),
('157-04-002', '入场预约示例二', '预约类型', '预约人B', '2026-05-17 14:00', 'WARNING', '入场预约演示数据二', NOW(), NOW());

CREATE TABLE time_slot_plan (
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
INSERT INTO time_slot_plan (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('157-05-001', '时段计划示例一', '作业类型', '计划人A', '2026-05-16 09:00', 'FINISHED', '时段编号、时段名称、作业类型、计划人、计划时间和开放状态维护', NOW(), NOW()),
('157-05-002', '时段计划示例二', '作业类型', '计划人B', '2026-05-17 14:00', 'PUBLISHED', '时段计划演示数据二', NOW(), NOW());

CREATE TABLE gate_checkin (
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
INSERT INTO gate_checkin (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('157-06-001', '门岗核验示例一', '核验类型', '核验人A', '2026-05-16 09:00', 'WARNING', '核验编号、入场车辆、核验类型、核验人、核验时间和核验状态维护', NOW(), NOW()),
('157-06-002', '门岗核验示例二', '核验类型', '核验人B', '2026-05-17 14:00', 'ACTIVE', '门岗核验演示数据二', NOW(), NOW());

CREATE TABLE queue_ticket (
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
INSERT INTO queue_ticket (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('157-07-001', '排队叫号示例一', '作业类型', '叫号人A', '2026-05-16 09:00', 'PUBLISHED', '叫号编号、排队车辆、作业类型、叫号人、叫号时间和排队状态维护', NOW(), NOW()),
('157-07-002', '排队叫号示例二', '作业类型', '叫号人B', '2026-05-17 14:00', 'BOOKED', '排队叫号演示数据二', NOW(), NOW());

CREATE TABLE dock_door (
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
INSERT INTO dock_door (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('157-08-001', '道口资源示例一', '道口类型', '负责人A', '2026-05-16 09:00', 'ACTIVE', '道口编号、道口名称、道口类型、负责人、启用时间和道口状态维护', NOW(), NOW()),
('157-08-002', '道口资源示例二', '道口类型', '负责人B', '2026-05-17 14:00', 'SCHEDULED', '道口资源演示数据二', NOW(), NOW());

CREATE TABLE dock_assignment (
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
INSERT INTO dock_assignment (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('157-09-001', '道口分配示例一', '作业类型', '调度人A', '2026-05-16 09:00', 'BOOKED', '分配编号、分配车辆、作业类型、调度人、分配时间和分配状态维护', NOW(), NOW()),
('157-09-002', '道口分配示例二', '作业类型', '调度人B', '2026-05-17 14:00', 'VERIFIED', '道口分配演示数据二', NOW(), NOW());

CREATE TABLE loading_task (
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
INSERT INTO loading_task (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('157-10-001', '装卸任务示例一', '装卸类型', '负责人A', '2026-05-16 09:00', 'SCHEDULED', '任务编号、货物批次、装卸类型、负责人、作业时间和任务状态维护', NOW(), NOW()),
('157-10-002', '装卸任务示例二', '装卸类型', '负责人B', '2026-05-17 14:00', 'PROCESSING', '装卸任务演示数据二', NOW(), NOW());

CREATE TABLE turnaround_record (
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
INSERT INTO turnaround_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('157-11-001', '周转统计示例一', '统计类型', '统计人A', '2026-05-16 09:00', 'VERIFIED', '统计编号、统计车辆、统计类型、统计人、统计周期和统计状态维护', NOW(), NOW()),
('157-11-002', '周转统计示例二', '统计类型', '统计人B', '2026-05-17 14:00', 'FINISHED', '周转统计演示数据二', NOW(), NOW());

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
('157-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'PROCESSING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('157-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'WARNING', '操作日志演示数据二', NOW(), NOW());
