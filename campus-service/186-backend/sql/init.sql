DROP DATABASE IF EXISTS campus_canteen_186;
CREATE DATABASE campus_canteen_186 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE campus_canteen_186;

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
('admin', '123456', '系统管理员', 'ADMIN', '校园餐厅后厨留样中心', '13918600001', 'admin@canteenhygiene186.local', 1, NOW(), NOW()),
('canteen', '123456', '食堂管理员', 'CANTEEN', '校园餐厅后厨留样中心', '13918600002', 'canteen@canteenhygiene186.local', 1, NOW(), NOW()),
('chef', '123456', '后厨负责人', 'CHEF', '校园餐厅后厨留样中心', '13918600003', 'chef@canteenhygiene186.local', 1, NOW(), NOW()),
('inspector', '123456', '卫生巡检员', 'INSPECTOR', '校园餐厅后厨留样中心', '13918600004', 'inspector@canteenhygiene186.local', 1, NOW(), NOW()),
('warehouse', '123456', '仓储管理员', 'WAREHOUSE', '校园餐厅后厨留样中心', '13918600005', 'warehouse@canteenhygiene186.local', 1, NOW(), NOW()),
('school', '123456', '校方监管', 'SCHOOL', '校园餐厅后厨留样中心', '13918600006', 'school@canteenhygiene186.local', 1, NOW(), NOW());

CREATE TABLE canteen_info (
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
INSERT INTO canteen_info (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('186-01-001', '餐厅档案示例一', '经营类型', '负责人员A', '2026-05-16 09:00', 'ORDERED', '餐厅编号、餐厅名称、经营类型、负责人员、启用时间和餐厅状态维护', NOW(), NOW()),
('186-01-002', '餐厅档案示例二', '经营类型', '负责人员B', '2026-05-17 14:00', 'COOKING', '餐厅档案演示数据二', NOW(), NOW());

CREATE TABLE kitchen_area (
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
INSERT INTO kitchen_area (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('186-02-001', '后厨区域示例一', '区域类型', '负责人A', '2026-05-16 09:00', 'SCHEDULED', '区域编号、区域名称、区域类型、负责人、启用时间和区域状态维护', NOW(), NOW()),
('186-02-002', '后厨区域示例二', '区域类型', '负责人B', '2026-05-17 14:00', 'DELIVERING', '后厨区域演示数据二', NOW(), NOW());

CREATE TABLE dish_menu (
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
INSERT INTO dish_menu (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('186-03-001', '菜品档案示例一', '菜品类型', '厨师人员A', '2026-05-16 09:00', 'COOKING', '菜品编号、菜品名称、菜品类型、厨师人员、上架时间和菜品状态维护', NOW(), NOW()),
('186-03-002', '菜品档案示例二', '菜品类型', '厨师人员B', '2026-05-17 14:00', 'SIGNED', '菜品档案演示数据二', NOW(), NOW());

CREATE TABLE sample_retention (
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
INSERT INTO sample_retention (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('186-04-001', '留样登记示例一', '留样类型', '留样人员A', '2026-05-16 09:00', 'DELIVERING', '留样编号、关联菜品、留样类型、留样人员、留样时间和留样状态维护', NOW(), NOW()),
('186-04-002', '留样登记示例二', '留样类型', '留样人员B', '2026-05-17 14:00', 'ANALYZED', '留样登记演示数据二', NOW(), NOW());

CREATE TABLE sample_storage (
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
INSERT INTO sample_storage (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('186-05-001', '留样存储示例一', '存储类型', '管理人员A', '2026-05-16 09:00', 'SIGNED', '存储编号、留样登记、存储类型、管理人员、存储时间和存储状态维护', NOW(), NOW()),
('186-05-002', '留样存储示例二', '存储类型', '管理人员B', '2026-05-17 14:00', 'CLOSED', '留样存储演示数据二', NOW(), NOW());

CREATE TABLE ingredient_acceptance (
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
INSERT INTO ingredient_acceptance (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('186-06-001', '食材验收示例一', '验收类型', '仓储人员A', '2026-05-16 09:00', 'ANALYZED', '验收编号、食材名称、验收类型、仓储人员、验收时间和验收状态维护', NOW(), NOW()),
('186-06-002', '食材验收示例二', '验收类型', '仓储人员B', '2026-05-17 14:00', 'REGISTERED', '食材验收演示数据二', NOW(), NOW());

CREATE TABLE disinfection_record (
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
INSERT INTO disinfection_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('186-07-001', '消毒记录示例一', '消毒类型', '执行人员A', '2026-05-16 09:00', 'CLOSED', '消毒编号、后厨区域、消毒类型、执行人员、消毒时间和消毒状态维护', NOW(), NOW()),
('186-07-002', '消毒记录示例二', '消毒类型', '执行人员B', '2026-05-17 14:00', 'ORDERED', '消毒记录演示数据二', NOW(), NOW());

CREATE TABLE hygiene_inspection (
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
INSERT INTO hygiene_inspection (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('186-08-001', '卫生巡检示例一', '巡检类型', '巡检人员A', '2026-05-16 09:00', 'REGISTERED', '巡检编号、后厨区域、巡检类型、巡检人员、巡检时间和巡检状态维护', NOW(), NOW()),
('186-08-002', '卫生巡检示例二', '巡检类型', '巡检人员B', '2026-05-17 14:00', 'SCHEDULED', '卫生巡检演示数据二', NOW(), NOW());

CREATE TABLE issue_rectification (
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
INSERT INTO issue_rectification (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('186-09-001', '问题整改示例一', '整改类型', '责任人员A', '2026-05-16 09:00', 'ORDERED', '整改编号、巡检问题、整改类型、责任人员、整改时间和整改状态维护', NOW(), NOW()),
('186-09-002', '问题整改示例二', '整改类型', '责任人员B', '2026-05-17 14:00', 'COOKING', '问题整改演示数据二', NOW(), NOW());

CREATE TABLE risk_warning (
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
INSERT INTO risk_warning (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('186-10-001', '风险提醒示例一', '风险类型', '提醒人员A', '2026-05-16 09:00', 'SCHEDULED', '提醒编号、风险对象、风险类型、提醒人员、提醒时间和提醒状态维护', NOW(), NOW()),
('186-10-002', '风险提醒示例二', '风险类型', '提醒人员B', '2026-05-17 14:00', 'DELIVERING', '风险提醒演示数据二', NOW(), NOW());

CREATE TABLE waste_disposal (
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
INSERT INTO waste_disposal (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('186-11-001', '厨余处置示例一', '处置类型', '处置人员A', '2026-05-16 09:00', 'COOKING', '处置编号、后厨区域、处置类型、处置人员、处置时间和处置状态维护', NOW(), NOW()),
('186-11-002', '厨余处置示例二', '处置类型', '处置人员B', '2026-05-17 14:00', 'SIGNED', '厨余处置演示数据二', NOW(), NOW());

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
('186-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'DELIVERING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('186-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'ANALYZED', '操作日志演示数据二', NOW(), NOW());
