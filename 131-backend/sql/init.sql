DROP DATABASE IF EXISTS drug_reaction_131;
CREATE DATABASE drug_reaction_131 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE drug_reaction_131;

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
('admin', '123456', '系统管理员', 'ADMIN', '药械监管中心', '13800013101', 'admin@demo.local', 1, NOW(), NOW()),
('reporter', '123456', '上报员', 'REPORTER', '临床科室', '13800013102', 'reporter@demo.local', 1, NOW(), NOW()),
('reviewer', '123456', '复核员', 'REVIEWER', '药学部', '13800013103', 'reviewer@demo.local', 1, NOW(), NOW()),
('doctor', '123456', '随访医生', 'DOCTOR', '随访组', '13800013104', 'doctor@demo.local', 1, NOW(), NOW());

CREATE TABLE patient_profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  patient_no VARCHAR(120),
  patient_name VARCHAR(120),
  gender_name VARCHAR(120),
  age_value INT,
  phone_number VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO patient_profile (patient_no, patient_name, gender_name, age_value, phone_number, status, created_time, updated_time) VALUES
('PAT-131-001', '患者姓名一', '男', 35, '13800013121', 'ACTIVE', NOW(), NOW()),
('PAT-131-002', '患者姓名二', '女', 40, '13800013122', 'ACTIVE', NOW(), NOW());

