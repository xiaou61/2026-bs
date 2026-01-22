-- 考研学习系统数据库初始化脚本
DROP DATABASE IF EXISTS kaoyan_study;
CREATE DATABASE kaoyan_study DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE kaoyan_study;

-- 用户表
CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    openid VARCHAR(100) COMMENT '微信openid',
    username VARCHAR(50) NOT NULL COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    nickname VARCHAR(50) COMMENT '昵称',
    avatar VARCHAR(255) COMMENT '头像',
    phone VARCHAR(20) COMMENT '手机号',
    email VARCHAR(100) COMMENT '邮箱',
    role INT DEFAULT 0 COMMENT '角色:0-学生,1-教师,2-管理员',
    target_school VARCHAR(100) COMMENT '目标院校',
    target_major VARCHAR(100) COMMENT '目标专业',
    exam_year INT COMMENT '考研年份',
    points INT DEFAULT 0 COMMENT '积分',
    study_days INT DEFAULT 0 COMMENT '累计学习天数',
    status INT DEFAULT 1 COMMENT '状态:0-禁用,1-正常',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
) COMMENT '用户表';

-- 科目分类表
CREATE TABLE subject (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL COMMENT '科目名称',
    code VARCHAR(20) COMMENT '科目代码',
    icon VARCHAR(255) COMMENT '图标',
    sort_order INT DEFAULT 0 COMMENT '排序',
    status INT DEFAULT 1 COMMENT '状态:0-禁用,1-正常',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
) COMMENT '科目分类表';

-- 课程表
CREATE TABLE course (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    subject_id BIGINT NOT NULL COMMENT '科目ID',
    teacher_id BIGINT COMMENT '教师ID',
    title VARCHAR(200) NOT NULL COMMENT '课程标题',
    cover VARCHAR(255) COMMENT '封面图',
    description TEXT COMMENT '课程简介',
    price DECIMAL(10,2) DEFAULT 0 COMMENT '价格',
    is_free INT DEFAULT 1 COMMENT '是否免费:0-否,1-是',
    chapter_count INT DEFAULT 0 COMMENT '章节数',
    student_count INT DEFAULT 0 COMMENT '学习人数',
    view_count INT DEFAULT 0 COMMENT '浏览量',
    status INT DEFAULT 1 COMMENT '状态:0-下架,1-上架',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
) COMMENT '课程表';

-- 章节表
CREATE TABLE chapter (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    course_id BIGINT NOT NULL COMMENT '课程ID',
    title VARCHAR(200) NOT NULL COMMENT '章节标题',
    video_url VARCHAR(500) COMMENT '视频链接',
    duration INT DEFAULT 0 COMMENT '时长(秒)',
    sort_order INT DEFAULT 0 COMMENT '排序',
    is_free INT DEFAULT 0 COMMENT '是否免费试看:0-否,1-是',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
) COMMENT '章节表';

-- 学习记录表
CREATE TABLE study_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    course_id BIGINT NOT NULL COMMENT '课程ID',
    chapter_id BIGINT COMMENT '章节ID',
    progress INT DEFAULT 0 COMMENT '学习进度(%)',
    last_position INT DEFAULT 0 COMMENT '上次播放位置(秒)',
    study_duration INT DEFAULT 0 COMMENT '学习时长(秒)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) COMMENT '学习记录表';

-- 题库分类表
CREATE TABLE question_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    subject_id BIGINT NOT NULL COMMENT '科目ID',
    name VARCHAR(100) NOT NULL COMMENT '分类名称',
    parent_id BIGINT DEFAULT 0 COMMENT '父级ID',
    sort_order INT DEFAULT 0 COMMENT '排序',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
) COMMENT '题库分类表';

-- 题目表
CREATE TABLE question (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    category_id BIGINT NOT NULL COMMENT '分类ID',
    subject_id BIGINT NOT NULL COMMENT '科目ID',
    type INT NOT NULL COMMENT '题型:1-单选,2-多选,3-判断,4-填空,5-简答',
    difficulty INT DEFAULT 1 COMMENT '难度:1-简单,2-中等,3-困难',
    content TEXT NOT NULL COMMENT '题目内容',
    options TEXT COMMENT '选项(JSON)',
    answer TEXT COMMENT '答案',
    analysis TEXT COMMENT '解析',
    source VARCHAR(200) COMMENT '来源(如真题年份)',
    view_count INT DEFAULT 0 COMMENT '浏览量',
    error_count INT DEFAULT 0 COMMENT '错误次数',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
) COMMENT '题目表';

