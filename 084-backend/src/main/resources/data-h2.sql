INSERT INTO sys_user (username, password, real_name, phone, role, status) VALUES
('admin', '123456', '系统管理员', '13800000001', 'admin', 1),
('teacher', '123456', '张老师', '13800000002', 'teacher', 1),
('student', '123456', '李同学', '13800000003', 'student', 1);

INSERT INTO material_category (parent_id, name, code, sort, status) VALUES
(0, '课件资料', 'COURSEWARE', 1, 1),
(0, '试题题库', 'QUESTION_BANK', 2, 1),
(0, '实验指导', 'LAB_GUIDE', 3, 1),
(0, '教学视频', 'VIDEO', 4, 1);

INSERT INTO material_tag (name, color, status) VALUES
('期末复习', '#409EFF', 1),
('重点难点', '#E6A23C', 1),
('实验必做', '#67C23A', 1),
('历年真题', '#F56C6C', 1);

INSERT INTO material_info (title, summary, category_id, grade, subject, keyword, uploader_id, audit_status, publish_status, download_count, favorite_count, status) VALUES
('数据结构期末复习讲义', '覆盖链表、树、图、排序核心知识点', 1, '2023级', '数据结构', '数据结构,复习,期末', 2, 1, 1, 32, 12, 1),
('Java程序设计实验指导书', '包含12个实验任务与评分标准', 3, '2024级', 'Java程序设计', 'Java,实验,指导书', 2, 1, 1, 21, 7, 1),
('数据库系统历年真题', '整理近五年数据库课程期末真题', 2, '2022级', '数据库系统', '数据库,真题,题库', 2, 0, 0, 5, 2, 1);

INSERT INTO material_file (material_id, file_name, file_path, file_size, file_type) VALUES
(1, '数据结构期末复习讲义.pdf', '/upload/material/1/review.pdf', 2536142, 'pdf'),
(2, 'Java实验指导书.docx', '/upload/material/2/lab.docx', 3215641, 'docx'),
(3, '数据库历年真题.zip', '/upload/material/3/questions.zip', 5412361, 'zip');

INSERT INTO material_tag_rel (material_id, tag_id) VALUES
(1, 1),
(1, 2),
(2, 3),
(3, 4);

INSERT INTO material_audit (material_id, audit_status, audit_remark, auditor_id) VALUES
(1, 1, '资料完整，审核通过', 1),
(2, 1, '实验步骤清晰，审核通过', 1),
(3, 0, '待审核', 1);

INSERT INTO material_download (material_id, user_id, source) VALUES
(1, 3, 'web'),
(1, 3, 'web'),
(2, 3, 'web'),
(2, 3, 'web'),
(1, 2, 'web');

INSERT INTO material_favorite (material_id, user_id) VALUES
(1, 3),
(2, 3);

INSERT INTO study_list (user_id, material_id, progress, note, status) VALUES
(3, 1, 60, '本周完成树与图章节', 1),
(3, 2, 30, '完成实验一到实验三', 1);

INSERT INTO system_notice (title, content, type, status, publisher_id, publish_time, view_count) VALUES
('教学资料上传规范', '上传资料请统一命名，并附摘要说明。', 'system', 1, 1, CURRENT_TIMESTAMP, 15),
('期末复习资料专区上线', '已上线期末复习专区，请同学们按需下载。', 'activity', 1, 1, CURRENT_TIMESTAMP, 28);

INSERT INTO operation_log (user_id, module, action, content) VALUES
(1, 'material', 'audit', '审核通过: 数据结构期末复习讲义'),
(2, 'material', 'upload', '上传: Java程序设计实验指导书');
