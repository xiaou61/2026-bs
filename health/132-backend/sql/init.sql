DROP DATABASE IF EXISTS medical_device_132;
CREATE DATABASE medical_device_132 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE medical_device_132;

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
('admin', '123456', '系统管理员', 'ADMIN', '器械中心', '13800013201', 'admin@demo.local', 1, NOW(), NOW()),
('nurse', '123456', '借用护士', 'NURSE', '临床科室', '13800013202', 'nurse@demo.local', 1, NOW(), NOW()),
('sterilizer', '123456', '消毒员', 'STERILIZER', '消毒供应室', '13800013203', 'sterilizer@demo.local', 1, NOW(), NOW()),
('manager', '123456', '器械主管', 'MANAGER', '器械管理部', '13800013204', 'manager@demo.local', 1, NOW(), NOW());

CREATE TABLE device_profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  device_no VARCHAR(120),
  device_name VARCHAR(120),
  model_name VARCHAR(120),
  dept_name VARCHAR(120),
  owner_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO device_profile (device_no, device_name, model_name, dept_name, owner_name, status, created_time, updated_time) VALUES
('DEV-132-001', '器械名称一', '型号一', '所属科室一', '责任人一', 'ACTIVE', NOW(), NOW()),
('DEV-132-002', '器械名称二', '型号二', '所属科室二', '责任人二', 'ACTIVE', NOW(), NOW());

