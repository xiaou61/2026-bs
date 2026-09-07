DROP DATABASE IF EXISTS childcare_safety_181;
CREATE DATABASE childcare_safety_181 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE childcare_safety_181;

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
('admin', '123456', '系统管理员', 'ADMIN', '社区儿童托管中心', '13918100001', 'admin@childcare181.local', 1, NOW(), NOW()),
('center', '123456', '中心管理员', 'CENTER', '社区儿童托管中心', '13918100002', 'center@childcare181.local', 1, NOW(), NOW()),
('teacher', '123456', '托管老师', 'TEACHER', '社区儿童托管中心', '13918100003', 'teacher@childcare181.local', 1, NOW(), NOW()),
('parent', '123456', '家长', 'PARENT', '社区儿童托管中心', '13918100004', 'parent@childcare181.local', 1, NOW(), NOW()),
('security', '123456', '安全员', 'SECURITY', '社区儿童托管中心', '13918100005', 'security@childcare181.local', 1, NOW(), NOW()),
('nurse', '123456', '保健老师', 'NURSE', '社区儿童托管中心', '13918100006', 'nurse@childcare181.local', 1, NOW(), NOW());

CREATE TABLE care_class (
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
INSERT INTO care_class (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('181-01-001', '托管班级示例一', '托管类型', '负责老师A', '2026-05-16 09:00', 'CHECKED_IN', '班级编号、班级名称、托管类型、负责老师、开班时间和班级状态维护', NOW(), NOW()),
('181-01-002', '托管班级示例二', '托管类型', '负责老师B', '2026-05-17 14:00', 'PICKED_UP', '托管班级演示数据二', NOW(), NOW());

CREATE TABLE child_profile (
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
INSERT INTO child_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('181-02-001', '儿童档案示例一', '托管类型', '监护家长A', '2026-05-16 09:00', 'AUTHORIZED', '儿童编号、儿童姓名、托管类型、监护家长、入托时间和档案状态维护', NOW(), NOW()),
('181-02-002', '儿童档案示例二', '托管类型', '监护家长B', '2026-05-17 14:00', 'ALERTING', '儿童档案演示数据二', NOW(), NOW());

CREATE TABLE guardian_profile (
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
INSERT INTO guardian_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('181-03-001', '监护人档案示例一', '关系类型', '关联儿童A', '2026-05-16 09:00', 'PICKED_UP', '监护编号、监护姓名、关系类型、关联儿童、登记时间和监护状态维护', NOW(), NOW()),
('181-03-002', '监护人档案示例二', '关系类型', '关联儿童B', '2026-05-17 14:00', 'HANDLING', '监护人档案演示数据二', NOW(), NOW());

CREATE TABLE attendance_checkin (
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
INSERT INTO attendance_checkin (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('181-04-001', '签到记录示例一', '签到类型', '签到老师A', '2026-05-16 09:00', 'ALERTING', '签到编号、托管儿童、签到类型、签到老师、签到时间和签到状态维护', NOW(), NOW()),
('181-04-002', '签到记录示例二', '签到类型', '签到老师B', '2026-05-17 14:00', 'NOTIFIED', '签到记录演示数据二', NOW(), NOW());

CREATE TABLE pickup_authorization (
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
INSERT INTO pickup_authorization (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('181-05-001', '接送授权示例一', '授权类型', '接送人员A', '2026-05-16 09:00', 'HANDLING', '授权编号、授权儿童、授权类型、接送人员、有效时间和授权状态维护', NOW(), NOW()),
('181-05-002', '接送授权示例二', '授权类型', '接送人员B', '2026-05-17 14:00', 'CLOSED', '接送授权演示数据二', NOW(), NOW());

CREATE TABLE pickup_record (
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
INSERT INTO pickup_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('181-06-001', '接送记录示例一', '接送类型', '接送人员A', '2026-05-16 09:00', 'NOTIFIED', '接送编号、托管儿童、接送类型、接送人员、接送时间和接送状态维护', NOW(), NOW()),
('181-06-002', '接送记录示例二', '接送类型', '接送人员B', '2026-05-17 14:00', 'REGISTERED', '接送记录演示数据二', NOW(), NOW());

CREATE TABLE safety_alert (
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
INSERT INTO safety_alert (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('181-07-001', '安全告警示例一', '告警类型', '处理人员A', '2026-05-16 09:00', 'CLOSED', '告警编号、告警位置、告警类型、处理人员、告警时间和告警状态维护', NOW(), NOW()),
('181-07-002', '安全告警示例二', '告警类型', '处理人员B', '2026-05-17 14:00', 'CHECKED_IN', '安全告警演示数据二', NOW(), NOW());

CREATE TABLE health_check (
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
INSERT INTO health_check (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('181-08-001', '健康晨检示例一', '健康类型', '保健老师A', '2026-05-16 09:00', 'REGISTERED', '晨检编号、托管儿童、健康类型、保健老师、晨检时间和健康状态维护', NOW(), NOW()),
('181-08-002', '健康晨检示例二', '健康类型', '保健老师B', '2026-05-17 14:00', 'AUTHORIZED', '健康晨检演示数据二', NOW(), NOW());

CREATE TABLE activity_schedule (
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
INSERT INTO activity_schedule (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('181-09-001', '托管活动示例一', '活动类型', '负责老师A', '2026-05-16 09:00', 'CHECKED_IN', '活动编号、活动名称、活动类型、负责老师、活动时间和活动状态维护', NOW(), NOW()),
('181-09-002', '托管活动示例二', '活动类型', '负责老师B', '2026-05-17 14:00', 'PICKED_UP', '托管活动演示数据二', NOW(), NOW());

CREATE TABLE parent_notice (
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
INSERT INTO parent_notice (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('181-10-001', '家长通知示例一', '通知类型', '发布人员A', '2026-05-16 09:00', 'AUTHORIZED', '通知编号、通知标题、通知类型、发布人员、发布时间和通知状态维护', NOW(), NOW()),
('181-10-002', '家长通知示例二', '通知类型', '发布人员B', '2026-05-17 14:00', 'ALERTING', '家长通知演示数据二', NOW(), NOW());

CREATE TABLE incident_followup (
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
INSERT INTO incident_followup (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('181-11-001', '事件跟进示例一', '跟进类型', '跟进人员A', '2026-05-16 09:00', 'PICKED_UP', '跟进编号、安全事件、跟进类型、跟进人员、跟进时间和跟进状态维护', NOW(), NOW()),
('181-11-002', '事件跟进示例二', '跟进类型', '跟进人员B', '2026-05-17 14:00', 'HANDLING', '事件跟进演示数据二', NOW(), NOW());

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
('181-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'ALERTING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('181-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'NOTIFIED', '操作日志演示数据二', NOW(), NOW());
