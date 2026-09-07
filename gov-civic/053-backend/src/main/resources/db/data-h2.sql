INSERT INTO user (id, username, password, real_name, phone, role, status) VALUES
(1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '系统管理员', '13800000001', 'ADMIN', 1),
(2, 'warehouse', 'e10adc3949ba59abbe56e057f20f883e', '仓库管理员', '13800000002', 'WAREHOUSE', 1),
(3, 'rescuer', 'e10adc3949ba59abbe56e057f20f883e', '救援人员', '13800000003', 'RESCUER', 1),
(4, 'reporter', 'e10adc3949ba59abbe56e057f20f883e', '信息员', '13800000004', 'REPORTER', 1);

INSERT INTO material_category (id, name, icon, sort, status) VALUES
(1, '食品物资', 'icon-food', 1, 1),
(2, '医疗物资', 'icon-medical', 2, 1),
(3, '生活物资', 'icon-life', 3, 1),
(4, '救援设备', 'icon-equipment', 4, 1),
(5, '其他物资', 'icon-other', 5, 1);

INSERT INTO material (id, category_id, name, spec, unit, safe_stock, status) VALUES
(1, 1, '矿泉水', '550ml/瓶', '箱', 1000, 1),
(2, 1, '方便面', '桶装', '箱', 500, 1),
(3, 1, '压缩饼干', '500g/袋', '箱', 300, 1),
(4, 2, '医用口罩', '一次性', '盒', 2000, 1),
(5, 2, '消毒液', '500ml/瓶', '箱', 500, 1),
(6, 2, '医用纱布', '10cm*10cm', '包', 1000, 1),
(7, 3, '帐篷', '4人帐', '顶', 200, 1),
(8, 3, '棉被', '200*230cm', '床', 500, 1),
(9, 3, '折叠床', '钢架折叠', '张', 300, 1),
(10, 4, '手电筒', 'LED充电', '个', 200, 1),
(11, 4, '对讲机', '5W', '台', 50, 1),
(12, 4, '发电机', '3KW', '台', 20, 1);

INSERT INTO warehouse (id, name, code, address, capacity, phone, status) VALUES
(1, '中心仓库', 'WH001', '北京市朝阳区救灾物资储备中心', 10000, '010-12345678', 1),
(2, '东区仓库', 'WH002', '北京市通州区物资储备库', 5000, '010-23456789', 1),
(3, '西区仓库', 'WH003', '北京市海淀区应急物资库', 5000, '010-34567890', 1);

INSERT INTO stock (id, warehouse_id, material_id, quantity) VALUES
(1, 1, 1, 500),
(2, 1, 2, 300),
(3, 1, 3, 200),
(4, 1, 4, 1000),
(5, 1, 5, 300),
(6, 1, 6, 500),
(7, 1, 7, 100),
(8, 1, 8, 300),
(9, 1, 9, 150),
(10, 1, 10, 100),
(11, 2, 1, 300),
(12, 2, 2, 200),
(13, 2, 4, 500),
(14, 2, 7, 50),
(15, 2, 8, 200),
(16, 3, 1, 200),
(17, 3, 3, 100),
(18, 3, 5, 200),
(19, 3, 9, 100),
(20, 3, 11, 30);

INSERT INTO disaster (id, title, type, level, province, city, district, address, affected_count, description, status, reporter_id, report_time) VALUES
(1, '暴雨洪涝灾害', 'FLOOD', 3, '河南省', '郑州市', '中原区', '某某街道', 5000, '持续暴雨导致多处积水，道路中断', 1, 4, CURRENT_TIMESTAMP),
(2, '山体滑坡', 'OTHER', 2, '四川省', '成都市', '都江堰市', '某某镇', 200, '连续降雨引发山体滑坡', 0, 4, CURRENT_TIMESTAMP);

INSERT INTO notice (id, title, content, type, status, publisher_id, publish_time) VALUES
(1, '救灾物资调度系统上线通知', '系统已正式上线运行，请各部门配合使用', 2, 1, 1, CURRENT_TIMESTAMP),
(2, '物资清点通知', '请各仓库于本周内完成物资清点工作', 1, 1, 1, CURRENT_TIMESTAMP);
