INSERT INTO sys_user (id, username, password, real_name, phone, role, status) VALUES
(1, 'admin', '123456', '系统管理员', '13800000001', 'admin', 1),
(2, 'doctor', '123456', '王医生', '13800000002', 'doctor', 1),
(3, 'nurse', '123456', '张护士', '13800000003', 'nurse', 1),
(4, 'reception', '123456', '李前台', '13800000004', 'reception', 1);

INSERT INTO elder_profile (id, name, gender, age, id_card, phone, address, blood_type, chronic_disease, status) VALUES
(1, '陈建国', '男', 68, '110101195801011234', '13911110001', '朝阳区建国路88号', 'A', '高血压', 1),
(2, '刘桂芳', '女', 72, '110101195402023456', '13911110002', '海淀区知春路66号', 'B', '糖尿病', 1),
(3, '王国强', '男', 65, '110101196001033333', '13911110003', '丰台区东大街12号', 'O', '无', 1);

INSERT INTO elder_medical_history (id, elder_id, allergy_history, past_history, family_history, medication_history) VALUES
(1, 1, '青霉素过敏', '2018年冠脉支架', '父亲高血压', '缬沙坦每日一次'),
(2, 2, '无', '2型糖尿病10年', '母亲糖尿病', '二甲双胍每日两次');

INSERT INTO elder_contact (id, elder_id, contact_name, relation, contact_phone, address) VALUES
(1, 1, '陈晓明', '儿子', '13888880001', '朝阳区建国路88号'),
(2, 2, '周婷', '女儿', '13888880002', '海淀区知春路66号');

INSERT INTO check_item (id, item_code, item_name, unit, low_limit, high_limit, warning_level, status) VALUES
(1, 'GLU', '空腹血糖', 'mmol/L', 3.90, 6.10, 'high', 1),
(2, 'SBP', '收缩压', 'mmHg', 90.00, 140.00, 'high', 1),
(3, 'DBP', '舒张压', 'mmHg', 60.00, 90.00, 'medium', 1),
(4, 'BMI', '体质指数', 'kg/m2', 18.50, 24.00, 'low', 1);

INSERT INTO check_package (id, package_name, package_code, price, description, suitable_crowd, status) VALUES
(1, '基础老年体检套餐', 'PKG_BASIC', 199.00, '常规血压、血糖、身高体重检查', '60岁以上老人', 1),
(2, '慢病专项筛查套餐', 'PKG_CHRONIC', 399.00, '适用于高血压糖尿病慢病人群', '慢病老人', 1);

INSERT INTO check_package_item (id, package_id, item_id, sort) VALUES
(1, 1, 1, 1),
(2, 1, 2, 2),
(3, 1, 3, 3),
(4, 1, 4, 4),
(5, 2, 1, 1),
(6, 2, 2, 2),
(7, 2, 3, 3);

INSERT INTO check_appointment (id, appointment_no, elder_id, package_id, appointment_date, slot_time, source, status, remark, create_by, create_time) VALUES
(1, 'AP20260310001', 1, 1, DATE '2026-03-10', '上午', '线下窗口', 2, '需优先安排', 4, TIMESTAMP '2026-03-10 08:00:00'),
(2, 'AP20260310002', 2, 2, DATE '2026-03-10', '下午', '电话预约', 1, '家属陪同', 4, TIMESTAMP '2026-03-10 08:30:00'),
(3, 'AP20260311001', 3, 1, DATE '2026-03-11', '上午', '社区活动', 0, '', 4, TIMESTAMP '2026-03-11 08:00:00');

INSERT INTO check_result (id, appointment_id, elder_id, item_id, item_value, abnormal_flag, conclusion, doctor_id, check_time) VALUES
(1, 1, 1, 1, '7.2', 1, '血糖偏高，建议复查', 2, TIMESTAMP '2026-03-10 09:30:00'),
(2, 1, 1, 2, '145', 1, '血压偏高，建议监测', 2, TIMESTAMP '2026-03-10 09:35:00'),
(3, 1, 1, 3, '88', 0, '正常范围', 2, TIMESTAMP '2026-03-10 09:35:00'),
(4, 2, 2, 1, '6.0', 0, '正常范围', 2, TIMESTAMP '2026-03-10 15:10:00');

INSERT INTO abnormal_warning (id, result_id, elder_id, item_id, warning_level, warning_content, status) VALUES
(1, 1, 1, 1, 'high', '空腹血糖指标异常: 7.2', 1),
(2, 2, 1, 2, 'high', '收缩压指标异常: 145', 0);

INSERT INTO follow_up_record (id, warning_id, elder_id, follow_date, follow_method, follow_content, doctor_id, status, next_follow_date) VALUES
(1, 1, 1, DATE '2026-03-10', '电话', '建议减少高糖饮食，一周后复检血糖', 2, 1, DATE '2026-03-17');

INSERT INTO system_notice (id, title, content, type, status, publisher_id, publish_time, view_count) VALUES
(1, '三月老年健康义诊通知', '本周六上午开展老年健康义诊活动，欢迎预约参与。', 'activity', 1, 1, TIMESTAMP '2026-03-09 10:00:00', 25),
(2, '体检系统升级公告', '系统将于周日凌晨进行升级维护。', 'system', 1, 1, TIMESTAMP '2026-03-09 18:00:00', 18);
