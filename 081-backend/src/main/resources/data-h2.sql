INSERT INTO user (username, password, real_name, phone, email, role, status) VALUES
('admin', '123456', '系统管理员', '13800138000', 'admin@repair.com', 'admin', 1),
('tech1', '123456', '王师傅', '13800138001', 'tech1@repair.com', 'technician', 1),
('user1', '123456', '张三', '13800138002', 'user1@repair.com', 'customer', 1),
('user2', '123456', '李四', '13800138004', 'user2@repair.com', 'customer', 1);

INSERT INTO appliance_category (name, brand, sort, status) VALUES
('空调', '格力', 1, 1),
('冰箱', '海尔', 2, 1),
('洗衣机', '小天鹅', 3, 1),
('热水器', '美的', 4, 1);

INSERT INTO technician (user_id, name, phone, skill_tags, service_area, level, work_status, rating, order_count) VALUES
(2, '王师傅', '13800138001', '空调,冰箱,洗衣机', '成都市高新区', '高级', 1, 4.90, 128),
(NULL, '李师傅', '13800138003', '热水器,燃气灶', '成都市锦江区', '中级', 1, 4.70, 83);

INSERT INTO spare_part (name, part_code, brand, specification, stock, unit_price, status) VALUES
('空调电容', 'PART-AC-001', '通用', '35UF', 120, 28.00, 1),
('冰箱压缩机', 'PART-FR-002', '海尔', 'R600a', 35, 560.00, 1),
('洗衣机排水泵', 'PART-WM-003', '小天鹅', '220V', 76, 95.00, 1);

INSERT INTO repair_order (order_no, user_id, contact_name, contact_phone, province, city, district, address, category_id, brand, model, fault_desc, urgency, status, technician_id, total_fee, parts_fee, labor_fee, pay_status) VALUES
('RO202603040001', 3, '张三', '13800138002', '四川省', '成都市', '高新区', '天府三街XX号', 1, '格力', 'KFR-35GW', '空调不制冷，运行有异响', 1, 2, 1, 280.00, 80.00, 200.00, 0),
('RO202603040002', 3, '张三', '13800138002', '四川省', '成都市', '高新区', '天府三街XX号', 2, '海尔', 'BCD-218', '冰箱冷藏室不制冷', 0, 0, NULL, 0.00, 0.00, 0.00, 0);

INSERT INTO repair_process (order_id, operator_id, operator_role, node_type, content) VALUES
(1, 3, 'customer', 'create', '用户提交报修工单'),
(1, 1, 'admin', 'assign', '平台已派单给王师傅'),
(1, 2, 'technician', 'status', '技师已上门完成检测，准备更换电容');

INSERT INTO notice (title, content, type, status, publisher_id, publish_time, view_count) VALUES
('春季空调维保优惠活动', '3月到4月空调清洗享8折，欢迎预约上门服务。', 'activity', 1, 1, CURRENT_TIMESTAMP, 36),
('报修服务时间调整通知', '工作日上门时间调整为8:30-20:00，周末不变。', 'system', 1, 1, CURRENT_TIMESTAMP, 52);

INSERT INTO evaluate (order_id, user_id, technician_id, score, attitude_score, quality_score, speed_score, content) VALUES
(1, 3, 1, 5, 5, 5, 4, '师傅上门准时，处理专业，态度很好。');
