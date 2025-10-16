USE campus_share;

INSERT INTO `user` (`id`, `username`, `password`, `nickname`, `avatar`, `phone`, `student_id`, `school`, `college`, `real_name`, `id_card`, `auth_status`, `credit_score`, `deposit_balance`, `account_balance`, `total_orders`, `total_income`, `role`, `status`, `create_time`, `update_time`) VALUES
(1, 'admin', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '系统管理员', NULL, '13800138000', NULL, NULL, NULL, NULL, NULL, 0, 100, 0.00, 0.00, 0, 0.00, 'ADMIN', 1, NOW(), NOW()),
(2, 'operator', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '运营人员', NULL, '13800138001', NULL, '校园大学', NULL, NULL, NULL, 0, 100, 0.00, 0.00, 0, 0.00, 'OPERATOR', 1, NOW(), NOW()),
(3, 'test1', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '张三', NULL, '13800138002', '2021001', '校园大学', '计算机学院', '张三', '110101199001011234', 2, 100, 200.00, 100.00, 5, 0.00, 'USER', 1, NOW(), NOW()),
(4, 'test2', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '李四', NULL, '13800138003', '2021002', '校园大学', '经济学院', '李四', '110101199001021234', 2, 98, 150.00, 200.00, 3, 150.00, 'PROVIDER', 1, NOW(), NOW()),
(5, 'test3', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '王五', NULL, '13800138004', '2021003', '校园大学', '艺术学院', '王五', '110101199001031234', 2, 105, 100.00, 150.00, 2, 80.00, 'PROVIDER', 1, NOW(), NOW()),
(6, 'test4', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '赵六', NULL, '13800138005', '2021004', '校园大学', '外语学院', NULL, NULL, 0, 100, 0.00, 50.00, 0, 0.00, 'USER', 1, NOW(), NOW()),
(7, 'test5', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '钱七', NULL, '13800138006', '2021005', '校园大学', '理学院', '钱七', '110101199001041234', 2, 92, 80.00, 100.00, 8, 200.00, 'PROVIDER', 1, NOW(), NOW()),
(8, 'test6', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '孙八', NULL, '13800138007', '2021006', '校园大学', '工学院', '孙八', '110101199001051234', 2, 110, 300.00, 80.00, 4, 120.00, 'PROVIDER', 1, NOW(), NOW()),
(9, 'test7', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '周九', NULL, '13800138008', '2021007', '校园大学', '文学院', NULL, NULL, 1, 100, 0.00, 0.00, 0, 0.00, 'USER', 1, NOW(), NOW()),
(10, 'test8', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '吴十', NULL, '13800138009', '2021008', '校园大学', '法学院', '吴十', '110101199001061234', 2, 95, 120.00, 90.00, 6, 300.00, 'PROVIDER', 1, NOW(), NOW());

