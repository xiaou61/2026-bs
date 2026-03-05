DROP DATABASE IF EXISTS alumni_db;
CREATE DATABASE alumni_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE alumni_db;

CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(50),
    role VARCHAR(20) DEFAULT 'alumni',
    status INT DEFAULT 0,
    avatar VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(100),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE grade (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    year INT,
    description VARCHAR(500),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE class_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    grade_id BIGINT,
    name VARCHAR(50) NOT NULL,
    major VARCHAR(100),
    student_count INT DEFAULT 0,
    contact_id BIGINT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE alumni_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    student_no VARCHAR(50),
    grade_id BIGINT,
    class_id BIGINT,
    gender INT DEFAULT 1,
    birthday DATE,
    native_place VARCHAR(100),
    political VARCHAR(50),
    degree VARCHAR(50),
    major VARCHAR(100),
    graduation_date DATE,
    company VARCHAR(200),
    position VARCHAR(100),
    industry VARCHAR(100),
    city VARCHAR(100),
    introduction TEXT,
    is_contact INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE news (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    cover VARCHAR(255),
    category VARCHAR(50) DEFAULT 'news',
    view_count INT DEFAULT 0,
    is_top INT DEFAULT 0,
    status INT DEFAULT 1,
    author_id BIGINT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE news_comment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    news_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    content VARCHAR(500),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE activity (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    cover VARCHAR(255),
    address VARCHAR(500),
    start_time DATETIME,
    end_time DATETIME,
    sign_deadline DATETIME,
    max_count INT DEFAULT 100,
    current_count INT DEFAULT 0,
    status INT DEFAULT 0,
    organizer_id BIGINT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE activity_sign (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    activity_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    sign_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    check_time DATETIME,
    status INT DEFAULT 0
);

CREATE TABLE activity_photo (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    activity_id BIGINT NOT NULL,
    url VARCHAR(255) NOT NULL,
    description VARCHAR(200),
    upload_user_id BIGINT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE donation_project (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    target_amount DECIMAL(12,2) DEFAULT 0,
    current_amount DECIMAL(12,2) DEFAULT 0,
    cover VARCHAR(255),
    status INT DEFAULT 0,
    start_time DATETIME,
    end_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE donation_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    project_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    amount DECIMAL(12,2) NOT NULL,
    message VARCHAR(500),
    is_anonymous INT DEFAULT 0,
    certificate_no VARCHAR(100),
    status INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE alumni_company (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    name VARCHAR(200) NOT NULL,
    logo VARCHAR(255),
    industry VARCHAR(100),
    scale VARCHAR(50),
    address VARCHAR(500),
    website VARCHAR(255),
    introduction TEXT,
    status INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE job_post (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    company_id BIGINT NOT NULL,
    title VARCHAR(200) NOT NULL,
    salary VARCHAR(100),
    city VARCHAR(100),
    experience VARCHAR(50),
    education VARCHAR(50),
    description TEXT,
    contact VARCHAR(100),
    status INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE forum_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    icon VARCHAR(255),
    sort INT DEFAULT 0,
    post_count INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE forum_post (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    category_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    view_count INT DEFAULT 0,
    reply_count INT DEFAULT 0,
    like_count INT DEFAULT 0,
    is_top INT DEFAULT 0,
    is_essence INT DEFAULT 0,
    status INT DEFAULT 0,
    last_reply_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE forum_reply (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    post_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    content TEXT,
    reply_to_id BIGINT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE forum_like (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    post_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_post_user (post_id, user_id)
);

CREATE TABLE banner (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200),
    image VARCHAR(255) NOT NULL,
    url VARCHAR(255),
    sort INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE operation_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    username VARCHAR(50),
    operation VARCHAR(200),
    method VARCHAR(200),
    params TEXT,
    ip VARCHAR(50),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO user (username, password, name, role, status, phone, email) VALUES
('admin', '123456', '管理员', 'admin', 1, '13800000000', 'admin@alumni.com'),
('user', '123456', '张三', 'alumni', 1, '13800000001', 'zhangsan@alumni.com'),
('user2', '123456', '李四', 'alumni', 0, '13800000002', 'lisi@alumni.com'),
('user3', '123456', '王五', 'alumni', 1, '13800000003', 'wangwu@alumni.com'),
('user4', '123456', '赵六', 'alumni', 1, '13800000004', 'zhaoliu@alumni.com');

INSERT INTO grade (name, year, description) VALUES
('2016届', 2016, '2016级计算机学院'),
('2017届', 2017, '2017级计算机学院'),
('2018届', 2018, '2018级计算机学院'),
('2019届', 2019, '2019级计算机学院'),
('2020届', 2020, '2020级计算机学院');

INSERT INTO class_info (grade_id, name, major, student_count) VALUES
(1, '计科1班', '计算机科学与技术', 35),
(1, '计科2班', '计算机科学与技术', 36),
(1, '软工1班', '软件工程', 38),
(2, '计科1班', '计算机科学与技术', 34),
(2, '计科2班', '计算机科学与技术', 35),
(3, '计科1班', '计算机科学与技术', 36),
(3, '软工1班', '软件工程', 37),
(4, '计科1班', '计算机科学与技术', 35),
(5, '计科1班', '计算机科学与技术', 38);

INSERT INTO alumni_info (user_id, student_no, grade_id, class_id, gender, birthday, native_place, political, degree, major, graduation_date, company, position, industry, city, introduction) VALUES
(2, '2016001001', 1, 1, 1, '1998-05-15', '北京', '党员', '本科', '计算机科学与技术', '2020-06-30', '阿里巴巴', '高级工程师', '互联网', '杭州', '热爱编程，专注后端开发'),
(3, '2016001002', 1, 1, 0, '1998-08-20', '上海', '团员', '本科', '计算机科学与技术', '2020-06-30', '腾讯', '前端工程师', '互联网', '深圳', '前端技术爱好者'),
(4, '2017001001', 2, 4, 1, '1999-03-10', '广州', '党员', '硕士', '计算机科学与技术', '2021-06-30', '字节跳动', '算法工程师', '互联网', '北京', '机器学习方向'),
(5, '2018001001', 3, 6, 1, '2000-01-25', '成都', '团员', '本科', '计算机科学与技术', '2022-06-30', '华为', '软件工程师', '通信', '深圳', '全栈开发工程师');

INSERT INTO news (title, content, cover, category, view_count, is_top, status, author_id) VALUES
('计算机学院2025届校友返校日活动通知', '<p>亲爱的校友们：</p><p>学院将于2025年10月举办校友返校日活动，届时将有学术讲座、校园参观、校友联谊等精彩环节，欢迎大家踊跃报名参加！</p>', NULL, 'notice', 256, 1, 1, 1),
('我院校友张三荣获科技创新奖', '<p>热烈祝贺我院2016届校友张三在全国科技创新大赛中荣获一等奖，为母校争光！</p>', NULL, 'news', 189, 0, 1, 1),
('校友企业招聘专场即将举行', '<p>为促进校友就业和企业人才引进，学院联合多家校友企业举办专场招聘会，欢迎在校生和应届毕业生参加。</p>', NULL, 'notice', 320, 0, 1, 1),
('学院新增人工智能实验室', '<p>学院新建成人工智能实验室正式投入使用，配备先进的GPU服务器集群，为科研和教学提供强大支持。</p>', NULL, 'news', 145, 0, 1, 1);

INSERT INTO activity (title, content, cover, address, start_time, end_time, sign_deadline, max_count, current_count, status, organizer_id) VALUES
('2025校友返校日', '<p>欢迎校友们重返母校，共叙同窗情谊。活动包括：校园参观、学术交流、校友晚宴等环节。</p>', NULL, '计算机学院大楼', '2025-10-15 09:00:00', '2025-10-15 18:00:00', '2025-10-10 23:59:59', 200, 45, 0, 1),
('校友篮球友谊赛', '<p>组织校友与在校生进行篮球友谊赛，增进校友间的交流与感情。</p>', NULL, '体育馆', '2025-11-20 14:00:00', '2025-11-20 17:00:00', '2025-11-18 23:59:59', 50, 18, 0, 1),
('AI技术分享会', '<p>邀请在AI领域工作的校友分享行业经验和技术趋势，为在校生提供职业指导。</p>', NULL, '学术报告厅', '2025-12-05 14:00:00', '2025-12-05 17:00:00', '2025-12-03 23:59:59', 100, 32, 0, 1);

INSERT INTO activity_sign (activity_id, user_id, status) VALUES
(1, 2, 0), (1, 4, 0), (1, 5, 0),
(2, 2, 0), (2, 4, 0),
(3, 2, 0), (3, 4, 0), (3, 5, 0);

INSERT INTO donation_project (name, description, target_amount, current_amount, status, start_time, end_time) VALUES
('校友奖学金基金', '设立校友奖学金，资助品学兼优的在校学生完成学业。', 500000.00, 125600.00, 0, '2025-01-01 00:00:00', '2025-12-31 23:59:59'),
('实验室设备升级', '为学院实验室购置先进的教学科研设备，提升教学质量。', 200000.00, 86000.00, 0, '2025-03-01 00:00:00', '2025-09-30 23:59:59'),
('困难学生帮扶基金', '帮助家庭困难的学生解决生活学习上的困难。', 100000.00, 45000.00, 0, '2025-01-01 00:00:00', '2025-12-31 23:59:59');

INSERT INTO donation_record (project_id, user_id, amount, message, is_anonymous, certificate_no, status) VALUES
(1, 2, 5000.00, '祝母校越办越好！', 0, 'CERT202501001', 1),
(1, 4, 10000.00, '感谢母校培养', 0, 'CERT202501002', 1),
(2, 2, 3000.00, '支持实验室建设', 0, 'CERT202502001', 1),
(3, 5, 2000.00, '希望能帮助到学弟学妹', 0, 'CERT202503001', 1);

INSERT INTO alumni_company (user_id, name, industry, scale, address, website, introduction, status) VALUES
(2, '杭州创新科技有限公司', '互联网', '100-500人', '杭州市西湖区', 'https://www.example1.com', '专注于企业数字化转型解决方案', 1),
(4, '北京智能科技有限公司', '人工智能', '50-100人', '北京市海淀区', 'https://www.example2.com', 'AI技术研发与应用', 1);

INSERT INTO job_post (company_id, title, salary, city, experience, education, description, contact, status) VALUES
(1, 'Java高级开发工程师', '25K-40K', '杭州', '3-5年', '本科', '<p>负责公司核心业务系统的开发与维护，参与技术方案设计。</p><p>要求：熟练掌握Java、Spring Boot、MyBatis等技术。</p>', '13800000001', 1),
(1, '前端开发工程师', '18K-30K', '杭州', '2-3年', '本科', '<p>负责公司前端项目开发，优化用户体验。</p><p>要求：熟练掌握Vue.js、React等前端框架。</p>', '13800000001', 1),
(2, '算法工程师', '30K-50K', '北京', '3-5年', '硕士', '<p>负责推荐算法、NLP等方向的研发工作。</p><p>要求：扎实的机器学习理论基础，熟悉Python、TensorFlow等。</p>', '13800000003', 1);

INSERT INTO forum_category (name, description, icon, sort, post_count) VALUES
('校友交流', '校友日常交流、问候寒暄', 'ChatDotRound', 1, 5),
('技术讨论', '技术问题讨论、经验分享', 'Monitor', 2, 3),
('求职招聘', '工作机会发布、求职咨询', 'Briefcase', 3, 2),
('活动组织', '校友活动组织、聚会邀约', 'Calendar', 4, 2),
('母校动态', '学校新闻、学院发展', 'School', 5, 1);

INSERT INTO forum_post (category_id, user_id, title, content, view_count, reply_count, like_count, is_top, is_essence, status, last_reply_time) VALUES
(1, 2, '2016届计科1班的同学们，还记得我吗？', '<p>大家好，我是张三，毕业快5年了，很想念大学的时光和同学们，不知道大家现在都怎么样了？</p>', 156, 8, 12, 0, 0, 0, '2025-02-20 15:30:00'),
(1, 4, '北京校友聚会召集', '<p>准备组织一次北京地区校友聚会，有兴趣的同学请跟帖报名！</p>', 98, 15, 8, 0, 0, 0, '2025-02-22 10:15:00'),
(2, 2, 'Spring Boot 3.0 新特性分享', '<p>最近研究了一下Spring Boot 3.0的新特性，分享给大家...</p>', 234, 12, 25, 0, 1, 0, '2025-02-21 09:00:00'),
(2, 4, '求推荐机器学习入门资料', '<p>想系统学习机器学习，有没有校友推荐一些好的入门资料？</p>', 87, 6, 5, 0, 0, 0, '2025-02-19 14:20:00'),
(3, 2, '阿里内推机会', '<p>我司招聘Java开发，有意向的学弟学妹可以私信我简历。</p>', 312, 20, 35, 1, 0, 0, '2025-02-23 11:00:00');

INSERT INTO forum_reply (post_id, user_id, content, reply_to_id) VALUES
(1, 4, '张三！我是王五啊，还记得我们一起做课程设计吗？', NULL),
(1, 5, '学长好！我是18级的学弟，经常听老师提起你们那一届。', NULL),
(3, 4, '非常实用的分享，学习了！', NULL),
(3, 5, '请问有完整的demo代码吗？', NULL),
(5, 4, '已私信简历，期待联系！', NULL);

INSERT INTO forum_like (post_id, user_id) VALUES
(1, 4), (1, 5), (3, 4), (3, 5), (5, 2), (5, 4), (5, 5);

INSERT INTO banner (title, image, url, sort, status) VALUES
('2025校友返校日', '/upload/banner1.jpg', '/activity/1', 1, 1),
('校友奖学金计划', '/upload/banner2.jpg', '/donation/1', 2, 1),
('校友企业招聘', '/upload/banner3.jpg', '/job', 3, 1);

INSERT INTO operation_log (user_id, username, operation, method, ip) VALUES
(1, 'admin', '用户登录', 'POST /api/auth/login', '127.0.0.1'),
(1, 'admin', '发布新闻', 'POST /api/news', '127.0.0.1'),
(1, 'admin', '创建活动', 'POST /api/activity', '127.0.0.1');
