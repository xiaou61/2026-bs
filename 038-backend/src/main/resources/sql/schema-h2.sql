DROP TABLE IF EXISTS recipe_favorites;
DROP TABLE IF EXISTS recipes;
DROP TABLE IF EXISTS health_data;
DROP TABLE IF EXISTS nutrition_goals;
DROP TABLE IF EXISTS diet_records;
DROP TABLE IF EXISTS foods;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  nickname VARCHAR(50),
  avatar VARCHAR(255),
  gender TINYINT,
  age INT,
  height DECIMAL(5,2),
  weight DECIMAL(5,2),
  email VARCHAR(100),
  phone VARCHAR(20),
  role VARCHAR(20) NOT NULL DEFAULT 'USER',
  status TINYINT NOT NULL DEFAULT 1,
  deleted TINYINT NOT NULL DEFAULT 0,
  create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE foods (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  category VARCHAR(50) NOT NULL,
  unit VARCHAR(20) NOT NULL DEFAULT 'g',
  calorie DECIMAL(10,2) NOT NULL,
  protein DECIMAL(10,2) DEFAULT 0,
  fat DECIMAL(10,2) DEFAULT 0,
  carbohydrate DECIMAL(10,2) DEFAULT 0,
  fiber DECIMAL(10,2) DEFAULT 0,
  sodium DECIMAL(10,2) DEFAULT 0,
  image VARCHAR(255),
  description CLOB,
  is_custom TINYINT NOT NULL DEFAULT 0,
  user_id BIGINT,
  deleted TINYINT NOT NULL DEFAULT 0,
  create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE diet_records (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  food_id BIGINT NOT NULL,
  food_name VARCHAR(100) NOT NULL,
  meal_type VARCHAR(20) NOT NULL,
  weight DECIMAL(10,2) NOT NULL,
  calorie DECIMAL(10,2) NOT NULL,
  protein DECIMAL(10,2) DEFAULT 0,
  fat DECIMAL(10,2) DEFAULT 0,
  carbohydrate DECIMAL(10,2) DEFAULT 0,
  fiber DECIMAL(10,2) DEFAULT 0,
  sodium DECIMAL(10,2) DEFAULT 0,
  eat_date DATE NOT NULL,
  eat_time TIME,
  remark VARCHAR(500),
  deleted TINYINT NOT NULL DEFAULT 0,
  create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE nutrition_goals (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  goal_type VARCHAR(20) NOT NULL,
  target_weight DECIMAL(5,2),
  target_calorie DECIMAL(10,2) NOT NULL,
  target_protein DECIMAL(10,2),
  target_fat DECIMAL(10,2),
  target_carbohydrate DECIMAL(10,2),
  target_fiber DECIMAL(10,2),
  target_sodium DECIMAL(10,2),
  start_date DATE NOT NULL,
  end_date DATE,
  is_active TINYINT NOT NULL DEFAULT 1,
  deleted TINYINT NOT NULL DEFAULT 0,
  create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE health_data (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  record_date DATE NOT NULL,
  weight DECIMAL(5,2),
  bmi DECIMAL(5,2),
  body_fat_rate DECIMAL(5,2),
  muscle_mass DECIMAL(5,2),
  basal_metabolism DECIMAL(10,2),
  blood_pressure_high INT,
  blood_pressure_low INT,
  heart_rate INT,
  sleep_hours DECIMAL(4,1),
  exercise_minutes INT,
  remark VARCHAR(500),
  deleted TINYINT NOT NULL DEFAULT 0,
  create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE (user_id, record_date)
);

CREATE TABLE recipes (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  category VARCHAR(50) NOT NULL,
  difficulty VARCHAR(20) DEFAULT 'EASY',
  cooking_time INT,
  servings INT DEFAULT 1,
  total_calorie DECIMAL(10,2) DEFAULT 0,
  image VARCHAR(255),
  description CLOB,
  ingredients CLOB,
  steps CLOB,
  tags VARCHAR(200),
  author_id BIGINT,
  view_count INT DEFAULT 0,
  favorite_count INT DEFAULT 0,
  deleted TINYINT NOT NULL DEFAULT 0,
  create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE recipe_favorites (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  recipe_id BIGINT NOT NULL,
  create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE (user_id, recipe_id)
);
