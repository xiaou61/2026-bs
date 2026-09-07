INSERT INTO sys_user (id, username, password, nickname, role, status) VALUES
(1, 'admin', '123456', '管理员', 2, 1),
(2, 'teacher1', '123456', '张老师', 1, 1),
(3, 'teacher2', '123456', '李老师', 1, 1);

INSERT INTO sys_user (id, username, password, nickname, role, target_school, target_major, exam_year, points, study_days, status) VALUES
(4, 'student1', '123456', '考研小明', 0, '清华大学', '计算机科学与技术', 2026, 100, 30, 1),
(5, 'student2', '123456', '学习小红', 0, '北京大学', '软件工程', 2026, 80, 20, 1);

INSERT INTO subject (id, name, code, icon, sort_order, status) VALUES
(1, '政治', 'politics', '政', 1, 1),
(2, '英语一', 'english1', '英', 2, 1),
(3, '英语二', 'english2', '英', 3, 1),
(4, '数学一', 'math1', '数', 4, 1),
(5, '数学二', 'math2', '数', 5, 1),
(6, '数学三', 'math3', '数', 6, 1),
(7, '计算机专业课', 'cs', '计', 7, 1),
(8, '管理学', 'management', '管', 8, 1);

INSERT INTO course (id, subject_id, teacher_id, title, cover, description, price, is_free, chapter_count, student_count, view_count, status) VALUES
(1, 1, 2, '考研政治精讲班', '', '全面讲解考研政治各模块知识点，包括马原、毛中特、史纲、思修、形势与政策。', 0, 1, 10, 1520, 8500, 1),
(2, 2, 3, '考研英语一核心词汇', '', '精选考研英语一高频核心词汇，配套科学记忆方法。', 0, 1, 20, 2300, 12000, 1),
(3, 4, 2, '考研数学一全程班', '', '高等数学、线性代数、概率论与数理统计全面覆盖。', 199, 0, 50, 890, 5600, 1),
(4, 7, 3, '408计算机综合精讲', '', '数据结构、计算机组成原理、操作系统、计算机网络系统精讲。', 299, 0, 40, 560, 3200, 1);

INSERT INTO chapter (course_id, title, video_url, duration, sort_order, is_free) VALUES
(1, '第一章 马克思主义基本原理概论', '/video/politics/1.mp4', 3600, 1, 1),
(1, '第二章 毛泽东思想和中国特色社会主义理论体系概论', '/video/politics/2.mp4', 5400, 2, 0),
(1, '第三章 中国近现代史纲要', '/video/politics/3.mp4', 4200, 3, 0),
(2, '第一章 考研英语词汇导学', '/video/english/1.mp4', 2400, 1, 1),
(2, '第二章 核心词汇A-C', '/video/english/2.mp4', 3600, 2, 0),
(3, '第一章 函数、极限与连续', '/video/math/1.mp4', 5400, 1, 1),
(3, '第二章 一元函数微分学', '/video/math/2.mp4', 7200, 2, 0),
(4, '第一章 数据结构导论', '/video/cs/1.mp4', 3600, 1, 1),
(4, '第二章 线性表', '/video/cs/2.mp4', 5400, 2, 0);

INSERT INTO question_category (id, subject_id, name, parent_id, sort_order) VALUES
(1, 1, '马克思主义基本原理', 0, 1),
(2, 1, '毛中特', 0, 2),
(3, 1, '中国近现代史纲要', 0, 3),
(4, 2, '阅读理解', 0, 1),
(5, 2, '完型填空', 0, 2),
(6, 2, '翻译', 0, 3),
(7, 4, '高等数学', 0, 1),
(8, 4, '线性代数', 0, 2),
(9, 4, '概率论', 0, 3),
(10, 7, '数据结构', 0, 1),
(11, 7, '计算机组成原理', 0, 2),
(12, 7, '操作系统', 0, 3),
(13, 7, '计算机网络', 0, 4);

