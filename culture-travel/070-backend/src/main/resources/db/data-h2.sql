INSERT INTO user (id, username, password, nickname, phone, email, balance, role, status) VALUES
(1, 'admin', '123456', '系统管理员', '13800000001', 'admin@ticket.com', 5000.00, 'ADMIN', 1),
(2, 'user', '123456', '普通用户', '13800000002', 'user@ticket.com', 500.00, 'USER', 1),
(3, 'test', '123456', '测试用户', '13800000003', 'test@ticket.com', 300.00, 'USER', 1);

INSERT INTO movie (id, title, type, category, duration, director, actors, poster, description, rating, comment_count, status, is_recommend, release_date) VALUES
(1, '星际迷航：新纪元', 'MOVIE', '科幻', 148, '詹姆斯·卡梅隆', '克里斯·帕拉特,佐伊·索尔达娜', 'https://picsum.photos/280/380?1', '宇宙探索题材科幻大片。', 8.8, 2, 'ON', 1, DATE '2026-05-15'),
(2, '笑傲江湖2026', 'MOVIE', '喜剧', 120, '周星驰', '沈腾,马丽', 'https://picsum.photos/280/380?2', '古装喜剧电影。', 7.9, 1, 'ON', 1, DATE '2026-05-18'),
(3, '暗影追踪', 'MOVIE', '动作', 135, '克里斯托弗·诺兰', '莱昂纳多·迪卡普里奥', 'https://picsum.photos/280/380?3', '动作悬疑类型电影。', 8.6, 1, 'ON', 0, DATE '2026-05-20'),
(4, '音乐会之夜', 'SHOW', '演出', 150, '国家交响乐团', '首席乐团', 'https://picsum.photos/280/380?4', '交响音乐会演出。', 9.2, 0, 'ON', 1, DATE '2026-05-22');

INSERT INTO cinema (id, name, address, phone, business_hours, facilities, status) VALUES
(1, '万达影城中心店', '市中心商业广场3楼', '010-88881111', '09:00-23:30', 'IMAX,杜比全景声,停车场', 1),
(2, '金逸影城大学城店', '大学城商业街B区2楼', '010-88882222', '10:00-23:00', 'VIP厅,取票机,餐饮区', 1),
(3, 'CGV影城科技园店', '科技园创业大厦负一楼', '010-88883333', '09:30-22:30', '巨幕厅,停车场', 1);

INSERT INTO hall (id, cinema_id, name, type, total_seats, seat_rows, seat_cols, status) VALUES
(1, 1, '1号厅', 'IMAX', 120, 10, 12, 1),
(2, 1, '2号厅', 'NORMAL', 80, 8, 10, 1),
(3, 2, '1号厅', 'VIP', 60, 6, 10, 1),
(4, 3, '1号厅', 'GIANT', 140, 10, 14, 1);

INSERT INTO showtime (id, movie_id, hall_id, cinema_id, start_time, end_time, price, status, available_seats) VALUES
(1, 1, 1, 1, TIMESTAMP '2026-05-24 10:00:00', TIMESTAMP '2026-05-24 12:28:00', 79.90, 'SELLING', 120),
(2, 1, 2, 1, TIMESTAMP '2026-05-24 14:00:00', TIMESTAMP '2026-05-24 16:28:00', 59.90, 'SELLING', 80),
(3, 2, 3, 2, TIMESTAMP '2026-05-24 10:30:00', TIMESTAMP '2026-05-24 12:30:00', 68.00, 'SELLING', 60),
(4, 3, 4, 3, TIMESTAMP '2026-05-24 13:00:00', TIMESTAMP '2026-05-24 15:15:00', 89.00, 'SELLING', 140),
(5, 4, 1, 1, TIMESTAMP '2026-05-25 19:00:00', TIMESTAMP '2026-05-25 21:30:00', 120.00, 'SELLING', 120);

