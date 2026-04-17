INSERT INTO user (id, username, password, email, phone, avatar, nickname, bio, user_type, status, create_time, update_time) VALUES
    (1, 'admin029', '$2b$12$fwMwH92D7d49ioHxEOqrXe93ZB6WGXxMOo9u9vobDmIfbI7oKqjk.', 'admin029@herbal.local', '13800000029', 'https://images.unsplash.com/photo-1560250097-0b93528c311a?auto=format&fit=crop&w=400&q=80', '平台管理员', '负责审核内容、处理创作者认证和巡检平台运行状态。', 2, 1, TIMESTAMP '2026-04-01 09:00:00', TIMESTAMP '2026-04-12 09:00:00'),
    (2, 'creator029', '$2b$12$fwMwH92D7d49ioHxEOqrXe93ZB6WGXxMOo9u9vobDmIfbI7oKqjk.', 'creator029@herbal.local', '13800000030', 'https://images.unsplash.com/photo-1500648767791-00dcc994a43e?auto=format&fit=crop&w=400&q=80', '岐黄食养师', '擅长把常见药食同源食材做成适合四季调养的家常食谱。', 1, 1, TIMESTAMP '2026-04-02 10:00:00', TIMESTAMP '2026-04-12 10:00:00'),
    (3, 'user029', '$2b$12$fwMwH92D7d49ioHxEOqrXe93ZB6WGXxMOo9u9vobDmIfbI7oKqjk.', 'user029@herbal.local', '13800000031', 'https://images.unsplash.com/photo-1494790108377-be9c29b29330?auto=format&fit=crop&w=400&q=80', '轻养同学', '关注熬夜后调理和办公室人群日常食补。', 0, 1, TIMESTAMP '2026-04-03 11:00:00', TIMESTAMP '2026-04-12 11:00:00'),
    (4, 'applicant029', '$2b$12$fwMwH92D7d49ioHxEOqrXe93ZB6WGXxMOo9u9vobDmIfbI7oKqjk.', 'applicant029@herbal.local', '13800000032', 'https://images.unsplash.com/photo-1544005313-94ddf0286df2?auto=format&fit=crop&w=400&q=80', '待认证作者', '准备提交食疗专栏，等待管理员审核。', 0, 1, TIMESTAMP '2026-04-04 12:00:00', TIMESTAMP '2026-04-12 12:00:00');

INSERT INTO health_profile (id, user_id, constitution, allergies, health_goals, dietary_restrictions, create_time, update_time) VALUES
    (1, 3, '气虚质', '海鲜轻微过敏', '改善熬夜后的疲劳感，减少上火和口干。', '工作日少糖、晚餐尽量清淡。', TIMESTAMP '2026-04-05 09:00:00', TIMESTAMP '2026-04-12 09:00:00');

INSERT INTO ingredient (id, name, category, nature, taste, efficacy, nutrition, image, status, create_time) VALUES
    (1, '山药', '根茎类', '平', '甘', '健脾益胃、补肺益肾，适合日常食补。', '富含膳食纤维、维生素 C 和钾。', 'https://images.unsplash.com/photo-1518977676601-b53f82aba655?auto=format&fit=crop&w=400&q=80', 1, TIMESTAMP '2026-04-01 08:30:00'),
    (2, '枸杞', '果实类', '平', '甘', '滋补肝肾、明目安神。', '富含胡萝卜素和多种氨基酸。', 'https://images.unsplash.com/photo-1615485925873-6a7fbeef8e6f?auto=format&fit=crop&w=400&q=80', 1, TIMESTAMP '2026-04-01 08:35:00'),
    (3, '红枣', '果实类', '温', '甘', '补中益气、养血安神。', '含铁、维生素和天然糖分。', 'https://images.unsplash.com/photo-1603048297172-c92544798d5c?auto=format&fit=crop&w=400&q=80', 1, TIMESTAMP '2026-04-01 08:40:00'),
    (4, '绿豆', '豆类', '寒', '甘', '清热解毒、消暑利水。', '高蛋白、富含 B 族维生素。', 'https://images.unsplash.com/photo-1611747436512-f1031ff2d21f?auto=format&fit=crop&w=400&q=80', 1, TIMESTAMP '2026-04-01 08:45:00');

INSERT INTO ingredient_taboo (id, ingredient_id1, ingredient_id2, reason, create_time) VALUES
    (1, 2, 4, '枸杞偏滋补、绿豆偏寒凉，同时大量食用容易削弱调养方向。', TIMESTAMP '2026-04-06 09:30:00');

