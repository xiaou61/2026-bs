DROP DATABASE IF EXISTS learning_path_139;
CREATE DATABASE learning_path_139 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE learning_path_139;

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
('admin', '123456', '系统管理员', 'ADMIN', '组织发展部', '139139000100', 'admin@demo.local', 1, NOW(), NOW()),
('trainer', '123456', '培训讲师', 'TRAINER', '企业大学', '139139000200', 'trainer@demo.local', 1, NOW(), NOW()),
('employee', '123456', '企业员工', 'EMPLOYEE', '客户成功部', '139139000300', 'employee@demo.local', 1, NOW(), NOW()),
('manager', '123456', '部门主管', 'MANAGER', '运营管理部', '139139000400', 'manager@demo.local', 1, NOW(), NOW());

CREATE TABLE training_program (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  program_no VARCHAR(120),
  program_name VARCHAR(120),
  target_role VARCHAR(120),
  organizer_name VARCHAR(120),
  session_count INT,
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO training_program (program_no, program_name, target_role, organizer_name, session_count, status, created_time, updated_time) VALUES
('PRG-139-001', '新员工启航训练营', 'EMPLOYEE', '企业大学', 8, 'ACTIVE', NOW(), NOW()),
('PRG-139-002', '中层管理者领导力提升', 'MANAGER', '组织发展部', 6, 'DRAFT', NOW(), NOW());

CREATE TABLE course_catalog (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  course_no VARCHAR(120),
  course_name VARCHAR(120),
  lecturer_name VARCHAR(120),
  credit_hours INT,
  delivery_mode VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO course_catalog (course_no, course_name, lecturer_name, credit_hours, delivery_mode, status, created_time, updated_time) VALUES
('CRS-139-001', '项目复盘与经验萃取', '周珊', 12, '线上直播', 'ACTIVE', NOW(), NOW()),
('CRS-139-002', '销售顾问实战陪练', '李程', 16, '线下面授', 'DRAFT', NOW(), NOW());

CREATE TABLE learner_profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  learner_no VARCHAR(120),
  learner_name VARCHAR(120),
  department_name VARCHAR(120),
  position_name VARCHAR(120),
  phone_number VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO learner_profile (learner_no, learner_name, department_name, position_name, phone_number, status, created_time, updated_time) VALUES
('LRN-139-001', '王楠', '客户成功部', '客户经理', '139139100101', 'ACTIVE', NOW(), NOW()),
('LRN-139-002', '陈璐', '运营管理部', '招商主管', '139139100102', 'ACTIVE', NOW(), NOW());

CREATE TABLE learning_path_plan (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  path_no VARCHAR(120),
  learner_name VARCHAR(120),
  path_name VARCHAR(120),
  stage_count INT,
  completed_rate INT,
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO learning_path_plan (path_no, learner_name, path_name, stage_count, completed_rate, status, created_time, updated_time) VALUES
('PAT-139-001', '王楠', '客户经理90天成长路径', 4, 50, 'PROCESSING', NOW(), NOW()),
('PAT-139-002', '陈璐', '招商主管晋升预备路径', 5, 20, 'OPEN', NOW(), NOW());

CREATE TABLE study_task (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  task_no VARCHAR(120),
  path_name VARCHAR(120),
  task_name VARCHAR(120),
  deadline_time VARCHAR(120),
  assignee_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO study_task (task_no, path_name, task_name, deadline_time, assignee_name, status, created_time, updated_time) VALUES
('TSK-139-001', '客户经理90天成长路径', '完成客户异议处理课程', '2026-05-20 18:00', '王楠', 'SUBMITTED', NOW(), NOW()),
('TSK-139-002', '招商主管晋升预备路径', '提交招商主管复盘报告', '2026-05-24 18:00', '陈璐', 'DRAFT', NOW(), NOW());

CREATE TABLE course_enrollment (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  enrollment_no VARCHAR(120),
  course_name VARCHAR(120),
  learner_name VARCHAR(120),
  apply_time VARCHAR(120),
  approver_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO course_enrollment (enrollment_no, course_name, learner_name, apply_time, approver_name, status, created_time, updated_time) VALUES
('ENR-139-001', '项目复盘与经验萃取', '王楠', '2026-05-12 09:30', '孙倩', 'APPROVED', NOW(), NOW()),
('ENR-139-002', '销售顾问实战陪练', '陈璐', '2026-05-13 14:20', '赵宁', 'SUBMITTED', NOW(), NOW());

CREATE TABLE assessment_exam (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  exam_no VARCHAR(120),
  exam_name VARCHAR(120),
  course_name VARCHAR(120),
  exam_date VARCHAR(120),
  participant_count INT,
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO assessment_exam (exam_no, exam_name, course_name, exam_date, participant_count, status, created_time, updated_time) VALUES
('EXM-139-001', '复盘方法论结业考核', '项目复盘与经验萃取', '2026-05-26', 24, 'PROCESSING', NOW(), NOW()),
('EXM-139-002', '销售陪练阶段测试', '销售顾问实战陪练', '2026-05-30', 18, 'OPEN', NOW(), NOW());

CREATE TABLE exam_score (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  score_no VARCHAR(120),
  exam_name VARCHAR(120),
  learner_name VARCHAR(120),
  score_value INT,
  evaluator_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO exam_score (score_no, exam_name, learner_name, score_value, evaluator_name, status, created_time, updated_time) VALUES
('SCR-139-001', '复盘方法论结业考核', '王楠', 92, '周珊', 'APPROVED', NOW(), NOW()),
('SCR-139-002', '销售陪练阶段测试', '陈璐', 86, '李程', 'SUBMITTED', NOW(), NOW());

CREATE TABLE skill_tag (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  tag_no VARCHAR(120),
  tag_name VARCHAR(120),
  category_name VARCHAR(120),
  tag_level VARCHAR(120),
  owner_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO skill_tag (tag_no, tag_name, category_name, tag_level, owner_name, status, created_time, updated_time) VALUES
('TAG-139-001', '客户异议处理', '销售能力', '高级', '周珊', 'APPROVED', NOW(), NOW()),
('TAG-139-002', '复盘表达', '管理能力', '中级', '李程', 'SUBMITTED', NOW(), NOW());

CREATE TABLE competency_profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  profile_no VARCHAR(120),
  learner_name VARCHAR(120),
  competency_level VARCHAR(120),
  gap_count INT,
  evaluator_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO competency_profile (profile_no, learner_name, competency_level, gap_count, evaluator_name, status, created_time, updated_time) VALUES
('CPF-139-001', '王楠', '中级', 2, '孙倩', 'PROCESSING', NOW(), NOW()),
('CPF-139-002', '陈璐', '高级', 1, '赵宁', 'OPEN', NOW(), NOW());

CREATE TABLE certification_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  cert_no VARCHAR(120),
  learner_name VARCHAR(120),
  certification_name VARCHAR(120),
  issuer_name VARCHAR(120),
  issue_date VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO certification_record (cert_no, learner_name, certification_name, issuer_name, issue_date, status, created_time, updated_time) VALUES
('CER-139-001', '王楠', '项目复盘内训师认证', '企业大学', '2026-04-30', 'ACTIVE', NOW(), NOW()),
('CER-139-002', '陈璐', '销售顾问带教认证', '组织发展部', '2026-05-08', 'DRAFT', NOW(), NOW());

CREATE TABLE learning_reminder (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  reminder_no VARCHAR(120),
  learner_name VARCHAR(120),
  reminder_type VARCHAR(120),
  remind_time VARCHAR(120),
  channel_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO learning_reminder (reminder_no, learner_name, reminder_type, remind_time, channel_name, status, created_time, updated_time) VALUES
('REM-139-001', '王楠', '课程开课提醒', '2026-05-18 08:30', '企业微信', 'PROCESSING', NOW(), NOW()),
('REM-139-002', '陈璐', '考试待参加提醒', '2026-05-28 09:00', '短信', 'OPEN', NOW(), NOW());

CREATE TABLE operation_log (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  operator_name VARCHAR(120),
  module_name VARCHAR(120),
  action_type VARCHAR(120),
  target_name VARCHAR(120),
  detail_info VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO operation_log (operator_name, module_name, action_type, target_name, detail_info, status, created_time, updated_time) VALUES
('系统管理员', '培训项目', '新增', '新员工启航训练营', '创建培训项目并分配讲师', 'SUCCESS', NOW(), NOW()),
('培训讲师', '成绩记录', '审批', '复盘方法论结业考核', '完成学员成绩确认', 'SUCCESS', NOW(), NOW());






