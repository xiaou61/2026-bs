DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS favorite;
DROP TABLE IF EXISTS note;
DROP TABLE IF EXISTS daily_checkin;
DROP TABLE IF EXISTS study_plan;
DROP TABLE IF EXISTS wrong_question;
DROP TABLE IF EXISTS answer_record;
DROP TABLE IF EXISTS question;
DROP TABLE IF EXISTS question_category;
DROP TABLE IF EXISTS study_record;
DROP TABLE IF EXISTS chapter;
DROP TABLE IF EXISTS course;
DROP TABLE IF EXISTS subject;
DROP TABLE IF EXISTS notice;
DROP TABLE IF EXISTS sys_user;

CREATE TABLE sys_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    openid VARCHAR(100),
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50),
    avatar VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(100),
    role INT DEFAULT 0,
    target_school VARCHAR(100),
    target_major VARCHAR(100),
    exam_year INT,
    points INT DEFAULT 0,
    study_days INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
);

CREATE TABLE subject (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    code VARCHAR(20),
    icon VARCHAR(32),
    sort_order INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
);

CREATE TABLE course (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    subject_id BIGINT NOT NULL,
    teacher_id BIGINT,
    title VARCHAR(200) NOT NULL,
    cover VARCHAR(255),
    description TEXT,
    price DECIMAL(10,2) DEFAULT 0,
    is_free INT DEFAULT 1,
    chapter_count INT DEFAULT 0,
    student_count INT DEFAULT 0,
    view_count INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
);

CREATE TABLE chapter (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    course_id BIGINT NOT NULL,
    title VARCHAR(200) NOT NULL,
    video_url VARCHAR(500),
    duration INT DEFAULT 0,
    sort_order INT DEFAULT 0,
    is_free INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
);

CREATE TABLE study_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    chapter_id BIGINT,
    progress INT DEFAULT 0,
    last_position INT DEFAULT 0,
    study_duration INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE question_category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    subject_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    parent_id BIGINT DEFAULT 0,
    sort_order INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
);

CREATE TABLE question (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_id BIGINT NOT NULL,
    subject_id BIGINT NOT NULL,
    type INT NOT NULL,
    difficulty INT DEFAULT 1,
    content TEXT NOT NULL,
    options TEXT,
    answer TEXT,
    analysis TEXT,
    source VARCHAR(200),
    view_count INT DEFAULT 0,
    error_count INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
);

CREATE TABLE answer_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    question_id BIGINT NOT NULL,
    user_answer TEXT,
    is_correct INT,
    time_spent INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE wrong_question (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    question_id BIGINT NOT NULL,
    wrong_count INT DEFAULT 1,
    last_wrong_time DATETIME,
    note TEXT,
    status INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE study_plan (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(200) NOT NULL,
    subject_id BIGINT,
    target_content TEXT,
    start_date DATE,
    end_date DATE,
    daily_hours DECIMAL(4,1) DEFAULT 2,
    status INT DEFAULT 0,
    progress INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
);

CREATE TABLE daily_checkin (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    checkin_date DATE NOT NULL,
    study_duration INT DEFAULT 0,
    question_count INT DEFAULT 0,
    correct_count INT DEFAULT 0,
    note TEXT,
    mood INT DEFAULT 3,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_user_date UNIQUE (user_id, checkin_date)
);

CREATE TABLE note (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    subject_id BIGINT,
    course_id BIGINT,
    question_id BIGINT,
    title VARCHAR(200),
    content TEXT,
    is_public INT DEFAULT 0,
    like_count INT DEFAULT 0,
    view_count INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
);

CREATE TABLE favorite (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    target_type INT NOT NULL,
    target_id BIGINT NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_user_target UNIQUE (user_id, target_type, target_id)
);

CREATE TABLE post (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    subject_id BIGINT,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    images VARCHAR(1000),
    view_count INT DEFAULT 0,
    like_count INT DEFAULT 0,
    comment_count INT DEFAULT 0,
    is_top INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
);

CREATE TABLE comment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    parent_id BIGINT DEFAULT 0,
    reply_user_id BIGINT,
    content TEXT NOT NULL,
    like_count INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
);

CREATE TABLE notice (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    type INT DEFAULT 1,
    is_top INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
);