INSERT INTO `shared_item` (`id`, `item_no`, `item_type`, `item_model`, `latitude`, `longitude`, `location_name`, `battery_level`, `status`, `hourly_price`, `daily_max_price`, `deposit_amount`, `total_usage_count`, `total_distance`, `create_time`, `update_time`) VALUES
(1, 'BIKE001', 'BIKE', '共享单车标准版', 39.915119, 116.403963, '图书馆门口', 100, 0, 2.00, 20.00, 99.00, 156, 1520.50, NOW(), NOW()),
(2, 'BIKE002', 'BIKE', '共享单车标准版', 39.916428, 116.405285, '教学楼A座', 85, 0, 2.00, 20.00, 99.00, 203, 2150.30, NOW(), NOW()),
(3, 'BIKE003', 'BIKE', '共享单车轻便版', 39.914520, 116.402841, '宿舍区1号楼', 92, 0, 2.00, 20.00, 99.00, 89, 980.00, NOW(), NOW()),
(4, 'BIKE004', 'BIKE', '共享单车标准版', 39.917635, 116.406491, '食堂北门', 78, 0, 2.00, 20.00, 99.00, 312, 3200.80, NOW(), NOW()),
(5, 'BIKE005', 'BIKE', '共享电动车', 39.913842, 116.401523, '体育馆', 65, 0, 3.00, 30.00, 199.00, 145, 1680.00, NOW(), NOW()),
(6, 'BIKE006', 'BIKE', '共享单车标准版', 39.918234, 116.407852, '实验楼', 95, 0, 2.00, 20.00, 99.00, 78, 720.50, NOW(), NOW()),
(7, 'BIKE007', 'BIKE', '共享单车轻便版', 39.912956, 116.400214, '南门', 88, 1, 2.00, 20.00, 99.00, 267, 2890.00, NOW(), NOW()),
(8, 'BIKE008', 'BIKE', '共享电动车', 39.919123, 116.408563, '北门', 72, 0, 3.00, 30.00, 199.00, 198, 2340.60, NOW(), NOW()),
(9, 'BIKE009', 'BIKE', '共享单车标准版', 39.911845, 116.399105, '操场', 100, 0, 2.00, 20.00, 99.00, 112, 1150.00, NOW(), NOW()),
(10, 'BIKE010', 'BIKE', '共享单车轻便版', 39.920012, 116.409674, '东门', 90, 0, 2.00, 20.00, 99.00, 95, 890.30, NOW(), NOW()),
(11, 'CHARGER001', 'CHARGER', '共享充电宝10000mAh', 39.915119, 116.403963, '图书馆1楼', 100, 0, 1.00, 10.00, 0.00, 89, 0.00, NOW(), NOW()),
(12, 'CHARGER002', 'CHARGER', '共享充电宝10000mAh', 39.916428, 116.405285, '教学楼A座大厅', 100, 0, 1.00, 10.00, 0.00, 156, 0.00, NOW(), NOW()),
(13, 'CHARGER003', 'CHARGER', '共享充电宝10000mAh', 39.917635, 116.406491, '食堂1楼', 85, 0, 1.00, 10.00, 0.00, 234, 0.00, NOW(), NOW()),
(14, 'CHARGER004', 'CHARGER', '共享充电宝20000mAh', 39.914520, 116.402841, '宿舍区1号楼大厅', 92, 1, 1.50, 12.00, 0.00, 178, 0.00, NOW(), NOW()),
(15, 'CHARGER005', 'CHARGER', '共享充电宝10000mAh', 39.913842, 116.401523, '体育馆', 78, 0, 1.00, 10.00, 0.00, 67, 0.00, NOW(), NOW()),
(16, 'UMBRELLA001', 'UMBRELLA', '共享雨伞标准版', 39.915119, 116.403963, '图书馆门口', NULL, 0, 0.50, 5.00, 10.00, 45, 0.00, NOW(), NOW()),
(17, 'UMBRELLA002', 'UMBRELLA', '共享雨伞标准版', 39.916428, 116.405285, '教学楼A座门口', NULL, 0, 0.50, 5.00, 10.00, 67, 0.00, NOW(), NOW()),
(18, 'UMBRELLA003', 'UMBRELLA', '共享雨伞标准版', 39.917635, 116.406491, '食堂门口', NULL, 0, 0.50, 5.00, 10.00, 89, 0.00, NOW(), NOW()),
(19, 'UMBRELLA004', 'UMBRELLA', '共享雨伞加大版', 39.914520, 116.402841, '宿舍区门口', NULL, 0, 0.50, 5.00, 10.00, 34, 0.00, NOW(), NOW()),
(20, 'UMBRELLA005', 'UMBRELLA', '共享雨伞标准版', 39.918234, 116.407852, '实验楼门口', NULL, 0, 0.50, 5.00, 10.00, 23, 0.00, NOW(), NOW());

