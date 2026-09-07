DROP DATABASE IF EXISTS training_refund_158;
CREATE DATABASE training_refund_158 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE training_refund_158;

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
('admin', '123456', '系统管理员', 'ADMIN', '教培运营中心', '13915800001', 'admin@edu158.local', 1, NOW(), NOW()),
('principal', '123456', '校区校长', 'PRINCIPAL', '教培运营中心', '13915800002', 'principal@edu158.local', 1, NOW(), NOW()),
('academic', '123456', '教务主管', 'ACADEMIC', '教培运营中心', '13915800003', 'academic@edu158.local', 1, NOW(), NOW()),
('teacher', '123456', '任课老师', 'TEACHER', '教培运营中心', '13915800004', 'teacher@edu158.local', 1, NOW(), NOW()),
('finance', '123456', '财务审核员', 'FINANCE', '教培运营中心', '13915800005', 'finance@edu158.local', 1, NOW(), NOW()),
('parent', '123456', '学员家长', 'PARENT', '教培运营中心', '13915800006', 'parent@edu158.local', 1, NOW(), NOW());

CREATE TABLE campus_branch (
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
INSERT INTO campus_branch (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('158-01-001', '校区档案示例一', '校区类型', '负责人A', '2026-05-16 09:00', 'BOOKED', '校区编号、校区名称、校区类型、负责人、启用时间和校区状态维护', NOW(), NOW()),
('158-01-002', '校区档案示例二', '校区类型', '负责人B', '2026-05-17 14:00', 'VERIFIED', '校区档案演示数据二', NOW(), NOW());

CREATE TABLE course_catalog (
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
INSERT INTO course_catalog (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('158-02-001', '课程产品示例一', '课程类型', '负责人A', '2026-05-16 09:00', 'SCHEDULED', '课程编号、课程名称、课程类型、负责人、上架时间和课程状态维护', NOW(), NOW()),
('158-02-002', '课程产品示例二', '课程类型', '负责人B', '2026-05-17 14:00', 'PROCESSING', '课程产品演示数据二', NOW(), NOW());

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
('158-03-001', '学员档案示例一', '报名课程', '监护人A', '2026-05-16 09:00', 'VERIFIED', '学员编号、学员姓名、报名课程、监护人、报名时间和学籍状态维护', NOW(), NOW()),
('158-03-002', '学员档案示例二', '报名课程', '监护人B', '2026-05-17 14:00', 'FINISHED', '学员档案演示数据二', NOW(), NOW());

CREATE TABLE teacher_profile (
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
INSERT INTO teacher_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('158-04-001', '教师档案示例一', '授课类型', '所属校区A', '2026-05-16 09:00', 'PROCESSING', '教师编号、教师姓名、授课类型、所属校区、入职时间和教师状态维护', NOW(), NOW()),
('158-04-002', '教师档案示例二', '授课类型', '所属校区B', '2026-05-17 14:00', 'WARNING', '教师档案演示数据二', NOW(), NOW());

CREATE TABLE class_group (
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
INSERT INTO class_group (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('158-05-001', '班级台账示例一', '班级类型', '班主任A', '2026-05-16 09:00', 'FINISHED', '班级编号、班级名称、班级类型、班主任、开班时间和班级状态维护', NOW(), NOW()),
('158-05-002', '班级台账示例二', '班级类型', '班主任B', '2026-05-17 14:00', 'PUBLISHED', '班级台账演示数据二', NOW(), NOW());

CREATE TABLE lesson_schedule (
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
INSERT INTO lesson_schedule (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('158-06-001', '排课计划示例一', '排课类型', '授课老师A', '2026-05-16 09:00', 'WARNING', '排课编号、课程班级、排课类型、授课老师、上课时间和排课状态维护', NOW(), NOW()),
('158-06-002', '排课计划示例二', '排课类型', '授课老师B', '2026-05-17 14:00', 'ACTIVE', '排课计划演示数据二', NOW(), NOW());

CREATE TABLE attendance_record (
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
INSERT INTO attendance_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('158-07-001', '上课考勤示例一', '考勤类型', '点名老师A', '2026-05-16 09:00', 'PUBLISHED', '考勤编号、上课班级、考勤类型、点名老师、考勤时间和考勤状态维护', NOW(), NOW()),
('158-07-002', '上课考勤示例二', '考勤类型', '点名老师B', '2026-05-17 14:00', 'BOOKED', '上课考勤演示数据二', NOW(), NOW());

CREATE TABLE consumption_record (
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
INSERT INTO consumption_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('158-08-001', '课消记录示例一', '课消类型', '确认人A', '2026-05-16 09:00', 'ACTIVE', '课消编号、课消课程、课消类型、确认人、课消时间和课消状态维护', NOW(), NOW()),
('158-08-002', '课消记录示例二', '课消类型', '确认人B', '2026-05-17 14:00', 'SCHEDULED', '课消记录演示数据二', NOW(), NOW());

CREATE TABLE refund_application (
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
INSERT INTO refund_application (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('158-09-001', '退费申请示例一', '退费类型', '申请人A', '2026-05-16 09:00', 'BOOKED', '申请编号、申请学员、退费类型、申请人、申请时间和申请状态维护', NOW(), NOW()),
('158-09-002', '退费申请示例二', '退费类型', '申请人B', '2026-05-17 14:00', 'VERIFIED', '退费申请演示数据二', NOW(), NOW());

CREATE TABLE refund_approval (
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
INSERT INTO refund_approval (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('158-10-001', '退费审批示例一', '审批类型', '审批人A', '2026-05-16 09:00', 'SCHEDULED', '审批编号、退费申请、审批类型、审批人、审批时间和审批状态维护', NOW(), NOW()),
('158-10-002', '退费审批示例二', '审批类型', '审批人B', '2026-05-17 14:00', 'PROCESSING', '退费审批演示数据二', NOW(), NOW());

CREATE TABLE finance_ledger (
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
INSERT INTO finance_ledger (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('158-11-001', '财务流水示例一', '收支类型', '经办人A', '2026-05-16 09:00', 'VERIFIED', '流水编号、流水事项、收支类型、经办人、发生时间和流水状态维护', NOW(), NOW()),
('158-11-002', '财务流水示例二', '收支类型', '经办人B', '2026-05-17 14:00', 'FINISHED', '财务流水演示数据二', NOW(), NOW());

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
('158-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'PROCESSING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('158-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'WARNING', '操作日志演示数据二', NOW(), NOW());
