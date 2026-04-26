INSERT INTO users (id, username, password, nickname, avatar, email, phone, gender, introduction, role, status) VALUES
(1, 'admin', '$2b$10$cvmYLCOE548cvrJQqO3pD.w1f5koCX7twgi87xkDojNs2z9dd7YZq', '管理员', '', 'admin@folksong.local', '13800138000', 1, '平台管理员', 1, 1),
(2, 'user', '$2b$10$cvmYLCOE548cvrJQqO3pD.w1f5koCX7twgi87xkDojNs2z9dd7YZq', '民歌爱好者', '', 'user@folksong.local', '13800138001', 0, '喜欢收集各地民歌故事', 0, 1);

INSERT INTO categories (id, name, description, region, cover_image, sort_order, status) VALUES
(1, '山歌', '山区民间歌曲，以高亢嘹亮著称', '西南', '', 1, 1),
(2, '号子', '劳动人民在劳动过程中演唱的歌曲', '全国', '', 2, 1),
(3, '小调', '流行于城镇的民间歌曲', '华东', '', 3, 1),
(4, '田歌', '农民在田间劳作时演唱的歌曲', '华中', '', 4, 1),
(5, '牧歌', '牧民在放牧时演唱的歌曲', '内蒙古', '', 5, 1),
(6, '渔歌', '渔民在捕鱼时演唱的歌曲', '沿海', '', 6, 1),
(7, '茶歌', '采茶劳动中演唱的歌曲', '华南', '', 7, 1),
(8, '秧歌', '插秧时演唱的歌曲', '东北', '', 8, 1);

INSERT INTO folk_songs (id, title, category_id, user_id, content, lyrics, audio_url, video_url, cover_image, region, ethnic, introduction, view_count, like_count, collect_count, comment_count, status, audit_status) VALUES
(1, '阿里山的姑娘', 3, 2, '旋律明快，展示山林生活与民族风情。', '高山青，涧水蓝。', '', '', '', '台湾', '高山族', '经典民歌示例，便于首页、搜索与详情演示。', 128, 12, 5, 1, 1, 1),
(2, '康定情歌', 1, 2, '四川康定地区流传广泛的山歌。', '跑马溜溜的山上，一朵溜溜的云哟。', '', '', '', '四川', '汉族', '用于热门民歌和分类筛选演示。', 256, 26, 11, 1, 1, 1),
(3, '待审核民歌样例', 5, 2, '用户新发布的作品，需要管理员审核。', '草原上的风吹过毡房。', '', '', '', '内蒙古', '蒙古族', '后台待审核列表演示数据。', 0, 0, 0, 0, 1, 0);

INSERT INTO comments (id, song_id, user_id, parent_id, content, like_count, status) VALUES
(1, 1, 1, NULL, '这首歌很适合做民歌文化入门展示。', 0, 1),
(2, 2, 1, NULL, '旋律辨识度高，适合首页推荐。', 0, 1);

INSERT INTO favorites (id, user_id, song_id) VALUES
(1, 2, 1);

INSERT INTO likes (id, user_id, song_id) VALUES
(1, 2, 1);

INSERT INTO announcements (id, title, content, publisher_id, status) VALUES
(1, '民歌征集活动启动', '欢迎上传各地民歌、民谣资料，传承民族文化。', 1, 1),
(2, '平台演示账号说明', '默认管理员账号 admin/123456，普通用户账号 user/123456。', 1, 1);

ALTER TABLE users ALTER COLUMN id RESTART WITH 3;
ALTER TABLE categories ALTER COLUMN id RESTART WITH 9;
ALTER TABLE folk_songs ALTER COLUMN id RESTART WITH 4;
ALTER TABLE comments ALTER COLUMN id RESTART WITH 3;
ALTER TABLE favorites ALTER COLUMN id RESTART WITH 2;
ALTER TABLE likes ALTER COLUMN id RESTART WITH 2;
ALTER TABLE announcements ALTER COLUMN id RESTART WITH 3;
