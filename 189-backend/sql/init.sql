DROP DATABASE IF EXISTS farm_machinery_189;
CREATE DATABASE farm_machinery_189 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE farm_machinery_189;

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
('admin', '123456', '系统管理员', 'ADMIN', '乡镇农机服务中心', '13918900001', 'admin@farmmachinery189.local', 1, NOW(), NOW()),
('station', '123456', '农机站管理员', 'STATION', '乡镇农机服务中心', '13918900002', 'station@farmmachinery189.local', 1, NOW(), NOW()),
('dispatch', '123456', '调度员', 'DISPATCH', '乡镇农机服务中心', '13918900003', 'dispatch@farmmachinery189.local', 1, NOW(), NOW()),
('driver', '123456', '农机机手', 'DRIVER', '乡镇农机服务中心', '13918900004', 'driver@farmmachinery189.local', 1, NOW(), NOW()),
('mechanic', '123456', '维修人员', 'MECHANIC', '乡镇农机服务中心', '13918900005', 'mechanic@farmmachinery189.local', 1, NOW(), NOW()),
('farmer', '123456', '农户', 'FARMER', '乡镇农机服务中心', '13918900006', 'farmer@farmmachinery189.local', 1, NOW(), NOW());

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
('189-01-001', '农机站点示例一', '服务类型', '负责人A', '2026-05-16 09:00', 'BOOKED', '站点编号、站点名称、服务类型、负责人、启用时间和站点状态维护', NOW(), NOW()),
('189-01-002', '农机站点示例二', '服务类型', '负责人B', '2026-05-17 14:00', 'WORKING', '农机站点演示数据二', NOW(), NOW());

CREATE TABLE machine_asset (
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
INSERT INTO machine_asset (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('189-02-001', '农机档案示例一', '农机类型', '管理人员A', '2026-05-16 09:00', 'DISPATCHED', '农机编号、农机名称、农机类型、管理人员、入库时间和农机状态维护', NOW(), NOW()),
('189-02-002', '农机档案示例二', '农机类型', '管理人员B', '2026-05-17 14:00', 'MAINTAINING', '农机档案演示数据二', NOW(), NOW());

CREATE TABLE farmer_profile (
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
INSERT INTO farmer_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('189-03-001', '农户档案示例一', '种植类型', '联系人A', '2026-05-16 09:00', 'WORKING', '农户编号、农户姓名、种植类型、联系人、建档时间和服务状态维护', NOW(), NOW()),
('189-03-002', '农户档案示例二', '种植类型', '联系人B', '2026-05-17 14:00', 'REPAIRING', '农户档案演示数据二', NOW(), NOW());

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
('189-04-001', '机手档案示例一', '作业类型', '所属站点A', '2026-05-16 09:00', 'MAINTAINING', '机手编号、机手姓名、作业类型、所属站点、建档时间和机手状态维护', NOW(), NOW()),
('189-04-002', '机手档案示例二', '作业类型', '所属站点B', '2026-05-17 14:00', 'SETTLED', '机手档案演示数据二', NOW(), NOW());

CREATE TABLE operation_booking (
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
INSERT INTO operation_booking (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('189-05-001', '作业预约示例一', '作业类型', '预约农户A', '2026-05-16 09:00', 'REPAIRING', '预约编号、作业地块、作业类型、预约农户、预约时间和预约状态维护', NOW(), NOW()),
('189-05-002', '作业预约示例二', '作业类型', '预约农户B', '2026-05-17 14:00', 'CLOSED', '作业预约演示数据二', NOW(), NOW());

CREATE TABLE dispatch_schedule (
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
INSERT INTO dispatch_schedule (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('189-06-001', '机手调度示例一', '作业类型', '调度人员A', '2026-05-16 09:00', 'SETTLED', '调度编号、预约任务、作业类型、调度人员、调度时间和调度状态维护', NOW(), NOW()),
('189-06-002', '机手调度示例二', '作业类型', '调度人员B', '2026-05-17 14:00', 'REGISTERED', '机手调度演示数据二', NOW(), NOW());

CREATE TABLE field_work_order (
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
INSERT INTO field_work_order (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('189-07-001', '作业派单示例一', '作业类型', '机手人员A', '2026-05-16 09:00', 'CLOSED', '派单编号、作业地块、作业类型、机手人员、派单时间和派单状态维护', NOW(), NOW()),
('189-07-002', '作业派单示例二', '作业类型', '机手人员B', '2026-05-17 14:00', 'BOOKED', '作业派单演示数据二', NOW(), NOW());

CREATE TABLE work_completion (
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
INSERT INTO work_completion (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('189-08-001', '作业回执示例一', '回执类型', '机手人员A', '2026-05-16 09:00', 'REGISTERED', '回执编号、作业派单、回执类型、机手人员、完成时间和回执状态维护', NOW(), NOW()),
('189-08-002', '作业回执示例二', '回执类型', '机手人员B', '2026-05-17 14:00', 'DISPATCHED', '作业回执演示数据二', NOW(), NOW());

CREATE TABLE maintenance_record (
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
INSERT INTO maintenance_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('189-09-001', '维修保养示例一', '保养类型', '维修人员A', '2026-05-16 09:00', 'BOOKED', '保养编号、农机设备、保养类型、维修人员、保养时间和保养状态维护', NOW(), NOW()),
('189-09-002', '维修保养示例二', '保养类型', '维修人员B', '2026-05-17 14:00', 'WORKING', '维修保养演示数据二', NOW(), NOW());

CREATE TABLE repair_followup (
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
INSERT INTO repair_followup (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('189-10-001', '维修跟踪示例一', '跟踪类型', '维修人员A', '2026-05-16 09:00', 'DISPATCHED', '跟踪编号、维修工单、跟踪类型、维修人员、跟踪时间和跟踪状态维护', NOW(), NOW()),
('189-10-002', '维修跟踪示例二', '跟踪类型', '维修人员B', '2026-05-17 14:00', 'MAINTAINING', '维修跟踪演示数据二', NOW(), NOW());

CREATE TABLE seasonal_statistics (
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
INSERT INTO seasonal_statistics (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('189-11-001', '季节统计示例一', '统计类型', '统计人员A', '2026-05-16 09:00', 'WORKING', '统计编号、作业季节、统计类型、统计人员、统计时间和统计状态维护', NOW(), NOW()),
('189-11-002', '季节统计示例二', '统计类型', '统计人员B', '2026-05-17 14:00', 'REPAIRING', '季节统计演示数据二', NOW(), NOW());

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
('189-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'MAINTAINING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('189-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'SETTLED', '操作日志演示数据二', NOW(), NOW());
