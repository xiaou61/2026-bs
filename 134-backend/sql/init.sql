DROP DATABASE IF EXISTS research_fund_134;
CREATE DATABASE research_fund_134 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE research_fund_134;

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
('admin', '123456', '系统管理员', 'ADMIN', '科研管理处', '139134000100', 'admin@demo.local', 1, NOW(), NOW()),
('researcher', '123456', '科研人员', 'RESEARCHER', '课题组', '139134000200', 'researcher@demo.local', 1, NOW(), NOW()),
('finance', '123456', '财务人员', 'FINANCE', '财务处', '139134000300', 'finance@demo.local', 1, NOW(), NOW()),
('leader', '123456', '审批领导', 'LEADER', '学院办公室', '139134000400', 'leader@demo.local', 1, NOW(), NOW());

CREATE TABLE research_project (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  project_no VARCHAR(120),
  project_name VARCHAR(120),
  leader_name VARCHAR(120),
  college_name VARCHAR(120),
  start_year INT,
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO research_project (project_no, project_name, leader_name, college_name, start_year, status, created_time, updated_time) VALUES
('PRO-134-001', '项目名称一', '负责人一', '所属学院一', 2026, 'ACTIVE', NOW(), NOW()),
('PRO-134-002', '项目名称二', '负责人二', '所属学院二', 2027, 'ACTIVE', NOW(), NOW());

CREATE TABLE budget_category (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  category_no VARCHAR(120),
  category_name VARCHAR(120),
  usage_scope VARCHAR(120),
  control_mode VARCHAR(120),
  manager_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO budget_category (category_no, category_name, usage_scope, control_mode, manager_name, status, created_time, updated_time) VALUES
('CAT-134-001', '科目名称一', '经费用途一', '控制方式一', '负责人一', 'ACTIVE', NOW(), NOW()),
('CAT-134-002', '科目名称二', '经费用途二', '控制方式二', '负责人二', 'ACTIVE', NOW(), NOW());

CREATE TABLE budget_allocation (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  budget_no VARCHAR(120),
  project_no VARCHAR(120),
  category_name VARCHAR(120),
  budget_amount DECIMAL(12,2),
  used_amount DECIMAL(12,2),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO budget_allocation (budget_no, project_no, category_name, budget_amount, used_amount, status, created_time, updated_time) VALUES
('BUD-134-001', 'BUD-134-001', '预算科目一', 38.5, 38.5, 'OPEN', NOW(), NOW()),
('BUD-134-002', 'BUD-134-002', '预算科目二', 51.5, 51.5, 'OPEN', NOW(), NOW());

CREATE TABLE expense_claim (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  claim_no VARCHAR(120),
  project_no VARCHAR(120),
  applicant_name VARCHAR(120),
  claim_amount DECIMAL(12,2),
  claim_time VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO expense_claim (claim_no, project_no, applicant_name, claim_amount, claim_time, status, created_time, updated_time) VALUES
('CLM-134-001', 'CLM-134-001', '申请人一', 38.5, '2026-05-6 10:00', 'SUBMITTED', NOW(), NOW()),
('CLM-134-002', 'CLM-134-002', '申请人二', 51.5, '2026-05-7 10:00', 'SUBMITTED', NOW(), NOW());

CREATE TABLE invoice_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  invoice_no VARCHAR(120),
  claim_no VARCHAR(120),
  invoice_type VARCHAR(120),
  invoice_amount DECIMAL(12,2),
  issuer_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO invoice_record (invoice_no, claim_no, invoice_type, invoice_amount, issuer_name, status, created_time, updated_time) VALUES
('INV-134-001', 'INV-134-001', '发票类型一', 38.5, '开票单位一', 'SUBMITTED', NOW(), NOW()),
('INV-134-002', 'INV-134-002', '发票类型二', 51.5, '开票单位二', 'SUBMITTED', NOW(), NOW());

CREATE TABLE approval_task (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  approval_no VARCHAR(120),
  claim_no VARCHAR(120),
  node_name VARCHAR(120),
  approver_name VARCHAR(120),
  approval_opinion VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO approval_task (approval_no, claim_no, node_name, approver_name, approval_opinion, status, created_time, updated_time) VALUES
('APR-134-001', 'APR-134-001', '审批节点一', '审批人一', '审批意见一', 'REVIEWING', NOW(), NOW()),
('APR-134-002', 'APR-134-002', '审批节点二', '审批人二', '审批意见二', 'REVIEWING', NOW(), NOW());

CREATE TABLE payment_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  payment_no VARCHAR(120),
  claim_no VARCHAR(120),
  payment_amount DECIMAL(12,2),
  payment_time VARCHAR(120),
  operator_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO payment_record (payment_no, claim_no, payment_amount, payment_time, operator_name, status, created_time, updated_time) VALUES
('PAY-134-001', 'PAY-134-001', 38.5, '2026-05-6 10:00', '经办人一', 'FINISHED', NOW(), NOW()),
('PAY-134-002', 'PAY-134-002', 51.5, '2026-05-7 10:00', '经办人二', 'FINISHED', NOW(), NOW());

CREATE TABLE research_achievement (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  achievement_no VARCHAR(120),
  project_no VARCHAR(120),
  achievement_name VARCHAR(120),
  achievement_type VARCHAR(120),
  owner_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO research_achievement (achievement_no, project_no, achievement_name, achievement_type, owner_name, status, created_time, updated_time) VALUES
('ACH-134-001', 'ACH-134-001', '成果名称一', '成果类型一', '完成人一', 'PUBLISHED', NOW(), NOW()),
('ACH-134-002', 'ACH-134-002', '成果名称二', '成果类型二', '完成人二', 'PUBLISHED', NOW(), NOW());

CREATE TABLE paper_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  paper_no VARCHAR(120),
  project_no VARCHAR(120),
  paper_title VARCHAR(120),
  journal_name VARCHAR(120),
  publish_time VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO paper_record (paper_no, project_no, paper_title, journal_name, publish_time, status, created_time, updated_time) VALUES
('PAP-134-001', 'PAP-134-001', '论文题目一', '期刊名称一', '2026-05-6 10:00', 'PUBLISHED', NOW(), NOW()),
('PAP-134-002', 'PAP-134-002', '论文题目二', '期刊名称二', '2026-05-7 10:00', 'PUBLISHED', NOW(), NOW());

CREATE TABLE patent_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  patent_no VARCHAR(120),
  project_no VARCHAR(120),
  patent_name VARCHAR(120),
  applicant_name VARCHAR(120),
  grant_time VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO patent_record (patent_no, project_no, patent_name, applicant_name, grant_time, status, created_time, updated_time) VALUES
('PAT-134-001', 'PAT-134-001', '专利名称一', '申请人一', '2026-05-6 10:00', 'PUBLISHED', NOW(), NOW()),
('PAT-134-002', 'PAT-134-002', '专利名称二', '申请人二', '2026-05-7 10:00', 'PUBLISHED', NOW(), NOW());

CREATE TABLE performance_statistic (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  stat_no VARCHAR(120),
  project_no VARCHAR(120),
  stat_month VARCHAR(120),
  claim_count INT,
  achievement_count INT,
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO performance_statistic (stat_no, project_no, stat_month, claim_count, achievement_count, status, created_time, updated_time) VALUES
('STA-134-001', 'STA-134-001', '2026-06', 35, 35, 'FINISHED', NOW(), NOW()),
('STA-134-002', 'STA-134-002', '2026-07', 40, 40, 'FINISHED', NOW(), NOW());

CREATE TABLE risk_warning (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  warning_no VARCHAR(120),
  project_no VARCHAR(120),
  risk_level VARCHAR(120),
  trigger_reason VARCHAR(120),
  handler_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO risk_warning (warning_no, project_no, risk_level, trigger_reason, handler_name, status, created_time, updated_time) VALUES
('RSK-134-001', 'RSK-134-001', '风险级别一', '触发原因一', '处理人一', 'WARNING', NOW(), NOW()),
('RSK-134-002', 'RSK-134-002', '风险级别二', '触发原因二', '处理人二', 'WARNING', NOW(), NOW());

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
('操作人一', '模块名称一', '动作类型一', '操作对象一', '操作详情一', 'SUCCESS', NOW(), NOW()),
('操作人二', '模块名称二', '动作类型二', '操作对象二', '操作详情二', 'SUCCESS', NOW(), NOW());