-- 做题记录表
CREATE TABLE answer_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    question_id BIGINT NOT NULL COMMENT '题目ID',
    user_answer TEXT COMMENT '用户答案',
    is_correct INT COMMENT '是否正确:0-错,1-对',
    time_spent INT DEFAULT 0 COMMENT '用时(秒)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) COMMENT '做题记录表';

-- 错题本表
CREATE TABLE wrong_question (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    question_id BIGINT NOT NULL COMMENT '题目ID',
    wrong_count INT DEFAULT 1 COMMENT '错误次数',
    last_wrong_time DATETIME COMMENT '最近错误时间',
    note TEXT COMMENT '笔记',
    status INT DEFAULT 0 COMMENT '状态:0-未掌握,1-已掌握',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) COMMENT '错题本表';

-- 学习计划表
CREATE TABLE study_plan (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    title VARCHAR(200) NOT NULL COMMENT '计划标题',
    subject_id BIGINT COMMENT '科目ID',
    target_content TEXT COMMENT '学习目标',
    start_date DATE COMMENT '开始日期',
    end_date DATE COMMENT '结束日期',
    daily_hours DECIMAL(4,1) DEFAULT 2 COMMENT '每日学习时长(小时)',
    status INT DEFAULT 0 COMMENT '状态:0-进行中,1-已完成,2-已放弃',
    progress INT DEFAULT 0 COMMENT '完成进度(%)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
) COMMENT '学习计划表';

-- 每日打卡表
CREATE TABLE daily_checkin (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    checkin_date DATE NOT NULL COMMENT '打卡日期',
    study_duration INT DEFAULT 0 COMMENT '学习时长(分钟)',
    question_count INT DEFAULT 0 COMMENT '做题数量',
    correct_count INT DEFAULT 0 COMMENT '正确数量',
    note TEXT COMMENT '学习心得',
    mood INT DEFAULT 3 COMMENT '心情:1-5',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_user_date (user_id, checkin_date)
) COMMENT '每日打卡表';

-- 笔记表
CREATE TABLE note (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    subject_id BIGINT COMMENT '科目ID',
    course_id BIGINT COMMENT '关联课程ID',
    question_id BIGINT COMMENT '关联题目ID',
    title VARCHAR(200) COMMENT '笔记标题',
    content TEXT COMMENT '笔记内容',
    is_public INT DEFAULT 0 COMMENT '是否公开:0-私密,1-公开',
    like_count INT DEFAULT 0 COMMENT '点赞数',
    view_count INT DEFAULT 0 COMMENT '浏览量',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
) COMMENT '笔记表';

-- 收藏表
CREATE TABLE favorite (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    target_type INT NOT NULL COMMENT '类型:1-课程,2-题目,3-笔记',
    target_id BIGINT NOT NULL COMMENT '目标ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_user_target (user_id, target_type, target_id)
) COMMENT '收藏表';

-- 讨论区帖子表
CREATE TABLE post (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    subject_id BIGINT COMMENT '科目ID',
    title VARCHAR(200) NOT NULL COMMENT '标题',
    content TEXT COMMENT '内容',
    images VARCHAR(1000) COMMENT '图片(多个逗号分隔)',
    view_count INT DEFAULT 0 COMMENT '浏览量',
    like_count INT DEFAULT 0 COMMENT '点赞数',
    comment_count INT DEFAULT 0 COMMENT '评论数',
    is_top INT DEFAULT 0 COMMENT '是否置顶:0-否,1-是',
    status INT DEFAULT 1 COMMENT '状态:0-隐藏,1-正常',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
) COMMENT '讨论区帖子表';

-- 评论表
CREATE TABLE comment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    post_id BIGINT NOT NULL COMMENT '帖子ID',
    parent_id BIGINT DEFAULT 0 COMMENT '父评论ID',
    reply_user_id BIGINT COMMENT '回复用户ID',
    content TEXT NOT NULL COMMENT '评论内容',
    like_count INT DEFAULT 0 COMMENT '点赞数',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
) COMMENT '评论表';

-- 公告表
CREATE TABLE notice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL COMMENT '标题',
    content TEXT COMMENT '内容',
    type INT DEFAULT 1 COMMENT '类型:1-系统公告,2-考试通知,3-学习提醒',
    is_top INT DEFAULT 0 COMMENT '是否置顶',
    status INT DEFAULT 1 COMMENT '状态:0-隐藏,1-显示',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
) COMMENT '公告表';

