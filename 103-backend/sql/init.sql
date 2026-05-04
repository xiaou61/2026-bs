DROP DATABASE IF EXISTS customer_service_103;
CREATE DATABASE customer_service_103 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE customer_service_103;

CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255),
    password VARCHAR(255),
    nickname VARCHAR(255),
    role VARCHAR(255),
    team_name VARCHAR(255),
    phone VARCHAR(255),
    email VARCHAR(255),
    status INT,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE customer_profile (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    customer_name VARCHAR(255),
    phone VARCHAR(255),
    email VARCHAR(255),
    level_name VARCHAR(255),
    tags VARCHAR(255),
    status INT,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE service_channel (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    channel_name VARCHAR(255),
    channel_type VARCHAR(255),
    owner_team VARCHAR(255),
    status INT,
    remark VARCHAR(255),
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE knowledge_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    category_name VARCHAR(255),
    parent_name VARCHAR(255),
    sort_no INT,
    status INT,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE knowledge_article (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    category_id BIGINT,
    title VARCHAR(255),
    keywords VARCHAR(255),
    content TEXT,
    status INT,
    hit_count INT,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE work_order (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_no VARCHAR(255),
    customer_id BIGINT,
    agent_id BIGINT,
    channel_id BIGINT,
    title VARCHAR(255),
    content TEXT,
    priority VARCHAR(255),
    status INT,
    solution TEXT,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE order_message (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT,
    sender_id BIGINT,
    sender_type VARCHAR(255),
    message_content TEXT,
    sensitive_flag INT,
    send_time DATETIME,
    create_time DATETIME
);

CREATE TABLE ticket_assignment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT,
    from_agent_id BIGINT,
    to_agent_id BIGINT,
    assign_reason VARCHAR(255),
    status INT,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE quality_rule (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    rule_name VARCHAR(255),
    rule_type VARCHAR(255),
    keyword VARCHAR(255),
    deduct_score DECIMAL(10,2),
    status INT,
    description TEXT,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE quality_task (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    task_no VARCHAR(255),
    order_id BIGINT,
    qa_user_id BIGINT,
    task_name VARCHAR(255),
    priority VARCHAR(255),
    status INT,
    create_time DATETIME,
    finish_time DATETIME
);

CREATE TABLE quality_result (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    task_id BIGINT,
    order_id BIGINT,
    agent_id BIGINT,
    score DECIMAL(10,2),
    risk_level VARCHAR(255),
    hit_rules VARCHAR(255),
    suggestion TEXT,
    review_status INT,
    review_comment TEXT,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE recommend_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT,
    article_id BIGINT,
    agent_id BIGINT,
    match_keyword VARCHAR(255),
    match_score DECIMAL(10,2),
    adopt_status INT,
    feedback TEXT,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE agent_performance (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    agent_id BIGINT,
    stat_date VARCHAR(255),
    order_count INT,
    resolved_count INT,
    avg_quality_score DECIMAL(10,2),
    satisfaction_score DECIMAL(10,2),
    rank_level VARCHAR(255),
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE operation_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    username VARCHAR(255),
    role VARCHAR(255),
    module_name VARCHAR(255),
    action_type VARCHAR(255),
    description TEXT,
    create_time DATETIME
);

INSERT INTO sys_user(username, password, nickname, role, team_name, phone, email, status, create_time, update_time) VALUES
('admin', '123456', '系统管理员', 'ADMIN', '客服平台部', '13800000301', 'admin@service.com', 1, NOW(), NOW()),
('supervisor', '123456', '客服主管', 'SUPERVISOR', '服务运营组', '13800000302', 'supervisor@service.com', 1, NOW(), NOW()),
('agent', '123456', '一线坐席', 'AGENT', '在线客服组', '13800000303', 'agent@service.com', 1, NOW(), NOW()),
('qa', '123456', '质检专员', 'QA', '质检组', '13800000304', 'qa@service.com', 1, NOW(), NOW());

INSERT INTO customer_profile(customer_name, phone, email, level_name, tags, status, create_time, update_time) VALUES
('赵先生', '13900000301', 'zhao@mail.com', '金卡客户', '退款,物流,高价值', 1, NOW(), NOW()),
('钱女士', '13900000302', 'qian@mail.com', '普通客户', '发票,售后', 1, NOW(), NOW());

INSERT INTO service_channel(channel_name, channel_type, owner_team, status, remark, create_time, update_time) VALUES
('官网在线客服', '网页', '在线客服组', 1, '主要承接售前售后咨询', NOW(), NOW()),
('400电话热线', '电话', '热线客服组', 1, '承接紧急投诉', NOW(), NOW());

INSERT INTO knowledge_category(category_name, parent_name, sort_no, status, create_time, update_time) VALUES
('退款售后', '客服知识库', 1, 1, NOW(), NOW()),
('物流查询', '客服知识库', 2, 1, NOW(), NOW());

INSERT INTO knowledge_article(category_id, title, keywords, content, status, hit_count, create_time, update_time) VALUES
(1, '七天无理由退款处理流程', '退款,退货,售后', '核验订单状态、商品签收时间和退款原因后创建售后单。', 1, 23, NOW(), NOW()),
(2, '物流异常安抚话术', '物流,延迟,投诉', '先表达歉意，再核验物流轨迹，最后承诺反馈时限。', 1, 18, NOW(), NOW());

INSERT INTO work_order(order_no, customer_id, agent_id, channel_id, title, content, priority, status, solution, create_time, update_time) VALUES
('WO103001', 1, 3, 1, '客户申请退款', '客户投诉物流延迟并要求退款', '高', 1, '已说明退款流程并创建售后单', NOW(), NOW()),
('WO103002', 2, 3, 2, '客户咨询发票', '客户需要补开发票并修改抬头', '普通', 0, NULL, NOW(), NOW());

INSERT INTO order_message(order_id, sender_id, sender_type, message_content, sensitive_flag, send_time, create_time) VALUES
(1, 1, 'CUSTOMER', '物流太慢了，我要退款', 1, NOW(), NOW()),
(1, 3, 'AGENT', '非常抱歉给您带来不便，我马上为您核实退款流程', 0, NOW(), NOW());

INSERT INTO ticket_assignment(order_id, from_agent_id, to_agent_id, assign_reason, status, create_time, update_time) VALUES
(1, NULL, 3, '高价值客户投诉优先处理', 1, NOW(), NOW());

INSERT INTO quality_rule(rule_name, rule_type, keyword, deduct_score, status, description, create_time, update_time) VALUES
('投诉关键词识别', '风险关键词', '投诉', 8.00, 1, '识别投诉场景并提示安抚', NOW(), NOW()),
('退款流程完整性', '流程合规', '退款', 6.00, 1, '检查退款说明是否完整', NOW(), NOW());

INSERT INTO quality_task(task_no, order_id, qa_user_id, task_name, priority, status, create_time, finish_time) VALUES
('QT103001', 1, 4, '赵先生退款工单质检', '高', 0, NOW(), NULL);

INSERT INTO quality_result(task_id, order_id, agent_id, score, risk_level, hit_rules, suggestion, review_status, review_comment, create_time, update_time) VALUES
(1, 1, 3, 84.00, '中', '投诉关键词识别,退款流程完整性', '建议补充主动赔付说明和回访动作', 0, NULL, NOW(), NOW());

INSERT INTO recommend_record(order_id, article_id, agent_id, match_keyword, match_score, adopt_status, feedback, create_time, update_time) VALUES
(1, 1, 3, '退款', 93.50, 1, '已采纳退款流程知识', NOW(), NOW()),
(1, 2, 3, '物流', 88.00, 0, NULL, NOW(), NOW());

INSERT INTO agent_performance(agent_id, stat_date, order_count, resolved_count, avg_quality_score, satisfaction_score, rank_level, create_time, update_time) VALUES
(3, '2026-05', 26, 22, 91.50, 96.00, 'A', NOW(), NOW());

INSERT INTO operation_log(user_id, username, role, module_name, action_type, description, create_time) VALUES
(1, 'admin', 'ADMIN', '系统初始化', '初始化', '初始化103号客服质检系统演示数据', NOW());
