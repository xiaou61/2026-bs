CREATE DATABASE IF NOT EXISTS campus_resource_platform DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE campus_resource_platform;

CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL UNIQUE,
  `password` varchar(255) NOT NULL,
  `nickname` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `role` varchar(20) DEFAULT 'student',
  `points` int DEFAULT 100,
  `level` int DEFAULT 1,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `deleted` int DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `resource` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `title` varchar(200) NOT NULL,
  `description` text,
  `category` varchar(50) DEFAULT NULL,
  `file_url` varchar(500) NOT NULL,
  `file_name` varchar(255) NOT NULL,
  `file_size` bigint DEFAULT NULL,
  `file_type` varchar(50) DEFAULT NULL,
  `points` int DEFAULT 0,
  `download_count` int DEFAULT 0,
  `view_count` int DEFAULT 0,
  `rating` double DEFAULT 0,
  `rating_count` int DEFAULT 0,
  `status` varchar(20) DEFAULT 'approved',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `deleted` int DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `study_group` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` text,
  `creator_id` bigint NOT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `max_members` int DEFAULT 50,
  `current_members` int DEFAULT 1,
  `category` varchar(50) DEFAULT NULL,
  `status` varchar(20) DEFAULT 'active',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `deleted` int DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `group_member` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `group_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `role` varchar(20) DEFAULT 'member',
  `join_time` datetime DEFAULT NULL,
  `deleted` int DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `question` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `subject` varchar(50) DEFAULT NULL,
  `difficulty` varchar(20) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `content` text NOT NULL,
  `options` text,
  `answer` text NOT NULL,
  `analysis` text,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `deleted` int DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `wrong_question` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `question_id` bigint NOT NULL,
  `user_answer` text,
  `wrong_count` int DEFAULT 1,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `deleted` int DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `question_answer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(200) NOT NULL,
  `content` text NOT NULL,
  `asker_id` bigint NOT NULL,
  `category` varchar(50) DEFAULT NULL,
  `bounty` int DEFAULT 0,
  `status` varchar(20) DEFAULT 'pending',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `deleted` int DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `answer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `question_answer_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `content` text NOT NULL,
  `like_count` int DEFAULT 0,
  `is_accepted` int DEFAULT 0,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `deleted` int DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `note` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `title` varchar(200) NOT NULL,
  `content` longtext NOT NULL,
  `category` varchar(50) DEFAULT NULL,
  `tags` varchar(255) DEFAULT NULL,
  `is_public` int DEFAULT 0,
  `view_count` int DEFAULT 0,
  `like_count` int DEFAULT 0,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `deleted` int DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `resource_rating` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `resource_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `rating` int NOT NULL,
  `comment` text,
  `create_time` datetime DEFAULT NULL,
  `deleted` int DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `points_record` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `points` int NOT NULL,
  `type` varchar(50) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `user` (`username`, `password`, `nickname`, `email`, `role`, `points`, `level`, `create_time`, `update_time`, `deleted`) VALUES
('admin', 'e10adc3949ba59abbe56e057f20f883e', '管理员', 'admin@example.com', 'admin', 10000, 10, NOW(), NOW(), 0),
('student1', 'e10adc3949ba59abbe56e057f20f883e', '学生一号', 'student1@example.com', 'student', 500, 3, NOW(), NOW(), 0),
('student2', 'e10adc3949ba59abbe56e057f20f883e', '学生二号', 'student2@example.com', 'student', 300, 2, NOW(), NOW(), 0),
('teacher1', 'e10adc3949ba59abbe56e057f20f883e', '老师一号', 'teacher1@example.com', 'teacher', 800, 5, NOW(), NOW(), 0);

INSERT INTO `resource` (`user_id`, `title`, `description`, `category`, `file_url`, `file_name`, `file_size`, `file_type`, `points`, `download_count`, `view_count`, `rating`, `rating_count`, `status`, `create_time`, `update_time`, `deleted`) VALUES
(2, 'Java编程基础教程', 'Java入门到精通的完整教程', '编程', '/files/java-basic.pdf', 'java-basic.pdf', 2048000, 'pdf', 10, 50, 120, 4.5, 25, 'approved', NOW(), NOW(), 0),
(2, '数据结构与算法笔记', '数据结构课程的详细笔记', '算法', '/files/data-structure.pdf', 'data-structure.pdf', 1024000, 'pdf', 5, 30, 80, 4.8, 15, 'approved', NOW(), NOW(), 0),
(4, '高等数学历年真题', '高等数学考试真题及答案解析', '数学', '/files/math-exam.pdf', 'math-exam.pdf', 512000, 'pdf', 20, 100, 200, 4.9, 50, 'approved', NOW(), NOW(), 0);

INSERT INTO `study_group` (`name`, `description`, `creator_id`, `max_members`, `current_members`, `category`, `status`, `create_time`, `update_time`, `deleted`) VALUES
('Java学习小组', '一起学习Java编程，交流技术', 2, 50, 3, '编程', 'active', NOW(), NOW(), 0),
('算法竞赛小组', '准备各类算法竞赛', 3, 30, 2, '算法', 'active', NOW(), NOW(), 0),
('考研数学小组', '一起备考考研数学', 4, 100, 5, '数学', 'active', NOW(), NOW(), 0);

INSERT INTO `question` (`user_id`, `subject`, `difficulty`, `type`, `content`, `options`, `answer`, `analysis`, `create_time`, `update_time`, `deleted`) VALUES
(4, '数学', '简单', '选择题', '下列哪个是质数？', 'A. 4|B. 6|C. 7|D. 9', 'C', '质数是只能被1和自身整除的大于1的自然数', NOW(), NOW(), 0),
(4, '编程', '中等', '编程题', '实现一个单例模式', '', '使用懒汉式或饿汉式实现', '单例模式确保一个类只有一个实例', NOW(), NOW(), 0),
(4, '算法', '困难', '算法题', '实现快速排序算法', '', '分治法实现', '时间复杂度O(nlogn)', NOW(), NOW(), 0);

INSERT INTO `question_answer` (`title`, `content`, `asker_id`, `category`, `bounty`, `status`, `create_time`, `update_time`, `deleted`) VALUES
('Java多线程问题', '如何正确使用synchronized关键字？', 2, '编程', 50, 'pending', NOW(), NOW(), 0),
('数据库优化问题', 'MySQL索引应该如何设计？', 3, '数据库', 100, 'pending', NOW(), NOW(), 0);

INSERT INTO `note` (`user_id`, `title`, `content`, `category`, `tags`, `is_public`, `view_count`, `like_count`, `create_time`, `update_time`, `deleted`) VALUES
(2, 'Java并发编程笔记', '# Java并发编程\n\n## 线程基础\n线程是程序执行的最小单元...', '编程', 'Java,并发', 1, 50, 20, NOW(), NOW(), 0),
(3, '算法学习总结', '# 算法学习笔记\n\n## 排序算法\n冒泡排序、快速排序...', '算法', '算法,排序', 1, 30, 15, NOW(), NOW(), 0);

