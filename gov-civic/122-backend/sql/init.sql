DROP DATABASE IF EXISTS smart_worksite_safety_122;
CREATE DATABASE smart_worksite_safety_122 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE smart_worksite_safety_122;

CREATE TABLE sys_user (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(80) NOT NULL,
  nickname VARCHAR(50) NOT NULL,
  role VARCHAR(50) NOT NULL,
  department VARCHAR(80),
  phone VARCHAR(30),
  email VARCHAR(80),
  status TINYINT DEFAULT 1,
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO sys_user (username, password, nickname, role, department, phone, email, status, created_time, updated_time) VALUES
('admin', '123456', '系统管理员', 'ADMIN', '安全管理中心', '13800012201', 'admin@worksite.local', 1, NOW(), NOW()),
('inspector', '123456', '安全巡检员', 'INSPECTOR', '现场巡检组', '13800012202', 'inspector@worksite.local', 1, NOW(), NOW()),
('teamleader', '123456', '班组长', 'TEAM_LEADER', '施工班组', '13800012203', 'teamleader@worksite.local', 1, NOW(), NOW()),
('supervisor', '123456', '安全主管', 'SUPERVISOR', '安全监督组', '13800012204', 'supervisor@worksite.local', 1, NOW(), NOW());

CREATE TABLE construction_project (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  project_no VARCHAR(180),
  project_name VARCHAR(180),
  location_name VARCHAR(180),
  contractor_name VARCHAR(180),
  start_date VARCHAR(180),
  risk_level VARCHAR(180),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO construction_project (project_no, project_name, location_name, contractor_name, start_date, risk_level, status, created_time, updated_time) VALUES
('PROJ-122-001', '城东综合体项目', '杭州城东', '华东建工', '2026-03-01', '高', 'ACTIVE', NOW(), NOW()),
('PROJ-122-002', '高新区厂房项目', '苏州高新区', '苏南建设', '2026-04-10', '中', 'ACTIVE', NOW(), NOW());

CREATE TABLE work_team (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  team_no VARCHAR(180),
  team_name VARCHAR(180),
  leader_name VARCHAR(180),
  worker_count INT,
  work_type VARCHAR(180),
  contact_phone VARCHAR(180),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO work_team (team_no, team_name, leader_name, worker_count, work_type, contact_phone, status, created_time, updated_time) VALUES
('TEAM-122-001', '钢筋班组', '周班长', 28, '钢筋工', '13800012205', 'ACTIVE', NOW(), NOW()),
('TEAM-122-002', '模板班组', '吴班长', 22, '木工', '13800012206', 'ACTIVE', NOW(), NOW());

CREATE TABLE safety_inspector (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  inspector_no VARCHAR(180),
  inspector_name VARCHAR(180),
  certificate_no VARCHAR(180),
  team_name VARCHAR(180),
  phone VARCHAR(180),
  specialty VARCHAR(180),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO safety_inspector (inspector_no, inspector_name, certificate_no, team_name, phone, specialty, status, created_time, updated_time) VALUES
('SAFE-122-001', '张安全', 'CERT-A001', '现场巡检组', '13800012207', '高处作业', 'ACTIVE', NOW(), NOW()),
('SAFE-122-002', '李安全', 'CERT-B002', '安全监督组', '13800012208', '临电检查', 'ACTIVE', NOW(), NOW());

CREATE TABLE inspection_plan (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  plan_no VARCHAR(180),
  project_name VARCHAR(180),
  plan_date VARCHAR(180),
  inspector_name VARCHAR(180),
  area_name VARCHAR(180),
  risk_focus VARCHAR(180),
  status VARCHAR(30) DEFAULT 'CREATED',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO inspection_plan (plan_no, project_name, plan_date, inspector_name, area_name, risk_focus, status, created_time, updated_time) VALUES
('PLAN-122-001', '城东综合体项目', '2026-05-06', '张安全', '塔吊区域', '高处坠落', 'CREATED', NOW(), NOW()),
('PLAN-122-002', '高新区厂房项目', '2026-05-07', '李安全', '临电区域', '触电风险', 'CREATED', NOW(), NOW());

CREATE TABLE inspection_task (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  task_no VARCHAR(180),
  plan_no VARCHAR(180),
  inspector_name VARCHAR(180),
  check_area VARCHAR(180),
  priority_level VARCHAR(180),
  deadline_time VARCHAR(180),
  status VARCHAR(30) DEFAULT 'ASSIGNED',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO inspection_task (task_no, plan_no, inspector_name, check_area, priority_level, deadline_time, status, created_time, updated_time) VALUES
('TASK-122-001', 'PLAN-122-001', '张安全', '塔吊区域', '高', '2026-05-06 18:00', 'ASSIGNED', NOW(), NOW()),
('TASK-122-002', 'PLAN-122-002', '李安全', '临电区域', '中', '2026-05-07 18:00', 'ASSIGNED', NOW(), NOW());

CREATE TABLE hazard_report (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  hazard_no VARCHAR(180),
  project_name VARCHAR(180),
  hazard_type VARCHAR(180),
  severity_level VARCHAR(180),
  reporter_name VARCHAR(180),
  report_time VARCHAR(180),
  status VARCHAR(30) DEFAULT 'OPEN',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO hazard_report (hazard_no, project_name, hazard_type, severity_level, reporter_name, report_time, status, created_time, updated_time) VALUES
('HAZ-122-001', '城东综合体项目', '临边防护缺失', '高', '张安全', '2026-05-06 10:20', 'OPEN', NOW(), NOW()),
('HAZ-122-002', '高新区厂房项目', '配电箱未上锁', '中', '李安全', '2026-05-07 11:30', 'OPEN', NOW(), NOW());

CREATE TABLE rectification_order (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  order_no VARCHAR(180),
  hazard_no VARCHAR(180),
  responsible_team VARCHAR(180),
  deadline_time VARCHAR(180),
  rectify_requirement VARCHAR(180),
  verify_status VARCHAR(180),
  status VARCHAR(30) DEFAULT 'PROCESSING',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO rectification_order (order_no, hazard_no, responsible_team, deadline_time, rectify_requirement, verify_status, status, created_time, updated_time) VALUES
('REC-122-001', 'HAZ-122-001', '钢筋班组', '2026-05-08 18:00', '补齐临边防护', '待验收', 'PROCESSING', NOW(), NOW()),
('REC-122-002', 'HAZ-122-002', '模板班组', '2026-05-09 18:00', '配电箱加锁并挂牌', '待整改', 'PROCESSING', NOW(), NOW());

CREATE TABLE acceptance_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  accept_no VARCHAR(180),
  order_no VARCHAR(180),
  inspector_name VARCHAR(180),
  accept_time VARCHAR(180),
  accept_result VARCHAR(180),
  score DECIMAL(12,2),
  status VARCHAR(30) DEFAULT 'CREATED',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO acceptance_record (accept_no, order_no, inspector_name, accept_time, accept_result, score, status, created_time, updated_time) VALUES
('ACC-122-001', 'REC-122-001', '张安全', '2026-05-09 09:30', 'PASS', 92.0, 'CREATED', NOW(), NOW()),
('ACC-122-002', 'REC-122-002', '李安全', '2026-05-10 09:30', 'FAIL', 76.0, 'CREATED', NOW(), NOW());

CREATE TABLE safety_training (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  training_no VARCHAR(180),
  course_name VARCHAR(180),
  trainer_name VARCHAR(180),
  team_name VARCHAR(180),
  trainee_count INT,
  train_date VARCHAR(180),
  status VARCHAR(30) DEFAULT 'CREATED',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO safety_training (training_no, course_name, trainer_name, team_name, trainee_count, train_date, status, created_time, updated_time) VALUES
('TRN-122-001', '高处作业安全', '王主管', '钢筋班组', 28, '2026-05-11', 'CREATED', NOW(), NOW()),
('TRN-122-002', '临时用电安全', '赵主管', '模板班组', 22, '2026-05-12', 'CREATED', NOW(), NOW());

CREATE TABLE equipment_check (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  check_no VARCHAR(180),
  equipment_name VARCHAR(180),
  equipment_type VARCHAR(180),
  project_name VARCHAR(180),
  check_result VARCHAR(180),
  inspector_name VARCHAR(180),
  status VARCHAR(30) DEFAULT 'CREATED',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO equipment_check (check_no, equipment_name, equipment_type, project_name, check_result, inspector_name, status, created_time, updated_time) VALUES
('EQC-122-001', '一号塔吊', '起重设备', '城东综合体项目', 'PASS', '张安全', 'CREATED', NOW(), NOW()),
('EQC-122-002', '二号配电箱', '临电设备', '高新区厂房项目', 'WARNING', '李安全', 'CREATED', NOW(), NOW());

CREATE TABLE protective_supply (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  supply_no VARCHAR(180),
  supply_name VARCHAR(180),
  supply_type VARCHAR(180),
  stock_qty INT,
  safe_qty INT,
  warehouse_name VARCHAR(180),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO protective_supply (supply_no, supply_name, supply_type, stock_qty, safe_qty, warehouse_name, status, created_time, updated_time) VALUES
('SUP-122-001', '安全帽', '头部防护', 260, 100, '一号仓库', 'ACTIVE', NOW(), NOW()),
('SUP-122-002', '安全带', '高处防护', 120, 60, '二号仓库', 'ACTIVE', NOW(), NOW());

CREATE TABLE risk_warning (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  warning_no VARCHAR(180),
  risk_object VARCHAR(180),
  warning_level VARCHAR(180),
  warning_reason VARCHAR(180),
  handler_name VARCHAR(180),
  trigger_time VARCHAR(180),
  status VARCHAR(30) DEFAULT 'OPEN',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO risk_warning (warning_no, risk_object, warning_level, warning_reason, handler_name, trigger_time, status, created_time, updated_time) VALUES
('WARN-122-001', '塔吊区域', '高', '高等级隐患未闭环', '王主管', '2026-05-05 16:00', 'OPEN', NOW(), NOW()),
('WARN-122-002', '临电区域', '中', '设备检查异常', '赵主管', '2026-05-05 17:00', 'OPEN', NOW(), NOW());

CREATE TABLE operation_log (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  operator_name VARCHAR(180),
  module_name VARCHAR(180),
  action_type VARCHAR(180),
  target_name VARCHAR(180),
  detail VARCHAR(180),
  ip_address VARCHAR(180),
  status VARCHAR(30) DEFAULT 'SUCCESS',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO operation_log (operator_name, module_name, action_type, target_name, detail, ip_address, status, created_time, updated_time) VALUES
('系统管理员', '隐患上报', 'CREATE', 'HAZ-122-001', '创建隐患记录', '127.0.0.1', 'SUCCESS', NOW(), NOW()),
('安全主管', '整改工单', 'UPDATE', 'REC-122-001', '提交整改工单', '127.0.0.1', 'SUCCESS', NOW(), NOW());
