DROP DATABASE IF EXISTS math_eval_db;
CREATE DATABASE math_eval_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE math_eval_db;

CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    real_name VARCHAR(50),
    phone VARCHAR(20),
    role VARCHAR(20) NOT NULL,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE course_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    parent_id BIGINT DEFAULT 0,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) NOT NULL UNIQUE,
    sort INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE math_course (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    course_name VARCHAR(120) NOT NULL,
    course_code VARCHAR(60) NOT NULL UNIQUE,
    category_id BIGINT NOT NULL,
    teacher_id BIGINT NOT NULL,
    credit DECIMAL(6,2) DEFAULT 2.00,
    term VARCHAR(50),
    description VARCHAR(1000),
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE eval_indicator (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    indicator_name VARCHAR(120) NOT NULL,
    weight DECIMAL(6,2) NOT NULL,
    sort INT DEFAULT 0,
    status INT DEFAULT 1,
    description VARCHAR(500),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE eval_task (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    task_name VARCHAR(150) NOT NULL,
    course_id BIGINT NOT NULL,
    term VARCHAR(50),
    start_time DATETIME,
    end_time DATETIME,
    status INT DEFAULT 1,
    creator_id BIGINT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE eval_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    task_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    total_score DECIMAL(6,2) NOT NULL,
    comment_content VARCHAR(1000),
    submit_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_task_student (task_id, student_id)
);

CREATE TABLE eval_record_item (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    record_id BIGINT NOT NULL,
    indicator_id BIGINT NOT NULL,
    score DECIMAL(6,2) NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE system_notice (
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

CREATE TABLE operation_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    module VARCHAR(50),
    action VARCHAR(50),
    content VARCHAR(1000),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO sys_user (username, password, real_name, phone, role, status) VALUES
('admin', '123456', '系统管理员', '13800000001', 'admin', 1),
('teacher', '123456', '王老师', '13800000002', 'teacher', 1),
('student', '123456', '张同学', '13800000003', 'student', 1),
('student2', '123456', '李同学', '13800000004', 'student', 1);

INSERT INTO course_category (parent_id, name, code, sort, status) VALUES
(0, '基础数学', 'BASIC_MATH', 1, 1),
(0, '应用数学', 'APPLIED_MATH', 2, 1),
(0, '数学建模', 'MATH_MODELING', 3, 1);

INSERT INTO math_course (course_name, course_code, category_id, teacher_id, credit, term, description, status) VALUES
('高等数学A', 'MATH101', 1, 2, 4.00, '2025-2026-2', '面向工科专业的高等数学基础课程', 1),
('线性代数', 'MATH102', 1, 2, 3.00, '2025-2026-2', '向量空间、矩阵及线性变换', 1),
('概率论与数理统计', 'MATH201', 2, 2, 3.00, '2025-2026-2', '概率模型、常见分布与统计推断', 1);

INSERT INTO eval_indicator (indicator_name, weight, sort, status, description) VALUES
('教学内容清晰度', 30.00, 1, 1, '知识点结构清晰、重点突出'),
('授课方法与节奏', 25.00, 2, 1, '讲授方式合理、节奏适中'),
('课堂互动效果', 20.00, 3, 1, '能有效引导提问与讨论'),
('作业与反馈质量', 15.00, 4, 1, '作业安排合理且反馈及时'),
('总体满意度', 10.00, 5, 1, '课程整体体验与收获');

INSERT INTO eval_task (task_name, course_id, term, start_time, end_time, status, creator_id) VALUES
('高等数学A-2025秋学期期中评价', 1, '2025-2026-2', DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_ADD(NOW(), INTERVAL 20 DAY), 1, 1),
('线性代数-2025秋学期期中评价', 2, '2025-2026-2', DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_ADD(NOW(), INTERVAL 20 DAY), 1, 1),
('概率论-2025秋学期期中评价', 3, '2025-2026-2', DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_ADD(NOW(), INTERVAL 20 DAY), 1, 1);

INSERT INTO eval_record (task_id, course_id, student_id, total_score, comment_content, submit_time) VALUES
(1, 1, 3, 91.50, '课程讲解清晰，建议增加随堂练习', DATE_SUB(NOW(), INTERVAL 2 DAY)),
(1, 1, 4, 88.00, '整体不错，希望增加难题讲评', DATE_SUB(NOW(), INTERVAL 1 DAY)),
(2, 2, 3, 86.50, '板书清晰，课堂互动可以更多', DATE_SUB(NOW(), INTERVAL 1 DAY));

INSERT INTO eval_record_item (record_id, indicator_id, score) VALUES
(1, 1, 93), (1, 2, 90), (1, 3, 92), (1, 4, 89), (1, 5, 95),
(2, 1, 90), (2, 2, 87), (2, 3, 86), (2, 4, 88), (2, 5, 89),
(3, 1, 88), (3, 2, 85), (3, 3, 84), (3, 4, 87), (3, 5, 89);

INSERT INTO system_notice (title, content, type, status, publisher_id, publish_time, view_count) VALUES
('数学课程评价系统上线通知', '请同学们在规定时间内完成课程评价，评价结果将用于教学改进。', 'system', 1, 1, NOW(), 18),
('评价填写说明', '每个指标按0-100分填写，建议附带改进建议。', 'guide', 1, 1, NOW(), 25);

INSERT INTO operation_log (user_id, module, action, content) VALUES
(1, 'task', 'create', '创建评价任务: 高等数学A-2025秋学期期中评价'),
(3, 'evaluation', 'submit', '提交课程评价: 高等数学A');
