DROP DATABASE IF EXISTS smart_warehouse_agv_118;
CREATE DATABASE smart_warehouse_agv_118 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE smart_warehouse_agv_118;

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
('admin', '123456', '系统管理员', 'ADMIN', '仓储调度中心', '13800001801', 'admin@warehouse.local', 1, NOW(), NOW()),
('dispatcher', '123456', '调度员', 'DISPATCHER', '任务调度组', '13800001802', 'dispatcher@warehouse.local', 1, NOW(), NOW()),
('warehouse', '123456', '仓管员', 'WAREHOUSE', '库存运营组', '13800001803', 'warehouse@warehouse.local', 1, NOW(), NOW()),
('maintainer', '123456', '维保员', 'MAINTAINER', '设备维保组', '13800001804', 'maintainer@warehouse.local', 1, NOW(), NOW());

CREATE TABLE warehouse_zone (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  zone_no VARCHAR(80) NOT NULL,
  zone_name VARCHAR(120) NOT NULL,
  temperature_type VARCHAR(60),
  aisle_count INT DEFAULT 0,
  manager_name VARCHAR(60),
  capacity_rate DECIMAL(8,2) DEFAULT 0,
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO warehouse_zone (zone_no, zone_name, temperature_type, aisle_count, manager_name, capacity_rate, status, created_time, updated_time) VALUES
('ZONE-118-A01', '立体库A区', '常温', 12, '周调度', 68.50, 'ACTIVE', NOW(), NOW()),
('ZONE-118-B01', '立体库B区', '冷藏', 19, '钱调度', 74.68, 'ACTIVE', NOW(), NOW());

CREATE TABLE storage_location (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  location_no VARCHAR(80) NOT NULL,
  zone_name VARCHAR(120),
  location_type VARCHAR(60),
  max_weight DECIMAL(12,2) DEFAULT 0,
  volume_capacity DECIMAL(12,2) DEFAULT 0,
  heat_level VARCHAR(30),
  status VARCHAR(30) DEFAULT 'EMPTY',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO storage_location (location_no, zone_name, location_type, max_weight, volume_capacity, heat_level, status, created_time, updated_time) VALUES
('LOC-118-A0101', '立体库A区', '托盘位', 1200.00, 8.50, '高频', 'EMPTY', NOW(), NOW()),
('LOC-118-B0101', '立体库B区', '托盘位', 1206.18, 14.68, '中频', 'EMPTY', NOW(), NOW());

CREATE TABLE agv_vehicle (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  agv_no VARCHAR(80) NOT NULL,
  agv_model VARCHAR(80),
  current_zone VARCHAR(120),
  battery_level INT DEFAULT 0,
  load_weight DECIMAL(12,2) DEFAULT 0,
  task_count INT DEFAULT 0,
  status VARCHAR(30) DEFAULT 'IDLE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO agv_vehicle (agv_no, agv_model, current_zone, battery_level, load_weight, task_count, status, created_time, updated_time) VALUES
('AGV-118-001', '潜伏式AGV', '立体库A区', 86, 320.00, 18, 'IDLE', NOW(), NOW()),
('AGV-118-002', '潜伏式AGV', '立体库B区', 93, 326.18, 25, 'IDLE', NOW(), NOW());

CREATE TABLE charging_station (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  station_no VARCHAR(80) NOT NULL,
  station_name VARCHAR(120),
  zone_name VARCHAR(120),
  port_count INT DEFAULT 0,
  idle_port_count INT DEFAULT 0,
  charge_power DECIMAL(10,2) DEFAULT 0,
  status VARCHAR(30) DEFAULT 'AVAILABLE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO charging_station (station_no, station_name, zone_name, port_count, idle_port_count, charge_power, status, created_time, updated_time) VALUES
('CHG-118-001', 'A区快充站', '立体库A区', 6, 3, 22.00, 'AVAILABLE', NOW(), NOW()),
('CHG-118-002', 'B区快充站', '立体库B区', 13, 10, 28.18, 'AVAILABLE', NOW(), NOW());

CREATE TABLE inventory_item (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  sku_no VARCHAR(80) NOT NULL,
  item_name VARCHAR(120) NOT NULL,
  batch_no VARCHAR(80),
  location_no VARCHAR(80),
  quantity INT DEFAULT 0,
  turnover_level VARCHAR(30),
  status VARCHAR(30) DEFAULT 'NORMAL',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO inventory_item (sku_no, item_name, batch_no, location_no, quantity, turnover_level, status, created_time, updated_time) VALUES
('SKU-118-001', '智能传感器模组', 'BATCH-118-01', 'LOC-118-A0101', 560, 'A类', 'NORMAL', NOW(), NOW()),
('SKU-118-002', '工业控制器套件', 'BATCH-118-01', 'LOC-118-B0101', 567, 'A类', 'NORMAL', NOW(), NOW());

CREATE TABLE inbound_order (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  inbound_no VARCHAR(80) NOT NULL,
  supplier_name VARCHAR(120),
  item_name VARCHAR(120),
  target_location VARCHAR(80),
  plan_time VARCHAR(40),
  quantity INT DEFAULT 0,
  status VARCHAR(30) DEFAULT 'CREATED',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO inbound_order (inbound_no, supplier_name, item_name, target_location, plan_time, quantity, status, created_time, updated_time) VALUES
('IN-118-001', '华东电子供应链', '智能传感器模组', 'LOC-118-A0101', '2026-05-04 09:30', 300, 'ASSIGNED', NOW(), NOW()),
('IN-118-002', '华南电子供应链', '工业控制器套件', 'LOC-118-B0101', '2026-05-04 09:30', 307, 'ASSIGNED', NOW(), NOW());

CREATE TABLE outbound_order (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  outbound_no VARCHAR(80) NOT NULL,
  customer_name VARCHAR(120),
  item_name VARCHAR(120),
  pick_location VARCHAR(80),
  priority_level VARCHAR(30),
  quantity INT DEFAULT 0,
  status VARCHAR(30) DEFAULT 'CREATED',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO outbound_order (outbound_no, customer_name, item_name, pick_location, priority_level, quantity, status, created_time, updated_time) VALUES
('OUT-118-001', '华东门店仓', '智能传感器模组', 'LOC-118-A0101', '高', 120, 'ALLOCATED', NOW(), NOW()),
('OUT-118-002', '华南门店仓', '工业控制器套件', 'LOC-118-B0101', '中', 127, 'ALLOCATED', NOW(), NOW());

CREATE TABLE agv_task (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  task_no VARCHAR(80) NOT NULL,
  task_type VARCHAR(60),
  agv_no VARCHAR(80),
  source_location VARCHAR(80),
  target_location VARCHAR(80),
  priority_level VARCHAR(30),
  status VARCHAR(30) DEFAULT 'WAIT_DISPATCH',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO agv_task (task_no, task_type, agv_no, source_location, target_location, priority_level, status, created_time, updated_time) VALUES
('TASK-118-001', '入库上架', 'AGV-118-001', 'RECV-01', 'LOC-118-A0101', '高', 'DISPATCHED', NOW(), NOW()),
('TASK-118-002', '入库上架', 'AGV-118-002', 'RECV-01', 'LOC-118-B0101', '中', 'DISPATCHED', NOW(), NOW());

CREATE TABLE task_route (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  route_no VARCHAR(80) NOT NULL,
  task_no VARCHAR(80),
  start_point VARCHAR(80),
  end_point VARCHAR(80),
  distance_meter DECIMAL(12,2) DEFAULT 0,
  estimate_seconds INT DEFAULT 0,
  congestion_level VARCHAR(30),
  status VARCHAR(30) DEFAULT 'DRAFT',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO task_route (route_no, task_no, start_point, end_point, distance_meter, estimate_seconds, congestion_level, status, created_time, updated_time) VALUES
('ROUTE-118-001', 'TASK-118-001', 'RECV-01', 'LOC-118-A0101', 186.50, 420, '低', 'ACTIVE', NOW(), NOW()),
('ROUTE-118-002', 'TASK-118-002', 'RECV-01', 'LOC-118-B0101', 192.68, 427, '低', 'ACTIVE', NOW(), NOW());

CREATE TABLE location_recommendation (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  recommend_no VARCHAR(80) NOT NULL,
  item_name VARCHAR(120),
  location_no VARCHAR(80),
  match_score DECIMAL(8,2) DEFAULT 0,
  reason_text VARCHAR(220),
  operator_name VARCHAR(60),
  status VARCHAR(30) DEFAULT 'WAIT_APPLY',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO location_recommendation (recommend_no, item_name, location_no, match_score, reason_text, operator_name, status, created_time, updated_time) VALUES
('REC-118-001', '智能传感器模组', 'LOC-118-A0101', 92.50, '高周转物料靠近出库口且承重匹配', '周调度', 'WAIT_APPLY', NOW(), NOW()),
('REC-118-002', '工业控制器套件', 'LOC-118-B0101', 98.68, '中周转物料靠近出库口且承重匹配', '钱调度', 'WAIT_APPLY', NOW(), NOW());

CREATE TABLE device_maintenance (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  maintenance_no VARCHAR(80) NOT NULL,
  device_no VARCHAR(80),
  device_type VARCHAR(60),
  fault_type VARCHAR(80),
  plan_time VARCHAR(40),
  handler_name VARCHAR(60),
  status VARCHAR(30) DEFAULT 'WAIT_HANDLE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO device_maintenance (maintenance_no, device_no, device_type, fault_type, plan_time, handler_name, status, created_time, updated_time) VALUES
('MNT-118-001', 'AGV-118-001', 'AGV车辆', '电池衰减', '2026-05-04 14:00', '吴维保', 'WAIT_HANDLE', NOW(), NOW()),
('MNT-118-002', 'AGV-118-002', 'AGV车辆', '电池衰减', '2026-05-04 14:00', '郑维保', 'WAIT_HANDLE', NOW(), NOW());

CREATE TABLE exception_alert (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  alert_no VARCHAR(80) NOT NULL,
  agv_no VARCHAR(80),
  location_no VARCHAR(80),
  alert_level VARCHAR(30),
  alert_content VARCHAR(220),
  handler_name VARCHAR(60),
  status VARCHAR(30) DEFAULT 'OPEN',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO exception_alert (alert_no, agv_no, location_no, alert_level, alert_content, handler_name, status, created_time, updated_time) VALUES
('ALT-118-001', 'AGV-118-001', 'LOC-118-A0101', '高', 'AGV在A区巷道等待超时', '周调度', 'OPEN', NOW(), NOW()),
('ALT-118-002', 'AGV-118-002', 'LOC-118-B0101', '中', 'AGV在B区巷道等待超时', '钱调度', 'OPEN', NOW(), NOW());

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
('系统管理员', 'AGV任务', 'CREATE', 'TASK-118-001', '创建入库上架AGV任务', '127.0.0.1', 'SUCCESS', NOW(), NOW()),
('系统管理员二号', 'AGV任务', 'CREATE', 'TASK-118-002', '创建入库上架AGV任务', '127.0.0.1', 'SUCCESS', NOW(), NOW());
