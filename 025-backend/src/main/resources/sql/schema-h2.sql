CREATE TABLE IF NOT EXISTS user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'OWNER',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS owner (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    name VARCHAR(50) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    building VARCHAR(20),
    unit VARCHAR(20),
    room VARCHAR(20),
    check_in_time DATE,
    CONSTRAINT fk_owner_user FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE IF NOT EXISTS fee (
    id INT AUTO_INCREMENT PRIMARY KEY,
    owner_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    type VARCHAR(50) NOT NULL,
    status VARCHAR(20) DEFAULT 'UNPAID',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    pay_time TIMESTAMP,
    CONSTRAINT fk_fee_owner FOREIGN KEY (owner_id) REFERENCES owner(id)
);

CREATE TABLE IF NOT EXISTS repair (
    id INT AUTO_INCREMENT PRIMARY KEY,
    owner_id INT NOT NULL,
    content CLOB NOT NULL,
    image VARCHAR(255),
    status VARCHAR(20) DEFAULT 'PENDING',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    finish_time TIMESTAMP,
    CONSTRAINT fk_repair_owner FOREIGN KEY (owner_id) REFERENCES owner(id)
);

CREATE TABLE IF NOT EXISTS notice (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    content CLOB NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS visitor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    owner_id INT NOT NULL,
    visitor_name VARCHAR(50) NOT NULL,
    visit_time TIMESTAMP NOT NULL,
    plate_number VARCHAR(20),
    status VARCHAR(20) DEFAULT 'PENDING',
    CONSTRAINT fk_visitor_owner FOREIGN KEY (owner_id) REFERENCES owner(id)
);

CREATE TABLE IF NOT EXISTS parking (
    id INT AUTO_INCREMENT PRIMARY KEY,
    spot_number VARCHAR(20) NOT NULL UNIQUE,
    status VARCHAR(20) DEFAULT 'FREE',
    owner_id INT,
    CONSTRAINT fk_parking_owner FOREIGN KEY (owner_id) REFERENCES owner(id)
);
