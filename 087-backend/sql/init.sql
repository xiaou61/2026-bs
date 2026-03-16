DROP DATABASE IF EXISTS course_manage_db;
CREATE DATABASE course_manage_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE course_manage_db;

CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    real_name VARCHAR(50),
    phone VARCHAR(20),
    role VARCHAR(20) NOT NULL,
    department_id BIGINT,
    major_id BIGINT,
    class_id BIGINT,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE department_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) NOT NULL UNIQUE,
    dean_name VARCHAR(50),
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE major_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    department_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) NOT NULL UNIQUE,
    grade_year VARCHAR(20),
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE class_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    major_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    counselor_name VARCHAR(50),
    student_count INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE academic_term (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    term_name VARCHAR(100) NOT NULL,
    start_date DATE,
    end_date DATE,
    current_flag INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE course_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    course_name VARCHAR(100) NOT NULL,
    course_code VARCHAR(50) NOT NULL UNIQUE,
    department_id BIGINT,
    teacher_id BIGINT,
    credit DECIMAL(5, 2) DEFAULT 0,
    course_type VARCHAR(30),
    course_hours INT DEFAULT 0,
    course_desc VARCHAR(1000),
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE course_schedule (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    course_id BIGINT NOT NULL,
    term_id BIGINT NOT NULL,
    teacher_id BIGINT NOT NULL,
    class_id BIGINT,
    classroom VARCHAR(100),
    week_day VARCHAR(20),
    start_section INT DEFAULT 1,
    end_section INT DEFAULT 2,
    max_student_count INT DEFAULT 50,
    selected_count INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE course_selection (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    schedule_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    select_status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_schedule_student (schedule_id, student_id)
);

CREATE TABLE course_resource (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    schedule_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    teacher_id BIGINT NOT NULL,
    title VARCHAR(200) NOT NULL,
    resource_type VARCHAR(30),
    resource_url VARCHAR(500),
    content_desc VARCHAR(1000),
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE attendance_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    schedule_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    teacher_id BIGINT NOT NULL,
    attendance_date DATE,
    attendance_status VARCHAR(30),
    remark VARCHAR(500),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE score_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    selection_id BIGINT NOT NULL UNIQUE,
    schedule_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    teacher_id BIGINT NOT NULL,
    usual_score DECIMAL(5, 2) DEFAULT 0,
    exam_score DECIMAL(5, 2) DEFAULT 0,
    total_score DECIMAL(5, 2) DEFAULT 0,
    grade_level VARCHAR(20),
    remark VARCHAR(500),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE course_evaluation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    schedule_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    teacher_id BIGINT NOT NULL,
    evaluation_score INT DEFAULT 0,
    evaluation_content VARCHAR(1000),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_eval_schedule_student (schedule_id, student_id)
);

CREATE TABLE system_notice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    type VARCHAR(30),
    status INT DEFAULT 1,
    publisher_id BIGINT,
    publish_time DATETIME,
    view_count INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE operation_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    module VARCHAR(50),
    action VARCHAR(50),
    content VARCHAR(1000),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO department_info (name, code, dean_name, status) VALUES
('计算机学院', 'CS', '王院长', 1),
('数学学院', 'MATH', '李院长', 1);

INSERT INTO major_info (department_id, name, code, grade_year, status) VALUES
(1, '软件工程', 'SE', '2023', 1),
(1, '计算机科学与技术', 'CS2023', '2023', 1),
(2, '应用数学', 'AM', '2023', 1);

INSERT INTO class_info (major_id, name, counselor_name, student_count, status) VALUES
(1, '软件工程1班', '张老师', 32, 1),
(2, '计科1班', '刘老师', 35, 1),
(3, '应用数学1班', '赵老师', 30, 1);

INSERT INTO academic_term (term_name, start_date, end_date, current_flag, status) VALUES
('2025-2026学年第二学期', '2026-02-24', '2026-07-10', 1, 1),
('2026-2027学年第一学期', '2026-09-01', '2027-01-15', 0, 1);

INSERT INTO sys_user (username, password, real_name, phone, role, department_id, major_id, class_id, status) VALUES
('admin', '123456', '系统管理员', '13800000001', 'admin', 1, NULL, NULL, 1),
('teacher', '123456', '陈教师', '13800000002', 'teacher', 1, NULL, NULL, 1),
('student', '123456', '王同学', '13800000003', 'student', 1, 1, 1, 1),
('student2', '123456', '李同学', '13800000004', 'student', 1, 2, 2, 1);

INSERT INTO course_info (course_name, course_code, department_id, teacher_id, credit, course_type, course_hours, course_desc, status) VALUES
('Java程序设计', 'CS101', 1, 2, 4.0, '必修', 64, '面向对象程序设计基础课程', 1),
('数据库原理', 'CS202', 1, 2, 3.0, '必修', 48, '关系数据库基础与SQL实践', 1),
('高等数学', 'MATH101', 2, 2, 5.0, '必修', 80, '高等数学基础课程', 1);

INSERT INTO course_schedule (course_id, term_id, teacher_id, class_id, classroom, week_day, start_section, end_section, max_student_count, selected_count, status) VALUES
(1, 1, 2, 1, 'A201', '周一', 1, 2, 60, 1, 1),
(2, 1, 2, 2, 'B305', '周三', 3, 4, 60, 1, 1),
(3, 1, 2, 3, 'C101', '周五', 1, 2, 60, 0, 1);

INSERT INTO course_selection (schedule_id, course_id, student_id, select_status) VALUES
(1, 1, 3, 1),
(2, 2, 4, 1);

INSERT INTO course_resource (schedule_id, course_id, teacher_id, title, resource_type, resource_url, content_desc, status) VALUES
(1, 1, 2, 'Java第一章课件', 'ppt', '/resource/java/ch1.pptx', '基础语法与开发环境', 1),
(2, 2, 2, '数据库实验指导', 'pdf', '/resource/db/lab1.pdf', '数据库实验任务书', 1);

INSERT INTO attendance_record (schedule_id, course_id, student_id, teacher_id, attendance_date, attendance_status, remark) VALUES
(1, 1, 3, 2, '2026-03-10', 'present', '正常到课'),
(2, 2, 4, 2, '2026-03-11', 'late', '迟到5分钟');

INSERT INTO score_record (selection_id, schedule_id, course_id, student_id, teacher_id, usual_score, exam_score, total_score, grade_level, remark) VALUES
(1, 1, 1, 3, 2, 88, 92, 90, 'A', '表现优秀'),
(2, 2, 2, 4, 2, 80, 86, 83, 'B', '继续保持');

INSERT INTO course_evaluation (schedule_id, course_id, student_id, teacher_id, evaluation_score, evaluation_content) VALUES
(1, 1, 3, 2, 95, '讲解清晰，案例丰富'),
(2, 2, 4, 2, 90, '实验安排合理，节奏适中');

INSERT INTO system_notice (title, content, type, status, publisher_id, publish_time, view_count) VALUES
('选课开放通知', '本学期第二轮选课将于下周一开放，请同学们按时参与。', 'system', 1, 1, NOW(), 25),
('课程资源更新提醒', 'Java程序设计课程已上传第一章课件，请相关同学及时查看。', 'course', 1, 1, NOW(), 18);

INSERT INTO operation_log (user_id, module, action, content) VALUES
(1, 'schedule', 'add', '新增Java程序设计排课'),
(2, 'resource', 'add', '发布Java第一章课件');
