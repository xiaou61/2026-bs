CREATE TABLE sys_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    real_name VARCHAR(50),
    phone VARCHAR(20),
    avatar VARCHAR(255),
    role TINYINT DEFAULT 0,
    status TINYINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);

CREATE TABLE shop (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    owner_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    logo VARCHAR(255),
    description CLOB,
    address VARCHAR(255),
    phone VARCHAR(20),
    business_hours VARCHAR(100),
    room_count INT DEFAULT 0,
    rating DECIMAL(2,1) DEFAULT 5.0,
    status TINYINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);

CREATE TABLE room (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    shop_id BIGINT NOT NULL,
    name VARCHAR(50) NOT NULL,
    capacity INT NOT NULL,
    description VARCHAR(255),
    price DECIMAL(10,2) DEFAULT 0,
    status TINYINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);

CREATE TABLE script_category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    sort INT DEFAULT 0,
    status TINYINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);

CREATE TABLE script (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    author_id BIGINT,
    category_id BIGINT,
    title VARCHAR(100) NOT NULL,
    cover VARCHAR(255),
    description CLOB,
    difficulty TINYINT DEFAULT 2,
    player_count VARCHAR(20),
    duration VARCHAR(50),
    type TINYINT DEFAULT 1,
    price DECIMAL(10,2) DEFAULT 0,
    view_count INT DEFAULT 0,
    like_count INT DEFAULT 0,
    status TINYINT DEFAULT 0,
    audit_remark VARCHAR(255),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);

CREATE TABLE script_content (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    script_id BIGINT NOT NULL,
    chapter_name VARCHAR(100),
    role_name VARCHAR(50),
    content CLOB,
    sort INT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);

CREATE TABLE shop_script (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    shop_id BIGINT NOT NULL,
    script_id BIGINT NOT NULL,
    price DECIMAL(10,2),
    play_count INT DEFAULT 0,
    status TINYINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);

CREATE TABLE reservation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_no VARCHAR(32) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    shop_id BIGINT NOT NULL,
    room_id BIGINT,
    script_id BIGINT,
    reservation_date DATE NOT NULL,
    time_slot VARCHAR(50),
    player_count INT,
    contact_name VARCHAR(50),
    contact_phone VARCHAR(20),
    total_price DECIMAL(10,2),
    remark VARCHAR(255),
    status TINYINT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);

CREATE TABLE review (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    reservation_id BIGINT,
    shop_id BIGINT,
    script_id BIGINT,
    rating INT DEFAULT 5,
    content CLOB,
    images VARCHAR(1000),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);

CREATE TABLE favorite (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    type TINYINT NOT NULL,
    target_id BIGINT NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);

CREATE TABLE notice (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    content CLOB,
    type TINYINT DEFAULT 1,
    publish_time TIMESTAMP,
    status TINYINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);