INSERT INTO `idle_item` (`id`, `user_id`, `category`, `title`, `description`, `images`, `condition_level`, `original_price`, `hourly_price`, `daily_price`, `weekly_price`, `deposit_amount`, `pickup_method`, `pickup_address`, `latitude`, `longitude`, `contact_phone`, `view_count`, `order_count`, `rating`, `status`, `create_time`, `update_time`) VALUES
(1, 4, '数码设备', 'Canon EOS R6 相机', '九成新佳能专业相机，适合摄影爱好者租用，配有24-105镜头，电池充满可拍摄一整天', '["https://example.com/camera1.jpg","https://example.com/camera2.jpg"]', 4, 18999.00, NULL, 150.00, 800.00, 1000.00, 2, '计算机学院宿舍楼', 39.915119, 116.403963, '13800138003', 256, 18, 4.85, 1, NOW(), NOW()),
(2, 5, '乐器', '雅马哈电吉他', '九五新电吉他，音色优美，适合初学者或练习使用', '["https://example.com/guitar1.jpg"]', 3, 2800.00, 10.00, 50.00, 300.00, 300.00, 1, '艺术学院琴房', 39.916428, 116.405285, '13800138004', 189, 12, 4.70, 1, NOW(), NOW()),
(3, 7, '数码设备', '索尼A7M3 微单相机', '全新未拆封，可长租短租，配齐全套配件', '["https://example.com/sony1.jpg","https://example.com/sony2.jpg"]', 1, 12999.00, NULL, 180.00, 1000.00, 1500.00, 2, '理学院实验室', 39.917635, 116.406491, '13800138006', 345, 25, 4.95, 1, NOW(), NOW()),
(4, 8, '投影设备', '明基投影仪 + 幕布套装', '九九新投影仪，适合寝室观影、小组讨论使用，1080P高清', '["https://example.com/projector1.jpg"]', 2, 3500.00, 15.00, 80.00, 400.00, 500.00, 2, '工学院宿舍', 39.914520, 116.402841, '13800138007', 298, 22, 4.80, 1, NOW(), NOW()),
(5, 10, '数码设备', 'iPad Pro 12.9寸', '九成新平板，适合绘画、笔记，配有Apple Pencil', '["https://example.com/ipad1.jpg"]', 4, 8999.00, NULL, 60.00, 350.00, 600.00, 1, '法学院图书馆', 39.913842, 116.401523, '13800138009', 412, 31, 4.90, 1, NOW(), NOW()),
(6, 4, '运动器材', '专业滑板套装', '九五新滑板，适合初学者，有护具齐全', '["https://example.com/skateboard1.jpg"]', 3, 800.00, 8.00, 30.00, 150.00, 100.00, 1, '体育馆旁', 39.918234, 116.407852, '13800138003', 178, 15, 4.65, 1, NOW(), NOW()),
(7, 5, '服装道具', '汉服套装（女款）', '全新汉服，适合拍照、活动穿着，多款可选', '["https://example.com/hanfu1.jpg","https://example.com/hanfu2.jpg"]', 1, 600.00, NULL, 40.00, 200.00, 150.00, 1, '艺术学院服装室', 39.912956, 116.400214, '13800138004', 523, 38, 4.88, 1, NOW(), NOW()),
(8, 7, '数码设备', '大疆无人机 Mini 3', '九九新无人机，可航拍，续航时间长', '["https://example.com/drone1.jpg"]', 2, 4799.00, NULL, 100.00, 500.00, 800.00, 2, '操场旁停车场', 39.919123, 116.408563, '13800138006', 267, 19, 4.75, 1, NOW(), NOW()),
(9, 8, '乐器', '雅马哈电子琴', '八成新电子琴，适合初学者练习', '["https://example.com/keyboard1.jpg"]', 5, 1500.00, 8.00, 40.00, 200.00, 200.00, 1, '工学院活动室', 39.911845, 116.399105, '13800138007', 145, 10, 4.60, 1, NOW(), NOW()),
(10, 10, '图书教材', '考研数学全套资料', '去年考研用的资料，包含视频课程U盘', '["https://example.com/book1.jpg"]', 4, 800.00, NULL, 15.00, 80.00, 50.00, 0, '法学院自习室', 39.920012, 116.409674, '13800138009', 389, 28, 4.92, 1, NOW(), NOW()),
(11, 4, '数码设备', 'GoPro Hero 11运动相机', '九成新运动相机，防水防震，适合户外拍摄', '["https://example.com/gopro1.jpg"]', 4, 3299.00, NULL, 60.00, 300.00, 400.00, 1, '计算机学院', 39.915119, 116.403963, '13800138003', 198, 14, 4.70, 1, NOW(), NOW()),
(12, 5, '服装道具', 'Cosplay服装-动漫角色', '全新Cosplay服装，多款角色可选', '["https://example.com/cosplay1.jpg"]', 1, 500.00, NULL, 35.00, 180.00, 100.00, 1, '艺术学院', 39.916428, 116.405285, '13800138004', 312, 22, 4.85, 1, NOW(), NOW()),
(13, 7, '投影设备', '便携投影仪', '全新便携投影仪，可连手机投屏', '["https://example.com/mini_projector1.jpg"]', 1, 1299.00, 12.00, 50.00, 250.00, 200.00, 1, '理学院', 39.917635, 116.406491, '13800138006', 234, 17, 4.78, 1, NOW(), NOW()),
(14, 8, '运动器材', '羽毛球拍套装', '九成新羽毛球拍，含球和包', '["https://example.com/badminton1.jpg"]', 4, 450.00, 5.00, 20.00, 100.00, 80.00, 1, '工学院体育馆', 39.914520, 116.402841, '13800138007', 167, 13, 4.68, 1, NOW(), NOW()),
(15, 10, '数码设备', '戴森吹风机', '九五新戴森吹风机，适合寝室共享使用', '["https://example.com/dyson1.jpg"]', 3, 2990.00, NULL, 25.00, 120.00, 150.00, 0, '法学院宿舍', 39.913842, 116.401523, '13800138009', 456, 32, 4.88, 1, NOW(), NOW()),
(16, 4, '数码设备', 'MacBook Pro 14寸', '九成新MacBook，适合设计、剪辑使用', '["https://example.com/macbook1.jpg"]', 4, 15999.00, NULL, 120.00, 700.00, 1000.00, 2, '计算机学院', 39.918234, 116.407852, '13800138003', 389, 26, 4.90, 1, NOW(), NOW()),
(17, 5, '乐器', '尤克里里', '九九新尤克里里，适合初学者', '["https://example.com/ukulele1.jpg"]', 2, 380.00, 6.00, 25.00, 120.00, 80.00, 1, '艺术学院', 39.912956, 116.400214, '13800138004', 278, 20, 4.75, 1, NOW(), NOW()),
(18, 7, '运动器材', '帐篷套装（4人）', '全新帐篷，适合露营、野餐', '["https://example.com/tent1.jpg"]', 1, 800.00, NULL, 40.00, 200.00, 150.00, 1, '理学院', 39.919123, 116.408563, '13800138006', 201, 16, 4.82, 1, NOW(), NOW()),
(19, 8, '数码设备', '索尼降噪耳机', '九成新降噪耳机，音质优秀', '["https://example.com/headphone1.jpg"]', 4, 2299.00, NULL, 30.00, 150.00, 200.00, 0, '工学院', 39.911845, 116.399105, '13800138007', 345, 24, 4.87, 1, NOW(), NOW()),
(20, 10, '图书教材', '专业课教材全套', '九成新专业课教材，法学相关', '["https://example.com/law_books1.jpg"]', 4, 600.00, NULL, 10.00, 50.00, 30.00, 0, '法学院', 39.920012, 116.409674, '13800138009', 289, 21, 4.79, 1, NOW(), NOW());

