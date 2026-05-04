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

CREATE TABLE opera_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) NOT NULL UNIQUE,
    dean_name VARCHAR(50),
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE artist_archive (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    department_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) NOT NULL UNIQUE,
    grade_year VARCHAR(20),
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE venue_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    major_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    counselor_name VARCHAR(50),
    member_count INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE cultural_season (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    term_name VARCHAR(100) NOT NULL,
    start_date DATE,
    end_date DATE,
    current_flag INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE repertoire_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    course_name VARCHAR(100) NOT NULL,
    course_code VARCHAR(50) NOT NULL UNIQUE,
    department_id BIGINT,
    artist_id BIGINT,
    credit DECIMAL(5, 2) DEFAULT 0,
    course_type VARCHAR(30),
    course_hours INT DEFAULT 0,
    course_desc VARCHAR(1000),
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE performance_schedule (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    course_id BIGINT NOT NULL,
    term_id BIGINT NOT NULL,
    artist_id BIGINT NOT NULL,
    class_id BIGINT,
    classroom VARCHAR(100),
    week_day VARCHAR(20),
    start_section INT DEFAULT 1,
    end_section INT DEFAULT 2,
    max_member_count INT DEFAULT 50,
    selected_count INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE booking_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    schedule_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    member_id BIGINT NOT NULL,
    select_status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_schedule_member UNIQUE (schedule_id, member_id)
);

CREATE TABLE media_resource (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    schedule_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    artist_id BIGINT NOT NULL,
    title VARCHAR(200) NOT NULL,
    resource_type VARCHAR(30),
    resource_url VARCHAR(500),
    content_desc VARCHAR(1000),
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE checkin_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    schedule_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    member_id BIGINT NOT NULL,
    artist_id BIGINT NOT NULL,
    attendance_date DATE,
    attendance_status VARCHAR(30),
    remark VARCHAR(500),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE review_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    selection_id BIGINT NOT NULL UNIQUE,
    schedule_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    member_id BIGINT NOT NULL,
    artist_id BIGINT NOT NULL,
    usual_score DECIMAL(5, 2) DEFAULT 0,
    exam_score DECIMAL(5, 2) DEFAULT 0,
    total_score DECIMAL(5, 2) DEFAULT 0,
    grade_level VARCHAR(20),
    remark VARCHAR(500),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE appreciation_comment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    schedule_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    member_id BIGINT NOT NULL,
    artist_id BIGINT NOT NULL,
    evaluation_score INT DEFAULT 0,
    evaluation_content VARCHAR(1000),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_eval_schedule_member UNIQUE (schedule_id, member_id)
);

CREATE TABLE system_notice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content CLOB,
    type VARCHAR(30),
    status INT DEFAULT 1,
    publisher_id BIGINT,
    publish_time TIMESTAMP,
    view_count INT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE operation_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    module VARCHAR(50),
    action VARCHAR(50),
    content VARCHAR(1000),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
