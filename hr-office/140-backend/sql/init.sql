DROP DATABASE IF EXISTS electronic_contract_140;
CREATE DATABASE electronic_contract_140 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE electronic_contract_140;

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
('admin', '$2a$12$tstfO416nC5sUSbpA/LUtOE5PO4lZI8B1mx.NssOQefP4IZakTf6q', '系统管理员', 'ADMIN', '信息管理部', '139140000100', 'admin@demo.local', 1, NOW(), NOW()),
('legal', '$2a$12$tstfO416nC5sUSbpA/LUtOE5PO4lZI8B1mx.NssOQefP4IZakTf6q', '法务专员', 'LEGAL', '法务合规部', '139140000200', 'legal@demo.local', 1, NOW(), NOW()),
('applicant', '$2a$12$tstfO416nC5sUSbpA/LUtOE5PO4lZI8B1mx.NssOQefP4IZakTf6q', '业务申请人', 'APPLICANT', '业务运营部', '139140000300', 'applicant@demo.local', 1, NOW(), NOW()),
('approver', '$2a$12$tstfO416nC5sUSbpA/LUtOE5PO4lZI8B1mx.NssOQefP4IZakTf6q', '审批负责人', 'APPROVER', '综合管理部', '139140000400', 'approver@demo.local', 1, NOW(), NOW());

