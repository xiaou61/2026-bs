INSERT INTO admin (username, password, real_name, phone, role, status) VALUES
('admin', '$2a$10$HHUHhJUyilVGAb3hOSPHROwvEaOp/ktp9ukbo9pxFYfoxQt0Kz3yC', '超级管理员', '13800138000', 2, 1),
('operator', '$2a$10$HHUHhJUyilVGAb3hOSPHROwvEaOp/ktp9ukbo9pxFYfoxQt0Kz3yC', '运维人员', '13800138001', 3, 1);

INSERT INTO station (station_name, address, longitude, latitude, capacity, current_count, status, description) VALUES
('教学楼A区', '教学楼A栋东侧', 116.3912750, 39.9065430, 30, 15, 1, '靠近教学楼A栋，方便上课'),
('教学楼B区', '教学楼B栋西侧', 116.3922750, 39.9075430, 25, 12, 1, '靠近教学楼B栋，方便上课'),
('图书馆站', '图书馆北门', 116.3932750, 39.9085430, 40, 20, 1, '图书馆门口，方便学习'),
('食堂站', '第一食堂门口', 116.3942750, 39.9095430, 50, 25, 1, '食堂门口，就餐高峰期车辆多'),
('宿舍1号站', '1号宿舍楼东侧', 116.3952750, 39.9105430, 35, 18, 1, '1号宿舍楼旁'),
('宿舍2号站', '2号宿舍楼西侧', 116.3962750, 39.9115430, 35, 16, 1, '2号宿舍楼旁'),
('体育馆站', '体育馆正门', 116.3972750, 39.9125430, 30, 10, 1, '体育馆门口'),
('行政楼站', '行政楼南侧', 116.3982750, 39.9135430, 20, 8, 1, '行政办公区'),
('实验楼站', '实验楼门口', 116.3992750, 39.9145430, 25, 12, 1, '实验楼旁边'),
('校门站', '学校正门内', 116.4002750, 39.9155430, 45, 22, 1, '校门口，进出校园便捷');

INSERT INTO bicycle (bike_no, qr_code, bike_type, status, station_id, longitude, latitude, purchase_date, total_mileage, total_orders) VALUES
('BIKE001', 'QR_BIKE001', 1, 0, 1, 116.3912750, 39.9065430, '2024-01-01', 12.50, 3),
('BIKE002', 'QR_BIKE002', 1, 0, 1, 116.3912750, 39.9065430, '2024-01-01', 8.30, 2),
('BIKE003', 'QR_BIKE003', 1, 0, 1, 116.3912750, 39.9065430, '2024-01-01', 5.20, 1),
('BIKE004', 'QR_BIKE004', 1, 0, 2, 116.3922750, 39.9075430, '2024-01-01', 18.60, 4),
('BIKE005', 'QR_BIKE005', 1, 0, 2, 116.3922750, 39.9075430, '2024-01-01', 9.80, 2),
('BIKE006', 'QR_BIKE006', 2, 0, 3, 116.3932750, 39.9085430, '2024-02-01', 25.30, 5),
('BIKE007', 'QR_BIKE007', 2, 0, 3, 116.3932750, 39.9085430, '2024-02-01', 13.40, 3),
('BIKE008', 'QR_BIKE008', 1, 0, 4, 116.3942750, 39.9095430, '2024-01-15', 7.10, 1),
('BIKE009', 'QR_BIKE009', 1, 0, 4, 116.3942750, 39.9095430, '2024-01-15', 6.50, 1),
('BIKE010', 'QR_BIKE010', 1, 0, 5, 116.3952750, 39.9105430, '2024-01-15', 10.00, 2),
('BIKE011', 'QR_BIKE011', 1, 0, 5, 116.3952750, 39.9105430, '2024-01-15', 11.20, 2),
('BIKE012', 'QR_BIKE012', 1, 0, 6, 116.3962750, 39.9115430, '2024-02-01', 14.80, 3),
('BIKE013', 'QR_BIKE013', 2, 0, 7, 116.3972750, 39.9125430, '2024-02-01', 21.70, 4),
('BIKE014', 'QR_BIKE014', 1, 0, 8, 116.3982750, 39.9135430, '2024-03-01', 4.90, 1),
('BIKE015', 'QR_BIKE015', 1, 0, 9, 116.3992750, 39.9145430, '2024-03-01', 3.80, 1),
('BIKE016', 'QR_BIKE016', 1, 0, 10, 116.4002750, 39.9155430, '2024-03-01', 2.40, 0),
('BIKE017', 'QR_BIKE017', 1, 0, 10, 116.4002750, 39.9155430, '2024-03-01', 1.20, 0),
('BIKE018', 'QR_BIKE018', 2, 0, 10, 116.4002750, 39.9155430, '2024-03-01', 0.80, 0),
('BIKE019', 'QR_BIKE019', 1, 2, NULL, NULL, NULL, '2024-01-01', 33.60, 8),
('BIKE020', 'QR_BIKE020', 1, 3, NULL, NULL, NULL, '2023-01-01', 52.10, 15);

