DROP DATABASE IF EXISTS drone_inspection_121;
CREATE DATABASE drone_inspection_121 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE drone_inspection_121;

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
('admin', '123456', '系统管理员', 'ADMIN', '无人机巡检中心', '13800012101', 'admin@droneinspect.local', 1, NOW(), NOW()),
('pilot', '123456', '巡检飞手', 'PILOT', '飞行执行组', '13800012102', 'pilot@droneinspect.local', 1, NOW(), NOW()),
('engineer', '123456', '缺陷工程师', 'ENGINEER', '缺陷整改组', '13800012103', 'engineer@droneinspect.local', 1, NOW(), NOW()),
('manager', '123456', '运营经理', 'MANAGER', '巡检运营组', '13800012104', 'manager@droneinspect.local', 1, NOW(), NOW());

CREATE TABLE drone_device (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  drone_no VARCHAR(180),
  drone_name VARCHAR(180),
  model_name VARCHAR(180),
  battery_level INT,
  owner_team VARCHAR(180),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO drone_device (drone_no, drone_name, model_name, battery_level, owner_team, status, created_time, updated_time) VALUES
('UAV-121-001', '巡检一号机', 'Matrice 350', 86, '电力巡检组', 'ACTIVE', NOW(), NOW()),
('UAV-121-002', '巡检二号机', 'Mavic 3T', 72, '园区巡检组', 'ACTIVE', NOW(), NOW());

CREATE TABLE pilot_profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  pilot_no VARCHAR(180),
  pilot_name VARCHAR(180),
  license_level VARCHAR(180),
  team_name VARCHAR(180),
  total_hours INT,
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO pilot_profile (pilot_no, pilot_name, license_level, team_name, total_hours, status, created_time, updated_time) VALUES
('PILOT-121-001', '张飞手', 'AOPA高级', '电力巡检组', 680, 'ACTIVE', NOW(), NOW()),
('PILOT-121-002', '李飞手', 'AOPA中级', '园区巡检组', 420, 'ACTIVE', NOW(), NOW());

CREATE TABLE inspection_zone (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  zone_no VARCHAR(180),
  zone_name VARCHAR(180),
  city_name VARCHAR(180),
  risk_level VARCHAR(180),
  manager_name VARCHAR(180),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO inspection_zone (zone_no, zone_name, city_name, risk_level, manager_name, status, created_time, updated_time) VALUES
('ZONE-121-001', '东部输电走廊', '杭州', '高', '王经理', 'ACTIVE', NOW(), NOW()),
('ZONE-121-002', '智慧园区北区', '苏州', '中', '赵经理', 'ACTIVE', NOW(), NOW());

CREATE TABLE flight_route (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  route_no VARCHAR(180),
  route_name VARCHAR(180),
  zone_name VARCHAR(180),
  distance_km DECIMAL(12,2),
  waypoint_count INT,
  risk_level VARCHAR(180),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO flight_route (route_no, route_name, zone_name, distance_km, waypoint_count, risk_level, status, created_time, updated_time) VALUES
('ROUTE-121-001', '输电塔巡检航线', '东部输电走廊', 12.6, 36, '高', 'ACTIVE', NOW(), NOW()),
('ROUTE-121-002', '园区楼顶巡检航线', '智慧园区北区', 8.4, 24, '中', 'ACTIVE', NOW(), NOW());

CREATE TABLE inspection_task (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  task_no VARCHAR(180),
  route_name VARCHAR(180),
  pilot_name VARCHAR(180),
  plan_time VARCHAR(180),
  priority_level VARCHAR(180),
  photo_required INT,
  status VARCHAR(30) DEFAULT 'CREATED',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO inspection_task (task_no, route_name, pilot_name, plan_time, priority_level, photo_required, status, created_time, updated_time) VALUES
('TASK-121-001', '输电塔巡检航线', '张飞手', '2026-05-06 09:00', '高', 120, 'CREATED', NOW(), NOW()),
('TASK-121-002', '园区楼顶巡检航线', '李飞手', '2026-05-06 14:00', '中', 80, 'CREATED', NOW(), NOW());

CREATE TABLE flight_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(180),
  task_no VARCHAR(180),
  drone_name VARCHAR(180),
  start_time VARCHAR(180),
  flight_duration INT,
  result_type VARCHAR(180),
  status VARCHAR(30) DEFAULT 'FLYING',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO flight_record (record_no, task_no, drone_name, start_time, flight_duration, result_type, status, created_time, updated_time) VALUES
('FLY-121-001', 'TASK-121-001', '巡检一号机', '2026-05-06 09:10', 48, '发现缺陷', 'FLYING', NOW(), NOW()),
('FLY-121-002', 'TASK-121-002', '巡检二号机', '2026-05-06 14:10', 36, '正常', 'FLYING', NOW(), NOW());

CREATE TABLE defect_report (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  defect_no VARCHAR(180),
  zone_name VARCHAR(180),
  defect_type VARCHAR(180),
  severity_level VARCHAR(180),
  reporter_name VARCHAR(180),
  photo_count INT,
  status VARCHAR(30) DEFAULT 'OPEN',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO defect_report (defect_no, zone_name, defect_type, severity_level, reporter_name, photo_count, status, created_time, updated_time) VALUES
('DEF-121-001', '东部输电走廊', '绝缘子破损', '高', '张飞手', 8, 'OPEN', NOW(), NOW()),
('DEF-121-002', '智慧园区北区', '屋顶积水', '中', '李飞手', 5, 'OPEN', NOW(), NOW());

CREATE TABLE defect_image (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  image_no VARCHAR(180),
  defect_no VARCHAR(180),
  file_name VARCHAR(180),
  ai_label VARCHAR(180),
  confidence_score DECIMAL(12,2),
  review_result VARCHAR(180),
  status VARCHAR(30) DEFAULT 'OPEN',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO defect_image (image_no, defect_no, file_name, ai_label, confidence_score, review_result, status, created_time, updated_time) VALUES
('IMG-121-001', 'DEF-121-001', 'tower-001.jpg', '破损', 92.5, '待复核', 'OPEN', NOW(), NOW()),
('IMG-121-002', 'DEF-121-002', 'roof-002.jpg', '积水', 87.3, '已确认', 'OPEN', NOW(), NOW());

CREATE TABLE rectification_order (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  order_no VARCHAR(180),
  defect_no VARCHAR(180),
  responsible_team VARCHAR(180),
  deadline_time VARCHAR(180),
  rectify_measure VARCHAR(180),
  verify_result VARCHAR(180),
  status VARCHAR(30) DEFAULT 'PROCESSING',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO rectification_order (order_no, defect_no, responsible_team, deadline_time, rectify_measure, verify_result, status, created_time, updated_time) VALUES
('REC-121-001', 'DEF-121-001', '线路检修组', '2026-05-08 18:00', '更换绝缘子', '待验收', 'PROCESSING', NOW(), NOW()),
('REC-121-002', 'DEF-121-002', '物业维修组', '2026-05-09 18:00', '疏通排水口', '待提交', 'PROCESSING', NOW(), NOW());

CREATE TABLE battery_station (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  station_no VARCHAR(180),
  station_name VARCHAR(180),
  zone_name VARCHAR(180),
  port_count INT,
  available_port INT,
  manager_name VARCHAR(180),
  status VARCHAR(30) DEFAULT 'NORMAL',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO battery_station (station_no, station_name, zone_name, port_count, available_port, manager_name, status, created_time, updated_time) VALUES
('BAT-121-001', '东部电池站', '东部输电走廊', 12, 8, '王经理', 'NORMAL', NOW(), NOW()),
('BAT-121-002', '北区电池站', '智慧园区北区', 8, 6, '赵经理', 'NORMAL', NOW(), NOW());

CREATE TABLE maintenance_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  maintenance_no VARCHAR(180),
  drone_name VARCHAR(180),
  maintenance_type VARCHAR(180),
  technician_name VARCHAR(180),
  maintenance_time VARCHAR(180),
  cost_amount DECIMAL(12,2),
  status VARCHAR(30) DEFAULT 'OPEN',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO maintenance_record (maintenance_no, drone_name, maintenance_type, technician_name, maintenance_time, cost_amount, status, created_time, updated_time) VALUES
('MNT-121-001', '巡检一号机', '例行保养', '陈工程师', '2026-05-07 10:00', 680.0, 'OPEN', NOW(), NOW()),
('MNT-121-002', '巡检二号机', '故障检修', '刘工程师', '2026-05-08 10:00', 920.0, 'OPEN', NOW(), NOW());

CREATE TABLE risk_warning (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  warning_no VARCHAR(180),
  risk_object VARCHAR(180),
  warning_level VARCHAR(180),
  warning_reason VARCHAR(180),
  handler_name VARCHAR(180),
  trigger_time VARCHAR(180),
  status VARCHAR(30) DEFAULT 'OPEN',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO risk_warning (warning_no, risk_object, warning_level, warning_reason, handler_name, trigger_time, status, created_time, updated_time) VALUES
('WARN-121-001', '输电塔巡检航线', '高', '连续缺陷增长', '陈工程师', '2026-05-05 16:00', 'OPEN', NOW(), NOW()),
('WARN-121-002', '巡检二号机', '中', '电池健康下降', '刘工程师', '2026-05-05 17:00', 'OPEN', NOW(), NOW());

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
('系统管理员', '巡检任务', 'CREATE', 'TASK-121-001', '创建巡检任务', '127.0.0.1', 'SUCCESS', NOW(), NOW()),
('运营经理', '缺陷报告', 'UPDATE', 'DEF-121-001', '确认缺陷报告', '127.0.0.1', 'SUCCESS', NOW(), NOW());
