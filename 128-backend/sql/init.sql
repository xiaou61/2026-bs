DROP DATABASE IF EXISTS esg_report_128;
CREATE DATABASE esg_report_128 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE esg_report_128;

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
('admin', '123456', '系统管理员', 'ADMIN', 'ESG管理中心', '13800012801', 'admin@demo.local', 1, NOW(), NOW()),
('filler', '123456', '填报员', 'FILLER', '数据填报组', '13800012802', 'filler@demo.local', 1, NOW(), NOW()),
('reviewer', '123456', '审核员', 'REVIEWER', '审核组', '13800012803', 'reviewer@demo.local', 1, NOW(), NOW()),
('manager', '123456', 'ESG经理', 'MANAGER', '可持续发展部', '13800012804', 'manager@demo.local', 1, NOW(), NOW());

CREATE TABLE indicator_library (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  indicator_no VARCHAR(120),
  indicator_name VARCHAR(120),
  dimension_name VARCHAR(120),
  caliber_text VARCHAR(120),
  weight_value DECIMAL(12,2),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO indicator_library (indicator_no, indicator_name, dimension_name, caliber_text, weight_value, status, created_time, updated_time) VALUES
('IND-128-001', '指标名称一', 'ESG维度一', '填报口径一', 48.1, 'ACTIVE', NOW(), NOW()),
('IND-128-002', '指标名称二', 'ESG维度二', '填报口径二', 83.6, 'ACTIVE', NOW(), NOW());

CREATE TABLE disclosure_template (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  template_no VARCHAR(120),
  template_name VARCHAR(120),
  industry_name VARCHAR(120),
  version_name VARCHAR(120),
  owner_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO disclosure_template (template_no, template_name, industry_name, version_name, owner_name, status, created_time, updated_time) VALUES
('TPL-128-001', '模板名称一', '适用行业一', '版本一', '负责人一', 'ACTIVE', NOW(), NOW()),
('TPL-128-002', '模板名称二', '适用行业二', '版本二', '负责人二', 'ACTIVE', NOW(), NOW());

CREATE TABLE reporting_period (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  period_no VARCHAR(120),
  company_name VARCHAR(120),
  report_month VARCHAR(120),
  template_name VARCHAR(120),
  owner_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO reporting_period (period_no, company_name, report_month, template_name, owner_name, status, created_time, updated_time) VALUES
('PER-128-001', '公司名称一', '2026-06', '模板名称一', '负责人一', 'DRAFT', NOW(), NOW()),
('PER-128-002', '公司名称二', '2026-07', '模板名称二', '负责人二', 'DRAFT', NOW(), NOW());

CREATE TABLE company_submission (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  submission_no VARCHAR(120),
  company_name VARCHAR(120),
  period_month VARCHAR(120),
  filler_name VARCHAR(120),
  submit_time VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO company_submission (submission_no, company_name, period_month, filler_name, submit_time, status, created_time, updated_time) VALUES
('SUB-128-001', '公司名称一', '2026-06', '填报人一', '2026-05-06 10:00', 'SUBMITTED', NOW(), NOW()),
('SUB-128-002', '公司名称二', '2026-07', '填报人二', '2026-05-07 10:00', 'SUBMITTED', NOW(), NOW());

CREATE TABLE indicator_data (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  data_no VARCHAR(120),
  indicator_name VARCHAR(120),
  company_name VARCHAR(120),
  data_value DECIMAL(12,2),
  unit_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO indicator_data (data_no, indicator_name, company_name, data_value, unit_name, status, created_time, updated_time) VALUES
('DAT-128-001', '指标名称一', '公司名称一', 48.1, '单位一', 'SUBMITTED', NOW(), NOW()),
('DAT-128-002', '指标名称二', '公司名称二', 83.6, '单位二', 'SUBMITTED', NOW(), NOW());

CREATE TABLE evidence_file (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  evidence_no VARCHAR(120),
  company_name VARCHAR(120),
  file_name VARCHAR(120),
  file_type VARCHAR(120),
  uploader_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO evidence_file (evidence_no, company_name, file_name, file_type, uploader_name, status, created_time, updated_time) VALUES
('EVD-128-001', '公司名称一', '文件名称一', '文件类型一', '上传人一', 'SUBMITTED', NOW(), NOW()),
('EVD-128-002', '公司名称二', '文件名称二', '文件类型二', '上传人二', 'SUBMITTED', NOW(), NOW());

CREATE TABLE review_task (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  review_no VARCHAR(120),
  submission_no VARCHAR(120),
  reviewer_name VARCHAR(120),
  deadline_time VARCHAR(120),
  review_opinion VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO review_task (review_no, submission_no, reviewer_name, deadline_time, review_opinion, status, created_time, updated_time) VALUES
('REV-128-001', 'REV-128-001', '审核员一', '2026-05-06 10:00', '审核意见一', 'REVIEWING', NOW(), NOW()),
('REV-128-002', 'REV-128-002', '审核员二', '2026-05-07 10:00', '审核意见二', 'REVIEWING', NOW(), NOW());

CREATE TABLE score_model (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  model_no VARCHAR(120),
  model_name VARCHAR(120),
  dimension_name VARCHAR(120),
  score_method VARCHAR(120),
  weight_value DECIMAL(12,2),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO score_model (model_no, model_name, dimension_name, score_method, weight_value, status, created_time, updated_time) VALUES
('MOD-128-001', '模型名称一', 'ESG维度一', '评分方法一', 48.1, 'ACTIVE', NOW(), NOW()),
('MOD-128-002', '模型名称二', 'ESG维度二', '评分方法二', 83.6, 'ACTIVE', NOW(), NOW());

CREATE TABLE esg_score (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  score_no VARCHAR(120),
  company_name VARCHAR(120),
  environment_score DECIMAL(12,2),
  social_score DECIMAL(12,2),
  governance_score DECIMAL(12,2),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO esg_score (score_no, company_name, environment_score, social_score, governance_score, status, created_time, updated_time) VALUES
('SCO-128-001', '公司名称一', 48.1, 48.1, 48.1, 'FINISHED', NOW(), NOW()),
('SCO-128-002', '公司名称二', 83.6, 83.6, 83.6, 'FINISHED', NOW(), NOW());

CREATE TABLE improvement_task (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  task_no VARCHAR(120),
  company_name VARCHAR(120),
  department_name VARCHAR(120),
  improve_item VARCHAR(120),
  deadline_time VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO improvement_task (task_no, company_name, department_name, improve_item, deadline_time, status, created_time, updated_time) VALUES
('IMP-128-001', '公司名称一', '责任部门一', '改进事项一', '2026-05-06 10:00', 'OPEN', NOW(), NOW()),
('IMP-128-002', '公司名称二', '责任部门二', '改进事项二', '2026-05-07 10:00', 'OPEN', NOW(), NOW());

CREATE TABLE stakeholder_feedback (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  feedback_no VARCHAR(120),
  stakeholder_name VARCHAR(120),
  feedback_type VARCHAR(120),
  content_text VARCHAR(120),
  handler_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO stakeholder_feedback (feedback_no, stakeholder_name, feedback_type, content_text, handler_name, status, created_time, updated_time) VALUES
('FDB-128-001', '相关方一', '反馈类型一', '反馈内容一', '处理人一', 'OPEN', NOW(), NOW()),
('FDB-128-002', '相关方二', '反馈类型二', '反馈内容二', '处理人二', 'OPEN', NOW(), NOW());

CREATE TABLE report_export (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  export_no VARCHAR(120),
  company_name VARCHAR(120),
  report_month VARCHAR(120),
  format_type VARCHAR(120),
  operator_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO report_export (export_no, company_name, report_month, format_type, operator_name, status, created_time, updated_time) VALUES
('EXP-128-001', '公司名称一', '2026-06', '导出格式一', '操作人一', 'DRAFT', NOW(), NOW()),
('EXP-128-002', '公司名称二', '2026-07', '导出格式二', '操作人二', 'DRAFT', NOW(), NOW());

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
