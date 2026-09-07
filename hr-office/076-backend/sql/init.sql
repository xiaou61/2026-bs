DROP DATABASE IF EXISTS enterprise_info_system;
CREATE DATABASE enterprise_info_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE enterprise_info_system;

CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50),
    avatar VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(100),
    role VARCHAR(20) DEFAULT 'USER',
    status INT DEFAULT 1,
    balance DECIMAL(10,2) DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE info_category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    icon VARCHAR(255),
    sort INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE enterprise_info (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_id BIGINT,
    seller_id BIGINT,
    title VARCHAR(120) NOT NULL,
    service_name VARCHAR(80) NOT NULL,
    store_name VARCHAR(80),
    vehicle_type VARCHAR(40),
    price DECIMAL(10,2) NOT NULL,
    stock INT DEFAULT 0,
    cover VARCHAR(255),
    description TEXT,
    booking_type VARCHAR(30),
    view_count INT DEFAULT 0,
    booked_count INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_enterprise_info_category(category_id),
    INDEX idx_enterprise_info_seller(seller_id)
);

CREATE TABLE info_process (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_no VARCHAR(50) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    service_id BIGINT NOT NULL,
    seller_id BIGINT NOT NULL,
    quantity INT DEFAULT 1,
    total_price DECIMAL(10,2),
    status INT DEFAULT 0,
    remark VARCHAR(255),
    appointment_date DATE NOT NULL,
    appointment_time TIME NOT NULL,
    plate_no VARCHAR(20) NOT NULL,
    vehicle_model VARCHAR(80) NOT NULL,
    fault_desc VARCHAR(500),
    pay_time DATETIME,
    finish_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_info_process_user(user_id),
    INDEX idx_info_process_service(service_id)
);

