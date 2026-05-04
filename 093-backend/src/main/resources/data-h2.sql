INSERT INTO sys_user (id, user_no, username, password, nickname, phone, email, balance, total_consume, role, status, last_login_time) VALUES
(1, 'U202603170001', 'admin', '123456', '系统管理员', '13800000001', 'admin@vending.com', 2000.00, 0.00, 'ADMIN', 1, CURRENT_TIMESTAMP),
(2, 'U202603170002', 'staff', '123456', '补货员小周', '13800000002', 'staff@vending.com', 200.00, 0.00, 'STAFF', 1, CURRENT_TIMESTAMP),
(3, 'U202603170003', 'customer', '123456', '顾客小林', '13800000003', 'customer@vending.com', 158.50, 46.50, 'CUSTOMER', 1, CURRENT_TIMESTAMP);

INSERT INTO machine_location (id, location_no, name, scene_type, contact_person, contact_phone, address, status, remark) VALUES
(1, 'L202603170001', '信息楼一层大厅', '教学楼', '李老师', '13900000001', '校园信息楼一层东侧', 1, '课间客流较高'),
(2, 'L202603170002', '创业园休闲区', '园区', '王经理', '13900000002', '创业园 A 座共享休闲区', 1, '夜间也有消费');

INSERT INTO vending_machine (id, machine_no, location_id, name, temperature_type, status, last_replenish_time, last_online_time, manager_name, manager_phone, remark) VALUES
(1, 'VM202603170001', 1, '信息楼饮料机', 'COLD', 'ONLINE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '周洋', '13811110001', '主打饮料与轻食'),
(2, 'VM202603170002', 1, '信息楼零食机', 'NORMAL', 'ONLINE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '周洋', '13811110001', '靠近电梯口'),
(3, 'VM202603170003', 2, '创业园综合机', 'NORMAL', 'MAINTAIN', DATEADD('DAY', -1, CURRENT_TIMESTAMP), DATEADD('HOUR', -2, CURRENT_TIMESTAMP), '赵航', '13811110002', '本周做保养');

INSERT INTO product_category (id, name, sort, status) VALUES
(1, '碳酸饮料', 1, 1),
(2, '休闲零食', 2, 1),
(3, '速食冲调', 3, 1);

INSERT INTO product_info (id, product_no, name, category_id, brand, spec, cover, cost_price, sale_price, stock_warn, status) VALUES
(1, 'P202603170001', '冰红茶', 1, '康师傅', '500ml', 'https://example.com/tea.jpg', 2.20, 4.50, 6, 1),
(2, 'P202603170002', '可乐', 1, '可口可乐', '330ml', 'https://example.com/cola.jpg', 2.00, 4.00, 6, 1),
(3, 'P202603170003', '原味薯片', 2, '乐事', '70g', 'https://example.com/chips.jpg', 3.00, 6.50, 5, 1),
(4, 'P202603170004', '夹心饼干', 2, '奥利奥', '97g', 'https://example.com/cookie.jpg', 3.20, 6.00, 5, 1),
(5, 'P202603170005', '桶装泡面', 3, '统一', '1桶', 'https://example.com/noodle.jpg', 3.80, 7.50, 4, 1),
(6, 'P202603170006', '速溶咖啡', 3, '雀巢', '2条装', 'https://example.com/coffee.jpg', 4.10, 8.00, 4, 1);

INSERT INTO machine_slot (id, machine_id, slot_no, product_id, capacity, current_stock, status, last_sync_time) VALUES
(1, 1, 'A01', 1, 20, 12, 'NORMAL', CURRENT_TIMESTAMP),
(2, 1, 'A02', 2, 20, 8, 'NORMAL', CURRENT_TIMESTAMP),
(3, 2, 'B01', 3, 15, 10, 'NORMAL', CURRENT_TIMESTAMP),
(4, 2, 'B02', 4, 15, 4, 'SOLD_OUT', CURRENT_TIMESTAMP),
(5, 3, 'C01', 5, 12, 6, 'NORMAL', CURRENT_TIMESTAMP),
(6, 3, 'C02', 6, 12, 3, 'DISABLED', CURRENT_TIMESTAMP);

INSERT INTO replenish_record (id, replenish_no, machine_id, slot_id, product_id, quantity, before_stock, after_stock, operator_id, remark, create_time) VALUES
(1, 'RP202603170001', 1, 1, 1, 8, 4, 12, 2, '早高峰前补货', DATEADD('HOUR', -5, CURRENT_TIMESTAMP)),
(2, 'RP202603170002', 2, 3, 3, 6, 4, 10, 2, '补充薯片库存', DATEADD('HOUR', -4, CURRENT_TIMESTAMP)),
(3, 'RP202603170003', 3, 5, 5, 4, 2, 6, 2, '园区机晚班补货', DATEADD('DAY', -1, CURRENT_TIMESTAMP));

INSERT INTO sale_order (id, order_no, user_id, machine_id, slot_id, product_id, quantity, total_amount, pay_amount, pickup_code, status, pay_time, create_time, update_time) VALUES
(1, 'SO202603170001', 3, 1, 1, 1, 2, 9.00, 9.00, 'PK6401', 'SHIPPED', DATEADD('HOUR', -3, CURRENT_TIMESTAMP), DATEADD('HOUR', -3, CURRENT_TIMESTAMP), DATEADD('HOUR', -3, CURRENT_TIMESTAMP)),
(2, 'SO202603170002', 3, 2, 3, 3, 1, 6.50, 6.50, 'PK6402', 'SHIPPED', DATEADD('HOUR', -2, CURRENT_TIMESTAMP), DATEADD('HOUR', -2, CURRENT_TIMESTAMP), DATEADD('HOUR', -2, CURRENT_TIMESTAMP)),
(3, 'SO202603170003', 3, 3, 5, 5, 1, 7.50, 0.00, 'PK6403', 'WAIT_PAY', NULL, DATEADD('MINUTE', -30, CURRENT_TIMESTAMP), DATEADD('MINUTE', -30, CURRENT_TIMESTAMP));

INSERT INTO payment_record (id, user_id, order_id, pay_no, pay_type, pay_amount, status, pay_time, create_time, update_time) VALUES
(1, 3, NULL, 'PAY202603170001', 'RECHARGE', 100.00, 'SUCCESS', DATEADD('DAY', -1, CURRENT_TIMESTAMP), DATEADD('DAY', -1, CURRENT_TIMESTAMP), DATEADD('DAY', -1, CURRENT_TIMESTAMP)),
(2, 3, 1, 'PAY202603170002', 'BALANCE', 9.00, 'SUCCESS', DATEADD('HOUR', -3, CURRENT_TIMESTAMP), DATEADD('HOUR', -3, CURRENT_TIMESTAMP), DATEADD('HOUR', -3, CURRENT_TIMESTAMP)),
(3, 3, 2, 'PAY202603170003', 'BALANCE', 6.50, 'SUCCESS', DATEADD('HOUR', -2, CURRENT_TIMESTAMP), DATEADD('HOUR', -2, CURRENT_TIMESTAMP), DATEADD('HOUR', -2, CURRENT_TIMESTAMP)),
(4, 1, NULL, 'PAY202603170004', 'RECHARGE', 2000.00, 'SUCCESS', DATEADD('DAY', -2, CURRENT_TIMESTAMP), DATEADD('DAY', -2, CURRENT_TIMESTAMP), DATEADD('DAY', -2, CURRENT_TIMESTAMP));

INSERT INTO shipment_record (id, order_id, user_id, machine_id, slot_id, product_id, quantity, shipment_status, shipment_time, result_msg, create_time, update_time) VALUES
(1, 1, 3, 1, 1, 1, 2, 'SUCCESS', DATEADD('HOUR', -3, CURRENT_TIMESTAMP), '设备已正常出货', DATEADD('HOUR', -3, CURRENT_TIMESTAMP), DATEADD('HOUR', -3, CURRENT_TIMESTAMP)),
(2, 2, 3, 2, 3, 3, 1, 'SUCCESS', DATEADD('HOUR', -2, CURRENT_TIMESTAMP), '顾客已完成取货', DATEADD('HOUR', -2, CURRENT_TIMESTAMP), DATEADD('HOUR', -2, CURRENT_TIMESTAMP)),
(3, 3, 3, 3, 5, 5, 1, 'PENDING', NULL, '等待支付后执行出货', DATEADD('MINUTE', -30, CURRENT_TIMESTAMP), DATEADD('MINUTE', -30, CURRENT_TIMESTAMP));

INSERT INTO fault_report (id, order_id, user_id, machine_id, report_type, content, handle_status, handle_result, handler_id, handle_time, create_time, update_time) VALUES
(1, 2, 3, 2, '未及时出货', '设备延迟较久后才出货，希望检查机械臂。', 'DONE', '已安排补货员检查并恢复正常。', 2, DATEADD('HOUR', -1, CURRENT_TIMESTAMP), DATEADD('MINUTE', -90, CURRENT_TIMESTAMP), DATEADD('HOUR', -1, CURRENT_TIMESTAMP)),
(2, NULL, 3, 3, '设备离线', '创业园综合机屏幕提示维护中。', 'PENDING', NULL, NULL, NULL, DATEADD('MINUTE', -20, CURRENT_TIMESTAMP), DATEADD('MINUTE', -20, CURRENT_TIMESTAMP));

INSERT INTO system_notice (id, title, content, publisher_id, notice_type, status, create_time, update_time) VALUES
(1, '春季饮品补货通知', '本周已上架低糖饮品和新品咖啡，请补货员优先巡检一层设备。', 1, 'SYSTEM', 1, DATEADD('HOUR', -6, CURRENT_TIMESTAMP), DATEADD('HOUR', -6, CURRENT_TIMESTAMP)),
(2, '晚间设备维护提醒', '创业园综合机今晚 22:00 后进行线路保养，请提前处理订单。', 1, 'SYSTEM', 1, DATEADD('HOUR', -2, CURRENT_TIMESTAMP), DATEADD('HOUR', -2, CURRENT_TIMESTAMP));
