DROP TABLE IF EXISTS award;
DROP TABLE IF EXISTS score_record;
DROP TABLE IF EXISTS score;
DROP TABLE IF EXISTS judge_assignment;
DROP TABLE IF EXISTS score_standard;
DROP TABLE IF EXISTS work;
DROP TABLE IF EXISTS notice;
DROP TABLE IF EXISTS competition;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS user;

CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50),
    avatar VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(100),
    role TINYINT DEFAULT 2,
    openid VARCHAR(100),
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    sort INT DEFAULT 0,
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE competition (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    category_id BIGINT,
    cover VARCHAR(255),
    description TEXT,
    requirement TEXT,
    start_time DATETIME,
    end_time DATETIME,
    submit_deadline DATETIME,
    status TINYINT DEFAULT 0,
    max_words INT DEFAULT 5000,
    min_words INT DEFAULT 100,
    publish_result TINYINT DEFAULT 0,
    create_user BIGINT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE work (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    competition_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    word_count INT DEFAULT 0,
    attachment VARCHAR(255),
    status TINYINT DEFAULT 0,
    reject_reason VARCHAR(500),
    final_score DECIMAL(5,2),
    rank INT,
    submit_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE score_standard (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    competition_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    max_score DECIMAL(5,2) DEFAULT 100,
    weight DECIMAL(3,2) DEFAULT 1.00,
    description VARCHAR(500),
    sort INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE judge_assignment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    competition_id BIGINT NOT NULL,
    judge_id BIGINT NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_competition_judge UNIQUE (competition_id, judge_id)
);

CREATE TABLE score (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    work_id BIGINT NOT NULL,
    judge_id BIGINT NOT NULL,
    standard_id BIGINT NOT NULL,
    score DECIMAL(5,2),
    comment VARCHAR(500),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_work_judge_standard UNIQUE (work_id, judge_id, standard_id)
);

CREATE TABLE score_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    work_id BIGINT NOT NULL,
    judge_id BIGINT NOT NULL,
    total_score DECIMAL(5,2),
    comment TEXT,
    status TINYINT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_work_judge UNIQUE (work_id, judge_id)
);

CREATE TABLE notice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    type TINYINT DEFAULT 0,
    competition_id BIGINT,
    status TINYINT DEFAULT 0,
    top TINYINT DEFAULT 0,
    create_user BIGINT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE award (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    competition_id BIGINT NOT NULL,
    work_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    award_level VARCHAR(50),
    certificate VARCHAR(255),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);
