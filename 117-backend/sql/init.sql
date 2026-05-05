DROP DATABASE IF EXISTS local_voucher_117;
CREATE DATABASE local_voucher_117 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE local_voucher_117;

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
('admin', '123456', '系统管理员', 'ADMIN', '平台管理组', '13800001701', 'admin@localvoucher.local', 1, NOW(), NOW()),
('merchant', '123456', '商户负责人', 'MERCHANT', '商户运营组', '13800001702', 'merchant@localvoucher.local', 1, NOW(), NOW()),
('cashier', '123456', '门店收银员', 'CASHIER', '门店核销组', '13800001703', 'cashier@localvoucher.local', 1, NOW(), NOW()),
('finance', '123456', '财务结算员', 'FINANCE', '结算财务组', '13800001704', 'finance@localvoucher.local', 1, NOW(), NOW());

CREATE TABLE merchant_info (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  merchant_name VARCHAR(120) NOT NULL,
  merchant_no VARCHAR(80) NOT NULL,
  industry_name VARCHAR(80),
  contact_name VARCHAR(60),
  contact_phone VARCHAR(30),
  settlement_cycle VARCHAR(40),
  status VARCHAR(30) DEFAULT 'PENDING',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO merchant_info (merchant_name, merchant_no, industry_name, contact_name, contact_phone, settlement_cycle, status, created_time, updated_time) VALUES
('湖滨轻食餐厅', 'MER-117-001', '餐饮美食', '陈店长', '13900001701', 'T+7', 'ACTIVE', NOW(), NOW()),
('城西轻食餐厅', 'MER-117-002', '休闲娱乐', '刘店长', '13900002701', 'T+7', 'ACTIVE', NOW(), NOW());

CREATE TABLE store_branch (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  branch_name VARCHAR(120) NOT NULL,
  branch_no VARCHAR(80) NOT NULL,
  merchant_name VARCHAR(120),
  city_name VARCHAR(80),
  business_district VARCHAR(80),
  address_text VARCHAR(180),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO store_branch (branch_name, branch_no, merchant_name, city_name, business_district, address_text, status, created_time, updated_time) VALUES
('湖滨轻食西湖店', 'STORE-117-001', '湖滨轻食餐厅', '杭州', '湖滨银泰', '延安路88号', 'ACTIVE', NOW(), NOW()),
('城西轻食西湖店', 'STORE-117-002', '城西轻食餐厅', '上海', '城西银泰', '延安路88号', 'ACTIVE', NOW(), NOW());

CREATE TABLE consumer_profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  consumer_name VARCHAR(80) NOT NULL,
  consumer_no VARCHAR(80) NOT NULL,
  phone VARCHAR(30),
  level_name VARCHAR(40),
  register_source VARCHAR(60),
  city_name VARCHAR(80),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO consumer_profile (consumer_name, consumer_no, phone, level_name, register_source, city_name, status, created_time, updated_time) VALUES
('王小满', 'CUS-117-001', '13900001702', '黄金会员', '小程序', '杭州', 'ACTIVE', NOW(), NOW()),
('李青禾', 'CUS-117-002', '13900002702', '黄金会员', 'App', '上海', 'ACTIVE', NOW(), NOW());

CREATE TABLE coupon_template (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  template_no VARCHAR(80) NOT NULL,
  coupon_name VARCHAR(120) NOT NULL,
  merchant_name VARCHAR(120),
  coupon_type VARCHAR(50),
  face_value DECIMAL(12,2) DEFAULT 0,
  threshold_amount DECIMAL(12,2) DEFAULT 0,
  valid_days INT DEFAULT 0,
  status VARCHAR(30) DEFAULT 'DRAFT',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO coupon_template (template_no, coupon_name, merchant_name, coupon_type, face_value, threshold_amount, valid_days, status, created_time, updated_time) VALUES
('TPL-117-001', '轻食套餐满减券', '湖滨轻食餐厅', '满减券', 30.00, 99.00, 30, 'PUBLISHED', NOW(), NOW()),
('TPL-117-002', '咖啡套餐满减券', '城西轻食餐厅', '满减券', 38.88, 107.88, 42, 'PUBLISHED', NOW(), NOW());

CREATE TABLE coupon_activity (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  activity_no VARCHAR(80) NOT NULL,
  activity_name VARCHAR(140) NOT NULL,
  merchant_name VARCHAR(120),
  coupon_name VARCHAR(120),
  start_date VARCHAR(30),
  end_date VARCHAR(30),
  issue_total INT DEFAULT 0,
  status VARCHAR(30) DEFAULT 'DRAFT',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO coupon_activity (activity_no, activity_name, merchant_name, coupon_name, start_date, end_date, issue_total, status, created_time, updated_time) VALUES
('ACT-117-001', '春日到店消费节', '湖滨轻食餐厅', '轻食套餐满减券', '2026-05-04', '2026-05-31', 3000, 'RUNNING', NOW(), NOW()),
('ACT-117-002', '春日到店消费节', '城西轻食餐厅', '咖啡套餐满减券', '2026-05-04', '2026-05-31', 3012, 'RUNNING', NOW(), NOW());

CREATE TABLE user_coupon (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  coupon_no VARCHAR(80) NOT NULL,
  consumer_name VARCHAR(80),
  coupon_name VARCHAR(120),
  receive_time VARCHAR(40),
  expire_time VARCHAR(40),
  source_channel VARCHAR(60),
  status VARCHAR(30) DEFAULT 'AVAILABLE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO user_coupon (coupon_no, consumer_name, coupon_name, receive_time, expire_time, source_channel, status, created_time, updated_time) VALUES
('UC-117-001', '王小满', '轻食套餐满减券', '2026-05-04 10:20', '2026-06-03 23:59', '小程序领券', 'AVAILABLE', NOW(), NOW()),
('UC-117-002', '李青禾', '咖啡套餐满减券', '2026-05-04 10:20', '2026-06-03 23:59', 'App领券', 'AVAILABLE', NOW(), NOW());

CREATE TABLE verification_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  verify_no VARCHAR(80) NOT NULL,
  coupon_no VARCHAR(80),
  store_name VARCHAR(120),
  cashier_name VARCHAR(60),
  consume_amount DECIMAL(12,2) DEFAULT 0,
  discount_amount DECIMAL(12,2) DEFAULT 0,
  verify_time VARCHAR(40),
  status VARCHAR(30) DEFAULT 'WAIT_CONFIRM',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO verification_record (verify_no, coupon_no, store_name, cashier_name, consume_amount, discount_amount, verify_time, status, created_time, updated_time) VALUES
('VER-117-001', 'UC-117-001', '湖滨轻食西湖店', '赵收银', 128.00, 30.00, '2026-05-04 12:10', 'CONFIRMED', NOW(), NOW()),
('VER-117-002', 'UC-117-002', '城西轻食西湖店', '钱收银', 136.88, 38.88, '2026-05-04 12:10', 'CONFIRMED', NOW(), NOW());

CREATE TABLE merchant_settlement (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  settlement_no VARCHAR(80) NOT NULL,
  merchant_name VARCHAR(120),
  settlement_cycle VARCHAR(40),
  verify_amount DECIMAL(12,2) DEFAULT 0,
  commission_amount DECIMAL(12,2) DEFAULT 0,
  payable_amount DECIMAL(12,2) DEFAULT 0,
  status VARCHAR(30) DEFAULT 'WAIT_CALCULATE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO merchant_settlement (settlement_no, merchant_name, settlement_cycle, verify_amount, commission_amount, payable_amount, status, created_time, updated_time) VALUES
('SET-117-001', '湖滨轻食餐厅', '2026-05-01~2026-05-07', 12800.00, 640.00, 12160.00, 'CALCULATED', NOW(), NOW()),
('SET-117-002', '城西轻食餐厅', '2026-05-01~2026-05-07', 12808.88, 648.88, 12168.88, 'CALCULATED', NOW(), NOW());

CREATE TABLE settlement_detail (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  detail_no VARCHAR(80) NOT NULL,
  settlement_no VARCHAR(80),
  verify_no VARCHAR(80),
  coupon_name VARCHAR(120),
  consume_amount DECIMAL(12,2) DEFAULT 0,
  subsidy_amount DECIMAL(12,2) DEFAULT 0,
  status VARCHAR(30) DEFAULT 'WAIT_CONFIRM',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO settlement_detail (detail_no, settlement_no, verify_no, coupon_name, consume_amount, subsidy_amount, status, created_time, updated_time) VALUES
('DET-117-001', 'SET-117-001', 'VER-117-001', '轻食套餐满减券', 128.00, 30.00, 'CONFIRMED', NOW(), NOW()),
('DET-117-002', 'SET-117-002', 'VER-117-002', '咖啡套餐满减券', 136.88, 38.88, 'CONFIRMED', NOW(), NOW());

CREATE TABLE payment_transfer (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  transfer_no VARCHAR(80) NOT NULL,
  settlement_no VARCHAR(80),
  merchant_name VARCHAR(120),
  transfer_amount DECIMAL(12,2) DEFAULT 0,
  bank_account VARCHAR(120),
  transfer_time VARCHAR(40),
  status VARCHAR(30) DEFAULT 'WAIT_SUBMIT',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO payment_transfer (transfer_no, settlement_no, merchant_name, transfer_amount, bank_account, transfer_time, status, created_time, updated_time) VALUES
('PAY-117-001', 'SET-117-001', '湖滨轻食餐厅', 12160.00, '6222001170001', '2026-05-08 10:00', 'SUBMITTED', NOW(), NOW()),
('PAY-117-002', 'SET-117-002', '城西轻食餐厅', 12168.88, '6222002170002', '2026-05-08 10:00', 'SUBMITTED', NOW(), NOW());

CREATE TABLE complaint_ticket (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  ticket_no VARCHAR(80) NOT NULL,
  consumer_name VARCHAR(80),
  merchant_name VARCHAR(120),
  issue_type VARCHAR(60),
  detail_text VARCHAR(220),
  handler_name VARCHAR(60),
  status VARCHAR(30) DEFAULT 'OPEN',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO complaint_ticket (ticket_no, consumer_name, merchant_name, issue_type, detail_text, handler_name, status, created_time, updated_time) VALUES
('TICKET-117-001', '王小满', '湖滨轻食餐厅', '核销争议', '用户反馈优惠券已扣减但门店未确认', '客服小周', 'ASSIGNED', NOW(), NOW()),
('TICKET-117-002', '李青禾', '城西轻食餐厅', '核销争议', '用户反馈优惠券已扣减但门店未确认', '客服小秦', 'ASSIGNED', NOW(), NOW());

CREATE TABLE activity_stat_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  stat_no VARCHAR(80) NOT NULL,
  activity_name VARCHAR(140),
  merchant_name VARCHAR(120),
  issue_count INT DEFAULT 0,
  receive_count INT DEFAULT 0,
  verify_count INT DEFAULT 0,
  gmv_amount DECIMAL(12,2) DEFAULT 0,
  status VARCHAR(30) DEFAULT 'DRAFT',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO activity_stat_record (stat_no, activity_name, merchant_name, issue_count, receive_count, verify_count, gmv_amount, status, created_time, updated_time) VALUES
('STAT-117-001', '春日到店消费节', '湖滨轻食餐厅', 3000, 1688, 642, 82200.00, 'PUBLISHED', NOW(), NOW()),
('STAT-117-002', '春日到店消费节', '城西轻食餐厅', 3012, 1700, 654, 82208.88, 'PUBLISHED', NOW(), NOW());

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
('系统管理员', '券模板', 'CREATE', 'TPL-117-001', '创建轻食套餐满减券', '127.0.0.1', 'SUCCESS', NOW(), NOW()),
('系统管理员二号', '券模板', 'CREATE', 'TPL-117-002', '创建咖啡套餐满减券', '127.0.0.1', 'SUCCESS', NOW(), NOW());
