MERGE INTO user (id, username, password, email, phone, avatar, nickname, bio, role, status, balance, create_time, update_time) KEY(id) VALUES
    (1, 'admin', '123456', 'admin@artist.com', '13800000000', 'https://images.unsplash.com/photo-1544723795-3fb6469f5b39?auto=format&fit=crop&w=400&q=80', '平台管理员', '负责审核画师与处理平台事务。', 'ADMIN', 'ACTIVE', 0.00, TIMESTAMP '2026-04-01 09:00:00', TIMESTAMP '2026-04-01 09:00:00'),
    (2, 'buyer01', '123456', 'buyer01@artist.com', '13800000001', 'https://images.unsplash.com/photo-1494790108377-be9c29b29330?auto=format&fit=crop&w=400&q=80', '委托人小鹿', '长期发布头像与封面委托需求。', 'USER', 'ACTIVE', 5200.00, TIMESTAMP '2026-04-02 10:00:00', TIMESTAMP '2026-04-12 18:00:00'),
    (3, 'artist01', '123456', 'artist01@artist.com', '13800000002', 'https://images.unsplash.com/photo-1500648767791-00dcc994a43e?auto=format&fit=crop&w=400&q=80', '星川绘', '擅长日系插画与角色立绘。', 'ARTIST', 'ACTIVE', 1680.00, TIMESTAMP '2026-04-02 11:00:00', TIMESTAMP '2026-04-12 18:30:00'),
    (4, 'artist02', '123456', 'artist02@artist.com', '13800000003', 'https://images.unsplash.com/photo-1544005313-94ddf0286df2?auto=format&fit=crop&w=400&q=80', '墨昼', '偏厚涂与海报概念设计。', 'USER', 'ACTIVE', 320.00, TIMESTAMP '2026-04-03 09:30:00', TIMESTAMP '2026-04-11 16:20:00');

MERGE INTO artist (id, user_id, real_name, id_card, style, price_min, price_max, delivery_days, accept_types, status, rating, order_count, create_time, update_time) KEY(id) VALUES
    (1, 3, '陈星川', '320101199801018888', '日系插画,角色立绘,Live2D拆件', 300.00, 1800.00, 7, '头像,半身,立绘', 'APPROVED', 4.90, 12, TIMESTAMP '2026-04-03 10:00:00', TIMESTAMP '2026-04-12 18:30:00'),
    (2, 4, '周墨', '320101199901019999', '厚涂海报,概念设计', 500.00, 2600.00, 10, '海报,封面,场景', 'PENDING', 5.00, 0, TIMESTAMP '2026-04-04 15:00:00', TIMESTAMP '2026-04-11 16:20:00');

MERGE INTO portfolio (id, artist_id, title, description, image_url, category, tags, is_featured, view_count, like_count, create_time, update_time) KEY(id) VALUES
    (1, 1, '春日头像委托展示', '清新日系头像，适合社交平台展示。', 'https://images.unsplash.com/photo-1517841905240-472988babdf9?auto=format&fit=crop&w=800&q=80', '头像', '日系,头像,清新', 1, 126, 38, TIMESTAMP '2026-04-05 10:00:00', TIMESTAMP '2026-04-12 11:00:00'),
    (2, 1, '双人纪念插画', '带有场景氛围的双人半身纪念插画。', 'https://images.unsplash.com/photo-1524504388940-b1c1722653e1?auto=format&fit=crop&w=800&q=80', '插画', '情侣,半身,氛围感', 0, 88, 21, TIMESTAMP '2026-04-06 14:20:00', TIMESTAMP '2026-04-12 11:20:00');

MERGE INTO demand (id, user_id, title, description, type, size, style, budget_min, budget_max, deadline, ref_images, target_artist_id, status, create_time, update_time) KEY(id) VALUES
    (1, 2, '直播间头像委托', '需要一张偏可爱风的直播头像，要求人物表情活泼，背景简洁。', '头像', '2048x2048', '可爱,日系', 300.00, 600.00, DATE '2026-04-30', 'https://images.unsplash.com/photo-1529626455594-4ff0802cfb7e?auto=format&fit=crop&w=800&q=80', NULL, 'OPEN', TIMESTAMP '2026-04-10 09:00:00', TIMESTAMP '2026-04-10 09:00:00'),
    (2, 2, '小说封面定制', '奇幻题材小说封面，需要主角半身与星空背景。', '封面', '3000x4500', '唯美,幻想', 800.00, 1500.00, DATE '2026-05-06', 'https://images.unsplash.com/photo-1516321318423-f06f85e504b3?auto=format&fit=crop&w=800&q=80', 1, 'ACCEPTED', TIMESTAMP '2026-04-09 14:00:00', TIMESTAMP '2026-04-10 18:00:00'),
    (3, 2, '角色立绘约稿', '用于同人企划的角色立绘，需要保留服装细节。', '立绘', '2480x3508', '精致,二次元', 1200.00, 1800.00, DATE '2026-05-10', 'https://images.unsplash.com/photo-1511988617509-a57c8a288659?auto=format&fit=crop&w=800&q=80', 1, 'ACCEPTED', TIMESTAMP '2026-04-08 16:30:00', TIMESTAMP '2026-04-11 17:30:00'),
    (4, 2, '周年纪念海报', '社团周年活动海报，需要人物群像和活动信息排版区域。', '海报', 'A3', '青春,活动感', 1500.00, 2200.00, DATE '2026-05-15', 'https://images.unsplash.com/photo-1521737604893-d14cc237f11d?auto=format&fit=crop&w=800&q=80', 1, 'CLOSED', TIMESTAMP '2026-04-07 19:00:00', TIMESTAMP '2026-04-12 20:30:00');

