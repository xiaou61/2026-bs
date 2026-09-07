DROP DATABASE IF EXISTS fresh_expiry_162;
CREATE DATABASE fresh_expiry_162 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE fresh_expiry_162;

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
('admin', '123456', '系统管理员', 'ADMIN', '生鲜门店运营中心', '13916200001', 'admin@fresh162.local', 1, NOW(), NOW()),
('manager', '123456', '门店店长', 'MANAGER', '生鲜门店运营中心', '13916200002', 'manager@fresh162.local', 1, NOW(), NOW()),
('clerk', '123456', '理货员', 'CLERK', '生鲜门店运营中心', '13916200003', 'clerk@fresh162.local', 1, NOW(), NOW()),
('stock', '123456', '库存主管', 'STOCK', '生鲜门店运营中心', '13916200004', 'stock@fresh162.local', 1, NOW(), NOW()),
('marketing', '123456', '促销专员', 'MARKETING', '生鲜门店运营中心', '13916200005', 'marketing@fresh162.local', 1, NOW(), NOW()),
('supplier', '123456', '供应商协同员', 'SUPPLIER', '生鲜门店运营中心', '13916200006', 'supplier@fresh162.local', 1, NOW(), NOW());

CREATE TABLE store_profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  record_name VARCHAR(120),
  category VARCHAR(120),
  owner_name VARCHAR(120),
  plan_time VARCHAR(120),
  status VARCHAR(40),
  remark VARCHAR(255),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO store_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('162-01-001', '门店档案示例一', '经营类型', '店长A', '2026-05-16 09:00', 'BOOKED', '门店编号、门店名称、经营类型、店长、启用时间和门店状态维护', NOW(), NOW()),
('162-01-002', '门店档案示例二', '经营类型', '店长B', '2026-05-17 14:00', 'VERIFIED', '门店档案演示数据二', NOW(), NOW());

CREATE TABLE supplier_profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  record_name VARCHAR(120),
  category VARCHAR(120),
  owner_name VARCHAR(120),
  plan_time VARCHAR(120),
  status VARCHAR(40),
  remark VARCHAR(255),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO supplier_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('162-02-001', '供应商档案示例一', '供货品类', '联系人A', '2026-05-16 09:00', 'SCHEDULED', '供应商编号、供应商名称、供货品类、联系人、合作时间和合作状态维护', NOW(), NOW()),
('162-02-002', '供应商档案示例二', '供货品类', '联系人B', '2026-05-17 14:00', 'PROCESSING', '供应商档案演示数据二', NOW(), NOW());

