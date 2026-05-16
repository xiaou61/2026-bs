DROP DATABASE IF EXISTS graduate_employment_173;
CREATE DATABASE graduate_employment_173 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE graduate_employment_173;

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
('admin', '123456', '系统管理员', 'ADMIN', '高校就业指导中心', '13917300001', 'admin@graduate173.local', 1, NOW(), NOW()),
('career', '123456', '就业中心', 'CAREER', '高校就业指导中心', '13917300002', 'career@graduate173.local', 1, NOW(), NOW()),
('college', '123456', '学院就业老师', 'COLLEGE', '高校就业指导中心', '13917300003', 'college@graduate173.local', 1, NOW(), NOW()),
('counselor', '123456', '辅导员', 'COUNSELOR', '高校就业指导中心', '13917300004', 'counselor@graduate173.local', 1, NOW(), NOW()),
('student', '123456', '毕业生', 'STUDENT', '高校就业指导中心', '13917300005', 'student@graduate173.local', 1, NOW(), NOW()),
('employer', '123456', '用人单位', 'EMPLOYER', '高校就业指导中心', '13917300006', 'employer@graduate173.local', 1, NOW(), NOW());

CREATE TABLE college_major (
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
INSERT INTO college_major (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('173-01-001', '学院专业示例一', '专业方向', '负责老师A', '2026-05-16 09:00', 'DRAFT', '学院编号、学院名称、专业方向、负责老师、启用时间和学院状态维护', NOW(), NOW()),
('173-01-002', '学院专业示例二', '专业方向', '负责老师B', '2026-05-17 14:00', 'PROCESSING', '学院专业演示数据二', NOW(), NOW());

CREATE TABLE graduate_profile (
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
INSERT INTO graduate_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('173-02-001', '毕业生档案示例一', '毕业专业', '辅导员A', '2026-05-16 09:00', 'SUBMITTED', '学生编号、学生姓名、毕业专业、辅导员、建档时间和学生状态维护', NOW(), NOW()),
('173-02-002', '毕业生档案示例二', '毕业专业', '辅导员B', '2026-05-17 14:00', 'ASSISTING', '毕业生档案演示数据二', NOW(), NOW());

CREATE TABLE employer_profile (
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
INSERT INTO employer_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('173-03-001', '用人单位示例一', '行业类型', '联系人A', '2026-05-16 09:00', 'PROCESSING', '单位编号、单位名称、行业类型、联系人、建档时间和单位状态维护', NOW(), NOW()),
('173-03-002', '用人单位示例二', '行业类型', '联系人B', '2026-05-17 14:00', 'SIGNED', '用人单位演示数据二', NOW(), NOW());

CREATE TABLE job_position (
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
INSERT INTO job_position (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('173-04-001', '招聘岗位示例一', '岗位类型', '发布单位A', '2026-05-16 09:00', 'ASSISTING', '岗位编号、岗位名称、岗位类型、发布单位、发布时间和岗位状态维护', NOW(), NOW()),
('173-04-002', '招聘岗位示例二', '岗位类型', '发布单位B', '2026-05-17 14:00', 'WARNING', '招聘岗位演示数据二', NOW(), NOW());

CREATE TABLE destination_report (
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
INSERT INTO destination_report (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('173-05-001', '去向填报示例一', '去向类型', '审核老师A', '2026-05-16 09:00', 'SIGNED', '填报编号、毕业学生、去向类型、审核老师、填报时间和填报状态维护', NOW(), NOW()),
('173-05-002', '去向填报示例二', '去向类型', '审核老师B', '2026-05-17 14:00', 'FINISHED', '去向填报演示数据二', NOW(), NOW());

CREATE TABLE contract_archive (
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
INSERT INTO contract_archive (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('173-06-001', '协议归档示例一', '协议类型', '审核人员A', '2026-05-16 09:00', 'WARNING', '协议编号、毕业学生、协议类型、审核人员、归档时间和协议状态维护', NOW(), NOW()),
('173-06-002', '协议归档示例二', '协议类型', '审核人员B', '2026-05-17 14:00', 'ACTIVE', '协议归档演示数据二', NOW(), NOW());

CREATE TABLE material_review (
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
INSERT INTO material_review (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('173-07-001', '材料审核示例一', '材料类型', '审核人员A', '2026-05-16 09:00', 'FINISHED', '审核编号、审核学生、材料类型、审核人员、审核时间和审核状态维护', NOW(), NOW()),
('173-07-002', '材料审核示例二', '材料类型', '审核人员B', '2026-05-17 14:00', 'DRAFT', '材料审核演示数据二', NOW(), NOW());

CREATE TABLE assistance_record (
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
INSERT INTO assistance_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('173-08-001', '帮扶记录示例一', '帮扶类型', '辅导人员A', '2026-05-16 09:00', 'ACTIVE', '帮扶编号、帮扶学生、帮扶类型、辅导人员、帮扶时间和帮扶状态维护', NOW(), NOW()),
('173-08-002', '帮扶记录示例二', '帮扶类型', '辅导人员B', '2026-05-17 14:00', 'SUBMITTED', '帮扶记录演示数据二', NOW(), NOW());

CREATE TABLE job_recommendation (
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
INSERT INTO job_recommendation (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('173-09-001', '岗位推荐示例一', '推荐类型', '推荐人员A', '2026-05-16 09:00', 'DRAFT', '推荐编号、推荐岗位、推荐类型、推荐人员、推荐时间和推荐状态维护', NOW(), NOW()),
('173-09-002', '岗位推荐示例二', '推荐类型', '推荐人员B', '2026-05-17 14:00', 'PROCESSING', '岗位推荐演示数据二', NOW(), NOW());

CREATE TABLE followup_record (
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
INSERT INTO followup_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('173-10-001', '跟踪回访示例一', '回访类型', '回访人员A', '2026-05-16 09:00', 'SUBMITTED', '回访编号、回访学生、回访类型、回访人员、回访时间和回访状态维护', NOW(), NOW()),
('173-10-002', '跟踪回访示例二', '回访类型', '回访人员B', '2026-05-17 14:00', 'ASSISTING', '跟踪回访演示数据二', NOW(), NOW());

CREATE TABLE employment_statistics (
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
INSERT INTO employment_statistics (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('173-11-001', '就业统计示例一', '统计类型', '统计人员A', '2026-05-16 09:00', 'PROCESSING', '统计编号、统计学院、统计类型、统计人员、统计时间和统计状态维护', NOW(), NOW()),
('173-11-002', '就业统计示例二', '统计类型', '统计人员B', '2026-05-17 14:00', 'SIGNED', '就业统计演示数据二', NOW(), NOW());

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
('173-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'ASSISTING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('173-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'WARNING', '操作日志演示数据二', NOW(), NOW());
