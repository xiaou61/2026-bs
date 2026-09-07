DROP DATABASE IF EXISTS asset_rfid_141;
CREATE DATABASE asset_rfid_141 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE asset_rfid_141;

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
('admin', '123456', '系统管理员', 'ADMIN', '信息管理部', '139141000100', 'admin@demo.local', 1, NOW(), NOW()),
('assetadmin', '123456', '资产管理员', 'ASSET_ADMIN', '固定资产管理部', '139141000200', 'assetadmin@demo.local', 1, NOW(), NOW()),
('borrower', '123456', '借用人员', 'BORROWER', '综合业务部', '139141000300', 'borrower@demo.local', 1, NOW(), NOW()),
('auditor', '123456', '审核专员', 'AUDITOR', '审计财务部', '139141000400', 'auditor@demo.local', 1, NOW(), NOW());

CREATE TABLE asset_info (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  asset_no VARCHAR(120),
  asset_name VARCHAR(120),
  asset_model VARCHAR(120),
  department_name VARCHAR(120),
  service_life_months INT,
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO asset_info (asset_no, asset_name, asset_model, department_name, service_life_months, status, created_time, updated_time) VALUES
('FA-141-001', '手持 RFID 盘点终端', 'PDA-R600', '固定资产管理部', 36, 'ACTIVE', NOW(), NOW()),
('FA-141-002', '会议室投影设备', 'PROJ-X22', '行政保障部', 60, 'DRAFT', NOW(), NOW());

CREATE TABLE asset_category (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  category_no VARCHAR(120),
  category_name VARCHAR(120),
  depreciation_method VARCHAR(120),
  useful_life VARCHAR(120),
  manager_name VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO asset_category (category_no, category_name, depreciation_method, useful_life, manager_name, status, created_time, updated_time) VALUES
('CAT-141-001', '信息化设备', '年限平均法', '36个月', '资产管理员', 'ACTIVE', NOW(), NOW()),
('CAT-141-002', '办公家具', '年限平均法', '60个月', '系统管理员', 'FINISHED', NOW(), NOW());

CREATE TABLE rfid_tag (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  tag_no VARCHAR(120),
  epc_code VARCHAR(120),
  asset_name VARCHAR(120),
  storage_area VARCHAR(120),
  manager_phone VARCHAR(30),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO rfid_tag (tag_no, epc_code, asset_name, storage_area, manager_phone, status, created_time, updated_time) VALUES
('TAG-141-001', 'EPC0001410001', '手持 RFID 盘点终端', '资产库房 A 区', '139141000201', 'ACTIVE', NOW(), NOW()),
('TAG-141-002', 'EPC0001410002', '会议室投影设备', '办公楼 6F 会议室', '139141000202', 'FINISHED', NOW(), NOW());

CREATE TABLE storage_location (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  location_no VARCHAR(120),
  asset_name VARCHAR(120),
  location_name VARCHAR(120),
  current_qty INT,
  locked_qty INT,
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO storage_location (location_no, asset_name, location_name, current_qty, locked_qty, status, created_time, updated_time) VALUES
('LOC-141-001', '手持 RFID 盘点终端', '资产库房 A 区', 8, 1, 'NORMAL', NOW(), NOW()),
('LOC-141-002', '会议室投影设备', '办公楼 6F 会议室', 2, 1, 'PROCESSING', NOW(), NOW());

CREATE TABLE inventory_task (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  task_no VARCHAR(120),
  asset_name VARCHAR(120),
  planned_count INT,
  executor_name VARCHAR(120),
  task_time VARCHAR(40),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO inventory_task (task_no, asset_name, planned_count, executor_name, task_time, status, created_time, updated_time) VALUES
('IT-141-001', '手持 RFID 盘点终端', 8, '资产管理员', '2026-05-15 09:00', 'SUBMITTED', NOW(), NOW()),
('IT-141-002', '会议室投影设备', 2, '审核专员', '2026-05-16 14:00', 'APPROVED', NOW(), NOW());

CREATE TABLE inventory_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  task_no VARCHAR(120),
  checker_name VARCHAR(120),
  difference_note VARCHAR(255),
  check_time VARCHAR(40),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO inventory_record (record_no, task_no, checker_name, difference_note, check_time, status, created_time, updated_time) VALUES
('IR-141-001', 'IT-141-001', '资产管理员', '账实一致，无盘亏', '2026-05-15 11:30', 'SUBMITTED', NOW(), NOW()),
('IR-141-002', 'IT-141-002', '审核专员', '标签脱落 1 台，已补录', '2026-05-16 16:20', 'APPROVED', NOW(), NOW());

CREATE TABLE borrow_application (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  application_no VARCHAR(120),
  borrower_name VARCHAR(120),
  asset_name VARCHAR(120),
  borrow_days INT,
  planned_return_date VARCHAR(40),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO borrow_application (application_no, borrower_name, asset_name, borrow_days, planned_return_date, status, created_time, updated_time) VALUES
('BA-141-001', '借用人员', '手持 RFID 盘点终端', 7, '2026-05-22', 'PROCESSING', NOW(), NOW()),
('BA-141-002', '系统管理员', '会议室投影设备', 2, '2026-05-18', 'FINISHED', NOW(), NOW());

CREATE TABLE return_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  return_no VARCHAR(120),
  application_no VARCHAR(120),
  asset_name VARCHAR(120),
  return_qty INT,
  operator_name VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO return_record (return_no, application_no, asset_name, return_qty, operator_name, status, created_time, updated_time) VALUES
('RT-141-001', 'BA-141-001', '手持 RFID 盘点终端', 1, '借用人员', 'SUBMITTED', NOW(), NOW()),
('RT-141-002', 'BA-141-002', '会议室投影设备', 1, '资产管理员', 'APPROVED', NOW(), NOW());

CREATE TABLE repair_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  repair_no VARCHAR(120),
  asset_name VARCHAR(120),
  fault_location VARCHAR(120),
  repair_hours INT,
  contact_name VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO repair_record (repair_no, asset_name, fault_location, repair_hours, contact_name, status, created_time, updated_time) VALUES
('RP-141-001', '会议室投影设备', '办公楼 6F 会议室', 4, '借用人员', 'SUBMITTED', NOW(), NOW()),
('RP-141-002', '手持 RFID 盘点终端', '资产库房 A 区', 2, '资产管理员', 'APPROVED', NOW(), NOW());

CREATE TABLE depreciation_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  depreciation_no VARCHAR(120),
  asset_name VARCHAR(120),
  department_name VARCHAR(120),
  original_value INT,
  net_value INT,
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO depreciation_record (depreciation_no, asset_name, department_name, original_value, net_value, status, created_time, updated_time) VALUES
('DP-141-001', '手持 RFID 盘点终端', '固定资产管理部', 12000, 9800, 'PROCESSING', NOW(), NOW()),
('DP-141-002', '会议室投影设备', '行政保障部', 18000, 12600, 'FINISHED', NOW(), NOW());

CREATE TABLE disposal_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  disposal_no VARCHAR(120),
  asset_name VARCHAR(120),
  disposal_qty INT,
  disposal_type VARCHAR(120),
  handler_name VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO disposal_record (disposal_no, asset_name, disposal_qty, disposal_type, handler_name, status, created_time, updated_time) VALUES
('DS-141-001', '老旧条码扫描枪', 3, '报废处置', '审核专员', 'ACTIVE', NOW(), NOW()),
('DS-141-002', '损坏办公桌', 2, '转移处置', '系统管理员', 'FINISHED', NOW(), NOW());

CREATE TABLE warning_notice (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  notice_no VARCHAR(120),
  asset_name VARCHAR(120),
  remaining_count INT,
  warning_type VARCHAR(120),
  handler_name VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO warning_notice (notice_no, asset_name, remaining_count, warning_type, handler_name, status, created_time, updated_time) VALUES
('WN-141-001', '手持 RFID 盘点终端', 1, '超期未还预警', '资产管理员', 'WARNING', NOW(), NOW()),
('WN-141-002', '会议室投影设备', 0, '维修逾期提醒', '审核专员', 'PROCESSING', NOW(), NOW());

CREATE TABLE operation_log (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  operator_name VARCHAR(120),
  module_name VARCHAR(120),
  action_type VARCHAR(120),
  target_name VARCHAR(120),
  detail_info VARCHAR(255),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO operation_log (operator_name, module_name, action_type, target_name, detail_info, status, created_time, updated_time) VALUES
('系统管理员', '资产档案', '新增', '手持 RFID 盘点终端', '新增固定资产档案并绑定盘点周期', 'SUCCESS', NOW(), NOW()),
('资产管理员', '借用申请', '处理', 'BA-141-001', '审核借用申请并下发归还日期提醒', 'SUCCESS', NOW(), NOW());
