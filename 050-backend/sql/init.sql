-- 课堂考勤签到系统数据库
DROP DATABASE IF EXISTS classroom_attendance;
CREATE DATABASE classroom_attendance DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE classroom_attendance;

-- 用户表（学生/教师/管理员）
CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    real_name VARCHAR(50) COMMENT '真实姓名',
    avatar VARCHAR(255) COMMENT '头像',
    phone VARCHAR(20) COMMENT '手机号',
    email VARCHAR(100) COMMENT '邮箱',
    openid VARCHAR(100) COMMENT '微信openid',
    student_no VARCHAR(50) COMMENT '学号（学生）',
    teacher_no VARCHAR(50) COMMENT '工号（教师）',
    department VARCHAR(100) COMMENT '院系',
    class_name VARCHAR(100) COMMENT '班级（学生）',
    role TINYINT DEFAULT 0 COMMENT '角色：0-学生 1-教师 2-管理员',
    status TINYINT DEFAULT 1 COMMENT '状态：0-禁用 1-正常',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT '用户表';

-- 学期表
CREATE TABLE semester (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL COMMENT '学期名称',
    start_date DATE NOT NULL COMMENT '开始日期',
    end_date DATE NOT NULL COMMENT '结束日期',
    is_current TINYINT DEFAULT 0 COMMENT '是否当前学期',
    status TINYINT DEFAULT 1 COMMENT '状态',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) COMMENT '学期表';

