DROP DATABASE IF EXISTS aquaculture_monitor_129;
CREATE DATABASE aquaculture_monitor_129 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE aquaculture_monitor_129;

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
('admin', '123456', '系统管理员', 'ADMIN', '养殖中心', '13800012901', 'admin@demo.local', 1, NOW(), NOW()),
('breeder', '123456', '养殖员', 'BREEDER', '养殖组', '13800012902', 'breeder@demo.local', 1, NOW(), NOW()),
('technician', '123456', '水质技术员', 'TECHNICIAN', '水质组', '13800012903', 'technician@demo.local', 1, NOW(), NOW()),
('manager', '123456', '养殖经理', 'MANAGER', '生产管理部', '13800012904', 'manager@demo.local', 1, NOW(), NOW());

CREATE TABLE pond_profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  pond_no VARCHAR(120),
  pond_name VARCHAR(120),
  farm_area VARCHAR(120),
  water_area DECIMAL(12,2),
  manager_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO pond_profile (pond_no, pond_name, farm_area, water_area, manager_name, status, created_time, updated_time) VALUES
('PON-129-001', '池塘名称一', '养殖区域一', 30.3, '负责人一', 'ACTIVE', NOW(), NOW()),
('PON-129-002', '池塘名称二', '养殖区域二', 42.0, '负责人二', 'ACTIVE', NOW(), NOW());

