DROP DATABASE IF EXISTS pet_cafe_platform_094;
CREATE DATABASE pet_cafe_platform_094 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE pet_cafe_platform_094;

CREATE TABLE sys_user (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_no VARCHAR(50) NOT NULL,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  nickname VARCHAR(50) NOT NULL,
  phone VARCHAR(20),
  email VARCHAR(100),
  avatar VARCHAR(255),
  balance DECIMAL(10,2) DEFAULT 0.00,
  total_consume DECIMAL(10,2) DEFAULT 0.00,
  role VARCHAR(20) NOT NULL,
  status INT DEFAULT 1,
  last_login_time DATETIME,
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_user_no (user_no),
  UNIQUE KEY uk_username (username)
);

CREATE TABLE cafe_area (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  area_no VARCHAR(50) NOT NULL,
  name VARCHAR(100) NOT NULL,
  city VARCHAR(50),
  district VARCHAR(50),
  address VARCHAR(255),
  contact_person VARCHAR(50),
  contact_phone VARCHAR(20),
  status INT DEFAULT 1,
  remark VARCHAR(255),
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_area_no (area_no)
);

CREATE TABLE cafe_shop (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  shop_no VARCHAR(50) NOT NULL,
  area_id BIGINT NOT NULL,
  name VARCHAR(100) NOT NULL,
  theme VARCHAR(100),
  open_time VARCHAR(20),
  close_time VARCHAR(20),
  status VARCHAR(20) DEFAULT 'OPEN',
  cover VARCHAR(255),
  manager_name VARCHAR(50),
  manager_phone VARCHAR(20),
  score INT DEFAULT 5,
  per_capita DECIMAL(10,2) DEFAULT 0.00,
  remark VARCHAR(255),
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_shop_no (shop_no)
);

CREATE TABLE resident_pet (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  pet_no VARCHAR(50) NOT NULL,
  shop_id BIGINT NOT NULL,
  name VARCHAR(50) NOT NULL,
  pet_type VARCHAR(20),
  breed VARCHAR(50),
  gender VARCHAR(20),
  age INT,
  personality VARCHAR(100),
  health_status VARCHAR(100),
  star_level INT DEFAULT 5,
  interaction_status VARCHAR(20) DEFAULT 'OPEN',
  avatar VARCHAR(255),
  introduction VARCHAR(500),
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_pet_no (pet_no)
);

CREATE TABLE menu_category (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  sort INT DEFAULT 0,
  status INT DEFAULT 1,
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE menu_item (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  menu_no VARCHAR(50) NOT NULL,
  name VARCHAR(100) NOT NULL,
  category_id BIGINT NOT NULL,
  taste VARCHAR(100),
  cover VARCHAR(255),
  price DECIMAL(10,2) DEFAULT 0.00,
  stock INT DEFAULT 0,
  recommend_flag INT DEFAULT 0,
  status INT DEFAULT 1,
  description VARCHAR(500),
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_menu_no (menu_no)
);

CREATE TABLE seat_info (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  shop_id BIGINT NOT NULL,
  seat_no VARCHAR(50) NOT NULL,
  zone_name VARCHAR(50),
  capacity INT DEFAULT 1,
  min_consume DECIMAL(10,2) DEFAULT 0.00,
  seat_status VARCHAR(20) DEFAULT 'NORMAL',
  reservation_status VARCHAR(20) DEFAULT 'AVAILABLE',
  remark VARCHAR(255),
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE reservation_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  reservation_no VARCHAR(50) NOT NULL,
  user_id BIGINT NOT NULL,
  shop_id BIGINT NOT NULL,
  seat_id BIGINT NOT NULL,
  customer_name VARCHAR(50),
  customer_phone VARCHAR(20),
  visit_date DATE,
  visit_time VARCHAR(30),
  people_count INT DEFAULT 1,
  pet_companion VARCHAR(100),
  remark VARCHAR(255),
  status VARCHAR(20) DEFAULT 'CONFIRMED',
  manager_remark VARCHAR(255),
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_reservation_no (reservation_no)
);

CREATE TABLE consume_order (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  order_no VARCHAR(50) NOT NULL,
  user_id BIGINT NOT NULL,
  shop_id BIGINT NOT NULL,
  menu_id BIGINT NOT NULL,
  quantity INT DEFAULT 1,
  dine_type VARCHAR(20) DEFAULT 'IN_STORE',
  total_amount DECIMAL(10,2) DEFAULT 0.00,
  pay_amount DECIMAL(10,2) DEFAULT 0.00,
  status VARCHAR(20) DEFAULT 'WAIT_PAY',
  remark VARCHAR(255),
  pay_time DATETIME,
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_order_no (order_no)
);

CREATE TABLE payment_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  order_id BIGINT,
  pay_no VARCHAR(50) NOT NULL,
  pay_type VARCHAR(20) DEFAULT 'BALANCE',
  pay_amount DECIMAL(10,2) DEFAULT 0.00,
  status VARCHAR(20) DEFAULT 'SUCCESS',
  pay_time DATETIME,
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_pay_no (pay_no)
);

