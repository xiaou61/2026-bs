DROP DATABASE IF EXISTS harbin_tourism;
CREATE DATABASE harbin_tourism DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE harbin_tourism;

CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50),
    avatar VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(100),
    role VARCHAR(20) DEFAULT 'user',
    balance DECIMAL(10,2) DEFAULT 0.00,
    status INT DEFAULT 1,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE scenic_spot (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    detail TEXT,
    location VARCHAR(200),
    category VARCHAR(50),
    cover_img VARCHAR(255),
    images TEXT,
    open_time VARCHAR(20),
    close_time VARCHAR(20),
    ticket_price DECIMAL(10,2),
    rating DECIMAL(2,1) DEFAULT 0.0,
    view_count INT DEFAULT 0,
    status INT DEFAULT 1,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE route (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    days INT DEFAULT 1,
    difficulty VARCHAR(20) DEFAULT 'easy',
    category VARCHAR(50),
    cover_img VARCHAR(255),
    estimated_cost DECIMAL(10,2),
    like_count INT DEFAULT 0,
    status INT DEFAULT 1,
    user_id BIGINT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE route_spot (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    route_id BIGINT NOT NULL,
    spot_id BIGINT NOT NULL,
    order_num INT DEFAULT 1,
    stay_hours DECIMAL(3,1) DEFAULT 2.0
);

CREATE TABLE ticket_type (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    spot_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    stock INT DEFAULT 100,
    max_per_order INT DEFAULT 5,
    description VARCHAR(255),
    status INT DEFAULT 1
);

CREATE TABLE ticket_order (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_no VARCHAR(50) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    spot_id BIGINT NOT NULL,
    ticket_type_id BIGINT NOT NULL,
    ticket_date DATE NOT NULL,
    quantity INT NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) DEFAULT 'pending',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE hotel (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    type VARCHAR(50),
    price_min DECIMAL(10,2),
    price_max DECIMAL(10,2),
    description TEXT,
    cover_img VARCHAR(255),
    facilities VARCHAR(500),
    rating DECIMAL(2,1) DEFAULT 0.0,
    status INT DEFAULT 1,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE restaurant (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    cuisine_type VARCHAR(50),
    price_per_person DECIMAL(10,2),
    description TEXT,
    cover_img VARCHAR(255),
    recommended_dish VARCHAR(500),
    tags VARCHAR(255),
    rating DECIMAL(2,1) DEFAULT 0.0,
    status INT DEFAULT 1,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE activity (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    location VARCHAR(200),
    start_time DATETIME,
    end_time DATETIME,
    cover_img VARCHAR(255),
    register_deadline DATETIME,
    max_participants INT DEFAULT 100,
    current_participants INT DEFAULT 0,
    status VARCHAR(20) DEFAULT 'registering',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE activity_registration (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    activity_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    register_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    status INT DEFAULT 1
);

CREATE TABLE travel_note (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    title VARCHAR(100) NOT NULL,
    content TEXT,
    cover_img VARCHAR(255),
    tags VARCHAR(255),
    spot_id BIGINT,
    like_count INT DEFAULT 0,
    view_count INT DEFAULT 0,
    status VARCHAR(20) DEFAULT 'pending',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE review (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    target_id BIGINT NOT NULL,
    target_type VARCHAR(20) NOT NULL,
    rating INT NOT NULL,
    content TEXT,
    images VARCHAR(500),
    status INT DEFAULT 1,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE announcement (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    content TEXT,
    type VARCHAR(20),
    is_top INT DEFAULT 0,
    status INT DEFAULT 1,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE favorite (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    target_id BIGINT NOT NULL,
    target_type VARCHAR(20) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_user_target (user_id, target_id, target_type)
);

INSERT INTO user (username, password, nickname, avatar, phone, email, role, balance) VALUES
('admin', '123456', '管理员', '/avatar/admin.jpg', '13800000001', 'admin@harbin.com', 'admin', 0.00),
('user', '123456', '冰城游客', '/avatar/user1.jpg', '13800000002', 'user@harbin.com', 'user', 200.00),
('tourist', '123456', '雪乡探索者', '/avatar/user2.jpg', '13800000003', 'tourist@harbin.com', 'user', 50.00);

INSERT INTO scenic_spot (name, description, detail, location, category, cover_img, images, open_time, close_time, ticket_price, rating, view_count) VALUES
('冰雪大世界', '世界最大冰雪主题乐园，汇集冰雕、雪雕、冰灯等冰雪艺术精品', '哈尔滨冰雪大世界始创于1999年，是由哈尔滨市政府为迎接千年庆典神州世纪游活动创办的大型冰雪艺术精品工程。园区占地60万平方米，用冰量约18万立方米，建有冰雕、雪雕、冰灯等冰雪艺术作品。', '哈尔滨市松北区太阳岛西侧', '冰雪景观', '/img/spot/bingxue.jpg', '/img/spot/bingxue1.jpg,/img/spot/bingxue2.jpg', '10:00', '21:30', 288.00, 4.8, 58620),
('中央大街', '亚洲最长步行街，汇集欧式建筑群和百年老店', '中央大街全长1450米，宽21.34米，始建于1898年，是目前亚洲最大最长的步行街。街道两侧汇集了文艺复兴、巴洛克等多种风格的建筑71栋。', '哈尔滨市道里区中央大街', '特色街区', '/img/spot/zhongyang.jpg', '/img/spot/zhongyang1.jpg,/img/spot/zhongyang2.jpg', '00:00', '24:00', 0.00, 4.7, 89540),
('圣索菲亚大教堂', '远东地区最大的东正教堂，拜占庭式建筑典范', '圣索菲亚大教堂始建于1907年，是目前中国保存最完整的拜占庭式建筑。教堂高53.35米，占地721平方米，是远东地区最大的东正教堂。', '哈尔滨市道里区透笼街88号', '历史建筑', '/img/spot/suofei.jpg', '/img/spot/suofei1.jpg,/img/spot/suofei2.jpg', '08:30', '17:00', 20.00, 4.6, 45230),
('太阳岛风景区', '国家5A级旅游景区，哈尔滨的城市名片', '太阳岛风景区是国家级风景名胜区、国家5A级旅游景区。全岛总面积88平方公里，是江漫滩湿地草原型风景名胜区。', '哈尔滨市松北区太阳大道1号', '自然风光', '/img/spot/taiyang.jpg', '/img/spot/taiyang1.jpg,/img/spot/taiyang2.jpg', '08:00', '17:30', 30.00, 4.5, 38760),
('东北虎林园', '世界最大东北虎繁育基地，近距离观赏野生东北虎', '东北虎林园是世界上最大的东北虎繁育基地，园区占地面积144万平方米，拥有各种不同年龄的纯种东北虎1000余只。', '哈尔滨市松北区松北街88号', '主题公园', '/img/spot/hulinyuan.jpg', '/img/spot/hulinyuan1.jpg', '08:30', '16:30', 130.00, 4.4, 28950),
('哈尔滨极地馆', '世界首家极地演艺游乐园，白鲸表演震撼人心', '哈尔滨极地馆是世界首座极地演艺游乐园，园内拥有多只白鲸、北极熊、企鹅等极地动物，白鲸表演《海洋之心》是镇馆之宝。', '哈尔滨市松北区太阳大道3号', '主题公园', '/img/spot/jidi.jpg', '/img/spot/jidi1.jpg,/img/spot/jidi2.jpg', '09:00', '17:00', 160.00, 4.6, 32180),
('黑龙江省博物馆', '了解黑龙江历史文化的最佳去处', '黑龙江省博物馆是中国省级综合性博物馆，馆藏文物6万余件，展现了黑龙江地区从远古到近现代的历史文化。', '哈尔滨市南岗区红军街64号', '博物馆', '/img/spot/bowuguan.jpg', '/img/spot/bowuguan1.jpg', '09:00', '16:30', 0.00, 4.3, 18650),
('松花江观光索道', '横跨松花江的空中走廊，俯瞰哈尔滨全景', '松花江索道全长1156米，是目前国内横跨江面最长的观光索道。乘坐索道可以俯瞰松花江两岸风光和哈尔滨城市全景。', '哈尔滨市道里区通江街218号', '自然风光', '/img/spot/suodao.jpg', '/img/spot/suodao1.jpg', '08:30', '21:00', 80.00, 4.5, 22340),
('老道外中华巴洛克', '百年历史街区，中西合璧建筑群落', '老道外中华巴洛克历史文化街区保存了大量清末民初的中华巴洛克风格建筑，是哈尔滨城市文化的活化石。', '哈尔滨市道外区南二道街', '历史建筑', '/img/spot/daowai.jpg', '/img/spot/daowai1.jpg,/img/spot/daowai2.jpg', '00:00', '24:00', 0.00, 4.2, 15890),
('亚布力滑雪旅游度假区', '亚洲最大滑雪场，冬奥冠军的摇篮', '亚布力滑雪旅游度假区是中国目前最大、设施最先进的滑雪场。拥有高山滑雪场地、越野滑雪场地、跳台滑雪场地等。', '哈尔滨市尚志市亚布力镇', '冰雪景观', '/img/spot/yabuli.jpg', '/img/spot/yabuli1.jpg,/img/spot/yabuli2.jpg', '08:00', '16:30', 180.00, 4.7, 41560);

INSERT INTO route (title, description, days, difficulty, category, cover_img, estimated_cost, like_count, user_id) VALUES
('哈尔滨经典三日游', '涵盖冰雪大世界、中央大街、圣索菲亚教堂等必打卡景点，体验冰城魅力', 3, 'easy', '经典路线', '/img/route/classic3.jpg', 800.00, 1256, 1),
('冰雪狂欢二日游', '专注冰雪体验，冰雪大世界+亚布力滑雪场，感受极致冰雪乐趣', 2, 'medium', '冰雪主题', '/img/route/snow2.jpg', 600.00, 986, 1),
('历史文化一日游', '探访圣索菲亚教堂、老道外巴洛克街区、省博物馆，感受百年历史', 1, 'easy', '文化之旅', '/img/route/culture1.jpg', 100.00, 652, 1),
('亲子欢乐二日游', '东北虎林园+极地馆+太阳岛，大人小孩都开心', 2, 'easy', '亲子游', '/img/route/family2.jpg', 500.00, 823, 1),
('深度体验五日游', '哈尔滨全景深度游，不留遗憾的冰城之旅', 5, 'hard', '深度游', '/img/route/deep5.jpg', 1500.00, 567, 1);

INSERT INTO route_spot (route_id, spot_id, order_num, stay_hours) VALUES
(1, 1, 1, 4.0), (1, 2, 2, 3.0), (1, 3, 3, 1.5), (1, 4, 4, 3.0), (1, 6, 5, 3.0),
(2, 1, 1, 5.0), (2, 10, 2, 6.0),
(3, 3, 1, 1.5), (3, 9, 2, 2.0), (3, 7, 3, 2.0),
(4, 5, 1, 3.0), (4, 6, 2, 3.0), (4, 4, 3, 3.0),
(5, 1, 1, 5.0), (5, 2, 2, 3.0), (5, 3, 3, 1.5), (5, 4, 4, 3.0), (5, 5, 5, 3.0), (5, 6, 6, 3.0), (5, 9, 7, 2.0), (5, 10, 8, 6.0);

INSERT INTO ticket_type (spot_id, name, price, stock, max_per_order, description) VALUES
(1, '成人票', 288.00, 5000, 5, '冰雪大世界成人门票'),
(1, '学生票', 168.00, 2000, 5, '需携带有效学生证'),
(1, '儿童票', 0.00, 1000, 3, '1.2米以下儿童免票'),
(3, '成人票', 20.00, 3000, 10, '圣索菲亚教堂参观票'),
(3, '学生票', 10.00, 1000, 10, '需携带有效学生证'),
(4, '成人票', 30.00, 5000, 10, '太阳岛风景区门票'),
(4, '学生票', 15.00, 2000, 10, '需携带有效学生证'),
(5, '成人票', 130.00, 2000, 5, '东北虎林园门票含观光车'),
(5, '儿童票', 65.00, 1000, 3, '1.2-1.4米儿童'),
(6, '成人票', 160.00, 3000, 5, '极地馆门票'),
(6, '学生票', 110.00, 1000, 5, '需携带有效学生证'),
(8, '单程票', 50.00, 2000, 5, '松花江索道单程票'),
(8, '往返票', 80.00, 2000, 5, '松花江索道往返票'),
(10, '初级道票', 180.00, 1000, 3, '亚布力初级雪道4小时'),
(10, '中级道票', 280.00, 500, 3, '亚布力中级雪道4小时');

INSERT INTO hotel (name, address, type, price_min, price_max, description, cover_img, facilities, rating) VALUES
('哈尔滨香格里拉大酒店', '哈尔滨市道里区友谊路555号', '豪华酒店', 800.00, 2500.00, '国际五星级酒店，位于松花江畔，尽享江景', '/img/hotel/shangrila.jpg', 'WiFi,停车场,健身房,游泳池,餐厅,会议室', 4.8),
('哈尔滨万达嘉华酒店', '哈尔滨市松北区世茂大道99号', '豪华酒店', 600.00, 1800.00, '位于万达茂商圈，出行便利，设施齐全', '/img/hotel/wanda.jpg', 'WiFi,停车场,健身房,餐厅,会议室', 4.6),
('哈尔滨中央大街亚朵酒店', '哈尔滨市道里区石头道街118号', '经济型', 300.00, 600.00, '毗邻中央大街，出行方便，性价比高', '/img/hotel/yaduo.jpg', 'WiFi,停车场,餐厅,洗衣房', 4.5),
('松花江畔青年旅社', '哈尔滨市道里区中央大街附近', '青年旅社', 50.00, 150.00, '背包客首选，认识志同道合的旅伴', '/img/hotel/youth.jpg', 'WiFi,公共厨房,洗衣房', 4.2),
('冰城印象民宿', '哈尔滨市道外区南二道街', '特色民宿', 200.00, 400.00, '老道外百年建筑改造，体验老哈尔滨风情', '/img/hotel/minsu1.jpg', 'WiFi,暖气,独立卫浴', 4.4),
('雪乡童话木屋', '哈尔滨市双城区雪乡景区内', '特色民宿', 400.00, 800.00, '雪乡特色木屋，推窗即是童话世界', '/img/hotel/xuexiang.jpg', 'WiFi,暖气,独立卫浴,雪景房', 4.7),
('哈尔滨索菲特大酒店', '哈尔滨市南岗区中山路99号', '豪华酒店', 700.00, 2000.00, '法国品牌五星酒店，法式浪漫风情', '/img/hotel/sofitel.jpg', 'WiFi,停车场,健身房,游泳池,SPA,餐厅', 4.7),
('如家快捷酒店中央大街店', '哈尔滨市道里区中央大街189号', '经济型', 150.00, 300.00, '连锁品牌，卫生有保障，价格实惠', '/img/hotel/rujia.jpg', 'WiFi,停车场', 4.0),
('汉庭酒店哈尔滨站店', '哈尔滨市南岗区春申街5号', '经济型', 180.00, 350.00, '靠近火车站，交通便利', '/img/hotel/hanting.jpg', 'WiFi,停车场,自助洗衣', 4.1),
('亚布力滑雪度假村', '哈尔滨市尚志市亚布力镇', '特色民宿', 500.00, 1200.00, '滑雪场配套酒店，滑雪度假首选', '/img/hotel/yabuli.jpg', 'WiFi,暖气,餐厅,雪具寄存,滑雪教练', 4.5);

INSERT INTO restaurant (name, address, cuisine_type, price_per_person, description, cover_img, recommended_dish, tags, rating) VALUES
('老厨家道台食府', '哈尔滨市道里区中央大街84号', '东北菜', 80.00, '百年老字号，正宗哈尔滨菜系代表', '/img/rest/laochujia.jpg', '锅包肉,熏酱大拼,铁锅炖', '锅包肉,百年老店,东北菜', 4.7),
('东方饺子王', '哈尔滨市道里区中央大街39号', '东北菜', 40.00, '现包现煮，皮薄馅大', '/img/rest/jiaozi.jpg', '三鲜馅,酸菜馅,黄瓜虾仁', '饺子,老字号', 4.5),
('马迭尔西餐厅', '哈尔滨市道里区中央大街89号', '西餐', 120.00, '百年历史，俄式西餐正宗体验', '/img/rest/madier.jpg', '俄式红菜汤,大列巴,俄式肉饼', '马迭尔,西餐,百年老店', 4.6),
('马迭尔冰棍', '哈尔滨市道里区中央大街', '冰品', 5.00, '哈尔滨必打卡，零下20度吃冰棍', '/img/rest/madier_ice.jpg', '原味冰棍,巧克力冰棍', '马迭尔冰棍,网红打卡', 4.8),
('秋林里道斯红肠', '哈尔滨市南岗区秋林公司', '东北菜', 30.00, '正宗哈尔滨红肠，百年品牌', '/img/rest/hongchang.jpg', '红肠,大列巴,俄式香肠', '红肠,秋林,老字号', 4.6),
('老六杀猪菜', '哈尔滨市道外区靖宇街', '东北菜', 60.00, '地道东北杀猪菜，酸菜血肠一绝', '/img/rest/shazhucai.jpg', '杀猪菜,血肠,酸菜白肉', '杀猪菜,东北味', 4.4),
('开江鱼馆', '哈尔滨市松北区', '东北菜', 100.00, '正宗开江鱼，鲜美无比', '/img/rest/kaijiangyu.jpg', '清蒸白鱼,红烧鲤鱼,铁锅炖鱼', '开江鱼,江鱼,鲜', 4.5),
('金汉斯烤肉', '哈尔滨市道里区上海街', '烧烤', 90.00, '自助烤肉，种类丰富', '/img/rest/jinhansi.jpg', '肥牛,羊腿,海鲜', '烤肉,自助', 4.3),
('冰城串吧', '哈尔滨市南岗区西大直街', '烧烤', 50.00, '哈尔滨特色烧烤，撸串喝啤酒', '/img/rest/chuanba.jpg', '羊肉串,牛肉串,烤茄子', '烧烤,撸串,哈啤', 4.4),
('辣庄火锅', '哈尔滨市道里区经纬街', '火锅', 80.00, '川味火锅，麻辣鲜香', '/img/rest/huoguo.jpg', '毛肚,鸭肠,牛肉', '火锅,川味', 4.3),
('韩香源', '哈尔滨市南岗区红军街', '日韩料理', 70.00, '正宗韩式烤肉，地道韩国味', '/img/rest/hanshi.jpg', '五花肉,牛舌,石锅拌饭', '韩餐,烤肉', 4.2),
('华梅西餐厅', '哈尔滨市道里区中央大街112号', '西餐', 150.00, '中国四大西餐厅之一，百年历史', '/img/rest/huamei.jpg', '罐焖牛肉,奶油鸡,软煎大马哈鱼', '西餐,百年老店,华梅', 4.7);

INSERT INTO activity (title, description, location, start_time, end_time, cover_img, register_deadline, max_participants, current_participants, status) VALUES
('2026哈尔滨冰雪节开幕式', '第42届中国哈尔滨国际冰雪节盛大开幕，冰雪狂欢夜', '冰雪大世界', '2026-01-05 18:00:00', '2026-01-05 22:00:00', '/img/activity/icefest.jpg', '2026-01-04 18:00:00', 10000, 8562, 'ended'),
('冰雕艺术大赛', '国际冰雕大师现场创作，见证冰雪奇迹诞生', '冰雪大世界', '2026-01-10 09:00:00', '2026-01-15 17:00:00', '/img/activity/iceart.jpg', '2026-01-09 17:00:00', 500, 423, 'ended'),
('元宵节松花江冰上龙舟赛', '冰上龙舟竞速，感受不一样的元宵节', '松花江冰面', '2026-02-12 10:00:00', '2026-02-12 16:00:00', '/img/activity/dragonboat.jpg', '2026-02-10 17:00:00', 200, 156, 'registering'),
('太阳岛雪博会', '雪雕艺术的殿堂，冬日里的童话世界', '太阳岛风景区', '2026-12-20 09:00:00', '2026-02-28 17:00:00', '/img/activity/snowart.jpg', '2026-02-27 17:00:00', 5000, 3218, 'ongoing'),
('冰城马拉松冬季挑战赛', '零下20度的马拉松，挑战极限', '中央大街起点', '2026-01-20 08:00:00', '2026-01-20 14:00:00', '/img/activity/marathon.jpg', '2026-01-15 17:00:00', 3000, 2156, 'ended'),
('哈尔滨之夏音乐会', '夏季音乐盛典，享受音乐与清凉', '哈尔滨大剧院', '2026-07-15 19:30:00', '2026-07-15 22:00:00', '/img/activity/summer.jpg', '2026-07-14 17:00:00', 1500, 0, 'registering');

INSERT INTO activity_registration (activity_id, user_id, register_time) VALUES
(3, 2, '2026-02-01 10:30:00'),
(4, 2, '2026-01-15 14:20:00'),
(6, 2, '2026-02-20 09:15:00'),
(3, 3, '2026-02-05 16:45:00');

INSERT INTO travel_note (user_id, title, content, cover_img, tags, spot_id, like_count, view_count, status) VALUES
(2, '冰雪大世界超详细攻略', '第一次来哈尔滨，冰雪大世界绝对是必打卡！建议下午4点入园，可以看到日景和夜景两种风情。记得穿厚一点，暖宝宝多贴几个...', '/img/note/note1.jpg', '冰雪大世界,攻略,冬游', 1, 328, 5620, 'published'),
(2, '中央大街一日暴走记', '中央大街真的太美了！每一栋建筑都值得驻足欣赏。马迭尔冰棍一定要尝，零下二十度吃冰棍的感觉太爽了...', '/img/note/note2.jpg', '中央大街,马迭尔,美食', 2, 256, 4380, 'published'),
(3, '圣索菲亚大教堂拍照指南', '教堂拍照最佳时间是傍晚，夕阳洒在圆顶上特别好看。冬天有雪的时候更是绝美...', '/img/note/note3.jpg', '圣索菲亚,拍照,建筑', 3, 189, 3250, 'published'),
(2, '亚布力滑雪初体验', '人生第一次滑雪献给了亚布力！教练很专业很耐心，从初级道开始学起...', '/img/note/note4.jpg', '亚布力,滑雪,初体验', 10, 412, 6890, 'published'),
(3, '东北虎林园亲子游记', '带娃来看老虎！孩子兴奋坏了，近距离看到真正的东北虎...', '/img/note/note5.jpg', '东北虎,亲子游,动物', 5, 167, 2890, 'published'),
(2, '哈尔滨美食地图', '来哈尔滨怎么能不吃！锅包肉、红肠、大列巴、杀猪菜...一篇文章告诉你哈尔滨必吃美食', '/img/note/note6.jpg', '美食,攻略,锅包肉', NULL, 523, 8960, 'published'),
(3, '松花江索道观景记', '乘坐索道横跨松花江，俯瞰整个哈尔滨，风景绝佳！', '/img/note/note7.jpg', '索道,观景,松花江', 8, 98, 1560, 'published'),
(2, '老道外巴洛克街区漫步', '这里保留着最原汁原味的老哈尔滨，中西合璧的建筑特别有韵味...', '/img/note/note8.jpg', '老道外,巴洛克,历史', 9, 145, 2340, 'published');

INSERT INTO review (user_id, target_id, target_type, rating, content, images, status) VALUES
(2, 1, 'spot', 5, '太震撼了！冰雕真的太美了，晚上灯光效果绝佳！', '/img/review/r1.jpg', 1),
(3, 1, 'spot', 5, '不虚此行，建议傍晚入园，日夜景都能看到', NULL, 1),
(2, 2, 'spot', 5, '中央大街太美了，每栋建筑都是艺术品', '/img/review/r2.jpg', 1),
(3, 2, 'spot', 4, '人很多但是值得，马迭尔冰棍必尝', NULL, 1),
(2, 3, 'spot', 5, '教堂很庄严，建筑很美，值得一看', NULL, 1),
(2, 5, 'spot', 4, '老虎很威武，孩子很喜欢', '/img/review/r3.jpg', 1),
(3, 6, 'spot', 5, '白鲸表演太棒了！强烈推荐', NULL, 1),
(2, 10, 'spot', 5, '滑雪场很专业，设施完善', '/img/review/r4.jpg', 1),
(2, 1, 'hotel', 5, '服务一流，江景房风景绝美', NULL, 1),
(3, 3, 'hotel', 4, '位置很好，出门就是中央大街', NULL, 1),
(2, 5, 'hotel', 5, '民宿很有特色，老板人很好', NULL, 1),
(3, 6, 'hotel', 5, '雪景房太美了，推开窗就是童话', '/img/review/r5.jpg', 1),
(2, 1, 'restaurant', 5, '锅包肉太正宗了！必吃', '/img/review/r6.jpg', 1),
(3, 1, 'restaurant', 5, '老字号果然名不虚传', NULL, 1),
(2, 3, 'restaurant', 4, '俄式西餐很有特色', NULL, 1),
(2, 4, 'restaurant', 5, '马迭尔冰棍yyds！', NULL, 1),
(3, 5, 'restaurant', 5, '红肠很正宗，买了好几根带回家', NULL, 1),
(2, 6, 'restaurant', 4, '杀猪菜量很大，味道不错', NULL, 1),
(3, 12, 'restaurant', 5, '华梅西餐厅环境很好，菜品精致', '/img/review/r7.jpg', 1),
(2, 8, 'restaurant', 4, '自助烤肉种类多，性价比高', NULL, 1);

INSERT INTO announcement (title, content, type, is_top, status) VALUES
('2026哈尔滨冰雪节盛大开幕', '第42届中国哈尔滨国际冰雪节于1月5日盛大开幕！今年冰雪大世界占地面积再创新高，欢迎各地游客来哈赏冰乐雪！', 'activity', 1, 1),
('春节期间景区营业时间调整通知', '春节期间（2月9日-2月15日），各大景区营业时间有所调整，请游客关注各景区官方公告。', 'notice', 0, 1),
('关于购票优惠政策说明', '即日起至2月底，本平台购买冰雪大世界门票享9折优惠，学生凭有效证件再享额外折扣。', 'promotion', 1, 1);

INSERT INTO favorite (user_id, target_id, target_type) VALUES
(2, 1, 'spot'), (2, 2, 'spot'), (2, 10, 'spot'),
(3, 1, 'spot'), (3, 3, 'spot'),
(2, 1, 'route'), (2, 2, 'route'),
(3, 3, 'route'),
(2, 1, 'hotel'), (2, 6, 'hotel'),
(3, 3, 'hotel'),
(2, 1, 'restaurant'), (2, 4, 'restaurant'),
(3, 5, 'restaurant'), (3, 12, 'restaurant');