CREATE TABLE sensor_device (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  sensor_no VARCHAR(120),
  pond_no VARCHAR(120),
  sensor_type VARCHAR(120),
  install_position VARCHAR(120),
  battery_level DECIMAL(12,2),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO sensor_device (sensor_no, pond_no, sensor_type, install_position, battery_level, status, created_time, updated_time) VALUES
('SEN-129-001', 'SEN-129-001', '设备类型一', '安装位置一', 30.3, 'ACTIVE', NOW(), NOW()),
('SEN-129-002', 'SEN-129-002', '设备类型二', '安装位置二', 42.0, 'ACTIVE', NOW(), NOW());

CREATE TABLE water_quality_reading (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  reading_no VARCHAR(120),
  pond_no VARCHAR(120),
  collect_time VARCHAR(120),
  dissolved_oxygen DECIMAL(12,2),
  ph_value DECIMAL(12,2),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO water_quality_reading (reading_no, pond_no, collect_time, dissolved_oxygen, ph_value, status, created_time, updated_time) VALUES
('WQR-129-001', 'WQR-129-001', '2026-05-06 10:00', 30.3, 30.3, 'NORMAL', NOW(), NOW()),
('WQR-129-002', 'WQR-129-002', '2026-05-07 10:00', 42.0, 42.0, 'NORMAL', NOW(), NOW());

CREATE TABLE feeding_plan (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  plan_no VARCHAR(120),
  pond_no VARCHAR(120),
  fish_species VARCHAR(120),
  feed_type VARCHAR(120),
  daily_amount DECIMAL(12,2),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO feeding_plan (plan_no, pond_no, fish_species, feed_type, daily_amount, status, created_time, updated_time) VALUES
('FPL-129-001', 'FPL-129-001', '鱼种一', '饲料类型一', 30.3, 'ACTIVE', NOW(), NOW()),
('FPL-129-002', 'FPL-129-002', '鱼种二', '饲料类型二', 42.0, 'ACTIVE', NOW(), NOW());

CREATE TABLE feeding_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  feed_no VARCHAR(120),
  pond_no VARCHAR(120),
  feed_time VARCHAR(120),
  feed_amount DECIMAL(12,2),
  operator_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO feeding_record (feed_no, pond_no, feed_time, feed_amount, operator_name, status, created_time, updated_time) VALUES
('FED-129-001', 'FED-129-001', '2026-05-06 10:00', 30.3, '操作人一', 'SUBMITTED', NOW(), NOW()),
('FED-129-002', 'FED-129-002', '2026-05-07 10:00', 42.0, '操作人二', 'SUBMITTED', NOW(), NOW());

CREATE TABLE fish_batch (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  batch_no VARCHAR(120),
  pond_no VARCHAR(120),
  species_name VARCHAR(120),
  seed_count INT,
  stock_date VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO fish_batch (batch_no, pond_no, species_name, seed_count, stock_date, status, created_time, updated_time) VALUES
('BAT-129-001', 'BAT-129-001', '鱼种一', 1560, '2026-05-06', 'ACTIVE', NOW(), NOW()),
('BAT-129-002', 'BAT-129-002', '鱼种二', 1920, '2026-05-07', 'ACTIVE', NOW(), NOW());

CREATE TABLE growth_sampling (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  sample_no VARCHAR(120),
  batch_no VARCHAR(120),
  sample_date VARCHAR(120),
  avg_weight DECIMAL(12,2),
  recorder_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO growth_sampling (sample_no, batch_no, sample_date, avg_weight, recorder_name, status, created_time, updated_time) VALUES
('SAM-129-001', 'SAM-129-001', '2026-05-06', 30.3, '记录人一', 'SUBMITTED', NOW(), NOW()),
('SAM-129-002', 'SAM-129-002', '2026-05-07', 42.0, '记录人二', 'SUBMITTED', NOW(), NOW());

CREATE TABLE disease_warning (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  warning_no VARCHAR(120),
  pond_no VARCHAR(120),
  warning_level VARCHAR(120),
  symptom_text VARCHAR(120),
  handler_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO disease_warning (warning_no, pond_no, warning_level, symptom_text, handler_name, status, created_time, updated_time) VALUES
('DIS-129-001', 'DIS-129-001', '高', '症状描述一', '处理人一', 'OPEN', NOW(), NOW()),
('DIS-129-002', 'DIS-129-002', '中', '症状描述二', '处理人二', 'OPEN', NOW(), NOW());

CREATE TABLE medication_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  med_no VARCHAR(120),
  pond_no VARCHAR(120),
  medicine_name VARCHAR(120),
  use_amount DECIMAL(12,2),
  use_date VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO medication_record (med_no, pond_no, medicine_name, use_amount, use_date, status, created_time, updated_time) VALUES
('MED-129-001', 'MED-129-001', '药品名称一', 30.3, '2026-05-06', 'SUBMITTED', NOW(), NOW()),
('MED-129-002', 'MED-129-002', '药品名称二', 42.0, '2026-05-07', 'SUBMITTED', NOW(), NOW());

CREATE TABLE equipment_device (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  device_no VARCHAR(120),
  pond_no VARCHAR(120),
  device_name VARCHAR(120),
  device_type VARCHAR(120),
  install_position VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO equipment_device (device_no, pond_no, device_name, device_type, install_position, status, created_time, updated_time) VALUES
('EQP-129-001', 'EQP-129-001', '设备名称一', '设备类型一', '安装位置一', 'ACTIVE', NOW(), NOW()),
('EQP-129-002', 'EQP-129-002', '设备名称二', '设备类型二', '安装位置二', 'ACTIVE', NOW(), NOW());

CREATE TABLE water_alert_rule (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  rule_no VARCHAR(120),
  rule_name VARCHAR(120),
  metric_name VARCHAR(120),
  threshold_value DECIMAL(12,2),
  alert_level VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO water_alert_rule (rule_no, rule_name, metric_name, threshold_value, alert_level, status, created_time, updated_time) VALUES
('RUL-129-001', '规则名称一', '指标名称一', 30.3, '高', 'ACTIVE', NOW(), NOW()),
('RUL-129-002', '规则名称二', '指标名称二', 42.0, '中', 'ACTIVE', NOW(), NOW());

CREATE TABLE production_statistic (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  stat_no VARCHAR(120),
  pond_no VARCHAR(120),
  stat_month VARCHAR(120),
  output_weight DECIMAL(12,2),
  survival_rate DECIMAL(12,2),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO production_statistic (stat_no, pond_no, stat_month, output_weight, survival_rate, status, created_time, updated_time) VALUES
('STA-129-001', 'STA-129-001', '2026-06', 30.3, 30.3, 'FINISHED', NOW(), NOW()),
('STA-129-002', 'STA-129-002', '2026-07', 42.0, 42.0, 'FINISHED', NOW(), NOW());

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