CREATE TABLE review_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  order_id BIGINT NOT NULL,
  shop_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  rating INT DEFAULT 5,
  content VARCHAR(500),
  reply_user_id BIGINT,
  reply_content VARCHAR(500),
  review_status VARCHAR(20) DEFAULT 'WAIT_REPLY',
  review_time DATETIME,
  reply_time DATETIME,
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE system_notice (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(100) NOT NULL,
  content TEXT,
  publisher_id BIGINT NOT NULL,
  notice_type VARCHAR(50) DEFAULT 'SYSTEM',
  status INT DEFAULT 1,
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO sys_user (id, user_no, username, password, nickname, phone, email, balance, total_consume, role, status, last_login_time) VALUES
(1, 'U202603170001', 'admin', '123456', '系统管理员', '13800000001', 'admin@petcafe.com', 2000.00, 0.00, 'ADMIN', 1, NOW()),
(2, 'U202603170002', 'staff', '123456', '店长小岚', '13800000002', 'staff@petcafe.com', 500.00, 0.00, 'STAFF', 1, NOW()),
(3, 'U202603170003', 'customer', '123456', '顾客小溪', '13800000003', 'customer@petcafe.com', 368.50, 131.50, 'CUSTOMER', 1, NOW());

INSERT INTO cafe_area (id, area_no, name, city, district, address, contact_person, contact_phone, status, remark) VALUES
(1, 'A202603170001', '松江大学城店群', '上海', '松江区', '文汇路宠物友好街区', '李晴', '13900000001', 1, '周末客流稳定'),
(2, 'A202603170002', '徐汇公园店群', '上海', '徐汇区', '漕溪北路宠物友好商业带', '王诺', '13900000002', 1, '适合露台主题活动');

INSERT INTO cafe_shop (id, shop_no, area_id, name, theme, open_time, close_time, status, cover, manager_name, manager_phone, score, per_capita, remark) VALUES
(1, 'S202603170001', 1, '喵语森林宠物咖啡馆', '森林猫咪主题', '10:00', '21:30', 'OPEN', 'https://example.com/cafe1.jpg', '林岚', '13811110001', 5, 68.00, '主打猫咪互动和下午茶'),
(2, 'S202603170002', 1, '汪星慢时光咖啡屋', '犬系陪伴主题', '09:30', '22:00', 'OPEN', 'https://example.com/cafe2.jpg', '周屿', '13811110002', 4, 72.00, '适合社交聚会和带宠到店'),
(3, 'S202603170003', 2, '爪印花园咖啡馆', '花园治愈主题', '10:30', '20:30', 'REST', 'https://example.com/cafe3.jpg', '陈澈', '13811110003', 5, 88.00, '本周进行主题布景调整');

INSERT INTO resident_pet (id, pet_no, shop_id, name, pet_type, breed, gender, age, personality, health_status, star_level, interaction_status, avatar, introduction) VALUES
(1, 'P202603170001', 1, '奶盖', 'CAT', '布偶猫', '母', 3, '黏人温柔', '已完成疫苗', 5, 'OPEN', 'https://example.com/pet1.jpg', '喜欢趴在窗边晒太阳，擅长陪拍照'),
(2, 'P202603170002', 1, '糯米', 'CAT', '银渐层', '公', 2, '活泼好奇', '健康良好', 4, 'OPEN', 'https://example.com/pet2.jpg', '最爱逗猫棒和零食互动'),
(3, 'P202603170003', 2, '可可', 'DOG', '柯基', '母', 4, '热情亲人', '已完成驱虫', 5, 'OPEN', 'https://example.com/pet3.jpg', '会主动迎接客人，适合互动打卡'),
(4, 'P202603170004', 3, '桃桃', 'RABBIT', '垂耳兔', '母', 1, '安静乖巧', '观察中', 4, 'REST', 'https://example.com/pet4.jpg', '露台区人气小明星');

INSERT INTO menu_category (id, name, sort, status) VALUES
(1, '招牌咖啡', 1, 1),
(2, '甜品小食', 2, 1),
(3, '宠物友好饮品', 3, 1);

INSERT INTO menu_item (id, menu_no, name, category_id, taste, cover, price, stock, recommend_flag, status, description) VALUES
(1, 'M202603170001', '榛果拿铁', 1, '香醇坚果', 'https://example.com/menu1.jpg', 28.00, 40, 1, 1, '门店高频复购饮品'),
(2, 'M202603170002', '海盐芝士美式', 1, '咸甜清爽', 'https://example.com/menu2.jpg', 26.00, 35, 1, 1, '适合夏季打卡'),
(3, 'M202603170003', '焦糖可颂', 2, '酥脆奶香', 'https://example.com/menu3.jpg', 18.00, 28, 0, 1, '适配下午茶套餐'),
(4, 'M202603170004', '草莓舒芙蕾', 2, '酸甜绵软', 'https://example.com/menu4.jpg', 32.00, 16, 1, 1, '拍照热门甜品'),
(5, 'M202603170005', '无糖酸奶杯', 3, '清新轻盈', 'https://example.com/menu5.jpg', 20.00, 24, 0, 1, '适合轻食用户'),
(6, 'M202603170006', '抹茶拿铁', 1, '茶香回甘', 'https://example.com/menu6.jpg', 27.00, 20, 0, 1, '门店日常推荐');

INSERT INTO seat_info (id, shop_id, seat_no, zone_name, capacity, min_consume, seat_status, reservation_status, remark) VALUES
(1, 1, 'A01', '落地窗区', 2, 68.00, 'NORMAL', 'AVAILABLE', '适合双人拍照'),
(2, 1, 'A02', '猫爬架区', 4, 128.00, 'NORMAL', 'BOOKED', '靠近互动区域'),
(3, 2, 'B01', '沙发区', 4, 138.00, 'NORMAL', 'AVAILABLE', '适合聚会'),
(4, 2, 'B02', '吧台区', 2, 58.00, 'NORMAL', 'AVAILABLE', '适合单人办公'),
(5, 3, 'C01', '花园区', 4, 168.00, 'DISABLED', 'AVAILABLE', '布景升级中'),
(6, 1, 'A03', '静谧阅读区', 2, 88.00, 'NORMAL', 'AVAILABLE', '适合安静约会');

INSERT INTO reservation_record (id, reservation_no, user_id, shop_id, seat_id, customer_name, customer_phone, visit_date, visit_time, people_count, pet_companion, remark, status, manager_remark, create_time, update_time) VALUES
(1, 'RSV202603170001', 3, 1, 2, '顾客小溪', '13800000003', CURDATE(), '14:00-15:30', 2, '无', '想靠近猫咪互动区', 'CONFIRMED', '已为您保留座位', DATE_SUB(NOW(), INTERVAL 4 HOUR), DATE_SUB(NOW(), INTERVAL 4 HOUR)),
(2, 'RSV202603170002', 3, 2, 3, '顾客小溪', '13800000003', DATE_ADD(CURDATE(), INTERVAL 1 DAY), '18:30-20:00', 3, '小型犬1只', '希望安排靠窗区域', 'CONFIRMED', '到店请提前10分钟', DATE_SUB(NOW(), INTERVAL 2 HOUR), DATE_SUB(NOW(), INTERVAL 2 HOUR)),
(3, 'RSV202603170003', 3, 1, 6, '顾客小溪', '13800000003', DATE_SUB(CURDATE(), INTERVAL 1 DAY), '16:00-17:00', 2, '无', '庆生小聚', 'COMPLETED', '已完成接待', DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY));

