CREATE DATABASE IF NOT EXISTS campus_confession DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE campus_confession;

CREATE TABLE IF NOT EXISTS `user` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `student_id` VARCHAR(30) NOT NULL UNIQUE COMMENT '学号',
    `real_name` VARCHAR(50) NOT NULL COMMENT '真实姓名',
    `phone` VARCHAR(20) NOT NULL UNIQUE COMMENT '手机号',
    `password` VARCHAR(100) NOT NULL COMMENT '密码',
    `school` VARCHAR(50) NULL COMMENT '学校',
    `college` VARCHAR(50) NULL COMMENT '学院',
    `grade` INT NULL COMMENT '年级',
    `auth_status` TINYINT DEFAULT 0 COMMENT '认证状态 0未认证 1审核中 2已认证 3未通过',
    `auth_image` VARCHAR(255) NULL COMMENT '认证照片',
    `points` INT DEFAULT 0 COMMENT '积分',
    `level` INT DEFAULT 1 COMMENT '等级',
    `post_count` INT DEFAULT 0 COMMENT '发帖数',
    `comment_count` INT DEFAULT 0 COMMENT '评论数',
    `like_count` INT DEFAULT 0 COMMENT '获赞数',
    `status` TINYINT DEFAULT 0 COMMENT '状态 0正常 1禁言 2封号',
    `ban_end_time` DATETIME NULL COMMENT '禁言结束时间',
    `ban_reason` VARCHAR(200) NULL COMMENT '禁言原因',
    `violation_count` INT DEFAULT 0 COMMENT '违规次数',
    `last_login_time` DATETIME NULL COMMENT '最后登录时间',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_student_id (`student_id`),
    INDEX idx_phone (`phone`),
    INDEX idx_status (`status`),
    INDEX idx_auth_status (`auth_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

CREATE TABLE IF NOT EXISTS `post` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `post_no` VARCHAR(20) NULL COMMENT '帖子编号',
    `user_id` BIGINT NOT NULL COMMENT '发布者ID',
    `anonymous_nickname` VARCHAR(50) NOT NULL COMMENT '匿名昵称',
    `anonymous_avatar` VARCHAR(255) NOT NULL COMMENT '匿名头像',
    `category` VARCHAR(20) NOT NULL COMMENT '分类 CONFESS表白 COMPLAIN吐槽 HELP求助 WHISPER树洞 CAMPUS校园 CHAT闲聊',
    `title` VARCHAR(200) NULL COMMENT '标题',
    `content` TEXT NOT NULL COMMENT '内容',
    `images` TEXT NULL COMMENT '图片列表JSON',
    `tags` VARCHAR(200) NULL COMMENT '话题标签',
    `view_count` INT DEFAULT 0 COMMENT '浏览数',
    `like_count` INT DEFAULT 0 COMMENT '点赞数',
    `comment_count` INT DEFAULT 0 COMMENT '评论数',
    `hot_score` DECIMAL(10,2) DEFAULT 0 COMMENT '热度分数',
    `is_top` TINYINT DEFAULT 0 COMMENT '是否置顶 0否 1是',
    `status` TINYINT DEFAULT 1 COMMENT '状态 0待审核 1已发布 2已删除 3审核拒绝',
    `audit_reason` VARCHAR(200) NULL COMMENT '审核原因',
    `sensitive_words` VARCHAR(500) NULL COMMENT '触发的敏感词',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_user_id (`user_id`),
    INDEX idx_category (`category`),
    INDEX idx_status (`status`),
    INDEX idx_create_time (`create_time`),
    INDEX idx_hot_score (`hot_score`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='帖子表';

CREATE TABLE IF NOT EXISTS `comment` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `post_id` BIGINT NOT NULL COMMENT '帖子ID',
    `user_id` BIGINT NOT NULL COMMENT '评论者ID',
    `anonymous_nickname` VARCHAR(50) NOT NULL COMMENT '匿名昵称',
    `anonymous_avatar` VARCHAR(255) NOT NULL COMMENT '匿名头像',
    `parent_id` BIGINT DEFAULT 0 COMMENT '父评论ID 0为一级评论',
    `reply_to_nickname` VARCHAR(50) NULL COMMENT '回复的匿名昵称',
    `content` TEXT NOT NULL COMMENT '评论内容',
    `like_count` INT DEFAULT 0 COMMENT '点赞数',
    `floor_number` INT NULL COMMENT '楼层号',
    `is_author` TINYINT DEFAULT 0 COMMENT '是否是楼主 0否 1是',
    `status` TINYINT DEFAULT 0 COMMENT '状态 0正常 1已删除',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_post_id (`post_id`),
    INDEX idx_user_id (`user_id`),
    INDEX idx_parent_id (`parent_id`),
    INDEX idx_create_time (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评论表';

CREATE TABLE IF NOT EXISTS `like_record` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `target_id` BIGINT NOT NULL COMMENT '目标ID',
    `target_type` TINYINT NOT NULL COMMENT '类型 1帖子 2评论',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    UNIQUE KEY uk_user_target (`user_id`, `target_id`, `target_type`),
    INDEX idx_target (`target_id`, `target_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='点赞表';

CREATE TABLE IF NOT EXISTS `message` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `conversation_id` VARCHAR(50) NOT NULL COMMENT '会话ID',
    `sender_id` BIGINT NOT NULL COMMENT '发送者ID',
    `receiver_id` BIGINT NOT NULL COMMENT '接收者ID',
    `sender_nickname` VARCHAR(50) NOT NULL COMMENT '发送者匿名昵称',
    `receiver_nickname` VARCHAR(50) NOT NULL COMMENT '接收者匿名昵称',
    `content` TEXT NOT NULL COMMENT '消息内容',
    `is_read` TINYINT DEFAULT 0 COMMENT '是否已读 0否 1是',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_conversation_id (`conversation_id`),
    INDEX idx_sender_id (`sender_id`),
    INDEX idx_receiver_id (`receiver_id`),
    INDEX idx_create_time (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='私信表';

CREATE TABLE IF NOT EXISTS `report` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `reporter_id` BIGINT NOT NULL COMMENT '举报人ID',
    `target_id` BIGINT NOT NULL COMMENT '目标ID',
    `target_type` TINYINT NOT NULL COMMENT '目标类型 1帖子 2评论',
    `report_type` VARCHAR(20) NOT NULL COMMENT '举报类型 PORN色情 VIOLENCE暴力 AD广告 FRAUD诈骗 ATTACK人身攻击 OTHER其他',
    `reason` TEXT NOT NULL COMMENT '举报原因',
    `evidence` TEXT NULL COMMENT '证据图片JSON',
    `status` TINYINT DEFAULT 0 COMMENT '状态 0待处理 1已处理 2已驳回',
    `handle_result` TEXT NULL COMMENT '处理结果',
    `handle_admin_id` BIGINT NULL COMMENT '处理管理员ID',
    `handle_time` DATETIME NULL COMMENT '处理时间',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_reporter_id (`reporter_id`),
    INDEX idx_target (`target_id`, `target_type`),
    INDEX idx_status (`status`),
    INDEX idx_create_time (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='举报表';

CREATE TABLE IF NOT EXISTS `sensitive_word` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `word` VARCHAR(100) NOT NULL COMMENT '敏感词',
    `level` TINYINT DEFAULT 1 COMMENT '敏感等级 1轻度 2中度 3重度',
    `category` VARCHAR(20) NOT NULL COMMENT '分类 POLITICS政治 PORN色情 VIOLENCE暴力 AD广告 ATTACK人身攻击',
    `is_enabled` TINYINT DEFAULT 1 COMMENT '是否启用 0否 1是',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    UNIQUE KEY uk_word (`word`),
    INDEX idx_category (`category`),
    INDEX idx_is_enabled (`is_enabled`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='敏感词表';

CREATE TABLE IF NOT EXISTS `admin` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `username` VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    `password` VARCHAR(100) NOT NULL COMMENT '密码',
    `real_name` VARCHAR(50) NOT NULL COMMENT '真实姓名',
    `role` TINYINT DEFAULT 2 COMMENT '角色 1超级管理员 2普通管理员',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_username (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员表';

