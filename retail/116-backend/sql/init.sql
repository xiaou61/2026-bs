DROP DATABASE IF EXISTS live_commerce_116;
CREATE DATABASE live_commerce_116 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE live_commerce_116;

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
('admin', '123456', '系统管理员', 'ADMIN', '平台管理组', '13800001601', 'admin@livecommerce.local', 1, NOW(), NOW()),
('operator', '123456', '主播运营', 'OPERATOR', '直播运营组', '13800001602', 'operator@livecommerce.local', 1, NOW(), NOW()),
('service', '123456', '客服专员', 'CUSTOMER_SERVICE', '售后客服组', '13800001603', 'service@livecommerce.local', 1, NOW(), NOW()),
('merchant', '123456', '商家代表', 'MERCHANT', '商家协同组', '13800001604', 'merchant@livecommerce.local', 1, NOW(), NOW());

CREATE TABLE live_channel (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  channel_name VARCHAR(100) NOT NULL,
  platform_name VARCHAR(60),
  account_no VARCHAR(80) NOT NULL,
  owner_name VARCHAR(60),
  fans_count INT DEFAULT 0,
  contact_phone VARCHAR(30),
  status VARCHAR(30) DEFAULT 'PENDING',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO live_channel (channel_name, platform_name, account_no, owner_name, fans_count, contact_phone, status, created_time, updated_time) VALUES
('美妆优选直播间', '抖音', 'LIVE-116-001', '李运营', 86000, '13900001601', 'ACTIVE', NOW(), NOW()),
('美妆优选直播间二号', '快手', 'LIVE-116-002', '王运营', 86008, '13900002601', 'ACTIVE', NOW(), NOW());

CREATE TABLE anchor_profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  anchor_name VARCHAR(80) NOT NULL,
  anchor_no VARCHAR(80) NOT NULL,
  special_category VARCHAR(80),
  level_name VARCHAR(40),
  commission_rate DECIMAL(8,2) DEFAULT 0,
  phone VARCHAR(30),
  status VARCHAR(30) DEFAULT 'PENDING',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO anchor_profile (anchor_name, anchor_no, special_category, level_name, commission_rate, phone, status, created_time, updated_time) VALUES
('林小雅', 'ANCHOR-116-001', '美妆护肤', '金牌主播', 12.50, '13900001602', 'CERTIFIED', NOW(), NOW()),
('林小雅二号', 'ANCHOR-116-002', '食品饮料', '金牌主播', 22.50, '13900002602', 'CERTIFIED', NOW(), NOW());

CREATE TABLE supplier_brand (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  supplier_name VARCHAR(120) NOT NULL,
  brand_name VARCHAR(100),
  contact_name VARCHAR(60),
  contact_phone VARCHAR(30),
  qualification_level VARCHAR(40),
  cooperation_mode VARCHAR(60),
  status VARCHAR(30) DEFAULT 'WAIT_AUDIT',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO supplier_brand (supplier_name, brand_name, contact_name, contact_phone, qualification_level, cooperation_mode, status, created_time, updated_time) VALUES
('杭州花漾美妆有限公司', '花漾肌密', '陈商务', '13900001603', 'A', '佣金带货', 'ACTIVE', NOW(), NOW()),
('杭州花漾美妆有限公司二号', '花漾肌密', '赵商务', '13900002603', 'A', '佣金带货', 'ACTIVE', NOW(), NOW());

CREATE TABLE product_selection (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  selection_no VARCHAR(80) NOT NULL,
  product_name VARCHAR(120) NOT NULL,
  brand_name VARCHAR(100),
  category_name VARCHAR(80),
  supply_price DECIMAL(12,2) DEFAULT 0,
  commission_rate DECIMAL(8,2) DEFAULT 0,
  status VARCHAR(30) DEFAULT 'DRAFT',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO product_selection (selection_no, product_name, brand_name, category_name, supply_price, commission_rate, status, created_time, updated_time) VALUES
('SEL-116-001', '玻尿酸精华套装', '花漾肌密', '美妆护肤', 89.00, 18.00, 'SHORTLISTED', NOW(), NOW()),
('SEL-116-002', '玻尿酸精华套装', '花漾肌密', '食品饮料', 99.00, 28.00, 'SHORTLISTED', NOW(), NOW());

CREATE TABLE live_session (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  session_no VARCHAR(80) NOT NULL,
  session_title VARCHAR(140) NOT NULL,
  channel_name VARCHAR(100),
  anchor_name VARCHAR(80),
  start_time VARCHAR(40),
  target_gmv DECIMAL(12,2) DEFAULT 0,
  status VARCHAR(30) DEFAULT 'PLANNED',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO live_session (session_no, session_title, channel_name, anchor_name, start_time, target_gmv, status, created_time, updated_time) VALUES
('SESSION-116-001', '夏季美妆爆品专场', '美妆优选直播间', '林小雅', '2026-05-06 20:00', 180000.00, 'SCHEDULED', NOW(), NOW()),
('SESSION-116-002', '夏季美妆爆品专场', '美妆优选直播间', '周一然', '2026-05-06 20:00', 180010.00, 'SCHEDULED', NOW(), NOW());

CREATE TABLE schedule_plan (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  plan_no VARCHAR(80) NOT NULL,
  session_title VARCHAR(140),
  plan_date VARCHAR(30),
  anchor_name VARCHAR(80),
  product_count INT DEFAULT 0,
  owner_name VARCHAR(60),
  status VARCHAR(30) DEFAULT 'DRAFT',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO schedule_plan (plan_no, session_title, plan_date, anchor_name, product_count, owner_name, status, created_time, updated_time) VALUES
('PLAN-116-001', '夏季美妆爆品专场', '2026-05-06', '林小雅', 18, '李运营', 'CONFIRMED', NOW(), NOW()),
('PLAN-116-002', '夏季美妆爆品专场', '2026-05-06', '周一然', 26, '王运营', 'CONFIRMED', NOW(), NOW());

CREATE TABLE sample_request (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  request_no VARCHAR(80) NOT NULL,
  product_name VARCHAR(120),
  supplier_name VARCHAR(120),
  receiver_name VARCHAR(60),
  express_no VARCHAR(80),
  request_date VARCHAR(30),
  status VARCHAR(30) DEFAULT 'APPLIED',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO sample_request (request_no, product_name, supplier_name, receiver_name, express_no, request_date, status, created_time, updated_time) VALUES
('SAMPLE-116-001', '玻尿酸精华套装', '杭州花漾美妆有限公司', '林小雅', 'SF1160001', '2026-05-04', 'SENT', NOW(), NOW()),
('SAMPLE-116-002', '玻尿酸精华套装', '杭州花漾美妆有限公司', '周一然', 'SF1160002', '2026-05-04', 'SENT', NOW(), NOW());

CREATE TABLE promotion_script (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  script_no VARCHAR(80) NOT NULL,
  product_name VARCHAR(120),
  main_selling_point VARCHAR(180),
  coupon_text VARCHAR(160),
  review_user VARCHAR(60),
  version_no VARCHAR(40),
  status VARCHAR(30) DEFAULT 'DRAFT',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO promotion_script (script_no, product_name, main_selling_point, coupon_text, review_user, version_no, status, created_time, updated_time) VALUES
('SCRIPT-116-001', '玻尿酸精华套装', '高保湿修护，直播专享组合', '前500单立减30元', '李运营', 'v1.0', 'APPROVED', NOW(), NOW()),
('SCRIPT-116-002', '玻尿酸精华套装', '高保湿修护，直播专享组合', '前500单立减30元', '王运营', 'v1.0', 'APPROVED', NOW(), NOW());

CREATE TABLE live_order (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  order_no VARCHAR(80) NOT NULL,
  session_title VARCHAR(140),
  product_name VARCHAR(120),
  buyer_name VARCHAR(80),
  order_amount DECIMAL(12,2) DEFAULT 0,
  pay_channel VARCHAR(60),
  status VARCHAR(30) DEFAULT 'PAID',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO live_order (order_no, session_title, product_name, buyer_name, order_amount, pay_channel, status, created_time, updated_time) VALUES
('ORDER-116-001', '夏季美妆爆品专场', '玻尿酸精华套装', '用户A', 129.00, '微信支付', 'PAID', NOW(), NOW()),
('ORDER-116-002', '夏季美妆爆品专场', '玻尿酸精华套装', '用户B', 139.00, '支付宝', 'PAID', NOW(), NOW());

CREATE TABLE after_sale_ticket (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  ticket_no VARCHAR(80) NOT NULL,
  order_no VARCHAR(80),
  customer_name VARCHAR(80),
  issue_type VARCHAR(60),
  service_user VARCHAR(60),
  solution_text VARCHAR(220),
  status VARCHAR(30) DEFAULT 'OPEN',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO after_sale_ticket (ticket_no, order_no, customer_name, issue_type, service_user, solution_text, status, created_time, updated_time) VALUES
('TICKET-116-001', 'ORDER-116-001', '用户A', '仅退款', '客服小周', '核对订单后进入退款流程', 'ASSIGNED', NOW(), NOW()),
('TICKET-116-002', 'ORDER-116-002', '用户B', '仅退款', '客服小秦', '核对订单后进入退款流程', 'ASSIGNED', NOW(), NOW());

CREATE TABLE refund_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  refund_no VARCHAR(80) NOT NULL,
  ticket_no VARCHAR(80),
  order_no VARCHAR(80),
  refund_amount DECIMAL(12,2) DEFAULT 0,
  reason_text VARCHAR(180),
  audit_user VARCHAR(60),
  status VARCHAR(30) DEFAULT 'WAIT_AUDIT',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO refund_record (refund_no, ticket_no, order_no, refund_amount, reason_text, audit_user, status, created_time, updated_time) VALUES
('REFUND-116-001', 'TICKET-116-001', 'ORDER-116-001', 129.00, '客户申请仅退款', '客服主管', 'APPROVED', NOW(), NOW()),
('REFUND-116-002', 'TICKET-116-002', 'ORDER-116-002', 139.00, '客户申请仅退款', '客服经理', 'APPROVED', NOW(), NOW());

CREATE TABLE anchor_performance (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  performance_no VARCHAR(80) NOT NULL,
  anchor_name VARCHAR(80),
  session_title VARCHAR(140),
  gmv_amount DECIMAL(12,2) DEFAULT 0,
  order_count INT DEFAULT 0,
  conversion_rate DECIMAL(8,2) DEFAULT 0,
  status VARCHAR(30) DEFAULT 'DRAFT',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO anchor_performance (performance_no, anchor_name, session_title, gmv_amount, order_count, conversion_rate, status, created_time, updated_time) VALUES
('PERF-116-001', '林小雅', '夏季美妆爆品专场', 216000.00, 1680, 8.60, 'CONFIRMED', NOW(), NOW()),
('PERF-116-002', '周一然', '夏季美妆爆品专场', 216010.00, 1688, 18.60, 'CONFIRMED', NOW(), NOW());

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
('系统管理员', '直播场次', 'CREATE', 'SESSION-116-001', '创建直播专场排期', '127.0.0.1', 'SUCCESS', NOW(), NOW()),
('系统管理员二号', '直播场次', 'CREATE', 'SESSION-116-002', '创建直播专场排期', '127.0.0.1', 'SUCCESS', NOW(), NOW());
