DROP DATABASE IF EXISTS phishing_training_111;
CREATE DATABASE phishing_training_111 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE phishing_training_111;

CREATE TABLE sys_user (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(80) NOT NULL,
  nickname VARCHAR(50) NOT NULL,
  role VARCHAR(40) NOT NULL,
  department VARCHAR(80),
  phone VARCHAR(30),
  email VARCHAR(80),
  status TINYINT DEFAULT 1,
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE employee_profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  employee_name VARCHAR(80) NOT NULL,
  employee_no VARCHAR(60) NOT NULL,
  email VARCHAR(100),
  department_name VARCHAR(80),
  position_name VARCHAR(80),
  risk_level VARCHAR(30),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE department_group (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  group_name VARCHAR(80) NOT NULL,
  group_code VARCHAR(60) NOT NULL,
  business_line VARCHAR(80),
  leader_name VARCHAR(50),
  employee_count INT DEFAULT 0,
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE mail_template (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  template_name VARCHAR(100) NOT NULL,
  subject_text VARCHAR(160),
  sender_name VARCHAR(80),
  bait_type VARCHAR(40),
  landing_url VARCHAR(160),
  status VARCHAR(30) DEFAULT 'DRAFT',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE phishing_campaign (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  campaign_name VARCHAR(100) NOT NULL,
  campaign_no VARCHAR(80) NOT NULL,
  template_name VARCHAR(100),
  target_scope VARCHAR(160),
  owner_name VARCHAR(50),
  planned_time DATETIME,
  status VARCHAR(30) DEFAULT 'DRAFT',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE campaign_target (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  campaign_name VARCHAR(100) NOT NULL,
  employee_name VARCHAR(80),
  email VARCHAR(100),
  department_name VARCHAR(80),
  risk_tag VARCHAR(40),
  status VARCHAR(30) DEFAULT 'WAIT_SEND',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE mail_send_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(80) NOT NULL,
  campaign_name VARCHAR(100),
  employee_name VARCHAR(80),
  email VARCHAR(100),
  send_time DATETIME,
  status VARCHAR(30) DEFAULT 'WAIT_SEND',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE click_tracking (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  track_no VARCHAR(80) NOT NULL,
  campaign_name VARCHAR(100),
  employee_name VARCHAR(80),
  client_ip VARCHAR(50),
  submit_credential VARCHAR(10),
  click_time DATETIME,
  status VARCHAR(30) DEFAULT 'PENDING',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE training_course (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  course_name VARCHAR(100) NOT NULL,
  course_code VARCHAR(80) NOT NULL,
  category_name VARCHAR(80),
  teacher_name VARCHAR(50),
  lesson_minutes INT DEFAULT 0,
  status VARCHAR(30) DEFAULT 'DRAFT',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE training_exam (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  exam_name VARCHAR(100) NOT NULL,
  exam_code VARCHAR(80) NOT NULL,
  course_name VARCHAR(100),
  pass_score INT DEFAULT 60,
  owner_name VARCHAR(50),
  status VARCHAR(30) DEFAULT 'DRAFT',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE exam_question (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  question_title VARCHAR(200) NOT NULL,
  exam_name VARCHAR(100),
  question_type VARCHAR(40),
  answer_key VARCHAR(80),
  score_value INT DEFAULT 0,
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE exam_attempt (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  attempt_no VARCHAR(80) NOT NULL,
  employee_name VARCHAR(80),
  exam_name VARCHAR(100),
  score_value INT DEFAULT 0,
  result_status VARCHAR(40),
  submit_time DATETIME,
  status VARCHAR(30) DEFAULT 'SUBMITTED',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE risk_score (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  employee_name VARCHAR(80) NOT NULL,
  department_name VARCHAR(80),
  click_count INT DEFAULT 0,
  exam_score INT DEFAULT 0,
  risk_score INT DEFAULT 0,
  risk_level VARCHAR(30),
  reviewer_name VARCHAR(50),
  status VARCHAR(30) DEFAULT 'WAIT_REVIEW',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE operation_log (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  operator_name VARCHAR(50) NOT NULL,
  module_name VARCHAR(80),
  action_type VARCHAR(50),
  target_name VARCHAR(120),
  detail TEXT,
  ip_address VARCHAR(50),
  status VARCHAR(30) DEFAULT 'SUCCESS',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO sys_user (username, password, nickname, role, department, phone, email, status, created_time, updated_time) VALUES
('admin', '123456', '系统管理员', 'ADMIN', '平台管理组', '13800001101', 'admin@phishing.com', 1, NOW(), NOW()),
('security', '123456', '安全专员', 'SECURITY', '安全运营组', '13800001102', 'security@phishing.com', 1, NOW(), NOW()),
('trainer', '123456', '培训讲师', 'TRAINER', '培训组', '13800001103', 'trainer@phishing.com', 1, NOW(), NOW()),
('auditor', '123456', '审计员', 'AUDITOR', '审计组', '13800001104', 'auditor@phishing.com', 1, NOW(), NOW());

INSERT INTO employee_profile (employee_name, employee_no, email, department_name, position_name, risk_level, status, created_time, updated_time) VALUES
('张员工', 'EMP001', 'zhang@corp.com', '研发中心', '后端工程师', 'MEDIUM', 'ACTIVE', NOW(), NOW()),
('李员工', 'EMP002', 'li@corp.com', '财务部', '会计', 'HIGH', 'ACTIVE', NOW(), NOW());

INSERT INTO department_group (group_name, group_code, business_line, leader_name, employee_count, status, created_time, updated_time) VALUES
('研发中心', 'DEPT-RD', '技术平台', '王经理', 120, 'ACTIVE', NOW(), NOW()),
('财务部', 'DEPT-FIN', '财务共享', '赵经理', 36, 'ACTIVE', NOW(), NOW());

INSERT INTO mail_template (template_name, subject_text, sender_name, bait_type, landing_url, status, created_time, updated_time) VALUES
('账号异常验证模板', '账号存在异常请立即验证', 'IT服务台', 'ACCOUNT', 'https://safe-demo/login', 'PUBLISHED', NOW(), NOW()),
('工资条查看模板', '本月工资条待查看', '人力资源部', 'PAYROLL', 'https://safe-demo/payroll', 'DRAFT', NOW(), NOW());

INSERT INTO phishing_campaign (campaign_name, campaign_no, template_name, target_scope, owner_name, planned_time, status, created_time, updated_time) VALUES
('研发中心账号验证演练', 'CAM-202605-001', '账号异常验证模板', '研发中心全员', '安全专员', '2026-05-06 09:00:00', 'RUNNING', NOW(), NOW()),
('财务工资条演练', 'CAM-202605-002', '工资条查看模板', '财务部全员', '培训讲师', '2026-05-07 10:00:00', 'DRAFT', NOW(), NOW());

INSERT INTO campaign_target (campaign_name, employee_name, email, department_name, risk_tag, status, created_time, updated_time) VALUES
('研发中心账号验证演练', '张员工', 'zhang@corp.com', '研发中心', 'MEDIUM', 'SENT', NOW(), NOW()),
('财务工资条演练', '李员工', 'li@corp.com', '财务部', 'HIGH', 'WAIT_SEND', NOW(), NOW());

INSERT INTO mail_send_record (record_no, campaign_name, employee_name, email, send_time, status, created_time, updated_time) VALUES
('SEND-001', '研发中心账号验证演练', '张员工', 'zhang@corp.com', '2026-05-06 09:10:00', 'SENT', NOW(), NOW()),
('SEND-002', '财务工资条演练', '李员工', 'li@corp.com', '2026-05-07 10:15:00', 'WAIT_SEND', NOW(), NOW());

INSERT INTO click_tracking (track_no, campaign_name, employee_name, client_ip, submit_credential, click_time, status, created_time, updated_time) VALUES
('CLK-001', '研发中心账号验证演练', '张员工', '10.0.1.12', 'NO', '2026-05-06 09:20:00', 'CONFIRMED', NOW(), NOW()),
('CLK-002', '财务工资条演练', '李员工', '10.0.2.18', 'YES', '2026-05-07 10:30:00', 'PENDING', NOW(), NOW());

INSERT INTO training_course (course_name, course_code, category_name, teacher_name, lesson_minutes, status, created_time, updated_time) VALUES
('钓鱼邮件识别基础', 'COURSE-PHISH-01', '安全意识', '培训讲师', 45, 'PUBLISHED', NOW(), NOW()),
('凭据保护专题', 'COURSE-CRED-02', '账号安全', '安全专员', 60, 'DRAFT', NOW(), NOW());

INSERT INTO training_exam (exam_name, exam_code, course_name, pass_score, owner_name, status, created_time, updated_time) VALUES
('钓鱼邮件识别测验', 'EXAM-PHISH-01', '钓鱼邮件识别基础', 80, '培训讲师', 'PUBLISHED', NOW(), NOW()),
('凭据保护考试', 'EXAM-CRED-02', '凭据保护专题', 85, '安全专员', 'DRAFT', NOW(), NOW());

INSERT INTO exam_question (question_title, exam_name, question_type, answer_key, score_value, status, created_time, updated_time) VALUES
('收到陌生链接邮件时应该怎么做', '钓鱼邮件识别测验', 'SINGLE', '提交安全团队核验', 10, 'ACTIVE', NOW(), NOW()),
('要求输入密码的邮件应如何处理', '凭据保护考试', 'JUDGE', '不输入', 10, 'ACTIVE', NOW(), NOW());

INSERT INTO exam_attempt (attempt_no, employee_name, exam_name, score_value, result_status, submit_time, status, created_time, updated_time) VALUES
('ATT-001', '张员工', '钓鱼邮件识别测验', 86, 'PASSED', '2026-05-08 14:00:00', 'PASSED', NOW(), NOW()),
('ATT-002', '李员工', '凭据保护考试', 62, 'FAILED', '2026-05-08 15:00:00', 'FAILED', NOW(), NOW());

INSERT INTO risk_score (employee_name, department_name, click_count, exam_score, risk_score, risk_level, reviewer_name, status, created_time, updated_time) VALUES
('张员工', '研发中心', 1, 86, 45, 'MEDIUM', '安全专员', 'WAIT_REVIEW', NOW(), NOW()),
('李员工', '财务部', 2, 62, 78, 'HIGH', '审计员', 'REVIEWED', NOW(), NOW());

INSERT INTO operation_log (operator_name, module_name, action_type, target_name, detail, ip_address, status, created_time, updated_time) VALUES
('admin', '演练活动', '启动演练', 'CAM-202605-001', '启动研发中心账号验证演练', '127.0.0.1', 'SUCCESS', NOW(), NOW()),
('security', '培训考试', '发布考试', 'EXAM-PHISH-01', '发布钓鱼邮件识别测验', '127.0.0.1', 'SUCCESS', NOW(), NOW());
