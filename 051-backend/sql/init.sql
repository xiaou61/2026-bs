CREATE DATABASE IF NOT EXISTS security_knowledge DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE security_knowledge;

CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    openid VARCHAR(64) NOT NULL UNIQUE,
    nickname VARCHAR(50),
    avatar VARCHAR(255),
    points INT DEFAULT 0,
    level INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE knowledge_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    icon VARCHAR(255),
    sort INT DEFAULT 0,
    status TINYINT DEFAULT 1
);

CREATE TABLE knowledge_article (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    category_id BIGINT NOT NULL,
    title VARCHAR(100) NOT NULL,
    cover VARCHAR(255),
    content TEXT,
    view_count INT DEFAULT 0,
    like_count INT DEFAULT 0,
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_category_id (category_id)
);

CREATE TABLE question (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    category_id BIGINT NOT NULL,
    content VARCHAR(500) NOT NULL,
    options JSON,
    answer VARCHAR(10) NOT NULL,
    explanation VARCHAR(500),
    difficulty TINYINT DEFAULT 1,
    INDEX idx_category_id (category_id)
);

CREATE TABLE answer_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    question_id BIGINT NOT NULL,
    user_answer VARCHAR(10),
    is_correct TINYINT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_user_id (user_id),
    INDEX idx_question_id (question_id)
);

CREATE TABLE learn_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    article_id BIGINT NOT NULL,
    progress INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_user_article (user_id, article_id)
);

CREATE TABLE favorite (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    article_id BIGINT NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_user_article (user_id, article_id)
);

CREATE TABLE news (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    cover VARCHAR(255),
    content TEXT,
    source VARCHAR(50),
    view_count INT DEFAULT 0,
    status TINYINT DEFAULT 1,
    publish_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE qa_post (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    title VARCHAR(100) NOT NULL,
    content TEXT,
    view_count INT DEFAULT 0,
    reply_count INT DEFAULT 0,
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_user_id (user_id)
);

CREATE TABLE qa_reply (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    post_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    content TEXT,
    like_count INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_post_id (post_id)
);

CREATE TABLE admin (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50),
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO admin (username, password, nickname) VALUES ('admin', 'e10adc3949ba59abbe56e057f20f883e', '管理员');

INSERT INTO knowledge_category (name, icon, sort) VALUES
('密码安全', '/static/icons/password.png', 1),
('防诈骗', '/static/icons/fraud.png', 2),
('隐私保护', '/static/icons/privacy.png', 3),
('病毒防护', '/static/icons/virus.png', 4),
('网络钓鱼', '/static/icons/phishing.png', 5),
('社交安全', '/static/icons/social.png', 6);

INSERT INTO knowledge_article (category_id, title, cover, content) VALUES
(1, '如何设置强密码', '/static/covers/password1.png', '<p>强密码是保护账户安全的第一道防线。一个强密码应该包含以下特点：</p><p>1. 长度至少12位以上</p><p>2. 包含大小写字母、数字和特殊符号</p><p>3. 避免使用生日、电话号码等个人信息</p><p>4. 不同账户使用不同密码</p><p>5. 定期更换密码</p>'),
(1, '密码管理器的使用', '/static/covers/password2.png', '<p>密码管理器可以帮助您安全地存储和管理所有密码：</p><p>1. 选择知名的密码管理器软件</p><p>2. 设置一个强主密码</p><p>3. 开启双因素认证</p><p>4. 定期备份密码库</p>'),
(2, '识别电信诈骗', '/static/covers/fraud1.png', '<p>常见电信诈骗手段：</p><p>1. 冒充公检法</p><p>2. 冒充客服退款</p><p>3. 刷单返利</p><p>4. 杀猪盘</p><p>防范方法：不轻信、不转账、多核实</p>'),
(2, '网购防骗指南', '/static/covers/fraud2.png', '<p>网购时注意以下几点：</p><p>1. 选择正规电商平台</p><p>2. 不要点击陌生链接</p><p>3. 使用平台官方支付渠道</p><p>4. 保留交易记录</p>'),
(3, '保护个人隐私', '/static/covers/privacy1.png', '<p>日常隐私保护建议：</p><p>1. 谨慎授权APP权限</p><p>2. 不在公共场所连接陌生WiFi</p><p>3. 定期清理浏览记录</p><p>4. 不随意填写个人信息</p>'),
(4, '防范勒索病毒', '/static/covers/virus1.png', '<p>勒索病毒防范措施：</p><p>1. 及时更新系统和软件</p><p>2. 安装正版杀毒软件</p><p>3. 不打开来历不明的邮件附件</p><p>4. 定期备份重要文件</p>');

INSERT INTO question (category_id, content, options, answer, explanation, difficulty) VALUES
(1, '以下哪个是强密码?', '["A. 123456", "B. password", "C. Abc@123#Xyz!", "D. qwerty"]', 'C', '强密码应包含大小写字母、数字和特殊符号，长度至少8位以上', 1),
(1, '密码多久更换一次比较合适?', '["A. 永不更换", "B. 每天更换", "C. 3-6个月", "D. 10年一次"]', 'C', '建议每3-6个月更换一次密码，平衡安全性和便利性', 1),
(2, '收到自称银行客服的电话要求转账，正确做法是?', '["A. 立即转账", "B. 挂断后拨打银行官方客服核实", "C. 告诉对方密码", "D. 按对方指示操作"]', 'B', '任何要求转账的电话都要谨慎，应通过官方渠道核实', 1),
(2, '以下哪种不是常见的诈骗手段?', '["A. 刷单返利", "B. 冒充公检法", "C. 正规银行柜台办理业务", "D. 杀猪盘"]', 'C', '正规银行柜台办理业务是安全的，其他都是常见诈骗手段', 1),
(3, '在公共WiFi环境下，以下哪种行为相对安全?', '["A. 登录网银", "B. 浏览新闻", "C. 输入支付密码", "D. 登录公司内网"]', 'B', '公共WiFi环境下应避免进行涉及敏感信息的操作', 2),
(4, '发现电脑中了病毒，首先应该?', '["A. 继续使用", "B. 断开网络连接", "C. 格式化硬盘", "D. 忽略不管"]', 'B', '发现病毒后应先断网防止扩散，再进行杀毒处理', 2);

INSERT INTO news (title, cover, content, source) VALUES
('2024年网络安全威胁趋势报告', '/static/news/news1.png', '<p>根据最新统计，2024年网络安全威胁呈现以下趋势：</p><p>1. 勒索软件攻击持续增加</p><p>2. AI驱动的网络攻击兴起</p><p>3. 供应链攻击成为新热点</p><p>4. 移动端安全威胁加剧</p>', '网络安全中心'),
('警惕新型网络诈骗手段', '/static/news/news2.png', '<p>近期出现多种新型网络诈骗手段：</p><p>1. AI换脸诈骗</p><p>2. 虚假投资平台</p><p>3. 冒充快递客服</p><p>请广大市民提高警惕，保护财产安全。</p>', '公安部门'),
('国家网络安全宣传周活动启动', '/static/news/news3.png', '<p>一年一度的国家网络安全宣传周正式启动，本次活动主题为"网络安全为人民，网络安全靠人民"。</p>', '新华社');
