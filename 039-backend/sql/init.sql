CREATE DATABASE folksong_platform;

\c folksong_platform;

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    nickname VARCHAR(50),
    avatar VARCHAR(255),
    email VARCHAR(100),
    phone VARCHAR(20),
    gender INTEGER DEFAULT 0,
    introduction TEXT,
    role INTEGER DEFAULT 0,
    status INTEGER DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description TEXT,
    region VARCHAR(50),
    cover_image VARCHAR(255),
    sort_order INTEGER DEFAULT 0,
    status INTEGER DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE folk_songs (
    id SERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    category_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    content TEXT,
    lyrics TEXT,
    audio_url VARCHAR(255),
    video_url VARCHAR(255),
    cover_image VARCHAR(255),
    region VARCHAR(50),
    ethnic VARCHAR(50),
    introduction TEXT,
    view_count INTEGER DEFAULT 0,
    like_count INTEGER DEFAULT 0,
    collect_count INTEGER DEFAULT 0,
    comment_count INTEGER DEFAULT 0,
    status INTEGER DEFAULT 1,
    audit_status INTEGER DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE comments (
    id SERIAL PRIMARY KEY,
    song_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    parent_id BIGINT,
    content TEXT NOT NULL,
    like_count INTEGER DEFAULT 0,
    status INTEGER DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE favorites (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    song_id BIGINT NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, song_id)
);

CREATE TABLE likes (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    song_id BIGINT NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, song_id)
);

CREATE TABLE announcements (
    id SERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    publisher_id BIGINT NOT NULL,
    status INTEGER DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_folk_songs_category ON folk_songs(category_id);
CREATE INDEX idx_folk_songs_user ON folk_songs(user_id);
CREATE INDEX idx_comments_song ON comments(song_id);
CREATE INDEX idx_comments_user ON comments(user_id);
CREATE INDEX idx_favorites_user ON favorites(user_id);
CREATE INDEX idx_likes_user ON likes(user_id);

INSERT INTO users (username, password, nickname, role, status) VALUES 
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EHsM', '管理员', 1, 1);

INSERT INTO categories (name, description, region, sort_order) VALUES 
('山歌', '山区民间歌曲，以高亢嘹亮著称', '西南', 1),
('号子', '劳动人民在劳动过程中演唱的歌曲', '全国', 2),
('小调', '流行于城镇的民间歌曲', '华东', 3),
('田歌', '农民在田间劳作时演唱的歌曲', '华中', 4),
('牧歌', '牧民在放牧时演唱的歌曲', '内蒙古', 5),
('渔歌', '渔民在捕鱼时演唱的歌曲', '沿海', 6),
('茶歌', '采茶劳动中演唱的歌曲', '华南', 7),
('秧歌', '插秧时演唱的歌曲', '东北', 8);
