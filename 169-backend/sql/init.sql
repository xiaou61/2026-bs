DROP DATABASE IF EXISTS school_bus_169;
CREATE DATABASE school_bus_169 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE school_bus_169;

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
('admin', '123456', '系统管理员', 'ADMIN', '校车安全管理中心', '13916900001', 'admin@bus169.local', 1, NOW(), NOW()),
('school', '123456', '校方管理员', 'SCHOOL', '校车安全管理中心', '13916900002', 'school@bus169.local', 1, NOW(), NOW()),
('dispatch', '123456', '调度管理员', 'DISPATCH', '校车安全管理中心', '13916900003', 'dispatch@bus169.local', 1, NOW(), NOW()),
('driver', '123456', '校车司机', 'DRIVER', '校车安全管理中心', '13916900004', 'driver@bus169.local', 1, NOW(), NOW()),
('teacher', '123456', '跟车老师', 'TEACHER', '校车安全管理中心', '13916900005', 'teacher@bus169.local', 1, NOW(), NOW()),
('guardian', '123456', '学生家长', 'GUARDIAN', '校车安全管理中心', '13916900006', 'guardian@bus169.local', 1, NOW(), NOW());

CREATE TABLE bus_route (
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
INSERT INTO bus_route (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('169-01-001', '校车线路示例一', '线路类型', '负责人员A', '2026-05-16 09:00', 'BOOKED', '线路编号、线路名称、线路类型、负责人员、启用时间和线路状态维护', NOW(), NOW()),
('169-01-002', '校车线路示例二', '线路类型', '负责人员B', '2026-05-17 14:00', 'VERIFIED', '校车线路演示数据二', NOW(), NOW());

CREATE TABLE bus_stop (
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
INSERT INTO bus_stop (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('169-02-001', '站点档案示例一', '站点类型', '负责人员A', '2026-05-16 09:00', 'SCHEDULED', '站点编号、站点名称、站点类型、负责人员、启用时间和站点状态维护', NOW(), NOW()),
('169-02-002', '站点档案示例二', '站点类型', '负责人员B', '2026-05-17 14:00', 'PROCESSING', '站点档案演示数据二', NOW(), NOW());

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
('169-03-001', '车辆档案示例一', '车辆类型', '管理人员A', '2026-05-16 09:00', 'VERIFIED', '车辆编号、车牌号码、车辆类型、管理人员、投用时间和车辆状态维护', NOW(), NOW()),
('169-03-002', '车辆档案示例二', '车辆类型', '管理人员B', '2026-05-17 14:00', 'FINISHED', '车辆档案演示数据二', NOW(), NOW());

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
('169-04-001', '司机档案示例一', '准驾类型', '管理人员A', '2026-05-16 09:00', 'PROCESSING', '司机编号、司机姓名、准驾类型、管理人员、入职时间和司机状态维护', NOW(), NOW()),
('169-04-002', '司机档案示例二', '准驾类型', '管理人员B', '2026-05-17 14:00', 'WARNING', '司机档案演示数据二', NOW(), NOW());

CREATE TABLE student_profile (
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
INSERT INTO student_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('169-05-001', '学生档案示例一', '年级班级', '班主任A', '2026-05-16 09:00', 'FINISHED', '学生编号、学生姓名、年级班级、班主任、建档时间和学生状态维护', NOW(), NOW()),
('169-05-002', '学生档案示例二', '年级班级', '班主任B', '2026-05-17 14:00', 'PUBLISHED', '学生档案演示数据二', NOW(), NOW());

CREATE TABLE guardian_profile (
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
INSERT INTO guardian_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('169-06-001', '家长档案示例一', '关联学生', '联系人员A', '2026-05-16 09:00', 'WARNING', '家长编号、家长姓名、关联学生、联系人员、建档时间和家长状态维护', NOW(), NOW()),
('169-06-002', '家长档案示例二', '关联学生', '联系人员B', '2026-05-17 14:00', 'ACTIVE', '家长档案演示数据二', NOW(), NOW());

CREATE TABLE ride_reservation (
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
INSERT INTO ride_reservation (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('169-07-001', '乘车预约示例一', '预约类型', '预约学生A', '2026-05-16 09:00', 'PUBLISHED', '预约编号、预约线路、预约类型、预约学生、预约时间和预约状态维护', NOW(), NOW()),
('169-07-002', '乘车预约示例二', '预约类型', '预约学生B', '2026-05-17 14:00', 'BOOKED', '乘车预约演示数据二', NOW(), NOW());

CREATE TABLE boarding_checkin (
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
INSERT INTO boarding_checkin (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('169-08-001', '上车核验示例一', '核验类型', '核验人员A', '2026-05-16 09:00', 'ACTIVE', '上车编号、核验线路、核验类型、核验人员、上车时间和上车状态维护', NOW(), NOW()),
('169-08-002', '上车核验示例二', '核验类型', '核验人员B', '2026-05-17 14:00', 'SCHEDULED', '上车核验演示数据二', NOW(), NOW());

CREATE TABLE dropoff_checkin (
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
INSERT INTO dropoff_checkin (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('169-09-001', '下车核验示例一', '核验类型', '核验人员A', '2026-05-16 09:00', 'BOOKED', '下车编号、核验线路、核验类型、核验人员、下车时间和下车状态维护', NOW(), NOW()),
('169-09-002', '下车核验示例二', '核验类型', '核验人员B', '2026-05-17 14:00', 'VERIFIED', '下车核验演示数据二', NOW(), NOW());

CREATE TABLE exception_alert (
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
INSERT INTO exception_alert (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('169-10-001', '异常告警示例一', '告警类型', '处置人员A', '2026-05-16 09:00', 'SCHEDULED', '告警编号、告警学生、告警类型、处置人员、告警时间和告警状态维护', NOW(), NOW()),
('169-10-002', '异常告警示例二', '告警类型', '处置人员B', '2026-05-17 14:00', 'PROCESSING', '异常告警演示数据二', NOW(), NOW());

CREATE TABLE parent_notice (
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
INSERT INTO parent_notice (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('169-11-001', '家长通知示例一', '通知类型', '发送人员A', '2026-05-16 09:00', 'VERIFIED', '通知编号、通知对象、通知类型、发送人员、发送时间和通知状态维护', NOW(), NOW()),
('169-11-002', '家长通知示例二', '通知类型', '发送人员B', '2026-05-17 14:00', 'FINISHED', '家长通知演示数据二', NOW(), NOW());

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
('169-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'PROCESSING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('169-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'WARNING', '操作日志演示数据二', NOW(), NOW());
