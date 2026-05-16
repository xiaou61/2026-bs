DROP DATABASE IF EXISTS noise_monitor_145;
CREATE DATABASE noise_monitor_145 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE noise_monitor_145;

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
('admin', '123456', '系统管理员', 'ADMIN', '市级治理中心', '139145000100', 'admin@demo.local', 1, NOW(), NOW()),
('citizen', '123456', '市民投诉人', 'CITIZEN', '社区居民', '139145000200', 'citizen@demo.local', 1, NOW(), NOW()),
('officer', '123456', '执法人员', 'OFFICER', '执法支队', '139145000300', 'officer@demo.local', 1, NOW(), NOW()),
('supervisor', '123456', '环保监督员', 'SUPERVISOR', '监督管理处', '139145000400', 'supervisor@demo.local', 1, NOW(), NOW());

CREATE TABLE complaint_ticket (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  ticket_no VARCHAR(120),
  complaint_title VARCHAR(120),
  noise_type VARCHAR(120),
  complaint_area VARCHAR(120),
  complaint_time VARCHAR(120),
  status VARCHAR(60),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO complaint_ticket (ticket_no, complaint_title, noise_type, complaint_area, complaint_time, status, created_time, updated_time) VALUES
('CMP-145-001', '夜间施工噪声投诉', '施工噪声', '朝阳街道一号工地', '2026-05-06 21:30', 'OPEN', NOW(), NOW()),
('CMP-145-002', '商业街夜市音响扰民', '经营噪声', '滨河商业街A区', '2026-05-07 20:15', 'PROCESSING', NOW(), NOW());

CREATE TABLE monitoring_site (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  site_no VARCHAR(120),
  site_name VARCHAR(120),
  site_type VARCHAR(120),
  street_name VARCHAR(120),
  noise_threshold VARCHAR(120),
  status VARCHAR(60),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO monitoring_site (site_no, site_name, site_type, street_name, noise_threshold, status, created_time, updated_time) VALUES
('SIT-145-001', '朝阳工地监测点', '固定点位', '朝阳街道', '70dB', 'ACTIVE', NOW(), NOW()),
('SIT-145-002', '滨河商圈监测点', '移动点位', '滨河路', '65dB', 'ACTIVE', NOW(), NOW());

CREATE TABLE noise_source (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  source_no VARCHAR(120),
  source_name VARCHAR(120),
  responsible_unit VARCHAR(120),
  source_type VARCHAR(120),
  contact_name VARCHAR(120),
  status VARCHAR(60),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO noise_source (source_no, source_name, responsible_unit, source_type, contact_name, status, created_time, updated_time) VALUES
('SRC-145-001', '朝阳工地夜间施工', '朝阳建设有限公司', '施工源', '李工', 'ACTIVE', NOW(), NOW()),
('SRC-145-002', '滨河夜市外摆音响', '滨河商业运营公司', '经营源', '王主管', 'ACTIVE', NOW(), NOW());

CREATE TABLE officer_profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  officer_no VARCHAR(120),
  officer_name VARCHAR(120),
  duty_area VARCHAR(120),
  contact_phone VARCHAR(120),
  entry_time VARCHAR(120),
  status VARCHAR(60),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO officer_profile (officer_no, officer_name, duty_area, contact_phone, entry_time, status, created_time, updated_time) VALUES
('OFF-145-001', '张执法', '朝阳街道片区', '139145000501', '2024-03-01', 'PROCESSING', NOW(), NOW()),
('OFF-145-002', '刘执法', '滨河商圈片区', '139145000502', '2023-11-15', 'FINISHED', NOW(), NOW());

CREATE TABLE handling_task (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  task_no VARCHAR(120),
  complaint_title VARCHAR(120),
  assignee_name VARCHAR(120),
  assign_time VARCHAR(120),
  deadline_time VARCHAR(120),
  status VARCHAR(60),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO handling_task (task_no, complaint_title, assignee_name, assign_time, deadline_time, status, created_time, updated_time) VALUES
('TSK-145-001', '夜间施工噪声投诉', '张执法', '2026-05-06 22:00', '2026-05-07 10:00', 'SUBMITTED', NOW(), NOW()),
('TSK-145-002', '商业街夜市音响扰民', '刘执法', '2026-05-07 20:30', '2026-05-08 09:00', 'APPROVED', NOW(), NOW());

CREATE TABLE patrol_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  patrol_no VARCHAR(120),
  complaint_title VARCHAR(120),
  patrol_officer VARCHAR(120),
  patrol_time VARCHAR(120),
  patrol_result VARCHAR(255),
  status VARCHAR(60),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO patrol_record (patrol_no, complaint_title, patrol_officer, patrol_time, patrol_result, status, created_time, updated_time) VALUES
('PTR-145-001', '夜间施工噪声投诉', '张执法', '2026-05-06 22:30', '现场检测超标，责令立即降噪', 'SUBMITTED', NOW(), NOW()),
('PTR-145-002', '商业街夜市音响扰民', '刘执法', '2026-05-07 21:00', '经营者已配合调低音量', 'APPROVED', NOW(), NOW());

CREATE TABLE rectification_notice (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  notice_no VARCHAR(120),
  complaint_title VARCHAR(120),
  rectify_requirement VARCHAR(255),
  issue_time VARCHAR(120),
  responsible_unit VARCHAR(120),
  status VARCHAR(60),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO rectification_notice (notice_no, complaint_title, rectify_requirement, issue_time, responsible_unit, status, created_time, updated_time) VALUES
('REC-145-001', '夜间施工噪声投诉', '夜间22点后停止高噪施工，增设隔音围挡', '2026-05-06 23:00', '朝阳建设有限公司', 'PROCESSING', NOW(), NOW()),
('REC-145-002', '商业街夜市音响扰民', '营业时段音量不得超过65dB，增派现场巡查', '2026-05-07 21:20', '滨河商业运营公司', 'FINISHED', NOW(), NOW());

CREATE TABLE retest_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  retest_no VARCHAR(120),
  complaint_title VARCHAR(120),
  retest_officer VARCHAR(120),
  noise_db_value DECIMAL(10,2),
  retest_time VARCHAR(120),
  status VARCHAR(60),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO retest_record (retest_no, complaint_title, retest_officer, noise_db_value, retest_time, status, created_time, updated_time) VALUES
('RET-145-001', '夜间施工噪声投诉', '张执法', 63.50, '2026-05-07 09:30', 'SUBMITTED', NOW(), NOW()),
('RET-145-002', '商业街夜市音响扰民', '刘执法', 60.20, '2026-05-08 08:40', 'APPROVED', NOW(), NOW());

CREATE TABLE penalty_decision (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  penalty_no VARCHAR(120),
  complaint_title VARCHAR(120),
  penalty_type VARCHAR(120),
  penalty_target VARCHAR(120),
  penalty_amount DECIMAL(12,2),
  status VARCHAR(60),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO penalty_decision (penalty_no, complaint_title, penalty_type, penalty_target, penalty_amount, status, created_time, updated_time) VALUES
('PEN-145-001', '夜间施工噪声投诉', '行政处罚', '朝阳建设有限公司', 5000.00, 'SUBMITTED', NOW(), NOW()),
('PEN-145-002', '商业街夜市音响扰民', '责令改正', '滨河商业运营公司', 2000.00, 'APPROVED', NOW(), NOW());

CREATE TABLE public_feedback (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  feedback_no VARCHAR(120),
  complaint_title VARCHAR(120),
  feedback_topic VARCHAR(120),
  feedback_channel VARCHAR(120),
  feedback_time VARCHAR(120),
  status VARCHAR(60),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO public_feedback (feedback_no, complaint_title, feedback_topic, feedback_channel, feedback_time, status, created_time, updated_time) VALUES
('FBK-145-001', '夜间施工噪声投诉', '治理满意度回访', '电话回访', '2026-05-07 11:00', 'PROCESSING', NOW(), NOW()),
('FBK-145-002', '商业街夜市音响扰民', '整改效果回访', '短信回访', '2026-05-08 10:20', 'FINISHED', NOW(), NOW());

CREATE TABLE warning_rule (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  rule_no VARCHAR(120),
  complaint_title VARCHAR(120),
  warning_metric VARCHAR(120),
  threshold_config VARCHAR(120),
  effective_time VARCHAR(120),
  status VARCHAR(60),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO warning_rule (rule_no, complaint_title, warning_metric, threshold_config, effective_time, status, created_time, updated_time) VALUES
('WRN-145-001', '夜间施工噪声投诉', '夜间分贝阈值', '22:00后>70dB触发', '2026-05-01', 'ACTIVE', NOW(), NOW()),
('WRN-145-002', '商业街夜市音响扰民', '经营噪声阈值', '20:00后>65dB触发', '2026-05-01', 'ACTIVE', NOW(), NOW());

CREATE TABLE public_notice (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  notice_no VARCHAR(120),
  complaint_title VARCHAR(120),
  notice_type VARCHAR(120),
  notice_content VARCHAR(255),
  publish_time VARCHAR(120),
  status VARCHAR(60),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO public_notice (notice_no, complaint_title, notice_type, notice_content, publish_time, status, created_time, updated_time) VALUES
('PUB-145-001', '夜间施工噪声投诉', '治理公示', '已完成整改并通过复测，欢迎社会监督', '2026-05-07 15:00', 'PROCESSING', NOW(), NOW()),
('PUB-145-002', '商业街夜市音响扰民', '提醒公告', '夜市商户需严格控制扩音设备音量', '2026-05-08 09:30', 'FINISHED', NOW(), NOW());

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
('张执法', '处置任务', '派发任务', '夜间施工噪声投诉', '向朝阳片区执法人员派发现场处置任务', 'SUCCESS', NOW(), NOW()),
('刘执法', '复测记录', '提交复测', '商业街夜市音响扰民', '提交复测分贝值并更新办结结果', 'SUCCESS', NOW(), NOW());
