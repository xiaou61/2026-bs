DROP DATABASE IF EXISTS dorm_energy_156;
CREATE DATABASE dorm_energy_156 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE dorm_energy_156;

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
('admin', '123456', '系统管理员', 'ADMIN', '后勤能源中心', '13915600001', 'admin@energy156.local', 1, NOW(), NOW()),
('logistics', '123456', '后勤管理员', 'LOGISTICS', '后勤能源中心', '13915600002', 'logistics@energy156.local', 1, NOW(), NOW()),
('counselor', '123456', '宿舍辅导员', 'COUNSELOR', '后勤能源中心', '13915600003', 'counselor@energy156.local', 1, NOW(), NOW()),
('energy', '123456', '能耗专员', 'ENERGY', '后勤能源中心', '13915600004', 'energy@energy156.local', 1, NOW(), NOW()),
('student', '123456', '学生代表', 'STUDENT', '后勤能源中心', '13915600005', 'student@energy156.local', 1, NOW(), NOW());

CREATE TABLE dorm_building (
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
INSERT INTO dorm_building (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('156-01-001', '宿舍楼栋示例一', '楼栋类型', '管理员A', '2026-05-16 09:00', 'BOOKED', '楼栋编号、楼栋名称、楼栋类型、管理员、启用时间和楼栋状态维护', NOW(), NOW()),
('156-01-002', '宿舍楼栋示例二', '楼栋类型', '管理员B', '2026-05-17 14:00', 'VERIFIED', '宿舍楼栋演示数据二', NOW(), NOW());

CREATE TABLE dorm_room (
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
INSERT INTO dorm_room (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('156-02-001', '宿舍房间示例一', '所属楼栋', '责任人A', '2026-05-16 09:00', 'SCHEDULED', '房间编号、宿舍房间、所属楼栋、责任人、入住时间和房间状态维护', NOW(), NOW()),
('156-02-002', '宿舍房间示例二', '所属楼栋', '责任人B', '2026-05-17 14:00', 'PROCESSING', '宿舍房间演示数据二', NOW(), NOW());

CREATE TABLE meter_device (
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
INSERT INTO meter_device (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('156-03-001', '能耗表计示例一', '表计类型', '维护人A', '2026-05-16 09:00', 'VERIFIED', '表计编号、表计名称、表计类型、维护人、安装时间和表计状态维护', NOW(), NOW()),
('156-03-002', '能耗表计示例二', '表计类型', '维护人B', '2026-05-17 14:00', 'FINISHED', '能耗表计演示数据二', NOW(), NOW());

CREATE TABLE energy_reading (
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
INSERT INTO energy_reading (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('156-04-001', '用电读数示例一', '读数类型', '采集人A', '2026-05-16 09:00', 'PROCESSING', '读数编号、表计房间、读数类型、采集人、采集时间和读数状态维护', NOW(), NOW()),
('156-04-002', '用电读数示例二', '读数类型', '采集人B', '2026-05-17 14:00', 'WARNING', '用电读数演示数据二', NOW(), NOW());

CREATE TABLE consumption_bill (
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
INSERT INTO consumption_bill (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('156-05-001', '能耗账单示例一', '费用类型', '核算人A', '2026-05-16 09:00', 'FINISHED', '账单编号、账单名称、费用类型、核算人、账期时间和账单状态维护', NOW(), NOW()),
('156-05-002', '能耗账单示例二', '费用类型', '核算人B', '2026-05-17 14:00', 'PUBLISHED', '能耗账单演示数据二', NOW(), NOW());

CREATE TABLE alert_rule (
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
INSERT INTO alert_rule (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('156-06-001', '预警策略示例一', '策略类型', '配置人A', '2026-05-16 09:00', 'WARNING', '策略编号、策略名称、策略类型、配置人、生效时间和策略状态维护', NOW(), NOW()),
('156-06-002', '预警策略示例二', '策略类型', '配置人B', '2026-05-17 14:00', 'ACTIVE', '预警策略演示数据二', NOW(), NOW());

CREATE TABLE abnormal_alert (
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
INSERT INTO abnormal_alert (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('156-07-001', '异常预警示例一', '预警类型', '处理人A', '2026-05-16 09:00', 'PUBLISHED', '预警编号、预警宿舍、预警类型、处理人、预警时间和预警状态维护', NOW(), NOW()),
('156-07-002', '异常预警示例二', '预警类型', '处理人B', '2026-05-17 14:00', 'BOOKED', '异常预警演示数据二', NOW(), NOW());

CREATE TABLE saving_task (
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
INSERT INTO saving_task (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('156-08-001', '节能任务示例一', '节能类型', '负责人A', '2026-05-16 09:00', 'ACTIVE', '任务编号、任务名称、节能类型、负责人、执行时间和任务状态维护', NOW(), NOW()),
('156-08-002', '节能任务示例二', '节能类型', '负责人B', '2026-05-17 14:00', 'SCHEDULED', '节能任务演示数据二', NOW(), NOW());

CREATE TABLE saving_ranking (
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
INSERT INTO saving_ranking (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('156-09-001', '节能排行示例一', '排行类型', '统计人A', '2026-05-16 09:00', 'BOOKED', '排行编号、排行名称、排行类型、统计人、统计周期和发布状态维护', NOW(), NOW()),
('156-09-002', '节能排行示例二', '排行类型', '统计人B', '2026-05-17 14:00', 'VERIFIED', '节能排行演示数据二', NOW(), NOW());

CREATE TABLE inspection_record (
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
INSERT INTO inspection_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('156-10-001', '巡查记录示例一', '巡查类型', '巡查人A', '2026-05-16 09:00', 'SCHEDULED', '巡查编号、巡查区域、巡查类型、巡查人、巡查时间和巡查状态维护', NOW(), NOW()),
('156-10-002', '巡查记录示例二', '巡查类型', '巡查人B', '2026-05-17 14:00', 'PROCESSING', '巡查记录演示数据二', NOW(), NOW());

CREATE TABLE energy_notice (
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
INSERT INTO energy_notice (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('156-11-001', '通知公告示例一', '公告类型', '发布人A', '2026-05-16 09:00', 'VERIFIED', '公告编号、公告标题、公告类型、发布人、发布时间和公告状态维护', NOW(), NOW()),
('156-11-002', '通知公告示例二', '公告类型', '发布人B', '2026-05-17 14:00', 'FINISHED', '通知公告演示数据二', NOW(), NOW());

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
('156-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'PROCESSING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('156-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'WARNING', '操作日志演示数据二', NOW(), NOW());
