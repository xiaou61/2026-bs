DROP TABLE IF EXISTS operation_log;
DROP TABLE IF EXISTS announcement;
DROP TABLE IF EXISTS appeal;
DROP TABLE IF EXISTS fraud_case;
DROP TABLE IF EXISTS fraud_alert;
DROP TABLE IF EXISTS risk_event;
DROP TABLE IF EXISTS risk_rule;
DROP TABLE IF EXISTS blacklist;
DROP TABLE IF EXISTS `user`;

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
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
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
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX uk_blacklist_type_value ON blacklist(type, value);

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
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
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
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_event_user ON risk_event(user_id);
CREATE INDEX idx_event_level ON risk_event(risk_level);
CREATE INDEX idx_event_status ON risk_event(status);

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
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_alert_event ON fraud_alert(event_id);
CREATE INDEX idx_alert_user ON fraud_alert(user_id);
CREATE INDEX idx_alert_status ON fraud_alert(status);

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
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_case_alert ON fraud_case(alert_id);
CREATE INDEX idx_case_owner ON fraud_case(owner_id);

CREATE TABLE appeal (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    alert_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    content VARCHAR(1000) NOT NULL,
    status INT DEFAULT 0,
    reply VARCHAR(1000),
    reply_admin_id BIGINT,
    reply_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_appeal_alert ON appeal(alert_id);
CREATE INDEX idx_appeal_user ON appeal(user_id);

CREATE TABLE announcement (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    content TEXT,
    admin_id BIGINT,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE operation_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    module VARCHAR(40) NOT NULL,
    action VARCHAR(40) NOT NULL,
    operator_id BIGINT,
    operator_role VARCHAR(20),
    biz_no VARCHAR(120),
    content VARCHAR(500),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_log_module ON operation_log(module);
CREATE INDEX idx_log_operator ON operation_log(operator_id);
CREATE INDEX idx_log_time ON operation_log(create_time);
