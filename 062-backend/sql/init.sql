DROP DATABASE IF EXISTS anti_fraud;
CREATE DATABASE anti_fraud DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE anti_fraud;

CREATE TABLE `user` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50),
    avatar VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(100),
    role VARCHAR(20) DEFAULT 'USER',
    status INT DEFAULT 1,
    last_login_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE blacklist (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(20) NOT NULL,
    value VARCHAR(100) NOT NULL,
    reason VARCHAR(255),
    level INT DEFAULT 2,
    status INT DEFAULT 1,
    creator_id BIGINT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_blacklist_type_value(type, value)
);

CREATE TABLE risk_rule (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rule_name VARCHAR(80) NOT NULL,
    rule_code VARCHAR(50) NOT NULL UNIQUE,
    rule_type VARCHAR(30) NOT NULL,
    threshold DECIMAL(12,2) DEFAULT 0,
    weight INT DEFAULT 10,
    hit_count INT DEFAULT 0,
    description VARCHAR(255),
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE risk_event (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    event_no VARCHAR(50) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    account_no VARCHAR(80) NOT NULL,
    device_id VARCHAR(80),
    ip VARCHAR(50),
    amount DECIMAL(12,2) NOT NULL,
    event_type VARCHAR(40) NOT NULL,
    channel VARCHAR(40) NOT NULL,
    risk_score INT DEFAULT 0,
    risk_level VARCHAR(20) DEFAULT 'LOW',
    status INT DEFAULT 1,
    hit_rules VARCHAR(500),
    remark VARCHAR(255),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_event_user(user_id),
    INDEX idx_event_level(risk_level),
    INDEX idx_event_status(status)
);

CREATE TABLE fraud_alert (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    alert_no VARCHAR(50) NOT NULL UNIQUE,
    event_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    risk_score INT DEFAULT 0,
    risk_level VARCHAR(20) DEFAULT 'LOW',
    status INT DEFAULT 0,
    assignee_id BIGINT,
    handle_result VARCHAR(100),
    handle_remark VARCHAR(500),
    handle_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_alert_event(event_id),
    INDEX idx_alert_user(user_id),
    INDEX idx_alert_status(status)
);

CREATE TABLE fraud_case (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    case_no VARCHAR(50) NOT NULL UNIQUE,
    alert_id BIGINT NOT NULL,
    case_type VARCHAR(40) NOT NULL,
    status INT DEFAULT 0,
    priority INT DEFAULT 2,
    owner_id BIGINT,
    summary VARCHAR(500),
    result VARCHAR(500),
    close_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_case_alert(alert_id),
    INDEX idx_case_owner(owner_id)
);

CREATE TABLE appeal (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    alert_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    content VARCHAR(1000) NOT NULL,
    status INT DEFAULT 0,
    reply VARCHAR(1000),
    reply_admin_id BIGINT,
    reply_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_appeal_alert(alert_id),
    INDEX idx_appeal_user(user_id)
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

CREATE TABLE operation_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    module VARCHAR(40) NOT NULL,
    action VARCHAR(40) NOT NULL,
    operator_id BIGINT,
    operator_role VARCHAR(20),
    biz_no VARCHAR(120),
    content VARCHAR(500),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_log_module(module),
    INDEX idx_log_operator(operator_id),
    INDEX idx_log_time(create_time)
);

INSERT INTO `user` (username, password, nickname, phone, email, role, status) VALUES
('admin', '123456', '系统管理员', '13800010001', 'admin@fraud.com', 'ADMIN', 1),
('analyst', '123456', '风控分析员', '13800010002', 'analyst@fraud.com', 'ANALYST', 1),
('user1', '123456', '商户甲', '13800010003', 'user1@fraud.com', 'USER', 1),
('user2', '123456', '商户乙', '13800010004', 'user2@fraud.com', 'USER', 1);

INSERT INTO blacklist (type, value, reason, level, status, creator_id) VALUES
('ACCOUNT', 'ACC-90001', '历史欺诈账号', 3, 1, 1),
('DEVICE', 'DEV-556677', '高风险设备', 2, 1, 2),
('IP', '10.10.10.9', '异常代理出口', 2, 1, 2);

INSERT INTO risk_rule (rule_name, rule_code, rule_type, threshold, weight, hit_count, description, status) VALUES
('大额交易预警', 'RULE_AMOUNT_5000', 'AMOUNT', 5000.00, 40, 6, '单笔金额超过5000', 1),
('24小时频次预警', 'RULE_FREQ_5', 'FREQUENCY', 5.00, 30, 4, '24小时事件次数超过5次', 1);

INSERT INTO risk_event (event_no, user_id, account_no, device_id, ip, amount, event_type, channel, risk_score, risk_level, status, hit_rules, remark, create_time) VALUES
('EV202602130001', 3, 'ACC-10001', 'DEV-1010', '10.10.10.1', 800.00, 'LOGIN', 'WEB', 10, 'LOW', 1, '', '常规登录', '2026-02-12 09:20:00'),
('EV202602130002', 3, 'ACC-90001', 'DEV-556677', '10.10.10.9', 12000.00, 'PAY', 'API', 105, 'HIGH', 2, 'BLACKLIST_ACCOUNT,BLACKLIST_DEVICE,BLACKLIST_IP,RULE_AMOUNT_5000', '可疑付款', '2026-02-12 11:30:00'),
('EV202602130003', 4, 'ACC-20001', 'DEV-8899', '10.10.10.8', 6200.00, 'WITHDRAW', 'APP', 55, 'MEDIUM', 3, 'RULE_AMOUNT_5000', '提现风控', '2026-02-12 15:00:00');

INSERT INTO fraud_alert (alert_no, event_id, user_id, risk_score, risk_level, status, assignee_id, handle_result, handle_remark, handle_time, create_time) VALUES
('AL202602130001', 2, 3, 105, 'HIGH', 2, 2, '确认欺诈', '命中多重规则，建议冻结', '2026-02-12 12:10:00', '2026-02-12 11:31:00'),
('AL202602130002', 3, 4, 55, 'MEDIUM', 1, 2, '人工复核中', '需补充交易凭证', NULL, '2026-02-12 15:01:00');

INSERT INTO fraud_case (case_no, alert_id, case_type, status, priority, owner_id, summary, result, close_time, create_time) VALUES
('CS202602130001', 1, 'PAY_FRAUD', 2, 3, 2, '高风险支付欺诈调查', '确认欺诈并冻结账号', '2026-02-12 14:00:00', '2026-02-12 11:50:00');

INSERT INTO appeal (alert_id, user_id, content, status, reply, reply_admin_id, reply_time, create_time) VALUES
(1, 3, '该笔交易为真实订单，请求复核', 1, '经复核证据不足，维持原判', 2, '2026-02-12 18:00:00', '2026-02-12 16:20:00');

INSERT INTO announcement (title, content, admin_id, status) VALUES
('反欺诈策略升级通知', '平台已上线多维规则联动策略，请商户完善设备与IP信息。', 1, 1),
('申诉资料提交规范', '请在申诉中提供订单号、凭证截图和资金流水以便快速复核。', 2, 1);

INSERT INTO operation_log (module, action, operator_id, operator_role, biz_no, content, create_time) VALUES
('USER', 'LOGIN', 1, 'ADMIN', 'admin', '管理员登录系统', '2026-02-12 09:00:00'),
('RULE', 'ADD', 1, 'ADMIN', 'RULE_AMOUNT_5000', '新增金额阈值规则', '2026-02-12 09:20:00'),
('ALERT', 'HANDLE', 2, 'ANALYST', '1', '处置高风险预警', '2026-02-12 12:12:00');
