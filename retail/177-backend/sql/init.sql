DROP DATABASE IF EXISTS live_base_177;
CREATE DATABASE live_base_177 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE live_base_177;

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
('admin', '$2a$12$m41tWJee9w5MEhyHM9HJOO7uCUFMbh4g6hK/ZT.JV8A3yZjMaF5QO', '系统管理员', 'ADMIN', '直播基地运营中心', '13917700001', 'admin@livebase177.local', 1, NOW(), NOW()),
('base', '$2a$12$m41tWJee9w5MEhyHM9HJOO7uCUFMbh4g6hK/ZT.JV8A3yZjMaF5QO', '基地运营', 'BASE', '直播基地运营中心', '13917700002', 'base@livebase177.local', 1, NOW(), NOW()),
('anchor', '$2a$12$m41tWJee9w5MEhyHM9HJOO7uCUFMbh4g6hK/ZT.JV8A3yZjMaF5QO', '主播', 'ANCHOR', '直播基地运营中心', '13917700003', 'anchor@livebase177.local', 1, NOW(), NOW()),
('selector', '$2a$12$m41tWJee9w5MEhyHM9HJOO7uCUFMbh4g6hK/ZT.JV8A3yZjMaF5QO', '选品专员', 'SELECTOR', '直播基地运营中心', '13917700004', 'selector@livebase177.local', 1, NOW(), NOW()),
('sample', '$2a$12$m41tWJee9w5MEhyHM9HJOO7uCUFMbh4g6hK/ZT.JV8A3yZjMaF5QO', '样品管理员', 'SAMPLE', '直播基地运营中心', '13917700005', 'sample@livebase177.local', 1, NOW(), NOW()),
('merchant', '$2a$12$m41tWJee9w5MEhyHM9HJOO7uCUFMbh4g6hK/ZT.JV8A3yZjMaF5QO', '商家代表', 'MERCHANT', '直播基地运营中心', '13917700006', 'merchant@livebase177.local', 1, NOW(), NOW());

CREATE TABLE live_studio (
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
INSERT INTO live_studio (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('177-01-001', '直播间档案示例一', '直播类型', '运营人员A', '2026-05-16 09:00', 'SCHEDULED', '直播间编号、直播间名称、直播类型、运营人员、启用时间和直播间状态维护', NOW(), NOW()),
('177-01-002', '直播间档案示例二', '直播类型', '运营人员B', '2026-05-17 14:00', 'SAMPLE_FLOW', '直播间档案演示数据二', NOW(), NOW());

CREATE TABLE anchor_profile (
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
INSERT INTO anchor_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('177-02-001', '主播档案示例一', '主播类型', '运营人员A', '2026-05-16 09:00', 'SELECTING', '主播编号、主播姓名、主播类型、运营人员、签约时间和主播状态维护', NOW(), NOW()),
('177-02-002', '主播档案示例二', '主播类型', '运营人员B', '2026-05-17 14:00', 'LIVE', '主播档案演示数据二', NOW(), NOW());

CREATE TABLE merchant_profile (
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
INSERT INTO merchant_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('177-03-001', '商家档案示例一', '经营类型', '对接人员A', '2026-05-16 09:00', 'SAMPLE_FLOW', '商家编号、商家名称、经营类型、对接人员、入驻时间和商家状态维护', NOW(), NOW()),
('177-03-002', '商家档案示例二', '经营类型', '对接人员B', '2026-05-17 14:00', 'REPLAY_PENDING', '商家档案演示数据二', NOW(), NOW());

CREATE TABLE product_catalog (
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
INSERT INTO product_catalog (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('177-04-001', '商品选品示例一', '商品类型', '提报商家A', '2026-05-16 09:00', 'LIVE', '商品编号、商品名称、商品类型、提报商家、提报时间和选品状态维护', NOW(), NOW()),
('177-04-002', '商品选品示例二', '商品类型', '提报商家B', '2026-05-17 14:00', 'REPLAYED', '商品选品演示数据二', NOW(), NOW());

CREATE TABLE sample_ledger (
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
INSERT INTO sample_ledger (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('177-05-001', '样品台账示例一', '样品类型', '仓储人员A', '2026-05-16 09:00', 'REPLAY_PENDING', '样品编号、样品名称、样品类型、仓储人员、入库时间和样品状态维护', NOW(), NOW()),
('177-05-002', '样品台账示例二', '样品类型', '仓储人员B', '2026-05-17 14:00', 'FINISHED', '样品台账演示数据二', NOW(), NOW());

CREATE TABLE sample_loan (
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
INSERT INTO sample_loan (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('177-06-001', '样品借还示例一', '借还类型', '借用主播A', '2026-05-16 09:00', 'REPLAYED', '借还编号、借还样品、借还类型、借用主播、借用时间和借还状态维护', NOW(), NOW()),
('177-06-002', '样品借还示例二', '借还类型', '借用主播B', '2026-05-17 14:00', 'PENDING', '样品借还演示数据二', NOW(), NOW());

CREATE TABLE anchor_schedule (
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
INSERT INTO anchor_schedule (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('177-07-001', '主播排班示例一', '排班类型', '运营人员A', '2026-05-16 09:00', 'FINISHED', '排班编号、排班主播、排班类型、运营人员、排班时间和排班状态维护', NOW(), NOW()),
('177-07-002', '主播排班示例二', '排班类型', '运营人员B', '2026-05-17 14:00', 'SCHEDULED', '主播排班演示数据二', NOW(), NOW());

CREATE TABLE selection_review (
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
INSERT INTO selection_review (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('177-08-001', '选品评测示例一', '评测类型', '选品人员A', '2026-05-16 09:00', 'PENDING', '评测编号、评测商品、评测类型、选品人员、评测时间和评测状态维护', NOW(), NOW()),
('177-08-002', '选品评测示例二', '评测类型', '选品人员B', '2026-05-17 14:00', 'SELECTING', '选品评测演示数据二', NOW(), NOW());

CREATE TABLE live_plan (
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
INSERT INTO live_plan (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('177-09-001', '直播计划示例一', '直播类型', '负责人A', '2026-05-16 09:00', 'SCHEDULED', '计划编号、计划主题、直播类型、负责人、计划时间和计划状态维护', NOW(), NOW()),
('177-09-002', '直播计划示例二', '直播类型', '负责人B', '2026-05-17 14:00', 'SAMPLE_FLOW', '直播计划演示数据二', NOW(), NOW());

CREATE TABLE live_session (
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
INSERT INTO live_session (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('177-10-001', '直播场次示例一', '场次类型', '主播人员A', '2026-05-16 09:00', 'SELECTING', '场次编号、直播主题、场次类型、主播人员、开播时间和场次状态维护', NOW(), NOW()),
('177-10-002', '直播场次示例二', '场次类型', '主播人员B', '2026-05-17 14:00', 'LIVE', '直播场次演示数据二', NOW(), NOW());

CREATE TABLE live_replay (
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
INSERT INTO live_replay (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('177-11-001', '直播复盘示例一', '复盘类型', '复盘人员A', '2026-05-16 09:00', 'SAMPLE_FLOW', '复盘编号、直播场次、复盘类型、复盘人员、复盘时间和复盘状态维护', NOW(), NOW()),
('177-11-002', '直播复盘示例二', '复盘类型', '复盘人员B', '2026-05-17 14:00', 'REPLAY_PENDING', '直播复盘演示数据二', NOW(), NOW());

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
('177-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'LIVE', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('177-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'REPLAYED', '操作日志演示数据二', NOW(), NOW());
