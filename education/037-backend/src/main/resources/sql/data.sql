-- ====================================================
-- 编程学习交流平台初始化数据脚本
-- ====================================================

USE programming_learning;

-- ====================================================
-- 1. 初始化用户数据
-- ====================================================

INSERT INTO `users` (`id`, `open_id`, `nickname`, `avatar`, `phone`, `email`, `role`, `status`, `score`, `level`, `bio`, `last_login_time`, `last_login_ip`) VALUES
(1, 'admin_open_id', '管理员', 'https://via.placeholder.com/100', '13800138000', 'admin@programming.com', 'ADMIN', 'NORMAL', 10000, 5, '系统管理员', NOW(), '127.0.0.1'),
(2, 'teacher_open_id', '张老师', 'https://via.placeholder.com/100', '13800138001', 'teacher1@programming.com', 'TEACHER', 'NORMAL', 5000, 4, 'Java高级工程师，10年开发经验', NOW(), '127.0.0.1'),
(3, 'user1_open_id', '编程小白', 'https://via.placeholder.com/100', '13800138002', 'user1@test.com', 'USER', 'NORMAL', 150, 2, '初学者，正在学习Java', NOW(), '127.0.0.1'),
(4, 'user2_open_id', '代码狂人', 'https://via.placeholder.com/100', '13800138003', 'user2@test.com', 'USER', 'NORMAL', 800, 3, 'Full Stack Developer', NOW(), '127.0.0.1'),
(5, 'user3_open_id', '技术大牛', 'https://via.placeholder.com/100', '13800138004', 'user3@test.com', 'USER', 'NORMAL', 3500, 4, '资深架构师', NOW(), '127.0.0.1');

-- ====================================================
-- 2. 初始化课程数据
-- ====================================================

INSERT INTO `courses` (`id`, `title`, `cover`, `description`, `category`, `level`, `status`, `teacher_id`, `teacher_name`, `student_count`, `chapter_count`, `duration`, `view_count`, `like_count`, `favorite_count`, `sort_order`) VALUES
(1, 'Java零基础入门', 'https://via.placeholder.com/300x200', '从零开始学习Java编程，适合零基础小白', 'Java', 'BEGINNER', 'PUBLISHED', 2, '张老师', 156, 20, 600, 1234, 89, 45, 1),
(2, 'Spring Boot实战开发', 'https://via.placeholder.com/300x200', '通过实战项目掌握Spring Boot开发技能', 'Java', 'INTERMEDIATE', 'PUBLISHED', 2, '张老师', 98, 25, 800, 876, 67, 32, 2),
(3, '数据结构与算法', 'https://via.placeholder.com/300x200', '系统学习常用数据结构和算法', '算法', 'INTERMEDIATE', 'PUBLISHED', 2, '张老师', 234, 30, 1000, 2345, 156, 78, 3),
(4, 'MySQL数据库入门', 'https://via.placeholder.com/300x200', '掌握MySQL数据库基础知识和SQL语句', '数据库', 'BEGINNER', 'PUBLISHED', 2, '张老师', 189, 15, 450, 987, 54, 28, 4),
(5, 'Vue3前端开发', 'https://via.placeholder.com/300x200', 'Vue3框架从入门到精通', '前端', 'INTERMEDIATE', 'PUBLISHED', 2, '张老师', 145, 22, 700, 1123, 76, 41, 5);

-- ====================================================
-- 3. 初始化章节数据
-- ====================================================

INSERT INTO `chapters` (`id`, `course_id`, `title`, `content`, `duration`, `sort_order`, `is_free`, `view_count`) VALUES
(1, 1, '第1章：Java简介', '# Java简介\n\nJava是一门面向对象的编程语言...', 30, 1, 1, 567),
(2, 1, '第2章：环境搭建', '# 环境搭建\n\n本章将介绍如何安装JDK和IDE...', 45, 2, 1, 489),
(3, 1, '第3章：Hello World', '# Hello World\n\n编写第一个Java程序...', 25, 3, 1, 423),
(4, 1, '第4章：数据类型', '# 数据类型\n\nJava的基本数据类型包括...', 40, 4, 0, 234),
(5, 1, '第5章：运算符', '# 运算符\n\n学习Java中的各种运算符...', 35, 5, 0, 198);

