DROP DATABASE IF EXISTS academic_integrity_100;
CREATE DATABASE academic_integrity_100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE academic_integrity_100;

CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(80) NOT NULL,
    role VARCHAR(30) NOT NULL,
    department VARCHAR(100),
    phone VARCHAR(30),
    email VARCHAR(120),
    status TINYINT DEFAULT 1,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE course_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    course_name VARCHAR(120) NOT NULL,
    course_code VARCHAR(60) NOT NULL,
    teacher_name VARCHAR(80),
    semester VARCHAR(40),
    description VARCHAR(500),
    status TINYINT DEFAULT 1,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE class_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    class_name VARCHAR(120) NOT NULL,
    major VARCHAR(120),
    grade VARCHAR(40),
    teacher_name VARCHAR(80),
    student_count INT DEFAULT 0,
    status TINYINT DEFAULT 1,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE student_profile (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_no VARCHAR(60) NOT NULL,
    name VARCHAR(80) NOT NULL,
    class_id BIGINT,
    major VARCHAR(120),
    grade VARCHAR(40),
    phone VARCHAR(30),
    email VARCHAR(120),
    status TINYINT DEFAULT 1,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE assignment_task (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    course_id BIGINT NOT NULL,
    title VARCHAR(180) NOT NULL,
    assignment_type VARCHAR(40),
    teacher_id BIGINT,
    deadline DATETIME,
    threshold_score DECIMAL(6,2) DEFAULT 75.00,
    description VARCHAR(800),
    status TINYINT DEFAULT 1,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE text_submission (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    assignment_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    title VARCHAR(180) NOT NULL,
    content TEXT NOT NULL,
    citation_note VARCHAR(1000),
    attachment_url VARCHAR(255),
    word_count INT DEFAULT 0,
    status TINYINT DEFAULT 0,
    submit_time DATETIME,
    update_time DATETIME
);

CREATE TABLE detection_rule (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    rule_name VARCHAR(120) NOT NULL,
    rule_type VARCHAR(50),
    risk_level VARCHAR(20),
    weight DECIMAL(6,2) DEFAULT 1.00,
    keywords VARCHAR(1000),
    description VARCHAR(800),
    status TINYINT DEFAULT 1,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE detection_task (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    task_no VARCHAR(80) NOT NULL,
    submission_id BIGINT NOT NULL,
    task_name VARCHAR(160) NOT NULL,
    priority VARCHAR(20),
    status TINYINT DEFAULT 0,
    reviewer_id BIGINT,
    create_time DATETIME,
    finish_time DATETIME
);

CREATE TABLE detection_result (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    task_id BIGINT NOT NULL,
    submission_id BIGINT NOT NULL,
    matched_rules VARCHAR(1000),
    ai_probability DECIMAL(6,2),
    repeat_rate DECIMAL(6,2),
    citation_risk DECIMAL(6,2),
    risk_level VARCHAR(20),
    score DECIMAL(6,2),
    conclusion VARCHAR(800),
    suggestion VARCHAR(800),
    review_status TINYINT DEFAULT 0,
    review_comment VARCHAR(800),
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE integrity_warning (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    result_id BIGINT,
    student_id BIGINT,
    warning_level VARCHAR(20),
    warning_title VARCHAR(160),
    warning_content VARCHAR(1000),
    status TINYINT DEFAULT 0,
    handler_id BIGINT,
    handle_comment VARCHAR(800),
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE rectification_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    warning_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    revision_note VARCHAR(1000),
    revision_url VARCHAR(255),
    status TINYINT DEFAULT 0,
    review_comment VARCHAR(800),
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE appeal_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    target_type VARCHAR(50),
    target_id BIGINT,
    student_id BIGINT,
    reason VARCHAR(1000),
    status TINYINT DEFAULT 0,
    handle_comment VARCHAR(800),
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE operation_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    username VARCHAR(80),
    role VARCHAR(30),
    module_name VARCHAR(80),
    action_type VARCHAR(50),
    description VARCHAR(500),
    create_time DATETIME
);

INSERT INTO sys_user(username, password, nickname, role, department, phone, email, status, create_time, update_time) VALUES
('admin', '123456', '系统管理员', 'ADMIN', '教务处', '13800000001', 'admin@school.edu', 1, NOW(), NOW()),
('teacher', '123456', '课程教师', 'TEACHER', '计算机学院', '13800000002', 'teacher@school.edu', 1, NOW(), NOW()),
('student', '123456', '学生用户', 'STUDENT', '软件工程', '13800000003', 'student@school.edu', 1, NOW(), NOW()),
('reviewer', '123456', '诚信复核员', 'REVIEWER', '学术诚信中心', '13800000004', 'reviewer@school.edu', 1, NOW(), NOW());

INSERT INTO course_info(course_name, course_code, teacher_name, semester, description, status, create_time, update_time) VALUES
('软件工程课程设计', 'SE2026', '课程教师', '2025-2026-2', '课程作业、论文初稿和实验报告统一检测', 1, NOW(), NOW()),
('毕业论文写作指导', 'THESIS2026', '课程教师', '2025-2026-2', '毕业论文初稿诚信检测与整改跟踪', 1, NOW(), NOW());

INSERT INTO class_info(class_name, major, grade, teacher_name, student_count, status, create_time, update_time) VALUES
('软件工程2201班', '软件工程', '2022级', '课程教师', 42, 1, NOW(), NOW()),
('计算机科学2202班', '计算机科学与技术', '2022级', '课程教师', 38, 1, NOW(), NOW());

INSERT INTO student_profile(student_no, name, class_id, major, grade, phone, email, status, create_time, update_time) VALUES
('2022010101', '李同学', 1, '软件工程', '2022级', '13900000001', 'li@student.edu', 1, NOW(), NOW()),
('2022010102', '王同学', 1, '软件工程', '2022级', '13900000002', 'wang@student.edu', 1, NOW(), NOW());

INSERT INTO assignment_task(course_id, title, assignment_type, teacher_id, deadline, threshold_score, description, status, create_time, update_time) VALUES
(1, '软件工程课程论文', '课程论文', 2, DATE_ADD(NOW(), INTERVAL 14 DAY), 75.00, '提交不少于3000字课程论文，需保留引用来源', 1, NOW(), NOW()),
(2, '毕业论文第一章初稿', '论文初稿', 2, DATE_ADD(NOW(), INTERVAL 21 DAY), 70.00, '提交研究背景、国内外现状和主要研究内容', 1, NOW(), NOW());

INSERT INTO text_submission(assignment_id, student_id, title, content, citation_note, attachment_url, word_count, status, submit_time, update_time) VALUES
(1, 1, '软件工程质量保障研究', '本文旨在从多维度分析软件工程质量保障体系。首先，需求分析是项目成功的重要基础。其次，测试管理能够显著提升软件质量。最后，过程改进对于团队协作具有重要意义。综上所述，系统性质量管理能够显著提升项目交付效果。', '', 'https://example.com/submission/1.docx', 108, 0, NOW(), NOW()),
(2, 2, '生成式人工智能对教学评价的影响', '本文通过课堂观察和问卷调查分析生成式人工智能对教学评价的影响，并结合三篇参考文献讨论学生学习行为变化。', '参考文献来自CNKI三篇论文和课程教材', 'https://example.com/submission/2.docx', 58, 1, NOW(), NOW());

INSERT INTO detection_rule(rule_name, rule_type, risk_level, weight, keywords, description, status, create_time, update_time) VALUES
('AI模板化表达', 'AI特征', '高', 2.20, '本文旨在,综上所述,多维度,系统性,显著提升', '识别生成式文本常见模板表达', 1, NOW(), NOW()),
('引用缺失风险', '引用规范', '高', 1.80, '参考文献缺失,未注明来源,引用不足', '识别引用说明缺失或来源不足', 1, NOW(), NOW()),
('重复空泛表达', '重复片段', '中', 1.20, '重要意义,实践价值,研究意义,优化路径', '识别重复使用的空泛论述', 1, NOW(), NOW());

INSERT INTO detection_task(task_no, submission_id, task_name, priority, status, reviewer_id, create_time, finish_time) VALUES
('DT100001', 1, '软件工程质量保障研究检测', '高', 0, 4, NOW(), NULL),
('DT100002', 2, '生成式人工智能教学评价检测', '普通', 2, 4, NOW(), NOW());

INSERT INTO detection_result(task_id, submission_id, matched_rules, ai_probability, repeat_rate, citation_risk, risk_level, score, conclusion, suggestion, review_status, review_comment, create_time, update_time) VALUES
(2, 2, '未命中高风险规则', 32.00, 8.00, 20.00, '低', 80.20, '综合风险低，AI概率32.00%，重复率8.00%，引用风险20.00%', '建议归档保存，保留抽检记录', 1, '复核通过', NOW(), NOW());

INSERT INTO integrity_warning(result_id, student_id, warning_level, warning_title, warning_content, status, handler_id, handle_comment, create_time, update_time) VALUES
(NULL, 1, '中', '引用说明不足预警', '提交文本引用说明为空，需要补充参考来源', 0, NULL, NULL, NOW(), NOW());

INSERT INTO rectification_record(warning_id, student_id, revision_note, revision_url, status, review_comment, create_time, update_time) VALUES
(1, 1, '已补充参考文献和课堂笔记来源，调整重复段落', 'https://example.com/revision/1.docx', 0, NULL, NOW(), NOW());

INSERT INTO appeal_record(target_type, target_id, student_id, reason, status, handle_comment, create_time, update_time) VALUES
('诚信预警', 1, 1, '该段落来自课堂笔记整理，并非AI直接生成，希望复核', 0, NULL, NOW(), NOW());

INSERT INTO operation_log(user_id, username, role, module_name, action_type, description, create_time) VALUES
(1, 'admin', 'ADMIN', '系统初始化', '初始化', '初始化100号学术诚信检测系统演示数据', NOW());