CREATE TABLE device_category (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  category_no VARCHAR(120),
  category_name VARCHAR(120),
  risk_level VARCHAR(120),
  sterilize_method VARCHAR(120),
  manager_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO device_category (category_no, category_name, risk_level, sterilize_method, manager_name, status, created_time, updated_time) VALUES
('CAT-132-001', '分类名称一', '高', '消毒方式一', '负责人一', 'ACTIVE', NOW(), NOW()),
('CAT-132-002', '分类名称二', '中', '消毒方式二', '负责人二', 'ACTIVE', NOW(), NOW());

CREATE TABLE department_info (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  dept_no VARCHAR(120),
  dept_name VARCHAR(120),
  floor_name VARCHAR(120),
  contact_name VARCHAR(120),
  phone_number VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO department_info (dept_no, dept_name, floor_name, contact_name, phone_number, status, created_time, updated_time) VALUES
('DEP-132-001', '科室名称一', '所在楼层一', '联系人一', '13800013221', 'ACTIVE', NOW(), NOW()),
('DEP-132-002', '科室名称二', '所在楼层二', '联系人二', '13800013222', 'ACTIVE', NOW(), NOW());

CREATE TABLE borrow_request (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  request_no VARCHAR(120),
  device_no VARCHAR(120),
  dept_name VARCHAR(120),
  applicant_name VARCHAR(120),
  request_time VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO borrow_request (request_no, device_no, dept_name, applicant_name, request_time, status, created_time, updated_time) VALUES
('REQ-132-001', 'REQ-132-001', '申请科室一', '申请人一', '2026-05-06 10:00', 'SUBMITTED', NOW(), NOW()),
('REQ-132-002', 'REQ-132-002', '申请科室二', '申请人二', '2026-05-07 10:00', 'SUBMITTED', NOW(), NOW());

CREATE TABLE borrow_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  borrow_no VARCHAR(120),
  request_no VARCHAR(120),
  device_no VARCHAR(120),
  borrow_time VARCHAR(120),
  borrower_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO borrow_record (borrow_no, request_no, device_no, borrow_time, borrower_name, status, created_time, updated_time) VALUES
('BOR-132-001', 'BOR-132-001', 'BOR-132-001', '2026-05-06 10:00', '借用人一', 'OPEN', NOW(), NOW()),
('BOR-132-002', 'BOR-132-002', 'BOR-132-002', '2026-05-07 10:00', '借用人二', 'OPEN', NOW(), NOW());

CREATE TABLE return_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  return_no VARCHAR(120),
  borrow_no VARCHAR(120),
  return_time VARCHAR(120),
  check_result VARCHAR(120),
  receiver_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO return_record (return_no, borrow_no, return_time, check_result, receiver_name, status, created_time, updated_time) VALUES
('RET-132-001', 'RET-132-001', '2026-05-06 10:00', '检查结果一', '接收人一', 'SUBMITTED', NOW(), NOW()),
('RET-132-002', 'RET-132-002', '2026-05-07 10:00', '检查结果二', '接收人二', 'SUBMITTED', NOW(), NOW());

CREATE TABLE sterilization_batch (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  batch_no VARCHAR(120),
  sterilize_method VARCHAR(120),
  machine_name VARCHAR(120),
  owner_name VARCHAR(120),
  start_time VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO sterilization_batch (batch_no, sterilize_method, machine_name, owner_name, start_time, status, created_time, updated_time) VALUES
('STB-132-001', '消毒方式一', '消毒设备一', '负责人一', '2026-05-06 10:00', 'OPEN', NOW(), NOW()),
('STB-132-002', '消毒方式二', '消毒设备二', '负责人二', '2026-05-07 10:00', 'OPEN', NOW(), NOW());

CREATE TABLE sterilization_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  batch_no VARCHAR(120),
  device_no VARCHAR(120),
  sterilize_result VARCHAR(120),
  operator_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO sterilization_record (record_no, batch_no, device_no, sterilize_result, operator_name, status, created_time, updated_time) VALUES
('STR-132-001', 'STR-132-001', 'STR-132-001', '消毒结果一', '操作人一', 'SUBMITTED', NOW(), NOW()),
('STR-132-002', 'STR-132-002', 'STR-132-002', '消毒结果二', '操作人二', 'SUBMITTED', NOW(), NOW());

CREATE TABLE qr_trace (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  trace_no VARCHAR(120),
  qr_code VARCHAR(120),
  device_no VARCHAR(120),
  current_location VARCHAR(120),
  flow_status VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO qr_trace (trace_no, qr_code, device_no, current_location, flow_status, status, created_time, updated_time) VALUES
('QRT-132-001', '二维码一', 'QRT-132-001', '当前位置一', '流转状态一', 'ACTIVE', NOW(), NOW()),
('QRT-132-002', '二维码二', 'QRT-132-002', '当前位置二', '流转状态二', 'ACTIVE', NOW(), NOW());

CREATE TABLE maintenance_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  maintenance_no VARCHAR(120),
  device_no VARCHAR(120),
  maintenance_type VARCHAR(120),
  maintenance_time VARCHAR(120),
  maintainer_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO maintenance_record (maintenance_no, device_no, maintenance_type, maintenance_time, maintainer_name, status, created_time, updated_time) VALUES
('MNT-132-001', 'MNT-132-001', '维护类型一', '2026-05-06 10:00', '维护人一', 'SUBMITTED', NOW(), NOW()),
('MNT-132-002', 'MNT-132-002', '维护类型二', '2026-05-07 10:00', '维护人二', 'SUBMITTED', NOW(), NOW());

CREATE TABLE inspection_task (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  inspection_no VARCHAR(120),
  device_no VARCHAR(120),
  inspection_date VARCHAR(120),
  inspector_name VARCHAR(120),
  result_text VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO inspection_task (inspection_no, device_no, inspection_date, inspector_name, result_text, status, created_time, updated_time) VALUES
('INS-132-001', 'INS-132-001', '2026-05-06', '巡检人一', '巡检结果一', 'OPEN', NOW(), NOW()),
('INS-132-002', 'INS-132-002', '2026-05-07', '巡检人二', '巡检结果二', 'OPEN', NOW(), NOW());

CREATE TABLE risk_alert (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  alert_no VARCHAR(120),
  device_no VARCHAR(120),
  alert_level VARCHAR(120),
  trigger_reason VARCHAR(120),
  handler_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO risk_alert (alert_no, device_no, alert_level, trigger_reason, handler_name, status, created_time, updated_time) VALUES
('ALT-132-001', 'ALT-132-001', '高', '触发原因一', '处理人一', 'OPEN', NOW(), NOW()),
('ALT-132-002', 'ALT-132-002', '中', '触发原因二', '处理人二', 'OPEN', NOW(), NOW());

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
