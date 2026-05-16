DROP DATABASE IF EXISTS clinical_rotation_163;
CREATE DATABASE clinical_rotation_163 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE clinical_rotation_163;

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
('admin', '123456', '系统管理员', 'ADMIN', '医学实践教学中心', '13916300001', 'admin@clinical163.local', 1, NOW(), NOW()),
('secretary', '123456', '教学秘书', 'SECRETARY', '医学实践教学中心', '13916300002', 'secretary@clinical163.local', 1, NOW(), NOW()),
('teacher', '123456', '带教老师', 'TEACHER', '医学实践教学中心', '13916300003', 'teacher@clinical163.local', 1, NOW(), NOW()),
('student', '123456', '实习学生', 'STUDENT', '医学实践教学中心', '13916300004', 'student@clinical163.local', 1, NOW(), NOW()),
('examiner', '123456', '考核评委', 'EXAMINER', '医学实践教学中心', '13916300005', 'examiner@clinical163.local', 1, NOW(), NOW()),
('director', '123456', '科室主任', 'DIRECTOR', '医学实践教学中心', '13916300006', 'director@clinical163.local', 1, NOW(), NOW());

CREATE TABLE department_profile (
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
INSERT INTO department_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('163-01-001', '轮转科室示例一', '专业方向', '科室主任A', '2026-05-16 09:00', 'BOOKED', '科室编号、科室名称、专业方向、科室主任、开放时间和科室状态维护', NOW(), NOW()),
('163-01-002', '轮转科室示例二', '专业方向', '科室主任B', '2026-05-17 14:00', 'VERIFIED', '轮转科室演示数据二', NOW(), NOW());

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
('163-02-001', '实习学生示例一', '专业年级', '导师A', '2026-05-16 09:00', 'SCHEDULED', '学号、学生姓名、专业年级、导师、入院时间和实习状态维护', NOW(), NOW()),
('163-02-002', '实习学生示例二', '专业年级', '导师B', '2026-05-17 14:00', 'PROCESSING', '实习学生演示数据二', NOW(), NOW());

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
('163-03-001', '带教老师示例一', '带教科室', '职称A', '2026-05-16 09:00', 'VERIFIED', '工号、老师姓名、带教科室、职称、带教时间和在岗状态维护', NOW(), NOW()),
('163-03-002', '带教老师示例二', '带教科室', '职称B', '2026-05-17 14:00', 'FINISHED', '带教老师演示数据二', NOW(), NOW());

CREATE TABLE rotation_plan (
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
INSERT INTO rotation_plan (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('163-04-001', '轮转安排示例一', '轮转科室', '带教老师A', '2026-05-16 09:00', 'PROCESSING', '轮转编号、轮转学生、轮转科室、带教老师、开始时间和轮转状态维护', NOW(), NOW()),
('163-04-002', '轮转安排示例二', '轮转科室', '带教老师B', '2026-05-17 14:00', 'WARNING', '轮转安排演示数据二', NOW(), NOW());

CREATE TABLE case_library (
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
INSERT INTO case_library (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('163-05-001', '病例学习示例一', '病例类型', '带教老师A', '2026-05-16 09:00', 'FINISHED', '病例编号、病例名称、病例类型、带教老师、发布时间和学习状态维护', NOW(), NOW()),
('163-05-002', '病例学习示例二', '病例类型', '带教老师B', '2026-05-17 14:00', 'PUBLISHED', '病例学习演示数据二', NOW(), NOW());

CREATE TABLE study_record (
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
INSERT INTO study_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('163-06-001', '学习记录示例一', '学习类型', '实习学生A', '2026-05-16 09:00', 'WARNING', '记录编号、学习病例、学习类型、实习学生、学习时间和完成状态维护', NOW(), NOW()),
('163-06-002', '学习记录示例二', '学习类型', '实习学生B', '2026-05-17 14:00', 'ACTIVE', '学习记录演示数据二', NOW(), NOW());

CREATE TABLE teaching_round (
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
INSERT INTO teaching_round (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('163-07-001', '教学查房示例一', '查房类型', '主持老师A', '2026-05-16 09:00', 'PUBLISHED', '查房编号、查房主题、查房类型、主持老师、查房时间和查房状态维护', NOW(), NOW()),
('163-07-002', '教学查房示例二', '查房类型', '主持老师B', '2026-05-17 14:00', 'BOOKED', '教学查房演示数据二', NOW(), NOW());

CREATE TABLE skill_training (
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
INSERT INTO skill_training (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('163-08-001', '技能训练示例一', '训练类型', '指导老师A', '2026-05-16 09:00', 'ACTIVE', '训练编号、训练项目、训练类型、指导老师、训练时间和训练状态维护', NOW(), NOW()),
('163-08-002', '技能训练示例二', '训练类型', '指导老师B', '2026-05-17 14:00', 'SCHEDULED', '技能训练演示数据二', NOW(), NOW());

CREATE TABLE teaching_score (
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
INSERT INTO teaching_score (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('163-09-001', '带教评分示例一', '评分维度', '评分人A', '2026-05-16 09:00', 'BOOKED', '评分编号、评分对象、评分维度、评分人、评分时间和评分状态维护', NOW(), NOW()),
('163-09-002', '带教评分示例二', '评分维度', '评分人B', '2026-05-17 14:00', 'VERIFIED', '带教评分演示数据二', NOW(), NOW());

CREATE TABLE exit_exam (
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
INSERT INTO exit_exam (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('163-10-001', '出科考核示例一', '考核类型', '考核评委A', '2026-05-16 09:00', 'SCHEDULED', '考核编号、考核学生、考核类型、考核评委、考核时间和考核状态维护', NOW(), NOW()),
('163-10-002', '出科考核示例二', '考核类型', '考核评委B', '2026-05-17 14:00', 'PROCESSING', '出科考核演示数据二', NOW(), NOW());

CREATE TABLE feedback_review (
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
INSERT INTO feedback_review (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('163-11-001', '反馈整改示例一', '反馈类型', '负责人A', '2026-05-16 09:00', 'VERIFIED', '反馈编号、反馈对象、反馈类型、负责人、反馈时间和整改状态维护', NOW(), NOW()),
('163-11-002', '反馈整改示例二', '反馈类型', '负责人B', '2026-05-17 14:00', 'FINISHED', '反馈整改演示数据二', NOW(), NOW());

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
('163-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'PROCESSING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('163-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'WARNING', '操作日志演示数据二', NOW(), NOW());
