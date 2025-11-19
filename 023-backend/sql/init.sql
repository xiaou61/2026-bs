-- AI智能学习助手数据库初始化脚本

-- 创建数据库
CREATE DATABASE IF NOT EXISTS ai_learning_db CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE ai_learning_db;

-- 1. 用户表
CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT '密码',
    real_name VARCHAR(50) NOT NULL COMMENT '真实姓名',
    email VARCHAR(100) COMMENT '邮箱',
    phone VARCHAR(20) COMMENT '手机号',
    grade VARCHAR(20) COMMENT '年级',
    major VARCHAR(100) COMMENT '专业',
    learning_style TINYINT DEFAULT 1 COMMENT '学习风格：1-视觉型，2-听觉型，3-动觉型',
    cognitive_level DECIMAL(3,2) DEFAULT 0.50 COMMENT '认知水平评分(0-1)',
    study_time_preference VARCHAR(50) COMMENT '学习时间偏好',
    difficulty_preference TINYINT DEFAULT 2 COMMENT '难度偏好：1-简单，2-中等，3-困难',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 2. 课程表
CREATE TABLE course (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    course_name VARCHAR(200) NOT NULL COMMENT '课程名称',
    course_code VARCHAR(50) UNIQUE COMMENT '课程编号',
    description TEXT COMMENT '课程描述',
    category_id BIGINT COMMENT '分类ID',
    difficulty_level TINYINT DEFAULT 1 COMMENT '难度等级：1-5',
    duration_minutes INT COMMENT '预估学习时长(分钟)',
    prerequisite_courses JSON COMMENT '前置课程ID列表',
    learning_objectives TEXT COMMENT '学习目标',
    content_type TINYINT COMMENT '内容类型：1-视频，2-文档，3-交互，4-测试',
    status TINYINT DEFAULT 1 COMMENT '状态：1-发布，0-下架',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 3. 知识点表
CREATE TABLE knowledge_point (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    point_name VARCHAR(200) NOT NULL COMMENT '知识点名称',
    point_code VARCHAR(100) UNIQUE COMMENT '知识点编码',
    course_id BIGINT COMMENT '所属课程ID',
    parent_id BIGINT COMMENT '父知识点ID',
    description TEXT COMMENT '知识点描述',
    difficulty_level TINYINT DEFAULT 1 COMMENT '难度等级',
    importance_score DECIMAL(3,2) DEFAULT 0.50 COMMENT '重要性评分',
    keywords VARCHAR(500) COMMENT '关键词列表',
    learning_time_estimate INT COMMENT '预估学习时间(分钟)',
    FOREIGN KEY (course_id) REFERENCES course(id),
    FOREIGN KEY (parent_id) REFERENCES knowledge_point(id)
);

-- 4. 学习记录表
CREATE TABLE learning_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    course_id BIGINT COMMENT '课程ID',
    knowledge_point_id BIGINT COMMENT '知识点ID',
    learning_type TINYINT NOT NULL COMMENT '学习类型：1-观看，2-练习，3-测试，4-复习',
    start_time DATETIME NOT NULL COMMENT '开始学习时间',
    end_time DATETIME COMMENT '结束学习时间',
    duration_seconds INT COMMENT '学习时长(秒)',
    completion_rate DECIMAL(5,2) DEFAULT 0 COMMENT '完成率(%)',
    mastery_level DECIMAL(3,2) COMMENT '掌握程度(0-1)',
    score DECIMAL(5,2) COMMENT '得分',
    interaction_count INT DEFAULT 0 COMMENT '交互次数',
    focus_duration INT COMMENT '专注时长(秒)',
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (course_id) REFERENCES course(id),
    FOREIGN KEY (knowledge_point_id) REFERENCES knowledge_point(id)
);

-- 5. 推荐记录表
CREATE TABLE recommendation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    content_type TINYINT NOT NULL COMMENT '推荐内容类型：1-课程，2-知识点，3-题目',
    content_id BIGINT NOT NULL COMMENT '内容ID',
    recommendation_type TINYINT NOT NULL COMMENT '推荐类型：1-协同过滤，2-内容推荐，3-热门推荐',
    recommendation_score DECIMAL(5,3) COMMENT '推荐分数',
    algorithm_version VARCHAR(50) COMMENT '算法版本',
    is_clicked BOOLEAN DEFAULT FALSE COMMENT '是否点击',
    is_completed BOOLEAN DEFAULT FALSE COMMENT '是否完成学习',
    feedback_score TINYINT COMMENT '用户反馈分数(1-5)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

-- 6. 知识图谱关系表
CREATE TABLE knowledge_relation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    from_knowledge_id BIGINT NOT NULL COMMENT '起始知识点ID',
    to_knowledge_id BIGINT NOT NULL COMMENT '目标知识点ID',
    relation_type TINYINT NOT NULL COMMENT '关系类型：1-前置，2-包含，3-相关，4-扩展',
    weight DECIMAL(3,2) DEFAULT 1.00 COMMENT '关系权重',
    confidence DECIMAL(3,2) DEFAULT 1.00 COMMENT '关系置信度',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (from_knowledge_id) REFERENCES knowledge_point(id),
    FOREIGN KEY (to_knowledge_id) REFERENCES knowledge_point(id)
);

-- 7. 问答记录表
CREATE TABLE qa_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    question TEXT NOT NULL COMMENT '用户问题',
    question_keywords VARCHAR(300) COMMENT '问题关键词',
    answer TEXT COMMENT 'AI回答',
    answer_score DECIMAL(3,2) COMMENT '答案相关性评分',
    knowledge_point_ids JSON COMMENT '相关知识点ID列表',
    session_id VARCHAR(100) COMMENT '对话会话ID',
    is_helpful BOOLEAN COMMENT '用户反馈是否有帮助',
    response_time_ms INT COMMENT '响应时间(毫秒)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

