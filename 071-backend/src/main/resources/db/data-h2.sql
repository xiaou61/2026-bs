INSERT INTO user (id, username, password, phone, real_name, role, credit_score, balance, deposit_paid, status) VALUES
(1, 'admin', '123456', '13800000001', '系统管理员', 'admin', 100, 10000.00, 0, 1),
(2, 'operator', '123456', '13800000002', '运维张三', 'operator', 100, 0.00, 0, 1),
(3, 'user', '123456', '13800000003', '用户李四', 'user', 100, 200.00, 1, 1),
(4, 'test', '123456', '13800000004', '测试王五', 'user', 100, 50.00, 0, 1);

INSERT INTO station (id, name, address, longitude, latitude, capacity, current_count, status) VALUES
(1, '天安门站', '北京市东城区天安门广场', 116.397128, 39.916527, 60, 3, 1),
(2, '王府井站', '北京市东城区王府井大街', 116.417480, 39.917840, 40, 2, 1),
(3, '西单站', '北京市西城区西单北大街', 116.373340, 39.913900, 50, 2, 1),
(4, '国贸站', '北京市朝阳区国贸桥', 116.461220, 39.908720, 45, 1, 1),
(5, '颐和园站', '北京市海淀区颐和园', 116.275400, 39.999800, 40, 0, 2);

INSERT INTO bike (id, bike_no, type, status, station_id, battery_level) VALUES
(1, 'BK20260001', 1, 1, 1, 100),
(2, 'BK20260002', 1, 1, 1, 100),
(3, 'BK20260003', 2, 1, 1, 95),
(4, 'BK20260004', 1, 1, 2, 100),
(5, 'BK20260005', 2, 3, 2, 88),
(6, 'BK20260006', 1, 1, 3, 100),
(7, 'BK20260007', 1, 1, 3, 100),
(8, 'BK20260008', 2, 1, 4, 76);

INSERT INTO pricing_rule (id, name, bike_type, base_price, base_duration, extra_price, daily_cap, status) VALUES
(1, '普通单车标准计费', 1, 1.50, 30, 1.00, 20.00, 1),
(2, '电动单车标准计费', 2, 2.25, 30, 1.50, 30.00, 1);

INSERT INTO ride_order (id, order_no, user_id, bike_id, start_station_id, end_station_id, start_time, end_time, duration, distance, amount, status, pay_status, create_time) VALUES
(1, 'RD20260425001', 3, 4, 1, 2, TIMESTAMP '2026-04-25 08:30:00', TIMESTAMP '2026-04-25 09:00:00', 30, 3.50, 1.50, 3, 1, TIMESTAMP '2026-04-25 09:00:00'),
(2, 'RD20260426001', 4, 6, 3, 1, TIMESTAMP '2026-04-26 10:00:00', TIMESTAMP '2026-04-26 10:45:00', 45, 4.10, 2.50, 3, 1, TIMESTAMP '2026-04-26 10:45:00');

INSERT INTO wallet_record (id, user_id, type, amount, balance_after, description, create_time) VALUES
(1, 3, 1, 200.00, 200.00, '账户充值', TIMESTAMP '2026-04-20 10:00:00'),
(2, 3, 2, -1.50, 198.50, '骑行扣费-RD20260425001', TIMESTAMP '2026-04-25 09:00:00'),
(3, 3, 3, -99.00, 99.50, '缴纳押金', TIMESTAMP '2026-04-21 10:00:00'),
(4, 4, 1, 50.00, 50.00, '账户充值', TIMESTAMP '2026-04-20 10:00:00');

INSERT INTO fault_report (id, user_id, bike_id, type, description, status, handler_id, handle_result, handle_time, create_time) VALUES
(1, 3, 5, 2, '前轮胎漏气，无法正常骑行', 2, 2, '已更换前轮内胎，修复完成', TIMESTAMP '2026-04-26 14:30:00', TIMESTAMP '2026-04-26 10:30:00');

INSERT INTO credit_record (id, user_id, type, score_change, score_after, description, create_time) VALUES
(1, 3, 1, 2, 102, '正常还车奖励', TIMESTAMP '2026-04-25 09:00:00'),
(2, 4, 1, 2, 102, '正常还车奖励', TIMESTAMP '2026-04-26 10:45:00');

INSERT INTO announcement (id, title, content, type, status) VALUES
(1, '共享单车系统正式上线', '欢迎使用共享单车系统！新用户注册即享首单免费骑行优惠。', 1, 1),
(2, '五一骑行优惠活动', '2026年五一期间，所有骑行费用享8折优惠。', 2, 1),
(3, '颐和园站点维护通知', '颐和园站点正在维护，暂时停止服务。', 3, 1);

INSERT INTO feedback (id, user_id, type, content, reply, status, create_time) VALUES
(1, 3, 1, '希望增加更多电动单车投放点', '感谢您的建议，我们将在后续规划中考虑增加投放。', 1, TIMESTAMP '2026-04-27 11:00:00');
