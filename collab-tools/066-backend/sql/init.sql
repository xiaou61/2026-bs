DROP DATABASE IF EXISTS lite_blog_066;
CREATE DATABASE lite_blog_066 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE lite_blog_066;

CREATE TABLE sys_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50),
    avatar VARCHAR(255),
    email VARCHAR(100),
    role VARCHAR(20) DEFAULT 'USER',
    status INT DEFAULT 1,
    last_login_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE blog_category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    sort INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE blog_tag (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE blog_post (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    summary VARCHAR(300),
    content TEXT NOT NULL,
    cover VARCHAR(255),
    category_id BIGINT,
    author_id BIGINT NOT NULL,
    status INT DEFAULT 0,
    is_top INT DEFAULT 0,
    view_count BIGINT DEFAULT 0,
    comment_count INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_post_category(category_id),
    INDEX idx_post_author(author_id),
    INDEX idx_post_status(status)
);

CREATE TABLE blog_post_tag (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    UNIQUE KEY uk_post_tag(post_id, tag_id),
    INDEX idx_pt_post(post_id),
    INDEX idx_pt_tag(tag_id)
);

CREATE TABLE blog_comment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    content VARCHAR(500) NOT NULL,
    status INT DEFAULT 0,
    reply_content VARCHAR(500),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_comment_post(post_id),
    INDEX idx_comment_user(user_id),
    INDEX idx_comment_status(status)
);

CREATE TABLE blog_notice (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    status INT DEFAULT 1,
    admin_id BIGINT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO sys_user (username, password, nickname, email, role, status) VALUES
('admin', '123456', '博客管理员', 'admin@blog.com', 'ADMIN', 1),
('user', '123456', '普通用户', 'user@blog.com', 'USER', 1),
('writer', '123456', '作者小王', 'writer@blog.com', 'USER', 1);

INSERT INTO blog_category (name, sort, status) VALUES
('技术分享', 1, 1),
('生活随笔', 2, 1),
('项目记录', 3, 1);

INSERT INTO blog_tag (name, status) VALUES
('SpringBoot', 1),
('Vue3', 1),
('MyBatis-Plus', 1),
('日常', 1);

INSERT INTO blog_post (title, summary, content, cover, category_id, author_id, status, is_top, view_count, comment_count, create_time) VALUES
('精简博客系统搭建记录', '记录从0到1搭建博客系统的关键过程', '这是一个基于Spring Boot与Vue3实现的精简博客系统。', '', 3, 1, 1, 1, 58, 2, DATE_SUB(NOW(), INTERVAL 2 DAY)),
('SpringBoot接口统一返回实践', '使用统一返回结构提升前后端协作效率', '统一返回结构通常包含code、message、data。', '', 1, 3, 1, 0, 36, 0, DATE_SUB(NOW(), INTERVAL 1 DAY)),
('周末阅读清单', '本周阅读过的3本技术书籍整理', '分享近期阅读的技术书单与感受。', '', 2, 2, 0, 0, 5, 0, NOW());

INSERT INTO blog_post_tag (post_id, tag_id) VALUES
(1, 1), (1, 2), (1, 3),
(2, 1), (2, 3),
(3, 4);

INSERT INTO blog_comment (post_id, user_id, content, status, reply_content, create_time) VALUES
(1, 2, '这篇文章写得很清晰，感谢分享。', 1, '感谢支持，后续会补充部署部分。', DATE_SUB(NOW(), INTERVAL 1 DAY)),
(1, 3, '请问前端权限控制是怎么做的？', 1, '通过路由meta角色和后端接口双重校验。', DATE_SUB(NOW(), INTERVAL 12 HOUR)),
(2, 2, '内容不错，学到了。', 0, NULL, NOW());

INSERT INTO blog_notice (title, content, status, admin_id) VALUES
('系统升级通知', '本周日凌晨进行系统升级维护，届时可能短时不可访问。', 1, 1),
('写作活动', '本月开启技术征文活动，欢迎积极投稿。', 1, 1);
