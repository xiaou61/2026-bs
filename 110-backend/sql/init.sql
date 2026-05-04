DROP DATABASE IF EXISTS privacy_auth_110;
CREATE DATABASE privacy_auth_110 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE privacy_auth_110;

CREATE TABLE sys_user (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(80) NOT NULL,
  nickname VARCHAR(50) NOT NULL,
  role VARCHAR(50) NOT NULL,
  department VARCHAR(80),
  phone VARCHAR(30),
  email VARCHAR(80),
  status TINYINT DEFAULT 1,
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE data_subject (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  subject_name VARCHAR(80) NOT NULL,
  subject_code VARCHAR(80) NOT NULL,
  identity_type VARCHAR(40),
  phone VARCHAR(30),
  email VARCHAR(80),
  region_name VARCHAR(80),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE personal_data_item (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  item_name VARCHAR(100) NOT NULL,
  item_code VARCHAR(80) NOT NULL,
  category_name VARCHAR(80),
  sensitive_level VARCHAR(30),
  retention_days INT DEFAULT 0,
  owner_name VARCHAR(50),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE consent_purpose (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  purpose_name VARCHAR(100) NOT NULL,
  purpose_code VARCHAR(80) NOT NULL,
  business_scene VARCHAR(120),
  purpose_text VARCHAR(240),
  valid_days INT DEFAULT 0,
  owner_name VARCHAR(50),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE consent_policy (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  policy_name VARCHAR(100) NOT NULL,
  policy_code VARCHAR(80) NOT NULL,
  purpose_name VARCHAR(100),
  data_scope VARCHAR(200),
  policy_version VARCHAR(30),
  status VARCHAR(30) DEFAULT 'DRAFT',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE authorization_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  auth_no VARCHAR(80) NOT NULL,
  subject_name VARCHAR(80),
  purpose_name VARCHAR(100),
  channel_name VARCHAR(40),
  grant_time DATETIME,
  expire_time DATETIME,
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE authorization_scope (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  auth_no VARCHAR(80) NOT NULL,
  item_name VARCHAR(100),
  scope_type VARCHAR(40),
  required_flag VARCHAR(10),
  description_text VARCHAR(200),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE access_application (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  application_no VARCHAR(80) NOT NULL,
  applicant_name VARCHAR(50),
  subject_name VARCHAR(80),
  purpose_name VARCHAR(100),
  reason_text VARCHAR(240),
  status VARCHAR(30) DEFAULT 'DRAFT',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE access_grant (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  grant_no VARCHAR(80) NOT NULL,
  application_no VARCHAR(80),
  grantee_name VARCHAR(50),
  data_scope VARCHAR(200),
  expire_time DATETIME,
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE access_log (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  log_no VARCHAR(80) NOT NULL,
  operator_name VARCHAR(50),
  subject_name VARCHAR(80),
  item_name VARCHAR(100),
  access_result VARCHAR(30),
  access_time DATETIME,
  status VARCHAR(30) DEFAULT 'NORMAL',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE revocation_request (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  request_no VARCHAR(80) NOT NULL,
  subject_name VARCHAR(80),
  auth_no VARCHAR(80),
  reason_text VARCHAR(240),
  request_time DATETIME,
  status VARCHAR(30) DEFAULT 'SUBMITTED',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE risk_warning (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  warning_no VARCHAR(80) NOT NULL,
  warning_type VARCHAR(50),
  subject_name VARCHAR(80),
  risk_level VARCHAR(30),
  owner_name VARCHAR(50),
  detected_time DATETIME,
  status VARCHAR(30) DEFAULT 'OPEN',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE audit_report (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  report_no VARCHAR(80) NOT NULL,
  report_name VARCHAR(120),
  report_period VARCHAR(30),
  generated_by VARCHAR(50),
  summary_text TEXT,
  status VARCHAR(30) DEFAULT 'DRAFT',
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
('admin', '123456', '系统管理员', 'ADMIN', '平台管理组', '13800001001', 'admin@privacy.com', 1, NOW(), NOW()),
('privacy', '123456', '隐私官', 'PRIVACY_OFFICER', '隐私合规组', '13800001002', 'privacy@privacy.com', 1, NOW(), NOW()),
('datauser', '123456', '数据使用人', 'DATA_USER', '业务使用组', '13800001003', 'datauser@privacy.com', 1, NOW(), NOW()),
('auditor', '123456', '审计员', 'AUDITOR', '审计组', '13800001004', 'auditor@privacy.com', 1, NOW(), NOW());

INSERT INTO data_subject (subject_name, subject_code, identity_type, phone, email, region_name, status, created_time, updated_time) VALUES
('张个人', 'SUB-001', 'ID_CARD', '13810000001', 'zhang@example.com', '北京', 'VERIFIED', NOW(), NOW()),
('李用户', 'SUB-002', 'PHONE', '13810000002', 'li@example.com', '上海', 'ACTIVE', NOW(), NOW());

INSERT INTO personal_data_item (item_name, item_code, category_name, sensitive_level, retention_days, owner_name, status, created_time, updated_time) VALUES
('身份证号码', 'PI-IDCARD', '身份信息', 'CRITICAL', 365, '隐私官', 'ACTIVE', NOW(), NOW()),
('手机号码', 'PI-PHONE', '联系方式', 'HIGH', 180, '数据管理员', 'ACTIVE', NOW(), NOW());

INSERT INTO consent_purpose (purpose_name, purpose_code, business_scene, purpose_text, valid_days, owner_name, status, created_time, updated_time) VALUES
('会员服务授权', 'PUR-MEMBER', '会员权益服务', '用于提供会员权益和账户服务', 365, '隐私官', 'ACTIVE', NOW(), NOW()),
('营销活动授权', 'PUR-MKT', '活动触达分析', '用于活动通知和效果分析', 90, '运营负责人', 'DRAFT', NOW(), NOW());

INSERT INTO consent_policy (policy_name, policy_code, purpose_name, data_scope, policy_version, status, created_time, updated_time) VALUES
('会员服务隐私策略', 'POL-MEMBER', '会员服务授权', '姓名、手机号、会员等级', 'v1.0', 'APPROVED', NOW(), NOW()),
('营销触达隐私策略', 'POL-MKT', '营销活动授权', '手机号、偏好标签', 'v1.1', 'DRAFT', NOW(), NOW());

INSERT INTO authorization_record (auth_no, subject_name, purpose_name, channel_name, grant_time, expire_time, status, created_time, updated_time) VALUES
('AUTH-001', '张个人', '会员服务授权', 'APP', '2026-05-01 10:00:00', '2027-05-01 10:00:00', 'ACTIVE', NOW(), NOW()),
('AUTH-002', '李用户', '营销活动授权', 'WEB', '2026-05-02 11:00:00', '2026-08-02 11:00:00', 'ACTIVE', NOW(), NOW());

INSERT INTO authorization_scope (auth_no, item_name, scope_type, required_flag, description_text, status, created_time, updated_time) VALUES
('AUTH-001', '手机号码', 'READ', 'YES', '会员服务登录验证', 'ACTIVE', NOW(), NOW()),
('AUTH-002', '偏好标签', 'PROCESS', 'NO', '活动推荐分析', 'ACTIVE', NOW(), NOW());

INSERT INTO access_application (application_no, applicant_name, subject_name, purpose_name, reason_text, status, created_time, updated_time) VALUES
('APP-001', '客服专员', '张个人', '会员服务授权', '处理客户服务请求', 'SUBMITTED', NOW(), NOW()),
('APP-002', '运营专员', '李用户', '营销活动授权', '分析活动参与记录', 'DRAFT', NOW(), NOW());

INSERT INTO access_grant (grant_no, application_no, grantee_name, data_scope, expire_time, status, created_time, updated_time) VALUES
('GRANT-001', 'APP-001', '客服专员', '手机号后四位、会员等级', '2026-05-15 23:59:59', 'ACTIVE', NOW(), NOW()),
('GRANT-002', 'APP-002', '运营专员', '偏好标签汇总', '2026-05-20 23:59:59', 'ACTIVE', NOW(), NOW());

INSERT INTO access_log (log_no, operator_name, subject_name, item_name, access_result, access_time, status, created_time, updated_time) VALUES
('LOG-001', '客服专员', '张个人', '手机号码', 'SUCCESS', '2026-05-04 09:30:00', 'NORMAL', NOW(), NOW()),
('LOG-002', '运营专员', '李用户', '偏好标签', 'DENIED', '2026-05-04 10:10:00', 'RISK', NOW(), NOW());

INSERT INTO revocation_request (request_no, subject_name, auth_no, reason_text, request_time, status, created_time, updated_time) VALUES
('REV-001', '张个人', 'AUTH-001', '不再使用会员服务', '2026-05-03 12:00:00', 'SUBMITTED', NOW(), NOW()),
('REV-002', '李用户', 'AUTH-002', '拒绝营销触达', '2026-05-04 13:00:00', 'APPROVED', NOW(), NOW());

INSERT INTO risk_warning (warning_no, warning_type, subject_name, risk_level, owner_name, detected_time, status, created_time, updated_time) VALUES
('RW-001', 'NO_AUTH', '张个人', 'HIGH', '隐私官', '2026-05-04 11:30:00', 'OPEN', NOW(), NOW()),
('RW-002', 'HIGH_FREQUENCY', '李用户', 'MEDIUM', '审计员', '2026-05-04 15:20:00', 'ACKED', NOW(), NOW());

INSERT INTO audit_report (report_no, report_name, report_period, generated_by, summary_text, status, created_time, updated_time) VALUES
('AUD-001', '5月隐私访问审计', '2026-05', '审计员', '本月授权访问整体正常，发现两条风险预警', 'PUBLISHED', NOW(), NOW()),
('AUD-002', '营销授权专项审计', '2026-Q2', '隐私官', '营销授权需补充撤销闭环材料', 'DRAFT', NOW(), NOW());

INSERT INTO operation_log (operator_name, module_name, action_type, target_name, detail, ip_address, status, created_time, updated_time) VALUES
('admin', '授权记录', '撤销授权', 'AUTH-001', '撤销营销活动授权', '127.0.0.1', 'SUCCESS', NOW(), NOW()),
('privacy', '访问申请', '审批申请', 'APP-001', '通过客服访问申请', '127.0.0.1', 'SUCCESS', NOW(), NOW());
