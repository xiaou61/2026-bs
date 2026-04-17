MERGE INTO user KEY(id) VALUES
    (1, 'admin', '123456', 'ADMIN', CURRENT_TIMESTAMP),
    (2, 'owner01', '123456', 'OWNER', CURRENT_TIMESTAMP),
    (3, 'owner02', '123456', 'OWNER', CURRENT_TIMESTAMP);

MERGE INTO owner KEY(id) VALUES
    (1, 2, '张三', '13800000001', '1栋', '1单元', '101', DATE '2024-09-01'),
    (2, 3, '李四', '13800000002', '2栋', '2单元', '202', DATE '2024-10-15');

MERGE INTO fee KEY(id) VALUES
    (1, 1, 288.00, 'PROPERTY', 'UNPAID', TIMESTAMP '2026-04-01 09:00:00', NULL),
    (2, 2, 120.00, 'PARKING', 'PAID', TIMESTAMP '2026-03-20 10:00:00', TIMESTAMP '2026-03-21 11:30:00');

MERGE INTO repair KEY(id) VALUES
    (1, 1, '客厅灯具闪烁，需要上门检查。', NULL, 'PENDING', TIMESTAMP '2026-04-09 14:00:00', NULL),
    (2, 2, '厨房水龙头漏水。', NULL, 'PROCESSING', TIMESTAMP '2026-04-08 09:30:00', NULL);

MERGE INTO notice KEY(id) VALUES
    (1, '停水通知', '4月12日 09:00-12:00 小区北区计划停水，请提前做好储水准备。', TIMESTAMP '2026-04-10 08:00:00'),
    (2, '春季环境整治', '本周末将开展公共区域卫生清洁与绿化修整，请居民配合。', TIMESTAMP '2026-04-09 18:30:00');

MERGE INTO visitor KEY(id) VALUES
    (1, 1, '王访客', TIMESTAMP '2026-04-10 19:00:00', '沪A12345', 'PENDING'),
    (2, 2, '赵师傅', TIMESTAMP '2026-04-10 15:30:00', '沪B88888', 'APPROVED');

MERGE INTO parking KEY(id) VALUES
    (1, 'A-001', 'FREE', NULL),
    (2, 'B-102', 'SOLD', 1),
    (3, 'C-205', 'RENTED', 2);