-- ====================================================
-- 4. 初始化问题数据
-- ====================================================

INSERT INTO `questions` (`id`, `user_id`, `nickname`, `avatar`, `title`, `content`, `tags`, `bounty_score`, `status`, `accepted_answer_id`, `view_count`, `answer_count`, `like_count`, `favorite_count`) VALUES
(1, 3, '编程小白', 'https://via.placeholder.com/100', 'Java中ArrayList和LinkedList有什么区别？', '# 问题描述\n\n初学Java，不太理解ArrayList和LinkedList的区别，什么时候该用哪个？', 'Java,集合框架', 10, 'SOLVED', 1, 234, 3, 12, 5),
(2, 4, '代码狂人', 'https://via.placeholder.com/100', 'Spring Boot如何配置多数据源？', '# 问题描述\n\n项目需要连接两个数据库，如何在Spring Boot中配置多数据源？', 'Spring Boot,数据库', 20, 'OPEN', NULL, 156, 2, 8, 3),
(3, 3, '编程小白', 'https://via.placeholder.com/100', 'MySQL索引优化技巧有哪些？', '# 问题描述\n\n数据库查询很慢，想了解MySQL索引优化的技巧', 'MySQL,索引,优化', 15, 'OPEN', NULL, 198, 1, 6, 4);

-- ====================================================
-- 5. 初始化回答数据
-- ====================================================

INSERT INTO `answers` (`id`, `question_id`, `user_id`, `nickname`, `avatar`, `content`, `is_accepted`, `like_count`, `comment_count`) VALUES
(1, 1, 5, '技术大牛', 'https://via.placeholder.com/100', '# ArrayList vs LinkedList\n\n## 底层实现\n- ArrayList：基于动态数组实现\n- LinkedList：基于双向链表实现\n\n## 性能对比\n- 随机访问：ArrayList O(1)，LinkedList O(n)\n- 插入删除：ArrayList O(n)，LinkedList O(1)\n\n## 使用场景\n- 频繁随机访问：使用ArrayList\n- 频繁插入删除：使用LinkedList', 1, 15, 2),
(2, 1, 4, '代码狂人', 'https://via.placeholder.com/100', 'ArrayList适合读多写少的场景，LinkedList适合写多读少的场景。', 0, 5, 0),
(3, 2, 5, '技术大牛', 'https://via.placeholder.com/100', '# Spring Boot多数据源配置\n\n需要配置多个DataSource Bean，并使用@Primary注解指定默认数据源...', 0, 8, 1);

-- ====================================================
-- 6. 初始化文章数据
-- ====================================================

INSERT INTO `articles` (`id`, `user_id`, `nickname`, `avatar`, `title`, `cover`, `summary`, `content`, `tags`, `status`, `view_count`, `like_count`, `favorite_count`, `comment_count`) VALUES
(1, 5, '技术大牛', 'https://via.placeholder.com/100', 'Java虚拟机JVM原理详解', 'https://via.placeholder.com/600x400', '深入理解JVM的工作原理和内存模型', '# JVM原理详解\n\n## 1. JVM架构\n\n## 2. 类加载机制\n\n## 3. 内存模型...', 'Java,JVM,原理', 'PUBLISHED', 456, 34, 18, 5),
(2, 4, '代码狂人', 'https://via.placeholder.com/100', 'Redis缓存设计最佳实践', 'https://via.placeholder.com/600x400', 'Redis在实际项目中的应用经验总结', '# Redis缓存设计\n\n## 1. 缓存策略\n\n## 2. 缓存雪崩...', 'Redis,缓存,架构', 'PUBLISHED', 389, 28, 15, 3),
(3, 5, '技术大牛', 'https://via.placeholder.com/100', 'RESTful API设计规范', 'https://via.placeholder.com/600x400', 'API设计的最佳实践和规范', '# RESTful API设计规范\n\n## 1. URL设计\n\n## 2. HTTP方法...', 'API,RESTful,规范', 'PUBLISHED', 567, 45, 22, 7);

