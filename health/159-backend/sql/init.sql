DROP DATABASE IF EXISTS medical_waste_159;
CREATE DATABASE medical_waste_159 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE medical_waste_159;

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
('admin', '123456', '系统管理员', 'ADMIN', '医废监管中心', '13915900001', 'admin@waste159.local', 1, NOW(), NOW()),
('hospital', '123456', '医院交接员', 'HOSPITAL', '医废监管中心', '13915900002', 'hospital@waste159.local', 1, NOW(), NOW()),
('collector', '123456', '收运调度员', 'COLLECTOR', '医废监管中心', '13915900003', 'collector@waste159.local', 1, NOW(), NOW()),
('transporter', '123456', '转运司机', 'TRANSPORTER', '医废监管中心', '13915900004', 'transporter@waste159.local', 1, NOW(), NOW()),
('disposal', '123456', '处置厂审核员', 'DISPOSAL', '医废监管中心', '13915900005', 'disposal@waste159.local', 1, NOW(), NOW()),
('regulator', '123456', '监管人员', 'REGULATOR', '医废监管中心', '13915900006', 'regulator@waste159.local', 1, NOW(), NOW());

CREATE TABLE waste_source (
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
INSERT INTO waste_source (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('159-01-001', '医废来源示例一', '来源类型', '责任人A', '2026-05-16 09:00', 'BOOKED', '来源编号、来源名称、来源类型、责任人、启用时间和来源状态维护', NOW(), NOW()),
('159-01-002', '医废来源示例二', '来源类型', '责任人B', '2026-05-17 14:00', 'VERIFIED', '医废来源演示数据二', NOW(), NOW());

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
('159-02-001', '废物类别示例一', '危险类型', '管理人A', '2026-05-16 09:00', 'SCHEDULED', '类别编号、类别名称、危险类型、管理人、启用时间和类别状态维护', NOW(), NOW()),
('159-02-002', '废物类别示例二', '危险类型', '管理人B', '2026-05-17 14:00', 'PROCESSING', '废物类别演示数据二', NOW(), NOW());

CREATE TABLE waste_package (
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
INSERT INTO waste_package (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('159-03-001', '包装赋码示例一', '包装类型', '交接人A', '2026-05-16 09:00', 'VERIFIED', '包装编号、废物名称、包装类型、交接人、封包时间和包装状态维护', NOW(), NOW()),
('159-03-002', '包装赋码示例二', '包装类型', '交接人B', '2026-05-17 14:00', 'FINISHED', '包装赋码演示数据二', NOW(), NOW());

CREATE TABLE collection_order (
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
INSERT INTO collection_order (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('159-04-001', '收集预约示例一', '收集类型', '联系人A', '2026-05-16 09:00', 'PROCESSING', '预约编号、预约机构、收集类型、联系人、预约时间和预约状态维护', NOW(), NOW()),
('159-04-002', '收集预约示例二', '收集类型', '联系人B', '2026-05-17 14:00', 'WARNING', '收集预约演示数据二', NOW(), NOW());

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
('159-05-001', '称重记录示例一', '称重类型', '称重人A', '2026-05-16 09:00', 'FINISHED', '称重编号、医废批次、称重类型、称重人、称重时间和称重状态维护', NOW(), NOW()),
('159-05-002', '称重记录示例二', '称重类型', '称重人B', '2026-05-17 14:00', 'PUBLISHED', '称重记录演示数据二', NOW(), NOW());

CREATE TABLE storage_handover (
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
INSERT INTO storage_handover (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('159-06-001', '暂存交接示例一', '交接类型', '交接人A', '2026-05-16 09:00', 'WARNING', '交接编号、暂存点位、交接类型、交接人、交接时间和交接状态维护', NOW(), NOW()),
('159-06-002', '暂存交接示例二', '交接类型', '交接人B', '2026-05-17 14:00', 'ACTIVE', '暂存交接演示数据二', NOW(), NOW());

CREATE TABLE transfer_manifest (
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
INSERT INTO transfer_manifest (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('159-07-001', '转运联单示例一', '转运类型', '承运人A', '2026-05-16 09:00', 'PUBLISHED', '联单编号、医废批次、转运类型、承运人、转运时间和联单状态维护', NOW(), NOW()),
('159-07-002', '转运联单示例二', '转运类型', '承运人B', '2026-05-17 14:00', 'BOOKED', '转运联单演示数据二', NOW(), NOW());

CREATE TABLE transport_track (
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
INSERT INTO transport_track (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('159-08-001', '运输轨迹示例一', '轨迹类型', '驾驶员A', '2026-05-16 09:00', 'ACTIVE', '轨迹编号、转运车辆、轨迹类型、驾驶员、定位时间和轨迹状态维护', NOW(), NOW()),
('159-08-002', '运输轨迹示例二', '轨迹类型', '驾驶员B', '2026-05-17 14:00', 'SCHEDULED', '运输轨迹演示数据二', NOW(), NOW());

CREATE TABLE disposal_confirm (
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
INSERT INTO disposal_confirm (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('159-09-001', '处置确认示例一', '处置方式', '确认人A', '2026-05-16 09:00', 'BOOKED', '处置编号、处置批次、处置方式、确认人、处置时间和处置状态维护', NOW(), NOW()),
('159-09-002', '处置确认示例二', '处置方式', '确认人B', '2026-05-17 14:00', 'VERIFIED', '处置确认演示数据二', NOW(), NOW());

CREATE TABLE exception_trace (
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
INSERT INTO exception_trace (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('159-10-001', '异常追溯示例一', '异常类型', '处置人A', '2026-05-16 09:00', 'SCHEDULED', '异常编号、异常对象、异常类型、处置人、发现时间和异常状态维护', NOW(), NOW()),
('159-10-002', '异常追溯示例二', '异常类型', '处置人B', '2026-05-17 14:00', 'PROCESSING', '异常追溯演示数据二', NOW(), NOW());

CREATE TABLE compliance_audit (
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
INSERT INTO compliance_audit (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('159-11-001', '监管抽查示例一', '抽查类型', '监管人A', '2026-05-16 09:00', 'VERIFIED', '抽查编号、抽查对象、抽查类型、监管人、抽查时间和抽查状态维护', NOW(), NOW()),
('159-11-002', '监管抽查示例二', '抽查类型', '监管人B', '2026-05-17 14:00', 'FINISHED', '监管抽查演示数据二', NOW(), NOW());

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
('159-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'PROCESSING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('159-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'WARNING', '操作日志演示数据二', NOW(), NOW());
