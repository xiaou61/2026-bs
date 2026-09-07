DROP TABLE IF EXISTS score_line;
DROP TABLE IF EXISTS notice;
DROP TABLE IF EXISTS admission;
DROP TABLE IF EXISTS score;
DROP TABLE IF EXISTS application;
DROP TABLE IF EXISTS student;
DROP TABLE IF EXISTS enrollment_plan;
DROP TABLE IF EXISTS major;
DROP TABLE IF EXISTS department;
DROP TABLE IF EXISTS admin;

CREATE TABLE admin (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(50),
    phone VARCHAR(20),
    role VARCHAR(20) DEFAULT 'admin',
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE department (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50),
    leader VARCHAR(50),
    phone VARCHAR(20),
    description TEXT,
    sort INT DEFAULT 0,
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE major (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    department_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50),
    degree VARCHAR(20),
    duration INT DEFAULT 4,
    tuition DECIMAL(10,2),
    description TEXT,
    requirement TEXT,
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE enrollment_plan (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    year INT NOT NULL,
    major_id BIGINT NOT NULL,
    plan_count INT DEFAULT 0,
    actual_count INT DEFAULT 0,
    min_score INT,
    status TINYINT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE student (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    exam_no VARCHAR(50),
    name VARCHAR(50) NOT NULL,
    gender TINYINT DEFAULT 1,
    id_card VARCHAR(20),
    birthday DATE,
    phone VARCHAR(20),
    email VARCHAR(100),
    province VARCHAR(50),
    city VARCHAR(50),
    address VARCHAR(200),
    high_school VARCHAR(100),
    photo VARCHAR(200),
    status TINYINT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE application (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_id BIGINT NOT NULL,
    plan_id BIGINT NOT NULL,
    first_major_id BIGINT,
    second_major_id BIGINT,
    adjust TINYINT DEFAULT 0,
    status TINYINT DEFAULT 0,
    remark VARCHAR(500),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE score (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_id BIGINT NOT NULL,
    year INT NOT NULL,
    chinese INT,
    math INT,
    english INT,
    comprehensive INT,
    total_score INT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE admission (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_id BIGINT NOT NULL,
    plan_id BIGINT,
    major_id BIGINT,
    score INT,
    admission_no VARCHAR(50),
    status TINYINT DEFAULT 0,
    admit_time DATETIME,
    confirm_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE notice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    type TINYINT DEFAULT 1,
    top TINYINT DEFAULT 0,
    status TINYINT DEFAULT 0,
    publish_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    admin_id BIGINT
);

CREATE TABLE score_line (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    year INT NOT NULL,
    major_id BIGINT,
    province VARCHAR(50),
    category VARCHAR(20),
    batch VARCHAR(20),
    score INT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);
