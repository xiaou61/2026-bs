CREATE DATABASE IF NOT EXISTS express_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE express_system;

DROP TABLE IF EXISTS user;
CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_id VARCHAR(20) UNIQUE COMMENT '学号/工号',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    real_name VARCHAR(50) NOT NULL COMMENT '真实姓名',
    phone VARCHAR(11) NOT NULL COMMENT '手机号',
    role VARCHAR(20) NOT NULL DEFAULT 'STUDENT' COMMENT '角色 STUDENT/COURIER/STATION_ADMIN/ADMIN',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态 0禁用 1正常',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_phone(phone),
    INDEX idx_student_id(student_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

DROP TABLE IF EXISTS express;
CREATE TABLE express (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    tracking_number VARCHAR(50) NOT NULL UNIQUE COMMENT '快递单号',
    express_company VARCHAR(50) NOT NULL COMMENT '快递公司',
    pickup_code VARCHAR(10) NOT NULL COMMENT '取件码',
    recipient_name VARCHAR(50) COMMENT '收件人姓名',
    recipient_phone VARCHAR(11) NOT NULL COMMENT '收件人手机号',
    recipient_id BIGINT COMMENT '收件人用户ID',
    station_id BIGINT NOT NULL COMMENT '代收点ID',
    shelf_location VARCHAR(20) COMMENT '货架位置',
    status TINYINT NOT NULL DEFAULT 0 COMMENT '状态 0待取件 1已取件 2超期 3退件 4取消',
    in_time DATETIME NOT NULL COMMENT '入库时间',
    out_time DATETIME COMMENT '取件时间',
    in_operator_id BIGINT NOT NULL COMMENT '入库操作员ID',
    in_operator_name VARCHAR(50) NOT NULL COMMENT '入库操作员姓名',
    out_operator_id BIGINT COMMENT '取件操作员ID',
    out_operator_name VARCHAR(50) COMMENT '取件操作员姓名',
    overdue_days INT DEFAULT 0 COMMENT '超期天数',
    overdue_fee DECIMAL(10,2) DEFAULT 0.00 COMMENT '超期费用',
    is_notified TINYINT DEFAULT 0 COMMENT '是否已通知 0否 1是',
    remark VARCHAR(200) COMMENT '备注',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_pickup_code(pickup_code),
    INDEX idx_tracking_number(tracking_number),
    INDEX idx_recipient_phone(recipient_phone),
    INDEX idx_recipient_id(recipient_id),
    INDEX idx_station_id(station_id),
    INDEX idx_status(status),
    INDEX idx_in_time(in_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='快递表';

DROP TABLE IF EXISTS station;
CREATE TABLE station (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL COMMENT '代收点名称',
    address VARCHAR(200) NOT NULL COMMENT '地址',
    contact_phone VARCHAR(20) NOT NULL COMMENT '联系电话',
    business_hours VARCHAR(50) DEFAULT '8:00-20:00' COMMENT '营业时间',
    manager_id BIGINT COMMENT '负责人ID',
    manager_name VARCHAR(50) COMMENT '负责人姓名',
    shelf_capacity INT DEFAULT 500 COMMENT '货架容量',
    current_stock INT DEFAULT 0 COMMENT '当前库存',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态 0暂停 1营业中',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='代收点表';

DROP TABLE IF EXISTS express_company;
CREATE TABLE express_company (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL COMMENT '公司名称',
    code VARCHAR(20) NOT NULL UNIQUE COMMENT '公司代码',
    phone VARCHAR(20) COMMENT '客服电话',
    logo VARCHAR(200) COMMENT '公司Logo',
    tracking_url VARCHAR(200) COMMENT '物流查询网址',
    sort_order INT DEFAULT 0 COMMENT '排序',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态 0禁用 1启用',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='快递公司表';

DROP TABLE IF EXISTS overdue_record;
CREATE TABLE overdue_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    express_id BIGINT NOT NULL COMMENT '快递ID',
    tracking_number VARCHAR(50) NOT NULL COMMENT '快递单号',
    recipient_id BIGINT COMMENT '收件人ID',
    recipient_name VARCHAR(50) COMMENT '收件人姓名',
    overdue_days INT NOT NULL COMMENT '超期天数',
    overdue_fee DECIMAL(10,2) NOT NULL COMMENT '超期费用',
    payment_status TINYINT DEFAULT 0 COMMENT '支付状态 0未支付 1已支付',
    payment_time DATETIME COMMENT '支付时间',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_express_id(express_id),
    INDEX idx_recipient_id(recipient_id),
    INDEX idx_payment_status(payment_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='超期记录表';

DROP TABLE IF EXISTS notification;
CREATE TABLE notification (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    type VARCHAR(20) NOT NULL COMMENT '类型 ARRIVAL/OVERDUE/RETURN',
    title VARCHAR(100) NOT NULL COMMENT '标题',
    content TEXT NOT NULL COMMENT '内容',
    express_id BIGINT COMMENT '关联快递ID',
    is_read TINYINT DEFAULT 0 COMMENT '是否已读 0否 1是',
    send_method VARCHAR(20) DEFAULT 'SYSTEM' COMMENT '发送方式 SMS/SYSTEM',
    send_status TINYINT DEFAULT 1 COMMENT '发送状态 0失败 1成功',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_user_id(user_id),
    INDEX idx_is_read(is_read),
    INDEX idx_create_time(create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='通知记录表';

DROP TABLE IF EXISTS system_config;
CREATE TABLE system_config (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    config_key VARCHAR(50) NOT NULL UNIQUE COMMENT '配置键',
    config_value VARCHAR(500) NOT NULL COMMENT '配置值',
    description VARCHAR(200) COMMENT '说明',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置表';

DROP TABLE IF EXISTS operation_log;
CREATE TABLE operation_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    operator_id BIGINT NOT NULL COMMENT '操作人ID',
    operator_name VARCHAR(50) NOT NULL COMMENT '操作人姓名',
    operation_type VARCHAR(50) NOT NULL COMMENT '操作类型 IN/OUT/UPDATE/DELETE',
    operation_desc VARCHAR(200) COMMENT '操作描述',
    express_id BIGINT COMMENT '关联快递ID',
    ip_address VARCHAR(50) COMMENT 'IP地址',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
    INDEX idx_operator_id(operator_id),
    INDEX idx_operation_type(operation_type),
    INDEX idx_create_time(create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';

INSERT INTO user (student_id, username, password, real_name, phone, role, status) VALUES
('ADMIN001', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '系统管理员', '13800000000', 'ADMIN', 1),
('SA001', 'station1', 'e10adc3949ba59abbe56e057f20f883e', '东区驿站管理员', '13800000001', 'STATION_ADMIN', 1),
('SA002', 'station2', 'e10adc3949ba59abbe56e057f20f883e', '西区驿站管理员', '13800000002', 'STATION_ADMIN', 1),
('C001', 'courier1', 'e10adc3949ba59abbe56e057f20f883e', '快递员张三', '13800000003', 'COURIER', 1),
('2021001', 'student1', 'e10adc3949ba59abbe56e057f20f883e', '李明', '13800001001', 'STUDENT', 1),
('2021002', 'student2', 'e10adc3949ba59abbe56e057f20f883e', '王芳', '13800001002', 'STUDENT', 1),
('2021003', 'student3', 'e10adc3949ba59abbe56e057f20f883e', '张伟', '13800001003', 'STUDENT', 1);

INSERT INTO station (name, address, contact_phone, business_hours, manager_id, manager_name, shelf_capacity, current_stock, status) VALUES
('东区菜鸟驿站', '校园东区1号楼底商', '0571-88888888', '8:00-20:00', 2, '东区驿站管理员', 500, 0, 1),
('西区快递中心', '校园西区生活广场2楼', '0571-88888889', '8:00-21:00', 3, '西区驿站管理员', 800, 0, 1),
('北区驿站', '校园北区宿舍楼1层', '0571-88888887', '9:00-19:00', NULL, NULL, 300, 0, 1);

INSERT INTO express_company (name, code, phone, tracking_url, sort_order, status) VALUES
('顺丰速运', 'SF', '95338', 'https://www.sf-express.com', 1, 1),
('圆通速递', 'YTO', '95554', 'https://www.yto.net.cn', 2, 1),
('中通快递', 'ZTO', '95311', 'https://www.zto.com', 3, 1),
('申通快递', 'STO', '95543', 'https://www.sto.cn', 4, 1),
('韵达快递', 'YD', '95546', 'https://www.yundaex.com', 5, 1),
('京东快递', 'JD', '950616', 'https://www.jdl.com', 6, 1),
('邮政EMS', 'EMS', '11183', 'https://www.ems.com.cn', 7, 1),
('极兔速递', 'JT', '95323', 'https://www.jtexpress.com.cn', 8, 1),
('邮政包裹', 'YZPY', '11183', 'https://www.chinapost.com.cn', 9, 1),
('菜鸟速递', 'CNSD', '95105', 'https://www.cainiao.com', 10, 1);

INSERT INTO system_config (config_key, config_value, description) VALUES
('free_days', '3', '免费保管天数'),
('overdue_fee_level1', '1.0', '超期4-7天每天收费(元)'),
('overdue_fee_level2', '2.0', '超期8天及以上每天收费(元)'),
('overdue_notify_days', '3,5', '超期提醒天数(逗号分隔)'),
('long_overdue_days', '15', '长期滞留天数');

INSERT INTO express (tracking_number, express_company, pickup_code, recipient_name, recipient_phone, recipient_id, station_id, shelf_location, status, in_time, in_operator_id, in_operator_name, overdue_days, overdue_fee) VALUES
('SF1234567890123', '顺丰速运', '123456', '李明', '13800001001', 5, 1, 'A-01-01', 0, DATE_SUB(NOW(), INTERVAL 1 DAY), 4, '快递员张三', 0, 0.00),
('YTO9876543210987', '圆通速递', '234567', '王芳', '13800001002', 6, 1, 'A-02-05', 0, DATE_SUB(NOW(), INTERVAL 2 DAY), 4, '快递员张三', 0, 0.00),
('ZTO5555666677778', '中通快递', '345678', '张伟', '13800001003', 7, 2, 'B-01-10', 0, DATE_SUB(NOW(), INTERVAL 4 DAY), 2, '东区驿站管理员', 1, 1.00),
('JD8888999900001', '京东快递', '456789', '李明', '13800001001', 5, 1, 'A-03-08', 0, DATE_SUB(NOW(), INTERVAL 5 DAY), 4, '快递员张三', 2, 2.00);

INSERT INTO notification (user_id, type, title, content, express_id, is_read, send_method, send_status) VALUES
(5, 'ARRIVAL', '快递到达通知', '您的顺丰快递(SF1234567890123)已到达东区菜鸟驿站，取件码：123456，请及时取件。', 1, 0, 'SYSTEM', 1),
(6, 'ARRIVAL', '快递到达通知', '您的圆通快递(YTO9876543210987)已到达东区菜鸟驿站，取件码：234567，请及时取件。', 2, 0, 'SYSTEM', 1),
(7, 'OVERDUE', '超期提醒', '您的中通快递(ZTO5555666677778)已超期1天，请尽快取件。', 3, 0, 'SYSTEM', 1),
(5, 'OVERDUE', '超期提醒', '您的京东快递(JD8888999900001)已超期2天，请尽快取件。', 4, 0, 'SYSTEM', 1);

