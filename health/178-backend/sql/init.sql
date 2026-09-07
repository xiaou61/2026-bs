DROP DATABASE IF EXISTS or_sterile_pack_178;
CREATE DATABASE or_sterile_pack_178 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE or_sterile_pack_178;

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
('admin', '123456', '系统管理员', 'ADMIN', '手术室器械供应中心', '13917800001', 'admin@orsterile178.local', 1, NOW(), NOW()),
('ornurse', '123456', '手术室护士', 'ORNURSE', '手术室器械供应中心', '13917800002', 'ornurse@orsterile178.local', 1, NOW(), NOW()),
('cssd', '123456', '供应中心', 'CSSD', '手术室器械供应中心', '13917800003', 'cssd@orsterile178.local', 1, NOW(), NOW()),
('sterile', '123456', '灭菌人员', 'STERILE', '手术室器械供应中心', '13917800004', 'sterile@orsterile178.local', 1, NOW(), NOW()),
('qa', '123456', '质控审核', 'QA', '手术室器械供应中心', '13917800005', 'qa@orsterile178.local', 1, NOW(), NOW()),
('surgeon', '123456', '手术医生', 'SURGEON', '手术室器械供应中心', '13917800006', 'surgeon@orsterile178.local', 1, NOW(), NOW());

CREATE TABLE operating_room (
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
INSERT INTO operating_room (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('178-01-001', '手术室档案示例一', '手术类型', '责任护士A', '2026-05-16 09:00', 'CLEANING', '手术室编号、手术室名称、手术类型、责任护士、启用时间和手术室状态维护', NOW(), NOW()),
('178-01-002', '手术室档案示例二', '手术类型', '责任护士B', '2026-05-17 14:00', 'STERILIZING', '手术室档案演示数据二', NOW(), NOW());

CREATE TABLE instrument_pack (
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
INSERT INTO instrument_pack (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('178-02-001', '器械包档案示例一', '包类型', '供应中心A', '2026-05-16 09:00', 'STERILIZING_PENDING', '包编号、器械包名称、包类型、供应中心、建包时间和器械包状态维护', NOW(), NOW()),
('178-02-002', '器械包档案示例二', '包类型', '供应中心B', '2026-05-17 14:00', 'RELEASE_PENDING', '器械包档案演示数据二', NOW(), NOW());

CREATE TABLE instrument_item (
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
INSERT INTO instrument_item (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('178-03-001', '器械明细示例一', '器械类型', '保管人员A', '2026-05-16 09:00', 'STERILIZING', '器械编号、器械名称、器械类型、保管人员、登记时间和器械状态维护', NOW(), NOW()),
('178-03-002', '器械明细示例二', '器械类型', '保管人员B', '2026-05-17 14:00', 'RELEASED', '器械明细演示数据二', NOW(), NOW());

CREATE TABLE pack_trace (
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
INSERT INTO pack_trace (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('178-04-001', '器械包追踪示例一', '追踪类型', '责任人员A', '2026-05-16 09:00', 'RELEASE_PENDING', '追踪编号、器械包名称、追踪类型、责任人员、追踪时间和追踪状态维护', NOW(), NOW()),
('178-04-002', '器械包追踪示例二', '追踪类型', '责任人员B', '2026-05-17 14:00', 'RECALLED', '器械包追踪演示数据二', NOW(), NOW());

CREATE TABLE sterilization_batch (
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
INSERT INTO sterilization_batch (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('178-05-001', '灭菌批次示例一', '灭菌类型', '灭菌人员A', '2026-05-16 09:00', 'RELEASED', '批次编号、灭菌锅次、灭菌类型、灭菌人员、开始时间和批次状态维护', NOW(), NOW()),
('178-05-002', '灭菌批次示例二', '灭菌类型', '灭菌人员B', '2026-05-17 14:00', 'FINISHED', '灭菌批次演示数据二', NOW(), NOW());

CREATE TABLE sterilization_record (
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
INSERT INTO sterilization_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('178-06-001', '灭菌记录示例一', '灭菌类型', '灭菌人员A', '2026-05-16 09:00', 'RECALLED', '记录编号、灭菌批次、灭菌类型、灭菌人员、灭菌时间和记录状态维护', NOW(), NOW()),
('178-06-002', '灭菌记录示例二', '灭菌类型', '灭菌人员B', '2026-05-17 14:00', 'PENDING', '灭菌记录演示数据二', NOW(), NOW());

CREATE TABLE release_approval (
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
INSERT INTO release_approval (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('178-07-001', '放行审核示例一', '审核类型', '质控人员A', '2026-05-16 09:00', 'FINISHED', '审核编号、器械包名称、审核类型、质控人员、审核时间和放行状态维护', NOW(), NOW()),
('178-07-002', '放行审核示例二', '审核类型', '质控人员B', '2026-05-17 14:00', 'CLEANING', '放行审核演示数据二', NOW(), NOW());

CREATE TABLE surgery_use (
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
INSERT INTO surgery_use (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('178-08-001', '手术使用示例一', '使用类型', '手术医生A', '2026-05-16 09:00', 'PENDING', '使用编号、手术名称、使用类型、手术医生、使用时间和使用状态维护', NOW(), NOW()),
('178-08-002', '手术使用示例二', '使用类型', '手术医生B', '2026-05-17 14:00', 'STERILIZING_PENDING', '手术使用演示数据二', NOW(), NOW());

CREATE TABLE return_checkin (
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
INSERT INTO return_checkin (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('178-09-001', '回收清点示例一', '清点类型', '回收人员A', '2026-05-16 09:00', 'CLEANING', '清点编号、器械包名称、清点类型、回收人员、回收时间和清点状态维护', NOW(), NOW()),
('178-09-002', '回收清点示例二', '清点类型', '回收人员B', '2026-05-17 14:00', 'STERILIZING', '回收清点演示数据二', NOW(), NOW());

CREATE TABLE defect_report (
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
INSERT INTO defect_report (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('178-10-001', '缺损上报示例一', '缺损类型', '上报人员A', '2026-05-16 09:00', 'STERILIZING_PENDING', '上报编号、缺损器械、缺损类型、上报人员、上报时间和缺损状态维护', NOW(), NOW()),
('178-10-002', '缺损上报示例二', '缺损类型', '上报人员B', '2026-05-17 14:00', 'RELEASE_PENDING', '缺损上报演示数据二', NOW(), NOW());

CREATE TABLE recall_event (
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
INSERT INTO recall_event (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('178-11-001', '异常召回示例一', '召回类型', '质控人员A', '2026-05-16 09:00', 'STERILIZING', '召回编号、召回器械包、召回类型、质控人员、召回时间和召回状态维护', NOW(), NOW()),
('178-11-002', '异常召回示例二', '召回类型', '质控人员B', '2026-05-17 14:00', 'RELEASED', '异常召回演示数据二', NOW(), NOW());

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
('178-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'RELEASE_PENDING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('178-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'RECALLED', '操作日志演示数据二', NOW(), NOW());
