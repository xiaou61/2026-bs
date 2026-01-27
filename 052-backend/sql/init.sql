CREATE DATABASE IF NOT EXISTS online_course DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_general_ci;

USE online_course;

DROP TABLE IF EXISTS user;
CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50),
    avatar VARCHAR(255),
    email VARCHAR(100),
    phone VARCHAR(20),
    role TINYINT DEFAULT 0 COMMENT '0学生1教师2管理员',
    status TINYINT DEFAULT 1 COMMENT '0禁用1正常',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS category;
CREATE TABLE category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    icon VARCHAR(255),
    sort INT DEFAULT 0,
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS course;
CREATE TABLE course (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    cover VARCHAR(255),
    description TEXT,
    category_id BIGINT,
    teacher_id BIGINT,
    price DECIMAL(10,2) DEFAULT 0,
    is_free TINYINT DEFAULT 1,
    learn_count INT DEFAULT 0,
    chapter_count INT DEFAULT 0,
    duration INT DEFAULT 0 COMMENT '总时长秒',
    score DECIMAL(3,1) DEFAULT 5.0,
    status TINYINT DEFAULT 1 COMMENT '0下架1上架',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS chapter;
CREATE TABLE chapter (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    course_id BIGINT NOT NULL,
    title VARCHAR(100) NOT NULL,
    sort INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS video;
CREATE TABLE video (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    chapter_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    title VARCHAR(100) NOT NULL,
    url VARCHAR(500),
    duration INT DEFAULT 0,
    sort INT DEFAULT 0,
    is_free TINYINT DEFAULT 0 COMMENT '是否免费试看',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS learn_record;
CREATE TABLE learn_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    video_id BIGINT,
    progress INT DEFAULT 0 COMMENT '当前视频进度秒',
    duration INT DEFAULT 0 COMMENT '已学总时长秒',
    finished_count INT DEFAULT 0,
    last_learn_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_user_course (user_id, course_id)
);

DROP TABLE IF EXISTS comment;
CREATE TABLE comment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    content TEXT,
    score INT DEFAULT 5 COMMENT '评分1-5',
    status TINYINT DEFAULT 1 COMMENT '0待审1通过2拒绝',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS favorite;
CREATE TABLE favorite (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_user_course (user_id, course_id)
);

DROP TABLE IF EXISTS banner;
CREATE TABLE banner (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100),
    image VARCHAR(255),
    url VARCHAR(255),
    sort INT DEFAULT 0,
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO user (username, password, nickname, role) VALUES
('admin', 'e10adc3949ba59abbe56e057f20f883e', '管理员', 2),
('teacher', 'e10adc3949ba59abbe56e057f20f883e', '张老师', 1),
('student', 'e10adc3949ba59abbe56e057f20f883e', '测试学生', 0);

INSERT INTO category (name, icon, sort) VALUES
('前端开发', 'icon-frontend', 1),
('后端开发', 'icon-backend', 2),
('移动开发', 'icon-mobile', 3),
('数据库', 'icon-database', 4),
('人工智能', 'icon-ai', 5);

INSERT INTO course (title, cover, description, category_id, teacher_id, is_free, learn_count, chapter_count, duration, score) VALUES
('Vue3从入门到精通', '/covers/vue3.jpg', 'Vue3全家桶完整教程，包含Composition API、Pinia、Vue Router等', 1, 2, 1, 1280, 8, 28800, 4.8),
('Spring Boot实战开发', '/covers/springboot.jpg', 'Spring Boot企业级项目实战，从零搭建完整项目', 2, 2, 1, 2560, 10, 36000, 4.9),
('React18核心技术', '/covers/react.jpg', 'React18新特性详解，Hooks深入讲解', 1, 2, 1, 980, 6, 21600, 4.7),
('MySQL数据库入门', '/covers/mysql.jpg', 'MySQL数据库从入门到精通', 4, 2, 1, 3200, 5, 18000, 4.6),
('Python机器学习', '/covers/python-ml.jpg', 'Python机器学习实战，掌握sklearn和pytorch', 5, 2, 0, 680, 12, 43200, 4.8);

INSERT INTO chapter (course_id, title, sort) VALUES
(1, '第一章 Vue3基础入门', 1),
(1, '第二章 组合式API详解', 2),
(1, '第三章 Vue Router路由', 3),
(1, '第四章 Pinia状态管理', 4),
(2, '第一章 Spring Boot概述', 1),
(2, '第二章 Web开发基础', 2),
(2, '第三章 数据库整合', 3),
(2, '第四章 安全框架', 4);

INSERT INTO video (chapter_id, course_id, title, url, duration, sort, is_free) VALUES
(1, 1, '1.1 Vue3简介与环境搭建', '/videos/vue3/1-1.mp4', 900, 1, 1),
(1, 1, '1.2 创建第一个Vue3项目', '/videos/vue3/1-2.mp4', 1200, 2, 1),
(1, 1, '1.3 模板语法基础', '/videos/vue3/1-3.mp4', 1500, 3, 0),
(2, 1, '2.1 setup函数详解', '/videos/vue3/2-1.mp4', 1800, 1, 0),
(2, 1, '2.2 ref和reactive', '/videos/vue3/2-2.mp4', 1200, 2, 0),
(5, 2, '1.1 Spring Boot概述', '/videos/springboot/1-1.mp4', 900, 1, 1),
(5, 2, '1.2 快速开始', '/videos/springboot/1-2.mp4', 1500, 2, 1),
(6, 2, '2.1 Controller详解', '/videos/springboot/2-1.mp4', 1800, 1, 0);

INSERT INTO banner (title, image, url, sort) VALUES
('Vue3全栈开发课程上线', '/banners/banner1.jpg', '/course/1', 1),
('Spring Boot实战特惠', '/banners/banner2.jpg', '/course/2', 2),
('新用户专享福利', '/banners/banner3.jpg', '/activity', 3);

INSERT INTO comment (user_id, course_id, content, score) VALUES
(3, 1, '讲解非常清晰，适合入门学习！', 5),
(3, 2, '课程内容很实用，学到了很多', 5);
