INSERT INTO user (id, member_no, username, password, nickname, phone, email, balance, member_level, points, total_recharge, total_consume, role, status) VALUES
(1, 'M202605030001', 'admin', '123456', '平台管理员', '13800000001', 'admin@cinema.com', 8000.00, 'ADMIN', 5800, 12000.00, 5800.00, 'ADMIN', 1),
(2, 'M202605030002', 'staff', '123456', '门店运营', '13800000002', 'staff@cinema.com', 1800.00, 'STAFF', 960, 3000.00, 960.00, 'STAFF', 1),
(3, 'M202605030003', 'member', '123456', '白银会员', '13800000003', 'member@cinema.com', 420.00, 'SILVER', 860, 1200.00, 860.00, 'MEMBER', 1),
(4, 'M202605030004', 'vip01', '123456', '黄金会员', '13800000004', 'vip01@cinema.com', 980.00, 'GOLD', 2640, 3800.00, 2640.00, 'MEMBER', 1),
(5, 'M202605030005', 'member2', '123456', '钻石会员', '13800000005', 'member2@cinema.com', 5000.00, 'DIAMOND', 6180, 8000.00, 6180.00, 'MEMBER', 1);

INSERT INTO movie (id, title, type, category, duration, director, actors, poster, description, rating, comment_count, status, is_recommend, release_date) VALUES
(1, '流浪地心计划', 'MOVIE', '科幻', 132, '韩延', '吴京,刘昊然', 'https://picsum.photos/280/380?cinema091-1', '适合会员包场活动的年度科幻大片。', 9.1, 1, 'ON', 1, '2026-04-01'),
(2, '喜剧合伙人', 'MOVIE', '喜剧', 118, '闫非', '沈腾,马丽', 'https://picsum.photos/280/380?cinema091-2', '适合家庭会员观影的合家欢喜剧。', 8.4, 1, 'ON', 1, '2026-04-20'),
(3, '深海行动', 'MOVIE', '动作', 126, '林超贤', '彭于晏,王一博', 'https://picsum.photos/280/380?cinema091-3', '高口碑动作商业片。', 8.7, 1, 'ON', 0, '2026-04-05'),
(4, '艺术放映厅：经典修复', 'MOVIE', '文艺', 110, '电影资料馆', '修复专场', 'https://picsum.photos/280/380?cinema091-4', '会员专享艺术电影展映场。', 9.3, 0, 'ON', 1, '2026-04-10');

INSERT INTO cinema (id, name, address, phone, business_hours, facilities, status) VALUES
(1, '耀光影城中心店', '滨江大道88号三层', '010-88881111', '09:00-23:30', 'IMAX,杜比全景声,会员休息区,停车场', 1),
(2, '星河影城大学城店', '大学城文创广场二层', '010-88882222', '10:00-23:00', 'VIP厅,自助取票机,主题餐饮区', 1),
(3, '云幕影城科技园店', '科技园创新中心负一层', '010-88883333', '09:30-22:30', '巨幕厅,咖啡吧,停车场', 1);

INSERT INTO hall (id, cinema_id, name, type, total_seats, seat_rows, seat_cols, status) VALUES
(1, 1, 'IMAX厅', 'IMAX', 120, 10, 12, 1),
(2, 1, '杜比厅', 'DOLBY', 80, 8, 10, 1),
(3, 2, 'VIP厅', 'VIP', 60, 6, 10, 1),
(4, 3, '巨幕厅', 'GIANT', 140, 10, 14, 1);

INSERT INTO showtime (id, movie_id, hall_id, cinema_id, start_time, end_time, price, status, available_seats) VALUES
(1, 1, 1, 1, '2026-06-18 19:20:00', '2026-06-18 21:32:00', 79.90, 'SELLING', 120),
(2, 2, 2, 1, '2026-06-18 20:00:00', '2026-06-18 21:58:00', 59.90, 'SELLING', 80),
(3, 3, 3, 2, '2026-06-19 18:30:00', '2026-06-19 20:36:00', 68.00, 'SELLING', 60),
(4, 4, 4, 3, '2026-06-19 19:10:00', '2026-06-19 21:00:00', 88.00, 'SELLING', 140),
(5, 1, 1, 1, '2026-06-20 14:00:00', '2026-06-20 16:12:00', 75.00, 'SELLING', 120);

