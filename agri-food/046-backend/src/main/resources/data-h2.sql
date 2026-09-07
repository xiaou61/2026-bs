INSERT INTO community (id, name, address, contact_person, contact_phone, status) VALUES
(1, '阳光花园小区', '市中心阳光路100号', '张经理', '13800138001', 1),
(2, '绿色家园小区', '城东绿洲路200号', '李主任', '13800138002', 1),
(3, '和谐社区', '城西和平路300号', '王物业', '13800138003', 1);

INSERT INTO garbage_category (id, name, code, description, price, points_rate, sort_order, status) VALUES
(1, '可回收物', 'recyclable', '废纸、塑料、玻璃、金属、织物等', 0.80, 10, 1, 1),
(2, '有害垃圾', 'hazardous', '废电池、废灯管、废药品、废油漆等', 0.00, 20, 2, 1),
(3, '厨余垃圾', 'kitchen', '剩菜剩饭、果皮果核、茶渣等', 0.10, 5, 3, 1),
(4, '其他垃圾', 'other', '砖瓦陶瓷、渣土、卫生间废纸等', 0.00, 2, 4, 1),
(5, '废纸类', 'paper', '报纸、书本、纸箱、快递盒等', 1.00, 10, 5, 1),
(6, '塑料类', 'plastic', '塑料瓶、塑料桶、塑料制品等', 0.50, 8, 6, 1),
(7, '金属类', 'metal', '易拉罐、铁制品、铝制品等', 2.00, 15, 7, 1),
(8, '玻璃类', 'glass', '玻璃瓶、玻璃制品等', 0.20, 5, 8, 1),
(9, '旧衣物', 'clothes', '旧衣服、床单被套、鞋帽等', 0.30, 8, 9, 1),
(10, '电子废弃物', 'electronic', '废旧手机、电脑配件、小家电等', 3.00, 25, 10, 1);

INSERT INTO sys_user (id, username, password, real_name, phone, role, address, community_id, points, status) VALUES
(1, 'admin', '$2a$10$2mwuqL8q/iAVXzjTNhraS.TamwA18iSAuRgzAYcZ4Gcdy.ag1Vcp2', '系统管理员', '13900000000', 2, NULL, NULL, 0, 1),
(2, 'collector1', '$2a$10$2mwuqL8q/iAVXzjTNhraS.TamwA18iSAuRgzAYcZ4Gcdy.ag1Vcp2', '回收员小王', '13900000001', 1, '阳光花园服务站', 1, 0, 1),
(3, 'collector2', '$2a$10$2mwuqL8q/iAVXzjTNhraS.TamwA18iSAuRgzAYcZ4Gcdy.ag1Vcp2', '回收员小李', '13900000002', 1, '绿色家园服务站', 2, 0, 1),
(4, 'user1', '$2a$10$2mwuqL8q/iAVXzjTNhraS.TamwA18iSAuRgzAYcZ4Gcdy.ag1Vcp2', '居民张三', '13900000003', 0, '阳光花园1号楼101', 1, 500, 1),
(5, 'user2', '$2a$10$2mwuqL8q/iAVXzjTNhraS.TamwA18iSAuRgzAYcZ4Gcdy.ag1Vcp2', '居民李四', '13900000004', 0, '绿色家园2号楼202', 2, 300, 1);

INSERT INTO points_product (id, name, description, points, stock, exchange_count, status) VALUES
(1, '环保购物袋', '可降解环保购物袋一个', 50, 100, 0, 1),
(2, '垃圾分类指南手册', '详细的垃圾分类知识手册', 30, 200, 0, 1),
(3, '定制水杯', '环保主题不锈钢保温杯', 200, 50, 0, 1),
(4, '话费充值卡10元', '移动/联通/电信通用', 500, 30, 0, 1),
(5, '超市代金券20元', '合作超市通用代金券', 800, 20, 0, 1);

INSERT INTO knowledge (id, title, content, category, view_count, status, publish_time) VALUES
(1, '垃圾分类小常识', '垃圾分类是指按规定将垃圾分类储存、分类投放和分类搬运，从而转变成公共资源的一系列活动的总称。', 1, 0, 1, CURRENT_TIMESTAMP),
(2, '可回收物包括哪些？', '可回收物主要包括废纸、塑料、玻璃、金属和布料五大类。', 1, 0, 1, CURRENT_TIMESTAMP),
(3, '有害垃圾的正确处理方式', '有害垃圾需要特殊安全处理，包括废电池、废灯管、废药品、废油漆及其容器等。', 1, 0, 1, CURRENT_TIMESTAMP);

INSERT INTO notice (id, title, content, type, status, publish_time) VALUES
(1, '垃圾回收服务正式上线', '为了营造更好的居住环境，本小区垃圾回收服务正式上线，欢迎居民使用！', 1, 1, CURRENT_TIMESTAMP),
(2, '积分兑换活动开始', '即日起，环保积分可兑换精美礼品，欢迎参与！', 2, 1, CURRENT_TIMESTAMP);

ALTER TABLE community ALTER COLUMN id RESTART WITH 4;
ALTER TABLE garbage_category ALTER COLUMN id RESTART WITH 11;
ALTER TABLE sys_user ALTER COLUMN id RESTART WITH 6;
ALTER TABLE points_product ALTER COLUMN id RESTART WITH 6;
ALTER TABLE knowledge ALTER COLUMN id RESTART WITH 4;
ALTER TABLE notice ALTER COLUMN id RESTART WITH 3;
