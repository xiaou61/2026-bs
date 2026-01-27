CREATE DATABASE IF NOT EXISTS disaster_rescue DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_general_ci;
USE disaster_rescue;

CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    real_name VARCHAR(50),
    phone VARCHAR(20),
    avatar VARCHAR(255),
    role VARCHAR(20) NOT NULL DEFAULT 'REPORTER',
    status TINYINT NOT NULL DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE disaster (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    type VARCHAR(20) NOT NULL,
    level TINYINT NOT NULL DEFAULT 1,
    province VARCHAR(50),
    city VARCHAR(50),
    district VARCHAR(50),
    address VARCHAR(200),
    longitude DECIMAL(10,6),
    latitude DECIMAL(10,6),
    affected_count INT DEFAULT 0,
    description TEXT,
    images VARCHAR(1000),
    status TINYINT NOT NULL DEFAULT 0,
    reporter_id BIGINT,
    report_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE material_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    icon VARCHAR(255),
    sort INT DEFAULT 0,
    status TINYINT NOT NULL DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE material (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    category_id BIGINT,
    name VARCHAR(100) NOT NULL,
    spec VARCHAR(100),
    unit VARCHAR(20),
    image VARCHAR(255),
    safe_stock INT DEFAULT 0,
    description VARCHAR(500),
    status TINYINT NOT NULL DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE warehouse (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50),
    address VARCHAR(200),
    longitude DECIMAL(10,6),
    latitude DECIMAL(10,6),
    capacity INT DEFAULT 0,
    manager_id BIGINT,
    phone VARCHAR(20),
    status TINYINT NOT NULL DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE stock (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    warehouse_id BIGINT NOT NULL,
    material_id BIGINT NOT NULL,
    quantity INT DEFAULT 0,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_warehouse_material (warehouse_id, material_id)
);

CREATE TABLE stock_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    warehouse_id BIGINT NOT NULL,
    material_id BIGINT NOT NULL,
    type TINYINT NOT NULL,
    quantity INT NOT NULL,
    before_quantity INT DEFAULT 0,
    after_quantity INT DEFAULT 0,
    source VARCHAR(200),
    operator_id BIGINT,
    remark VARCHAR(500),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE dispatch (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    dispatch_no VARCHAR(50) NOT NULL UNIQUE,
    disaster_id BIGINT,
    from_warehouse_id BIGINT,
    to_address VARCHAR(200),
    to_longitude DECIMAL(10,6),
    to_latitude DECIMAL(10,6),
    priority TINYINT DEFAULT 1,
    status TINYINT DEFAULT 0,
    applicant_id BIGINT,
    approver_id BIGINT,
    approve_time DATETIME,
    approve_remark VARCHAR(500),
    complete_time DATETIME,
    remark VARCHAR(500),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE dispatch_item (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    dispatch_id BIGINT NOT NULL,
    material_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE rescue_task (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    task_no VARCHAR(50) NOT NULL UNIQUE,
    disaster_id BIGINT,
    title VARCHAR(100) NOT NULL,
    content TEXT,
    priority TINYINT DEFAULT 1,
    status TINYINT DEFAULT 0,
    assignee_id BIGINT,
    publisher_id BIGINT,
    start_time DATETIME,
    end_time DATETIME,
    feedback TEXT,
    feedback_images VARCHAR(1000),
    feedback_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE notice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    content TEXT,
    type TINYINT DEFAULT 1,
    status TINYINT DEFAULT 0,
    publisher_id BIGINT,
    publish_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE operation_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    username VARCHAR(50),
    module VARCHAR(50),
    operation VARCHAR(100),
    method VARCHAR(200),
    params TEXT,
    ip VARCHAR(50),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO user (username, password, real_name, phone, role, status) VALUES
('admin', 'e10adc3949ba59abbe56e057f20f883e', '系统管理员', '13800000001', 'ADMIN', 1),
('warehouse', 'e10adc3949ba59abbe56e057f20f883e', '仓库管理员', '13800000002', 'WAREHOUSE', 1),
('rescuer', 'e10adc3949ba59abbe56e057f20f883e', '救援人员', '13800000003', 'RESCUER', 1),
('reporter', 'e10adc3949ba59abbe56e057f20f883e', '信息员', '13800000004', 'REPORTER', 1);

INSERT INTO material_category (name, icon, sort) VALUES
('食品物资', 'icon-food', 1),
('医疗物资', 'icon-medical', 2),
('生活物资', 'icon-life', 3),
('救援设备', 'icon-equipment', 4),
('其他物资', 'icon-other', 5);

INSERT INTO material (category_id, name, spec, unit, safe_stock) VALUES
(1, '矿泉水', '550ml/瓶', '箱', 1000),
(1, '方便面', '桶装', '箱', 500),
(1, '压缩饼干', '500g/袋', '箱', 300),
(2, '医用口罩', '一次性', '盒', 2000),
(2, '消毒液', '500ml/瓶', '箱', 500),
(2, '医用纱布', '10cm*10cm', '包', 1000),
(3, '帐篷', '4人帐', '顶', 200),
(3, '棉被', '200*230cm', '床', 500),
(3, '折叠床', '钢架折叠', '张', 300),
(4, '手电筒', 'LED充电', '个', 200),
(4, '对讲机', '5W', '台', 50),
(4, '发电机', '3KW', '台', 20);

INSERT INTO warehouse (name, code, address, capacity, phone) VALUES
('中心仓库', 'WH001', '北京市朝阳区救灾物资储备中心', 10000, '010-12345678'),
('东区仓库', 'WH002', '北京市通州区物资储备库', 5000, '010-23456789'),
('西区仓库', 'WH003', '北京市海淀区应急物资库', 5000, '010-34567890');

INSERT INTO stock (warehouse_id, material_id, quantity) VALUES
(1, 1, 500), (1, 2, 300), (1, 3, 200), (1, 4, 1000), (1, 5, 300),
(1, 6, 500), (1, 7, 100), (1, 8, 300), (1, 9, 150), (1, 10, 100),
(2, 1, 300), (2, 2, 200), (2, 4, 500), (2, 7, 50), (2, 8, 200),
(3, 1, 200), (3, 3, 100), (3, 5, 200), (3, 9, 100), (3, 11, 30);

INSERT INTO disaster (title, type, level, province, city, district, address, affected_count, description, status, reporter_id, report_time) VALUES
('暴雨洪涝灾害', 'FLOOD', 3, '河南省', '郑州市', '中原区', '某某街道', 5000, '持续暴雨导致多处积水，道路中断', 1, 4, NOW()),
('山体滑坡', 'OTHER', 2, '四川省', '成都市', '都江堰市', '某某镇', 200, '连续降雨引发山体滑坡', 0, 4, NOW());

INSERT INTO notice (title, content, type, status, publisher_id, publish_time) VALUES
('救灾物资调度系统上线通知', '系统已正式上线运行，请各部门配合使用', 2, 1, 1, NOW()),
('物资清点通知', '请各仓库于本周内完成物资清点工作', 1, 1, 1, NOW());
