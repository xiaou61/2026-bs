DROP DATABASE IF EXISTS elder_meal_182;
CREATE DATABASE elder_meal_182 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE elder_meal_182;

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
('admin', '123456', '系统管理员', 'ADMIN', '社区养老助餐中心', '13918200001', 'admin@eldermeal182.local', 1, NOW(), NOW()),
('center', '123456', '助餐中心', 'CENTER', '社区养老助餐中心', '13918200002', 'center@eldermeal182.local', 1, NOW(), NOW()),
('dietitian', '123456', '营养师', 'DIETITIAN', '社区养老助餐中心', '13918200003', 'dietitian@eldermeal182.local', 1, NOW(), NOW()),
('dispatch', '123456', '调度员', 'DISPATCH', '社区养老助餐中心', '13918200004', 'dispatch@eldermeal182.local', 1, NOW(), NOW()),
('courier', '123456', '配送员', 'COURIER', '社区养老助餐中心', '13918200005', 'courier@eldermeal182.local', 1, NOW(), NOW()),
('elder', '123456', '老人用户', 'ELDER', '社区养老助餐中心', '13918200006', 'elder@eldermeal182.local', 1, NOW(), NOW());

CREATE TABLE meal_site (
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
INSERT INTO meal_site (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('182-01-001', '助餐站点示例一', '站点类型', '负责人员A', '2026-05-16 09:00', 'ORDERED', '站点编号、站点名称、站点类型、负责人员、启用时间和站点状态维护', NOW(), NOW()),
('182-01-002', '助餐站点示例二', '站点类型', '负责人员B', '2026-05-17 14:00', 'COOKING', '助餐站点演示数据二', NOW(), NOW());

CREATE TABLE elder_profile (
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
INSERT INTO elder_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('182-02-001', '老人档案示例一', '服务类型', '联系人A', '2026-05-16 09:00', 'SCHEDULED', '老人编号、老人姓名、服务类型、联系人、建档时间和档案状态维护', NOW(), NOW()),
('182-02-002', '老人档案示例二', '服务类型', '联系人B', '2026-05-17 14:00', 'DELIVERING', '老人档案演示数据二', NOW(), NOW());

CREATE TABLE nutrition_menu (
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
INSERT INTO nutrition_menu (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('182-03-001', '营养套餐示例一', '膳食类型', '营养师A', '2026-05-16 09:00', 'COOKING', '套餐编号、套餐名称、膳食类型、营养师、上架时间和套餐状态维护', NOW(), NOW()),
('182-03-002', '营养套餐示例二', '膳食类型', '营养师B', '2026-05-17 14:00', 'SIGNED', '营养套餐演示数据二', NOW(), NOW());

CREATE TABLE meal_order (
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
INSERT INTO meal_order (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('182-04-001', '助餐订单示例一', '套餐类型', '服务人员A', '2026-05-16 09:00', 'DELIVERING', '订单编号、订餐老人、套餐类型、服务人员、下单时间和订单状态维护', NOW(), NOW()),
('182-04-002', '助餐订单示例二', '套餐类型', '服务人员B', '2026-05-17 14:00', 'ANALYZED', '助餐订单演示数据二', NOW(), NOW());

CREATE TABLE route_plan (
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
INSERT INTO route_plan (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('182-05-001', '配送排线示例一', '排线类型', '调度人员A', '2026-05-16 09:00', 'SIGNED', '排线编号、配送区域、排线类型、调度人员、排线时间和排线状态维护', NOW(), NOW()),
('182-05-002', '配送排线示例二', '排线类型', '调度人员B', '2026-05-17 14:00', 'CLOSED', '配送排线演示数据二', NOW(), NOW());

CREATE TABLE delivery_task (
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
INSERT INTO delivery_task (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('182-06-001', '配送任务示例一', '配送类型', '配送人员A', '2026-05-16 09:00', 'ANALYZED', '任务编号、助餐订单、配送类型、配送人员、配送时间和任务状态维护', NOW(), NOW()),
('182-06-002', '配送任务示例二', '配送类型', '配送人员B', '2026-05-17 14:00', 'REGISTERED', '配送任务演示数据二', NOW(), NOW());

CREATE TABLE sign_receipt (
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
INSERT INTO sign_receipt (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('182-07-001', '签收回执示例一', '签收类型', '签收人员A', '2026-05-16 09:00', 'CLOSED', '回执编号、配送任务、签收类型、签收人员、签收时间和回执状态维护', NOW(), NOW()),
('182-07-002', '签收回执示例二', '签收类型', '签收人员B', '2026-05-17 14:00', 'ORDERED', '签收回执演示数据二', NOW(), NOW());

CREATE TABLE dietary_restriction (
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
INSERT INTO dietary_restriction (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('182-08-001', '饮食禁忌示例一', '禁忌类型', '记录人员A', '2026-05-16 09:00', 'REGISTERED', '禁忌编号、老人档案、禁忌类型、记录人员、记录时间和禁忌状态维护', NOW(), NOW()),
('182-08-002', '饮食禁忌示例二', '禁忌类型', '记录人员B', '2026-05-17 14:00', 'SCHEDULED', '饮食禁忌演示数据二', NOW(), NOW());

CREATE TABLE nutrition_analysis (
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
INSERT INTO nutrition_analysis (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('182-09-001', '营养分析示例一', '分析类型', '营养师A', '2026-05-16 09:00', 'ORDERED', '分析编号、助餐订单、分析类型、营养师、分析时间和分析状态维护', NOW(), NOW()),
('182-09-002', '营养分析示例二', '分析类型', '营养师B', '2026-05-17 14:00', 'COOKING', '营养分析演示数据二', NOW(), NOW());

CREATE TABLE subsidy_record (
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
INSERT INTO subsidy_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('182-10-001', '补贴记录示例一', '补贴类型', '审核人员A', '2026-05-16 09:00', 'SCHEDULED', '补贴编号、服务对象、补贴类型、审核人员、登记时间和补贴状态维护', NOW(), NOW()),
('182-10-002', '补贴记录示例二', '补贴类型', '审核人员B', '2026-05-17 14:00', 'DELIVERING', '补贴记录演示数据二', NOW(), NOW());

CREATE TABLE visit_followup (
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
INSERT INTO visit_followup (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('182-11-001', '回访关怀示例一', '回访类型', '回访人员A', '2026-05-16 09:00', 'COOKING', '回访编号、助餐老人、回访类型、回访人员、回访时间和回访状态维护', NOW(), NOW()),
('182-11-002', '回访关怀示例二', '回访类型', '回访人员B', '2026-05-17 14:00', 'SIGNED', '回访关怀演示数据二', NOW(), NOW());

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
('182-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'DELIVERING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('182-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'ANALYZED', '操作日志演示数据二', NOW(), NOW());
