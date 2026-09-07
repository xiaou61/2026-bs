CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50),
    phone VARCHAR(20) UNIQUE,
    email VARCHAR(100),
    avatar VARCHAR(255),
    balance DECIMAL(10,2) DEFAULT 0,
    role VARCHAR(20) DEFAULT 'USER',
    status TINYINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE station (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    station_code VARCHAR(20) UNIQUE NOT NULL,
    station_name VARCHAR(50) UNIQUE NOT NULL,
    city VARCHAR(50),
    province VARCHAR(50),
    address VARCHAR(255),
    status TINYINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE train_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    train_code VARCHAR(20) UNIQUE NOT NULL,
    train_name VARCHAR(100) NOT NULL,
    train_type VARCHAR(30) NOT NULL,
    carriage_count INT DEFAULT 1,
    seat_count INT DEFAULT 0,
    cover_url VARCHAR(255),
    description CLOB,
    status TINYINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE carriage_template (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    template_name VARCHAR(50) UNIQUE NOT NULL,
    seat_type VARCHAR(30) NOT NULL,
    coach_count INT DEFAULT 1,
    seat_rows INT DEFAULT 10,
    seat_cols INT DEFAULT 5,
    price_factor DECIMAL(10,2) DEFAULT 1.00,
    status TINYINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE train_schedule (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    train_id BIGINT NOT NULL,
    carriage_id BIGINT NOT NULL,
    departure_station_id BIGINT NOT NULL,
    arrival_station_id BIGINT NOT NULL,
    travel_date DATE NOT NULL,
    departure_time TIMESTAMP NOT NULL,
    arrival_time TIMESTAMP NOT NULL,
    base_price DECIMAL(10,2) NOT NULL,
    total_seats INT DEFAULT 0,
    available_seats INT DEFAULT 0,
    sale_status VARCHAR(30) DEFAULT 'ON_SALE',
    status TINYINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE schedule_seat (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    schedule_id BIGINT NOT NULL,
    coach_no INT NOT NULL,
    row_num INT NOT NULL,
    col_num INT NOT NULL,
    seat_no VARCHAR(10) NOT NULL,
    seat_type VARCHAR(30),
    price DECIMAL(10,2),
    status VARCHAR(30) DEFAULT 'AVAILABLE',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE passenger_profile (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    passenger_name VARCHAR(50) NOT NULL,
    id_card VARCHAR(30) UNIQUE NOT NULL,
    phone VARCHAR(20),
    passenger_type VARCHAR(20) DEFAULT 'ADULT',
    is_default TINYINT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE ticket_order (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_no VARCHAR(50) UNIQUE NOT NULL,
    user_id BIGINT NOT NULL,
    schedule_id BIGINT NOT NULL,
    train_code VARCHAR(20),
    departure_station VARCHAR(50),
    arrival_station VARCHAR(50),
    departure_time TIMESTAMP,
    arrival_time TIMESTAMP,
    seat_info CLOB,
    passenger_names VARCHAR(255),
    passenger_info CLOB,
    contact_phone VARCHAR(20),
    total_amount DECIMAL(10,2),
    pay_amount DECIMAL(10,2),
    status VARCHAR(30) DEFAULT 'WAIT_PAY',
    pay_time TIMESTAMP,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE train_ticket (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    ticket_no VARCHAR(50) UNIQUE NOT NULL,
    passenger_name VARCHAR(50),
    id_card VARCHAR(30),
    seat_label VARCHAR(50),
    qr_code VARCHAR(100),
    status VARCHAR(30) DEFAULT 'UNUSED',
    verify_time TIMESTAMP,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE payment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT,
    pay_no VARCHAR(50) UNIQUE NOT NULL,
    pay_type VARCHAR(30),
    pay_amount DECIMAL(10,2),
    status VARCHAR(30) DEFAULT 'WAIT',
    pay_time TIMESTAMP,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE after_sale_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    service_type VARCHAR(20) NOT NULL,
    target_schedule_id BIGINT,
    target_schedule_info VARCHAR(255),
    reason VARCHAR(255),
    review_status VARCHAR(20) DEFAULT 'PENDING',
    review_remark VARCHAR(255),
    review_time TIMESTAMP,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE system_notice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    content CLOB NOT NULL,
    publish_status TINYINT DEFAULT 1,
    sort_no INT DEFAULT 0,
    publish_time TIMESTAMP,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
