CREATE TABLE IF NOT EXISTS user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(64) NOT NULL UNIQUE,
    password VARCHAR(128) NOT NULL,
    phone VARCHAR(32),
    nickname VARCHAR(64),
    role TINYINT NOT NULL DEFAULT 1,
    status TINYINT NOT NULL DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS farm_plot (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    area DECIMAL(10, 2) NOT NULL,
    crop_variety VARCHAR(100),
    growth_stage VARCHAR(100),
    location VARCHAR(255),
    latitude DOUBLE,
    longitude DOUBLE,
    photos CLOB,
    remark VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS machine (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    owner_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    model VARCHAR(100),
    width DECIMAL(10, 2),
    power DECIMAL(10, 2),
    price_per_hour DECIMAL(12, 2),
    price_per_mu DECIMAL(12, 2),
    service_radius_km INT,
    status TINYINT DEFAULT 1,
    remark VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS booking (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    farmer_id BIGINT NOT NULL,
    machine_id BIGINT,
    plot_id BIGINT NOT NULL,
    expect_date DATE,
    time_window VARCHAR(50),
    area DECIMAL(10, 2),
    status VARCHAR(20) DEFAULT 'PENDING',
    quote_amount DECIMAL(12, 2),
    settle_amount DECIMAL(12, 2),
    address VARCHAR(255),
    latitude DOUBLE,
    longitude DOUBLE,
    remark VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS booking_status_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    booking_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL,
    note VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS dispatch_task (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    booking_id BIGINT NOT NULL,
    machine_id BIGINT,
    driver_id BIGINT,
    assigned_by BIGINT,
    status VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS work_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    booking_id BIGINT NOT NULL,
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    real_area DECIMAL(10, 2),
    photos CLOB,
    note VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS review (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    booking_id BIGINT NOT NULL,
    rating TINYINT,
    content VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS notice (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content CLOB,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);

CREATE INDEX IF NOT EXISTS idx_plot_user ON farm_plot(user_id);
CREATE INDEX IF NOT EXISTS idx_machine_owner ON machine(owner_id);
CREATE INDEX IF NOT EXISTS idx_booking_farmer ON booking(farmer_id);
CREATE INDEX IF NOT EXISTS idx_booking_plot ON booking(plot_id);
CREATE INDEX IF NOT EXISTS idx_bsl_booking ON booking_status_log(booking_id);
CREATE INDEX IF NOT EXISTS idx_dispatch_booking ON dispatch_task(booking_id);
CREATE INDEX IF NOT EXISTS idx_work_booking ON work_record(booking_id);
CREATE INDEX IF NOT EXISTS idx_review_booking ON review(booking_id);
