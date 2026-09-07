DROP TABLE IF EXISTS pickup_record;
DROP TABLE IF EXISTS system_notice;
DROP TABLE IF EXISTS parent_feedback;
DROP TABLE IF EXISTS health_record;
DROP TABLE IF EXISTS attendance_record;
DROP TABLE IF EXISTS weekly_recipe;
DROP TABLE IF EXISTS child_profile;
DROP TABLE IF EXISTS activity_schedule;
DROP TABLE IF EXISTS activity_info;
DROP TABLE IF EXISTS school_term;
DROP TABLE IF EXISTS class_info;
DROP TABLE IF EXISTS grade_info;
DROP TABLE IF EXISTS campus_info;
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
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE campus_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) NOT NULL UNIQUE,
    dean_name VARCHAR(50),
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE grade_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    department_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) NOT NULL UNIQUE,
    grade_year VARCHAR(20),
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE class_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    major_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    counselor_name VARCHAR(50),
    student_count INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE school_term (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    term_name VARCHAR(100) NOT NULL,
    start_date DATE,
    end_date DATE,
    current_flag INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE activity_info (
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
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE activity_schedule (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    course_id BIGINT NOT NULL,
    term_id BIGINT NOT NULL,
    teacher_id BIGINT NOT NULL,
    class_id BIGINT,
    classroom VARCHAR(100),
    week_day VARCHAR(20),
    start_section INT DEFAULT 1,
    end_section INT DEFAULT 2,
    max_student_count INT DEFAULT 40,
    selected_count INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE child_profile (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    child_name VARCHAR(50) NOT NULL,
    gender VARCHAR(10),
    birthday DATE,
    campus_id BIGINT,
    grade_id BIGINT,
    class_id BIGINT,
    parent_id BIGINT,
    teacher_id BIGINT,
    allergy_info VARCHAR(500),
    pickup_person VARCHAR(50),
    profile_status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE weekly_recipe (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    schedule_id BIGINT,
    course_id BIGINT,
    teacher_id BIGINT,
    title VARCHAR(200) NOT NULL,
    resource_type VARCHAR(30),
    resource_url VARCHAR(500),
    content_desc VARCHAR(1000),
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
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
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE health_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    child_id BIGINT NOT NULL,
    teacher_id BIGINT NOT NULL,
    record_date DATE,
    temperature DECIMAL(4, 1),
    health_status VARCHAR(100),
    emotion_status VARCHAR(100),
    attendance_advice VARCHAR(200),
    remark VARCHAR(500),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE parent_feedback (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    child_id BIGINT NOT NULL,
    parent_id BIGINT NOT NULL,
    teacher_id BIGINT,
    feedback_type VARCHAR(50),
    feedback_score INT DEFAULT 0,
    feedback_content VARCHAR(1000),
    reply_content VARCHAR(1000),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE system_notice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    type VARCHAR(30),
    status INT DEFAULT 1,
    publisher_id BIGINT,
    publish_time TIMESTAMP,
    view_count INT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE pickup_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    child_id BIGINT NOT NULL,
    parent_id BIGINT NOT NULL,
    pickup_person VARCHAR(50),
    pickup_time TIMESTAMP,
    pickup_status VARCHAR(30),
    remark VARCHAR(500),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
