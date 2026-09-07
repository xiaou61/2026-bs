DROP DATABASE IF EXISTS pharmacy_care_196;
CREATE DATABASE pharmacy_care_196 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE pharmacy_care_196;

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
('admin', '123456', '系统管理员', 'ADMIN', '连锁药店慢病服务部', '13919600001', 'admin@pharmacycare196.local', 1, NOW(), NOW()),
('pharmacy', '123456', '药店管理员', 'PHARMACY', '连锁药店慢病服务部', '13919600002', 'pharmacy@pharmacycare196.local', 1, NOW(), NOW()),
('pharmacist', '123456', '药师审核', 'PHARMACIST', '连锁药店慢病服务部', '13919600003', 'pharmacist@pharmacycare196.local', 1, NOW(), NOW()),
('clerk', '123456', '门店店员', 'CLERK', '连锁药店慢病服务部', '13919600004', 'clerk@pharmacycare196.local', 1, NOW(), NOW()),
('followup', '123456', '随访专员', 'FOLLOWUP', '连锁药店慢病服务部', '13919600005', 'followup@pharmacycare196.local', 1, NOW(), NOW()),
('customer', '123456', '慢病顾客', 'CUSTOMER', '连锁药店慢病服务部', '13919600006', 'customer@pharmacycare196.local', 1, NOW(), NOW());

CREATE TABLE pharmacy_store (
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
INSERT INTO pharmacy_store (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('196-01-001', '药店门店示例一', '门店类型', '负责人A', '2026-05-16 09:00', 'REVIEWING', '门店编号、门店名称、门店类型、负责人、建档时间和门店状态维护', NOW(), NOW()),
('196-01-002', '药店门店示例二', '门店类型', '负责人B', '2026-05-17 14:00', 'PURCHASED', '药店门店演示数据二', NOW(), NOW());

CREATE TABLE customer_profile (
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
INSERT INTO customer_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('196-02-001', '顾客档案示例一', '慢病类型', '联系人员A', '2026-05-16 09:00', 'RISK_CHECKING', '档案编号、顾客姓名、慢病类型、联系人员、建档时间和档案状态维护', NOW(), NOW()),
('196-02-002', '顾客档案示例二', '慢病类型', '联系人员B', '2026-05-17 14:00', 'GUIDED', '顾客档案演示数据二', NOW(), NOW());

CREATE TABLE medicine_catalog (
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
INSERT INTO medicine_catalog (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('196-03-001', '药品目录示例一', '药品类别', '管理人员A', '2026-05-16 09:00', 'PURCHASED', '药品编号、药品名称、药品类别、管理人员、入库时间和药品状态维护', NOW(), NOW()),
('196-03-002', '药品目录示例二', '药品类别', '管理人员B', '2026-05-17 14:00', 'REMINDING', '药品目录演示数据二', NOW(), NOW());

CREATE TABLE prescription_record (
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
INSERT INTO prescription_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('196-04-001', '处方登记示例一', '处方类型', '登记人员A', '2026-05-16 09:00', 'GUIDED', '处方编号、顾客姓名、处方类型、登记人员、登记时间和处方状态维护', NOW(), NOW()),
('196-04-002', '处方登记示例二', '处方类型', '登记人员B', '2026-05-17 14:00', 'FOLLOWED', '处方登记演示数据二', NOW(), NOW());

CREATE TABLE prescription_review (
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
INSERT INTO prescription_review (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('196-05-001', '处方审核示例一', '审核类型', '审核药师A', '2026-05-16 09:00', 'REMINDING', '审核编号、处方记录、审核类型、审核药师、审核时间和审核状态维护', NOW(), NOW()),
('196-05-002', '处方审核示例二', '审核类型', '审核药师B', '2026-05-17 14:00', 'CLOSED', '处方审核演示数据二', NOW(), NOW());

CREATE TABLE risk_check (
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
INSERT INTO risk_check (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('196-06-001', '风险复核示例一', '风险类型', '复核人员A', '2026-05-16 09:00', 'FOLLOWED', '复核编号、处方记录、风险类型、复核人员、复核时间和复核状态维护', NOW(), NOW()),
('196-06-002', '风险复核示例二', '风险类型', '复核人员B', '2026-05-17 14:00', 'REGISTERED', '风险复核演示数据二', NOW(), NOW());

CREATE TABLE purchase_record (
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
INSERT INTO purchase_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('196-07-001', '购药记录示例一', '药品名称', '经办人员A', '2026-05-16 09:00', 'CLOSED', '购药编号、顾客姓名、药品名称、经办人员、购药时间和购药状态维护', NOW(), NOW()),
('196-07-002', '购药记录示例二', '药品名称', '经办人员B', '2026-05-17 14:00', 'REVIEWING', '购药记录演示数据二', NOW(), NOW());

CREATE TABLE medication_guide (
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
INSERT INTO medication_guide (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('196-08-001', '用药指导示例一', '指导类型', '指导药师A', '2026-05-16 09:00', 'REGISTERED', '指导编号、购药记录、指导类型、指导药师、指导时间和指导状态维护', NOW(), NOW()),
('196-08-002', '用药指导示例二', '指导类型', '指导药师B', '2026-05-17 14:00', 'RISK_CHECKING', '用药指导演示数据二', NOW(), NOW());

CREATE TABLE chronic_plan (
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
INSERT INTO chronic_plan (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('196-09-001', '慢病方案示例一', '慢病类型', '管理人员A', '2026-05-16 09:00', 'REVIEWING', '方案编号、顾客姓名、慢病类型、管理人员、方案时间和方案状态维护', NOW(), NOW()),
('196-09-002', '慢病方案示例二', '慢病类型', '管理人员B', '2026-05-17 14:00', 'PURCHASED', '慢病方案演示数据二', NOW(), NOW());

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
('196-10-001', '续方提醒示例一', '提醒类型', '随访人员A', '2026-05-16 09:00', 'RISK_CHECKING', '提醒编号、慢病方案、提醒类型、随访人员、提醒时间和提醒状态维护', NOW(), NOW()),
('196-10-002', '续方提醒示例二', '提醒类型', '随访人员B', '2026-05-17 14:00', 'GUIDED', '续方提醒演示数据二', NOW(), NOW());

CREATE TABLE followup_record (
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
INSERT INTO followup_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('196-11-001', '随访记录示例一', '随访类型', '随访人员A', '2026-05-16 09:00', 'PURCHASED', '随访编号、慢病顾客、随访类型、随访人员、随访时间和随访状态维护', NOW(), NOW()),
('196-11-002', '随访记录示例二', '随访类型', '随访人员B', '2026-05-17 14:00', 'REMINDING', '随访记录演示数据二', NOW(), NOW());

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
('196-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'GUIDED', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('196-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'FOLLOWED', '操作日志演示数据二', NOW(), NOW());
