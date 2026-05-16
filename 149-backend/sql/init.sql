DROP DATABASE IF EXISTS equipment_share_149;
CREATE DATABASE equipment_share_149 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE equipment_share_149;

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
('admin', '123456', '系统管理员', 'ADMIN', '实验管理中心', '13800001490', 'admin@equipment149.local', 1, NOW(), NOW()),
('teacher', '123456', '实验教师', 'TEACHER', '机械工程学院', '13800001491', 'teacher@equipment149.local', 1, NOW(), NOW()),
('student', '123456', '学生使用者', 'STUDENT', '人工智能 2201 班', '13800001492', 'student@equipment149.local', 1, NOW(), NOW()),
('manager', '123456', '设备管理员', 'MANAGER', '实验设备科', '13800001493', 'manager@equipment149.local', 1, NOW(), NOW());

CREATE TABLE equipment_asset (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  asset_no VARCHAR(120),
  asset_name VARCHAR(120),
  asset_model VARCHAR(120),
  laboratory_name VARCHAR(120),
  storage_time VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO equipment_asset (asset_no, asset_name, asset_model, laboratory_name, storage_time, status, created_time, updated_time) VALUES
('EQ-149-001', '三维打印机', 'MakerLab X3', '智能制造实验室', '2026-03-12 09:00', 'ACTIVE', NOW(), NOW()),
('EQ-149-002', '示波器', 'Tek DPO-4100', '电子技术实验室', '2026-03-18 14:30', 'ACTIVE', NOW(), NOW());

CREATE TABLE laboratory_room (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  lab_no VARCHAR(120),
  lab_name VARCHAR(120),
  lab_type VARCHAR(120),
  campus_name VARCHAR(120),
  capacity INT,
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO laboratory_room (lab_no, lab_name, lab_type, campus_name, capacity, status, created_time, updated_time) VALUES
('LAB-149-001', '智能制造实验室', '工程实训', '主校区', 36, 'ACTIVE', NOW(), NOW()),
('LAB-149-002', '电子技术实验室', '基础实验', '东校区', 48, 'ACTIVE', NOW(), NOW());

CREATE TABLE equipment_category (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  category_no VARCHAR(120),
  category_name VARCHAR(120),
  equipment_type VARCHAR(120),
  storage_area VARCHAR(120),
  status_tag VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO equipment_category (category_no, category_name, equipment_type, storage_area, status_tag, status, created_time, updated_time) VALUES
('CAT-149-001', '精密加工设备', '机械类', 'A 区设备库', '重点监管', 'ACTIVE', NOW(), NOW()),
('CAT-149-002', '电子测量设备', '电子类', 'B 区设备库', '常规共享', 'ACTIVE', NOW(), NOW());

CREATE TABLE borrow_user (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  borrower_no VARCHAR(120),
  borrower_name VARCHAR(120),
  class_name VARCHAR(120),
  phone_number VARCHAR(120),
  register_time VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO borrow_user (borrower_no, borrower_name, class_name, phone_number, register_time, status, created_time, updated_time) VALUES
('BOR-149-001', '陈晓航', '机械设计 2201', '13900001491', '2026-05-06 10:00', 'ACTIVE', NOW(), NOW()),
('BOR-149-002', '林子涵', '电子信息 2202', '13900001492', '2026-05-07 11:20', 'ACTIVE', NOW(), NOW());

CREATE TABLE reservation_request (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  reservation_no VARCHAR(120),
  equipment_name VARCHAR(120),
  applicant_name VARCHAR(120),
  reservation_time VARCHAR(120),
  reservation_status VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO reservation_request (reservation_no, equipment_name, applicant_name, reservation_time, reservation_status, status, created_time, updated_time) VALUES
('RES-149-001', '三维打印机', '陈晓航', '2026-05-16 09:00-11:00', '待教师审核', 'SUBMITTED', NOW(), NOW()),
('RES-149-002', '示波器', '林子涵', '2026-05-16 14:00-16:00', '已通过', 'APPROVED', NOW(), NOW());

CREATE TABLE borrow_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  borrow_no VARCHAR(120),
  equipment_name VARCHAR(120),
  borrower_name VARCHAR(120),
  borrow_time VARCHAR(120),
  borrow_purpose VARCHAR(255),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO borrow_record (borrow_no, equipment_name, borrower_name, borrow_time, borrow_purpose, status, created_time, updated_time) VALUES
('BR-149-001', '三维打印机', '陈晓航', '2026-05-16 09:10', '课程项目原型制作', 'SUBMITTED', NOW(), NOW()),
('BR-149-002', '示波器', '林子涵', '2026-05-16 14:15', '电路信号调试实验', 'APPROVED', NOW(), NOW());

CREATE TABLE usage_registration (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  registration_no VARCHAR(120),
  equipment_name VARCHAR(120),
  usage_scene VARCHAR(120),
  registration_time VARCHAR(120),
  teacher_name VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO usage_registration (registration_no, equipment_name, usage_scene, registration_time, teacher_name, status, created_time, updated_time) VALUES
('USE-149-001', '三维打印机', '创新训练项目', '2026-05-16 10:30', '赵老师', 'PROCESSING', NOW(), NOW()),
('USE-149-002', '示波器', '电子技术基础实验', '2026-05-16 15:00', '孙老师', 'FINISHED', NOW(), NOW());

CREATE TABLE violation_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  violation_no VARCHAR(120),
  equipment_name VARCHAR(120),
  handler_name VARCHAR(120),
  violation_type VARCHAR(120),
  violation_time VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO violation_record (violation_no, equipment_name, handler_name, violation_type, violation_time, status, created_time, updated_time) VALUES
('VIO-149-001', '三维打印机', '设备管理员王晨', '超时未归还', '2026-05-12 18:20', 'SUBMITTED', NOW(), NOW()),
('VIO-149-002', '示波器', '实验教师孙老师', '未经审批跨室使用', '2026-05-13 09:40', 'APPROVED', NOW(), NOW());

CREATE TABLE maintenance_work_order (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  work_order_no VARCHAR(120),
  equipment_name VARCHAR(120),
  fault_type VARCHAR(120),
  reporter_name VARCHAR(120),
  result_info VARCHAR(255),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO maintenance_work_order (work_order_no, equipment_name, fault_type, reporter_name, result_info, status, created_time, updated_time) VALUES
('MAI-149-001', '三维打印机', '喷头堵塞', '设备管理员王晨', '已安排 5 月 17 日更换喷头', 'SUBMITTED', NOW(), NOW()),
('MAI-149-002', '示波器', '探头接触异常', '实验教师孙老师', '已完成接头检修并复测通过', 'APPROVED', NOW(), NOW());

CREATE TABLE return_confirmation (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  confirm_no VARCHAR(120),
  equipment_name VARCHAR(120),
  returner_name VARCHAR(120),
  return_status VARCHAR(120),
  confirm_time VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO return_confirmation (confirm_no, equipment_name, returner_name, return_status, confirm_time, status, created_time, updated_time) VALUES
('RET-149-001', '三维打印机', '陈晓航', '待设备管理员确认', '2026-05-16 11:40', 'PROCESSING', NOW(), NOW()),
('RET-149-002', '示波器', '林子涵', '归还完好', '2026-05-16 17:10', 'FINISHED', NOW(), NOW());

CREATE TABLE inspection_plan (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  inspection_no VARCHAR(120),
  equipment_name VARCHAR(120),
  inspection_theme VARCHAR(120),
  inspection_content VARCHAR(255),
  inspection_time VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO inspection_plan (inspection_no, equipment_name, inspection_theme, inspection_content, inspection_time, status, created_time, updated_time) VALUES
('INS-149-001', '三维打印机', '周度喷头巡检', '检查喷头磨损、料仓清洁和设备校准情况', '2026-05-18 09:00', 'ACTIVE', NOW(), NOW()),
('INS-149-002', '示波器', '月度精度校验', '核对量程精度、探头接口和接地状态', '2026-05-20 14:00', 'ACTIVE', NOW(), NOW());

CREATE TABLE system_notice (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  notice_no VARCHAR(120),
  equipment_name VARCHAR(120),
  notice_type VARCHAR(120),
  notice_content VARCHAR(255),
  publish_time VARCHAR(120),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO system_notice (notice_no, equipment_name, notice_type, notice_content, publish_time, status, created_time, updated_time) VALUES
('NOT-149-001', '三维打印机', '预约提醒', '周五上午设备预约量较高，请提前半天提交申请。', '2026-05-15 08:30', 'PUBLISHED', NOW(), NOW()),
('NOT-149-002', '示波器', '维护公告', '电子技术实验室 2 号示波器已恢复开放，可正常预约使用。', '2026-05-15 10:00', 'PUBLISHED', NOW(), NOW());

CREATE TABLE operation_log (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  operator_name VARCHAR(120),
  action_type VARCHAR(120),
  module_name VARCHAR(120),
  target_name VARCHAR(120),
  detail_info VARCHAR(255),
  status VARCHAR(40),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO operation_log (operator_name, action_type, module_name, target_name, detail_info, status, created_time, updated_time) VALUES
('系统管理员', '审批通过', '预约申请', 'RES-149-002', '审批通过示波器共享预约申请', 'SUCCESS', NOW(), NOW()),
('设备管理员王晨', '发布公告', '通知公告', 'NOT-149-002', '发布示波器恢复开放通知', 'SUCCESS', NOW(), NOW());
