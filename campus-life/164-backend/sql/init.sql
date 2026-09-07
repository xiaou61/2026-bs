DROP DATABASE IF EXISTS campus_sport_event_164;
CREATE DATABASE campus_sport_event_164 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE campus_sport_event_164;

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
('admin', '123456', '系统管理员', 'ADMIN', '校园体育赛事中心', '13916400001', 'admin@sport164.local', 1, NOW(), NOW()),
('organizer', '123456', '赛事组织员', 'ORGANIZER', '校园体育赛事中心', '13916400002', 'organizer@sport164.local', 1, NOW(), NOW()),
('coach', '123456', '队伍教练', 'COACH', '校园体育赛事中心', '13916400003', 'coach@sport164.local', 1, NOW(), NOW()),
('athlete', '123456', '参赛学生', 'ATHLETE', '校园体育赛事中心', '13916400004', 'athlete@sport164.local', 1, NOW(), NOW()),
('referee', '123456', '裁判员', 'REFEREE', '校园体育赛事中心', '13916400005', 'referee@sport164.local', 1, NOW(), NOW()),
('committee', '123456', '仲裁委员', 'COMMITTEE', '校园体育赛事中心', '13916400006', 'committee@sport164.local', 1, NOW(), NOW());

CREATE TABLE sport_event (
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
INSERT INTO sport_event (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('164-01-001', '体育赛事示例一', '项目类型', '主办老师A', '2026-05-16 09:00', 'BOOKED', '赛事编号、赛事名称、项目类型、主办老师、开赛时间和赛事状态维护', NOW(), NOW()),
('164-01-002', '体育赛事示例二', '项目类型', '主办老师B', '2026-05-17 14:00', 'VERIFIED', '体育赛事演示数据二', NOW(), NOW());

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
('164-02-001', '参赛队伍示例一', '参赛组别', '队伍教练A', '2026-05-16 09:00', 'SCHEDULED', '队伍编号、队伍名称、参赛组别、队伍教练、报名时间和队伍状态维护', NOW(), NOW()),
('164-02-002', '参赛队伍示例二', '参赛组别', '队伍教练B', '2026-05-17 14:00', 'PROCESSING', '参赛队伍演示数据二', NOW(), NOW());

CREATE TABLE athlete_profile (
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
INSERT INTO athlete_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('164-03-001', '运动员档案示例一', '所属队伍', '指导教练A', '2026-05-16 09:00', 'VERIFIED', '学号、学生姓名、所属队伍、指导教练、登记时间和参赛状态维护', NOW(), NOW()),
('164-03-002', '运动员档案示例二', '所属队伍', '指导教练B', '2026-05-17 14:00', 'FINISHED', '运动员档案演示数据二', NOW(), NOW());

CREATE TABLE event_registration (
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
INSERT INTO event_registration (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('164-04-001', '赛事报名示例一', '参赛队伍', '报名负责人A', '2026-05-16 09:00', 'PROCESSING', '报名编号、报名项目、参赛队伍、报名负责人、提交时间和报名状态维护', NOW(), NOW()),
('164-04-002', '赛事报名示例二', '参赛队伍', '报名负责人B', '2026-05-17 14:00', 'WARNING', '赛事报名演示数据二', NOW(), NOW());

CREATE TABLE group_draw (
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
INSERT INTO group_draw (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('164-05-001', '报名分组示例一', '竞赛项目', '分组负责人A', '2026-05-16 09:00', 'FINISHED', '分组编号、分组名称、竞赛项目、分组负责人、抽签时间和分组状态维护', NOW(), NOW()),
('164-05-002', '报名分组示例二', '竞赛项目', '分组负责人B', '2026-05-17 14:00', 'PUBLISHED', '报名分组演示数据二', NOW(), NOW());

CREATE TABLE schedule_match (
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
INSERT INTO schedule_match (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('164-06-001', '赛程编排示例一', '竞赛轮次', '编排人员A', '2026-05-16 09:00', 'WARNING', '赛程编号、比赛名称、竞赛轮次、编排人员、比赛时间和赛程状态维护', NOW(), NOW()),
('164-06-002', '赛程编排示例二', '竞赛轮次', '编排人员B', '2026-05-17 14:00', 'ACTIVE', '赛程编排演示数据二', NOW(), NOW());

CREATE TABLE venue_resource (
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
INSERT INTO venue_resource (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('164-07-001', '场地资源示例一', '场地类型', '场馆管理员A', '2026-05-16 09:00', 'PUBLISHED', '场地编号、场地名称、场地类型、场馆管理员、开放时间和场地状态维护', NOW(), NOW()),
('164-07-002', '场地资源示例二', '场地类型', '场馆管理员B', '2026-05-17 14:00', 'BOOKED', '场地资源演示数据二', NOW(), NOW());

CREATE TABLE referee_assignment (
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
INSERT INTO referee_assignment (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('164-08-001', '裁判指派示例一', '裁判岗位', '裁判员A', '2026-05-16 09:00', 'ACTIVE', '指派编号、比赛项目、裁判岗位、裁判员、指派时间和指派状态维护', NOW(), NOW()),
('164-08-002', '裁判指派示例二', '裁判岗位', '裁判员B', '2026-05-17 14:00', 'SCHEDULED', '裁判指派演示数据二', NOW(), NOW());

CREATE TABLE score_record (
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
INSERT INTO score_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('164-09-001', '裁判评分示例一', '评分项目', '裁判员A', '2026-05-16 09:00', 'BOOKED', '评分编号、比赛对象、评分项目、裁判员、评分时间和评分状态维护', NOW(), NOW()),
('164-09-002', '裁判评分示例二', '评分项目', '裁判员B', '2026-05-17 14:00', 'VERIFIED', '裁判评分演示数据二', NOW(), NOW());

CREATE TABLE result_publish (
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
INSERT INTO result_publish (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('164-10-001', '成绩公示示例一', '名次组别', '发布人员A', '2026-05-16 09:00', 'SCHEDULED', '公示编号、公示项目、名次组别、发布人员、公示时间和公示状态维护', NOW(), NOW()),
('164-10-002', '成绩公示示例二', '名次组别', '发布人员B', '2026-05-17 14:00', 'PROCESSING', '成绩公示演示数据二', NOW(), NOW());

CREATE TABLE appeal_review (
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
INSERT INTO appeal_review (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('164-11-001', '申诉复核示例一', '申诉类型', '复核委员A', '2026-05-16 09:00', 'VERIFIED', '申诉编号、申诉赛事、申诉类型、复核委员、申诉时间和复核状态维护', NOW(), NOW()),
('164-11-002', '申诉复核示例二', '申诉类型', '复核委员B', '2026-05-17 14:00', 'FINISHED', '申诉复核演示数据二', NOW(), NOW());

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
('164-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'PROCESSING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('164-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'WARNING', '操作日志演示数据二', NOW(), NOW());
