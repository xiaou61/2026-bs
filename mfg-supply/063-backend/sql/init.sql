DROP DATABASE IF EXISTS inventory_system;
CREATE DATABASE inventory_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE inventory_system;

CREATE TABLE sys_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50),
    phone VARCHAR(20),
    email VARCHAR(100),
    role VARCHAR(20) DEFAULT 'STAFF',
    status INT DEFAULT 1,
    last_login_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE supplier (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    supplier_no VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    contact_person VARCHAR(50),
    phone VARCHAR(20),
    address VARCHAR(255),
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE customer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_no VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    contact_person VARCHAR(50),
    phone VARCHAR(20),
    address VARCHAR(255),
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    sort INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_no VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    category_id BIGINT NOT NULL,
    spec VARCHAR(100),
    unit VARCHAR(20),
    cost_price DECIMAL(10,2) DEFAULT 0,
    sale_price DECIMAL(10,2) DEFAULT 0,
    stock INT DEFAULT 0,
    stock_warn INT DEFAULT 10,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_product_category(category_id),
    INDEX idx_product_status(status)
);

CREATE TABLE purchase_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_no VARCHAR(50) NOT NULL UNIQUE,
    supplier_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    amount DECIMAL(12,2) NOT NULL,
    status INT DEFAULT 0,
    remark VARCHAR(255),
    creator_id BIGINT,
    audit_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_purchase_supplier(supplier_id),
    INDEX idx_purchase_product(product_id),
    INDEX idx_purchase_status(status)
);

CREATE TABLE sale_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_no VARCHAR(50) NOT NULL UNIQUE,
    customer_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    amount DECIMAL(12,2) NOT NULL,
    status INT DEFAULT 0,
    remark VARCHAR(255),
    creator_id BIGINT,
    audit_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_sale_customer(customer_id),
    INDEX idx_sale_product(product_id),
    INDEX idx_sale_status(status)
);

CREATE TABLE stock_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    biz_type VARCHAR(20) NOT NULL,
    biz_no VARCHAR(50) NOT NULL,
    product_id BIGINT NOT NULL,
    change_qty INT NOT NULL,
    before_stock INT NOT NULL,
    after_stock INT NOT NULL,
    remark VARCHAR(255),
    operator_id BIGINT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_stock_biz_no(biz_no),
    INDEX idx_stock_product(product_id),
    INDEX idx_stock_type(biz_type)
);

CREATE TABLE announcement (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    content TEXT,
    admin_id BIGINT,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO sys_user (username, password, nickname, phone, email, role, status) VALUES
('admin', '123456', '系统管理员', '13800020001', 'admin@inventory.com', 'ADMIN', 1),
('staff', '123456', '采购专员', '13800020002', 'staff@inventory.com', 'STAFF', 1);

INSERT INTO supplier (supplier_no, name, contact_person, phone, address, status) VALUES
('SUP2026001', '华北电子供应商', '李志', '13911110001', '北京市海淀区xx路1号', 1),
('SUP2026002', '华东办公用品商', '张云', '13911110002', '上海市浦东新区xx路2号', 1);

INSERT INTO customer (customer_no, name, contact_person, phone, address, status) VALUES
('CUS2026001', '星辰科技有限公司', '王涛', '13722220001', '深圳市南山区xx路3号', 1),
('CUS2026002', '晨曦贸易有限公司', '陈静', '13722220002', '广州市天河区xx路4号', 1);

INSERT INTO category (name, sort, status) VALUES
('电子设备', 1, 1),
('办公用品', 2, 1),
('耗材', 3, 1);

INSERT INTO product (product_no, name, category_id, spec, unit, cost_price, sale_price, stock, stock_warn, status) VALUES
('PROD2026001', '笔记本电脑A1', 1, 'i5/16G/512G', '台', 4200.00, 4899.00, 20, 5, 1),
('PROD2026002', '激光打印机P8', 1, 'A4双面', '台', 1400.00, 1799.00, 15, 4, 1),
('PROD2026003', '办公打印纸', 2, 'A4 70g', '箱', 120.00, 168.00, 50, 10, 1),
('PROD2026004', '碳粉盒T3', 3, '黑色', '盒', 85.00, 129.00, 40, 8, 1);

INSERT INTO purchase_order (order_no, supplier_id, product_id, quantity, price, amount, status, remark, creator_id, audit_time, create_time) VALUES
('PO202602130001', 1, 1, 5, 4200.00, 21000.00, 1, '月度采购', 2, '2026-02-12 10:20:00', '2026-02-12 10:00:00'),
('PO202602130002', 2, 3, 10, 120.00, 1200.00, 0, '补充耗材', 2, NULL, '2026-02-12 16:00:00');

INSERT INTO sale_order (order_no, customer_id, product_id, quantity, price, amount, status, remark, creator_id, audit_time, create_time) VALUES
('SO202602130001', 1, 1, 2, 4899.00, 9798.00, 1, '企业采购订单', 2, '2026-02-12 14:00:00', '2026-02-12 13:40:00'),
('SO202602130002', 2, 4, 6, 129.00, 774.00, 0, '客户补货', 2, NULL, '2026-02-12 17:30:00');

INSERT INTO stock_record (biz_type, biz_no, product_id, change_qty, before_stock, after_stock, remark, operator_id, create_time) VALUES
('PURCHASE', 'PO202602130001', 1, 5, 15, 20, '采购入库', 2, '2026-02-12 10:21:00'),
('SALE', 'SO202602130001', 1, -2, 22, 20, '销售出库', 2, '2026-02-12 14:01:00');

INSERT INTO announcement (title, content, admin_id, status) VALUES
('系统启用通知', '进销存管理系统已上线，业务人员请按流程提交采购和销售单。', 1, 1),
('库存盘点安排', '本周五18:00开始月度盘点，请提前完成单据审核。', 1, 1);
