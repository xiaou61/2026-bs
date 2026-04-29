INSERT INTO sys_user (username, password, nickname, phone, email, role, status) VALUES
('admin', '123456', '平台管理员', '13800030001', 'admin@classic.com', 'ADMIN', 1),
('user', '123456', '调理用户', '13800030002', 'user@classic.com', 'USER', 1);

INSERT INTO category (name, sort, status) VALUES
('补气类', 1, 1),
('健脾类', 2, 1),
('安神类', 3, 1),
('祛湿类', 4, 1);

INSERT INTO ingredient (name, category_id, nature_taste, meridian, efficacy, suitable_people, taboo_people, status) VALUES
('黄芪', 1, '甘 微温', '脾 肺', '补气升阳 固表止汗', '气虚乏力人群', '实热体质慎用', 1),
('山药', 2, '甘 平', '脾 肺 肾', '健脾益胃 补肺益肾', '脾胃虚弱人群', '便秘实热慎用', 1),
('莲子', 3, '甘 涩 平', '脾 肾 心', '补脾止泻 养心安神', '失眠心悸人群', '大便干结慎用', 1),
('薏苡仁', 4, '甘 淡 凉', '脾 胃 肺', '健脾渗湿 清热排脓', '湿重困倦人群', '孕妇慎用', 1);

INSERT INTO formula_info (name, source, indication, composition, usage_method, status) VALUES
('四君子汤', '太平惠民和剂局方', '脾胃气虚 食少便溏 倦怠乏力', '人参 白术 茯苓 炙甘草', '水煎服 每日一剂 分两次温服', 1),
('归脾汤', '济生方', '心脾两虚 失眠健忘 面色萎黄', '黄芪 人参 白术 茯神 龙眼肉 酸枣仁 远志 木香 甘草', '水煎服 每日一剂', 1),
('参苓白术散', '太平惠民和剂局方', '脾虚湿盛 食少便溏 形体乏力', '人参 白术 茯苓 山药 莲子 薏苡仁 白扁豆 砂仁 甘草', '研末冲服或水煎服', 1);

INSERT INTO meal_plan (name, formula_id, ingredient_summary, suitable_constitution, meal_time, steps, status) VALUES
('健脾补气山药粥', 1, '山药 黄芪 粳米 红枣', '气虚质', '早餐', '山药去皮切块 黄芪煎汁后同粳米共煮成粥', 1),
('安神莲子百合羹', 2, '莲子 百合 小米 枸杞', '心脾两虚', '晚餐', '莲子与百合浸泡后慢炖 加入小米煮至软糯', 1),
('祛湿薏仁茯苓汤', 3, '薏苡仁 茯苓 山药', '痰湿质', '午餐', '薏苡仁提前浸泡 与茯苓山药同煮40分钟', 1);

INSERT INTO service_order (order_no, user_id, plan_id, contact_name, contact_phone, appointment_date, status, remark, admin_reply) VALUES
('SO202602140001', 2, 1, '张三', '13900040001', '2026-02-16', 1, '近期乏力明显，希望安排调理建议', '已安排线上评估，请按时参加');

INSERT INTO constitution_record (user_id, constitution_type, symptom_desc, suggestion, status) VALUES
(2, '气虚质', '容易疲劳，活动后气短，自汗', '建议规律作息，优先选择健脾补气类药膳方案', 1),
(2, '痰湿质', '晨起困重，舌苔偏腻', NULL, 0);

INSERT INTO favorite (user_id, target_type, target_id) VALUES
(2, 'FORMULA', 1),
(2, 'PLAN', 2);

INSERT INTO announcement (title, content, admin_id, status) VALUES
('平台上线通知', '经方药食两用服务平台已上线，欢迎体验体质记录与药膳服务预约。', 1, 1),
('春季调理提醒', '春季以疏肝健脾为主，建议优先选择清淡少油饮食。', 1, 1);
