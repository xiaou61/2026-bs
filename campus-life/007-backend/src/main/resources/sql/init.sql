CREATE DATABASE IF NOT EXISTS volunteer_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE volunteer_db;

CREATE TABLE IF NOT EXISTS `user` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
  `password` VARCHAR(100) NOT NULL COMMENT '密码',
  `name` VARCHAR(50) NOT NULL COMMENT '真实姓名',
  `phone` VARCHAR(20) COMMENT '手机号',
  `role` VARCHAR(20) NOT NULL COMMENT '角色',
  `total_points` INT DEFAULT 0 COMMENT '总积分',
  `available_points` INT DEFAULT 0 COMMENT '可用积分',
  `volunteer_hours` DOUBLE DEFAULT 0 COMMENT '志愿时长',
  `status` INT DEFAULT 1 COMMENT '状态',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  INDEX idx_username (`username`),
  INDEX idx_role (`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

CREATE TABLE IF NOT EXISTS `activity` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `title` VARCHAR(200) NOT NULL COMMENT '活动标题',
  `description` TEXT COMMENT '活动描述',
  `location` VARCHAR(200) COMMENT '活动地点',
  `start_time` DATETIME NOT NULL COMMENT '开始时间',
  `end_time` DATETIME NOT NULL COMMENT '结束时间',
  `max_participants` INT NOT NULL COMMENT '最大参与人数',
  `current_participants` INT DEFAULT 0 COMMENT '当前报名人数',
  `points` INT NOT NULL COMMENT '活动积分',
  `hours` DOUBLE NOT NULL COMMENT '志愿时长',
  `status` INT DEFAULT 1 COMMENT '状态',
  `publisher_id` BIGINT NOT NULL COMMENT '发布者ID',
  `publisher_name` VARCHAR(50) COMMENT '发布者姓名',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  INDEX idx_status (`status`),
  INDEX idx_publisher (`publisher_id`),
  INDEX idx_start_time (`start_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='活动表';

CREATE TABLE IF NOT EXISTS `enrollment` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `activity_id` BIGINT NOT NULL COMMENT '活动ID',
  `activity_title` VARCHAR(200) COMMENT '活动标题',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `user_name` VARCHAR(50) COMMENT '用户姓名',
  `phone` VARCHAR(20) COMMENT '联系电话',
  `status` INT DEFAULT 0 COMMENT '状态',
  `apply_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '报名时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  INDEX idx_activity (`activity_id`),
  INDEX idx_user (`user_id`),
  INDEX idx_status (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='报名表';

CREATE TABLE IF NOT EXISTS `attendance` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `activity_id` BIGINT NOT NULL COMMENT '活动ID',
  `activity_title` VARCHAR(200) COMMENT '活动标题',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `user_name` VARCHAR(50) COMMENT '用户姓名',
  `check_in_time` DATETIME NOT NULL COMMENT '签到时间',
  `check_out_time` DATETIME COMMENT '签退时间',
  `actual_hours` DOUBLE COMMENT '实际时长',
  `points` INT COMMENT '获得积分',
  `status` INT DEFAULT 0 COMMENT '状态',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  INDEX idx_activity (`activity_id`),
  INDEX idx_user (`user_id`),
  INDEX idx_status (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='签到表';

CREATE TABLE IF NOT EXISTS `points_record` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `user_name` VARCHAR(50) COMMENT '用户姓名',
  `type` INT NOT NULL COMMENT '类型',
  `points` INT NOT NULL COMMENT '积分变动',
  `balance` INT NOT NULL COMMENT '变动后余额',
  `related_id` BIGINT COMMENT '关联ID',
  `related_title` VARCHAR(200) COMMENT '关联标题',
  `remark` VARCHAR(500) COMMENT '备注',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  INDEX idx_user (`user_id`),
  INDEX idx_type (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='积分记录表';

CREATE TABLE IF NOT EXISTS `reward` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(200) NOT NULL COMMENT '商品名称',
  `description` TEXT COMMENT '商品描述',
  `image` VARCHAR(500) COMMENT '商品图片',
  `points` INT NOT NULL COMMENT '所需积分',
  `stock` INT NOT NULL COMMENT '库存数量',
  `exchange_count` INT DEFAULT 0 COMMENT '兑换次数',
  `status` INT DEFAULT 1 COMMENT '状态',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  INDEX idx_status (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='奖励表';

CREATE TABLE IF NOT EXISTS `exchange_record` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `reward_id` BIGINT NOT NULL COMMENT '商品ID',
  `reward_name` VARCHAR(200) COMMENT '商品名称',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `user_name` VARCHAR(50) COMMENT '用户姓名',
  `points` INT NOT NULL COMMENT '消耗积分',
  `status` INT DEFAULT 0 COMMENT '状态',
  `phone` VARCHAR(20) COMMENT '联系电话',
  `address` VARCHAR(500) COMMENT '收货地址',
  `remark` VARCHAR(500) COMMENT '备注',
  `exchange_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '兑换时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  INDEX idx_reward (`reward_id`),
  INDEX idx_user (`user_id`),
  INDEX idx_status (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='兑换记录表';

INSERT INTO `user` (`username`, `password`, `name`, `phone`, `role`, `total_points`, `available_points`, `volunteer_hours`, `status`) VALUES
('admin', 'e10adc3949ba59abbe56e057f20f883e', '系统管理员', '13800138000', 'ADMIN', 0, 0, 0, 1),
('volunteer', 'e10adc3949ba59abbe56e057f20f883e', '测试志愿者', '13900139000', 'VOLUNTEER', 100, 100, 5.5, 1),
('zhangsan', 'e10adc3949ba59abbe56e057f20f883e', '张三', '13700137000', 'VOLUNTEER', 80, 80, 4.0, 1),
('lisi', 'e10adc3949ba59abbe56e057f20f883e', '李四', '13600136000', 'VOLUNTEER', 120, 120, 6.0, 1);

INSERT INTO `activity` (`title`, `description`, `location`, `start_time`, `end_time`, `max_participants`, `current_participants`, `points`, `hours`, `status`, `publisher_id`, `publisher_name`) VALUES
('社区环保志愿活动', '参与社区环境清洁，美化社区环境', '阳光社区', '2025-10-15 09:00:00', '2025-10-15 12:00:00', 20, 5, 30, 3, 1, 1, '系统管理员'),
('敬老院关爱行动', '前往敬老院陪伴老人，开展文艺活动', '幸福敬老院', '2025-10-18 14:00:00', '2025-10-18 17:00:00', 15, 3, 40, 3, 1, 1, '系统管理员'),
('图书馆整理志愿服务', '协助图书馆整理书籍，维护阅读环境', '市图书馆', '2025-10-20 08:00:00', '2025-10-20 12:00:00', 10, 2, 50, 4, 1, 1, '系统管理员'),
('交通文明引导', '在交通路口引导市民文明出行', '市中心广场', '2025-10-22 07:00:00', '2025-10-22 09:00:00', 8, 1, 25, 2, 1, 1, '系统管理员'),
('儿童福利院陪伴活动', '陪伴福利院儿童，开展游戏和学习辅导', '儿童福利院', '2025-10-25 09:00:00', '2025-10-25 16:00:00', 12, 4, 70, 7, 1, 1, '系统管理员');

INSERT INTO `reward` (`name`, `description`, `image`, `points`, `stock`, `exchange_count`, `status`) VALUES
('志愿者纪念T恤', '印有志愿者标志的纪念T恤，多种颜色可选', '', 50, 100, 0, 1),
('环保购物袋', '结实耐用的环保购物袋，提倡绿色生活', '', 20, 200, 0, 1),
('志愿者证书', '由组织颁发的正式志愿者证书', '', 100, 50, 0, 1),
('爱心雨伞', '印有公益标志的雨伞', '', 60, 80, 0, 1),
('保温杯', '不锈钢保温杯，志愿者专属款', '', 80, 60, 0, 1),
('电影票兑换券', '可在指定影院兑换的电影票', '', 40, 100, 0, 1),
('图书代金券', '价值50元的图书代金券', '', 45, 150, 0, 1),
('运动毛巾', '吸汗透气的运动毛巾', '', 30, 120, 0, 1);

