USE campus_club;

INSERT INTO `admin` (`username`, `password`, `real_name`, `role`) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '超级管理员', 1),
('manager', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '普通管理员', 2);

INSERT INTO `badge` (`name`, `description`, `icon`, `condition_type`, `condition_value`, `points`) VALUES
('新人勋章', '欢迎加入校园社团平台', '🎉', 'REGISTER', 1, 10),
('社团达人', '加入3个社团', '🏆', 'JOIN_CLUB', 3, 20),
('活跃分子', '参加10次活动', '⭐', 'JOIN_ACTIVITY', 10, 30),
('意见领袖', '发布50个话题', '💡', 'PUBLISH_TOPIC', 50, 50),
('热心肠', '评论100次', '❤️', 'COMMENT', 100, 40),
('社交达人', '获得100个点赞', '👍', 'GET_LIKE', 100, 60);

INSERT INTO `interest_tag` (`name`, `category`, `icon`) VALUES
('编程', '学术', '💻'),
('数学', '学术', '🔢'),
('英语', '学术', '📚'),
('物理', '学术', '⚛️'),
('化学', '学术', '🧪'),
('绘画', '艺术', '🎨'),
('音乐', '艺术', '🎵'),
('舞蹈', '艺术', '💃'),
('摄影', '艺术', '📷'),
('书法', '艺术', '✍️'),
('篮球', '运动', '🏀'),
('足球', '运动', '⚽'),
('羽毛球', '运动', '🏸'),
('乒乓球', '运动', '🏓'),
('跑步', '运动', '🏃'),
('人工智能', '科技', '🤖'),
('机器人', '科技', '🦾'),
('无人机', '科技', '🚁'),
('3D打印', '科技', '🖨️'),
('VR/AR', '科技', '🥽'),
('志愿服务', '公益', '🤝'),
('支教', '公益', '📖'),
('环保', '公益', '🌱'),
('动物保护', '公益', '🐾'),
('献血', '公益', '💉');

INSERT INTO `user` (`username`, `password`, `student_id`, `real_name`, `email`, `avatar`, `major`, `grade`, `points`, `level`) VALUES
('student1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '2021001', '张三', 'zhangsan@example.com', NULL, '计算机科学与技术', 2021, 50, 1),
('student2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '2021002', '李四', 'lisi@example.com', NULL, '软件工程', 2021, 30, 1),
('student3', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '2022001', '王五', 'wangwu@example.com', NULL, '电子信息工程', 2022, 40, 1);

INSERT INTO `club` (`name`, `category`, `logo`, `cover`, `description`, `president_id`, `member_count`, `max_members`, `status`, `is_recruiting`, `recruit_info`) VALUES
('计算机协会', '学术', NULL, NULL, '致力于计算机技术交流与学习，定期举办技术分享会和编程竞赛', 1, 1, 100, 1, 1, '欢迎对编程感兴趣的同学加入！无需基础，我们提供从入门到进阶的学习路线。'),
('书画社', '文艺', NULL, NULL, '传承中华传统文化，学习书法和绘画艺术', 2, 1, 50, 1, 1, '招收对书法、绘画感兴趣的同学，提供专业指导和展示平台。'),
('篮球俱乐部', '体育', NULL, NULL, '篮球爱好者的聚集地，定期组织训练和比赛', 3, 1, 80, 1, 1, '热爱篮球运动的同学欢迎加入，不限水平，以球会友！');

INSERT INTO `club_member` (`club_id`, `user_id`, `role`, `status`) VALUES
(1, 1, 2, 1),
(2, 2, 2, 1),
(3, 3, 2, 1);

INSERT INTO `activity` (`club_id`, `title`, `cover`, `description`, `location`, `start_time`, `end_time`, `max_participants`, `status`, `points`, `sign_code`) VALUES
(1, '编程马拉松活动', NULL, '48小时编程挑战，团队协作完成项目开发', '创新创业中心', '2024-12-20 09:00:00', '2024-12-22 18:00:00', 50, 0, 20, 'CODE2024'),
(2, '迎新书画展', NULL, '展示社团成员优秀作品，传承艺术文化', '艺术展厅', '2024-12-15 14:00:00', '2024-12-15 17:00:00', 100, 0, 10, 'ART2024'),
(3, '新生杯篮球赛', NULL, '新生篮球友谊赛，增进同学交流', '体育馆', '2024-12-18 15:00:00', '2024-12-18 18:00:00', 30, 0, 15, 'BALL2024');

INSERT INTO `circle` (`name`, `category`, `cover`, `description`, `creator_id`, `member_count`, `topic_count`) VALUES
('技术交流圈', '科技', NULL, '分享最新技术动态，讨论技术问题', 1, 1, 0),
('艺术鉴赏圈', '艺术', NULL, '欣赏艺术作品，交流艺术心得', 2, 1, 0),
('运动健身圈', '运动', NULL, '分享运动经验，相约一起锻炼', 3, 1, 0);

INSERT INTO `circle_member` (`circle_id`, `user_id`) VALUES
(1, 1),
(2, 2),
(3, 3);

INSERT INTO `topic` (`user_id`, `club_id`, `circle_id`, `title`, `content`, `view_count`, `like_count`, `comment_count`) VALUES
(1, 1, 1, '如何学习Java编程？', '最近想学习Java，大家有什么好的学习资源推荐吗？', 15, 3, 2),
(2, 2, 2, '分享我的第一幅水墨画', '今天完成了人生第一幅水墨画作品，虽然技法还不够成熟，但很有成就感！', 20, 5, 3),
(3, 3, 3, '约球！周末一起打篮球', '周六下午3点体育馆，有没有想一起打篮球的？', 10, 2, 1);

INSERT INTO `user_badge` (`user_id`, `badge_id`) VALUES
(1, 1),
(2, 1),
(3, 1);

INSERT INTO `points_record` (`user_id`, `points`, `type`, `description`) VALUES
(1, 10, 'REGISTER', '注册账户'),
(1, 20, 'COMPLETE_PROFILE', '完善个人资料'),
(1, 5, 'JOIN_CLUB', '加入社团：计算机协会'),
(1, 3, 'PUBLISH_TOPIC', '发布话题'),
(2, 10, 'REGISTER', '注册账户'),
(2, 20, 'COMPLETE_PROFILE', '完善个人资料'),
(3, 10, 'REGISTER', '注册账户'),
(3, 20, 'COMPLETE_PROFILE', '完善个人资料');