INSERT INTO `skill_service` (`id`, `user_id`, `category`, `title`, `description`, `images`, `portfolio`, `service_duration`, `hourly_price`, `service_location`, `location_type`, `latitude`, `longitude`, `introduction`, `view_count`, `order_count`, `rating`, `status`, `create_time`, `update_time`) VALUES
(1, 4, '学业辅导', '高数一对一辅导', '计算机专业研究生，高数成绩优异，有2年辅导经验，善于讲解难点', '["https://example.com/tutor1.jpg"]', '["https://example.com/cert1.jpg"]', 120, 80.00, '图书馆或线上', 2, NULL, NULL, '本人计算机专业研二学生，高数满分通过，辅导过多名学生，效果显著', 456, 32, 4.92, 1, NOW(), NOW()),
(2, 5, '设计服务', 'PPT设计制作', '专业PPT设计，包含答辩、汇报、商业计划书等各类PPT，一对一定制', '["https://example.com/ppt1.jpg","https://example.com/ppt2.jpg"]', '["https://example.com/ppt_work1.jpg","https://example.com/ppt_work2.jpg"]', 180, 100.00, '线上服务', 2, NULL, NULL, '艺术设计专业，精通各类设计软件，服务过50+客户', 523, 45, 4.88, 1, NOW(), NOW()),
(3, 7, '摄影服务', '证件照拍摄', '专业证件照拍摄，包含精修，多种尺寸，当天出图', '["https://example.com/photo1.jpg"]', '["https://example.com/photo_work1.jpg"]', 30, 50.00, '理学院摄影棚', 1, 39.917635, 116.406491, '摄影专业大三学生，有专业摄影棚和设备，拍摄经验丰富', 389, 56, 4.95, 1, NOW(), NOW()),
(4, 8, '学业辅导', '英语四六级辅导', '英语专业八级，四六级高分通过，提供听说读写全面辅导', '["https://example.com/english1.jpg"]', '["https://example.com/cert_cet.jpg"]', 120, 70.00, '自习室或线上', 2, NULL, NULL, '英语专业研一，专八优秀，辅导学生通过率95%', 412, 38, 4.90, 1, NOW(), NOW()),
(5, 10, '设计服务', 'UI界面设计', '专业UI设计，APP、网页、小程序界面设计，提供源文件', '["https://example.com/ui1.jpg"]', '["https://example.com/ui_work1.jpg","https://example.com/ui_work2.jpg"]', 240, 150.00, '线上服务', 2, NULL, NULL, '设计专业大四，参与过多个项目，熟悉Figma、Sketch等工具', 478, 28, 4.85, 1, NOW(), NOW()),
(6, 4, '学业辅导', 'Python编程入门', 'Python零基础教学，从入门到实战项目，包教包会', '["https://example.com/python1.jpg"]', '["https://example.com/code1.jpg"]', 180, 90.00, '线上或实验室', 2, NULL, NULL, '计算机研二，Python开发经验3年，参与过多个项目', 345, 25, 4.88, 1, NOW(), NOW()),
(7, 5, '摄影服务', '活动摄影跟拍', '活动现场摄影，包含后期修图，提供高清原图和精修图', '["https://example.com/event1.jpg"]', '["https://example.com/event_work1.jpg"]', 240, 200.00, '校内各场地', 0, NULL, NULL, '艺术学院摄影社社长，拍摄过上百场活动，经验丰富', 567, 41, 4.93, 1, NOW(), NOW()),
(8, 7, '健身指导', '减脂增肌私教', '专业健身指导，制定个性化训练计划，饮食建议', '["https://example.com/fitness1.jpg"]', '["https://example.com/body1.jpg"]', 60, 80.00, '校体育馆', 1, 39.913842, 116.401523, '体育学院大四，国家二级健身教练，帮助多人成功减脂', 298, 22, 4.87, 1, NOW(), NOW()),
(9, 8, '设计服务', '视频剪辑制作', '专业视频剪辑，Vlog、宣传片、短视频等，提供创意策划', '["https://example.com/video1.jpg"]', '["https://example.com/video_work1.mp4"]', 180, 120.00, '线上服务', 2, NULL, NULL, '传媒专业，精通PR、AE等软件，作品获奖多次', 434, 35, 4.89, 1, NOW(), NOW()),
(10, 10, '学业辅导', '考研法学辅导', '法学考研专业课辅导，提供真题讲解、答题技巧', '["https://example.com/law1.jpg"]', '["https://example.com/law_cert.jpg"]', 120, 100.00, '图书馆或线上', 2, NULL, NULL, '法学研二，初试专业课140+，有辅导经验', 389, 28, 4.91, 1, NOW(), NOW()),
(11, 4, '代做服务', '校园代购跑腿', '提供校内外代购、代取快递、代排队等服务', '["https://example.com/delivery1.jpg"]', NULL, 30, 15.00, '校内各区域', 0, NULL, NULL, '时间灵活，服务热情，校内各区域均可送达', 678, 89, 4.82, 1, NOW(), NOW()),
(12, 5, '音乐教学', '吉他教学', '吉他零基础教学，包含弹唱、指弹，提供吉他租借', '["https://example.com/guitar_teach1.jpg"]', '["https://example.com/guitar_show.mp4"]', 90, 60.00, '艺术学院琴房', 1, 39.916428, 116.405285, '音乐学院大三，吉他十级，教学经验2年', 312, 26, 4.86, 1, NOW(), NOW()),
(13, 7, '设计服务', '平面设计', '海报、传单、LOGO、名片等平面设计，提供印刷建议', '["https://example.com/design1.jpg"]', '["https://example.com/design_work1.jpg"]', 120, 80.00, '线上服务', 2, NULL, NULL, '设计专业研一，熟练使用AI、PS等软件', 289, 31, 4.84, 1, NOW(), NOW()),
(14, 8, '学业辅导', '数据结构与算法', '计算机专业核心课程辅导，算法竞赛指导', '["https://example.com/algorithm1.jpg"]', '["https://example.com/code2.jpg"]', 120, 90.00, '线上或实验室', 2, NULL, NULL, '计算机专业研一，ACM铜牌，算法基础扎实', 267, 19, 4.88, 1, NOW(), NOW()),
(15, 10, '摄影服务', '写真拍摄', '个人写真、毕业照拍摄，包含精修10张，提供服装建议', '["https://example.com/portrait1.jpg"]', '["https://example.com/portrait_work1.jpg"]', 120, 150.00, '校园各景点', 0, NULL, NULL, '摄影爱好者，拍摄过上百组写真，风格多样', 445, 37, 4.92, 1, NOW(), NOW()),
(16, 4, '学业辅导', 'Java编程', 'Java从入门到精通，Spring框架教学，项目实战', '["https://example.com/java1.jpg"]', NULL, 180, 100.00, '线上服务', 2, NULL, NULL, '计算机研二，Java开发经验4年，参与多个商业项目', 356, 24, 4.89, 1, NOW(), NOW()),
(17, 5, '健身指导', '瑜伽教学', '瑜伽零基础教学，提供个性化课程，改善体态', '["https://example.com/yoga1.jpg"]', '["https://example.com/yoga_cert.jpg"]', 60, 60.00, '体育馆瑜伽室', 1, 39.913842, 116.401523, '瑜伽教练资格证，教学经验1年，学员反馈好', 389, 33, 4.90, 1, NOW(), NOW()),
(18, 7, '设计服务', '简历优化', '专业简历设计优化，提高面试通过率，包含求职指导', '["https://example.com/resume1.jpg"]', '["https://example.com/resume_work1.jpg"]', 60, 50.00, '线上服务', 2, NULL, NULL, '人力资源专业，熟悉各行业简历要求，帮助多人成功求职', 501, 42, 4.87, 1, NOW(), NOW()),
(19, 8, '音乐教学', '声乐指导', '声乐基础教学，唱歌技巧提升，帮助找到适合自己的唱法', '["https://example.com/vocal1.jpg"]', NULL, 90, 70.00, '艺术楼音乐教室', 1, 39.916428, 116.405285, '音乐学院声乐专业，参加过多次比赛获奖', 234, 18, 4.85, 1, NOW(), NOW()),
(20, 10, '代做服务', '文档整理排版', 'Word、Excel文档整理排版，论文格式调整', '["https://example.com/document1.jpg"]', NULL, 60, 40.00, '线上服务', 2, NULL, NULL, '办公软件熟练，细心负责，快速交付', 423, 48, 4.83, 1, NOW(), NOW());