INSERT INTO system_config (config_key, config_value, description) VALUES
('price_per_30min', '1.0', '每30分钟收费（元）'),
('daily_cap', '20.0', '日封顶费用（元）'),
('deposit', '99.0', '押金（元）'),
('free_deposit_credit', '600', '免押金所需信用分'),
('night_discount', '0.8', '夜间折扣'),
('night_start', '22', '夜间开始时间'),
('night_end', '6', '夜间结束时间'),
('initial_credit', '100', '初始信用分'),
('normal_return_credit', '1', '正常还车信用分'),
('proper_parking_credit', '2', '规范停车信用分'),
('report_fault_credit', '5', '上报故障信用分'),
('overtime_penalty_credit', '-10', '超时未还扣分'),
('illegal_parking_credit', '-5', '违规停车扣分'),
('damage_penalty_credit', '-20', '损坏车辆扣分');

INSERT INTO user (username, password, real_name, phone, student_id, gender, credit_score, status, auth_status) VALUES
('testuser', '$2a$10$QmXa./jwOc/a4dDNUbTR0eLzZY0rg10bOlMvH0seCWqD6GP25KBWu', '测试用户', '13900139000', '2024001', 1, 100, 1, 2),
('zhangsan', '$2a$10$QmXa./jwOc/a4dDNUbTR0eLzZY0rg10bOlMvH0seCWqD6GP25KBWu', '张三', '13900139001', '2024002', 1, 150, 1, 2),
('lisi', '$2a$10$QmXa./jwOc/a4dDNUbTR0eLzZY0rg10bOlMvH0seCWqD6GP25KBWu', '李四', '13900139002', '2024003', 2, 80, 1, 2);

INSERT INTO user_wallet (user_id, balance, deposit, deposit_status) VALUES
(1, 50.00, 99.00, 1),
(2, 100.00, 99.00, 1),
(3, 20.00, 0.00, 0);

INSERT INTO rental_order (order_no, user_id, bike_id, start_station_id, end_station_id, start_time, end_time, duration, distance, amount, status, pay_status, remark) VALUES
('ORD202604130001', 2, 4, 2, 2, DATEADD('HOUR', -30, CURRENT_TIMESTAMP), DATEADD('HOUR', -29, CURRENT_TIMESTAMP), 42, 3.60, 2.00, 1, 1, '历史已完成订单');

INSERT INTO recharge_record (user_id, amount, type, pay_method, status, transaction_no, create_time) VALUES
(1, 99.00, 2, 1, 1, 'TXN202604130001', DATEADD('DAY', -7, CURRENT_TIMESTAMP)),
(2, 50.00, 1, 2, 1, 'TXN202604130002', DATEADD('DAY', -2, CURRENT_TIMESTAMP));

INSERT INTO consumption_record (user_id, order_id, amount, type, balance_after, description, create_time) VALUES
(2, 1, 2.00, 1, 98.00, '骑行消费 - 订单ORD202604130001', DATEADD('HOUR', -29, CURRENT_TIMESTAMP));

INSERT INTO credit_record (user_id, change_value, change_type, reason, order_id, score_after, create_time) VALUES
(2, 1, 1, '正常还车', 1, 151, DATEADD('HOUR', -29, CURRENT_TIMESTAMP)),
(3, 5, 3, '上报故障', NULL, 85, DATEADD('DAY', -1, CURRENT_TIMESTAMP));

INSERT INTO fault_report (bike_id, user_id, fault_type, description, images, status, create_time, update_time) VALUES
(19, 3, 4, '车锁异常，无法正常闭合', NULL, 0, DATEADD('DAY', -1, CURRENT_TIMESTAMP), DATEADD('DAY', -1, CURRENT_TIMESTAMP));