-- 初始化数据
-- 管理员
INSERT INTO sys_user (username, password, nickname, role, status) VALUES
('admin', '123456', '管理员', 2, 1);

-- 教师
INSERT INTO sys_user (username, password, nickname, role, status) VALUES
('teacher1', '123456', '张老师', 1, 1),
('teacher2', '123456', '李老师', 1, 1);

-- 学生
INSERT INTO sys_user (username, password, nickname, role, target_school, target_major, exam_year, points, study_days, status) VALUES
('student1', '123456', '考研小明', 0, '清华大学', '计算机科学与技术', 2026, 100, 30, 1),
('student2', '123456', '学习小红', 0, '北京大学', '软件工程', 2026, 80, 20, 1);

-- 科目
INSERT INTO subject (name, code, icon, sort_order, status) VALUES
('政治', 'politics', '📕', 1, 1),
('英语一', 'english1', '📗', 2, 1),
('英语二', 'english2', '📘', 3, 1),
('数学一', 'math1', '📙', 4, 1),
('数学二', 'math2', '📓', 5, 1),
('数学三', 'math3', '📔', 6, 1),
('计算机专业课', 'cs', '💻', 7, 1),
('管理学', 'management', '📊', 8, 1);

-- 课程
INSERT INTO course (subject_id, teacher_id, title, description, price, is_free, chapter_count, student_count, view_count, status) VALUES
(1, 2, '考研政治精讲班', '全面讲解考研政治各模块知识点，包括马原、毛中特、史纲、思修、形势与政策', 0, 1, 10, 1520, 8500, 1),
(2, 3, '考研英语一核心词汇', '精选考研英语一高频核心词汇5500+，科学记忆方法', 0, 1, 20, 2300, 12000, 1),
(4, 2, '考研数学一全程班', '高等数学、线性代数、概率论与数理统计全面覆盖', 199, 0, 50, 890, 5600, 1),
(7, 3, '408计算机综合精讲', '数据结构、计算机组成原理、操作系统、计算机网络', 299, 0, 40, 560, 3200, 1);

-- 章节
INSERT INTO chapter (course_id, title, video_url, duration, sort_order, is_free) VALUES
(1, '第一章 马克思主义基本原理概论', '/video/politics/1.mp4', 3600, 1, 1),
(1, '第二章 毛泽东思想和中国特色社会主义理论体系概论', '/video/politics/2.mp4', 5400, 2, 0),
(1, '第三章 中国近现代史纲要', '/video/politics/3.mp4', 4200, 3, 0),
(2, '第一章 考研英语词汇导学', '/video/english/1.mp4', 2400, 1, 1),
(2, '第二章 核心词汇A-C', '/video/english/2.mp4', 3600, 2, 0),
(3, '第一章 函数、极限与连续', '/video/math/1.mp4', 5400, 1, 1),
(3, '第二章 一元函数微分学', '/video/math/2.mp4', 7200, 2, 0),
(4, '第一章 数据结构导论', '/video/cs/1.mp4', 3600, 1, 1),
(4, '第二章 线性表', '/video/cs/2.mp4', 5400, 2, 0);

-- 题库分类
INSERT INTO question_category (subject_id, name, parent_id, sort_order) VALUES
(1, '马克思主义基本原理', 0, 1),
(1, '毛中特', 0, 2),
(1, '中国近现代史纲要', 0, 3),
(2, '阅读理解', 0, 1),
(2, '完型填空', 0, 2),
(2, '翻译', 0, 3),
(4, '高等数学', 0, 1),
(4, '线性代数', 0, 2),
(4, '概率论', 0, 3),
(7, '数据结构', 0, 1),
(7, '计算机组成原理', 0, 2),
(7, '操作系统', 0, 3),
(7, '计算机网络', 0, 4);

