INSERT INTO admin (username, password, role) VALUES
('admin', '$2b$12$bbNLHZ6giRlQlAPsuZwqRuJR.V59XjSkK4T.yBhOa0LjSe0sAiyZG', 'ADMIN');

INSERT INTO user (phone, password, nickname, avatar, gender, points, level, balance) VALUES
('13800138001', '$2b$12$V0yE1ehQAHEXJKd4X2C3/uPWIAXqXy.VyvVfdfFqZcdILPHvodBze', '张三', 'https://via.placeholder.com/150', 1, 50, '普通会员', 500.00),
('13800138002', '$2b$12$V0yE1ehQAHEXJKd4X2C3/uPWIAXqXy.VyvVfdfFqZcdILPHvodBze', '李四', 'https://via.placeholder.com/150', 0, 800, '金卡会员', 1000.00);

INSERT INTO store (name, address, phone, open_time, close_time, intro, images, rating, longitude, latitude, status) VALUES
('时尚造型·市中心店', '北京市朝阳区建国路88号', '010-12345678', '09:00:00', '21:00:00',
 '专业美发连锁品牌，20年匠心品质，资深发型师团队为您打造专属造型。',
 '["https://via.placeholder.com/600x400","https://via.placeholder.com/600x400"]', 4.80, 116.4551000, 39.9195000, 1),
('潮流发艺·大学城店', '北京市海淀区中关村大街1号', '010-87654321', '10:00:00', '22:00:00',
 '年轻时尚理发店，专注潮流造型，学生价优惠多多。',
 '["https://via.placeholder.com/600x400"]', 4.60, 116.3107000, 39.9762000, 1);

INSERT INTO hairdresser (store_id, name, gender, work_no, avatar, skills, work_years, intro, rating, status) VALUES
(1, '王大师', 1, 'HS001', 'https://via.placeholder.com/150', '["剪发","染发","烫发","造型设计"]', 15, '从业15年，擅长日韩风格造型，多次获得全国美发大赛金奖。', 4.90, 1),
(1, '李小美', 0, 'HS002', 'https://via.placeholder.com/150', '["女士剪发","染发","烫发"]', 8, '专注女性造型8年，善于打造气质优雅发型。', 4.70, 1),
(1, '赵师傅', 1, 'HS003', 'https://via.placeholder.com/150', '["男士剪发","儿童剪发"]', 10, '男士理发专家，细致耐心，深受顾客好评。', 4.80, 1),
(2, '张潮流', 1, 'HS004', 'https://via.placeholder.com/150', '["潮流剪发","染发","烫发"]', 5, '紧跟时尚潮流，Instagram风格专家。', 4.50, 1),
(2, '刘艺术', 0, 'HS005', 'https://via.placeholder.com/150', '["女士剪发","造型设计"]', 6, '艺术造型师，擅长个性化设计。', 4.60, 1),
(2, '周设计', 1, 'HS006', 'https://via.placeholder.com/150', '["剪发","染发","造型"]', 7, '创意发型设计师，为您打造独特风格。', 4.70, 1);

