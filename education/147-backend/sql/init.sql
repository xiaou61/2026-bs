DROP DATABASE IF EXISTS campus_psychology_147;
CREATE DATABASE campus_psychology_147 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE campus_psychology_147;

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
('admin', '123456', '系统管理员', 'ADMIN', '学生心理健康中心', '139147000100', 'admin@demo.local', 1, NOW(), NOW()),
('teacher', '123456', '心理老师', 'TEACHER', '学生心理健康中心', '139147000200', 'teacher@demo.local', 1, NOW(), NOW()),
('student', '123456', '学生来访者', 'STUDENT', '软件工程2203班', '139147000300', 'student@demo.local', 1, NOW(), NOW()),
('counselor', '123456', '学院辅导员', 'COUNSELOR', '信息工程学院', '139147000400', 'counselor@demo.local', 1, NOW(), NOW());

CREATE TABLE counsel_case (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  case_no VARCHAR(120),
  case_theme VARCHAR(120),
  issue_type VARCHAR(120),
  college_name VARCHAR(120),
  submit_time VARCHAR(120),
  status VARCHAR(60),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO counsel_case (case_no, case_theme, issue_type, college_name, submit_time, status, created_time, updated_time) VALUES
('CASE-147-001', '新生入学适应焦虑', '适应压力', '信息工程学院', '2026-05-08 09:30', 'OPEN', NOW(), NOW()),
('CASE-147-002', '考研冲刺睡眠障碍', '情绪压力', '经济管理学院', '2026-05-09 15:20', 'ACTIVE', NOW(), NOW());

CREATE TABLE counsel_room (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  room_no VARCHAR(120),
  room_name VARCHAR(120),
  room_type VARCHAR(120),
  campus_name VARCHAR(120),
  capacity_limit INT,
  status VARCHAR(60),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO counsel_room (room_no, room_name, room_type, campus_name, capacity_limit, status, created_time, updated_time) VALUES
('ROOM-147-001', '一号暖心咨询室', '个体咨询', '主校区', 4, 'ACTIVE', NOW(), NOW()),
('ROOM-147-002', '团辅活动室', '团体辅导', '南校区', 12, 'ACTIVE', NOW(), NOW());

CREATE TABLE student_profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  student_no VARCHAR(120),
  student_name VARCHAR(120),
  class_name VARCHAR(120),
  focus_tag VARCHAR(120),
  phone_number VARCHAR(120),
  status VARCHAR(60),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO student_profile (student_no, student_name, class_name, focus_tag, phone_number, status, created_time, updated_time) VALUES
('STU-147-001', '林晨', '软件工程2203班', '适应跟踪', '138147000101', 'ACTIVE', NOW(), NOW()),
('STU-147-002', '周晴', '财务管理2202班', '睡眠干预', '138147000102', 'ACTIVE', NOW(), NOW());

CREATE TABLE duty_schedule (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  schedule_no VARCHAR(120),
  duty_teacher VARCHAR(120),
  duty_date VARCHAR(120),
  duty_period VARCHAR(120),
  effective_time VARCHAR(120),
  status VARCHAR(60),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO duty_schedule (schedule_no, duty_teacher, duty_date, duty_period, effective_time, status, created_time, updated_time) VALUES
('DUTY-147-001', '张老师', '2026-05-16', '上午 08:30-12:00', '2026-05-15 18:00', 'PROCESSING', NOW(), NOW()),
('DUTY-147-002', '李老师', '2026-05-17', '下午 14:00-17:30', '2026-05-15 18:00', 'PROCESSING', NOW(), NOW());

CREATE TABLE appointment_request (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  appointment_no VARCHAR(120),
  case_theme VARCHAR(120),
  applicant_name VARCHAR(120),
  appointment_time VARCHAR(120),
  appointment_status VARCHAR(120),
  status VARCHAR(60),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO appointment_request (appointment_no, case_theme, applicant_name, appointment_time, appointment_status, status, created_time, updated_time) VALUES
('APPT-147-001', '新生入学适应焦虑', '林晨', '2026-05-16 10:00', '待老师确认', 'SUBMITTED', NOW(), NOW()),
('APPT-147-002', '考研冲刺睡眠障碍', '周晴', '2026-05-17 15:00', '已完成安排', 'APPROVED', NOW(), NOW());

CREATE TABLE counsel_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  case_theme VARCHAR(120),
  counselor_name VARCHAR(120),
  counsel_time VARCHAR(120),
  conclusion_summary VARCHAR(255),
  status VARCHAR(60),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO counsel_record (record_no, case_theme, counselor_name, counsel_time, conclusion_summary, status, created_time, updated_time) VALUES
('RECORD-147-001', '新生入学适应焦虑', '张老师', '2026-05-10 14:00', '完成首次接谈，建议持续两周跟踪', 'SUBMITTED', NOW(), NOW()),
('RECORD-147-002', '考研冲刺睡眠障碍', '李老师', '2026-05-11 16:30', '完成睡眠评估并给出作息调整建议', 'APPROVED', NOW(), NOW());

CREATE TABLE assessment_questionnaire (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  questionnaire_no VARCHAR(120),
  case_theme VARCHAR(120),
  questionnaire_name VARCHAR(120),
  submit_time VARCHAR(120),
  target_group VARCHAR(120),
  status VARCHAR(60),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO assessment_questionnaire (questionnaire_no, case_theme, questionnaire_name, submit_time, target_group, status, created_time, updated_time) VALUES
('QUES-147-001', '新生入学适应焦虑', 'SCL-90 初筛问卷', '2026-05-08 10:20', '新生群体', 'PROCESSING', NOW(), NOW()),
('QUES-147-002', '考研冲刺睡眠障碍', '睡眠质量自评问卷', '2026-05-09 19:10', '毕业班群体', 'FINISHED', NOW(), NOW());

CREATE TABLE risk_assessment (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  assessment_no VARCHAR(120),
  case_theme VARCHAR(120),
  assessor_name VARCHAR(120),
  risk_level VARCHAR(120),
  assessment_time VARCHAR(120),
  status VARCHAR(60),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO risk_assessment (assessment_no, case_theme, assessor_name, risk_level, assessment_time, status, created_time, updated_time) VALUES
('RISK-147-001', '新生入学适应焦虑', '张老师', '中风险', '2026-05-10 14:30', 'SUBMITTED', NOW(), NOW()),
('RISK-147-002', '考研冲刺睡眠障碍', '李老师', '低风险', '2026-05-11 17:00', 'APPROVED', NOW(), NOW());

CREATE TABLE crisis_intervention (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  intervention_no VARCHAR(120),
  case_theme VARCHAR(120),
  intervention_type VARCHAR(120),
  target_person VARCHAR(120),
  intervention_result VARCHAR(255),
  status VARCHAR(60),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO crisis_intervention (intervention_no, case_theme, intervention_type, target_person, intervention_result, status, created_time, updated_time) VALUES
('CRISIS-147-001', '宿舍冲突情绪失控', '紧急约谈', '王同学', '完成辅导员联动和当日安抚', 'SUBMITTED', NOW(), NOW()),
('CRISIS-147-002', '考研冲刺睡眠障碍', '热线干预', '周晴', '完成电话跟进并纳入连续观察', 'APPROVED', NOW(), NOW());

CREATE TABLE family_communication (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  communication_no VARCHAR(120),
  case_theme VARCHAR(120),
  communication_topic VARCHAR(120),
  communication_method VARCHAR(120),
  communication_time VARCHAR(120),
  status VARCHAR(60),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO family_communication (communication_no, case_theme, communication_topic, communication_method, communication_time, status, created_time, updated_time) VALUES
('FAM-147-001', '新生入学适应焦虑', '家长支持建议沟通', '电话沟通', '2026-05-12 20:00', 'PROCESSING', NOW(), NOW()),
('FAM-147-002', '宿舍冲突情绪失控', '家校联合关注提醒', '视频会议', '2026-05-13 18:30', 'FINISHED', NOW(), NOW());

CREATE TABLE follow_up_plan (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  follow_up_no VARCHAR(120),
  case_theme VARCHAR(120),
  follow_up_stage VARCHAR(120),
  follow_up_content VARCHAR(255),
  planned_time VARCHAR(120),
  status VARCHAR(60),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO follow_up_plan (follow_up_no, case_theme, follow_up_stage, follow_up_content, planned_time, status, created_time, updated_time) VALUES
('FOLLOW-147-001', '新生入学适应焦虑', '第一阶段', '复盘宿舍适应和课堂参与状态', '2026-05-18 09:00', 'ACTIVE', NOW(), NOW()),
('FOLLOW-147-002', '考研冲刺睡眠障碍', '第二阶段', '跟踪作息调整和焦虑缓解情况', '2026-05-20 19:30', 'FINISHED', NOW(), NOW());

CREATE TABLE system_notice (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  notice_no VARCHAR(120),
  case_theme VARCHAR(120),
  notice_type VARCHAR(120),
  notice_content VARCHAR(255),
  publish_time VARCHAR(120),
  status VARCHAR(60),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO system_notice (notice_no, case_theme, notice_type, notice_content, publish_time, status, created_time, updated_time) VALUES
('NOTICE-147-001', '新生入学适应焦虑', '预约提醒', '请来访同学提前 10 分钟到达咨询室并完成签到。', '2026-05-15 09:00', 'PROCESSING', NOW(), NOW()),
('NOTICE-147-002', '考研冲刺睡眠障碍', '心理月公告', '本周开放睡眠主题团辅报名，欢迎毕业班学生参加。', '2026-05-15 10:00', 'FINISHED', NOW(), NOW());

CREATE TABLE operation_log (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  operator_name VARCHAR(120),
  module_name VARCHAR(120),
  action_type VARCHAR(120),
  target_name VARCHAR(120),
  detail_info VARCHAR(255),
  status VARCHAR(60),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO operation_log (operator_name, module_name, action_type, target_name, detail_info, status, created_time, updated_time) VALUES
('系统管理员', '预约申请', '审核通过', 'APPT-147-002', '确认周晴预约并分配咨询老师', 'SUCCESS', NOW(), NOW()),
('心理老师', '风险评估', '提交评估', 'RISK-147-001', '完成个案初始风险评估并提交复核', 'SUCCESS', NOW(), NOW());