-- 课程表
CREATE TABLE course (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    course_code VARCHAR(50) NOT NULL COMMENT '课程编号',
    name VARCHAR(100) NOT NULL COMMENT '课程名称',
    teacher_id BIGINT NOT NULL COMMENT '授课教师ID',
    semester_id BIGINT COMMENT '学期ID',
    credit DECIMAL(3,1) COMMENT '学分',
    hours INT COMMENT '学时',
    description TEXT COMMENT '课程描述',
    student_count INT DEFAULT 0 COMMENT '选课人数',
    status TINYINT DEFAULT 1 COMMENT '状态：0-已结束 1-进行中',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT '课程表';

-- 课程安排表（上课时间地点）
CREATE TABLE course_schedule (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    course_id BIGINT NOT NULL COMMENT '课程ID',
    week_day TINYINT NOT NULL COMMENT '星期几：1-7',
    start_time TIME NOT NULL COMMENT '开始时间',
    end_time TIME NOT NULL COMMENT '结束时间',
    classroom VARCHAR(100) COMMENT '教室',
    building VARCHAR(100) COMMENT '教学楼',
    start_week INT DEFAULT 1 COMMENT '起始周',
    end_week INT DEFAULT 18 COMMENT '结束周',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) COMMENT '课程安排表';

-- 选课表（学生-课程关联）
CREATE TABLE course_student (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    course_id BIGINT NOT NULL COMMENT '课程ID',
    student_id BIGINT NOT NULL COMMENT '学生ID',
    status TINYINT DEFAULT 1 COMMENT '状态：0-退选 1-正常',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_course_student (course_id, student_id)
) COMMENT '选课表';

-- 签到任务表
CREATE TABLE attendance_task (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    course_id BIGINT NOT NULL COMMENT '课程ID',
    schedule_id BIGINT COMMENT '课程安排ID',
    teacher_id BIGINT NOT NULL COMMENT '发起教师ID',
    title VARCHAR(100) COMMENT '签到标题',
    sign_type TINYINT DEFAULT 1 COMMENT '签到方式：1-普通签到 2-定位签到 3-二维码签到 4-数字签到',
    sign_code VARCHAR(20) COMMENT '签到码（数字签到）',
    qr_code VARCHAR(255) COMMENT '二维码内容',
    latitude DECIMAL(10,6) COMMENT '签到位置纬度',
    longitude DECIMAL(10,6) COMMENT '签到位置经度',
    address VARCHAR(255) COMMENT '签到地址',
    distance INT DEFAULT 100 COMMENT '签到范围（米）',
    start_time DATETIME NOT NULL COMMENT '签到开始时间',
    end_time DATETIME NOT NULL COMMENT '签到结束时间',
    duration INT COMMENT '签到时长（分钟）',
    late_time DATETIME COMMENT '迟到时间界限',
    status TINYINT DEFAULT 1 COMMENT '状态：0-已结束 1-进行中 2-未开始',
    total_count INT DEFAULT 0 COMMENT '应签人数',
    signed_count INT DEFAULT 0 COMMENT '已签人数',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT '签到任务表';

-- 签到记录表
CREATE TABLE attendance_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    task_id BIGINT NOT NULL COMMENT '签到任务ID',
    student_id BIGINT NOT NULL COMMENT '学生ID',
    course_id BIGINT NOT NULL COMMENT '课程ID',
    sign_time DATETIME COMMENT '签到时间',
    sign_type TINYINT COMMENT '实际签到方式',
    latitude DECIMAL(10,6) COMMENT '签到位置纬度',
    longitude DECIMAL(10,6) COMMENT '签到位置经度',
    address VARCHAR(255) COMMENT '签到地址',
    distance DECIMAL(10,2) COMMENT '与目标位置距离（米）',
    device_info VARCHAR(255) COMMENT '设备信息',
    ip_address VARCHAR(50) COMMENT 'IP地址',
    status TINYINT DEFAULT 0 COMMENT '状态：0-未签 1-已签 2-迟到 3-早退 4-请假 5-补签',
    remark VARCHAR(255) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_task_student (task_id, student_id)
) COMMENT '签到记录表';

-- 请假表
CREATE TABLE leave_request (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_id BIGINT NOT NULL COMMENT '学生ID',
    course_id BIGINT NOT NULL COMMENT '课程ID',
    task_id BIGINT COMMENT '签到任务ID',
    leave_type TINYINT DEFAULT 1 COMMENT '请假类型：1-事假 2-病假 3-公假',
    start_time DATETIME NOT NULL COMMENT '开始时间',
    end_time DATETIME NOT NULL COMMENT '结束时间',
    reason TEXT COMMENT '请假原因',
    attachment VARCHAR(255) COMMENT '附件（证明材料）',
    status TINYINT DEFAULT 0 COMMENT '状态：0-待审核 1-已批准 2-已拒绝',
    approve_time DATETIME COMMENT '审批时间',
    approve_remark VARCHAR(255) COMMENT '审批备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT '请假表';

-- 补签申请表
CREATE TABLE makeup_request (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_id BIGINT NOT NULL COMMENT '学生ID',
    task_id BIGINT NOT NULL COMMENT '签到任务ID',
    reason TEXT COMMENT '补签原因',
    attachment VARCHAR(255) COMMENT '附件',
    status TINYINT DEFAULT 0 COMMENT '状态：0-待审核 1-已批准 2-已拒绝',
    approve_time DATETIME COMMENT '审批时间',
    approve_remark VARCHAR(255) COMMENT '审批备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT '补签申请表';

-- 考勤统计表
CREATE TABLE attendance_stat (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_id BIGINT NOT NULL COMMENT '学生ID',
    course_id BIGINT NOT NULL COMMENT '课程ID',
    semester_id BIGINT COMMENT '学期ID',
    total_count INT DEFAULT 0 COMMENT '总签到次数',
    normal_count INT DEFAULT 0 COMMENT '正常签到次数',
    late_count INT DEFAULT 0 COMMENT '迟到次数',
    absent_count INT DEFAULT 0 COMMENT '缺勤次数',
    leave_count INT DEFAULT 0 COMMENT '请假次数',
    makeup_count INT DEFAULT 0 COMMENT '补签次数',
    attendance_rate DECIMAL(5,2) DEFAULT 0 COMMENT '出勤率',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_student_course (student_id, course_id)
) COMMENT '考勤统计表';

-- 通知公告表
CREATE TABLE notice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL COMMENT '标题',
    content TEXT COMMENT '内容',
    type TINYINT DEFAULT 1 COMMENT '类型：1-系统公告 2-课程通知 3-考勤提醒',
    course_id BIGINT COMMENT '关联课程ID',
    publisher_id BIGINT COMMENT '发布者ID',
    is_top TINYINT DEFAULT 0 COMMENT '是否置顶',
    status TINYINT DEFAULT 1 COMMENT '状态',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT '通知公告表';

-- 消息表
CREATE TABLE message (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '接收用户ID',
    title VARCHAR(200) COMMENT '标题',
    content TEXT COMMENT '内容',
    type TINYINT DEFAULT 1 COMMENT '类型：1-签到提醒 2-审批通知 3-系统消息',
    related_id BIGINT COMMENT '关联ID',
    is_read TINYINT DEFAULT 0 COMMENT '是否已读',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) COMMENT '消息表';

-- 初始化数据

-- 学期
INSERT INTO semester (name, start_date, end_date, is_current) VALUES
('2025-2026学年第一学期', '2025-09-01', '2026-01-15', 0),
('2025-2026学年第二学期', '2026-02-20', '2026-07-10', 1);

-- 用户数据
INSERT INTO sys_user (username, password, real_name, phone, student_no, teacher_no, department, class_name, role) VALUES
('admin', '123456', '系统管理员', '13800000000', NULL, 'A001', '教务处', NULL, 2),
('teacher1', '123456', '张老师', '13800000001', NULL, 'T001', '计算机学院', NULL, 1),
('teacher2', '123456', '李老师', '13800000002', NULL, 'T002', '计算机学院', NULL, 1),
('teacher3', '123456', '王老师', '13800000003', NULL, 'T003', '软件学院', NULL, 1),
('student1', '123456', '张三', '13900000001', '2022001001', NULL, '计算机学院', '计算机2201班', 0),
('student2', '123456', '李四', '13900000002', '2022001002', NULL, '计算机学院', '计算机2201班', 0),
('student3', '123456', '王五', '13900000003', '2022001003', NULL, '计算机学院', '计算机2201班', 0),
('student4', '123456', '赵六', '13900000004', '2022001004', NULL, '计算机学院', '计算机2202班', 0),
('student5', '123456', '钱七', '13900000005', '2022001005', NULL, '软件学院', '软件2201班', 0);

-- 课程数据
INSERT INTO course (course_code, name, teacher_id, semester_id, credit, hours, description, student_count) VALUES
('CS101', 'Java程序设计', 2, 2, 3.0, 48, 'Java语言基础与面向对象编程', 3),
('CS102', '数据库原理', 2, 2, 3.0, 48, '数据库基础理论与SQL实践', 3),
('CS103', '数据结构', 3, 2, 4.0, 64, '常用数据结构与算法分析', 2),
('SE101', '软件工程', 4, 2, 3.0, 48, '软件开发流程与项目管理', 2);

-- 课程安排
INSERT INTO course_schedule (course_id, week_day, start_time, end_time, classroom, building, start_week, end_week) VALUES
(1, 1, '08:00:00', '09:40:00', 'A301', '教学楼A', 1, 16),
(1, 3, '10:00:00', '11:40:00', 'A301', '教学楼A', 1, 16),
(2, 2, '14:00:00', '15:40:00', 'B201', '教学楼B', 1, 16),
(2, 4, '14:00:00', '15:40:00', 'B201', '教学楼B', 1, 16),
(3, 3, '08:00:00', '09:40:00', 'C102', '教学楼C', 1, 16),
(4, 5, '10:00:00', '11:40:00', 'D301', '实验楼D', 1, 16);

-- 选课数据
INSERT INTO course_student (course_id, student_id) VALUES
(1, 5), (1, 6), (1, 7),
(2, 5), (2, 6), (2, 8),
(3, 5), (3, 6),
(4, 9), (4, 8);

-- 签到任务示例
INSERT INTO attendance_task (course_id, schedule_id, teacher_id, title, sign_type, sign_code, start_time, end_time, late_time, status, total_count, signed_count, latitude, longitude, address, distance) VALUES
(1, 1, 2, 'Java课程第1周签到', 1, NULL, '2026-01-20 08:00:00', '2026-01-20 08:15:00', '2026-01-20 08:10:00', 0, 3, 3, 39.9042, 116.4074, '教学楼A301', 100),
(1, 2, 2, 'Java课程第2周签到', 4, '6789', '2026-01-22 10:00:00', '2026-01-22 10:15:00', '2026-01-22 10:10:00', 1, 3, 1, 39.9042, 116.4074, '教学楼A301', 100);

-- 签到记录示例
INSERT INTO attendance_record (task_id, student_id, course_id, sign_time, sign_type, status, latitude, longitude) VALUES
(1, 5, 1, '2026-01-20 08:02:00', 1, 1, 39.9043, 116.4075),
(1, 6, 1, '2026-01-20 08:05:00', 1, 1, 39.9041, 116.4073),
(1, 7, 1, '2026-01-20 08:12:00', 1, 2, 39.9044, 116.4076),
(2, 5, 1, '2026-01-22 10:03:00', 4, 1, 39.9042, 116.4074);

-- 考勤统计
INSERT INTO attendance_stat (student_id, course_id, semester_id, total_count, normal_count, late_count, absent_count, leave_count, attendance_rate) VALUES
(5, 1, 2, 2, 2, 0, 0, 0, 100.00),
(6, 1, 2, 2, 1, 0, 1, 0, 50.00),
(7, 1, 2, 2, 0, 1, 1, 0, 50.00);

-- 公告
INSERT INTO notice (title, content, type, publisher_id, is_top) VALUES
('关于本学期考勤管理的通知', '为加强学风建设，本学期将严格执行课堂考勤制度。请各位同学按时签到，累计缺勤超过课程学时1/3者，将取消考试资格。', 1, 1, 1),
('签到系统使用说明', '1. 进入课程后点击签到按钮\n2. 根据教师发起的签到方式完成签到\n3. 支持普通签到、定位签到、二维码签到、数字签到', 1, 1, 0);
