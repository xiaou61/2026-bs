DROP DATABASE IF EXISTS nurse_training_188;
CREATE DATABASE nurse_training_188 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE nurse_training_188;

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
('admin', '123456', '系统管理员', 'ADMIN', '医疗护理培训中心', '13918800001', 'admin@nursetraining188.local', 1, NOW(), NOW()),
('edu', '123456', '护理培训管理员', 'EDU', '医疗护理培训中心', '13918800002', 'edu@nursetraining188.local', 1, NOW(), NOW()),
('instructor', '123456', '带教老师', 'INSTRUCTOR', '医疗护理培训中心', '13918800003', 'instructor@nursetraining188.local', 1, NOW(), NOW()),
('assessor', '123456', '考核老师', 'ASSESSOR', '医疗护理培训中心', '13918800004', 'assessor@nursetraining188.local', 1, NOW(), NOW()),
('nurse', '123456', '护理人员', 'NURSE', '医疗护理培训中心', '13918800005', 'nurse@nursetraining188.local', 1, NOW(), NOW()),
('hr', '123456', '护理人事', 'HR', '医疗护理培训中心', '13918800006', 'hr@nursetraining188.local', 1, NOW(), NOW());

CREATE TABLE training_program (
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
INSERT INTO training_program (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('188-01-001', '培训项目示例一', '培训类型', '负责人A', '2026-05-16 09:00', 'ORDERED', '项目编号、项目名称、培训类型、负责人、计划时间和项目状态维护', NOW(), NOW()),
('188-01-002', '培训项目示例二', '培训类型', '负责人B', '2026-05-17 14:00', 'TRAINING', '培训项目演示数据二', NOW(), NOW());

CREATE TABLE nurse_profile (
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
INSERT INTO nurse_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('188-02-001', '护理人员档案示例一', '科室类型', '责任老师A', '2026-05-16 09:00', 'SCHEDULED', '档案编号、护士姓名、科室类型、责任老师、建档时间和在岗状态维护', NOW(), NOW()),
('188-02-002', '护理人员档案示例二', '科室类型', '责任老师B', '2026-05-17 14:00', 'ASSESSING', '护理人员档案演示数据二', NOW(), NOW());

CREATE TABLE training_class (
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
INSERT INTO training_class (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('188-03-001', '培训班次示例一', '培训类型', '带教老师A', '2026-05-16 09:00', 'TRAINING', '班次编号、班次名称、培训类型、带教老师、开班时间和班次状态维护', NOW(), NOW()),
('188-03-002', '培训班次示例二', '培训类型', '带教老师B', '2026-05-17 14:00', 'PASSED', '培训班次演示数据二', NOW(), NOW());

CREATE TABLE training_checkin (
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
INSERT INTO training_checkin (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('188-04-001', '培训签到示例一', '签到类型', '签到人员A', '2026-05-16 09:00', 'ASSESSING', '签到编号、关联班次、签到类型、签到人员、签到时间和签到状态维护', NOW(), NOW()),
('188-04-002', '培训签到示例二', '签到类型', '签到人员B', '2026-05-17 14:00', 'RETRAINING', '培训签到演示数据二', NOW(), NOW());

CREATE TABLE skill_item (
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
INSERT INTO skill_item (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('188-05-001', '技能项目示例一', '技能类型', '维护人员A', '2026-05-16 09:00', 'PASSED', '技能编号、技能名称、技能类型、维护人员、更新时间和技能状态维护', NOW(), NOW()),
('188-05-002', '技能项目示例二', '技能类型', '维护人员B', '2026-05-17 14:00', 'CLOSED', '技能项目演示数据二', NOW(), NOW());

CREATE TABLE skill_assessment (
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
INSERT INTO skill_assessment (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('188-06-001', '技能考核示例一', '考核类型', '考核老师A', '2026-05-16 09:00', 'RETRAINING', '考核编号、考核项目、考核类型、考核老师、考核时间和考核状态维护', NOW(), NOW()),
('188-06-002', '技能考核示例二', '考核类型', '考核老师B', '2026-05-17 14:00', 'REGISTERED', '技能考核演示数据二', NOW(), NOW());

CREATE TABLE certificate_archive (
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
INSERT INTO certificate_archive (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('188-07-001', '证书档案示例一', '证书类型', '归档人员A', '2026-05-16 09:00', 'CLOSED', '证书编号、证书名称、证书类型、归档人员、发证时间和证书状态维护', NOW(), NOW()),
('188-07-002', '证书档案示例二', '证书类型', '归档人员B', '2026-05-17 14:00', 'ORDERED', '证书档案演示数据二', NOW(), NOW());

CREATE TABLE retraining_reminder (
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
INSERT INTO retraining_reminder (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('188-08-001', '续训提醒示例一', '提醒类型', '提醒人员A', '2026-05-16 09:00', 'REGISTERED', '提醒编号、证书项目、提醒类型、提醒人员、提醒时间和续训状态维护', NOW(), NOW()),
('188-08-002', '续训提醒示例二', '提醒类型', '提醒人员B', '2026-05-17 14:00', 'SCHEDULED', '续训提醒演示数据二', NOW(), NOW());

CREATE TABLE practice_record (
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
INSERT INTO practice_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('188-09-001', '实操记录示例一', '实操类型', '指导老师A', '2026-05-16 09:00', 'ORDERED', '实操编号、实操项目、实操类型、指导老师、实操时间和记录状态维护', NOW(), NOW()),
('188-09-002', '实操记录示例二', '实操类型', '指导老师B', '2026-05-17 14:00', 'TRAINING', '实操记录演示数据二', NOW(), NOW());

CREATE TABLE assessment_appeal (
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
INSERT INTO assessment_appeal (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('188-10-001', '考核申诉示例一', '申诉类型', '申诉人员A', '2026-05-16 09:00', 'SCHEDULED', '申诉编号、关联考核、申诉类型、申诉人员、申诉时间和处理状态维护', NOW(), NOW()),
('188-10-002', '考核申诉示例二', '申诉类型', '申诉人员B', '2026-05-17 14:00', 'ASSESSING', '考核申诉演示数据二', NOW(), NOW());

CREATE TABLE training_notice (
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
INSERT INTO training_notice (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('188-11-001', '培训通知示例一', '通知类型', '发布人员A', '2026-05-16 09:00', 'TRAINING', '通知编号、通知对象、通知类型、发布人员、发布时间和通知状态维护', NOW(), NOW()),
('188-11-002', '培训通知示例二', '通知类型', '发布人员B', '2026-05-17 14:00', 'PASSED', '培训通知演示数据二', NOW(), NOW());

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
('188-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'ASSESSING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('188-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'RETRAINING', '操作日志演示数据二', NOW(), NOW());