CREATE TABLE fresh_category (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  record_name VARCHAR(120),
  category VARCHAR(120),
  owner_name VARCHAR(120),
  plan_time VARCHAR(120),
  status VARCHAR(40),
  remark VARCHAR(255),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO fresh_category (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('162-03-001', '生鲜品类示例一', '温控类型', '负责人A', '2026-05-16 09:00', 'VERIFIED', '品类编号、品类名称、温控类型、负责人、启用时间和品类状态维护', NOW(), NOW()),
('162-03-002', '生鲜品类示例二', '温控类型', '负责人B', '2026-05-17 14:00', 'FINISHED', '生鲜品类演示数据二', NOW(), NOW());

CREATE TABLE product_batch (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  record_name VARCHAR(120),
  category VARCHAR(120),
  owner_name VARCHAR(120),
  plan_time VARCHAR(120),
  status VARCHAR(40),
  remark VARCHAR(255),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO product_batch (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('162-04-001', '商品批次示例一', '商品品类', '理货员A', '2026-05-16 09:00', 'PROCESSING', '批次编号、商品名称、商品品类、理货员、到货时间和批次状态维护', NOW(), NOW()),
('162-04-002', '商品批次示例二', '商品品类', '理货员B', '2026-05-17 14:00', 'WARNING', '商品批次演示数据二', NOW(), NOW());

CREATE TABLE shelf_life_rule (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  record_name VARCHAR(120),
  category VARCHAR(120),
  owner_name VARCHAR(120),
  plan_time VARCHAR(120),
  status VARCHAR(40),
  remark VARCHAR(255),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO shelf_life_rule (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('162-05-001', '保质期规则示例一', '预警类型', '负责人A', '2026-05-16 09:00', 'FINISHED', '规则编号、适用品类、预警类型、负责人、生效时间和规则状态维护', NOW(), NOW()),
('162-05-002', '保质期规则示例二', '预警类型', '负责人B', '2026-05-17 14:00', 'PUBLISHED', '保质期规则演示数据二', NOW(), NOW());

CREATE TABLE expiry_warning (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  record_name VARCHAR(120),
  category VARCHAR(120),
  owner_name VARCHAR(120),
  plan_time VARCHAR(120),
  status VARCHAR(40),
  remark VARCHAR(255),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO expiry_warning (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('162-06-001', '临期预警示例一', '预警等级', '处理人A', '2026-05-16 09:00', 'WARNING', '预警编号、预警商品、预警等级、处理人、预警时间和预警状态维护', NOW(), NOW()),
('162-06-002', '临期预警示例二', '预警等级', '处理人B', '2026-05-17 14:00', 'ACTIVE', '临期预警演示数据二', NOW(), NOW());

CREATE TABLE promotion_strategy (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  record_name VARCHAR(120),
  category VARCHAR(120),
  owner_name VARCHAR(120),
  plan_time VARCHAR(120),
  status VARCHAR(40),
  remark VARCHAR(255),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO promotion_strategy (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('162-07-001', '促销策略示例一', '促销类型', '促销专员A', '2026-05-16 09:00', 'PUBLISHED', '策略编号、促销商品、促销类型、促销专员、开始时间和策略状态维护', NOW(), NOW()),
('162-07-002', '促销策略示例二', '促销类型', '促销专员B', '2026-05-17 14:00', 'BOOKED', '促销策略演示数据二', NOW(), NOW());

CREATE TABLE discount_execution (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  record_name VARCHAR(120),
  category VARCHAR(120),
  owner_name VARCHAR(120),
  plan_time VARCHAR(120),
  status VARCHAR(40),
  remark VARCHAR(255),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO discount_execution (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('162-08-001', '折扣执行示例一', '折扣类型', '执行人A', '2026-05-16 09:00', 'ACTIVE', '执行编号、执行商品、折扣类型、执行人、执行时间和执行状态维护', NOW(), NOW()),
('162-08-002', '折扣执行示例二', '折扣类型', '执行人B', '2026-05-17 14:00', 'SCHEDULED', '折扣执行演示数据二', NOW(), NOW());

CREATE TABLE loss_report (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  record_name VARCHAR(120),
  category VARCHAR(120),
  owner_name VARCHAR(120),
  plan_time VARCHAR(120),
  status VARCHAR(40),
  remark VARCHAR(255),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO loss_report (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('162-09-001', '报损记录示例一', '报损原因', '登记人A', '2026-05-16 09:00', 'BOOKED', '报损编号、报损商品、报损原因、登记人、报损时间和报损状态维护', NOW(), NOW()),
('162-09-002', '报损记录示例二', '报损原因', '登记人B', '2026-05-17 14:00', 'VERIFIED', '报损记录演示数据二', NOW(), NOW());

CREATE TABLE inventory_turnover (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  record_name VARCHAR(120),
  category VARCHAR(120),
  owner_name VARCHAR(120),
  plan_time VARCHAR(120),
  status VARCHAR(40),
  remark VARCHAR(255),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO inventory_turnover (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('162-10-001', '库存周转示例一', '周转类型', '负责人A', '2026-05-16 09:00', 'SCHEDULED', '周转编号、周转商品、周转类型、负责人、统计时间和周转状态维护', NOW(), NOW()),
('162-10-002', '库存周转示例二', '周转类型', '负责人B', '2026-05-17 14:00', 'PROCESSING', '库存周转演示数据二', NOW(), NOW());

CREATE TABLE store_analysis (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  record_name VARCHAR(120),
  category VARCHAR(120),
  owner_name VARCHAR(120),
  plan_time VARCHAR(120),
  status VARCHAR(40),
  remark VARCHAR(255),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO store_analysis (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('162-11-001', '门店分析示例一', '分析类型', '分析人A', '2026-05-16 09:00', 'VERIFIED', '分析编号、分析门店、分析类型、分析人、分析时间和分析状态维护', NOW(), NOW()),
('162-11-002', '门店分析示例二', '分析类型', '分析人B', '2026-05-17 14:00', 'FINISHED', '门店分析演示数据二', NOW(), NOW());

CREATE TABLE operation_log (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  record_name VARCHAR(120),
  category VARCHAR(120),
  owner_name VARCHAR(120),
  plan_time VARCHAR(120),
  status VARCHAR(40),
  remark VARCHAR(255),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO operation_log (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('162-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'PROCESSING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('162-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'WARNING', '操作日志演示数据二', NOW(), NOW());
