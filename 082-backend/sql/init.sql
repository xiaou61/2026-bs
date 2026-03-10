DROP DATABASE IF EXISTS gongkao_db;
CREATE DATABASE gongkao_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE gongkao_db;

CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    real_name VARCHAR(50),
    phone VARCHAR(20),
    role VARCHAR(20) NOT NULL,
    avatar VARCHAR(255),
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE subject (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(500),
    sort INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
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
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE course_chapter (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    course_id BIGINT NOT NULL,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    video_url VARCHAR(255),
    sort INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE question_bank (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    subject_id BIGINT NOT NULL,
    name VARCHAR(200) NOT NULL,
    type VARCHAR(30) NOT NULL,
    total_count INT DEFAULT 0,
    difficulty VARCHAR(30),
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE question (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    bank_id BIGINT NOT NULL,
    subject_id BIGINT NOT NULL,
    type VARCHAR(20) NOT NULL,
    stem TEXT NOT NULL,
    option_a VARCHAR(500),
    option_b VARCHAR(500),
    option_c VARCHAR(500),
    option_d VARCHAR(500),
    answer VARCHAR(200) NOT NULL,
    analysis TEXT,
    score DECIMAL(10,2) DEFAULT 1.00,
    difficulty VARCHAR(30),
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
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
    start_time DATETIME,
    end_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
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
    start_time DATETIME,
    submit_time DATETIME,
    duration_seconds INT DEFAULT 0,
    total_score DECIMAL(10,2) DEFAULT 0,
    objective_score DECIMAL(10,2) DEFAULT 0,
    subjective_score DECIMAL(10,2) DEFAULT 0,
    pass_status INT DEFAULT 0,
    status VARCHAR(30) DEFAULT 'submitted',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
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
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
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
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE notice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    type VARCHAR(30),
    status INT DEFAULT 0,
    publisher_id BIGINT,
    publish_time DATETIME,
    view_count INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO user (username, password, real_name, phone, role, status) VALUES
('admin', '123456', '系统管理员', '13800000001', 'admin', 1),
('teacher', '123456', '张老师', '13800000002', 'teacher', 1),
('user', '123456', '李同学', '13800000003', 'student', 1);

INSERT INTO subject (name, code, description, sort, status) VALUES
('行政职业能力测验', 'XINGCE', '行测模块题型训练', 1, 1),
('申论', 'SHENLUN', '申论阅读与写作能力提升', 2, 1),
('面试', 'MIANSHI', '结构化面试与无领导训练', 3, 1);

INSERT INTO course (subject_id, title, teacher_id, level, summary, study_hours, status) VALUES
(1, '行测言语理解高频考点', 2, '中级', '覆盖逻辑填空、片段阅读核心技巧', 24, 1),
(1, '行测判断推理提分课', 2, '高级', '图形推理、定义判断专项训练', 30, 1),
(2, '申论大作文写作训练营', 2, '中级', '从立意到结构的完整写作方法', 20, 1);

INSERT INTO course_chapter (course_id, title, content, sort, status) VALUES
(1, '词语辨析基础', '掌握高频近义词辨析方法', 1, 1),
(1, '片段阅读主旨题', '快速定位中心句与干扰项排除', 2, 1),
(3, '申论审题与立意', '建立材料阅读与观点提炼框架', 1, 1);

INSERT INTO question_bank (subject_id, name, type, total_count, difficulty, status) VALUES
(1, '行测单选题库A', 'single', 3, '中等', 1),
(2, '申论判断题库', 'judge', 1, '基础', 1);

INSERT INTO question (bank_id, subject_id, type, stem, option_a, option_b, option_c, option_d, answer, analysis, score, difficulty, status) VALUES
(1, 1, 'single', '下列词语中没有错别字的是？', '融会贯通', '再接再励', '迫不急待', '一愁莫展', 'A', 'B应为再接再厉，C应为迫不及待，D应为一筹莫展', 2.00, '基础', 1),
(1, 1, 'single', '某次数列规律推理题答案是？', '12', '15', '18', '21', 'C', '按等差递增规律得到18', 2.00, '中等', 1),
(1, 1, 'single', '逻辑判断：若所有A是B，部分B是C，则可推出？', '所有A是C', '部分A是C', '无法必然推出', '所有C是A', 'C', '前提不足以推出A与C关系', 2.00, '中等', 1),
(2, 2, 'judge', '申论大作文只需要观点正确，不需要结构。', NULL, NULL, NULL, NULL, '0', '申论写作对结构完整性要求很高', 1.00, '基础', 1);

INSERT INTO exam_paper (subject_id, title, duration_minutes, total_score, pass_score, question_count, publish_status, start_time, end_time) VALUES
(1, '行测模拟卷一', 120, 100, 60, 3, 1, '2026-03-01 08:00:00', '2026-04-01 23:59:59'),
(2, '申论基础测评卷', 150, 100, 60, 1, 1, '2026-03-01 08:00:00', '2026-04-01 23:59:59');

INSERT INTO exam_paper_question (paper_id, question_id, sort, score) VALUES
(1, 1, 1, 30),
(1, 2, 2, 35),
(1, 3, 3, 35),
(2, 4, 1, 100);

INSERT INTO exam_record (paper_id, user_id, start_time, submit_time, duration_seconds, total_score, objective_score, subjective_score, pass_status, status) VALUES
(1, 3, '2026-03-02 09:00:00', '2026-03-02 10:30:00', 5400, 72, 72, 0, 1, 'submitted'),
(2, 3, '2026-03-03 09:00:00', '2026-03-03 10:40:00', 6000, 58, 58, 0, 0, 'submitted');

INSERT INTO exam_record_answer (record_id, paper_id, question_id, user_answer, correct_answer, score, is_correct) VALUES
(1, 1, 1, 'A', 'A', 30, 1),
(1, 1, 2, 'C', 'C', 35, 1),
(1, 1, 3, 'B', 'C', 7, 0),
(2, 2, 4, '1', '0', 0, 0);

INSERT INTO study_plan (user_id, subject_id, title, start_date, end_date, daily_target, completed_days, status) VALUES
(3, 1, '30天行测冲刺计划', '2026-03-01', '2026-03-30', '每天完成60题并复盘错题', 6, 1),
(3, 2, '申论写作提升计划', '2026-03-01', '2026-03-31', '每周2篇大作文练习', 4, 1);

INSERT INTO notice (title, content, type, status, publisher_id, publish_time, view_count) VALUES
('3月模考安排通知', '本月每周六晚8点统一进行线上模考，请提前15分钟入场。', 'exam', 1, 1, NOW(), 56),
('申论批改服务上线', '申论作业提交后48小时内完成批改反馈。', 'service', 1, 1, NOW(), 33);
