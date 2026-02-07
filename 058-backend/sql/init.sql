DROP DATABASE IF EXISTS milk_order;
CREATE DATABASE milk_order DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE milk_order;

CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50),
    phone VARCHAR(20),
    avatar VARCHAR(255),
    role VARCHAR(20) DEFAULT 'USER',
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE milk_category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    sort INT DEFAULT 0,
    status INT DEFAULT 1
);

CREATE TABLE milk_product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_id BIGINT,
    name VARCHAR(100) NOT NULL,
    image VARCHAR(255),
    price DECIMAL(10,2) NOT NULL,
    unit VARCHAR(20) DEFAULT '瓶',
    spec VARCHAR(50),
    description TEXT,
    stock INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE address (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    contact_name VARCHAR(50),
    contact_phone VARCHAR(20),
    province VARCHAR(50),
    city VARCHAR(50),
    district VARCHAR(50),
    detail VARCHAR(200),
    is_default INT DEFAULT 0
);

CREATE TABLE delivery_area (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    province VARCHAR(50),
    city VARCHAR(50),
    district VARCHAR(50),
    status INT DEFAULT 1
);

CREATE TABLE subscription (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    address_id BIGINT NOT NULL,
    type VARCHAR(20) NOT NULL,
    quantity INT DEFAULT 1,
    delivery_time VARCHAR(20) DEFAULT 'MORNING',
    week_days VARCHAR(50),
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE milk_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_no VARCHAR(50) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    subscription_id BIGINT,
    product_id BIGINT NOT NULL,
    quantity INT DEFAULT 1,
    total_price DECIMAL(10,2),
    address VARCHAR(300),
    contact_name VARCHAR(50),
    contact_phone VARCHAR(20),
    delivery_date DATE,
    delivery_time VARCHAR(20),
    status INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE delivery_route (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    area_id BIGINT,
    delivery_user_id BIGINT,
    description VARCHAR(500),
    status INT DEFAULT 1
);

CREATE TABLE delivery_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    delivery_user_id BIGINT NOT NULL,
    route_id BIGINT,
    status INT DEFAULT 0,
    remark VARCHAR(200),
    delivery_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE complaint (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    order_id BIGINT,
    type VARCHAR(20) DEFAULT 'COMPLAINT',
    content TEXT,
    images VARCHAR(500),
    status INT DEFAULT 0,
    reply TEXT,
    reply_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE notification (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(100),
    content TEXT,
    type VARCHAR(20),
    is_read INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE payment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    amount DECIMAL(10,2),
    pay_type VARCHAR(20),
    pay_no VARCHAR(100),
    status INT DEFAULT 0,
    pay_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO user (username, password, nickname, phone, role, status) VALUES
('admin', '123456', '系统管理员', '13800000001', 'ADMIN', 1),
('delivery', '123456', '配送员张三', '13800000002', 'DELIVERY', 1),
('delivery2', '123456', '配送员李四', '13800000003', 'DELIVERY', 1),
('user', '123456', '用户王五', '13800000004', 'USER', 1),
('user2', '123456', '用户赵六', '13800000005', 'USER', 1);

INSERT INTO milk_category (name, sort, status) VALUES
('鲜牛奶', 1, 1),
('酸奶', 2, 1),
('低脂奶', 3, 1),
('有机奶', 4, 1),
('儿童奶', 5, 1);

INSERT INTO milk_product (category_id, name, image, price, unit, spec, description, stock, status) VALUES
(1, '每日鲜纯牛奶', '/images/milk1.jpg', 6.50, '瓶', '250ml', '新鲜牧场直供，当日挤奶当日配送', 500, 1),
(1, '高钙鲜牛奶', '/images/milk2.jpg', 7.80, '瓶', '250ml', '富含钙质，适合全家饮用', 300, 1),
(2, '原味酸奶', '/images/milk3.jpg', 5.50, '杯', '200ml', '优质奶源发酵，口感醇厚', 400, 1),
(2, '草莓酸奶', '/images/milk4.jpg', 6.00, '杯', '200ml', '新鲜草莓果粒，酸甜可口', 350, 1),
(3, '低脂鲜牛奶', '/images/milk5.jpg', 8.00, '瓶', '250ml', '脂肪含量仅1.5%，健康之选', 200, 1),
(4, '有机纯牛奶', '/images/milk6.jpg', 12.00, '瓶', '250ml', '有机牧场认证，零添加', 150, 1),
(5, '儿童成长奶', '/images/milk7.jpg', 8.50, '盒', '200ml', '添加DHA和钙铁锌，助力成长', 250, 1),
(1, '巴氏杀菌奶', '/images/milk8.jpg', 9.00, '瓶', '500ml', '巴氏杀菌工艺，营养更完整', 180, 1);

INSERT INTO address (user_id, contact_name, contact_phone, province, city, district, detail, is_default) VALUES
(4, '王五', '13800000004', '浙江省', '杭州市', '西湖区', '文三路100号1栋2单元301', 1),
(4, '王五', '13800000004', '浙江省', '杭州市', '西湖区', '天目山路200号3栋1单元502', 0),
(5, '赵六', '13800000005', '浙江省', '杭州市', '滨江区', '江南大道500号5栋3单元801', 1);

INSERT INTO delivery_area (name, province, city, district, status) VALUES
('西湖区配送区', '浙江省', '杭州市', '西湖区', 1),
('滨江区配送区', '浙江省', '杭州市', '滨江区', 1),
('拱墅区配送区', '浙江省', '杭州市', '拱墅区', 1);

INSERT INTO delivery_route (name, area_id, delivery_user_id, description, status) VALUES
('西湖区A线', 1, 2, '文三路-天目山路沿线', 1),
('西湖区B线', 1, 2, '学院路-教工路沿线', 1),
('滨江区A线', 2, 3, '江南大道-滨盛路沿线', 1);

INSERT INTO subscription (user_id, product_id, address_id, type, quantity, delivery_time, week_days, start_date, end_date, status) VALUES
(4, 1, 1, 'DAILY', 2, 'MORNING', NULL, '2024-01-01', '2024-06-30', 1),
(4, 3, 1, 'WEEKLY', 3, 'AFTERNOON', '1,3,5', '2024-01-01', '2024-06-30', 1),
(5, 2, 3, 'MONTHLY', 30, 'MORNING', NULL, '2024-02-01', '2024-12-31', 1);

INSERT INTO milk_order (order_no, user_id, subscription_id, product_id, quantity, total_price, address, contact_name, contact_phone, delivery_date, delivery_time, status) VALUES
('MO202401010001', 4, 1, 1, 2, 13.00, '浙江省杭州市西湖区文三路100号1栋2单元301', '王五', '13800000004', '2024-01-15', 'MORNING', 2),
('MO202401010002', 4, 2, 3, 3, 16.50, '浙江省杭州市西湖区文三路100号1栋2单元301', '王五', '13800000004', '2024-01-15', 'AFTERNOON', 2),
('MO202401020001', 4, 1, 1, 2, 13.00, '浙江省杭州市西湖区文三路100号1栋2单元301', '王五', '13800000004', '2024-01-16', 'MORNING', 0),
('MO202401020002', 5, 3, 2, 30, 234.00, '浙江省杭州市滨江区江南大道500号5栋3单元801', '赵六', '13800000005', '2024-01-16', 'MORNING', 1);

INSERT INTO delivery_record (order_id, delivery_user_id, route_id, status, remark, delivery_time) VALUES
(1, 2, 1, 1, NULL, '2024-01-15 07:30:00'),
(2, 2, 1, 1, NULL, '2024-01-15 14:20:00'),
(3, 2, 1, 0, NULL, NULL),
(4, 3, 3, 0, NULL, NULL);

INSERT INTO complaint (user_id, order_id, type, content, status, reply, reply_time) VALUES
(4, 1, 'COMPLAINT', '牛奶包装有破损', 1, '非常抱歉给您带来不便，我们已安排补发', '2024-01-16 10:00:00'),
(5, 4, 'FEEDBACK', '希望增加早上6点的配送时段', 0, NULL, NULL);

INSERT INTO notification (user_id, title, content, type, is_read) VALUES
(4, '订阅成功通知', '您已成功订阅每日鲜纯牛奶，配送将于明日开始', 'SUBSCRIPTION', 1),
(4, '配送完成通知', '您的订单MO202401010001已配送完成', 'DELIVERY', 1),
(5, '欢迎使用', '欢迎使用鲜牛奶订购系统', 'SYSTEM', 0);

INSERT INTO payment (order_id, user_id, amount, pay_type, pay_no, status, pay_time) VALUES
(1, 4, 13.00, '微信支付', 'WX20240115001', 1, '2024-01-14 20:00:00'),
(2, 4, 16.50, '支付宝', 'ZFB20240115001', 1, '2024-01-14 20:05:00'),
(3, 4, 13.00, '微信支付', 'WX20240116001', 1, '2024-01-15 20:00:00'),
(4, 5, 234.00, '微信支付', 'WX20240201001', 1, '2024-02-01 08:00:00');
