INSERT INTO sys_user (id, username, nickname, password, phone, role, status) VALUES
(1, 'admin', '系统管理员', '$2a$10$2mwuqL8q/iAVXzjTNhraS.TamwA18iSAuRgzAYcZ4Gcdy.ag1Vcp2', '13800000000', 'ADMIN', 1),
(2, 'petuser', '寄养用户', '$2a$10$fCxQT3EZhPQ5pk/VCcQALOyAXBj9r7bJaZfQ4T7Asp8Qp3HVAX8lm', '13800000001', 'USER', 1),
(3, 'provider', '安心寄养师', '$2a$10$fCxQT3EZhPQ5pk/VCcQALOyAXBj9r7bJaZfQ4T7Asp8Qp3HVAX8lm', '13800000002', 'PROVIDER', 1);

INSERT INTO pet (id, user_id, name, type, breed, age, gender, weight, health_status, vaccination, remark) VALUES
(1, 2, '豆包', 'DOG', '柯基', 3, '公', 11.50, '健康，性格亲人', '已接种狂犬和六联', '不吃鸡肉');

INSERT INTO provider (id, user_id, name, address, phone, business_hours, description, images, longitude, latitude, status, rating, order_count) VALUES
(1, 3, '暖爪宠物寄养中心', '杭州市西湖区文三路 88 号', '13800000002', '09:00-21:00', '家庭式宠物寄养，支持猫狗分区、每日视频反馈。', '[]', 120.1234567, 30.1234567, 'APPROVED', 4.90, 28);

INSERT INTO provider_service (id, provider_id, name, price, pet_types, description) VALUES
(1, 1, '犬类标准寄养', 88.00, 'DOG', '每日两次遛狗，提供基础喂养和照片反馈'),
(2, 1, '猫咪安静寄养', 68.00, 'CAT', '独立猫房，定时铲砂，减少应激');

ALTER TABLE sys_user ALTER COLUMN id RESTART WITH 4;
ALTER TABLE pet ALTER COLUMN id RESTART WITH 2;
ALTER TABLE provider ALTER COLUMN id RESTART WITH 2;
ALTER TABLE provider_service ALTER COLUMN id RESTART WITH 3;
