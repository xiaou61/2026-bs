CREATE DATABASE IF NOT EXISTS course_selection_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE course_selection_system;

DROP TABLE IF EXISTS grades;
DROP TABLE IF EXISTS course_selections;
DROP TABLE IF EXISTS notices;
DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS system_config;

CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(50) NOT NULL,
    role VARCHAR(20) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    student_no VARCHAR(50),
    teacher_no VARCHAR(50),
    major VARCHAR(100),
    class_name VARCHAR(50),
    status INT DEFAULT 1,
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE courses (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    course_no VARCHAR(50) NOT NULL UNIQUE,
    course_name VARCHAR(100) NOT NULL,
    teacher_id BIGINT,
    teacher_name VARCHAR(50),
    credit DECIMAL(3,1) NOT NULL,
    total_capacity INT NOT NULL DEFAULT 0,
    selected_count INT DEFAULT 0,
    course_type VARCHAR(50),
    semester VARCHAR(50),
    classroom VARCHAR(100),
    time_slot VARCHAR(100),
    description TEXT,
    status INT DEFAULT 1,
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (teacher_id) REFERENCES users(id)
);

CREATE TABLE course_selections (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_id BIGINT NOT NULL,
    student_name VARCHAR(50),
    student_no VARCHAR(50),
    course_id BIGINT NOT NULL,
    course_name VARCHAR(100),
    course_no VARCHAR(50),
    teacher_name VARCHAR(50),
    credit DECIMAL(3,1),
    status VARCHAR(20) DEFAULT 'selected',
    selection_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES users(id),
    FOREIGN KEY (course_id) REFERENCES courses(id),
    UNIQUE KEY uk_student_course (student_id, course_id)
);

CREATE TABLE grades (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_id BIGINT NOT NULL,
    student_name VARCHAR(50),
    student_no VARCHAR(50),
    course_id BIGINT NOT NULL,
    course_name VARCHAR(100),
    course_no VARCHAR(50),
    teacher_id BIGINT,
    teacher_name VARCHAR(50),
    credit DECIMAL(3,1),
    score DECIMAL(5,2),
    gpa DECIMAL(3,2),
    semester VARCHAR(50),
    status VARCHAR(20) DEFAULT 'pending',
    submit_time DATETIME,
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES users(id),
    FOREIGN KEY (course_id) REFERENCES courses(id),
    UNIQUE KEY uk_student_course (student_id, course_id)
);

CREATE TABLE notices (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    publisher_id BIGINT,
    publisher_name VARCHAR(50),
    type VARCHAR(50),
    status INT DEFAULT 1,
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (publisher_id) REFERENCES users(id)
);

CREATE TABLE system_config (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    config_key VARCHAR(100) NOT NULL UNIQUE,
    config_value VARCHAR(500),
    description VARCHAR(200),
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO users (username, password, name, role, email, student_no, major, class_name) VALUES
('admin', '123456', '系统管理员', 'admin', 'admin@example.com', NULL, NULL, NULL),
('student1', '123456', '张三', 'student', 'student1@example.com', '2021001', '计算机科学与技术', '计算机21-1'),
('student2', '123456', '李四', 'student', 'student2@example.com', '2021002', '软件工程', '软件21-1'),
('teacher1', '123456', '王教授', 'teacher', 'teacher1@example.com', NULL, NULL, NULL),
('teacher2', '123456', '赵老师', 'teacher', 'teacher2@example.com', NULL, NULL, NULL);

UPDATE users SET teacher_no = 'T001' WHERE username = 'teacher1';
UPDATE users SET teacher_no = 'T002' WHERE username = 'teacher2';

INSERT INTO courses (course_no, course_name, teacher_id, teacher_name, credit, total_capacity, course_type, semester, classroom, time_slot, description) VALUES
('CS101', '数据结构与算法', 4, '王教授', 4.0, 60, '专业必修', '2024-2025-1', '教学楼A101', '周一3-4节，周三3-4节', '讲授数据结构基本概念和常用算法'),
('CS102', '操作系统原理', 4, '王教授', 3.5, 50, '专业必修', '2024-2025-1', '教学楼A102', '周二1-2节', '讲授操作系统的基本原理'),
('CS201', '数据库系统', 5, '赵老师', 3.0, 55, '专业必修', '2024-2025-1', '教学楼B201', '周四3-4节', '讲授数据库设计与应用'),
('CS202', '计算机网络', 5, '赵老师', 3.5, 50, '专业必修', '2024-2025-1', '教学楼B202', '周五1-2节', '讲授计算机网络基础知识');

INSERT INTO system_config (config_key, config_value, description) VALUES
('selection_start_time', '2024-09-01 00:00:00', '选课开始时间'),
('selection_end_time', '2024-09-30 23:59:59', '选课结束时间'),
('selection_enabled', 'true', '是否开放选课');

INSERT INTO notices (title, content, publisher_id, publisher_name, type) VALUES
('2024-2025学年第一学期选课通知', '各位同学：\n2024-2025学年第一学期选课工作即将开始，请大家在规定时间内完成选课。选课时间：2024年9月1日至9月30日。', 1, '系统管理员', 'system'),
('关于课程容量调整的通知', '因教学需求调整，部分课程容量有所变化，请同学们及时关注。', 1, '系统管理员', 'course');

