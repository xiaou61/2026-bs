-- 活动表字段迁移脚本
-- 如果activity表已存在，需要执行此脚本更新表结构

USE campus_affairs;

-- 删除旧的activity表（如果需要重建）
DROP TABLE IF EXISTS `activity`;

-- 重新创建activity表
CREATE TABLE `activity` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(200) NOT NULL COMMENT '活动标题',
  `description` text NOT NULL COMMENT '活动描述',
  `location` varchar(200) NOT NULL COMMENT '活动地点',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `max_participants` int NOT NULL DEFAULT '100' COMMENT '人数限制',
  `current_participants` int NOT NULL DEFAULT '0' COMMENT '当前报名人数',
  `cover_image` varchar(500) DEFAULT NULL COMMENT '活动封面图',
  `organizer_id` bigint NOT NULL COMMENT '组织者ID',
  `status` varchar(20) NOT NULL DEFAULT '未开始' COMMENT '活动状态（未开始、进行中、已结束）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_organizer_id` (`organizer_id`),
  KEY `idx_status` (`status`),
  KEY `idx_start_time` (`start_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='活动表';

-- 插入示例活动数据
INSERT INTO `activity` (`title`, `description`, `location`, `start_time`, `end_time`, `max_participants`, `organizer_id`, `status`) VALUES
('春季运动会', '学校一年一度的春季运动会，欢迎全校师生参加。设有田径、球类等多个项目。', '校体育场', '2024-04-15 09:00:00', '2024-04-15 17:00:00', 200, 2, '未开始'),
('编程竞赛', '面向全校学生的编程竞赛，设有个人赛和团队赛，获奖者将获得丰厚奖品。', '计算机学院机房', '2024-04-20 14:00:00', '2024-04-20 18:00:00', 50, 2, '未开始');

