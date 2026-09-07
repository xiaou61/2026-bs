INSERT INTO `user` (id, username, password, nickname, role, status, created_at, updated_at) VALUES
(1, 'admin', '$2a$10$4cD1zoM.j9zoRsWqmzh.pu.LGsJ..tA8fQlzmtUwScKz3h/pPmth2', '系统管理员', 'ADMIN', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'counselor1', '$2a$10$4cD1zoM.j9zoRsWqmzh.pu.LGsJ..tA8fQlzmtUwScKz3h/pPmth2', '李心理', 'COUNSELOR', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'user1', '$2a$10$4cD1zoM.j9zoRsWqmzh.pu.LGsJ..tA8fQlzmtUwScKz3h/pPmth2', '测试用户', 'USER', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO `counselor` (id, user_id, real_name, certificate, certificate_no, specialties, introduction, style, price, rating, consult_count, status, created_at, updated_at) VALUES
(1, 2, '李心理', '国家二级心理咨询师', 'PSY2023001', '焦虑,抑郁,压力管理', '从业8年，擅长认知行为疗法', '温和包容,专业严谨', 300.00, 4.95, 12, 'APPROVED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO `scale` (id, name, category, description, question_count, duration, status, created_at, updated_at) VALUES
(1, '焦虑自评量表(SAS)', '焦虑量表', '用于评估焦虑症状的严重程度', 20, 10, 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, '抑郁自评量表(SDS)', '抑郁量表', '用于评估抑郁症状的严重程度', 20, 10, 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, '压力感知量表(PSS)', '压力量表', '评估个体感知压力的程度', 14, 8, 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO `question` (id, scale_id, content, type, options, score_rule, order_num, created_at, updated_at) VALUES
(1, 1, '我觉得比平常容易紧张和着急', 'SINGLE', '1=没有或很少时间;2=小部分时间;3=相当多时间;4=绝大部分或全部时间', '{"1":1,"2":2,"3":3,"4":4}', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, '我无缘无故地感到害怕', 'SINGLE', '1=没有或很少时间;2=小部分时间;3=相当多时间;4=绝大部分或全部时间', '{"1":1,"2":2,"3":3,"4":4}', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, '我感到情绪沮丧，郁闷', 'SINGLE', '1=没有或很少时间;2=小部分时间;3=相当多时间;4=绝大部分或全部时间', '{"1":1,"2":2,"3":3,"4":4}', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 2, '我感到早晨心情最好', 'SINGLE', '1=没有或很少时间;2=小部分时间;3=相当多时间;4=绝大部分或全部时间', '{"1":4,"2":3,"3":2,"4":1}', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO `article` (id, title, category, content, tags, author_id, view_count, like_count, collect_count, status, is_top, is_recommended, created_at, updated_at) VALUES
(1, '如何缓解焦虑情绪', '情绪管理', '焦虑是现代人常见的情绪问题。本文将介绍几种有效的缓解方法。', '焦虑,情绪管理', 1, 100, 12, 5, 'PUBLISHED', FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, '正念冥想的力量', '自我成长', '正念冥想是一种有效的心理调节方法。', '正念,冥想,成长', 1, 80, 8, 3, 'PUBLISHED', FALSE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO `time_slot` (id, counselor_id, slot_date, slot_time, duration, status, created_at, updated_at) VALUES
(1, 1, '2026-05-01', '09:00:00', 60, 'AVAILABLE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, '2026-05-01', '10:00:00', 60, 'AVAILABLE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1, '2026-05-01', '14:00:00', 60, 'AVAILABLE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

ALTER TABLE `user` ALTER COLUMN id RESTART WITH 4;
ALTER TABLE `counselor` ALTER COLUMN id RESTART WITH 2;
ALTER TABLE `scale` ALTER COLUMN id RESTART WITH 4;
ALTER TABLE `question` ALTER COLUMN id RESTART WITH 5;
ALTER TABLE `article` ALTER COLUMN id RESTART WITH 3;
ALTER TABLE `time_slot` ALTER COLUMN id RESTART WITH 4;
