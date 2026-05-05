DROP DATABASE IF EXISTS cross_border_115;
CREATE DATABASE cross_border_115 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE cross_border_115;

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
('admin', '123456', '系统管理员', 'ADMIN', '平台管理组', '13800001501', 'admin@crossborder.local', 1, NOW(), NOW()),
('customs', '123456', '关务专员', 'CUSTOMS', '清关申报组', '13800001502', 'customs@crossborder.local', 1, NOW(), NOW()),
('finance', '123456', '财务结算员', 'FINANCE', '财务结算组', '13800001503', 'finance@crossborder.local', 1, NOW(), NOW()),
('operator', '123456', '运营人员', 'OPERATOR', '跨境运营组', '13800001504', 'operator@crossborder.local', 1, NOW(), NOW());

CREATE TABLE merchant_store (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  store_name VARCHAR(100) NOT NULL,
  store_code VARCHAR(80) NOT NULL,
  platform_name VARCHAR(80),
  company_name VARCHAR(120),
  country_region VARCHAR(80),
  contact_name VARCHAR(60),
  status VARCHAR(30) DEFAULT 'PENDING',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO merchant_store (store_name, store_code, platform_name, company_name, country_region, contact_name, status, created_time, updated_time) VALUES
('北美数码旗舰店', 'STORE-115-001', 'Amazon', '杭州跨贸科技有限公司', '美国', '林运营', 'ACTIVE', NOW(), NOW()),
('欧洲家居优选店', 'STORE-115-002', 'eBay', '宁波海贸供应链有限公司', '德国', '周运营', 'PENDING', NOW(), NOW());

CREATE TABLE customer_profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  customer_name VARCHAR(80) NOT NULL,
  customer_no VARCHAR(80) NOT NULL,
  country_region VARCHAR(80),
  email VARCHAR(100),
  phone VARCHAR(40),
  identity_no VARCHAR(100),
  status VARCHAR(30) DEFAULT 'NEW',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO customer_profile (customer_name, customer_no, country_region, email, phone, identity_no, status, created_time, updated_time) VALUES
('Emma Johnson', 'CUS-115-001', '美国', 'emma@example.com', '+1-202-555-0188', 'ID-US-8821', 'VERIFIED', NOW(), NOW()),
('Lukas Weber', 'CUS-115-002', '德国', 'lukas@example.de', '+49-30-555-0199', 'ID-DE-7712', 'NEW', NOW(), NOW());

CREATE TABLE product_sku (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  sku_no VARCHAR(80) NOT NULL,
  product_name VARCHAR(120) NOT NULL,
  category_name VARCHAR(80),
  hs_code VARCHAR(40),
  origin_country VARCHAR(80),
  declared_value DECIMAL(12,2) DEFAULT 0,
  status VARCHAR(30) DEFAULT 'DRAFT',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO product_sku (sku_no, product_name, category_name, hs_code, origin_country, declared_value, status, created_time, updated_time) VALUES
('SKU-115-001', '智能降噪耳机', '数码配件', '8518300000', '中国', 89.90, 'ACTIVE', NOW(), NOW()),
('SKU-115-002', '便携咖啡磨豆机', '家居用品', '8509409000', '中国', 46.50, 'DRAFT', NOW(), NOW());

CREATE TABLE cross_border_order (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  order_no VARCHAR(80) NOT NULL,
  store_name VARCHAR(100),
  customer_name VARCHAR(80),
  destination_country VARCHAR(80),
  currency_code VARCHAR(20),
  order_amount DECIMAL(12,2) DEFAULT 0,
  order_status VARCHAR(30),
  status VARCHAR(30) DEFAULT 'CREATED',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO cross_border_order (order_no, store_name, customer_name, destination_country, currency_code, order_amount, order_status, status, created_time, updated_time) VALUES
('CB-ORDER-115-001', '北美数码旗舰店', 'Emma Johnson', '美国', 'USD', 199.80, 'DECLARING', 'DECLARING', NOW(), NOW()),
('CB-ORDER-115-002', '欧洲家居优选店', 'Lukas Weber', '德国', 'EUR', 138.60, 'CREATED', 'CREATED', NOW(), NOW());

CREATE TABLE clearance_declaration (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  declaration_no VARCHAR(80) NOT NULL,
  order_no VARCHAR(80),
  customs_port VARCHAR(100),
  declare_type VARCHAR(50),
  declare_amount DECIMAL(12,2) DEFAULT 0,
  risk_level VARCHAR(30),
  status VARCHAR(30) DEFAULT 'DRAFT',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO clearance_declaration (declaration_no, order_no, customs_port, declare_type, declare_amount, risk_level, status, created_time, updated_time) VALUES
('DEC-115-001', 'CB-ORDER-115-001', '洛杉矶口岸', 'B2C', 199.80, 'LOW', 'SUBMITTED', NOW(), NOW()),
('DEC-115-002', 'CB-ORDER-115-002', '汉堡口岸', 'B2C', 138.60, 'MEDIUM', 'DRAFT', NOW(), NOW());

CREATE TABLE customs_document (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  document_no VARCHAR(80) NOT NULL,
  order_no VARCHAR(80),
  document_type VARCHAR(60),
  file_url VARCHAR(180),
  audit_user VARCHAR(60),
  audit_result VARCHAR(60),
  status VARCHAR(30) DEFAULT 'WAIT_AUDIT',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO customs_document (document_no, order_no, document_type, file_url, audit_user, audit_result, status, created_time, updated_time) VALUES
('DOC-115-001', 'CB-ORDER-115-001', 'INVOICE', 'https://cross.local/doc/115/001', '关务专员', '资料齐全', 'APPROVED', NOW(), NOW()),
('DOC-115-002', 'CB-ORDER-115-002', 'PACKING_LIST', 'https://cross.local/doc/115/002', '关务专员', '待补充箱单重量', 'WAIT_AUDIT', NOW(), NOW());

CREATE TABLE tax_fee_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  fee_no VARCHAR(80) NOT NULL,
  order_no VARCHAR(80),
  tax_type VARCHAR(60),
  tax_amount DECIMAL(12,2) DEFAULT 0,
  currency_code VARCHAR(20),
  pay_status VARCHAR(30),
  status VARCHAR(30) DEFAULT 'WAIT_PAY',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO tax_fee_record (fee_no, order_no, tax_type, tax_amount, currency_code, pay_status, status, created_time, updated_time) VALUES
('TAX-115-001', 'CB-ORDER-115-001', 'DUTY', 18.66, 'USD', 'PAID', 'PAID', NOW(), NOW()),
('TAX-115-002', 'CB-ORDER-115-002', 'VAT', 23.58, 'EUR', 'WAIT_PAY', 'WAIT_PAY', NOW(), NOW());

CREATE TABLE exchange_rate (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  rate_no VARCHAR(80) NOT NULL,
  base_currency VARCHAR(20),
  target_currency VARCHAR(20),
  rate_value DECIMAL(12,6) DEFAULT 0,
  effective_date VARCHAR(30),
  provider_name VARCHAR(100),
  status VARCHAR(30) DEFAULT 'DRAFT',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO exchange_rate (rate_no, base_currency, target_currency, rate_value, effective_date, provider_name, status, created_time, updated_time) VALUES
('RATE-115-001', 'USD', 'CNY', 7.120000, '2026-05-04', '中国银行', 'ACTIVE', NOW(), NOW()),
('RATE-115-002', 'EUR', 'CNY', 7.680000, '2026-05-04', '中国银行', 'ACTIVE', NOW(), NOW());

CREATE TABLE settlement_bill (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  bill_no VARCHAR(80) NOT NULL,
  merchant_name VARCHAR(100),
  currency_code VARCHAR(20),
  original_amount DECIMAL(12,2) DEFAULT 0,
  cny_amount DECIMAL(12,2) DEFAULT 0,
  settlement_status VARCHAR(30),
  status VARCHAR(30) DEFAULT 'WAIT_CALCULATE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO settlement_bill (bill_no, merchant_name, currency_code, original_amount, cny_amount, settlement_status, status, created_time, updated_time) VALUES
('BILL-115-001', '北美数码旗舰店', 'USD', 218.46, 1555.44, 'CALCULATED', 'CALCULATED', NOW(), NOW()),
('BILL-115-002', '欧洲家居优选店', 'EUR', 162.18, 1245.54, 'WAIT_CALCULATE', 'WAIT_CALCULATE', NOW(), NOW());

CREATE TABLE payment_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  payment_no VARCHAR(80) NOT NULL,
  bill_no VARCHAR(80),
  pay_channel VARCHAR(60),
  pay_amount DECIMAL(12,2) DEFAULT 0,
  currency_code VARCHAR(20),
  pay_time VARCHAR(40),
  status VARCHAR(30) DEFAULT 'WAIT_CONFIRM',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO payment_record (payment_no, bill_no, pay_channel, pay_amount, currency_code, pay_time, status, created_time, updated_time) VALUES
('PAY-115-001', 'BILL-115-001', 'BANK_TRANSFER', 1555.44, 'CNY', '2026-05-04 15:20', 'SUCCESS', NOW(), NOW()),
('PAY-115-002', 'BILL-115-002', 'PAYPAL', 162.18, 'EUR', '2026-05-04 16:30', 'WAIT_CONFIRM', NOW(), NOW());

CREATE TABLE logistics_track (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  tracking_no VARCHAR(80) NOT NULL,
  order_no VARCHAR(80),
  carrier_name VARCHAR(100),
  current_node VARCHAR(120),
  location_text VARCHAR(160),
  track_time VARCHAR(40),
  status VARCHAR(30) DEFAULT 'CREATED',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO logistics_track (tracking_no, order_no, carrier_name, current_node, location_text, track_time, status, created_time, updated_time) VALUES
('TRK-115-001', 'CB-ORDER-115-001', 'DHL', '洛杉矶清关仓', 'Los Angeles, CA', '2026-05-04 16:10', 'ARRIVED', NOW(), NOW()),
('TRK-115-002', 'CB-ORDER-115-002', 'UPS', '上海国际转运中心', 'Shanghai, CN', '2026-05-04 13:40', 'IN_TRANSIT', NOW(), NOW());

CREATE TABLE reconciliation_task (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  task_no VARCHAR(80) NOT NULL,
  bill_no VARCHAR(80),
  order_count INT DEFAULT 0,
  difference_amount DECIMAL(12,2) DEFAULT 0,
  check_result VARCHAR(80),
  status VARCHAR(30) DEFAULT 'WAIT_CHECK',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO reconciliation_task (task_no, bill_no, order_count, difference_amount, check_result, status, created_time, updated_time) VALUES
('REC-115-001', 'BILL-115-001', 32, 0.00, '账实一致', 'MATCHED', NOW(), NOW()),
('REC-115-002', 'BILL-115-002', 18, 12.40, '税费汇率差异待复核', 'DIFFERENCE', NOW(), NOW());

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
('系统管理员', '跨境订单', 'CREATE', 'CB-ORDER-115-001', '创建订单并进入清关申报', '127.0.0.1', 'SUCCESS', NOW(), NOW()),
('财务结算员', '结算账单', 'CALCULATE', 'BILL-115-001', '按USD/CNY牌价折算人民币金额', '10.10.15.8', 'SUCCESS', NOW(), NOW());
