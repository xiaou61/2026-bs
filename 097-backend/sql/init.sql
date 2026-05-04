DROP DATABASE IF EXISTS prompt_asset_097;
CREATE DATABASE prompt_asset_097 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE prompt_asset_097;

CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50) NOT NULL,
    role VARCHAR(30) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100),
    status INT DEFAULT 1,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE team_space (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    owner_name VARCHAR(50),
    description VARCHAR(255),
    member_count INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE prompt_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    sort INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE prompt_asset (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(150) NOT NULL,
    category_id BIGINT NOT NULL,
    team_id BIGINT NOT NULL,
    scene VARCHAR(100),
    tags VARCHAR(255),
    description VARCHAR(500),
    status INT DEFAULT 1,
    creator_id BIGINT,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE prompt_version (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    asset_id BIGINT NOT NULL,
    version_no VARCHAR(30) NOT NULL,
    content TEXT NOT NULL,
    variables VARCHAR(500),
    model_hint VARCHAR(100),
    change_log VARCHAR(500),
    is_baseline INT DEFAULT 0,
    status INT DEFAULT 0,
    creator_id BIGINT,
    create_time DATETIME
);

CREATE TABLE test_case (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    asset_id BIGINT NOT NULL,
    title VARCHAR(150) NOT NULL,
    input_text TEXT NOT NULL,
    expected_output TEXT,
    score_points VARCHAR(500),
    difficulty VARCHAR(20),
    status INT DEFAULT 1,
    creator_id BIGINT,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE model_config (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    provider VARCHAR(100),
    model_name VARCHAR(100),
    temperature DECIMAL(4,2) DEFAULT 0.70,
    max_tokens INT DEFAULT 2048,
    is_default INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE score_rule (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    dimension VARCHAR(100),
    weight DECIMAL(5,2) DEFAULT 1.00,
    description VARCHAR(255),
    status INT DEFAULT 1,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE evaluation_task (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    task_no VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(150) NOT NULL,
    asset_id BIGINT NOT NULL,
    version_id BIGINT NOT NULL,
    model_id BIGINT NOT NULL,
    status INT DEFAULT 0,
    average_score DECIMAL(5,2) DEFAULT 0.00,
    pass_rate DECIMAL(5,2) DEFAULT 0.00,
    creator_id BIGINT,
    create_time DATETIME,
    finish_time DATETIME
);

CREATE TABLE evaluation_result (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    task_id BIGINT NOT NULL,
    case_id BIGINT NOT NULL,
    input_text TEXT,
    expected_output TEXT,
    actual_output TEXT,
    score DECIMAL(5,2) DEFAULT 0.00,
    passed INT DEFAULT 0,
    review_status INT DEFAULT 0,
    review_comment VARCHAR(500),
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE prompt_feedback (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    asset_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    content VARCHAR(500) NOT NULL,
    priority VARCHAR(20),
    status INT DEFAULT 0,
    reply_content VARCHAR(500),
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE operation_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    username VARCHAR(50),
    role VARCHAR(30),
    module_name VARCHAR(50),
    action_type VARCHAR(50),
    description VARCHAR(255),
    create_time DATETIME
);

INSERT INTO sys_user(username, password, nickname, role, phone, email, status, create_time, update_time) VALUES
('admin', '123456', '系统管理员', 'ADMIN', '13800000001', 'admin@promptops.com', 1, NOW(), NOW()),
('engineer', '123456', '提示词工程师', 'ENGINEER', '13800000002', 'engineer@promptops.com', 1, NOW(), NOW()),
('reviewer', '123456', '效果评审员', 'REVIEWER', '13800000003', 'reviewer@promptops.com', 1, NOW(), NOW());

INSERT INTO team_space(name, owner_name, description, member_count, status, create_time, update_time) VALUES
('AI 应用中台组', '提示词工程师', '负责企业内部大模型应用提示词资产建设', 8, 1, NOW(), NOW()),
('智能客服优化组', '效果评审员', '负责客服场景提示词效果复核和质检', 5, 1, NOW(), NOW());

INSERT INTO prompt_category(name, code, description, sort, status, create_time, update_time) VALUES
('客服问答', 'CUSTOMER_SERVICE', '适用于售前售后和客服工单回复', 100, 1, NOW(), NOW()),
('内容生成', 'CONTENT_GENERATION', '适用于营销文案、通知公告和知识摘要', 90, 1, NOW(), NOW()),
('数据分析', 'DATA_ANALYSIS', '适用于结构化数据解释和运营分析', 80, 1, NOW(), NOW());

INSERT INTO prompt_asset(title, category_id, team_id, scene, tags, description, status, creator_id, create_time, update_time) VALUES
('售后工单回复助手', 1, 1, '客服工单', '客服,售后,质检', '根据用户问题、订单状态和知识库生成规范回复', 1, 2, NOW(), NOW()),
('营销活动文案生成器', 2, 1, '营销文案', '活动,文案,AIGC', '根据商品卖点和活动规则生成营销文案', 1, 2, NOW(), NOW()),
('运营日报解读助手', 3, 2, '数据分析', '日报,指标,分析', '根据核心指标变化生成运营解读和建议', 1, 2, NOW(), NOW());

INSERT INTO prompt_version(asset_id, version_no, content, variables, model_hint, change_log, is_baseline, status, creator_id, create_time) VALUES
(1, 'v1.0', '你是一名专业客服，请基于用户问题、订单状态和知识库给出礼貌、准确、可执行的回复。', 'user_question,order_status,knowledge', '通用对话模型', '初始版本', 1, 1, 2, NOW()),
(1, 'v1.1', '你是一名专业客服，请先判断问题类型，再基于订单状态和知识库给出分步骤回复，最后补充安抚语。', 'user_question,order_status,knowledge', '通用对话模型', '增加问题分类和安抚语', 0, 1, 2, NOW()),
(2, 'v1.0', '请根据商品卖点、目标人群和促销力度生成三条短文案。', 'product,customer,promotion', '内容生成模型', '初始版本', 1, 1, 2, NOW());

INSERT INTO test_case(asset_id, title, input_text, expected_output, score_points, difficulty, status, creator_id, create_time, update_time) VALUES
(1, '退款咨询回复', '用户询问订单已发货是否可以退款', '说明退款条件、操作路径和注意事项', '准确性、礼貌度、步骤完整性', '中', 1, 2, NOW(), NOW()),
(1, '物流延迟安抚', '用户反馈物流三天未更新', '解释可能原因并给出查询和补偿路径', '安抚语、解决方案、风险提示', '低', 1, 2, NOW(), NOW()),
(2, '新品促销文案', '新款降噪耳机，年轻白领，满减活动', '生成有卖点和行动号召的短文案', '卖点突出、目标清晰、表达自然', '中', 1, 2, NOW(), NOW());

INSERT INTO model_config(name, provider, model_name, temperature, max_tokens, is_default, status, create_time, update_time) VALUES
('通用对话模型', 'OpenAI Compatible', 'gpt-general', 0.70, 2048, 1, 1, NOW(), NOW()),
('内容生成模型', 'Local LLM', 'content-writer', 0.85, 3072, 0, 1, NOW(), NOW());

INSERT INTO score_rule(name, dimension, weight, description, status, create_time, update_time) VALUES
('事实准确性', 'accuracy', 0.40, '回答是否符合输入信息和业务事实', 1, NOW(), NOW()),
('表达清晰度', 'clarity', 0.30, '回答是否结构清楚、语言自然', 1, NOW(), NOW()),
('执行可用性', 'actionability', 0.30, '回答是否提供可执行步骤', 1, NOW(), NOW());

INSERT INTO evaluation_task(task_no, name, asset_id, version_id, model_id, status, average_score, pass_rate, creator_id, create_time, finish_time) VALUES
('EV20260001', '售后工单 v1.1 基准评测', 1, 2, 1, 2, 88.50, 100.00, 2, NOW(), NOW());

INSERT INTO evaluation_result(task_id, case_id, input_text, expected_output, actual_output, score, passed, review_status, review_comment, create_time, update_time) VALUES
(1, 1, '用户询问订单已发货是否可以退款', '说明退款条件、操作路径和注意事项', '您好，已发货订单可以在收到货后申请退货退款，您可以在订单详情中提交申请。', 89.00, 1, 1, '回复完整，可补充时效说明', NOW(), NOW()),
(1, 2, '用户反馈物流三天未更新', '解释可能原因并给出查询和补偿路径', '您好，物流未更新可能是转运节点延迟，我们会帮您催促并持续跟进。', 88.00, 1, 1, '安抚语充分', NOW(), NOW());

INSERT INTO prompt_feedback(asset_id, user_id, content, priority, status, reply_content, create_time, update_time) VALUES
(1, 3, '建议增加对售后政策版本的引用，方便客服复核。', '高', 1, '已加入下一版本优化计划', NOW(), NOW());

INSERT INTO operation_log(user_id, username, role, module_name, action_type, description, create_time) VALUES
(1, 'admin', 'ADMIN', '系统', '初始化', '初始化提示词资产管理演示数据', NOW());
