INSERT INTO user (username, password, real_name, phone, email, role, status) VALUES
('admin', '123456', '系统管理员', '13800138000', 'admin@charity.com', 'admin', 1),
('donor', '123456', '爱心人士', '13800138001', 'donor@charity.com', 'donor', 1),
('volunteer', '123456', '志愿者', '13800138002', 'volunteer@charity.com', 'volunteer', 1),
('donor2', '123456', '企业捐赠', '13800138003', 'donor2@charity.com', 'donor', 1);

INSERT INTO child (name, gender, birth_date, id_card, province, city, district, address, school, grade, family_situation, health_status, sponsor_status) VALUES
('小明', '男', '2012-05-15', '510101201205150011', '四川省', '成都市', '金牛区', '某某村1号', '金牛小学', '五年级', '父母务农，家庭困难', '健康', 0),
('小红', '女', '2013-08-20', '510101201308200022', '四川省', '成都市', '武侯区', '某某村2号', '武侯小学', '四年级', '单亲家庭，母亲务工', '健康', 1),
('小刚', '男', '2011-03-10', '510101201103100033', '四川省', '绵阳市', '涪城区', '某某村3号', '涪城小学', '六年级', '父亲残疾，母亲务农', '健康', 1),
('小丽', '女', '2014-11-25', '510101201411250044', '四川省', '德阳市', '旌阳区', '某某村4号', '旌阳小学', '三年级', '孤儿，由祖父母抚养', '健康', 0),
('小华', '男', '2012-07-08', '510101201207080055', '四川省', '乐山市', '市中区', '某某村5号', '乐山小学', '五年级', '父母外出务工，留守儿童', '健康', 0);

INSERT INTO donor (user_id, donor_type, company_name, contact_person, contact_phone, total_amount, donation_count) VALUES
(2, 'personal', NULL, '爱心人士', '13800138001', 5000.00, 2),
(4, 'enterprise', '爱心企业有限公司', '张经理', '13800138003', 50000.00, 5);

INSERT INTO application (child_id, apply_reason, required_amount, apply_status, reviewer_id, review_time, review_comment) VALUES
(1, '家庭困难，需要学费资助', 3000.00, 1, 1, '2024-01-15 10:30:00', '审核通过'),
(4, '孤儿，需要生活费资助', 5000.00, 1, 1, '2024-01-16 14:20:00', '审核通过'),
(5, '留守儿童，需要教育资助', 2000.00, 0, NULL, NULL, NULL);

INSERT INTO donation (donor_id, child_id, project_id, amount, donation_type, donation_date, payment_method, certificate_no, status) VALUES
(1, 2, NULL, 2000.00, 'money', '2024-01-20', 'alipay', 'CERT20240120001', 1),
(1, 3, NULL, 3000.00, 'money', '2024-02-10', 'wechat', 'CERT20240210001', 1),
(2, NULL, 1, 20000.00, 'money', '2024-01-25', 'bank', 'CERT20240125001', 1),
(2, NULL, 1, 30000.00, 'money', '2024-02-15', 'bank', 'CERT20240215001', 1);

INSERT INTO project (project_name, project_desc, target_amount, current_amount, start_date, end_date, project_status, cover_image) VALUES
('春蕾计划', '资助贫困地区女童完成学业', 100000.00, 50000.00, '2024-01-01', '2024-12-31', 1, NULL),
('温暖冬天', '为贫困儿童提供冬季衣物', 50000.00, 30000.00, '2024-11-01', '2025-02-28', 1, NULL),
('希望书屋', '为山区学校建设图书室', 80000.00, 20000.00, '2024-03-01', '2024-09-30', 1, NULL);

INSERT INTO fund_record (record_type, amount, related_id, related_type, purpose, operator_id, record_date, remark) VALUES
('income', 2000.00, 1, 'donation', '个人捐赠', 1, '2024-01-20', '支付宝转账'),
('income', 3000.00, 2, 'donation', '个人捐赠', 1, '2024-02-10', '微信转账'),
('income', 20000.00, 3, 'donation', '企业捐赠', 1, '2024-01-25', '银行转账'),
('expense', 2000.00, 2, 'donation', '资助小红学费', 1, '2024-01-22', '已发放'),
('expense', 3000.00, 3, 'donation', '资助小刚学费', 1, '2024-02-12', '已发放');

INSERT INTO feedback (feedback_type, child_id, donor_id, content) VALUES
('letter', 2, 1, '亲爱的叔叔阿姨，感谢您的资助，我一定会好好学习！'),
('letter', 3, 1, '谢谢您的帮助，让我能够继续上学，我会努力的！'),
('message', NULL, 1, '很高兴能帮助到这些孩子，希望他们健康成长！');

INSERT INTO growth_record (child_id, record_date, record_type, content, recorder_id) VALUES
(2, '2024-01-25', 'study', '期末考试成绩优秀，数学95分，语文92分', 3),
(2, '2024-02-01', 'life', '积极参加学校活动，表现良好', 3),
(3, '2024-01-28', 'study', '学习态度认真，成绩稳步提升', 3);

INSERT INTO announcement (title, content, announcement_type, publish_status, view_count, publisher_id, publish_time) VALUES
('2024年春季资助计划启动', '我们的春季资助计划正式启动，欢迎爱心人士参与...', 'news', 1, 150, 1, '2024-01-10 09:00:00'),
('资助管理办法', '为规范资助流程，特制定本管理办法...', 'policy', 1, 80, 1, '2024-01-05 10:00:00'),
('春节慰问活动通知', '春节将至，我们将组织志愿者进行慰问活动...', 'notice', 1, 120, 1, '2024-01-20 14:00:00');

INSERT INTO sponsor_relation (child_id, donor_id, sponsor_type, sponsor_amount, start_date, end_date, status) VALUES
(2, 1, 'yearly', 2000.00, '2024-01-20', '2024-12-31', 1),
(3, 1, 'yearly', 3000.00, '2024-02-10', '2024-12-31', 1);

INSERT INTO statistics (stat_date, total_children, sponsored_children, total_donors, total_amount, total_projects) VALUES
('2024-01-31', 5, 2, 2, 25000.00, 3),
('2024-02-29', 5, 2, 2, 55000.00, 3);
