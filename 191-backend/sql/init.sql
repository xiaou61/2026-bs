DROP DATABASE IF EXISTS project_191;
CREATE DATABASE project_191 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE project_191;

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
('admin', '123456', '系统管理员', 'ADMIN', '社区助残器具借用中心', '13919100001', 'admin@project191.local', 1, NOW(), NOW()),
('auditor', '123456', '审核人员', 'AUDITOR', '社区助残器具借用中心', '13919100002', 'auditor@project191.local', 1, NOW(), NOW()),
('operator', '123456', '运营人员', 'OPERATOR', '社区助残器具借用中心', '13919100003', 'operator@project191.local', 1, NOW(), NOW()),
('member', '123456', '服务对象', 'MEMBER', '社区助残器具借用中心', '13919100004', 'member@project191.local', 1, NOW(), NOW());

CREATE TABLE biz_record_01 (
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
INSERT INTO biz_record_01 (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('191-01-001', '器具借用示例一', '器具借用', '业务人员A', '2026-05-16 09:00', 'SUBMITTED', '器具借用演示数据一', NOW(), NOW()),
('191-01-002', '器具借用示例二', '器具借用', '业务人员B', '2026-05-17 14:00', 'PROCESSING', '器具借用演示数据二', NOW(), NOW());

CREATE TABLE biz_record_02 (
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
INSERT INTO biz_record_02 (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('191-02-001', '康复计划示例一', '康复计划', '业务人员A', '2026-05-16 09:00', 'APPROVED', '康复计划演示数据一', NOW(), NOW()),
('191-02-002', '康复计划示例二', '康复计划', '业务人员B', '2026-05-17 14:00', 'FINISHED', '康复计划演示数据二', NOW(), NOW());

CREATE TABLE biz_record_03 (
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
INSERT INTO biz_record_03 (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('191-03-001', '随访记录示例一', '随访记录', '业务人员A', '2026-05-16 09:00', 'PROCESSING', '随访记录演示数据一', NOW(), NOW()),
('191-03-002', '随访记录示例二', '随访记录', '业务人员B', '2026-05-17 14:00', 'PUBLISHED', '随访记录演示数据二', NOW(), NOW());

CREATE TABLE biz_record_04 (
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
INSERT INTO biz_record_04 (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('191-04-001', '回收提醒示例一', '回收提醒', '业务人员A', '2026-05-16 09:00', 'FINISHED', '回收提醒演示数据一', NOW(), NOW()),
('191-04-002', '回收提醒示例二', '回收提醒', '业务人员B', '2026-05-17 14:00', 'WARNING', '回收提醒演示数据二', NOW(), NOW());

CREATE TABLE biz_record_05 (
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
INSERT INTO biz_record_05 (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('191-05-001', '基础档案示例一', '基础档案', '业务人员A', '2026-05-16 09:00', 'PUBLISHED', '基础档案演示数据一', NOW(), NOW()),
('191-05-002', '基础档案示例二', '基础档案', '业务人员B', '2026-05-17 14:00', 'SUCCESS', '基础档案演示数据二', NOW(), NOW());

CREATE TABLE biz_record_06 (
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
INSERT INTO biz_record_06 (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('191-06-001', '人员档案示例一', '人员档案', '业务人员A', '2026-05-16 09:00', 'WARNING', '人员档案演示数据一', NOW(), NOW()),
('191-06-002', '人员档案示例二', '人员档案', '业务人员B', '2026-05-17 14:00', 'ACTIVE', '人员档案演示数据二', NOW(), NOW());

CREATE TABLE biz_record_07 (
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
INSERT INTO biz_record_07 (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('191-07-001', '资源台账示例一', '资源台账', '业务人员A', '2026-05-16 09:00', 'SUCCESS', '资源台账演示数据一', NOW(), NOW()),
('191-07-002', '资源台账示例二', '资源台账', '业务人员B', '2026-05-17 14:00', 'SUBMITTED', '资源台账演示数据二', NOW(), NOW());

CREATE TABLE biz_record_08 (
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
INSERT INTO biz_record_08 (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('191-08-001', '服务申请示例一', '服务申请', '业务人员A', '2026-05-16 09:00', 'ACTIVE', '服务申请演示数据一', NOW(), NOW()),
('191-08-002', '服务申请示例二', '服务申请', '业务人员B', '2026-05-17 14:00', 'APPROVED', '服务申请演示数据二', NOW(), NOW());

CREATE TABLE biz_record_09 (
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
INSERT INTO biz_record_09 (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('191-09-001', '审批记录示例一', '审批记录', '业务人员A', '2026-05-16 09:00', 'SUBMITTED', '审批记录演示数据一', NOW(), NOW()),
('191-09-002', '审批记录示例二', '审批记录', '业务人员B', '2026-05-17 14:00', 'PROCESSING', '审批记录演示数据二', NOW(), NOW());

CREATE TABLE biz_record_10 (
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
INSERT INTO biz_record_10 (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('191-10-001', '执行记录示例一', '执行记录', '业务人员A', '2026-05-16 09:00', 'APPROVED', '执行记录演示数据一', NOW(), NOW()),
('191-10-002', '执行记录示例二', '执行记录', '业务人员B', '2026-05-17 14:00', 'FINISHED', '执行记录演示数据二', NOW(), NOW());

CREATE TABLE biz_record_11 (
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
INSERT INTO biz_record_11 (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('191-11-001', '统计分析示例一', '统计分析', '业务人员A', '2026-05-16 09:00', 'PROCESSING', '统计分析演示数据一', NOW(), NOW()),
('191-11-002', '统计分析示例二', '统计分析', '业务人员B', '2026-05-17 14:00', 'PUBLISHED', '统计分析演示数据二', NOW(), NOW());

CREATE TABLE biz_record_12 (
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
INSERT INTO biz_record_12 (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('191-12-001', '通知公告示例一', '通知公告', '业务人员A', '2026-05-16 09:00', 'FINISHED', '通知公告演示数据一', NOW(), NOW()),
('191-12-002', '通知公告示例二', '通知公告', '业务人员B', '2026-05-17 14:00', 'WARNING', '通知公告演示数据二', NOW(), NOW());
