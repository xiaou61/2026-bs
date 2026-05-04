DROP TABLE IF EXISTS review_record;
DROP TABLE IF EXISTS payment_record;
DROP TABLE IF EXISTS consume_order;
DROP TABLE IF EXISTS reservation_record;
DROP TABLE IF EXISTS seat_info;
DROP TABLE IF EXISTS menu_item;
DROP TABLE IF EXISTS menu_category;
DROP TABLE IF EXISTS resident_pet;
DROP TABLE IF EXISTS cafe_shop;
DROP TABLE IF EXISTS cafe_area;
DROP TABLE IF EXISTS system_notice;
DROP TABLE IF EXISTS sys_user;

CREATE TABLE sys_user (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
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
  last_login_time TIMESTAMP,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE (user_no),
  UNIQUE (username)
);

CREATE TABLE cafe_area (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  area_no VARCHAR(50) NOT NULL,
  name VARCHAR(100) NOT NULL,
  city VARCHAR(50),
  district VARCHAR(50),
  address VARCHAR(255),
  contact_person VARCHAR(50),
  contact_phone VARCHAR(20),
  status INT DEFAULT 1,
  remark VARCHAR(255),
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE (area_no)
);

CREATE TABLE cafe_shop (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
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
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE (shop_no)
);

CREATE TABLE resident_pet (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
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
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE (pet_no)
);

CREATE TABLE menu_category (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  sort INT DEFAULT 0,
  status INT DEFAULT 1,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE menu_item (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
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
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE (menu_no)
);

CREATE TABLE seat_info (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  shop_id BIGINT NOT NULL,
  seat_no VARCHAR(50) NOT NULL,
  zone_name VARCHAR(50),
  capacity INT DEFAULT 1,
  min_consume DECIMAL(10,2) DEFAULT 0.00,
  seat_status VARCHAR(20) DEFAULT 'NORMAL',
  reservation_status VARCHAR(20) DEFAULT 'AVAILABLE',
  remark VARCHAR(255),
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE reservation_record (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
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
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE (reservation_no)
);

CREATE TABLE consume_order (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
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
  pay_time TIMESTAMP,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE (order_no)
);

CREATE TABLE payment_record (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  order_id BIGINT,
  pay_no VARCHAR(50) NOT NULL,
  pay_type VARCHAR(20) DEFAULT 'BALANCE',
  pay_amount DECIMAL(10,2) DEFAULT 0.00,
  status VARCHAR(20) DEFAULT 'SUCCESS',
  pay_time TIMESTAMP,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE (pay_no)
);

CREATE TABLE review_record (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  order_id BIGINT NOT NULL,
  shop_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  rating INT DEFAULT 5,
  content VARCHAR(500),
  reply_user_id BIGINT,
  reply_content VARCHAR(500),
  review_status VARCHAR(20) DEFAULT 'WAIT_REPLY',
  review_time TIMESTAMP,
  reply_time TIMESTAMP,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE system_notice (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(100) NOT NULL,
  content CLOB,
  publisher_id BIGINT NOT NULL,
  notice_type VARCHAR(50) DEFAULT 'SYSTEM',
  status INT DEFAULT 1,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
