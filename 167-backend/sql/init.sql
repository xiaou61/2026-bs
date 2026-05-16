DROP DATABASE IF EXISTS waste_sorting_167;
CREATE DATABASE waste_sorting_167 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE waste_sorting_167;

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
('admin', '123456', '系统管理员', 'ADMIN', '社区垃圾分类服务站', '13916700001', 'admin@waste167.local', 1, NOW(), NOW()),
('community', '123456', '社区管理员', 'COMMUNITY', '社区垃圾分类服务站', '13916700002', 'community@waste167.local', 1, NOW(), NOW()),
('supervisor', '123456', '分类督导员', 'SUPERVISOR', '社区垃圾分类服务站', '13916700003', 'supervisor@waste167.local', 1, NOW(), NOW()),
('resident', '123456', '居民用户', 'RESIDENT', '社区垃圾分类服务站', '13916700004', 'resident@waste167.local', 1, NOW(), NOW()),
('volunteer', '123456', '志愿协管员', 'VOLUNTEER', '社区垃圾分类服务站', '13916700005', 'volunteer@waste167.local', 1, NOW(), NOW()),
('exchange', '123456', '积分兑换员', 'EXCHANGE', '社区垃圾分类服务站', '13916700006', 'exchange@waste167.local', 1, NOW(), NOW());

CREATE TABLE community_area (
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
INSERT INTO community_area (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('167-01-001', '社区区域示例一', '覆盖楼栋', '区域负责人A', '2026-05-16 09:00', 'BOOKED', '区域编号、区域名称、覆盖楼栋、区域负责人、启用时间和区域状态维护', NOW(), NOW()),
('167-01-002', '社区区域示例二', '覆盖楼栋', '区域负责人B', '2026-05-17 14:00', 'VERIFIED', '社区区域演示数据二', NOW(), NOW());

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
('167-02-001', '楼栋档案示例一', '所属网格', '楼栋管家A', '2026-05-16 09:00', 'SCHEDULED', '楼栋编号、楼栋名称、所属网格、楼栋管家、建档时间和楼栋状态维护', NOW(), NOW()),
('167-02-002', '楼栋档案示例二', '所属网格', '楼栋管家B', '2026-05-17 14:00', 'PROCESSING', '楼栋档案演示数据二', NOW(), NOW());

CREATE TABLE resident_profile (
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
INSERT INTO resident_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('167-03-001', '居民档案示例一', '所属楼栋', '联系方式A', '2026-05-16 09:00', 'VERIFIED', '居民编号、居民姓名、所属楼栋、联系方式、登记时间和居民状态维护', NOW(), NOW()),
('167-03-002', '居民档案示例二', '所属楼栋', '联系方式B', '2026-05-17 14:00', 'FINISHED', '居民档案演示数据二', NOW(), NOW());

CREATE TABLE sorting_category (
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
INSERT INTO sorting_category (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('167-04-001', '分类规则示例一', '投放要求', '责任人员A', '2026-05-16 09:00', 'PROCESSING', '规则编号、垃圾类别、投放要求、责任人员、生效时间和规则状态维护', NOW(), NOW()),
('167-04-002', '分类规则示例二', '投放要求', '责任人员B', '2026-05-17 14:00', 'WARNING', '分类规则演示数据二', NOW(), NOW());

CREATE TABLE sorting_checkin (
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
INSERT INTO sorting_checkin (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('167-05-001', '分类打卡示例一', '垃圾类别', '督导人员A', '2026-05-16 09:00', 'FINISHED', '打卡编号、打卡居民、垃圾类别、督导人员、打卡时间和打卡状态维护', NOW(), NOW()),
('167-05-002', '分类打卡示例二', '垃圾类别', '督导人员B', '2026-05-17 14:00', 'PUBLISHED', '分类打卡演示数据二', NOW(), NOW());

CREATE TABLE supervision_record (
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
INSERT INTO supervision_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('167-06-001', '督导记录示例一', '督导类型', '督导人员A', '2026-05-16 09:00', 'WARNING', '督导编号、督导楼栋、督导类型、督导人员、督导时间和督导状态维护', NOW(), NOW()),
('167-06-002', '督导记录示例二', '督导类型', '督导人员B', '2026-05-17 14:00', 'ACTIVE', '督导记录演示数据二', NOW(), NOW());

CREATE TABLE correction_task (
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
INSERT INTO correction_task (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('167-07-001', '整改任务示例一', '问题类型', '责任人员A', '2026-05-16 09:00', 'PUBLISHED', '整改编号、整改对象、问题类型、责任人员、整改时间和整改状态维护', NOW(), NOW()),
('167-07-002', '整改任务示例二', '问题类型', '责任人员B', '2026-05-17 14:00', 'BOOKED', '整改任务演示数据二', NOW(), NOW());

CREATE TABLE points_ledger (
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
INSERT INTO points_ledger (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('167-08-001', '积分记录示例一', '积分来源', '记录人员A', '2026-05-16 09:00', 'ACTIVE', '积分编号、积分居民、积分来源、记录人员、积分时间和积分状态维护', NOW(), NOW()),
('167-08-002', '积分记录示例二', '积分来源', '记录人员B', '2026-05-17 14:00', 'SCHEDULED', '积分记录演示数据二', NOW(), NOW());

CREATE TABLE reward_catalog (
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
INSERT INTO reward_catalog (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('167-09-001', '兑换商品示例一', '商品类型', '兑换负责人A', '2026-05-16 09:00', 'BOOKED', '商品编号、商品名称、商品类型、兑换负责人、上架时间和商品状态维护', NOW(), NOW()),
('167-09-002', '兑换商品示例二', '商品类型', '兑换负责人B', '2026-05-17 14:00', 'VERIFIED', '兑换商品演示数据二', NOW(), NOW());

CREATE TABLE exchange_order (
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
INSERT INTO exchange_order (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('167-10-001', '积分兑换示例一', '兑换类型', '兑换人员A', '2026-05-16 09:00', 'SCHEDULED', '兑换编号、兑换居民、兑换类型、兑换人员、兑换时间和兑换状态维护', NOW(), NOW()),
('167-10-002', '积分兑换示例二', '兑换类型', '兑换人员B', '2026-05-17 14:00', 'PROCESSING', '积分兑换演示数据二', NOW(), NOW());

CREATE TABLE building_ranking (
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
INSERT INTO building_ranking (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('167-11-001', '楼栋排名示例一', '排名周期', '统计人员A', '2026-05-16 09:00', 'VERIFIED', '排名编号、排名楼栋、排名周期、统计人员、统计时间和排名状态维护', NOW(), NOW()),
('167-11-002', '楼栋排名示例二', '排名周期', '统计人员B', '2026-05-17 14:00', 'FINISHED', '楼栋排名演示数据二', NOW(), NOW());

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
('167-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'PROCESSING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('167-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'WARNING', '操作日志演示数据二', NOW(), NOW());
