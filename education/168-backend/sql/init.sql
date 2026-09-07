DROP DATABASE IF EXISTS cert_training_168;
CREATE DATABASE cert_training_168 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE cert_training_168;

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
('admin', '123456', '系统管理员', 'ADMIN', '职业培训认证中心', '13916800001', 'admin@cert168.local', 1, NOW(), NOW()),
('training', '123456', '培训管理员', 'TRAINING', '职业培训认证中心', '13916800002', 'training@cert168.local', 1, NOW(), NOW()),
('instructor', '123456', '授课讲师', 'INSTRUCTOR', '职业培训认证中心', '13916800003', 'instructor@cert168.local', 1, NOW(), NOW()),
('trainee', '123456', '参训学员', 'TRAINEE', '职业培训认证中心', '13916800004', 'trainee@cert168.local', 1, NOW(), NOW()),
('examiner', '123456', '考务管理员', 'EXAMINER', '职业培训认证中心', '13916800005', 'examiner@cert168.local', 1, NOW(), NOW()),
('certifier', '123456', '证书管理员', 'CERTIFIER', '职业培训认证中心', '13916800006', 'certifier@cert168.local', 1, NOW(), NOW());

CREATE TABLE training_course (
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
INSERT INTO training_course (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('168-01-001', '培训课程示例一', '课程类型', '授课讲师A', '2026-05-16 09:00', 'BOOKED', '课程编号、课程名称、课程类型、授课讲师、开课时间和课程状态维护', NOW(), NOW()),
('168-01-002', '培训课程示例二', '课程类型', '授课讲师B', '2026-05-17 14:00', 'VERIFIED', '培训课程演示数据二', NOW(), NOW());

CREATE TABLE trainee_profile (
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
INSERT INTO trainee_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('168-02-001', '学员档案示例一', '职业方向', '联系人员A', '2026-05-16 09:00', 'SCHEDULED', '学员编号、学员姓名、职业方向、联系人员、建档时间和学员状态维护', NOW(), NOW()),
('168-02-002', '学员档案示例二', '职业方向', '联系人员B', '2026-05-17 14:00', 'PROCESSING', '学员档案演示数据二', NOW(), NOW());

CREATE TABLE instructor_profile (
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
INSERT INTO instructor_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('168-03-001', '讲师档案示例一', '授课方向', '负责人员A', '2026-05-16 09:00', 'VERIFIED', '讲师编号、讲师姓名、授课方向、负责人员、入驻时间和讲师状态维护', NOW(), NOW()),
('168-03-002', '讲师档案示例二', '授课方向', '负责人员B', '2026-05-17 14:00', 'FINISHED', '讲师档案演示数据二', NOW(), NOW());

CREATE TABLE course_enrollment (
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
INSERT INTO course_enrollment (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('168-04-001', '课程报名示例一', '报名类型', '报名学员A', '2026-05-16 09:00', 'PROCESSING', '报名编号、报名课程、报名类型、报名学员、报名时间和报名状态维护', NOW(), NOW()),
('168-04-002', '课程报名示例二', '报名类型', '报名学员B', '2026-05-17 14:00', 'WARNING', '课程报名演示数据二', NOW(), NOW());

CREATE TABLE learning_progress (
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
INSERT INTO learning_progress (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('168-05-001', '学习进度示例一', '学习阶段', '跟进讲师A', '2026-05-16 09:00', 'FINISHED', '进度编号、学习课程、学习阶段、跟进讲师、更新时间和学习状态维护', NOW(), NOW()),
('168-05-002', '学习进度示例二', '学习阶段', '跟进讲师B', '2026-05-17 14:00', 'PUBLISHED', '学习进度演示数据二', NOW(), NOW());

CREATE TABLE exam_plan (
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
INSERT INTO exam_plan (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('168-06-001', '考试安排示例一', '考试类型', '考务人员A', '2026-05-16 09:00', 'WARNING', '考试编号、考试名称、考试类型、考务人员、考试时间和考试状态维护', NOW(), NOW()),
('168-06-002', '考试安排示例二', '考试类型', '考务人员B', '2026-05-17 14:00', 'ACTIVE', '考试安排演示数据二', NOW(), NOW());

CREATE TABLE exam_score (
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
INSERT INTO exam_score (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('168-07-001', '考试成绩示例一', '成绩类型', '评分人员A', '2026-05-16 09:00', 'PUBLISHED', '成绩编号、考试科目、成绩类型、评分人员、出分时间和成绩状态维护', NOW(), NOW()),
('168-07-002', '考试成绩示例二', '成绩类型', '评分人员B', '2026-05-17 14:00', 'BOOKED', '考试成绩演示数据二', NOW(), NOW());

CREATE TABLE certificate_issue (
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
INSERT INTO certificate_issue (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('168-08-001', '证书发放示例一', '证书类型', '发证人员A', '2026-05-16 09:00', 'ACTIVE', '证书编号、证书名称、证书类型、发证人员、发证时间和发证状态维护', NOW(), NOW()),
('168-08-002', '证书发放示例二', '证书类型', '发证人员B', '2026-05-17 14:00', 'SCHEDULED', '证书发放演示数据二', NOW(), NOW());

CREATE TABLE certificate_ledger (
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
INSERT INTO certificate_ledger (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('168-09-001', '证书台账示例一', '证书等级', '管理人员A', '2026-05-16 09:00', 'BOOKED', '台账编号、持证人员、证书等级、管理人员、登记时间和台账状态维护', NOW(), NOW()),
('168-09-002', '证书台账示例二', '证书等级', '管理人员B', '2026-05-17 14:00', 'VERIFIED', '证书台账演示数据二', NOW(), NOW());

CREATE TABLE renewal_application (
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
INSERT INTO renewal_application (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('168-10-001', '续证申请示例一', '续证类型', '申请人员A', '2026-05-16 09:00', 'SCHEDULED', '续证编号、续证证书、续证类型、申请人员、申请时间和续证状态维护', NOW(), NOW()),
('168-10-002', '续证申请示例二', '续证类型', '申请人员B', '2026-05-17 14:00', 'PROCESSING', '续证申请演示数据二', NOW(), NOW());

CREATE TABLE reminder_notice (
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
INSERT INTO reminder_notice (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('168-11-001', '提醒通知示例一', '提醒类型', '发送人员A', '2026-05-16 09:00', 'VERIFIED', '提醒编号、提醒对象、提醒类型、发送人员、发送时间和提醒状态维护', NOW(), NOW()),
('168-11-002', '提醒通知示例二', '提醒类型', '发送人员B', '2026-05-17 14:00', 'FINISHED', '提醒通知演示数据二', NOW(), NOW());

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
('168-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'PROCESSING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('168-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'WARNING', '操作日志演示数据二', NOW(), NOW());
