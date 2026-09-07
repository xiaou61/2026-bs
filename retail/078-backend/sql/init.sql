DROP DATABASE IF EXISTS group_buy;
CREATE DATABASE group_buy DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE group_buy;

CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50),
    phone VARCHAR(20),
    email VARCHAR(100),
    avatar VARCHAR(255),
    role INT DEFAULT 2,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE merchant (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    logo VARCHAR(255),
    description TEXT,
    contact VARCHAR(50),
    phone VARCHAR(20),
    address VARCHAR(255),
    status INT DEFAULT 0,
    audit_remark VARCHAR(255),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    parent_id BIGINT DEFAULT 0,
    name VARCHAR(50) NOT NULL,
    icon VARCHAR(255),
    sort INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE product (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    merchant_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    name VARCHAR(200) NOT NULL,
    cover VARCHAR(255),
    images TEXT,
    description TEXT,
    original_price DECIMAL(10,2) NOT NULL,
    stock INT DEFAULT 0,
    sales INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE group_activity (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT NOT NULL,
    merchant_id BIGINT NOT NULL,
    group_price DECIMAL(10,2) NOT NULL,
    min_count INT NOT NULL,
    max_count INT,
    limit_hours INT DEFAULT 24,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    status INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE group_order (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    activity_id BIGINT NOT NULL,
    leader_id BIGINT NOT NULL,
    current_count INT DEFAULT 1,
    target_count INT NOT NULL,
    status INT DEFAULT 0,
    expire_time DATETIME NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_no VARCHAR(50) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    merchant_id BIGINT NOT NULL,
    group_order_id BIGINT,
    total_amount DECIMAL(10,2) NOT NULL,
    pay_amount DECIMAL(10,2) NOT NULL,
    address_info VARCHAR(500),
    status INT DEFAULT 0,
    pay_time DATETIME,
    ship_time DATETIME,
    receive_time DATETIME,
    remark VARCHAR(255),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE order_item (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    product_name VARCHAR(200),
    product_cover VARCHAR(255),
    price DECIMAL(10,2) NOT NULL,
    quantity INT NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL
);

CREATE TABLE address (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    name VARCHAR(50) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    province VARCHAR(50),
    city VARCHAR(50),
    district VARCHAR(50),
    detail VARCHAR(255),
    is_default INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE cart (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    activity_id BIGINT,
    quantity INT DEFAULT 1,
    selected INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE review (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    merchant_id BIGINT NOT NULL,
    rating INT DEFAULT 5,
    content TEXT,
    images TEXT,
    status INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE notice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    type INT DEFAULT 0,
    status INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE INDEX idx_merchant_user ON merchant(user_id);
CREATE INDEX idx_product_merchant ON product(merchant_id);
CREATE INDEX idx_product_category ON product(category_id);
CREATE INDEX idx_activity_product ON group_activity(product_id);
CREATE INDEX idx_group_order_activity ON group_order(activity_id);
CREATE INDEX idx_orders_user ON orders(user_id);
CREATE INDEX idx_orders_merchant ON orders(merchant_id);
CREATE INDEX idx_order_item_order ON order_item(order_id);
CREATE INDEX idx_address_user ON address(user_id);
CREATE INDEX idx_cart_user ON cart(user_id);
CREATE INDEX idx_review_product ON review(product_id);

INSERT INTO user (username, password, nickname, phone, role, status) VALUES
('admin', '123456', '管理员', '13800000000', 0, 1),
('merchant', '123456', '商家小王', '13800000001', 1, 1),
('merchant2', '123456', '商家小李', '13800000002', 1, 1),
('user', '123456', '用户张三', '13800000003', 2, 1),
('user2', '123456', '用户李四', '13800000004', 2, 1),
('user3', '123456', '用户王五', '13800000005', 2, 1);

INSERT INTO merchant (user_id, name, logo, description, contact, phone, address, status) VALUES
(2, '优选生鲜店', 'https://picsum.photos/200/200?random=1', '专注生鲜配送，品质保证', '王经理', '13800000001', '北京市朝阳区建国路88号', 1),
(3, '数码潮品店', 'https://picsum.photos/200/200?random=2', '正品数码，售后无忧', '李经理', '13800000002', '上海市浦东新区世纪大道100号', 1);

INSERT INTO category (parent_id, name, icon, sort) VALUES
(0, '生鲜水果', 'el-icon-apple', 1),
(0, '数码电子', 'el-icon-phone', 2),
(0, '家居日用', 'el-icon-house', 3),
(0, '服饰鞋包', 'el-icon-goods', 4),
(1, '新鲜水果', '', 1),
(1, '蔬菜蛋奶', '', 2),
(1, '肉禽水产', '', 3),
(2, '手机通讯', '', 1),
(2, '电脑办公', '', 2),
(2, '智能设备', '', 3),
(3, '厨房用品', '', 1),
(3, '清洁用品', '', 2),
(4, '男装', '', 1),
(4, '女装', '', 2);

INSERT INTO product (merchant_id, category_id, name, cover, images, description, original_price, stock, sales, status) VALUES
(1, 5, '新疆阿克苏冰糖心苹果5斤装', 'https://picsum.photos/400/400?random=10', '["https://picsum.photos/400/400?random=11","https://picsum.photos/400/400?random=12"]', '正宗新疆阿克苏苹果，冰糖心，脆甜可口', 59.90, 500, 120, 1),
(1, 5, '智利进口车厘子2斤装', 'https://picsum.photos/400/400?random=13', '["https://picsum.photos/400/400?random=14"]', '智利空运直达，果大肉厚，甜度高', 128.00, 200, 80, 1),
(1, 6, '有机蔬菜套餐5斤装', 'https://picsum.photos/400/400?random=15', '["https://picsum.photos/400/400?random=16"]', '农场直供，无农药残留，健康放心', 45.00, 300, 150, 1),
(1, 7, '澳洲进口牛排套餐', 'https://picsum.photos/400/400?random=17', '["https://picsum.photos/400/400?random=18"]', '原切牛排，谷饲180天，鲜嫩多汁', 199.00, 100, 45, 1),
(2, 8, '新款智能手机 8+256G', 'https://picsum.photos/400/400?random=20', '["https://picsum.photos/400/400?random=21","https://picsum.photos/400/400?random=22"]', '旗舰处理器，超长续航，5G双模', 2999.00, 50, 30, 1),
(2, 9, '轻薄笔记本电脑 i5-1235U', 'https://picsum.photos/400/400?random=23', '["https://picsum.photos/400/400?random=24"]', '14英寸高清屏，16G内存，512G固态', 4599.00, 30, 15, 1),
(2, 10, '智能手表运动版', 'https://picsum.photos/400/400?random=25', '["https://picsum.photos/400/400?random=26"]', '心率血氧监测，运动追踪，超长待机', 899.00, 100, 60, 1),
(2, 10, '真无线蓝牙耳机', 'https://picsum.photos/400/400?random=27', '["https://picsum.photos/400/400?random=28"]', '主动降噪，30小时续航，Hi-Fi音质', 399.00, 200, 90, 1);

INSERT INTO group_activity (product_id, merchant_id, group_price, min_count, max_count, limit_hours, start_time, end_time, status) VALUES
(1, 1, 39.90, 3, 50, 24, '2026-02-01 00:00:00', '2026-03-31 23:59:59', 1),
(2, 1, 98.00, 2, 30, 24, '2026-02-01 00:00:00', '2026-03-31 23:59:59', 1),
(3, 1, 35.00, 5, 100, 48, '2026-02-01 00:00:00', '2026-03-31 23:59:59', 1),
(4, 1, 159.00, 2, 20, 24, '2026-02-01 00:00:00', '2026-03-31 23:59:59', 1),
(5, 2, 2599.00, 2, 10, 48, '2026-02-01 00:00:00', '2026-03-31 23:59:59', 1),
(6, 2, 3999.00, 2, 10, 48, '2026-02-01 00:00:00', '2026-03-31 23:59:59', 1),
(7, 2, 699.00, 3, 50, 24, '2026-02-01 00:00:00', '2026-03-31 23:59:59', 1),
(8, 2, 299.00, 3, 100, 24, '2026-02-01 00:00:00', '2026-03-31 23:59:59', 1);

INSERT INTO group_order (activity_id, leader_id, current_count, target_count, status, expire_time) VALUES
(1, 4, 2, 3, 0, '2026-02-26 18:00:00'),
(2, 5, 1, 2, 0, '2026-02-26 18:00:00'),
(7, 4, 3, 3, 1, '2026-02-24 18:00:00');

INSERT INTO address (user_id, name, phone, province, city, district, detail, is_default) VALUES
(4, '张三', '13800000003', '北京市', '北京市', '朝阳区', '建国路100号院5号楼301', 1),
(4, '张三', '13800000003', '北京市', '北京市', '海淀区', '中关村大街1号', 0),
(5, '李四', '13800000004', '上海市', '上海市', '浦东新区', '世纪大道200号', 1),
(6, '王五', '13800000005', '广东省', '深圳市', '南山区', '科技园路100号', 1);

INSERT INTO orders (order_no, user_id, merchant_id, group_order_id, total_amount, pay_amount, address_info, status, pay_time, create_time) VALUES
('202602250001', 4, 1, 1, 39.90, 39.90, '{"name":"张三","phone":"13800000003","address":"北京市北京市朝阳区建国路100号院5号楼301"}', 3, '2026-02-20 10:00:00', '2026-02-20 09:55:00'),
('202602250002', 6, 1, 1, 39.90, 39.90, '{"name":"王五","phone":"13800000005","address":"广东省深圳市南山区科技园路100号"}', 2, '2026-02-20 11:00:00', '2026-02-20 10:55:00'),
('202602250003', 5, 1, 2, 98.00, 98.00, '{"name":"李四","phone":"13800000004","address":"上海市上海市浦东新区世纪大道200号"}', 1, '2026-02-21 14:00:00', '2026-02-21 13:55:00'),
('202602250004', 4, 2, 3, 699.00, 699.00, '{"name":"张三","phone":"13800000003","address":"北京市北京市朝阳区建国路100号院5号楼301"}', 3, '2026-02-22 09:00:00', '2026-02-22 08:55:00');

INSERT INTO order_item (order_id, product_id, product_name, product_cover, price, quantity, subtotal) VALUES
(1, 1, '新疆阿克苏冰糖心苹果5斤装', 'https://picsum.photos/400/400?random=10', 39.90, 1, 39.90),
(2, 1, '新疆阿克苏冰糖心苹果5斤装', 'https://picsum.photos/400/400?random=10', 39.90, 1, 39.90),
(3, 2, '智利进口车厘子2斤装', 'https://picsum.photos/400/400?random=13', 98.00, 1, 98.00),
(4, 7, '智能手表运动版', 'https://picsum.photos/400/400?random=25', 699.00, 1, 699.00);

INSERT INTO review (order_id, user_id, product_id, merchant_id, rating, content, images, status) VALUES
(1, 4, 1, 1, 5, '苹果很新鲜，冰糖心，很甜很脆！物流也很快，包装完好，下次还会购买！', '["https://picsum.photos/400/400?random=30"]', 1),
(4, 4, 7, 2, 5, '手表功能很全，续航也不错，运动监测很准确，性价比很高！', '["https://picsum.photos/400/400?random=31"]', 1);

INSERT INTO notice (title, content, type, status) VALUES
('欢迎使用网上团购系统', '本平台致力于为用户提供优质的团购服务，多人拼团享受超低优惠价格！', 0, 1),
('新用户专享福利', '新用户注册即可获得优惠券，首单立减10元！', 1, 1),
('春季生鲜大促', '新鲜水果蔬菜，产地直供，3人成团立享5折优惠！', 1, 1);
