INSERT INTO sys_user (id, username, password, nickname, phone, email, gender, age, role, status, balance, create_time, update_time) VALUES
(1, 'admin', '123456', '系统管理员', '13800000001', 'admin@hospital.com', '男', 35, 'ADMIN', 1, 0.00, NOW(), NOW()),
(2, 'doctor01', '123456', '王主任', '13800000002', 'doctor01@hospital.com', '男', 45, 'DOCTOR', 1, 0.00, NOW(), NOW()),
(3, 'doctor02', '123456', '李医生', '13800000003', 'doctor02@hospital.com', '女', 39, 'DOCTOR', 1, 0.00, NOW(), NOW()),
(4, 'patient01', '123456', '张小明', '13800000004', 'patient01@hospital.com', '男', 28, 'PATIENT', 1, 0.00, NOW(), NOW()),
(5, 'patient02', '123456', '刘佳', '13800000005', 'patient02@hospital.com', '女', 31, 'PATIENT', 1, 0.00, NOW(), NOW());

INSERT INTO patient_profile (user_id, real_name, id_card, gender, age, blood_type, allergy_history, emergency_contact, emergency_phone, create_time, update_time) VALUES
(4, '张小明', '370101199801010011', '男', 28, 'A', '无', '张父', '13811110001', NOW(), NOW()),
(5, '刘佳', '370101199501010022', '女', 31, 'B', '青霉素过敏', '刘母', '13811110002', NOW(), NOW());

INSERT INTO medical_card (user_id, card_no, patient_name, phone, id_card, is_default, status, create_time) VALUES
(4, 'CARD2026001', '张小明', '13800000004', '370101199801010011', 1, 1, NOW()),
(5, 'CARD2026002', '刘佳', '13800000005', '370101199501010022', 1, 1, NOW());

INSERT INTO department_info (id, name, code, description, location, phone, sort, status, create_time, update_time) VALUES
(1, '内科', 'NK001', '负责常见内科疾病初诊与复诊', '门诊楼二层', '0531-82000001', 100, 1, NOW(), NOW()),
(2, '儿科', 'EK001', '提供儿童常见病与发热门诊服务', '门诊楼三层', '0531-82000002', 90, 1, NOW(), NOW()),
(3, '口腔科', 'KQ001', '口腔检查、洁牙与牙体修复', '门诊楼一层', '0531-82000003', 80, 1, NOW(), NOW());

INSERT INTO doctor_info (id, user_id, department_id, doctor_name, title, gender, specialty, introduction, avatar, consultation_fee, status, visit_count, create_time, update_time) VALUES
(1, 2, 1, '王建国', '主任医师', '男', '高血压、糖尿病、慢病管理', '从事内科临床工作二十余年，擅长慢病诊疗与综合评估。', 'https://dummyimage.com/120x120/5b8ff9/ffffff&text=W', 30.00, 1, 128, NOW(), NOW()),
(2, 3, 2, '李雪', '副主任医师', '女', '儿童呼吸道感染、过敏性疾病', '专注儿科门诊与儿童成长健康管理。', 'https://dummyimage.com/120x120/61d9a4/ffffff&text=L', 25.00, 1, 96, NOW(), NOW());

INSERT INTO doctor_schedule (id, doctor_id, department_id, schedule_date, time_slot, total_source, remaining_source, status, fee, clinic_room, create_time, update_time) VALUES
(1, 1, 1, DATEADD('DAY', 1, CURRENT_DATE), '上午', 20, 18, 1, 30.00, '内科201', NOW(), NOW()),
(2, 1, 1, DATEADD('DAY', 2, CURRENT_DATE), '下午', 15, 15, 1, 30.00, '内科202', NOW(), NOW()),
(3, 2, 2, DATEADD('DAY', 1, CURRENT_DATE), '上午', 18, 17, 1, 25.00, '儿科301', NOW(), NOW()),
(4, 2, 2, DATEADD('DAY', 3, CURRENT_DATE), '下午', 16, 16, 1, 25.00, '儿科302', NOW(), NOW());

INSERT INTO appointment_record (id, appointment_no, user_id, card_id, department_id, doctor_id, schedule_id, order_id, patient_name, contact_phone, remark, status, appointment_date, time_slot, amount, pay_time, finish_time, create_time, update_time) VALUES
(1, 'AP20260001', 4, 1, 1, 1, 1, 1, '张小明', '13800000004', '复诊高血压', 1, DATEADD('DAY', 1, CURRENT_DATE), '上午', 30.00, NOW(), NULL, NOW(), NOW()),
(2, 'AP20260002', 5, 2, 2, 2, 3, 2, '刘佳', '13800000005', '儿童咳嗽咨询', 3, DATEADD('DAY', -1, CURRENT_DATE), '上午', 25.00, DATEADD('DAY', -2, CURRENT_TIMESTAMP), DATEADD('DAY', -1, CURRENT_TIMESTAMP), DATEADD('DAY', -2, CURRENT_TIMESTAMP), NOW());

INSERT INTO payment_order (id, order_no, appointment_id, user_id, amount, status, payment_method, pay_time, create_time, update_time) VALUES
(1, 'OD20260001', 1, 4, 30.00, 1, '在线支付', NOW(), NOW(), NOW()),
(2, 'OD20260002', 2, 5, 25.00, 1, '在线支付', DATEADD('DAY', -2, CURRENT_TIMESTAMP), DATEADD('DAY', -2, CURRENT_TIMESTAMP), NOW());

INSERT INTO doctor_review (appointment_id, doctor_id, user_id, rating, content, status, patient_name, create_time) VALUES
(2, 2, 5, 5, '医生问诊细致，给出的建议很实用。', 1, '刘佳', NOW());

INSERT INTO news_notice (title, content, status, sort, admin_id, create_time, update_time) VALUES
('门诊就诊须知', '请患者提前15分钟到院签到，初诊患者请携带有效身份证件。', 1, 100, 1, NOW(), NOW()),
('五一假期排班安排', '假期期间内科与儿科正常接诊，口腔科实行预约制。', 1, 90, 1, NOW(), NOW());

INSERT INTO banner_info (title, image_url, link_url, description, sort, status, create_time, update_time) VALUES
('智慧门诊挂号', 'https://dummyimage.com/900x260/5b8ff9/ffffff&text=Online+Hospital', '/doctor-market', '在线选科室、选医生、选时间，一站式完成预约。', 100, 1, NOW(), NOW()),
('儿科专家门诊', 'https://dummyimage.com/900x260/61d9a4/ffffff&text=Pediatrics', '/doctor-market', '儿童发热、咳嗽、过敏等常见问题可快速挂号。', 90, 1, NOW(), NOW());

INSERT INTO operation_log (user_id, username, role, module_name, action_type, description, create_time) VALUES
(1, 'admin', 'ADMIN', '系统', '初始化', '初始化线上医院挂号演示数据', NOW());
