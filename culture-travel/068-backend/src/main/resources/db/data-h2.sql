INSERT INTO sys_user (id, username, password, nickname, phone, email, avatar, profile, role, status, last_login_time) VALUES
(1, 'admin', '123456', '平台管理员', '13800000000', 'admin@travel.com', '', '负责平台运营管理', 'ADMIN', 1, CURRENT_TIMESTAMP),
(2, 'user', '123456', '周边游用户', '13900000000', 'user@travel.com', '', '热爱周末出行', 'USER', 1, CURRENT_TIMESTAMP);

INSERT INTO scenic_spot (id, name, city, tags, price, cover, intro, status) VALUES
(1, '云湖湿地公园', '杭州', '亲子,骑行,自然风光', 88.00, 'https://picsum.photos/seed/spot1/400/240', '城市近郊湿地生态游，适合周末半日出行。', 1),
(2, '青山古镇', '苏州', '古镇,美食,摄影', 68.00, 'https://picsum.photos/seed/spot2/400/240', '古街夜景和非遗手作体验结合。', 1),
(3, '松林露营地', '南京', '露营,团建,烧烤', 128.00, 'https://picsum.photos/seed/spot3/400/240', '配置完善的露营营地，支持拎包露营。', 1),
(4, '西坡温泉谷', '无锡', '温泉,康养,情侣', 188.00, 'https://picsum.photos/seed/spot4/400/240', '周边游热门温泉打卡地。', 1),
(5, '花海田园小镇', '常州', '赏花,拍照,亲子', 99.00, 'https://picsum.photos/seed/spot5/400/240', '四季花田与乡野研学体验。', 1),
(6, '山野徒步线', '宁波', '徒步,轻运动,自然', 59.00, 'https://picsum.photos/seed/spot6/400/240', '新手友好的轻徒步路线。', 1);

INSERT INTO traveler (id, user_id, name, cert_type, cert_no, phone, is_default) VALUES
(1, 2, '张三', '身份证', '330102199901010011', '13900000000', 1),
(2, 2, '李四', '身份证', '330102199902020022', '13700000000', 0);

INSERT INTO user_favorite (id, user_id, spot_id) VALUES
(1, 2, 1),
(2, 2, 3);

INSERT INTO travel_order (id, order_no, user_id, traveler_id, spot_id, travel_date, quantity, total_amount, status, contact_name, contact_phone, remark, pay_time, finish_time, create_time, update_time) VALUES
(1, 'TR202602140001', 2, 1, 1, DATE '2026-02-20', 2, 176.00, 'WAIT_PAY', '张三', '13900000000', '周末上午出发', NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'TR202602140002', 2, 2, 3, DATE '2026-02-08', 1, 128.00, 'FINISHED', '李四', '13700000000', '已完成露营', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'TR202602140003', 2, 1, 4, DATE '2026-02-12', 1, 188.00, 'PAID', '张三', '13900000000', '待出行', CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO travel_review (id, order_id, user_id, spot_id, score, content, status, reply_content, create_time, update_time) VALUES
(1, 2, 2, 3, 5, '营地环境非常好，服务人员也很专业。', 1, '感谢反馈，欢迎再次预订。', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO user_complaint (id, user_id, order_id, type, content, attachment_urls, status, handle_result, handle_by, handle_time, create_time, update_time) VALUES
(1, 2, 3, '行程变更', '希望支持更灵活的改签时间。', '[]', 'WAITING', '', NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

ALTER TABLE sys_user ALTER COLUMN id RESTART WITH 3;
ALTER TABLE scenic_spot ALTER COLUMN id RESTART WITH 7;
ALTER TABLE traveler ALTER COLUMN id RESTART WITH 3;
ALTER TABLE user_favorite ALTER COLUMN id RESTART WITH 3;
ALTER TABLE travel_order ALTER COLUMN id RESTART WITH 4;
ALTER TABLE travel_review ALTER COLUMN id RESTART WITH 2;
ALTER TABLE user_complaint ALTER COLUMN id RESTART WITH 2;
