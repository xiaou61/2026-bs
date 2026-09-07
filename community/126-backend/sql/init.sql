DROP DATABASE IF EXISTS home_energy_126;
CREATE DATABASE home_energy_126 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE home_energy_126;

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
('admin', '123456', '系统管理员', 'ADMIN', '能源服务中心', '13800012601', 'admin@homeenergy.local', 1, NOW(), NOW()),
('resident', '123456', '家庭用户', 'RESIDENT', '居民家庭', '13800012602', 'resident@homeenergy.local', 1, NOW(), NOW()),
('analyst', '123456', '能耗分析师', 'ANALYST', '节能分析组', '13800012603', 'analyst@homeenergy.local', 1, NOW(), NOW()),
('maintainer', '123456', '维修员', 'MAINTAINER', '设备维修组', '13800012604', 'maintainer@homeenergy.local', 1, NOW(), NOW());

CREATE TABLE home_profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  home_no VARCHAR(180),
  owner_name VARCHAR(180),
  city_name VARCHAR(180),
  community_name VARCHAR(180),
  area_size DECIMAL(12,2),
  member_count INT,
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO home_profile (home_no, owner_name, city_name, community_name, area_size, member_count, status, created_time, updated_time) VALUES
('HOME-126-001', '陈家庭', '杭州', '绿城花园', 96.5, 3, 'ACTIVE', NOW(), NOW()),
('HOME-126-002', '刘家庭', '苏州', '滨江家园', 128.0, 4, 'ACTIVE', NOW(), NOW());

