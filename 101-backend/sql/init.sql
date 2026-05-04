DROP DATABASE IF EXISTS recruit_match_101;
CREATE DATABASE recruit_match_101 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE recruit_match_101;

CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(80) NOT NULL,
    role VARCHAR(30) NOT NULL,
    department VARCHAR(100),
    phone VARCHAR(30),
    email VARCHAR(120),
    status TINYINT DEFAULT 1,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE candidate_profile (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    real_name VARCHAR(80) NOT NULL,
    gender VARCHAR(20),
    education VARCHAR(80),
    work_years INT DEFAULT 0,
    target_job VARCHAR(120),
    skill_tags VARCHAR(500),
    expected_salary DECIMAL(10,2),
    status TINYINT DEFAULT 1,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE resume_file (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    candidate_id BIGINT NOT NULL,
    file_name VARCHAR(160) NOT NULL,
    file_url VARCHAR(255),
    resume_text TEXT,
    education VARCHAR(80),
    skills VARCHAR(500),
    work_years INT DEFAULT 0,
    parse_status TINYINT DEFAULT 0,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE certificate_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    candidate_id BIGINT NOT NULL,
    cert_name VARCHAR(160) NOT NULL,
    cert_type VARCHAR(60),
    issue_org VARCHAR(160),
    cert_url VARCHAR(255),
    verify_status TINYINT DEFAULT 0,
    review_comment VARCHAR(500),
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE job_position (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    job_name VARCHAR(160) NOT NULL,
    department VARCHAR(120),
    job_type VARCHAR(60),
    location VARCHAR(120),
    salary_range VARCHAR(80),
    description VARCHAR(1000),
    status TINYINT DEFAULT 0,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE job_requirement (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    job_id BIGINT NOT NULL,
    requirement_type VARCHAR(60),
    keyword VARCHAR(120) NOT NULL,
    weight DECIMAL(6,2) DEFAULT 1.00,
    description VARCHAR(500),
    status TINYINT DEFAULT 1,
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE parsing_task (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    task_no VARCHAR(80) NOT NULL,
    resume_id BIGINT NOT NULL,
    task_name VARCHAR(160) NOT NULL,
    priority VARCHAR(20),
    status TINYINT DEFAULT 0,
    handler_id BIGINT,
    create_time DATETIME,
    finish_time DATETIME
);

CREATE TABLE parsing_result (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    task_id BIGINT NOT NULL,
    resume_id BIGINT NOT NULL,
    candidate_id BIGINT NOT NULL,
    extracted_education VARCHAR(120),
    extracted_skills VARCHAR(500),
    extracted_experience VARCHAR(500),
    score DECIMAL(6,2),
    conclusion VARCHAR(800),
    review_status TINYINT DEFAULT 0,
    review_comment VARCHAR(800),
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE match_task (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    task_no VARCHAR(80) NOT NULL,
    candidate_id BIGINT NOT NULL,
    job_id BIGINT NOT NULL,
    task_name VARCHAR(160) NOT NULL,
    priority VARCHAR(20),
    status TINYINT DEFAULT 0,
    handler_id BIGINT,
    create_time DATETIME,
    finish_time DATETIME
);

CREATE TABLE match_result (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    task_id BIGINT NOT NULL,
    candidate_id BIGINT NOT NULL,
    job_id BIGINT NOT NULL,
    matched_skills VARCHAR(500),
    missing_skills VARCHAR(500),
    match_score DECIMAL(6,2),
    recommend_level VARCHAR(40),
    conclusion VARCHAR(800),
    review_status TINYINT DEFAULT 0,
    review_comment VARCHAR(800),
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE interview_plan (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    candidate_id BIGINT NOT NULL,
    job_id BIGINT NOT NULL,
    interviewer_id BIGINT,
    interview_time DATETIME,
    interview_type VARCHAR(50),
    meeting_address VARCHAR(255),
    status TINYINT DEFAULT 0,
    remark VARCHAR(500),
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE interview_feedback (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    plan_id BIGINT NOT NULL,
    interviewer_id BIGINT,
    score DECIMAL(6,2),
    result_status TINYINT DEFAULT 0,
    strengths VARCHAR(800),
    weaknesses VARCHAR(800),
    suggestion VARCHAR(800),
    create_time DATETIME,
    update_time DATETIME
);

CREATE TABLE operation_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    username VARCHAR(80),
    role VARCHAR(30),
    module_name VARCHAR(80),
    action_type VARCHAR(50),
    description VARCHAR(500),
    create_time DATETIME
);

INSERT INTO sys_user(username, password, nickname, role, department, phone, email, status, create_time, update_time) VALUES
('admin', '123456', '系统管理员', 'ADMIN', '招聘平台部', '13800000101', 'admin@recruit.com', 1, NOW(), NOW()),
('hr', '123456', '招聘HR', 'HR', '人力资源部', '13800000102', 'hr@recruit.com', 1, NOW(), NOW()),
('candidate', '123456', '候选人', 'CANDIDATE', '候选人', '13800000103', 'candidate@mail.com', 1, NOW(), NOW()),
('interviewer', '123456', '技术面试官', 'INTERVIEWER', '研发中心', '13800000104', 'interviewer@recruit.com', 1, NOW(), NOW());

INSERT INTO candidate_profile(user_id, real_name, gender, education, work_years, target_job, skill_tags, expected_salary, status, create_time, update_time) VALUES
(3, '李明', '男', '本科', 3, 'Java后端工程师', 'Java,SpringBoot,MySQL,Redis,Vue', 18000.00, 1, NOW(), NOW()),
(NULL, '周晴', '女', '硕士', 2, '算法工程师', 'Python,机器学习,简历解析,NLP', 22000.00, 1, NOW(), NOW());

INSERT INTO resume_file(candidate_id, file_name, file_url, resume_text, education, skills, work_years, parse_status, create_time, update_time) VALUES
(1, '李明Java后端简历.pdf', 'https://example.com/resume/liming.pdf', '三年Java后端开发经验，熟悉SpringBoot、MySQL、Redis和Vue，有招聘系统和CRM项目经验。', '本科', 'Java,SpringBoot,MySQL,Redis,Vue', 3, 0, NOW(), NOW()),
(2, '周晴算法简历.pdf', 'https://example.com/resume/zhouqing.pdf', '硕士学历，熟悉Python、机器学习、NLP和多模态材料解析，有模型训练经验。', '硕士', 'Python,机器学习,NLP,多模态', 2, 1, NOW(), NOW());

INSERT INTO certificate_record(candidate_id, cert_name, cert_type, issue_org, cert_url, verify_status, review_comment, create_time, update_time) VALUES
(1, '软件设计师', '职业资格', '工业和信息化部', 'https://example.com/cert/1.jpg', 1, '证书核验通过', NOW(), NOW()),
(2, '机器学习专项证书', '技能证书', '在线教育平台', 'https://example.com/cert/2.jpg', 0, NULL, NOW(), NOW());

INSERT INTO job_position(job_name, department, job_type, location, salary_range, description, status, create_time, update_time) VALUES
('Java后端工程师', '研发中心', '社招', '杭州', '15k-25k', '负责招聘平台后端服务开发，要求熟悉SpringBoot、MySQL和Redis。', 1, NOW(), NOW()),
('算法工程师', 'AI实验室', '校招', '上海', '20k-30k', '负责简历解析、岗位匹配和推荐算法。', 1, NOW(), NOW());

INSERT INTO job_requirement(job_id, requirement_type, keyword, weight, description, status, create_time, update_time) VALUES
(1, '技能', 'Java', 1.50, 'Java基础扎实', 1, NOW(), NOW()),
(1, '技能', 'SpringBoot', 1.40, '熟悉SpringBoot框架', 1, NOW(), NOW()),
(1, '技能', 'Redis', 1.10, '熟悉缓存应用', 1, NOW(), NOW()),
(2, '技能', 'Python', 1.50, 'Python工程能力', 1, NOW(), NOW()),
(2, '技能', 'NLP', 1.30, '自然语言处理经验', 1, NOW(), NOW());

INSERT INTO parsing_task(task_no, resume_id, task_name, priority, status, handler_id, create_time, finish_time) VALUES
('PT101001', 2, '周晴简历解析', '高', 2, 2, NOW(), NOW()),
('PT101002', 1, '李明简历解析', '普通', 0, 2, NOW(), NULL);

INSERT INTO parsing_result(task_id, resume_id, candidate_id, extracted_education, extracted_skills, extracted_experience, score, conclusion, review_status, review_comment, create_time, update_time) VALUES
(1, 2, 2, '硕士', 'Python,机器学习,NLP,多模态', '2年算法工程经验，具备模型训练和多模态解析经历', 88.00, '材料完整度高，适合进入岗位匹配', 1, '解析准确', NOW(), NOW());

INSERT INTO match_task(task_no, candidate_id, job_id, task_name, priority, status, handler_id, create_time, finish_time) VALUES
('MT101001', 2, 2, '周晴匹配算法工程师岗位', '高', 2, 2, NOW(), NOW()),
('MT101002', 1, 1, '李明匹配Java后端岗位', '普通', 0, 2, NOW(), NULL);

INSERT INTO match_result(task_id, candidate_id, job_id, matched_skills, missing_skills, match_score, recommend_level, conclusion, review_status, review_comment, create_time, update_time) VALUES
(1, 2, 2, 'Python,NLP', '深度学习部署', 86.00, '强推荐', '周晴对算法工程师岗位匹配度较高', 1, '建议安排技术面', NOW(), NOW());

INSERT INTO interview_plan(candidate_id, job_id, interviewer_id, interview_time, interview_type, meeting_address, status, remark, create_time, update_time) VALUES
(2, 2, 4, DATE_ADD(NOW(), INTERVAL 2 DAY), '视频面试', '腾讯会议 101-202-303', 0, '重点考察NLP项目经验', NOW(), NOW());

INSERT INTO interview_feedback(plan_id, interviewer_id, score, result_status, strengths, weaknesses, suggestion, create_time, update_time) VALUES
(1, 4, 86.00, 1, 'NLP基础扎实，项目表达清晰', '工程部署经验需要继续确认', '建议进入二面', NOW(), NOW());

INSERT INTO operation_log(user_id, username, role, module_name, action_type, description, create_time) VALUES
(1, 'admin', 'ADMIN', '系统初始化', '初始化', '初始化101号招聘匹配系统演示数据', NOW());
