DROP DATABASE IF EXISTS hazard_work_152;
CREATE DATABASE hazard_work_152 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE hazard_work_152;

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
('admin', '123456', '系统管理员', 'ADMIN', '工厂安全作业中心', '13915200001', 'admin@hazard152.local', 1, NOW(), NOW()),
('safety', '123456', '安全主管', 'SAFETY', '工厂安全作业中心', '13915200002', 'safety@hazard152.local', 1, NOW(), NOW()),
('approver', '123456', '审批负责人', 'APPROVER', '工厂安全作业中心', '13915200003', 'approver@hazard152.local', 1, NOW(), NOW()),
('guardian', '123456', '现场监护人', 'GUARDIAN', '工厂安全作业中心', '13915200004', 'guardian@hazard152.local', 1, NOW(), NOW()),
('worker', '123456', '作业申请人', 'WORKER', '工厂安全作业中心', '13915200005', 'worker@hazard152.local', 1, NOW(), NOW());

CREATE TABLE work_area (
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
INSERT INTO work_area (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('152-01-001', '作业区域示例一', '危险等级', '负责人A', '2026-05-16 09:00', 'BOOKED', '区域编码、区域名称、危险等级、负责人、管控时段和区域状态维护', NOW(), NOW()),
('152-01-002', '作业区域示例二', '危险等级', '负责人B', '2026-05-17 14:00', 'VERIFIED', '作业区域演示数据二', NOW(), NOW());

CREATE TABLE hazard_source (
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
INSERT INTO hazard_source (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('152-02-001', '风险源台账示例一', '风险类型', '责任人A', '2026-05-16 09:00', 'SCHEDULED', '风险编号、风险源名称、风险类型、责任人、巡检周期和管控状态维护', NOW(), NOW()),
('152-02-002', '风险源台账示例二', '风险类型', '责任人B', '2026-05-17 14:00', 'PROCESSING', '风险源台账演示数据二', NOW(), NOW());

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
('152-03-001', '作业人员档案示例一', '资质等级', '所属班组A', '2026-05-16 09:00', 'VERIFIED', '工号、姓名、资质等级、所属班组、有效期和资质状态维护', NOW(), NOW()),
('152-03-002', '作业人员档案示例二', '资质等级', '所属班组B', '2026-05-17 14:00', 'FINISHED', '作业人员档案演示数据二', NOW(), NOW());

CREATE TABLE work_permit (
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
INSERT INTO work_permit (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('152-04-001', '作业票申请示例一', '作业类型', '申请人A', '2026-05-16 09:00', 'PROCESSING', '作业票号、作业项目、作业类型、申请人、作业时间和申请状态维护', NOW(), NOW()),
('152-04-002', '作业票申请示例二', '作业类型', '申请人B', '2026-05-17 14:00', 'WARNING', '作业票申请演示数据二', NOW(), NOW());

CREATE TABLE permit_approval (
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
INSERT INTO permit_approval (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('152-05-001', '审批链路示例一', '审批节点', '审批人A', '2026-05-16 09:00', 'FINISHED', '审批编号、作业票号、审批节点、审批人、审批时间和审批状态维护', NOW(), NOW()),
('152-05-002', '审批链路示例二', '审批节点', '审批人B', '2026-05-17 14:00', 'PUBLISHED', '审批链路演示数据二', NOW(), NOW());

CREATE TABLE safety_briefing (
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
INSERT INTO safety_briefing (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('152-06-001', '安全交底示例一', '交底类型', '交底人A', '2026-05-16 09:00', 'WARNING', '交底编号、作业项目、交底类型、交底人、交底时间和确认状态维护', NOW(), NOW()),
('152-06-002', '安全交底示例二', '交底类型', '交底人B', '2026-05-17 14:00', 'ACTIVE', '安全交底演示数据二', NOW(), NOW());

CREATE TABLE guardian_assignment (
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
INSERT INTO guardian_assignment (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('152-07-001', '监护安排示例一', '监护类型', '监护人A', '2026-05-16 09:00', 'PUBLISHED', '安排编号、作业票号、监护类型、监护人、监护时段和监护状态维护', NOW(), NOW()),
('152-07-002', '监护安排示例二', '监护类型', '监护人B', '2026-05-17 14:00', 'BOOKED', '监护安排演示数据二', NOW(), NOW());

CREATE TABLE monitor_record (
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
INSERT INTO monitor_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('152-08-001', '监护记录示例一', '监护项目', '记录人A', '2026-05-16 09:00', 'ACTIVE', '记录编号、作业票号、监护项目、记录人、记录时间和现场状态维护', NOW(), NOW()),
('152-08-002', '监护记录示例二', '监护项目', '记录人B', '2026-05-17 14:00', 'SCHEDULED', '监护记录演示数据二', NOW(), NOW());

CREATE TABLE hidden_danger (
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
INSERT INTO hidden_danger (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('152-09-001', '隐患闭环示例一', '隐患类型', '发现人A', '2026-05-16 09:00', 'BOOKED', '隐患编号、隐患位置、隐患类型、发现人、整改期限和整改状态维护', NOW(), NOW()),
('152-09-002', '隐患闭环示例二', '隐患类型', '发现人B', '2026-05-17 14:00', 'VERIFIED', '隐患闭环演示数据二', NOW(), NOW());

CREATE TABLE gas_detection (
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
INSERT INTO gas_detection (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('152-10-001', '气体检测示例一', '检测类型', '检测人A', '2026-05-16 09:00', 'SCHEDULED', '检测编号、作业票号、检测类型、检测人、检测时间和检测状态维护', NOW(), NOW()),
('152-10-002', '气体检测示例二', '检测类型', '检测人B', '2026-05-17 14:00', 'PROCESSING', '气体检测演示数据二', NOW(), NOW());

CREATE TABLE emergency_plan (
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
INSERT INTO emergency_plan (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('152-11-001', '应急预案示例一', '适用场景', '负责人A', '2026-05-16 09:00', 'VERIFIED', '预案编号、预案名称、适用场景、负责人、演练时间和启用状态维护', NOW(), NOW()),
('152-11-002', '应急预案示例二', '适用场景', '负责人B', '2026-05-17 14:00', 'FINISHED', '应急预案演示数据二', NOW(), NOW());

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
('152-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'PROCESSING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('152-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'WARNING', '操作日志演示数据二', NOW(), NOW());
