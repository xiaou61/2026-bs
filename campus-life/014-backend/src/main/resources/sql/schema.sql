CREATE DATABASE IF NOT EXISTS campus_club DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE campus_club;

CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `student_id` varchar(20) DEFAULT NULL COMMENT '学号',
  `real_name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `major` varchar(50) DEFAULT NULL COMMENT '专业',
  `grade` int DEFAULT NULL COMMENT '年级',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `points` int DEFAULT 0 COMMENT '积分',
  `level` int DEFAULT 1 COMMENT '等级',
  `status` tinyint DEFAULT 0 COMMENT '状态：0-正常，1-禁用',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  UNIQUE KEY `uk_student_id` (`student_id`),
  KEY `idx_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

CREATE TABLE `interest_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '标签名称',
  `category` varchar(50) NOT NULL COMMENT '分类',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `user_count` int DEFAULT 0 COMMENT '使用人数',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_category` (`category`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='兴趣标签表';

CREATE TABLE `user_interest` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `interest_id` bigint NOT NULL COMMENT '兴趣标签ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_interest` (`user_id`, `interest_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_interest_id` (`interest_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户兴趣关联表';

CREATE TABLE `club` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '社团名称',
  `category` varchar(50) NOT NULL COMMENT '分类',
  `logo` varchar(255) DEFAULT NULL COMMENT '社团Logo',
  `cover` varchar(255) DEFAULT NULL COMMENT '封面图',
  `description` text COMMENT '简介',
  `president_id` bigint NOT NULL COMMENT '社长用户ID',
  `member_count` int DEFAULT 0 COMMENT '成员数',
  `max_members` int DEFAULT 100 COMMENT '最大成员数',
  `status` tinyint DEFAULT 0 COMMENT '状态：0-待审核，1-正常，2-已拒绝，3-已解散',
  `is_recruiting` tinyint DEFAULT 0 COMMENT '是否招新：0-否，1-是',
  `recruit_info` text COMMENT '招新信息',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_president_id` (`president_id`),
  KEY `idx_category` (`category`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='社团表';

CREATE TABLE `club_member` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `club_id` bigint NOT NULL COMMENT '社团ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role` tinyint DEFAULT 0 COMMENT '角色：0-普通成员，1-管理员，2-社长',
  `join_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '加入时间',
  `status` tinyint DEFAULT 0 COMMENT '状态：0-申请中，1-已通过，2-已拒绝，3-已退出',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_club_user` (`club_id`, `user_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='社团成员表';

CREATE TABLE `activity` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `club_id` bigint NOT NULL COMMENT '社团ID',
  `title` varchar(200) NOT NULL COMMENT '活动标题',
  `cover` varchar(255) DEFAULT NULL COMMENT '封面图',
  `description` text COMMENT '活动介绍',
  `location` varchar(200) DEFAULT NULL COMMENT '活动地点',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `max_participants` int DEFAULT 0 COMMENT '最大参与人数',
  `current_participants` int DEFAULT 0 COMMENT '当前报名人数',
  `sign_code` varchar(20) DEFAULT NULL COMMENT '签到码',
  `status` tinyint DEFAULT 0 COMMENT '状态：0-报名中，1-进行中，2-已结束，3-已取消',
  `points` int DEFAULT 10 COMMENT '活动积分',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_club_id` (`club_id`),
  KEY `idx_start_time` (`start_time`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='活动表';

CREATE TABLE `activity_registration` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `status` tinyint DEFAULT 0 COMMENT '状态：0-已报名，1-已签到，2-已取消',
  `register_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '报名时间',
  `sign_time` datetime DEFAULT NULL COMMENT '签到时间',
  `rating` int DEFAULT NULL COMMENT '评分1-5',
  `comment` text COMMENT '评价',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_activity_user` (`activity_id`, `user_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='活动报名表';

CREATE TABLE `circle` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '圈子名称',
  `category` varchar(50) NOT NULL COMMENT '分类',
  `cover` varchar(255) DEFAULT NULL COMMENT '封面图',
  `description` text COMMENT '描述',
  `creator_id` bigint NOT NULL COMMENT '创建者ID',
  `member_count` int DEFAULT 0 COMMENT '成员数',
  `topic_count` int DEFAULT 0 COMMENT '话题数',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_creator_id` (`creator_id`),
  KEY `idx_category` (`category`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='圈子表';

CREATE TABLE `circle_member` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `circle_id` bigint NOT NULL COMMENT '圈子ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `join_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '加入时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_circle_user` (`circle_id`, `user_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='圈子成员表';

CREATE TABLE `topic` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '发布者ID',
  `club_id` bigint DEFAULT NULL COMMENT '关联社团ID',
  `circle_id` bigint DEFAULT NULL COMMENT '关联圈子ID',
  `title` varchar(200) NOT NULL COMMENT '标题',
  `content` text NOT NULL COMMENT '内容',
  `images` text COMMENT '图片列表',
  `view_count` int DEFAULT 0 COMMENT '浏览量',
  `like_count` int DEFAULT 0 COMMENT '点赞数',
  `comment_count` int DEFAULT 0 COMMENT '评论数',
  `is_top` tinyint DEFAULT 0 COMMENT '是否置顶：0-否，1-是',
  `status` tinyint DEFAULT 0 COMMENT '状态：0-正常，1-已删除',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_club_id` (`club_id`),
  KEY `idx_circle_id` (`circle_id`),
  KEY `idx_create_time` (`create_time`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='话题表';

CREATE TABLE `topic_comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `topic_id` bigint NOT NULL COMMENT '话题ID',
  `user_id` bigint NOT NULL COMMENT '评论者ID',
  `parent_id` bigint DEFAULT 0 COMMENT '父评论ID',
  `reply_to_id` bigint DEFAULT NULL COMMENT '回复的用户ID',
  `content` text NOT NULL COMMENT '评论内容',
  `like_count` int DEFAULT 0 COMMENT '点赞数',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_topic_id` (`topic_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='话题评论表';

CREATE TABLE `like_record` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `target_id` bigint NOT NULL COMMENT '目标ID',
  `target_type` tinyint NOT NULL COMMENT '类型：1-话题，2-评论',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_target` (`user_id`, `target_id`, `target_type`),
  KEY `idx_target` (`target_id`, `target_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='点赞表';

CREATE TABLE `badge` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '勋章名称',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `condition_type` varchar(50) NOT NULL COMMENT '获取条件类型',
  `condition_value` int NOT NULL COMMENT '条件值',
  `points` int DEFAULT 0 COMMENT '获得积分',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='勋章表';

CREATE TABLE `user_badge` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `badge_id` bigint NOT NULL COMMENT '勋章ID',
  `obtain_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '获得时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_badge` (`user_id`, `badge_id`),
  KEY `idx_badge_id` (`badge_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户勋章表';

CREATE TABLE `points_record` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `points` int NOT NULL COMMENT '积分变化',
  `type` varchar(50) NOT NULL COMMENT '类型',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='积分记录表';

CREATE TABLE `notification` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '接收者ID',
  `type` tinyint NOT NULL COMMENT '类型：1-系统，2-互动，3-社团',
  `title` varchar(200) NOT NULL COMMENT '标题',
  `content` text COMMENT '内容',
  `link_type` tinyint DEFAULT NULL COMMENT '关联类型',
  `link_id` bigint DEFAULT NULL COMMENT '关联ID',
  `is_read` tinyint DEFAULT 0 COMMENT '是否已读：0-未读，1-已读',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_is_read` (`is_read`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='消息通知表';

CREATE TABLE `admin` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `real_name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `role` tinyint DEFAULT 2 COMMENT '角色：1-超级管理员，2-普通管理员',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='管理员表';

