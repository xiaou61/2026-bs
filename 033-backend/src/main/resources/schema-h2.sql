CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(200) NOT NULL,
    real_name VARCHAR(50),
    phone VARCHAR(20),
    email VARCHAR(100),
    avatar VARCHAR(500),
    role VARCHAR(20) NOT NULL DEFAULT 'CUSTOMER',
    status TINYINT NOT NULL DEFAULT 1,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT NOT NULL DEFAULT 0
);

CREATE TABLE customer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    name VARCHAR(50) NOT NULL,
    gender TINYINT,
    age INT,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(100),
    address VARCHAR(200),
    customer_type VARCHAR(20),
    source VARCHAR(50),
    tags VARCHAR(500),
    birthday DATE,
    remark TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT NOT NULL DEFAULT 0
);

CREATE TABLE customer_follow_up (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    follow_user_id BIGINT NOT NULL,
    follow_type VARCHAR(20),
    content TEXT,
    next_follow_time TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE package (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    description TEXT,
    content TEXT,
    costume_count INT,
    refined_photo_count INT,
    shooting_duration INT,
    makeup_count INT,
    scene_count INT,
    gift TEXT,
    cover_image VARCHAR(500),
    images TEXT,
    status TINYINT NOT NULL DEFAULT 1,
    sort_order INT DEFAULT 0,
    sales_count INT DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT NOT NULL DEFAULT 0
);

CREATE TABLE photographer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    name VARCHAR(50) NOT NULL,
    phone VARCHAR(20),
    photo VARCHAR(500),
    level VARCHAR(20),
    specialty TEXT,
    portfolio TEXT,
    introduction TEXT,
    status TINYINT NOT NULL DEFAULT 1,
    commission_rate DECIMAL(5, 2),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT NOT NULL DEFAULT 0
);

CREATE TABLE photographer_schedule (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    photographer_id BIGINT NOT NULL,
    work_date DATE NOT NULL,
    time_slot VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE studio (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    area DECIMAL(10, 2),
    style VARCHAR(50),
    equipment TEXT,
    description TEXT,
    images TEXT,
    status TINYINT NOT NULL DEFAULT 1,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT NOT NULL DEFAULT 0
);

CREATE TABLE studio_booking (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    studio_id BIGINT NOT NULL,
    booking_date DATE NOT NULL,
    time_slot VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE',
    order_id BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE costume (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(50),
    size VARCHAR(20),
    color VARCHAR(50),
    images TEXT,
    status VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE',
    purchase_date DATE,
    purchase_price DECIMAL(10, 2),
    remark TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT NOT NULL DEFAULT 0
);

CREATE TABLE costume_borrow (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    costume_id BIGINT NOT NULL,
    order_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    borrow_date TIMESTAMP NOT NULL,
    return_date TIMESTAMP,
    status VARCHAR(20) NOT NULL DEFAULT 'BORROWED',
    remark TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE prop (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(50),
    quantity INT NOT NULL DEFAULT 1,
    status VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE',
    images TEXT,
    remark TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT NOT NULL DEFAULT 0
);

CREATE TABLE appointment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    appointment_type VARCHAR(20) NOT NULL,
    package_id BIGINT,
    photographer_id BIGINT,
    studio_id BIGINT,
    appointment_date DATE NOT NULL,
    time_slot VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    remark TEXT,
    created_by BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `order` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_no VARCHAR(50) NOT NULL UNIQUE,
    customer_id BIGINT NOT NULL,
    package_id BIGINT NOT NULL,
    photographer_id BIGINT,
    appointment_id BIGINT,
    total_amount DECIMAL(10, 2) NOT NULL,
    deposit_amount DECIMAL(10, 2),
    balance_amount DECIMAL(10, 2),
    paid_amount DECIMAL(10, 2) NOT NULL DEFAULT 0,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    payment_method VARCHAR(20),
    shooting_date DATE,
    photo_selection_date DATE,
    pickup_date DATE,
    remark TEXT,
    created_by BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT NOT NULL DEFAULT 0
);

CREATE TABLE photo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    photographer_id BIGINT,
    file_name VARCHAR(200) NOT NULL,
    file_path VARCHAR(500) NOT NULL,
    file_size BIGINT,
    file_type VARCHAR(50),
    is_selected TINYINT NOT NULL DEFAULT 0,
    is_refined TINYINT NOT NULL DEFAULT 0,
    refined_status VARCHAR(20),
    upload_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT NOT NULL DEFAULT 0
);

CREATE TABLE photo_selection (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    photo_id BIGINT NOT NULL,
    order_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    selected_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE financial_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    record_type VARCHAR(20) NOT NULL,
    order_id BIGINT,
    amount DECIMAL(10, 2) NOT NULL,
    payment_method VARCHAR(20),
    category VARCHAR(50),
    description TEXT,
    record_date DATE NOT NULL,
    created_by BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT NOT NULL DEFAULT 0
);

CREATE TABLE operation_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    username VARCHAR(50),
    operation VARCHAR(100),
    method VARCHAR(200),
    params TEXT,
    ip VARCHAR(50),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE system_config (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    config_key VARCHAR(100) NOT NULL UNIQUE,
    config_value TEXT,
    config_type VARCHAR(20),
    description VARCHAR(200),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
