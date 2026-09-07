MERGE INTO sys_user KEY(id) VALUES
(1, 'admin', '21232f297a57a5a743894a0e4a801fc3', '平台管理员', NULL, '13800000000', 'ADMIN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO sys_user KEY(id) VALUES
(2, 'user1', 'e10adc3949ba59abbe56e057f20f883e', '爱心用户', NULL, '13900000001', 'USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO pet_info KEY(id) VALUES
(1, '奶糖', 'Cat', '中华田园猫', '8个月', 'Female', 'AVAILABLE', '性格温顺，已完成基础驱虫，适合家庭领养。', 'https://images.unsplash.com/photo-1511044568932-338cba0ad803?auto=format&fit=crop&w=800&q=80', '健康良好', '已完成首针疫苗', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO pet_info KEY(id) VALUES
(2, '豆包', 'Dog', '中华田园犬', '1岁', 'Male', 'PENDING', '非常亲人，喜欢户外活动，目前正在等待领养审核结果。', 'https://images.unsplash.com/photo-1517849845537-4d257902454a?auto=format&fit=crop&w=800&q=80', '状态稳定', '已完成疫苗接种', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO pet_info KEY(id) VALUES
(3, '雪球', 'Cat', '英短混血', '2岁', 'Female', 'ADOPTED', '曾被救助站长期照顾，现已成功领养。', 'https://images.unsplash.com/photo-1574158622682-e40e69881006?auto=format&fit=crop&w=800&q=80', '健康良好', '疫苗齐全', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

MERGE INTO banner KEY(id) VALUES
(1, '紧急救助', 'https://images.unsplash.com/photo-1548681528-6a5c45b66b42?auto=format&fit=crop&w=1200&q=80', '/pet/list', 1, TRUE, CURRENT_TIMESTAMP);
