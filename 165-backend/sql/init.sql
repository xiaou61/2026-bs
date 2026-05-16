DROP DATABASE IF EXISTS visitor_access_165;
CREATE DATABASE visitor_access_165 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE visitor_access_165;

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
('admin', '123456', '系统管理员', 'ADMIN', '企业访客接待中心', '13916500001', 'admin@visitor165.local', 1, NOW(), NOW()),
('reception', '123456', '前台接待员', 'RECEPTION', '企业访客接待中心', '13916500002', 'reception@visitor165.local', 1, NOW(), NOW()),
('employee', '123456', '被访员工', 'EMPLOYEE', '企业访客接待中心', '13916500003', 'employee@visitor165.local', 1, NOW(), NOW()),
('security', '123456', '安保门岗', 'SECURITY', '企业访客接待中心', '13916500004', 'security@visitor165.local', 1, NOW(), NOW()),
('visitor', '123456', '外来访客', 'VISITOR', '企业访客接待中心', '13916500005', 'visitor@visitor165.local', 1, NOW(), NOW()),
('manager', '123456', '行政主管', 'MANAGER', '企业访客接待中心', '13916500006', 'manager@visitor165.local', 1, NOW(), NOW());

CREATE TABLE company_zone (
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
INSERT INTO company_zone (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('165-01-001', '楼宇区域示例一', '楼宇位置', '区域负责人A', '2026-05-16 09:00', 'BOOKED', '区域编号、区域名称、楼宇位置、区域负责人、开放时间和区域状态维护', NOW(), NOW()),
('165-01-002', '楼宇区域示例二', '楼宇位置', '区域负责人B', '2026-05-17 14:00', 'VERIFIED', '楼宇区域演示数据二', NOW(), NOW());

CREATE TABLE host_employee (
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
INSERT INTO host_employee (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('165-02-001', '被访员工示例一', '所属部门', '接待权限A', '2026-05-16 09:00', 'SCHEDULED', '工号、员工姓名、所属部门、接待权限、在岗时间和员工状态维护', NOW(), NOW()),
('165-02-002', '被访员工示例二', '所属部门', '接待权限B', '2026-05-17 14:00', 'PROCESSING', '被访员工演示数据二', NOW(), NOW());

CREATE TABLE visitor_profile (
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
INSERT INTO visitor_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('165-03-001', '访客档案示例一', '访客类型', '接待员工A', '2026-05-16 09:00', 'VERIFIED', '访客编号、访客姓名、访客类型、接待员工、登记时间和访客状态维护', NOW(), NOW()),
('165-03-002', '访客档案示例二', '访客类型', '接待员工B', '2026-05-17 14:00', 'FINISHED', '访客档案演示数据二', NOW(), NOW());

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
('165-04-001', '访客预约示例一', '访问区域', '被访员工A', '2026-05-16 09:00', 'PROCESSING', '预约编号、来访事由、访问区域、被访员工、预约时间和预约状态维护', NOW(), NOW()),
('165-04-002', '访客预约示例二', '访问区域', '被访员工B', '2026-05-17 14:00', 'WARNING', '访客预约演示数据二', NOW(), NOW());

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
('165-05-001', '访问审批示例一', '审批类型', '审批人员A', '2026-05-16 09:00', 'FINISHED', '审批编号、预约事项、审批类型、审批人员、审批时间和审批状态维护', NOW(), NOW()),
('165-05-002', '访问审批示例二', '审批类型', '审批人员B', '2026-05-17 14:00', 'PUBLISHED', '访问审批演示数据二', NOW(), NOW());

CREATE TABLE qr_pass (
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
INSERT INTO qr_pass (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('165-06-001', '二维码通行示例一', '通行类型', '签发人员A', '2026-05-16 09:00', 'WARNING', '通行码编号、通行对象、通行类型、签发人员、有效时间和通行状态维护', NOW(), NOW()),
('165-06-002', '二维码通行示例二', '通行类型', '签发人员B', '2026-05-17 14:00', 'ACTIVE', '二维码通行演示数据二', NOW(), NOW());

CREATE TABLE gate_device (
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
INSERT INTO gate_device (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('165-07-001', '门禁设备示例一', '设备类型', '设备管理员A', '2026-05-16 09:00', 'PUBLISHED', '设备编号、设备名称、设备类型、设备管理员、巡检时间和设备状态维护', NOW(), NOW()),
('165-07-002', '门禁设备示例二', '设备类型', '设备管理员B', '2026-05-17 14:00', 'BOOKED', '门禁设备演示数据二', NOW(), NOW());

CREATE TABLE access_linkage (
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
INSERT INTO access_linkage (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('165-08-001', '门禁联动示例一', '联动类型', '联动人员A', '2026-05-16 09:00', 'ACTIVE', '联动编号、联动门禁、联动类型、联动人员、触发时间和联动状态维护', NOW(), NOW()),
('165-08-002', '门禁联动示例二', '联动类型', '联动人员B', '2026-05-17 14:00', 'SCHEDULED', '门禁联动演示数据二', NOW(), NOW());

CREATE TABLE entry_record (
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
INSERT INTO entry_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('165-09-001', '入园登记示例一', '通行方式', '门岗人员A', '2026-05-16 09:00', 'BOOKED', '登记编号、来访对象、通行方式、门岗人员、入园时间和登记状态维护', NOW(), NOW()),
('165-09-002', '入园登记示例二', '通行方式', '门岗人员B', '2026-05-17 14:00', 'VERIFIED', '入园登记演示数据二', NOW(), NOW());

CREATE TABLE trail_record (
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
INSERT INTO trail_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('165-10-001', '轨迹留痕示例一', '轨迹类型', '记录人员A', '2026-05-16 09:00', 'SCHEDULED', '轨迹编号、访问区域、轨迹类型、记录人员、记录时间和轨迹状态维护', NOW(), NOW()),
('165-10-002', '轨迹留痕示例二', '轨迹类型', '记录人员B', '2026-05-17 14:00', 'PROCESSING', '轨迹留痕演示数据二', NOW(), NOW());

CREATE TABLE exception_alert (
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
INSERT INTO exception_alert (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('165-11-001', '异常告警示例一', '告警类型', '处置人员A', '2026-05-16 09:00', 'VERIFIED', '告警编号、告警对象、告警类型、处置人员、告警时间和处置状态维护', NOW(), NOW()),
('165-11-002', '异常告警示例二', '告警类型', '处置人员B', '2026-05-17 14:00', 'FINISHED', '异常告警演示数据二', NOW(), NOW());

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
('165-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'PROCESSING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('165-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'WARNING', '操作日志演示数据二', NOW(), NOW());