MERGE INTO `order` (id, order_no, demand_id, user_id, artist_id, title, description, total_price, deposit, final_payment, status, draft_url, final_url, revise_count, max_revise, commission_rate, create_time, update_time) KEY(id) VALUES
    (1, 'ORD202604140001', 2, 2, 1, '小说封面定制', '封面主视觉已经确认，等待支付定金进入制作。', 1200.00, 360.00, 840.00, 'PENDING_DEPOSIT', NULL, NULL, 0, 2, 8.00, TIMESTAMP '2026-04-10 18:10:00', TIMESTAMP '2026-04-10 18:10:00'),
    (2, 'ORD202604140002', 3, 2, 1, '角色立绘约稿', '画师已提交草图，等待客户确认。', 1600.00, 480.00, 1120.00, 'DRAFT_SUBMITTED', 'https://images.unsplash.com/photo-1517841905240-472988babdf9?auto=format&fit=crop&w=800&q=80', NULL, 1, 2, 8.00, TIMESTAMP '2026-04-09 09:30:00', TIMESTAMP '2026-04-12 13:00:00'),
    (3, 'ORD202604140003', 4, 2, 1, '周年纪念海报', '终稿已提交，等待客户支付尾款。', 2000.00, 600.00, 1400.00, 'PENDING_FINAL_PAYMENT', 'https://images.unsplash.com/photo-1521737604893-d14cc237f11d?auto=format&fit=crop&w=800&q=80', 'https://images.unsplash.com/photo-1516321318423-f06f85e504b3?auto=format&fit=crop&w=800&q=80', 1, 2, 8.00, TIMESTAMP '2026-04-08 11:20:00', TIMESTAMP '2026-04-12 20:00:00'),
    (4, 'ORD202604140004', 4, 2, 1, '活动 KV 延展图', '订单已完成并完成评价。', 1800.00, 540.00, 1260.00, 'COMPLETED', 'https://images.unsplash.com/photo-1524504388940-b1c1722653e1?auto=format&fit=crop&w=800&q=80', 'https://images.unsplash.com/photo-1511988617509-a57c8a288659?auto=format&fit=crop&w=800&q=80', 0, 2, 8.00, TIMESTAMP '2026-04-06 13:00:00', TIMESTAMP '2026-04-12 09:00:00');

MERGE INTO payment (id, order_id, user_id, amount, type, status, create_time) KEY(id) VALUES
    (1, 4, 2, 540.00, 'DEPOSIT', 'SUCCESS', TIMESTAMP '2026-04-06 13:10:00'),
    (2, 4, 2, 1260.00, 'FINAL_PAYMENT', 'SUCCESS', TIMESTAMP '2026-04-11 18:00:00');

MERGE INTO review (id, order_id, from_user_id, to_user_id, rating, quality_rating, attitude_rating, speed_rating, content, tags, create_time) KEY(id) VALUES
    (1, 4, 2, 3, 5, 5, 5, 4, '沟通顺畅，交付准时，成品质量很好。', '高质量,准时,沟通顺畅', TIMESTAMP '2026-04-12 09:30:00');

MERGE INTO message (id, user_id, title, content, type, related_id, is_read, create_time) KEY(id) VALUES
    (1, 3, '新订单提醒', '您有新的小说封面定制订单，请及时确认需求。', 'ORDER', 1, 0, TIMESTAMP '2026-04-10 18:15:00'),
    (2, 2, '尾款待支付', '周年纪念海报终稿已提交，请尽快完成尾款支付。', 'ORDER', 3, 0, TIMESTAMP '2026-04-12 20:05:00');

MERGE INTO withdraw (id, user_id, amount, account_type, account_no, status, remark, create_time, update_time) KEY(id) VALUES
    (1, 3, 500.00, 'ALIPAY', 'artist01@artist.com', 'PENDING', '申请提现至支付宝', TIMESTAMP '2026-04-12 21:00:00', TIMESTAMP '2026-04-12 21:00:00');
