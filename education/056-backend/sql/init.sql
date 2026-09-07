DROP DATABASE IF EXISTS writing_competition;
CREATE DATABASE writing_competition DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE writing_competition;

CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50),
    avatar VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(100),
    role TINYINT DEFAULT 2,
    openid VARCHAR(100),
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_username (username),
    INDEX idx_role (role),
    INDEX idx_openid (openid)
);

CREATE TABLE category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    sort INT DEFAULT 0,
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_status (status)
);

CREATE TABLE competition (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    category_id BIGINT,
    cover VARCHAR(255),
    description TEXT,
    requirement TEXT,
    start_time DATETIME,
    end_time DATETIME,
    submit_deadline DATETIME,
    status TINYINT DEFAULT 0,
    max_words INT DEFAULT 5000,
    min_words INT DEFAULT 100,
    publish_result TINYINT DEFAULT 0,
    create_user BIGINT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_category (category_id),
    INDEX idx_status (status),
    INDEX idx_create_time (create_time)
);

CREATE TABLE work (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    competition_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    word_count INT DEFAULT 0,
    attachment VARCHAR(255),
    status TINYINT DEFAULT 0,
    reject_reason VARCHAR(500),
    final_score DECIMAL(5,2),
    `rank` INT,
    submit_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_competition (competition_id),
    INDEX idx_user (user_id),
    INDEX idx_status (status)
);

CREATE TABLE score_standard (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    competition_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    max_score DECIMAL(5,2) DEFAULT 100,
    weight DECIMAL(3,2) DEFAULT 1.00,
    description VARCHAR(500),
    sort INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_competition (competition_id)
);

CREATE TABLE judge_assignment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    competition_id BIGINT NOT NULL,
    judge_id BIGINT NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_competition_judge (competition_id, judge_id),
    INDEX idx_judge (judge_id)
);

CREATE TABLE score (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    work_id BIGINT NOT NULL,
    judge_id BIGINT NOT NULL,
    standard_id BIGINT NOT NULL,
    score DECIMAL(5,2),
    comment VARCHAR(500),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_work_judge_standard (work_id, judge_id, standard_id),
    INDEX idx_work (work_id),
    INDEX idx_judge (judge_id)
);

CREATE TABLE score_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    work_id BIGINT NOT NULL,
    judge_id BIGINT NOT NULL,
    total_score DECIMAL(5,2),
    comment TEXT,
    status TINYINT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_work_judge (work_id, judge_id),
    INDEX idx_work (work_id),
    INDEX idx_judge (judge_id)
);

CREATE TABLE notice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    type TINYINT DEFAULT 0,
    competition_id BIGINT,
    status TINYINT DEFAULT 0,
    top TINYINT DEFAULT 0,
    create_user BIGINT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_type (type),
    INDEX idx_status (status),
    INDEX idx_competition (competition_id)
);

CREATE TABLE award (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    competition_id BIGINT NOT NULL,
    work_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    award_level VARCHAR(50),
    certificate VARCHAR(255),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_competition (competition_id),
    INDEX idx_user (user_id)
);

INSERT INTO user (username, password, nickname, role, status) VALUES
('admin', 'e10adc3949ba59abbe56e057f20f883e', '管理员', 0, 1),
('judge', 'e10adc3949ba59abbe56e057f20f883e', '评委老师', 1, 1),
('judge2', 'e10adc3949ba59abbe56e057f20f883e', '评委老师2', 1, 1),
('user', 'e10adc3949ba59abbe56e057f20f883e', '参赛者小明', 2, 1),
('user2', 'e10adc3949ba59abbe56e057f20f883e', '参赛者小红', 2, 1),
('user3', 'e10adc3949ba59abbe56e057f20f883e', '参赛者小李', 2, 1);

INSERT INTO category (name, sort, status) VALUES
('散文', 1, 1),
('诗歌', 2, 1),
('小说', 3, 1),
('议论文', 4, 1),
('记叙文', 5, 1);

INSERT INTO competition (title, category_id, description, requirement, start_time, end_time, submit_deadline, status, max_words, min_words, create_user) VALUES
('2024年春季散文大赛', 1, '以"春天的故事"为主题，书写春天的美好与希望。', '1. 原创作品，严禁抄袭\n2. 字数800-3000字\n3. 主题明确，情感真挚', '2024-03-01 00:00:00', '2024-05-31 23:59:59', '2024-05-15 23:59:59', 1, 3000, 800, 1),
('青春诗歌创作赛', 2, '用诗歌表达青春的热情与梦想。', '1. 现代诗或古体诗均可\n2. 字数不限\n3. 内容积极向上', '2024-04-01 00:00:00', '2024-06-30 23:59:59', '2024-06-15 23:59:59', 1, 1000, 50, 1),
('微小说征文比赛', 3, '用最精炼的文字，讲述最动人的故事。', '1. 字数500-2000字\n2. 题材不限\n3. 故事完整，有起承转合', '2024-05-01 00:00:00', '2024-07-31 23:59:59', '2024-07-15 23:59:59', 1, 2000, 500, 1);

