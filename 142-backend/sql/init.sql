DROP DATABASE IF EXISTS vehicle_claim_142;
CREATE DATABASE vehicle_claim_142 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE vehicle_claim_142;

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
('admin', '123456', '系统管理员', 'ADMIN', '运营管理部', '139142000100', 'admin@demo.local', 1, NOW(), NOW()),
('legal', '123456', '理赔专员', 'LEGAL', '车险理赔部', '139142000200', 'legal@demo.local', 1, NOW(), NOW()),
('applicant', '123456', '车主客户', 'APPLICANT', '客户服务部', '139142000300', 'applicant@demo.local', 1, NOW(), NOW()),
('approver', '123456', '审核主管', 'APPROVER', '风控审核部', '139142000400', 'approver@demo.local', 1, NOW(), NOW());

CREATE TABLE insurance_policy (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  policy_no VARCHAR(120),
  policy_name VARCHAR(120),
  policy_type VARCHAR(120),
  insurer_name VARCHAR(120),
  coverage_period VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO insurance_policy (policy_no, policy_name, policy_type, insurer_name, coverage_period, status, created_time, updated_time) VALUES
('POL-142-001', '交强险续保计划', '交强险', '华安保险', '2026-01-01 至 2026-12-31', 'ACTIVE', NOW(), NOW()),
('POL-142-002', '家庭用车综合险', '商业险', '太平洋保险', '2026-03-01 至 2027-02-28', 'DRAFT', NOW(), NOW());

CREATE TABLE vehicle_profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  vehicle_no VARCHAR(120),
  plate_number VARCHAR(120),
  vin_code VARCHAR(120),
  vehicle_model VARCHAR(120),
  usage_type VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO vehicle_profile (vehicle_no, plate_number, vin_code, vehicle_model, usage_type, status, created_time, updated_time) VALUES
('VEH-142-001', '沪A3Q521', 'LHGCM82633A142001', '本田雅阁 2024 款', '家庭自用', 'ACTIVE', NOW(), NOW()),
('VEH-142-002', '苏B9K118', 'LSVFA49J7A2142002', '大众帕萨特 2023 款', '网约营运', 'FINISHED', NOW(), NOW());

CREATE TABLE customer_profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  customer_no VARCHAR(120),
  customer_name VARCHAR(120),
  id_card_no VARCHAR(120),
  phone VARCHAR(30),
  customer_type VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO customer_profile (customer_no, customer_name, id_card_no, phone, customer_type, status, created_time, updated_time) VALUES
('CUS-142-001', '张先生', '320102199208086512', '138142000301', '个人车主', 'ACTIVE', NOW(), NOW()),
('CUS-142-002', '远航物流有限公司', '91320594MA2X1420Q8', '138142000302', '企业客户', 'FINISHED', NOW(), NOW());

CREATE TABLE claim_application (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  application_no VARCHAR(120),
  case_name VARCHAR(120),
  report_channel VARCHAR(120),
  application_time VARCHAR(40),
  accident_location VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO claim_application (application_no, case_name, report_channel, application_time, accident_location, status, created_time, updated_time) VALUES
('CLA-142-001', '沪A3Q521 前保险杠碰撞案', 'APP 小程序', '2026-05-10 09:30', '上海市浦东新区龙阳路', 'SUBMITTED', NOW(), NOW()),
('CLA-142-002', '苏B9K118 追尾维修案', '95500 电话报案', '2026-05-12 14:10', '苏州市工业园区星湖街', 'APPROVED', NOW(), NOW());

CREATE TABLE accident_report (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  report_no VARCHAR(120),
  case_name VARCHAR(120),
  accident_type VARCHAR(120),
  report_time VARCHAR(40),
  reporter_name VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO accident_report (report_no, case_name, accident_type, report_time, reporter_name, status, created_time, updated_time) VALUES
('REP-142-001', '沪A3Q521 前保险杠碰撞案', '单车剐蹭', '2026-05-10 09:10', '张先生', 'SUBMITTED', NOW(), NOW()),
('REP-142-002', '苏B9K118 追尾维修案', '两车追尾', '2026-05-12 13:50', '王调度', 'APPROVED', NOW(), NOW());

CREATE TABLE material_checklist (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  checklist_no VARCHAR(120),
  report_no VARCHAR(120),
  material_type VARCHAR(120),
  material_desc VARCHAR(255),
  submit_time VARCHAR(40),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO material_checklist (checklist_no, report_no, material_type, material_desc, submit_time, status, created_time, updated_time) VALUES
('MAT-142-001', 'REP-142-001', '现场照片', '车头受损照片 6 张，已按要求压缩上传', '2026-05-10 11:20', 'SUBMITTED', NOW(), NOW()),
('MAT-142-002', 'REP-142-002', '责任认定书', '交警责任认定书与行驶证扫描件齐全', '2026-05-12 16:00', 'APPROVED', NOW(), NOW());

CREATE TABLE material_review (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  review_no VARCHAR(120),
  report_no VARCHAR(120),
  review_result VARCHAR(120),
  review_time VARCHAR(40),
  reviewer_name VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO material_review (review_no, report_no, review_result, review_time, reviewer_name, status, created_time, updated_time) VALUES
('REV-142-001', 'REP-142-001', '待补充维修发票', '2026-05-11 10:00', '理赔专员', 'PROCESSING', NOW(), NOW()),
('REV-142-002', 'REP-142-002', '材料齐全，可进入赔付', '2026-05-13 09:30', '审核主管', 'FINISHED', NOW(), NOW());

CREATE TABLE loss_assessment (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  assessment_no VARCHAR(120),
  report_no VARCHAR(120),
  assessment_amount DECIMAL(12,2),
  assessment_time VARCHAR(40),
  assessor_name VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO loss_assessment (assessment_no, report_no, assessment_amount, assessment_time, assessor_name, status, created_time, updated_time) VALUES
('LOS-142-001', 'REP-142-001', 2860.00, '2026-05-11 14:20', '李定损', 'PROCESSING', NOW(), NOW()),
('LOS-142-002', 'REP-142-002', 7420.00, '2026-05-13 10:40', '周定损', 'FINISHED', NOW(), NOW());

CREATE TABLE compensation_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  compensation_no VARCHAR(120),
  report_no VARCHAR(120),
  compensation_amount DECIMAL(12,2),
  compensation_time VARCHAR(40),
  payee_name VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO compensation_record (compensation_no, report_no, compensation_amount, compensation_time, payee_name, status, created_time, updated_time) VALUES
('PAY-142-001', 'REP-142-001', 2000.00, '2026-05-13 17:00', '张先生', 'PROCESSING', NOW(), NOW()),
('PAY-142-002', 'REP-142-002', 7420.00, '2026-05-14 11:30', '远航物流有限公司', 'FINISHED', NOW(), NOW());

CREATE TABLE progress_track (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  track_no VARCHAR(120),
  report_no VARCHAR(120),
  current_node VARCHAR(120),
  handle_dept VARCHAR(120),
  expected_finish_time VARCHAR(40),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO progress_track (track_no, report_no, current_node, handle_dept, expected_finish_time, status, created_time, updated_time) VALUES
('TRK-142-001', 'REP-142-001', '材料补正', '理赔服务部', '2026-05-15 18:00', 'PROCESSING', NOW(), NOW()),
('TRK-142-002', 'REP-142-002', '赔付款已发起', '财务结算部', '2026-05-14 18:00', 'FINISHED', NOW(), NOW());

CREATE TABLE follow_up_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  followup_no VARCHAR(120),
  report_no VARCHAR(120),
  followup_method VARCHAR(120),
  followup_content VARCHAR(255),
  customer_feedback VARCHAR(255),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO follow_up_record (followup_no, report_no, followup_method, followup_content, customer_feedback, status, created_time, updated_time) VALUES
('FUP-142-001', 'REP-142-001', '电话回访', '确认客户已补传维修发票', '客户希望尽快完成审核', 'PROCESSING', NOW(), NOW()),
('FUP-142-002', 'REP-142-002', '短信回访', '通知赔付到账并提醒评价服务', '客户反馈到账及时，流程清晰', 'FINISHED', NOW(), NOW());

CREATE TABLE claim_notice (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  notice_no VARCHAR(120),
  report_no VARCHAR(120),
  notice_type VARCHAR(120),
  notice_content VARCHAR(255),
  receiver_name VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO claim_notice (notice_no, report_no, notice_type, notice_content, receiver_name, status, created_time, updated_time) VALUES
('NT-142-001', 'REP-142-001', '补件提醒', '请于 24 小时内补充维修发票和受损清单', '张先生', 'PROCESSING', NOW(), NOW()),
('NT-142-002', 'REP-142-002', '赔付通知', '赔付款已完成审批并进入打款流程', '王调度', 'FINISHED', NOW(), NOW());

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
('系统管理员', '保险保单', '新增', 'POL-142-001', '录入年度交强险续保计划并分配理赔专员跟进', 'SUCCESS', NOW(), NOW()),
('理赔专员', '材料审核', '处理', 'REV-142-001', '审核报案材料并向客户发送补件提醒', 'SUCCESS', NOW(), NOW());