INSERT INTO consume_order (id, order_no, user_id, shop_id, menu_id, quantity, dine_type, total_amount, pay_amount, status, remark, pay_time, create_time, update_time) VALUES
(1, 'CO202603170001', 3, 1, 1, 2, 'IN_STORE', 56.00, 56.00, 'COMPLETED', '少糖', DATE_SUB(NOW(), INTERVAL 3 HOUR), DATE_SUB(NOW(), INTERVAL 3 HOUR), DATE_SUB(NOW(), INTERVAL 3 HOUR)),
(2, 'CO202603170002', 3, 1, 4, 1, 'IN_STORE', 32.00, 32.00, 'COMPLETED', '生日插牌', DATE_SUB(NOW(), INTERVAL 2 HOUR), DATE_SUB(NOW(), INTERVAL 2 HOUR), DATE_SUB(NOW(), INTERVAL 2 HOUR)),
(3, 'CO202603170003', 3, 2, 2, 1, 'TAKE_AWAY', 26.00, 26.00, 'WAIT_PAY', '去冰', NULL, DATE_SUB(NOW(), INTERVAL 20 MINUTE), DATE_SUB(NOW(), INTERVAL 20 MINUTE));

INSERT INTO payment_record (id, user_id, order_id, pay_no, pay_type, pay_amount, status, pay_time, create_time, update_time) VALUES
(1, 3, NULL, 'PAY202603170001', 'RECHARGE', 200.00, 'SUCCESS', DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY)),
(2, 3, 1, 'PAY202603170002', 'BALANCE', 56.00, 'SUCCESS', DATE_SUB(NOW(), INTERVAL 3 HOUR), DATE_SUB(NOW(), INTERVAL 3 HOUR), DATE_SUB(NOW(), INTERVAL 3 HOUR)),
(3, 3, 2, 'PAY202603170003', 'BALANCE', 32.00, 'SUCCESS', DATE_SUB(NOW(), INTERVAL 2 HOUR), DATE_SUB(NOW(), INTERVAL 2 HOUR), DATE_SUB(NOW(), INTERVAL 2 HOUR)),
(4, 1, NULL, 'PAY202603170004', 'RECHARGE', 2000.00, 'SUCCESS', DATE_SUB(NOW(), INTERVAL 3 DAY), DATE_SUB(NOW(), INTERVAL 3 DAY), DATE_SUB(NOW(), INTERVAL 3 DAY));