INSERT INTO score_standard (competition_id, name, max_score, weight, description, sort) VALUES
(1, '主题契合度', 30, 0.30, '文章是否紧扣主题，立意是否明确', 1),
(1, '文字表达', 30, 0.30, '语言是否优美流畅，表达是否准确', 2),
(1, '情感真挚', 20, 0.20, '情感是否真实感人，能否引起共鸣', 3),
(1, '结构完整', 20, 0.20, '文章结构是否合理，层次是否分明', 4),
(2, '意境营造', 40, 0.40, '诗歌意境是否优美，画面感是否强', 1),
(2, '语言韵律', 30, 0.30, '语言是否凝练，韵律是否和谐', 2),
(2, '情感表达', 30, 0.30, '情感是否真挚，主题是否鲜明', 3),
(3, '故事性', 35, 0.35, '故事是否吸引人，情节是否紧凑', 1),
(3, '人物塑造', 25, 0.25, '人物形象是否鲜明，性格是否立体', 2),
(3, '语言表达', 20, 0.20, '语言是否简洁有力，描写是否生动', 3),
(3, '创意新颖', 20, 0.20, '构思是否独特，角度是否新颖', 4);

INSERT INTO judge_assignment (competition_id, judge_id) VALUES
(1, 2), (1, 3),
(2, 2), (2, 3),
(3, 2), (3, 3);

INSERT INTO work (competition_id, user_id, title, content, word_count, status, submit_time) VALUES
(1, 4, '春风十里', '春风十里，不如你。当第一缕春风拂过脸颊，我知道，那个充满希望的季节又来了...\n\n三月的阳光，温柔地洒在校园的每一个角落。樱花树下，几个女孩正在拍照，她们的笑声清脆如银铃。我坐在长椅上，手中捧着一本诗集，却没有心思阅读。\n\n记得去年的这个时候，奶奶还在。她总是说，春天是播种希望的季节。每年春天，她都会在院子里种下各种花草，然后坐在摇椅上，等待它们发芽、开花。\n\n如今，院子里的花依旧开得灿烂，可那个等花开的人却不在了。但我知道，奶奶并没有真正离开，她化作了春风，化作了阳光，永远陪伴在我身边。\n\n春风十里，皆是思念。', 856, 1, '2024-03-15 10:30:00'),
(1, 5, '春天的使者', '窗外，一只燕子正在衔泥筑巢。这是今年的第一只燕子，它带来了春天的消息...\n\n我推开窗户，深深呼吸着春天的气息。空气中弥漫着泥土的芬芳和花草的清香，让人心旷神怡。院子里的桃花开了，粉嫩的花瓣在微风中轻轻摇曳。\n\n母亲正在院子里晾衣服，阳光照在她的脸上，显得格外温柔。她抬头看见燕子，笑着说："燕子来了，春天就真的来了。"\n\n是啊，燕子是春天的使者，它们不畏千里之遥，只为回到这片熟悉的土地。这种执着，这种忠诚，不正是我们应该学习的吗？\n\n我决定，这个春天，我也要像燕子一样，为自己的梦想不懈努力。', 823, 1, '2024-03-18 14:20:00'),
(2, 4, '青春不散场', '青春是一场盛大的烟火\n在夜空中绽放\n绚烂而短暂\n\n我们在教室里\n听粉笔划过黑板的声音\n那是青春的乐章\n\n操场上的汗水\n图书馆的灯火\n都是青春的印记\n\n时光匆匆\n青春不散场\n因为记忆永存', 320, 1, '2024-04-10 09:15:00'),
(2, 6, '梦想的颜色', '梦想是什么颜色\n是清晨第一缕阳光的金色\n是深夜星空的深蓝\n还是奋斗汗水的透明\n\n我想\n梦想应该是彩虹的颜色\n经过风雨的洗礼\n才能绽放出最美的光芒\n\n所以\n不要害怕困难\n不要畏惧失败\n因为每一次跌倒\n都是梦想上色的过程', 356, 1, '2024-04-12 16:45:00');

INSERT INTO notice (title, content, type, competition_id, status, top, create_user) VALUES
('2024年春季散文大赛正式开始', '亲爱的同学们，2024年春季散文大赛现已正式开始，欢迎大家踊跃参加！本次大赛以"春天的故事"为主题，期待看到大家的精彩作品。', 1, 1, 1, 1, 1),
('青春诗歌创作赛报名通知', '青春诗歌创作赛现已开放报名，欢迎热爱诗歌的同学们参加。用诗歌表达你的青春，让文字承载你的梦想！', 1, 2, 1, 0, 1),
('关于作品提交的说明', '请各位参赛者注意：\n1. 作品提交后可在截止日期前修改\n2. 请确保作品为原创\n3. 字数需符合比赛要求\n如有疑问，请联系组委会。', 0, NULL, 1, 0, 1),
('评分规则公布', '为保证比赛公平公正，现公布评分规则：\n1. 每篇作品由至少2名评委评分\n2. 去除最高分和最低分后取平均分\n3. 评分标准详见各比赛说明', 0, NULL, 1, 0, 1);
