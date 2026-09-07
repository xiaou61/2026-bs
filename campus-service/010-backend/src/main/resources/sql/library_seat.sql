CREATE DATABASE IF NOT EXISTS library_seat CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE library_seat;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `student_no` VARCHAR(20) NOT NULL UNIQUE COMMENT '学号',
  `password` VARCHAR(100) NOT NULL COMMENT '密码',
  `name` VARCHAR(50) NOT NULL COMMENT '姓名',
  `gender` TINYINT DEFAULT 0 COMMENT '性别 0女 1男',
  `college` VARCHAR(50) COMMENT '学院',
  `major` VARCHAR(50) COMMENT '专业',
  `phone` VARCHAR(20) COMMENT '联系电话',
  `credit_score` INT DEFAULT 100 COMMENT '信用分',
  `role` VARCHAR(20) DEFAULT 'USER' COMMENT '角色',
  `status` TINYINT DEFAULT 1 COMMENT '状态 0禁用 1正常 2临时禁用',
  `ban_until` DATETIME COMMENT '禁用到期时间',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX idx_student_no (`student_no`),
  INDEX idx_credit_score (`credit_score`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

DROP TABLE IF EXISTS `floor`;
CREATE TABLE `floor` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(50) NOT NULL COMMENT '楼层名称',
  `description` VARCHAR(200) COMMENT '描述',
  `sort_order` INT DEFAULT 0 COMMENT '排序',
  `status` TINYINT DEFAULT 1 COMMENT '状态 0禁用 1正常',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='楼层表';

DROP TABLE IF EXISTS `area`;
CREATE TABLE `area` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `floor_id` BIGINT NOT NULL COMMENT '所属楼层',
  `name` VARCHAR(50) NOT NULL COMMENT '区域名称',
  `description` VARCHAR(200) COMMENT '描述',
  `is_hot` TINYINT DEFAULT 0 COMMENT '是否热门区域',
  `min_credit` INT DEFAULT 0 COMMENT '最低信用分要求',
  `sort_order` INT DEFAULT 0 COMMENT '排序',
  `status` TINYINT DEFAULT 1 COMMENT '状态 0禁用 1正常',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX idx_floor_id (`floor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='区域表';

DROP TABLE IF EXISTS `seat`;
CREATE TABLE `seat` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `area_id` BIGINT NOT NULL COMMENT '所属区域',
  `seat_no` VARCHAR(20) NOT NULL COMMENT '座位编号',
  `seat_type` VARCHAR(20) COMMENT '座位类型',
  `has_power` TINYINT DEFAULT 0 COMMENT '是否有电源',
  `has_lamp` TINYINT DEFAULT 0 COMMENT '是否有台灯',
  `position_x` INT DEFAULT 0 COMMENT 'X坐标',
  `position_y` INT DEFAULT 0 COMMENT 'Y坐标',
  `hot_score` INT DEFAULT 0 COMMENT '热门度分数',
  `total_book_count` INT DEFAULT 0 COMMENT '总预约次数',
  `status` TINYINT DEFAULT 1 COMMENT '状态 0禁用 1可用 2维修中',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX idx_area_id (`area_id`),
  INDEX idx_seat_no (`seat_no`),
  INDEX idx_hot_score (`hot_score`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='座位表';

DROP TABLE IF EXISTS `booking`;
CREATE TABLE `booking` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `seat_id` BIGINT NOT NULL COMMENT '座位ID',
  `booking_date` DATE NOT NULL COMMENT '预约日期',
  `time_slot` VARCHAR(20) NOT NULL COMMENT '时段',
  `start_time` DATETIME NOT NULL COMMENT '开始时间',
  `end_time` DATETIME NOT NULL COMMENT '结束时间',
  `check_in_time` DATETIME COMMENT '签到时间',
  `check_out_time` DATETIME COMMENT '签退时间',
  `status` VARCHAR(20) NOT NULL COMMENT '状态',
  `leave_time` DATETIME COMMENT '临时离开时间',
  `cancel_time` DATETIME COMMENT '取消时间',
  `cancel_reason` VARCHAR(200) COMMENT '取消原因',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX idx_user_id (`user_id`),
  INDEX idx_seat_id (`seat_id`),
  INDEX idx_booking_date (`booking_date`),
  INDEX idx_status (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='预约记录表';

DROP TABLE IF EXISTS `violation`;
CREATE TABLE `violation` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `booking_id` BIGINT NOT NULL COMMENT '预约ID',
  `violation_type` VARCHAR(20) NOT NULL COMMENT '违约类型',
  `deduct_score` INT NOT NULL COMMENT '扣除信用分',
  `violation_time` DATETIME NOT NULL COMMENT '违约时间',
  `appeal_status` VARCHAR(20) COMMENT '申诉状态',
  `appeal_reason` TEXT COMMENT '申诉理由',
  `appeal_images` VARCHAR(500) COMMENT '申诉图片',
  `appeal_time` DATETIME COMMENT '申诉时间',
  `handle_admin_id` BIGINT COMMENT '处理管理员ID',
  `handle_result` VARCHAR(200) COMMENT '处理结果',
  `handle_time` DATETIME COMMENT '处理时间',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  INDEX idx_user_id (`user_id`),
  INDEX idx_booking_id (`booking_id`),
  INDEX idx_appeal_status (`appeal_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='违约记录表';

DROP TABLE IF EXISTS `credit_log`;
CREATE TABLE `credit_log` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `change_type` VARCHAR(20) NOT NULL COMMENT '变动类型',
  `change_score` INT NOT NULL COMMENT '变动分数',
  `before_score` INT NOT NULL COMMENT '变动前分数',
  `after_score` INT NOT NULL COMMENT '变动后分数',
  `related_id` BIGINT COMMENT '关联ID',
  `reason` VARCHAR(200) COMMENT '原因',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  INDEX idx_user_id (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='信用分变动记录表';

DROP TABLE IF EXISTS `system_config`;
CREATE TABLE `system_config` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `config_key` VARCHAR(50) NOT NULL UNIQUE COMMENT '配置键',
  `config_value` VARCHAR(200) NOT NULL COMMENT '配置值',
  `config_name` VARCHAR(50) NOT NULL COMMENT '配置名称',
  `config_desc` VARCHAR(200) COMMENT '配置描述',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX idx_config_key (`config_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置表';

DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `user_id` BIGINT COMMENT '用户ID',
  `title` VARCHAR(100) NOT NULL COMMENT '标题',
  `content` TEXT NOT NULL COMMENT '内容',
  `type` VARCHAR(20) NOT NULL COMMENT '类型',
  `is_read` TINYINT DEFAULT 0 COMMENT '是否已读',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  INDEX idx_user_id (`user_id`),
  INDEX idx_is_read (`is_read`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='通知消息表';

DROP TABLE IF EXISTS `time_slot`;
CREATE TABLE `time_slot` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `slot_name` VARCHAR(20) NOT NULL COMMENT '时段名称',
  `slot_code` VARCHAR(20) NOT NULL UNIQUE COMMENT '时段代码',
  `start_time` TIME NOT NULL COMMENT '开始时间',
  `end_time` TIME NOT NULL COMMENT '结束时间',
  `status` TINYINT DEFAULT 1 COMMENT '状态 0禁用 1启用',
  `sort_order` INT DEFAULT 0 COMMENT '排序',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX idx_slot_code (`slot_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='时段配置表';

INSERT INTO `user` (`student_no`, `password`, `name`, `gender`, `college`, `major`, `phone`, `credit_score`, `role`, `status`) VALUES
('admin', 'e10adc3949ba59abbe56e057f20f883e', '管理员', 1, '信息学院', '计算机科学与技术', '13800138000', 100, 'ADMIN', 1),
('20240001', 'e10adc3949ba59abbe56e057f20f883e', '张三', 1, '信息学院', '计算机科学与技术', '13800138001', 100, 'USER', 1),
('20240002', 'e10adc3949ba59abbe56e057f20f883e', '李四', 0, '文学院', '汉语言文学', '13800138002', 95, 'USER', 1),
('20240003', 'e10adc3949ba59abbe56e057f20f883e', '王五', 1, '理学院', '数学与应用数学', '13800138003', 88, 'USER', 1),
('20240004', 'e10adc3949ba59abbe56e057f20f883e', '赵六', 0, '经管学院', '工商管理', '13800138004', 76, 'USER', 1),
('20240005', 'e10adc3949ba59abbe56e057f20f883e', '孙七', 1, '工学院', '机械工程', '13800138005', 55, 'USER', 1);

INSERT INTO `floor` (`name`, `description`, `sort_order`, `status`) VALUES
('一楼', '一楼阅览区', 1, 1),
('二楼', '二楼自习区', 2, 1),
('三楼', '三楼考研区', 3, 1),
('四楼', '四楼专业区', 4, 1);

INSERT INTO `area` (`floor_id`, `name`, `description`, `is_hot`, `min_credit`, `sort_order`, `status`) VALUES
(1, 'A区', '靠窗安静区域', 1, 80, 1, 1),
(1, 'B区', '普通阅览区', 0, 0, 2, 1),
(2, 'C区', '自习区-东侧', 1, 70, 1, 1),
(2, 'D区', '自习区-西侧', 0, 0, 2, 1),
(3, 'E区', '考研专区', 1, 85, 1, 1),
(3, 'F区', '考研普通区', 0, 60, 2, 1),
(4, 'G区', '专业学习区', 0, 0, 1, 1),
(4, 'H区', '小组讨论区', 0, 0, 2, 1);

INSERT INTO `seat` (`area_id`, `seat_no`, `seat_type`, `has_power`, `has_lamp`, `position_x`, `position_y`, `hot_score`, `total_book_count`, `status`) VALUES
(1, 'A-001', '单人座', 1, 1, 1, 1, 0, 0, 1),
(1, 'A-002', '单人座', 1, 1, 2, 1, 0, 0, 1),
(1, 'A-003', '单人座', 1, 0, 3, 1, 0, 0, 1),
(1, 'A-004', '单人座', 1, 0, 4, 1, 0, 0, 1),
(1, 'A-005', '单人座', 0, 1, 5, 1, 0, 0, 1),
(2, 'B-001', '单人座', 1, 0, 1, 1, 0, 0, 1),
(2, 'B-002', '单人座', 1, 0, 2, 1, 0, 0, 1),
(2, 'B-003', '单人座', 0, 0, 3, 1, 0, 0, 1),
(2, 'B-004', '单人座', 0, 0, 4, 1, 0, 0, 1),
(2, 'B-005', '单人座', 0, 0, 5, 1, 0, 0, 1),
(3, 'C-001', '单人座', 1, 1, 1, 1, 0, 0, 1),
(3, 'C-002', '单人座', 1, 1, 2, 1, 0, 0, 1),
(3, 'C-003', '单人座', 1, 1, 3, 1, 0, 0, 1),
(3, 'C-004', '单人座', 1, 0, 4, 1, 0, 0, 1),
(3, 'C-005', '单人座', 1, 0, 5, 1, 0, 0, 1),
(4, 'D-001', '单人座', 1, 0, 1, 1, 0, 0, 1),
(4, 'D-002', '单人座', 1, 0, 2, 1, 0, 0, 1),
(4, 'D-003', '单人座', 0, 0, 3, 1, 0, 0, 1),
(4, 'D-004', '单人座', 0, 0, 4, 1, 0, 0, 1),
(4, 'D-005', '单人座', 0, 0, 5, 1, 0, 0, 1),
(5, 'E-001', '单人座', 1, 1, 1, 1, 0, 0, 1),
(5, 'E-002', '单人座', 1, 1, 2, 1, 0, 0, 1),
(5, 'E-003', '单人座', 1, 1, 3, 1, 0, 0, 1),
(5, 'E-004', '单人座', 1, 1, 4, 1, 0, 0, 1),
(5, 'E-005', '单人座', 1, 1, 5, 1, 0, 0, 1),
(6, 'F-001', '单人座', 1, 0, 1, 1, 0, 0, 1),
(6, 'F-002', '单人座', 1, 0, 2, 1, 0, 0, 1),
(6, 'F-003', '单人座', 1, 0, 3, 1, 0, 0, 1),
(6, 'F-004', '单人座', 0, 0, 4, 1, 0, 0, 1),
(6, 'F-005', '单人座', 0, 0, 5, 1, 0, 0, 1),
(7, 'G-001', '单人座', 1, 1, 1, 1, 0, 0, 1),
(7, 'G-002', '单人座', 1, 1, 2, 1, 0, 0, 1),
(7, 'G-003', '单人座', 1, 0, 3, 1, 0, 0, 1),
(7, 'G-004', '单人座', 0, 0, 4, 1, 0, 0, 1),
(7, 'G-005', '单人座', 0, 0, 5, 1, 0, 0, 1),
(8, 'H-001', '双人座', 1, 1, 1, 1, 0, 0, 1),
(8, 'H-002', '双人座', 1, 1, 2, 1, 0, 0, 1),
(8, 'H-003', '双人座', 1, 0, 3, 1, 0, 0, 1),
(8, 'H-004', '双人座', 0, 0, 4, 1, 0, 0, 1),
(8, 'H-005', '双人座', 0, 0, 5, 1, 0, 0, 1);

INSERT INTO `time_slot` (`slot_name`, `slot_code`, `start_time`, `end_time`, `status`, `sort_order`) VALUES
('上午', 'MORNING', '08:00:00', '12:00:00', 1, 1),
('下午', 'AFTERNOON', '14:00:00', '18:00:00', 1, 2),
('晚上', 'EVENING', '18:30:00', '22:00:00', 1, 3),
('全天', 'ALLDAY', '08:00:00', '22:00:00', 1, 4);

INSERT INTO `system_config` (`config_key`, `config_value`, `config_name`, `config_desc`) VALUES
('advance_days', '2', '预约提前天数', '可以提前几天预约座位'),
('daily_book_limit', '3', '每日预约次数', '每天最多预约几次'),
('checkin_grace_minutes', '15', '签到宽限时间', '预约开始后多久内必须签到'),
('checkout_grace_minutes', '30', '签退宽限时间', '预约结束后多久算违约'),
('initial_credit', '100', '初始信用分', '新用户初始信用分'),
('normal_complete_score', '2', '正常完成加分', '正常签到签退加多少分'),
('no_checkin_deduct', '5', '未签到扣分', '预约后未签到扣多少分'),
('no_checkout_deduct', '3', '未签退扣分', '签到后未签退扣多少分'),
('leave_timeout_deduct', '3', '临时离开超时扣分', '临时离开超时扣多少分');

