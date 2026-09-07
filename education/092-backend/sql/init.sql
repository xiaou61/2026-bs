DROP DATABASE IF EXISTS blue_sky_kindergarten_db;
CREATE DATABASE blue_sky_kindergarten_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE blue_sky_kindergarten_db;

CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    real_name VARCHAR(50),
    phone VARCHAR(20),
    role VARCHAR(20) NOT NULL,
    department_id BIGINT,
    major_id BIGINT,
    class_id BIGINT,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE campus_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) NOT NULL UNIQUE,
    dean_name VARCHAR(50),
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE grade_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    department_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) NOT NULL UNIQUE,
    grade_year VARCHAR(20),
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE class_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    major_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    counselor_name VARCHAR(50),
    student_count INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE school_term (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    term_name VARCHAR(100) NOT NULL,
    start_date DATE,
    end_date DATE,
    current_flag INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE activity_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    course_name VARCHAR(100) NOT NULL,
    course_code VARCHAR(50) NOT NULL UNIQUE,
    department_id BIGINT,
    teacher_id BIGINT,
    credit DECIMAL(5, 2) DEFAULT 0,
    course_type VARCHAR(30),
    course_hours INT DEFAULT 0,
    course_desc VARCHAR(1000),
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE activity_schedule (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    course_id BIGINT NOT NULL,
    term_id BIGINT NOT NULL,
    teacher_id BIGINT NOT NULL,
    class_id BIGINT,
    classroom VARCHAR(100),
    week_day VARCHAR(20),
    start_section INT DEFAULT 1,
    end_section INT DEFAULT 2,
    max_student_count INT DEFAULT 40,
    selected_count INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE child_profile (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    child_name VARCHAR(50) NOT NULL,
    gender VARCHAR(10),
    birthday DATE,
    campus_id BIGINT,
    grade_id BIGINT,
    class_id BIGINT,
    parent_id BIGINT,
    teacher_id BIGINT,
    allergy_info VARCHAR(500),
    pickup_person VARCHAR(50),
    profile_status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE weekly_recipe (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    schedule_id BIGINT,
    course_id BIGINT,
    teacher_id BIGINT,
    title VARCHAR(200) NOT NULL,
    resource_type VARCHAR(30),
    resource_url VARCHAR(500),
    content_desc VARCHAR(1000),
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE attendance_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    schedule_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    teacher_id BIGINT NOT NULL,
    attendance_date DATE,
    attendance_status VARCHAR(30),
    remark VARCHAR(500),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE health_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    child_id BIGINT NOT NULL,
    teacher_id BIGINT NOT NULL,
    record_date DATE,
    temperature DECIMAL(4, 1),
    health_status VARCHAR(100),
    emotion_status VARCHAR(100),
    attendance_advice VARCHAR(200),
    remark VARCHAR(500),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE parent_feedback (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    child_id BIGINT NOT NULL,
    parent_id BIGINT NOT NULL,
    teacher_id BIGINT,
    feedback_type VARCHAR(50),
    feedback_score INT DEFAULT 0,
    feedback_content VARCHAR(1000),
    reply_content VARCHAR(1000),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE system_notice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    type VARCHAR(30),
    status INT DEFAULT 1,
    publisher_id BIGINT,
    publish_time DATETIME,
    view_count INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE pickup_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    child_id BIGINT NOT NULL,
    parent_id BIGINT NOT NULL,
    pickup_person VARCHAR(50),
    pickup_time DATETIME,
    pickup_status VARCHAR(30),
    remark VARCHAR(500),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO campus_info (name, code, dean_name, status) VALUES
('蓝天总园', 'MAIN', '周园长', 1),
('彩虹分园', 'RAINBOW', '林园长', 1);

INSERT INTO grade_info (department_id, name, code, grade_year, status) VALUES
(1, '小班', 'SMALL', '3-4岁', 1),
(1, '中班', 'MIDDLE', '4-5岁', 1),
(2, '大班', 'LARGE', '5-6岁', 1);

INSERT INTO class_info (major_id, name, counselor_name, student_count, status) VALUES
(1, '向日葵一班', '陈老师', 28, 1),
(2, '蒲公英二班', '李老师', 30, 1),
(3, '海豚三班', '王老师', 26, 1);

INSERT INTO school_term (term_name, start_date, end_date, current_flag, status) VALUES
('2026年春季学期', '2026-02-23', '2026-07-10', 1, 1),
('2026年秋季学期', '2026-09-01', '2027-01-15', 0, 1);

INSERT INTO sys_user (username, password, real_name, phone, role, department_id, major_id, class_id, status) VALUES
('admin', '123456', '周园长', '13800000001', 'admin', 1, NULL, NULL, 1),
('teacher', '123456', '陈老师', '13800000002', 'teacher', 1, NULL, 1, 1),
('parent', '123456', '朵朵妈妈', '13800000003', 'parent', 1, 1, 1, 1),
('parent2', '123456', '天天爸爸', '13800000004', 'parent', 1, 2, 2, 1),
('teacher2', '123456', '王老师', '13800000005', 'teacher', 2, NULL, 3, 1);

INSERT INTO activity_info (course_name, course_code, department_id, teacher_id, credit, course_type, course_hours, course_desc, status) VALUES
('音乐律动', 'ACT001', 1, 2, 1.0, '艺术启蒙', 24, '通过律动游戏培养节奏感与表现力', 1),
('创意美术', 'ACT002', 1, 2, 1.0, '艺术启蒙', 20, '围绕涂鸦、手工和色彩启蒙开展活动', 1),
('户外体能', 'ACT003', 2, 5, 1.0, '体能游戏', 28, '围绕跳跃、平衡和团队合作开展训练', 1);

INSERT INTO activity_schedule (course_id, term_id, teacher_id, class_id, classroom, week_day, start_section, end_section, max_student_count, selected_count, status) VALUES
(1, 1, 2, 1, '多功能活动室', '周一', 1, 2, 35, 28, 1),
(2, 1, 2, 2, '美术教室', '周三', 3, 4, 35, 30, 1),
(3, 1, 5, 3, '户外操场', '周五', 1, 2, 35, 26, 1);

INSERT INTO child_profile (child_name, gender, birthday, campus_id, grade_id, class_id, parent_id, teacher_id, allergy_info, pickup_person, profile_status) VALUES
('朵朵', '女', '2021-05-18', 1, 1, 1, 3, 2, '鸡蛋轻微过敏', '朵朵妈妈', 1),
('天天', '男', '2020-11-03', 1, 2, 2, 4, 2, '无', '天天爸爸', 1);

INSERT INTO weekly_recipe (schedule_id, course_id, teacher_id, title, resource_type, resource_url, content_desc, status) VALUES
(1, 1, 2, '第3周早餐食谱', '早餐', '/recipe/week3/breakfast', '牛奶、南瓜饼、水果拼盘', 1),
(2, 2, 2, '第3周午餐食谱', '午餐', '/recipe/week3/lunch', '香菇鸡肉饭、时蔬、冬瓜汤', 1),
(3, 3, 5, '第3周加餐安排', '加餐', '/recipe/week3/snack', '酸奶、香蕉、坚果碎', 1);

INSERT INTO attendance_record (schedule_id, course_id, student_id, teacher_id, attendance_date, attendance_status, remark) VALUES
(1, 1, 3, 2, '2026-03-16', 'present', '按时到园'),
(2, 2, 4, 2, '2026-03-16', 'late', '家长路上堵车'),
(3, 3, 4, 5, '2026-03-17', 'leave', '请假在家休息');

INSERT INTO health_record (child_id, teacher_id, record_date, temperature, health_status, emotion_status, attendance_advice, remark) VALUES
(1, 2, '2026-03-16', 36.5, '正常', '活跃', '可正常入园', '精神状态良好'),
(2, 2, '2026-03-16', 37.2, '轻微咳嗽', '平稳', '建议午休重点观察', '已通知家长关注');

INSERT INTO parent_feedback (child_id, parent_id, teacher_id, feedback_type, feedback_score, feedback_content, reply_content) VALUES
(1, 3, 2, '成长反馈', 95, '孩子回家后主动分享了音乐活动，很喜欢老师的节奏游戏。', '已收到反馈，后续会继续加强互动表达。'),
(2, 4, 2, '膳食建议', 90, '希望午餐增加更多软糯类主食，孩子更容易接受。', '感谢建议，本周会和保健老师一起调整菜单。');

INSERT INTO system_notice (title, content, type, status, publisher_id, publish_time, view_count) VALUES
('春季亲子运动会通知', '本周六上午举行蓝天幼儿园春季亲子运动会，请家长提前到园签到。', 'activity', 1, 1, NOW(), 25),
('第3周食谱发布', '本周早餐、午餐和加餐食谱已更新，请家长及时查看。', 'parent', 1, 1, NOW(), 18);

INSERT INTO pickup_record (child_id, parent_id, pickup_person, pickup_time, pickup_status, remark) VALUES
(1, 3, '朵朵妈妈', '2026-03-16 17:20:00', 'completed', '按时接走'),
(2, 4, '天天爸爸', '2026-03-16 17:32:00', 'completed', '园外等候接送');
