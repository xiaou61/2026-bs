INSERT INTO user (id, username, password, nickname, phone, email, role, status, balance) VALUES
(1, 'admin', '123456', '平台管理员', '13800000001', 'admin@craftsale.com', 'ADMIN', 1, 99999.00),
(2, 'seller1', '123456', '青瓷工坊', '13800000002', 'seller1@craftsale.com', 'USER', 1, 5200.00),
(3, 'seller2', '123456', '木语手作', '13800000003', 'seller2@craftsale.com', 'USER', 1, 4300.00),
(4, 'buyer1', '123456', '手作爱好者', '13800000004', 'buyer1@craftsale.com', 'USER', 1, 2600.00);

INSERT INTO craft_category (id, name, icon, sort, status) VALUES
(1, '陶艺瓷器', '/images/category/ceramic.png', 1, 1),
(2, '木作雕刻', '/images/category/wood.png', 2, 1),
(3, '刺绣布艺', '/images/category/embroidery.png', 3, 1),
(4, '编织手作', '/images/category/weaving.png', 4, 1),
(5, '漆器工艺', '/images/category/lacquer.png', 5, 1),
(6, '金工首饰', '/images/category/jewelry.png', 6, 1);

INSERT INTO craft_item (id, category_id, seller_id, title, craft_name, workshop_name, craft_type, price, stock, cover, description, delivery_type, view_count, sold_count, status) VALUES
(1, 1, 2, '景德镇青花茶杯套装', '青花手作杯', '景德镇窑坊', '瓷器', 299.00, 18, '/images/item/ceramic1.jpg', '高温烧制，釉面细腻，适合日常茶席。', '顺丰包邮', 358, 41, 1),
(2, 1, 2, '手捏陶土花器', '侘寂花器', '青瓷工坊', '陶艺', 168.00, 25, '/images/item/ceramic2.jpg', '手工成型，每件纹理略有差异。', '普通快递', 246, 33, 1),
(3, 2, 3, '黑胡桃木首饰收纳盒', '榫卯收纳盒', '木语手作', '木作', 369.00, 12, '/images/item/wood1.jpg', '传统榫卯结构，原木蜡油处理。', '顺丰包邮', 412, 22, 1),
(4, 2, 3, '檀木书签礼盒', '雕花书签', '木语手作', '木雕', 89.00, 60, '/images/item/wood2.jpg', '手工雕花并打磨，附礼盒包装。', '普通快递', 191, 57, 1),
(5, 3, 2, '苏绣团扇', '花鸟团扇', '绣语工坊', '刺绣', 219.00, 20, '/images/item/emb1.jpg', '双面绣工艺，适合礼赠与陈列。', '顺丰包邮', 287, 19, 1),
(6, 3, 3, '苗绣手拿包', '民族刺绣包', '木语手作', '布艺', 259.00, 16, '/images/item/emb2.jpg', '手工刺绣图案，内里加固耐用。', '普通快递', 173, 11, 1),
(7, 4, 2, '藤编果盘', '藤编收纳盘', '青瓷工坊', '编织', 129.00, 35, '/images/item/weave1.jpg', '环保藤材，适配客厅餐桌收纳。', '普通快递', 144, 26, 1),
(8, 5, 3, '天然大漆茶则', '漆艺茶则', '木语手作', '漆器', 199.00, 14, '/images/item/lacquer1.jpg', '多次髹漆工艺，手感温润。', '顺丰包邮', 108, 9, 1),
(9, 6, 2, '银丝绕线耳饰', '银线耳坠', '银匠小馆', '金工', 149.00, 28, '/images/item/jewel1.jpg', '925银材质，轻量佩戴舒适。', '顺丰包邮', 265, 38, 1),
(10, 6, 3, '手工黄铜胸针', '叶形胸针', '木语手作', '首饰', 119.00, 30, '/images/item/jewel2.jpg', '纯手工打磨抛光，纹理自然。', '普通快递', 132, 17, 1);

INSERT INTO craft_order (id, order_no, user_id, item_id, seller_id, quantity, total_price, status, remark, pay_time, finish_time, create_time) VALUES
(1, 'HC202602120001', 4, 1, 2, 2, 598.00, 3, '包装完整，已收货', '2026-02-12 10:00:00', '2026-02-14 16:20:00', '2026-02-12 09:58:00'),
(2, 'HC202602120002', 4, 3, 3, 1, 369.00, 2, '等待签收', '2026-02-12 11:20:00', NULL, '2026-02-12 11:18:00'),
(3, 'HC202602120003', 2, 9, 2, 1, 149.00, 1, '请做好礼盒包装', '2026-02-12 12:35:00', NULL, '2026-02-12 12:33:00');

INSERT INTO review (id, order_id, user_id, item_id, rating, content, status, create_time) VALUES
(1, 1, 4, 1, 5, '做工细致，釉色很漂亮，实物比图片更好看。', 1, '2026-02-14 18:10:00');

INSERT INTO favorite (id, user_id, item_id) VALUES
(1, 4, 1),
(2, 4, 3),
(3, 4, 9),
(4, 2, 5);

INSERT INTO complaint (id, order_id, user_id, target_user_id, type, content, images, status, reply, reply_admin_id, reply_time, create_time) VALUES
(1, 2, 4, 3, 'ORDER', '物流信息长时间未更新，请平台协助跟进。', '', 1, '已联系卖家补充物流节点，预计24小时内更新。', 1, '2026-02-13 16:20:00', '2026-02-13 15:10:00');

INSERT INTO announcement (id, title, content, admin_id, status) VALUES
(1, '春季手作节活动上线', '3月1日至3月15日平台服务费限时9折，指定品类额外流量扶持。', 1, 1),
(2, '物流发货规范提醒', '请卖家在48小时内完成发货并上传有效物流单号。', 1, 1),
(3, '原创保护声明', '平台严禁盗图与抄袭作品，一经核实将立即下架并处罚。', 1, 1);
