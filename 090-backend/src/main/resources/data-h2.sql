INSERT INTO opera_category (id, name, code, dean_name, status) VALUES
(1, '京剧', 'JJ', '北京', 1),
(2, '越剧', 'YJ', '浙江', 1),
(3, '黄梅戏', 'HMX', '安徽', 1);

INSERT INTO artist_archive (id, department_id, name, code, grade_year, status) VALUES
(1, 1, '梅派传承档案', 'MP001', '国家级', 1),
(2, 2, '越韵新声档案', 'YY002', '省级', 1),
(3, 3, '黄梅新苗档案', 'HM003', '市级', 1);

INSERT INTO venue_info (id, major_id, name, counselor_name, member_count, status) VALUES
(1, 1, '梨园小剧场', '张馆长', 120, 1),
(2, 2, '水袖体验坊', '刘策展', 80, 1),
(3, 3, '非遗研学厅', '赵老师', 60, 1);

INSERT INTO cultural_season (id, term_name, start_date, end_date, current_flag, status) VALUES
(1, '春季梨园雅集', DATE '2026-03-01', DATE '2026-05-31', 1, 1),
(2, '暑期非遗研学季', DATE '2026-07-01', DATE '2026-08-31', 0, 1),
(3, '国庆经典展演周', DATE '2026-10-01', DATE '2026-10-07', 0, 1);

INSERT INTO sys_user (id, username, password, real_name, phone, role, department_id, major_id, class_id, status) VALUES
(1, 'admin', '123456', '系统管理员', '13800000001', 'admin', 1, NULL, NULL, 1),
(2, 'artist', '123456', '陈艺术家', '13800000002', 'artist', 1, NULL, NULL, 1),
(3, 'member', '123456', '王票友', '13800000003', 'member', 1, 1, 1, 1),
(4, 'member2', '123456', '李戏迷', '13800000004', 'member', 2, 2, 2, 1);

INSERT INTO repertoire_info (id, course_name, course_code, department_id, artist_id, credit, course_type, course_hours, course_desc, status) VALUES
(1, '霸王别姬', 'OP001', 1, 2, 4.0, '经典剧目', 120, '梅派代表作赏析与身段体验', 1),
(2, '梁山伯与祝英台', 'OP002', 2, 2, 3.0, '水袖体验', 100, '越剧唱腔与表演程式体验', 1),
(3, '天仙配', 'OP003', 3, 2, 3.0, '非遗研学', 90, '黄梅戏入门导赏与互动教学', 1);

INSERT INTO performance_schedule (id, course_id, term_id, artist_id, class_id, classroom, week_day, start_section, end_section, max_member_count, selected_count, status) VALUES
(1, 1, 1, 2, 1, '主舞台', '周五', 19, 21, 120, 1, 1),
(2, 2, 1, 2, 2, '水袖坊', '周六', 14, 16, 80, 1, 1),
(3, 3, 2, 2, 3, '研学厅', '周日', 10, 12, 60, 0, 1);

INSERT INTO booking_record (id, schedule_id, course_id, member_id, select_status) VALUES
(1, 1, 1, 3, 1),
(2, 2, 2, 4, 1);

INSERT INTO media_resource (id, schedule_id, course_id, artist_id, title, resource_type, resource_url, content_desc, status) VALUES
(1, 1, 1, 2, '霸王别姬唱段图文导览', 'pdf', '/resource/opera/bwbieji-guide.pdf', '经典唱段讲解与角色关系图谱', 1),
(2, 2, 2, 2, '越剧水袖动作示范', 'video', '/resource/opera/yueju-shuixiu.mp4', '水袖动作分解与练习要点', 1);

INSERT INTO checkin_record (id, schedule_id, course_id, member_id, artist_id, attendance_date, attendance_status, remark) VALUES
(1, 1, 1, 3, 2, DATE '2026-03-20', 'present', '主舞台活动签到成功'),
(2, 2, 2, 4, 2, DATE '2026-03-21', 'late', '活动开场后5分钟到场');

INSERT INTO review_record (id, selection_id, schedule_id, course_id, member_id, artist_id, usual_score, exam_score, total_score, grade_level, remark) VALUES
(1, 1, 1, 1, 3, 2, 88, 92, 90, 'A', '身段体验表现优秀'),
(2, 2, 2, 2, 4, 2, 80, 86, 83, 'B', '水袖动作稳定，继续保持');

INSERT INTO appreciation_comment (id, schedule_id, course_id, member_id, artist_id, evaluation_score, evaluation_content) VALUES
(1, 1, 1, 3, 2, 95, '名段导赏清晰，现场沉浸感很强'),
(2, 2, 2, 4, 2, 90, '互动环节设计合理，适合初学者体验');

INSERT INTO system_notice (id, title, content, type, status, publisher_id, publish_time, view_count) VALUES
(1, '春季梨园雅集预约开放通知', '本周五晚场展演已开放预约，请会员按时完成登记。', 'system', 1, 1, CURRENT_TIMESTAMP, 25),
(2, '数字资源更新提醒', '霸王别姬导赏资料与越剧水袖示范视频已同步上传。', 'repertoire', 1, 1, CURRENT_TIMESTAMP, 18);

INSERT INTO operation_log (id, user_id, module, action, content) VALUES
(1, 1, 'performance', 'add', '新增霸王别姬晚场展演排期'),
(2, 2, 'resource', 'add', '发布霸王别姬导赏资料');
