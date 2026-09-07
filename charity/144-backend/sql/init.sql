DROP DATABASE IF EXISTS accessible_travel_144;
CREATE DATABASE accessible_travel_144 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE accessible_travel_144;

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
('admin', '123456', '系统管理员', 'ADMIN', '平台运营中心', '139144000100', 'admin@demo.local', 1, NOW(), NOW()),
('traveler', '123456', '出行用户', 'TRAVELER', '出行服务中心', '139144000200', 'traveler@demo.local', 1, NOW(), NOW()),
('volunteer', '123456', '志愿者', 'VOLUNTEER', '志愿服务队', '139144000300', 'volunteer@demo.local', 1, NOW(), NOW()),
('dispatcher', '123456', '调度员', 'DISPATCHER', '调度中心', '139144000400', 'dispatcher@demo.local', 1, NOW(), NOW());

CREATE TABLE accessible_route (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  route_no VARCHAR(120),
  route_name VARCHAR(120),
  route_type VARCHAR(120),
  travel_scenario VARCHAR(120),
  suggested_duration INT,
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO accessible_route (route_no, route_name, route_type, travel_scenario, suggested_duration, status, created_time, updated_time) VALUES
('ROU-144-001', '市民中心无障碍通勤线', '地铁接驳', '轮椅通勤', 45, 'ACTIVE', NOW(), NOW()),
('ROU-144-002', '医院门诊陪同保障线', '步行引导', '陪诊出行', 60, 'FINISHED', NOW(), NOW());

CREATE TABLE facility_point (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  point_no VARCHAR(120),
  point_name VARCHAR(120),
  facility_type VARCHAR(120),
  address_detail VARCHAR(255),
  open_status VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO facility_point (point_no, point_name, facility_type, address_detail, open_status, status, created_time, updated_time) VALUES
('POI-144-001', '地铁 A 口无障碍电梯', '无障碍电梯', '幸福路地铁站 A 口', '开放', 'ACTIVE', NOW(), NOW()),
('POI-144-002', '市民中心低位服务台', '低位柜台', '市民中心一层东侧', '维护中', 'FINISHED', NOW(), NOW());

CREATE TABLE traveler_profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  traveler_no VARCHAR(120),
  traveler_name VARCHAR(120),
  phone VARCHAR(30),
  assistance_need VARCHAR(120),
  travel_preference VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO traveler_profile (traveler_no, traveler_name, phone, assistance_need, travel_preference, status, created_time, updated_time) VALUES
('TRA-144-001', '王女士', '138144000201', '轮椅推行协助', '避开台阶路线', 'PROCESSING', NOW(), NOW()),
('TRA-144-002', '陈先生', '138144000202', '视障语音引导', '优先电梯换乘', 'FINISHED', NOW(), NOW());

CREATE TABLE assist_request (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  request_no VARCHAR(120),
  request_title VARCHAR(120),
  departure_point VARCHAR(120),
  request_time VARCHAR(40),
  destination VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO assist_request (request_no, request_title, departure_point, request_time, destination, status, created_time, updated_time) VALUES
('REQ-144-001', '门诊陪诊预约', '幸福里社区南门', '2026-05-18 09:00', '市第一人民医院门诊大厅', 'SUBMITTED', NOW(), NOW()),
('REQ-144-002', '政务大厅办事协助', '和睦苑小区北门', '2026-05-19 14:30', '市民中心办事大厅', 'APPROVED', NOW(), NOW());

CREATE TABLE volunteer_profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  volunteer_no VARCHAR(120),
  volunteer_name VARCHAR(120),
  service_expertise VARCHAR(120),
  join_time VARCHAR(40),
  service_area VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO volunteer_profile (volunteer_no, volunteer_name, service_expertise, join_time, service_area, status, created_time, updated_time) VALUES
('VOL-144-001', '李志愿', '轮椅陪行', '2026-03-02', '市民中心片区', 'SUBMITTED', NOW(), NOW()),
('VOL-144-002', '周志愿', '视障语音引导', '2026-03-15', '医院服务片区', 'APPROVED', NOW(), NOW());

CREATE TABLE route_plan (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  plan_no VARCHAR(120),
  volunteer_no VARCHAR(120),
  route_type VARCHAR(120),
  route_detail VARCHAR(255),
  generate_time VARCHAR(40),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO route_plan (plan_no, volunteer_no, route_type, route_detail, generate_time, status, created_time, updated_time) VALUES
('PLA-144-001', 'VOL-144-001', '轮椅避障路线', '社区南门 - 地铁无障碍电梯 - 医院绿色通道', '2026-05-17 18:30', 'SUBMITTED', NOW(), NOW()),
('PLA-144-002', 'VOL-144-002', '视障语音引导路线', '小区北门 - 公交无障碍站台 - 市民中心东门', '2026-05-18 08:20', 'APPROVED', NOW(), NOW());

CREATE TABLE assist_task (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  task_no VARCHAR(120),
  volunteer_no VARCHAR(120),
  task_type VARCHAR(120),
  execute_time VARCHAR(40),
  executor_name VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO assist_task (task_no, volunteer_no, task_type, execute_time, executor_name, status, created_time, updated_time) VALUES
('TAS-144-001', 'VOL-144-001', '门诊陪诊', '2026-05-18 09:00', '李志愿', 'PROCESSING', NOW(), NOW()),
('TAS-144-002', 'VOL-144-002', '政务办事引导', '2026-05-19 14:30', '周志愿', 'FINISHED', NOW(), NOW());

CREATE TABLE service_checkin (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  checkin_no VARCHAR(120),
  volunteer_no VARCHAR(120),
  checkin_location VARCHAR(120),
  checkin_time VARCHAR(40),
  checkin_name VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO service_checkin (checkin_no, volunteer_no, checkin_location, checkin_time, checkin_name, status, created_time, updated_time) VALUES
('CHK-144-001', 'VOL-144-001', '市第一人民医院门诊大厅', '2026-05-18 08:55', '李志愿', 'ACTIVE', NOW(), NOW()),
('CHK-144-002', 'VOL-144-002', '市民中心东门服务点', '2026-05-19 14:20', '周志愿', 'FINISHED', NOW(), NOW());

CREATE TABLE feedback_review (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  feedback_no VARCHAR(120),
  volunteer_no VARCHAR(120),
  satisfaction_level INT,
  feedback_time VARCHAR(40),
  review_target VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO feedback_review (feedback_no, volunteer_no, satisfaction_level, feedback_time, review_target, status, created_time, updated_time) VALUES
('FED-144-001', 'VOL-144-001', 5, '2026-05-18 12:10', '门诊陪诊服务', 'ACTIVE', NOW(), NOW()),
('FED-144-002', 'VOL-144-002', 4, '2026-05-19 17:40', '政务引导服务', 'FINISHED', NOW(), NOW());

CREATE TABLE emergency_contact (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  contact_no VARCHAR(120),
  traveler_no VARCHAR(120),
  contact_name VARCHAR(120),
  contact_phone VARCHAR(30),
  relation_remark VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO emergency_contact (contact_no, traveler_no, contact_name, contact_phone, relation_remark, status, created_time, updated_time) VALUES
('EMG-144-001', 'TRA-144-001', '王先生', '137144000211', '家属联系人', 'ACTIVE', NOW(), NOW()),
('EMG-144-002', 'TRA-144-002', '陈女士', '137144000212', '同住家人', 'FINISHED', NOW(), NOW());

CREATE TABLE trip_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  trip_no VARCHAR(120),
  traveler_no VARCHAR(120),
  travel_date VARCHAR(40),
  travel_route VARCHAR(255),
  completion_status VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO trip_record (trip_no, traveler_no, travel_date, travel_route, completion_status, status, created_time, updated_time) VALUES
('TRI-144-001', 'TRA-144-001', '2026-05-18', '幸福里社区南门 - 医院门诊大厅', '陪诊中', 'PROCESSING', NOW(), NOW()),
('TRI-144-002', 'TRA-144-002', '2026-05-19', '和睦苑北门 - 市民中心东门', '已完成全程引导', 'FINISHED', NOW(), NOW());

CREATE TABLE message_notice (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  notice_no VARCHAR(120),
  traveler_no VARCHAR(120),
  notice_type VARCHAR(120),
  notice_content VARCHAR(255),
  receiver_name VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO message_notice (notice_no, traveler_no, notice_type, notice_content, receiver_name, status, created_time, updated_time) VALUES
('NOT-144-001', 'TRA-144-001', '签到提醒', '志愿者已在医院门诊大厅签到，请按约定时间到达。', '王女士', 'PROCESSING', NOW(), NOW()),
('NOT-144-002', 'TRA-144-002', '路线更新', '市民中心东门无障碍电梯恢复开放，建议按新方案出行。', '陈先生', 'FINISHED', NOW(), NOW());

CREATE TABLE operation_log (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  operator_name VARCHAR(120),
  module_name VARCHAR(120),
  action_type VARCHAR(120),
  target_name VARCHAR(120),
  detail_info VARCHAR(255),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO operation_log (operator_name, module_name, action_type, target_name, detail_info, status, created_time, updated_time) VALUES
('系统管理员', '无障碍路线', '新增', '市民中心无障碍通勤线', '已发布市民中心无障碍路线方案', 'SUCCESS', NOW(), NOW()),
('调度员', '协助任务', '派发', '门诊陪诊任务', '已为李志愿分配门诊陪诊协助任务', 'SUCCESS', NOW(), NOW());
