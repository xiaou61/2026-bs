INSERT INTO user (id, username, password, nickname, phone, email, balance, role, status) VALUES
(1, 'admin', '$2a$12$e77kHVxuze/VuQObbJwQfu/KTRO1RcfNjLJyzm6BWBquk8AaS5oTK', '系统管理员', '13800000001', 'admin@railway.com', 5000.00, 'ADMIN', 1),
(2, 'dispatcher', '$2a$12$e77kHVxuze/VuQObbJwQfu/KTRO1RcfNjLJyzm6BWBquk8AaS5oTK', '调度员', '13800000002', 'dispatcher@railway.com', 2000.00, 'DISPATCHER', 1),
(3, 'user', '$2a$12$e77kHVxuze/VuQObbJwQfu/KTRO1RcfNjLJyzm6BWBquk8AaS5oTK', '普通乘客', '13800000003', 'user@railway.com', 800.00, 'USER', 1),
(4, 'user2', '$2a$12$e77kHVxuze/VuQObbJwQfu/KTRO1RcfNjLJyzm6BWBquk8AaS5oTK', '其他乘客', '13800000005', 'user2@railway.com', 500.00, 'USER', 1);

INSERT INTO station (id, station_code, station_name, city, province, address, status) VALUES
(1, 'BJP', '北京南', '北京', '北京', '丰台区永外车站路12号', 1),
(2, 'TJP', '天津西', '天津', '天津', '红桥区西站前街1号', 1),
(3, 'JNP', '济南西', '济南', '山东', '槐荫区齐州路6号', 1),
(4, 'SHH', '上海虹桥', '上海', '上海', '闵行区申虹路1500号', 1);

INSERT INTO train_info (id, train_code, train_name, train_type, carriage_count, seat_count, cover_url, description, status) VALUES
(1, 'G101', '京沪高速线', '高铁', 4, 80, 'https://picsum.photos/640/320?railway-1', '北京南至上海虹桥高铁线路', 1),
(2, 'G125', '京津济联程线', '高铁', 3, 60, 'https://picsum.photos/640/320?railway-2', '北京南经天津西至济南西高速线路', 1),
(3, 'D221', '华东城际线', '动车', 2, 40, 'https://picsum.photos/640/320?railway-3', '济南西至上海虹桥城际动车', 1);

INSERT INTO carriage_template (id, template_name, seat_type, coach_count, seat_rows, seat_cols, price_factor, status) VALUES
(1, '高铁二等座模板', '二等座', 4, 10, 5, 1.00, 1),
(2, '高铁商务座模板', '商务座', 2, 8, 4, 1.60, 1),
(3, '动车二等座模板', '二等座', 2, 10, 4, 0.88, 1);

INSERT INTO train_schedule (id, train_id, carriage_id, departure_station_id, arrival_station_id, travel_date, departure_time, arrival_time, base_price, total_seats, available_seats, sale_status, status) VALUES
(1, 1, 1, 1, 4, DATE '2026-05-20', TIMESTAMP '2026-05-20 08:00:00', TIMESTAMP '2026-05-20 12:38:00', 553.50, 200, 198, 'ON_SALE', 1),
(2, 2, 1, 1, 3, DATE '2026-05-20', TIMESTAMP '2026-05-20 09:20:00', TIMESTAMP '2026-05-20 11:45:00', 214.00, 200, 200, 'ON_SALE', 1),
(3, 3, 3, 3, 4, DATE '2026-05-20', TIMESTAMP '2026-05-20 14:00:00', TIMESTAMP '2026-05-20 17:50:00', 298.00, 80, 80, 'ON_SALE', 1);

INSERT INTO passenger_profile (id, user_id, passenger_name, id_card, phone, passenger_type, is_default) VALUES
(1, 3, '张三', '370101199901010011', '13800000003', 'ADULT', 1),
(2, 3, '李四', '370101200001010022', '13800000004', 'ADULT', 0),
(3, 4, '王五', '370101200101010033', '13800000005', 'ADULT', 1);

INSERT INTO ticket_order (id, order_no, user_id, schedule_id, train_code, departure_station, arrival_station, departure_time, arrival_time, seat_info, passenger_names, passenger_info, contact_phone, total_amount, pay_amount, status, pay_time) VALUES
(1, 'RT202605150001', 3, 1, 'G101', '北京南', '上海虹桥', TIMESTAMP '2026-05-20 08:00:00', TIMESTAMP '2026-05-20 12:38:00', '1:1车1排A座', '张三', '张三|370101199901010011', '13800000003', 553.50, 553.50, 'PAID', TIMESTAMP '2026-05-15 18:20:00');

INSERT INTO train_ticket (id, order_id, ticket_no, passenger_name, id_card, seat_label, qr_code, status) VALUES
(1, 1, 'TK202605150001', '张三', '370101199901010011', '1车1排A座', 'QRCODE-TK202605150001', 'UNUSED');

INSERT INTO payment (id, order_id, pay_no, pay_type, pay_amount, status, pay_time) VALUES
(1, 1, 'PAY202605150001', 'BALANCE', 553.50, 'SUCCESS', TIMESTAMP '2026-05-15 18:20:00');

INSERT INTO after_sale_record (id, order_id, user_id, service_type, target_schedule_id, target_schedule_info, reason, review_status) VALUES
(1, 1, 3, 'CHANGE', 2, 'G125 2026-05-20T09:20', '想改签到济南西', 'PENDING');

INSERT INTO system_notice (id, title, content, publish_status, sort_no, publish_time) VALUES
(1, '春运出行提醒', '春运高峰期间请至少提前30分钟到站候车，携带有效身份证件进站。', 1, 10, TIMESTAMP '2026-05-14 09:00:00'),
(2, '改签退票规则说明', '开车前均可在线提交退改签申请，审核结果会同步到订单中心。', 1, 9, TIMESTAMP '2026-05-14 11:00:00');
