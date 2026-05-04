DROP DATABASE IF EXISTS knowledge_qa_098;
CREATE DATABASE knowledge_qa_098 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE knowledge_qa_098;

CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50) NOT NULL,
    role VARCHAR(30) NOT NULL,
    department VARCHAR(80),
    phone VARCHAR(20),
    email VARCHAR(100),
    status INT DEFAULT 1,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE knowledge_space (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(120) NOT NULL,
    owner_name VARCHAR(50),
    visibility VARCHAR(30),
    description VARCHAR(255),
    status INT DEFAULT 1,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE document_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    space_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    sort INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE knowledge_document (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    space_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    title VARCHAR(180) NOT NULL,
    summary VARCHAR(500),
    source_type VARCHAR(50),
    tags VARCHAR(255),
    content TEXT,
    status INT DEFAULT 0,
    creator_id BIGINT,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE document_chunk (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    document_id BIGINT NOT NULL,
    chunk_title VARCHAR(180) NOT NULL,
    chunk_content TEXT NOT NULL,
    keywords VARCHAR(255),
    vector_status INT DEFAULT 0,
    sort INT DEFAULT 0,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE permission_group (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(120) NOT NULL,
    owner_name VARCHAR(50),
    description VARCHAR(255),
    status INT DEFAULT 1,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE group_member (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    group_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    member_role VARCHAR(50),
    status INT DEFAULT 1,
    create_time DATETIME
);

CREATE TABLE document_permission (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    space_id BIGINT,
    document_id BIGINT,
    group_id BIGINT NOT NULL,
    permission_level VARCHAR(30),
    status INT DEFAULT 1,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE qa_session (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    session_no VARCHAR(50) NOT NULL UNIQUE,
    title VARCHAR(160) NOT NULL,
    space_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    status INT DEFAULT 0,
    create_time DATETIME,
    close_time DATETIME
);

CREATE TABLE qa_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    session_id BIGINT NOT NULL,
    question VARCHAR(500) NOT NULL,
    answer TEXT,
    source_chunk_ids VARCHAR(255),
    confidence DECIMAL(5,2) DEFAULT 0.00,
    resolved INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE qa_feedback (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    record_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    content VARCHAR(500) NOT NULL,
    score INT DEFAULT 5,
    status INT DEFAULT 0,
    reply_content VARCHAR(500),
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE access_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    username VARCHAR(50),
    role VARCHAR(30),
    module_name VARCHAR(50),
    action_type VARCHAR(50),
    description VARCHAR(255),
    create_time DATETIME
);

INSERT INTO sys_user(username, password, nickname, role, department, phone, email, status, create_time, update_time) VALUES
('admin', '123456', '系统管理员', 'ADMIN', '信息中心', '13800010001', 'admin@knowledgeqa.com', 1, NOW(), NOW()),
('editor', '123456', '知识编辑员', 'EDITOR', '产品运营部', '13800010002', 'editor@knowledgeqa.com', 1, NOW(), NOW()),
('staff', '123456', '企业员工', 'STAFF', '客户成功部', '13800010003', 'staff@knowledgeqa.com', 1, NOW(), NOW());

INSERT INTO knowledge_space(name, owner_name, visibility, description, status, create_time, update_time) VALUES
('产品知识库', '知识编辑员', 'GROUP', '沉淀产品功能、版本发布和常见问题', 1, NOW(), NOW()),
('内部制度库', '系统管理员', 'PRIVATE', '沉淀人事、财务、行政等内部制度', 1, NOW(), NOW()),
('客户成功案例库', '企业员工', 'GROUP', '沉淀客户交付案例和服务方法论', 1, NOW(), NOW());

INSERT INTO document_category(space_id, name, code, description, sort, status, create_time, update_time) VALUES
(1, '产品手册', 'PRODUCT_MANUAL', '产品功能和使用说明', 100, 1, NOW(), NOW()),
(1, '常见问题', 'FAQ', '售前售后高频问题', 90, 1, NOW(), NOW()),
(2, '人事制度', 'HR_POLICY', '入职、转正、考勤和请假制度', 80, 1, NOW(), NOW());

INSERT INTO knowledge_document(space_id, category_id, title, summary, source_type, tags, content, status, creator_id, create_time, update_time) VALUES
(1, 1, '智能问答平台产品手册', '介绍知识库、权限、问答和来源追踪能力', '手工录入', '产品,知识库,问答', '平台支持文档入库、文本分段、权限授权、问答记录和反馈处理。', 1, 2, NOW(), NOW()),
(1, 2, '智能问答常见问题', '整理用户在使用智能问答时的高频问题', '导入文档', 'FAQ,客服,问答', '用户可在已授权空间中创建会话，提交问题后系统返回参考答案和命中来源。', 1, 2, NOW(), NOW()),
(2, 3, '员工请假管理制度', '说明年假、病假、事假申请和审批要求', '手工录入', '制度,请假,审批', '员工请假需提前在系统提交申请，紧急情况可先口头报备后补流程。', 1, 2, NOW(), NOW());

INSERT INTO document_chunk(document_id, chunk_title, chunk_content, keywords, vector_status, sort, create_time, update_time) VALUES
(1, '知识空间能力', '知识空间用于隔离不同业务线文档，可配置负责人、可见级别和启用状态。', '知识空间,隔离,权限', 1, 1, NOW(), NOW()),
(1, '问答来源追踪', '每条问答记录会保存命中的分段编号、置信度、答案内容和解决状态。', '问答,来源,置信度', 1, 2, NOW(), NOW()),
(2, '提问流程', '员工进入会话后提交问题，系统在当前空间已索引分段中检索参考内容并生成答案。', '提问,会话,检索', 1, 1, NOW(), NOW()),
(3, '请假申请', '请假申请需填写请假类型、开始结束时间、请假原因，并提交直属负责人审批。', '请假,审批,制度', 1, 1, NOW(), NOW());

INSERT INTO permission_group(name, owner_name, description, status, create_time, update_time) VALUES
('产品运营组', '知识编辑员', '可访问产品知识库和常见问题', 1, NOW(), NOW()),
('客户成功组', '企业员工', '可访问客户成功案例和产品常见问题', 1, NOW(), NOW());

INSERT INTO group_member(group_id, user_id, member_role, status, create_time) VALUES
(1, 2, '维护者', 1, NOW()),
(1, 3, '访问者', 1, NOW()),
(2, 3, '维护者', 1, NOW());

INSERT INTO document_permission(space_id, document_id, group_id, permission_level, status, create_time, update_time) VALUES
(1, NULL, 1, 'MANAGE', 1, NOW(), NOW()),
(1, 2, 2, 'QA', 1, NOW(), NOW()),
(2, 3, 1, 'READ', 1, NOW(), NOW());

INSERT INTO qa_session(session_no, title, space_id, user_id, status, create_time, close_time) VALUES
('QA20260001', '产品问答演示会话', 1, 3, 0, NOW(), NULL);

INSERT INTO qa_record(session_id, question, answer, source_chunk_ids, confidence, resolved, status, create_time, update_time) VALUES
(1, '系统如何追踪问答来源？', '每条问答记录会保存命中的分段编号、置信度、答案内容和解决状态。', '2', 91.00, 1, 1, NOW(), NOW());

INSERT INTO qa_feedback(record_id, user_id, content, score, status, reply_content, create_time, update_time) VALUES
(1, 3, '答案能说明来源编号，但希望展示原文标题。', 4, 1, '已加入来源标题展示优化计划', NOW(), NOW());

INSERT INTO access_log(user_id, username, role, module_name, action_type, description, create_time) VALUES
(1, 'admin', 'ADMIN', '系统', '初始化', '初始化企业知识库问答演示数据', NOW());
