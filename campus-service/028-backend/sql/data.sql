-- 初始数据脚本
USE campus_bike;

-- 插入默认管理员 (密码: admin123)
INSERT INTO `admin` (`username`, `password`, `real_name`, `phone`, `role`, `status`) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '超级管理员', '13800138000', 2, 1),
('operator', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '运维人员', '13800138001', 3, 1);

-- 插入示例停车点
INSERT INTO `station` (`station_name`, `address`, `longitude`, `latitude`, `capacity`, `current_count`, `status`, `description`) VALUES
('教学楼A区', '教学楼A栋东侧', 116.3912750, 39.9065430, 30, 15, 1, '靠近教学楼A栋，方便上课'),
('教学楼B区', '教学楼B栋西侧', 116.3922750, 39.9075430, 25, 12, 1, '靠近教学楼B栋，方便上课'),
('图书馆站', '图书馆北门', 116.3932750, 39.9085430, 40, 20, 1, '图书馆门口，方便学习'),
('食堂站', '第一食堂门口', 116.3942750, 39.9095430, 50, 25, 1, '食堂门口，就餐高峰期车辆多'),
('宿舍1号站', '1号宿舍楼东侧', 116.3952750, 39.9105430, 35, 18, 1, '1号宿舍楼旁'),
('宿舍2号站', '2号宿舍楼西侧', 116.3962750, 39.9115430, 35, 16, 1, '2号宿舍楼旁'),
('体育馆站', '体育馆正门', 116.3972750, 39.9125430, 30, 10, 1, '体育馆门口'),
('行政楼站', '行政楼南侧', 116.3982750, 39.9135430, 20, 8, 1, '行政办公区'),
('实验楼站', '实验楼门口', 116.3992750, 39.9145430, 25, 12, 1, '实验楼旁边'),
('校门站', '学校正门内', 116.4002750, 39.9155430, 45, 22, 1, '校门口，进出校园便捷');

-- 插入示例自行车
INSERT INTO `bicycle` (`bike_no`, `qr_code`, `bike_type`, `status`, `station_id`, `longitude`, `latitude`, `purchase_date`) VALUES
('BIKE001', 'QR_BIKE001', 1, 0, 1, 116.3912750, 39.9065430, '2024-01-01'),
('BIKE002', 'QR_BIKE002', 1, 0, 1, 116.3912750, 39.9065430, '2024-01-01'),
('BIKE003', 'QR_BIKE003', 1, 0, 1, 116.3912750, 39.9065430, '2024-01-01'),
('BIKE004', 'QR_BIKE004', 1, 0, 2, 116.3922750, 39.9075430, '2024-01-01'),
('BIKE005', 'QR_BIKE005', 1, 0, 2, 116.3922750, 39.9075430, '2024-01-01'),
('BIKE006', 'QR_BIKE006', 2, 0, 3, 116.3932750, 39.9085430, '2024-02-01'),
('BIKE007', 'QR_BIKE007', 2, 0, 3, 116.3932750, 39.9085430, '2024-02-01'),
('BIKE008', 'QR_BIKE008', 1, 0, 4, 116.3942750, 39.9095430, '2024-01-15'),
('BIKE009', 'QR_BIKE009', 1, 0, 4, 116.3942750, 39.9095430, '2024-01-15'),
('BIKE010', 'QR_BIKE010', 1, 0, 5, 116.3952750, 39.9105430, '2024-01-15'),
('BIKE011', 'QR_BIKE011', 1, 0, 5, 116.3952750, 39.9105430, '2024-01-15'),
('BIKE012', 'QR_BIKE012', 1, 0, 6, 116.3962750, 39.9115430, '2024-02-01'),
('BIKE013', 'QR_BIKE013', 2, 0, 7, 116.3972750, 39.9125430, '2024-02-01'),
('BIKE014', 'QR_BIKE014', 1, 0, 8, 116.3982750, 39.9135430, '2024-03-01'),
('BIKE015', 'QR_BIKE015', 1, 0, 9, 116.3992750, 39.9145430, '2024-03-01'),
('BIKE016', 'QR_BIKE016', 1, 0, 10, 116.4002750, 39.9155430, '2024-03-01'),
('BIKE017', 'QR_BIKE017', 1, 0, 10, 116.4002750, 39.9155430, '2024-03-01'),
('BIKE018', 'QR_BIKE018', 2, 0, 10, 116.4002750, 39.9155430, '2024-03-01'),
('BIKE019', 'QR_BIKE019', 1, 2, NULL, NULL, NULL, '2024-01-01'),
('BIKE020', 'QR_BIKE020', 1, 3, NULL, NULL, NULL, '2023-01-01');

-- 插入系统配置
INSERT INTO `system_config` (`config_key`, `config_value`, `description`) VALUES
('price_per_30min', '1.0', '每30分钟收费（元）'),
('daily_cap', '20.0', '日封顶费用（元）'),
('deposit', '99.0', '押金（元）'),
('free_deposit_credit', '600', '免押金所需信用分'),
('night_discount', '0.8', '夜间折扣'),
('night_start', '22', '夜间开始时间'),
('night_end', '6', '夜间结束时间'),
('initial_credit', '100', '初始信用分'),
('normal_return_credit', '1', '正常还车信用分'),
('proper_parking_credit', '2', '规范停车信用分'),
('report_fault_credit', '5', '上报故障信用分'),
('overtime_penalty_credit', '-10', '超时未还扣分'),
('illegal_parking_credit', '-5', '违规停车扣分'),
('damage_penalty_credit', '-20', '损坏车辆扣分');

-- 插入测试用户 (密码: 123456)
INSERT INTO `user` (`username`, `password`, `real_name`, `phone`, `student_id`, `gender`, `credit_score`, `status`, `auth_status`) VALUES
('testuser', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '测试用户', '13900139000', '2024001', 1, 100, 1, 2),
('zhangsan', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '张三', '13900139001', '2024002', 1, 150, 1, 2),
('lisi', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '李四', '13900139002', '2024003', 2, 80, 1, 2);

-- 插入用户钱包
INSERT INTO `user_wallet` (`user_id`, `balance`, `deposit`, `deposit_status`) VALUES
(1, 50.00, 99.00, 1),
(2, 100.00, 99.00, 1),
(3, 20.00, 0.00, 0);
