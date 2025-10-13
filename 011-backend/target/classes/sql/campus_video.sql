CREATE DATABASE IF NOT EXISTS campus_video DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE campus_video;

DROP TABLE IF EXISTS video_report;
DROP TABLE IF EXISTS play_history;
DROP TABLE IF EXISTS user_points_log;
DROP TABLE IF EXISTS notification;
DROP TABLE IF EXISTS video_draft;
DROP TABLE IF EXISTS user_follow;
DROP TABLE IF EXISTS video_collect;
DROP TABLE IF EXISTS video_share;
DROP TABLE IF EXISTS comment_like;
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS video_like;
DROP TABLE IF EXISTS video_topic;
DROP TABLE IF EXISTS topic;
DROP TABLE IF EXISTS video;
DROP TABLE IF EXISTS user;

CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    nickname VARCHAR(50) NOT NULL COMMENT '昵称',
    avatar VARCHAR(200) DEFAULT '/default-avatar.jpg' COMMENT '头像URL',
    gender TINYINT DEFAULT 0 COMMENT '性别 0未知 1男 2女',
    phone VARCHAR(20) COMMENT '手机号',
    student_id VARCHAR(30) COMMENT '学号',
    school VARCHAR(50) COMMENT '学校',
    college VARCHAR(50) COMMENT '学院',
    major VARCHAR(50) COMMENT '专业',
    signature VARCHAR(200) COMMENT '个性签名',
    level INT DEFAULT 1 COMMENT '等级',
    points INT DEFAULT 0 COMMENT '积分',
    like_count INT DEFAULT 0 COMMENT '获赞总数',
    fans_count INT DEFAULT 0 COMMENT '粉丝数',
    follow_count INT DEFAULT 0 COMMENT '关注数',
    video_count INT DEFAULT 0 COMMENT '作品数',
    role VARCHAR(20) DEFAULT 'USER' COMMENT '角色 USER/ADMIN',
    status TINYINT DEFAULT 1 COMMENT '状态 0禁用 1正常',
    last_login_time DATETIME COMMENT '最后登录时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_username (username),
    INDEX idx_phone (phone),
    INDEX idx_student_id (student_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

CREATE TABLE video (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    title VARCHAR(100) NOT NULL COMMENT '视频标题',
    description TEXT COMMENT '视频描述',
    video_url VARCHAR(200) NOT NULL COMMENT '视频URL',
    cover_url VARCHAR(200) NOT NULL COMMENT '封面URL',
    duration INT DEFAULT 0 COMMENT '视频时长(秒)',
    width INT DEFAULT 0 COMMENT '视频宽度',
    height INT DEFAULT 0 COMMENT '视频高度',
    file_size BIGINT DEFAULT 0 COMMENT '文件大小(字节)',
    location VARCHAR(100) COMMENT '地理位置',
    permission TINYINT DEFAULT 1 COMMENT '权限 0私密 1公开 2仅粉丝',
    play_count INT DEFAULT 0 COMMENT '播放量',
    like_count INT DEFAULT 0 COMMENT '点赞数',
    comment_count INT DEFAULT 0 COMMENT '评论数',
    share_count INT DEFAULT 0 COMMENT '转发数',
    collect_count INT DEFAULT 0 COMMENT '收藏数',
    heat_score DECIMAL(10,2) DEFAULT 0 COMMENT '热度分数',
    status TINYINT DEFAULT 1 COMMENT '状态 0审核中 1已发布 2审核不通过 3已删除',
    audit_reason VARCHAR(200) COMMENT '审核原因',
    is_top TINYINT DEFAULT 0 COMMENT '是否置顶 0否 1是',
    publish_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_user_id (user_id),
    INDEX idx_status (status),
    INDEX idx_heat_score (heat_score),
    INDEX idx_publish_time (publish_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='视频表';

CREATE TABLE topic (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    topic_name VARCHAR(50) NOT NULL UNIQUE COMMENT '话题名称',
    description TEXT COMMENT '话题描述',
    cover_url VARCHAR(200) COMMENT '话题封面',
    category VARCHAR(20) COMMENT '话题分类',
    video_count INT DEFAULT 0 COMMENT '视频数量',
    view_count BIGINT DEFAULT 0 COMMENT '浏览量',
    participant_count INT DEFAULT 0 COMMENT '参与人数',
    heat_score DECIMAL(10,2) DEFAULT 0 COMMENT '热度分数',
    status TINYINT DEFAULT 1 COMMENT '状态 0禁用 1启用',
    is_hot TINYINT DEFAULT 0 COMMENT '是否热门 0否 1是',
    sort_order INT DEFAULT 0 COMMENT '排序',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_heat_score (heat_score),
    INDEX idx_is_hot (is_hot)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='话题表';

CREATE TABLE video_topic (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    video_id BIGINT NOT NULL COMMENT '视频ID',
    topic_id BIGINT NOT NULL COMMENT '话题ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_video_id (video_id),
    INDEX idx_topic_id (topic_id),
    UNIQUE KEY uk_video_topic (video_id, topic_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='视频话题关联表';

CREATE TABLE video_like (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    video_id BIGINT NOT NULL COMMENT '视频ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_user_id (user_id),
    INDEX idx_video_id (video_id),
    UNIQUE KEY uk_user_video (user_id, video_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='视频点赞表';

CREATE TABLE comment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    video_id BIGINT NOT NULL COMMENT '视频ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    parent_id BIGINT DEFAULT 0 COMMENT '父评论ID',
    reply_to_user_id BIGINT COMMENT '回复的用户ID',
    content TEXT NOT NULL COMMENT '评论内容',
    like_count INT DEFAULT 0 COMMENT '点赞数',
    reply_count INT DEFAULT 0 COMMENT '回复数',
    status TINYINT DEFAULT 1 COMMENT '状态 0待审核 1已发布 2已删除',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_video_id (video_id),
    INDEX idx_user_id (user_id),
    INDEX idx_parent_id (parent_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='评论表';

CREATE TABLE comment_like (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    comment_id BIGINT NOT NULL COMMENT '评论ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_user_id (user_id),
    INDEX idx_comment_id (comment_id),
    UNIQUE KEY uk_user_comment (user_id, comment_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='评论点赞表';

CREATE TABLE video_share (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    video_id BIGINT NOT NULL COMMENT '视频ID',
    original_user_id BIGINT NOT NULL COMMENT '原作者ID',
    share_text VARCHAR(200) COMMENT '转发语',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_user_id (user_id),
    INDEX idx_video_id (video_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='视频转发表';

CREATE TABLE video_collect (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    video_id BIGINT NOT NULL COMMENT '视频ID',
    folder_id BIGINT DEFAULT 0 COMMENT '收藏夹ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_user_id (user_id),
    INDEX idx_video_id (video_id),
    UNIQUE KEY uk_user_video (user_id, video_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='视频收藏表';

CREATE TABLE user_follow (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID(关注者)',
    follow_user_id BIGINT NOT NULL COMMENT '被关注用户ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_user_id (user_id),
    INDEX idx_follow_user_id (follow_user_id),
    UNIQUE KEY uk_user_follow (user_id, follow_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户关注表';

CREATE TABLE video_draft (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    title VARCHAR(100) COMMENT '视频标题',
    description TEXT COMMENT '视频描述',
    video_url VARCHAR(200) COMMENT '视频URL',
    cover_url VARCHAR(200) COMMENT '封面URL',
    topic_ids VARCHAR(100) COMMENT '话题ID(逗号分隔)',
    location VARCHAR(100) COMMENT '地理位置',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='视频草稿表';

CREATE TABLE notification (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '接收用户ID',
    from_user_id BIGINT COMMENT '发送用户ID',
    type VARCHAR(20) NOT NULL COMMENT '类型 LIKE/COMMENT/FOLLOW/MENTION/SYSTEM',
    content VARCHAR(200) COMMENT '通知内容',
    related_id BIGINT COMMENT '关联ID',
    is_read TINYINT DEFAULT 0 COMMENT '是否已读 0未读 1已读',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_user_id (user_id),
    INDEX idx_is_read (is_read)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='消息通知表';

CREATE TABLE user_points_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    change_type VARCHAR(20) NOT NULL COMMENT '变动类型',
    change_points INT NOT NULL COMMENT '变动积分',
    before_points INT NOT NULL COMMENT '变动前积分',
    after_points INT NOT NULL COMMENT '变动后积分',
    reason VARCHAR(200) COMMENT '原因',
    related_id BIGINT COMMENT '关联ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户积分记录表';

CREATE TABLE play_history (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    video_id BIGINT NOT NULL COMMENT '视频ID',
    play_duration INT DEFAULT 0 COMMENT '播放时长(秒)',
    play_percentage INT DEFAULT 0 COMMENT '播放进度(百分比)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_user_id (user_id),
    INDEX idx_video_id (video_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='播放历史表';

CREATE TABLE video_report (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '举报用户ID',
    video_id BIGINT NOT NULL COMMENT '视频ID',
    report_type VARCHAR(20) NOT NULL COMMENT '举报类型',
    report_reason TEXT COMMENT '举报原因',
    status TINYINT DEFAULT 0 COMMENT '状态 0待处理 1已处理 2已驳回',
    handle_result VARCHAR(200) COMMENT '处理结果',
    handle_admin_id BIGINT COMMENT '处理管理员ID',
    handle_time DATETIME COMMENT '处理时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_user_id (user_id),
    INDEX idx_video_id (video_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='视频举报表';

INSERT INTO user (username, password, nickname, avatar, gender, phone, student_id, school, college, role, level, points) VALUES
('admin', 'e10adc3949ba59abbe56e057f20f883e', '管理员', '/avatar/admin.jpg', 1, '13800000000', 'ADMIN001', '某某大学', '计算机学院', 'ADMIN', 5, 10000),
('test1', 'e10adc3949ba59abbe56e057f20f883e', '小明', '/avatar/test1.jpg', 1, '13800000001', '2024001', '某某大学', '计算机学院', 'USER', 3, 500),
('test2', 'e10adc3949ba59abbe56e057f20f883e', '小红', '/avatar/test2.jpg', 2, '13800000002', '2024002', '某某大学', '外语学院', 'USER', 2, 200),
('test3', 'e10adc3949ba59abbe56e057f20f883e', '小李', '/avatar/test3.jpg', 1, '13800000003', '2024003', '某某大学', '经济学院', 'USER', 2, 150),
('test4', 'e10adc3949ba59abbe56e057f20f883e', '小张', '/avatar/test4.jpg', 2, '13800000004', '2024004', '某某大学', '艺术学院', 'USER', 1, 50);

INSERT INTO topic (topic_name, description, cover_url, category, is_hot, sort_order) VALUES
('#校园生活#', '记录校园里的美好瞬间', '/topic/campus.jpg', '校园生活', 1, 1),
('#学习打卡#', '一起学习一起进步', '/topic/study.jpg', '学习打卡', 1, 2),
('#才艺展示#', '展示你的才华', '/topic/talent.jpg', '才艺展示', 1, 3),
('#美食探店#', '发现校园美食', '/topic/food.jpg', '美食探店', 1, 4),
('#运动健身#', '热爱运动热爱生活', '/topic/sports.jpg', '运动健身', 0, 5),
('#搞笑段子#', '快乐源泉', '/topic/funny.jpg', '搞笑段子', 1, 6),
('#情感故事#', '分享你的故事', '/topic/emotion.jpg', '情感故事', 0, 7),
('#技能教学#', '学习新技能', '/topic/skill.jpg', '技能教学', 0, 8),
('#毕业季#', '毕业季回忆', '/topic/graduation.jpg', '校园生活', 1, 9),
('#考研加油#', '考研路上一起加油', '/topic/exam.jpg', '学习打卡', 0, 10);

