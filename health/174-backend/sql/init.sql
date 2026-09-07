DROP DATABASE IF EXISTS chronic_care_174;
CREATE DATABASE chronic_care_174 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE chronic_care_174;

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
('admin', '123456', '系统管理员', 'ADMIN', '社区慢病管理中心', '13917400001', 'admin@chronic174.local', 1, NOW(), NOW()),
('center', '123456', '社区卫生中心', 'CENTER', '社区慢病管理中心', '13917400002', 'center@chronic174.local', 1, NOW(), NOW()),
('doctor', '123456', '家庭医生', 'DOCTOR', '社区慢病管理中心', '13917400003', 'doctor@chronic174.local', 1, NOW(), NOW()),
('nurse', '123456', '随访护士', 'NURSE', '社区慢病管理中心', '13917400004', 'nurse@chronic174.local', 1, NOW(), NOW()),
('pharmacist', '123456', '用药药师', 'PHARMACIST', '社区慢病管理中心', '13917400005', 'pharmacist@chronic174.local', 1, NOW(), NOW()),
('resident', '123456', '居民患者', 'RESIDENT', '社区慢病管理中心', '13917400006', 'resident@chronic174.local', 1, NOW(), NOW());

CREATE TABLE community_clinic (
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
INSERT INTO community_clinic (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('174-01-001', '社区站点示例一', '服务区域', '负责人A', '2026-05-16 09:00', 'PENDING', '站点编号、站点名称、服务区域、负责人、启用时间和站点状态维护', NOW(), NOW()),
('174-01-002', '社区站点示例二', '服务区域', '负责人B', '2026-05-17 14:00', 'PROCESSING', '社区站点演示数据二', NOW(), NOW());

CREATE TABLE chronic_patient (
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
INSERT INTO chronic_patient (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('174-02-001', '患者档案示例一', '慢病类型', '签约医生A', '2026-05-16 09:00', 'SCHEDULED', '患者编号、患者姓名、慢病类型、签约医生、建档时间和档案状态维护', NOW(), NOW()),
('174-02-002', '患者档案示例二', '慢病类型', '签约医生B', '2026-05-17 14:00', 'MEDICATING', '患者档案演示数据二', NOW(), NOW());

CREATE TABLE doctor_team (
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
INSERT INTO doctor_team (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('174-03-001', '医护团队示例一', '服务类型', '团队负责人A', '2026-05-16 09:00', 'PROCESSING', '团队编号、团队名称、服务类型、团队负责人、组建时间和团队状态维护', NOW(), NOW()),
('174-03-002', '医护团队示例二', '服务类型', '团队负责人B', '2026-05-17 14:00', 'WARNING', '医护团队演示数据二', NOW(), NOW());

CREATE TABLE disease_archive (
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
INSERT INTO disease_archive (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('174-04-001', '慢病档案示例一', '疾病类型', '管理医生A', '2026-05-16 09:00', 'MEDICATING', '档案编号、患者姓名、疾病类型、管理医生、建档时间和管理状态维护', NOW(), NOW()),
('174-04-002', '慢病档案示例二', '疾病类型', '管理医生B', '2026-05-17 14:00', 'STABLE', '慢病档案演示数据二', NOW(), NOW());

CREATE TABLE followup_plan (
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
INSERT INTO followup_plan (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('174-05-001', '随访计划示例一', '随访类型', '责任人员A', '2026-05-16 09:00', 'WARNING', '计划编号、随访患者、随访类型、责任人员、计划时间和计划状态维护', NOW(), NOW()),
('174-05-002', '随访计划示例二', '随访类型', '责任人员B', '2026-05-17 14:00', 'FINISHED', '随访计划演示数据二', NOW(), NOW());

CREATE TABLE followup_record (
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
INSERT INTO followup_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('174-06-001', '随访记录示例一', '随访方式', '随访人员A', '2026-05-16 09:00', 'STABLE', '记录编号、随访患者、随访方式、随访人员、随访时间和随访状态维护', NOW(), NOW()),
('174-06-002', '随访记录示例二', '随访方式', '随访人员B', '2026-05-17 14:00', 'ACTIVE', '随访记录演示数据二', NOW(), NOW());

CREATE TABLE medication_plan (
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
INSERT INTO medication_plan (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('174-07-001', '用药方案示例一', '药品类型', '指导药师A', '2026-05-16 09:00', 'FINISHED', '方案编号、用药患者、药品类型、指导药师、启用时间和方案状态维护', NOW(), NOW()),
('174-07-002', '用药方案示例二', '药品类型', '指导药师B', '2026-05-17 14:00', 'PENDING', '用药方案演示数据二', NOW(), NOW());

CREATE TABLE medication_adherence (
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
INSERT INTO medication_adherence (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('174-08-001', '服药打卡示例一', '打卡类型', '核验人员A', '2026-05-16 09:00', 'ACTIVE', '打卡编号、用药患者、打卡类型、核验人员、打卡时间和依从状态维护', NOW(), NOW()),
('174-08-002', '服药打卡示例二', '打卡类型', '核验人员B', '2026-05-17 14:00', 'SCHEDULED', '服药打卡演示数据二', NOW(), NOW());

CREATE TABLE health_indicator (
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
INSERT INTO health_indicator (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('174-09-001', '健康指标示例一', '指标类型', '采集人员A', '2026-05-16 09:00', 'PENDING', '指标编号、监测患者、指标类型、采集人员、采集时间和指标状态维护', NOW(), NOW()),
('174-09-002', '健康指标示例二', '指标类型', '采集人员B', '2026-05-17 14:00', 'PROCESSING', '健康指标演示数据二', NOW(), NOW());

CREATE TABLE risk_stratification (
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
INSERT INTO risk_stratification (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('174-10-001', '风险分层示例一', '风险类型', '评估医生A', '2026-05-16 09:00', 'SCHEDULED', '分层编号、评估患者、风险类型、评估医生、评估时间和风险状态维护', NOW(), NOW()),
('174-10-002', '风险分层示例二', '风险类型', '评估医生B', '2026-05-17 14:00', 'MEDICATING', '风险分层演示数据二', NOW(), NOW());

CREATE TABLE reminder_notice (
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
INSERT INTO reminder_notice (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('174-11-001', '提醒通知示例一', '通知类型', '发送人员A', '2026-05-16 09:00', 'PROCESSING', '通知编号、通知患者、通知类型、发送人员、发送时间和通知状态维护', NOW(), NOW()),
('174-11-002', '提醒通知示例二', '通知类型', '发送人员B', '2026-05-17 14:00', 'WARNING', '提醒通知演示数据二', NOW(), NOW());

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
('174-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'MEDICATING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('174-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'STABLE', '操作日志演示数据二', NOW(), NOW());
