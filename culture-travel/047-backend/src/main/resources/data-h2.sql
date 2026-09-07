INSERT INTO sys_user (id, username, password, real_name, phone, role, status) VALUES
(1, 'admin', '123456', '管理员', '13800000000', 3, 1),
(2, 'shop1', '123456', '张店长', '13800000001', 1, 1),
(3, 'shop2', '123456', '李店长', '13800000002', 1, 1),
(4, 'author1', '123456', '王作者', '13800000003', 2, 1),
(5, 'author2', '123456', '刘作者', '13800000004', 2, 1),
(6, 'user1', '123456', '玩家小明', '13800000005', 0, 1),
(7, 'user2', '123456', '玩家小红', '13800000006', 0, 1);

INSERT INTO shop (id, owner_id, name, description, address, phone, business_hours, room_count, rating, status) VALUES
(1, 2, '迷雾剧本杀', '专业沉浸式剧本杀体验馆', '北京市朝阳区星河街100号', '010-12345678', '14:00-23:00', 6, 4.8, 1),
(2, 3, '推理世界', '推理爱好者的天堂', '上海市浦东新区推理路88号', '021-87654321', '13:00-22:00', 4, 4.6, 1);

INSERT INTO room (id, shop_id, name, capacity, description, price, status) VALUES
(1, 1, '迷雾厅', 8, '欧式复古风格', 50.00, 1),
(2, 1, '暗夜厅', 6, '暗黑哥特风格', 60.00, 1),
(3, 1, '阳光厅', 10, '明亮温馨风格', 45.00, 1),
(4, 2, '福尔摩斯厅', 6, '英伦侦探风格', 55.00, 1),
(5, 2, '东方快车厅', 8, '列车主题包厢', 65.00, 1);

INSERT INTO script_category (id, name, description, sort, status) VALUES
(1, '情感本', '以情感体验为主的剧本', 1, 1),
(2, '推理本', '以逻辑推理为主的剧本', 2, 1),
(3, '恐怖本', '惊悚恐怖题材的剧本', 3, 1),
(4, '欢乐本', '轻松搞笑的剧本', 4, 1),
(5, '机制本', '以游戏机制为主的剧本', 5, 1);

INSERT INTO script (id, author_id, category_id, title, description, difficulty, player_count, duration, type, price, view_count, like_count, status) VALUES
(1, 4, 1, '年轮', '一段跨越时空的爱情故事', 2, '6人', '4-5小时', 1, 2000.00, 1580, 326, 1),
(2, 4, 2, '迷雾庄园', '古老庄园中的连环谋杀案', 3, '7人', '5-6小时', 2, 2500.00, 2340, 512, 1),
(3, 5, 3, '午夜来电', '一通神秘电话打破平静', 2, '5-6人', '3-4小时', 3, 1800.00, 890, 178, 1),
(4, 5, 4, '欢乐农场', '一场关于农场的搞笑闹剧', 1, '6-8人', '3小时', 4, 1500.00, 1200, 289, 1),
(5, 4, 2, '古董局中局', '围绕神秘古董展开的智力较量', 3, '6人', '4-5小时', 2, 2200.00, 1890, 423, 1);

INSERT INTO script_content (id, script_id, chapter_name, role_name, content, sort) VALUES
(1, 1, '第一幕', '旁白', '多年之后，旧友重逢，尘封的秘密再次浮出水面。', 1),
(2, 2, '序章', '侦探', '迷雾庄园的钟声响起，第一位受害者倒在大厅中央。', 1);

INSERT INTO shop_script (id, shop_id, script_id, price, play_count, status) VALUES
(1, 1, 1, 268.00, 45, 1),
(2, 1, 2, 328.00, 32, 1),
(3, 1, 3, 238.00, 28, 1),
(4, 2, 2, 358.00, 56, 1),
(5, 2, 5, 298.00, 41, 1);

INSERT INTO reservation (id, order_no, user_id, shop_id, room_id, script_id, reservation_date, time_slot, player_count, contact_name, contact_phone, total_price, status) VALUES
(1, 'RES20240115001', 6, 1, 1, 1, '2024-01-20', '14:00-18:00', 6, '小明', '13800000005', 268.00, 3),
(2, 'RES20240116001', 7, 1, 2, 3, '2024-01-21', '19:00-22:00', 5, '小红', '13800000006', 238.00, 1),
(3, 'RES20240117001', 6, 2, 4, 2, '2024-01-22', '14:00-19:00', 7, '小明', '13800000005', 358.00, 0);

INSERT INTO review (id, user_id, reservation_id, shop_id, script_id, rating, content) VALUES
(1, 6, 1, 1, 1, 5, '非常棒的体验！DM带入感很强，剧本情感丰富！'),
(2, 7, NULL, 1, NULL, 4, '店铺装修很有氛围，服务不错');

INSERT INTO favorite (id, user_id, type, target_id) VALUES
(1, 6, 1, 1),
(2, 6, 1, 2),
(3, 6, 2, 1),
(4, 7, 1, 3),
(5, 7, 2, 2);

INSERT INTO notice (id, title, content, type, publish_time, status) VALUES
(1, '平台上线公告', '欢迎来到剧本杀创作与预约平台！', 1, CURRENT_TIMESTAMP, 1),
(2, '新剧本上架活动', '本周新上架5部精品剧本，首单立减20元！', 2, CURRENT_TIMESTAMP, 1);

ALTER TABLE sys_user ALTER COLUMN id RESTART WITH 8;
ALTER TABLE shop ALTER COLUMN id RESTART WITH 3;
ALTER TABLE room ALTER COLUMN id RESTART WITH 6;
ALTER TABLE script_category ALTER COLUMN id RESTART WITH 6;
ALTER TABLE script ALTER COLUMN id RESTART WITH 6;
ALTER TABLE script_content ALTER COLUMN id RESTART WITH 3;
ALTER TABLE shop_script ALTER COLUMN id RESTART WITH 6;
ALTER TABLE reservation ALTER COLUMN id RESTART WITH 4;
ALTER TABLE review ALTER COLUMN id RESTART WITH 3;
ALTER TABLE favorite ALTER COLUMN id RESTART WITH 6;
ALTER TABLE notice ALTER COLUMN id RESTART WITH 3;
