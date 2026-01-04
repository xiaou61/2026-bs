-- ====================================================
-- 个人饮食管理系统数据库建表脚本
-- Database: diet_management
-- ====================================================

CREATE DATABASE IF NOT EXISTS diet_management DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE diet_management;

-- ====================================================
-- 1. 用户表
-- ====================================================
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` VARCHAR(50) NOT NULL COMMENT '用户名',
  `password` VARCHAR(255) NOT NULL COMMENT '密码',
  `nickname` VARCHAR(50) DEFAULT NULL COMMENT '昵称',
  `avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像URL',
  `gender` TINYINT(1) DEFAULT NULL COMMENT '性别(0女1男)',
  `age` INT(11) DEFAULT NULL COMMENT '年龄',
  `height` DECIMAL(5,2) DEFAULT NULL COMMENT '身高(cm)',
  `weight` DECIMAL(5,2) DEFAULT NULL COMMENT '体重(kg)',
  `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
  `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
  `role` VARCHAR(20) NOT NULL DEFAULT 'USER' COMMENT '角色(USER/ADMIN)',
  `status` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态(0禁用1正常)',
  `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除(0否1是)',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  KEY `idx_phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ====================================================
-- 2. 食物库表
-- ====================================================
DROP TABLE IF EXISTS `foods`;
CREATE TABLE `foods` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '食物ID',
  `name` VARCHAR(100) NOT NULL COMMENT '食物名称',
  `category` VARCHAR(50) NOT NULL COMMENT '食物分类',
  `unit` VARCHAR(20) NOT NULL DEFAULT 'g' COMMENT '单位',
  `calorie` DECIMAL(10,2) NOT NULL COMMENT '热量(kcal/100g)',
  `protein` DECIMAL(10,2) DEFAULT 0 COMMENT '蛋白质(g/100g)',
  `fat` DECIMAL(10,2) DEFAULT 0 COMMENT '脂肪(g/100g)',
  `carbohydrate` DECIMAL(10,2) DEFAULT 0 COMMENT '碳水化合物(g/100g)',
  `fiber` DECIMAL(10,2) DEFAULT 0 COMMENT '膳食纤维(g/100g)',
  `sodium` DECIMAL(10,2) DEFAULT 0 COMMENT '钠(mg/100g)',
  `image` VARCHAR(255) DEFAULT NULL COMMENT '食物图片',
  `description` TEXT COMMENT '食物描述',
  `is_custom` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否自定义(0否1是)',
  `user_id` BIGINT(20) DEFAULT NULL COMMENT '创建用户ID(自定义食物)',
  `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除(0否1是)',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_category` (`category`),
  KEY `idx_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='食物库表';

-- ====================================================
-- 3. 饮食记录表
-- ====================================================
DROP TABLE IF EXISTS `diet_records`;
CREATE TABLE `diet_records` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
  `food_id` BIGINT(20) NOT NULL COMMENT '食物ID',
  `food_name` VARCHAR(100) NOT NULL COMMENT '食物名称',
  `meal_type` VARCHAR(20) NOT NULL COMMENT '餐次类型(BREAKFAST/LUNCH/DINNER/SNACK)',
  `weight` DECIMAL(10,2) NOT NULL COMMENT '食用重量(g)',
  `calorie` DECIMAL(10,2) NOT NULL COMMENT '摄入热量(kcal)',
  `protein` DECIMAL(10,2) DEFAULT 0 COMMENT '摄入蛋白质(g)',
  `fat` DECIMAL(10,2) DEFAULT 0 COMMENT '摄入脂肪(g)',
  `carbohydrate` DECIMAL(10,2) DEFAULT 0 COMMENT '摄入碳水化合物(g)',
  `fiber` DECIMAL(10,2) DEFAULT 0 COMMENT '摄入膳食纤维(g)',
  `sodium` DECIMAL(10,2) DEFAULT 0 COMMENT '摄入钠(mg)',
  `eat_date` DATE NOT NULL COMMENT '进食日期',
  `eat_time` TIME DEFAULT NULL COMMENT '进食时间',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
  `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除(0否1是)',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_date` (`user_id`, `eat_date`),
  KEY `idx_food_id` (`food_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='饮食记录表';

-- ====================================================
-- 4. 营养目标表
-- ====================================================
DROP TABLE IF EXISTS `nutrition_goals`;
CREATE TABLE `nutrition_goals` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '目标ID',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
  `goal_type` VARCHAR(20) NOT NULL COMMENT '目标类型(WEIGHT_LOSS/WEIGHT_GAIN/MAINTAIN)',
  `target_weight` DECIMAL(5,2) DEFAULT NULL COMMENT '目标体重(kg)',
  `target_calorie` INT(11) NOT NULL COMMENT '目标热量(kcal/天)',
  `target_protein` DECIMAL(10,2) DEFAULT NULL COMMENT '目标蛋白质(g/天)',
  `target_fat` DECIMAL(10,2) DEFAULT NULL COMMENT '目标脂肪(g/天)',
  `target_carbohydrate` DECIMAL(10,2) DEFAULT NULL COMMENT '目标碳水(g/天)',
  `target_fiber` DECIMAL(10,2) DEFAULT NULL COMMENT '目标膳食纤维(g/天)',
  `target_sodium` DECIMAL(10,2) DEFAULT NULL COMMENT '目标钠(mg/天)',
  `start_date` DATE NOT NULL COMMENT '开始日期',
  `end_date` DATE DEFAULT NULL COMMENT '结束日期',
  `is_active` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否激活(0否1是)',
  `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除(0否1是)',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='营养目标表';

-- ====================================================
-- 5. 健康数据表
-- ====================================================
DROP TABLE IF EXISTS `health_data`;
CREATE TABLE `health_data` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '数据ID',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
  `record_date` DATE NOT NULL COMMENT '记录日期',
  `weight` DECIMAL(5,2) DEFAULT NULL COMMENT '体重(kg)',
  `bmi` DECIMAL(5,2) DEFAULT NULL COMMENT 'BMI指数',
  `body_fat_rate` DECIMAL(5,2) DEFAULT NULL COMMENT '体脂率(%)',
  `muscle_mass` DECIMAL(5,2) DEFAULT NULL COMMENT '肌肉量(kg)',
  `basal_metabolism` INT(11) DEFAULT NULL COMMENT '基础代谢(kcal)',
  `blood_pressure_high` INT(11) DEFAULT NULL COMMENT '收缩压(mmHg)',
  `blood_pressure_low` INT(11) DEFAULT NULL COMMENT '舒张压(mmHg)',
  `heart_rate` INT(11) DEFAULT NULL COMMENT '心率(次/分)',
  `sleep_hours` DECIMAL(4,1) DEFAULT NULL COMMENT '睡眠时长(小时)',
  `exercise_minutes` INT(11) DEFAULT NULL COMMENT '运动时长(分钟)',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
  `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除(0否1是)',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_date` (`user_id`, `record_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='健康数据表';

-- ====================================================
-- 6. 食谱表
-- ====================================================
DROP TABLE IF EXISTS `recipes`;
CREATE TABLE `recipes` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '食谱ID',
  `name` VARCHAR(100) NOT NULL COMMENT '食谱名称',
  `category` VARCHAR(50) NOT NULL COMMENT '食谱分类',
  `difficulty` VARCHAR(20) DEFAULT 'EASY' COMMENT '难度(EASY/MEDIUM/HARD)',
  `cooking_time` INT(11) DEFAULT NULL COMMENT '烹饪时长(分钟)',
  `servings` INT(11) DEFAULT 1 COMMENT '份数',
  `total_calorie` DECIMAL(10,2) DEFAULT 0 COMMENT '总热量(kcal)',
  `image` VARCHAR(255) DEFAULT NULL COMMENT '食谱图片',
  `description` TEXT COMMENT '食谱描述',
  `ingredients` TEXT COMMENT '食材清单(JSON)',
  `steps` TEXT COMMENT '制作步骤(JSON)',
  `tags` VARCHAR(200) DEFAULT NULL COMMENT '标签',
  `author_id` BIGINT(20) DEFAULT NULL COMMENT '作者ID',
  `view_count` INT(11) DEFAULT 0 COMMENT '浏览次数',
  `favorite_count` INT(11) DEFAULT 0 COMMENT '收藏次数',
  `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除(0否1是)',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_category` (`category`),
  KEY `idx_author_id` (`author_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='食谱表';

-- ====================================================
-- 7. 食谱收藏表
-- ====================================================
DROP TABLE IF EXISTS `recipe_favorites`;
CREATE TABLE `recipe_favorites` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '收藏ID',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
  `recipe_id` BIGINT(20) NOT NULL COMMENT '食谱ID',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_recipe` (`user_id`, `recipe_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='食谱收藏表';
