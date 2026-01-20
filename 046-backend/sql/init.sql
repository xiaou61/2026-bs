-- 垃圾回收服务系统数据库初始化脚本
CREATE DATABASE IF NOT EXISTS garbage_recycle DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE garbage_recycle;

-- 用户表（居民、回收员、管理员）
CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    real_name VARCHAR(50) COMMENT '真实姓名',
    phone VARCHAR(20) COMMENT '手机号',
    avatar VARCHAR(255) COMMENT '头像',
    role INT DEFAULT 0 COMMENT '角色：0-居民，1-回收员，2-管理员',
    address VARCHAR(255) COMMENT '详细地址',
    community_id BIGINT COMMENT '所属小区ID',
    points INT DEFAULT 0 COMMENT '环保积分',
    status INT DEFAULT 1 COMMENT '状态：0-禁用，1-正常',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
) COMMENT '用户表';

-- 小区表
CREATE TABLE community (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL COMMENT '小区名称',
    address VARCHAR(255) COMMENT '地址',
    contact_person VARCHAR(50) COMMENT '联系人',
    contact_phone VARCHAR(20) COMMENT '联系电话',
    status INT DEFAULT 1 COMMENT '状态：0-停用，1-正常',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
) COMMENT '小区表';

-- 垃圾分类表
CREATE TABLE garbage_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL COMMENT '分类名称',
    code VARCHAR(20) NOT NULL COMMENT '分类编码',
    description VARCHAR(255) COMMENT '描述',
    icon VARCHAR(255) COMMENT '图标',
    price DECIMAL(10,2) DEFAULT 0 COMMENT '回收单价(元/kg)',
    points_rate INT DEFAULT 1 COMMENT '积分比例(每kg获得积分)',
    sort_order INT DEFAULT 0 COMMENT '排序',
    status INT DEFAULT 1 COMMENT '状态：0-停用，1-正常',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
) COMMENT '垃圾分类表';

-- 回收预约表
CREATE TABLE recycle_order (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_no VARCHAR(32) NOT NULL UNIQUE COMMENT '订单编号',
    user_id BIGINT NOT NULL COMMENT '居民ID',
    collector_id BIGINT COMMENT '回收员ID',
    community_id BIGINT COMMENT '小区ID',
    address VARCHAR(255) NOT NULL COMMENT '上门地址',
    contact_name VARCHAR(50) COMMENT '联系人',
    contact_phone VARCHAR(20) COMMENT '联系电话',
    appointment_time DATETIME COMMENT '预约上门时间',
    category_ids VARCHAR(100) COMMENT '垃圾分类ID，多个逗号分隔',
    estimated_weight DECIMAL(10,2) COMMENT '预估重量(kg)',
    actual_weight DECIMAL(10,2) COMMENT '实际重量(kg)',
    amount DECIMAL(10,2) DEFAULT 0 COMMENT '回收金额',
    points INT DEFAULT 0 COMMENT '获得积分',
    status INT DEFAULT 0 COMMENT '状态：0-待接单，1-已接单，2-进行中，3-已完成，4-已取消',
    remark VARCHAR(500) COMMENT '备注',
    complete_time DATETIME COMMENT '完成时间',
    cancel_reason VARCHAR(255) COMMENT '取消原因',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
) COMMENT '回收预约订单表';

-- 订单明细表
CREATE TABLE order_detail (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL COMMENT '订单ID',
    category_id BIGINT NOT NULL COMMENT '垃圾分类ID',
    weight DECIMAL(10,2) COMMENT '重量(kg)',
    price DECIMAL(10,2) COMMENT '单价',
    amount DECIMAL(10,2) COMMENT '金额',
    points INT DEFAULT 0 COMMENT '积分',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
) COMMENT '订单明细表';

-- 积分记录表
CREATE TABLE points_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    points INT NOT NULL COMMENT '积分变动值',
    type INT COMMENT '类型：1-回收获得，2-兑换消费，3-签到奖励',
    order_id BIGINT COMMENT '关联订单ID',
    description VARCHAR(255) COMMENT '描述',
    balance INT COMMENT '变动后余额',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
) COMMENT '积分记录表';

-- 积分商品表
CREATE TABLE points_product (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL COMMENT '商品名称',
    image VARCHAR(255) COMMENT '商品图片',
    description VARCHAR(500) COMMENT '商品描述',
    points INT NOT NULL COMMENT '所需积分',
    stock INT DEFAULT 0 COMMENT '库存',
    exchange_count INT DEFAULT 0 COMMENT '已兑换数量',
    status INT DEFAULT 1 COMMENT '状态：0-下架，1-上架',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
) COMMENT '积分商品表';

