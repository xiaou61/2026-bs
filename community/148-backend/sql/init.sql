DROP DATABASE IF EXISTS elder_service_148;
CREATE DATABASE elder_service_148 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE elder_service_148;

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
('admin', '123456', '系统管理员', 'ADMIN', '社区养老服务中心', '139148000100', 'admin@eldercare.local', 1, NOW(), NOW()),
('consultant', '123456', '养老顾问', 'CONSULTANT', '服务调度组', '139148000200', 'consultant@eldercare.local', 1, NOW(), NOW()),
('caregiver', '123456', '护理人员', 'CAREGIVER', '上门照护组', '139148000300', 'caregiver@eldercare.local', 1, NOW(), NOW()),
('family', '123456', '家属监护人', 'FAMILY', '家庭联络组', '139148000400', 'family@eldercare.local', 1, NOW(), NOW());

CREATE TABLE service_package (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  package_no VARCHAR(120),
  package_name VARCHAR(120),
  service_category VARCHAR(120),
  target_group VARCHAR(120),
  service_cycle VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO service_package (package_no, package_name, service_category, target_group, service_cycle, status, created_time, updated_time) VALUES
('PKG-148-001', '长者居家关怀包', '上门照护', '高龄独居老人', '每周 3 次', 'ACTIVE', NOW(), NOW()),
('PKG-148-002', '慢病健康跟踪包', '健康随访', '慢病管理老人', '每周 1 次', 'ACTIVE', NOW(), NOW());

CREATE TABLE elder_profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  elder_no VARCHAR(120),
  elder_name VARCHAR(120),
  age_group VARCHAR(120),
  home_address VARCHAR(255),
  care_level VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO elder_profile (elder_no, elder_name, age_group, home_address, care_level, status, created_time, updated_time) VALUES
('ELD-148-001', '张桂芳', '80-89 岁', '幸福社区 3 栋 202', '重点照护', 'ACTIVE', NOW(), NOW()),
('ELD-148-002', '李春兰', '70-79 岁', '康宁社区 6 栋 501', '常规照护', 'ACTIVE', NOW(), NOW());

CREATE TABLE caregiver_profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  caregiver_no VARCHAR(120),
  caregiver_name VARCHAR(120),
  phone_number VARCHAR(60),
  caregiver_level VARCHAR(120),
  organization_name VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO caregiver_profile (caregiver_no, caregiver_name, phone_number, caregiver_level, organization_name, status, created_time, updated_time) VALUES
('CAR-148-001', '王秀梅', '138148000501', '高级护理员', '安心照护站', 'PROCESSING', NOW(), NOW()),
('CAR-148-002', '陈建华', '138148000502', '中级护理员', '乐龄服务站', 'PROCESSING', NOW(), NOW());

CREATE TABLE service_order (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  order_no VARCHAR(120),
  service_subject VARCHAR(120),
  service_address VARCHAR(255),
  visit_time VARCHAR(120),
  dispatcher_name VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO service_order (order_no, service_subject, service_address, visit_time, dispatcher_name, status, created_time, updated_time) VALUES
('ORD-148-001', '陪诊与送药服务', '幸福社区 3 栋 202', '2026-05-16 09:30', '刘顾问', 'SUBMITTED', NOW(), NOW()),
('ORD-148-002', '术后康复上门照护', '康宁社区 6 栋 501', '2026-05-17 14:00', '赵调度', 'APPROVED', NOW(), NOW());

CREATE TABLE care_team (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  team_no VARCHAR(120),
  team_name VARCHAR(120),
  service_expertise VARCHAR(120),
  build_time VARCHAR(120),
  service_area VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO care_team (team_no, team_name, service_expertise, build_time, service_area, status, created_time, updated_time) VALUES
('TEAM-148-001', '东片区长者关怀组', '慢病照护', '2026-04-20', '东片区', 'APPROVED', NOW(), NOW()),
('TEAM-148-002', '西片区康复随访组', '康复训练', '2026-04-28', '西片区', 'APPROVED', NOW(), NOW());

CREATE TABLE visit_checkin (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  checkin_no VARCHAR(120),
  elder_name VARCHAR(120),
  checkin_type VARCHAR(120),
  checkin_remark VARCHAR(255),
  checkin_time VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO visit_checkin (checkin_no, elder_name, checkin_type, checkin_remark, checkin_time, status, created_time, updated_time) VALUES
('CHK-148-001', '张桂芳', '上门签到', '已完成血压测量与服药核验', '2026-05-15 09:10', 'APPROVED', NOW(), NOW()),
('CHK-148-002', '李春兰', '电话签到', '老人表示今日精神状态良好', '2026-05-15 16:20', 'SUBMITTED', NOW(), NOW());

CREATE TABLE service_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  elder_name VARCHAR(120),
  service_conclusion VARCHAR(255),
  service_time VARCHAR(120),
  caregiver_name VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO service_record (record_no, elder_name, service_conclusion, service_time, caregiver_name, status, created_time, updated_time) VALUES
('REC-148-001', '张桂芳', '完成陪诊并提醒晚间服药', '2026-05-15 11:00', '王秀梅', 'PROCESSING', NOW(), NOW()),
('REC-148-002', '李春兰', '完成康复训练并记录步态情况', '2026-05-15 15:30', '陈建华', 'FINISHED', NOW(), NOW());

CREATE TABLE health_assessment (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  assessment_no VARCHAR(120),
  elder_name VARCHAR(120),
  assessment_item VARCHAR(120),
  assessment_time VARCHAR(120),
  assessor_name VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO health_assessment (assessment_no, elder_name, assessment_item, assessment_time, assessor_name, status, created_time, updated_time) VALUES
('ASM-148-001', '张桂芳', '跌倒风险评估', '2026-05-15 09:40', '刘顾问', 'ACTIVE', NOW(), NOW()),
('ASM-148-002', '李春兰', '营养状态评估', '2026-05-15 14:10', '陈建华', 'FINISHED', NOW(), NOW());

CREATE TABLE medication_reminder (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  reminder_no VARCHAR(120),
  elder_name VARCHAR(120),
  reminder_type VARCHAR(120),
  reminder_time VARCHAR(120),
  receiver_name VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO medication_reminder (reminder_no, elder_name, reminder_type, reminder_time, receiver_name, status, created_time, updated_time) VALUES
('REM-148-001', '张桂芳', '高血压药物提醒', '2026-05-15 20:00', '张桂芳家属', 'ACTIVE', NOW(), NOW()),
('REM-148-002', '李春兰', '降糖药复核提醒', '2026-05-16 08:00', '李春兰', 'FINISHED', NOW(), NOW());

CREATE TABLE family_visit (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  visit_no VARCHAR(120),
  elder_name VARCHAR(120),
  visit_subject VARCHAR(120),
  visit_method VARCHAR(120),
  visit_time VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO family_visit (visit_no, elder_name, visit_subject, visit_method, visit_time, status, created_time, updated_time) VALUES
('FAM-148-001', '张桂芳', '本周照护满意度回访', '电话回访', '2026-05-15 18:00', 'ACTIVE', NOW(), NOW()),
('FAM-148-002', '李春兰', '康复进展同步', '微信回访', '2026-05-16 19:30', 'FINISHED', NOW(), NOW());

CREATE TABLE alert_event (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  alert_no VARCHAR(120),
  elder_name VARCHAR(120),
  report_time VARCHAR(120),
  alert_type VARCHAR(120),
  handling_suggestion VARCHAR(255),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO alert_event (alert_no, elder_name, report_time, alert_type, handling_suggestion, status, created_time, updated_time) VALUES
('ALT-148-001', '张桂芳', '2026-05-15 08:45', '血压异常预警', '建议今日加密随访并联系家属确认复测', 'PROCESSING', NOW(), NOW()),
('ALT-148-002', '李春兰', '2026-05-15 13:20', '情绪波动预警', '建议安排顾问回访并记录睡眠情况', 'FINISHED', NOW(), NOW());

CREATE TABLE care_notice (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  notice_no VARCHAR(120),
  elder_name VARCHAR(120),
  notice_type VARCHAR(120),
  notice_content VARCHAR(255),
  receiver_name VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO care_notice (notice_no, elder_name, notice_type, notice_content, receiver_name, status, created_time, updated_time) VALUES
('NOT-148-001', '张桂芳', '服务提醒', '请明日上午 9 点前保持电话畅通，护理员将上门随访。', '张桂芳家属', 'PROCESSING', NOW(), NOW()),
('NOT-148-002', '李春兰', '健康提示', '本周复诊材料已准备完毕，请家属协助确认出行安排。', '李春兰家属', 'FINISHED', NOW(), NOW());

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
('系统管理员', '服务工单', '审批通过', 'ORD-148-002', '已完成上门照护工单审批并推送护理班组', 'SUCCESS', NOW(), NOW()),
('养老顾问', '预警事件', '跟进处理', 'ALT-148-001', '已联系家属并安排当日二次随访', 'SUCCESS', NOW(), NOW());
