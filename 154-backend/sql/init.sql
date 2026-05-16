DROP DATABASE IF EXISTS pet_hospital_154;
CREATE DATABASE pet_hospital_154 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE pet_hospital_154;

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
('admin', '123456', '系统管理员', 'ADMIN', '宠物医院接诊中心', '13915400001', 'admin@pet154.local', 1, NOW(), NOW()),
('reception', '123456', '前台接诊员', 'RECEPTION', '宠物医院接诊中心', '13915400002', 'reception@pet154.local', 1, NOW(), NOW()),
('doctor', '123456', '宠物医生', 'DOCTOR', '宠物医院接诊中心', '13915400003', 'doctor@pet154.local', 1, NOW(), NOW()),
('nurse', '123456', '护理接种员', 'NURSE', '宠物医院接诊中心', '13915400004', 'nurse@pet154.local', 1, NOW(), NOW()),
('owner', '123456', '宠物主人', 'OWNER', '宠物医院接诊中心', '13915400005', 'owner@pet154.local', 1, NOW(), NOW());

CREATE TABLE pet_owner (
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
INSERT INTO pet_owner (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('154-01-001', '宠主档案示例一', '联系方式', '常住区域A', '2026-05-16 09:00', 'BOOKED', '宠主编号、宠主姓名、联系方式、常住区域、建档时间和账号状态维护', NOW(), NOW()),
('154-01-002', '宠主档案示例二', '联系方式', '常住区域B', '2026-05-17 14:00', 'VERIFIED', '宠主档案演示数据二', NOW(), NOW());

CREATE TABLE pet_profile (
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
INSERT INTO pet_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('154-02-001', '宠物档案示例一', '宠物类型', '宠主姓名A', '2026-05-16 09:00', 'SCHEDULED', '宠物编号、宠物昵称、宠物类型、宠主姓名、出生日期和健康状态维护', NOW(), NOW()),
('154-02-002', '宠物档案示例二', '宠物类型', '宠主姓名B', '2026-05-17 14:00', 'PROCESSING', '宠物档案演示数据二', NOW(), NOW());

CREATE TABLE doctor_profile (
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
INSERT INTO doctor_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('154-03-001', '医生档案示例一', '擅长科室', '所属诊室A', '2026-05-16 09:00', 'VERIFIED', '医生工号、医生姓名、擅长科室、所属诊室、坐诊时间和在岗状态维护', NOW(), NOW()),
('154-03-002', '医生档案示例二', '擅长科室', '所属诊室B', '2026-05-17 14:00', 'FINISHED', '医生档案演示数据二', NOW(), NOW());

CREATE TABLE clinic_schedule (
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
INSERT INTO clinic_schedule (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('154-04-001', '接诊排班示例一', '诊室区域', '班次类型A', '2026-05-16 09:00', 'PROCESSING', '排班编号、坐诊医生、诊室区域、班次类型、排班时间和排班状态维护', NOW(), NOW()),
('154-04-002', '接诊排班示例二', '诊室区域', '班次类型B', '2026-05-17 14:00', 'WARNING', '接诊排班演示数据二', NOW(), NOW());

CREATE TABLE visit_appointment (
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
INSERT INTO visit_appointment (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('154-05-001', '接诊预约示例一', '接诊类型', '预约人A', '2026-05-16 09:00', 'FINISHED', '预约编号、宠物名称、接诊类型、预约人、预约时间和预约状态维护', NOW(), NOW()),
('154-05-002', '接诊预约示例二', '接诊类型', '预约人B', '2026-05-17 14:00', 'PUBLISHED', '接诊预约演示数据二', NOW(), NOW());

CREATE TABLE visit_record (
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
INSERT INTO visit_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('154-06-001', '接诊记录示例一', '疾病类型', '接诊医生A', '2026-05-16 09:00', 'WARNING', '接诊编号、关联预约、疾病类型、接诊医生、接诊时间和接诊状态维护', NOW(), NOW()),
('154-06-002', '接诊记录示例二', '疾病类型', '接诊医生B', '2026-05-17 14:00', 'ACTIVE', '接诊记录演示数据二', NOW(), NOW());

CREATE TABLE vaccine_plan (
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
INSERT INTO vaccine_plan (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('154-07-001', '疫苗计划示例一', '疫苗类型', '负责护士A', '2026-05-16 09:00', 'PUBLISHED', '计划编号、宠物名称、疫苗类型、负责护士、计划时间和提醒状态维护', NOW(), NOW()),
('154-07-002', '疫苗计划示例二', '疫苗类型', '负责护士B', '2026-05-17 14:00', 'BOOKED', '疫苗计划演示数据二', NOW(), NOW());

CREATE TABLE vaccine_record (
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
INSERT INTO vaccine_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('154-08-001', '疫苗接种示例一', '疫苗批次', '接种护士A', '2026-05-16 09:00', 'ACTIVE', '接种编号、疫苗计划、疫苗批次、接种护士、接种时间和接种状态维护', NOW(), NOW()),
('154-08-002', '疫苗接种示例二', '疫苗批次', '接种护士B', '2026-05-17 14:00', 'SCHEDULED', '疫苗接种演示数据二', NOW(), NOW());

CREATE TABLE follow_up_record (
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
INSERT INTO follow_up_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('154-09-001', '随访记录示例一', '随访方式', '随访人A', '2026-05-16 09:00', 'BOOKED', '随访编号、关联接诊、随访方式、随访人、随访时间和随访状态维护', NOW(), NOW()),
('154-09-002', '随访记录示例二', '随访方式', '随访人B', '2026-05-17 14:00', 'VERIFIED', '随访记录演示数据二', NOW(), NOW());

CREATE TABLE medicine_stock (
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
INSERT INTO medicine_stock (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('154-10-001', '药品库存示例一', '药品类型', '管理人A', '2026-05-16 09:00', 'SCHEDULED', '药品编号、药品名称、药品类型、管理人、有效日期和库存状态维护', NOW(), NOW()),
('154-10-002', '药品库存示例二', '药品类型', '管理人B', '2026-05-17 14:00', 'PROCESSING', '药品库存演示数据二', NOW(), NOW());

CREATE TABLE billing_record (
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
INSERT INTO billing_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('154-11-001', '费用结算示例一', '费用类型', '收费员A', '2026-05-16 09:00', 'VERIFIED', '结算编号、关联接诊、费用类型、收费员、结算时间和结算状态维护', NOW(), NOW()),
('154-11-002', '费用结算示例二', '费用类型', '收费员B', '2026-05-17 14:00', 'FINISHED', '费用结算演示数据二', NOW(), NOW());

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
('154-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'PROCESSING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('154-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'WARNING', '操作日志演示数据二', NOW(), NOW());
