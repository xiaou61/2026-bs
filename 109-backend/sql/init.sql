DROP DATABASE IF EXISTS data_masking_109;
CREATE DATABASE data_masking_109 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE data_masking_109;

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

CREATE TABLE data_source_config (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  source_name VARCHAR(100) NOT NULL,
  source_code VARCHAR(80) NOT NULL,
  source_type VARCHAR(40),
  host_address VARCHAR(120),
  database_name VARCHAR(80),
  owner_name VARCHAR(50),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE data_set_catalog (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  dataset_name VARCHAR(100) NOT NULL,
  dataset_code VARCHAR(80) NOT NULL,
  source_name VARCHAR(100),
  table_name VARCHAR(100),
  business_domain VARCHAR(80),
  security_level VARCHAR(30),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE sensitive_rule (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  rule_name VARCHAR(100) NOT NULL,
  rule_type VARCHAR(50),
  match_pattern VARCHAR(200),
  risk_level VARCHAR(30),
  masking_type VARCHAR(50),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE recognition_task (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  task_no VARCHAR(80) NOT NULL,
  dataset_name VARCHAR(100),
  rule_scope VARCHAR(100),
  scan_mode VARCHAR(40),
  owner_name VARCHAR(50),
  started_time DATETIME,
  status VARCHAR(30) DEFAULT 'DRAFT',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE recognition_result (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  result_no VARCHAR(80) NOT NULL,
  task_no VARCHAR(80),
  field_name VARCHAR(100),
  sensitive_type VARCHAR(50),
  sample_value VARCHAR(120),
  confidence DECIMAL(6,2) DEFAULT 0,
  risk_level VARCHAR(30),
  status VARCHAR(30) DEFAULT 'PENDING',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE masking_strategy (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  strategy_name VARCHAR(100) NOT NULL,
  sensitive_type VARCHAR(50),
  masking_method VARCHAR(50),
  masking_expression VARCHAR(120),
  review_role VARCHAR(50),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE masking_task (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  task_no VARCHAR(80) NOT NULL,
  dataset_name VARCHAR(100),
  strategy_name VARCHAR(100),
  execute_mode VARCHAR(40),
  owner_name VARCHAR(50),
  started_time DATETIME,
  status VARCHAR(30) DEFAULT 'DRAFT',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE masking_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(80) NOT NULL,
  task_no VARCHAR(80),
  field_name VARCHAR(100),
  before_sample VARCHAR(120),
  after_sample VARCHAR(120),
  processed_count INT DEFAULT 0,
  status VARCHAR(30) DEFAULT 'SUCCESS',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE field_lineage (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  field_name VARCHAR(100) NOT NULL,
  dataset_name VARCHAR(100),
  source_path VARCHAR(160),
  target_path VARCHAR(160),
  owner_name VARCHAR(50),
  security_level VARCHAR(30),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE access_request (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  request_no VARCHAR(80) NOT NULL,
  requester_name VARCHAR(50),
  dataset_name VARCHAR(100),
  purpose_text VARCHAR(200),
  expire_time DATETIME,
  status VARCHAR(30) DEFAULT 'DRAFT',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE export_approval (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  approval_no VARCHAR(80) NOT NULL,
  dataset_name VARCHAR(100),
  export_type VARCHAR(40),
  applicant_name VARCHAR(50),
  file_level VARCHAR(30),
  status VARCHAR(30) DEFAULT 'DRAFT',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE risk_alert (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  alert_no VARCHAR(80) NOT NULL,
  dataset_name VARCHAR(100),
  alert_type VARCHAR(50),
  risk_level VARCHAR(30),
  owner_name VARCHAR(50),
  detected_time DATETIME,
  status VARCHAR(30) DEFAULT 'OPEN',
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
('admin', '123456', '系统管理员', 'ADMIN', '平台管理组', '13800000901', 'admin@masking.com', 1, NOW(), NOW()),
('security', '123456', '安全工程师', 'SECURITY', '数据安全组', '13800000902', 'security@masking.com', 1, NOW(), NOW()),
('owner', '123456', '数据负责人', 'DATA_OWNER', '数据治理组', '13800000903', 'owner@masking.com', 1, NOW(), NOW()),
('auditor', '123456', '审计员', 'AUDITOR', '内控审计组', '13800000904', 'auditor@masking.com', 1, NOW(), NOW());

INSERT INTO data_source_config (source_name, source_code, source_type, host_address, database_name, owner_name, status, created_time, updated_time) VALUES
('客户生产库', 'SRC-CUSTOMER-PROD', 'MYSQL', '127.0.0.1:3306', 'customer_core', '赵数据', 'ACTIVE', NOW(), NOW()),
('营销CSV文件', 'SRC-MKT-CSV', 'CSV', 'file-server/mkt', 'market_file', '钱营销', 'PENDING', NOW(), NOW());

INSERT INTO data_set_catalog (dataset_name, dataset_code, source_name, table_name, business_domain, security_level, status, created_time, updated_time) VALUES
('客户档案表', 'DS-CUSTOMER', '客户生产库', 'customer_profile', '客户中心', 'L3', 'ACTIVE', NOW(), NOW()),
('营销线索表', 'DS-LEADS', '营销CSV文件', 'market_leads', '营销中心', 'L2', 'DRAFT', NOW(), NOW());

INSERT INTO sensitive_rule (rule_name, rule_type, match_pattern, risk_level, masking_type, status, created_time, updated_time) VALUES
('身份证识别规则', 'ID_CARD', '身份证号码', 'HIGH', 'MASK', 'ACTIVE', NOW(), NOW()),
('手机号识别规则', 'PHONE', '手机号', 'MEDIUM', 'MASK', 'ACTIVE', NOW(), NOW());

INSERT INTO recognition_task (task_no, dataset_name, rule_scope, scan_mode, owner_name, started_time, status, created_time, updated_time) VALUES
('REC-202605-001', '客户档案表', '身份证+手机号', 'FULL', '赵数据', '2026-05-01 09:00:00', 'FINISHED', NOW(), NOW()),
('REC-202605-002', '营销线索表', '邮箱+手机号', 'SAMPLE', '孙安全', '2026-05-02 10:00:00', 'DRAFT', NOW(), NOW());

INSERT INTO recognition_result (result_no, task_no, field_name, sensitive_type, sample_value, confidence, risk_level, status, created_time, updated_time) VALUES
('RES-001', 'REC-202605-001', 'id_card', 'ID_CARD', '110101********1234', 96.5, 'HIGH', 'CONFIRMED', NOW(), NOW()),
('RES-002', 'REC-202605-002', 'mobile', 'PHONE', '138****0000', 92.0, 'MEDIUM', 'PENDING', NOW(), NOW());

INSERT INTO masking_strategy (strategy_name, sensitive_type, masking_method, masking_expression, review_role, status, created_time, updated_time) VALUES
('身份证星号策略', 'ID_CARD', 'MASK', '保留前6后4', 'AUDITOR', 'ACTIVE', NOW(), NOW()),
('手机号保留前三后四', 'PHONE', 'MASK', '保留前3后4', 'SECURITY', 'ACTIVE', NOW(), NOW());

INSERT INTO masking_task (task_no, dataset_name, strategy_name, execute_mode, owner_name, started_time, status, created_time, updated_time) VALUES
('MASK-202605-001', '客户档案表', '身份证星号策略', 'MANUAL', '赵数据', '2026-05-03 09:00:00', 'SUBMITTED', NOW(), NOW()),
('MASK-202605-002', '营销线索表', '手机号保留前三后四', 'SCHEDULED', '孙安全', '2026-05-04 09:30:00', 'DRAFT', NOW(), NOW());

INSERT INTO masking_record (record_no, task_no, field_name, before_sample, after_sample, processed_count, status, created_time, updated_time) VALUES
('MR-001', 'MASK-202605-001', 'id_card', '110101199001011234', '110101********1234', 12000, 'SUCCESS', NOW(), NOW()),
('MR-002', 'MASK-202605-002', 'mobile', '13800000000', '138****0000', 6800, 'SUCCESS', NOW(), NOW());

INSERT INTO field_lineage (field_name, dataset_name, source_path, target_path, owner_name, security_level, status, created_time, updated_time) VALUES
('id_card', '客户档案表', 'customer_core.customer_profile.id_card', 'ods.customer_profile.id_card', '赵数据', 'L3', 'ACTIVE', NOW(), NOW()),
('mobile', '营销线索表', 'market_file.market_leads.mobile', 'dw.market_leads.mobile', '钱营销', 'L2', 'ACTIVE', NOW(), NOW());

INSERT INTO access_request (request_no, requester_name, dataset_name, purpose_text, expire_time, status, created_time, updated_time) VALUES
('AR-202605-001', '李分析', '客户档案表', '客户风险排查', '2026-06-01 23:59:59', 'APPROVED', NOW(), NOW()),
('AR-202605-002', '王运营', '营销线索表', '活动效果分析', '2026-05-20 23:59:59', 'SUBMITTED', NOW(), NOW());

INSERT INTO export_approval (approval_no, dataset_name, export_type, applicant_name, file_level, status, created_time, updated_time) VALUES
('EXP-202605-001', '客户档案表', 'MASKED', '李分析', 'L3', 'APPROVED', NOW(), NOW()),
('EXP-202605-002', '营销线索表', 'SUMMARY', '王运营', 'L2', 'DRAFT', NOW(), NOW());

INSERT INTO risk_alert (alert_no, dataset_name, alert_type, risk_level, owner_name, detected_time, status, created_time, updated_time) VALUES
('RA-202605-001', '客户档案表', 'RAW_EXPORT', 'HIGH', '孙安全', '2026-05-04 11:20:00', 'OPEN', NOW(), NOW()),
('RA-202605-002', '营销线索表', 'HIGH_RISK_FIELD', 'CRITICAL', '赵数据', '2026-05-04 15:40:00', 'ACKED', NOW(), NOW());

INSERT INTO operation_log (operator_name, module_name, action_type, target_name, detail, ip_address, status, created_time, updated_time) VALUES
('admin', '识别任务', '启动任务', 'REC-202605-001', '启动客户档案敏感识别任务', '127.0.0.1', 'SUCCESS', NOW(), NOW()),
('security', '脱敏策略', '启用策略', '身份证星号策略', '启用身份证星号脱敏策略', '127.0.0.1', 'SUCCESS', NOW(), NOW());
