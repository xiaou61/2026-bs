DROP DATABASE IF EXISTS teacher_eval_069;
CREATE DATABASE teacher_eval_069 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE teacher_eval_069;

DROP TABLE IF EXISTS evaluation_appeal;
DROP TABLE IF EXISTS evaluation_record;
DROP TABLE IF EXISTS evaluation_task;
DROP TABLE IF EXISTS evaluation_notice;
DROP TABLE IF EXISTS evaluation_indicator;
DROP TABLE IF EXISTS teacher_profile;
DROP TABLE IF EXISTS teaching_class;
DROP TABLE IF EXISTS subject_info;
DROP TABLE IF EXISTS sys_user;

CREATE TABLE sys_user (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  nickname VARCHAR(50) NOT NULL,
  phone VARCHAR(20) DEFAULT '',
  email VARCHAR(100) DEFAULT '',
  role VARCHAR(20) NOT NULL,
  class_id BIGINT NULL,
  status TINYINT NOT NULL DEFAULT 1,
  last_login_time DATETIME NULL,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_username (username),
  KEY idx_user_class_id (class_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE subject_info (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  subject_name VARCHAR(50) NOT NULL,
  subject_code VARCHAR(30) NOT NULL,
  status TINYINT NOT NULL DEFAULT 1,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_subject_code (subject_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE teaching_class (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  grade_name VARCHAR(30) NOT NULL,
  class_name VARCHAR(30) NOT NULL,
  head_teacher VARCHAR(50) DEFAULT '',
  status TINYINT NOT NULL DEFAULT 1,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_grade_class (grade_name, class_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE teacher_profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  teacher_no VARCHAR(30) NOT NULL,
  subject_id BIGINT NOT NULL,
  title_name VARCHAR(30) DEFAULT '教师',
  status TINYINT NOT NULL DEFAULT 1,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_teacher_user (user_id),
  UNIQUE KEY uk_teacher_no (teacher_no),
  KEY idx_teacher_subject_id (subject_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE evaluation_indicator (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  indicator_name VARCHAR(50) NOT NULL,
  dimension_name VARCHAR(50) NOT NULL,
  weight_value DECIMAL(5,2) NOT NULL,
  sort_no INT NOT NULL DEFAULT 1,
  status TINYINT NOT NULL DEFAULT 1,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  KEY idx_indicator_dimension (dimension_name),
  KEY idx_indicator_sort (sort_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE evaluation_task (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  task_name VARCHAR(100) NOT NULL,
  term_name VARCHAR(30) NOT NULL,
  class_id BIGINT NOT NULL,
  teacher_id BIGINT NOT NULL,
  start_time DATETIME NOT NULL,
  end_time DATETIME NOT NULL,
  status VARCHAR(20) NOT NULL DEFAULT 'DRAFT',
  creator_id BIGINT NOT NULL,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  KEY idx_task_class_id (class_id),
  KEY idx_task_teacher_id (teacher_id),
  KEY idx_task_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE evaluation_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  task_id BIGINT NOT NULL,
  evaluator_id BIGINT NOT NULL,
  teacher_id BIGINT NOT NULL,
  class_id BIGINT NOT NULL,
  attitude_score DECIMAL(5,2) NOT NULL,
  content_score DECIMAL(5,2) NOT NULL,
  method_score DECIMAL(5,2) NOT NULL,
  manage_score DECIMAL(5,2) NOT NULL,
  homework_score DECIMAL(5,2) NOT NULL,
  total_score DECIMAL(6,2) NOT NULL,
  comment_text VARCHAR(1000) DEFAULT '',
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_task_evaluator (task_id, evaluator_id),
  KEY idx_record_teacher_id (teacher_id),
  KEY idx_record_class_id (class_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE evaluation_appeal (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_id BIGINT NOT NULL,
  task_id BIGINT NOT NULL,
  teacher_id BIGINT NOT NULL,
  reason_text VARCHAR(1000) NOT NULL,
  reply_text VARCHAR(1000) DEFAULT '',
  status VARCHAR(20) NOT NULL DEFAULT 'WAITING',
  handle_by BIGINT NULL,
  handle_time DATETIME NULL,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  KEY idx_appeal_teacher_id (teacher_id),
  KEY idx_appeal_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE evaluation_notice (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(100) NOT NULL,
  content_text VARCHAR(2000) NOT NULL,
  status TINYINT NOT NULL DEFAULT 1,
  publish_time DATETIME NULL,
  creator_id BIGINT NOT NULL,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  KEY idx_notice_status (status),
  KEY idx_notice_publish_time (publish_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO subject_info (id, subject_name, subject_code, status) VALUES
(1, '语文', 'SUB-CHN', 1),
(2, '数学', 'SUB-MATH', 1),
(3, '英语', 'SUB-ENG', 1),
(4, '物理', 'SUB-PHY', 1);

INSERT INTO teaching_class (id, grade_name, class_name, head_teacher, status) VALUES
(1, '高一', '1班', '王芳', 1),
(2, '高一', '2班', '李敏', 1);

INSERT INTO sys_user (id, username, password, nickname, phone, email, role, class_id, status, last_login_time) VALUES
(1, 'admin', '123456', '系统管理员', '13800000000', 'admin@school.com', 'ADMIN', NULL, 1, NOW()),
(2, 'teacher1', '123456', '王芳', '13800000001', 'teacher1@school.com', 'TEACHER', NULL, 1, NOW()),
(3, 'teacher2', '123456', '李敏', '13800000002', 'teacher2@school.com', 'TEACHER', NULL, 1, NOW()),
(4, 'student1', '123456', '张三', '13900000001', 'student1@school.com', 'STUDENT', 1, 1, NOW()),
(5, 'student2', '123456', '李四', '13900000002', 'student2@school.com', 'STUDENT', 2, 1, NOW());

INSERT INTO teacher_profile (id, user_id, teacher_no, subject_id, title_name, status) VALUES
(1, 2, 'T2026001', 2, '高级教师', 1),
(2, 3, 'T2026002', 3, '一级教师', 1);

INSERT INTO evaluation_indicator (id, indicator_name, dimension_name, weight_value, sort_no, status) VALUES
(1, '教学态度', '课堂表现', 20.00, 1, 1),
(2, '教学内容', '课堂表现', 20.00, 2, 1),
(3, '教学方法', '课堂表现', 20.00, 3, 1),
(4, '课堂管理', '课堂表现', 20.00, 4, 1),
(5, '作业反馈', '课后指导', 20.00, 5, 1);

INSERT INTO evaluation_task (id, task_name, term_name, class_id, teacher_id, start_time, end_time, status, creator_id, create_time, update_time) VALUES
(1, '高一数学第一阶段评教', '2025-2026学年第一学期', 1, 1, '2026-02-01 08:00:00', '2026-03-01 23:59:59', 'OPEN', 1, NOW(), NOW()),
(2, '高一英语第一阶段评教', '2025-2026学年第一学期', 2, 2, '2026-01-01 08:00:00', '2026-01-20 23:59:59', 'CLOSED', 1, NOW(), NOW());

INSERT INTO evaluation_record (id, task_id, evaluator_id, teacher_id, class_id, attitude_score, content_score, method_score, manage_score, homework_score, total_score, comment_text, create_time, update_time) VALUES
(1, 2, 5, 2, 2, 90.00, 92.00, 91.00, 88.00, 90.00, 90.20, '课堂讲解清晰，互动性很好。', NOW(), NOW());

INSERT INTO evaluation_appeal (id, record_id, task_id, teacher_id, reason_text, reply_text, status, handle_by, handle_time, create_time, update_time) VALUES
(1, 1, 2, 2, '希望补充说明该次课堂为复习课，评分依据应区分课堂类型。', '', 'WAITING', NULL, NULL, NOW(), NOW());

INSERT INTO evaluation_notice (id, title, content_text, status, publish_time, creator_id, create_time, update_time) VALUES
(1, '本周评教任务提醒', '请各班同学在截止时间前完成科任教师评教。', 1, NOW(), 1, NOW(), NOW()),
(2, '教师申诉流程说明', '教师可在记录页面针对异常评分提交申诉，由管理员统一处理。', 1, NOW(), 1, NOW(), NOW());
