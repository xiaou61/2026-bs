-- 博物馆文物数字化管理平台数据库初始化脚本
CREATE DATABASE IF NOT EXISTS museum_relic DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE museum_relic;

-- 用户表
DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    real_name VARCHAR(50) COMMENT '真实姓名',
    phone VARCHAR(20) COMMENT '手机号',
    email VARCHAR(100) COMMENT '邮箱',
    avatar VARCHAR(255) COMMENT '头像',
    role INT DEFAULT 0 COMMENT '角色:0-游客,1-讲解员,2-研究员,3-管理员',
    status INT DEFAULT 1 COMMENT '状态:0-禁用,1-正常',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) COMMENT '用户表';

-- 文物分类表
DROP TABLE IF EXISTS relic_category;
CREATE TABLE relic_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL COMMENT '分类名称',
    description VARCHAR(500) COMMENT '分类描述',
    sort INT DEFAULT 0 COMMENT '排序',
    status INT DEFAULT 1 COMMENT '状态:0-禁用,1-正常',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) COMMENT '文物分类表';

-- 朝代表
DROP TABLE IF EXISTS dynasty;
CREATE TABLE dynasty (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL COMMENT '朝代名称',
    start_year VARCHAR(20) COMMENT '起始年份',
    end_year VARCHAR(20) COMMENT '结束年份',
    description VARCHAR(500) COMMENT '朝代描述',
    sort INT DEFAULT 0 COMMENT '排序',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) COMMENT '朝代表';

-- 展厅表
DROP TABLE IF EXISTS exhibition_hall;
CREATE TABLE exhibition_hall (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL COMMENT '展厅名称',
    floor INT COMMENT '楼层',
    area DECIMAL(10,2) COMMENT '面积(平方米)',
    capacity INT COMMENT '容纳人数',
    description VARCHAR(500) COMMENT '展厅描述',
    status INT DEFAULT 1 COMMENT '状态:0-关闭,1-开放',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) COMMENT '展厅表';

-- 文物表
DROP TABLE IF EXISTS relic;
CREATE TABLE relic (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL COMMENT '文物名称',
    category_id BIGINT COMMENT '分类ID',
    dynasty_id BIGINT COMMENT '朝代ID',
    hall_id BIGINT COMMENT '展厅ID',
    relic_no VARCHAR(50) COMMENT '文物编号',
    material VARCHAR(100) COMMENT '材质',
    size VARCHAR(100) COMMENT '尺寸',
    weight VARCHAR(50) COMMENT '重量',
    origin VARCHAR(200) COMMENT '出土地点',
    discovery_date DATE COMMENT '发现日期',
    level INT DEFAULT 3 COMMENT '文物等级:1-一级,2-二级,3-三级,4-一般',
    image VARCHAR(255) COMMENT '主图',
    model_url VARCHAR(255) COMMENT '3D模型URL',
    audio_url VARCHAR(255) COMMENT '语音讲解URL',
    description TEXT COMMENT '文物描述',
    historical_value TEXT COMMENT '历史价值',
    view_count INT DEFAULT 0 COMMENT '浏览次数',
    like_count INT DEFAULT 0 COMMENT '点赞次数',
    status INT DEFAULT 1 COMMENT '状态:0-下架,1-展出中,2-修复中,3-外借中',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) COMMENT '文物表';

-- 文物图片表
DROP TABLE IF EXISTS relic_image;
CREATE TABLE relic_image (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    relic_id BIGINT NOT NULL COMMENT '文物ID',
    image_url VARCHAR(255) NOT NULL COMMENT '图片URL',
    description VARCHAR(200) COMMENT '图片描述',
    sort INT DEFAULT 0 COMMENT '排序',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) COMMENT '文物图片表';

-- 研究成果表
DROP TABLE IF EXISTS research;
CREATE TABLE research (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL COMMENT '标题',
    relic_id BIGINT COMMENT '关联文物ID',
    author_id BIGINT COMMENT '作者ID',
    content TEXT COMMENT '内容',
    summary VARCHAR(500) COMMENT '摘要',
    publish_date DATE COMMENT '发布日期',
    file_url VARCHAR(255) COMMENT '附件URL',
    view_count INT DEFAULT 0 COMMENT '浏览次数',
    status INT DEFAULT 0 COMMENT '状态:0-待审核,1-已发布,2-已拒绝',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) COMMENT '研究成果表';

