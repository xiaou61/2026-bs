DROP TABLE IF EXISTS operation_log;
DROP TABLE IF EXISTS system_notice;
DROP TABLE IF EXISTS course_evaluation;
DROP TABLE IF EXISTS score_record;
DROP TABLE IF EXISTS attendance_record;
DROP TABLE IF EXISTS course_resource;
DROP TABLE IF EXISTS course_selection;
DROP TABLE IF EXISTS course_schedule;
DROP TABLE IF EXISTS course_info;
DROP TABLE IF EXISTS academic_term;
DROP TABLE IF EXISTS class_info;
DROP TABLE IF EXISTS major_info;
DROP TABLE IF EXISTS department_info;
DROP TABLE IF EXISTS sys_user;

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
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE department_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) NOT NULL UNIQUE,
    dean_name VARCHAR(50),
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE major_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    department_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) NOT NULL UNIQUE,
    grade_year VARCHAR(20),
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE class_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    major_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    counselor_name VARCHAR(50),
    student_count INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE academic_term (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    term_name VARCHAR(100) NOT NULL,
    start_date DATE,
    end_date DATE,
    current_flag INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
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
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
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
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE course_selection (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    schedule_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    select_status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_schedule_student UNIQUE (schedule_id, student_id)
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
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
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
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
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
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
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
    CONSTRAINT uk_eval_schedule_student UNIQUE (schedule_id, student_id)
);

CREATE TABLE system_notice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content CLOB,
    type VARCHAR(30),
    status INT DEFAULT 1,
    publisher_id BIGINT,
    publish_time DATETIME,
    view_count INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE operation_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    module VARCHAR(50),
    action VARCHAR(50),
    content VARCHAR(1000),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);
