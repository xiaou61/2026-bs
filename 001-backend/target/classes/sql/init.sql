-- 校园事务管理系统数据库初始化脚本

-- 创建数据库
CREATE DATABASE IF NOT EXISTS campus_affairs DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE campus_affairs;

-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码（MD5加密）',
  `role` varchar(20) NOT NULL DEFAULT 'student' COMMENT '角色（admin/teacher/student）',
  `real_name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像地址',
  `student_no` varchar(50) DEFAULT NULL COMMENT '学号/工号',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除（0-未删除，1-已删除）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  KEY `idx_role` (`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 请假申请表
CREATE TABLE IF NOT EXISTS `leave_request` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `student_id` bigint NOT NULL COMMENT '学生ID',
  `reason` varchar(500) NOT NULL COMMENT '请假理由',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `days` int NOT NULL DEFAULT '1' COMMENT '请假天数',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态（0-待审批，1-已通过，2-已驳回）',
  `approver_id` bigint DEFAULT NULL COMMENT '审批人ID',
  `approval_comment` varchar(500) DEFAULT NULL COMMENT '审批意见',
  `approval_time` datetime DEFAULT NULL COMMENT '审批时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_student_id` (`student_id`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='请假申请表';

-- 报修申请表
CREATE TABLE IF NOT EXISTS `repair_request` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `student_id` bigint NOT NULL COMMENT '学生ID',
  `type` varchar(50) NOT NULL COMMENT '报修类型（电器/水管/门窗/其他）',
  `location` varchar(200) NOT NULL COMMENT '报修位置',
  `description` varchar(1000) NOT NULL COMMENT '报修说明',
  `image_url` varchar(1000) DEFAULT NULL COMMENT '图片地址（多张用逗号分隔）',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态（0-未处理，1-处理中，2-已完成）',
  `handler_id` bigint DEFAULT NULL COMMENT '处理人ID',
  `handle_comment` varchar(500) DEFAULT NULL COMMENT '处理备注',
  `handle_time` datetime DEFAULT NULL COMMENT '处理时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_student_id` (`student_id`),
  KEY `idx_status` (`status`),
  KEY `idx_type` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='报修申请表';

-- 公告表
CREATE TABLE IF NOT EXISTS `notice` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(200) NOT NULL COMMENT '标题',
  `content` text NOT NULL COMMENT '内容',
  `category` varchar(50) NOT NULL DEFAULT '通知' COMMENT '分类（通知/活动/系统）',
  `author_id` bigint NOT NULL COMMENT '发布者ID',
  `is_top` tinyint NOT NULL DEFAULT '0' COMMENT '是否置顶（0-否，1-是）',
  `view_count` int NOT NULL DEFAULT '0' COMMENT '浏览次数',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_author_id` (`author_id`),
  KEY `idx_category` (`category`),
  KEY `idx_is_top` (`is_top`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='公告表';

-- 活动表
CREATE TABLE IF NOT EXISTS `activity` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(200) NOT NULL COMMENT '活动名称',
  `description` text NOT NULL COMMENT '活动描述',
  `location` varchar(200) NOT NULL COMMENT '活动地点',
  `activity_time` datetime NOT NULL COMMENT '活动时间',
  `signup_start_time` datetime NOT NULL COMMENT '报名开始时间',
  `signup_end_time` datetime NOT NULL COMMENT '报名结束时间',
  `max_participants` int NOT NULL DEFAULT '100' COMMENT '人数限制',
  `current_participants` int NOT NULL DEFAULT '0' COMMENT '当前报名人数',
  `cover_image` varchar(500) DEFAULT NULL COMMENT '活动封面图',
  `publisher_id` bigint NOT NULL COMMENT '发布者ID',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '活动状态（0-未开始，1-报名中，2-已结束）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_publisher_id` (`publisher_id`),
  KEY `idx_status` (`status`),
  KEY `idx_activity_time` (`activity_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='活动表';

-- 活动报名表
CREATE TABLE IF NOT EXISTS `activity_signup` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `student_id` bigint NOT NULL COMMENT '学生ID',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '报名状态（0-待审核，1-已通过，2-已取消）',
  `remark` varchar(500) DEFAULT NULL COMMENT '报名备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_activity_student` (`activity_id`,`student_id`),
  KEY `idx_student_id` (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='活动报名表';

-- 初始化默认数据
INSERT INTO `user` (`username`, `password`, `role`, `real_name`, `phone`, `email`, `student_no`) VALUES
-- 默认管理员 (密码: admin123)
('admin', '0192023a7bbd73250516f069df18b500', 'admin', '系统管理员', '13800000000', 'admin@campus.edu', 'ADMIN001'),
-- 默认教师 (密码: teacher123) 
('teacher001', '5e543256c480ac577d30f76f9120eb74', 'teacher', '张老师', '13800000001', 'zhang@campus.edu', 'T001'),
-- 默认学生 (密码: student123)
('student001', 'b7f40055515237ae9e6ad8d808769e53', 'student', '李小明', '13800000002', 'li@campus.edu', '2024001'),
('student002', 'b7f40055515237ae9e6ad8d808769e53', 'student', '王小红', '13800000003', 'wang@campus.edu', '2024002');

-- 插入示例公告数据
INSERT INTO `notice` (`title`, `content`, `category`, `author_id`, `is_top`) VALUES
('欢迎使用校园事务管理系统', '本系统为学生、教师提供便捷的校园事务管理服务，包括请假申请、报修申请、公告查看、活动报名等功能。', '系统', 1, 1),
('校园网络维护通知', '定于本周末进行校园网络设备维护，届时网络可能出现短暂中断，请提前做好准备。', '通知', 1, 0);

-- 插入示例活动数据
INSERT INTO `activity` (`name`, `description`, `location`, `activity_time`, `signup_start_time`, `signup_end_time`, `max_participants`, `publisher_id`) VALUES
('春季运动会', '学校一年一度的春季运动会，欢迎全校师生参加。设有田径、球类等多个项目。', '校体育场', '2024-04-15 09:00:00', '2024-04-01 00:00:00', '2024-04-10 23:59:59', 200, 2),
('编程竞赛', '面向全校学生的编程竞赛，设有个人赛和团队赛，获奖者将获得丰厚奖品。', '计算机学院机房', '2024-04-20 14:00:00', '2024-04-05 00:00:00', '2024-04-18 23:59:59', 50, 2);
