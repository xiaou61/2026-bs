MERGE INTO user KEY(id) VALUES
(1, 'admin', '21232f297a57a5a743894a0e4a801fc3', '系统管理员', 'admin@example.com', '13800000000', '研究生', '计算机科学', 1, 0.80, '晚间', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
MERGE INTO user KEY(id) VALUES
(2, 'student1', 'e10adc3949ba59abbe56e057f20f883e', '张三', 'zhangsan@example.com', '13900000001', '大三', '软件工程', 1, 0.60, '上午', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
MERGE INTO user KEY(id) VALUES
(3, 'student2', 'e10adc3949ba59abbe56e057f20f883e', '李四', 'lisi@example.com', '13900000002', '大二', '计算机科学', 2, 0.55, '下午', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO course KEY(id) VALUES
(1, 'Java基础编程', 'CS001', 'Java语言基础知识学习，包括语法、面向对象等。', 101, 2, 480, NULL, '掌握Java语法、面向对象思想和基础开发能力。', 1, 1, CURRENT_TIMESTAMP);
MERGE INTO course KEY(id) VALUES
(2, '数据结构与算法', 'CS002', '学习常用的数据结构和算法设计方法。', 102, 3, 720, '[1]', '理解典型数据结构并具备基础算法分析能力。', 1, 1, CURRENT_TIMESTAMP);
MERGE INTO course KEY(id) VALUES
(3, 'Web前端开发', 'CS003', 'HTML、CSS、JavaScript等前端技术学习。', 103, 2, 600, NULL, '完成前端页面开发和基础交互实现。', 1, 1, CURRENT_TIMESTAMP);
MERGE INTO course KEY(id) VALUES
(4, '数据库原理', 'CS004', '关系数据库理论基础和SQL语言。', 104, 3, 540, NULL, '掌握数据库设计和 SQL 查询能力。', 2, 1, CURRENT_TIMESTAMP);
MERGE INTO course KEY(id) VALUES
(5, '机器学习入门', 'CS005', '机器学习基础概念和常用算法。', 105, 4, 900, '[2,4]', '理解监督学习、特征工程和模型评估。', 3, 1, CURRENT_TIMESTAMP);

MERGE INTO knowledge_point KEY(id) VALUES
(1, 'Java语法基础', 'JAVA_001', 1, NULL, 'Java基本语法、变量、数据类型。', 1, 0.90, 'Java,语法,变量,数据类型', 60, CURRENT_TIMESTAMP);
MERGE INTO knowledge_point KEY(id) VALUES
(2, '面向对象编程', 'JAVA_002', 1, NULL, '类、对象、继承、多态、封装。', 2, 0.95, '面向对象,类,对象,继承,多态', 90, CURRENT_TIMESTAMP);
MERGE INTO knowledge_point KEY(id) VALUES
(3, '数组与链表', 'DS_001', 2, NULL, '数组和链表的实现与应用。', 2, 0.80, '数组,链表,数据结构', 80, CURRENT_TIMESTAMP);
MERGE INTO knowledge_point KEY(id) VALUES
(4, '排序算法', 'DS_002', 2, NULL, '冒泡排序、快速排序、归并排序等。', 3, 0.85, '排序,算法,冒泡,快排', 100, CURRENT_TIMESTAMP);
MERGE INTO knowledge_point KEY(id) VALUES
(5, 'HTML基础', 'WEB_001', 3, NULL, 'HTML标签和文档结构。', 1, 0.70, 'HTML,标签,网页', 45, CURRENT_TIMESTAMP);

UPDATE knowledge_point SET create_time = CURRENT_TIMESTAMP WHERE create_time IS NULL;

MERGE INTO knowledge_relation KEY(id) VALUES
(1, 1, 2, 1, 0.80, 0.90, CURRENT_TIMESTAMP);
MERGE INTO knowledge_relation KEY(id) VALUES
(2, 3, 4, 1, 0.70, 0.80, CURRENT_TIMESTAMP);
MERGE INTO knowledge_relation KEY(id) VALUES
(3, 2, 3, 3, 0.60, 0.70, CURRENT_TIMESTAMP);

MERGE INTO learning_record KEY(id) VALUES
(1001, 1, 1, 1, 1, TIMESTAMP '2026-04-06 09:00:00', TIMESTAMP '2026-04-06 10:30:00', 5400, 92.00, 0.88, 91.00, 12, 4800);
MERGE INTO learning_record KEY(id) VALUES
(1002, 1, 2, 3, 2, TIMESTAMP '2026-04-07 14:00:00', TIMESTAMP '2026-04-07 15:40:00', 6000, 75.00, 0.67, 78.00, 18, 5100);
MERGE INTO learning_record KEY(id) VALUES
(1003, 1, 3, 5, 1, TIMESTAMP '2026-04-08 10:00:00', TIMESTAMP '2026-04-08 11:10:00', 4200, 60.00, 0.58, 80.00, 9, 3600);
MERGE INTO learning_record KEY(id) VALUES
(1004, 2, 1, 2, 1, TIMESTAMP '2026-04-07 19:00:00', TIMESTAMP '2026-04-07 20:00:00', 3600, 85.00, 0.73, 86.00, 11, 3200);
MERGE INTO learning_record KEY(id) VALUES
(1005, 3, 4, NULL, 4, TIMESTAMP '2026-04-05 20:00:00', TIMESTAMP '2026-04-05 21:00:00', 3600, 68.00, 0.62, 79.00, 7, 2900);

MERGE INTO recommendation KEY(id) VALUES
(2001, 1, 1, 5, 1, 0.923, 'v1', FALSE, FALSE, NULL, CURRENT_TIMESTAMP);
MERGE INTO recommendation KEY(id) VALUES
(2002, 1, 1, 4, 2, 0.811, 'v1', TRUE, FALSE, 4, CURRENT_TIMESTAMP);
MERGE INTO recommendation KEY(id) VALUES
(2003, 1, 2, 4, 2, 0.768, 'v1', FALSE, FALSE, NULL, CURRENT_TIMESTAMP);

MERGE INTO qa_record KEY(id) VALUES
(3001, 1, '什么是面向对象编程？', '面向对象,编程', '根据相关资料，面向对象编程强调以对象为中心，通过封装、继承和多态组织代码。建议您结合 Java 基础课程继续学习。', 0.91, '2', 'session_demo_1', TRUE, 820, CURRENT_TIMESTAMP);

MERGE INTO learning_path KEY(id) VALUES
(4001, 1, '学习路径 - 排序算法', 4, '[{\"knowledgePointId\":1,\"pointName\":\"Java语法基础\",\"difficultyLevel\":1,\"estimatedTime\":60,\"completed\":true},{\"knowledgePointId\":2,\"pointName\":\"面向对象编程\",\"difficultyLevel\":2,\"estimatedTime\":90,\"completed\":true},{\"knowledgePointId\":3,\"pointName\":\"数组与链表\",\"difficultyLevel\":2,\"estimatedTime\":80,\"completed\":false},{\"knowledgePointId\":4,\"pointName\":\"排序算法\",\"difficultyLevel\":3,\"estimatedTime\":100,\"completed\":false}]', 6, 1, 50.00, 'dijkstra_optimized', 0.872, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO user_behavior KEY(id) VALUES
(5001, 1, 'login', 'page', NULL, '{\"source\":\"login\"}', 'session_demo_1', '127.0.0.1', 'Mozilla/5.0', CURRENT_TIMESTAMP);
MERGE INTO user_behavior KEY(id) VALUES
(5002, 1, 'page_view', 'course', 1, '{\"pageName\":\"Java基础编程\"}', 'session_demo_1', '127.0.0.1', 'Mozilla/5.0', CURRENT_TIMESTAMP);
MERGE INTO user_behavior KEY(id) VALUES
(5003, 1, 'study', 'course', 2, '{\"duration\":6000}', 'session_demo_1', '127.0.0.1', 'Mozilla/5.0', CURRENT_TIMESTAMP);