INSERT INTO `order_info` (`id`, `order_no`, `order_type`, `user_id`, `provider_id`, `item_id`, `item_title`, `start_time`, `end_time`, `planned_duration`, `actual_duration`, `rental_fee`, `deposit_amount`, `platform_fee`, `total_amount`, `paid_amount`, `deposit_status`, `payment_status`, `order_status`, `create_time`, `update_time`) VALUES
(1, '202401010001', 'SHARED', 3, NULL, 1, '共享单车BIKE001', DATE_SUB(NOW(), INTERVAL 5 DAY), DATE_SUB(NOW(), INTERVAL 5 DAY), 60, 65, 2.00, 99.00, 0.00, 2.00, 2.00, 2, 1, 4, DATE_SUB(NOW(), INTERVAL 5 DAY), DATE_SUB(NOW(), INTERVAL 5 DAY)),
(2, '202401010002', 'IDLE', 3, 4, 1, 'Canon EOS R6 相机', DATE_SUB(NOW(), INTERVAL 3 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY), 2880, 2880, 300.00, 1000.00, 30.00, 330.00, 330.00, 2, 1, 4, DATE_SUB(NOW(), INTERVAL 3 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY)),
(3, '202401010003', 'SKILL', 6, 4, 1, '高数一对一辅导', DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY), 120, 120, 80.00, 0.00, 8.00, 88.00, 88.00, 0, 1, 4, DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY)),
(4, '202401010004', 'SHARED', 3, NULL, 11, '共享充电宝CHARGER001', DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY), 120, 125, 2.00, 0.00, 0.00, 2.00, 2.00, 0, 1, 4, DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY)),
(5, '202401010005', 'SHARED', 3, NULL, 7, '共享单车BIKE007', NOW(), NULL, 60, NULL, 0.00, 99.00, 0.00, 0.00, 0.00, 1, 1, 1, NOW(), NOW());

