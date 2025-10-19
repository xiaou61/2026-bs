USE campus_confession;

INSERT INTO `admin` (`username`, `password`, `real_name`, `role`) VALUES
('admin', '$2a$10$xQh7YKZjYvFjXXLLQpWqUeN9Z3jZvN7lXNT8Y3kJqN.wJqGqVHRQW', '超级管理员', 1);

INSERT INTO `user` (`student_id`, `real_name`, `phone`, `password`, `school`, `college`, `grade`, `auth_status`, `points`, `level`) VALUES
('20210001', '张三', '13800138001', '$2a$10$xQh7YKZjYvFjXXLLQpWqUeN9Z3jZvN7lXNT8Y3kJqN.wJqGqVHRQW', '某某大学', '计算机学院', 2021, 2, 100, 3),
('20210002', '李四', '13800138002', '$2a$10$xQh7YKZjYvFjXXLLQpWqUeN9Z3jZvN7lXNT8Y3kJqN.wJqGqVHRQW', '某某大学', '软件学院', 2021, 2, 80, 2);

INSERT INTO `sensitive_word` (`word`, `level`, `category`) VALUES
('政治', 3, 'POLITICS'),
('暴力', 3, 'VIOLENCE'),
('色情', 3, 'PORN'),
('赌博', 3, 'AD'),
('毒品', 3, 'VIOLENCE'),
('骗子', 2, 'FRAUD'),
('傻逼', 2, 'ATTACK'),
('垃圾', 1, 'ATTACK'),
('广告', 1, 'AD'),
('微信', 1, 'AD'),
('QQ', 1, 'AD'),
('手机号', 1, 'AD'),
('加我', 1, 'AD');

INSERT INTO `post` (`user_id`, `post_no`, `anonymous_nickname`, `anonymous_avatar`, `category`, `title`, `content`, `view_count`, `like_count`, `comment_count`, `status`) VALUES
(1, '#00001', '可爱的小熊1234', '/images/avatar/default-1.png', 'CONFESS', '表白墙第一帖', '想对图书馆三楼总是戴耳机学习的女生说,你真的很认真很美~', 156, 23, 8, 1),
(2, '#00002', '温柔的小猫5678', '/images/avatar/default-2.png', 'COMPLAIN', '食堂吐槽', '为什么食堂的饭菜越来越贵了,学生党真的伤不起啊！', 234, 45, 12, 1),
(1, '#00003', '阳光的小狗9876', '/images/avatar/default-3.png', 'HELP', '求助贴', '有没有学长学姐推荐一下数据结构的学习资料？期末要考试了急急急', 89, 12, 5, 1);

INSERT INTO `comment` (`post_id`, `user_id`, `anonymous_nickname`, `anonymous_avatar`, `parent_id`, `content`, `floor_number`, `is_author`) VALUES
(1, 2, '害羞的小兔4321', '/images/avatar/default-4.png', 0, '好甜啊,祝你们幸福！', 1, 0),
(1, 1, '可爱的小熊1234', '/images/avatar/default-1.png', 1, '谢谢祝福~', 1, 1),
(2, 1, '勇敢的小鹿7890', '/images/avatar/default-5.png', 0, '确实,物价飞涨啊', 2, 0);

