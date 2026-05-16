DROP DATABASE IF EXISTS parking_lease_184;
CREATE DATABASE parking_lease_184 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE parking_lease_184;

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
('admin', '123456', '系统管理员', 'ADMIN', '停车场运营中心', '13918400001', 'admin@parkinglease184.local', 1, NOW(), NOW()),
('parking', '123456', '停车场管理员', 'PARKING', '停车场运营中心', '13918400002', 'parking@parkinglease184.local', 1, NOW(), NOW()),
('finance', '123456', '收费财务', 'FINANCE', '停车场运营中心', '13918400003', 'finance@parkinglease184.local', 1, NOW(), NOW()),
('patrol', '123456', '巡检员', 'PATROL', '停车场运营中心', '13918400004', 'patrol@parkinglease184.local', 1, NOW(), NOW()),
('tenant', '123456', '月租车主', 'TENANT', '停车场运营中心', '13918400005', 'tenant@parkinglease184.local', 1, NOW(), NOW()),
('service', '123456', '客服专员', 'SERVICE', '停车场运营中心', '13918400006', 'service@parkinglease184.local', 1, NOW(), NOW());

CREATE TABLE parking_lot (
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
INSERT INTO parking_lot (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('184-01-001', '停车场档案示例一', '区域类型', '负责人员A', '2026-05-16 09:00', 'ORDERED', '场库编号、场库名称、区域类型、负责人员、启用时间和场库状态维护', NOW(), NOW()),
('184-01-002', '停车场档案示例二', '区域类型', '负责人员B', '2026-05-17 14:00', 'COOKING', '停车场档案演示数据二', NOW(), NOW());

CREATE TABLE parking_space (
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
INSERT INTO parking_space (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('184-02-001', '车位档案示例一', '车位类型', '管理人员A', '2026-05-16 09:00', 'SCHEDULED', '车位编号、车位区域、车位类型、管理人员、启用时间和车位状态维护', NOW(), NOW()),
('184-02-002', '车位档案示例二', '车位类型', '管理人员B', '2026-05-17 14:00', 'DELIVERING', '车位档案演示数据二', NOW(), NOW());

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
('184-03-001', '月租车主示例一', '客户类型', '联系人A', '2026-05-16 09:00', 'COOKING', '车主编号、车主姓名、客户类型、联系人、建档时间和车主状态维护', NOW(), NOW()),
('184-03-002', '月租车主示例二', '客户类型', '联系人B', '2026-05-17 14:00', 'SIGNED', '月租车主演示数据二', NOW(), NOW());

CREATE TABLE lease_contract (
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
INSERT INTO lease_contract (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('184-04-001', '月租合同示例一', '合同类型', '经办人员A', '2026-05-16 09:00', 'DELIVERING', '合同编号、月租车主、合同类型、经办人员、签约时间和合同状态维护', NOW(), NOW()),
('184-04-002', '月租合同示例二', '合同类型', '经办人员B', '2026-05-17 14:00', 'ANALYZED', '月租合同演示数据二', NOW(), NOW());

CREATE TABLE renewal_reminder (
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
INSERT INTO renewal_reminder (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('184-05-001', '续费提醒示例一', '提醒类型', '客服人员A', '2026-05-16 09:00', 'SIGNED', '提醒编号、月租合同、提醒类型、客服人员、提醒时间和提醒状态维护', NOW(), NOW()),
('184-05-002', '续费提醒示例二', '提醒类型', '客服人员B', '2026-05-17 14:00', 'CLOSED', '续费提醒演示数据二', NOW(), NOW());

CREATE TABLE renewal_payment (
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
INSERT INTO renewal_payment (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('184-06-001', '续费缴费示例一', '缴费类型', '财务人员A', '2026-05-16 09:00', 'ANALYZED', '缴费编号、月租合同、缴费类型、财务人员、缴费时间和缴费状态维护', NOW(), NOW()),
('184-06-002', '续费缴费示例二', '缴费类型', '财务人员B', '2026-05-17 14:00', 'REGISTERED', '续费缴费演示数据二', NOW(), NOW());

CREATE TABLE vehicle_bind (
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
INSERT INTO vehicle_bind (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('184-07-001', '车辆绑定示例一', '绑定类型', '管理人员A', '2026-05-16 09:00', 'CLOSED', '绑定编号、车主车辆、绑定类型、管理人员、绑定时间和绑定状态维护', NOW(), NOW()),
('184-07-002', '车辆绑定示例二', '绑定类型', '管理人员B', '2026-05-17 14:00', 'ORDERED', '车辆绑定演示数据二', NOW(), NOW());

CREATE TABLE occupancy_check (
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
INSERT INTO occupancy_check (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('184-08-001', '占位巡检示例一', '巡检类型', '巡检人员A', '2026-05-16 09:00', 'REGISTERED', '巡检编号、车位区域、巡检类型、巡检人员、巡检时间和巡检状态维护', NOW(), NOW()),
('184-08-002', '占位巡检示例二', '巡检类型', '巡检人员B', '2026-05-17 14:00', 'SCHEDULED', '占位巡检演示数据二', NOW(), NOW());

CREATE TABLE abnormal_occupancy (
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
INSERT INTO abnormal_occupancy (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('184-09-001', '异常占位示例一', '异常类型', '上报人员A', '2026-05-16 09:00', 'ORDERED', '异常编号、占用车位、异常类型、上报人员、上报时间和异常状态维护', NOW(), NOW()),
('184-09-002', '异常占位示例二', '异常类型', '上报人员B', '2026-05-17 14:00', 'COOKING', '异常占位演示数据二', NOW(), NOW());

CREATE TABLE violation_handling (
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
INSERT INTO violation_handling (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('184-10-001', '违规处理示例一', '处理类型', '处理人员A', '2026-05-16 09:00', 'SCHEDULED', '处理编号、异常占位、处理类型、处理人员、处理时间和处理状态维护', NOW(), NOW()),
('184-10-002', '违规处理示例二', '处理类型', '处理人员B', '2026-05-17 14:00', 'DELIVERING', '违规处理演示数据二', NOW(), NOW());

CREATE TABLE service_ticket (
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
INSERT INTO service_ticket (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('184-11-001', '客服工单示例一', '诉求类型', '客服人员A', '2026-05-16 09:00', 'COOKING', '工单编号、月租车主、诉求类型、客服人员、提交时间和工单状态维护', NOW(), NOW()),
('184-11-002', '客服工单示例二', '诉求类型', '客服人员B', '2026-05-17 14:00', 'SIGNED', '客服工单演示数据二', NOW(), NOW());

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
('184-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'DELIVERING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('184-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'ANALYZED', '操作日志演示数据二', NOW(), NOW());
