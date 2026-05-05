DROP DATABASE IF EXISTS urban_flood_dispatch_123;
CREATE DATABASE urban_flood_dispatch_123 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE urban_flood_dispatch_123;

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
('admin', '123456', '系统管理员', 'ADMIN', '应急指挥中心', '13800012301', 'admin@floodcity.local', 1, NOW(), NOW()),
('monitor', '123456', '监测员', 'MONITOR', '水情监测组', '13800012302', 'monitor@floodcity.local', 1, NOW(), NOW()),
('dispatcher', '123456', '调度员', 'DISPATCHER', '应急调度组', '13800012303', 'dispatcher@floodcity.local', 1, NOW(), NOW()),
('manager', '123456', '应急主管', 'MANAGER', '城市防汛办', '13800012304', 'manager@floodcity.local', 1, NOW(), NOW());

CREATE TABLE water_level_point (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  point_no VARCHAR(180),
  point_name VARCHAR(180),
  district_name VARCHAR(180),
  location_name VARCHAR(180),
  warning_level DECIMAL(12,2),
  manager_name VARCHAR(180),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO water_level_point (point_no, point_name, district_name, location_name, warning_level, manager_name, status, created_time, updated_time) VALUES
('WLP-123-001', '城东下穿点', '上城区', '城东路下穿', 2.8, '王监测', 'ACTIVE', NOW(), NOW()),
('WLP-123-002', '滨河低洼点', '滨江区', '滨河路口', 3.2, '李监测', 'ACTIVE', NOW(), NOW());

CREATE TABLE rain_gauge_station (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  station_no VARCHAR(180),
  station_name VARCHAR(180),
  district_name VARCHAR(180),
  device_type VARCHAR(180),
  sample_minute INT,
  manager_name VARCHAR(180),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO rain_gauge_station (station_no, station_name, district_name, device_type, sample_minute, manager_name, status, created_time, updated_time) VALUES
('RAIN-123-001', '东部雨量站', '上城区', '翻斗式', 5, '王监测', 'ACTIVE', NOW(), NOW()),
('RAIN-123-002', '滨江雨量站', '滨江区', '雷达式', 10, '李监测', 'ACTIVE', NOW(), NOW());

CREATE TABLE drainage_pump (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  pump_no VARCHAR(180),
  pump_name VARCHAR(180),
  district_name VARCHAR(180),
  capacity_value DECIMAL(12,2),
  online_count INT,
  manager_name VARCHAR(180),
  status VARCHAR(30) DEFAULT 'NORMAL',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO drainage_pump (pump_no, pump_name, district_name, capacity_value, online_count, manager_name, status, created_time, updated_time) VALUES
('PUMP-123-001', '城东泵站', '上城区', 3200.0, 4, '赵设备', 'NORMAL', NOW(), NOW()),
('PUMP-123-002', '滨江泵站', '滨江区', 2800.0, 3, '钱设备', 'NORMAL', NOW(), NOW());

CREATE TABLE water_level_data (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  data_no VARCHAR(180),
  point_name VARCHAR(180),
  collect_time VARCHAR(180),
  water_level DECIMAL(12,2),
  rise_speed DECIMAL(12,2),
  source_type VARCHAR(180),
  status VARCHAR(30) DEFAULT 'NORMAL',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO water_level_data (data_no, point_name, collect_time, water_level, rise_speed, source_type, status, created_time, updated_time) VALUES
('WD-123-001', '城东下穿点', '2026-05-05 15:00', 2.45, 0.18, '自动采集', 'NORMAL', NOW(), NOW()),
('WD-123-002', '滨河低洼点', '2026-05-05 15:05', 3.38, 0.26, '人工复核', 'NORMAL', NOW(), NOW());

CREATE TABLE rainfall_data (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  data_no VARCHAR(180),
  station_name VARCHAR(180),
  collect_time VARCHAR(180),
  hour_rainfall DECIMAL(12,2),
  total_rainfall DECIMAL(12,2),
  rain_level VARCHAR(180),
  status VARCHAR(30) DEFAULT 'NORMAL',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO rainfall_data (data_no, station_name, collect_time, hour_rainfall, total_rainfall, rain_level, status, created_time, updated_time) VALUES
('RD-123-001', '东部雨量站', '2026-05-05 15:00', 38.5, 86.2, '暴雨', 'NORMAL', NOW(), NOW()),
('RD-123-002', '滨江雨量站', '2026-05-05 15:05', 52.8, 114.6, '大暴雨', 'NORMAL', NOW(), NOW());

CREATE TABLE warning_rule (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  rule_no VARCHAR(180),
  rule_name VARCHAR(180),
  metric_type VARCHAR(180),
  threshold_value DECIMAL(12,2),
  warning_level VARCHAR(180),
  owner_name VARCHAR(180),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO warning_rule (rule_no, rule_name, metric_type, threshold_value, warning_level, owner_name, status, created_time, updated_time) VALUES
('RULE-123-001', '下穿点水位预警', '水位', 3.0, '橙色', '王监测', 'ACTIVE', NOW(), NOW()),
('RULE-123-002', '小时雨量预警', '雨量', 50.0, '红色', '李监测', 'ACTIVE', NOW(), NOW());

CREATE TABLE flood_warning (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  warning_no VARCHAR(180),
  point_name VARCHAR(180),
  warning_level VARCHAR(180),
  trigger_reason VARCHAR(180),
  handler_name VARCHAR(180),
  trigger_time VARCHAR(180),
  status VARCHAR(30) DEFAULT 'OPEN',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO flood_warning (warning_no, point_name, warning_level, trigger_reason, handler_name, trigger_time, status, created_time, updated_time) VALUES
('WARN-123-001', '城东下穿点', '橙色', '水位接近警戒线', '赵调度', '2026-05-05 15:20', 'OPEN', NOW(), NOW()),
('WARN-123-002', '滨河低洼点', '红色', '小时雨量超阈值', '钱调度', '2026-05-05 15:30', 'OPEN', NOW(), NOW());

CREATE TABLE emergency_plan (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  plan_no VARCHAR(180),
  plan_name VARCHAR(180),
  apply_level VARCHAR(180),
  department_name VARCHAR(180),
  start_condition VARCHAR(180),
  owner_name VARCHAR(180),
  status VARCHAR(30) DEFAULT 'PUBLISHED',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO emergency_plan (plan_no, plan_name, apply_level, department_name, start_condition, owner_name, status, created_time, updated_time) VALUES
('PLAN-123-001', '下穿点封控预案', '橙色', '交警大队', '水位超2.8米', '赵调度', 'PUBLISHED', NOW(), NOW()),
('PLAN-123-002', '低洼区排涝预案', '红色', '排水公司', '累计雨量超100mm', '钱调度', 'PUBLISHED', NOW(), NOW());

CREATE TABLE dispatch_task (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  task_no VARCHAR(180),
  warning_no VARCHAR(180),
  task_type VARCHAR(180),
  responsible_unit VARCHAR(180),
  deadline_time VARCHAR(180),
  dispatcher_name VARCHAR(180),
  status VARCHAR(30) DEFAULT 'ASSIGNED',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO dispatch_task (task_no, warning_no, task_type, responsible_unit, deadline_time, dispatcher_name, status, created_time, updated_time) VALUES
('TASK-123-001', 'WARN-123-001', '道路封控', '交警大队', '2026-05-05 16:00', '赵调度', 'ASSIGNED', NOW(), NOW()),
('TASK-123-002', 'WARN-123-002', '排水抢险', '排水公司', '2026-05-05 16:30', '钱调度', 'ASSIGNED', NOW(), NOW());

CREATE TABLE rescue_team (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  team_no VARCHAR(180),
  team_name VARCHAR(180),
  team_type VARCHAR(180),
  member_count INT,
  contact_phone VARCHAR(180),
  base_location VARCHAR(180),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO rescue_team (team_no, team_name, team_type, member_count, contact_phone, base_location, status, created_time, updated_time) VALUES
('TEAM-123-001', '城东抢险队', '抢险救援', 32, '13800012305', '城东仓库', 'ACTIVE', NOW(), NOW()),
('TEAM-123-002', '滨江排涝队', '排水抢修', 24, '13800012306', '滨江仓库', 'ACTIVE', NOW(), NOW());

CREATE TABLE material_reserve (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  material_no VARCHAR(180),
  material_name VARCHAR(180),
  material_type VARCHAR(180),
  stock_qty INT,
  safe_qty INT,
  warehouse_name VARCHAR(180),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO material_reserve (material_no, material_name, material_type, stock_qty, safe_qty, warehouse_name, status, created_time, updated_time) VALUES
('MAT-123-001', '防汛沙袋', '防汛物资', 5000, 2000, '城东仓库', 'ACTIVE', NOW(), NOW()),
('MAT-123-002', '移动水泵', '排水设备', 18, 8, '滨江仓库', 'ACTIVE', NOW(), NOW());

CREATE TABLE shelter_site (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  site_no VARCHAR(180),
  site_name VARCHAR(180),
  address_name VARCHAR(180),
  capacity_count INT,
  manager_name VARCHAR(180),
  contact_phone VARCHAR(180),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO shelter_site (site_no, site_name, address_name, capacity_count, manager_name, contact_phone, status, created_time, updated_time) VALUES
('SHEL-123-001', '城东体育馆', '城东路88号', 1200, '孙主管', '13800012307', 'ACTIVE', NOW(), NOW()),
('SHEL-123-002', '滨江学校', '滨河路66号', 800, '周主管', '13800012308', 'ACTIVE', NOW(), NOW());

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
('系统管理员', '内涝预警', 'CREATE', 'WARN-123-001', '生成内涝预警', '127.0.0.1', 'SUCCESS', NOW(), NOW()),
('应急主管', '调度任务', 'UPDATE', 'TASK-123-001', '派发调度任务', '127.0.0.1', 'SUCCESS', NOW(), NOW());