INSERT INTO `review` (`id`, `order_id`, `reviewer_id`, `reviewed_id`, `review_type`, `rating`, `content`, `tags`, `is_anonymous`, `create_time`) VALUES
(1, 1, 3, 1, 'ITEM', 5, '单车很好骑，车况不错，推荐！', '车况好,骑行舒适', 0, DATE_SUB(NOW(), INTERVAL 5 DAY)),
(2, 2, 3, 4, 'USER', 5, '相机很棒，物主很好说话，沟通顺畅', '守时,爱护物品,沟通好', 0, DATE_SUB(NOW(), INTERVAL 1 DAY)),
(3, 3, 6, 4, 'SERVICE', 5, '老师讲解很详细，一次就听懂了，非常感谢！', '专业,耐心,效果好', 0, DATE_SUB(NOW(), INTERVAL 2 DAY)),
(4, 4, 3, 11, 'ITEM', 4, '充电宝电量足，就是归还点有点少', '电量足,方便', 0, DATE_SUB(NOW(), INTERVAL 1 DAY));

INSERT INTO `notification` (`id`, `user_id`, `type`, `title`, `content`, `related_id`, `related_type`, `is_read`, `create_time`) VALUES
(1, 3, 'ORDER', '订单完成', '您的共享单车订单已完成，感谢使用', 1, 'ORDER', 1, DATE_SUB(NOW(), INTERVAL 5 DAY)),
(2, 4, 'ORDER', '收到新订单', '您的Canon EOS R6 相机收到新的租借订单', 2, 'ORDER', 1, DATE_SUB(NOW(), INTERVAL 3 DAY)),
(3, 3, 'SYSTEM', '欢迎使用', '欢迎使用校园共享经济平台，祝您使用愉快！', NULL, NULL, 0, DATE_SUB(NOW(), INTERVAL 7 DAY)),
(4, 6, 'ORDER', '服务完成', '您预约的高数辅导服务已完成', 3, 'ORDER', 1, DATE_SUB(NOW(), INTERVAL 2 DAY)),
(5, 3, 'PAYMENT', '押金已退还', '您的押金99元已退还至账户', 1, 'DEPOSIT', 1, DATE_SUB(NOW(), INTERVAL 5 DAY));

