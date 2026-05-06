DROP DATABASE IF EXISTS conference_review_135;
CREATE DATABASE conference_review_135 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE conference_review_135;

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
('admin', '123456', '系统管理员', 'ADMIN', '实验中心', '139133000100', 'admin@demo.local', 1, NOW(), NOW()),
('manager', '123456', '会务管理员', 'MANAGER', '会务办公室', '139135000200', 'manager@demo.local', 1, NOW(), NOW()),
('reviewer', '123456', '审稿专家', 'REVIEWER', '学术委员会', '139135000300', 'reviewer@demo.local', 1, NOW(), NOW()),
('secretary', '123456', '议程秘书', 'SECRETARY', '会议秘书处', '139135000400', 'secretary@demo.local', 1, NOW(), NOW());

CREATE TABLE consumable_catalog (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  consumable_no VARCHAR(120),
  consumable_name VARCHAR(120),
  spec_model VARCHAR(120),
  unit_name VARCHAR(120),
  safe_stock INT,
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO consumable_catalog (consumable_no, consumable_name, spec_model, unit_name, safe_stock, status, created_time, updated_time) VALUES
('CON-133-001', '耗材名称一', '规格型号一', '计量单位一', 35, 'ACTIVE', NOW(), NOW()),
('CON-133-002', '耗材名称二', '规格型号二', '计量单位二', 40, 'ACTIVE', NOW(), NOW());

CREATE TABLE supplier_profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  supplier_no VARCHAR(120),
  supplier_name VARCHAR(120),
  contact_name VARCHAR(120),
  phone_number VARCHAR(120),
  qualification_level VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO supplier_profile (supplier_no, supplier_name, contact_name, phone_number, qualification_level, status, created_time, updated_time) VALUES
('SUP-133-001', '供应商名称一', '联系人一', '139133000101', '资质等级一', 'ACTIVE', NOW(), NOW()),
('SUP-133-002', '供应商名称二', '联系人二', '139133000201', '资质等级二', 'ACTIVE', NOW(), NOW());

CREATE TABLE lab_room (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  lab_no VARCHAR(120),
  lab_name VARCHAR(120),
  building_name VARCHAR(120),
  manager_name VARCHAR(120),
  phone_number VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO lab_room (lab_no, lab_name, building_name, manager_name, phone_number, status, created_time, updated_time) VALUES
('LAB-133-001', '实验室名称一', '楼栋位置一', '负责人一', '139133000101', 'ACTIVE', NOW(), NOW()),
('LAB-133-002', '实验室名称二', '楼栋位置二', '负责人二', '139133000201', 'ACTIVE', NOW(), NOW());

CREATE TABLE stock_item (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  stock_no VARCHAR(120),
  consumable_name VARCHAR(120),
  lab_name VARCHAR(120),
  current_qty INT,
  locked_qty INT,
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO stock_item (stock_no, consumable_name, lab_name, current_qty, locked_qty, status, created_time, updated_time) VALUES
('STK-133-001', '耗材名称一', '实验室一', 35, 35, 'NORMAL', NOW(), NOW()),
('STK-133-002', '耗材名称二', '实验室二', 40, 40, 'NORMAL', NOW(), NOW());

CREATE TABLE purchase_request (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  request_no VARCHAR(120),
  consumable_name VARCHAR(120),
  request_qty INT,
  applicant_name VARCHAR(120),
  request_time VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO purchase_request (request_no, consumable_name, request_qty, applicant_name, request_time, status, created_time, updated_time) VALUES
('REQ-133-001', '耗材名称一', 35, '申请人一', '2026-05-6 10:00', 'SUBMITTED', NOW(), NOW()),
('REQ-133-002', '耗材名称二', 40, '申请人二', '2026-05-7 10:00', 'SUBMITTED', NOW(), NOW());

CREATE TABLE purchase_approval (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  approval_no VARCHAR(120),
  request_no VARCHAR(120),
  approver_name VARCHAR(120),
  approval_opinion VARCHAR(120),
  approval_time VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO purchase_approval (approval_no, request_no, approver_name, approval_opinion, approval_time, status, created_time, updated_time) VALUES
('APR-133-001', 'APR-133-001', '审批人一', '审批意见一', '2026-05-6 10:00', 'REVIEWING', NOW(), NOW()),
('APR-133-002', 'APR-133-002', '审批人二', '审批意见二', '2026-05-7 10:00', 'REVIEWING', NOW(), NOW());

CREATE TABLE purchase_order (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  order_no VARCHAR(120),
  supplier_name VARCHAR(120),
  consumable_name VARCHAR(120),
  order_amount DECIMAL(12,2),
  arrival_date VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO purchase_order (order_no, supplier_name, consumable_name, order_amount, arrival_date, status, created_time, updated_time) VALUES
('ORD-133-001', '供应商一', '耗材名称一', 38.5, '2026-05-6', 'OPEN', NOW(), NOW()),
('ORD-133-002', '供应商二', '耗材名称二', 51.5, '2026-05-7', 'OPEN', NOW(), NOW());

CREATE TABLE inbound_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  inbound_no VARCHAR(120),
  order_no VARCHAR(120),
  consumable_name VARCHAR(120),
  inbound_qty INT,
  operator_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO inbound_record (inbound_no, order_no, consumable_name, inbound_qty, operator_name, status, created_time, updated_time) VALUES
('INB-133-001', 'INB-133-001', '耗材名称一', 35, '入库人一', 'FINISHED', NOW(), NOW()),
('INB-133-002', 'INB-133-002', '耗材名称二', 40, '入库人二', 'FINISHED', NOW(), NOW());

CREATE TABLE outbound_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  outbound_no VARCHAR(120),
  consumable_name VARCHAR(120),
  lab_name VARCHAR(120),
  outbound_qty INT,
  receiver_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO outbound_record (outbound_no, consumable_name, lab_name, outbound_qty, receiver_name, status, created_time, updated_time) VALUES
('OUT-133-001', '耗材名称一', '领用实验室一', 35, '领用人一', 'SUBMITTED', NOW(), NOW()),
('OUT-133-002', '耗材名称二', '领用实验室二', 40, '领用人二', 'SUBMITTED', NOW(), NOW());

CREATE TABLE inventory_check (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  check_no VARCHAR(120),
  lab_name VARCHAR(120),
  consumable_name VARCHAR(120),
  book_qty INT,
  actual_qty INT,
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO inventory_check (check_no, lab_name, consumable_name, book_qty, actual_qty, status, created_time, updated_time) VALUES
('CHK-133-001', '实验室一', '耗材名称一', 35, 35, 'PROCESSING', NOW(), NOW()),
('CHK-133-002', '实验室二', '耗材名称二', 40, 40, 'PROCESSING', NOW(), NOW());

CREATE TABLE warning_rule (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  rule_no VARCHAR(120),
  category_name VARCHAR(120),
  min_stock INT,
  warning_level VARCHAR(120),
  notice_target VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO warning_rule (rule_no, category_name, min_stock, warning_level, notice_target, status, created_time, updated_time) VALUES
('RUL-133-001', '耗材分类一', 35, '预警级别一', '通知对象一', 'ACTIVE', NOW(), NOW()),
('RUL-133-002', '耗材分类二', 40, '预警级别二', '通知对象二', 'ACTIVE', NOW(), NOW());

CREATE TABLE stock_warning (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  warning_no VARCHAR(120),
  consumable_name VARCHAR(120),
  current_qty INT,
  warning_level VARCHAR(120),
  handler_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO stock_warning (warning_no, consumable_name, current_qty, warning_level, handler_name, status, created_time, updated_time) VALUES
('WAR-133-001', '耗材名称一', 35, '预警级别一', '处理人一', 'WARNING', NOW(), NOW()),
('WAR-133-002', '耗材名称二', 40, '预警级别二', '处理人二', 'WARNING', NOW(), NOW());

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


