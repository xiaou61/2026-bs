DROP TABLE IF EXISTS operation_log;
DROP TABLE IF EXISTS system_notice;
DROP TABLE IF EXISTS study_list;
DROP TABLE IF EXISTS material_favorite;
DROP TABLE IF EXISTS material_download;
DROP TABLE IF EXISTS material_audit;
DROP TABLE IF EXISTS material_tag_rel;
DROP TABLE IF EXISTS material_file;
DROP TABLE IF EXISTS material_info;
DROP TABLE IF EXISTS material_tag;
DROP TABLE IF EXISTS material_category;
DROP TABLE IF EXISTS sys_user;

CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    real_name VARCHAR(50),
    phone VARCHAR(20),
    role VARCHAR(20) NOT NULL,
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE material_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    parent_id BIGINT DEFAULT 0,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) NOT NULL UNIQUE,
    sort INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE material_tag (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    color VARCHAR(20),
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE material_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    summary VARCHAR(1000),
    category_id BIGINT NOT NULL,
    grade VARCHAR(30),
    subject VARCHAR(50),
    keyword VARCHAR(255),
    uploader_id BIGINT NOT NULL,
    audit_status INT DEFAULT 0,
    publish_status INT DEFAULT 0,
    download_count INT DEFAULT 0,
    favorite_count INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE material_file (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    material_id BIGINT NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    file_path VARCHAR(500) NOT NULL,
    file_size BIGINT DEFAULT 0,
    file_type VARCHAR(50),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE material_tag_rel (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    material_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL
);

CREATE TABLE material_audit (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    material_id BIGINT NOT NULL,
    audit_status INT NOT NULL,
    audit_remark VARCHAR(500),
    auditor_id BIGINT NOT NULL,
    audit_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE material_download (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    material_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    source VARCHAR(30),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE material_favorite (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    material_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (material_id, user_id)
);

CREATE TABLE study_list (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    material_id BIGINT NOT NULL,
    progress INT DEFAULT 0,
    note VARCHAR(500),
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (user_id, material_id)
);

CREATE TABLE system_notice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content CLOB,
    type VARCHAR(30),
    status INT DEFAULT 0,
    publisher_id BIGINT,
    publish_time TIMESTAMP,
    view_count INT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE operation_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    module VARCHAR(50),
    action VARCHAR(50),
    content VARCHAR(1000),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
