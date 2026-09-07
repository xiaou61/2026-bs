DROP DATABASE IF EXISTS carbon_accounting_127;
CREATE DATABASE carbon_accounting_127 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE carbon_accounting_127;

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
('admin', '123456', '系统管理员', 'ADMIN', '碳管理中心', '13800012701', 'admin@demo.local', 1, NOW(), NOW()),
('accountant', '123456', '碳核算员', 'ACCOUNTANT', '核算组', '13800012702', 'accountant@demo.local', 1, NOW(), NOW()),
('auditor', '123456', '核查员', 'AUDITOR', '核查组', '13800012703', 'auditor@demo.local', 1, NOW(), NOW()),
('manager', '123456', '减排经理', 'MANAGER', '减排办公室', '13800012704', 'manager@demo.local', 1, NOW(), NOW());

CREATE TABLE company_profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  company_no VARCHAR(120),
  company_name VARCHAR(120),
  industry_name VARCHAR(120),
  region_name VARCHAR(120),
  contact_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO company_profile (company_no, company_name, industry_name, region_name, contact_name, status, created_time, updated_time) VALUES
('COM-127-001', '企业名称一', '所属行业一', '所在区域一', '联系人一', 'ACTIVE', NOW(), NOW()),
('COM-127-002', '企业名称二', '所属行业二', '所在区域二', '联系人二', 'ACTIVE', NOW(), NOW());

