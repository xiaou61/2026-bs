-- 养老院管理系统数据库初始化脚本
CREATE DATABASE IF NOT EXISTS nursing_home DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE nursing_home;

-- 系统用户表
CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT '密码',
    real_name VARCHAR(50) COMMENT '真实姓名',
    phone VARCHAR(20) COMMENT '手机号',
    avatar VARCHAR(255) COMMENT '头像',
    role TINYINT DEFAULT 0 COMMENT '角色: 0-家属 1-护工 2-管理员',
    elder_id BIGINT COMMENT '关联老人ID(家属专用)',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-禁用 1-正常',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT '系统用户表';

-- 楼栋表
CREATE TABLE building (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL COMMENT '楼栋名称',
    floors INT DEFAULT 1 COMMENT '楼层数',
    description VARCHAR(255) COMMENT '描述',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-停用 1-正常',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT '楼栋表';

-- 房间表
CREATE TABLE room (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    building_id BIGINT NOT NULL COMMENT '楼栋ID',
    room_no VARCHAR(20) NOT NULL COMMENT '房间号',
    floor INT DEFAULT 1 COMMENT '所在楼层',
    room_type TINYINT DEFAULT 1 COMMENT '房间类型: 1-单人间 2-双人间 3-多人间',
    bed_count INT DEFAULT 1 COMMENT '床位数',
    price DECIMAL(10,2) DEFAULT 0 COMMENT '月租金',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-停用 1-正常',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT '房间表';

-- 床位表
CREATE TABLE bed (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    room_id BIGINT NOT NULL COMMENT '房间ID',
    bed_no VARCHAR(20) NOT NULL COMMENT '床位号',
    status TINYINT DEFAULT 0 COMMENT '状态: 0-空闲 1-已入住 2-预留',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT '床位表';

-- 老人信息表
CREATE TABLE elder (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    gender TINYINT DEFAULT 1 COMMENT '性别: 1-男 2-女',
    birthday DATE COMMENT '出生日期',
    id_card VARCHAR(18) COMMENT '身份证号',
    phone VARCHAR(20) COMMENT '联系电话',
    photo VARCHAR(255) COMMENT '照片',
    bed_id BIGINT COMMENT '床位ID',
    care_level TINYINT DEFAULT 1 COMMENT '护理等级: 1-自理 2-半护理 3-全护理 4-特护',
    health_status VARCHAR(500) COMMENT '健康状况',
    allergies VARCHAR(255) COMMENT '过敏史',
    emergency_contact VARCHAR(50) COMMENT '紧急联系人',
    emergency_phone VARCHAR(20) COMMENT '紧急联系电话',
    check_in_date DATE COMMENT '入住日期',
    check_out_date DATE COMMENT '退住日期',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-已退住 1-在住',
    remark VARCHAR(500) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT '老人信息表';

-- 健康记录表
CREATE TABLE health_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    elder_id BIGINT NOT NULL COMMENT '老人ID',
    record_date DATE NOT NULL COMMENT '记录日期',
    blood_pressure VARCHAR(20) COMMENT '血压',
    heart_rate INT COMMENT '心率',
    temperature DECIMAL(3,1) COMMENT '体温',
    blood_sugar DECIMAL(4,2) COMMENT '血糖',
    weight DECIMAL(5,2) COMMENT '体重',
    sleep_hours DECIMAL(3,1) COMMENT '睡眠时长',
    appetite TINYINT COMMENT '食欲: 1-差 2-一般 3-好',
    mental_state TINYINT COMMENT '精神状态: 1-差 2-一般 3-好',
    medication VARCHAR(500) COMMENT '用药情况',
    symptoms VARCHAR(500) COMMENT '症状描述',
    recorder_id BIGINT COMMENT '记录人ID',
    remark VARCHAR(500) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT '健康记录表';

-- 护理计划表
CREATE TABLE care_plan (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    elder_id BIGINT NOT NULL COMMENT '老人ID',
    plan_name VARCHAR(100) NOT NULL COMMENT '计划名称',
    care_content TEXT COMMENT '护理内容',
    frequency VARCHAR(50) COMMENT '执行频率',
    start_date DATE COMMENT '开始日期',
    end_date DATE COMMENT '结束日期',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-已结束 1-进行中',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT '护理计划表';

-- 护理记录表
CREATE TABLE care_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    elder_id BIGINT NOT NULL COMMENT '老人ID',
    care_plan_id BIGINT COMMENT '护理计划ID',
    care_type VARCHAR(50) COMMENT '护理类型',
    care_content VARCHAR(500) COMMENT '护理内容',
    care_time DATETIME COMMENT '护理时间',
    caregiver_id BIGINT COMMENT '护工ID',
    remark VARCHAR(500) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT '护理记录表';

-- 费用项目表
CREATE TABLE fee_item (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL COMMENT '费用名称',
    category TINYINT DEFAULT 1 COMMENT '类别: 1-床位费 2-护理费 3-餐费 4-其他',
    price DECIMAL(10,2) DEFAULT 0 COMMENT '单价',
    unit VARCHAR(20) COMMENT '单位',
    description VARCHAR(255) COMMENT '描述',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-停用 1-正常',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT '费用项目表';

-- 月度账单表
CREATE TABLE bill (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    bill_no VARCHAR(50) NOT NULL UNIQUE COMMENT '账单号',
    elder_id BIGINT NOT NULL COMMENT '老人ID',
    bill_month VARCHAR(7) NOT NULL COMMENT '账单月份 yyyy-MM',
    bed_fee DECIMAL(10,2) DEFAULT 0 COMMENT '床位费',
    care_fee DECIMAL(10,2) DEFAULT 0 COMMENT '护理费',
    meal_fee DECIMAL(10,2) DEFAULT 0 COMMENT '餐费',
    other_fee DECIMAL(10,2) DEFAULT 0 COMMENT '其他费用',
    total_amount DECIMAL(10,2) DEFAULT 0 COMMENT '总金额',
    paid_amount DECIMAL(10,2) DEFAULT 0 COMMENT '已付金额',
    status TINYINT DEFAULT 0 COMMENT '状态: 0-待支付 1-部分支付 2-已支付',
    due_date DATE COMMENT '应缴日期',
    remark VARCHAR(500) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT '月度账单表';

-- 缴费记录表
CREATE TABLE payment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    payment_no VARCHAR(50) NOT NULL UNIQUE COMMENT '缴费单号',
    bill_id BIGINT NOT NULL COMMENT '账单ID',
    elder_id BIGINT NOT NULL COMMENT '老人ID',
    amount DECIMAL(10,2) NOT NULL COMMENT '缴费金额',
    pay_method TINYINT DEFAULT 1 COMMENT '支付方式: 1-现金 2-微信 3-支付宝 4-银行转账',
    pay_time DATETIME COMMENT '支付时间',
    operator_id BIGINT COMMENT '操作员ID',
    remark VARCHAR(500) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT '缴费记录表';

-- 探访记录表
CREATE TABLE visit (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    elder_id BIGINT NOT NULL COMMENT '老人ID',
    visitor_name VARCHAR(50) NOT NULL COMMENT '探访人姓名',
    visitor_phone VARCHAR(20) COMMENT '探访人电话',
    relationship VARCHAR(20) COMMENT '与老人关系',
    visit_date DATE NOT NULL COMMENT '探访日期',
    visit_time VARCHAR(20) COMMENT '探访时间段',
    visitor_count INT DEFAULT 1 COMMENT '探访人数',
    purpose VARCHAR(255) COMMENT '探访目的',
    status TINYINT DEFAULT 0 COMMENT '状态: 0-待审核 1-已通过 2-已拒绝 3-已完成 4-已取消',
    apply_user_id BIGINT COMMENT '申请人ID',
    approve_user_id BIGINT COMMENT '审批人ID',
    approve_time DATETIME COMMENT '审批时间',
    approve_remark VARCHAR(255) COMMENT '审批备注',
    remark VARCHAR(500) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT '探访记录表';

-- 公告通知表
CREATE TABLE notice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL COMMENT '标题',
    content TEXT COMMENT '内容',
    type TINYINT DEFAULT 1 COMMENT '类型: 1-公告 2-通知 3-活动',
    target TINYINT DEFAULT 0 COMMENT '对象: 0-全部 1-护工 2-家属',
    publisher_id BIGINT COMMENT '发布人ID',
    publish_time DATETIME COMMENT '发布时间',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-下架 1-发布',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT '公告通知表';

-- 初始数据
-- 管理员账户 admin/admin123
INSERT INTO sys_user (username, password, real_name, phone, role, status) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '系统管理员', '13800000000', 2, 1);

-- 护工账户 nurse1/nurse123
INSERT INTO sys_user (username, password, real_name, phone, role, status) VALUES
('nurse1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '张护士', '13800000001', 1, 1),
('nurse2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '李护士', '13800000002', 1, 1);

-- 楼栋数据
INSERT INTO building (name, floors, description) VALUES
('颐养楼', 5, '主楼，适合自理和半护理老人'),
('康复楼', 3, '康复护理专用楼'),
('特护楼', 2, '全护理和特护专用楼');

-- 房间数据
INSERT INTO room (building_id, room_no, floor, room_type, bed_count, price) VALUES
(1, '101', 1, 1, 1, 3000),
(1, '102', 1, 2, 2, 2500),
(1, '103', 1, 3, 4, 2000),
(1, '201', 2, 1, 1, 3200),
(1, '202', 2, 2, 2, 2700),
(2, '101', 1, 2, 2, 3500),
(2, '102', 1, 2, 2, 3500),
(3, '101', 1, 1, 1, 5000),
(3, '102', 1, 1, 1, 5000);

-- 床位数据
INSERT INTO bed (room_id, bed_no, status) VALUES
(1, '101-1', 1),
(2, '102-1', 1),
(2, '102-2', 0),
(3, '103-1', 1),
(3, '103-2', 0),
(3, '103-3', 0),
(3, '103-4', 0),
(4, '201-1', 0),
(5, '202-1', 0),
(5, '202-2', 0),
(6, '101-1', 0),
(6, '101-2', 0),
(7, '102-1', 0),
(7, '102-2', 0),
(8, '101-1', 0),
(9, '102-1', 0);

-- 老人数据
INSERT INTO elder (name, gender, birthday, id_card, phone, bed_id, care_level, health_status, emergency_contact, emergency_phone, check_in_date, status) VALUES
('张大爷', 1, '1940-05-15', '110101194005150011', '13900000001', 1, 1, '身体健康，能自理', '张明', '13900000011', '2024-01-15', 1),
('李奶奶', 2, '1938-08-20', '110101193808200022', '13900000002', 2, 2, '高血压，需定期测量', '李华', '13900000012', '2024-02-01', 1),
('王大爷', 1, '1942-03-10', '110101194203100033', '13900000003', 4, 3, '糖尿病，需注射胰岛素', '王强', '13900000013', '2024-03-10', 1);

-- 更新家属账户关联老人
INSERT INTO sys_user (username, password, real_name, phone, role, elder_id, status) VALUES
('family1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '张明', '13900000011', 0, 1, 1),
('family2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '李华', '13900000012', 0, 2, 1);

-- 费用项目
INSERT INTO fee_item (name, category, price, unit, description) VALUES
('单人间床位费', 1, 3000, '月', '单人间每月床位费'),
('双人间床位费', 1, 2500, '月', '双人间每月床位费'),
('多人间床位费', 1, 2000, '月', '多人间每月床位费'),
('自理护理费', 2, 500, '月', '自理老人护理费'),
('半护理费', 2, 1500, '月', '半护理老人护理费'),
('全护理费', 2, 3000, '月', '全护理老人护理费'),
('特护费', 2, 5000, '月', '特护老人护理费'),
('标准餐费', 3, 900, '月', '三餐标准餐费'),
('特殊餐费', 3, 1200, '月', '特殊饮食餐费');

-- 护理计划
INSERT INTO care_plan (elder_id, plan_name, care_content, frequency, start_date, status) VALUES
(1, '日常护理', '每日查房、协助用餐、陪同散步', '每日', '2024-01-15', 1),
(2, '血压监测', '每日测量血压并记录，异常及时报告', '每日2次', '2024-02-01', 1),
(3, '糖尿病护理', '每日测血糖、注射胰岛素、饮食控制', '每日3次', '2024-03-10', 1);

-- 公告通知
INSERT INTO notice (title, content, type, target, publisher_id, publish_time, status) VALUES
('春节放假通知', '春节期间探访时间调整为上午9:00-11:00，下午14:00-16:00，请各位家属提前预约。', 1, 0, 1, NOW(), 1),
('健康讲座活动', '本周六上午10点将举办老年人健康养生讲座，欢迎各位老人和家属参加。', 3, 0, 1, NOW(), 1);
