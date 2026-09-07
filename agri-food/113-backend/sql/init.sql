DROP DATABASE IF EXISTS agri_trace_113;
CREATE DATABASE agri_trace_113 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE agri_trace_113;

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
('admin', '123456', '系统管理员', 'ADMIN', '平台管理组', '13800001301', 'admin@trace.com', 1, NOW(), NOW()),
('regulator', '123456', '监管员', 'REGULATOR', '市场监管局', '13800001302', 'regulator@trace.com', 1, NOW(), NOW()),
('farmer', '123456', '农户', 'FARMER', '东山合作社', '13800001303', 'farmer@trace.com', 1, NOW(), NOW()),
('inspector', '123456', '质检员', 'INSPECTOR', '质检中心', '13800001304', 'inspector@trace.com', 1, NOW(), NOW());

CREATE TABLE farm_base (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  base_name VARCHAR(100) NOT NULL,
  base_code VARCHAR(80) NOT NULL,
  region_name VARCHAR(100),
  area_mu INT DEFAULT 0,
  owner_name VARCHAR(60),
  cert_status VARCHAR(30),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO farm_base (base_name, base_code, region_name, area_mu, owner_name, cert_status, status, created_time, updated_time) VALUES
('东山有机蔬菜基地', 'BASE-113-001', '浙江杭州', 320, '陈农场主', 'CERTIFIED', 'ACTIVE', NOW(), NOW()),
('南湖生态稻田', 'BASE-113-002', '江苏苏州', 580, '周合作社', 'PENDING', 'ACTIVE', NOW(), NOW());

CREATE TABLE farmer_profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  farmer_name VARCHAR(80) NOT NULL,
  farmer_no VARCHAR(60) NOT NULL,
  cooperative_name VARCHAR(100),
  region_name VARCHAR(100),
  phone VARCHAR(30),
  credit_level VARCHAR(20),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO farmer_profile (farmer_name, farmer_no, cooperative_name, region_name, phone, credit_level, status, created_time, updated_time) VALUES
('陈农场主', 'FARMER-001', '东山合作社', '浙江杭州', '13900001301', 'A', 'ACTIVE', NOW(), NOW()),
('周合作社', 'FARMER-002', '南湖合作社', '江苏苏州', '13900001302', 'B', 'ACTIVE', NOW(), NOW());

CREATE TABLE product_category (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  category_name VARCHAR(100) NOT NULL,
  category_code VARCHAR(80) NOT NULL,
  standard_name VARCHAR(120),
  inspection_rule VARCHAR(200),
  owner_name VARCHAR(60),
  risk_level VARCHAR(30),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO product_category (category_name, category_code, standard_name, inspection_rule, owner_name, risk_level, status, created_time, updated_time) VALUES
('有机番茄', 'CAT-TOMATO', '绿色食品A级', '农残抽检合格', '质检员', 'LOW', 'ACTIVE', NOW(), NOW()),
('生态大米', 'CAT-RICE', '有机产品认证', '重金属和农残检测', '监管员', 'MEDIUM', 'ACTIVE', NOW(), NOW());

CREATE TABLE product_batch (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  batch_no VARCHAR(80) NOT NULL,
  product_name VARCHAR(100) NOT NULL,
  base_name VARCHAR(100),
  farmer_name VARCHAR(80),
  harvest_date VARCHAR(30),
  trace_code VARCHAR(100),
  chain_status VARCHAR(30),
  status VARCHAR(30) DEFAULT 'PLANTED',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO product_batch (batch_no, product_name, base_name, farmer_name, harvest_date, trace_code, chain_status, status, created_time, updated_time) VALUES
('BATCH-113-001', '东山有机番茄', '东山有机蔬菜基地', '陈农场主', '2026-05-01', 'TRACE-113-001', 'CHAINED', 'CHAINED', NOW(), NOW()),
('BATCH-113-002', '南湖生态大米', '南湖生态稻田', '周合作社', '2026-05-03', 'TRACE-113-002', 'WAIT_CHAIN', 'PLANTED', NOW(), NOW());

CREATE TABLE planting_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(80) NOT NULL,
  batch_no VARCHAR(80),
  operation_type VARCHAR(40),
  operation_date VARCHAR(30),
  operator_name VARCHAR(60),
  detail_text VARCHAR(220),
  status VARCHAR(30) DEFAULT 'RECORDED',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO planting_record (record_no, batch_no, operation_type, operation_date, operator_name, detail_text, status, created_time, updated_time) VALUES
('PLANT-113-001', 'BATCH-113-001', 'FERTILIZE', '2026-04-20', '陈农场主', '使用有机肥并完成拍照留痕', 'CONFIRMED', NOW(), NOW()),
('PLANT-113-002', 'BATCH-113-002', 'HARVEST', '2026-05-03', '周合作社', '采收后送检', 'RECORDED', NOW(), NOW());

CREATE TABLE input_material (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  material_name VARCHAR(100) NOT NULL,
  material_no VARCHAR(80) NOT NULL,
  batch_no VARCHAR(80),
  supplier_name VARCHAR(100),
  use_amount INT DEFAULT 0,
  audit_status VARCHAR(30),
  status VARCHAR(30) DEFAULT 'RECORDED',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO input_material (material_name, material_no, batch_no, supplier_name, use_amount, audit_status, status, created_time, updated_time) VALUES
('有机复合肥', 'MAT-113-001', 'BATCH-113-001', '绿色农资公司', 80, 'APPROVED', 'APPROVED', NOW(), NOW()),
('水稻良种', 'MAT-113-002', 'BATCH-113-002', '良种供应站', 120, 'WAIT_AUDIT', 'RECORDED', NOW(), NOW());

CREATE TABLE quality_inspection (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  report_no VARCHAR(80) NOT NULL,
  batch_no VARCHAR(80),
  product_name VARCHAR(100),
  inspector_name VARCHAR(60),
  inspection_date VARCHAR(30),
  result_status VARCHAR(30),
  report_url VARCHAR(160),
  status VARCHAR(30) DEFAULT 'DRAFT',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO quality_inspection (report_no, batch_no, product_name, inspector_name, inspection_date, result_status, report_url, status, created_time, updated_time) VALUES
('QI-113-001', 'BATCH-113-001', '东山有机番茄', '王质检', '2026-05-02', 'PASS', 'https://trace.local/report/001', 'PUBLISHED', NOW(), NOW()),
('QI-113-002', 'BATCH-113-002', '南湖生态大米', '赵质检', '2026-05-04', 'WARNING', 'https://trace.local/report/002', 'DRAFT', NOW(), NOW());

CREATE TABLE chain_block (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  block_no VARCHAR(80) NOT NULL,
  batch_no VARCHAR(80),
  tx_hash VARCHAR(160),
  data_hash VARCHAR(160),
  block_height INT DEFAULT 0,
  chain_time VARCHAR(30),
  status VARCHAR(30) DEFAULT 'CONFIRMED',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO chain_block (block_no, batch_no, tx_hash, data_hash, block_height, chain_time, status, created_time, updated_time) VALUES
('BLOCK-113-001', 'BATCH-113-001', '0x113abc001', 'HASH-PLANT-001', 113001, '2026-05-02 10:20', 'CONFIRMED', NOW(), NOW()),
('BLOCK-113-002', 'BATCH-113-002', '0x113abc002', 'HASH-QI-002', 113002, '2026-05-04 09:30', 'PENDING', NOW(), NOW());

CREATE TABLE trace_node (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  node_name VARCHAR(100) NOT NULL,
  node_code VARCHAR(80) NOT NULL,
  node_type VARCHAR(40),
  region_name VARCHAR(100),
  contact_name VARCHAR(60),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO trace_node (node_name, node_code, node_type, region_name, contact_name, status, created_time, updated_time) VALUES
('杭州冷链仓', 'NODE-HZ-001', 'WAREHOUSE', '浙江杭州', '仓储经理', 'ACTIVE', NOW(), NOW()),
('苏州农贸批发中心', 'NODE-SZ-002', 'WHOLESALE', '江苏苏州', '市场经理', 'ACTIVE', NOW(), NOW());

CREATE TABLE logistics_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  logistics_no VARCHAR(80) NOT NULL,
  batch_no VARCHAR(80),
  from_node VARCHAR(100),
  to_node VARCHAR(100),
  carrier_name VARCHAR(100),
  temperature_text VARCHAR(80),
  status VARCHAR(30) DEFAULT 'IN_TRANSIT',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO logistics_record (logistics_no, batch_no, from_node, to_node, carrier_name, temperature_text, status, created_time, updated_time) VALUES
('LOG-113-001', 'BATCH-113-001', '东山有机蔬菜基地', '杭州冷链仓', '绿色冷链物流', '4-8℃', 'ARRIVED', NOW(), NOW()),
('LOG-113-002', 'BATCH-113-002', '南湖生态稻田', '苏州农贸批发中心', '城市配送公司', '常温', 'IN_TRANSIT', NOW(), NOW());

CREATE TABLE recall_event (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  recall_no VARCHAR(80) NOT NULL,
  batch_no VARCHAR(80),
  product_name VARCHAR(100),
  reason_text VARCHAR(220),
  risk_level VARCHAR(30),
  handler_name VARCHAR(60),
  status VARCHAR(30) DEFAULT 'OPEN',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO recall_event (recall_no, batch_no, product_name, reason_text, risk_level, handler_name, status, created_time, updated_time) VALUES
('RECALL-113-001', 'BATCH-113-001', '东山有机番茄', '标签信息异常需复核', 'MEDIUM', '监管员', 'PROCESSING', NOW(), NOW()),
('RECALL-113-002', 'BATCH-113-002', '南湖生态大米', '抽检指标预警', 'HIGH', '质检员', 'OPEN', NOW(), NOW());

CREATE TABLE regulation_check (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  check_no VARCHAR(80) NOT NULL,
  target_name VARCHAR(100),
  check_type VARCHAR(40),
  regulator_name VARCHAR(60),
  check_date VARCHAR(30),
  result_status VARCHAR(30),
  rectify_text VARCHAR(220),
  status VARCHAR(30) DEFAULT 'DRAFT',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO regulation_check (check_no, target_name, check_type, regulator_name, check_date, result_status, rectify_text, status, created_time, updated_time) VALUES
('CHECK-113-001', '东山有机蔬菜基地', 'ROUTINE', '监管员', '2026-05-04', 'PASS', '资料齐全', 'PUBLISHED', NOW(), NOW()),
('CHECK-113-002', '杭州冷链仓', 'RANDOM', '市场管理员', '2026-05-05', 'RECTIFY', '补充冷链温控记录', 'DRAFT', NOW(), NOW());

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
('系统管理员', '产品批次', 'CREATE', 'BATCH-113-001', '创建产品批次', '127.0.0.1', 'SUCCESS', NOW(), NOW()),
('监管员', '区块存证', 'CHAIN', 'BLOCK-113-001', '写入链上存证', '10.10.13.8', 'SUCCESS', NOW(), NOW());
