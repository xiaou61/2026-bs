DROP DATABASE IF EXISTS cold_chain_114;
CREATE DATABASE cold_chain_114 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE cold_chain_114;

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
('admin', '123456', '系统管理员', 'ADMIN', '平台管理组', '13800001401', 'admin@cold.com', 1, NOW(), NOW()),
('dispatcher', '123456', '调度员', 'DISPATCHER', '冷链调度中心', '13800001402', 'dispatcher@cold.com', 1, NOW(), NOW()),
('carrier', '123456', '承运员', 'CARRIER', '绿色冷链物流', '13800001403', 'carrier@cold.com', 1, NOW(), NOW()),
('supervisor', '123456', '监管员', 'SUPERVISOR', '监管中心', '13800001404', 'supervisor@cold.com', 1, NOW(), NOW());

CREATE TABLE warehouse_node (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  node_name VARCHAR(100) NOT NULL,
  node_code VARCHAR(80) NOT NULL,
  node_type VARCHAR(40),
  region_name VARCHAR(100),
  manager_name VARCHAR(60),
  capacity_ton INT DEFAULT 0,
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO warehouse_node (node_name, node_code, node_type, region_name, manager_name, capacity_ton, status, created_time, updated_time) VALUES
('杭州冷链中心仓', 'WH-HZ-001', 'WAREHOUSE', '浙江杭州', '仓储经理', 800, 'ACTIVE', NOW(), NOW()),
('苏州生鲜分拨站', 'WH-SZ-002', 'HUB', '江苏苏州', '分拨经理', 360, 'ACTIVE', NOW(), NOW());

CREATE TABLE carrier_company (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  company_name VARCHAR(100) NOT NULL,
  company_code VARCHAR(80) NOT NULL,
  license_no VARCHAR(100),
  contact_name VARCHAR(60),
  contact_phone VARCHAR(30),
  vehicle_count INT DEFAULT 0,
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO carrier_company (company_name, company_code, license_no, contact_name, contact_phone, vehicle_count, status, created_time, updated_time) VALUES
('绿色冷链物流', 'CAR-001', 'LICE-COLD-001', '刘经理', '13900001401', 32, 'ACTIVE', NOW(), NOW()),
('鲜达运输公司', 'CAR-002', 'LICE-COLD-002', '王经理', '13900001402', 18, 'PENDING', NOW(), NOW());

CREATE TABLE cold_device (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  device_name VARCHAR(100) NOT NULL,
  device_no VARCHAR(80) NOT NULL,
  vehicle_no VARCHAR(60),
  owner_name VARCHAR(80),
  online_status VARCHAR(30),
  last_heartbeat VARCHAR(40),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO cold_device (device_name, device_no, vehicle_no, owner_name, online_status, last_heartbeat, status, created_time, updated_time) VALUES
('车载温控终端01', 'DEV-COLD-001', '浙A-COLD1', '绿色冷链物流', 'ONLINE', '2026-05-04 10:00', 'ACTIVE', NOW(), NOW()),
('冷柜传感器02', 'DEV-COLD-002', '苏E-COLD2', '鲜达运输公司', 'OFFLINE', '2026-05-04 10:05', 'ACTIVE', NOW(), NOW());

CREATE TABLE product_cargo (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  cargo_name VARCHAR(100) NOT NULL,
  cargo_no VARCHAR(80) NOT NULL,
  category_name VARCHAR(80),
  owner_name VARCHAR(100),
  temperature_range VARCHAR(60),
  cargo_weight INT DEFAULT 0,
  status VARCHAR(30) DEFAULT 'WAIT_SHIP',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO product_cargo (cargo_name, cargo_no, category_name, owner_name, temperature_range, cargo_weight, status, created_time, updated_time) VALUES
('进口三文鱼', 'CG-114-001', '水产', '生鲜贸易公司', '-18~-12℃', 1200, 'SHIPPED', NOW(), NOW()),
('低温鲜奶', 'CG-114-002', '乳制品', '牧场乳业', '2~6℃', 800, 'WAIT_SHIP', NOW(), NOW());

CREATE TABLE transport_order (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  order_no VARCHAR(80) NOT NULL,
  cargo_name VARCHAR(100),
  from_node VARCHAR(100),
  to_node VARCHAR(100),
  carrier_name VARCHAR(100),
  vehicle_no VARCHAR(60),
  status VARCHAR(30) DEFAULT 'DRAFT',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO transport_order (order_no, cargo_name, from_node, to_node, carrier_name, vehicle_no, status, created_time, updated_time) VALUES
('TO-114-001', '进口三文鱼', '杭州冷链中心仓', '上海门店01', '绿色冷链物流', '浙A-COLD1', 'DISPATCHED', NOW(), NOW()),
('TO-114-002', '低温鲜奶', '苏州生鲜分拨站', '南京门店02', '鲜达运输公司', '苏E-COLD2', 'DRAFT', NOW(), NOW());

CREATE TABLE temperature_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(80) NOT NULL,
  order_no VARCHAR(80),
  device_no VARCHAR(80),
  cargo_name VARCHAR(100),
  temperature_value INT DEFAULT 0,
  humidity_value INT DEFAULT 0,
  collect_time VARCHAR(40),
  status VARCHAR(30) DEFAULT 'NORMAL',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO temperature_record (record_no, order_no, device_no, cargo_name, temperature_value, humidity_value, collect_time, status, created_time, updated_time) VALUES
('TEMP-114-001', 'TO-114-001', 'DEV-COLD-001', '进口三文鱼', -15, 72, '2026-05-04 10:10', 'NORMAL', NOW(), NOW()),
('TEMP-114-002', 'TO-114-002', 'DEV-COLD-002', '低温鲜奶', 4, 65, '2026-05-04 10:15', 'WARNING', NOW(), NOW());

CREATE TABLE gps_track (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  track_no VARCHAR(80) NOT NULL,
  order_no VARCHAR(80),
  vehicle_no VARCHAR(60),
  location_name VARCHAR(120),
  longitude_text VARCHAR(40),
  latitude_text VARCHAR(40),
  speed_value INT DEFAULT 0,
  status VARCHAR(30) DEFAULT 'NORMAL',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO gps_track (track_no, order_no, vehicle_no, location_name, longitude_text, latitude_text, speed_value, status, created_time, updated_time) VALUES
('GPS-114-001', 'TO-114-001', '浙A-COLD1', '沪杭高速服务区', '120.2101', '30.2468', 68, 'NORMAL', NOW(), NOW()),
('GPS-114-002', 'TO-114-002', '苏E-COLD2', '苏州工业园区', '120.6519', '31.2989', 52, 'NORMAL', NOW(), NOW());

CREATE TABLE alert_rule (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  rule_name VARCHAR(100) NOT NULL,
  rule_code VARCHAR(80) NOT NULL,
  rule_type VARCHAR(40),
  threshold_value INT DEFAULT 0,
  owner_name VARCHAR(60),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO alert_rule (rule_name, rule_code, rule_type, threshold_value, owner_name, status, created_time, updated_time) VALUES
('冷冻温度上限告警', 'RULE-TEMP-HIGH', 'TEMP', -10, '调度员', 'ACTIVE', NOW(), NOW()),
('设备离线告警', 'RULE-OFFLINE', 'OFFLINE', 10, '监管员', 'ACTIVE', NOW(), NOW());

CREATE TABLE exception_alert (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  alert_no VARCHAR(80) NOT NULL,
  order_no VARCHAR(80),
  alert_type VARCHAR(40),
  cargo_name VARCHAR(100),
  severity_level VARCHAR(30),
  alert_time VARCHAR(40),
  detail_text VARCHAR(220),
  status VARCHAR(30) DEFAULT 'OPEN',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO exception_alert (alert_no, order_no, alert_type, cargo_name, severity_level, alert_time, detail_text, status, created_time, updated_time) VALUES
('ALERT-114-001', 'TO-114-001', 'TEMP', '进口三文鱼', 'HIGH', '2026-05-04 10:18', '温度超过上限3分钟', 'OPEN', NOW(), NOW()),
('ALERT-114-002', 'TO-114-002', 'OFFLINE', '低温鲜奶', 'MEDIUM', '2026-05-04 10:22', '设备心跳中断', 'ACKED', NOW(), NOW());

CREATE TABLE disposal_task (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  task_no VARCHAR(80) NOT NULL,
  alert_no VARCHAR(80),
  handler_name VARCHAR(60),
  measure_text VARCHAR(220),
  finish_time VARCHAR(40),
  status VARCHAR(30) DEFAULT 'WAIT_HANDLE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO disposal_task (task_no, alert_no, handler_name, measure_text, finish_time, status, created_time, updated_time) VALUES
('TASK-114-001', 'ALERT-114-001', '调度员', '联系司机检查制冷机组', '2026-05-04 11:00', 'PROCESSING', NOW(), NOW()),
('TASK-114-002', 'ALERT-114-002', '承运员', '更换备用温控设备', '2026-05-04 11:20', 'WAIT_HANDLE', NOW(), NOW());

CREATE TABLE responsibility_trace (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  trace_no VARCHAR(80) NOT NULL,
  order_no VARCHAR(80),
  responsible_party VARCHAR(100),
  node_name VARCHAR(100),
  reason_text VARCHAR(220),
  impact_scope VARCHAR(160),
  status VARCHAR(30) DEFAULT 'WAIT_REVIEW',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO responsibility_trace (trace_no, order_no, responsible_party, node_name, reason_text, impact_scope, status, created_time, updated_time) VALUES
('RESP-114-001', 'TO-114-001', '绿色冷链物流', '运输途中', '司机未及时处理温控告警', 'CG-114-001批次', 'REVIEWED', NOW(), NOW()),
('RESP-114-002', 'TO-114-002', '鲜达运输公司', '分拨站', '设备交接检查不完整', 'TO-114-002订单', 'WAIT_REVIEW', NOW(), NOW());

CREATE TABLE maintenance_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  maintenance_no VARCHAR(80) NOT NULL,
  device_no VARCHAR(80),
  maintainer_name VARCHAR(60),
  maintenance_type VARCHAR(40),
  result_status VARCHAR(30),
  detail_text VARCHAR(220),
  status VARCHAR(30) DEFAULT 'DRAFT',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO maintenance_record (maintenance_no, device_no, maintainer_name, maintenance_type, result_status, detail_text, status, created_time, updated_time) VALUES
('MAIN-114-001', 'DEV-COLD-001', '设备工程师', 'CHECK', 'PASS', '设备运行正常', 'FINISHED', NOW(), NOW()),
('MAIN-114-002', 'DEV-COLD-002', '运维工程师', 'CALIBRATE', 'PASS', '完成温度校准', 'DRAFT', NOW(), NOW());

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
('系统管理员', '运输订单', 'CREATE', 'TO-114-001', '创建运输订单', '127.0.0.1', 'SUCCESS', NOW(), NOW()),
('调度员', '异常告警', 'ACK', 'ALERT-114-001', '确认温控告警', '10.10.14.8', 'SUCCESS', NOW(), NOW());