INSERT INTO question (id, category_id, subject_id, type, difficulty, content, options, answer, analysis, source) VALUES
(1, 1, 1, 1, 1, '马克思主义哲学认为，世界的真正统一性在于它的（）', '["A.物质性","B.运动性","C.客观性","D.可知性"]', 'A', '马克思主义哲学认为世界的真正统一性在于它的物质性。', '2020年真题'),
(2, 1, 1, 1, 2, '实践的基本形式包括（）', '["A.生产实践","B.社会实践","C.科学实验","D.以上都是"]', 'D', '实践的基本形式包括生产实践、社会实践和科学实验。', '2021年真题'),
(3, 7, 4, 1, 2, '设函数f(x)在点x=0处连续，且lim(x→0)[f(x)/x]=1，则（）', '["A.f(0)=0","B.f(0)=1","C.f′(0)=1","D.f′(0)不存在"]', 'A', '由极限存在且分母趋于0可知分子也趋于0。', '2019年真题'),
(4, 10, 7, 1, 1, '在一个长度为n的顺序表中，向第i个位置插入一个元素，需要移动的元素个数为（）', '["A.n-i","B.n-i+1","C.n-i-1","D.i"]', 'B', '在第i个位置插入，需要将第i到第n个元素后移。', '基础题'),
(5, 10, 7, 1, 2, '栈和队列的主要区别是（）', '["A.逻辑结构不同","B.存储结构不同","C.所包含的运算不同","D.插入和删除运算的位置不同"]', 'D', '栈是后进先出，队列是先进先出。', '基础题');

INSERT INTO study_plan (user_id, title, subject_id, target_content, start_date, end_date, daily_hours, status, progress) VALUES
(4, '政治基础阶段复习', 1, '完成马原、毛中特基础知识学习', '2026-03-01', '2026-06-30', 2, 0, 30),
(4, '英语词汇强化', 2, '背诵核心词汇5500个', '2026-03-01', '2026-08-31', 1.5, 0, 45),
(5, '数学一全程复习', 4, '高数、线代、概率论系统复习', '2026-04-01', '2026-12-01', 4, 0, 20);

INSERT INTO daily_checkin (user_id, checkin_date, study_duration, question_count, correct_count, note, mood) VALUES
(4, CURRENT_DATE, 180, 50, 42, '今天学习了马原第一章，感觉良好', 4),
(4, DATEADD('DAY', -1, CURRENT_DATE), 150, 40, 35, '英语单词背了100个', 3),
(5, CURRENT_DATE, 240, 30, 25, '数学做了几道大题，有点难', 3);

INSERT INTO note (user_id, subject_id, title, content, is_public, like_count, view_count) VALUES
(4, 1, '马原重点知识点总结', '世界的物质性、实践与认识论、真理检验标准。', 1, 25, 150),
(5, 4, '高数极限计算方法', '直接代入、分解因式、有理化、洛必达与等价无穷小替换。', 1, 18, 120);

INSERT INTO post (id, user_id, subject_id, title, content, view_count, like_count, comment_count, status) VALUES
(1, 4, 1, '求问：马原哲学部分怎么背？', '马原的哲学部分内容太多了，大家有什么好的记忆方法吗？', 230, 15, 1, 1),
(2, 5, 4, '分享：数学一140+经验', '复习了一年，数学一考了142分，分享一下我的复习经验和资料。', 560, 45, 2, 1),
(3, 4, 2, '英语一阅读技巧分享', '总结了一些阅读理解的做题技巧，希望对大家有帮助。', 180, 12, 0, 1);

INSERT INTO comment (user_id, post_id, parent_id, content, like_count) VALUES
(5, 1, 0, '建议先理解再记忆，可以画思维导图。', 5),
(4, 2, 0, '太厉害了，请问高数用的什么资料？', 3),
(5, 2, 2, '我用的是张宇的全套，从基础到强化。', 2);

INSERT INTO favorite (user_id, target_type, target_id) VALUES
(4, 1, 1),
(4, 2, 1),
(5, 3, 2);

INSERT INTO notice (title, content, type, is_top, status) VALUES
('2026年考研大纲已发布', '2026年全国硕士研究生招生考试大纲已发布，请同学们及时关注变化。', 2, 1, 1),
('系统更新通知', '系统已完成升级，新增错题本智能复习功能，欢迎体验。', 1, 0, 1),
('国庆学习打卡活动', '国庆期间连续打卡7天，可获得100积分奖励。', 3, 0, 1);
