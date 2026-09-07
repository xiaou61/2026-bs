DROP TABLE IF EXISTS qa_reply;
DROP TABLE IF EXISTS qa_post;
DROP TABLE IF EXISTS news;
DROP TABLE IF EXISTS favorite;
DROP TABLE IF EXISTS learn_record;
DROP TABLE IF EXISTS answer_record;
DROP TABLE IF EXISTS question;
DROP TABLE IF EXISTS knowledge_article;
DROP TABLE IF EXISTS knowledge_category;
DROP TABLE IF EXISTS admin;
DROP TABLE IF EXISTS user;

CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    openid VARCHAR(64) NOT NULL UNIQUE,
    nickname VARCHAR(50),
    avatar VARCHAR(255),
    points INT DEFAULT 0,
    level INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE knowledge_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    icon VARCHAR(255),
    sort INT DEFAULT 0,
    status TINYINT DEFAULT 1
);

CREATE TABLE knowledge_article (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    category_id BIGINT NOT NULL,
    title VARCHAR(100) NOT NULL,
    cover VARCHAR(255),
    content CLOB,
    view_count INT DEFAULT 0,
    like_count INT DEFAULT 0,
    status TINYINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_article_category_id ON knowledge_article (category_id);

CREATE TABLE question (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    category_id BIGINT NOT NULL,
    content VARCHAR(500) NOT NULL,
    options VARCHAR(1000),
    answer VARCHAR(10) NOT NULL,
    explanation VARCHAR(500),
    difficulty TINYINT DEFAULT 1
);

CREATE INDEX idx_question_category_id ON question (category_id);

CREATE TABLE answer_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    question_id BIGINT NOT NULL,
    user_answer VARCHAR(10),
    is_correct TINYINT,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_answer_user_id ON answer_record (user_id);
CREATE INDEX idx_answer_question_id ON answer_record (question_id);

CREATE TABLE learn_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    article_id BIGINT NOT NULL,
    progress INT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_learn_user_article UNIQUE (user_id, article_id)
);

CREATE TABLE favorite (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    article_id BIGINT NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_favorite_user_article UNIQUE (user_id, article_id)
);

CREATE TABLE news (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    cover VARCHAR(255),
    content CLOB,
    source VARCHAR(50),
    view_count INT DEFAULT 0,
    status TINYINT DEFAULT 1,
    publish_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE qa_post (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    title VARCHAR(100) NOT NULL,
    content CLOB,
    view_count INT DEFAULT 0,
    reply_count INT DEFAULT 0,
    status TINYINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_qa_post_user_id ON qa_post (user_id);

CREATE TABLE qa_reply (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    post_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    content CLOB,
    like_count INT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_qa_reply_post_id ON qa_reply (post_id);

CREATE TABLE admin (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50),
    status TINYINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