INSERT INTO `favorite` (`id`, `user_id`, `target_id`, `target_type`, `create_time`) VALUES
(1, 3, 5, 'IDLE', DATE_SUB(NOW(), INTERVAL 2 DAY)),
(2, 3, 2, 'SKILL', DATE_SUB(NOW(), INTERVAL 3 DAY)),
(3, 6, 1, 'IDLE', DATE_SUB(NOW(), INTERVAL 1 DAY)),
(4, 6, 7, 'SKILL', DATE_SUB(NOW(), INTERVAL 4 DAY)),
(5, 3, 8, 'IDLE', NOW());

INSERT INTO `balance_record` (`id`, `user_id`, `record_type`, `amount`, `before_balance`, `after_balance`, `related_order_id`, `remark`, `create_time`) VALUES
(1, 3, 'CHARGE', 100.00, 0.00, 100.00, NULL, '充值余额', DATE_SUB(NOW(), INTERVAL 10 DAY)),
(2, 3, 'PAY', -2.00, 100.00, 98.00, 1, '支付订单', DATE_SUB(NOW(), INTERVAL 5 DAY)),
(3, 4, 'INCOME', 270.00, 0.00, 270.00, 2, '订单收益', DATE_SUB(NOW(), INTERVAL 1 DAY)),
(4, 6, 'CHARGE', 50.00, 0.00, 50.00, NULL, '充值余额', DATE_SUB(NOW(), INTERVAL 3 DAY)),
(5, 4, 'INCOME', 72.00, 270.00, 342.00, 3, '订单收益（已扣除平台费10%）', DATE_SUB(NOW(), INTERVAL 2 DAY));