INSERT INTO creator_auth (id, user_id, auth_type, credentials, status, auth_time, create_time) VALUES
    (1, 2, '营养师认证', '国家公共营养师证书与中药食疗课程结业证明。', 1, TIMESTAMP '2026-04-07 10:00:00', TIMESTAMP '2026-04-06 18:00:00'),
    (2, 4, '内容创作者认证', '提交健康养生专栏作品集，等待平台复核。', 0, NULL, TIMESTAMP '2026-04-10 14:00:00');

INSERT INTO recipe (id, title, description, author_id, cover_image, efficacy, nutrition, difficulty, cook_time, servings, applicable_people, seasons, status, views, likes, collects, create_time, update_time) VALUES
    (1, '山药枸杞小米粥', '适合熬夜后早餐食用，口感绵软，适合作为日常轻养餐。', 2, 'https://images.unsplash.com/photo-1512058564366-18510be2db19?auto=format&fit=crop&w=900&q=80', '健脾益气、缓解疲劳，适合作息不规律人群。', '碳水与膳食纤维搭配，饱腹感较强。', 1, 30, 2, '学生、白领、脾胃虚弱人群', '春秋', 2, 128, 36, 22, TIMESTAMP '2026-04-07 08:00:00', TIMESTAMP '2026-04-12 08:30:00'),
    (2, '红枣山药蒸糕', '作为下午加餐的轻甜点，减少精制糖摄入。', 2, 'https://images.unsplash.com/photo-1512058564366-18510be2db19?auto=format&fit=crop&w=900&q=80', '补中益气，适合容易乏力的人群作为点心。', '含天然糖分和复合碳水，适合控制零食摄入。', 2, 45, 4, '女性、老人、体虚人群', '四季', 1, 52, 10, 6, TIMESTAMP '2026-04-09 09:15:00', TIMESTAMP '2026-04-12 09:15:00');

INSERT INTO recipe_ingredient (id, recipe_id, ingredient_id, quantity, unit, create_time) VALUES
    (1, 1, 1, 120.00, '克', TIMESTAMP '2026-04-07 08:05:00'),
    (2, 1, 2, 10.00, '克', TIMESTAMP '2026-04-07 08:05:00'),
    (3, 1, 3, 20.00, '克', TIMESTAMP '2026-04-07 08:05:00'),
    (4, 2, 1, 150.00, '克', TIMESTAMP '2026-04-09 09:20:00'),
    (5, 2, 3, 60.00, '克', TIMESTAMP '2026-04-09 09:20:00');

INSERT INTO topic (id, title, content, author_id, category, tags, views, replies, status, create_time, update_time) VALUES
    (1, '春季祛湿，大家早餐更推荐粥还是汤？', '最近南方回南天明显，想做一份既能祛湿又不太麻烦的早餐，欢迎分享自己的做法。', 3, '日常调养', '春季,祛湿,早餐', 86, 2, 1, TIMESTAMP '2026-04-08 07:40:00', TIMESTAMP '2026-04-12 07:40:00'),
    (2, '创作者分享：药食同源内容怎么做得更生活化', '我在做食疗内容时，会先从家常菜切入，再解释背后的体质适配逻辑，欢迎一起交流。', 2, '创作经验', '创作者,内容策划,食疗', 64, 1, 1, TIMESTAMP '2026-04-10 21:00:00', TIMESTAMP '2026-04-12 21:00:00');

INSERT INTO comment (id, target_type, target_id, user_id, content, rating, image_urls, likes, status, create_time, update_time) VALUES
    (1, 0, 1, 3, '按这个配方做了一次，早上喝完胃里很舒服，下午也没那么犯困。', 5, NULL, 8, 0, TIMESTAMP '2026-04-08 08:20:00', TIMESTAMP '2026-04-12 08:20:00'),
    (2, 1, 1, 2, '如果是湿气重又怕寒凉，可以把绿豆替换成少量茯苓粉一起煮。', NULL, NULL, 5, 0, TIMESTAMP '2026-04-08 09:10:00', TIMESTAMP '2026-04-12 09:10:00');

INSERT INTO collection (id, user_id, target_type, target_id, create_time) VALUES
    (1, 3, 0, 1, TIMESTAMP '2026-04-09 20:00:00'),
    (2, 3, 1, 1, TIMESTAMP '2026-04-09 20:05:00');