-- 展览活动表
DROP TABLE IF EXISTS exhibition;
CREATE TABLE exhibition (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL COMMENT '展览标题',
    hall_id BIGINT COMMENT '展厅ID',
    cover_image VARCHAR(255) COMMENT '封面图',
    start_date DATE COMMENT '开始日期',
    end_date DATE COMMENT '结束日期',
    description TEXT COMMENT '展览介绍',
    ticket_price DECIMAL(10,2) DEFAULT 0 COMMENT '票价',
    view_count INT DEFAULT 0 COMMENT '浏览次数',
    status INT DEFAULT 0 COMMENT '状态:0-未开始,1-进行中,2-已结束',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) COMMENT '展览活动表';

-- 预约参观表
DROP TABLE IF EXISTS reservation;
CREATE TABLE reservation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_no VARCHAR(50) NOT NULL COMMENT '订单号',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    exhibition_id BIGINT COMMENT '展览ID',
    visit_date DATE NOT NULL COMMENT '参观日期',
    time_slot VARCHAR(50) COMMENT '时间段',
    visitor_count INT DEFAULT 1 COMMENT '参观人数',
    contact_name VARCHAR(50) COMMENT '联系人',
    contact_phone VARCHAR(20) COMMENT '联系电话',
    total_price DECIMAL(10,2) DEFAULT 0 COMMENT '总金额',
    status INT DEFAULT 0 COMMENT '状态:0-待确认,1-已确认,2-已完成,3-已取消',
    remark VARCHAR(200) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) COMMENT '预约参观表';

-- 讲解预约表
DROP TABLE IF EXISTS guide_booking;
CREATE TABLE guide_booking (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_no VARCHAR(50) NOT NULL COMMENT '订单号',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    guide_id BIGINT COMMENT '讲解员ID',
    visit_date DATE NOT NULL COMMENT '讲解日期',
    start_time TIME COMMENT '开始时间',
    duration INT DEFAULT 60 COMMENT '时长(分钟)',
    visitor_count INT DEFAULT 1 COMMENT '人数',
    language VARCHAR(20) DEFAULT '中文' COMMENT '语言',
    price DECIMAL(10,2) DEFAULT 0 COMMENT '价格',
    status INT DEFAULT 0 COMMENT '状态:0-待确认,1-已确认,2-进行中,3-已完成,4-已取消',
    remark VARCHAR(200) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) COMMENT '讲解预约表';

-- 收藏表
DROP TABLE IF EXISTS favorite;
CREATE TABLE favorite (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    relic_id BIGINT NOT NULL COMMENT '文物ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_user_relic (user_id, relic_id)
) COMMENT '收藏表';

-- 评论表
DROP TABLE IF EXISTS comment;
CREATE TABLE comment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    relic_id BIGINT COMMENT '文物ID',
    exhibition_id BIGINT COMMENT '展览ID',
    content VARCHAR(500) NOT NULL COMMENT '评论内容',
    rating INT DEFAULT 5 COMMENT '评分1-5',
    status INT DEFAULT 1 COMMENT '状态:0-隐藏,1-显示',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) COMMENT '评论表';

-- 系统公告表
DROP TABLE IF EXISTS notice;
CREATE TABLE notice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL COMMENT '标题',
    content TEXT COMMENT '内容',
    type INT DEFAULT 1 COMMENT '类型:1-系统公告,2-展览通知,3-闭馆通知',
    publish_time DATETIME COMMENT '发布时间',
    status INT DEFAULT 0 COMMENT '状态:0-草稿,1-已发布',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) COMMENT '公告表';

