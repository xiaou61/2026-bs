INSERT INTO user (username, password, phone, nickname, role, status, created_at, updated_at, deleted)
VALUES
    ('admin', '$2b$12$jK8zNPbXrkWPvITUUIio4Od0t5y/ZQ2F2owIex/Sa2eScamJF1BBW', '13800000000', '管理员', 9, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
    ('farmer_demo', '$2b$12$jK8zNPbXrkWPvITUUIio4Od0t5y/ZQ2F2owIex/Sa2eScamJF1BBW', '13800000001', '演示农户', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
    ('driver_demo', '$2b$12$jK8zNPbXrkWPvITUUIio4Od0t5y/ZQ2F2owIex/Sa2eScamJF1BBW', '13800000002', '演示机手', 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);
