DROP DATABASE IF EXISTS class_attendance_179;
CREATE DATABASE class_attendance_179 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE class_attendance_179;

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
('admin', '123456', '系统管理员', 'ADMIN', '高校课堂考勤中心', '13917900001', 'admin@attendance179.local', 1, NOW(), NOW()),
('academic', '123456', '教务管理员', 'ACADEMIC', '高校课堂考勤中心', '13917900002', 'academic@attendance179.local', 1, NOW(), NOW()),
('teacher', '123456', '任课教师', 'TEACHER', '高校课堂考勤中心', '13917900003', 'teacher@attendance179.local', 1, NOW(), NOW()),
('inspector', '123456', '课堂巡查员', 'INSPECTOR', '高校课堂考勤中心', '13917900004', 'inspector@attendance179.local', 1, NOW(), NOW()),
('counselor', '123456', '辅导员', 'COUNSELOR', '高校课堂考勤中心', '13917900005', 'counselor@attendance179.local', 1, NOW(), NOW()),
('student', '123456', '学生', 'STUDENT', '高校课堂考勤中心', '13917900006', 'student@attendance179.local', 1, NOW(), NOW());

CREATE TABLE teaching_class (
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
INSERT INTO teaching_class (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('179-01-001', '教学班级示例一', '课程类型', '教务人员A', '2026-05-16 09:00', 'SIGNED', '班级编号、班级名称、课程类型、教务人员、开课时间和班级状态维护', NOW(), NOW()),
('179-01-002', '教学班级示例二', '课程类型', '教务人员B', '2026-05-17 14:00', 'APPEALING', '教学班级演示数据二', NOW(), NOW());

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
('179-02-001', '学生档案示例一', '学生类型', '辅导员A', '2026-05-16 09:00', 'EXCEPTION_PENDING', '学生学号、学生姓名、学生类型、辅导员、入学时间和学生状态维护', NOW(), NOW()),
('179-02-002', '学生档案示例二', '学生类型', '辅导员B', '2026-05-17 14:00', 'REVIEWING', '学生档案演示数据二', NOW(), NOW());

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
('179-03-001', '教师档案示例一', '教师类型', '教务人员A', '2026-05-16 09:00', 'APPEALING', '教师工号、教师姓名、教师类型、教务人员、任职时间和教师状态维护', NOW(), NOW()),
('179-03-002', '教师档案示例二', '教师类型', '教务人员B', '2026-05-17 14:00', 'INSPECTING', '教师档案演示数据二', NOW(), NOW());

CREATE TABLE course_schedule (
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
INSERT INTO course_schedule (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('179-04-001', '课程排课示例一', '排课类型', '任课教师A', '2026-05-16 09:00', 'REVIEWING', '排课编号、课程名称、排课类型、任课教师、上课时间和排课状态维护', NOW(), NOW()),
('179-04-002', '课程排课示例二', '排课类型', '任课教师B', '2026-05-17 14:00', 'RECTIFYING', '课程排课演示数据二', NOW(), NOW());

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
('179-05-001', '考勤记录示例一', '考勤类型', '任课教师A', '2026-05-16 09:00', 'INSPECTING', '考勤编号、课程班级、考勤类型、任课教师、考勤时间和考勤状态维护', NOW(), NOW()),
('179-05-002', '考勤记录示例二', '考勤类型', '任课教师B', '2026-05-17 14:00', 'FINISHED', '考勤记录演示数据二', NOW(), NOW());

CREATE TABLE exception_appeal (
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
INSERT INTO exception_appeal (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('179-06-001', '异常申诉示例一', '申诉类型', '申诉学生A', '2026-05-16 09:00', 'RECTIFYING', '申诉编号、考勤记录、申诉类型、申诉学生、申诉时间和申诉状态维护', NOW(), NOW()),
('179-06-002', '异常申诉示例二', '申诉类型', '申诉学生B', '2026-05-17 14:00', 'PENDING', '异常申诉演示数据二', NOW(), NOW());

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
('179-07-001', '申诉审核示例一', '审核类型', '审核人员A', '2026-05-16 09:00', 'FINISHED', '审核编号、申诉事项、审核类型、审核人员、审核时间和审核状态维护', NOW(), NOW()),
('179-07-002', '申诉审核示例二', '审核类型', '审核人员B', '2026-05-17 14:00', 'SIGNED', '申诉审核演示数据二', NOW(), NOW());

CREATE TABLE classroom_inspection (
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
INSERT INTO classroom_inspection (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('179-08-001', '课堂巡查示例一', '巡查类型', '巡查人员A', '2026-05-16 09:00', 'PENDING', '巡查编号、巡查课堂、巡查类型、巡查人员、巡查时间和巡查状态维护', NOW(), NOW()),
('179-08-002', '课堂巡查示例二', '巡查类型', '巡查人员B', '2026-05-17 14:00', 'EXCEPTION_PENDING', '课堂巡查演示数据二', NOW(), NOW());

CREATE TABLE inspection_issue (
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
INSERT INTO inspection_issue (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('179-09-001', '巡查问题示例一', '问题类型', '责任人员A', '2026-05-16 09:00', 'SIGNED', '问题编号、巡查事项、问题类型、责任人员、发现时间和问题状态维护', NOW(), NOW()),
('179-09-002', '巡查问题示例二', '问题类型', '责任人员B', '2026-05-17 14:00', 'APPEALING', '巡查问题演示数据二', NOW(), NOW());

CREATE TABLE rectification_task (
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
INSERT INTO rectification_task (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('179-10-001', '整改任务示例一', '整改类型', '责任人员A', '2026-05-16 09:00', 'EXCEPTION_PENDING', '任务编号、问题事项、整改类型、责任人员、整改时间和任务状态维护', NOW(), NOW()),
('179-10-002', '整改任务示例二', '整改类型', '责任人员B', '2026-05-17 14:00', 'REVIEWING', '整改任务演示数据二', NOW(), NOW());

CREATE TABLE rectification_feedback (
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
INSERT INTO rectification_feedback (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('179-11-001', '整改反馈示例一', '反馈类型', '反馈人员A', '2026-05-16 09:00', 'APPEALING', '反馈编号、整改任务、反馈类型、反馈人员、反馈时间和反馈状态维护', NOW(), NOW()),
('179-11-002', '整改反馈示例二', '反馈类型', '反馈人员B', '2026-05-17 14:00', 'INSPECTING', '整改反馈演示数据二', NOW(), NOW());

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
('179-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'REVIEWING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('179-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'RECTIFYING', '操作日志演示数据二', NOW(), NOW());
