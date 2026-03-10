DROP DATABASE IF EXISTS teachres_db;
CREATE DATABASE teachres_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE teachres_db;

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

CREATE TABLE material_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    parent_id BIGINT DEFAULT 0,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) NOT NULL UNIQUE,
    sort INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE material_tag (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    color VARCHAR(20),
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE material_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    summary VARCHAR(1000),
    category_id BIGINT NOT NULL,
    grade VARCHAR(30),
    subject VARCHAR(50),
    keyword VARCHAR(255),
    uploader_id BIGINT NOT NULL,
    audit_status INT DEFAULT 0,
    publish_status INT DEFAULT 0,
    download_count INT DEFAULT 0,
    favorite_count INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE material_file (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    material_id BIGINT NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    file_path VARCHAR(500) NOT NULL,
    file_size BIGINT DEFAULT 0,
    file_type VARCHAR(50),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE material_tag_rel (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    material_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL
);

CREATE TABLE material_audit (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    material_id BIGINT NOT NULL,
    audit_status INT NOT NULL,
    audit_remark VARCHAR(500),
    auditor_id BIGINT NOT NULL,
    audit_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE material_download (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    material_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    source VARCHAR(30),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE material_favorite (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    material_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_material_user (material_id, user_id)
);

CREATE TABLE study_list (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    material_id BIGINT NOT NULL,
    progress INT DEFAULT 0,
    note VARCHAR(500),
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_study_user_material (user_id, material_id)
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
('teacher', '123456', '张老师', '13800000002', 'teacher', 1),
('student', '123456', '李同学', '13800000003', 'student', 1);

INSERT INTO material_category (parent_id, name, code, sort, status) VALUES
(0, '课件资料', 'COURSEWARE', 1, 1),
(0, '试题题库', 'QUESTION_BANK', 2, 1),
(0, '实验指导', 'LAB_GUIDE', 3, 1),
(0, '教学视频', 'VIDEO', 4, 1);

INSERT INTO material_tag (name, color, status) VALUES
('期末复习', '#409EFF', 1),
('重点难点', '#E6A23C', 1),
('实验必做', '#67C23A', 1),
('历年真题', '#F56C6C', 1);

INSERT INTO material_info (title, summary, category_id, grade, subject, keyword, uploader_id, audit_status, publish_status, download_count, favorite_count, status) VALUES
('数据结构期末复习讲义', '覆盖链表、树、图、排序核心知识点', 1, '2023级', '数据结构', '数据结构,复习,期末', 2, 1, 1, 32, 12, 1),
('Java程序设计实验指导书', '包含12个实验任务与评分标准', 3, '2024级', 'Java程序设计', 'Java,实验,指导书', 2, 1, 1, 21, 7, 1),
('数据库系统历年真题', '整理近五年数据库课程期末真题', 2, '2022级', '数据库系统', '数据库,真题,题库', 2, 0, 0, 5, 2, 1);

INSERT INTO material_file (material_id, file_name, file_path, file_size, file_type) VALUES
(1, '数据结构期末复习讲义.pdf', '/upload/material/1/review.pdf', 2536142, 'pdf'),
(2, 'Java实验指导书.docx', '/upload/material/2/lab.docx', 3215641, 'docx'),
(3, '数据库历年真题.zip', '/upload/material/3/questions.zip', 5412361, 'zip');

INSERT INTO material_tag_rel (material_id, tag_id) VALUES
(1, 1),
(1, 2),
(2, 3),
(3, 4);

INSERT INTO material_audit (material_id, audit_status, audit_remark, auditor_id, audit_time) VALUES
(1, 1, '资料完整，审核通过', 1, NOW()),
(2, 1, '实验步骤清晰，审核通过', 1, NOW()),
(3, 0, '待审核', 1, NOW());

INSERT INTO material_download (material_id, user_id, source) VALUES
(1, 3, 'web'),
(1, 3, 'web'),
(2, 3, 'web'),
(2, 3, 'web'),
(1, 2, 'web');

INSERT INTO material_favorite (material_id, user_id) VALUES
(1, 3),
(2, 3);

INSERT INTO study_list (user_id, material_id, progress, note, status) VALUES
(3, 1, 60, '本周完成树与图章节', 1),
(3, 2, 30, '完成实验一到实验三', 1);

INSERT INTO system_notice (title, content, type, status, publisher_id, publish_time, view_count) VALUES
('教学资料上传规范', '上传资料请统一命名，并附摘要说明。', 'system', 1, 1, NOW(), 15),
('期末复习资料专区上线', '已上线期末复习专区，请同学们按需下载。', 'activity', 1, 1, NOW(), 28);

INSERT INTO operation_log (user_id, module, action, content) VALUES
(1, 'material', 'audit', '审核通过: 数据结构期末复习讲义'),
(2, 'material', 'upload', '上传: Java程序设计实验指导书');
