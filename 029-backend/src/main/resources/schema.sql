CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL UNIQUE,
  `password` varchar(255) NOT NULL,
  `email` varchar(100) NOT NULL UNIQUE,
  `phone` varchar(20),
  `avatar` varchar(255),
  `nickname` varchar(50),
  `bio` varchar(500),
  `user_type` tinyint DEFAULT 0,
  `status` tinyint DEFAULT 1,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `idx_username` (`username`)
);

CREATE TABLE IF NOT EXISTS `health_profile` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `constitution` varchar(50),
  `allergies` text,
  `health_goals` text,
  `dietary_restrictions` text,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  UNIQUE KEY `uk_user_id` (`user_id`)
);

CREATE TABLE IF NOT EXISTS `recipe` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `description` text,
  `author_id` bigint NOT NULL,
  `cover_image` varchar(255),
  `efficacy` text,
  `nutrition` text,
  `difficulty` tinyint,
  `cook_time` int,
  `servings` int,
  `applicable_people` varchar(255),
  `seasons` varchar(255),
  `status` tinyint DEFAULT 0,
  `views` int DEFAULT 0,
  `likes` int DEFAULT 0,
  `collects` int DEFAULT 0,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`author_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  INDEX `idx_author_id` (`author_id`),
  INDEX `idx_status` (`status`)
);

CREATE TABLE IF NOT EXISTS `ingredient` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL UNIQUE,
  `category` varchar(50),
  `nature` varchar(50),
  `taste` varchar(100),
  `efficacy` text,
  `nutrition` text,
  `image` varchar(255),
  `status` tinyint DEFAULT 1,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `idx_category` (`category`)
);

CREATE TABLE IF NOT EXISTS `ingredient_taboo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ingredient_id1` bigint NOT NULL,
  `ingredient_id2` bigint NOT NULL,
  `reason` text,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`ingredient_id1`) REFERENCES `ingredient` (`id`) ON DELETE CASCADE,
  FOREIGN KEY (`ingredient_id2`) REFERENCES `ingredient` (`id`) ON DELETE CASCADE,
  UNIQUE KEY `uk_ingredients` (`ingredient_id1`, `ingredient_id2`)
);

CREATE TABLE IF NOT EXISTS `recipe_ingredient` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `recipe_id` bigint NOT NULL,
  `ingredient_id` bigint NOT NULL,
  `quantity` decimal(10,2),
  `unit` varchar(20),
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`id`) ON DELETE CASCADE,
  FOREIGN KEY (`ingredient_id`) REFERENCES `ingredient` (`id`) ON DELETE CASCADE,
  INDEX `idx_recipe_id` (`recipe_id`)
);

CREATE TABLE IF NOT EXISTS `topic` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(200) NOT NULL,
  `content` text,
  `author_id` bigint NOT NULL,
  `category` varchar(50),
  `tags` text,
  `views` int DEFAULT 0,
  `replies` int DEFAULT 0,
  `status` tinyint DEFAULT 1,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`author_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  INDEX `idx_author_id` (`author_id`),
  INDEX `idx_status` (`status`)
);

CREATE TABLE IF NOT EXISTS `comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `target_type` tinyint NOT NULL,
  `target_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `content` text,
  `rating` tinyint,
  `image_urls` text,
  `likes` int DEFAULT 0,
  `status` tinyint DEFAULT 0,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  INDEX `idx_target` (`target_type`, `target_id`),
  INDEX `idx_user_id` (`user_id`)
);

CREATE TABLE IF NOT EXISTS `collection` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `target_type` tinyint NOT NULL,
  `target_id` bigint NOT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  UNIQUE KEY `uk_collection` (`user_id`, `target_type`, `target_id`),
  INDEX `idx_user_target` (`user_id`, `target_type`)
);

CREATE TABLE IF NOT EXISTS `creator_auth` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `auth_type` varchar(50),
  `credentials` text,
  `status` tinyint DEFAULT 0,
  `auth_time` datetime,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  UNIQUE KEY `uk_user_id` (`user_id`),
  INDEX `idx_status` (`status`)
);
