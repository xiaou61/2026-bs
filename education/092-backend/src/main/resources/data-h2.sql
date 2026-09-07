INSERT INTO campus_info (id, name, code, dean_name, status) VALUES
(1, '蓝天总园', 'MAIN', '周园长', 1),
(2, '彩虹分园', 'RAINBOW', '林园长', 1);

INSERT INTO grade_info (id, department_id, name, code, grade_year, status) VALUES
(1, 1, '小班', 'SMALL', '3-4岁', 1),
(2, 1, '中班', 'MIDDLE', '4-5岁', 1),
(3, 2, '大班', 'LARGE', '5-6岁', 1);

INSERT INTO class_info (id, major_id, name, counselor_name, student_count, status) VALUES
(1, 1, '向日葵一班', '陈老师', 28, 1),
(2, 2, '蒲公英二班', '李老师', 30, 1),
(3, 3, '海豚三班', '王老师', 26, 1);

INSERT INTO school_term (id, term_name, start_date, end_date, current_flag, status) VALUES
(1, '2026年春季学期', DATE '2026-02-23', DATE '2026-07-10', 1, 1),
(2, '2026年秋季学期', DATE '2026-09-01', DATE '2027-01-15', 0, 1);

INSERT INTO sys_user (id, username, password, real_name, phone, role, department_id, major_id, class_id, status) VALUES
(1, 'admin', '123456', '周园长', '13800000001', 'admin', 1, NULL, NULL, 1),
(2, 'teacher', '123456', '陈老师', '13800000002', 'teacher', 1, NULL, 1, 1),
(3, 'parent', '123456', '朵朵妈妈', '13800000003', 'parent', 1, 1, 1, 1),
(4, 'parent2', '123456', '天天爸爸', '13800000004', 'parent', 1, 2, 2, 1),
(5, 'teacher2', '123456', '王老师', '13800000005', 'teacher', 2, NULL, 3, 1);

INSERT INTO activity_info (id, course_name, course_code, department_id, teacher_id, credit, course_type, course_hours, course_desc, status) VALUES
(1, '音乐律动', 'ACT001', 1, 2, 1.0, '艺术启蒙', 24, '通过律动游戏培养节奏感与表现力', 1),
(2, '创意美术', 'ACT002', 1, 2, 1.0, '艺术启蒙', 20, '围绕涂鸦、手工和色彩启蒙开展活动', 1),
(3, '户外体能', 'ACT003', 2, 5, 1.0, '体能游戏', 28, '围绕跳跃、平衡和团队合作开展训练', 1);

INSERT INTO activity_schedule (id, course_id, term_id, teacher_id, class_id, classroom, week_day, start_section, end_section, max_student_count, selected_count, status) VALUES
(1, 1, 1, 2, 1, '多功能活动室', '周一', 1, 2, 35, 28, 1),
(2, 2, 1, 2, 2, '美术教室', '周三', 3, 4, 35, 30, 1),
(3, 3, 1, 5, 3, '户外操场', '周五', 1, 2, 35, 26, 1);

INSERT INTO child_profile (id, child_name, gender, birthday, campus_id, grade_id, class_id, parent_id, teacher_id, allergy_info, pickup_person, profile_status) VALUES
(1, '朵朵', '女', DATE '2021-05-18', 1, 1, 1, 3, 2, '鸡蛋轻微过敏', '朵朵妈妈', 1),
(2, '天天', '男', DATE '2020-11-03', 1, 2, 2, 4, 2, '无', '天天爸爸', 1);

INSERT INTO weekly_recipe (id, schedule_id, course_id, teacher_id, title, resource_type, resource_url, content_desc, status) VALUES
(1, 1, 1, 2, '第3周早餐食谱', '早餐', '/recipe/week3/breakfast', '牛奶、南瓜饼、水果拼盘', 1),
(2, 2, 2, 2, '第3周午餐食谱', '午餐', '/recipe/week3/lunch', '香菇鸡肉饭、时蔬、冬瓜汤', 1),
(3, 3, 3, 5, '第3周加餐安排', '加餐', '/recipe/week3/snack', '酸奶、香蕉、坚果碎', 1);

INSERT INTO attendance_record (id, schedule_id, course_id, student_id, teacher_id, attendance_date, attendance_status, remark) VALUES
(1, 1, 1, 3, 2, DATE '2026-03-16', 'present', '按时到园'),
(2, 2, 2, 4, 2, DATE '2026-03-16', 'late', '家长路上堵车'),
(3, 3, 3, 4, 5, DATE '2026-03-17', 'leave', '请假在家休息');

INSERT INTO health_record (id, child_id, teacher_id, record_date, temperature, health_status, emotion_status, attendance_advice, remark) VALUES
(1, 1, 2, DATE '2026-03-16', 36.5, '正常', '活跃', '可正常入园', '精神状态良好'),
(2, 2, 2, DATE '2026-03-16', 37.2, '轻微咳嗽', '平稳', '建议午休重点观察', '已通知家长关注');

INSERT INTO parent_feedback (id, child_id, parent_id, teacher_id, feedback_type, feedback_score, feedback_content, reply_content) VALUES
(1, 1, 3, 2, '成长反馈', 95, '孩子回家后主动分享了音乐活动，很喜欢老师的节奏游戏。', '已收到反馈，后续会继续加强互动表达。'),
(2, 2, 4, 2, '膳食建议', 90, '希望午餐增加更多软糯类主食，孩子更容易接受。', '感谢建议，本周会和保健老师一起调整菜单。');

INSERT INTO system_notice (id, title, content, type, status, publisher_id, publish_time, view_count) VALUES
(1, '春季亲子运动会通知', '本周六上午举行蓝天幼儿园春季亲子运动会，请家长提前到园签到。', 'activity', 1, 1, CURRENT_TIMESTAMP, 25),
(2, '第3周食谱发布', '本周早餐、午餐和加餐食谱已更新，请家长及时查看。', 'parent', 1, 1, CURRENT_TIMESTAMP, 18);

INSERT INTO pickup_record (id, child_id, parent_id, pickup_person, pickup_time, pickup_status, remark) VALUES
(1, 1, 3, '朵朵妈妈', TIMESTAMP '2026-03-16 17:20:00', 'completed', '按时接走'),
(2, 2, 4, '天天爸爸', TIMESTAMP '2026-03-16 17:32:00', 'completed', '园外等候接送');
