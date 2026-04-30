INSERT INTO subject_info (id, subject_name, subject_code, status) VALUES
(1, '语文', 'SUB-CHN', 1),
(2, '数学', 'SUB-MATH', 1),
(3, '英语', 'SUB-ENG', 1),
(4, '物理', 'SUB-PHY', 1);

INSERT INTO teaching_class (id, grade_name, class_name, head_teacher, status) VALUES
(1, '高一', '1班', '王芳', 1),
(2, '高一', '2班', '李敏', 1);

INSERT INTO sys_user (id, username, password, nickname, phone, email, role, class_id, status, last_login_time) VALUES
(1, 'admin', '123456', '系统管理员', '13800000000', 'admin@school.com', 'ADMIN', NULL, 1, CURRENT_TIMESTAMP),
(2, 'teacher1', '123456', '王芳', '13800000001', 'teacher1@school.com', 'TEACHER', NULL, 1, CURRENT_TIMESTAMP),
(3, 'teacher2', '123456', '李敏', '13800000002', 'teacher2@school.com', 'TEACHER', NULL, 1, CURRENT_TIMESTAMP),
(4, 'student1', '123456', '张三', '13900000001', 'student1@school.com', 'STUDENT', 1, 1, CURRENT_TIMESTAMP),
(5, 'student2', '123456', '李四', '13900000002', 'student2@school.com', 'STUDENT', 2, 1, CURRENT_TIMESTAMP);

INSERT INTO teacher_profile (id, user_id, teacher_no, subject_id, title_name, status) VALUES
(1, 2, 'T2026001', 2, '高级教师', 1),
(2, 3, 'T2026002', 3, '一级教师', 1);

INSERT INTO evaluation_indicator (id, indicator_name, dimension_name, weight_value, sort_no, status) VALUES
(1, '教学态度', '课堂表现', 20.00, 1, 1),
(2, '教学内容', '课堂表现', 20.00, 2, 1),
(3, '教学方法', '课堂表现', 20.00, 3, 1),
(4, '课堂管理', '课堂表现', 20.00, 4, 1),
(5, '作业反馈', '课后指导', 20.00, 5, 1);

INSERT INTO evaluation_task (id, task_name, term_name, class_id, teacher_id, start_time, end_time, status, creator_id, create_time, update_time) VALUES
(1, '高一数学第二阶段评教', '2025-2026学年第二学期', 1, 1, TIMESTAMP '2026-04-01 08:00:00', TIMESTAMP '2026-12-31 23:59:59', 'OPEN', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, '高一英语第一阶段评教', '2025-2026学年第一学期', 2, 2, TIMESTAMP '2026-01-01 08:00:00', TIMESTAMP '2026-01-20 23:59:59', 'CLOSED', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO evaluation_record (id, task_id, evaluator_id, teacher_id, class_id, attitude_score, content_score, method_score, manage_score, homework_score, total_score, comment_text, create_time, update_time) VALUES
(1, 2, 5, 2, 2, 90.00, 92.00, 91.00, 88.00, 90.00, 90.20, '课堂讲解清晰，互动性很好。', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO evaluation_appeal (id, record_id, task_id, teacher_id, reason_text, reply_text, status, handle_by, handle_time, create_time, update_time) VALUES
(1, 1, 2, 2, '希望补充说明该次课堂为复习课，评分依据应区分课堂类型。', '', 'WAITING', NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO evaluation_notice (id, title, content_text, status, publish_time, creator_id, create_time, update_time) VALUES
(1, '本周评教任务提醒', '请各班同学在截止时间前完成科任教师评教。', 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, '教师申诉流程说明', '教师可在记录页面针对异常评分提交申诉，由管理员统一处理。', 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

ALTER TABLE subject_info ALTER COLUMN id RESTART WITH 5;
ALTER TABLE teaching_class ALTER COLUMN id RESTART WITH 3;
ALTER TABLE sys_user ALTER COLUMN id RESTART WITH 6;
ALTER TABLE teacher_profile ALTER COLUMN id RESTART WITH 3;
ALTER TABLE evaluation_indicator ALTER COLUMN id RESTART WITH 6;
ALTER TABLE evaluation_task ALTER COLUMN id RESTART WITH 3;
ALTER TABLE evaluation_record ALTER COLUMN id RESTART WITH 2;
ALTER TABLE evaluation_appeal ALTER COLUMN id RESTART WITH 2;
ALTER TABLE evaluation_notice ALTER COLUMN id RESTART WITH 3;