INSERT INTO review_record (id, order_id, shop_id, user_id, rating, content, reply_user_id, reply_content, review_status, review_time, reply_time, create_time, update_time) VALUES
(1, 1, 1, 3, 5, '店宠很亲人，榛果拿铁也很稳定，下次还会来。', 2, '感谢喜欢奶盖和我们的咖啡，欢迎下次再来。', 'REPLIED', DATE_SUB(NOW(), INTERVAL 150 MINUTE), DATE_SUB(NOW(), INTERVAL 100 MINUTE), DATE_SUB(NOW(), INTERVAL 150 MINUTE), DATE_SUB(NOW(), INTERVAL 100 MINUTE)),
(2, 2, 1, 3, 4, '舒芙蕾口感不错，希望周末能增加座位。', NULL, NULL, 'WAIT_REPLY', DATE_SUB(NOW(), INTERVAL 70 MINUTE), NULL, DATE_SUB(NOW(), INTERVAL 70 MINUTE), DATE_SUB(NOW(), INTERVAL 70 MINUTE));

INSERT INTO system_notice (id, title, content, publisher_id, notice_type, status, create_time, update_time) VALUES
(1, '周末宠物互动场加开', '本周六下午加开猫咪互动场，已预约用户可优先参与。', 1, 'ACTIVITY', 1, DATE_SUB(NOW(), INTERVAL 6 HOUR), DATE_SUB(NOW(), INTERVAL 6 HOUR)),
(2, '春季新品菜单上架', '门店新增海盐芝士美式和草莓舒芙蕾，欢迎到店打卡。', 1, 'SYSTEM', 1, DATE_SUB(NOW(), INTERVAL 2 HOUR), DATE_SUB(NOW(), INTERVAL 2 HOUR));
