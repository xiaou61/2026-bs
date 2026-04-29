INSERT INTO sys_user (username, password, nickname, phone, email, role, status) VALUES
('admin', '123456', '系统管理员', '13800050001', 'admin@property.com', 'ADMIN', 1),
('staff', '123456', '物业管家', '13800050002', 'staff@property.com', 'STAFF', 1),
('owner', '123456', '张业主', '13800050003', 'owner@property.com', 'OWNER', 1),
('owner2', '123456', '李业主', '13800050004', 'owner2@property.com', 'OWNER', 1);

INSERT INTO building (name, floors, status) VALUES
('1号楼', 18, 1),
('2号楼', 24, 1),
('3号楼', 20, 1);

INSERT INTO house (building_id, unit_no, room_no, area, owner_id, status) VALUES
(1, '1单元', '101', 89.50, 3, 1),
(1, '1单元', '102', 92.00, 4, 1),
(2, '2单元', '1201', 120.00, NULL, 0);

INSERT INTO fee_order (order_no, house_id, owner_id, amount, fee_month, status, pay_time, remark, creator_id, create_time) VALUES
('FEE202602150001', 1, 3, 280.00, '2026-02', 1, '2026-02-10 10:30:00', '2月物业费', 2, '2026-02-01 09:00:00'),
('FEE202602150002', 2, 4, 320.00, '2026-02', 0, NULL, '2月物业费', 2, '2026-02-01 09:10:00');

INSERT INTO repair_order (order_no, house_id, owner_id, title, content, status, assignee_id, reply, create_time) VALUES
('REP202602150001', 1, 3, '厨房水龙头漏水', '水龙头连接处持续滴水', 2, 2, '已更换密封圈并完成检测', '2026-02-08 14:00:00'),
('REP202602150002', 2, 4, '卫生间排水不畅', '排水速度慢并有异味', 1, 2, '已安排维修师傅上门', '2026-02-12 16:20:00');

INSERT INTO complaint (order_no, owner_id, title, content, status, reply, create_time) VALUES
('COM202602150001', 3, '夜间噪音问题', '楼上夜间施工影响休息', 1, '已上门沟通并要求限时施工', '2026-02-09 21:00:00'),
('COM202602150002', 4, '电梯卫生问题', '电梯内有异味，清洁不及时', 0, NULL, '2026-02-13 08:30:00');

INSERT INTO visitor_record (order_no, owner_id, visitor_name, visitor_phone, visit_time, status, remark, create_time) VALUES
('VIS202602150001', 3, '王明', '13912340001', '2026-05-16 10:00:00', 1, '亲属来访', '2026-02-15 09:00:00'),
('VIS202602150002', 4, '赵峰', '13912340002', '2026-05-16 15:30:00', 0, '朋友来访', '2026-02-15 11:00:00');

INSERT INTO parking_slot (slot_no, location, owner_id, status) VALUES
('A-001', '地库A区', 3, 1),
('A-002', '地库A区', NULL, 0),
('B-105', '地库B区', 4, 1);

INSERT INTO announcement (title, content, admin_id, status) VALUES
('物业费缴纳通知', '请各位业主于每月15日前完成当月物业费缴纳。', 1, 1),
('电梯维护公告', '2号楼电梯将于本周六上午进行例行检修。', 1, 1);
