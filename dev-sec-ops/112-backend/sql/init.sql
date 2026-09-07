DROP DATABASE IF EXISTS zero_trust_112;
CREATE DATABASE zero_trust_112 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE zero_trust_112;

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

CREATE TABLE device_asset (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  device_name VARCHAR(100) NOT NULL,
  device_no VARCHAR(80) NOT NULL,
  device_type VARCHAR(40),
  os_version VARCHAR(80),
  owner_name VARCHAR(50),
  department_name VARCHAR(80),
  status VARCHAR(30) DEFAULT 'PENDING',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE employee_account (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  employee_name VARCHAR(80) NOT NULL,
  employee_no VARCHAR(60) NOT NULL,
  account_name VARCHAR(80),
  department_name VARCHAR(80),
  mfa_status VARCHAR(30),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE identity_provider (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  provider_name VARCHAR(100) NOT NULL,
  provider_code VARCHAR(80) NOT NULL,
  sync_mode VARCHAR(40),
  endpoint_url VARCHAR(160),
  owner_name VARCHAR(50),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE risk_model (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  model_name VARCHAR(100) NOT NULL,
  model_code VARCHAR(80) NOT NULL,
  model_type VARCHAR(40),
  threshold_score INT DEFAULT 0,
  owner_name VARCHAR(50),
  status VARCHAR(30) DEFAULT 'DRAFT',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE risk_assessment (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  assessment_no VARCHAR(80) NOT NULL,
  device_name VARCHAR(100),
  employee_name VARCHAR(80),
  risk_score INT DEFAULT 0,
  risk_level VARCHAR(30),
  advice_text VARCHAR(200),
  status VARCHAR(30) DEFAULT 'WAIT_REVIEW',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE access_policy (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  policy_name VARCHAR(100) NOT NULL,
  policy_code VARCHAR(80) NOT NULL,
  resource_name VARCHAR(100),
  condition_text VARCHAR(240),
  owner_name VARCHAR(50),
  status VARCHAR(30) DEFAULT 'DRAFT',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE policy_rule (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  rule_name VARCHAR(100) NOT NULL,
  policy_name VARCHAR(100),
  condition_type VARCHAR(40),
  action_type VARCHAR(40),
  priority INT DEFAULT 1,
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE access_application (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  application_no VARCHAR(80) NOT NULL,
  device_name VARCHAR(100),
  employee_name VARCHAR(80),
  resource_name VARCHAR(100),
  reason_text VARCHAR(240),
  status VARCHAR(30) DEFAULT 'DRAFT',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE access_session (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  session_no VARCHAR(80) NOT NULL,
  device_name VARCHAR(100),
  employee_name VARCHAR(80),
  resource_name VARCHAR(100),
  source_ip VARCHAR(50),
  login_time DATETIME,
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE network_segment (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  segment_name VARCHAR(100) NOT NULL,
  segment_code VARCHAR(80) NOT NULL,
  cidr_block VARCHAR(80),
  security_zone VARCHAR(40),
  owner_name VARCHAR(50),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE device_certificate (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  cert_no VARCHAR(100) NOT NULL,
  device_name VARCHAR(100),
  issuer_name VARCHAR(80),
  issued_time DATETIME,
  expire_time DATETIME,
  status VARCHAR(30) DEFAULT 'VALID',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE audit_event (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  event_no VARCHAR(80) NOT NULL,
  event_type VARCHAR(50),
  device_name VARCHAR(100),
  employee_name VARCHAR(80),
  detail_text VARCHAR(240),
  event_time DATETIME,
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
('admin', '123456', '系统管理员', 'ADMIN', '平台管理组', '13800001201', 'admin@zt.com', 1, NOW(), NOW()),
('security', '123456', '安全管理员', 'SECURITY', '安全运营组', '13800001202', 'security@zt.com', 1, NOW(), NOW()),
('network', '123456', '网络管理员', 'NETWORK', '网络组', '13800001203', 'network@zt.com', 1, NOW(), NOW()),
('auditor', '123456', '审计员', 'AUDITOR', '审计组', '13800001204', 'auditor@zt.com', 1, NOW(), NOW());

INSERT INTO device_asset (device_name, device_no, device_type, os_version, owner_name, department_name, status, created_time, updated_time) VALUES
('研发笔记本01', 'DEV-RD-001', 'LAPTOP', 'Windows 11', '张工程师', '研发中心', 'APPROVED', NOW(), NOW()),
('财务台式机02', 'DEV-FIN-002', 'DESKTOP', 'Windows 10', '李财务', '财务部', 'PENDING', NOW(), NOW());

INSERT INTO employee_account (employee_name, employee_no, account_name, department_name, mfa_status, status, created_time, updated_time) VALUES
('张工程师', 'EMP112001', 'zhang.rd', '研发中心', 'ENABLED', 'ACTIVE', NOW(), NOW()),
('李财务', 'EMP112002', 'li.fin', '财务部', 'DISABLED', 'ACTIVE', NOW(), NOW());

INSERT INTO identity_provider (provider_name, provider_code, sync_mode, endpoint_url, owner_name, status, created_time, updated_time) VALUES
('企业AD域', 'IDP-AD', 'LDAP', 'ldap://ad.local', '安全管理员', 'ACTIVE', NOW(), NOW()),
('统一身份平台', 'IDP-IAM', 'OIDC', 'https://iam.local', '平台管理员', 'ACTIVE', NOW(), NOW());

INSERT INTO risk_model (model_name, model_code, model_type, threshold_score, owner_name, status, created_time, updated_time) VALUES
('设备健康评分模型', 'RM-DEVICE', 'DEVICE', 70, '安全管理员', 'ACTIVE', NOW(), NOW()),
('异常位置评分模型', 'RM-LOCATION', 'LOCATION', 80, '审计员', 'DRAFT', NOW(), NOW());

INSERT INTO risk_assessment (assessment_no, device_name, employee_name, risk_score, risk_level, advice_text, status, created_time, updated_time) VALUES
('RA-112-001', '研发笔记本01', '张工程师', 45, 'MEDIUM', '允许访问研发资源', 'REVIEWED', NOW(), NOW()),
('RA-112-002', '财务台式机02', '李财务', 86, 'HIGH', '需阻断并重新认证', 'WAIT_REVIEW', NOW(), NOW());

INSERT INTO access_policy (policy_name, policy_code, resource_name, condition_text, owner_name, status, created_time, updated_time) VALUES
('研发系统准入策略', 'POL-RD', '研发代码平台', '设备已准入且MFA启用', '网络管理员', 'ACTIVE', NOW(), NOW()),
('财务系统强认证策略', 'POL-FIN', '财务报销系统', '设备准入且风险分低于70', '安全管理员', 'DRAFT', NOW(), NOW());

INSERT INTO policy_rule (rule_name, policy_name, condition_type, action_type, priority, status, created_time, updated_time) VALUES
('健康设备允许', '研发系统准入策略', 'DEVICE', 'ALLOW', 1, 'ACTIVE', NOW(), NOW()),
('高风险阻断', '财务系统强认证策略', 'RISK', 'DENY', 2, 'ACTIVE', NOW(), NOW());

INSERT INTO access_application (application_no, device_name, employee_name, resource_name, reason_text, status, created_time, updated_time) VALUES
('APP-112-001', '研发笔记本01', '张工程师', '研发代码平台', '新设备接入研发网络', 'SUBMITTED', NOW(), NOW()),
('APP-112-002', '财务台式机02', '李财务', '财务报销系统', '财务设备访问报销系统', 'DRAFT', NOW(), NOW());

INSERT INTO access_session (session_no, device_name, employee_name, resource_name, source_ip, login_time, status, created_time, updated_time) VALUES
('SES-112-001', '研发笔记本01', '张工程师', '研发代码平台', '10.10.1.12', '2026-05-05 09:20:00', 'ACTIVE', NOW(), NOW()),
('SES-112-002', '财务台式机02', '李财务', '财务报销系统', '10.20.2.18', '2026-05-05 10:10:00', 'ACTIVE', NOW(), NOW());

INSERT INTO network_segment (segment_name, segment_code, cidr_block, security_zone, owner_name, status, created_time, updated_time) VALUES
('研发办公网', 'SEG-RD', '10.10.0.0/16', 'OFFICE', '网络管理员', 'ACTIVE', NOW(), NOW()),
('财务隔离区', 'SEG-FIN', '10.20.0.0/16', 'FINANCE', '安全管理员', 'ACTIVE', NOW(), NOW());

INSERT INTO device_certificate (cert_no, device_name, issuer_name, issued_time, expire_time, status, created_time, updated_time) VALUES
('CERT-112-001', '研发笔记本01', 'ZT-CA', '2026-05-01 09:00:00', '2027-05-01 09:00:00', 'VALID', NOW(), NOW()),
('CERT-112-002', '财务台式机02', 'ZT-CA', '2026-05-02 10:00:00', '2027-05-02 10:00:00', 'VALID', NOW(), NOW());

INSERT INTO audit_event (event_no, event_type, device_name, employee_name, detail_text, event_time, status, created_time, updated_time) VALUES
('EVT-112-001', 'ADMISSION', '研发笔记本01', '张工程师', '设备通过准入策略', '2026-05-05 09:30:00', 'OPEN', NOW(), NOW()),
('EVT-112-002', 'BLOCK', '财务台式机02', '李财务', '高风险访问被阻断', '2026-05-05 10:30:00', 'ACKED', NOW(), NOW());

INSERT INTO operation_log (operator_name, module_name, action_type, target_name, detail, ip_address, status, created_time, updated_time) VALUES
('admin', '设备资产', '设备准入', 'DEV-RD-001', '批准研发笔记本准入', '127.0.0.1', 'SUCCESS', NOW(), NOW()),
('security', '访问策略', '启用策略', 'POL-RD', '启用研发系统准入策略', '127.0.0.1', 'SUCCESS', NOW(), NOW());
