DROP TABLE IF EXISTS message;
DROP TABLE IF EXISTS notice;
DROP TABLE IF EXISTS attendance_stat;
DROP TABLE IF EXISTS makeup_request;
DROP TABLE IF EXISTS leave_request;
DROP TABLE IF EXISTS attendance_record;
DROP TABLE IF EXISTS attendance_task;
DROP TABLE IF EXISTS course_student;
DROP TABLE IF EXISTS course_schedule;
DROP TABLE IF EXISTS course;
DROP TABLE IF EXISTS semester;
DROP TABLE IF EXISTS sys_user;

CREATE TABLE sys_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    real_name VARCHAR(50),
    avatar VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(100),
    openid VARCHAR(100),
    student_no VARCHAR(50),
    teacher_no VARCHAR(50),
    department VARCHAR(100),
    class_name VARCHAR(100),
    role TINYINT DEFAULT 0,
    status TINYINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);

CREATE TABLE semester (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    is_current TINYINT DEFAULT 0,
    status TINYINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE course (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    course_code VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    teacher_id BIGINT NOT NULL,
    semester_id BIGINT,
    credit DECIMAL(3,1),
    hours INT,
    description CLOB,
    student_count INT DEFAULT 0,
    status TINYINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);

CREATE TABLE course_schedule (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    course_id BIGINT NOT NULL,
    week_day TINYINT NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    classroom VARCHAR(100),
    building VARCHAR(100),
    start_week INT DEFAULT 1,
    end_week INT DEFAULT 18,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE course_student (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    course_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    status TINYINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_course_student UNIQUE (course_id, student_id)
);

CREATE TABLE attendance_task (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    course_id BIGINT NOT NULL,
    schedule_id BIGINT,
    teacher_id BIGINT NOT NULL,
    title VARCHAR(100),
    sign_type TINYINT DEFAULT 1,
    sign_code VARCHAR(20),
    qr_code VARCHAR(255),
    latitude DECIMAL(10,6),
    longitude DECIMAL(10,6),
    address VARCHAR(255),
    distance INT DEFAULT 100,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    duration INT,
    late_time TIMESTAMP,
    status TINYINT DEFAULT 1,
    total_count INT DEFAULT 0,
    signed_count INT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);

CREATE TABLE attendance_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    task_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    sign_time TIMESTAMP,
    sign_type TINYINT,
    latitude DECIMAL(10,6),
    longitude DECIMAL(10,6),
    address VARCHAR(255),
    distance DECIMAL(10,2),
    device_info VARCHAR(255),
    ip_address VARCHAR(50),
    status TINYINT DEFAULT 0,
    remark VARCHAR(255),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_task_student UNIQUE (task_id, student_id)
);

CREATE TABLE leave_request (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    task_id BIGINT,
    leave_type TINYINT DEFAULT 1,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    reason CLOB,
    attachment VARCHAR(255),
    status TINYINT DEFAULT 0,
    approve_time TIMESTAMP,
    approve_remark VARCHAR(255),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);

CREATE TABLE makeup_request (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    task_id BIGINT NOT NULL,
    reason CLOB,
    attachment VARCHAR(255),
    status TINYINT DEFAULT 0,
    approve_time TIMESTAMP,
    approve_remark VARCHAR(255),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);

CREATE TABLE attendance_stat (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    semester_id BIGINT,
    total_count INT DEFAULT 0,
    normal_count INT DEFAULT 0,
    late_count INT DEFAULT 0,
    absent_count INT DEFAULT 0,
    leave_count INT DEFAULT 0,
    makeup_count INT DEFAULT 0,
    attendance_rate DECIMAL(5,2) DEFAULT 0,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_student_course UNIQUE (student_id, course_id)
);

CREATE TABLE notice (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content CLOB,
    type TINYINT DEFAULT 1,
    course_id BIGINT,
    publisher_id BIGINT,
    is_top TINYINT DEFAULT 0,
    status TINYINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);

CREATE TABLE message (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(200),
    content CLOB,
    type TINYINT DEFAULT 1,
    related_id BIGINT,
    is_read TINYINT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
