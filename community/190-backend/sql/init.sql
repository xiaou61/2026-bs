DROP DATABASE IF EXISTS smart_building_190;
CREATE DATABASE smart_building_190 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE smart_building_190;

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
('admin', '123456', '系统管理员', 'ADMIN', '智慧楼宇运维中心', '13919000001', 'admin@smartbuilding190.local', 1, NOW(), NOW()),
('property', '123456', '物业管理员', 'PROPERTY', '智慧楼宇运维中心', '13919000002', 'property@smartbuilding190.local', 1, NOW(), NOW()),
('dispatch', '123456', '访修调度员', 'DISPATCH', '智慧楼宇运维中心', '13919000003', 'dispatch@smartbuilding190.local', 1, NOW(), NOW()),
('technician', '123456', '维修技师', 'TECHNICIAN', '智慧楼宇运维中心', '13919000004', 'technician@smartbuilding190.local', 1, NOW(), NOW()),
('inspector', '123456', '设备巡检员', 'INSPECTOR', '智慧楼宇运维中心', '13919000005', 'inspector@smartbuilding190.local', 1, NOW(), NOW()),
('tenant', '123456', '入驻用户', 'TENANT', '智慧楼宇运维中心', '13919000006', 'tenant@smartbuilding190.local', 1, NOW(), NOW());

CREATE TABLE building_profile (
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
INSERT INTO building_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('190-01-001', '楼宇档案示例一', '楼层区域', '物业负责人A', '2026-05-16 09:00', 'SUBMITTED', '楼宇编号、楼宇名称、楼层区域、物业负责人、建档时间和楼宇状态维护', NOW(), NOW()),
('190-01-002', '楼宇档案示例二', '楼层区域', '物业负责人B', '2026-05-17 14:00', 'IN_PROGRESS', '楼宇档案演示数据二', NOW(), NOW());

CREATE TABLE equipment_asset (
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
INSERT INTO equipment_asset (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('190-02-001', '设备档案示例一', '设备类型', '管护人员A', '2026-05-16 09:00', 'DISPATCHED', '设备编号、设备名称、设备类型、管护人员、启用时间和设备状态维护', NOW(), NOW()),
('190-02-002', '设备档案示例二', '设备类型', '管护人员B', '2026-05-17 14:00', 'MAINTENANCE', '设备档案演示数据二', NOW(), NOW());

CREATE TABLE tenant_profile (
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
INSERT INTO tenant_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('190-03-001', '入驻档案示例一', '租户类型', '联系人A', '2026-05-16 09:00', 'IN_PROGRESS', '档案编号、入驻单位、租户类型、联系人、入驻时间和服务状态维护', NOW(), NOW()),
('190-03-002', '入驻档案示例二', '租户类型', '联系人B', '2026-05-17 14:00', 'WARNING', '入驻档案演示数据二', NOW(), NOW());

CREATE TABLE visit_repair_ticket (
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
INSERT INTO visit_repair_ticket (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('190-04-001', '访修工单示例一', '访修类型', '报修人员A', '2026-05-16 09:00', 'MAINTENANCE', '工单编号、报修位置、访修类型、报修人员、提交时间和工单状态维护', NOW(), NOW()),
('190-04-002', '访修工单示例二', '访修类型', '报修人员B', '2026-05-17 14:00', 'RESOLVED', '访修工单演示数据二', NOW(), NOW());

CREATE TABLE repair_dispatch (
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
INSERT INTO repair_dispatch (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('190-05-001', '维修派工示例一', '维修类型', '维修技师A', '2026-05-16 09:00', 'WARNING', '派工编号、关联工单、维修类型、维修技师、派工时间和派工状态维护', NOW(), NOW()),
('190-05-002', '维修派工示例二', '维修类型', '维修技师B', '2026-05-17 14:00', 'CLOSED', '维修派工演示数据二', NOW(), NOW());

CREATE TABLE maintenance_plan (
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
INSERT INTO maintenance_plan (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('190-06-001', '保养计划示例一', '保养类型', '计划负责人A', '2026-05-16 09:00', 'RESOLVED', '计划编号、保养设备、保养类型、计划负责人、计划时间和计划状态维护', NOW(), NOW()),
('190-06-002', '保养计划示例二', '保养类型', '计划负责人B', '2026-05-17 14:00', 'REGISTERED', '保养计划演示数据二', NOW(), NOW());

CREATE TABLE maintenance_task (
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
INSERT INTO maintenance_task (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('190-07-001', '保养任务示例一', '任务类型', '执行人员A', '2026-05-16 09:00', 'CLOSED', '任务编号、保养计划、任务类型、执行人员、执行时间和任务状态维护', NOW(), NOW()),
('190-07-002', '保养任务示例二', '任务类型', '执行人员B', '2026-05-17 14:00', 'SUBMITTED', '保养任务演示数据二', NOW(), NOW());

CREATE TABLE fault_alert (
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
INSERT INTO fault_alert (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('190-08-001', '故障预警示例一', '预警类型', '处置人员A', '2026-05-16 09:00', 'REGISTERED', '预警编号、预警设备、预警类型、处置人员、预警时间和预警状态维护', NOW(), NOW()),
('190-08-002', '故障预警示例二', '预警类型', '处置人员B', '2026-05-17 14:00', 'DISPATCHED', '故障预警演示数据二', NOW(), NOW());

CREATE TABLE inspection_record (
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
INSERT INTO inspection_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('190-09-001', '巡检记录示例一', '巡检类型', '巡检人员A', '2026-05-16 09:00', 'SUBMITTED', '巡检编号、巡检区域、巡检类型、巡检人员、巡检时间和巡检状态维护', NOW(), NOW()),
('190-09-002', '巡检记录示例二', '巡检类型', '巡检人员B', '2026-05-17 14:00', 'IN_PROGRESS', '巡检记录演示数据二', NOW(), NOW());

CREATE TABLE spare_part_stock (
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
INSERT INTO spare_part_stock (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('190-10-001', '备件库存示例一', '备件类型', '库管人员A', '2026-05-16 09:00', 'DISPATCHED', '备件编号、备件名称、备件类型、库管人员、入库时间和库存状态维护', NOW(), NOW()),
('190-10-002', '备件库存示例二', '备件类型', '库管人员B', '2026-05-17 14:00', 'MAINTENANCE', '备件库存演示数据二', NOW(), NOW());

CREATE TABLE service_feedback (
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
INSERT INTO service_feedback (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('190-11-001', '服务评价示例一', '评价类型', '评价人员A', '2026-05-16 09:00', 'IN_PROGRESS', '评价编号、服务工单、评价类型、评价人员、评价时间和评价状态维护', NOW(), NOW()),
('190-11-002', '服务评价示例二', '评价类型', '评价人员B', '2026-05-17 14:00', 'WARNING', '服务评价演示数据二', NOW(), NOW());

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
('190-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'MAINTENANCE', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('190-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'RESOLVED', '操作日志演示数据二', NOW(), NOW());
