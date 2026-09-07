CREATE TABLE IF NOT EXISTS user (
    id BIGINT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    real_name VARCHAR(50) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    grade VARCHAR(20),
    major VARCHAR(100),
    learning_style TINYINT DEFAULT 1,
    cognitive_level DECIMAL(3,2) DEFAULT 0.50,
    study_time_preference VARCHAR(50),
    difficulty_preference TINYINT DEFAULT 2,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS course (
    id BIGINT PRIMARY KEY,
    course_name VARCHAR(200) NOT NULL,
    course_code VARCHAR(50) UNIQUE,
    description CLOB,
    category_id BIGINT,
    difficulty_level TINYINT DEFAULT 1,
    duration_minutes INT,
    prerequisite_courses VARCHAR(1000),
    learning_objectives CLOB,
    content_type TINYINT,
    status TINYINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS knowledge_point (
    id BIGINT PRIMARY KEY,
    point_name VARCHAR(200) NOT NULL,
    point_code VARCHAR(100) UNIQUE,
    course_id BIGINT,
    parent_id BIGINT,
    description CLOB,
    difficulty_level TINYINT DEFAULT 1,
    importance_score DECIMAL(3,2) DEFAULT 0.50,
    keywords VARCHAR(500),
    learning_time_estimate INT,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS learning_record (
    id BIGINT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    course_id BIGINT,
    knowledge_point_id BIGINT,
    learning_type TINYINT NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP,
    duration_seconds INT,
    completion_rate DECIMAL(5,2) DEFAULT 0,
    mastery_level DECIMAL(3,2),
    score DECIMAL(5,2),
    interaction_count INT DEFAULT 0,
    focus_duration INT
);

CREATE TABLE IF NOT EXISTS recommendation (
    id BIGINT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    content_type TINYINT NOT NULL,
    content_id BIGINT NOT NULL,
    recommendation_type TINYINT NOT NULL,
    recommendation_score DECIMAL(5,3),
    algorithm_version VARCHAR(50),
    is_clicked BOOLEAN DEFAULT FALSE,
    is_completed BOOLEAN DEFAULT FALSE,
    feedback_score TINYINT,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS knowledge_relation (
    id BIGINT PRIMARY KEY,
    from_knowledge_id BIGINT NOT NULL,
    to_knowledge_id BIGINT NOT NULL,
    relation_type TINYINT NOT NULL,
    weight DECIMAL(3,2) DEFAULT 1.00,
    confidence DECIMAL(3,2) DEFAULT 1.00,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS qa_record (
    id BIGINT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    question CLOB NOT NULL,
    question_keywords VARCHAR(300),
    answer CLOB,
    answer_score DECIMAL(3,2),
    knowledge_point_ids VARCHAR(500),
    session_id VARCHAR(100),
    is_helpful BOOLEAN,
    response_time_ms INT,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS learning_path (
    id BIGINT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    path_name VARCHAR(200) NOT NULL,
    target_knowledge_point_id BIGINT,
    path_steps CLOB NOT NULL,
    estimated_duration INT,
    current_step INT DEFAULT 0,
    progress_rate DECIMAL(5,2) DEFAULT 0,
    algorithm_type VARCHAR(50),
    optimization_score DECIMAL(5,3),
    is_active BOOLEAN DEFAULT TRUE,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS user_behavior (
    id BIGINT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    behavior_type VARCHAR(50) NOT NULL,
    target_type VARCHAR(50),
    target_id BIGINT,
    behavior_data CLOB,
    session_id VARCHAR(100),
    ip_address VARCHAR(50),
    user_agent CLOB,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_user_username ON user(username);
CREATE INDEX IF NOT EXISTS idx_learning_record_user_id ON learning_record(user_id);
CREATE INDEX IF NOT EXISTS idx_learning_record_course_id ON learning_record(course_id);
CREATE INDEX IF NOT EXISTS idx_recommendation_user_id ON recommendation(user_id);
CREATE INDEX IF NOT EXISTS idx_knowledge_relation_from ON knowledge_relation(from_knowledge_id);
CREATE INDEX IF NOT EXISTS idx_knowledge_relation_to ON knowledge_relation(to_knowledge_id);
CREATE INDEX IF NOT EXISTS idx_qa_record_user_id ON qa_record(user_id);
CREATE INDEX IF NOT EXISTS idx_learning_path_user_id ON learning_path(user_id);
CREATE INDEX IF NOT EXISTS idx_user_behavior_user_id ON user_behavior(user_id);

ALTER TABLE knowledge_point ADD COLUMN IF NOT EXISTS create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
