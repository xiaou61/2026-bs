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
('精简博客系统搭建记录', '记录从0到1搭建博客系统的关键过程', '这是一个基于Spring Boot与Vue3实现的精简博客系统。', '', 3, 1, 1, 1, 58, 2, '2026-04-27 09:00:00'),
('SpringBoot接口统一返回实践', '使用统一返回结构提升前后端协作效率', '统一返回结构通常包含code、message、data。', '', 1, 3, 1, 0, 36, 0, '2026-04-28 10:00:00'),
('周末阅读清单', '本周阅读过的3本技术书籍整理', '分享近期阅读的技术书单与感受。', '', 2, 2, 0, 0, 5, 0, '2026-04-29 11:00:00');

INSERT INTO blog_post_tag (post_id, tag_id) VALUES
(1, 1), (1, 2), (1, 3),
(2, 1), (2, 3),
(3, 4);

INSERT INTO blog_comment (post_id, user_id, content, status, reply_content, create_time) VALUES
(1, 2, '这篇文章写得很清晰，感谢分享。', 1, '感谢支持，后续会补充部署部分。', '2026-04-28 12:00:00'),
(1, 3, '请问前端权限控制是怎么做的？', 1, '通过路由meta角色和后端接口双重校验。', '2026-04-29 08:00:00'),
(2, 2, '内容不错，学到了。', 0, NULL, '2026-04-29 09:30:00');

INSERT INTO blog_notice (title, content, status, admin_id) VALUES
('系统升级通知', '本周日凌晨进行系统升级维护，届时可能短时不可访问。', 1, 1),
('写作活动', '本月开启技术征文活动，欢迎积极投稿。', 1, 1);
