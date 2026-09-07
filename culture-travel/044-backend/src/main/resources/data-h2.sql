INSERT INTO sys_user (id, username, password, nickname, phone, role, status) VALUES
(1, 'admin', '$2a$10$2mwuqL8q/iAVXzjTNhraS.TamwA18iSAuRgzAYcZ4Gcdy.ag1Vcp2', '管理员', '13800138000', 2, 1),
(2, 'host1', '$2a$10$fCxQT3EZhPQ5pk/VCcQALOyAXBj9r7bJaZfQ4T7Asp8Qp3HVAX8lm', '山水民宿房东', '13800138001', 1, 1),
(3, 'guest1', '$2a$10$fCxQT3EZhPQ5pk/VCcQALOyAXBj9r7bJaZfQ4T7Asp8Qp3HVAX8lm', '旅行用户', '13800138002', 0, 1);

INSERT INTO facility (id, name, icon, category) VALUES
(1, 'WiFi', 'wifi', '基础设施'),
(2, '空调', 'air-conditioning', '基础设施'),
(3, '热水', 'hot-water', '基础设施'),
(4, '厨房', 'kitchen', '基础设施'),
(5, '停车位', 'parking', '基础设施'),
(6, '花园', 'garden', '休闲娱乐'),
(7, '早餐', 'breakfast', '特色服务');

INSERT INTO homestay (id, host_id, name, description, province, city, district, address, latitude, longitude, cover_image, images, min_price, max_guests, rating, review_count, booking_count, status) VALUES
(1, 2, '山水间精品民宿', '坐落于山水之间，拥有独立庭院和山景露台。', '浙江省', '杭州市', '西湖区', '西湖风景区龙井路88号', 30.2489000, 120.1240000, '', '[]', 388.00, 6, 4.8, 18, 36, 1),
(2, 2, '老街故事民宿', '百年老宅改造，保留传统建筑风貌，适合古镇慢旅行。', '浙江省', '嘉兴市', '桐乡市', '乌镇西栅大街168号', 30.7460000, 120.4900000, '', '[]', 298.00, 4, 4.9, 25, 42, 1);

INSERT INTO room_type (id, homestay_id, name, description, price, area, max_guests, bed_type, total_count, images, status) VALUES
(1, 1, '山景大床房', '270度山景视野，1.8米大床', 388.00, 35, 2, '1.8米大床', 3, '[]', 1),
(2, 1, '家庭套房', '一室一厅，可住4人，含儿童床', 588.00, 60, 4, '1.8米+1.2米', 1, '[]', 1),
(3, 2, '临水大床房', '推窗见水，古镇风情', 298.00, 28, 2, '1.8米大床', 4, '[]', 1);

INSERT INTO homestay_facility (id, homestay_id, facility_id) VALUES
(1, 1, 1), (2, 1, 2), (3, 1, 3), (4, 1, 4), (5, 1, 5), (6, 1, 6), (7, 1, 7),
(8, 2, 1), (9, 2, 2), (10, 2, 3), (11, 2, 7);

ALTER TABLE sys_user ALTER COLUMN id RESTART WITH 4;
ALTER TABLE facility ALTER COLUMN id RESTART WITH 8;
ALTER TABLE homestay ALTER COLUMN id RESTART WITH 3;
ALTER TABLE room_type ALTER COLUMN id RESTART WITH 4;
ALTER TABLE homestay_facility ALTER COLUMN id RESTART WITH 12;
