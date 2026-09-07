INSERT INTO users (id, username, password, nickname, gender, age, height, weight, email, phone, role) VALUES
(1, 'admin', '$2b$12$jK8zNPbXrkWPvITUUIio4Od0t5y/ZQ2F2owIex/Sa2eScamJF1BBW', '管理员', 1, 25, 175.00, 70.00, 'admin@diet.com', '13800138000', 'ADMIN'),
(2, 'user1', '$2b$12$jK8zNPbXrkWPvITUUIio4Od0t5y/ZQ2F2owIex/Sa2eScamJF1BBW', '健康达人', 0, 22, 165.00, 55.00, 'user1@test.com', '13800138001', 'USER'),
(3, 'user2', '$2b$12$jK8zNPbXrkWPvITUUIio4Od0t5y/ZQ2F2owIex/Sa2eScamJF1BBW', '减肥小能手', 1, 28, 180.00, 85.00, 'user2@test.com', '13800138002', 'USER');

INSERT INTO foods (name, category, calorie, protein, fat, carbohydrate, fiber, sodium) VALUES
('米饭', '主食', 116, 2.6, 0.3, 25.9, 0.3, 2.0),
('馒头', '主食', 233, 7.0, 1.1, 49.0, 1.3, 213.0),
('面条', '主食', 284, 8.3, 1.4, 60.0, 1.3, 300.0),
('鸡胸肉', '肉类', 133, 19.4, 5.0, 2.5, 0.0, 63.0),
('鸡蛋', '蛋类', 144, 13.3, 8.8, 2.8, 0.0, 131.0),
('牛奶', '乳制品', 54, 3.0, 3.2, 3.4, 0.0, 37.0),
('苹果', '水果', 52, 0.2, 0.2, 13.8, 2.4, 1.0),
('香蕉', '水果', 89, 1.1, 0.2, 22.8, 2.6, 1.0),
('西兰花', '蔬菜', 34, 4.1, 0.6, 4.3, 3.0, 34.0),
('番茄', '蔬菜', 18, 0.9, 0.2, 3.9, 1.2, 5.0);

INSERT INTO diet_records (user_id, food_id, food_name, meal_type, weight, calorie, protein, fat, carbohydrate, fiber, sodium, eat_date, eat_time, remark) VALUES
(2, 1, '米饭', 'BREAKFAST', 150.00, 174.00, 3.90, 0.45, 38.85, 0.45, 3.00, CURRENT_DATE, '08:00:00', '早餐主食'),
(2, 4, '鸡胸肉', 'LUNCH', 120.00, 159.60, 23.28, 6.00, 3.00, 0.00, 75.60, CURRENT_DATE, '12:30:00', '午餐蛋白质');

INSERT INTO nutrition_goals (user_id, goal_type, target_weight, target_calorie, target_protein, target_fat, target_carbohydrate, start_date, is_active) VALUES
(2, 'MAINTAIN', 55.00, 1800, 65, 50, 250, CURRENT_DATE, 1),
(3, 'WEIGHT_LOSS', 75.00, 1600, 80, 40, 200, CURRENT_DATE, 1);

INSERT INTO health_data (user_id, record_date, weight, bmi, body_fat_rate, muscle_mass, basal_metabolism, blood_pressure_high, blood_pressure_low, heart_rate, sleep_hours, exercise_minutes, remark) VALUES
(2, CURRENT_DATE, 55.00, 20.20, 22.50, 38.00, 1280, 112, 72, 76, 7.5, 30, '状态良好'),
(3, CURRENT_DATE, 85.00, 26.23, 25.80, 58.00, 1780, 126, 82, 80, 6.5, 45, '减脂期');

INSERT INTO recipes (name, category, difficulty, cooking_time, servings, total_calorie, description, tags) VALUES
('水煮鸡胸肉', '减脂餐', 'EASY', 15, 1, 200, '简单快手的健康减脂餐', '低脂,高蛋白'),
('番茄炒蛋', '家常菜', 'EASY', 10, 2, 350, '经典家常菜，营养美味', '快手菜,营养'),
('蔬菜沙拉', '轻食', 'EASY', 5, 1, 150, '清爽低卡的蔬菜沙拉', '低卡,维生素');