CREATE TABLE drug_catalog (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  drug_no VARCHAR(120),
  drug_name VARCHAR(120),
  dosage_form VARCHAR(120),
  manufacturer_name VARCHAR(120),
  approval_no VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO drug_catalog (drug_no, drug_name, dosage_form, manufacturer_name, approval_no, status, created_time, updated_time) VALUES
('DRG-131-001', '药品名称一', '剂型一', '生产企业一', 'DRG-131-001', 'ACTIVE', NOW(), NOW()),
('DRG-131-002', '药品名称二', '剂型二', '生产企业二', 'DRG-131-002', 'ACTIVE', NOW(), NOW());

CREATE TABLE reporter_profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  reporter_no VARCHAR(120),
  reporter_name VARCHAR(120),
  organization_name VARCHAR(120),
  phone_number VARCHAR(120),
  role_type VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO reporter_profile (reporter_no, reporter_name, organization_name, phone_number, role_type, status, created_time, updated_time) VALUES
('RPT-131-001', '上报人姓名一', '所属机构一', '13800013121', '角色类型一', 'ACTIVE', NOW(), NOW()),
('RPT-131-002', '上报人姓名二', '所属机构二', '13800013122', '角色类型二', 'ACTIVE', NOW(), NOW());

CREATE TABLE adverse_report (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  report_no VARCHAR(120),
  patient_no VARCHAR(120),
  drug_name VARCHAR(120),
  report_time VARCHAR(120),
  severity_level VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO adverse_report (report_no, patient_no, drug_name, report_time, severity_level, status, created_time, updated_time) VALUES
('ADR-131-001', 'ADR-131-001', '药品名称一', '2026-05-06 10:00', '高', 'SUBMITTED', NOW(), NOW()),
('ADR-131-002', 'ADR-131-002', '药品名称二', '2026-05-07 10:00', '中', 'SUBMITTED', NOW(), NOW());

CREATE TABLE reaction_symptom (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  symptom_no VARCHAR(120),
  report_no VARCHAR(120),
  symptom_name VARCHAR(120),
  onset_time VARCHAR(120),
  symptom_level VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO reaction_symptom (symptom_no, report_no, symptom_name, onset_time, symptom_level, status, created_time, updated_time) VALUES
('SYM-131-001', 'SYM-131-001', '症状名称一', '2026-05-06 10:00', '高', 'OPEN', NOW(), NOW()),
('SYM-131-002', 'SYM-131-002', '症状名称二', '2026-05-07 10:00', '中', 'OPEN', NOW(), NOW());

CREATE TABLE risk_assessment (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  assess_no VARCHAR(120),
  report_no VARCHAR(120),
  assess_level VARCHAR(120),
  score_value DECIMAL(12,2),
  assessor_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO risk_assessment (assess_no, report_no, assess_level, score_value, assessor_name, status, created_time, updated_time) VALUES
('RSK-131-001', 'RSK-131-001', '高', 33.8, '评估人一', 'REVIEWING', NOW(), NOW()),
('RSK-131-002', 'RSK-131-002', '中', 47.1, '评估人二', 'REVIEWING', NOW(), NOW());

CREATE TABLE followup_plan (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  plan_no VARCHAR(120),
  report_no VARCHAR(120),
  follow_date VARCHAR(120),
  follow_method VARCHAR(120),
  owner_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO followup_plan (plan_no, report_no, follow_date, follow_method, owner_name, status, created_time, updated_time) VALUES
('FPL-131-001', 'FPL-131-001', '2026-05-06', '随访方式一', '负责人一', 'OPEN', NOW(), NOW()),
('FPL-131-002', 'FPL-131-002', '2026-05-07', '随访方式二', '负责人二', 'OPEN', NOW(), NOW());

CREATE TABLE followup_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  plan_no VARCHAR(120),
  follow_time VARCHAR(120),
  patient_condition VARCHAR(120),
  recorder_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO followup_record (record_no, plan_no, follow_time, patient_condition, recorder_name, status, created_time, updated_time) VALUES
('FRE-131-001', 'FRE-131-001', '2026-05-06 10:00', '患者情况一', '记录人一', 'SUBMITTED', NOW(), NOW()),
('FRE-131-002', 'FRE-131-002', '2026-05-07 10:00', '患者情况二', '记录人二', 'SUBMITTED', NOW(), NOW());

CREATE TABLE case_review (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  review_no VARCHAR(120),
  report_no VARCHAR(120),
  reviewer_name VARCHAR(120),
  review_opinion VARCHAR(120),
  review_time VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO case_review (review_no, report_no, reviewer_name, review_opinion, review_time, status, created_time, updated_time) VALUES
('REV-131-001', 'REV-131-001', '复核人一', '复核意见一', '2026-05-06 10:00', 'REVIEWING', NOW(), NOW()),
('REV-131-002', 'REV-131-002', '复核人二', '复核意见二', '2026-05-07 10:00', 'REVIEWING', NOW(), NOW());

CREATE TABLE treatment_advice (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  advice_no VARCHAR(120),
  report_no VARCHAR(120),
  advice_type VARCHAR(120),
  advice_content VARCHAR(120),
  doctor_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO treatment_advice (advice_no, report_no, advice_type, advice_content, doctor_name, status, created_time, updated_time) VALUES
('ADV-131-001', 'ADV-131-001', '建议类型一', '建议内容一', '医生一', 'PUBLISHED', NOW(), NOW()),
('ADV-131-002', 'ADV-131-002', '建议类型二', '建议内容二', '医生二', 'PUBLISHED', NOW(), NOW());

CREATE TABLE department_info (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  dept_no VARCHAR(120),
  dept_name VARCHAR(120),
  hospital_name VARCHAR(120),
  contact_name VARCHAR(120),
  phone_number VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO department_info (dept_no, dept_name, hospital_name, contact_name, phone_number, status, created_time, updated_time) VALUES
('DEP-131-001', '科室名称一', '医院名称一', '联系人一', '13800013121', 'ACTIVE', NOW(), NOW()),
('DEP-131-002', '科室名称二', '医院名称二', '联系人二', '13800013122', 'ACTIVE', NOW(), NOW());

CREATE TABLE statistics_report (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  stat_no VARCHAR(120),
  dept_name VARCHAR(120),
  stat_month VARCHAR(120),
  report_count INT,
  serious_count INT,
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO statistics_report (stat_no, dept_name, stat_month, report_count, serious_count, status, created_time, updated_time) VALUES
('STA-131-001', '科室名称一', '2026-06', 35, 35, 'FINISHED', NOW(), NOW()),
('STA-131-002', '科室名称二', '2026-07', 40, 40, 'FINISHED', NOW(), NOW());

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
