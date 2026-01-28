DROP DATABASE IF EXISTS agriculture_tech;
CREATE DATABASE agriculture_tech DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE agriculture_tech;

CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    real_name VARCHAR(50),
    role VARCHAR(20) DEFAULT 'FARMER',
    phone VARCHAR(20),
    email VARCHAR(100),
    avatar VARCHAR(255),
    region VARCHAR(100),
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE crop_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    parent_id BIGINT DEFAULT 0,
    sort INT DEFAULT 0,
    description TEXT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE crop (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    category_id BIGINT,
    variety VARCHAR(100),
    growth_cycle INT,
    suitable_temp VARCHAR(50),
    suitable_soil VARCHAR(100),
    planting_season VARCHAR(50),
    yield_per_mu DECIMAL(10,2),
    description TEXT,
    image VARCHAR(255),
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE knowledge (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    category VARCHAR(50),
    content TEXT,
    images TEXT,
    tags VARCHAR(200),
    author_id BIGINT,
    view_count INT DEFAULT 0,
    like_count INT DEFAULT 0,
    collect_count INT DEFAULT 0,
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE production_plan (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    plan_name VARCHAR(200) NOT NULL,
    crop_id BIGINT,
    area DECIMAL(10,2),
    expected_yield DECIMAL(10,2),
    start_date DATE,
    end_date DATE,
    status TINYINT DEFAULT 0,
    progress INT DEFAULT 0,
    creator_id BIGINT,
    remark TEXT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE production_task (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    plan_id BIGINT,
    task_name VARCHAR(200) NOT NULL,
    task_type VARCHAR(50),
    assignee_id BIGINT,
    start_date DATE,
    end_date DATE,
    status TINYINT DEFAULT 0,
    completion_time DATETIME,
    remark TEXT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE pest_disease (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    type TINYINT,
    crop_type VARCHAR(100),
    symptom TEXT,
    harm TEXT,
    prevention TEXT,
    treatment TEXT,
    images TEXT,
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE pest_warning (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    pest_disease_id BIGINT,
    region VARCHAR(100),
    crop_type VARCHAR(100),
    warning_level TINYINT,
    warning_date DATE,
    content TEXT,
    publisher_id BIGINT,
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE treatment_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    plan_id BIGINT,
    pest_disease_id BIGINT,
    treatment_date DATE,
    method TEXT,
    medicine VARCHAR(200),
    dosage VARCHAR(100),
    effect TINYINT,
    cost DECIMAL(10,2),
    operator_id BIGINT,
    remark TEXT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE agricultural_material (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(50),
    specification VARCHAR(100),
    unit VARCHAR(20),
    stock INT DEFAULT 0,
    warning_stock INT DEFAULT 0,
    unit_price DECIMAL(10,2),
    supplier VARCHAR(100),
    description TEXT,
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE material_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    material_id BIGINT,
    type TINYINT,
    quantity INT,
    unit_price DECIMAL(10,2),
    total_price DECIMAL(10,2),
    purpose VARCHAR(200),
    operator_id BIGINT,
    record_date DATE,
    remark TEXT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE consultation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    images TEXT,
    crop_type VARCHAR(100),
    tags VARCHAR(200),
    questioner_id BIGINT,
    status TINYINT DEFAULT 0,
    view_count INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE consultation_answer (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    consultation_id BIGINT,
    content TEXT,
    images TEXT,
    answerer_id BIGINT,
    is_adopted TINYINT DEFAULT 0,
    like_count INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE expert_appointment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    expert_id BIGINT,
    farmer_id BIGINT,
    appointment_date DATE,
    appointment_time VARCHAR(50),
    topic VARCHAR(200),
    description TEXT,
    status TINYINT DEFAULT 0,
    rating TINYINT,
    feedback TEXT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE notice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    type VARCHAR(50),
    publisher_id BIGINT,
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO user (username, password, real_name, role, phone, region, status) VALUES
('admin', '123456', '系统管理员', 'ADMIN', '13800000001', '全国', 1),
('expert', '123456', '张专家', 'EXPERT', '13800000002', '山东省', 1),
('tech', '123456', '李技术员', 'TECHNICIAN', '13800000003', '河南省', 1),
('farmer', '123456', '王农户', 'FARMER', '13800000004', '河北省', 1);

INSERT INTO crop_category (name, parent_id, sort, description) VALUES
('粮食作物', 0, 1, '主要粮食作物'),
('经济作物', 0, 2, '经济类作物'),
('蔬菜', 0, 3, '蔬菜类作物'),
('水果', 0, 4, '水果类作物'),
('水稻', 1, 1, '水稻品种'),
('小麦', 1, 2, '小麦品种'),
('玉米', 1, 3, '玉米品种'),
('棉花', 2, 1, '棉花品种'),
('油菜', 2, 2, '油菜品种'),
('叶菜类', 3, 1, '叶菜类蔬菜'),
('果菜类', 3, 2, '果菜类蔬菜'),
('苹果', 4, 1, '苹果品种'),
('柑橘', 4, 2, '柑橘品种');

INSERT INTO crop (name, category_id, variety, growth_cycle, suitable_temp, suitable_soil, planting_season, yield_per_mu, description, status) VALUES
('杂交水稻', 5, '两优培九', 120, '20-30℃', '水田土壤', '4-5月', 600.00, '高产杂交水稻品种，抗病性强', 1),
('冬小麦', 6, '济麦22', 240, '10-25℃', '壤土', '10月', 500.00, '优质冬小麦品种，适合北方种植', 1),
('春玉米', 7, '郑单958', 100, '25-32℃', '壤土或沙壤土', '4-5月', 700.00, '高产春玉米品种', 1),
('大白菜', 10, '北京新3号', 70, '15-20℃', '肥沃壤土', '8-9月', 5000.00, '优质大白菜品种', 1),
('番茄', 11, '金棚一号', 90, '20-28℃', '富含有机质土壤', '3-4月', 4000.00, '设施栽培番茄品种', 1),
('红富士苹果', 12, '红富士', 365, '15-25℃', '沙壤土', '3-4月', 2500.00, '优质红富士苹果品种', 1);

INSERT INTO knowledge (title, category, content, tags, author_id, view_count, like_count, status) VALUES
('水稻高产栽培技术要点', '种植技术', '水稻高产栽培需要注意以下几点：1.选择优良品种；2.适时播种；3.合理密植；4.科学施肥；5.水分管理；6.病虫害防治。', '水稻,高产,栽培技术', 2, 256, 45, 1),
('小麦病虫害综合防治技术', '病虫害防治', '小麦主要病虫害包括条锈病、白粉病、蚜虫等，应采取农业防治、生物防治和化学防治相结合的综合防治措施。', '小麦,病虫害,防治', 2, 189, 32, 1),
('玉米合理施肥技术', '施肥技术', '玉米施肥应遵循"基肥为主、追肥为辅"的原则，重施底肥，巧施追肥，根据土壤养分含量和目标产量确定施肥量。', '玉米,施肥,技术', 3, 167, 28, 1),
('蔬菜节水灌溉技术', '灌溉技术', '蔬菜节水灌溉可采用滴灌、喷灌等方式，既能节约用水，又能提高水分利用效率，减少病虫害发生。', '蔬菜,灌溉,节水', 3, 134, 21, 1);

INSERT INTO production_plan (plan_name, crop_id, area, expected_yield, start_date, end_date, status, progress, creator_id, remark) VALUES
('2024年春季水稻种植计划', 1, 100.00, 60000.00, '2024-04-01', '2024-09-30', 1, 65, 4, '主要种植杂交水稻'),
('2024年冬小麦种植计划', 2, 80.00, 40000.00, '2023-10-15', '2024-06-15', 2, 100, 4, '选用济麦22品种'),
('2024年春玉米种植计划', 3, 50.00, 35000.00, '2024-04-15', '2024-08-30', 0, 0, 4, '计划种植春玉米');

INSERT INTO production_task (plan_id, task_name, task_type, assignee_id, start_date, end_date, status, remark) VALUES
(1, '水稻育秧', '播种', 4, '2024-04-01', '2024-04-15', 2, '完成育秧工作'),
(1, '水稻插秧', '播种', 4, '2024-05-01', '2024-05-15', 2, '完成插秧工作'),
(1, '第一次追肥', '施肥', 4, '2024-06-01', '2024-06-10', 2, '追施尿素'),
(1, '病虫害防治', '病虫害防治', 4, '2024-06-15', '2024-07-15', 1, '防治稻飞虱'),
(1, '水稻收割', '采收', 4, '2024-09-15', '2024-09-30', 0, '计划收割');

INSERT INTO pest_disease (name, type, crop_type, symptom, harm, prevention, treatment, status) VALUES
('稻瘟病', 1, '水稻', '叶片出现褐色病斑，严重时叶片枯死', '可造成水稻减产20%-50%', '选用抗病品种，合理施肥，避免偏施氮肥', '发病初期喷施三环唑、稻瘟灵等药剂', 1),
('小麦条锈病', 1, '小麦', '叶片出现黄色条状病斑', '可造成小麦减产10%-30%', '选用抗病品种，适期播种', '发病初期喷施三唑酮、戊唑醇等药剂', 1),
('玉米螟', 2, '玉米', '幼虫蛀食茎秆，造成植株倒伏', '可造成玉米减产10%-20%', '清除田间残株，减少虫源', '在卵孵化盛期喷施高效氯氰菊酯等药剂', 1),
('蚜虫', 2, '蔬菜', '群集于嫩叶、嫩茎吸食汁液', '影响植株生长，传播病毒病', '保护利用天敌，清除杂草', '喷施吡虫啉、啶虫脒等药剂', 1);

INSERT INTO pest_warning (title, pest_disease_id, region, crop_type, warning_level, warning_date, content, publisher_id, status) VALUES
('水稻稻瘟病预警', 1, '江南地区', '水稻', 2, '2024-06-15', '近期气温偏高、湿度大，有利于稻瘟病发生，请各地加强监测，及时防治。', 2, 1),
('小麦条锈病预警', 2, '黄淮地区', '小麦', 3, '2024-04-10', '小麦条锈病在部分地区发生较重，请抓紧时间进行药剂防治。', 2, 1);

INSERT INTO agricultural_material (name, category, specification, unit, stock, warning_stock, unit_price, supplier, description, status) VALUES
('尿素', '肥料', '含氮46%', '袋', 500, 100, 120.00, '中化化肥', '高浓度氮肥', 1),
('复合肥', '肥料', '15-15-15', '袋', 300, 50, 180.00, '金正大', '平衡型复合肥', 1),
('三唑酮', '农药', '15%可湿性粉剂', '袋', 200, 30, 25.00, '先正达', '杀菌剂，防治小麦锈病', 1),
('吡虫啉', '农药', '10%可湿性粉剂', '袋', 150, 20, 35.00, '拜耳', '杀虫剂，防治蚜虫', 1),
('杂交水稻种子', '种子', '两优培九', '公斤', 100, 20, 80.00, '隆平高科', '优质杂交稻种', 1),
('锄头', '农具', '普通型', '把', 50, 10, 45.00, '本地农具厂', '除草农具', 1);

INSERT INTO consultation (title, content, crop_type, tags, questioner_id, status, view_count) VALUES
('水稻叶片发黄是什么原因？', '我家的水稻最近叶片开始发黄，不知道是什么原因，请专家帮忙诊断一下。', '水稻', '水稻,叶片发黄,诊断', 4, 1, 56),
('番茄如何提高坐果率？', '大棚种植的番茄坐果率不高，请问有什么好的方法可以提高坐果率？', '番茄', '番茄,坐果率,大棚', 4, 0, 34);

INSERT INTO consultation_answer (consultation_id, content, answerer_id, is_adopted, like_count) VALUES
(1, '水稻叶片发黄可能有以下原因：1.缺氮，建议追施氮肥；2.缺钾，补充钾肥；3.根系受损，检查是否有病虫害；4.水分过多或过少。建议先检查根系情况，再对症处理。', 2, 1, 12);

INSERT INTO expert_appointment (expert_id, farmer_id, appointment_date, appointment_time, topic, description, status, rating, feedback) VALUES
(2, 4, '2024-06-20', '上午9:00-10:00', '水稻病虫害防治咨询', '想请专家指导水稻病虫害综合防治技术', 2, 5, '专家讲解非常详细，很有帮助'),
(2, 4, '2024-06-25', '下午14:00-15:00', '玉米施肥技术咨询', '想了解玉米施肥的最佳时期和方法', 1, NULL, NULL);

INSERT INTO notice (title, content, type, publisher_id, status) VALUES
('关于加强夏季农业生产技术指导的通知', '当前正值夏季农业生产关键时期，各地要加强技术指导服务，确保农业生产顺利进行。', '政策通知', 1, 1),
('新型农业技术推广培训班招生通知', '为提高农业生产技术水平，拟于7月份举办新型农业技术推广培训班，欢迎各位农户报名参加。', '技术推广', 1, 1),
('系统维护公告', '系统将于本周六晚间进行升级维护，届时可能无法正常访问，敬请谅解。', '系统公告', 1, 1);