-- ====================================================
-- 7. 初始化代码片段数据
-- ====================================================

INSERT INTO `code_snippets` (`id`, `user_id`, `nickname`, `avatar`, `title`, `description`, `language`, `code`, `tags`, `fork_from_id`, `fork_count`, `view_count`, `like_count`, `favorite_count`) VALUES
(1, 5, '技术大牛', 'https://via.placeholder.com/100', '快速排序算法实现', 'Java实现的快速排序算法', 'Java', 'public class QuickSort {\n    public static void quickSort(int[] arr, int left, int right) {\n        if (left < right) {\n            int pivot = partition(arr, left, right);\n            quickSort(arr, left, pivot - 1);\n            quickSort(arr, pivot + 1, right);\n        }\n    }\n    \n    private static int partition(int[] arr, int left, int right) {\n        int pivot = arr[right];\n        int i = left - 1;\n        for (int j = left; j < right; j++) {\n            if (arr[j] < pivot) {\n                i++;\n                swap(arr, i, j);\n            }\n        }\n        swap(arr, i + 1, right);\n        return i + 1;\n    }\n    \n    private static void swap(int[] arr, int i, int j) {\n        int temp = arr[i];\n        arr[i] = arr[j];\n        arr[j] = temp;\n    }\n}', '算法,排序', NULL, 5, 123, 18, 9),
(2, 4, '代码狂人', 'https://via.placeholder.com/100', 'Redis分布式锁', 'Redis实现的分布式锁', 'Java', '@Component\npublic class RedisLock {\n    @Autowired\n    private StringRedisTemplate redisTemplate;\n    \n    public boolean tryLock(String key, String value, long timeout) {\n        Boolean result = redisTemplate.opsForValue()\n            .setIfAbsent(key, value, timeout, TimeUnit.SECONDS);\n        return result != null && result;\n    }\n    \n    public void unlock(String key, String value) {\n        String currentValue = redisTemplate.opsForValue().get(key);\n        if (value.equals(currentValue)) {\n            redisTemplate.delete(key);\n        }\n    }\n}', 'Redis,分布式锁', NULL, 8, 234, 25, 12);

-- ====================================================
-- 8. 初始化打卡记录数据
-- ====================================================

INSERT INTO `check_in_logs` (`id`, `user_id`, `check_in_date`, `continuous_days`, `study_minutes`, `score_earned`, `remark`) VALUES
(1, 3, CURDATE(), 7, 120, 5, '今天学习了Java基础'),
(2, 4, CURDATE(), 15, 180, 5, '完成了Spring Boot练习'),
(3, 5, CURDATE(), 30, 240, 5, '研究JVM原理');

-- ====================================================
-- 9. 初始化积分记录数据
-- ====================================================

INSERT INTO `score_logs` (`id`, `user_id`, `score_change`, `after_score`, `source_type`, `source_id`, `description`) VALUES
(1, 3, 5, 150, 'CHECK_IN', 1, '每日签到'),
(2, 4, 20, 800, 'PUBLISH_TUTORIAL', 1, '发布教程'),
(3, 5, 20, 3500, 'ANSWER_ACCEPTED', 1, '答案被采纳');

-- ====================================================
-- 10. 初始化通知数据
-- ====================================================

INSERT INTO `notifications` (`id`, `user_id`, `type`, `title`, `content`, `related_type`, `related_id`, `is_read`) VALUES
(1, 3, 'SYSTEM', '欢迎加入编程学习交流平台', '欢迎您加入我们的学习社区，开始您的编程之旅！', NULL, NULL, 0),
(2, 3, 'ANSWER', '您的问题收到新回答', '您的问题《Java中ArrayList和LinkedList有什么区别？》收到了新的回答', 'QUESTION', 1, 0),
(3, 5, 'ACCEPT', '您的回答被采纳', '您的回答被提问者采纳，获得20积分奖励', 'ANSWER', 1, 0);
