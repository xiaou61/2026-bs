DROP DATABASE IF EXISTS public_service_195;
CREATE DATABASE public_service_195 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE public_service_195;

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
('admin', '123456', '系统管理员', 'ADMIN', '便民服务中心', '13919500001', 'admin@publicservice195.local', 1, NOW(), NOW()),
('center', '123456', '中心管理员', 'CENTER', '便民服务中心', '13919500002', 'center@publicservice195.local', 1, NOW(), NOW()),
('window', '123456', '窗口经办', 'WINDOW', '便民服务中心', '13919500003', 'window@publicservice195.local', 1, NOW(), NOW()),
('review', '123456', '材料审核', 'REVIEW', '便民服务中心', '13919500004', 'review@publicservice195.local', 1, NOW(), NOW()),
('supervise', '123456', '督办人员', 'SUPERVISE', '便民服务中心', '13919500005', 'supervise@publicservice195.local', 1, NOW(), NOW()),
('citizen', '123456', '办事群众', 'CITIZEN', '便民服务中心', '13919500006', 'citizen@publicservice195.local', 1, NOW(), NOW());

CREATE TABLE service_item (
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
INSERT INTO service_item (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('195-01-001', '事项档案示例一', '事项类别', '责任窗口A', '2026-05-16 09:00', 'BOOKED', '事项编号、事项名称、事项类别、责任窗口、启用时间和事项状态维护', NOW(), NOW()),
('195-01-002', '事项档案示例二', '事项类别', '责任窗口B', '2026-05-17 14:00', 'REVIEWING', '事项档案演示数据二', NOW(), NOW());

CREATE TABLE window_profile (
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
INSERT INTO window_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('195-02-001', '窗口档案示例一', '业务类型', '窗口负责人A', '2026-05-16 09:00', 'QUEUING', '窗口编号、窗口名称、业务类型、窗口负责人、开放时间和窗口状态维护', NOW(), NOW()),
('195-02-002', '窗口档案示例二', '业务类型', '窗口负责人B', '2026-05-17 14:00', 'PROCESSING', '窗口档案演示数据二', NOW(), NOW());

CREATE TABLE clerk_roster (
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
INSERT INTO clerk_roster (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('195-03-001', '人员排班示例一', '排班类型', '排班负责人A', '2026-05-16 09:00', 'REVIEWING', '排班编号、窗口人员、排班类型、排班负责人、排班时间和排班状态维护', NOW(), NOW()),
('195-03-002', '人员排班示例二', '排班类型', '排班负责人B', '2026-05-17 14:00', 'NOTIFIED', '人员排班演示数据二', NOW(), NOW());

CREATE TABLE appointment_booking (
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
INSERT INTO appointment_booking (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('195-04-001', '事项预约示例一', '预约类型', '申请人员A', '2026-05-16 09:00', 'PROCESSING', '预约编号、预约事项、预约类型、申请人员、预约时间和预约状态维护', NOW(), NOW()),
('195-04-002', '事项预约示例二', '预约类型', '申请人员B', '2026-05-17 14:00', 'EVALUATED', '事项预约演示数据二', NOW(), NOW());

CREATE TABLE queue_call (
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
INSERT INTO queue_call (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('195-05-001', '窗口叫号示例一', '叫号类型', '窗口人员A', '2026-05-16 09:00', 'NOTIFIED', '叫号编号、预约事项、叫号类型、窗口人员、叫号时间和叫号状态维护', NOW(), NOW()),
('195-05-002', '窗口叫号示例二', '叫号类型', '窗口人员B', '2026-05-17 14:00', 'CLOSED', '窗口叫号演示数据二', NOW(), NOW());

CREATE TABLE material_review (
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
INSERT INTO material_review (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('195-06-001', '材料审核示例一', '材料类型', '审核人员A', '2026-05-16 09:00', 'EVALUATED', '审核编号、预约事项、材料类型、审核人员、审核时间和审核状态维护', NOW(), NOW()),
('195-06-002', '材料审核示例二', '材料类型', '审核人员B', '2026-05-17 14:00', 'REGISTERED', '材料审核演示数据二', NOW(), NOW());

CREATE TABLE process_progress (
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
INSERT INTO process_progress (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('195-07-001', '办理进度示例一', '办理环节', '经办人员A', '2026-05-16 09:00', 'CLOSED', '进度编号、预约事项、办理环节、经办人员、办理时间和办理状态维护', NOW(), NOW()),
('195-07-002', '办理进度示例二', '办理环节', '经办人员B', '2026-05-17 14:00', 'BOOKED', '办理进度演示数据二', NOW(), NOW());

CREATE TABLE message_notice (
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
INSERT INTO message_notice (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('195-08-001', '通知提醒示例一', '通知类型', '发送人员A', '2026-05-16 09:00', 'REGISTERED', '通知编号、预约事项、通知类型、发送人员、发送时间和通知状态维护', NOW(), NOW()),
('195-08-002', '通知提醒示例二', '通知类型', '发送人员B', '2026-05-17 14:00', 'QUEUING', '通知提醒演示数据二', NOW(), NOW());

CREATE TABLE service_evaluation (
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
INSERT INTO service_evaluation (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('195-09-001', '满意评价示例一', '评价类型', '评价人员A', '2026-05-16 09:00', 'BOOKED', '评价编号、办理事项、评价类型、评价人员、评价时间和评价状态维护', NOW(), NOW()),
('195-09-002', '满意评价示例二', '评价类型', '评价人员B', '2026-05-17 14:00', 'REVIEWING', '满意评价演示数据二', NOW(), NOW());

CREATE TABLE complaint_handling (
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
INSERT INTO complaint_handling (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('195-10-001', '投诉处理示例一', '投诉类型', '处理人员A', '2026-05-16 09:00', 'QUEUING', '投诉编号、办理事项、投诉类型、处理人员、投诉时间和处理状态维护', NOW(), NOW()),
('195-10-002', '投诉处理示例二', '投诉类型', '处理人员B', '2026-05-17 14:00', 'PROCESSING', '投诉处理演示数据二', NOW(), NOW());

CREATE TABLE performance_archive (
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
INSERT INTO performance_archive (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('195-11-001', '绩效统计示例一', '统计类型', '统计人员A', '2026-05-16 09:00', 'REVIEWING', '统计编号、统计对象、统计类型、统计人员、统计时间和统计状态维护', NOW(), NOW()),
('195-11-002', '绩效统计示例二', '统计类型', '统计人员B', '2026-05-17 14:00', 'NOTIFIED', '绩效统计演示数据二', NOW(), NOW());

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
('195-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'PROCESSING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('195-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'EVALUATED', '操作日志演示数据二', NOW(), NOW());
