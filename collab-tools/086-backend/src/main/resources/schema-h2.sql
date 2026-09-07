DROP TABLE IF EXISTS operation_log;
DROP TABLE IF EXISTS system_notice;
DROP TABLE IF EXISTS carousel_banner;
DROP TABLE IF EXISTS wallpaper_audit;
DROP TABLE IF EXISTS wallpaper_download;
DROP TABLE IF EXISTS wallpaper_favorite;
DROP TABLE IF EXISTS wallpaper_tag_rel;
DROP TABLE IF EXISTS wallpaper_info;
DROP TABLE IF EXISTS wallpaper_tag;
DROP TABLE IF EXISTS wallpaper_category;
DROP TABLE IF EXISTS sys_user;

CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    real_name VARCHAR(50),
    avatar VARCHAR(255),
    email VARCHAR(100),
    phone VARCHAR(20),
    role VARCHAR(20) NOT NULL,
    bio VARCHAR(500),
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE wallpaper_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    parent_id BIGINT DEFAULT 0,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) NOT NULL UNIQUE,
    icon VARCHAR(100),
    sort INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE wallpaper_tag (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    color VARCHAR(20),
    sort INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE wallpaper_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    subtitle VARCHAR(255),
    category_id BIGINT NOT NULL,
    cover_url VARCHAR(500),
    image_url VARCHAR(500) NOT NULL,
    preview_url VARCHAR(500),
    wallpaper_type VARCHAR(20),
    resolution VARCHAR(50),
    width INT DEFAULT 0,
    height INT DEFAULT 0,
    color_hex VARCHAR(20),
    file_format VARCHAR(20),
    file_size BIGINT DEFAULT 0,
    source_type VARCHAR(30),
    author_name VARCHAR(100),
    uploader_id BIGINT NOT NULL,
    description CLOB,
    download_count INT DEFAULT 0,
    favorite_count INT DEFAULT 0,
    view_count INT DEFAULT 0,
    audit_status INT DEFAULT 0,
    publish_status INT DEFAULT 0,
    featured INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE wallpaper_tag_rel (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    wallpaper_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL
);

CREATE TABLE wallpaper_favorite (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    wallpaper_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_wallpaper_user UNIQUE (wallpaper_id, user_id)
);

CREATE TABLE wallpaper_download (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    wallpaper_id BIGINT NOT NULL,
    user_id BIGINT,
    source VARCHAR(30),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE wallpaper_audit (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    wallpaper_id BIGINT NOT NULL,
    audit_status INT NOT NULL,
    audit_remark VARCHAR(500),
    auditor_id BIGINT NOT NULL,
    audit_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE carousel_banner (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    image_url VARCHAR(500) NOT NULL,
    link_url VARCHAR(500),
    sort INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE system_notice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content CLOB,
    type VARCHAR(30),
    status INT DEFAULT 0,
    publisher_id BIGINT,
    publish_time DATETIME,
    view_count INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE operation_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    module VARCHAR(50),
    action VARCHAR(50),
    content VARCHAR(1000),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);
