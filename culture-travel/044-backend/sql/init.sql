-- 创建数据库
CREATE DATABASE IF NOT EXISTS homestay_booking DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE homestay_booking;

-- 用户表
CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    nickname VARCHAR(50) COMMENT '昵称',
    phone VARCHAR(20) COMMENT '手机号',
    email VARCHAR(100) COMMENT '邮箱',
    avatar VARCHAR(255) COMMENT '头像',
    role TINYINT DEFAULT 0 COMMENT '角色：0-游客 1-房东 2-管理员',
    status TINYINT DEFAULT 1 COMMENT '状态：0-禁用 1-正常',
    deleted TINYINT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 民宿表
CREATE TABLE IF NOT EXISTS homestay (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    host_id BIGINT NOT NULL COMMENT '房东ID',
    name VARCHAR(100) NOT NULL COMMENT '民宿名称',
    description TEXT COMMENT '描述',
    province VARCHAR(50) COMMENT '省份',
    city VARCHAR(50) COMMENT '城市',
    district VARCHAR(50) COMMENT '区/县',
    address VARCHAR(255) COMMENT '详细地址',
    latitude DECIMAL(10,7) COMMENT '纬度',
    longitude DECIMAL(10,7) COMMENT '经度',
    cover_image VARCHAR(255) COMMENT '封面图片',
    images TEXT COMMENT '图片列表(JSON)',
    min_price DECIMAL(10,2) COMMENT '最低价格',
    max_guests INT DEFAULT 4 COMMENT '最大入住人数',
    rating DECIMAL(2,1) DEFAULT 5.0 COMMENT '评分',
    review_count INT DEFAULT 0 COMMENT '评价数',
    booking_count INT DEFAULT 0 COMMENT '预订数',
    status TINYINT DEFAULT 1 COMMENT '状态：0-下架 1-上架 2-审核中',
    deleted TINYINT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_host_id (host_id),
    INDEX idx_city (city),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='民宿表';

-- 房型表
CREATE TABLE IF NOT EXISTS room_type (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    homestay_id BIGINT NOT NULL COMMENT '民宿ID',
    name VARCHAR(50) NOT NULL COMMENT '房型名称',
    description TEXT COMMENT '房型描述',
    price DECIMAL(10,2) NOT NULL COMMENT '价格/晚',
    area INT COMMENT '面积(平方米)',
    max_guests INT DEFAULT 2 COMMENT '最大入住人数',
    bed_type VARCHAR(50) COMMENT '床型',
    total_count INT DEFAULT 1 COMMENT '总房间数',
    images TEXT COMMENT '图片列表(JSON)',
    status TINYINT DEFAULT 1 COMMENT '状态：0-下架 1-上架',
    deleted TINYINT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_homestay_id (homestay_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='房型表';

-- 设施表
CREATE TABLE IF NOT EXISTS facility (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL COMMENT '设施名称',
    icon VARCHAR(50) COMMENT '图标',
    category VARCHAR(50) COMMENT '分类',
    deleted TINYINT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设施表';

-- 民宿设施关联表
CREATE TABLE IF NOT EXISTS homestay_facility (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    homestay_id BIGINT NOT NULL,
    facility_id BIGINT NOT NULL,
    UNIQUE KEY uk_homestay_facility (homestay_id, facility_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='民宿设施关联表';

-- 预订表
CREATE TABLE IF NOT EXISTS booking (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_no VARCHAR(50) NOT NULL UNIQUE COMMENT '订单号',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    homestay_id BIGINT NOT NULL COMMENT '民宿ID',
    room_type_id BIGINT NOT NULL COMMENT '房型ID',
    check_in_date DATE NOT NULL COMMENT '入住日期',
    check_out_date DATE NOT NULL COMMENT '离店日期',
    nights INT NOT NULL COMMENT '入住晚数',
    guests INT DEFAULT 1 COMMENT '入住人数',
    total_price DECIMAL(10,2) NOT NULL COMMENT '总价',
    contact_name VARCHAR(50) COMMENT '联系人姓名',
    contact_phone VARCHAR(20) COMMENT '联系人电话',
    remark TEXT COMMENT '备注',
    status TINYINT DEFAULT 0 COMMENT '状态：0-待支付 1-待确认 2-已确认 3-进行中 4-已完成 5-已取消',
    cancel_reason VARCHAR(255) COMMENT '取消原因',
    deleted TINYINT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_user_id (user_id),
    INDEX idx_homestay_id (homestay_id),
    INDEX idx_status (status),
    INDEX idx_order_no (order_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='预订表';

-- 评价表
CREATE TABLE IF NOT EXISTS review (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    booking_id BIGINT NOT NULL COMMENT '订单ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    homestay_id BIGINT NOT NULL COMMENT '民宿ID',
    rating TINYINT NOT NULL COMMENT '评分1-5',
    content TEXT COMMENT '评价内容',
    images TEXT COMMENT '评价图片(JSON)',
    host_reply TEXT COMMENT '房东回复',
    reply_time DATETIME COMMENT '回复时间',
    deleted TINYINT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_homestay_id (homestay_id),
    INDEX idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评价表';

-- 收藏表
CREATE TABLE IF NOT EXISTS favorite (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    homestay_id BIGINT NOT NULL COMMENT '民宿ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_user_homestay (user_id, homestay_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收藏表';

-- 初始化设施数据
INSERT INTO facility (name, icon, category) VALUES
('WiFi', 'wifi', '基础设施'),
('空调', 'air-conditioning', '基础设施'),
('热水', 'hot-water', '基础设施'),
('电视', 'tv', '基础设施'),
('洗衣机', 'washing-machine', '基础设施'),
('冰箱', 'refrigerator', '基础设施'),
('厨房', 'kitchen', '基础设施'),
('停车位', 'parking', '基础设施'),
('泳池', 'pool', '休闲娱乐'),
('健身房', 'gym', '休闲娱乐'),
('花园', 'garden', '休闲娱乐'),
('烧烤', 'bbq', '休闲娱乐'),
('宠物友好', 'pet', '特色服务'),
('早餐', 'breakfast', '特色服务'),
('接机', 'pickup', '特色服务');

-- 初始化管理员账户 密码: admin123
INSERT INTO sys_user (username, password, nickname, role, status) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '管理员', 2, 1);

-- 初始化测试房东账户 密码: host123
INSERT INTO sys_user (username, password, nickname, phone, role, status) VALUES
('host1', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36W.PLVqKcN2cMLVV/mjHWu', '山水民宿', '13800138001', 1, 1);

-- 初始化测试民宿数据
INSERT INTO homestay (host_id, name, description, province, city, district, address, min_price, max_guests, rating, status) VALUES
(2, '山水间精品民宿', '坐落于山水之间，体验自然与宁静的完美融合。拥有独立庭院和山景露台，是您远离城市喧嚣的理想之选。', '浙江省', '杭州市', '西湖区', '西湖风景区龙井路88号', 388.00, 6, 4.8, 1),
(2, '老街故事民宿', '位于古镇核心区域，百年老宅改造，保留传统建筑风貌，融入现代舒适设施。漫步青石板路，感受时光静好。', '浙江省', '嘉兴市', '桐乡市', '乌镇西栅大街168号', 298.00, 4, 4.9, 1);

-- 初始化房型数据
INSERT INTO room_type (homestay_id, name, description, price, area, max_guests, bed_type, total_count) VALUES
(1, '山景大床房', '270°山景视野，落地窗设计，1.8米大床', 388.00, 35, 2, '1.8米大床', 3),
(1, '庭院双床房', '带独立小院，两张1.2米单人床', 358.00, 40, 2, '1.2米双床', 2),
(1, '家庭套房', '一室一厅，可住4人，含儿童床', 588.00, 60, 4, '1.8米+1.2米', 1),
(2, '临水大床房', '推窗见水，古镇风情', 298.00, 28, 2, '1.8米大床', 4),
(2, '雅致双床房', '中式装修风格，双床设计', 268.00, 30, 2, '1.2米双床', 3);

-- 关联民宿设施
INSERT INTO homestay_facility (homestay_id, facility_id) VALUES
(1, 1), (1, 2), (1, 3), (1, 4), (1, 7), (1, 8), (1, 11), (1, 14),
(2, 1), (2, 2), (2, 3), (2, 4), (2, 6), (2, 14), (2, 15);
