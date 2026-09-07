DROP DATABASE IF EXISTS innovation_class_193;
CREATE DATABASE innovation_class_193 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE innovation_class_193;

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
('admin', '123456', '系统管理员', 'ADMIN', '校园创新实验班管理中心', '13919300001', 'admin@innovationclass193.local', 1, NOW(), NOW()),
('academic', '123456', '教务管理员', 'ACADEMIC', '校园创新实验班管理中心', '13919300002', 'academic@innovationclass193.local', 1, NOW(), NOW()),
('reviewer', '123456', '选拔评审', 'REVIEWER', '校园创新实验班管理中心', '13919300003', 'reviewer@innovationclass193.local', 1, NOW(), NOW()),
('mentor', '123456', '导师教师', 'MENTOR', '校园创新实验班管理中心', '13919300004', 'mentor@innovationclass193.local', 1, NOW(), NOW()),
('counselor', '123456', '辅导员', 'COUNSELOR', '校园创新实验班管理中心', '13919300005', 'counselor@innovationclass193.local', 1, NOW(), NOW()),
('student', '123456', '学生用户', 'STUDENT', '校园创新实验班管理中心', '13919300006', 'student@innovationclass193.local', 1, NOW(), NOW());

CREATE TABLE innovation_program (
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
INSERT INTO innovation_program (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('193-01-001', '实验班项目示例一', '培养方向', '项目负责人A', '2026-05-16 09:00', 'APPLIED', '项目编号、项目名称、培养方向、项目负责人、建档时间和项目状态维护', NOW(), NOW()),
('193-01-002', '实验班项目示例二', '培养方向', '项目负责人B', '2026-05-17 14:00', 'INTERVIEWING', '实验班项目演示数据二', NOW(), NOW());

CREATE TABLE student_profile (
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
INSERT INTO student_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('193-02-001', '学生档案示例一', '专业年级', '辅导员A', '2026-05-16 09:00', 'REVIEWING', '档案编号、学生姓名、专业年级、辅导员、入学时间和培养状态维护', NOW(), NOW()),
('193-02-002', '学生档案示例二', '专业年级', '辅导员B', '2026-05-17 14:00', 'SELECTED', '学生档案演示数据二', NOW(), NOW());

CREATE TABLE mentor_profile (
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
INSERT INTO mentor_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('193-03-001', '导师档案示例一', '研究方向', '所属学院A', '2026-05-16 09:00', 'INTERVIEWING', '导师编号、导师姓名、研究方向、所属学院、入库时间和导师状态维护', NOW(), NOW()),
('193-03-002', '导师档案示例二', '研究方向', '所属学院B', '2026-05-17 14:00', 'MATCHED', '导师档案演示数据二', NOW(), NOW());

CREATE TABLE selection_notice (
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
INSERT INTO selection_notice (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('193-04-001', '选拔公告示例一', '选拔批次', '发布人员A', '2026-05-16 09:00', 'SELECTED', '公告编号、公告名称、选拔批次、发布人员、发布时间和公告状态维护', NOW(), NOW()),
('193-04-002', '选拔公告示例二', '选拔批次', '发布人员B', '2026-05-17 14:00', 'TRACKING', '选拔公告演示数据二', NOW(), NOW());

CREATE TABLE application_registration (
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
INSERT INTO application_registration (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('193-05-001', '报名选拔示例一', '申报方向', '申请人员A', '2026-05-16 09:00', 'MATCHED', '报名编号、报名学生、申报方向、申请人员、报名时间和报名状态维护', NOW(), NOW()),
('193-05-002', '报名选拔示例二', '申报方向', '申请人员B', '2026-05-17 14:00', 'CLOSED', '报名选拔演示数据二', NOW(), NOW());

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
('193-06-001', '资格评审示例一', '评审类型', '评审人员A', '2026-05-16 09:00', 'TRACKING', '评审编号、报名申请、评审类型、评审人员、评审时间和评审状态维护', NOW(), NOW()),
('193-06-002', '资格评审示例二', '评审类型', '评审人员B', '2026-05-17 14:00', 'REGISTERED', '资格评审演示数据二', NOW(), NOW());

CREATE TABLE interview_assessment (
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
INSERT INTO interview_assessment (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('193-07-001', '面试考核示例一', '考核项目', '评委人员A', '2026-05-16 09:00', 'CLOSED', '考核编号、报名学生、考核项目、评委人员、考核时间和考核状态维护', NOW(), NOW()),
('193-07-002', '面试考核示例二', '考核项目', '评委人员B', '2026-05-17 14:00', 'APPLIED', '面试考核演示数据二', NOW(), NOW());

CREATE TABLE mentor_match (
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
INSERT INTO mentor_match (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('193-08-001', '导师匹配示例一', '匹配方向', '导师教师A', '2026-05-16 09:00', 'REGISTERED', '匹配编号、入选学生、匹配方向、导师教师、匹配时间和匹配状态维护', NOW(), NOW()),
('193-08-002', '导师匹配示例二', '匹配方向', '导师教师B', '2026-05-17 14:00', 'REVIEWING', '导师匹配演示数据二', NOW(), NOW());

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
('193-09-001', '培养计划示例一', '培养阶段', '指导导师A', '2026-05-16 09:00', 'APPLIED', '计划编号、培养学生、培养阶段、指导导师、计划时间和计划状态维护', NOW(), NOW()),
('193-09-002', '培养计划示例二', '培养阶段', '指导导师B', '2026-05-17 14:00', 'INTERVIEWING', '培养计划演示数据二', NOW(), NOW());

CREATE TABLE process_tracking (
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
INSERT INTO process_tracking (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('193-10-001', '过程跟踪示例一', '跟踪类型', '跟进人员A', '2026-05-16 09:00', 'REVIEWING', '跟踪编号、培养计划、跟踪类型、跟进人员、跟踪时间和跟踪状态维护', NOW(), NOW()),
('193-10-002', '过程跟踪示例二', '跟踪类型', '跟进人员B', '2026-05-17 14:00', 'SELECTED', '过程跟踪演示数据二', NOW(), NOW());

CREATE TABLE achievement_archive (
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
INSERT INTO achievement_archive (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('193-11-001', '成果档案示例一', '成果类型', '指导人员A', '2026-05-16 09:00', 'INTERVIEWING', '成果编号、培养学生、成果类型、指导人员、归档时间和成果状态维护', NOW(), NOW()),
('193-11-002', '成果档案示例二', '成果类型', '指导人员B', '2026-05-17 14:00', 'MATCHED', '成果档案演示数据二', NOW(), NOW());

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
('193-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'SELECTED', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('193-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'TRACKING', '操作日志演示数据二', NOW(), NOW());
