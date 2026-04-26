INSERT INTO users (id, username, password, phone, real_name, room_number, role, status, points, created_at, updated_at) VALUES
(1, 'admin', '$2a$10$4cD1zoM.j9zoRsWqmzh.pu.LGsJ..tA8fQlzmtUwScKz3h/pPmth2', '13800138000', '系统管理员', '物业中心', 'ADMIN', 'ACTIVE', 1000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'user1', '$2a$10$4cD1zoM.j9zoRsWqmzh.pu.LGsJ..tA8fQlzmtUwScKz3h/pPmth2', '13800138001', '张三', '1-101', 'USER', 'ACTIVE', 100, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'manager1', '$2a$10$4cD1zoM.j9zoRsWqmzh.pu.LGsJ..tA8fQlzmtUwScKz3h/pPmth2', '13800138002', '李四', '物业办公室', 'MANAGER', 'ACTIVE', 200, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO vehicles (id, user_id, plate_number, vehicle_type, vehicle_color, vehicle_photo, status, created_at, updated_at) VALUES
(1, 2, '京A12345', '小型轿车', '白色', '', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 3, '京B23456', 'SUV', '黑色', '', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO violation_types (id, name, description, penalty_points, created_at) VALUES
(1, '占用消防通道', '车辆停放在消防通道上，影响消防安全', 30, CURRENT_TIMESTAMP),
(2, '占用他人车位', '车辆停放在他人专属车位上', 20, CURRENT_TIMESTAMP),
(3, '占用盲道', '车辆停放在盲道上，影响行人通行', 25, CURRENT_TIMESTAMP),
(4, '堵塞出入口', '车辆停放在小区或楼栋出入口，堵塞通行', 30, CURRENT_TIMESTAMP),
(5, '占用绿化带', '车辆停放在绿化带上，破坏环境', 15, CURRENT_TIMESTAMP),
(6, '其他违停', '其他类型的违规停车行为', 10, CURRENT_TIMESTAMP);

INSERT INTO reports (id, report_no, reporter_id, vehicle_id, plate_number, violation_type_id, location, longitude, latitude, description, images, is_anonymous, status, audit_user_id, audit_time, audit_reason, created_at, updated_at) VALUES
(1, 'WP20260426001', 2, 1, '京A12345', 1, 'A栋消防通道', 116.397128, 39.916527, '车辆长时间占用消防通道', '', FALSE, 'PENDING', NULL, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'WP20260426002', 2, NULL, '京C34567', 2, 'B栋地下车库B201旁', 116.397228, 39.916627, '疑似占用他人车位', '', FALSE, 'APPROVED', 1, CURRENT_TIMESTAMP, '证据清晰，审核通过', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO parking_spaces (id, space_no, space_type, building, floor, owner_id, status, created_at, updated_at) VALUES
(1, 'A101', '固定车位', 'A栋', '地下1层', NULL, 'AVAILABLE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'A102', '固定车位', 'A栋', '地下1层', 2, 'OCCUPIED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'B201', '临时车位', 'B栋', '地下2层', NULL, 'AVAILABLE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO system_config (id, config_key, config_value, description, created_at, updated_at) VALUES
(1, 'report_reward_points', '10', '举报奖励积分', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'violation_penalty_points', '20', '违停扣除积分', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'appeal_success_restore_points', '20', '申诉成功恢复积分', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

ALTER TABLE users ALTER COLUMN id RESTART WITH 4;
ALTER TABLE vehicles ALTER COLUMN id RESTART WITH 3;
ALTER TABLE violation_types ALTER COLUMN id RESTART WITH 7;
ALTER TABLE reports ALTER COLUMN id RESTART WITH 3;
ALTER TABLE parking_spaces ALTER COLUMN id RESTART WITH 4;
ALTER TABLE system_config ALTER COLUMN id RESTART WITH 4;
