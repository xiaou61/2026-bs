DROP DATABASE IF EXISTS student_integrated_067;
CREATE DATABASE student_integrated_067 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE student_integrated_067;

CREATE TABLE sys_user (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  nickname VARCHAR(50) NOT NULL,
  phone VARCHAR(20) DEFAULT '',
  email VARCHAR(100) DEFAULT '',
  role VARCHAR(20) NOT NULL,
  status TINYINT NOT NULL DEFAULT 1,
  college VARCHAR(100) DEFAULT '',
  major VARCHAR(100) DEFAULT '',
  grade VARCHAR(20) DEFAULT '',
  last_login_time DATETIME NULL,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE campus_course (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(120) NOT NULL,
  teacher_id BIGINT NOT NULL,
  credit DECIMAL(4,1) NOT NULL DEFAULT 2.0,
  location VARCHAR(120) DEFAULT '',
  max_student INT NOT NULL DEFAULT 60,
  selected_count INT NOT NULL DEFAULT 0,
  start_date DATE NULL,
  end_date DATE NULL,
  status TINYINT NOT NULL DEFAULT 1,
  description TEXT,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE course_enroll (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  course_id BIGINT NOT NULL,
  student_id BIGINT NOT NULL,
  status TINYINT NOT NULL DEFAULT 1,
  score DECIMAL(5,2) DEFAULT NULL,
  remark VARCHAR(255) DEFAULT '',
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_course_student (course_id, student_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE campus_activity (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(120) NOT NULL,
  organizer_id BIGINT NOT NULL,
  location VARCHAR(120) DEFAULT '',
  start_time DATETIME NULL,
  end_time DATETIME NULL,
  max_participant INT NOT NULL DEFAULT 200,
  participant_count INT NOT NULL DEFAULT 0,
  status TINYINT NOT NULL DEFAULT 1,
  content TEXT,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE activity_signup (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  activity_id BIGINT NOT NULL,
  student_id BIGINT NOT NULL,
  status TINYINT NOT NULL DEFAULT 1,
  checkin_time DATETIME NULL,
  remark VARCHAR(255) DEFAULT '',
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_activity_student (activity_id, student_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE intern_job (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(120) NOT NULL,
  company VARCHAR(120) NOT NULL,
  city VARCHAR(80) DEFAULT '',
  salary VARCHAR(80) DEFAULT '',
  deadline DATE NULL,
  publisher_id BIGINT NOT NULL,
  status TINYINT NOT NULL DEFAULT 1,
  description TEXT,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE job_apply (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  job_id BIGINT NOT NULL,
  student_id BIGINT NOT NULL,
  resume_url VARCHAR(255) DEFAULT '',
  status TINYINT NOT NULL DEFAULT 0,
  apply_note VARCHAR(255) DEFAULT '',
  review_note VARCHAR(255) DEFAULT '',
  reviewer_id BIGINT NULL,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_job_student (job_id, student_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE lost_found (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  type TINYINT NOT NULL,
  title VARCHAR(120) NOT NULL,
  item_name VARCHAR(120) NOT NULL,
  contact VARCHAR(120) NOT NULL,
  location VARCHAR(120) DEFAULT '',
  happen_time DATETIME NULL,
  description TEXT,
  image_url VARCHAR(255) DEFAULT '',
  status TINYINT NOT NULL DEFAULT 1,
  publisher_id BIGINT NOT NULL,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE campus_notice (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(120) NOT NULL,
  content TEXT,
  publisher_id BIGINT NOT NULL,
  status TINYINT NOT NULL DEFAULT 1,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO sys_user(id, username, password, nickname, phone, email, role, status, college, major, grade) VALUES
(1, 'admin', '123456', '系统管理员', '13800000001', 'admin@campus.com', 'ADMIN', 1, '信息工程学院', '软件工程', '2026'),
(2, 'teacher', '123456', '张老师', '13800000002', 'teacher@campus.com', 'TEACHER', 1, '信息工程学院', '计算机科学', '教师'),
(3, 'student', '123456', '李同学', '13800000003', 'student@campus.com', 'STUDENT', 1, '信息工程学院', '软件工程', '2023'),
(4, 'student2', '123456', '王同学', '13800000004', 'student2@campus.com', 'STUDENT', 1, '经济管理学院', '工商管理', '2022');

INSERT INTO campus_course(id, name, teacher_id, credit, location, max_student, selected_count, start_date, end_date, status, description) VALUES
(1, 'Java企业级开发', 2, 3.0, 'A1-301', 80, 2, '2026-03-01', '2026-06-30', 1, '面向企业级应用开发实践课程'),
(2, '数据分析基础', 2, 2.0, 'B2-205', 60, 1, '2026-03-05', '2026-07-01', 1, '数据处理与可视化入门课程');

INSERT INTO course_enroll(id, course_id, student_id, status, score, remark) VALUES
(1, 1, 3, 1, NULL, '正常选课'),
(2, 2, 3, 1, NULL, '正常选课'),
(3, 1, 4, 1, NULL, '正常选课');

INSERT INTO campus_activity(id, title, organizer_id, location, start_time, end_time, max_participant, participant_count, status, content) VALUES
(1, '春季校园招聘会志愿服务', 2, '体育馆主馆', '2026-03-20 14:00:00', '2026-03-20 18:00:00', 300, 2, 1, '面向全校学生开放的志愿服务活动'),
(2, '大学生创新创业沙龙', 2, '图书馆报告厅', '2026-04-02 19:00:00', '2026-04-02 21:00:00', 200, 1, 1, '邀请企业导师开展创新创业分享');

INSERT INTO activity_signup(id, activity_id, student_id, status, checkin_time, remark) VALUES
(1, 1, 3, 1, NULL, '已报名'),
(2, 1, 4, 1, NULL, '已报名'),
(3, 2, 3, 1, NULL, '已报名');

INSERT INTO intern_job(id, title, company, city, salary, deadline, publisher_id, status, description) VALUES
(1, 'Java后端实习生', '星海科技', '上海', '6k-8k/月', '2026-05-30', 2, 1, '参与校园管理系统开发与测试'),
(2, '数据运营实习生', '智联未来', '杭州', '5k-7k/月', '2026-05-20', 2, 1, '负责数据整理与运营分析');

INSERT INTO job_apply(id, job_id, student_id, resume_url, status, apply_note, review_note, reviewer_id) VALUES
(1, 1, 3, 'https://example.com/resume/student.pdf', 0, '有后端项目经验', '', NULL),
(2, 2, 4, 'https://example.com/resume/student2.pdf', 1, '参加过运营类项目', '通过', 2);

INSERT INTO lost_found(id, type, title, item_name, contact, location, happen_time, description, image_url, status, publisher_id) VALUES
(1, 1, '寻找校园卡', '校园卡', 'student@campus.com', '图书馆三楼', '2026-02-10 16:20:00', '卡套为蓝色', '', 1, 3),
(2, 2, '拾到蓝牙耳机', '蓝牙耳机', 'teacher@campus.com', '教学楼B区', '2026-02-09 10:00:00', '已交到辅导员办公室', '', 1, 2);

INSERT INTO campus_notice(id, title, content, publisher_id, status) VALUES
(1, '关于期中教学周安排通知', '请各班级按教学办安排参加期中检查。', 2, 1),
(2, '系统升级公告', '平台将于本周六晚22:00进行维护升级。', 1, 1);

ALTER TABLE sys_user AUTO_INCREMENT = 10;
ALTER TABLE campus_course AUTO_INCREMENT = 10;
ALTER TABLE course_enroll AUTO_INCREMENT = 10;
ALTER TABLE campus_activity AUTO_INCREMENT = 10;
ALTER TABLE activity_signup AUTO_INCREMENT = 10;
ALTER TABLE intern_job AUTO_INCREMENT = 10;
ALTER TABLE job_apply AUTO_INCREMENT = 10;
ALTER TABLE lost_found AUTO_INCREMENT = 10;
ALTER TABLE campus_notice AUTO_INCREMENT = 10;