-- 插入测试数据
INSERT INTO sys_user (username, password, real_name, phone, role, status) VALUES
('admin', '123456', '系统管理员', '13800000000', 3, 1),
('guide1', '123456', '张讲解', '13800000001', 1, 1),
('guide2', '123456', '李讲解', '13800000002', 1, 1),
('researcher1', '123456', '王研究员', '13800000003', 2, 1),
('researcher2', '123456', '赵研究员', '13800000004', 2, 1),
('user1', '123456', '游客小明', '13800000005', 0, 1),
('user2', '123456', '游客小红', '13800000006', 0, 1);

INSERT INTO relic_category (name, description, sort) VALUES
('青铜器', '商周至秦汉时期的青铜器皿', 1),
('陶瓷器', '各朝代陶瓷制品', 2),
('玉器', '玉石雕刻制品', 3),
('书画', '名人书法绘画作品', 4),
('金银器', '金银制品及首饰', 5),
('石刻', '石雕石刻作品', 6),
('织绣', '丝绸织物刺绣品', 7),
('杂项', '其他类别文物', 8);

INSERT INTO dynasty (name, start_year, end_year, description, sort) VALUES
('夏朝', '约前2070年', '约前1600年', '中国历史上第一个世袭王朝', 1),
('商朝', '约前1600年', '约前1046年', '青铜器文明的鼎盛时期', 2),
('周朝', '约前1046年', '前256年', '分为西周和东周，礼乐制度完善', 3),
('秦朝', '前221年', '前207年', '中国第一个统一的中央集权王朝', 4),
('汉朝', '前202年', '220年', '分为西汉和东汉，丝绸之路开辟', 5),
('唐朝', '618年', '907年', '中国历史上最强盛的朝代之一', 6),
('宋朝', '960年', '1279年', '文化艺术高度发展的时期', 7),
('元朝', '1271年', '1368年', '蒙古族建立的大一统王朝', 8),
('明朝', '1368年', '1644年', '汉族建立的最后一个大一统王朝', 9),
('清朝', '1636年', '1912年', '中国最后一个封建王朝', 10);

INSERT INTO exhibition_hall (name, floor, area, capacity, description, status) VALUES
('青铜器展厅', 1, 500.00, 200, '展示商周时期精美青铜器', 1),
('陶瓷展厅', 1, 600.00, 250, '展示历代陶瓷精品', 1),
('玉器展厅', 2, 400.00, 150, '展示各朝代玉器珍品', 1),
('书画展厅', 2, 800.00, 300, '展示历代名家书画', 1),
('临时展厅A', 3, 1000.00, 400, '用于临时展览活动', 1),
('临时展厅B', 3, 800.00, 350, '用于临时展览活动', 1);

INSERT INTO relic (name, category_id, dynasty_id, hall_id, relic_no, material, size, weight, origin, level, description, historical_value, status) VALUES
('四羊方尊', 1, 2, 1, 'QT-001', '青铜', '高58.3厘米', '34.5千克', '湖南宁乡', 1, '商代晚期青铜礼器，是现存商代青铜方尊中最大的一件。', '四羊方尊是商代晚期青铜器的杰出代表，体现了商代高超的铸造技术。', 1),
('后母戊鼎', 1, 2, 1, 'QT-002', '青铜', '高133厘米，口长112厘米', '832.84千克', '河南安阳', 1, '商代晚期铸造的青铜鼎，是世界上现存最大、最重的青铜礼器。', '后母戊鼎是商代王室祭祀重器，代表了中国青铜文明的最高成就。', 1),
('越王勾践剑', 1, 3, 1, 'QT-003', '青铜', '长55.7厘米', '约0.9千克', '湖北江陵', 1, '春秋时期越国国君勾践所用青铜剑，历经两千多年仍锋利如新。', '越王勾践剑代表了春秋时期冶炼和铸造技术的最高水平。', 1),
('汝窑天青釉洗', 2, 7, 2, 'TC-001', '瓷', '高3.5厘米，直径13厘米', '约0.3千克', '河南宝丰', 1, '北宋汝窑瓷器，釉色天青，温润如玉，是宋代五大名窑之首。', '汝窑瓷器传世稀少，素有"汝窑为魁"之称。', 1),
('青花瓷大盘', 2, 9, 2, 'TC-002', '瓷', '直径45厘米', '约2千克', '江西景德镇', 2, '明代永乐年间景德镇官窑烧制的青花瓷大盘，绘有龙纹图案。', '明代青花瓷代表了中国陶瓷工艺的高峰。', 1),
('和田白玉观音', 3, 10, 3, 'YQ-001', '和田玉', '高25厘米', '约1.2千克', '新疆和田', 2, '清代乾隆年间雕刻的和田白玉观音像，玉质温润，雕工精细。', '清代玉雕工艺精湛，此件观音像体现了乾隆时期宫廷玉器的最高水准。', 1),
('翠玉白菜', 3, 10, 3, 'YQ-002', '翡翠', '高18.7厘米', '约0.5千克', '缅甸', 1, '清代翡翠雕刻作品，利用翠绿和白色玉石巧妙雕成白菜造型。', '翠玉白菜是清宫旧藏珍品，展现了中国玉雕"巧色"技艺的最高水平。', 1),
('清明上河图', 4, 7, 4, 'SH-001', '绢本设色', '纵24.8厘米，横528.7厘米', '—', '北京故宫', 1, '北宋画家张择端所绘，描绘北宋都城汴京的繁华景象和社会生活。', '清明上河图是中国十大传世名画之一，具有极高的历史价值和艺术价值。', 1);

