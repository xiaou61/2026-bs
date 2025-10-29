USE campus_ordering;

-- 初始化管理员账号
INSERT INTO `admin` (`username`, `password`, `real_name`, `phone`, `role`, `status`) VALUES
('admin', '$2a$10$YJZeZH7nQ0BnxLhKLQxKdO5KqN6b6j3vVV4nXTLPxHN3pN1rj3j9O', '系统管理员', '13800138000', 1, 0);

-- 初始化菜品分类
INSERT INTO `category` (`category_name`, `parent_id`, `sort`, `status`) VALUES
('中餐', 0, 1, 0),
('西餐', 0, 2, 0),
('小吃', 0, 3, 0),
('饮品', 0, 4, 0),
('主食', 1, 1, 0),
('荤菜', 1, 2, 0),
('素菜', 1, 3, 0),
('汤类', 1, 4, 0),
('西式快餐', 2, 1, 0),
('西式简餐', 2, 2, 0);

-- 初始化测试商家
INSERT INTO `merchant` (`merchant_name`, `license_no`, `contact_person`, `contact_phone`, `password`, `address`, `location`, `category`, `image`, `description`, `notice`, `business_hours`, `delivery_fee`, `min_price`, `avg_prepare_time`, `status`, `audit_status`) VALUES
('食堂一楼中餐', 'LICENSE001', '张三', '13900000001', '$2a$10$YJZeZH7nQ0BnxLhKLQxKdO5KqN6b6j3vVV4nXTLPxHN3pN1rj3j9O', '第一食堂', '一楼A区', '中餐', 'https://via.placeholder.com/400x200?text=Chinese+Food', '提供各种中式家常菜', '欢迎光临！', '07:00-20:00', 0.00, 10.00, 20, 1, 1),
('食堂二楼西餐', 'LICENSE002', '李四', '13900000002', '$2a$10$YJZeZH7nQ0BnxLhKLQxKdO5KqN6b6j3vVV4nXTLPxHN3pN1rj3j9O', '第一食堂', '二楼B区', '西餐', 'https://via.placeholder.com/400x200?text=Western+Food', '西式简餐快餐', '新品上市！', '08:00-21:00', 2.00, 15.00, 15, 1, 1),
('食堂一楼小吃', 'LICENSE003', '王五', '13900000003', '$2a$10$YJZeZH7nQ0BnxLhKLQxKdO5KqN6b6j3vVV4nXTLPxHN3pN1rj3j9O', '第一食堂', '一楼C区', '小吃', 'https://via.placeholder.com/400x200?text=Snacks', '各种特色小吃', '美味小吃！', '07:30-21:30', 1.00, 5.00, 10, 1, 1);

-- 初始化测试用户
INSERT INTO `user` (`student_id`, `username`, `phone`, `password`, `real_name`, `department`, `major`, `dormitory`, `balance`, `status`) VALUES
('20210001', '学生张三', '13800000001', '$2a$10$YJZeZH7nQ0BnxLhKLQxKdO5KqN6b6j3vVV4nXTLPxHN3pN1rj3j9O', '张三', '计算机学院', '软件工程', '1号楼101', 500.00, 0),
('20210002', '学生李四', '13800000002', '$2a$10$YJZeZH7nQ0BnxLhKLQxKdO5KqN6b6j3vVV4nXTLPxHN3pN1rj3j9O', '李四', '计算机学院', '计算机科学', '2号楼201', 300.00, 0);

-- 初始化测试菜品（商家1-中餐）
INSERT INTO `dish` (`merchant_id`, `dish_name`, `category_id`, `image`, `description`, `price`, `original_price`, `stock`, `sales`, `month_sales`, `is_recommend`, `status`) VALUES
(1, '宫保鸡丁', 6, 'https://via.placeholder.com/300x300?text=Kung+Pao+Chicken', '经典川菜，香辣可口', 18.00, 22.00, 100, 156, 45, 1, 1),
(1, '鱼香肉丝', 6, 'https://via.placeholder.com/300x300?text=Fish+Flavored+Pork', '酸甜口味，下饭首选', 16.00, 20.00, 100, 132, 38, 1, 1),
(1, '青椒土豆丝', 7, 'https://via.placeholder.com/300x300?text=Potato+Shreds', '清淡爽口的家常菜', 8.00, 10.00, 100, 98, 28, 0, 1),
(1, '番茄炒蛋', 7, 'https://via.placeholder.com/300x300?text=Tomato+Egg', '经典搭配，营养美味', 10.00, 12.00, 100, 145, 42, 1, 1),
(1, '白米饭', 5, 'https://via.placeholder.com/300x300?text=Rice', '香喷喷的白米饭', 2.00, 2.00, 200, 256, 78, 0, 1),
(1, '紫菜蛋花汤', 8, 'https://via.placeholder.com/300x300?text=Seaweed+Soup', '清淡营养的汤品', 6.00, 6.00, 100, 89, 25, 0, 1);

-- 初始化测试菜品（商家2-西餐）
INSERT INTO `dish` (`merchant_id`, `dish_name`, `category_id`, `image`, `description`, `price`, `original_price`, `stock`, `sales`, `month_sales`, `is_recommend`, `status`) VALUES
(2, '汉堡套餐', 9, 'https://via.placeholder.com/300x300?text=Burger+Set', '汉堡+薯条+可乐', 28.00, 32.00, 50, 234, 67, 1, 1),
(2, '炸鸡套餐', 9, 'https://via.placeholder.com/300x300?text=Fried+Chicken', '香酥炸鸡，配饮料', 25.00, 28.00, 50, 189, 54, 1, 1),
(2, '意大利面', 10, 'https://via.placeholder.com/300x300?text=Pasta', '经典番茄肉酱意面', 22.00, 25.00, 30, 123, 35, 0, 1),
(2, '牛排套餐', 10, 'https://via.placeholder.com/300x300?text=Steak', '嫩滑牛排，配沙拉', 45.00, 50.00, 20, 78, 22, 1, 1);

-- 初始化测试菜品（商家3-小吃）
INSERT INTO `dish` (`merchant_id`, `dish_name`, `category_id`, `image`, `description`, `price`, `original_price`, `stock`, `sales`, `month_sales`, `is_recommend`, `status`) VALUES
(3, '煎饼果子', 3, 'https://via.placeholder.com/300x300?text=Jianbing', '传统早餐，香脆可口', 8.00, 10.00, 100, 312, 89, 1, 1),
(3, '肉夹馍', 3, 'https://via.placeholder.com/300x300?text=Roujiamo', '西安特色小吃', 12.00, 15.00, 80, 267, 76, 1, 1),
(3, '烤冷面', 3, 'https://via.placeholder.com/300x300?text=Grilled+Noodles', '东北特色小吃', 10.00, 12.00, 100, 198, 56, 0, 1),
(3, '奶茶', 4, 'https://via.placeholder.com/300x300?text=Milk+Tea', '香浓奶茶，多种口味', 12.00, 15.00, 150, 445, 127, 1, 1);

