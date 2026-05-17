DROP DATABASE IF EXISTS powerbank_198;
CREATE DATABASE powerbank_198 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE powerbank_198;

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
('admin', '123456', '系统管理员', 'ADMIN', '城市共享充电宝运营中心', '13919800001', 'admin@powerbank198.local', 1, NOW(), NOW()),
('operator', '123456', '运营管理员', 'OPERATOR', '城市共享充电宝运营中心', '13919800002', 'operator@powerbank198.local', 1, NOW(), NOW()),
('site', '123456', '点位商户', 'SITE', '城市共享充电宝运营中心', '13919800003', 'site@powerbank198.local', 1, NOW(), NOW()),
('inspector', '123456', '巡检人员', 'INSPECTOR', '城市共享充电宝运营中心', '13919800004', 'inspector@powerbank198.local', 1, NOW(), NOW()),
('finance', '123456', '财务结算', 'FINANCE', '城市共享充电宝运营中心', '13919800005', 'finance@powerbank198.local', 1, NOW(), NOW()),
('merchant', '123456', '合作商户', 'MERCHANT', '城市共享充电宝运营中心', '13919800006', 'merchant@powerbank198.local', 1, NOW(), NOW());

CREATE TABLE placement_site (
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
INSERT INTO placement_site (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('198-01-001', '投放点位示例一', '点位类型', '负责人A', '2026-05-16 09:00', 'PLANNING', '点位编号、点位名称、点位类型、负责人、建档时间和点位状态维护', NOW(), NOW()),
('198-01-002', '投放点位示例二', '点位类型', '负责人B', '2026-05-17 14:00', 'INSPECTING', '投放点位演示数据二', NOW(), NOW());

CREATE TABLE cabinet_profile (
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
INSERT INTO cabinet_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('198-02-001', '设备柜档案示例一', '设备类型', '维护人员A', '2026-05-16 09:00', 'DEPLOYING', '设备柜编号、点位名称、设备类型、维护人员、投放时间和设备柜状态维护', NOW(), NOW()),
('198-02-002', '设备柜档案示例二', '设备类型', '维护人员B', '2026-05-17 14:00', 'REPAIRING', '设备柜档案演示数据二', NOW(), NOW());

CREATE TABLE powerbank_device (
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
INSERT INTO powerbank_device (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('198-03-001', '充电宝档案示例一', '电池规格', '维护人员A', '2026-05-16 09:00', 'INSPECTING', '设备编号、所属机柜、电池规格、维护人员、入库时间和设备状态维护', NOW(), NOW()),
('198-03-002', '充电宝档案示例二', '电池规格', '维护人员B', '2026-05-17 14:00', 'RECYCLING', '充电宝档案演示数据二', NOW(), NOW());

CREATE TABLE placement_plan (
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
INSERT INTO placement_plan (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('198-04-001', '点位投放示例一', '投放批次', '运营人员A', '2026-05-16 09:00', 'REPAIRING', '投放编号、点位名称、投放批次、运营人员、计划时间和投放状态维护', NOW(), NOW()),
('198-04-002', '点位投放示例二', '投放批次', '运营人员B', '2026-05-17 14:00', 'SETTLING', '点位投放演示数据二', NOW(), NOW());

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
('198-05-001', '设备巡检示例一', '巡检类型', '巡检人员A', '2026-05-16 09:00', 'RECYCLING', '巡检编号、设备柜名称、巡检类型、巡检人员、巡检时间和巡检状态维护', NOW(), NOW()),
('198-05-002', '设备巡检示例二', '巡检类型', '巡检人员B', '2026-05-17 14:00', 'CLOSED', '设备巡检演示数据二', NOW(), NOW());

CREATE TABLE fault_repair (
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
INSERT INTO fault_repair (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('198-06-001', '故障维修示例一', '故障类型', '维修人员A', '2026-05-16 09:00', 'SETTLING', '维修编号、故障设备、故障类型、维修人员、报修时间和维修状态维护', NOW(), NOW()),
('198-06-002', '故障维修示例二', '故障类型', '维修人员B', '2026-05-17 14:00', 'REGISTERED', '故障维修演示数据二', NOW(), NOW());

CREATE TABLE abnormal_recycle (
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
INSERT INTO abnormal_recycle (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('198-07-001', '异常回收示例一', '异常类型', '回收人员A', '2026-05-16 09:00', 'CLOSED', '回收编号、异常设备、异常类型、回收人员、回收时间和回收状态维护', NOW(), NOW()),
('198-07-002', '异常回收示例二', '异常类型', '回收人员B', '2026-05-17 14:00', 'PLANNING', '异常回收演示数据二', NOW(), NOW());

CREATE TABLE lease_order (
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
INSERT INTO lease_order (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('198-08-001', '租借订单示例一', '租借类型', '下单用户A', '2026-05-16 09:00', 'REGISTERED', '订单编号、点位名称、租借类型、下单用户、下单时间和订单状态维护', NOW(), NOW()),
('198-08-002', '租借订单示例二', '租借类型', '下单用户B', '2026-05-17 14:00', 'DEPLOYING', '租借订单演示数据二', NOW(), NOW());

CREATE TABLE merchant_income (
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
INSERT INTO merchant_income (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('198-09-001', '商户收益示例一', '收益类型', '商户负责人A', '2026-05-16 09:00', 'PLANNING', '收益编号、合作点位、收益类型、商户负责人、入账时间和收益状态维护', NOW(), NOW()),
('198-09-002', '商户收益示例二', '收益类型', '商户负责人B', '2026-05-17 14:00', 'INSPECTING', '商户收益演示数据二', NOW(), NOW());

CREATE TABLE settlement_record (
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
INSERT INTO settlement_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('198-10-001', '收益结算示例一', '结算类型', '财务人员A', '2026-05-16 09:00', 'DEPLOYING', '结算编号、结算周期、结算类型、财务人员、结算时间和结算状态维护', NOW(), NOW()),
('198-10-002', '收益结算示例二', '结算类型', '财务人员B', '2026-05-17 14:00', 'REPAIRING', '收益结算演示数据二', NOW(), NOW());

CREATE TABLE inventory_transfer (
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
INSERT INTO inventory_transfer (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('198-11-001', '库存调拨示例一', '调拨类型', '经办人员A', '2026-05-16 09:00', 'INSPECTING', '调拨编号、调拨仓点、调拨类型、经办人员、调拨时间和调拨状态维护', NOW(), NOW()),
('198-11-002', '库存调拨示例二', '调拨类型', '经办人员B', '2026-05-17 14:00', 'RECYCLING', '库存调拨演示数据二', NOW(), NOW());

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
('198-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'REPAIRING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('198-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'SETTLING', '操作日志演示数据二', NOW(), NOW());