INSERT INTO coupon (id, name, type, discount_type, discount_value, min_amount, total_count, used_count, start_time, end_time, status) VALUES
(1, '新会员首单立减20元', 'NEW_MEMBER', 'AMOUNT', 20.00, 80.00, 2000, 1, '2026-05-01 00:00:00', '2026-07-30 23:59:59', 1),
(2, '黄金会员9折券', 'LEVEL', 'RATE', 0.90, 60.00, 1500, 1, '2026-05-01 00:00:00', '2026-07-15 23:59:59', 1),
(3, '储值满500赠50', 'RECHARGE', 'AMOUNT', 50.00, 120.00, 800, 1, '2026-05-10 00:00:00', '2026-07-30 23:59:59', 1);

INSERT INTO user_coupon (id, user_id, coupon_id, status, expire_time) VALUES
(1, 3, 1, 'UNUSED', '2026-07-30 23:59:59'),
(2, 4, 2, 'UNUSED', '2026-07-15 23:59:59'),
(3, 5, 3, 'USED', '2026-07-30 23:59:59');

INSERT INTO ticket_order (id, order_no, user_id, showtime_id, movie_title, cinema_name, hall_name, show_time, seat_info, total_amount, discount_amount, pay_amount, coupon_id, status, pay_time) VALUES
(1, 'TK202605010001', 3, 1, '流浪地心计划', '耀光影城中心店', 'IMAX厅', '2026-06-18 19:20:00', NULL, 159.80, 20.00, 139.80, 1, 'PAID', '2026-05-01 20:10:00'),
(2, 'TK202605020002', 4, 3, '深海行动', '星河影城大学城店', 'VIP厅', '2026-06-19 18:30:00', NULL, 136.00, 0.00, 136.00, NULL, 'FINISHED', '2026-05-02 12:30:00'),
(3, 'TK202605020003', 5, 4, '艺术放映厅：经典修复', '云幕影城科技园店', '巨幕厅', '2026-06-19 19:10:00', NULL, 176.00, 50.00, 126.00, 3, 'WAIT_PAY', NULL);

INSERT INTO payment (id, user_id, order_id, pay_no, pay_type, pay_amount, status, pay_time) VALUES
(1, 3, 1, 'PAY202605010001', 'BALANCE', 139.80, 'SUCCESS', '2026-05-01 20:10:00'),
(2, 4, 2, 'PAY202605020002', 'BALANCE', 136.00, 'SUCCESS', '2026-05-02 12:30:00'),
(3, 5, 3, 'PAY202605020003', 'BALANCE', 126.00, 'WAIT', NULL),
(4, 3, NULL, 'PAY202605010101', 'RECHARGE', 500.00, 'SUCCESS', '2026-05-01 09:20:00'),
(5, 5, NULL, 'PAY202605020201', 'RECHARGE', 2000.00, 'SUCCESS', '2026-05-02 18:00:00');

INSERT INTO ticket (id, order_id, ticket_no, qr_code, status) VALUES
(1, 1, 'ET202605010001', 'QRCODE-ET202605010001', 'UNUSED'),
(2, 2, 'ET202605020002', 'QRCODE-ET202605020002', 'USED');

INSERT INTO comment (id, movie_id, user_id, order_id, rating, content, status) VALUES
(1, 1, 3, 1, 9, '会员日活动力度大，IMAX 体验也很稳。', 'APPROVED'),
(2, 3, 4, 2, 8, 'VIP厅环境不错，适合高频会员复购。', 'APPROVED'),
(3, 4, 5, NULL, 9, '艺术专场适合做高等级会员专享活动。', 'PENDING');
