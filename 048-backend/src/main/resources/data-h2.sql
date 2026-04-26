INSERT INTO sys_user (id, username, password, real_name, phone, email, role, status) VALUES
(1, 'admin', '123456', '系统管理员', '13800000000', 'admin@museum.local', 3, 1),
(2, 'guide1', '123456', '张讲解', '13800000001', 'guide1@museum.local', 1, 1),
(3, 'guide2', '123456', '李讲解', '13800000002', 'guide2@museum.local', 1, 1),
(4, 'researcher1', '123456', '王研究员', '13800000003', 'researcher1@museum.local', 2, 1),
(5, 'researcher2', '123456', '赵研究员', '13800000004', 'researcher2@museum.local', 2, 1),
(6, 'user1', '123456', '游客小明', '13800000005', 'user1@museum.local', 0, 1),
(7, 'user2', '123456', '游客小红', '13800000006', 'user2@museum.local', 0, 1);

INSERT INTO relic_category (id, name, description, sort, status) VALUES
(1, '青铜器', '商周至秦汉时期的青铜器皿', 1, 1),
(2, '陶瓷器', '各朝代陶瓷制品', 2, 1),
(3, '玉器', '玉石雕刻制品', 3, 1),
(4, '书画', '名人书法绘画作品', 4, 1),
(5, '金银器', '金银制品及首饰', 5, 1),
(6, '石刻', '石雕石刻作品', 6, 1),
(7, '织绣', '丝绸织物刺绣品', 7, 1),
(8, '杂项', '其他类别文物', 8, 1);

INSERT INTO dynasty (id, name, start_year, end_year, description, sort) VALUES
(1, '夏朝', '约前2070年', '约前1600年', '中国历史上第一个世袭王朝', 1),
(2, '商朝', '约前1600年', '约前1046年', '青铜器文明的鼎盛时期', 2),
(3, '周朝', '约前1046年', '前256年', '礼乐制度完善', 3),
(4, '秦朝', '前221年', '前207年', '中国第一个统一中央集权王朝', 4),
(5, '汉朝', '前202年', '220年', '丝绸之路开辟', 5),
(6, '唐朝', '618年', '907年', '中国历史上最强盛的朝代之一', 6),
(7, '宋朝', '960年', '1279年', '文化艺术高度发展的时期', 7),
(8, '元朝', '1271年', '1368年', '蒙古族建立的大一统王朝', 8),
(9, '明朝', '1368年', '1644年', '汉族建立的最后一个大一统王朝', 9),
(10, '清朝', '1636年', '1912年', '中国最后一个封建王朝', 10);

INSERT INTO exhibition_hall (id, name, floor, area, capacity, description, status) VALUES
(1, '青铜器展厅', 1, 500.00, 200, '展示商周时期精美青铜器', 1),
(2, '陶瓷展厅', 1, 600.00, 250, '展示历代陶瓷精品', 1),
(3, '玉器展厅', 2, 400.00, 150, '展示各朝代玉器珍品', 1),
(4, '书画展厅', 2, 800.00, 300, '展示历代名家书画', 1),
(5, '临时展厅A', 3, 1000.00, 400, '用于临时展览活动', 1),
(6, '临时展厅B', 3, 800.00, 350, '用于临时展览活动', 1);

INSERT INTO relic (id, name, category_id, dynasty_id, hall_id, relic_no, material, size, weight, origin, discovery_date, level, image, model_url, audio_url, description, historical_value, view_count, like_count, status) VALUES
(1, '四羊方尊', 1, 2, 1, 'QT-001', '青铜', '高58.3厘米', '34.5千克', '湖南宁乡', '1938-01-01', 1, '/images/relic/siyang.jpg', '/models/siyang.glb', '/audio/siyang.mp3', '商代晚期青铜礼器，是现存商代青铜方尊中最大的一件。', '四羊方尊是商代晚期青铜器的杰出代表。', 1580, 326, 1),
(2, '后母戊鼎', 1, 2, 1, 'QT-002', '青铜', '高133厘米，口长112厘米', '832.84千克', '河南安阳', '1939-03-19', 1, '/images/relic/houmuwu.jpg', '/models/houmuwu.glb', '/audio/houmuwu.mp3', '世界上现存最大、最重的青铜礼器。', '后母戊鼎代表了中国青铜文明的最高成就。', 2340, 512, 1),
(3, '越王勾践剑', 1, 3, 1, 'QT-003', '青铜', '长55.7厘米', '约0.9千克', '湖北江陵', '1965-12-01', 1, '/images/relic/goujian.jpg', '/models/goujian.glb', '/audio/goujian.mp3', '春秋时期越国国君勾践所用青铜剑。', '代表了春秋时期冶炼和铸造技术的高水平。', 1890, 423, 1),
(4, '汝窑天青釉洗', 2, 7, 2, 'TC-001', '瓷', '高3.5厘米，直径13厘米', '约0.3千克', '河南宝丰', '1950-01-01', 1, '/images/relic/ruyao.jpg', NULL, '/audio/ruyao.mp3', '北宋汝窑瓷器，釉色天青，温润如玉。', '汝窑瓷器传世稀少，素有汝窑为魁之称。', 1200, 289, 1),
(5, '青花瓷大盘', 2, 9, 2, 'TC-002', '瓷', '直径45厘米', '约2千克', '江西景德镇', '1978-05-01', 2, '/images/relic/qinghua.jpg', NULL, '/audio/qinghua.mp3', '明代永乐年间景德镇官窑烧制的青花瓷大盘。', '明代青花瓷代表了中国陶瓷工艺的高峰。', 890, 178, 1),
(6, '和田白玉观音', 3, 10, 3, 'YQ-001', '和田玉', '高25厘米', '约1.2千克', '新疆和田', '1985-09-01', 2, '/images/relic/guanyin.jpg', NULL, '/audio/guanyin.mp3', '清代乾隆年间雕刻的和田白玉观音像。', '体现了乾隆时期宫廷玉器的高水准。', 760, 156, 1),
(7, '翠玉白菜', 3, 10, 3, 'YQ-002', '翡翠', '高18.7厘米', '约0.5千克', '缅甸', '1900-01-01', 1, '/images/relic/baicai.jpg', NULL, '/audio/baicai.mp3', '清代翡翠雕刻作品，巧妙雕成白菜造型。', '展现了中国玉雕巧色技艺。', 1450, 398, 1),
(8, '清明上河图', 4, 7, 4, 'SH-001', '绢本设色', '纵24.8厘米，横528.7厘米', '—', '北京故宫', '1100-01-01', 1, '/images/relic/qingming.jpg', NULL, '/audio/qingming.mp3', '北宋画家张择端所绘，描绘汴京繁华景象。', '中国十大传世名画之一。', 3200, 888, 1);

