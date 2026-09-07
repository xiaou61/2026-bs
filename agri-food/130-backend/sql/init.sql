DROP DATABASE IF EXISTS greenhouse_iot_130;
CREATE DATABASE greenhouse_iot_130 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE greenhouse_iot_130;

CREATE TABLE sys_user (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(60) NOT NULL UNIQUE,
  password VARCHAR(100) NOT NULL,
  nickname VARCHAR(80),
  role VARCHAR(40),
  department VARCHAR(100),
  phone VARCHAR(30),
  email VARCHAR(100),
  status INT DEFAULT 1,
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO sys_user (username, password, nickname, role, department, phone, email, status, created_time, updated_time) VALUES
('admin', '123456', '系统管理员', 'ADMIN', '温室中心', '13800013001', 'admin@demo.local', 1, NOW(), NOW()),
('grower', '123456', '种植员', 'GROWER', '种植组', '13800013002', 'grower@demo.local', 1, NOW(), NOW()),
('technician', '123456', '设备技术员', 'TECHNICIAN', '设备组', '13800013003', 'technician@demo.local', 1, NOW(), NOW()),
('manager', '123456', '园区经理', 'MANAGER', '园区管理部', '13800013004', 'manager@demo.local', 1, NOW(), NOW());

CREATE TABLE greenhouse_profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  greenhouse_no VARCHAR(120),
  greenhouse_name VARCHAR(120),
  base_name VARCHAR(120),
  area_size DECIMAL(12,2),
  manager_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO greenhouse_profile (greenhouse_no, greenhouse_name, base_name, area_size, manager_name, status, created_time, updated_time) VALUES
('GRE-130-001', '温室名称一', '基地名称一', 30.3, '负责人一', 'ACTIVE', NOW(), NOW()),
('GRE-130-002', '温室名称二', '基地名称二', 42.0, '负责人二', 'ACTIVE', NOW(), NOW());

CREATE TABLE crop_batch (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  batch_no VARCHAR(120),
  greenhouse_no VARCHAR(120),
  crop_name VARCHAR(120),
  plant_date VARCHAR(120),
  owner_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO crop_batch (batch_no, greenhouse_no, crop_name, plant_date, owner_name, status, created_time, updated_time) VALUES
('CRP-130-001', 'CRP-130-001', '作物名称一', '2026-05-06', '负责人一', 'ACTIVE', NOW(), NOW()),
('CRP-130-002', 'CRP-130-002', '作物名称二', '2026-05-07', '负责人二', 'ACTIVE', NOW(), NOW());

CREATE TABLE environment_sensor (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  sensor_no VARCHAR(120),
  greenhouse_no VARCHAR(120),
  sensor_type VARCHAR(120),
  install_position VARCHAR(120),
  battery_level DECIMAL(12,2),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO environment_sensor (sensor_no, greenhouse_no, sensor_type, install_position, battery_level, status, created_time, updated_time) VALUES
('SEN-130-001', 'SEN-130-001', '设备类型一', '安装位置一', 30.3, 'ACTIVE', NOW(), NOW()),
('SEN-130-002', 'SEN-130-002', '设备类型二', '安装位置二', 42.0, 'ACTIVE', NOW(), NOW());

CREATE TABLE environment_reading (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  reading_no VARCHAR(120),
  greenhouse_no VARCHAR(120),
  collect_time VARCHAR(120),
  temperature_value DECIMAL(12,2),
  humidity_value DECIMAL(12,2),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO environment_reading (reading_no, greenhouse_no, collect_time, temperature_value, humidity_value, status, created_time, updated_time) VALUES
('ENV-130-001', 'ENV-130-001', '2026-05-06 10:00', 30.3, 30.3, 'NORMAL', NOW(), NOW()),
('ENV-130-002', 'ENV-130-002', '2026-05-07 10:00', 42.0, 42.0, 'NORMAL', NOW(), NOW());

CREATE TABLE irrigation_task (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  task_no VARCHAR(120),
  greenhouse_no VARCHAR(120),
  task_time VARCHAR(120),
  water_amount DECIMAL(12,2),
  executor_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO irrigation_task (task_no, greenhouse_no, task_time, water_amount, executor_name, status, created_time, updated_time) VALUES
('IRR-130-001', 'IRR-130-001', '2026-05-06 10:00', 30.3, '执行人一', 'OPEN', NOW(), NOW()),
('IRR-130-002', 'IRR-130-002', '2026-05-07 10:00', 42.0, '执行人二', 'OPEN', NOW(), NOW());

CREATE TABLE fertilizer_plan (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  plan_no VARCHAR(120),
  greenhouse_no VARCHAR(120),
  fertilizer_type VARCHAR(120),
  plan_amount DECIMAL(12,2),
  owner_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO fertilizer_plan (plan_no, greenhouse_no, fertilizer_type, plan_amount, owner_name, status, created_time, updated_time) VALUES
('FER-130-001', 'FER-130-001', '肥料类型一', 30.3, '负责人一', 'ACTIVE', NOW(), NOW()),
('FER-130-002', 'FER-130-002', '肥料类型二', 42.0, '负责人二', 'ACTIVE', NOW(), NOW());

CREATE TABLE pest_warning (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  warning_no VARCHAR(120),
  greenhouse_no VARCHAR(120),
  pest_type VARCHAR(120),
  warning_level VARCHAR(120),
  handler_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO pest_warning (warning_no, greenhouse_no, pest_type, warning_level, handler_name, status, created_time, updated_time) VALUES
('PES-130-001', 'PES-130-001', '虫害类型一', '高', '处理人一', 'OPEN', NOW(), NOW()),
('PES-130-002', 'PES-130-002', '虫害类型二', '中', '处理人二', 'OPEN', NOW(), NOW());

CREATE TABLE disease_diagnosis (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  diagnosis_no VARCHAR(120),
  batch_no VARCHAR(120),
  disease_name VARCHAR(120),
  suggestion_text VARCHAR(120),
  expert_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO disease_diagnosis (diagnosis_no, batch_no, disease_name, suggestion_text, expert_name, status, created_time, updated_time) VALUES
('DIA-130-001', 'DIA-130-001', '病害名称一', '建议方案一', '专家一', 'REVIEWING', NOW(), NOW()),
('DIA-130-002', 'DIA-130-002', '病害名称二', '建议方案二', '专家二', 'REVIEWING', NOW(), NOW());

CREATE TABLE control_device (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  device_no VARCHAR(120),
  greenhouse_no VARCHAR(120),
  device_name VARCHAR(120),
  device_type VARCHAR(120),
  control_mode VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO control_device (device_no, greenhouse_no, device_name, device_type, control_mode, status, created_time, updated_time) VALUES
('DEV-130-001', 'DEV-130-001', '设备名称一', '设备类型一', '控制模式一', 'ACTIVE', NOW(), NOW()),
('DEV-130-002', 'DEV-130-002', '设备名称二', '设备类型二', '控制模式二', 'ACTIVE', NOW(), NOW());

CREATE TABLE remote_command (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  command_no VARCHAR(120),
  device_no VARCHAR(120),
  command_type VARCHAR(120),
  param_text VARCHAR(120),
  operator_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO remote_command (command_no, device_no, command_type, param_text, operator_name, status, created_time, updated_time) VALUES
('CMD-130-001', 'CMD-130-001', '指令类型一', '指令参数一', '下发人一', 'OPEN', NOW(), NOW()),
('CMD-130-002', 'CMD-130-002', '指令类型二', '指令参数二', '下发人二', 'OPEN', NOW(), NOW());

CREATE TABLE harvest_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  harvest_no VARCHAR(120),
  batch_no VARCHAR(120),
  harvest_date VARCHAR(120),
  harvest_weight DECIMAL(12,2),
  quality_level VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO harvest_record (harvest_no, batch_no, harvest_date, harvest_weight, quality_level, status, created_time, updated_time) VALUES
('HAR-130-001', 'HAR-130-001', '2026-05-06', 30.3, '高', 'FINISHED', NOW(), NOW()),
('HAR-130-002', 'HAR-130-002', '2026-05-07', 42.0, '中', 'FINISHED', NOW(), NOW());

CREATE TABLE maintenance_ticket (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  ticket_no VARCHAR(120),
  device_no VARCHAR(120),
  issue_type VARCHAR(120),
  owner_name VARCHAR(120),
  deadline_time VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO maintenance_ticket (ticket_no, device_no, issue_type, owner_name, deadline_time, status, created_time, updated_time) VALUES
('TCK-130-001', 'TCK-130-001', '问题类型一', '负责人一', '2026-05-06 10:00', 'OPEN', NOW(), NOW()),
('TCK-130-002', 'TCK-130-002', '问题类型二', '负责人二', '2026-05-07 10:00', 'OPEN', NOW(), NOW());

CREATE TABLE operation_log (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  operator_name VARCHAR(120),
  module_name VARCHAR(120),
  action_type VARCHAR(120),
  target_name VARCHAR(120),
  detail_info VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO operation_log (operator_name, module_name, action_type, target_name, detail_info, status, created_time, updated_time) VALUES
('操作人一', '模块名称一', '动作类型一', '操作对象一', '操作详情一', 'SUCCESS', NOW(), NOW()),
('操作人二', '模块名称二', '动作类型二', '操作对象二', '操作详情二', 'SUCCESS', NOW(), NOW());