-- 8. 学习路径表
CREATE TABLE learning_path (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    path_name VARCHAR(200) NOT NULL COMMENT '路径名称',
    target_knowledge_point_id BIGINT COMMENT '目标知识点ID',
    path_steps JSON NOT NULL COMMENT '学习步骤序列',
    estimated_duration INT COMMENT '预估完成时长(小时)',
    current_step INT DEFAULT 0 COMMENT '当前步骤',
    progress_rate DECIMAL(5,2) DEFAULT 0 COMMENT '完成进度(%)',
    algorithm_type VARCHAR(50) COMMENT '生成算法类型',
    optimization_score DECIMAL(5,3) COMMENT '路径优化分数',
    is_active BOOLEAN DEFAULT TRUE COMMENT '是否激活',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (target_knowledge_point_id) REFERENCES knowledge_point(id)
);

-- 9. 用户行为表
CREATE TABLE user_behavior (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    behavior_type VARCHAR(50) NOT NULL COMMENT '行为类型：login,view,click,search,download等',
    target_type VARCHAR(50) COMMENT '目标类型：course,knowledge,question等',
    target_id BIGINT COMMENT '目标ID',
    behavior_data JSON COMMENT '行为详细数据',
    session_id VARCHAR(100) COMMENT '会话ID',
    ip_address VARCHAR(50) COMMENT 'IP地址',
    user_agent TEXT COMMENT '用户代理',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

-- 插入测试数据

-- 测试用户
INSERT INTO user (username, password, real_name, email, grade, major, learning_style, cognitive_level, difficulty_preference) VALUES
('admin', '21232f297a57a5a743894a0e4a801fc3', '系统管理员', 'admin@example.com', '研究生', '计算机科学', 1, 0.8, 3),
('student1', 'e10adc3949ba59abbe56e057f20f883e', '张三', 'zhangsan@example.com', '大三', '软件工程', 1, 0.6, 2),
('student2', 'e10adc3949ba59abbe56e057f20f883e', '李四', 'lisi@example.com', '大二', '计算机科学', 2, 0.5, 1),
('student3', 'e10adc3949ba59abbe56e057f20f883e', '王五', 'wangwu@example.com', '大四', '信息管理', 3, 0.7, 3);

-- 测试课程数据
INSERT INTO course (course_name, course_code, description, difficulty_level, duration_minutes, content_type, status) VALUES
('Java基础编程', 'CS001', 'Java语言基础知识学习，包括语法、面向对象等', 2, 480, 1, 1),
('数据结构与算法', 'CS002', '学习常用的数据结构和算法设计方法', 3, 720, 1, 1),
('Web前端开发', 'CS003', 'HTML、CSS、JavaScript等前端技术学习', 2, 600, 1, 1),
('数据库原理', 'CS004', '关系数据库的理论基础和SQL语言', 3, 540, 2, 1),
('机器学习入门', 'CS005', '机器学习基础概念和常用算法', 4, 900, 3, 1);

-- 测试知识点数据
INSERT INTO knowledge_point (point_name, point_code, course_id, description, difficulty_level, importance_score, keywords) VALUES
('Java语法基础', 'JAVA_001', 1, 'Java基本语法、变量、数据类型', 1, 0.9, 'Java,语法,变量,数据类型'),
('面向对象编程', 'JAVA_002', 1, '类、对象、继承、多态、封装', 2, 0.95, '面向对象,类,对象,继承,多态'),
('数组与链表', 'DS_001', 2, '数组和链表的实现与应用', 2, 0.8, '数组,链表,数据结构'),
('排序算法', 'DS_002', 2, '冒泡排序、快速排序、归并排序等', 3, 0.85, '排序,算法,冒泡,快排'),
('HTML基础', 'WEB_001', 3, 'HTML标签和文档结构', 1, 0.7, 'HTML,标签,网页');

-- 测试知识点关系
INSERT INTO knowledge_relation (from_knowledge_id, to_knowledge_id, relation_type, weight, confidence) VALUES
(1, 2, 1, 0.8, 0.9), -- Java语法基础 -> 面向对象编程 (前置关系)
(3, 4, 1, 0.7, 0.8), -- 数组与链表 -> 排序算法 (前置关系)
(2, 3, 3, 0.6, 0.7); -- 面向对象编程 -> 数组与链表 (相关关系)

-- 创建索引优化查询性能
CREATE INDEX idx_user_username ON user(username);
CREATE INDEX idx_learning_record_user_id ON learning_record(user_id);
CREATE INDEX idx_learning_record_course_id ON learning_record(course_id);
CREATE INDEX idx_recommendation_user_id ON recommendation(user_id);
CREATE INDEX idx_knowledge_relation_from ON knowledge_relation(from_knowledge_id);
CREATE INDEX idx_knowledge_relation_to ON knowledge_relation(to_knowledge_id);
CREATE INDEX idx_qa_record_user_id ON qa_record(user_id);
CREATE INDEX idx_learning_path_user_id ON learning_path(user_id);
CREATE INDEX idx_user_behavior_user_id ON user_behavior(user_id);

-- 创建视图简化查询
CREATE VIEW v_user_learning_stats AS
SELECT 
    u.id as user_id,
    u.real_name,
    COUNT(DISTINCT lr.course_id) as courses_learned,
    AVG(lr.mastery_level) as avg_mastery,
    SUM(lr.duration_seconds) as total_learning_seconds
FROM user u
LEFT JOIN learning_record lr ON u.id = lr.user_id
GROUP BY u.id, u.real_name;