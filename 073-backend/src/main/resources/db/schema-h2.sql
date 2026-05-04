DROP TABLE IF EXISTS announcement;
DROP TABLE IF EXISTS contract;
DROP TABLE IF EXISTS training;
DROP TABLE IF EXISTS resume;
DROP TABLE IF EXISTS recruitment;
DROP TABLE IF EXISTS salary;
DROP TABLE IF EXISTS leave_request;
DROP TABLE IF EXISTS attendance;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS position;
DROP TABLE IF EXISTS department;

CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(50),
    avatar VARCHAR(255),
    role VARCHAR(20) NOT NULL DEFAULT 'employee',
    employee_id BIGINT,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE department (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    parent_id BIGINT DEFAULT 0,
    leader_id BIGINT,
    sort INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE position (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    level INT DEFAULT 1,
    min_salary DECIMAL(10,2),
    max_salary DECIMAL(10,2),
    description TEXT,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE employee (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    emp_no VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(50) NOT NULL,
    gender VARCHAR(10),
    phone VARCHAR(20),
    email VARCHAR(100),
    id_card VARCHAR(20),
    birthday DATE,
    address VARCHAR(255),
    department_id BIGINT,
    position_id BIGINT,
    entry_date DATE,
    leave_date DATE,
    status VARCHAR(20) DEFAULT 'trial',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE attendance (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    employee_id BIGINT NOT NULL,
    date DATE NOT NULL,
    clock_in DATETIME,
    clock_out DATETIME,
    status VARCHAR(20) DEFAULT 'normal',
    remark VARCHAR(255),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE leave_request (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    employee_id BIGINT NOT NULL,
    type VARCHAR(20) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    days DECIMAL(4,1),
    reason VARCHAR(500),
    status VARCHAR(20) DEFAULT 'pending',
    approver_id BIGINT,
    approve_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE salary (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    employee_id BIGINT NOT NULL,
    year_month VARCHAR(10) NOT NULL,
    base_salary DECIMAL(10,2),
    performance DECIMAL(10,2),
    allowance DECIMAL(10,2),
    deduction DECIMAL(10,2),
    actual_salary DECIMAL(10,2),
    status VARCHAR(20) DEFAULT 'pending',
    pay_date DATE,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE recruitment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    position_id BIGINT,
    department_id BIGINT,
    count INT DEFAULT 1,
    salary_range VARCHAR(50),
    requirements TEXT,
    status VARCHAR(20) DEFAULT 'open',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE resume (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    recruitment_id BIGINT,
    name VARCHAR(50) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100),
    education VARCHAR(50),
    experience TEXT,
    status VARCHAR(20) DEFAULT 'pending',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE training (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    trainer VARCHAR(50),
    start_time DATETIME,
    end_time DATETIME,
    location VARCHAR(200),
    max_count INT DEFAULT 50,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE contract (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    employee_id BIGINT NOT NULL,
    contract_no VARCHAR(50),
    type VARCHAR(50),
    start_date DATE,
    end_date DATE,
    status VARCHAR(20) DEFAULT 'active',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE announcement (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    publisher_id BIGINT,
    is_top INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);
