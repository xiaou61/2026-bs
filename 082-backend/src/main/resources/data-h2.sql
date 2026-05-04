INSERT INTO user (id, username, password, real_name, phone, role, status, create_time, update_time) VALUES
(1, 'admin', '123456', '系统管理员', '13800000001', 'admin', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'teacher', '123456', '张老师', '13800000002', 'teacher', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'user', '123456', '李同学', '13800000003', 'student', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO subject (id, name, code, description, sort, status, create_time, update_time) VALUES
(1, '行政职业能力测验', 'XINGCE', '行测模块题型训练', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, '申论', 'SHENLUN', '申论阅读与写作能力提升', 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, '面试', 'MIANSHI', '结构化面试与无领导训练', 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO course (id, subject_id, title, teacher_id, level, summary, study_hours, status, create_time, update_time) VALUES
(1, 1, '行测言语理解高频考点', 2, '中级', '覆盖逻辑填空、片段阅读核心技巧', 24, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, '行测判断推理提分课', 2, '高级', '图形推理、定义判断专项训练', 30, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, '申论大作文写作训练营', 2, '中级', '从立意到结构的完整写作方法', 20, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO course_chapter (id, course_id, title, content, sort, status, create_time, update_time) VALUES
(1, 1, '词语辨析基础', '掌握高频近义词辨析方法', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, '片段阅读主旨题', '快速定位中心句与干扰项排除', 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 3, '申论审题与立意', '建立材料阅读与观点提炼框架', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO question_bank (id, subject_id, name, type, total_count, difficulty, status, create_time, update_time) VALUES
(1, 1, '行测单选题库A', 'single', 3, '中等', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, '申论判断题库', 'judge', 1, '基础', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO question (id, bank_id, subject_id, type, stem, option_a, option_b, option_c, option_d, answer, analysis, score, difficulty, status, create_time, update_time) VALUES
(1, 1, 1, 'single', '下列词语中没有错别字的是？', '融会贯通', '再接再励', '迫不急待', '一愁莫展', 'A', 'B应为再接再厉，C应为迫不及待，D应为一筹莫展', 2.00, '基础', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 1, 'single', '某次数列规律推理题答案是？', '12', '15', '18', '21', 'C', '按等差递增规律得到18', 2.00, '中等', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1, 1, 'single', '逻辑判断：若所有A是B，部分B是C，则可推出？', '所有A是C', '部分A是C', '无法必然推出', '所有C是A', 'C', '前提不足以推出A与C关系', 2.00, '中等', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 2, 2, 'judge', '申论大作文只需要观点正确，不需要结构。', NULL, NULL, NULL, NULL, '0', '申论写作对结构完整性要求很高', 1.00, '基础', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO exam_paper (id, subject_id, title, duration_minutes, total_score, pass_score, question_count, publish_status, start_time, end_time, create_time, update_time) VALUES
(1, 1, '行测模拟卷一', 120, 100.00, 60.00, 3, 1, TIMESTAMP '2026-03-01 08:00:00', TIMESTAMP '2026-12-31 23:59:59', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, '申论基础测评卷', 150, 100.00, 60.00, 1, 1, TIMESTAMP '2026-03-01 08:00:00', TIMESTAMP '2026-12-31 23:59:59', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO exam_paper_question (id, paper_id, question_id, sort, score) VALUES
(1, 1, 1, 1, 30.00),
(2, 1, 2, 2, 35.00),
(3, 1, 3, 3, 35.00),
(4, 2, 4, 1, 100.00);

INSERT INTO exam_record (id, paper_id, user_id, start_time, submit_time, duration_seconds, total_score, objective_score, subjective_score, pass_status, status, create_time, update_time) VALUES
(1, 1, 3, TIMESTAMP '2026-03-02 09:00:00', TIMESTAMP '2026-03-02 10:30:00', 5400, 72.00, 72.00, 0.00, 1, 'submitted', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 3, TIMESTAMP '2026-03-03 09:00:00', TIMESTAMP '2026-03-03 10:40:00', 6000, 58.00, 58.00, 0.00, 0, 'submitted', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO exam_record_answer (id, record_id, paper_id, question_id, user_answer, correct_answer, score, is_correct, create_time) VALUES
(1, 1, 1, 1, 'A', 'A', 30.00, 1, CURRENT_TIMESTAMP),
(2, 1, 1, 2, 'C', 'C', 35.00, 1, CURRENT_TIMESTAMP),
(3, 1, 1, 3, 'B', 'C', 7.00, 0, CURRENT_TIMESTAMP),
(4, 2, 2, 4, '1', '0', 0.00, 0, CURRENT_TIMESTAMP);

INSERT INTO study_plan (id, user_id, subject_id, title, start_date, end_date, daily_target, completed_days, status, create_time, update_time) VALUES
(1, 3, 1, '30天行测冲刺计划', DATE '2026-03-01', DATE '2026-03-30', '每天完成60题并复盘错题', 6, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 3, 2, '申论写作提升计划', DATE '2026-03-01', DATE '2026-03-31', '每周2篇大作文练习', 4, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO notice (id, title, content, type, status, publisher_id, publish_time, view_count, create_time, update_time) VALUES
(1, '3月模考安排通知', '本月每周六晚8点统一进行线上模考，请提前15分钟入场。', 'exam', 1, 1, CURRENT_TIMESTAMP, 56, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, '申论批改服务上线', '申论作业提交后48小时内完成批改反馈。', 'service', 1, 1, CURRENT_TIMESTAMP, 33, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
