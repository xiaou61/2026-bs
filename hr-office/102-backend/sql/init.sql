DROP DATABASE IF EXISTS legal_case_102;
CREATE DATABASE legal_case_102 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE legal_case_102;

CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255),
    password VARCHAR(255),
    nickname VARCHAR(255),
    role VARCHAR(255),
    org_name VARCHAR(255),
    phone VARCHAR(255),
    email VARCHAR(255),
    status INT,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE client_profile (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    real_name VARCHAR(255),
    id_no VARCHAR(255),
    phone VARCHAR(255),
    address VARCHAR(255),
    case_preference VARCHAR(255),
    status INT,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE lawyer_profile (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    real_name VARCHAR(255),
    license_no VARCHAR(255),
    speciality VARCHAR(255),
    experience_years INT,
    service_status INT,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE legal_case (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    case_no VARCHAR(255),
    client_id BIGINT,
    lawyer_id BIGINT,
    title VARCHAR(255),
    case_type VARCHAR(255),
    priority VARCHAR(255),
    status INT,
    summary VARCHAR(1000),
    current_stage VARCHAR(255),
    next_action VARCHAR(500),
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE case_stage (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    case_id BIGINT,
    stage_name VARCHAR(255),
    stage_order INT,
    status INT,
    plan_date DATETIME,
    finish_date DATETIME,
    remark VARCHAR(800),
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE consultation_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    case_id BIGINT,
    client_id BIGINT,
    lawyer_id BIGINT,
    consult_type VARCHAR(255),
    consult_time DATETIME,
    question TEXT,
    answer TEXT,
    risk_level VARCHAR(255),
    follow_action VARCHAR(500),
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE document_template (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    template_name VARCHAR(255),
    template_type VARCHAR(255),
    content_variables TEXT,
    status INT,
    usage_count INT,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE legal_document (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    case_id BIGINT,
    template_id BIGINT,
    document_no VARCHAR(255),
    document_title VARCHAR(255),
    document_type VARCHAR(255),
    content TEXT,
    status INT,
    generated_by BIGINT,
    review_comment VARCHAR(800),
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE document_version (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    document_id BIGINT,
    version_no VARCHAR(255),
    content TEXT,
    change_summary VARCHAR(800),
    operator_id BIGINT,
    create_time DATETIME
);

CREATE TABLE appointment_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    case_id BIGINT,
    client_id BIGINT,
    lawyer_id BIGINT,
    appointment_time DATETIME,
    appointment_type VARCHAR(255),
    location VARCHAR(255),
    status INT,
    remark VARCHAR(800),
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE evidence_material (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    case_id BIGINT,
    material_name VARCHAR(255),
    material_type VARCHAR(255),
    file_url VARCHAR(255),
    submitter_id BIGINT,
    verify_status INT,
    review_comment VARCHAR(800),
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE fee_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    case_id BIGINT,
    client_id BIGINT,
    fee_type VARCHAR(255),
    amount DECIMAL(10,2),
    pay_status INT,
    pay_time DATETIME,
    remark VARCHAR(800),
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
    description VARCHAR(800),
    create_time DATETIME
);

INSERT INTO sys_user(username, password, nickname, role, org_name, phone, email, status, create_time, update_time) VALUES
('admin', '123456', '系统管理员', 'ADMIN', '法律服务中心', '13800000201', 'admin@legal.com', 1, NOW(), NOW()),
('lawyer', '123456', '张律师', 'LAWYER', '民商事团队', '13800000202', 'lawyer@legal.com', 1, NOW(), NOW()),
('assistant', '123456', '法务助理', 'ASSISTANT', '案件运营组', '13800000203', 'assistant@legal.com', 1, NOW(), NOW()),
('client', '123456', '委托人王先生', 'CLIENT', '个人委托人', '13800000204', 'client@mail.com', 1, NOW(), NOW());

INSERT INTO client_profile(user_id, real_name, id_no, phone, address, case_preference, status, create_time, update_time) VALUES
(4, '王先生', '330102199001010011', '13800000204', '杭州市西湖区', '劳动争议', 1, NOW(), NOW()),
(NULL, '李女士', '330102199202020022', '13800000205', '上海市浦东新区', '合同纠纷', 1, NOW(), NOW());

INSERT INTO lawyer_profile(user_id, real_name, license_no, speciality, experience_years, service_status, create_time, update_time) VALUES
(2, '张律师', 'A202633010001', '劳动争议,合同纠纷,律师函', 8, 1, NOW(), NOW()),
(NULL, '陈律师', 'A202633010002', '婚姻家事,侵权责任', 6, 1, NOW(), NOW());

INSERT INTO legal_case(case_no, client_id, lawyer_id, title, case_type, priority, status, summary, current_stage, next_action, create_time, update_time) VALUES
('LC102001', 1, 1, '王先生劳动合同解除咨询案', '劳动争议', '高', 1, '委托人认为公司违法解除劳动合同，需要评估赔偿和仲裁方案。', '证据整理', '补充工资流水和解除通知', NOW(), NOW()),
('LC102002', 2, 1, '李女士服务合同违约咨询案', '合同纠纷', '普通', 0, '服务合同履行过程中出现违约争议，需要起草律师函。', '咨询受理', '安排线上咨询并生成律师函初稿', NOW(), NOW());

INSERT INTO case_stage(case_id, stage_name, stage_order, status, plan_date, finish_date, remark, create_time, update_time) VALUES
(1, '咨询受理', 1, 2, NOW(), NOW(), '已完成首次咨询', NOW(), NOW()),
(1, '证据整理', 2, 1, DATE_ADD(NOW(), INTERVAL 1 DAY), NULL, '等待委托人补充材料', NOW(), NOW()),
(2, '文书准备', 1, 0, DATE_ADD(NOW(), INTERVAL 2 DAY), NULL, '需生成律师函草稿', NOW(), NOW());

INSERT INTO consultation_record(case_id, client_id, lawyer_id, consult_type, consult_time, question, answer, risk_level, follow_action, create_time, update_time) VALUES
(1, 1, 1, '电话咨询', NOW(), '公司未提前通知解除劳动合同是否违法？', '需结合劳动合同、解除通知和工资流水判断，可初步主张经济赔偿。', '中', '补充解除通知和工资流水', NOW(), NOW()),
(2, 2, 1, '线上咨询', NOW(), '对方迟迟不履行服务合同如何催告？', '建议先发送律师函固定催告证据，再评估诉讼成本。', '低', '生成律师函初稿', NOW(), NOW());

INSERT INTO document_template(template_name, template_type, content_variables, status, usage_count, create_time, update_time) VALUES
('劳动仲裁申请书模板', '仲裁申请', '委托人姓名、用人单位、诉求、事实理由、证据清单', 1, 3, NOW(), NOW()),
('合同违约律师函模板', '律师函', '委托人姓名、相对方、合同名称、违约事实、履行期限', 1, 5, NOW(), NOW());

INSERT INTO legal_document(case_id, template_id, document_no, document_title, document_type, content, status, generated_by, review_comment, create_time, update_time) VALUES
(1, 1, 'DOC102001', '劳动仲裁申请书初稿', '仲裁申请', '申请人王先生因劳动合同解除争议申请劳动仲裁，请求依法支持赔偿诉求。', 1, 2, NULL, NOW(), NOW()),
(2, 2, 'DOC102002', '合同违约律师函初稿', '律师函', '根据委托人陈述，现就服务合同履行争议向相对方发出律师函。', 0, 3, NULL, NOW(), NOW());

INSERT INTO document_version(document_id, version_no, content, change_summary, operator_id, create_time) VALUES
(1, 'V1.0', '劳动仲裁申请书初稿内容', '系统生成初稿', 2, NOW()),
(1, 'V1.1', '补充赔偿诉求和证据目录', '律师补充诉求', 2, NOW());

INSERT INTO appointment_record(case_id, client_id, lawyer_id, appointment_time, appointment_type, location, status, remark, create_time, update_time) VALUES
(1, 1, 1, DATE_ADD(NOW(), INTERVAL 1 DAY), '线下会谈', '法律服务中心3号会议室', 1, '核对证据原件', NOW(), NOW()),
(2, 2, 1, DATE_ADD(NOW(), INTERVAL 2 DAY), '视频咨询', '腾讯会议 102-202-302', 0, '确认律师函收件信息', NOW(), NOW());

INSERT INTO evidence_material(case_id, material_name, material_type, file_url, submitter_id, verify_status, review_comment, create_time, update_time) VALUES
(1, '解除劳动合同通知书', '书面证据', 'https://example.com/legal/evidence/notice.pdf', 4, 1, '材料清晰有效', NOW(), NOW()),
(1, '工资流水', '财务材料', 'https://example.com/legal/evidence/salary.pdf', 4, 0, NULL, NOW(), NOW());

INSERT INTO fee_record(case_id, client_id, fee_type, amount, pay_status, pay_time, remark, create_time, update_time) VALUES
(1, 1, '咨询费', 300.00, 1, NOW(), '首次电话咨询', NOW(), NOW()),
(2, 2, '文书费', 800.00, 0, NULL, '律师函初稿费用', NOW(), NOW());

INSERT INTO operation_log(user_id, username, role, module_name, action_type, description, create_time) VALUES
(1, 'admin', 'ADMIN', '系统初始化', '初始化', '初始化102号法律咨询系统演示数据', NOW());