INSERT INTO `deposit_record` (`id`, `user_id`, `order_id`, `record_type`, `amount`, `before_balance`, `after_balance`, `reason`, `create_time`) VALUES
(1, 3, NULL, 'CHARGE', 200.00, 0.00, 200.00, '充值押金', DATE_SUB(NOW(), INTERVAL 10 DAY)),
(2, 3, 1, 'FREEZE', -99.00, 200.00, 101.00, '冻结押金', DATE_SUB(NOW(), INTERVAL 5 DAY)),
(3, 3, 1, 'UNFREEZE', 99.00, 101.00, 200.00, '解冻押金', DATE_SUB(NOW(), INTERVAL 5 DAY)),
(4, 3, 5, 'FREEZE', -99.00, 200.00, 101.00, '冻结押金', NOW());

INSERT INTO `credit_log` (`id`, `user_id`, `change_type`, `change_score`, `before_score`, `after_score`, `reason`, `related_order_id`, `create_time`) VALUES
(1, 3, 'AUTH', 10, 100, 110, '完成实名认证', NULL, DATE_SUB(NOW(), INTERVAL 8 DAY)),
(2, 3, 'ON_TIME', 2, 110, 112, '按时归还', 1, DATE_SUB(NOW(), INTERVAL 5 DAY)),
(3, 4, 'AUTH', 10, 100, 110, '完成实名认证', NULL, DATE_SUB(NOW(), INTERVAL 7 DAY)),
(4, 4, 'GOOD_REVIEW', 1, 110, 111, '获得好评', 2, DATE_SUB(NOW(), INTERVAL 1 DAY)),
(5, 3, 'ON_TIME', 2, 112, 114, '按时归还', 2, DATE_SUB(NOW(), INTERVAL 1 DAY));