-- 兑换记录表
CREATE TABLE exchange_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    product_id BIGINT NOT NULL COMMENT '商品ID',
    product_name VARCHAR(100) COMMENT '商品名称',
    points INT NOT NULL COMMENT '消耗积分',
    quantity INT DEFAULT 1 COMMENT '兑换数量',
    status INT DEFAULT 0 COMMENT '状态：0-待发放，1-已发放，2-已取消',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
) COMMENT '兑换记录表';

-- 环保知识表
CREATE TABLE knowledge (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL COMMENT '标题',
    content TEXT COMMENT '内容',
    cover VARCHAR(255) COMMENT '封面图',
    category INT DEFAULT 1 COMMENT '分类：1-分类知识，2-环保资讯，3-政策法规',
    view_count INT DEFAULT 0 COMMENT '浏览量',
    publisher_id BIGINT COMMENT '发布人ID',
    status INT DEFAULT 1 COMMENT '状态：0-草稿，1-已发布',
    publish_time DATETIME COMMENT '发布时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
) COMMENT '环保知识表';

-- 公告表
CREATE TABLE notice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL COMMENT '标题',
    content TEXT COMMENT '内容',
    type INT DEFAULT 1 COMMENT '类型：1-系统公告，2-活动通知',
    publisher_id BIGINT COMMENT '发布人',
    status INT DEFAULT 1 COMMENT '状态：0-草稿，1-已发布',
    publish_time DATETIME COMMENT '发布时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
) COMMENT '公告表';

-- 初始化垃圾分类数据
INSERT INTO garbage_category (name, code, description, price, points_rate, sort_order) VALUES
('可回收物', 'recyclable', '废纸、塑料、玻璃、金属、织物等', 0.80, 10, 1),
('有害垃圾', 'hazardous', '废电池、废灯管、废药品、废油漆等', 0.00, 20, 2),
('厨余垃圾', 'kitchen', '剩菜剩饭、果皮果核、茶渣等', 0.10, 5, 3),
('其他垃圾', 'other', '砖瓦陶瓷、渣土、卫生间废纸等', 0.00, 2, 4),
('废纸类', 'paper', '报纸、书本、纸箱、快递盒等', 1.00, 10, 5),
('塑料类', 'plastic', '塑料瓶、塑料桶、塑料制品等', 0.50, 8, 6),
('金属类', 'metal', '易拉罐、铁制品、铝制品等', 2.00, 15, 7),
('玻璃类', 'glass', '玻璃瓶、玻璃制品等', 0.20, 5, 8),
('旧衣物', 'clothes', '旧衣服、床单被套、鞋帽等', 0.30, 8, 9),
('电子废弃物', 'electronic', '废旧手机、电脑配件、小家电等', 3.00, 25, 10);

-- 初始化小区数据
INSERT INTO community (name, address, contact_person, contact_phone) VALUES
('阳光花园小区', '市中心阳光路100号', '张经理', '13800138001'),
('绿色家园小区', '城东绿洲路200号', '李主任', '13800138002'),
('和谐社区', '城西和平路300号', '王物业', '13800138003');

-- 初始化用户数据
INSERT INTO sys_user (username, password, real_name, phone, role, community_id, points, status) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '系统管理员', '13900000000', 2, NULL, 0, 1),
('collector1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '回收员小王', '13900000001', 1, 1, 0, 1),
('collector2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '回收员小李', '13900000002', 1, 2, 0, 1),
('user1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '居民张三', '13900000003', 0, 1, 500, 1),
('user2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '居民李四', '13900000004', 0, 2, 300, 1);

-- 初始化积分商品
INSERT INTO points_product (name, description, points, stock, status) VALUES
('环保购物袋', '可降解环保购物袋一个', 50, 100, 1),
('垃圾分类指南手册', '详细的垃圾分类知识手册', 30, 200, 1),
('定制水杯', '环保主题不锈钢保温杯', 200, 50, 1),
('话费充值卡10元', '移动/联通/电信通用', 500, 30, 1),
('超市代金券20元', '合作超市通用代金券', 800, 20, 1);

-- 初始化环保知识
INSERT INTO knowledge (title, content, category, status, publish_time) VALUES
('垃圾分类小常识', '垃圾分类是指按规定将垃圾分类储存、分类投放和分类搬运，从而转变成公共资源的一系列活动的总称。', 1, 1, NOW()),
('可回收物包括哪些？', '可回收物主要包括废纸、塑料、玻璃、金属和布料五大类。', 1, 1, NOW()),
('有害垃圾的正确处理方式', '有害垃圾需要特殊安全处理，包括废电池、废灯管、废药品、废油漆及其容器等。', 1, 1, NOW());

-- 初始化公告
INSERT INTO notice (title, content, type, status, publish_time) VALUES
('垃圾回收服务正式上线', '为了营造更好的居住环境，本小区垃圾回收服务正式上线，欢迎居民使用！', 1, 1, NOW()),
('积分兑换活动开始', '即日起，环保积分可兑换精美礼品，欢迎参与！', 2, 1, NOW());
