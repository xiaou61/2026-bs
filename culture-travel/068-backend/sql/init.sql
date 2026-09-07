DROP DATABASE IF EXISTS nearby_travel_068;
CREATE DATABASE nearby_travel_068 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE nearby_travel_068;

DROP TABLE IF EXISTS user_complaint;
DROP TABLE IF EXISTS travel_review;
DROP TABLE IF EXISTS travel_order;
DROP TABLE IF EXISTS user_favorite;
DROP TABLE IF EXISTS traveler;
DROP TABLE IF EXISTS scenic_spot;
DROP TABLE IF EXISTS sys_user;

CREATE TABLE sys_user (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  nickname VARCHAR(50) NOT NULL,
  phone VARCHAR(20) DEFAULT '',
  email VARCHAR(100) DEFAULT '',
  avatar VARCHAR(255) DEFAULT '',
  profile VARCHAR(500) DEFAULT '',
  role VARCHAR(20) NOT NULL,
  status TINYINT NOT NULL DEFAULT 1,
  last_login_time DATETIME NULL,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE scenic_spot (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  city VARCHAR(50) NOT NULL,
  tags VARCHAR(255) DEFAULT '',
  price DECIMAL(10,2) NOT NULL DEFAULT 0,
  cover LONGTEXT,
  intro VARCHAR(1000) DEFAULT '',
  status TINYINT NOT NULL DEFAULT 1,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE traveler (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  name VARCHAR(50) NOT NULL,
  cert_type VARCHAR(20) NOT NULL DEFAULT '身份证',
  cert_no VARCHAR(50) NOT NULL,
  phone VARCHAR(20) NOT NULL,
  is_default TINYINT NOT NULL DEFAULT 0,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  KEY idx_traveler_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE user_favorite (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  spot_id BIGINT NOT NULL,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY uk_user_spot (user_id, spot_id),
  KEY idx_favorite_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE travel_order (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  order_no VARCHAR(40) NOT NULL,
  user_id BIGINT NOT NULL,
  traveler_id BIGINT NOT NULL,
  spot_id BIGINT NOT NULL,
  travel_date DATE NOT NULL,
  quantity INT NOT NULL DEFAULT 1,
  total_amount DECIMAL(10,2) NOT NULL DEFAULT 0,
  status VARCHAR(20) NOT NULL,
  contact_name VARCHAR(50) NOT NULL,
  contact_phone VARCHAR(20) NOT NULL,
  remark VARCHAR(500) DEFAULT '',
  pay_time DATETIME NULL,
  finish_time DATETIME NULL,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_order_no (order_no),
  KEY idx_order_user_id (user_id),
  KEY idx_order_spot_id (spot_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE travel_review (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  order_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  spot_id BIGINT NOT NULL,
  score TINYINT NOT NULL,
  content VARCHAR(1000) NOT NULL,
  status TINYINT NOT NULL DEFAULT 1,
  reply_content VARCHAR(1000) DEFAULT '',
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_review_order_id (order_id),
  KEY idx_review_spot_id (spot_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE user_complaint (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  order_id BIGINT NOT NULL,
  type VARCHAR(50) NOT NULL,
  content VARCHAR(1000) NOT NULL,
  attachment_urls LONGTEXT,
  status VARCHAR(20) NOT NULL DEFAULT 'WAITING',
  handle_result VARCHAR(1000) DEFAULT '',
  handle_by BIGINT NULL,
  handle_time DATETIME NULL,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  KEY idx_complaint_user_id (user_id),
  KEY idx_complaint_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO sys_user (id, username, password, nickname, phone, email, avatar, profile, role, status, last_login_time) VALUES
(1, 'admin', '123456', '平台管理员', '13800000000', 'admin@travel.com', '', '负责平台运营管理', 'ADMIN', 1, NOW()),
(2, 'user', '123456', '周边游用户', '13900000000', 'user@travel.com', '', '热爱周末出行', 'USER', 1, NOW());

INSERT INTO scenic_spot (id, name, city, tags, price, cover, intro, status) VALUES
(1, '云湖湿地公园', '杭州', '亲子,骑行,自然风光', 88.00, 'https://picsum.photos/seed/spot1/400/240', '城市近郊湿地生态游，适合周末半日出行。', 1),
(2, '青山古镇', '苏州', '古镇,美食,摄影', 68.00, 'https://picsum.photos/seed/spot2/400/240', '古街夜景和非遗手作体验结合。', 1),
(3, '松林露营地', '南京', '露营,团建,烧烤', 128.00, 'https://picsum.photos/seed/spot3/400/240', '配置完善的露营营地，支持拎包露营。', 1),
(4, '西坡温泉谷', '无锡', '温泉,康养,情侣', 188.00, 'https://picsum.photos/seed/spot4/400/240', '周边游热门温泉打卡地。', 1),
(5, '花海田园小镇', '常州', '赏花,拍照,亲子', 99.00, 'https://picsum.photos/seed/spot5/400/240', '四季花田与乡野研学体验。', 1),
(6, '山野徒步线', '宁波', '徒步,轻运动,自然', 59.00, 'https://picsum.photos/seed/spot6/400/240', '新手友好的轻徒步路线。', 1);

INSERT INTO traveler (id, user_id, name, cert_type, cert_no, phone, is_default) VALUES
(1, 2, '张三', '身份证', '330102199901010011', '13900000000', 1),
(2, 2, '李四', '身份证', '330102199902020022', '13700000000', 0);

INSERT INTO user_favorite (id, user_id, spot_id) VALUES
(1, 2, 1),
(2, 2, 3);

INSERT INTO travel_order (id, order_no, user_id, traveler_id, spot_id, travel_date, quantity, total_amount, status, contact_name, contact_phone, remark, pay_time, finish_time, create_time, update_time) VALUES
(1, 'TR202602140001', 2, 1, 1, '2026-02-20', 2, 176.00, 'WAIT_PAY', '张三', '13900000000', '周末上午出发', NULL, NULL, NOW(), NOW()),
(2, 'TR202602140002', 2, 2, 3, '2026-02-08', 1, 128.00, 'FINISHED', '李四', '13700000000', '已完成露营', NOW(), NOW(), NOW(), NOW()),
(3, 'TR202602140003', 2, 1, 4, '2026-02-12', 1, 188.00, 'PAID', '张三', '13900000000', '待出行', NOW(), NULL, NOW(), NOW());

INSERT INTO travel_review (id, order_id, user_id, spot_id, score, content, status, reply_content, create_time, update_time) VALUES
(1, 2, 2, 3, 5, '营地环境非常好，服务人员也很专业。', 1, '感谢反馈，欢迎再次预订。', NOW(), NOW());

INSERT INTO user_complaint (id, user_id, order_id, type, content, attachment_urls, status, handle_result, handle_by, handle_time, create_time, update_time) VALUES
(1, 2, 3, '行程变更', '希望支持更灵活的改签时间。', '[]', 'WAITING', '', NULL, NULL, NOW(), NOW());
