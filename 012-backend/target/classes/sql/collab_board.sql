CREATE DATABASE IF NOT EXISTS collab_board DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE collab_board;

CREATE TABLE `user` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `username` VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    `password` VARCHAR(100) NOT NULL COMMENT '密码',
    `nickname` VARCHAR(50) NOT NULL COMMENT '昵称',
    `avatar` VARCHAR(200) COMMENT '头像URL',
    `email` VARCHAR(100) COMMENT '邮箱',
    `phone` VARCHAR(20) COMMENT '手机号',
    `signature` VARCHAR(200) COMMENT '个性签名',
    `doc_count` INT DEFAULT 0 COMMENT '文档数',
    `collab_count` INT DEFAULT 0 COMMENT '协作次数',
    `team_count` INT DEFAULT 0 COMMENT '团队数',
    `role` VARCHAR(20) DEFAULT 'USER' COMMENT '角色',
    `status` TINYINT DEFAULT 1 COMMENT '状态0禁用1正常',
    `last_login_time` DATETIME COMMENT '最后登录时间',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_username (`username`),
    INDEX idx_email (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

CREATE TABLE `document` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL COMMENT '创建者ID',
    `team_id` BIGINT DEFAULT 0 COMMENT '团队ID',
    `folder_id` BIGINT DEFAULT 0 COMMENT '文件夹ID',
    `title` VARCHAR(200) NOT NULL COMMENT '文档标题',
    `doc_type` VARCHAR(20) NOT NULL COMMENT '文档类型',
    `content` LONGTEXT COMMENT '文档内容',
    `cover_url` VARCHAR(200) COMMENT '封面URL',
    `description` TEXT COMMENT '文档描述',
    `tags` VARCHAR(200) COMMENT '标签',
    `view_count` INT DEFAULT 0 COMMENT '查看次数',
    `edit_count` INT DEFAULT 0 COMMENT '编辑次数',
    `collab_count` INT DEFAULT 0 COMMENT '协作人数',
    `is_public` TINYINT DEFAULT 0 COMMENT '是否公开',
    `is_template` TINYINT DEFAULT 0 COMMENT '是否模板',
    `is_starred` TINYINT DEFAULT 0 COMMENT '是否星标',
    `share_link` VARCHAR(100) COMMENT '分享链接',
    `share_password` VARCHAR(20) COMMENT '分享密码',
    `share_expire_time` DATETIME COMMENT '分享过期时间',
    `status` TINYINT DEFAULT 1 COMMENT '状态0草稿1正常2已删除',
    `delete_time` DATETIME COMMENT '删除时间',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_user_id (`user_id`),
    INDEX idx_team_id (`team_id`),
    INDEX idx_folder_id (`folder_id`),
    INDEX idx_status (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文档表';

CREATE TABLE `folder` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL COMMENT '创建者ID',
    `team_id` BIGINT DEFAULT 0 COMMENT '团队ID',
    `parent_id` BIGINT DEFAULT 0 COMMENT '父文件夹ID',
    `folder_name` VARCHAR(100) NOT NULL COMMENT '文件夹名称',
    `description` VARCHAR(200) COMMENT '描述',
    `doc_count` INT DEFAULT 0 COMMENT '文档数量',
    `sort_order` INT DEFAULT 0 COMMENT '排序',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_user_id (`user_id`),
    INDEX idx_team_id (`team_id`),
    INDEX idx_parent_id (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件夹表';

CREATE TABLE `document_version` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `document_id` BIGINT NOT NULL COMMENT '文档ID',
    `version_number` INT NOT NULL COMMENT '版本号',
    `content` LONGTEXT COMMENT '版本内容',
    `version_name` VARCHAR(100) COMMENT '版本名称',
    `change_log` TEXT COMMENT '变更日志',
    `file_size` BIGINT COMMENT '文件大小',
    `create_user_id` BIGINT NOT NULL COMMENT '创建者ID',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_document_id (`document_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文档版本表';

CREATE TABLE `collaboration` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `document_id` BIGINT NOT NULL COMMENT '文档ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `permission` VARCHAR(20) NOT NULL COMMENT '权限',
    `invite_user_id` BIGINT COMMENT '邀请人ID',
    `is_active` TINYINT DEFAULT 1 COMMENT '是否活跃',
    `last_edit_time` DATETIME COMMENT '最后编辑时间',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_document_id (`document_id`),
    INDEX idx_user_id (`user_id`),
    UNIQUE KEY uk_doc_user (`document_id`, `user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='协作表';

CREATE TABLE `team` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `team_name` VARCHAR(100) NOT NULL COMMENT '团队名称',
    `description` TEXT COMMENT '团队描述',
    `avatar` VARCHAR(200) COMMENT '团队头像',
    `creator_id` BIGINT NOT NULL COMMENT '创建者ID',
    `member_count` INT DEFAULT 0 COMMENT '成员数',
    `doc_count` INT DEFAULT 0 COMMENT '文档数',
    `status` TINYINT DEFAULT 1 COMMENT '状态',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_creator_id (`creator_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='团队表';

CREATE TABLE `team_member` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `team_id` BIGINT NOT NULL COMMENT '团队ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `role` VARCHAR(20) NOT NULL COMMENT '角色',
    `join_time` DATETIME COMMENT '加入时间',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_team_id (`team_id`),
    INDEX idx_user_id (`user_id`),
    UNIQUE KEY uk_team_user (`team_id`, `user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='团队成员表';

CREATE TABLE `template` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL COMMENT '创建者ID',
    `team_id` BIGINT DEFAULT 0 COMMENT '团队ID',
    `template_name` VARCHAR(100) NOT NULL COMMENT '模板名称',
    `template_type` VARCHAR(20) NOT NULL COMMENT '模板类型',
    `category` VARCHAR(50) COMMENT '分类',
    `cover_url` VARCHAR(200) COMMENT '封面URL',
    `description` TEXT COMMENT '模板描述',
    `content` LONGTEXT COMMENT '模板内容',
    `use_count` INT DEFAULT 0 COMMENT '使用次数',
    `collect_count` INT DEFAULT 0 COMMENT '收藏次数',
    `rating` DECIMAL(3,2) DEFAULT 0 COMMENT '评分',
    `is_public` TINYINT DEFAULT 0 COMMENT '是否公开',
    `is_official` TINYINT DEFAULT 0 COMMENT '是否官方',
    `status` TINYINT DEFAULT 1 COMMENT '状态',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_user_id (`user_id`),
    INDEX idx_type (`template_type`),
    INDEX idx_public (`is_public`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='模板表';

CREATE TABLE `template_collect` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `template_id` BIGINT NOT NULL COMMENT '模板ID',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_user_id (`user_id`),
    INDEX idx_template_id (`template_id`),
    UNIQUE KEY uk_user_template (`user_id`, `template_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='模板收藏表';

CREATE TABLE `document_collect` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `document_id` BIGINT NOT NULL COMMENT '文档ID',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_user_id (`user_id`),
    INDEX idx_document_id (`document_id`),
    UNIQUE KEY uk_user_document (`user_id`, `document_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文档收藏表';

CREATE TABLE `recent_visit` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `document_id` BIGINT NOT NULL COMMENT '文档ID',
    `visit_time` DATETIME NOT NULL COMMENT '访问时间',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_user_id (`user_id`),
    INDEX idx_visit_time (`visit_time`),
    UNIQUE KEY uk_user_document (`user_id`, `document_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='最近访问表';

CREATE TABLE `notification` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL COMMENT '接收用户ID',
    `from_user_id` BIGINT COMMENT '发送用户ID',
    `type` VARCHAR(20) NOT NULL COMMENT '类型',
    `title` VARCHAR(100) COMMENT '标题',
    `content` VARCHAR(500) COMMENT '通知内容',
    `related_id` BIGINT COMMENT '关联ID',
    `related_type` VARCHAR(20) COMMENT '关联类型',
    `is_read` TINYINT DEFAULT 0 COMMENT '是否已读',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_user_id (`user_id`),
    INDEX idx_is_read (`is_read`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息通知表';

CREATE TABLE `operation_log` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `document_id` BIGINT COMMENT '文档ID',
    `operation_type` VARCHAR(50) NOT NULL COMMENT '操作类型',
    `operation_detail` TEXT COMMENT '操作详情',
    `ip_address` VARCHAR(50) COMMENT 'IP地址',
    `user_agent` VARCHAR(200) COMMENT '用户代理',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_user_id (`user_id`),
    INDEX idx_document_id (`document_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';

CREATE TABLE `attachment` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `document_id` BIGINT NOT NULL COMMENT '文档ID',
    `user_id` BIGINT NOT NULL COMMENT '上传者ID',
    `file_name` VARCHAR(200) NOT NULL COMMENT '文件名',
    `file_size` BIGINT COMMENT '文件大小',
    `file_type` VARCHAR(50) COMMENT '文件类型',
    `file_url` VARCHAR(200) NOT NULL COMMENT '文件URL',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_document_id (`document_id`),
    INDEX idx_user_id (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='附件表';

INSERT INTO `user` (`username`, `password`, `nickname`, `email`, `role`, `status`) VALUES
('admin', 'e10adc3949ba59abbe56e057f20f883e', '管理员', 'admin@collabboard.com', 'ADMIN', 1),
('test1', 'e10adc3949ba59abbe56e057f20f883e', '测试用户1', 'test1@collabboard.com', 'USER', 1),
('test2', 'e10adc3949ba59abbe56e057f20f883e', '测试用户2', 'test2@collabboard.com', 'USER', 1),
('test3', 'e10adc3949ba59abbe56e057f20f883e', '测试用户3', 'test3@collabboard.com', 'USER', 1),
('test4', 'e10adc3949ba59abbe56e057f20f883e', '测试用户4', 'test4@collabboard.com', 'USER', 1);

INSERT INTO `template` (`user_id`, `template_name`, `template_type`, `category`, `description`, `content`, `is_public`, `is_official`, `status`) VALUES
(1, '空白白板', 'BOARD', '基础', '空白画布，自由创作', '{}', 1, 1, 1),
(1, '流程图模板', 'BOARD', '流程图', '标准流程图模板', '{}', 1, 1, 1),
(1, 'UML类图', 'BOARD', 'UML', 'UML类图模板', '{}', 1, 1, 1),
(1, '思维导图', 'MINDMAP', '思维导图', '经典思维导图模板', '{}', 1, 1, 1),
(1, '组织架构图', 'MINDMAP', '组织架构', '企业组织架构模板', '{}', 1, 1, 1),
(1, '会议纪要', 'NOTE', '会议', '会议纪要模板', '# 会议纪要\n\n## 会议信息\n- 时间：\n- 地点：\n- 参会人：\n\n## 会议议程\n\n## 讨论内容\n\n## 待办事项', 1, 1, 1),
(1, '日报模板', 'NOTE', '日报', '每日工作日报', '# 日报\n\n## 今日完成\n\n## 明日计划\n\n## 问题与建议', 1, 1, 1),
(1, '项目方案', 'NOTE', '方案', '项目方案模板', '# 项目方案\n\n## 项目背景\n\n## 项目目标\n\n## 实施方案\n\n## 预期成果', 1, 1, 1);