CREATE TABLE review (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    service_id BIGINT NOT NULL,
    rating INT DEFAULT 5,
    content TEXT,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE favorite (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    service_id BIGINT NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_favorite_user_service(user_id, service_id)
);

CREATE TABLE complaint (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    target_user_id BIGINT,
    order_status_snapshot INT,
    type VARCHAR(30) DEFAULT 'ORDER',
    content TEXT,
    images VARCHAR(500),
    status INT DEFAULT 0,
    reply TEXT,
    reply_admin_id BIGINT,
    reply_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
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

INSERT INTO user (username, password, nickname, phone, email, role, status, balance) VALUES
('admin', '123456', '平台管理员', '13800000001', 'admin@enterprise.com', 'ADMIN', 1, 99999.00),
('seller1', '123456', '华星信息咨询', '13800000002', 'seller1@enterprise.com', 'USER', 1, 12800.00),
('seller2', '123456', '卓信企业服务', '13800000003', 'seller2@enterprise.com', 'USER', 1, 9300.00),
('buyer1', '123456', '王经理', '13800000004', 'buyer1@enterprise.com', 'USER', 1, 4200.00);

INSERT INTO info_category (name, icon, sort, status) VALUES
('工商登记', '/images/category/gsdj.png', 1, 1),
('税务信息', '/images/category/swxx.png', 2, 1),
('人事档案', '/images/category/rsda.png', 3, 1),
('合同台账', '/images/category/httz.png', 4, 1),
('资质证照', '/images/category/zzzz.png', 5, 1),
('经营数据', '/images/category/jysj.png', 6, 1);

INSERT INTO enterprise_info (category_id, seller_id, title, service_name, store_name, vehicle_type, price, stock, cover, description, booking_type, view_count, booked_count, status) VALUES
(1, 2, '企业基础工商信息维护', '华星基础档案', '企业服务', '中小型', 299.00, 28, '/images/item/company1.jpg', '包含企业名称、注册地址、法定代表人等基础信息维护。', '在线提交', 386, 52, 1),
(1, 2, '股东与出资信息更新', '股权信息更新', '企业服务', '成长型', 420.00, 16, '/images/item/company2.jpg', '支持股东结构、认缴实缴、出资比例等信息更新。', '在线审核', 245, 31, 1),
(2, 3, '税务申报信息核验', '税务核验', '财税服务', '中大型', 560.00, 14, '/images/item/tax1.jpg', '核验纳税人识别信息、申报历史及税种匹配关系。', '在线提交', 298, 27, 1),
(2, 3, '发票主体信息维护', '发票信息维护', '财税服务', '中小型', 360.00, 22, '/images/item/tax2.jpg', '维护开票抬头、税号、地址电话及开户行信息。', '在线审核', 211, 19, 1),
(3, 2, '员工档案信息整理', '人事档案整理', '人力资源', '中大型', 480.00, 18, '/images/item/hr1.jpg', '统一员工基础档案模板，支持批量导入与校验。', '在线提交', 335, 40, 1),
(3, 3, '组织架构与岗位库维护', '组织岗位维护', '人力资源', '集团型', 620.00, 10, '/images/item/hr2.jpg', '维护部门层级、岗位编制和职责描述信息。', '在线审核', 174, 12, 1),
(4, 3, '合同台账结构化管理', '合同台账服务', '法务服务', '中大型', 760.00, 9, '/images/item/contract1.jpg', '支持合同类型归档、到期提醒、签署状态管理。', '在线提交', 162, 11, 1),
(5, 2, '资质证照到期预警配置', '证照预警服务', '企业服务', '中小型', 390.00, 20, '/images/item/cert1.jpg', '证照信息集中维护并配置到期预警提醒规则。', '在线审核', 223, 24, 1),
(6, 2, '经营指标信息看板配置', '经营看板配置', '数据服务', '集团型', 880.00, 6, '/images/item/data1.jpg', '按月维护营收、成本、利润等经营指标口径。', '在线提交', 131, 9, 1),
(6, 3, '多公司数据口径统一', '数据口径治理', '数据服务', '集团型', 980.00, 5, '/images/item/data2.jpg', '统一多主体统计口径，支持跨公司指标对齐。', '在线审核', 96, 6, 1);

INSERT INTO info_process (order_no, user_id, service_id, seller_id, quantity, total_price, status, remark, appointment_date, appointment_time, plate_no, vehicle_model, fault_desc, pay_time, finish_time, create_time) VALUES
('EI202602240001', 4, 1, 2, 1, 299.00, 3, '希望本周内完成', '2026-02-22', '10:30:00', '13912345678', '王经理', '企业注册地址与联系电话需要同步更新。', '2026-02-20 09:20:00', '2026-02-22 16:10:00', '2026-02-20 09:12:00'),
('EI202602240002', 4, 5, 2, 1, 480.00, 2, '请优先处理人事档案', '2026-02-25', '09:30:00', '13788886666', '李主管', '员工档案字段不统一，需要标准化处理。', '2026-02-24 11:05:00', NULL, '2026-02-24 10:58:00'),
('EI202602240003', 2, 3, 3, 1, 560.00, 1, '先给核验报告', '2026-02-26', '14:00:00', '13666665555', '赵总', '税务申报历史存在缺失记录，需核验。', '2026-02-24 14:10:00', NULL, '2026-02-24 14:02:00');

INSERT INTO review (order_id, user_id, service_id, rating, content, status, create_time) VALUES
(1, 4, 1, 5, '处理效率很高，企业基础信息已按要求更新完成。', 1, '2026-02-22 18:10:00');

INSERT INTO favorite (user_id, service_id) VALUES
(4, 1),
(4, 5),
(4, 8),
(2, 9);

INSERT INTO complaint (order_id, user_id, target_user_id, type, content, images, status, reply, reply_admin_id, reply_time, create_time) VALUES
(2, 4, 2, 'ORDER', '处理时间有延迟，影响内部档案提报节点，请平台协调。', '', 1, '平台已协调维护方优先处理并给出新的时间承诺。', 1, '2026-02-24 15:40:00', '2026-02-24 13:20:00');

INSERT INTO announcement (title, content, admin_id, status) VALUES
('企业信息模板升级通知', '系统已上线新版企业档案模板，支持批量字段校验。', 1, 1),
('处理单提交规范提醒', '请在提交处理单时完善联系人和处理说明，避免退回。', 1, 1),
('月度数据治理活动', '本月经营数据口径治理服务限时95折。', 1, 1);
