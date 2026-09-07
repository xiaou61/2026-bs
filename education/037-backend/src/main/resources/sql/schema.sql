-- ====================================================
-- 编程学习交流平台数据库建表脚本
-- Database: programming_learning
-- ====================================================

CREATE DATABASE IF NOT EXISTS programming_learning DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE programming_learning;

-- ====================================================
-- 1. 用户相关表
-- ====================================================

-- 用户表
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `open_id` VARCHAR(100) DEFAULT NULL COMMENT '微信openId',
  `union_id` VARCHAR(100) DEFAULT NULL COMMENT '微信unionId',
  `nickname` VARCHAR(50) NOT NULL COMMENT '用户昵称',
  `avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像URL',
  `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
  `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
  `role` VARCHAR(20) NOT NULL DEFAULT 'USER' COMMENT '角色：USER/TEACHER/ADMIN',
  `status` VARCHAR(20) NOT NULL DEFAULT 'NORMAL' COMMENT '状态：NORMAL/DISABLED',
  `score` INT(11) NOT NULL DEFAULT 0 COMMENT '积分',
  `level` INT(11) NOT NULL DEFAULT 1 COMMENT '等级(1-5)',
  `bio` VARCHAR(500) DEFAULT NULL COMMENT '个人简介',
  `last_login_time` DATETIME DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` VARCHAR(50) DEFAULT NULL COMMENT '最后登录IP',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_open_id` (`open_id`),
  UNIQUE KEY `uk_phone` (`phone`),
  KEY `idx_score` (`score`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ====================================================
-- 2. 课程相关表
-- ====================================================

-- 课程表
DROP TABLE IF EXISTS `courses`;
CREATE TABLE `courses` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '课程ID',
  `title` VARCHAR(200) NOT NULL COMMENT '课程标题',
  `cover` VARCHAR(255) DEFAULT NULL COMMENT '课程封面',
  `description` TEXT COMMENT '课程简介',
  `category` VARCHAR(50) NOT NULL COMMENT '课程分类',
  `level` VARCHAR(20) NOT NULL DEFAULT 'BEGINNER' COMMENT '课程难度：BEGINNER/INTERMEDIATE/ADVANCED',
  `status` VARCHAR(20) NOT NULL DEFAULT 'DRAFT' COMMENT '课程状态：DRAFT/PUBLISHED/OFFLINE',
  `teacher_id` BIGINT(20) NOT NULL COMMENT '授课教师ID',
  `teacher_name` VARCHAR(50) NOT NULL COMMENT '授课教师姓名',
  `student_count` INT(11) NOT NULL DEFAULT 0 COMMENT '学习人数',
  `chapter_count` INT(11) NOT NULL DEFAULT 0 COMMENT '章节数',
  `duration` INT(11) NOT NULL DEFAULT 0 COMMENT '课程时长(分钟)',
  `view_count` INT(11) NOT NULL DEFAULT 0 COMMENT '浏览次数',
  `like_count` INT(11) NOT NULL DEFAULT 0 COMMENT '点赞数',
  `favorite_count` INT(11) NOT NULL DEFAULT 0 COMMENT '收藏数',
  `sort_order` INT(11) NOT NULL DEFAULT 0 COMMENT '排序号',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_teacher_id` (`teacher_id`),
  KEY `idx_category` (`category`),
  KEY `idx_status` (`status`),
  KEY `idx_hot` (`view_count`, `student_count`, `like_count`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程表';

-- 章节表
DROP TABLE IF EXISTS `chapters`;
CREATE TABLE `chapters` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '章节ID',
  `course_id` BIGINT(20) NOT NULL COMMENT '课程ID',
  `title` VARCHAR(200) NOT NULL COMMENT '章节标题',
  `content` LONGTEXT COMMENT '章节内容(Markdown)',
  `duration` INT(11) NOT NULL DEFAULT 0 COMMENT '章节时长(分钟)',
  `sort_order` INT(11) NOT NULL DEFAULT 0 COMMENT '排序号',
  `is_free` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否免费',
  `view_count` INT(11) NOT NULL DEFAULT 0 COMMENT '浏览次数',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_course_id` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='章节表';

-- 学习记录表
DROP TABLE IF EXISTS `learning_records`;
CREATE TABLE `learning_records` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
  `course_id` BIGINT(20) NOT NULL COMMENT '课程ID',
  `chapter_id` BIGINT(20) NOT NULL COMMENT '章节ID',
  `progress` INT(11) NOT NULL DEFAULT 0 COMMENT '学习进度(0-100)',
  `duration` INT(11) NOT NULL DEFAULT 0 COMMENT '学习时长(分钟)',
  `is_completed` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否完成',
  `completed_time` DATETIME DEFAULT NULL COMMENT '完成时间',
  `last_learn_time` DATETIME NOT NULL COMMENT '最后学习时间',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_chapter` (`user_id`, `chapter_id`),
  KEY `idx_user_course` (`user_id`, `course_id`),
  KEY `idx_last_learn_time` (`last_learn_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学习记录表';

-- ====================================================
-- 3. 问答相关表
-- ====================================================

-- 问题表
DROP TABLE IF EXISTS `questions`;
CREATE TABLE `questions` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '问题ID',
  `user_id` BIGINT(20) NOT NULL COMMENT '提问用户ID',
  `nickname` VARCHAR(50) NOT NULL COMMENT '提问用户昵称',
  `avatar` VARCHAR(255) DEFAULT NULL COMMENT '提问用户头像',
  `title` VARCHAR(200) NOT NULL COMMENT '问题标题',
  `content` LONGTEXT NOT NULL COMMENT '问题内容(Markdown)',
  `tags` VARCHAR(200) DEFAULT NULL COMMENT '问题标签,逗号分隔',
  `bounty_score` INT(11) NOT NULL DEFAULT 0 COMMENT '悬赏积分',
  `status` VARCHAR(20) NOT NULL DEFAULT 'OPEN' COMMENT '问题状态：OPEN/SOLVED/CLOSED',
  `accepted_answer_id` BIGINT(20) DEFAULT NULL COMMENT '采纳的答案ID',
  `view_count` INT(11) NOT NULL DEFAULT 0 COMMENT '浏览次数',
  `answer_count` INT(11) NOT NULL DEFAULT 0 COMMENT '回答数',
  `like_count` INT(11) NOT NULL DEFAULT 0 COMMENT '点赞数',
  `favorite_count` INT(11) NOT NULL DEFAULT 0 COMMENT '收藏数',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`),
  KEY `idx_hot` (`view_count`, `answer_count`, `like_count`),
  FULLTEXT KEY `ft_title_content` (`title`, `content`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='问题表';

-- 回答表
DROP TABLE IF EXISTS `answers`;
CREATE TABLE `answers` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '回答ID',
  `question_id` BIGINT(20) NOT NULL COMMENT '问题ID',
  `user_id` BIGINT(20) NOT NULL COMMENT '回答用户ID',
  `nickname` VARCHAR(50) NOT NULL COMMENT '回答用户昵称',
  `avatar` VARCHAR(255) DEFAULT NULL COMMENT '回答用户头像',
  `content` LONGTEXT NOT NULL COMMENT '回答内容(Markdown)',
  `is_accepted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否被采纳',
  `like_count` INT(11) NOT NULL DEFAULT 0 COMMENT '点赞数',
  `comment_count` INT(11) NOT NULL DEFAULT 0 COMMENT '评论数',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_question_id` (`question_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='回答表';

-- ====================================================
-- 4. 文章相关表
-- ====================================================

-- 文章表
DROP TABLE IF EXISTS `articles`;
CREATE TABLE `articles` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '文章ID',
  `user_id` BIGINT(20) NOT NULL COMMENT '作者ID',
  `nickname` VARCHAR(50) NOT NULL COMMENT '作者昵称',
  `avatar` VARCHAR(255) DEFAULT NULL COMMENT '作者头像',
  `column_id` BIGINT(20) DEFAULT NULL COMMENT '专栏ID',
  `title` VARCHAR(200) NOT NULL COMMENT '文章标题',
  `cover` VARCHAR(255) DEFAULT NULL COMMENT '文章封面',
  `summary` VARCHAR(500) DEFAULT NULL COMMENT '文章摘要',
  `content` LONGTEXT NOT NULL COMMENT '文章内容(Markdown)',
  `tags` VARCHAR(200) DEFAULT NULL COMMENT '文章标签,逗号分隔',
  `status` VARCHAR(20) NOT NULL DEFAULT 'DRAFT' COMMENT '文章状态：DRAFT/PUBLISHED/DELETED',
  `view_count` INT(11) NOT NULL DEFAULT 0 COMMENT '浏览次数',
  `like_count` INT(11) NOT NULL DEFAULT 0 COMMENT '点赞数',
  `favorite_count` INT(11) NOT NULL DEFAULT 0 COMMENT '收藏数',
  `comment_count` INT(11) NOT NULL DEFAULT 0 COMMENT '评论数',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_column_id` (`column_id`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`),
  KEY `idx_hot` (`view_count`, `like_count`, `favorite_count`),
  FULLTEXT KEY `ft_title_content` (`title`, `content`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章表';

-- ====================================================
-- 5. 代码相关表
-- ====================================================

-- 代码片段表
DROP TABLE IF EXISTS `code_snippets`;
CREATE TABLE `code_snippets` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '代码片段ID',
  `user_id` BIGINT(20) NOT NULL COMMENT '作者ID',
  `nickname` VARCHAR(50) NOT NULL COMMENT '作者昵称',
  `avatar` VARCHAR(255) DEFAULT NULL COMMENT '作者头像',
  `title` VARCHAR(200) NOT NULL COMMENT '代码标题',
  `description` TEXT COMMENT '代码描述',
  `language` VARCHAR(50) NOT NULL COMMENT '编程语言',
  `code` LONGTEXT NOT NULL COMMENT '代码内容',
  `tags` VARCHAR(200) DEFAULT NULL COMMENT '代码标签,逗号分隔',
  `fork_from_id` BIGINT(20) DEFAULT NULL COMMENT 'Fork来源ID',
  `fork_count` INT(11) NOT NULL DEFAULT 0 COMMENT 'Fork次数',
  `view_count` INT(11) NOT NULL DEFAULT 0 COMMENT '浏览次数',
  `like_count` INT(11) NOT NULL DEFAULT 0 COMMENT '点赞数',
  `favorite_count` INT(11) NOT NULL DEFAULT 0 COMMENT '收藏数',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_language` (`language`),
  KEY `idx_fork_from_id` (`fork_from_id`),
  KEY `idx_create_time` (`create_time`),
  FULLTEXT KEY `ft_title_desc` (`title`, `description`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='代码片段表';

-- ====================================================
-- 6. 互动相关表
-- ====================================================

-- 点赞表
DROP TABLE IF EXISTS `likes`;
CREATE TABLE `likes` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '点赞ID',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
  `target_type` VARCHAR(20) NOT NULL COMMENT '目标类型：COURSE/QUESTION/ANSWER/ARTICLE/CODE',
  `target_id` BIGINT(20) NOT NULL COMMENT '目标ID',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_target` (`user_id`, `target_type`, `target_id`),
  KEY `idx_target` (`target_type`, `target_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='点赞表';

-- 收藏表
DROP TABLE IF EXISTS `favorites`;
CREATE TABLE `favorites` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '收藏ID',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
  `target_type` VARCHAR(20) NOT NULL COMMENT '目标类型：COURSE/QUESTION/ARTICLE/CODE',
  `target_id` BIGINT(20) NOT NULL COMMENT '目标ID',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_target` (`user_id`, `target_type`, `target_id`),
  KEY `idx_user_type` (`user_id`, `target_type`),
  KEY `idx_target` (`target_type`, `target_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收藏表';

-- 评论表
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `user_id` BIGINT(20) NOT NULL COMMENT '评论用户ID',
  `nickname` VARCHAR(50) NOT NULL COMMENT '评论用户昵称',
  `avatar` VARCHAR(255) DEFAULT NULL COMMENT '评论用户头像',
  `target_type` VARCHAR(20) NOT NULL COMMENT '目标类型：QUESTION/ANSWER/ARTICLE/CODE',
  `target_id` BIGINT(20) NOT NULL COMMENT '目标ID',
  `parent_id` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '父评论ID(0表示顶级评论)',
  `reply_to_id` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '回复的评论ID(0表示不是回复)',
  `reply_to_nickname` VARCHAR(50) DEFAULT NULL COMMENT '回复的用户昵称',
  `content` TEXT NOT NULL COMMENT '评论内容',
  `like_count` INT(11) NOT NULL DEFAULT 0 COMMENT '点赞数',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_target` (`target_type`, `target_id`),
  KEY `idx_parent_id` (`parent_id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评论表';

-- ====================================================
-- 7. 系统相关表
-- ====================================================

-- 打卡记录表
DROP TABLE IF EXISTS `check_in_logs`;
CREATE TABLE `check_in_logs` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '打卡ID',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
  `check_in_date` DATE NOT NULL COMMENT '打卡日期',
  `continuous_days` INT(11) NOT NULL DEFAULT 1 COMMENT '连续打卡天数',
  `study_minutes` INT(11) NOT NULL DEFAULT 0 COMMENT '今日学习时长(分钟)',
  `score_earned` INT(11) NOT NULL DEFAULT 0 COMMENT '获得积分',
  `remark` VARCHAR(200) DEFAULT NULL COMMENT '打卡备注',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_date` (`user_id`, `check_in_date`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_check_in_date` (`check_in_date`),
  KEY `idx_continuous_days` (`continuous_days`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='打卡记录表';

-- 积分记录表
DROP TABLE IF EXISTS `score_logs`;
CREATE TABLE `score_logs` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
  `score_change` INT(11) NOT NULL COMMENT '积分变化(正数为增加,负数为减少)',
  `after_score` INT(11) NOT NULL COMMENT '变化后积分',
  `source_type` VARCHAR(50) NOT NULL COMMENT '积分来源类型',
  `source_id` BIGINT(20) DEFAULT NULL COMMENT '来源ID',
  `description` VARCHAR(200) NOT NULL COMMENT '描述',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='积分记录表';

-- 通知表
DROP TABLE IF EXISTS `notifications`;
CREATE TABLE `notifications` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '通知ID',
  `user_id` BIGINT(20) NOT NULL COMMENT '接收用户ID',
  `type` VARCHAR(20) NOT NULL COMMENT '通知类型',
  `title` VARCHAR(100) NOT NULL COMMENT '通知标题',
  `content` TEXT NOT NULL COMMENT '通知内容',
  `related_type` VARCHAR(20) DEFAULT NULL COMMENT '关联类型',
  `related_id` BIGINT(20) DEFAULT NULL COMMENT '关联ID',
  `is_read` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否已读',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `read_time` DATETIME DEFAULT NULL COMMENT '阅读时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_is_read` (`is_read`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='通知表';
