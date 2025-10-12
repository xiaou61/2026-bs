CREATE DATABASE IF NOT EXISTS smart_recipe DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE smart_recipe;

CREATE TABLE IF NOT EXISTS `user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `username` VARCHAR(50) NOT NULL COMMENT '用户名',
    `password` VARCHAR(100) NOT NULL COMMENT '密码',
    `nickname` VARCHAR(50) NOT NULL COMMENT '昵称',
    `gender` TINYINT DEFAULT 0 COMMENT '性别 0女 1男',
    `age` INT DEFAULT NULL COMMENT '年龄',
    `height` DECIMAL(5,2) DEFAULT NULL COMMENT '身高(cm)',
    `weight` DECIMAL(5,2) DEFAULT NULL COMMENT '体重(kg)',
    `taste_prefer` VARCHAR(200) DEFAULT NULL COMMENT '口味偏好(JSON数组)',
    `diet_taboo` VARCHAR(200) DEFAULT NULL COMMENT '饮食禁忌(JSON数组)',
    `role` VARCHAR(20) NOT NULL DEFAULT 'USER' COMMENT '角色 USER/ADMIN',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态 0禁用 1正常',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

CREATE TABLE IF NOT EXISTS `ingredient` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name` VARCHAR(50) NOT NULL COMMENT '食材名称',
    `category` VARCHAR(20) NOT NULL COMMENT '分类',
    `calories` DECIMAL(8,2) DEFAULT 0 COMMENT '热量(kcal/100g)',
    `protein` DECIMAL(8,2) DEFAULT 0 COMMENT '蛋白质(g/100g)',
    `fat` DECIMAL(8,2) DEFAULT 0 COMMENT '脂肪(g/100g)',
    `carbohydrate` DECIMAL(8,2) DEFAULT 0 COMMENT '碳水化合物(g/100g)',
    `fiber` DECIMAL(8,2) DEFAULT 0 COMMENT '膳食纤维(g/100g)',
    `unit` VARCHAR(20) NOT NULL DEFAULT '克' COMMENT '常用单位',
    `shelf_life` INT DEFAULT 7 COMMENT '保质期(天)',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_category` (`category`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='全局食材表';

CREATE TABLE IF NOT EXISTS `user_ingredient` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `ingredient_id` BIGINT NOT NULL COMMENT '食材ID',
    `quantity` DECIMAL(10,2) NOT NULL COMMENT '数量',
    `unit` VARCHAR(20) NOT NULL COMMENT '单位',
    `purchase_date` DATE DEFAULT NULL COMMENT '购买日期',
    `expire_date` DATE DEFAULT NULL COMMENT '过期日期',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态 0已用完 1正常',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_ingredient_id` (`ingredient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户食材库存表';

CREATE TABLE IF NOT EXISTS `recipe` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name` VARCHAR(100) NOT NULL COMMENT '菜谱名称',
    `image` VARCHAR(200) DEFAULT NULL COMMENT '菜谱图片',
    `cuisine_type` VARCHAR(20) DEFAULT NULL COMMENT '菜系分类',
    `difficulty` TINYINT NOT NULL DEFAULT 1 COMMENT '难度 1简单 2中等 3困难',
    `cooking_time` INT DEFAULT 30 COMMENT '烹饪时间(分钟)',
    `serving_size` INT DEFAULT 2 COMMENT '适合人数',
    `total_calories` DECIMAL(10,2) DEFAULT 0 COMMENT '总热量',
    `description` TEXT COMMENT '描述',
    `view_count` INT NOT NULL DEFAULT 0 COMMENT '浏览次数',
    `collect_count` INT NOT NULL DEFAULT 0 COMMENT '收藏次数',
    `rating_score` DECIMAL(3,2) DEFAULT 5.00 COMMENT '平均评分',
    `rating_count` INT NOT NULL DEFAULT 0 COMMENT '评分人数',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态 0下架 1上架',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_cuisine_type` (`cuisine_type`),
    KEY `idx_difficulty` (`difficulty`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜谱表';

CREATE TABLE IF NOT EXISTS `recipe_ingredient` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `recipe_id` BIGINT NOT NULL COMMENT '菜谱ID',
    `ingredient_id` BIGINT NOT NULL COMMENT '食材ID',
    `quantity` DECIMAL(10,2) NOT NULL COMMENT '用量',
    `unit` VARCHAR(20) NOT NULL COMMENT '单位',
    `is_required` TINYINT NOT NULL DEFAULT 1 COMMENT '是否必需 0可选 1必需',
    PRIMARY KEY (`id`),
    KEY `idx_recipe_id` (`recipe_id`),
    KEY `idx_ingredient_id` (`ingredient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜谱食材关联表';

CREATE TABLE IF NOT EXISTS `recipe_step` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `recipe_id` BIGINT NOT NULL COMMENT '菜谱ID',
    `step_number` INT NOT NULL COMMENT '步骤序号',
    `description` TEXT NOT NULL COMMENT '步骤描述',
    `image` VARCHAR(200) DEFAULT NULL COMMENT '步骤图片',
    `duration` INT DEFAULT 0 COMMENT '步骤时长(分钟)',
    PRIMARY KEY (`id`),
    KEY `idx_recipe_id` (`recipe_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜谱步骤表';

CREATE TABLE IF NOT EXISTS `user_collect` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `recipe_id` BIGINT NOT NULL COMMENT '菜谱ID',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_recipe` (`user_id`, `recipe_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_recipe_id` (`recipe_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户收藏表';

CREATE TABLE IF NOT EXISTS `cooking_record` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `recipe_id` BIGINT NOT NULL COMMENT '菜谱ID',
    `image` VARCHAR(200) DEFAULT NULL COMMENT '成品图片',
    `note` TEXT COMMENT '心得体会',
    `taste_rating` TINYINT DEFAULT 5 COMMENT '味道评分 1-5',
    `difficulty_rating` TINYINT DEFAULT 3 COMMENT '难度评分 1-5',
    `time_rating` TINYINT DEFAULT 3 COMMENT '时间评分 1-5',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '打卡时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_recipe_id` (`recipe_id`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='做菜打卡表';

CREATE TABLE IF NOT EXISTS `shopping_list` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `ingredient_id` BIGINT NOT NULL COMMENT '食材ID',
    `quantity` DECIMAL(10,2) NOT NULL COMMENT '数量',
    `unit` VARCHAR(20) NOT NULL COMMENT '单位',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态 0未购买 1已购买',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_ingredient_id` (`ingredient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物清单表';

INSERT INTO `user` (`username`, `password`, `nickname`, `gender`, `age`, `height`, `weight`, `role`, `status`) VALUES
('admin', 'e10adc3949ba59abbe56e057f20f883e', '管理员', 1, 30, 175.00, 70.00, 'ADMIN', 1),
('user1', 'e10adc3949ba59abbe56e057f20f883e', '美食爱好者', 0, 25, 165.00, 55.00, 'USER', 1),
('user2', 'e10adc3949ba59abbe56e057f20f883e', '厨房新手', 1, 22, 178.00, 75.00, 'USER', 1);

INSERT INTO `ingredient` (`name`, `category`, `calories`, `protein`, `fat`, `carbohydrate`, `fiber`, `unit`, `shelf_life`) VALUES
('鸡蛋', '禽蛋类', 144.00, 13.30, 8.80, 2.80, 0.00, '个', 30),
('西红柿', '蔬菜类', 19.00, 0.90, 0.20, 4.00, 1.20, '个', 7),
('土豆', '蔬菜类', 81.00, 2.00, 0.20, 17.80, 0.70, '个', 30),
('猪肉', '肉类', 395.00, 13.20, 37.00, 2.40, 0.00, '克', 3),
('牛肉', '肉类', 125.00, 19.90, 4.20, 2.00, 0.00, '克', 3),
('大米', '谷物类', 346.00, 7.40, 0.80, 77.90, 0.70, '克', 365),
('面粉', '谷物类', 366.00, 11.20, 1.50, 73.60, 2.30, '克', 365),
('白菜', '蔬菜类', 17.00, 1.50, 0.10, 3.20, 0.80, '克', 7),
('豆腐', '豆制品', 81.00, 8.10, 3.70, 4.20, 0.40, '块', 3),
('鸡胸肉', '肉类', 133.00, 19.40, 5.00, 2.50, 0.00, '克', 3),
('黄瓜', '蔬菜类', 15.00, 0.80, 0.20, 2.90, 0.50, '根', 7),
('胡萝卜', '蔬菜类', 37.00, 1.00, 0.20, 8.80, 3.20, '根', 14),
('生姜', '调料类', 41.00, 1.30, 0.60, 8.70, 2.70, '克', 60),
('大蒜', '调料类', 128.00, 4.50, 0.20, 27.60, 1.10, '克', 60),
('食用油', '调料类', 899.00, 0.00, 99.90, 0.00, 0.00, '克', 365),
('食盐', '调料类', 0.00, 0.00, 0.00, 0.00, 0.00, '克', 3650),
('酱油', '调料类', 63.00, 5.60, 0.10, 10.10, 0.00, '克', 730),
('白糖', '调料类', 400.00, 0.00, 0.00, 99.90, 0.00, '克', 730);

INSERT INTO `recipe` (`name`, `image`, `cuisine_type`, `difficulty`, `cooking_time`, `serving_size`, `total_calories`, `description`, `rating_score`) VALUES
('西红柿炒鸡蛋', '/images/recipe1.jpg', '家常菜', 1, 15, 2, 350.00, '经典家常菜，酸甜可口，营养丰富', 4.80),
('土豆炖牛肉', '/images/recipe2.jpg', '家常菜', 2, 60, 3, 800.00, '营养丰富的家常炖菜，软烂入味', 4.70),
('麻婆豆腐', '/images/recipe3.jpg', '川菜', 2, 20, 2, 300.00, '麻辣鲜香的经典川菜', 4.90),
('清炒白菜', '/images/recipe4.jpg', '家常菜', 1, 10, 2, 80.00, '清淡爽口的素菜', 4.50),
('蒜蓉黄瓜', '/images/recipe5.jpg', '凉菜', 1, 5, 2, 50.00, '清爽开胃的凉拌菜', 4.60);

INSERT INTO `recipe_ingredient` (`recipe_id`, `ingredient_id`, `quantity`, `unit`, `is_required`) VALUES
(1, 1, 3.00, '个', 1),
(1, 2, 2.00, '个', 1),
(1, 15, 10.00, '克', 1),
(1, 16, 3.00, '克', 1),
(1, 18, 5.00, '克', 0),
(2, 3, 2.00, '个', 1),
(2, 5, 500.00, '克', 1),
(2, 12, 1.00, '根', 1),
(2, 13, 10.00, '克', 1),
(2, 15, 20.00, '克', 1),
(2, 16, 5.00, '克', 1),
(2, 17, 20.00, '克', 1),
(3, 9, 1.00, '块', 1),
(3, 4, 100.00, '克', 1),
(3, 13, 15.00, '克', 1),
(3, 14, 10.00, '克', 1),
(3, 15, 15.00, '克', 1),
(3, 17, 15.00, '克', 1),
(4, 8, 300.00, '克', 1),
(4, 14, 10.00, '克', 1),
(4, 15, 10.00, '克', 1),
(4, 16, 3.00, '克', 1),
(5, 11, 2.00, '根', 1),
(5, 14, 15.00, '克', 1),
(5, 17, 10.00, '克', 1),
(5, 16, 2.00, '克', 1);

INSERT INTO `recipe_step` (`recipe_id`, `step_number`, `description`, `duration`) VALUES
(1, 1, '鸡蛋打散，加少许盐搅拌均匀', 2),
(1, 2, '西红柿切块备用', 2),
(1, 3, '热锅下油，倒入鸡蛋液炒至凝固盛出', 3),
(1, 4, '锅中再加油，放入西红柿翻炒出汁', 4),
(1, 5, '倒入炒好的鸡蛋，加盐和糖调味，翻炒均匀即可', 4),
(2, 1, '牛肉切块焯水去血沫', 5),
(2, 2, '土豆去皮切块，胡萝卜切块', 5),
(2, 3, '热锅下油，放入姜片和牛肉块煸炒', 10),
(2, 4, '加入清水没过牛肉，大火烧开转小火炖40分钟', 40),
(2, 5, '加入土豆和胡萝卜，继续炖15分钟', 15),
(2, 6, '加盐和酱油调味，大火收汁即可', 5),
(3, 1, '豆腐切小块，焯水备用', 3),
(3, 2, '猪肉剁成末，姜蒜切末', 3),
(3, 3, '热锅下油，炒香姜蒜和肉末', 3),
(3, 4, '加入豆瓣酱炒出红油', 2),
(3, 5, '加入豆腐和适量水，煮5分钟', 5),
(3, 6, '加盐和酱油调味，水淀粉勾芡即可', 4),
(4, 1, '白菜洗净切段', 2),
(4, 2, '大蒜切片', 1),
(4, 3, '热锅下油，爆香蒜片', 2),
(4, 4, '倒入白菜大火翻炒至断生', 4),
(4, 5, '加盐调味即可出锅', 1),
(5, 1, '黄瓜洗净拍碎切块', 2),
(5, 2, '大蒜捣成蒜泥', 1),
(5, 3, '黄瓜放入碗中，加入蒜泥、盐、酱油拌匀', 2);

