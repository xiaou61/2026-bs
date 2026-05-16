DROP DATABASE IF EXISTS emergency_supply_171;
CREATE DATABASE emergency_supply_171 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE emergency_supply_171;

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
('admin', '123456', '系统管理员', 'ADMIN', '应急物资储备中心', '13917100001', 'admin@supply171.local', 1, NOW(), NOW()),
('warehouse', '123456', '仓储管理员', 'WAREHOUSE', '应急物资储备中心', '13917100002', 'warehouse@supply171.local', 1, NOW(), NOW()),
('checker', '123456', '盘点人员', 'CHECKER', '应急物资储备中心', '13917100003', 'checker@supply171.local', 1, NOW(), NOW()),
('applicant', '123456', '申领单位', 'APPLICANT', '应急物资储备中心', '13917100004', 'applicant@supply171.local', 1, NOW(), NOW()),
('dispatch', '123456', '调度人员', 'DISPATCH', '应急物资储备中心', '13917100005', 'dispatch@supply171.local', 1, NOW(), NOW()),
('auditor', '123456', '审核人员', 'AUDITOR', '应急物资储备中心', '13917100006', 'auditor@supply171.local', 1, NOW(), NOW());

CREATE TABLE reserve_warehouse (
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
INSERT INTO reserve_warehouse (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('171-01-001', '储备仓库示例一', '仓库类型', '负责人员A', '2026-05-16 09:00', 'CHECKING', '仓库编号、仓库名称、仓库类型、负责人员、启用时间和仓库状态维护', NOW(), NOW()),
('171-01-002', '储备仓库示例二', '仓库类型', '负责人员B', '2026-05-17 14:00', 'PROCESSING', '储备仓库演示数据二', NOW(), NOW());

CREATE TABLE material_category (
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
INSERT INTO material_category (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('171-02-001', '物资分类示例一', '保障类型', '负责人员A', '2026-05-16 09:00', 'PENDING_APPROVAL', '分类编号、分类名称、保障类型、负责人员、启用时间和分类状态维护', NOW(), NOW()),
('171-02-002', '物资分类示例二', '保障类型', '负责人员B', '2026-05-17 14:00', 'OUTBOUND', '物资分类演示数据二', NOW(), NOW());

CREATE TABLE material_ledger (
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
INSERT INTO material_ledger (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('171-03-001', '物资台账示例一', '物资类别', '管理人员A', '2026-05-16 09:00', 'PROCESSING', '物资编号、物资名称、物资类别、管理人员、建档时间和物资状态维护', NOW(), NOW()),
('171-03-002', '物资台账示例二', '物资类别', '管理人员B', '2026-05-17 14:00', 'FINISHED', '物资台账演示数据二', NOW(), NOW());

CREATE TABLE stock_batch (
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
INSERT INTO stock_batch (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('171-04-001', '库存批次示例一', '储备仓库', '仓储人员A', '2026-05-16 09:00', 'OUTBOUND', '批次编号、物资名称、储备仓库、仓储人员、入库时间和批次状态维护', NOW(), NOW()),
('171-04-002', '库存批次示例二', '储备仓库', '仓储人员B', '2026-05-17 14:00', 'WARNING', '库存批次演示数据二', NOW(), NOW());

CREATE TABLE inventory_check (
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
INSERT INTO inventory_check (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('171-05-001', '库存盘点示例一', '盘点类型', '盘点人员A', '2026-05-16 09:00', 'FINISHED', '盘点编号、盘点仓库、盘点类型、盘点人员、盘点时间和盘点状态维护', NOW(), NOW()),
('171-05-002', '库存盘点示例二', '盘点类型', '盘点人员B', '2026-05-17 14:00', 'PUBLISHED', '库存盘点演示数据二', NOW(), NOW());

CREATE TABLE check_difference (
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
INSERT INTO check_difference (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('171-06-001', '盘点差异示例一', '差异类型', '处理人员A', '2026-05-16 09:00', 'WARNING', '差异编号、关联盘点、差异类型、处理人员、发现时间和差异状态维护', NOW(), NOW()),
('171-06-002', '盘点差异示例二', '差异类型', '处理人员B', '2026-05-17 14:00', 'ACTIVE', '盘点差异演示数据二', NOW(), NOW());

CREATE TABLE requisition_order (
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
INSERT INTO requisition_order (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('171-07-001', '申领工单示例一', '申领单位', '申请人员A', '2026-05-16 09:00', 'PUBLISHED', '工单编号、申领物资、申领单位、申请人员、申请时间和工单状态维护', NOW(), NOW()),
('171-07-002', '申领工单示例二', '申领单位', '申请人员B', '2026-05-17 14:00', 'CHECKING', '申领工单演示数据二', NOW(), NOW());

CREATE TABLE allocation_approval (
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
INSERT INTO allocation_approval (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('171-08-001', '调拨审批示例一', '审批类型', '审核人员A', '2026-05-16 09:00', 'ACTIVE', '审批编号、调拨物资、审批类型、审核人员、审批时间和审批状态维护', NOW(), NOW()),
('171-08-002', '调拨审批示例二', '审批类型', '审核人员B', '2026-05-17 14:00', 'PENDING_APPROVAL', '调拨审批演示数据二', NOW(), NOW());

CREATE TABLE dispatch_task (
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
INSERT INTO dispatch_task (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('171-09-001', '调度任务示例一', '调度类型', '调度人员A', '2026-05-16 09:00', 'CHECKING', '任务编号、调度物资、调度类型、调度人员、调度时间和任务状态维护', NOW(), NOW()),
('171-09-002', '调度任务示例二', '调度类型', '调度人员B', '2026-05-17 14:00', 'PROCESSING', '调度任务演示数据二', NOW(), NOW());

CREATE TABLE outbound_record (
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
INSERT INTO outbound_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('171-10-001', '出库记录示例一', '出库类型', '仓储人员A', '2026-05-16 09:00', 'PENDING_APPROVAL', '出库编号、出库物资、出库类型、仓储人员、出库时间和出库状态维护', NOW(), NOW()),
('171-10-002', '出库记录示例二', '出库类型', '仓储人员B', '2026-05-17 14:00', 'OUTBOUND', '出库记录演示数据二', NOW(), NOW());

CREATE TABLE stock_warning (
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
INSERT INTO stock_warning (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('171-11-001', '库存预警示例一', '预警类型', '责任人员A', '2026-05-16 09:00', 'PROCESSING', '预警编号、预警物资、预警类型、责任人员、预警时间和预警状态维护', NOW(), NOW()),
('171-11-002', '库存预警示例二', '预警类型', '责任人员B', '2026-05-17 14:00', 'FINISHED', '库存预警演示数据二', NOW(), NOW());

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
('171-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'OUTBOUND', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('171-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'WARNING', '操作日志演示数据二', NOW(), NOW());