INSERT INTO coupon (id, name, type, discount_type, discount_value, min_amount, total_count, used_count, start_time, end_time, status) VALUES
(1, '新用户立减20元', 'GENERAL', 'AMOUNT', 20.00, 80.00, 1000, 2, TIMESTAMP '2026-04-01 00:00:00', TIMESTAMP '2026-06-30 23:59:59', 1),
(2, '9折观影券', 'GENERAL', 'RATE', 0.90, 50.00, 2000, 1, TIMESTAMP '2026-04-01 00:00:00', TIMESTAMP '2026-06-15 23:59:59', 1),
(3, '周末满200减50', 'WEEKEND', 'AMOUNT', 50.00, 200.00, 500, 0, TIMESTAMP '2026-04-20 00:00:00', TIMESTAMP '2026-06-30 23:59:59', 1);

INSERT INTO user_coupon (id, user_id, coupon_id, status, expire_time) VALUES
(1, 2, 1, 'UNUSED', TIMESTAMP '2026-06-30 23:59:59'),
(2, 2, 2, 'USED', TIMESTAMP '2026-06-15 23:59:59'),
(3, 3, 1, 'UNUSED', TIMESTAMP '2026-06-30 23:59:59');

INSERT INTO ticket_order (id, order_no, user_id, showtime_id, movie_title, cinema_name, hall_name, show_time, seat_info, total_amount, discount_amount, pay_amount, coupon_id, status, pay_time) VALUES
(1, 'TK202604300001', 2, 2, '星际迷航：新纪元', '万达影城中心店', '2号厅', TIMESTAMP '2026-05-24 14:00:00', '3排5座,3排6座', 119.80, 20.00, 99.80, 1, 'PAID', CURRENT_TIMESTAMP),
(2, 'TK202604300002', 3, 3, '笑傲江湖2026', '金逸影城大学城店', '1号厅', TIMESTAMP '2026-05-24 10:30:00', '2排1座,2排2座', 136.00, 0.00, 136.00, NULL, 'WAIT_PAY', NULL);

INSERT INTO payment (id, order_id, pay_no, pay_type, pay_amount, status, pay_time) VALUES
(1, 1, 'PAY202604300001', 'BALANCE', 99.80, 'SUCCESS', CURRENT_TIMESTAMP),
(2, 2, 'PAY202604300002', 'BALANCE', 0.00, 'WAIT', NULL);

INSERT INTO ticket (id, order_id, ticket_no, qr_code, status) VALUES
(1, 1, 'ET202604300001', 'QRCODE-ET202604300001', 'UNUSED'),
(2, 1, 'ET202604300002', 'QRCODE-ET202604300002', 'UNUSED');

INSERT INTO comment (id, movie_id, user_id, order_id, rating, content, status) VALUES
(1, 1, 2, 1, 9, '特效和音效都非常震撼。', 'APPROVED'),
(2, 2, 3, NULL, 8, '笑点很多，适合周末放松。', 'PENDING');

ALTER TABLE user ALTER COLUMN id RESTART WITH 4;
ALTER TABLE movie ALTER COLUMN id RESTART WITH 5;
ALTER TABLE cinema ALTER COLUMN id RESTART WITH 4;
ALTER TABLE hall ALTER COLUMN id RESTART WITH 5;
ALTER TABLE showtime ALTER COLUMN id RESTART WITH 6;
ALTER TABLE seat ALTER COLUMN id RESTART WITH 1;
ALTER TABLE ticket_order ALTER COLUMN id RESTART WITH 3;
ALTER TABLE ticket ALTER COLUMN id RESTART WITH 3;
ALTER TABLE payment ALTER COLUMN id RESTART WITH 3;
ALTER TABLE comment ALTER COLUMN id RESTART WITH 3;
ALTER TABLE coupon ALTER COLUMN id RESTART WITH 4;
ALTER TABLE user_coupon ALTER COLUMN id RESTART WITH 4;
