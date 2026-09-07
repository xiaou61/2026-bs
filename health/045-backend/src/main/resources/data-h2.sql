INSERT INTO sys_user (id, username, password, real_name, phone, role, elder_id, status) VALUES
(1, 'admin', '$2a$10$2mwuqL8q/iAVXzjTNhraS.TamwA18iSAuRgzAYcZ4Gcdy.ag1Vcp2', '系统管理员', '13800000000', 2, NULL, 1),
(2, 'nurse1', '$2a$10$2mwuqL8q/iAVXzjTNhraS.TamwA18iSAuRgzAYcZ4Gcdy.ag1Vcp2', '张护士', '13800000001', 1, NULL, 1),
(3, 'family1', '$2a$10$2mwuqL8q/iAVXzjTNhraS.TamwA18iSAuRgzAYcZ4Gcdy.ag1Vcp2', '张明', '13900000011', 0, 1, 1);

INSERT INTO building (id, name, floors, description, status) VALUES
(1, '颐养楼', 5, '主楼，适合自理和半护理老人', 1),
(2, '康复楼', 3, '康复护理专用楼', 1);

INSERT INTO room (id, building_id, room_no, floor, room_type, bed_count, price, status) VALUES
(1, 1, '101', 1, 1, 1, 3000.00, 1),
(2, 1, '102', 1, 2, 2, 2500.00, 1),
(3, 2, '201', 2, 2, 2, 3500.00, 1);

INSERT INTO bed (id, room_id, bed_no, status) VALUES
(1, 1, '101-1', 1),
(2, 2, '102-1', 0),
(3, 2, '102-2', 0),
(4, 3, '201-1', 0);

INSERT INTO elder (id, name, gender, birthday, id_card, phone, bed_id, care_level, health_status, emergency_contact, emergency_phone, check_in_date, status, remark) VALUES
(1, '张大爷', 1, '1940-05-15', '110101194005150011', '13900000001', 1, 1, '身体健康，能自理', '张明', '13900000011', '2024-01-15', 1, '演示老人'),
(2, '李奶奶', 2, '1938-08-20', '110101193808200022', '13900000002', NULL, 2, '高血压，需定期测量', '李华', '13900000012', '2024-02-01', 1, '重点关注血压');

INSERT INTO health_record (id, elder_id, record_date, blood_pressure, heart_rate, temperature, blood_sugar, weight, sleep_hours, appetite, mental_state, recorder_id, remark) VALUES
(1, 1, '2026-04-25', '128/82', 76, 36.5, 5.80, 62.50, 7.0, 3, 3, 2, '状态稳定');

INSERT INTO care_plan (id, elder_id, plan_name, care_content, frequency, start_date, status) VALUES
(1, 1, '日常护理', '每日查房、协助用餐、陪同散步', '每日', '2024-01-15', 1);

INSERT INTO fee_item (id, name, category, price, unit, description, status) VALUES
(1, '单人间床位费', 1, 3000.00, '月', '单人间每月床位费', 1),
(2, '自理护理费', 2, 500.00, '月', '自理老人护理费', 1),
(3, '标准餐费', 3, 900.00, '月', '三餐标准餐费', 1);

INSERT INTO bill (id, bill_no, elder_id, bill_month, bed_fee, care_fee, meal_fee, other_fee, total_amount, paid_amount, status, due_date, remark) VALUES
(1, 'BILL202604001', 1, '2026-04', 3000.00, 500.00, 900.00, 0.00, 4400.00, 0.00, 0, '2026-04-30', '四月费用账单');

INSERT INTO notice (id, title, content, type, target, publisher_id, publish_time, status) VALUES
(1, '探访时间通知', '探访时间为上午9:00-11:00，下午14:00-16:00，请家属提前预约。', 1, 0, 1, CURRENT_TIMESTAMP, 1),
(2, '健康讲座活动', '本周六上午10点举办老年人健康养生讲座。', 3, 0, 1, CURRENT_TIMESTAMP, 1);

ALTER TABLE sys_user ALTER COLUMN id RESTART WITH 4;
ALTER TABLE building ALTER COLUMN id RESTART WITH 3;
ALTER TABLE room ALTER COLUMN id RESTART WITH 4;
ALTER TABLE bed ALTER COLUMN id RESTART WITH 5;
ALTER TABLE elder ALTER COLUMN id RESTART WITH 3;
ALTER TABLE health_record ALTER COLUMN id RESTART WITH 2;
ALTER TABLE care_plan ALTER COLUMN id RESTART WITH 2;
ALTER TABLE fee_item ALTER COLUMN id RESTART WITH 4;
ALTER TABLE bill ALTER COLUMN id RESTART WITH 2;
ALTER TABLE notice ALTER COLUMN id RESTART WITH 3;