INSERT INTO relic_image (relic_id, image_url, description, sort) VALUES
(1, '/images/relic/siyang-1.jpg', '四羊方尊正面', 1),
(1, '/images/relic/siyang-2.jpg', '四羊方尊局部纹饰', 2),
(2, '/images/relic/houmuwu-1.jpg', '后母戊鼎整体', 1),
(8, '/images/relic/qingming-1.jpg', '清明上河图局部', 1);

INSERT INTO exhibition (id, title, hall_id, start_date, end_date, description, ticket_price, view_count, status) VALUES
(1, '青铜器文明特展', 5, '2026-01-01', '2026-03-31', '展示商周时期青铜器精品，探索中国青铜文明的发展历程', 50.00, 860, 1),
(2, '宋代美学特展', 6, '2026-02-01', '2026-04-30', '展示宋代瓷器、书画等艺术品，感受宋代美学的独特魅力', 60.00, 720, 1),
(3, '丝绸之路文物展', 5, '2026-05-01', '2026-07-31', '展示丝绸之路沿线出土文物，再现古代东西方文化交流盛况', 80.00, 310, 0);

INSERT INTO research (id, title, relic_id, author_id, content, summary, publish_date, file_url, view_count, status) VALUES
(1, '四羊方尊铸造工艺研究', 1, 4, '本文通过对四羊方尊的铸造工艺进行深入分析。', '研究发现四羊方尊采用分铸法和浑铸法相结合的复杂工艺', '2025-06-15', '/files/research/siyang.pdf', 210, 1),
(2, '汝窑釉色成因分析', 4, 5, '通过现代科技手段分析汝窑天青釉的化学成分和烧制条件。', '揭示汝窑天青釉色形成的科学原理', '2025-08-20', '/files/research/ruyao.pdf', 168, 1),
(3, '越王勾践剑防锈之谜', 3, 4, '探讨越王勾践剑历经两千多年不锈的原因。', '分析表明剑身含有特殊合金成分', NULL, '/files/research/goujian.pdf', 45, 0);

INSERT INTO notice (id, title, content, type, publish_time, status) VALUES
(1, '春节期间开馆公告', '尊敬的观众朋友们，春节期间本馆正常开放，开馆时间为9:00-17:00，欢迎参观！', 1, '2026-01-15 10:00:00', 1),
(2, '青铜器文明特展开幕', '青铜器文明特展将于2026年1月1日在临时展厅A开幕，欢迎预约参观！', 2, '2025-12-25 09:00:00', 1),
(3, '2月5日临时闭馆通知', '因设备检修，2月5日本馆临时闭馆一天，给您带来不便敬请谅解。', 3, '2026-01-28 14:00:00', 1);

INSERT INTO reservation (id, order_no, user_id, exhibition_id, visit_date, time_slot, visitor_count, contact_name, contact_phone, total_price, status) VALUES
(1, 'R202601200001', 6, 1, '2026-01-25', '09:00-12:00', 2, '小明', '13800000005', 100.00, 1),
(2, 'R202601200002', 7, 2, '2026-01-26', '14:00-17:00', 3, '小红', '13800000006', 180.00, 0);

INSERT INTO guide_booking (id, order_no, user_id, guide_id, visit_date, start_time, duration, visitor_count, language, price, status) VALUES
(1, 'G202601200001', 6, 2, '2026-01-25', '10:00:00', 90, 2, '中文', 200.00, 1),
(2, 'G202601200002', 7, 3, '2026-01-26', '14:30:00', 60, 3, '中文', 150.00, 0);
