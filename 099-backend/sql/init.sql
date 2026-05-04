DROP DATABASE IF EXISTS aigc_copyright_099;
CREATE DATABASE aigc_copyright_099 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE aigc_copyright_099;

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

CREATE TABLE image_asset (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(160) NOT NULL,
    image_url VARCHAR(500) NOT NULL,
    prompt_text TEXT,
    model_name VARCHAR(100),
    category VARCHAR(60),
    description VARCHAR(500),
    creator_id BIGINT,
    status INT DEFAULT 0,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE audit_rule (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    rule_name VARCHAR(120) NOT NULL,
    rule_type VARCHAR(60),
    risk_level VARCHAR(20),
    keywords VARCHAR(255),
    description VARCHAR(255),
    status INT DEFAULT 1,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE audit_task (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    task_no VARCHAR(50) NOT NULL UNIQUE,
    asset_id BIGINT NOT NULL,
    task_name VARCHAR(160) NOT NULL,
    priority VARCHAR(20),
    status INT DEFAULT 0,
    auditor_id BIGINT,
    create_time DATETIME,
    finish_time DATETIME
);

CREATE TABLE audit_result (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    task_id BIGINT NOT NULL,
    asset_id BIGINT NOT NULL,
    matched_rules VARCHAR(500),
    risk_level VARCHAR(20),
    score DECIMAL(5,2) DEFAULT 0.00,
    conclusion VARCHAR(100),
    suggestion VARCHAR(500),
    review_status INT DEFAULT 0,
    review_comment VARCHAR(500),
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE risk_tag (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    tag_name VARCHAR(80) NOT NULL,
    tag_type VARCHAR(50),
    color VARCHAR(30),
    description VARCHAR(255),
    status INT DEFAULT 1,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE copyright_register (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    asset_id BIGINT NOT NULL,
    author_name VARCHAR(80) NOT NULL,
    right_type VARCHAR(80),
    declaration VARCHAR(500),
    register_status INT DEFAULT 0,
    review_comment VARCHAR(500),
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE evidence_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    register_id BIGINT NOT NULL,
    evidence_no VARCHAR(80) NOT NULL,
    hash_value VARCHAR(128),
    platform_name VARCHAR(100),
    evidence_status INT DEFAULT 0,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE license_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    register_id BIGINT NOT NULL,
    licensee VARCHAR(120) NOT NULL,
    purpose VARCHAR(255),
    start_date DATE,
    end_date DATE,
    status INT DEFAULT 1,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE infringement_clue (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    asset_id BIGINT NOT NULL,
    platform_name VARCHAR(100),
    clue_url VARCHAR(500),
    description VARCHAR(500),
    evidence_url VARCHAR(500),
    status INT DEFAULT 0,
    handler_id BIGINT,
    handle_comment VARCHAR(500),
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE appeal_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    target_type VARCHAR(50) NOT NULL,
    target_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    reason VARCHAR(500),
    status INT DEFAULT 0,
    handle_comment VARCHAR(500),
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
('admin', '123456', '系统管理员', 'ADMIN', '13800020001', 'admin@aigcimg.com', 1, NOW(), NOW()),
('auditor', '123456', '内容审核员', 'AUDITOR', '13800020002', 'auditor@aigcimg.com', 1, NOW(), NOW()),
('creator', '123456', 'AIGC创作者', 'CREATOR', '13800020003', 'creator@aigcimg.com', 1, NOW(), NOW());

INSERT INTO image_asset(title, image_url, prompt_text, model_name, category, description, creator_id, status, create_time, update_time) VALUES
('未来城市海报', 'https://images.unsplash.com/photo-1519608487953-e999c86e7455?auto=format&fit=crop&w=1200&q=80', '未来城市、霓虹灯、雨夜、电影感', 'Stable Diffusion XL', '商业海报', '用于科技活动主视觉的 AIGC 图片', 3, 1, NOW(), NOW()),
('国风山水插画', 'https://images.unsplash.com/photo-1500530855697-b586d89ba3ee?auto=format&fit=crop&w=1200&q=80', '国风山水、云雾、飞鸟、水墨质感', 'Midjourney', '插画素材', '用于文创周边的国风插画', 3, 0, NOW(), NOW()),
('品牌联名概念图', 'https://images.unsplash.com/photo-1500534314209-a25ddb2bd429?auto=format&fit=crop&w=1200&q=80', '奢侈品牌联名、产品海报、高清摄影', 'Firefly', '营销素材', '可能涉及品牌元素，需要版权审核', 3, 3, NOW(), NOW());

INSERT INTO audit_rule(rule_name, rule_type, risk_level, keywords, description, status, create_time, update_time) VALUES
('品牌商标风险', '版权风险', '高', '品牌,商标,联名,logo', '识别可能涉及品牌商标或联名误导的图片描述', 1, NOW(), NOW()),
('人物肖像风险', '肖像风险', '中', '明星,真人,肖像,名人', '识别可能涉及真人肖像权益的生成内容', 1, NOW(), NOW()),
('暴力违规风险', '内容安全', '高', '暴力,血腥,武器', '识别内容安全高风险元素', 1, NOW(), NOW());

INSERT INTO audit_task(task_no, asset_id, task_name, priority, status, auditor_id, create_time, finish_time) VALUES
('AT20260001', 1, '未来城市海报初审', '中', 2, 2, NOW(), NOW()),
('AT20260002', 3, '品牌联名概念图复核', '高', 1, 2, NOW(), NULL);

INSERT INTO audit_result(task_id, asset_id, matched_rules, risk_level, score, conclusion, suggestion, review_status, review_comment, create_time, update_time) VALUES
(1, 1, '未命中高危规则', '低', 96.00, '审核通过', '可进入版权登记流程', 1, '画面内容正常', NOW(), NOW()),
(2, 3, '品牌商标风险', '高', 62.00, '建议驳回', '建议修改品牌相关提示词并补充授权材料', 0, NULL, NOW(), NOW());

INSERT INTO risk_tag(tag_name, tag_type, color, description, status, create_time, update_time) VALUES
('版权高风险', '版权风险', '#ef4444', '可能涉及商标、图库或风格侵权', 1, NOW(), NOW()),
('需人工复核', '流程标签', '#f59e0b', '模型初审无法直接通过，需要审核员复核', 1, NOW(), NOW()),
('可登记', '版权状态', '#22c55e', '审核通过，可进入版权登记', 1, NOW(), NOW());

INSERT INTO copyright_register(asset_id, author_name, right_type, declaration, register_status, review_comment, create_time, update_time) VALUES
(1, 'AIGC创作者', '信息网络传播权', '作品由本人通过 AIGC 工具生成，并已完成内部审核。', 1, '材料完整，准予登记', NOW(), NOW()),
(2, 'AIGC创作者', '复制权', '国风插画用于文创素材，申请版权登记。', 0, NULL, NOW(), NOW());

INSERT INTO evidence_record(register_id, evidence_no, hash_value, platform_name, evidence_status, create_time, update_time) VALUES
(1, 'EV20260001', 'a5d72c7e98f15d32a7d6a89d42139d7ce6c931df2c43a2ce11e611de88f89901', 'AIGC版权存证链', 1, NOW(), NOW());

INSERT INTO license_record(register_id, licensee, purpose, start_date, end_date, status, create_time, update_time) VALUES
(1, '星河科技有限公司', '科技活动宣传海报使用', '2026-05-01', '2027-05-01', 1, NOW(), NOW());

INSERT INTO infringement_clue(asset_id, platform_name, clue_url, description, evidence_url, status, handler_id, handle_comment, create_time, update_time) VALUES
(1, '社交媒体平台', 'https://example.com/post/1001', '发现疑似未经授权转载未来城市海报', 'https://example.com/evidence/1001', 0, NULL, NULL, NOW(), NOW());

INSERT INTO appeal_record(target_type, target_id, user_id, reason, status, handle_comment, create_time, update_time) VALUES
('AUDIT', 2, 3, '该图仅为概念草图，未实际使用任何品牌标识，请求重新审核。', 0, NULL, NOW(), NOW());

INSERT INTO operation_log(user_id, username, role, module_name, action_type, description, create_time) VALUES
(1, 'admin', 'ADMIN', '系统', '初始化', '初始化 AIGC 图片审核与版权存证演示数据', NOW());
