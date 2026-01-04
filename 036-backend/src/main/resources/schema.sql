CREATE DATABASE IF NOT EXISTS dream_donation;

CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    real_name VARCHAR(50),
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(20),
    avatar VARCHAR(500),
    role VARCHAR(20) DEFAULT 'USER',
    status VARCHAR(20) DEFAULT 'ACTIVE',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS donation_projects (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    cover_image VARCHAR(500),
    category VARCHAR(100),
    target_amount DECIMAL(15,2) NOT NULL,
    current_amount DECIMAL(15,2) DEFAULT 0,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    organization_name VARCHAR(200),
    location VARCHAR(100),
    status VARCHAR(20) DEFAULT 'DRAFT',
    creator_id BIGINT REFERENCES users(id),
    donor_count INTEGER DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS donations (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    project_id BIGINT NOT NULL REFERENCES donation_projects(id),
    amount DECIMAL(15,2) NOT NULL,
    message VARCHAR(500),
    anonymous BOOLEAN DEFAULT FALSE,
    payment_method VARCHAR(20),
    transaction_id VARCHAR(100),
    payment_status VARCHAR(20) DEFAULT 'PENDING',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    payment_time TIMESTAMP
);

CREATE TABLE IF NOT EXISTS project_progress (
    id BIGSERIAL PRIMARY KEY,
    project_id BIGINT NOT NULL REFERENCES donation_projects(id),
    title VARCHAR(200) NOT NULL,
    content TEXT,
    images VARCHAR(1000),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_user_username ON users(username);
CREATE INDEX idx_user_email ON users(email);
CREATE INDEX idx_project_status ON donation_projects(status);
CREATE INDEX idx_project_category ON donation_projects(category);
CREATE INDEX idx_donation_user ON donations(user_id);
CREATE INDEX idx_donation_project ON donations(project_id);
CREATE INDEX idx_progress_project ON project_progress(project_id);
