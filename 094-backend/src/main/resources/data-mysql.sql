INSERT INTO sys_user (id, user_no, username, password, nickname, phone, email, balance, total_consume, role, status, last_login_time) VALUES
(1, 'U202605030001', 'admin', '123456', '系统管理员', '13800000001', 'admin@petcafe.com', 2000.00, 0.00, 'ADMIN', 1, NOW()),
(2, 'U202605030002', 'staff', '123456', '店长小岚', '13800000002', 'staff@petcafe.com', 500.00, 0.00, 'STAFF', 1, NOW()),
(3, 'U202605030003', 'customer', '123456', '顾客小溪', '13800000003', 'customer@petcafe.com', 368.50, 131.50, 'CUSTOMER', 1, NOW());

INSERT INTO cafe_area (id, area_no, name, city, district, address, contact_person, contact_phone, status, remark) VALUES
(1, 'A202605030001', '松江大学城店群', '上海', '松江区', '文汇路宠物友好街区', '李晴', '13900000001', 1, '周末客流稳定'),
(2, 'A202605030002', '徐汇公园店群', '上海', '徐汇区', '漕溪北路宠物友好商业带', '王诺', '13900000002', 1, '适合露台主题活动');

INSERT INTO cafe_shop (id, shop_no, area_id, name, theme, open_time, close_time, status, cover, manager_name, manager_phone, score, per_capita, remark) VALUES
(1, 'S202605030001', 1, '喵语森林宠物咖啡馆', '森林猫咪主题', '10:00', '21:30', 'OPEN', 'https://example.com/cafe1.jpg', '林岚', '13811110001', 5, 68.00, '主打猫咪互动和下午茶'),
(2, 'S202605030002', 1, '汪星慢时光咖啡屋', '犬系陪伴主题', '09:30', '22:00', 'OPEN', 'https://example.com/cafe2.jpg', '周屿', '13811110002', 4, 72.00, '适合社交聚会和带宠到店'),
(3, 'S202605030003', 2, '爪印花园咖啡馆', '花园治愈主题', '10:30', '20:30', 'REST', 'https://example.com/cafe3.jpg', '陈澈', '13811110003', 5, 88.00, '本周进行主题布景调整');

INSERT INTO resident_pet (id, pet_no, shop_id, name, pet_type, breed, gender, age, personality, health_status, star_level, interaction_status, avatar, introduction) VALUES
(1, 'P202605030001', 1, '奶盖', 'CAT', '布偶猫', '母', 3, '黏人温柔', '已完成疫苗', 5, 'OPEN', 'https://example.com/pet1.jpg', '喜欢趴在窗边晒太阳'),
(2, 'P202605030002', 2, '可可', 'DOG', '柯基', '母', 4, '热情亲人', '已完成驱虫', 5, 'OPEN', 'https://example.com/pet2.jpg', '会主动迎接客人');

INSERT INTO menu_category (id, name, sort, status) VALUES
(1, '招牌咖啡', 1, 1),
(2, '甜品小食', 2, 1);

INSERT INTO menu_item (id, menu_no, name, category_id, taste, cover, price, stock, recommend_flag, status, description) VALUES
(1, 'M202605030001', '榛果拿铁', 1, '香醇坚果', 'https://example.com/menu1.jpg', 28.00, 40, 1, 1, '门店高频复购饮品'),
(2, 'M202605030002', '草莓舒芙蕾', 2, '酸甜绵软', 'https://example.com/menu2.jpg', 32.00, 16, 1, 1, '拍照热门甜品');

INSERT INTO seat_info (id, shop_id, seat_no, zone_name, capacity, min_consume, seat_status, reservation_status, remark) VALUES
(1, 1, 'A01', '落地窗区', 2, 68.00, 'NORMAL', 'AVAILABLE', '适合双人拍照'),
(2, 1, 'A02', '猫爬架区', 4, 128.00, 'NORMAL', 'BOOKED', '靠近互动区域'),
(3, 2, 'B01', '沙发区', 4, 138.00, 'NORMAL', 'AVAILABLE', '适合聚会');

INSERT INTO reservation_record (id, reservation_no, user_id, shop_id, seat_id, customer_name, customer_phone, visit_date, visit_time, people_count, pet_companion, remark, status, manager_remark, create_time, update_time) VALUES
(1, 'RSV202605030001', 3, 1, 2, '顾客小溪', '13800000003', CURDATE(), '14:00-15:30', 2, '无', '想靠近猫咪互动区', 'CONFIRMED', '已为您保留座位', DATE_SUB(NOW(), INTERVAL 4 HOUR), DATE_SUB(NOW(), INTERVAL 4 HOUR)),
(2, 'RSV202605030002', 3, 1, 1, '顾客小溪', '13800000003', DATE_SUB(CURDATE(), INTERVAL 1 DAY), '16:00-17:00', 2, '无', '庆生小聚', 'COMPLETED', '已完成接待', DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY));

INSERT INTO consume_order (id, order_no, user_id, shop_id, menu_id, quantity, dine_type, total_amount, pay_amount, status, remark, pay_time, create_time, update_time) VALUES
(1, 'CO202605030001', 3, 1, 1, 2, 'IN_STORE', 56.00, 56.00, 'COMPLETED', '少糖', DATE_SUB(NOW(), INTERVAL 3 HOUR), DATE_SUB(NOW(), INTERVAL 3 HOUR), DATE_SUB(NOW(), INTERVAL 3 HOUR)),
(2, 'CO202605030002', 3, 1, 2, 1, 'IN_STORE', 32.00, 32.00, 'WAIT_PAY', '生日插牌', NULL, DATE_SUB(NOW(), INTERVAL 20 MINUTE), DATE_SUB(NOW(), INTERVAL 20 MINUTE));

INSERT INTO payment_record (id, user_id, order_id, pay_no, pay_type, pay_amount, status, pay_time, create_time, update_time) VALUES
(1, 3, NULL, 'PAY202605030001', 'RECHARGE', 200.00, 'SUCCESS', DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY)),
(2, 3, 1, 'PAY202605030002', 'BALANCE', 56.00, 'SUCCESS', DATE_SUB(NOW(), INTERVAL 3 HOUR), DATE_SUB(NOW(), INTERVAL 3 HOUR), DATE_SUB(NOW(), INTERVAL 3 HOUR));

INSERT INTO review_record (id, order_id, shop_id, user_id, rating, content, reply_user_id, reply_content, review_status, review_time, reply_time, create_time, update_time) VALUES
(1, 1, 1, 3, 5, '店宠很亲人，咖啡也很稳定。', 2, '感谢喜欢奶盖和我们的咖啡，欢迎下次再来。', 'REPLIED', DATE_SUB(NOW(), INTERVAL 150 MINUTE), DATE_SUB(NOW(), INTERVAL 100 MINUTE), DATE_SUB(NOW(), INTERVAL 150 MINUTE), DATE_SUB(NOW(), INTERVAL 100 MINUTE));

INSERT INTO system_notice (id, title, content, publisher_id, notice_type, status, create_time, update_time) VALUES
(1, '五一主题活动上线', '本周末到店可领取宠物咖啡拉花限定贴纸。', 1, 'SYSTEM', 1, NOW(), NOW()),
(2, '花园店布景调整', '爪印花园咖啡馆本周进行露台布景更新。', 1, 'SYSTEM', 1, NOW(), NOW());