CREATE TABLE emission_factor (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  factor_no VARCHAR(120),
  factor_name VARCHAR(120),
  energy_type VARCHAR(120),
  factor_value DECIMAL(12,2),
  unit_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO emission_factor (factor_no, factor_name, energy_type, factor_value, unit_name, status, created_time, updated_time) VALUES
('FAC-127-001', '因子名称一', '能源类型一', 48.1, '单位一', 'ACTIVE', NOW(), NOW()),
('FAC-127-002', '因子名称二', '能源类型二', 83.6, '单位二', 'ACTIVE', NOW(), NOW());

CREATE TABLE accounting_period (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  period_no VARCHAR(120),
  company_no VARCHAR(120),
  period_month VARCHAR(120),
  boundary_scope VARCHAR(120),
  reviewer_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO accounting_period (period_no, company_no, period_month, boundary_scope, reviewer_name, status, created_time, updated_time) VALUES
('PER-127-001', 'PER-127-001', '2026-06', '核算边界一', '复核人一', 'DRAFT', NOW(), NOW()),
('PER-127-002', 'PER-127-002', '2026-07', '核算边界二', '复核人二', 'DRAFT', NOW(), NOW());

CREATE TABLE energy_consumption (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  company_no VARCHAR(120),
  energy_type VARCHAR(120),
  amount_value DECIMAL(12,2),
  unit_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO energy_consumption (record_no, company_no, energy_type, amount_value, unit_name, status, created_time, updated_time) VALUES
('ENE-127-001', 'ENE-127-001', '能源类型一', 48.1, '单位一', 'SUBMITTED', NOW(), NOW()),
('ENE-127-002', 'ENE-127-002', '能源类型二', 83.6, '单位二', 'SUBMITTED', NOW(), NOW());

CREATE TABLE emission_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  emission_no VARCHAR(120),
  company_no VARCHAR(120),
  scope_type VARCHAR(120),
  carbon_amount DECIMAL(12,2),
  source_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO emission_record (emission_no, company_no, scope_type, carbon_amount, source_name, status, created_time, updated_time) VALUES
('EMI-127-001', 'EMI-127-001', '范围类型一', 48.1, '排放来源一', 'REVIEWING', NOW(), NOW()),
('EMI-127-002', 'EMI-127-002', '范围类型二', 83.6, '排放来源二', 'REVIEWING', NOW(), NOW());

CREATE TABLE reduction_task (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  task_no VARCHAR(120),
  company_no VARCHAR(120),
  task_name VARCHAR(120),
  target_amount DECIMAL(12,2),
  owner_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO reduction_task (task_no, company_no, task_name, target_amount, owner_name, status, created_time, updated_time) VALUES
('TSK-127-001', 'TSK-127-001', '任务名称一', 48.1, '责任人一', 'OPEN', NOW(), NOW()),
('TSK-127-002', 'TSK-127-002', '任务名称二', 83.6, '责任人二', 'OPEN', NOW(), NOW());

CREATE TABLE reduction_measure (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  measure_no VARCHAR(120),
  task_no VARCHAR(120),
  measure_name VARCHAR(120),
  investment_amount DECIMAL(12,2),
  expected_reduction DECIMAL(12,2),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO reduction_measure (measure_no, task_no, measure_name, investment_amount, expected_reduction, status, created_time, updated_time) VALUES
('MEA-127-001', 'MEA-127-001', '措施名称一', 48.1, 48.1, 'OPEN', NOW(), NOW()),
('MEA-127-002', 'MEA-127-002', '措施名称二', 83.6, 83.6, 'OPEN', NOW(), NOW());

CREATE TABLE carbon_quota (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  quota_no VARCHAR(120),
  company_no VARCHAR(120),
  quota_year INT,
  quota_amount DECIMAL(12,2),
  used_amount DECIMAL(12,2),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO carbon_quota (quota_no, company_no, quota_year, quota_amount, used_amount, status, created_time, updated_time) VALUES
('QUO-127-001', 'QUO-127-001', 2026, 48.1, 48.1, 'ACTIVE', NOW(), NOW()),
('QUO-127-002', 'QUO-127-002', 2026, 83.6, 83.6, 'ACTIVE', NOW(), NOW());

CREATE TABLE verification_report (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  report_no VARCHAR(120),
  company_no VARCHAR(120),
  report_month VARCHAR(120),
  auditor_name VARCHAR(120),
  conclusion_text VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO verification_report (report_no, company_no, report_month, auditor_name, conclusion_text, status, created_time, updated_time) VALUES
('REP-127-001', 'REP-127-001', '2026-06', '核查员一', '核查结论一', 'DRAFT', NOW(), NOW()),
('REP-127-002', 'REP-127-002', '2026-07', '核查员二', '核查结论二', 'DRAFT', NOW(), NOW());

CREATE TABLE data_attachment (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  attach_no VARCHAR(120),
  company_no VARCHAR(120),
  file_name VARCHAR(120),
  file_type VARCHAR(120),
  uploader_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO data_attachment (attach_no, company_no, file_name, file_type, uploader_name, status, created_time, updated_time) VALUES
('ATT-127-001', 'ATT-127-001', '文件名称一', '文件类型一', '上传人一', 'SUBMITTED', NOW(), NOW()),
('ATT-127-002', 'ATT-127-002', '文件名称二', '文件类型二', '上传人二', 'SUBMITTED', NOW(), NOW());

CREATE TABLE alert_rule (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  rule_no VARCHAR(120),
  rule_name VARCHAR(120),
  metric_name VARCHAR(120),
  threshold_value DECIMAL(12,2),
  alert_level VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO alert_rule (rule_no, rule_name, metric_name, threshold_value, alert_level, status, created_time, updated_time) VALUES
('RUL-127-001', '规则名称一', '指标名称一', 48.1, '高', 'ACTIVE', NOW(), NOW()),
('RUL-127-002', '规则名称二', '指标名称二', 83.6, '中', 'ACTIVE', NOW(), NOW());

CREATE TABLE carbon_warning (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  warning_no VARCHAR(120),
  company_no VARCHAR(120),
  warning_level VARCHAR(120),
  trigger_reason VARCHAR(120),
  handler_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO carbon_warning (warning_no, company_no, warning_level, trigger_reason, handler_name, status, created_time, updated_time) VALUES
('WAR-127-001', 'WAR-127-001', '高', '触发原因一', '处理人一', 'OPEN', NOW(), NOW()),
('WAR-127-002', 'WAR-127-002', '中', '触发原因二', '处理人二', 'OPEN', NOW(), NOW());

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
