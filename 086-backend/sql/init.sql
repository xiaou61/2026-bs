DROP DATABASE IF EXISTS wallpaper_db;
CREATE DATABASE wallpaper_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE wallpaper_db;

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
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
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
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE wallpaper_tag (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    color VARCHAR(20),
    sort INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
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
    description TEXT,
    download_count INT DEFAULT 0,
    favorite_count INT DEFAULT 0,
    view_count INT DEFAULT 0,
    audit_status INT DEFAULT 0,
    publish_status INT DEFAULT 0,
    featured INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
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
    UNIQUE KEY uk_wallpaper_user (wallpaper_id, user_id)
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
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE system_notice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    type VARCHAR(30),
    status INT DEFAULT 0,
    publisher_id BIGINT,
    publish_time DATETIME,
    view_count INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE operation_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    module VARCHAR(50),
    action VARCHAR(50),
    content VARCHAR(1000),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO sys_user (username, password, real_name, avatar, email, phone, role, bio, status) VALUES
('admin', '123456', '平台管理员', 'https://api.dicebear.com/7.x/initials/svg?seed=ADMIN', 'admin@wallpaper.com', '13800000001', 'admin', '负责平台内容审核与运营管理', 1),
('user', '123456', '星河用户', 'https://api.dicebear.com/7.x/initials/svg?seed=USER', 'user@wallpaper.com', '13800000002', 'user', '热爱收集高清壁纸', 1);

INSERT INTO wallpaper_category (parent_id, name, code, icon, sort, status) VALUES
(0, '风景自然', 'NATURE', 'Picture', 1, 1),
(0, '城市夜景', 'CITY', 'OfficeBuilding', 2, 1),
(0, '动漫插画', 'ANIME', 'Brush', 3, 1),
(0, '极简美学', 'MINIMAL', 'SetUp', 4, 1),
(0, '手机壁纸', 'MOBILE', 'Iphone', 5, 1);

INSERT INTO wallpaper_tag (name, color, sort, status) VALUES
('4K', '#22c55e', 1, 1),
('深色系', '#0f172a', 2, 1),
('治愈', '#38bdf8', 3, 1),
('赛博朋克', '#ec4899', 4, 1),
('高人气', '#f59e0b', 5, 1),
('手机专用', '#8b5cf6', 6, 1);

INSERT INTO wallpaper_info (title, subtitle, category_id, cover_url, image_url, preview_url, wallpaper_type, resolution, width, height, color_hex, file_format, file_size, source_type, author_name, uploader_id, description, download_count, favorite_count, view_count, audit_status, publish_status, featured, status) VALUES
('湖岸晨雾', '静谧的清晨山水光影', 1, 'https://picsum.photos/id/1015/800/500', 'https://picsum.photos/id/1015/1920/1080', 'https://picsum.photos/id/1015/1200/700', 'pc', '1920x1080', 1920, 1080, '#94a3b8', 'jpg', 2548120, 'official', 'Aurora Studio', 1, '适合桌面背景的自然风景壁纸，层次感丰富。', 126, 45, 388, 1, 1, 1, 1),
('霓虹街区', '雨夜城市与霓虹反射', 2, 'https://picsum.photos/id/1033/800/500', 'https://picsum.photos/id/1033/1920/1080', 'https://picsum.photos/id/1033/1200/700', 'pc', '1920x1080', 1920, 1080, '#1d4ed8', 'jpg', 2984512, 'official', 'Night Frame', 1, '城市夜景主题壁纸，适合深色桌面。', 168, 57, 460, 1, 1, 1, 1),
('霓光少女', '适配手机竖屏的插画壁纸', 5, 'https://picsum.photos/id/1027/500/900', 'https://picsum.photos/id/1027/1080/1920', 'https://picsum.photos/id/1027/700/1200', 'mobile', '1080x1920', 1080, 1920, '#7c3aed', 'jpg', 1865120, 'user_upload', '星河用户', 2, '手机端使用的插画壁纸投稿示例。', 52, 18, 139, 0, 0, 0, 1),
('留白桌面', '低饱和极简构图', 4, 'https://picsum.photos/id/1040/800/500', 'https://picsum.photos/id/1040/2560/1440', 'https://picsum.photos/id/1040/1200/700', 'pc', '2560x1440', 2560, 1440, '#e2e8f0', 'jpg', 2123450, 'official', 'Mono Lab', 1, '留白感明显的极简风格壁纸。', 89, 33, 227, 1, 1, 0, 1);

INSERT INTO wallpaper_tag_rel (wallpaper_id, tag_id) VALUES
(1, 1),
(1, 3),
(2, 1),
(2, 2),
(2, 4),
(2, 5),
(3, 6),
(4, 3);

INSERT INTO wallpaper_favorite (wallpaper_id, user_id) VALUES
(1, 2),
(2, 2);

INSERT INTO wallpaper_download (wallpaper_id, user_id, source) VALUES
(1, 2, 'web'),
(1, 2, 'web'),
(2, 2, 'web'),
(4, 2, 'web');

INSERT INTO wallpaper_audit (wallpaper_id, audit_status, audit_remark, auditor_id, audit_time) VALUES
(1, 1, '构图与清晰度符合要求', 1, NOW()),
(2, 1, '已通过审核', 1, NOW()),
(4, 1, '内容质量良好', 1, NOW());

INSERT INTO carousel_banner (title, image_url, link_url, sort, status) VALUES
('春日自然精选', 'https://picsum.photos/id/1018/1400/500', '/discover?categoryId=1', 1, 1),
('深色桌面专题', 'https://picsum.photos/id/1011/1400/500', '/discover?tagId=2', 2, 1),
('手机壁纸上新', 'https://picsum.photos/id/1025/1400/500', '/discover?categoryId=5', 3, 1);

INSERT INTO system_notice (title, content, type, status, publisher_id, publish_time, view_count) VALUES
('高清壁纸投稿规范', '上传壁纸请确保画质清晰，无水印，且标题与分类填写完整。', 'system', 1, 1, NOW(), 52),
('手机壁纸专区已上线', '新增竖屏壁纸筛选能力，欢迎体验。', 'activity', 1, 1, NOW(), 37);

INSERT INTO operation_log (user_id, module, action, content) VALUES
(1, 'wallpaper', 'audit', '审核通过 湖岸晨雾'),
(2, 'wallpaper', 'upload', '投稿 霓光少女');