CREATE TABLE contract_template (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  template_no VARCHAR(120),
  template_name VARCHAR(120),
  contract_type VARCHAR(120),
  version_no VARCHAR(60),
  maintainer_name VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO contract_template (template_no, template_name, contract_type, version_no, maintainer_name, status, created_time, updated_time) VALUES
('TPL-140-001', '标准采购合同模板', '采购合同', 'V1.0', '法务专员', 'ACTIVE', NOW(), NOW()),
('TPL-140-002', '软件服务合同模板', '服务合同', 'V2.1', '系统管理员', 'DRAFT', NOW(), NOW());

CREATE TABLE counterparty_profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  counterparty_no VARCHAR(120),
  counterparty_name VARCHAR(120),
  credit_code VARCHAR(120),
  contact_name VARCHAR(120),
  contact_phone VARCHAR(30),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO counterparty_profile (counterparty_no, counterparty_name, credit_code, contact_name, contact_phone, status, created_time, updated_time) VALUES
('CP-140-001', '华东数字科技有限公司', '91310112MA8AB1234X', '李峰', '13800138001', 'ACTIVE', NOW(), NOW()),
('CP-140-002', '卓越供应链服务有限公司', '91440300MA5CD5678Y', '王敏', '13800138002', 'FINISHED', NOW(), NOW());

CREATE TABLE signer_profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  signer_no VARCHAR(120),
  signer_name VARCHAR(120),
  signer_type VARCHAR(80),
  certificate_no VARCHAR(120),
  authorization_status VARCHAR(40),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO signer_profile (signer_no, signer_name, signer_type, certificate_no, authorization_status, status, created_time, updated_time) VALUES
('SIG-140-001', '张琳', '企业签署人', 'CERT-EC-2026001', 'AUTHORIZED', 'ACTIVE', NOW(), NOW()),
('SIG-140-002', '陈涛', '代理签署人', 'CERT-EC-2026002', 'PENDING', 'PROCESSING', NOW(), NOW());

CREATE TABLE contract_draft (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  draft_no VARCHAR(120),
  template_name VARCHAR(120),
  contract_title VARCHAR(120),
  applicant_name VARCHAR(120),
  submit_time VARCHAR(40),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO contract_draft (draft_no, template_name, contract_title, applicant_name, submit_time, status, created_time, updated_time) VALUES
('DR-140-001', '标准采购合同模板', '2026年办公设备采购合同', '业务申请人', '2026-05-12 09:30', 'DRAFT', NOW(), NOW()),
('DR-140-002', '软件服务合同模板', '云平台运维服务续签合同', '系统管理员', '2026-05-13 15:20', 'SUBMITTED', NOW(), NOW());

CREATE TABLE seal_application (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  application_no VARCHAR(120),
  contract_title VARCHAR(120),
  seal_type VARCHAR(80),
  applicant_name VARCHAR(120),
  use_date VARCHAR(40),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO seal_application (application_no, contract_title, seal_type, applicant_name, use_date, status, created_time, updated_time) VALUES
('SEA-140-001', '2026年办公设备采购合同', '合同专用章', '业务申请人', '2026-05-16', 'SUBMITTED', NOW(), NOW()),
('SEA-140-002', '云平台运维服务续签合同', '公司公章', '系统管理员', '2026-05-18', 'APPROVED', NOW(), NOW());

CREATE TABLE approval_flow (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  flow_no VARCHAR(120),
  contract_title VARCHAR(120),
  current_node VARCHAR(120),
  approver_name VARCHAR(120),
  approval_opinion VARCHAR(255),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO approval_flow (flow_no, contract_title, current_node, approver_name, approval_opinion, status, created_time, updated_time) VALUES
('APR-140-001', '2026年办公设备采购合同', '法务审查', '法务专员', '条款清晰，可进入用印审批', 'SUBMITTED', NOW(), NOW()),
('APR-140-002', '云平台运维服务续签合同', '分管领导审批', '审批负责人', '同意签署并归档', 'APPROVED', NOW(), NOW());

CREATE TABLE contract_signing (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  signing_no VARCHAR(120),
  contract_title VARCHAR(120),
  signatory_name VARCHAR(120),
  sign_time VARCHAR(40),
  signing_status VARCHAR(40),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO contract_signing (signing_no, contract_title, signatory_name, sign_time, signing_status, status, created_time, updated_time) VALUES
('CS-140-001', '2026年办公设备采购合同', '张琳', '2026-05-18 10:30', 'PENDING', 'PROCESSING', NOW(), NOW()),
('CS-140-002', '云平台运维服务续签合同', '陈涛', '2026-05-19 14:00', 'SIGNED', 'FINISHED', NOW(), NOW());

CREATE TABLE seal_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  seal_record_no VARCHAR(120),
  contract_title VARCHAR(120),
  seal_type VARCHAR(80),
  operator_name VARCHAR(120),
  seal_time VARCHAR(40),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO seal_record (seal_record_no, contract_title, seal_type, operator_name, seal_time, status, created_time, updated_time) VALUES
('SR-140-001', '2026年办公设备采购合同', '合同专用章', '法务专员', '2026-05-18 11:00', 'ACTIVE', NOW(), NOW()),
('SR-140-002', '云平台运维服务续签合同', '公司公章', '系统管理员', '2026-05-19 14:30', 'FINISHED', NOW(), NOW());

CREATE TABLE archive_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  archive_no VARCHAR(120),
  contract_title VARCHAR(120),
  archive_location VARCHAR(120),
  archivist_name VARCHAR(120),
  archive_date VARCHAR(40),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO archive_record (archive_no, contract_title, archive_location, archivist_name, archive_date, status, created_time, updated_time) VALUES
('AR-140-001', '2026年办公设备采购合同', '电子档案室-A01', '法务专员', '2026-05-20', 'ACTIVE', NOW(), NOW()),
('AR-140-002', '云平台运维服务续签合同', '电子档案室-B03', '系统管理员', '2026-05-21', 'FINISHED', NOW(), NOW());

CREATE TABLE expiration_reminder (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  reminder_no VARCHAR(120),
  contract_title VARCHAR(120),
  counterparty_name VARCHAR(120),
  expiry_date VARCHAR(40),
  reminder_method VARCHAR(80),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO expiration_reminder (reminder_no, contract_title, counterparty_name, expiry_date, reminder_method, status, created_time, updated_time) VALUES
('RM-140-001', '2026年办公设备采购合同', '华东数字科技有限公司', '2027-05-30', '邮件提醒', 'ACTIVE', NOW(), NOW()),
('RM-140-002', '云平台运维服务续签合同', '卓越供应链服务有限公司', '2026-12-31', '短信提醒', 'FINISHED', NOW(), NOW());

CREATE TABLE risk_clause (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  clause_no VARCHAR(120),
  contract_title VARCHAR(120),
  clause_type VARCHAR(120),
  risk_level VARCHAR(40),
  reviewer_name VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO risk_clause (clause_no, contract_title, clause_type, risk_level, reviewer_name, status, created_time, updated_time) VALUES
('RK-140-001', '2026年办公设备采购合同', '违约责任条款', 'HIGH', '法务专员', 'PROCESSING', NOW(), NOW()),
('RK-140-002', '云平台运维服务续签合同', '数据安全条款', 'MEDIUM', '系统管理员', 'FINISHED', NOW(), NOW());

CREATE TABLE contract_notice (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  notice_no VARCHAR(120),
  notice_title VARCHAR(120),
  notice_type VARCHAR(120),
  receiver_name VARCHAR(120),
  publish_time VARCHAR(40),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO contract_notice (notice_no, notice_title, notice_type, receiver_name, publish_time, status, created_time, updated_time) VALUES
('NT-140-001', '采购合同待签通知', '待签提醒', '业务申请人', '2026-05-18 12:00', 'PROCESSING', NOW(), NOW()),
('NT-140-002', '服务合同归档完成通知', '归档通知', '审批负责人', '2026-05-21 16:20', 'FINISHED', NOW(), NOW());

CREATE TABLE operation_log (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  operator_name VARCHAR(120),
  module_name VARCHAR(120),
  action_type VARCHAR(120),
  target_name VARCHAR(120),
  detail_info VARCHAR(255),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO operation_log (operator_name, module_name, action_type, target_name, detail_info, status, created_time, updated_time) VALUES
('系统管理员', '合同模板', '新增', '标准采购合同模板', '新增采购合同模板并提交法务复核', 'SUCCESS', NOW(), NOW()),
('法务专员', '审批流程', '审批', '云平台运维服务续签合同', '完成法务审查并推送至签署环节', 'SUCCESS', NOW(), NOW());
