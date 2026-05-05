DROP DATABASE IF EXISTS smart_parking_125;
CREATE DATABASE smart_parking_125 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE smart_parking_125;

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
('admin', '123456', '系统管理员', 'ADMIN', '停车运营中心', '13800012501', 'admin@parkingguide.local', 1, NOW(), NOW()),
('operator', '123456', '运营员', 'OPERATOR', '场库运营组', '13800012502', 'operator@parkingguide.local', 1, NOW(), NOW()),
('guard', '123456', '岗亭员', 'GUARD', '现场岗亭组', '13800012503', 'guard@parkingguide.local', 1, NOW(), NOW()),
('analyst', '123456', '分析员', 'ANALYST', '空位预测组', '13800012504', 'analyst@parkingguide.local', 1, NOW(), NOW());

CREATE TABLE parking_lot (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  lot_no VARCHAR(180),
  lot_name VARCHAR(180),
  city_name VARCHAR(180),
  address_name VARCHAR(180),
  total_space INT,
  operator_name VARCHAR(180),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO parking_lot (lot_no, lot_name, city_name, address_name, total_space, operator_name, status, created_time, updated_time) VALUES
('LOT-125-001', '城东综合停车场', '杭州', '城东路88号', 860, '城投停车', 'ACTIVE', NOW(), NOW()),
('LOT-125-002', '滨江商业停车场', '苏州', '滨河路66号', 520, '绿行停车', 'ACTIVE', NOW(), NOW());

CREATE TABLE parking_area (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  area_no VARCHAR(180),
  lot_name VARCHAR(180),
  area_name VARCHAR(180),
  floor_name VARCHAR(180),
  space_count INT,
  manager_name VARCHAR(180),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO parking_area (area_no, lot_name, area_name, floor_name, space_count, manager_name, status, created_time, updated_time) VALUES
('AREA-125-001', '城东综合停车场', 'A区', 'B1', 180, '王运营', 'ACTIVE', NOW(), NOW()),
('AREA-125-002', '滨江商业停车场', 'B区', 'B2', 120, '李运营', 'ACTIVE', NOW(), NOW());

CREATE TABLE parking_space (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  space_no VARCHAR(180),
  area_name VARCHAR(180),
  space_type VARCHAR(180),
  plate_label VARCHAR(180),
  fee_type VARCHAR(180),
  sensor_no VARCHAR(180),
  status VARCHAR(30) DEFAULT 'AVAILABLE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO parking_space (space_no, area_name, space_type, plate_label, fee_type, sensor_no, status, created_time, updated_time) VALUES
('A-001', 'A区', '标准车位', 'A001', '计时', 'SNS-125-001', 'AVAILABLE', NOW(), NOW()),
('B-018', 'B区', '新能源车位', 'B018', '包月', 'SNS-125-002', 'AVAILABLE', NOW(), NOW());

CREATE TABLE space_sensor (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  sensor_no VARCHAR(180),
  space_no VARCHAR(180),
  device_type VARCHAR(180),
  battery_level INT,
  signal_strength INT,
  last_heartbeat VARCHAR(180),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO space_sensor (sensor_no, space_no, device_type, battery_level, signal_strength, last_heartbeat, status, created_time, updated_time) VALUES
('SNS-125-001', 'A-001', '地磁', 88, 95, '2026-05-05 16:00', 'ACTIVE', NOW(), NOW()),
('SNS-125-002', 'B-018', '视频桩', 72, 82, '2026-05-05 16:05', 'ACTIVE', NOW(), NOW());

CREATE TABLE vehicle_profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  vehicle_no VARCHAR(180),
  owner_name VARCHAR(180),
  plate_no VARCHAR(180),
  vehicle_type VARCHAR(180),
  phone VARCHAR(180),
  member_level VARCHAR(180),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO vehicle_profile (vehicle_no, owner_name, plate_no, vehicle_type, phone, member_level, status, created_time, updated_time) VALUES
('VEH-125-001', '陈车主', '浙A12345', 'SUV', '13800012505', '金卡', 'ACTIVE', NOW(), NOW()),
('VEH-125-002', '刘车主', '苏E54321', '轿车', '13800012506', '银卡', 'ACTIVE', NOW(), NOW());

CREATE TABLE reservation_order (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  reservation_no VARCHAR(180),
  owner_name VARCHAR(180),
  lot_name VARCHAR(180),
  space_no VARCHAR(180),
  reservation_time VARCHAR(180),
  duration_minute INT,
  status VARCHAR(30) DEFAULT 'RESERVED',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO reservation_order (reservation_no, owner_name, lot_name, space_no, reservation_time, duration_minute, status, created_time, updated_time) VALUES
('RSV-125-001', '陈车主', '城东综合停车场', 'A-001', '2026-05-06 09:00', 120, 'RESERVED', NOW(), NOW()),
('RSV-125-002', '刘车主', '滨江商业停车场', 'B-018', '2026-05-06 14:00', 90, 'RESERVED', NOW(), NOW());

CREATE TABLE parking_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(180),
  reservation_no VARCHAR(180),
  plate_no VARCHAR(180),
  enter_time VARCHAR(180),
  leave_time VARCHAR(180),
  parking_minute INT,
  status VARCHAR(30) DEFAULT 'ENTERED',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO parking_record (record_no, reservation_no, plate_no, enter_time, leave_time, parking_minute, status, created_time, updated_time) VALUES
('PARK-125-001', 'RSV-125-001', '浙A12345', '2026-05-06 09:05', '2026-05-06 11:05', 120, 'ENTERED', NOW(), NOW()),
('PARK-125-002', 'RSV-125-002', '苏E54321', '2026-05-06 14:05', '2026-05-06 15:35', 90, 'ENTERED', NOW(), NOW());

CREATE TABLE payment_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  payment_no VARCHAR(180),
  record_no VARCHAR(180),
  owner_name VARCHAR(180),
  pay_amount DECIMAL(12,2),
  pay_method VARCHAR(180),
  pay_time VARCHAR(180),
  status VARCHAR(30) DEFAULT 'PAID',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO payment_record (payment_no, record_no, owner_name, pay_amount, pay_method, pay_time, status, created_time, updated_time) VALUES
('PAY-125-001', 'PARK-125-001', '陈车主', 28.0, '微信', '2026-05-06 11:10', 'PAID', NOW(), NOW()),
('PAY-125-002', 'PARK-125-002', '刘车主', 18.0, '支付宝', '2026-05-06 15:40', 'PAID', NOW(), NOW());

CREATE TABLE vacancy_prediction (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  prediction_no VARCHAR(180),
  lot_name VARCHAR(180),
  predict_time VARCHAR(180),
  vacancy_count INT,
  confidence_score DECIMAL(12,2),
  model_version VARCHAR(180),
  status VARCHAR(30) DEFAULT 'PUBLISHED',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO vacancy_prediction (prediction_no, lot_name, predict_time, vacancy_count, confidence_score, model_version, status, created_time, updated_time) VALUES
('PRED-125-001', '城东综合停车场', '2026-05-06 09:00', 126, 91.5, 'PARK-V1', 'PUBLISHED', NOW(), NOW()),
('PRED-125-002', '滨江商业停车场', '2026-05-06 14:00', 58, 86.8, 'PARK-V1', 'PUBLISHED', NOW(), NOW());

CREATE TABLE guidance_screen (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  screen_no VARCHAR(180),
  lot_name VARCHAR(180),
  position_name VARCHAR(180),
  display_text VARCHAR(180),
  refresh_time VARCHAR(180),
  manager_name VARCHAR(180),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO guidance_screen (screen_no, lot_name, position_name, display_text, refresh_time, manager_name, status, created_time, updated_time) VALUES
('SCR-125-001', '城东综合停车场', '入口一', 'A区剩余126位', '2026-05-05 16:00', '王运营', 'ACTIVE', NOW(), NOW()),
('SCR-125-002', '滨江商业停车场', '入口二', 'B区剩余58位', '2026-05-05 16:05', '李运营', 'ACTIVE', NOW(), NOW());

CREATE TABLE navigation_route (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  route_no VARCHAR(180),
  lot_name VARCHAR(180),
  entry_name VARCHAR(180),
  target_area VARCHAR(180),
  distance_meter INT,
  congestion_level VARCHAR(180),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO navigation_route (route_no, lot_name, entry_name, target_area, distance_meter, congestion_level, status, created_time, updated_time) VALUES
('NAV-125-001', '城东综合停车场', '东入口', 'A区', 260, '低', 'ACTIVE', NOW(), NOW()),
('NAV-125-002', '滨江商业停车场', '西入口', 'B区', 180, '中', 'ACTIVE', NOW(), NOW());

CREATE TABLE fault_report (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  fault_no VARCHAR(180),
  target_name VARCHAR(180),
  fault_type VARCHAR(180),
  severity_level VARCHAR(180),
  reporter_name VARCHAR(180),
  report_time VARCHAR(180),
  status VARCHAR(30) DEFAULT 'OPEN',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO fault_report (fault_no, target_name, fault_type, severity_level, reporter_name, report_time, status, created_time, updated_time) VALUES
('FAULT-125-001', 'SNS-125-001', '传感器离线', '高', '岗亭员', '2026-05-06 10:00', 'OPEN', NOW(), NOW()),
('FAULT-125-002', 'SCR-125-002', '屏幕不刷新', '中', '运营员', '2026-05-06 15:00', 'OPEN', NOW(), NOW());

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
('系统管理员', '预约订单', 'CREATE', 'RSV-125-001', '创建预约订单', '127.0.0.1', 'SUCCESS', NOW(), NOW()),
('运营员', '空位预测', 'UPDATE', 'PRED-125-001', '发布空位预测', '127.0.0.1', 'SUCCESS', NOW(), NOW());
