DROP DATABASE IF EXISTS ev_charging_124;
CREATE DATABASE ev_charging_124 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE ev_charging_124;

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
('admin', '123456', '系统管理员', 'ADMIN', '充电运营中心', '13800012401', 'admin@chargepile.local', 1, NOW(), NOW()),
('operator', '123456', '运营员', 'OPERATOR', '站点运营组', '13800012402', 'operator@chargepile.local', 1, NOW(), NOW()),
('maintainer', '123456', '维修员', 'MAINTAINER', '设备维修组', '13800012403', 'maintainer@chargepile.local', 1, NOW(), NOW()),
('owner', '123456', '车主用户', 'OWNER', '个人车主', '13800012404', 'owner@chargepile.local', 1, NOW(), NOW());

CREATE TABLE charging_station (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  station_no VARCHAR(180),
  station_name VARCHAR(180),
  city_name VARCHAR(180),
  address_name VARCHAR(180),
  operator_name VARCHAR(180),
  open_time VARCHAR(180),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO charging_station (station_no, station_name, city_name, address_name, operator_name, open_time, status, created_time, updated_time) VALUES
('ST-124-001', '城东快充站', '杭州', '城东路88号', '绿能运营', '00:00-24:00', 'ACTIVE', NOW(), NOW()),
('ST-124-002', '滨江社区站', '苏州', '滨河路66号', '城投能源', '06:00-23:00', 'ACTIVE', NOW(), NOW());

CREATE TABLE charging_pile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  pile_no VARCHAR(180),
  station_name VARCHAR(180),
  power_kw DECIMAL(12,2),
  connector_type VARCHAR(180),
  price_per_kwh DECIMAL(12,2),
  position_name VARCHAR(180),
  status VARCHAR(30) DEFAULT 'AVAILABLE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO charging_pile (pile_no, station_name, power_kw, connector_type, price_per_kwh, position_name, status, created_time, updated_time) VALUES
('PILE-124-001', '城东快充站', 120.0, '国标直流', 1.28, 'A区01', 'AVAILABLE', NOW(), NOW()),
('PILE-124-002', '滨江社区站', 60.0, '国标交流', 0.98, 'B区08', 'AVAILABLE', NOW(), NOW());

CREATE TABLE user_vehicle (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  vehicle_no VARCHAR(180),
  owner_name VARCHAR(180),
  plate_no VARCHAR(180),
  battery_capacity DECIMAL(12,2),
  vehicle_model VARCHAR(180),
  phone VARCHAR(180),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO user_vehicle (vehicle_no, owner_name, plate_no, battery_capacity, vehicle_model, phone, status, created_time, updated_time) VALUES
('VEH-124-001', '陈车主', '浙A12345', 72.0, '纯电SUV', '13800012405', 'ACTIVE', NOW(), NOW()),
('VEH-124-002', '刘车主', '苏E54321', 55.0, '纯电轿车', '13800012406', 'ACTIVE', NOW(), NOW());

CREATE TABLE appointment_order (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  appointment_no VARCHAR(180),
  owner_name VARCHAR(180),
  station_name VARCHAR(180),
  pile_no VARCHAR(180),
  appointment_time VARCHAR(180),
  duration_minute INT,
  status VARCHAR(30) DEFAULT 'CREATED',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO appointment_order (appointment_no, owner_name, station_name, pile_no, appointment_time, duration_minute, status, created_time, updated_time) VALUES
('APP-124-001', '陈车主', '城东快充站', 'PILE-124-001', '2026-05-06 09:00', 60, 'CREATED', NOW(), NOW()),
('APP-124-002', '刘车主', '滨江社区站', 'PILE-124-002', '2026-05-06 14:00', 90, 'CREATED', NOW(), NOW());

CREATE TABLE charging_session (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  session_no VARCHAR(180),
  appointment_no VARCHAR(180),
  pile_no VARCHAR(180),
  start_time VARCHAR(180),
  energy_kwh DECIMAL(12,2),
  charge_amount DECIMAL(12,2),
  status VARCHAR(30) DEFAULT 'CHARGING',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO charging_session (session_no, appointment_no, pile_no, start_time, energy_kwh, charge_amount, status, created_time, updated_time) VALUES
('CHG-124-001', 'APP-124-001', 'PILE-124-001', '2026-05-06 09:05', 42.5, 54.4, 'CHARGING', NOW(), NOW()),
('CHG-124-002', 'APP-124-002', 'PILE-124-002', '2026-05-06 14:05', 36.8, 36.1, 'CHARGING', NOW(), NOW());

CREATE TABLE fault_report (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  fault_no VARCHAR(180),
  pile_no VARCHAR(180),
  fault_type VARCHAR(180),
  severity_level VARCHAR(180),
  reporter_name VARCHAR(180),
  report_time VARCHAR(180),
  status VARCHAR(30) DEFAULT 'OPEN',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO fault_report (fault_no, pile_no, fault_type, severity_level, reporter_name, report_time, status, created_time, updated_time) VALUES
('FAULT-124-001', 'PILE-124-001', '无法启动', '高', '陈车主', '2026-05-06 10:00', 'OPEN', NOW(), NOW()),
('FAULT-124-002', 'PILE-124-002', '充电中断', '中', '运营员', '2026-05-06 15:00', 'OPEN', NOW(), NOW());

CREATE TABLE repair_work_order (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  work_order_no VARCHAR(180),
  fault_no VARCHAR(180),
  maintainer_name VARCHAR(180),
  solution_text VARCHAR(180),
  deadline_time VARCHAR(180),
  repair_result VARCHAR(180),
  status VARCHAR(30) DEFAULT 'PROCESSING',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO repair_work_order (work_order_no, fault_no, maintainer_name, solution_text, deadline_time, repair_result, status, created_time, updated_time) VALUES
('REP-124-001', 'FAULT-124-001', '张维修', '更换控制模块', '2026-05-07 18:00', '待处理', 'PROCESSING', NOW(), NOW()),
('REP-124-002', 'FAULT-124-002', '李维修', '检查通信模块', '2026-05-08 18:00', '待检测', 'PROCESSING', NOW(), NOW());

CREATE TABLE maintenance_plan (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  plan_no VARCHAR(180),
  station_name VARCHAR(180),
  pile_no VARCHAR(180),
  maintenance_type VARCHAR(180),
  owner_name VARCHAR(180),
  plan_time VARCHAR(180),
  status VARCHAR(30) DEFAULT 'CREATED',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO maintenance_plan (plan_no, station_name, pile_no, maintenance_type, owner_name, plan_time, status, created_time, updated_time) VALUES
('PLAN-124-001', '城东快充站', 'PILE-124-001', '月度巡检', '张维修', '2026-05-10 09:00', 'CREATED', NOW(), NOW()),
('PLAN-124-002', '滨江社区站', 'PILE-124-002', '年度保养', '李维修', '2026-05-11 09:00', 'CREATED', NOW(), NOW());

CREATE TABLE electricity_price (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  price_no VARCHAR(180),
  station_name VARCHAR(180),
  period_type VARCHAR(180),
  electric_price DECIMAL(12,2),
  service_fee DECIMAL(12,2),
  effective_date VARCHAR(180),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO electricity_price (price_no, station_name, period_type, electric_price, service_fee, effective_date, status, created_time, updated_time) VALUES
('PRICE-124-001', '城东快充站', '峰时', 1.05, 0.25, '2026-05-01', 'ACTIVE', NOW(), NOW()),
('PRICE-124-002', '滨江社区站', '谷时', 0.62, 0.18, '2026-05-01', 'ACTIVE', NOW(), NOW());

CREATE TABLE payment_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  payment_no VARCHAR(180),
  session_no VARCHAR(180),
  owner_name VARCHAR(180),
  pay_amount DECIMAL(12,2),
  pay_method VARCHAR(180),
  pay_time VARCHAR(180),
  status VARCHAR(30) DEFAULT 'PAID',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO payment_record (payment_no, session_no, owner_name, pay_amount, pay_method, pay_time, status, created_time, updated_time) VALUES
('PAY-124-001', 'CHG-124-001', '陈车主', 54.4, '微信', '2026-05-06 10:10', 'PAID', NOW(), NOW()),
('PAY-124-002', 'CHG-124-002', '刘车主', 36.1, '支付宝', '2026-05-06 15:10', 'PAID', NOW(), NOW());

CREATE TABLE revenue_statistic (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  stat_no VARCHAR(180),
  station_name VARCHAR(180),
  stat_date VARCHAR(180),
  order_count INT,
  energy_kwh DECIMAL(12,2),
  revenue_amount DECIMAL(12,2),
  status VARCHAR(30) DEFAULT 'FINISHED',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO revenue_statistic (stat_no, station_name, stat_date, order_count, energy_kwh, revenue_amount, status, created_time, updated_time) VALUES
('REV-124-001', '城东快充站', '2026-05-06', 126, 2860.5, 3620.8, 'FINISHED', NOW(), NOW()),
('REV-124-002', '滨江社区站', '2026-05-06', 88, 1920.8, 2286.4, 'FINISHED', NOW(), NOW());

CREATE TABLE energy_monitor (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  monitor_no VARCHAR(180),
  station_name VARCHAR(180),
  collect_time VARCHAR(180),
  power_kwh DECIMAL(12,2),
  load_rate DECIMAL(12,2),
  abnormal_type VARCHAR(180),
  status VARCHAR(30) DEFAULT 'NORMAL',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO energy_monitor (monitor_no, station_name, collect_time, power_kwh, load_rate, abnormal_type, status, created_time, updated_time) VALUES
('ENE-124-001', '城东快充站', '2026-05-06 16:00', 420.5, 82.6, '无', 'NORMAL', NOW(), NOW()),
('ENE-124-002', '滨江社区站', '2026-05-06 16:05', 310.2, 66.8, '负载偏高', 'NORMAL', NOW(), NOW());

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
('系统管理员', '预约订单', 'CREATE', 'APP-124-001', '创建预约订单', '127.0.0.1', 'SUCCESS', NOW(), NOW()),
('运营员', '故障报修', 'UPDATE', 'FAULT-124-001', '确认故障报修', '127.0.0.1', 'SUCCESS', NOW(), NOW());
