USE wedding_photo_studio;

INSERT INTO `user` (username, password, real_name, phone, email, role, status) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '系统管理员', '13800138000', 'admin@wedding.com', 'ADMIN', 1),
('manager', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '门店经理', '13800138001', 'manager@wedding.com', 'MANAGER', 1),
('finance', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '财务人员', '13800138002', 'finance@wedding.com', 'FINANCE', 1),
('photographer1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '张摄影师', '13800138003', 'photo1@wedding.com', 'PHOTOGRAPHER', 1),
('photographer2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '李摄影师', '13800138004', 'photo2@wedding.com', 'PHOTOGRAPHER', 1),
('customer1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '王客户', '13900139001', 'customer1@email.com', 'CUSTOMER', 1),
('customer2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '李客户', '13900139002', 'customer2@email.com', 'CUSTOMER', 1);

INSERT INTO photographer (user_id, name, phone, level, specialty, introduction, status, commission_rate) VALUES
(4, '张摄影师', '13800138003', '首席', '婚纱摄影,韩式风格', '10年婚纱摄影经验，擅长韩式浪漫风格', 1, 15.00),
(5, '李摄影师', '13800138004', '高级', '写真摄影,复古风格', '8年摄影经验，擅长复古文艺风格', 1, 12.00);

INSERT INTO customer (user_id, name, gender, age, phone, email, customer_type, source) VALUES
(6, '王客户', 1, 28, '13900139001', 'customer1@email.com', '婚纱客户', '网络'),
(7, '李客户', 2, 26, '13900139002', 'customer2@email.com', '写真客户', '门店');

INSERT INTO package (name, category, price, description, content, costume_count, refined_photo_count, shooting_duration, makeup_count, scene_count, gift, status, sort_order) VALUES
('经典婚纱套餐', '婚纱摄影', 3999.00, '适合预算有限的新人，包含基础服务', '3套服装，30张精修，拍摄4小时', 3, 30, 240, 3, 2, '相框1个，摆台2个', 1, 1),
('豪华婚纱套餐', '婚纱摄影', 6999.00, '高端婚纱套餐，服务全面', '5套服装，50张精修，拍摄6小时', 5, 50, 360, 5, 3, '相框2个，摆台3个，相册1本', 1, 2),
('至尊婚纱套餐', '婚纱摄影', 9999.00, '顶级婚纱套餐，尊享服务', '8套服装，80张精修，拍摄8小时，含外景', 8, 80, 480, 8, 5, '相框3个，摆台5个，相册2本，婚纱一件', 1, 3),
('个人写真套餐', '个人写真', 1999.00, '展现个人魅力的写真套餐', '3套服装，20张精修，拍摄3小时', 3, 20, 180, 3, 2, '相框1个', 1, 4),
('闺蜜写真套餐', '个人写真', 2999.00, '闺蜜专属写真套餐', '4套服装，40张精修，拍摄4小时', 4, 40, 240, 4, 3, '相框2个，摆台2个', 1, 5),
('儿童摄影套餐', '儿童摄影', 1599.00, '记录宝贝成长的美好时刻', '3套服装，15张精修，拍摄2小时', 3, 15, 120, 2, 2, '相框1个，摆台1个', 1, 6),
('全家福套餐', '全家福', 1299.00, '温馨家庭照', '2套服装，12张精修，拍摄2小时', 2, 12, 120, 1, 1, '相框1个', 1, 7);

INSERT INTO studio (name, area, style, equipment, description, status) VALUES
('韩式影棚A', 80.00, '韩式', 'LED灯光系统，背景布，道具', '明亮温馨的韩式风格影棚', 1),
('复古影棚B', 100.00, '复古', 'LED灯光系统，复古家具，背景布', '充满怀旧气息的复古影棚', 1),
('简约影棚C', 60.00, '简约', 'LED灯光系统，纯色背景', '简洁大方的现代风格影棚', 1),
('欧式影棚D', 120.00, '欧式', 'LED灯光系统，欧式家具，水晶灯', '奢华典雅的欧式影棚', 1);

INSERT INTO costume (code, name, category, size, color, status, purchase_date, purchase_price) VALUES
('WD001', '公主款白纱', '婚纱', 'M', '白色', 'AVAILABLE', '2024-01-01', 5000.00),
('WD002', '鱼尾款白纱', '婚纱', 'S', '白色', 'AVAILABLE', '2024-01-01', 6000.00),
('WD003', 'A字款白纱', '婚纱', 'L', '白色', 'AVAILABLE', '2024-01-01', 4500.00),
('WD004', '红色秀禾服', '中式', 'M', '红色', 'AVAILABLE', '2024-01-15', 3000.00),
('WD005', '龙凤褂', '中式', 'S', '红色', 'AVAILABLE', '2024-01-15', 4000.00),
('LS001', '蓝色礼服', '礼服', 'M', '蓝色', 'AVAILABLE', '2024-02-01', 2000.00),
('LS002', '粉色礼服', '礼服', 'S', '粉色', 'AVAILABLE', '2024-02-01', 1800.00),
('LS003', '黑色礼服', '礼服', 'L', '黑色', 'AVAILABLE', '2024-02-01', 2200.00),
('XZ001', '白色连衣裙', '写真服装', 'M', '白色', 'AVAILABLE', '2024-03-01', 800.00),
('XZ002', '学院风制服', '写真服装', 'S', '蓝色', 'AVAILABLE', '2024-03-01', 600.00);

INSERT INTO prop (code, name, category, quantity, status) VALUES
('P001', '白色背景布', '背景布', 5, 'AVAILABLE'),
('P002', '粉色背景布', '背景布', 3, 'AVAILABLE'),
('P003', '花束道具', '饰品', 10, 'AVAILABLE'),
('P004', '婚戒道具', '饰品', 8, 'AVAILABLE'),
('P005', '复古椅子', '家具', 4, 'AVAILABLE'),
('P006', '欧式沙发', '家具', 2, 'AVAILABLE'),
('P007', '气球装饰', '装饰', 20, 'AVAILABLE');

INSERT INTO system_config (config_key, config_value, config_type, description) VALUES
('store_name', '幸福时光婚纱写真馆', 'STRING', '门店名称'),
('store_phone', '400-888-6666', 'STRING', '门店电话'),
('store_address', '北京市朝阳区某某路123号', 'STRING', '门店地址'),
('business_hours', '09:00-21:00', 'STRING', '营业时间'),
('time_slots', '上午,下午,晚上', 'STRING', '预约时段'),
('deposit_ratio', '0.3', 'NUMBER', '定金比例'),
('photographer_commission', '0.15', 'NUMBER', '摄影师提成比例'),
('max_upload_size', '10', 'NUMBER', '最大上传文件大小(MB)');
