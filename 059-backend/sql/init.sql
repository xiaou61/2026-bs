DROP DATABASE IF EXISTS mfg_erp;
CREATE DATABASE mfg_erp DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE mfg_erp;

CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    real_name VARCHAR(50),
    phone VARCHAR(20),
    role VARCHAR(20) NOT NULL DEFAULT 'operator',
    status INT NOT NULL DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_username (username)
);

CREATE TABLE equipment_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE equipment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    category_id BIGINT,
    model VARCHAR(100),
    manufacturer VARCHAR(100),
    purchase_date DATE,
    location VARCHAR(200),
    status VARCHAR(20) NOT NULL DEFAULT 'stopped',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_code (code)
);

CREATE TABLE sensor_data (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    equipment_id BIGINT NOT NULL,
    temperature DECIMAL(10,2),
    pressure DECIMAL(10,2),
    vibration DECIMAL(10,2),
    speed DECIMAL(10,2),
    collect_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_equipment_id (equipment_id),
    INDEX idx_collect_time (collect_time)
);

CREATE TABLE alert_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    equipment_id BIGINT NOT NULL,
    alert_type VARCHAR(50),
    alert_level VARCHAR(20) NOT NULL DEFAULT 'normal',
    content VARCHAR(500),
    status INT NOT NULL DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    handle_time DATETIME,
    INDEX idx_equipment_id (equipment_id),
    INDEX idx_status (status)
);

CREATE TABLE product (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    spec VARCHAR(200),
    unit VARCHAR(20),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_code (code)
);

CREATE TABLE production_order (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_no VARCHAR(50) NOT NULL,
    product_id BIGINT,
    equipment_id BIGINT,
    plan_quantity INT DEFAULT 0,
    actual_quantity INT DEFAULT 0,
    status VARCHAR(20) NOT NULL DEFAULT 'pending',
    plan_start_date DATE,
    plan_end_date DATE,
    actual_start_date DATE,
    actual_end_date DATE,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_order_no (order_no)
);

CREATE TABLE material (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    spec VARCHAR(200),
    unit VARCHAR(20),
    stock_quantity DECIMAL(10,2) DEFAULT 0,
    safe_stock DECIMAL(10,2) DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_code (code)
);

CREATE TABLE material_stock_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    material_id BIGINT NOT NULL,
    type VARCHAR(10) NOT NULL,
    quantity DECIMAL(10,2) NOT NULL,
    reason VARCHAR(200),
    operator VARCHAR(50),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_material_id (material_id)
);

