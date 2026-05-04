INSERT INTO user (id, username, password, nickname, phone, email, role, status, balance) VALUES
(1, 'admin', '123456', 'MES平台管理员', '13800000001', 'admin@mes.com', 'ADMIN', 1, 99999.00),
(2, 'planner1', '123456', '一车间生产主管', '13800000002', 'planner1@mes.com', 'USER', 1, 18600.00),
(3, 'planner2', '123456', '二车间生产主管', '13800000003', 'planner2@mes.com', 'USER', 1, 15200.00),
(4, 'operator1', '123456', '计划员张工', '13800000004', 'operator1@mes.com', 'USER', 1, 8600.00);

INSERT INTO mes_category (id, name, icon, sort, status) VALUES
(1, '机加工', '/images/category/machining.png', 1, 1),
(2, '装配', '/images/category/assembly.png', 2, 1),
(3, '涂装', '/images/category/painting.png', 3, 1),
(4, '质检', '/images/category/quality.png', 4, 1),
(5, '包装入库', '/images/category/packaging.png', 5, 1),
(6, '设备保养', '/images/category/maintenance.png', 6, 1);

INSERT INTO mes_task (id, category_id, seller_id, title, service_name, store_name, vehicle_type, price, stock, cover, description, booking_type, view_count, booked_count, status) VALUES
(1, 1, 2, '电机壳体CNC批量加工任务', 'TASK-MES-001', '一号机加车间', 'CNC精加工', 320.00, 40, '/images/item/mes1.jpg', '完成电机壳体外圆精车、钻孔和去毛刺，支持批量工单排产。', '按批排产', 486, 95, 1),
(2, 1, 2, '减速器齿轮精铣任务', 'TASK-MES-002', '一号机加车间', '齿轮精铣', 450.00, 26, '/images/item/mes2.jpg', '按工艺卡完成齿轮精铣与尺寸复核，输出首件报告。', '按单排产', 352, 67, 1),
(3, 2, 3, '电控模块总装任务', 'TASK-MES-003', '二号装配车间', '模块装配', 510.00, 22, '/images/item/mes3.jpg', '完成控制板、线束、外壳总装及通电测试。', '按批排产', 308, 58, 1),
(4, 2, 3, '执行器子组件预装任务', 'TASK-MES-004', '二号装配车间', '子组件预装', 390.00, 30, '/images/item/mes4.jpg', '执行器子组件预装、扭矩校验及工序签核。', '按单排产', 277, 41, 1),
(5, 3, 2, '支架粉末喷涂任务', 'TASK-MES-005', '涂装车间', '静电喷涂', 280.00, 34, '/images/item/mes5.jpg', '支架喷涂、固化和色差检查，支持多批次连续排产。', '按批排产', 266, 39, 1),
(6, 4, 3, '成品抽检与复判任务', 'TASK-MES-006', '质量中心', '终检复判', 360.00, 18, '/images/item/mes6.jpg', '按AQL标准执行抽检、复判并输出检验报告。', '按单排产', 231, 28, 1),
(7, 5, 2, '包装贴标入库任务', 'TASK-MES-007', '包装车间', '包装入库', 190.00, 50, '/images/item/mes7.jpg', '执行成品包装、条码贴标和入库交接。', '按批排产', 205, 44, 1),
(8, 6, 3, '产线点检维护任务', 'TASK-MES-008', '设备维护组', '点检维护', 420.00, 16, '/images/item/mes8.jpg', '进行产线关键设备点检、润滑与异常复位。', '按单排产', 184, 25, 1),
(9, 4, 2, '关键尺寸在线复检任务', 'TASK-MES-009', '质量中心', '在线复检', 330.00, 24, '/images/item/mes9.jpg', '针对关键尺寸执行在线复检并回写质量数据。', '按批排产', 169, 21, 1),
(10, 3, 3, '外观返修喷涂任务', 'TASK-MES-010', '涂装车间', '外观返修', 260.00, 20, '/images/item/mes10.jpg', '返修喷涂、固化复检和色差确认。', '按单排产', 142, 18, 1);

INSERT INTO mes_work_order (id, order_no, user_id, service_id, seller_id, quantity, total_price, status, remark, appointment_date, appointment_time, plate_no, vehicle_model, fault_desc, pay_time, finish_time, create_time) VALUES
(1, 'MES202602240001', 4, 1, 2, 2, 640.00, 3, '需在周末前完成首批', '2026-02-22', '10:30:00', 'BATCH-202602-001', 'MTR-AX200', '优先保证壳体孔位同轴度，按工艺卡V3执行。', '2026-02-20 09:20:00', '2026-02-22 16:10:00', '2026-02-20 09:12:00'),
(2, 'MES202602240002', 4, 5, 2, 1, 280.00, 2, '请安排白班产线', '2026-02-25', '09:30:00', 'BATCH-202602-017', 'BRK-S200', '喷涂厚度控制在80um-100um，出具固化记录。', '2026-02-24 11:05:00', NULL, '2026-02-24 10:58:00'),
(3, 'MES202602240003', 2, 3, 3, 1, 510.00, 1, '先执行首件确认', '2026-02-26', '14:00:00', 'BATCH-202602-021', 'CTRL-M90', '总装后进行通电老化测试2小时。', '2026-02-24 14:10:00', NULL, '2026-02-24 14:02:00');

INSERT INTO review (id, order_id, user_id, service_id, rating, content, status, create_time) VALUES
(1, 1, 4, 1, 5, '工单执行节奏稳定，首件和批量一致性都很好。', 1, '2026-02-22 18:10:00');

INSERT INTO favorite (id, user_id, service_id) VALUES
(1, 4, 1),
(2, 4, 5),
(3, 4, 6),
(4, 2, 3);

INSERT INTO complaint (id, order_id, user_id, target_user_id, type, content, images, status, reply, reply_admin_id, reply_time, create_time) VALUES
(1, 2, 4, 2, 'ORDER', '计划时间被临时后移，影响下游装配节拍，请协助协调。', '', 1, '平台已协调车间优先排产，并补充班次保障当日交付。', 1, '2026-02-24 15:40:00', '2026-02-24 13:20:00');

INSERT INTO announcement (id, title, content, admin_id, status) VALUES
(1, 'MES排产模板升级通知', '系统已上线新版排产模板，支持批次号与产品型号联动校验。', 1, 1),
(2, '执行工单提交流程提醒', '请在创建执行工单时完整填写计划日期、批次号和工艺说明。', 1, 1),
(3, '月度产线稼动率分析', '本月新增稼动率看板，支持按车间与工艺维度查看趋势。', 1, 1);
