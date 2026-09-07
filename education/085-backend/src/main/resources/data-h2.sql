INSERT INTO sys_user (username, password, real_name, phone, role, status) VALUES
('admin', '123456', '系统管理员', '13800000001', 'admin', 1),
('teacher', '123456', '王老师', '13800000002', 'teacher', 1),
('student', '123456', '张同学', '13800000003', 'student', 1),
('student2', '123456', '李同学', '13800000004', 'student', 1);

INSERT INTO course_category (parent_id, name, code, sort, status) VALUES
(0, '基础数学', 'BASIC_MATH', 1, 1),
(0, '应用数学', 'APPLIED_MATH', 2, 1),
(0, '数学建模', 'MATH_MODELING', 3, 1);

INSERT INTO math_course (course_name, course_code, category_id, teacher_id, credit, term, description, status) VALUES
('高等数学A', 'MATH101', 1, 2, 4.00, '2025-2026-2', '面向工科专业的高等数学基础课程', 1),
('线性代数', 'MATH102', 1, 2, 3.00, '2025-2026-2', '向量空间、矩阵及线性变换', 1),
('概率论与数理统计', 'MATH201', 2, 2, 3.00, '2025-2026-2', '概率模型、常见分布与统计推断', 1);

INSERT INTO eval_indicator (indicator_name, weight, sort, status, description) VALUES
('教学内容清晰度', 30.00, 1, 1, '知识点结构清晰、重点突出'),
('授课方法与节奏', 25.00, 2, 1, '讲授方式合理、节奏适中'),
('课堂互动效果', 20.00, 3, 1, '能有效引导提问与讨论'),
('作业与反馈质量', 15.00, 4, 1, '作业安排合理且反馈及时'),
('总体满意度', 10.00, 5, 1, '课程整体体验与收获');

INSERT INTO eval_task (task_name, course_id, term, start_time, end_time, status, creator_id) VALUES
('高等数学A-2025秋学期期中评价', 1, '2025-2026-2', DATEADD('DAY', -10, CURRENT_TIMESTAMP), DATEADD('DAY', 20, CURRENT_TIMESTAMP), 1, 1),
('线性代数-2025秋学期期中评价', 2, '2025-2026-2', DATEADD('DAY', -10, CURRENT_TIMESTAMP), DATEADD('DAY', 20, CURRENT_TIMESTAMP), 1, 1),
('概率论-2025秋学期期中评价', 3, '2025-2026-2', DATEADD('DAY', -10, CURRENT_TIMESTAMP), DATEADD('DAY', 20, CURRENT_TIMESTAMP), 1, 1);

INSERT INTO eval_record (task_id, course_id, student_id, total_score, comment_content, submit_time) VALUES
(1, 1, 3, 91.50, '课程讲解清晰，建议增加随堂练习', DATEADD('DAY', -2, CURRENT_TIMESTAMP)),
(1, 1, 4, 88.00, '整体不错，希望增加难题讲评', DATEADD('DAY', -1, CURRENT_TIMESTAMP)),
(2, 2, 3, 86.50, '板书清晰，课堂互动可以更多', DATEADD('DAY', -1, CURRENT_TIMESTAMP));

INSERT INTO eval_record_item (record_id, indicator_id, score) VALUES
(1, 1, 93), (1, 2, 90), (1, 3, 92), (1, 4, 89), (1, 5, 95),
(2, 1, 90), (2, 2, 87), (2, 3, 86), (2, 4, 88), (2, 5, 89),
(3, 1, 88), (3, 2, 85), (3, 3, 84), (3, 4, 87), (3, 5, 89);

INSERT INTO system_notice (title, content, type, status, publisher_id, publish_time, view_count) VALUES
('数学课程评价系统上线通知', '请同学们在规定时间内完成课程评价，评价结果将用于教学改进。', 'system', 1, 1, CURRENT_TIMESTAMP, 18),
('评价填写说明', '每个指标按0-100分填写，建议附带改进建议。', 'guide', 1, 1, CURRENT_TIMESTAMP, 25);

INSERT INTO operation_log (user_id, module, action, content) VALUES
(1, 'task', 'create', '创建评价任务: 高等数学A-2025秋学期期中评价'),
(3, 'evaluation', 'submit', '提交课程评价: 高等数学A');