CREATE TABLE quality_inspection (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT,
    product_id BIGINT,
    inspect_quantity INT DEFAULT 0,
    qualified_quantity INT DEFAULT 0,
    unqualified_quantity INT DEFAULT 0,
    result VARCHAR(20) DEFAULT 'passed',
    inspector VARCHAR(50),
    remark VARCHAR(500),
    inspect_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE maintenance_plan (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    equipment_id BIGINT NOT NULL,
    plan_name VARCHAR(100) NOT NULL,
    cycle_days INT DEFAULT 30,
    last_maintain_date DATE,
    next_maintain_date DATE,
    content VARCHAR(500),
    status VARCHAR(20) NOT NULL DEFAULT 'active',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE maintenance_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    plan_id BIGINT,
    equipment_id BIGINT NOT NULL,
    maintain_type VARCHAR(20) DEFAULT 'regular',
    content VARCHAR(500),
    maintainer VARCHAR(50),
    cost DECIMAL(10,2) DEFAULT 0,
    status VARCHAR(20) NOT NULL DEFAULT 'pending',
    start_time DATETIME,
    end_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO user (username, password, real_name, phone, role, status) VALUES
('admin', '123456', '系统管理员', '13800000001', 'admin', 1),
('manager', '123456', '张经理', '13800000002', 'manager', 1),
('operator', '123456', '李操作员', '13800000003', 'operator', 1),
('inspector', '123456', '王质检员', '13800000004', 'inspector', 1);

INSERT INTO equipment_category (name, description) VALUES
('数控机床', 'CNC数控加工中心、车床、铣床等'),
('冲压设备', '液压冲床、气动冲床等冲压成型设备'),
('焊接设备', '自动焊接机器人、手动焊接工位'),
('注塑设备', '注塑成型机及辅助设备'),
('检测设备', '三坐标测量仪、硬度计等检测设备');

INSERT INTO equipment (code, name, category_id, model, manufacturer, purchase_date, location, status) VALUES
('EQ-CNC-001', '五轴加工中心A1', 1, 'VMC-850', '大连机床', '2023-03-15', 'A车间-1号工位', 'running'),
('EQ-CNC-002', '数控车床B1', 1, 'CK6140', '沈阳机床', '2023-05-20', 'A车间-2号工位', 'running'),
('EQ-PRS-001', '液压冲床C1', 2, 'YH-200T', '扬力集团', '2022-11-10', 'B车间-1号工位', 'stopped'),
('EQ-WLD-001', '焊接机器人D1', 3, 'RA-1520', '松下', '2024-01-08', 'C车间-1号工位', 'running'),
('EQ-INJ-001', '注塑机E1', 4, 'MA-1600', '海天国际', '2023-08-22', 'D车间-1号工位', 'repairing'),
('EQ-TST-001', '三坐标测量仪F1', 5, 'CONTURA', '蔡司', '2023-06-01', 'E车间-检测室', 'running');

INSERT INTO sensor_data (equipment_id, temperature, pressure, vibration, speed, collect_time) VALUES
(1, 45.2, 6.8, 0.12, 3500, '2026-02-07 08:00:00'),
(1, 46.1, 6.9, 0.13, 3520, '2026-02-07 08:05:00'),
(1, 47.8, 7.1, 0.15, 3480, '2026-02-07 08:10:00'),
(2, 38.5, 5.2, 0.08, 2800, '2026-02-07 08:00:00'),
(2, 39.1, 5.3, 0.09, 2820, '2026-02-07 08:05:00'),
(4, 52.3, 4.5, 0.22, 1200, '2026-02-07 08:00:00'),
(4, 53.1, 4.6, 0.24, 1180, '2026-02-07 08:05:00');

INSERT INTO alert_record (equipment_id, alert_type, alert_level, content, status, create_time) VALUES
(1, '温度告警', 'normal', '设备温度偏高，当前47.8°C，阈值45°C', 1, '2026-02-07 08:10:00'),
(5, '故障告警', 'urgent', '注塑机E1液压系统压力异常，已自动停机', 0, '2026-02-07 07:30:00'),
(4, '振动告警', 'important', '焊接机器人D1振动值超标，建议检查', 0, '2026-02-07 07:45:00');

INSERT INTO product (code, name, spec, unit) VALUES
('PRD-001', '精密齿轮', 'φ80×30mm 45#钢', '件'),
('PRD-002', '连接法兰', 'DN100 PN16 304SS', '件'),
('PRD-003', '电机外壳', '铝合金 AL6061', '件'),
('PRD-004', '注塑件A', 'ABS 120×80×25mm', '件'),
('PRD-005', '焊接组件B', 'Q235+304 组焊件', '套');

INSERT INTO production_order (order_no, product_id, equipment_id, plan_quantity, actual_quantity, status, plan_start_date, plan_end_date, actual_start_date) VALUES
('WO-20260201-001', 1, 1, 500, 320, 'producing', '2026-02-01', '2026-02-10', '2026-02-01'),
('WO-20260201-002', 2, 2, 200, 200, 'completed', '2026-02-01', '2026-02-05', '2026-02-01'),
('WO-20260203-001', 3, 1, 300, 0, 'pending', '2026-02-10', '2026-02-15', NULL),
('WO-20260205-001', 4, 5, 1000, 450, 'producing', '2026-02-05', '2026-02-12', '2026-02-05'),
('WO-20260206-001', 5, 4, 150, 0, 'pending', '2026-02-12', '2026-02-18', NULL);

INSERT INTO material (code, name, spec, unit, stock_quantity, safe_stock) VALUES
('MAT-001', '45号钢圆棒', 'φ85×200mm', '根', 350, 100),
('MAT-002', '304不锈钢板', '3mm×1220×2440', '张', 45, 20),
('MAT-003', '铝合金棒', 'AL6061 φ120×300mm', '根', 180, 50),
('MAT-004', 'ABS原料', '通用级 白色', 'kg', 2500, 500),
('MAT-005', '焊丝', 'ER308L φ1.2mm', '盘', 30, 10),
('MAT-006', '液压油', '46号抗磨液压油', 'L', 800, 200);

INSERT INTO material_stock_record (material_id, type, quantity, reason, operator, create_time) VALUES
(1, 'in', 200, '采购入库', '张经理', '2026-02-01 09:00:00'),
(1, 'out', 50, '生产领料-WO-20260201-001', '李操作员', '2026-02-02 08:30:00'),
(4, 'in', 1000, '采购入库', '张经理', '2026-02-03 10:00:00'),
(4, 'out', 300, '生产领料-WO-20260205-001', '李操作员', '2026-02-05 08:00:00'),
(5, 'in', 20, '采购入库', '张经理', '2026-02-04 14:00:00');

INSERT INTO quality_inspection (order_id, product_id, inspect_quantity, qualified_quantity, unqualified_quantity, result, inspector, remark, inspect_time) VALUES
(2, 2, 200, 195, 5, 'passed', '王质检员', '5件表面缺陷', '2026-02-06 10:00:00'),
(1, 1, 150, 148, 2, 'passed', '王质检员', '2件尺寸超差', '2026-02-05 15:00:00'),
(4, 4, 200, 180, 20, 'failed', '王质检员', '不良率10%偏高,需调整工艺', '2026-02-06 16:00:00');

INSERT INTO maintenance_plan (equipment_id, plan_name, cycle_days, last_maintain_date, next_maintain_date, content, status) VALUES
(1, '五轴加工中心定期保养', 30, '2026-01-15', '2026-02-14', '更换润滑油、检查主轴精度、清洁导轨', 'active'),
(2, '数控车床月度保养', 30, '2026-01-20', '2026-02-19', '检查卡盘、更换冷却液、校准尾座', 'active'),
(5, '注塑机液压系统检修', 90, '2025-11-10', '2026-02-08', '更换液压油、检查密封件、清洗滤芯', 'active');

INSERT INTO maintenance_record (plan_id, equipment_id, maintain_type, content, maintainer, cost, status, start_time, end_time, create_time) VALUES
(1, 1, 'regular', '已更换润滑油5L，主轴精度正常', '李操作员', 350.00, 'completed', '2026-01-15 09:00:00', '2026-01-15 12:00:00', '2026-01-15 09:00:00'),
(3, 5, 'repair', '液压系统压力异常，更换密封件', '李操作员', 1200.00, 'processing', '2026-02-07 08:00:00', NULL, '2026-02-07 08:00:00');
