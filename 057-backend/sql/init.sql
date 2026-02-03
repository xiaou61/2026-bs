DROP DATABASE IF EXISTS enrollment_db;
CREATE DATABASE enrollment_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE enrollment_db;

CREATE TABLE admin (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(50),
    phone VARCHAR(20),
    role VARCHAR(20) DEFAULT 'admin',
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
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
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
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
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
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
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
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

INSERT INTO admin (username, password, name, phone, role, status) VALUES
('admin', '123456', '超级管理员', '13800000001', 'super_admin', 1),
('user', '123456', '普通管理员', '13800000002', 'admin', 1);

INSERT INTO department (name, code, leader, phone, description, sort, status) VALUES
('计算机学院', 'CS', '张院长', '010-12345001', '培养计算机专业人才', 1, 1),
('电子信息学院', 'EE', '李院长', '010-12345002', '培养电子信息专业人才', 2, 1),
('经济管理学院', 'EM', '王院长', '010-12345003', '培养经济管理专业人才', 3, 1),
('外国语学院', 'FL', '赵院长', '010-12345004', '培养外语专业人才', 4, 1),
('机械工程学院', 'ME', '刘院长', '010-12345005', '培养机械工程专业人才', 5, 1);

INSERT INTO major (department_id, name, code, degree, duration, tuition, description, requirement, status) VALUES
(1, '计算机科学与技术', 'CS001', '本科', 4, 5500.00, '培养具有良好的科学素养的计算机专业人才', '数学成绩优秀', 1),
(1, '软件工程', 'CS002', '本科', 4, 6000.00, '培养软件开发与管理人才', '逻辑思维能力强', 1),
(1, '人工智能', 'CS003', '本科', 4, 6500.00, '培养AI领域专业人才', '数学物理基础好', 1),
(2, '电子信息工程', 'EE001', '本科', 4, 5500.00, '培养电子信息领域工程技术人才', '物理成绩优秀', 1),
(2, '通信工程', 'EE002', '本科', 4, 5500.00, '培养通信领域专业人才', '数学物理基础好', 1),
(3, '工商管理', 'EM001', '本科', 4, 5000.00, '培养企业管理人才', '综合素质高', 1),
(3, '会计学', 'EM002', '本科', 4, 5000.00, '培养财务会计专业人才', '数学成绩良好', 1),
(4, '英语', 'FL001', '本科', 4, 4500.00, '培养英语专业人才', '英语成绩优秀', 1),
(5, '机械设计制造及自动化', 'ME001', '本科', 4, 5500.00, '培养机械工程领域人才', '物理成绩优秀', 1);

INSERT INTO enrollment_plan (year, major_id, plan_count, actual_count, min_score, status) VALUES
(2024, 1, 120, 115, 550, 1),
(2024, 2, 100, 98, 545, 1),
(2024, 3, 60, 58, 560, 1),
(2024, 4, 80, 78, 540, 1),
(2024, 5, 80, 75, 535, 1),
(2024, 6, 100, 95, 520, 1),
(2024, 7, 120, 118, 530, 1),
(2024, 8, 60, 55, 540, 1),
(2024, 9, 80, 76, 530, 1),
(2025, 1, 130, 0, NULL, 1),
(2025, 2, 110, 0, NULL, 1),
(2025, 3, 80, 0, NULL, 1);

INSERT INTO student (exam_no, name, gender, id_card, birthday, phone, email, province, city, address, high_school, status) VALUES
('2024010001', '张三', 1, '110101200601010011', '2006-01-01', '13900000001', 'zhangsan@email.com', '北京', '北京', '朝阳区XX路', '北京一中', 1),
('2024010002', '李四', 1, '110101200602020022', '2006-02-02', '13900000002', 'lisi@email.com', '北京', '北京', '海淀区XX路', '北京二中', 1),
('2024010003', '王五', 0, '310101200603030033', '2006-03-03', '13900000003', 'wangwu@email.com', '上海', '上海', '浦东新区XX路', '上海中学', 1),
('2024010004', '赵六', 1, '440101200604040044', '2006-04-04', '13900000004', 'zhaoliu@email.com', '广东', '广州', '天河区XX路', '广州一中', 2),
('2024010005', '钱七', 0, '330101200605050055', '2006-05-05', '13900000005', 'qianqi@email.com', '浙江', '杭州', '西湖区XX路', '杭州二中', 2),
('2024010006', '孙八', 1, '320101200606060066', '2006-06-06', '13900000006', 'sunba@email.com', '江苏', '南京', '鼓楼区XX路', '南京一中', 1),
('2024010007', '周九', 0, '510101200607070077', '2006-07-07', '13900000007', 'zhoujiu@email.com', '四川', '成都', '武侯区XX路', '成都七中', 0),
('2024010008', '吴十', 1, '420101200608080088', '2006-08-08', '13900000008', 'wushi@email.com', '湖北', '武汉', '武昌区XX路', '华师一附中', 0);

INSERT INTO application (student_id, plan_id, first_major_id, second_major_id, adjust, status) VALUES
(1, 1, 1, 2, 1, 1),
(2, 2, 2, 1, 1, 1),
(3, 3, 3, 1, 0, 1),
(4, 1, 1, 3, 1, 1),
(5, 4, 4, 5, 1, 1),
(6, 6, 6, 7, 0, 0);

INSERT INTO score (student_id, year, chinese, math, english, comprehensive, total_score) VALUES
(1, 2024, 125, 140, 135, 260, 660),
(2, 2024, 118, 145, 130, 255, 648),
(3, 2024, 130, 138, 142, 270, 680),
(4, 2024, 115, 142, 128, 250, 635),
(5, 2024, 120, 135, 140, 245, 640),
(6, 2024, 110, 130, 125, 240, 605);

INSERT INTO admission (student_id, plan_id, major_id, score, admission_no, status, admit_time) VALUES
(1, 1, 1, 660, 'AD2024001', 1, '2024-08-01 10:00:00'),
(2, 2, 2, 648, 'AD2024002', 1, '2024-08-01 10:00:00'),
(3, 3, 3, 680, 'AD2024003', 1, '2024-08-01 10:00:00'),
(4, 1, 1, 635, 'AD2024004', 0, '2024-08-02 10:00:00'),
(5, 4, 4, 640, 'AD2024005', 1, '2024-08-02 10:00:00');

INSERT INTO notice (title, content, type, top, status, publish_time, admin_id) VALUES
('2024年招生简章', '我校2024年招生工作正式启动，欢迎广大考生报考...', 1, 1, 1, '2024-06-01 09:00:00', 1),
('关于2024年招生录取工作的通知', '根据教育部相关规定，现将2024年招生录取工作安排通知如下...', 2, 0, 1, '2024-07-01 09:00:00', 1),
('2024年各专业录取分数线公布', '经招生委员会研究决定，现公布2024年各专业录取分数线...', 3, 1, 1, '2024-08-01 09:00:00', 1),
('新生报到须知', '2024级新生报到时间为9月1日至9月3日，请携带以下材料...', 2, 0, 1, '2024-08-15 09:00:00', 1);

INSERT INTO score_line (year, major_id, province, category, batch, score) VALUES
(2024, 1, '北京', '理科', '一本', 550),
(2024, 1, '上海', '理科', '一本', 555),
(2024, 1, '广东', '理科', '一本', 548),
(2024, 2, '北京', '理科', '一本', 545),
(2024, 2, '上海', '理科', '一本', 550),
(2024, 3, '北京', '理科', '一本', 560),
(2024, 4, '北京', '理科', '一本', 540),
(2024, 5, '北京', '理科', '一本', 535),
(2024, 6, '北京', '文科', '一本', 520),
(2024, 7, '北京', '文科', '一本', 530),
(2024, 8, '北京', '文科', '一本', 540);
