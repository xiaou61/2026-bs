DROP DATABASE IF EXISTS club_finance_160;
CREATE DATABASE club_finance_160 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE club_finance_160;

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
('admin', '123456', '系统管理员', 'ADMIN', '校团委社团中心', '13916000001', 'admin@club160.local', 1, NOW(), NOW()),
('union', '123456', '团委审核员', 'UNION', '校团委社团中心', '13916000002', 'union@club160.local', 1, NOW(), NOW()),
('club', '123456', '社团负责人', 'CLUB', '校团委社团中心', '13916000003', 'club@club160.local', 1, NOW(), NOW()),
('treasurer', '123456', '社团财务员', 'TREASURER', '校团委社团中心', '13916000004', 'treasurer@club160.local', 1, NOW(), NOW()),
('warehouse', '123456', '物资管理员', 'WAREHOUSE', '校团委社团中心', '13916000005', 'warehouse@club160.local', 1, NOW(), NOW()),
('member', '123456', '社团成员', 'MEMBER', '校团委社团中心', '13916000006', 'member@club160.local', 1, NOW(), NOW());

CREATE TABLE club_profile (
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
INSERT INTO club_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('160-01-001', '社团档案示例一', '社团类型', '指导老师A', '2026-05-16 09:00', 'BOOKED', '社团编号、社团名称、社团类型、指导老师、成立时间和社团状态维护', NOW(), NOW()),
('160-01-002', '社团档案示例二', '社团类型', '指导老师B', '2026-05-17 14:00', 'VERIFIED', '社团档案演示数据二', NOW(), NOW());

CREATE TABLE member_profile (
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
INSERT INTO member_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('160-02-001', '成员档案示例一', '所属社团', '负责人A', '2026-05-16 09:00', 'SCHEDULED', '成员编号、成员姓名、所属社团、负责人、入社时间和成员状态维护', NOW(), NOW()),
('160-02-002', '成员档案示例二', '所属社团', '负责人B', '2026-05-17 14:00', 'PROCESSING', '成员档案演示数据二', NOW(), NOW());

CREATE TABLE activity_plan (
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
INSERT INTO activity_plan (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('160-03-001', '活动立项示例一', '活动类型', '负责人A', '2026-05-16 09:00', 'VERIFIED', '活动编号、活动名称、活动类型、负责人、活动时间和立项状态维护', NOW(), NOW()),
('160-03-002', '活动立项示例二', '活动类型', '负责人B', '2026-05-17 14:00', 'FINISHED', '活动立项演示数据二', NOW(), NOW());

CREATE TABLE budget_request (
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
INSERT INTO budget_request (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('160-04-001', '预算申请示例一', '预算类型', '申请人A', '2026-05-16 09:00', 'PROCESSING', '预算编号、活动名称、预算类型、申请人、申请时间和预算状态维护', NOW(), NOW()),
('160-04-002', '预算申请示例二', '预算类型', '申请人B', '2026-05-17 14:00', 'WARNING', '预算申请演示数据二', NOW(), NOW());

CREATE TABLE budget_line_item (
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
INSERT INTO budget_line_item (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('160-05-001', '预算明细示例一', '费用类型', '填报人A', '2026-05-16 09:00', 'FINISHED', '明细编号、费用项目、费用类型、填报人、填报时间和明细状态维护', NOW(), NOW()),
('160-05-002', '预算明细示例二', '费用类型', '填报人B', '2026-05-17 14:00', 'PUBLISHED', '预算明细演示数据二', NOW(), NOW());

CREATE TABLE approval_record (
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
INSERT INTO approval_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('160-06-001', '审批记录示例一', '审批类型', '审批人A', '2026-05-16 09:00', 'WARNING', '审批编号、审批事项、审批类型、审批人、审批时间和审批状态维护', NOW(), NOW()),
('160-06-002', '审批记录示例二', '审批类型', '审批人B', '2026-05-17 14:00', 'ACTIVE', '审批记录演示数据二', NOW(), NOW());

CREATE TABLE reimbursement_claim (
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
INSERT INTO reimbursement_claim (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('160-07-001', '报销申请示例一', '报销类型', '申请人A', '2026-05-16 09:00', 'PUBLISHED', '报销编号、报销活动、报销类型、申请人、申请时间和报销状态维护', NOW(), NOW()),
('160-07-002', '报销申请示例二', '报销类型', '申请人B', '2026-05-17 14:00', 'BOOKED', '报销申请演示数据二', NOW(), NOW());

CREATE TABLE receipt_archive (
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
INSERT INTO receipt_archive (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('160-08-001', '票据归档示例一', '票据类型', '归档人A', '2026-05-16 09:00', 'ACTIVE', '票据编号、票据事项、票据类型、归档人、归档时间和票据状态维护', NOW(), NOW()),
('160-08-002', '票据归档示例二', '票据类型', '归档人B', '2026-05-17 14:00', 'SCHEDULED', '票据归档演示数据二', NOW(), NOW());

CREATE TABLE material_asset (
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
INSERT INTO material_asset (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('160-09-001', '物资台账示例一', '物资类型', '保管人A', '2026-05-16 09:00', 'BOOKED', '物资编号、物资名称、物资类型、保管人、入库时间和物资状态维护', NOW(), NOW()),
('160-09-002', '物资台账示例二', '物资类型', '保管人B', '2026-05-17 14:00', 'VERIFIED', '物资台账演示数据二', NOW(), NOW());

CREATE TABLE borrow_request (
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
INSERT INTO borrow_request (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('160-10-001', '物资借用示例一', '借用类型', '借用人A', '2026-05-16 09:00', 'SCHEDULED', '借用编号、借用物资、借用类型、借用人、借用时间和借用状态维护', NOW(), NOW()),
('160-10-002', '物资借用示例二', '借用类型', '借用人B', '2026-05-17 14:00', 'PROCESSING', '物资借用演示数据二', NOW(), NOW());

CREATE TABLE return_inspection (
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
INSERT INTO return_inspection (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('160-11-001', '归还验收示例一', '验收类型', '验收人A', '2026-05-16 09:00', 'VERIFIED', '验收编号、归还物资、验收类型、验收人、验收时间和验收状态维护', NOW(), NOW()),
('160-11-002', '归还验收示例二', '验收类型', '验收人B', '2026-05-17 14:00', 'FINISHED', '归还验收演示数据二', NOW(), NOW());

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
('160-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'PROCESSING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('160-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'WARNING', '操作日志演示数据二', NOW(), NOW());
