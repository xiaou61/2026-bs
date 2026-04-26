INSERT INTO user (id, username, password, role, real_name, phone, status, balance) VALUES
(1, 'admin', '$2a$10$AAuAMGyD3nVJzWIlC7Aso.GEHonXi7ZOTaujKqH6gAzbhSbE9gqgq', 'admin', '系统管理员', '13800000000', 1, 0.00),
(2, 'landlord', '$2a$10$lNftYRX2w3jKykJHUn.gRu3NYx1QCJ7WvjA0yFOyUgUIgSutOPVAC', 'landlord', '张三', '13800000001', 1, 0.00),
(3, 'tenant', '$2a$10$lNftYRX2w3jKykJHUn.gRu3NYx1QCJ7WvjA0yFOyUgUIgSutOPVAC', 'tenant', '李四', '13800000002', 1, 10000.00);

INSERT INTO house (id, landlord_id, title, description, province, city, district, address, price, deposit, area, room_type, floor, total_floor, orientation, decoration, facilities, images, status, view_count) VALUES
(1, 2, '精装修两居室 近地铁 拎包入住', '房屋位于市中心，交通便利，周边配套齐全，适合年轻白领。', '北京市', '北京市', '朝阳区', '朝阳门外大街1号', 5500.00, 5500.00, 75.00, '2室1厅1卫', 8, 18, '南', '精装', '["空调","冰箱","洗衣机","热水器","宽带"]', '["/uploads/house1_1.jpg","/uploads/house1_2.jpg"]', 1, 156),
(2, 2, '温馨一居室 独立卫浴 采光好', '小户型精装修，适合单身人士，安静舒适。', '北京市', '北京市', '海淀区', '中关村大街2号', 3800.00, 3800.00, 45.00, '1室1厅1卫', 5, 12, '东南', '精装', '["空调","热水器","宽带"]', '["/uploads/house2_1.jpg"]', 1, 89),
(3, 2, '整租三居室 适合家庭', '大户型三居室，空间宽敞，适合家庭居住。', '北京市', '北京市', '西城区', '西单北大街3号', 8500.00, 17000.00, 120.00, '3室2厅2卫', 15, 28, '南北通透', '精装', '["空调","冰箱","洗衣机","热水器","宽带","电视","微波炉"]', '["/uploads/house3_1.jpg","/uploads/house3_2.jpg","/uploads/house3_3.jpg"]', 1, 234);

ALTER TABLE user ALTER COLUMN id RESTART WITH 4;
ALTER TABLE house ALTER COLUMN id RESTART WITH 4;
