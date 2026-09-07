INSERT INTO sys_role (id, name, code) VALUES
    (1, '管理员', 'ADMIN'),
    (2, '仓库员', 'WAREHOUSE');

INSERT INTO sys_user (id, username, password, role_id, status, created_at) VALUES
    (1, 'admin', 'admin', 1, 'ENABLED', CURRENT_TIMESTAMP()),
    (2, 'warehouse', 'warehouse', 2, 'ENABLED', CURRENT_TIMESTAMP());

INSERT INTO supplier (id, name, contact, phone) VALUES
    (1, '零食总仓供应商', '王采购', '13800000001');

INSERT INTO customer (id, name, contact, phone) VALUES
    (1, '校园门店A', '李店长', '13900000001');

INSERT INTO warehouse (id, name, code, address) VALUES
    (1, '中心仓', 'WH-CENTER', '零食街 1 号'),
    (2, '门店前置仓', 'WH-FRONT', '零食街 2 号');

INSERT INTO zone (id, warehouse_id, name, type) VALUES
    (1, 1, '常温区', 'NORMAL'),
    (2, 2, '门店区', 'NORMAL');

INSERT INTO location (id, zone_id, code, capacity) VALUES
    (1, 1, 'A-01-01', 500),
    (2, 2, 'B-01-01', 300);

INSERT INTO sku (id, code, name, spec, shelf_life_days, barcode, supplier_id) VALUES
    (1, 'SKU-CHIPS-001', '番茄味薯片', '80g/袋', 240, '690000000001', 1),
    (2, 'SKU-NUTS-001', '原味坚果', '120g/袋', 180, '690000000002', 1);

INSERT INTO batch (id, sku_id, batch_no, production_date, expire_date) VALUES
    (1, 1, 'BATCH-202601', DATE '2026-01-01', DATE '2026-12-31'),
    (2, 2, 'BATCH-202602', DATE '2026-02-01', DATE '2026-10-31');

INSERT INTO inventory (id, sku_id, warehouse_id, location_id, batch_id, quantity_available, quantity_locked, quantity_frozen) VALUES
    (1, 1, 1, 1, 1, 100, 0, 0),
    (2, 2, 1, 1, 2, 60, 0, 0);
