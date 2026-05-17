DROP DATABASE IF EXISTS sport_rehab_199;
CREATE DATABASE sport_rehab_199 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE sport_rehab_199;

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
('admin', '123456', '系统管理员', 'ADMIN', '运动康复训练中心', '13919900001', 'admin@sportrehab199.local', 1, NOW(), NOW()),
('center', '123456', '中心管理员', 'CENTER', '运动康复训练中心', '13919900002', 'center@sportrehab199.local', 1, NOW(), NOW()),
('assessor', '123456', '体测评估师', 'ASSESSOR', '运动康复训练中心', '13919900003', 'assessor@sportrehab199.local', 1, NOW(), NOW()),
('coach', '123456', '康复教练', 'COACH', '运动康复训练中心', '13919900004', 'coach@sportrehab199.local', 1, NOW(), NOW()),
('therapist', '123456', '康复治疗师', 'THERAPIST', '运动康复训练中心', '13919900005', 'therapist@sportrehab199.local', 1, NOW(), NOW()),
('member', '123456', '训练会员', 'MEMBER', '运动康复训练中心', '13919900006', 'member@sportrehab199.local', 1, NOW(), NOW());

CREATE TABLE rehab_center (
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
INSERT INTO rehab_center (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('199-01-001', '康复中心示例一', '服务方向', '负责人A', '2026-05-17 09:00', 'EVALUATING', '中心编号、中心名称、服务方向、负责人、建档时间和中心状态维护', NOW(), NOW()),
('199-01-002', '康复中心示例二', '服务方向', '负责人B', '2026-05-18 14:00', 'PLANNING', '康复中心演示数据二', NOW(), NOW());

CREATE TABLE member_profile (
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
INSERT INTO member_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('199-02-001', '会员档案示例一', '康复目标', '负责教练A', '2026-05-17 09:00', 'WARNING', '会员编号、会员姓名、康复目标、负责教练、建档时间和会员状态维护', NOW(), NOW()),
('199-02-002', '会员档案示例二', '康复目标', '负责教练B', '2026-05-18 14:00', 'TRAINING', '会员档案演示数据二', NOW(), NOW());

CREATE TABLE coach_profile (
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
INSERT INTO coach_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('199-03-001', '教练档案示例一', '擅长项目', '管理人员A', '2026-05-17 09:00', 'PLANNING', '教练编号、教练姓名、擅长项目、管理人员、入职时间和教练状态维护', NOW(), NOW()),
('199-03-002', '教练档案示例二', '擅长项目', '管理人员B', '2026-05-18 14:00', 'FEEDBACK', '教练档案演示数据二', NOW(), NOW());

CREATE TABLE assessment_item (
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
INSERT INTO assessment_item (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('199-04-001', '体测项目示例一', '评估类型', '维护人员A', '2026-05-17 09:00', 'TRAINING', '项目编号、项目名称、评估类型、维护人员、启用时间和项目状态维护', NOW(), NOW()),
('199-04-002', '体测项目示例二', '评估类型', '维护人员B', '2026-05-18 14:00', 'REVIEWING', '体测项目演示数据二', NOW(), NOW());

CREATE TABLE fitness_assessment (
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
INSERT INTO fitness_assessment (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('199-05-001', '体测评估示例一', '评估类型', '评估人员A', '2026-05-17 09:00', 'FEEDBACK', '评估编号、会员姓名、评估类型、评估人员、评估时间和评估状态维护', NOW(), NOW()),
('199-05-002', '体测评估示例二', '评估类型', '评估人员B', '2026-05-18 14:00', 'CLOSED', '体测评估演示数据二', NOW(), NOW());

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
('199-06-001', '风险提醒示例一', '风险类型', '评估人员A', '2026-05-17 09:00', 'REVIEWING', '提醒编号、会员姓名、风险类型、评估人员、提醒时间和处理状态维护', NOW(), NOW()),
('199-06-002', '风险提醒示例二', '风险类型', '评估人员B', '2026-05-18 14:00', 'REGISTERED', '风险提醒演示数据二', NOW(), NOW());

CREATE TABLE training_plan (
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
INSERT INTO training_plan (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('199-07-001', '训练计划示例一', '训练目标', '制定教练A', '2026-05-17 09:00', 'CLOSED', '计划编号、会员姓名、训练目标、制定教练、计划时间和计划状态维护', NOW(), NOW()),
('199-07-002', '训练计划示例二', '训练目标', '制定教练B', '2026-05-18 14:00', 'EVALUATING', '训练计划演示数据二', NOW(), NOW());

CREATE TABLE training_session (
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
INSERT INTO training_session (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('199-08-001', '训练安排示例一', '训练类型', '指导教练A', '2026-05-17 09:00', 'REGISTERED', '安排编号、训练计划、训练类型、指导教练、训练时间和训练状态维护', NOW(), NOW()),
('199-08-002', '训练安排示例二', '训练类型', '指导教练B', '2026-05-18 14:00', 'WARNING', '训练安排演示数据二', NOW(), NOW());

CREATE TABLE exercise_checkin (
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
INSERT INTO exercise_checkin (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('199-09-001', '训练打卡示例一', '打卡类型', '记录人员A', '2026-05-17 09:00', 'EVALUATING', '打卡编号、训练安排、打卡类型、记录人员、打卡时间和打卡状态维护', NOW(), NOW()),
('199-09-002', '训练打卡示例二', '打卡类型', '记录人员B', '2026-05-18 14:00', 'PLANNING', '训练打卡演示数据二', NOW(), NOW());

CREATE TABLE rehab_feedback (
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
INSERT INTO rehab_feedback (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('199-10-001', '康复反馈示例一', '反馈类型', '康复治疗师A', '2026-05-17 09:00', 'WARNING', '反馈编号、训练安排、反馈类型、康复治疗师、反馈时间和反馈状态维护', NOW(), NOW()),
('199-10-002', '康复反馈示例二', '反馈类型', '康复治疗师B', '2026-05-18 14:00', 'TRAINING', '康复反馈演示数据二', NOW(), NOW());

CREATE TABLE reassessment_record (
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
INSERT INTO reassessment_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('199-11-001', '复评记录示例一', '复评类型', '评估人员A', '2026-05-17 09:00', 'PLANNING', '复评编号、会员姓名、复评类型、评估人员、复评时间和复评状态维护', NOW(), NOW()),
('199-11-002', '复评记录示例二', '复评类型', '评估人员B', '2026-05-18 14:00', 'FEEDBACK', '复评记录演示数据二', NOW(), NOW());

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
('199-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-17 09:00', 'TRAINING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('199-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-18 14:00', 'REVIEWING', '操作日志演示数据二', NOW(), NOW());
