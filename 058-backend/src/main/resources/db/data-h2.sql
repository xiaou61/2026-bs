INSERT INTO user (username, password, nickname, phone, role, status) VALUES
('admin', '123456', '系统管理员', '13800000001', 'ADMIN', 1),
('delivery', '123456', '配送员张三', '13800000002', 'DELIVERY', 1),
('delivery2', '123456', '配送员李四', '13800000003', 'DELIVERY', 1),
('user', '123456', '用户王五', '13800000004', 'USER', 1),
('user2', '123456', '用户赵六', '13800000005', 'USER', 1),
('disabled', '123456', '禁用用户', '13800000006', 'USER', 0);

INSERT INTO milk_category (name, sort, status) VALUES
('鲜牛奶', 1, 1),
('酸奶', 2, 1),
('低脂奶', 3, 1),
('有机奶', 4, 1),
('儿童奶', 5, 1);

INSERT INTO milk_product (category_id, name, image, price, unit, spec, description, stock, status) VALUES
(1, '每日鲜纯牛奶', '/images/milk1.jpg', 6.50, '瓶', '250ml', '新鲜牧场直供，当日挤奶当日配送', 500, 1),
(1, '高钙鲜牛奶', '/images/milk2.jpg', 7.80, '瓶', '250ml', '富含钙质，适合全家饮用', 300, 1),
(2, '原味酸奶', '/images/milk3.jpg', 5.50, '杯', '200ml', '优质奶源发酵，口感醇厚', 400, 1),
(2, '草莓酸奶', '/images/milk4.jpg', 6.00, '杯', '200ml', '新鲜草莓果粒，酸甜可口', 350, 1),
(3, '低脂鲜牛奶', '/images/milk5.jpg', 8.00, '瓶', '250ml', '脂肪含量仅1.5%，健康之选', 200, 1);

INSERT INTO address (user_id, contact_name, contact_phone, province, city, district, detail, is_default) VALUES
(4, '王五', '13800000004', '浙江省', '杭州市', '西湖区', '文三路100号1栋2单元301', 1),
(4, '王五', '13800000004', '浙江省', '杭州市', '西湖区', '天目山路200号3栋1单元502', 0),
(5, '赵六', '13800000005', '浙江省', '杭州市', '滨江区', '江南大道500号5栋3单元801', 1);

INSERT INTO delivery_area (name, province, city, district, status) VALUES
('西湖区配送区', '浙江省', '杭州市', '西湖区', 1),
('滨江区配送区', '浙江省', '杭州市', '滨江区', 1),
('拱墅区配送区', '浙江省', '杭州市', '拱墅区', 1);

INSERT INTO delivery_route (name, area_id, delivery_user_id, description, status) VALUES
('西湖区A线', 1, 2, '文三路-天目山路沿线', 1),
('西湖区B线', 1, 2, '学院路-教工路沿线', 1),
('滨江区A线', 2, 3, '江南大道-滨盛路沿线', 1);

INSERT INTO subscription (user_id, product_id, address_id, type, quantity, delivery_time, week_days, start_date, end_date, status) VALUES
(4, 1, 1, 'DAILY', 2, 'MORNING', NULL, '2026-01-01', '2026-06-30', 1),
(4, 3, 1, 'WEEKLY', 3, 'AFTERNOON', '1,3,5', '2026-01-01', '2026-06-30', 1),
(5, 2, 3, 'MONTHLY', 30, 'MORNING', NULL, '2026-02-01', '2026-12-31', 1);

INSERT INTO milk_order (order_no, user_id, subscription_id, product_id, quantity, total_price, address, contact_name, contact_phone, delivery_date, delivery_time, status) VALUES
('MO202601010001', 4, 1, 1, 2, 13.00, '浙江省杭州市西湖区文三路100号1栋2单元301', '王五', '13800000004', '2026-01-15', 'MORNING', 2),
('MO202601010002', 4, 2, 3, 3, 16.50, '浙江省杭州市西湖区文三路100号1栋2单元301', '王五', '13800000004', '2026-01-15', 'AFTERNOON', 2),
('MO202601020001', 4, 1, 1, 2, 13.00, '浙江省杭州市西湖区文三路100号1栋2单元301', '王五', '13800000004', '2026-01-16', 'MORNING', 0),
('MO202601020002', 5, 3, 2, 30, 234.00, '浙江省杭州市滨江区江南大道500号5栋3单元801', '赵六', '13800000005', '2026-01-16', 'MORNING', 1);

INSERT INTO delivery_record (order_id, delivery_user_id, route_id, status, remark, delivery_time) VALUES
(1, 2, 1, 1, NULL, '2026-01-15 07:30:00'),
(2, 2, 1, 1, NULL, '2026-01-15 14:20:00'),
(3, 2, 1, 0, NULL, NULL),
(4, 3, 3, 0, NULL, NULL);

INSERT INTO complaint (user_id, order_id, type, content, status, reply, reply_time) VALUES
(4, 1, 'COMPLAINT', '牛奶包装有破损', 1, '非常抱歉给您带来不便，我们已安排补发', '2026-01-16 10:00:00'),
(5, 4, 'FEEDBACK', '希望增加早上6点的配送时段', 0, NULL, NULL);

INSERT INTO notification (user_id, title, content, type, is_read) VALUES
(4, '订阅成功通知', '您已成功订阅每日鲜纯牛奶，配送将于明日开始', 'SUBSCRIPTION', 1),
(4, '配送完成通知', '您的订单MO202601010001已配送完成', 'DELIVERY', 1),
(5, '欢迎使用', '欢迎使用鲜牛奶订购系统', 'SYSTEM', 0);

INSERT INTO payment (order_id, user_id, amount, pay_type, pay_no, status, pay_time) VALUES
(1, 4, 13.00, '微信支付', 'WX20260115001', 1, '2026-01-14 20:00:00'),
(2, 4, 16.50, '支付宝', 'ZFB20260115001', 1, '2026-01-14 20:05:00'),
(3, 4, 13.00, '微信支付', 'WX20260116001', 1, '2026-01-15 20:00:00'),
(4, 5, 234.00, '微信支付', 'WX20260201001', 1, '2026-02-01 08:00:00');
