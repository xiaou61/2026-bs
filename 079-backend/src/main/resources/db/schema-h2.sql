DROP TABLE IF EXISTS operation_log;
DROP TABLE IF EXISTS banner;
DROP TABLE IF EXISTS forum_like;
DROP TABLE IF EXISTS forum_reply;
DROP TABLE IF EXISTS forum_post;
DROP TABLE IF EXISTS forum_category;
DROP TABLE IF EXISTS job_post;
DROP TABLE IF EXISTS alumni_company;
DROP TABLE IF EXISTS donation_record;
DROP TABLE IF EXISTS donation_project;
DROP TABLE IF EXISTS activity_photo;
DROP TABLE IF EXISTS activity_sign;
DROP TABLE IF EXISTS activity;
DROP TABLE IF EXISTS news_comment;
DROP TABLE IF EXISTS news;
DROP TABLE IF EXISTS alumni_info;
DROP TABLE IF EXISTS class_info;
DROP TABLE IF EXISTS grade;
DROP TABLE IF EXISTS user;

CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(50),
    role VARCHAR(20) DEFAULT 'alumni',
    status INT DEFAULT 0,
    avatar VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(100),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE grade (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    year INT,
    description VARCHAR(500),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE class_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    grade_id BIGINT,
    name VARCHAR(50) NOT NULL,
    major VARCHAR(100),
    student_count INT DEFAULT 0,
    contact_id BIGINT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE alumni_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    student_no VARCHAR(50),
    grade_id BIGINT,
    class_id BIGINT,
    gender INT DEFAULT 1,
    birthday DATE,
    native_place VARCHAR(100),
    political VARCHAR(50),
    degree VARCHAR(50),
    major VARCHAR(100),
    graduation_date DATE,
    company VARCHAR(200),
    position VARCHAR(100),
    industry VARCHAR(100),
    city VARCHAR(100),
    introduction TEXT,
    is_contact INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE news (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    cover VARCHAR(255),
    category VARCHAR(50) DEFAULT 'news',
    view_count INT DEFAULT 0,
    is_top INT DEFAULT 0,
    status INT DEFAULT 1,
    author_id BIGINT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE news_comment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    news_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    content VARCHAR(500),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE activity (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    cover VARCHAR(255),
    address VARCHAR(500),
    start_time DATETIME,
    end_time DATETIME,
    sign_deadline DATETIME,
    max_count INT DEFAULT 100,
    current_count INT DEFAULT 0,
    status INT DEFAULT 0,
    organizer_id BIGINT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE activity_sign (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    activity_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    sign_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    check_time DATETIME,
    status INT DEFAULT 0
);

CREATE TABLE activity_photo (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    activity_id BIGINT NOT NULL,
    url VARCHAR(255) NOT NULL,
    description VARCHAR(200),
    upload_user_id BIGINT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE donation_project (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    target_amount DECIMAL(12,2) DEFAULT 0,
    current_amount DECIMAL(12,2) DEFAULT 0,
    cover VARCHAR(255),
    status INT DEFAULT 0,
    start_time DATETIME,
    end_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE donation_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    project_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    amount DECIMAL(12,2) NOT NULL,
    message VARCHAR(500),
    is_anonymous INT DEFAULT 0,
    certificate_no VARCHAR(100),
    status INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE alumni_company (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    name VARCHAR(200) NOT NULL,
    logo VARCHAR(255),
    industry VARCHAR(100),
    scale VARCHAR(50),
    address VARCHAR(500),
    website VARCHAR(255),
    introduction TEXT,
    status INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE job_post (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    company_id BIGINT NOT NULL,
    title VARCHAR(200) NOT NULL,
    salary VARCHAR(100),
    city VARCHAR(100),
    experience VARCHAR(50),
    education VARCHAR(50),
    description TEXT,
    contact VARCHAR(100),
    status INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE forum_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    icon VARCHAR(255),
    sort INT DEFAULT 0,
    post_count INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE forum_post (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    category_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    view_count INT DEFAULT 0,
    reply_count INT DEFAULT 0,
    like_count INT DEFAULT 0,
    is_top INT DEFAULT 0,
    is_essence INT DEFAULT 0,
    status INT DEFAULT 0,
    last_reply_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE forum_reply (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    post_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    content TEXT,
    reply_to_id BIGINT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE forum_like (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    post_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_post_user (post_id, user_id)
);

CREATE TABLE banner (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200),
    image VARCHAR(255) NOT NULL,
    url VARCHAR(255),
    sort INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE operation_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    username VARCHAR(50),
    operation VARCHAR(200),
    method VARCHAR(200),
    params TEXT,
    ip VARCHAR(50),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

