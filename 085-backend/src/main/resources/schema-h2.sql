DROP TABLE IF EXISTS operation_log;
DROP TABLE IF EXISTS system_notice;
DROP TABLE IF EXISTS eval_record_item;
DROP TABLE IF EXISTS eval_record;
DROP TABLE IF EXISTS eval_task;
DROP TABLE IF EXISTS eval_indicator;
DROP TABLE IF EXISTS math_course;
DROP TABLE IF EXISTS course_category;
DROP TABLE IF EXISTS sys_user;

CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    real_name VARCHAR(50),
    phone VARCHAR(20),
    role VARCHAR(20) NOT NULL,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE course_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    parent_id BIGINT DEFAULT 0,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) NOT NULL UNIQUE,
    sort INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE math_course (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    course_name VARCHAR(120) NOT NULL,
    course_code VARCHAR(60) NOT NULL UNIQUE,
    category_id BIGINT NOT NULL,
    teacher_id BIGINT NOT NULL,
    credit DECIMAL(6,2) DEFAULT 2.00,
    term VARCHAR(50),
    description VARCHAR(1000),
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE eval_indicator (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    indicator_name VARCHAR(120) NOT NULL,
    weight DECIMAL(6,2) NOT NULL,
    sort INT DEFAULT 0,
    status INT DEFAULT 1,
    description VARCHAR(500),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE eval_task (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    task_name VARCHAR(150) NOT NULL,
    course_id BIGINT NOT NULL,
    term VARCHAR(50),
    start_time DATETIME,
    end_time DATETIME,
    status INT DEFAULT 1,
    creator_id BIGINT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE eval_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    task_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    total_score DECIMAL(6,2) NOT NULL,
    comment_content VARCHAR(1000),
    submit_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_task_student UNIQUE (task_id, student_id)
);

CREATE TABLE eval_record_item (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    record_id BIGINT NOT NULL,
    indicator_id BIGINT NOT NULL,
    score DECIMAL(6,2) NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE system_notice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content CLOB,
    type VARCHAR(30),
    status INT DEFAULT 0,
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
