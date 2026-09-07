DROP DATABASE IF EXISTS heritage_workshop_200;
CREATE DATABASE heritage_workshop_200 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE heritage_workshop_200;

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
('admin', '123456', '系统管理员', 'ADMIN', '非遗工坊文创中心', '13920000001', 'admin@heritage200.local', 1, NOW(), NOW()),
('workshop', '123456', '工坊管理员', 'WORKSHOP', '非遗工坊文创中心', '13920000002', 'workshop@heritage200.local', 1, NOW(), NOW()),
('teacher', '123456', '非遗讲师', 'TEACHER', '非遗工坊文创中心', '13920000003', 'teacher@heritage200.local', 1, NOW(), NOW()),
('curator', '123456', '展陈运营', 'CURATOR', '非遗工坊文创中心', '13920000004', 'curator@heritage200.local', 1, NOW(), NOW()),
('sales', '123456', '展销财务', 'SALES', '非遗工坊文创中心', '13920000005', 'sales@heritage200.local', 1, NOW(), NOW()),
('visitor', '123456', '学员访客', 'VISITOR', '非遗工坊文创中心', '13920000006', 'visitor@heritage200.local', 1, NOW(), NOW());

CREATE TABLE workshop_profile (
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
INSERT INTO workshop_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('200-01-001', '工坊档案示例一', '非遗门类', '负责人A', '2026-05-17 09:00', 'SCHEDULED', '工坊编号、工坊名称、非遗门类、负责人、建档时间和工坊状态维护', NOW(), NOW()),
('200-01-002', '工坊档案示例二', '非遗门类', '负责人B', '2026-05-18 14:00', 'REVIEWING', '工坊档案演示数据二', NOW(), NOW());

CREATE TABLE inheritor_profile (
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
INSERT INTO inheritor_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('200-02-001', '传承人档案示例一', '技艺方向', '管理人员A', '2026-05-17 09:00', 'BOOKING', '传承人编号、传承人姓名、技艺方向、管理人员、建档时间和档案状态维护', NOW(), NOW()),
('200-02-002', '传承人档案示例二', '技艺方向', '管理人员B', '2026-05-18 14:00', 'CHECKING', '传承人档案演示数据二', NOW(), NOW());

CREATE TABLE course_catalog (
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
INSERT INTO course_catalog (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('200-03-001', '课程目录示例一', '课程类型', '授课讲师A', '2026-05-17 09:00', 'REVIEWING', '课程编号、课程名称、课程类型、授课讲师、启用时间和课程状态维护', NOW(), NOW()),
('200-03-002', '课程目录示例二', '课程类型', '授课讲师B', '2026-05-18 14:00', 'SHOWING', '课程目录演示数据二', NOW(), NOW());

CREATE TABLE course_schedule (
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
INSERT INTO course_schedule (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('200-04-001', '工坊排期示例一', '排期类型', '授课讲师A', '2026-05-17 09:00', 'CHECKING', '排期编号、课程名称、排期类型、授课讲师、开课时间和排期状态维护', NOW(), NOW()),
('200-04-002', '工坊排期示例二', '排期类型', '授课讲师B', '2026-05-18 14:00', 'SETTLING', '工坊排期演示数据二', NOW(), NOW());

CREATE TABLE course_booking (
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
INSERT INTO course_booking (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('200-05-001', '课程预约示例一', '预约类型', '预约学员A', '2026-05-17 09:00', 'SHOWING', '预约编号、课程名称、预约类型、预约学员、预约时间和预约状态维护', NOW(), NOW()),
('200-05-002', '课程预约示例二', '预约类型', '预约学员B', '2026-05-18 14:00', 'CLOSED', '课程预约演示数据二', NOW(), NOW());

CREATE TABLE booking_review (
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
INSERT INTO booking_review (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('200-06-001', '预约审核示例一', '审核类型', '审核人员A', '2026-05-17 09:00', 'SETTLING', '审核编号、课程预约、审核类型、审核人员、审核时间和审核状态维护', NOW(), NOW()),
('200-06-002', '预约审核示例二', '审核类型', '审核人员B', '2026-05-18 14:00', 'REGISTERED', '预约审核演示数据二', NOW(), NOW());

CREATE TABLE class_checkin (
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
INSERT INTO class_checkin (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('200-07-001', '课程签到示例一', '签到类型', '记录人员A', '2026-05-17 09:00', 'CLOSED', '签到编号、课程排期、签到类型、记录人员、签到时间和签到状态维护', NOW(), NOW()),
('200-07-002', '课程签到示例二', '签到类型', '记录人员B', '2026-05-18 14:00', 'SCHEDULED', '课程签到演示数据二', NOW(), NOW());

CREATE TABLE artwork_catalog (
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
INSERT INTO artwork_catalog (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('200-08-001', '作品档案示例一', '作品类型', '创作人员A', '2026-05-17 09:00', 'REGISTERED', '作品编号、作品名称、作品类型、创作人员、入库时间和作品状态维护', NOW(), NOW()),
('200-08-002', '作品档案示例二', '作品类型', '创作人员B', '2026-05-18 14:00', 'BOOKING', '作品档案演示数据二', NOW(), NOW());

CREATE TABLE exhibition_showcase (
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
INSERT INTO exhibition_showcase (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('200-09-001', '作品展销示例一', '展销类型', '展陈人员A', '2026-05-17 09:00', 'SCHEDULED', '展销编号、作品名称、展销类型、展陈人员、上架时间和展销状态维护', NOW(), NOW()),
('200-09-002', '作品展销示例二', '展销类型', '展陈人员B', '2026-05-18 14:00', 'REVIEWING', '作品展销演示数据二', NOW(), NOW());

CREATE TABLE product_order (
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
INSERT INTO product_order (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('200-10-001', '展销订单示例一', '订单类型', '下单用户A', '2026-05-17 09:00', 'BOOKING', '订单编号、作品名称、订单类型、下单用户、下单时间和订单状态维护', NOW(), NOW()),
('200-10-002', '展销订单示例二', '订单类型', '下单用户B', '2026-05-18 14:00', 'CHECKING', '展销订单演示数据二', NOW(), NOW());

CREATE TABLE sales_settlement (
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
INSERT INTO sales_settlement (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('200-11-001', '展销结算示例一', '结算类型', '财务人员A', '2026-05-17 09:00', 'REVIEWING', '结算编号、结算周期、结算类型、财务人员、结算时间和结算状态维护', NOW(), NOW()),
('200-11-002', '展销结算示例二', '结算类型', '财务人员B', '2026-05-18 14:00', 'SHOWING', '展销结算演示数据二', NOW(), NOW());

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
('200-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-17 09:00', 'CHECKING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('200-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-18 14:00', 'SETTLING', '操作日志演示数据二', NOW(), NOW());
