DROP TABLE IF EXISTS work_log;
DROP TABLE IF EXISTS salary;
DROP TABLE IF EXISTS document;
DROP TABLE IF EXISTS schedule;
DROP TABLE IF EXISTS notice_read;
DROP TABLE IF EXISTS notice;
DROP TABLE IF EXISTS meeting;
DROP TABLE IF EXISTS meeting_room;
DROP TABLE IF EXISTS leave_request;
DROP TABLE IF EXISTS attendance;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS department;

CREATE TABLE department (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    parent_id BIGINT DEFAULT 0,
    leader_id BIGINT,
    sort INT DEFAULT 0,
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

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
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
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
    CONSTRAINT uk_notice_user UNIQUE (notice_id, user_id)
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
    CONSTRAINT uk_user_month UNIQUE (user_id, year_month)
);

CREATE TABLE work_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    log_date DATE NOT NULL,
    content TEXT,
    plan TEXT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);