INSERT INTO exhibition (title, hall_id, start_date, end_date, description, ticket_price, status) VALUES
('青铜器文明特展', 5, '2026-01-01', '2026-03-31', '展示商周时期青铜器精品，探索中国青铜文明的发展历程', 50.00, 1),
('宋代美学特展', 6, '2026-02-01', '2026-04-30', '展示宋代瓷器、书画等艺术品，感受宋代美学的独特魅力', 60.00, 1),
('丝绸之路文物展', 5, '2026-05-01', '2026-07-31', '展示丝绸之路沿线出土文物，再现古代东西方文化交流盛况', 80.00, 0);

INSERT INTO research (title, relic_id, author_id, content, summary, publish_date, status) VALUES
('四羊方尊铸造工艺研究', 1, 4, '本文通过对四羊方尊的铸造工艺进行深入分析...', '研究发现四羊方尊采用分铸法和浑铸法相结合的复杂工艺', '2025-06-15', 1),
('汝窑釉色成因分析', 4, 5, '通过现代科技手段分析汝窑天青釉的化学成分和烧制条件...', '揭示汝窑天青釉色形成的科学原理', '2025-08-20', 1),
('越王勾践剑防锈之谜', 3, 4, '探讨越王勾践剑历经两千多年不锈的原因...', '分析表明剑身含有特殊合金成分', '2025-10-10', 0);

INSERT INTO notice (title, content, type, publish_time, status) VALUES
('春节期间开馆公告', '尊敬的观众朋友们，春节期间本馆正常开放，开馆时间为9:00-17:00，欢迎参观！', 1, '2026-01-15 10:00:00', 1),
('青铜器文明特展开幕', '青铜器文明特展将于2026年1月1日在临时展厅A开幕，欢迎预约参观！', 2, '2025-12-25 09:00:00', 1),
('2月5日临时闭馆通知', '因设备检修，2月5日本馆临时闭馆一天，给您带来不便敬请谅解。', 3, '2026-01-28 14:00:00', 1);

INSERT INTO reservation (order_no, user_id, exhibition_id, visit_date, time_slot, visitor_count, contact_name, contact_phone, total_price, status) VALUES
('R202601200001', 6, 1, '2026-01-25', '09:00-12:00', 2, '小明', '13800000005', 100.00, 1),
('R202601200002', 7, 2, '2026-01-26', '14:00-17:00', 3, '小红', '13800000006', 180.00, 0);

INSERT INTO guide_booking (order_no, user_id, guide_id, visit_date, start_time, duration, visitor_count, price, status) VALUES
('G202601200001', 6, 2, '2026-01-25', '10:00:00', 90, 2, 200.00, 1),
('G202601200002', 7, 3, '2026-01-26', '14:30:00', 60, 3, 150.00, 0);
