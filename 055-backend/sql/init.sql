DROP DATABASE IF EXISTS oa_system;
CREATE DATABASE oa_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE oa_system;

CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    real_name VARCHAR(50),
    gender TINYINT DEFAULT 1,
    phone VARCHAR(20),
    email VARCHAR(100),
    avatar VARCHAR(255),
    dept_id BIGINT,
    position VARCHAR(50),
    role VARCHAR(20) DEFAULT 'employee',
    entry_date DATE,
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE department (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    parent_id BIGINT DEFAULT 0,
    leader_id BIGINT,
    sort INT DEFAULT 0,
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE attendance (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    attendance_date DATE NOT NULL,
    clock_in_time DATETIME,
    clock_out_time DATETIME,
    status TINYINT DEFAULT 0,
    remark VARCHAR(255),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE leave_request (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    leave_type TINYINT NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    days DECIMAL(5,1) NOT NULL,
    reason VARCHAR(500),
    status TINYINT DEFAULT 0,
    approver_id BIGINT,
    approve_time DATETIME,
    approve_remark VARCHAR(255),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE meeting_room (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    location VARCHAR(100),
    capacity INT DEFAULT 10,
    equipment VARCHAR(255),
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE meeting (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    room_id BIGINT,
    organizer_id BIGINT NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    participants VARCHAR(500),
    content TEXT,
    status TINYINT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE notice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    content TEXT,
    publisher_id BIGINT NOT NULL,
    is_top TINYINT DEFAULT 0,
    status TINYINT DEFAULT 0,
    publish_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE notice_read (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    notice_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    read_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_notice_user (notice_id, user_id)
);

CREATE TABLE schedule (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    title VARCHAR(100) NOT NULL,
    content VARCHAR(500),
    start_time DATETIME NOT NULL,
    end_time DATETIME,
    category VARCHAR(20) DEFAULT 'work',
    remind TINYINT DEFAULT 0,
    status TINYINT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE document (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    path VARCHAR(255) NOT NULL,
    size BIGINT,
    type VARCHAR(50),
    category VARCHAR(50),
    uploader_id BIGINT NOT NULL,
    is_shared TINYINT DEFAULT 0,
    download_count INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE salary (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    year_month VARCHAR(7) NOT NULL,
    basic_salary DECIMAL(10,2) DEFAULT 0,
    bonus DECIMAL(10,2) DEFAULT 0,
    allowance DECIMAL(10,2) DEFAULT 0,
    deduction DECIMAL(10,2) DEFAULT 0,
    social_security DECIMAL(10,2) DEFAULT 0,
    actual_salary DECIMAL(10,2) DEFAULT 0,
    status TINYINT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_user_month (user_id, year_month)
);

CREATE TABLE work_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    log_date DATE NOT NULL,
    content TEXT,
    plan TEXT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO department (id, name, parent_id, sort) VALUES
(1, '总经办', 0, 1),
(2, '技术部', 0, 2),
(3, '人事部', 0, 3),
(4, '财务部', 0, 4),
(5, '市场部', 0, 5),
(6, '前端组', 2, 1),
(7, '后端组', 2, 2);

INSERT INTO user (id, username, password, real_name, gender, phone, email, dept_id, position, role, entry_date, status) VALUES
(1, 'admin', 'admin123', '系统管理员', 1, '13800000001', 'admin@oa.com', 1, '总经理', 'admin', '2020-01-01', 1),
(2, 'manager', 'manager123', '张经理', 1, '13800000002', 'manager@oa.com', 2, '技术总监', 'manager', '2021-03-15', 1),
(3, 'employee', 'employee123', '李员工', 1, '13800000003', 'employee@oa.com', 6, '前端工程师', 'employee', '2022-06-01', 1),
(4, 'hr', 'hr123456', '王小红', 2, '13800000004', 'hr@oa.com', 3, 'HR主管', 'manager', '2021-05-10', 1),
(5, 'zhangsan', '123456', '张三', 1, '13800000005', 'zhangsan@oa.com', 7, '后端工程师', 'employee', '2023-01-15', 1),
(6, 'lisi', '123456', '李四', 2, '13800000006', 'lisi@oa.com', 6, '前端工程师', 'employee', '2023-03-20', 1),
(7, 'wangwu', '123456', '王五', 1, '13800000007', 'wangwu@oa.com', 4, '财务专员', 'employee', '2022-08-01', 1);

UPDATE department SET leader_id = 1 WHERE id = 1;
UPDATE department SET leader_id = 2 WHERE id = 2;
UPDATE department SET leader_id = 4 WHERE id = 3;

INSERT INTO meeting_room (id, name, location, capacity, equipment, status) VALUES
(1, '大会议室A', '3楼西侧', 30, '投影仪,白板,视频会议设备', 1),
(2, '小会议室B', '3楼东侧', 10, '投影仪,白板', 1),
(3, '培训室', '2楼', 50, '投影仪,音响,白板', 1),
(4, '洽谈室', '1楼大厅', 6, '白板', 1);

INSERT INTO notice (id, title, content, publisher_id, is_top, status, publish_time) VALUES
(1, '关于2026年春节放假安排的通知', '根据国家规定，2026年春节放假时间为1月28日至2月3日，共7天。请各部门提前做好工作安排。', 1, 1, 1, '2026-01-15 09:00:00'),
(2, '公司年会通知', '公司年会定于2026年1月25日下午2点在大会议室A举行，请全体员工准时参加。', 1, 0, 1, '2026-01-20 10:00:00'),
(3, '关于加强考勤管理的通知', '为规范公司考勤制度，请各位同事严格遵守上下班时间，按时打卡。', 4, 0, 1, '2026-01-22 14:00:00');

INSERT INTO attendance (user_id, attendance_date, clock_in_time, clock_out_time, status) VALUES
(3, '2026-01-27', '2026-01-27 08:55:00', '2026-01-27 18:30:00', 0),
(3, '2026-01-26', '2026-01-26 09:10:00', '2026-01-26 18:05:00', 1),
(5, '2026-01-27', '2026-01-27 08:50:00', '2026-01-27 18:00:00', 0),
(6, '2026-01-27', '2026-01-27 09:05:00', '2026-01-27 17:55:00', 4);

INSERT INTO leave_request (user_id, leave_type, start_time, end_time, days, reason, status, approver_id, approve_time) VALUES
(3, 1, '2026-02-10 09:00:00', '2026-02-11 18:00:00', 2.0, '有私事需要处理', 1, 2, '2026-01-26 10:00:00'),
(5, 2, '2026-01-30 09:00:00', '2026-01-30 18:00:00', 1.0, '身体不适需要看医生', 0, NULL, NULL),
(6, 3, '2026-02-05 09:00:00', '2026-02-07 18:00:00', 3.0, '休年假', 1, 2, '2026-01-25 15:00:00');

INSERT INTO meeting (title, room_id, organizer_id, start_time, end_time, participants, content, status) VALUES
('技术方案评审会', 1, 2, '2026-01-28 14:00:00', '2026-01-28 16:00:00', '2,3,5,6', '评审新项目技术方案', 0),
('周例会', 2, 2, '2026-01-29 10:00:00', '2026-01-29 11:00:00', '2,3,5,6', '周工作汇报', 0);

INSERT INTO schedule (user_id, title, content, start_time, end_time, category, remind, status) VALUES
(3, '完成前端页面开发', '首页和用户管理页面开发', '2026-01-28 09:00:00', '2026-01-28 18:00:00', 'work', 1, 0),
(3, '参加技术评审会', '下午2点大会议室A', '2026-01-28 14:00:00', '2026-01-28 16:00:00', 'meeting', 1, 0),
(5, '代码review', '审核前端提交的代码', '2026-01-28 10:00:00', '2026-01-28 12:00:00', 'work', 0, 0);

INSERT INTO salary (user_id, year_month, basic_salary, bonus, allowance, deduction, social_security, actual_salary, status) VALUES
(3, '2026-01', 12000.00, 2000.00, 500.00, 100.00, 1200.00, 13200.00, 1),
(5, '2026-01', 15000.00, 3000.00, 500.00, 0.00, 1500.00, 17000.00, 1),
(6, '2026-01', 11000.00, 1500.00, 500.00, 200.00, 1100.00, 11700.00, 1),
(3, '2025-12', 12000.00, 1500.00, 500.00, 0.00, 1200.00, 12800.00, 1),
(5, '2025-12', 15000.00, 2500.00, 500.00, 100.00, 1500.00, 16400.00, 1);

INSERT INTO work_log (user_id, log_date, content, plan) VALUES
(3, '2026-01-27', '1. 完成用户管理页面开发\n2. 修复登录页面bug\n3. 参加技术讨论会', '1. 继续开发部门管理页面\n2. 优化页面性能'),
(5, '2026-01-27', '1. 完成用户接口开发\n2. 编写接口文档\n3. 代码优化', '1. 开发部门管理接口\n2. 配合前端联调');
