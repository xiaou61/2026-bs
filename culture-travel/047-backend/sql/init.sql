-- 剧本杀创作与预约平台数据库初始化脚本
DROP DATABASE IF EXISTS script_kill;
CREATE DATABASE script_kill DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE script_kill;

-- 用户表
CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    real_name VARCHAR(50) COMMENT '真实姓名',
    phone VARCHAR(20) COMMENT '手机号',
    avatar VARCHAR(255) COMMENT '头像',
    role TINYINT DEFAULT 0 COMMENT '角色：0-玩家 1-店家 2-作者 3-管理员',
    status TINYINT DEFAULT 1 COMMENT '状态：0-禁用 1-正常',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT '用户表';

-- 店铺表
CREATE TABLE shop (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    owner_id BIGINT NOT NULL COMMENT '店主用户ID',
    name VARCHAR(100) NOT NULL COMMENT '店铺名称',
    logo VARCHAR(255) COMMENT '店铺logo',
    description TEXT COMMENT '店铺简介',
    address VARCHAR(255) COMMENT '店铺地址',
    phone VARCHAR(20) COMMENT '联系电话',
    business_hours VARCHAR(100) COMMENT '营业时间',
    room_count INT DEFAULT 0 COMMENT '房间数量',
    rating DECIMAL(2,1) DEFAULT 5.0 COMMENT '店铺评分',
    status TINYINT DEFAULT 1 COMMENT '状态：0-关闭 1-营业',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT '店铺表';

-- 房间表
CREATE TABLE room (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    shop_id BIGINT NOT NULL COMMENT '店铺ID',
    name VARCHAR(50) NOT NULL COMMENT '房间名称',
    capacity INT NOT NULL COMMENT '容纳人数',
    description VARCHAR(255) COMMENT '房间描述',
    price DECIMAL(10,2) DEFAULT 0 COMMENT '基础价格',
    status TINYINT DEFAULT 1 COMMENT '状态：0-不可用 1-可用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT '房间表';

-- 剧本分类表
CREATE TABLE script_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL COMMENT '分类名称',
    description VARCHAR(255) COMMENT '分类描述',
    sort INT DEFAULT 0 COMMENT '排序',
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT '剧本分类表';

-- 剧本表
CREATE TABLE script (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    author_id BIGINT COMMENT '作者ID',
    category_id BIGINT COMMENT '分类ID',
    title VARCHAR(100) NOT NULL COMMENT '剧本标题',
    cover VARCHAR(255) COMMENT '封面图',
    description TEXT COMMENT '剧本简介',
    difficulty TINYINT DEFAULT 2 COMMENT '难度：1-新手 2-进阶 3-硬核',
    player_count VARCHAR(20) COMMENT '适合人数',
    duration VARCHAR(50) COMMENT '游戏时长',
    type TINYINT DEFAULT 1 COMMENT '类型：1-情感 2-推理 3-恐怖 4-欢乐 5-机制',
    price DECIMAL(10,2) DEFAULT 0 COMMENT '授权价格',
    view_count INT DEFAULT 0 COMMENT '浏览量',
    like_count INT DEFAULT 0 COMMENT '点赞数',
    status TINYINT DEFAULT 0 COMMENT '状态：0-待审核 1-已上架 2-已下架 3-审核拒绝',
    audit_remark VARCHAR(255) COMMENT '审核备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT '剧本表';

-- 剧本内容表
CREATE TABLE script_content (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    script_id BIGINT NOT NULL COMMENT '剧本ID',
    chapter_name VARCHAR(100) COMMENT '章节名称',
    role_name VARCHAR(50) COMMENT '角色名称',
    content TEXT COMMENT '内容',
    sort INT DEFAULT 0 COMMENT '排序',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT '剧本内容表';

-- 店铺剧本关联表
CREATE TABLE shop_script (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    shop_id BIGINT NOT NULL COMMENT '店铺ID',
    script_id BIGINT NOT NULL COMMENT '剧本ID',
    price DECIMAL(10,2) COMMENT '店铺定价',
    play_count INT DEFAULT 0 COMMENT '已开本次数',
    status TINYINT DEFAULT 1 COMMENT '状态：0-下架 1-上架',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT '店铺剧本关联表';

-- 预约表
CREATE TABLE reservation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_no VARCHAR(32) NOT NULL UNIQUE COMMENT '订单编号',
    user_id BIGINT NOT NULL COMMENT '预约用户ID',
    shop_id BIGINT NOT NULL COMMENT '店铺ID',
    room_id BIGINT COMMENT '房间ID',
    script_id BIGINT COMMENT '剧本ID',
    reservation_date DATE NOT NULL COMMENT '预约日期',
    time_slot VARCHAR(50) COMMENT '时间段',
    player_count INT COMMENT '玩家人数',
    contact_name VARCHAR(50) COMMENT '联系人',
    contact_phone VARCHAR(20) COMMENT '联系电话',
    total_price DECIMAL(10,2) COMMENT '总价',
    remark VARCHAR(255) COMMENT '备注',
    status TINYINT DEFAULT 0 COMMENT '状态：0-待确认 1-已确认 2-进行中 3-已完成 4-已取消',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT '预约表';

-- 评价表
CREATE TABLE review (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    reservation_id BIGINT COMMENT '预约ID',
    shop_id BIGINT COMMENT '店铺ID',
    script_id BIGINT COMMENT '剧本ID',
    rating INT DEFAULT 5 COMMENT '评分1-5',
    content TEXT COMMENT '评价内容',
    images VARCHAR(1000) COMMENT '图片',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT '评价表';

-- 收藏表
CREATE TABLE favorite (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    type TINYINT NOT NULL COMMENT '类型：1-剧本 2-店铺',
    target_id BIGINT NOT NULL COMMENT '目标ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT '收藏表';

-- 系统公告表
CREATE TABLE notice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL COMMENT '标题',
    content TEXT COMMENT '内容',
    type TINYINT DEFAULT 1 COMMENT '类型：1-系统公告 2-活动通知',
    publish_time DATETIME COMMENT '发布时间',
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT '系统公告表';

-- 测试数据
INSERT INTO sys_user (username, password, real_name, phone, role) VALUES
('admin', '123456', '管理员', '13800000000', 3),
('shop1', '123456', '张店长', '13800000001', 1),
('shop2', '123456', '李店长', '13800000002', 1),
('author1', '123456', '王作者', '13800000003', 2),
('author2', '123456', '刘作者', '13800000004', 2),
('user1', '123456', '玩家小明', '13800000005', 0),
('user2', '123456', '玩家小红', '13800000006', 0);

INSERT INTO shop (owner_id, name, description, address, phone, business_hours, room_count, rating) VALUES
(2, '迷雾剧本杀', '专业沉浸式剧本杀体验馆', '北京市朝阳区xxx街道xxx号', '010-12345678', '14:00-23:00', 6, 4.8),
(3, '推理世界', '推理爱好者的天堂', '上海市浦东新区xxx路xxx号', '021-87654321', '13:00-22:00', 4, 4.6);

INSERT INTO room (shop_id, name, capacity, description, price) VALUES
(1, '迷雾厅', 8, '欧式复古风格', 50.00),
(1, '暗夜厅', 6, '暗黑哥特风格', 60.00),
(1, '阳光厅', 10, '明亮温馨风格', 45.00),
(2, '福尔摩斯厅', 6, '英伦侦探风格', 55.00),
(2, '东方快车厅', 8, '列车主题包厢', 65.00);

INSERT INTO script_category (name, description, sort) VALUES
('情感本', '以情感体验为主的剧本', 1),
('推理本', '以逻辑推理为主的剧本', 2),
('恐怖本', '惊悚恐怖题材的剧本', 3),
('欢乐本', '轻松搞笑的剧本', 4),
('机制本', '以游戏机制为主的剧本', 5);

INSERT INTO script (author_id, category_id, title, description, difficulty, player_count, duration, type, price, view_count, like_count, status) VALUES
(4, 1, '年轮', '一段跨越时空的爱情故事', 2, '6人', '4-5小时', 1, 2000.00, 1580, 326, 1),
(4, 2, '迷雾庄园', '古老庄园中的连环谋杀案', 3, '7人', '5-6小时', 2, 2500.00, 2340, 512, 1),
(5, 3, '午夜来电', '一通神秘电话打破平静', 2, '5-6人', '3-4小时', 3, 1800.00, 890, 178, 1),
(5, 4, '欢乐农场', '一场关于农场的搞笑闹剧', 1, '6-8人', '3小时', 4, 1500.00, 1200, 289, 1),
(4, 2, '古董局中局', '围绕神秘古董展开的智力较量', 3, '6人', '4-5小时', 2, 2200.00, 1890, 423, 1);

INSERT INTO shop_script (shop_id, script_id, price, play_count) VALUES
(1, 1, 268.00, 45),
(1, 2, 328.00, 32),
(1, 3, 238.00, 28),
(2, 2, 358.00, 56),
(2, 5, 298.00, 41);

INSERT INTO reservation (order_no, user_id, shop_id, room_id, script_id, reservation_date, time_slot, player_count, contact_name, contact_phone, total_price, status) VALUES
('RES20240115001', 6, 1, 1, 1, '2024-01-20', '14:00-18:00', 6, '小明', '13800000005', 268.00, 3),
('RES20240116001', 7, 1, 2, 3, '2024-01-21', '19:00-22:00', 5, '小红', '13800000006', 238.00, 1),
('RES20240117001', 6, 2, 4, 2, '2024-01-22', '14:00-19:00', 7, '小明', '13800000005', 358.00, 0);

INSERT INTO review (user_id, reservation_id, shop_id, script_id, rating, content) VALUES
(6, 1, 1, 1, 5, '非常棒的体验！DM带入感很强，剧本情感丰富！'),
(7, NULL, 1, NULL, 4, '店铺装修很有氛围，服务不错');

INSERT INTO favorite (user_id, type, target_id) VALUES
(6, 1, 1), (6, 1, 2), (6, 2, 1), (7, 1, 3), (7, 2, 2);

INSERT INTO notice (title, content, type, publish_time) VALUES
('平台上线公告', '欢迎来到剧本杀创作与预约平台！', 1, NOW()),
('新剧本上架活动', '本周新上架5部精品剧本，首单立减20元！', 2, NOW());
