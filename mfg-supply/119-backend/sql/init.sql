DROP DATABASE IF EXISTS spare_life_119;
CREATE DATABASE spare_life_119 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE spare_life_119;

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
);

INSERT INTO sys_user (username, password, nickname, role, department, phone, email, status, created_time, updated_time) VALUES
('admin', '123456', '系统管理员', 'ADMIN', '设备运维中心', '13800011901', 'admin@sparelife.local', 1, NOW(), NOW()),
('equipment', '123456', '设备管理员', 'EQUIPMENT', '设备资产组', '13800011902', 'equipment@sparelife.local', 1, NOW(), NOW()),
('maintainer', '123456', '维保员', 'MAINTAINER', '现场维保组', '13800011903', 'maintainer@sparelife.local', 1, NOW(), NOW()),
('analyst', '123456', '分析员', 'ANALYST', '寿命分析组', '13800011904', 'analyst@sparelife.local', 1, NOW(), NOW());

CREATE TABLE equipment_asset (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  equipment_no VARCHAR(80) NOT NULL,
  equipment_name VARCHAR(120) NOT NULL,
  line_name VARCHAR(100),
  asset_level VARCHAR(30),
  online_date VARCHAR(30),
  runtime_hours INT DEFAULT 0,
  status VARCHAR(30) DEFAULT 'RUNNING',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO equipment_asset (equipment_no, equipment_name, line_name, asset_level, online_date, runtime_hours, status, created_time, updated_time) VALUES
('EQ-119-001', '一号风机', '上海一线', '高', '2026-05-05', 8600, 'RUNNING', NOW(), NOW()),
('EQ-119-002', '一号泵组', '苏州一线', '中', '2026-05-05', 8609, 'RUNNING', NOW(), NOW());

CREATE TABLE spare_part_catalog (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  part_no VARCHAR(80) NOT NULL,
  part_name VARCHAR(120) NOT NULL,
  category_name VARCHAR(80),
  spec_model VARCHAR(100),
  fit_equipment VARCHAR(120),
  safe_stock INT DEFAULT 0,
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO spare_part_catalog (part_no, part_name, category_name, spec_model, fit_equipment, safe_stock, status, created_time, updated_time) VALUES
('PART-119-001', '高速轴承', '传动件', 'BR-6208', '一号风机', 20, 'ACTIVE', NOW(), NOW()),
('PART-119-002', '高速滤芯', '传动件', 'BR-6208', '一号泵组', 29, 'ACTIVE', NOW(), NOW());

CREATE TABLE spare_part_stock (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  stock_no VARCHAR(80) NOT NULL,
  part_name VARCHAR(120),
  warehouse_name VARCHAR(100),
  batch_no VARCHAR(80),
  available_qty INT DEFAULT 0,
  frozen_qty INT DEFAULT 0,
  status VARCHAR(30) DEFAULT 'NORMAL',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO spare_part_stock (stock_no, part_name, warehouse_name, batch_no, available_qty, frozen_qty, status, created_time, updated_time) VALUES
('STK-119-001', '高速轴承', '中心备件库', 'BATCH-119-01', 56, 2, 'NORMAL', NOW(), NOW()),
('STK-119-002', '高速滤芯', '中心备件库', 'BATCH-119-01', 65, 11, 'NORMAL', NOW(), NOW());

CREATE TABLE spare_part_inbound (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  inbound_no VARCHAR(80) NOT NULL,
  part_name VARCHAR(120),
  supplier_name VARCHAR(120),
  inbound_qty INT DEFAULT 0,
  quality_result VARCHAR(60),
  inbound_time VARCHAR(40),
  status VARCHAR(30) DEFAULT 'CREATED',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO spare_part_inbound (inbound_no, part_name, supplier_name, inbound_qty, quality_result, inbound_time, status, created_time, updated_time) VALUES
('INB-119-001', '高速轴承', '华东机电', 40, '合格', '2026-05-05 10:30', 'CREATED', NOW(), NOW()),
('INB-119-002', '高速滤芯', '华东机电', 49, '合格', '2026-05-05 10:30', 'CREATED', NOW(), NOW());

CREATE TABLE spare_part_outbound (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  outbound_no VARCHAR(80) NOT NULL,
  part_name VARCHAR(120),
  equipment_name VARCHAR(120),
  receiver_name VARCHAR(60),
  outbound_qty INT DEFAULT 0,
  purpose_text VARCHAR(180),
  status VARCHAR(30) DEFAULT 'CREATED',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO spare_part_outbound (outbound_no, part_name, equipment_name, receiver_name, outbound_qty, purpose_text, status, created_time, updated_time) VALUES
('OUT-119-001', '高速轴承', '一号风机', '张维保', 4, '计划性更换', 'CREATED', NOW(), NOW()),
('OUT-119-002', '高速滤芯', '一号泵组', '李维保', 13, '计划性更换', 'CREATED', NOW(), NOW());

CREATE TABLE usage_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  usage_no VARCHAR(80) NOT NULL,
  part_name VARCHAR(120),
  equipment_name VARCHAR(120),
  install_time VARCHAR(40),
  runtime_hours INT DEFAULT 0,
  installer_name VARCHAR(60),
  status VARCHAR(30) DEFAULT 'IN_USE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO usage_record (usage_no, part_name, equipment_name, install_time, runtime_hours, installer_name, status, created_time, updated_time) VALUES
('USE-119-001', '高速轴承', '一号风机', '2026-05-05 11:00', 2200, '张维保', 'IN_USE', NOW(), NOW()),
('USE-119-002', '高速滤芯', '一号泵组', '2026-05-05 11:00', 2209, '李维保', 'IN_USE', NOW(), NOW());

CREATE TABLE sensor_metric (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  metric_no VARCHAR(80) NOT NULL,
  equipment_name VARCHAR(120),
  metric_source VARCHAR(100),
  temperature_value DECIMAL(10,2) DEFAULT 0,
  vibration_value DECIMAL(10,2) DEFAULT 0,
  current_value DECIMAL(10,2) DEFAULT 0,
  collect_time VARCHAR(40),
  status VARCHAR(30) DEFAULT 'NORMAL',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO sensor_metric (metric_no, equipment_name, metric_source, temperature_value, vibration_value, current_value, collect_time, status, created_time, updated_time) VALUES
('MET-119-001', '一号风机', '振动传感器', 62.50, 3.20, 18.60, '2026-05-05 12:00', 'NORMAL', NOW(), NOW()),
('MET-119-002', '一号泵组', '振动传感器', 69.86, 10.56, 25.96, '2026-05-05 12:00', 'NORMAL', NOW(), NOW());

CREATE TABLE failure_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  failure_no VARCHAR(80) NOT NULL,
  equipment_name VARCHAR(120),
  failure_type VARCHAR(80),
  impact_level VARCHAR(30),
  downtime_hour DECIMAL(8,2) DEFAULT 0,
  handler_name VARCHAR(60),
  status VARCHAR(30) DEFAULT 'OPEN',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO failure_record (failure_no, equipment_name, failure_type, impact_level, downtime_hour, handler_name, status, created_time, updated_time) VALUES
('FAIL-119-001', '一号风机', '轴承异响', '高', 2.50, '张维保', 'OPEN', NOW(), NOW()),
('FAIL-119-002', '一号泵组', '滤芯异响', '中', 9.86, '李维保', 'OPEN', NOW(), NOW());

CREATE TABLE life_prediction (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  prediction_no VARCHAR(80) NOT NULL,
  part_name VARCHAR(120),
  equipment_name VARCHAR(120),
  remain_hours INT DEFAULT 0,
  health_score DECIMAL(8,2) DEFAULT 0,
  risk_level VARCHAR(30),
  model_version VARCHAR(50),
  status VARCHAR(30) DEFAULT 'GENERATED',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO life_prediction (prediction_no, part_name, equipment_name, remain_hours, health_score, risk_level, model_version, status, created_time, updated_time) VALUES
('PRED-119-001', '高速轴承', '一号风机', 420, 73.50, '高', 'LIFE-V1', 'GENERATED', NOW(), NOW()),
('PRED-119-002', '高速滤芯', '一号泵组', 429, 80.86, '中', 'LIFE-V1', 'GENERATED', NOW(), NOW());

CREATE TABLE maintenance_plan (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  plan_no VARCHAR(80) NOT NULL,
  equipment_name VARCHAR(120),
  part_name VARCHAR(120),
  suggest_action VARCHAR(120),
  plan_time VARCHAR(40),
  owner_name VARCHAR(60),
  status VARCHAR(30) DEFAULT 'WAIT_EXECUTE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO maintenance_plan (plan_no, equipment_name, part_name, suggest_action, plan_time, owner_name, status, created_time, updated_time) VALUES
('PLAN-119-001', '一号风机', '高速轴承', '提前更换', '2026-05-06 09:00', '张维保', 'WAIT_EXECUTE', NOW(), NOW()),
('PLAN-119-002', '一号泵组', '高速滤芯', '提前更换', '2026-05-06 09:00', '李维保', 'WAIT_EXECUTE', NOW(), NOW());

CREATE TABLE procurement_request (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  request_no VARCHAR(80) NOT NULL,
  part_name VARCHAR(120),
  request_qty INT DEFAULT 0,
  budget_amount DECIMAL(12,2) DEFAULT 0,
  applicant_name VARCHAR(60),
  approver_name VARCHAR(60),
  status VARCHAR(30) DEFAULT 'SUBMITTED',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO procurement_request (request_no, part_name, request_qty, budget_amount, applicant_name, approver_name, status, created_time, updated_time) VALUES
('REQ-119-001', '高速轴承', 60, 18000.00, '张维保', '系统管理员', 'SUBMITTED', NOW(), NOW()),
('REQ-119-002', '高速滤芯', 69, 18007.36, '李维保', '系统管理员', 'SUBMITTED', NOW(), NOW());

CREATE TABLE risk_warning (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  warning_no VARCHAR(80) NOT NULL,
  equipment_name VARCHAR(120),
  part_name VARCHAR(120),
  warning_level VARCHAR(30),
  reason_text VARCHAR(220),
  handler_name VARCHAR(60),
  status VARCHAR(30) DEFAULT 'OPEN',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO risk_warning (warning_no, equipment_name, part_name, warning_level, reason_text, handler_name, status, created_time, updated_time) VALUES
('WARN-119-001', '一号风机', '高速轴承', '高', '剩余寿命低于阈值且振动指标升高', '张维保', 'OPEN', NOW(), NOW()),
('WARN-119-002', '一号泵组', '高速滤芯', '中', '剩余寿命低于阈值且振动指标升中', '李维保', 'OPEN', NOW(), NOW());

CREATE TABLE operation_log (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  operator_name VARCHAR(60) NOT NULL,
  module_name VARCHAR(80),
  action_type VARCHAR(50),
  target_name VARCHAR(100),
  detail VARCHAR(220),
  ip_address VARCHAR(50),
  status VARCHAR(30) DEFAULT 'SUCCESS',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO operation_log (operator_name, module_name, action_type, target_name, detail, ip_address, status, created_time, updated_time) VALUES
('系统管理员', '寿命预测', 'CREATE', 'PRED-119-001', '生成高速轴承寿命预测', '127.0.0.1', 'SUCCESS', NOW(), NOW()),
('系统管理员二号', '寿命预测', 'CREATE', 'PRED-119-002', '生成高速滤芯寿命预测', '127.0.0.1', 'SUCCESS', NOW(), NOW());
