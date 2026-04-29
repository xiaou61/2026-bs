INSERT INTO user (username, password, nickname, phone, email, role, status, balance) VALUES
('admin', '123456', '系统管理员', '13800000001', 'admin@gametrade.com', 'ADMIN', 1, 99999.00),
('user1', '123456', '峡谷商人', '13800000002', 'user1@gametrade.com', 'USER', 1, 3000.00),
('user2', '123456', '江湖倒爷', '13800000003', 'user2@gametrade.com', 'USER', 1, 2800.00),
('user3', '123456', '冒险买家', '13800000004', 'user3@gametrade.com', 'USER', 1, 1200.00);

INSERT INTO game_category (name, icon, sort, status) VALUES
('MOBA', '/images/category/moba.png', 1, 1),
('FPS', '/images/category/fps.png', 2, 1),
('MMORPG', '/images/category/mmorpg.png', 3, 1),
('卡牌', '/images/category/card.png', 4, 1),
('沙盒', '/images/category/sandbox.png', 5, 1),
('体育竞技', '/images/category/sport.png', 6, 1);

INSERT INTO game_item (category_id, seller_id, title, game_name, server_name, item_type, price, stock, cover, description, trade_mode, view_count, sold_count, status) VALUES
(1, 2, '王者荣耀 国服百星号', '王者荣耀', '微信区', '账号', 1299.00, 1, '/images/item/1.jpg', '全英雄皮肤丰富，实名认证可改', '账号换绑', 458, 2, 1),
(2, 3, 'CS2 爪子刀 渐变之色', 'CS2', '国际服', '饰品', 3588.00, 1, '/images/item/2.jpg', '可直接走平台库存，支持验货', '平台库存', 732, 3, 1),
(3, 2, '梦幻西游 109神威大唐', '梦幻西游', '2008区', '账号', 2400.00, 1, '/images/item/3.jpg', '装备宝宝齐全，支持视频验号', '账号换绑', 339, 1, 1),
(4, 3, '炉石传说 传说卡背合集号', '炉石传说', '亚服', '账号', 666.00, 1, '/images/item/4.jpg', '多赛季传说，经典皮肤齐全', '账号换绑', 221, 1, 1),
(5, 2, '我的世界 Java正版激活码', '我的世界', '国际版', '激活码', 188.00, 20, '/images/item/5.jpg', '付款后自动发货，秒到', '自动发货', 509, 50, 1),
(1, 3, '王者荣耀 荣耀典藏皮肤代抽服务', '王者荣耀', 'QQ区', '代练服务', 299.00, 30, '/images/item/6.jpg', '7天内完成，失败可退款', '服务订单', 612, 18, 1),
(6, 2, 'FC24 终极球队金币 500K', 'FC24', 'PS5', '金币', 350.00, 15, '/images/item/7.jpg', '安全转移，实时在线', '平台库存', 177, 8, 1),
(3, 3, '剑网3 电五区稀有外观账号', '剑网3', '电信五区', '账号', 980.00, 1, '/images/item/8.jpg', '外观挂件丰富，历史干净', '账号换绑', 205, 0, 1);

INSERT INTO trade_order (order_no, user_id, item_id, seller_id, quantity, total_price, status, remark, pay_time, finish_time, create_time) VALUES
('GT202604290001', 4, 5, 2, 2, 376.00, 3, '自动发货，已验收', '2026-04-29 10:00:00', '2026-04-29 10:15:00', '2026-04-29 09:59:00'),
('GT202604290002', 4, 2, 3, 1, 3588.00, 2, '等待卖家交割', '2026-04-29 11:20:00', NULL, '2026-04-29 11:18:00'),
('GT202604290003', 2, 6, 3, 1, 299.00, 1, '服务排期中', '2026-04-29 12:35:00', NULL, '2026-04-29 12:33:00');

INSERT INTO review (order_id, user_id, item_id, rating, content, status, create_time) VALUES
(1, 4, 5, 5, '发货很快，价格也合适', 1, '2026-04-29 10:20:00');

INSERT INTO favorite (user_id, item_id) VALUES
(4, 1),
(4, 2),
(2, 5);

INSERT INTO complaint (order_id, user_id, target_user_id, type, content, images, status, reply, reply_admin_id, reply_time, create_time) VALUES
(2, 4, 3, 'ORDER', '卖家发货超时，申请平台介入', '', 1, '已联系卖家，24小时内处理', 1, '2026-04-29 16:20:00', '2026-04-29 15:10:00');

INSERT INTO announcement (title, content, admin_id, status) VALUES
('五一活动上线', '5月平台手续费限时8折，活动期内下单自动生效。', 1, 1),
('交易安全提醒', '请勿通过站外转账，所有交易请走平台订单。', 1, 1),
('实名认证升级通知', '5月20日起高价值账号交易需完成实名验证。', 1, 1);
