DROP DATABASE IF EXISTS digital_twin_park_120;
CREATE DATABASE digital_twin_park_120 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE digital_twin_park_120;

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
('admin', '123456', '系统管理员', 'ADMIN', '园区管理中心', '13800012001', 'admin@twinpark.local', 1, NOW(), NOW()),
('inspector', '123456', '巡检员', 'INSPECTOR', '巡检执行组', '13800012002', 'inspector@twinpark.local', 1, NOW(), NOW()),
('engineer', '123456', '工程师', 'ENGINEER', '设备工程组', '13800012003', 'engineer@twinpark.local', 1, NOW(), NOW()),
('manager', '123456', '园区经理', 'MANAGER', '运营管理组', '13800012004', 'manager@twinpark.local', 1, NOW(), NOW());

CREATE TABLE park_building (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  building_no VARCHAR(80) NOT NULL,
  building_name VARCHAR(120) NOT NULL,
  floor_count INT DEFAULT 0,
  area_size DECIMAL(12,2) DEFAULT 0,
  manager_name VARCHAR(60),
  twin_status VARCHAR(30),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO park_building (building_no, building_name, floor_count, area_size, manager_name, twin_status, status, created_time, updated_time) VALUES
('BLD-120-001', 'A栋研发楼', 18, 28600.00, '园区经理', '已建模', 'ACTIVE', NOW(), NOW()),
('BLD-120-002', 'B栋研发楼', 27, 28607.36, '园区经理', '已建模', 'ACTIVE', NOW(), NOW());

CREATE TABLE twin_device (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  device_no VARCHAR(80) NOT NULL,
  device_name VARCHAR(120) NOT NULL,
  building_name VARCHAR(120),
  device_type VARCHAR(80),
  model_no VARCHAR(80),
  health_score DECIMAL(8,2) DEFAULT 0,
  status VARCHAR(30) DEFAULT 'ONLINE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO twin_device (device_no, device_name, building_name, device_type, model_no, health_score, status, created_time, updated_time) VALUES
('DEV-120-001', 'A栋空调主机', 'A栋研发楼', '暖通空调', 'MODEL-120-001', 88.50, 'ONLINE', NOW(), NOW()),
('DEV-120-002', 'B栋空调主机', 'B栋研发楼', '暖通电梯', 'MODEL-120-002', 95.86, 'ONLINE', NOW(), NOW());

CREATE TABLE inspection_route (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  route_no VARCHAR(80) NOT NULL,
  route_name VARCHAR(120),
  building_name VARCHAR(120),
  point_count INT DEFAULT 0,
  estimate_minutes INT DEFAULT 0,
  owner_name VARCHAR(60),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO inspection_route (route_no, route_name, building_name, point_count, estimate_minutes, owner_name, status, created_time, updated_time) VALUES
('ROUTE-120-001', 'A栋暖通巡检线', 'A栋研发楼', 12, 45, '张巡检', 'ACTIVE', NOW(), NOW()),
('ROUTE-120-002', 'B栋暖通巡检线', 'B栋研发楼', 21, 54, '李巡检', 'ACTIVE', NOW(), NOW());

CREATE TABLE inspection_point (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  point_no VARCHAR(80) NOT NULL,
  point_name VARCHAR(120),
  device_name VARCHAR(120),
  floor_name VARCHAR(60),
  check_standard VARCHAR(180),
  last_result VARCHAR(60),
  status VARCHAR(30) DEFAULT 'NORMAL',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO inspection_point (point_no, point_name, device_name, floor_name, check_standard, last_result, status, created_time, updated_time) VALUES
('POINT-120-001', '空调主机房', 'A栋空调主机', 'B1', '温度振动压力读数正常', '正常', 'NORMAL', NOW(), NOW()),
('POINT-120-002', '电梯主机房', 'B栋空调主机', 'B1', '温度振动压力读数正常', '正常', 'NORMAL', NOW(), NOW());

CREATE TABLE inspection_task (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  task_no VARCHAR(80) NOT NULL,
  route_name VARCHAR(120),
  inspector_name VARCHAR(60),
  plan_time VARCHAR(40),
  priority_level VARCHAR(30),
  point_total INT DEFAULT 0,
  status VARCHAR(30) DEFAULT 'CREATED',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO inspection_task (task_no, route_name, inspector_name, plan_time, priority_level, point_total, status, created_time, updated_time) VALUES
('TASK-120-001', 'A栋暖通巡检线', '张巡检', '2026-05-05 09:00', '高', 12, 'ASSIGNED', NOW(), NOW()),
('TASK-120-002', 'B栋暖通巡检线', '李巡检', '2026-05-05 09:00', '中', 21, 'ASSIGNED', NOW(), NOW());

CREATE TABLE inspection_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(80) NOT NULL,
  task_no VARCHAR(80),
  point_name VARCHAR(120),
  result_type VARCHAR(60),
  meter_value DECIMAL(10,2) DEFAULT 0,
  inspect_time VARCHAR(40),
  status VARCHAR(30) DEFAULT 'WAIT_REVIEW',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO inspection_record (record_no, task_no, point_name, result_type, meter_value, inspect_time, status, created_time, updated_time) VALUES
('REC-120-001', 'TASK-120-001', '空调主机房', '正常', 26.50, '2026-05-05 09:30', 'WAIT_REVIEW', NOW(), NOW()),
('REC-120-002', 'TASK-120-002', '电梯主机房', '正常', 33.86, '2026-05-05 09:30', 'WAIT_REVIEW', NOW(), NOW());

CREATE TABLE defect_report (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  defect_no VARCHAR(80) NOT NULL,
  device_name VARCHAR(120),
  severity_level VARCHAR(30),
  defect_desc VARCHAR(220),
  reporter_name VARCHAR(60),
  suggestion VARCHAR(180),
  status VARCHAR(30) DEFAULT 'OPEN',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO defect_report (defect_no, device_name, severity_level, defect_desc, reporter_name, suggestion, status, created_time, updated_time) VALUES
('DEF-120-001', 'A栋空调主机', '高', '冷却水压力波动异常', '张巡检', '安排工程师现场复核', 'OPEN', NOW(), NOW()),
('DEF-120-002', 'B栋空调主机', '中', '冷却水压力波动异常', '李巡检', '安排工程师现场复核', 'OPEN', NOW(), NOW());

CREATE TABLE work_order (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  work_order_no VARCHAR(80) NOT NULL,
  defect_no VARCHAR(80),
  device_name VARCHAR(120),
  engineer_name VARCHAR(60),
  deadline_time VARCHAR(40),
  handle_result VARCHAR(180),
  status VARCHAR(30) DEFAULT 'WAIT_HANDLE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO work_order (work_order_no, defect_no, device_name, engineer_name, deadline_time, handle_result, status, created_time, updated_time) VALUES
('WO-120-001', 'DEF-120-001', 'A栋空调主机', '设备工程师', '2026-05-06 18:00', '待处理', 'WAIT_HANDLE', NOW(), NOW()),
('WO-120-002', 'DEF-120-002', 'B栋空调主机', '设备工程师', '2026-05-06 18:00', '待处理', 'WAIT_HANDLE', NOW(), NOW());

CREATE TABLE sensor_data (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  data_no VARCHAR(80) NOT NULL,
  device_name VARCHAR(120),
  sensor_type VARCHAR(80),
  temperature_value DECIMAL(10,2) DEFAULT 0,
  energy_value DECIMAL(10,2) DEFAULT 0,
  pressure_value DECIMAL(10,2) DEFAULT 0,
  collect_time VARCHAR(40),
  status VARCHAR(30) DEFAULT 'NORMAL',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO sensor_data (data_no, device_name, sensor_type, temperature_value, energy_value, pressure_value, collect_time, status, created_time, updated_time) VALUES
('DATA-120-001', 'A栋空调主机', '温湿度', 26.50, 128.60, 0.86, '2026-05-05 10:00', 'NORMAL', NOW(), NOW()),
('DATA-120-002', 'B栋空调主机', '温湿度', 33.86, 135.96, 8.22, '2026-05-05 10:00', 'NORMAL', NOW(), NOW());

CREATE TABLE twin_model (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  model_no VARCHAR(80) NOT NULL,
  model_name VARCHAR(120),
  model_type VARCHAR(80),
  device_name VARCHAR(120),
  version_no VARCHAR(50),
  sync_time VARCHAR(40),
  status VARCHAR(30) DEFAULT 'ONLINE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO twin_model (model_no, model_name, model_type, device_name, version_no, sync_time, status, created_time, updated_time) VALUES
('MODEL-120-001', '空调主机三维模型', '设备模型', 'A栋空调主机', 'V1.0', '2026-05-05 10:30', 'ONLINE', NOW(), NOW()),
('MODEL-120-002', '电梯主机三维模型', '设备模型', 'B栋空调主机', 'V1.0', '2026-05-05 10:30', 'ONLINE', NOW(), NOW());

CREATE TABLE energy_monitor (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  energy_no VARCHAR(80) NOT NULL,
  building_name VARCHAR(120),
  device_name VARCHAR(120),
  energy_type VARCHAR(60),
  period_name VARCHAR(40),
  energy_value DECIMAL(12,2) DEFAULT 0,
  change_rate DECIMAL(8,2) DEFAULT 0,
  status VARCHAR(30) DEFAULT 'NORMAL',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO energy_monitor (energy_no, building_name, device_name, energy_type, period_name, energy_value, change_rate, status, created_time, updated_time) VALUES
('ENG-120-001', 'A栋研发楼', 'A栋空调主机', '用电', '2026-05', 32680.00, 6.20, 'NORMAL', NOW(), NOW()),
('ENG-120-002', 'B栋研发楼', 'B栋空调主机', '用电', '2026-05', 32687.36, 13.56, 'NORMAL', NOW(), NOW());

CREATE TABLE maintenance_schedule (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  schedule_no VARCHAR(80) NOT NULL,
  device_name VARCHAR(120),
  maintenance_type VARCHAR(80),
  plan_time VARCHAR(40),
  owner_name VARCHAR(60),
  execute_result VARCHAR(180),
  status VARCHAR(30) DEFAULT 'WAIT_EXECUTE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO maintenance_schedule (schedule_no, device_name, maintenance_type, plan_time, owner_name, execute_result, status, created_time, updated_time) VALUES
('SCH-120-001', 'A栋空调主机', '月度保养', '2026-05-08 09:00', '设备工程师', '待执行', 'WAIT_EXECUTE', NOW(), NOW()),
('SCH-120-002', 'B栋空调主机', '月度保养', '2026-05-08 09:00', '设备工程师', '待执行', 'WAIT_EXECUTE', NOW(), NOW());

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
('系统管理员', '巡检任务', 'CREATE', 'TASK-120-001', '创建A栋暖通巡检任务', '127.0.0.1', 'SUCCESS', NOW(), NOW()),
('系统管理员二号', '巡检任务', 'CREATE', 'TASK-120-002', '创建B栋暖通巡检任务', '127.0.0.1', 'SUCCESS', NOW(), NOW());