INSERT INTO service (store_id, category, name, price, duration, description, image, status) VALUES
(1, '男士理发', '经典男士剪发', 58.00, 30, '专业男士发型修剪，含洗发、剪发、吹风造型', 'https://via.placeholder.com/400', 1),
(1, '男士理发', '时尚男士造型', 88.00, 45, '时尚潮流造型设计，含洗发、剪发、造型、吹风', 'https://via.placeholder.com/400', 1),
(1, '女士理发', '女士精剪', 98.00, 60, '女士专业剪发，含洗发、剪发、护理、造型', 'https://via.placeholder.com/400', 1),
(1, '烫染护理', '韩式烫发', 298.00, 120, '韩式流行烫发，使用进口药水，持久不伤发', 'https://via.placeholder.com/400', 1),
(1, '烫染护理', '时尚染发', 228.00, 90, '专业染发服务，多种流行色可选', 'https://via.placeholder.com/400', 1),
(1, '儿童理发', '儿童剪发', 38.00, 30, '专业儿童剪发，温柔细致，让宝宝不害怕', 'https://via.placeholder.com/400', 1),
(2, '男士理发', '学生价剪发', 28.00, 25, '学生专享优惠，凭学生证享受特价', 'https://via.placeholder.com/400', 1),
(2, '女士理发', '女士修剪', 68.00, 45, '女士修剪整理，简约时尚', 'https://via.placeholder.com/400', 1),
(2, '烫染护理', '潮流挑染', 188.00, 60, '个性挑染，打造独特风格', 'https://via.placeholder.com/400', 1),
(2, '造型设计', '特殊场合造型', 128.00, 60, '婚礼、毕业、面试等特殊场合造型设计', 'https://via.placeholder.com/400', 1);

INSERT INTO schedule (hairdresser_id, work_date, start_time, end_time, status) VALUES
(1, DATEADD('DAY', 1, CURRENT_DATE), '09:00:00', '18:00:00', 1),
(2, DATEADD('DAY', 1, CURRENT_DATE), '09:00:00', '18:00:00', 1),
(3, DATEADD('DAY', 1, CURRENT_DATE), '09:00:00', '18:00:00', 1),
(4, DATEADD('DAY', 1, CURRENT_DATE), '09:00:00', '18:00:00', 1),
(5, DATEADD('DAY', 1, CURRENT_DATE), '09:00:00', '18:00:00', 1),
(6, DATEADD('DAY', 1, CURRENT_DATE), '09:00:00', '18:00:00', 1);

INSERT INTO appointment (user_id, store_id, hairdresser_id, service_id, appointment_date, appointment_time, status) VALUES
(1, 1, 1, 2, DATEADD('DAY', 1, CURRENT_DATE), '14:00:00', '已确认'),
(2, 2, 4, 7, DATEADD('DAY', 2, CURRENT_DATE), '10:30:00', '待确认'),
(1, 2, 4, 8, DATEADD('DAY', -1, CURRENT_DATE), '11:00:00', '已完成');

INSERT INTO orders (order_no, appointment_id, user_id, store_id, hairdresser_id, service_name, original_price, discount, actual_price, pay_type, pay_status, pay_time) VALUES
('ORD202411270001', 1, 1, 1, 1, '时尚男士造型', 88.00, 1.00, 88.00, '余额支付', 1, CURRENT_TIMESTAMP),
('ORD202411270002', 3, 1, 2, 4, '女士修剪', 68.00, 1.00, 68.00, NULL, 0, NULL);

INSERT INTO review (order_id, user_id, hairdresser_id, store_id, rating, content, tags) VALUES
(1, 1, 1, 1, 5, '王大师手艺非常好，剪的发型很满意，服务态度也很棒！', '["手艺好","态度好","环境佳"]');

INSERT INTO points_log (user_id, points, reason) VALUES
(1, 10, '注册奖励'),
(1, 5, '每日签到'),
(1, 88, '消费获得积分'),
(1, 10, '评价获得积分'),
(2, 10, '注册奖励'),
(2, 5, '每日签到');

INSERT INTO balance_log (user_id, amount, type, reason) VALUES
(1, 500.00, '充值', '余额充值'),
(1, -88.00, '消费', '支付订单ORD202411270001'),
(2, 1000.00, '充值', '余额充值');

INSERT INTO notification (user_id, type, title, content, is_read) VALUES
(1, '预约确认', '预约确认通知', '您的预约已确认，预约时间：明天14:00，理发师：王大师', 1),
(1, '积分变动', '积分增加', '恭喜您获得88积分，消费1元=1积分', 0),
(2, '预约提醒', '预约待确认', '您有一个新预约待确认，请尽快处理', 0);
