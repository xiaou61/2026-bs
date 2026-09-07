DROP DATABASE IF EXISTS lab_animal_183;
CREATE DATABASE lab_animal_183 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE lab_animal_183;

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
('admin', '123456', '系统管理员', 'ADMIN', '实验动物中心', '13918300001', 'admin@labanimal183.local', 1, NOW(), NOW()),
('lab', '123456', '实验室管理员', 'LAB', '实验动物中心', '13918300002', 'lab@labanimal183.local', 1, NOW(), NOW()),
('breeder', '123456', '饲养员', 'BREEDER', '实验动物中心', '13918300003', 'breeder@labanimal183.local', 1, NOW(), NOW()),
('researcher', '123456', '课题研究员', 'RESEARCHER', '实验动物中心', '13918300004', 'researcher@labanimal183.local', 1, NOW(), NOW()),
('ethics', '123456', '伦理委员', 'ETHICS', '实验动物中心', '13918300005', 'ethics@labanimal183.local', 1, NOW(), NOW()),
('vet', '123456', '兽医', 'VET', '实验动物中心', '13918300006', 'vet@labanimal183.local', 1, NOW(), NOW());

CREATE TABLE animal_room (
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
INSERT INTO animal_room (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('183-01-001', '动物房间示例一', '屏障级别', '负责人员A', '2026-05-16 09:00', 'ORDERED', '房间编号、房间名称、屏障级别、负责人员、启用时间和房间状态维护', NOW(), NOW()),
('183-01-002', '动物房间示例二', '屏障级别', '负责人员B', '2026-05-17 14:00', 'COOKING', '动物房间演示数据二', NOW(), NOW());

CREATE TABLE animal_profile (
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
INSERT INTO animal_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('183-02-001', '实验动物档案示例一', '动物类型', '负责人员A', '2026-05-16 09:00', 'SCHEDULED', '动物编号、动物品系、动物类型、负责人员、建档时间和档案状态维护', NOW(), NOW()),
('183-02-002', '实验动物档案示例二', '动物类型', '负责人员B', '2026-05-17 14:00', 'DELIVERING', '实验动物档案演示数据二', NOW(), NOW());

CREATE TABLE feeding_schedule (
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
INSERT INTO feeding_schedule (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('183-03-001', '饲养排班示例一', '排班类型', '饲养人员A', '2026-05-16 09:00', 'COOKING', '排班编号、动物房间、排班类型、饲养人员、排班时间和排班状态维护', NOW(), NOW()),
('183-03-002', '饲养排班示例二', '排班类型', '饲养人员B', '2026-05-17 14:00', 'SIGNED', '饲养排班演示数据二', NOW(), NOW());

CREATE TABLE feeding_record (
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
INSERT INTO feeding_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('183-04-001', '饲养记录示例一', '饲养类型', '饲养人员A', '2026-05-16 09:00', 'DELIVERING', '记录编号、实验动物、饲养类型、饲养人员、饲养时间和记录状态维护', NOW(), NOW()),
('183-04-002', '饲养记录示例二', '饲养类型', '饲养人员B', '2026-05-17 14:00', 'ANALYZED', '饲养记录演示数据二', NOW(), NOW());

CREATE TABLE ethics_application (
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
INSERT INTO ethics_application (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('183-05-001', '伦理申请示例一', '实验类型', '申请人员A', '2026-05-16 09:00', 'SIGNED', '申请编号、课题名称、实验类型、申请人员、申请时间和申请状态维护', NOW(), NOW()),
('183-05-002', '伦理申请示例二', '实验类型', '申请人员B', '2026-05-17 14:00', 'CLOSED', '伦理申请演示数据二', NOW(), NOW());

CREATE TABLE ethics_review (
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
INSERT INTO ethics_review (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('183-06-001', '伦理审批示例一', '审批类型', '审批人员A', '2026-05-16 09:00', 'ANALYZED', '审批编号、伦理申请、审批类型、审批人员、审批时间和审批状态维护', NOW(), NOW()),
('183-06-002', '伦理审批示例二', '审批类型', '审批人员B', '2026-05-17 14:00', 'REGISTERED', '伦理审批演示数据二', NOW(), NOW());

CREATE TABLE experiment_registration (
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
INSERT INTO experiment_registration (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('183-07-001', '实验登记示例一', '实验类型', '研究人员A', '2026-05-16 09:00', 'CLOSED', '实验编号、伦理申请、实验类型、研究人员、实验时间和实验状态维护', NOW(), NOW()),
('183-07-002', '实验登记示例二', '实验类型', '研究人员B', '2026-05-17 14:00', 'ORDERED', '实验登记演示数据二', NOW(), NOW());

CREATE TABLE health_inspection (
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
INSERT INTO health_inspection (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('183-08-001', '健康巡检示例一', '巡检类型', '兽医人员A', '2026-05-16 09:00', 'REGISTERED', '巡检编号、动物房间、巡检类型、兽医人员、巡检时间和巡检状态维护', NOW(), NOW()),
('183-08-002', '健康巡检示例二', '巡检类型', '兽医人员B', '2026-05-17 14:00', 'SCHEDULED', '健康巡检演示数据二', NOW(), NOW());

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
('183-09-001', '异常告警示例一', '告警类型', '上报人员A', '2026-05-16 09:00', 'ORDERED', '告警编号、实验动物、告警类型、上报人员、告警时间和告警状态维护', NOW(), NOW()),
('183-09-002', '异常告警示例二', '告警类型', '上报人员B', '2026-05-17 14:00', 'COOKING', '异常告警演示数据二', NOW(), NOW());

CREATE TABLE veterinary_treatment (
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
INSERT INTO veterinary_treatment (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('183-10-001', '兽医处置示例一', '处置类型', '兽医人员A', '2026-05-16 09:00', 'SCHEDULED', '处置编号、异常告警、处置类型、兽医人员、处置时间和处置状态维护', NOW(), NOW()),
('183-10-002', '兽医处置示例二', '处置类型', '兽医人员B', '2026-05-17 14:00', 'DELIVERING', '兽医处置演示数据二', NOW(), NOW());

CREATE TABLE material_usage (
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
INSERT INTO material_usage (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('183-11-001', '耗材使用示例一', '耗材类型', '领用人员A', '2026-05-16 09:00', 'COOKING', '使用编号、动物房间、耗材类型、领用人员、使用时间和使用状态维护', NOW(), NOW()),
('183-11-002', '耗材使用示例二', '耗材类型', '领用人员B', '2026-05-17 14:00', 'SIGNED', '耗材使用演示数据二', NOW(), NOW());

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
('183-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'DELIVERING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('183-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'ANALYZED', '操作日志演示数据二', NOW(), NOW());