CREATE TABLE family_member (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  member_no VARCHAR(180),
  home_no VARCHAR(180),
  member_name VARCHAR(180),
  relation_type VARCHAR(180),
  phone VARCHAR(180),
  usage_preference VARCHAR(180),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO family_member (member_no, home_no, member_name, relation_type, phone, usage_preference, status, created_time, updated_time) VALUES
('MEM-126-001', 'HOME-126-001', '陈先生', '户主', '13800012605', '舒适优先', 'ACTIVE', NOW(), NOW()),
('MEM-126-002', 'HOME-126-002', '刘女士', '配偶', '13800012606', '节能优先', 'ACTIVE', NOW(), NOW());

CREATE TABLE smart_meter (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  meter_no VARCHAR(180),
  home_no VARCHAR(180),
  install_location VARCHAR(180),
  communication_type VARCHAR(180),
  ratio_value DECIMAL(12,2),
  last_heartbeat VARCHAR(180),
  status VARCHAR(30) DEFAULT 'NORMAL',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO smart_meter (meter_no, home_no, install_location, communication_type, ratio_value, last_heartbeat, status, created_time, updated_time) VALUES
('METER-126-001', 'HOME-126-001', '入户电箱', 'NB-IoT', 1.0, '2026-05-05 16:00', 'NORMAL', NOW(), NOW()),
('METER-126-002', 'HOME-126-002', '地下电井', '4G', 1.0, '2026-05-05 16:05', 'NORMAL', NOW(), NOW());

CREATE TABLE electric_device (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  device_no VARCHAR(180),
  home_no VARCHAR(180),
  device_name VARCHAR(180),
  device_type VARCHAR(180),
  rated_power DECIMAL(12,2),
  room_name VARCHAR(180),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO electric_device (device_no, home_no, device_name, device_type, rated_power, room_name, status, created_time, updated_time) VALUES
('DEV-126-001', 'HOME-126-001', '客厅空调', '空调', 2.2, '客厅', 'ACTIVE', NOW(), NOW()),
('DEV-126-002', 'HOME-126-002', '电热水器', '热水器', 3.0, '卫生间', 'ACTIVE', NOW(), NOW());

CREATE TABLE energy_reading (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  reading_no VARCHAR(180),
  meter_no VARCHAR(180),
  collect_time VARCHAR(180),
  total_kwh DECIMAL(12,2),
  peak_kwh DECIMAL(12,2),
  valley_kwh DECIMAL(12,2),
  status VARCHAR(30) DEFAULT 'NORMAL',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO energy_reading (reading_no, meter_no, collect_time, total_kwh, peak_kwh, valley_kwh, status, created_time, updated_time) VALUES
('READ-126-001', 'METER-126-001', '2026-05-05 16:00', 386.5, 126.2, 88.4, 'NORMAL', NOW(), NOW()),
('READ-126-002', 'METER-126-002', '2026-05-05 16:05', 512.8, 188.6, 102.3, 'NORMAL', NOW(), NOW());

CREATE TABLE electricity_bill (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  bill_no VARCHAR(180),
  home_no VARCHAR(180),
  bill_month VARCHAR(180),
  energy_kwh DECIMAL(12,2),
  bill_amount DECIMAL(12,2),
  pay_method VARCHAR(180),
  status VARCHAR(30) DEFAULT 'CREATED',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO electricity_bill (bill_no, home_no, bill_month, energy_kwh, bill_amount, pay_method, status, created_time, updated_time) VALUES
('BILL-126-001', 'HOME-126-001', '2026-05', 386.5, 246.8, '微信', 'CREATED', NOW(), NOW()),
('BILL-126-002', 'HOME-126-002', '2026-05', 512.8, 328.5, '支付宝', 'CREATED', NOW(), NOW());

CREATE TABLE device_usage (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  usage_no VARCHAR(180),
  device_name VARCHAR(180),
  usage_date VARCHAR(180),
  energy_kwh DECIMAL(12,2),
  runtime_hour DECIMAL(12,2),
  energy_ratio DECIMAL(12,2),
  status VARCHAR(30) DEFAULT 'NORMAL',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO device_usage (usage_no, device_name, usage_date, energy_kwh, runtime_hour, energy_ratio, status, created_time, updated_time) VALUES
('USE-126-001', '客厅空调', '2026-05-05', 12.6, 6.5, 32.5, 'NORMAL', NOW(), NOW()),
('USE-126-002', '电热水器', '2026-05-05', 8.4, 3.2, 21.7, 'NORMAL', NOW(), NOW());

CREATE TABLE energy_budget (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  budget_no VARCHAR(180),
  home_no VARCHAR(180),
  budget_month VARCHAR(180),
  budget_kwh DECIMAL(12,2),
  budget_amount DECIMAL(12,2),
  owner_name VARCHAR(180),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO energy_budget (budget_no, home_no, budget_month, budget_kwh, budget_amount, owner_name, status, created_time, updated_time) VALUES
('BUD-126-001', 'HOME-126-001', '2026-06', 420.0, 280.0, '陈先生', 'ACTIVE', NOW(), NOW()),
('BUD-126-002', 'HOME-126-002', '2026-06', 520.0, 360.0, '刘女士', 'ACTIVE', NOW(), NOW());

CREATE TABLE saving_suggestion (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  suggestion_no VARCHAR(180),
  home_no VARCHAR(180),
  device_name VARCHAR(180),
  suggestion_type VARCHAR(180),
  saving_kwh DECIMAL(12,2),
  analyst_name VARCHAR(180),
  status VARCHAR(30) DEFAULT 'CREATED',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO saving_suggestion (suggestion_no, home_no, device_name, suggestion_type, saving_kwh, analyst_name, status, created_time, updated_time) VALUES
('SUG-126-001', 'HOME-126-001', '客厅空调', '温度优化', 36.5, '张分析', 'CREATED', NOW(), NOW()),
('SUG-126-002', 'HOME-126-002', '电热水器', '错峰使用', 28.0, '李分析', 'CREATED', NOW(), NOW());

CREATE TABLE abnormal_alert (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  alert_no VARCHAR(180),
  home_no VARCHAR(180),
  device_name VARCHAR(180),
  alert_level VARCHAR(180),
  alert_reason VARCHAR(180),
  handler_name VARCHAR(180),
  status VARCHAR(30) DEFAULT 'OPEN',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO abnormal_alert (alert_no, home_no, device_name, alert_level, alert_reason, handler_name, status, created_time, updated_time) VALUES
('ALT-126-001', 'HOME-126-001', '客厅空调', '高', '夜间耗电异常', '张分析', 'OPEN', NOW(), NOW()),
('ALT-126-002', 'HOME-126-002', '电热水器', '中', '连续高负载', '李分析', 'OPEN', NOW(), NOW());

CREATE TABLE carbon_statistic (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  stat_no VARCHAR(180),
  home_no VARCHAR(180),
  stat_month VARCHAR(180),
  energy_kwh DECIMAL(12,2),
  carbon_kg DECIMAL(12,2),
  year_rate DECIMAL(12,2),
  status VARCHAR(30) DEFAULT 'FINISHED',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO carbon_statistic (stat_no, home_no, stat_month, energy_kwh, carbon_kg, year_rate, status, created_time, updated_time) VALUES
('CAR-126-001', 'HOME-126-001', '2026-05', 386.5, 221.4, -6.2, 'FINISHED', NOW(), NOW()),
('CAR-126-002', 'HOME-126-002', '2026-05', 512.8, 293.7, 3.8, 'FINISHED', NOW(), NOW());

CREATE TABLE maintenance_ticket (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  ticket_no VARCHAR(180),
  home_no VARCHAR(180),
  device_name VARCHAR(180),
  issue_type VARCHAR(180),
  maintainer_name VARCHAR(180),
  appointment_time VARCHAR(180),
  status VARCHAR(30) DEFAULT 'OPEN',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO maintenance_ticket (ticket_no, home_no, device_name, issue_type, maintainer_name, appointment_time, status, created_time, updated_time) VALUES
('TCK-126-001', 'HOME-126-001', '客厅空调', '耗电异常', '王维修', '2026-05-07 10:00', 'OPEN', NOW(), NOW()),
('TCK-126-002', 'HOME-126-002', '电热水器', '设备老化', '赵维修', '2026-05-08 10:00', 'OPEN', NOW(), NOW());

CREATE TABLE operation_log (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  operator_name VARCHAR(180),
  module_name VARCHAR(180),
  action_type VARCHAR(180),
  target_name VARCHAR(180),
  detail VARCHAR(180),
  ip_address VARCHAR(180),
  status VARCHAR(30) DEFAULT 'SUCCESS',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO operation_log (operator_name, module_name, action_type, target_name, detail, ip_address, status, created_time, updated_time) VALUES
('系统管理员', '节能建议', 'CREATE', 'SUG-126-001', '生成节能建议', '127.0.0.1', 'SUCCESS', NOW(), NOW()),
('能耗分析师', '异常预警', 'UPDATE', 'ALT-126-001', '派发异常预警', '127.0.0.1', 'SUCCESS', NOW(), NOW());
