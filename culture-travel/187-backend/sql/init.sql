DROP DATABASE IF EXISTS team_building_187;
CREATE DATABASE team_building_187 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE team_building_187;

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
('admin', '123456', '系统管理员', 'ADMIN', '旅行社团建行程中心', '13918700001', 'admin@teambuilding187.local', 1, NOW(), NOW()),
('agency', '123456', '旅行社管理员', 'AGENCY', '旅行社团建行程中心', '13918700002', 'agency@teambuilding187.local', 1, NOW(), NOW()),
('planner', '123456', '行程策划', 'PLANNER', '旅行社团建行程中心', '13918700003', 'planner@teambuilding187.local', 1, NOW(), NOW()),
('finance', '123456', '财务人员', 'FINANCE', '旅行社团建行程中心', '13918700004', 'finance@teambuilding187.local', 1, NOW(), NOW()),
('leader', '123456', '团队领队', 'LEADER', '旅行社团建行程中心', '13918700005', 'leader@teambuilding187.local', 1, NOW(), NOW()),
('participant', '123456', '团建成员', 'PARTICIPANT', '旅行社团建行程中心', '13918700006', 'participant@teambuilding187.local', 1, NOW(), NOW());

CREATE TABLE agency_info (
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
INSERT INTO agency_info (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('187-01-001', '旅行社档案示例一', '服务类型', '负责人员A', '2026-05-16 09:00', 'ORDERED', '旅行社编号、旅行社名称、服务类型、负责人员、合作时间和旅行社状态维护', NOW(), NOW()),
('187-01-002', '旅行社档案示例二', '服务类型', '负责人员B', '2026-05-17 14:00', 'COOKING', '旅行社档案演示数据二', NOW(), NOW());

CREATE TABLE team_profile (
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
INSERT INTO team_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('187-02-001', '团队档案示例一', '团队类型', '领队人员A', '2026-05-16 09:00', 'SCHEDULED', '团队编号、团队名称、团队类型、领队人员、建档时间和团队状态维护', NOW(), NOW()),
('187-02-002', '团队档案示例二', '团队类型', '领队人员B', '2026-05-17 14:00', 'DELIVERING', '团队档案演示数据二', NOW(), NOW());

CREATE TABLE trip_plan (
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
INSERT INTO trip_plan (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('187-03-001', '团建行程示例一', '行程类型', '策划人员A', '2026-05-16 09:00', 'COOKING', '行程编号、行程名称、行程类型、策划人员、出行时间和行程状态维护', NOW(), NOW()),
('187-03-002', '团建行程示例二', '行程类型', '策划人员B', '2026-05-17 14:00', 'SIGNED', '团建行程演示数据二', NOW(), NOW());

CREATE TABLE signup_registration (
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
INSERT INTO signup_registration (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('187-04-001', '行程报名示例一', '报名类型', '报名人员A', '2026-05-16 09:00', 'DELIVERING', '报名编号、关联行程、报名类型、报名人员、报名时间和报名状态维护', NOW(), NOW()),
('187-04-002', '行程报名示例二', '报名类型', '报名人员B', '2026-05-17 14:00', 'ANALYZED', '行程报名演示数据二', NOW(), NOW());

CREATE TABLE member_confirmation (
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
INSERT INTO member_confirmation (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('187-05-001', '成员确认示例一', '确认类型', '确认人员A', '2026-05-16 09:00', 'SIGNED', '确认编号、报名记录、确认类型、确认人员、确认时间和确认状态维护', NOW(), NOW()),
('187-05-002', '成员确认示例二', '确认类型', '确认人员B', '2026-05-17 14:00', 'CLOSED', '成员确认演示数据二', NOW(), NOW());

CREATE TABLE cost_budget (
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
INSERT INTO cost_budget (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('187-06-001', '费用预算示例一', '预算类型', '编制人员A', '2026-05-16 09:00', 'ANALYZED', '预算编号、预算项目、预算类型、编制人员、编制时间和预算状态维护', NOW(), NOW()),
('187-06-002', '费用预算示例二', '预算类型', '编制人员B', '2026-05-17 14:00', 'REGISTERED', '费用预算演示数据二', NOW(), NOW());

CREATE TABLE cost_share (
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
INSERT INTO cost_share (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('187-07-001', '费用分摊示例一', '分摊类型', '财务人员A', '2026-05-16 09:00', 'CLOSED', '分摊编号、费用项目、分摊类型、财务人员、分摊时间和分摊状态维护', NOW(), NOW()),
('187-07-002', '费用分摊示例二', '分摊类型', '财务人员B', '2026-05-17 14:00', 'ORDERED', '费用分摊演示数据二', NOW(), NOW());

CREATE TABLE payment_record (
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
INSERT INTO payment_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('187-08-001', '缴费记录示例一', '缴费类型', '缴费人员A', '2026-05-16 09:00', 'REGISTERED', '缴费编号、分摊费用、缴费类型、缴费人员、缴费时间和缴费状态维护', NOW(), NOW()),
('187-08-002', '缴费记录示例二', '缴费类型', '缴费人员B', '2026-05-17 14:00', 'SCHEDULED', '缴费记录演示数据二', NOW(), NOW());

CREATE TABLE notice_sync (
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
INSERT INTO notice_sync (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('187-09-001', '通知同步示例一', '通知类型', '发布人员A', '2026-05-16 09:00', 'ORDERED', '通知编号、通知对象、通知类型、发布人员、发布时间和通知状态维护', NOW(), NOW()),
('187-09-002', '通知同步示例二', '通知类型', '发布人员B', '2026-05-17 14:00', 'COOKING', '通知同步演示数据二', NOW(), NOW());

CREATE TABLE travel_feedback (
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
INSERT INTO travel_feedback (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('187-10-001', '出行反馈示例一', '反馈类型', '反馈人员A', '2026-05-16 09:00', 'SCHEDULED', '反馈编号、关联行程、反馈类型、反馈人员、反馈时间和反馈状态维护', NOW(), NOW()),
('187-10-002', '出行反馈示例二', '反馈类型', '反馈人员B', '2026-05-17 14:00', 'DELIVERING', '出行反馈演示数据二', NOW(), NOW());

CREATE TABLE invoice_record (
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
INSERT INTO invoice_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('187-11-001', '发票记录示例一', '发票类型', '财务人员A', '2026-05-16 09:00', 'COOKING', '发票编号、费用项目、发票类型、财务人员、开票时间和发票状态维护', NOW(), NOW()),
('187-11-002', '发票记录示例二', '发票类型', '财务人员B', '2026-05-17 14:00', 'SIGNED', '发票记录演示数据二', NOW(), NOW());

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
('187-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'DELIVERING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('187-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'ANALYZED', '操作日志演示数据二', NOW(), NOW());
