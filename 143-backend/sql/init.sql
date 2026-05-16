DROP DATABASE IF EXISTS time_bank_143;
CREATE DATABASE time_bank_143 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE time_bank_143;

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
('admin', '123456', '系统管理员', 'ADMIN', '平台运营中心', '139143000100', 'admin@demo.local', 1, NOW(), NOW()),
('resident', '123456', '社区居民', 'RESIDENT', '幸福里社区', '139143000200', 'resident@demo.local', 1, NOW(), NOW()),
('volunteer', '123456', '志愿者', 'VOLUNTEER', '时间银行志愿队', '139143000300', 'volunteer@demo.local', 1, NOW(), NOW()),
('manager', '123456', '站点管理员', 'MANAGER', '社区服务站', '139143000400', 'manager@demo.local', 1, NOW(), NOW());

CREATE TABLE service_project (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  project_no VARCHAR(120),
  project_name VARCHAR(120),
  service_topic VARCHAR(120),
  service_target VARCHAR(120),
  publish_time VARCHAR(40),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO service_project (project_no, project_name, service_topic, service_target, publish_time, status, created_time, updated_time) VALUES
('PRO-143-001', '银龄陪伴上门服务', '高龄陪护', '独居老人', '2026-05-10 09:00', 'ACTIVE', NOW(), NOW()),
('PRO-143-002', '周末社区便民跑腿', '便民代办', '行动不便居民', '2026-05-12 10:30', 'FINISHED', NOW(), NOW());

CREATE TABLE service_category (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  category_no VARCHAR(120),
  category_name VARCHAR(120),
  service_type VARCHAR(120),
  target_group VARCHAR(120),
  suggested_duration INT,
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO service_category (category_no, category_name, service_type, target_group, suggested_duration, status, created_time, updated_time) VALUES
('CAT-143-001', '关怀探访', '上门陪伴', '高龄老人', 120, 'ACTIVE', NOW(), NOW()),
('CAT-143-002', '便民代办', '生活服务', '残障居民', 90, 'FINISHED', NOW(), NOW());

CREATE TABLE resident_profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  resident_no VARCHAR(120),
  resident_name VARCHAR(120),
  community_name VARCHAR(120),
  service_preference VARCHAR(120),
  phone VARCHAR(30),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO resident_profile (resident_no, resident_name, community_name, service_preference, phone, status, created_time, updated_time) VALUES
('RES-143-001', '李阿姨', '幸福里社区', '陪诊陪伴', '138143000201', 'ACTIVE', NOW(), NOW()),
('RES-143-002', '赵大爷', '和睦苑社区', '代购物资', '138143000202', 'FINISHED', NOW(), NOW());

CREATE TABLE volunteer_profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  volunteer_no VARCHAR(120),
  volunteer_name VARCHAR(120),
  service_expertise VARCHAR(120),
  available_time VARCHAR(120),
  join_time VARCHAR(40),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO volunteer_profile (volunteer_no, volunteer_name, service_expertise, available_time, join_time, status, created_time, updated_time) VALUES
('VOL-143-001', '周志愿', '陪诊沟通', '工作日晚间 / 周末全天', '2026-03-01', 'ACTIVE', NOW(), NOW()),
('VOL-143-002', '孙志愿', '上门探访', '周三下午 / 周六上午', '2026-03-15', 'FINISHED', NOW(), NOW());

CREATE TABLE service_booking (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  booking_no VARCHAR(120),
  project_name VARCHAR(120),
  reserver_name VARCHAR(120),
  booking_time VARCHAR(40),
  booking_count INT,
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO service_booking (booking_no, project_name, reserver_name, booking_time, booking_count, status, created_time, updated_time) VALUES
('BOO-143-001', '银龄陪伴上门服务', '社区居民', '2026-05-15 14:00', 1, 'SUBMITTED', NOW(), NOW()),
('BOO-143-002', '周末社区便民跑腿', '李阿姨', '2026-05-16 09:30', 2, 'APPROVED', NOW(), NOW());

CREATE TABLE service_checkin (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  checkin_no VARCHAR(120),
  project_name VARCHAR(120),
  checkin_name VARCHAR(120),
  checkin_time VARCHAR(40),
  service_result VARCHAR(255),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO service_checkin (checkin_no, project_name, checkin_name, checkin_time, service_result, status, created_time, updated_time) VALUES
('CHK-143-001', '银龄陪伴上门服务', '周志愿', '2026-05-15 14:05', '已完成陪诊陪同，居民满意', 'SUBMITTED', NOW(), NOW()),
('CHK-143-002', '周末社区便民跑腿', '孙志愿', '2026-05-16 09:35', '已送达药品与生活物资', 'APPROVED', NOW(), NOW());

CREATE TABLE time_account (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  account_no VARCHAR(120),
  project_name VARCHAR(120),
  account_type VARCHAR(120),
  open_time VARCHAR(40),
  owner_name VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO time_account (account_no, project_name, account_type, open_time, owner_name, status, created_time, updated_time) VALUES
('ACC-143-001', '银龄陪伴上门服务', '志愿时长账户', '2026-05-01 09:00', '周志愿', 'ACTIVE', NOW(), NOW()),
('ACC-143-002', '周末社区便民跑腿', '居民互助账户', '2026-05-02 10:00', '李阿姨', 'FINISHED', NOW(), NOW());

CREATE TABLE time_exchange (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  exchange_no VARCHAR(120),
  project_name VARCHAR(120),
  exchanger_name VARCHAR(120),
  exchange_hours INT,
  exchange_time VARCHAR(40),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO time_exchange (exchange_no, project_name, exchanger_name, exchange_hours, exchange_time, status, created_time, updated_time) VALUES
('EXC-143-001', '银龄陪伴上门服务', '社区居民', 2, '2026-05-17 11:00', 'SUBMITTED', NOW(), NOW()),
('EXC-143-002', '周末社区便民跑腿', '周志愿', 3, '2026-05-18 15:20', 'APPROVED', NOW(), NOW());

CREATE TABLE feedback_review (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  feedback_no VARCHAR(120),
  project_name VARCHAR(120),
  feedback_type VARCHAR(120),
  review_target VARCHAR(120),
  satisfaction_level INT,
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO feedback_review (feedback_no, project_name, feedback_type, review_target, satisfaction_level, status, created_time, updated_time) VALUES
('FED-143-001', '银龄陪伴上门服务', '服务评价', '周志愿', 5, 'SUBMITTED', NOW(), NOW()),
('FED-143-002', '周末社区便民跑腿', '活动反馈', '便民跑腿项目组', 4, 'APPROVED', NOW(), NOW());

CREATE TABLE community_activity (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  activity_no VARCHAR(120),
  project_name VARCHAR(120),
  activity_theme VARCHAR(120),
  activity_location VARCHAR(120),
  activity_time VARCHAR(40),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO community_activity (activity_no, project_name, activity_theme, activity_location, activity_time, status, created_time, updated_time) VALUES
('ACT-143-001', '银龄陪伴上门服务', '母亲节暖心陪伴日', '幸福里社区活动室', '2026-05-11 09:30', 'PUBLISHED', NOW(), NOW()),
('ACT-143-002', '周末社区便民跑腿', '邻里互助便民集市', '和睦苑社区广场', '2026-05-19 08:30', 'FINISHED', NOW(), NOW());

CREATE TABLE point_rule (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  rule_no VARCHAR(120),
  project_name VARCHAR(120),
  point_item VARCHAR(120),
  point_value INT,
  effective_time VARCHAR(40),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO point_rule (rule_no, project_name, point_item, point_value, effective_time, status, created_time, updated_time) VALUES
('RUL-143-001', '银龄陪伴上门服务', '上门陪伴 1 小时', 10, '2026-05-01 00:00', 'ACTIVE', NOW(), NOW()),
('RUL-143-002', '周末社区便民跑腿', '便民跑腿 1 单', 8, '2026-05-03 00:00', 'FINISHED', NOW(), NOW());

CREATE TABLE site_notice (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  notice_no VARCHAR(120),
  project_name VARCHAR(120),
  notice_type VARCHAR(120),
  notice_content VARCHAR(255),
  publish_time VARCHAR(40),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO site_notice (notice_no, project_name, notice_type, notice_content, publish_time, status, created_time, updated_time) VALUES
('NOT-143-001', '银龄陪伴上门服务', '活动提醒', '母亲节暖心陪伴日活动将于本周日开展，请志愿者按时签到。', '2026-05-10 18:00', 'PUBLISHED', NOW(), NOW()),
('NOT-143-002', '周末社区便民跑腿', '兑换通知', '互助时长兑换审核已完成，请居民及时查看账户变动。', '2026-05-18 17:30', 'FINISHED', NOW(), NOW());

CREATE TABLE operation_log (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  operator_name VARCHAR(120),
  module_name VARCHAR(120),
  action_type VARCHAR(120),
  target_name VARCHAR(120),
  detail_info VARCHAR(255),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO operation_log (operator_name, module_name, action_type, target_name, detail_info, status, created_time, updated_time) VALUES
('系统管理员', '服务项目', '新增', 'PRO-143-001', '新增银龄陪伴上门服务项目并发布给社区居民预约', 'SUCCESS', NOW(), NOW()),
('站点管理员', '站内通知', '发布', 'NOT-143-001', '发布母亲节暖心陪伴日志愿者签到提醒', 'SUCCESS', NOW(), NOW());
