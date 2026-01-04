INSERT INTO users (username, password, real_name, email, phone, role, status) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '管理员', 'admin@example.com', '13800138000', 'ADMIN', 'ACTIVE'),
('user1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '张三', 'user1@example.com', '13800138001', 'USER', 'ACTIVE'),
('org1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '爱心组织', 'org1@example.com', '13800138002', 'ORGANIZATION', 'ACTIVE');

INSERT INTO donation_projects (title, description, category, target_amount, current_amount, start_date, end_date, organization_name, location, status, creator_id, donor_count) VALUES
('山区儿童助学计划', '为贫困山区的孩子们提供学习用品和教育资源，让每一个孩子都能接受良好的教育。', '教育助学', 100000.00, 35000.00, NOW(), NOW() + INTERVAL '90 days', '希望工程基金会', '云南省', 'ACTIVE', 3, 120),
('关爱留守儿童', '为留守儿童提供心理辅导、课外活动和生活关怀，让他们感受到社会的温暖。', '儿童关爱', 50000.00, 28000.00, NOW(), NOW() + INTERVAL '60 days', '儿童关爱协会', '四川省', 'ACTIVE', 3, 85),
('灾区重建援助', '为受灾地区提供紧急救援物资和重建资金，帮助灾民重建家园。', '灾害救助', 200000.00, 150000.00, NOW() - INTERVAL '30 days', NOW() + INTERVAL '30 days', '红十字会', '河南省', 'ACTIVE', 1, 350),
('关爱空巢老人', '为独居老人提供生活照料、医疗保健和精神慰藉服务。', '敬老助老', 80000.00, 45000.00, NOW(), NOW() + INTERVAL '120 days', '夕阳红公益组织', '北京市', 'ACTIVE', 3, 200),
('环境保护行动', '组织环保志愿者清理河道、植树造林，保护我们的生态环境。', '环境保护', 60000.00, 38000.00, NOW(), NOW() + INTERVAL '180 days', '绿色地球协会', '浙江省', 'ACTIVE', 3, 160),
('贫困家庭医疗援助', '为贫困家庭提供医疗费用援助，让他们能够及时得到治疗。', '医疗救助', 120000.00, 72000.00, NOW(), NOW() + INTERVAL '90 days', '医疗救助基金', '广东省', 'ACTIVE', 1, 280);

INSERT INTO donations (user_id, project_id, amount, message, anonymous, payment_method, transaction_id, payment_status, payment_time) VALUES
(2, 1, 100.00, '希望孩子们能好好学习', FALSE, 'ALIPAY', 'TXN001', 'SUCCESS', NOW()),
(2, 2, 200.00, '关爱留守儿童，人人有责', FALSE, 'WECHAT', 'TXN002', 'SUCCESS', NOW()),
(2, 3, 500.00, '愿灾区人民早日重建家园', TRUE, 'ALIPAY', 'TXN003', 'SUCCESS', NOW());

INSERT INTO project_progress (project_id, title, content) VALUES
(1, '第一批学习用品已发放', '我们已经为200名山区儿童发放了书包、文具和课外书籍，孩子们非常开心。'),
(1, '新学期助学金发放', '第二批助学金已经发放给50名特困学生，帮助他们顺利完成学业。'),
(3, '首批救援物资到达', '包含食品、饮用水、帐篷等救援物资已送达灾区，正在有序发放中。');
