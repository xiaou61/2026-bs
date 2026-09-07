DROP DATABASE IF EXISTS campus_book_drift_175;
CREATE DATABASE campus_book_drift_175 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE campus_book_drift_175;

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
('admin', '123456', '系统管理员', 'ADMIN', '校园图书漂流中心', '13917500001', 'admin@bookdrift175.local', 1, NOW(), NOW()),
('library', '123456', '图书馆管理员', 'LIBRARY', '校园图书漂流中心', '13917500002', 'library@bookdrift175.local', 1, NOW(), NOW()),
('curator', '123456', '漂流负责人', 'CURATOR', '校园图书漂流中心', '13917500003', 'curator@bookdrift175.local', 1, NOW(), NOW()),
('student', '123456', '学生读者', 'STUDENT', '校园图书漂流中心', '13917500004', 'student@bookdrift175.local', 1, NOW(), NOW()),
('club', '123456', '读书社团', 'CLUB', '校园图书漂流中心', '13917500005', 'club@bookdrift175.local', 1, NOW(), NOW()),
('teacher', '123456', '阅读指导老师', 'TEACHER', '校园图书漂流中心', '13917500006', 'teacher@bookdrift175.local', 1, NOW(), NOW());

CREATE TABLE drift_shelf (
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
INSERT INTO drift_shelf (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('175-01-001', '漂流书架示例一', '所在校区', '维护人员A', '2026-05-16 09:00', 'DRIFTING', '书架编号、书架名称、所在校区、维护人员、启用时间和书架状态维护', NOW(), NOW()),
('175-01-002', '漂流书架示例二', '所在校区', '维护人员B', '2026-05-17 14:00', 'RETURNED', '漂流书架演示数据二', NOW(), NOW());

CREATE TABLE book_profile (
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
INSERT INTO book_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('175-02-001', '漂流图书示例一', '图书类型', '当前持有人A', '2026-05-16 09:00', 'BORROWED', '图书编号、图书名称、图书类型、当前持有人、入库时间和图书状态维护', NOW(), NOW()),
('175-02-002', '漂流图书示例二', '图书类型', '当前持有人B', '2026-05-17 14:00', 'CHECKED', '漂流图书演示数据二', NOW(), NOW());

CREATE TABLE reader_profile (
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
INSERT INTO reader_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('175-03-001', '读者档案示例一', '所在班级', '负责老师A', '2026-05-16 09:00', 'RETURNED', '读者编号、读者姓名、所在班级、负责老师、建档时间和读者状态维护', NOW(), NOW()),
('175-03-002', '读者档案示例二', '所在班级', '负责老师B', '2026-05-17 14:00', 'AWARDED', '读者档案演示数据二', NOW(), NOW());

CREATE TABLE book_donation (
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
INSERT INTO book_donation (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('175-04-001', '图书捐赠示例一', '捐赠类型', '接收人员A', '2026-05-16 09:00', 'CHECKED', '捐赠编号、捐赠图书、捐赠类型、接收人员、捐赠时间和捐赠状态维护', NOW(), NOW()),
('175-04-002', '图书捐赠示例二', '捐赠类型', '接收人员B', '2026-05-17 14:00', 'PUBLISHED', '图书捐赠演示数据二', NOW(), NOW());

CREATE TABLE borrow_record (
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
INSERT INTO borrow_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('175-05-001', '借阅记录示例一', '借阅类型', '借阅读者A', '2026-05-16 09:00', 'AWARDED', '借阅编号、借阅图书、借阅类型、借阅读者、借阅时间和借阅状态维护', NOW(), NOW()),
('175-05-002', '借阅记录示例二', '借阅类型', '借阅读者B', '2026-05-17 14:00', 'FINISHED', '借阅记录演示数据二', NOW(), NOW());

CREATE TABLE return_exchange (
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
INSERT INTO return_exchange (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('175-06-001', '归还流转示例一', '流转类型', '交接人员A', '2026-05-16 09:00', 'PUBLISHED', '流转编号、归还图书、流转类型、交接人员、归还时间和流转状态维护', NOW(), NOW()),
('175-06-002', '归还流转示例二', '流转类型', '交接人员B', '2026-05-17 14:00', 'AVAILABLE', '归还流转演示数据二', NOW(), NOW());

CREATE TABLE reading_checkin (
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
INSERT INTO reading_checkin (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('175-07-001', '读书打卡示例一', '打卡类型', '打卡读者A', '2026-05-16 09:00', 'FINISHED', '打卡编号、打卡图书、打卡类型、打卡读者、打卡时间和打卡状态维护', NOW(), NOW()),
('175-07-002', '读书打卡示例二', '打卡类型', '打卡读者B', '2026-05-17 14:00', 'DRIFTING', '读书打卡演示数据二', NOW(), NOW());

CREATE TABLE reading_note (
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
INSERT INTO reading_note (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('175-08-001', '读书笔记示例一', '笔记类型', '提交读者A', '2026-05-16 09:00', 'AVAILABLE', '笔记编号、阅读图书、笔记类型、提交读者、提交时间和笔记状态维护', NOW(), NOW()),
('175-08-002', '读书笔记示例二', '笔记类型', '提交读者B', '2026-05-17 14:00', 'BORROWED', '读书笔记演示数据二', NOW(), NOW());

CREATE TABLE points_medal (
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
INSERT INTO points_medal (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('175-09-001', '积分勋章示例一', '积分类型', '评定人员A', '2026-05-16 09:00', 'DRIFTING', '勋章编号、获奖读者、积分类型、评定人员、获得时间和勋章状态维护', NOW(), NOW()),
('175-09-002', '积分勋章示例二', '积分类型', '评定人员B', '2026-05-17 14:00', 'RETURNED', '积分勋章演示数据二', NOW(), NOW());

CREATE TABLE reading_activity (
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
INSERT INTO reading_activity (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('175-10-001', '阅读活动示例一', '活动类型', '组织人员A', '2026-05-16 09:00', 'BORROWED', '活动编号、活动名称、活动类型、组织人员、活动时间和活动状态维护', NOW(), NOW()),
('175-10-002', '阅读活动示例二', '活动类型', '组织人员B', '2026-05-17 14:00', 'CHECKED', '阅读活动演示数据二', NOW(), NOW());

CREATE TABLE message_notice (
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
INSERT INTO message_notice (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('175-11-001', '消息通知示例一', '通知类型', '发送人员A', '2026-05-16 09:00', 'RETURNED', '通知编号、通知标题、通知类型、发送人员、发送时间和通知状态维护', NOW(), NOW()),
('175-11-002', '消息通知示例二', '通知类型', '发送人员B', '2026-05-17 14:00', 'AWARDED', '消息通知演示数据二', NOW(), NOW());

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
('175-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'CHECKED', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('175-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'PUBLISHED', '操作日志演示数据二', NOW(), NOW());