-- 题目示例
INSERT INTO question (category_id, subject_id, type, difficulty, content, options, answer, analysis, source) VALUES
(1, 1, 1, 1, '马克思主义哲学认为，世界的真正统一性在于它的（）', '["A.物质性","B.运动性","C.客观性","D.可知性"]', 'A', '马克思主义哲学认为世界的真正统一性在于它的物质性，物质是世界的本原。', '2020年真题'),
(1, 1, 1, 2, '实践的基本形式包括（）', '["A.生产实践","B.社会实践","C.科学实验","D.以上都是"]', 'D', '实践的基本形式包括生产实践、社会实践和科学实验三种。', '2021年真题'),
(7, 4, 1, 2, '设函数f(x)在点x=0处连续，且lim(x→0)[f(x)/x]=1，则（）', '["A.f(0)=0","B.f(0)=1","C.f′(0)=1","D.f′(0)不存在"]', 'A', '由极限存在且分母趋于0可知分子也趋于0，即f(0)=0。', '2019年真题'),
(10, 7, 1, 1, '在一个长度为n的顺序表中，向第i个位置插入一个元素，需要移动的元素个数为（）', '["A.n-i","B.n-i+1","C.n-i-1","D.i"]', 'B', '在第i个位置插入，需要将第i到第n个元素后移，共n-i+1个元素。', '基础题'),
(10, 7, 1, 2, '栈和队列的主要区别是（）', '["A.逻辑结构不同","B.存储结构不同","C.所包含的运算不同","D.插入和删除运算的位置不同"]', 'D', '栈是后进先出(LIFO)，队列是先进先出(FIFO)，主要区别在于插入和删除运算的位置不同。', '基础题');

-- 学习计划示例
INSERT INTO study_plan (user_id, title, subject_id, target_content, start_date, end_date, daily_hours, status, progress) VALUES
(4, '政治基础阶段复习', 1, '完成马原、毛中特基础知识学习', '2025-03-01', '2025-06-30', 2, 0, 30),
(4, '英语词汇强化', 2, '背诵核心词汇5500个', '2025-03-01', '2025-08-31', 1.5, 0, 45),
(5, '数学一全程复习', 4, '高数、线代、概率论系统复习', '2025-04-01', '2025-12-01', 4, 0, 20);

-- 打卡记录
INSERT INTO daily_checkin (user_id, checkin_date, study_duration, question_count, correct_count, note, mood) VALUES
(4, CURDATE(), 180, 50, 42, '今天学习了马原第一章，感觉良好', 4),
(4, DATE_SUB(CURDATE(), INTERVAL 1 DAY), 150, 40, 35, '英语单词背了100个', 3),
(5, CURDATE(), 240, 30, 25, '数学做了几道大题，有点难', 3);

-- 笔记
INSERT INTO note (user_id, subject_id, title, content, is_public, like_count, view_count) VALUES
(4, 1, '马原重点知识点总结', '## 马克思主义哲学\n\n### 1. 世界的物质性\n- 物质是世界的本原\n- 运动是物质的根本属性\n\n### 2. 认识论\n- 实践是认识的基础\n- 实践是检验真理的唯一标准', 1, 25, 150),
(5, 4, '高数极限计算方法', '## 极限计算方法总结\n\n1. 直接代入法\n2. 分解因式法\n3. 有理化法\n4. 洛必达法则\n5. 等价无穷小替换', 1, 18, 120);

-- 帖子
INSERT INTO post (user_id, subject_id, title, content, view_count, like_count, comment_count, status) VALUES
(4, 1, '求问：马原哲学部分怎么背？', '马原的哲学部分内容太多了，大家有什么好的记忆方法吗？', 230, 15, 8, 1),
(5, 4, '分享：数学一140+经验', '复习了一年，数学一考了142分，分享一下我的复习经验和用的资料...', 560, 45, 23, 1),
(4, 2, '英语一阅读技巧分享', '总结了一些阅读理解的做题技巧，希望对大家有帮助', 180, 12, 5, 1);

-- 评论
INSERT INTO comment (user_id, post_id, parent_id, content, like_count) VALUES
(5, 1, 0, '建议先理解再记忆，可以画思维导图', 5),
(4, 2, 0, '太厉害了，请问高数用的什么资料？', 3),
(5, 2, 2, '我用的是张宇的全套，从基础到强化', 2);

-- 公告
INSERT INTO notice (title, content, type, is_top, status) VALUES
('2026年考研大纲已发布', '2026年全国硕士研究生招生考试大纲已于9月发布，请同学们及时关注变化。', 2, 1, 1),
('系统更新通知', '系统已完成升级，新增错题本智能复习功能，欢迎体验！', 1, 0, 1),
('国庆学习打卡活动', '国庆期间连续打卡7天，可获得100积分奖励！', 3, 0, 1);
