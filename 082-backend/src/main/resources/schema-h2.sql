CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    real_name VARCHAR(50),
    phone VARCHAR(20),
    role VARCHAR(20) NOT NULL,
    avatar VARCHAR(255),
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE subject (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(500),
    sort INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE course (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    subject_id BIGINT NOT NULL,
    title VARCHAR(200) NOT NULL,
    teacher_id BIGINT,
    level VARCHAR(30),
    cover VARCHAR(255),
    summary VARCHAR(1000),
    study_hours INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE course_chapter (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    course_id BIGINT NOT NULL,
    title VARCHAR(200) NOT NULL,
    content CLOB,
    video_url VARCHAR(255),
    sort INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE question_bank (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    subject_id BIGINT NOT NULL,
    name VARCHAR(200) NOT NULL,
    type VARCHAR(30) NOT NULL,
    total_count INT DEFAULT 0,
    difficulty VARCHAR(30),
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE question (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    bank_id BIGINT NOT NULL,
    subject_id BIGINT NOT NULL,
    type VARCHAR(20) NOT NULL,
    stem CLOB NOT NULL,
    option_a VARCHAR(500),
    option_b VARCHAR(500),
    option_c VARCHAR(500),
    option_d VARCHAR(500),
    answer VARCHAR(200) NOT NULL,
    analysis CLOB,
    score DECIMAL(10,2) DEFAULT 1.00,
    difficulty VARCHAR(30),
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE exam_paper (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    subject_id BIGINT NOT NULL,
    title VARCHAR(200) NOT NULL,
    duration_minutes INT DEFAULT 120,
    total_score DECIMAL(10,2) DEFAULT 100.00,
    pass_score DECIMAL(10,2) DEFAULT 60.00,
    question_count INT DEFAULT 0,
    publish_status INT DEFAULT 0,
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE exam_paper_question (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    paper_id BIGINT NOT NULL,
    question_id BIGINT NOT NULL,
    sort INT DEFAULT 0,
    score DECIMAL(10,2) DEFAULT 1.00
);

CREATE TABLE exam_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    paper_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    start_time TIMESTAMP,
    submit_time TIMESTAMP,
    duration_seconds INT DEFAULT 0,
    total_score DECIMAL(10,2) DEFAULT 0,
    objective_score DECIMAL(10,2) DEFAULT 0,
    subjective_score DECIMAL(10,2) DEFAULT 0,
    pass_status INT DEFAULT 0,
    status VARCHAR(30) DEFAULT 'submitted',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE exam_record_answer (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    record_id BIGINT NOT NULL,
    paper_id BIGINT NOT NULL,
    question_id BIGINT NOT NULL,
    user_answer VARCHAR(500),
    correct_answer VARCHAR(200),
    score DECIMAL(10,2) DEFAULT 0,
    is_correct INT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE study_plan (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    subject_id BIGINT NOT NULL,
    title VARCHAR(200) NOT NULL,
    start_date DATE,
    end_date DATE,
    daily_target VARCHAR(500),
    completed_days INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE notice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content CLOB,
    type VARCHAR(30),
    status INT DEFAULT 0,
    publisher_id BIGINT,
    publish_time TIMESTAMP,
    view_count INT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
