INSERT INTO user (id, username, password, nickname, role, status, email, phone) VALUES
(1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '管理员', 2, 1, 'admin@example.com', '13800000000'),
(2, 'teacher', 'e10adc3949ba59abbe56e057f20f883e', '张老师', 1, 1, 'teacher@example.com', '13800000001'),
(3, 'student', 'e10adc3949ba59abbe56e057f20f883e', '测试学生', 0, 1, 'student@example.com', '13800000002');

INSERT INTO category (id, name, icon, sort, status) VALUES
(1, '前端开发', 'icon-frontend', 1, 1),
(2, '后端开发', 'icon-backend', 2, 1),
(3, '移动开发', 'icon-mobile', 3, 1),
(4, '数据库', 'icon-database', 4, 1),
(5, '人工智能', 'icon-ai', 5, 1);

INSERT INTO course (id, title, cover, description, category_id, teacher_id, is_free, learn_count, chapter_count, duration, score, status) VALUES
(1, 'Vue3从入门到精通', '', 'Vue3全家桶完整教程，包含 Composition API、Pinia、Vue Router 等。', 1, 2, 1, 1280, 4, 28800, 4.8, 1),
(2, 'Spring Boot实战开发', '', 'Spring Boot 企业级项目实战，从零搭建完整项目。', 2, 2, 1, 2560, 4, 36000, 4.9, 1),
(3, 'React18核心技术', '', 'React18 新特性详解，Hooks 深入讲解。', 1, 2, 1, 980, 2, 21600, 4.7, 1),
(4, 'MySQL数据库入门', '', 'MySQL 数据库从入门到精通。', 4, 2, 1, 3200, 2, 18000, 4.6, 1),
(5, 'Python机器学习', '', 'Python 机器学习实战，掌握 sklearn 和 pytorch。', 5, 2, 0, 680, 2, 43200, 4.8, 1);

INSERT INTO chapter (id, course_id, title, sort) VALUES
(1, 1, '第一章 Vue3基础入门', 1),
(2, 1, '第二章 组合式 API 详解', 2),
(3, 1, '第三章 Vue Router 路由', 3),
(4, 1, '第四章 Pinia 状态管理', 4),
(5, 2, '第一章 Spring Boot 概述', 1),
(6, 2, '第二章 Web 开发基础', 2),
(7, 2, '第三章 数据库整合', 3),
(8, 2, '第四章 安全框架', 4);

INSERT INTO video (id, chapter_id, course_id, title, url, duration, sort, is_free) VALUES
(1, 1, 1, '1.1 Vue3简介与环境搭建', '/videos/vue3/1-1.mp4', 900, 1, 1),
(2, 1, 1, '1.2 创建第一个 Vue3 项目', '/videos/vue3/1-2.mp4', 1200, 2, 1),
(3, 1, 1, '1.3 模板语法基础', '/videos/vue3/1-3.mp4', 1500, 3, 0),
(4, 2, 1, '2.1 setup 函数详解', '/videos/vue3/2-1.mp4', 1800, 1, 0),
(5, 2, 1, '2.2 ref 和 reactive', '/videos/vue3/2-2.mp4', 1200, 2, 0),
(6, 5, 2, '1.1 Spring Boot 概述', '/videos/springboot/1-1.mp4', 900, 1, 1),
(7, 5, 2, '1.2 快速开始', '/videos/springboot/1-2.mp4', 1500, 2, 1),
(8, 6, 2, '2.1 Controller 详解', '/videos/springboot/2-1.mp4', 1800, 1, 0);

INSERT INTO banner (id, title, image, url, sort, status) VALUES
(1, 'Vue3全栈开发课程上线', '', '/course/1', 1, 1),
(2, 'Spring Boot实战特惠', '', '/course/2', 2, 1),
(3, '新用户专享福利', '', '/activity', 3, 1);

INSERT INTO comment (id, user_id, course_id, content, score, status) VALUES
(1, 3, 1, '讲解非常清晰，适合入门学习！', 5, 1),
(2, 3, 2, '课程内容很实用，学到了很多。', 5, 1);

INSERT INTO learn_record (user_id, course_id, video_id, progress, duration, finished_count, last_learn_time) VALUES
(3, 1, 1, 300, 1200, 1, CURRENT_TIMESTAMP);

INSERT INTO favorite (user_id, course_id) VALUES
(3, 1);
