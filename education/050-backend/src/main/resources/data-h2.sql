INSERT INTO semester (id, name, start_date, end_date, is_current, status) VALUES
(1, '2025-2026学年第一学期', '2025-09-01', '2026-01-15', 0, 1),
(2, '2025-2026学年第二学期', '2026-02-20', '2026-07-10', 1, 1);

INSERT INTO sys_user (id, username, password, real_name, phone, student_no, teacher_no, department, class_name, role, status) VALUES
(1, 'admin', '$2a$10$38QkAorQH6TwPA0zMb3TgOtz67p9orbsvYs3GRjGXBqLka4RYnq/u', '系统管理员', '13800000000', NULL, 'A001', '教务处', NULL, 2, 1),
(2, 'teacher1', '$2a$10$38QkAorQH6TwPA0zMb3TgOtz67p9orbsvYs3GRjGXBqLka4RYnq/u', '张老师', '13800000001', NULL, 'T001', '计算机学院', NULL, 1, 1),
(3, 'teacher2', '$2a$10$38QkAorQH6TwPA0zMb3TgOtz67p9orbsvYs3GRjGXBqLka4RYnq/u', '李老师', '13800000002', NULL, 'T002', '计算机学院', NULL, 1, 1),
(4, 'teacher3', '$2a$10$38QkAorQH6TwPA0zMb3TgOtz67p9orbsvYs3GRjGXBqLka4RYnq/u', '王老师', '13800000003', NULL, 'T003', '软件学院', NULL, 1, 1),
(5, 'student1', '$2a$10$38QkAorQH6TwPA0zMb3TgOtz67p9orbsvYs3GRjGXBqLka4RYnq/u', '张三', '13900000001', '2022001001', NULL, '计算机学院', '计算机2201班', 0, 1),
(6, 'student2', '$2a$10$38QkAorQH6TwPA0zMb3TgOtz67p9orbsvYs3GRjGXBqLka4RYnq/u', '李四', '13900000002', '2022001002', NULL, '计算机学院', '计算机2201班', 0, 1),
(7, 'student3', '$2a$10$38QkAorQH6TwPA0zMb3TgOtz67p9orbsvYs3GRjGXBqLka4RYnq/u', '王五', '13900000003', '2022001003', NULL, '计算机学院', '计算机2201班', 0, 1),
(8, 'student4', '$2a$10$38QkAorQH6TwPA0zMb3TgOtz67p9orbsvYs3GRjGXBqLka4RYnq/u', '赵六', '13900000004', '2022001004', NULL, '计算机学院', '计算机2202班', 0, 1),
(9, 'student5', '$2a$10$38QkAorQH6TwPA0zMb3TgOtz67p9orbsvYs3GRjGXBqLka4RYnq/u', '钱七', '13900000005', '2022001005', NULL, '软件学院', '软件2201班', 0, 1);

INSERT INTO course (id, course_code, name, teacher_id, semester_id, credit, hours, description, student_count, status) VALUES
(1, 'CS101', 'Java程序设计', 2, 2, 3.0, 48, 'Java语言基础与面向对象编程', 3, 1),
(2, 'CS102', '数据库原理', 2, 2, 3.0, 48, '数据库基础理论与SQL实践', 3, 1),
(3, 'CS103', '数据结构', 3, 2, 4.0, 64, '常用数据结构与算法分析', 2, 1),
(4, 'SE101', '软件工程', 4, 2, 3.0, 48, '软件开发流程与项目管理', 2, 1);

INSERT INTO course_schedule (id, course_id, week_day, start_time, end_time, classroom, building, start_week, end_week) VALUES
(1, 1, 1, '08:00:00', '09:40:00', 'A301', '教学楼A', 1, 16),
(2, 1, 3, '10:00:00', '11:40:00', 'A301', '教学楼A', 1, 16),
(3, 2, 2, '14:00:00', '15:40:00', 'B201', '教学楼B', 1, 16),
(4, 2, 4, '14:00:00', '15:40:00', 'B201', '教学楼B', 1, 16),
(5, 3, 3, '08:00:00', '09:40:00', 'C102', '教学楼C', 1, 16),
(6, 4, 5, '10:00:00', '11:40:00', 'D301', '实验楼D', 1, 16);

INSERT INTO course_student (id, course_id, student_id, status) VALUES
(1, 1, 5, 1), (2, 1, 6, 1), (3, 1, 7, 1),
(4, 2, 5, 1), (5, 2, 6, 1), (6, 2, 8, 1),
(7, 3, 5, 1), (8, 3, 6, 1),
(9, 4, 9, 1), (10, 4, 8, 1);

INSERT INTO attendance_task (id, course_id, schedule_id, teacher_id, title, sign_type, sign_code, start_time, end_time, late_time, status, total_count, signed_count, latitude, longitude, address, distance) VALUES
(1, 1, 1, 2, 'Java课程历史签到', 1, NULL, '2026-01-20 08:00:00', '2026-01-20 08:15:00', '2026-01-20 08:10:00', 0, 3, 3, 39.9042, 116.4074, '教学楼A301', 100),
(2, 1, 2, 2, 'Java课程进行中签到', 4, '678900', '2026-04-20 10:00:00', '2026-12-31 23:59:59', '2026-12-31 23:30:00', 1, 3, 0, 39.9042, 116.4074, '教学楼A301', 100);

INSERT INTO attendance_record (id, task_id, student_id, course_id, sign_time, sign_type, status, latitude, longitude) VALUES
(1, 1, 5, 1, '2026-01-20 08:02:00', 1, 1, 39.9043, 116.4075),
(2, 1, 6, 1, '2026-01-20 08:05:00', 1, 1, 39.9041, 116.4073),
(3, 1, 7, 1, '2026-01-20 08:12:00', 1, 2, 39.9044, 116.4076),
(4, 2, 5, 1, NULL, NULL, 0, NULL, NULL),
(5, 2, 6, 1, NULL, NULL, 0, NULL, NULL),
(6, 2, 7, 1, NULL, NULL, 0, NULL, NULL);

INSERT INTO attendance_stat (id, student_id, course_id, semester_id, total_count, normal_count, late_count, absent_count, leave_count, makeup_count, attendance_rate) VALUES
(1, 5, 1, 2, 2, 1, 0, 1, 0, 0, 50.00),
(2, 6, 1, 2, 2, 1, 0, 1, 0, 0, 50.00),
(3, 7, 1, 2, 2, 0, 1, 1, 0, 0, 50.00);

INSERT INTO leave_request (id, student_id, course_id, task_id, leave_type, start_time, end_time, reason, status) VALUES
(1, 5, 1, 2, 1, '2026-04-28 08:00:00', '2026-04-28 09:40:00', '自动化演示请假', 0);

INSERT INTO makeup_request (id, student_id, task_id, reason, status) VALUES
(1, 6, 2, '自动化演示补签', 0);

INSERT INTO notice (id, title, content, type, publisher_id, is_top, status) VALUES
(1, '关于本学期考勤管理的通知', '为加强学风建设，本学期将严格执行课堂考勤制度。', 1, 1, 1, 1),
(2, '签到系统使用说明', '进入课程后点击签到按钮，根据教师发起的签到方式完成签到。', 1, 1, 0, 1);

INSERT INTO message (id, user_id, title, content, type, related_id, is_read) VALUES
(1, 5, '签到提醒', 'Java程序设计正在签到，请尽快完成。', 1, 2, 0),
(2, 2, '请假待审批', '张三提交了请假申请。', 2, 1, 0);
