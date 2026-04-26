INSERT INTO users (id, open_id, nickname, avatar, phone, email, role, status, score, level, bio, last_login_time, last_login_ip) VALUES
(1, 'admin_open_id', '管理员', '', '13800138000', 'admin@programming.com', 'ADMIN', 'NORMAL', 10000, 5, '系统管理员', NOW(), '127.0.0.1'),
(2, 'teacher_open_id', '张老师', '', '13800138001', 'teacher1@programming.com', 'TEACHER', 'NORMAL', 5000, 4, 'Java高级工程师，10年开发经验', NOW(), '127.0.0.1'),
(3, 'user1_open_id', '编程小白', '', '13800138002', 'user1@test.com', 'USER', 'NORMAL', 150, 2, '初学者，正在学习Java', NOW(), '127.0.0.1'),
(4, 'user2_open_id', '代码狂人', '', '13800138003', 'user2@test.com', 'USER', 'NORMAL', 800, 3, 'Full Stack Developer', NOW(), '127.0.0.1');

INSERT INTO courses (id, title, cover, description, category, level, status, teacher_id, teacher_name, student_count, chapter_count, duration, view_count, like_count, favorite_count, sort_order) VALUES
(1, 'Java零基础入门', '', '从零开始学习Java编程，适合零基础小白。', 'Java', 'BEGINNER', 'PUBLISHED', 2, '张老师', 156, 3, 120, 1234, 89, 45, 1),
(2, 'Spring Boot实战开发', '', '通过实战项目掌握Spring Boot开发技能。', 'Java', 'INTERMEDIATE', 'PUBLISHED', 2, '张老师', 98, 3, 180, 876, 67, 32, 2),
(3, '数据结构与算法', '', '系统学习常用数据结构和算法。', '算法', 'INTERMEDIATE', 'PUBLISHED', 2, '张老师', 234, 3, 200, 2345, 156, 78, 3),
(4, 'MySQL数据库入门', '', '掌握MySQL数据库基础知识和SQL语句。', '数据库', 'BEGINNER', 'PUBLISHED', 2, '张老师', 189, 2, 90, 987, 54, 28, 4);

INSERT INTO chapters (id, course_id, title, content, duration, sort_order, is_free, view_count) VALUES
(1, 1, '第1章：Java简介', '# Java简介\n\nJava是一门面向对象的编程语言。', 30, 1, TRUE, 567),
(2, 1, '第2章：环境搭建', '# 环境搭建\n\n本章介绍如何安装JDK和IDE。', 45, 2, TRUE, 489),
(3, 2, '第1章：Spring Boot简介', '# Spring Boot简介\n\n快速构建Spring应用。', 40, 1, TRUE, 330);
