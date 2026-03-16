DROP DATABASE IF EXISTS `088_child_adoption`;
CREATE DATABASE `088_child_adoption` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `088_child_adoption`;

CREATE TABLE `sys_user` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `username` VARCHAR(50) NOT NULL UNIQUE,
    `password` VARCHAR(100) NOT NULL,
    `real_name` VARCHAR(50) NOT NULL,
    `phone` VARCHAR(20),
    `role` VARCHAR(20) NOT NULL,
    `status` TINYINT DEFAULT 1,
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `adopter_profile` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL,
    `gender` TINYINT,
    `id_card` VARCHAR(30),
    `marital_status` VARCHAR(20),
    `birth_date` DATE,
    `province` VARCHAR(50),
    `city` VARCHAR(50),
    `address` VARCHAR(255),
    `occupation` VARCHAR(100),
    `income_level` VARCHAR(50),
    `family_info` TEXT,
    `adoption_reason` TEXT,
    `status` TINYINT DEFAULT 1,
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `child_profile` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `child_no` VARCHAR(32) NOT NULL UNIQUE,
    `name` VARCHAR(50) NOT NULL,
    `gender` TINYINT,
    `birth_date` DATE,
    `age` INT,
    `status` TINYINT DEFAULT 1,
    `adoption_status` TINYINT DEFAULT 0,
    `avatar_url` VARCHAR(255),
    `summary` TEXT,
    `guardian_info` VARCHAR(255),
    `admission_date` DATE,
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `child_health_record` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `child_id` BIGINT NOT NULL,
    `health_status` VARCHAR(100),
    `blood_type` VARCHAR(20),
    `allergy_info` VARCHAR(255),
    `vaccination_info` VARCHAR(255),
    `medical_history` TEXT,
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `adoption_application` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `application_no` VARCHAR(32) NOT NULL UNIQUE,
    `child_id` BIGINT NOT NULL,
    `applicant_id` BIGINT NOT NULL,
    `status` TINYINT DEFAULT 1,
    `reason` TEXT,
    `expected_contact_date` DATE,
    `review_remark` VARCHAR(255),
    `submit_time` DATETIME,
    `review_time` DATETIME,
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `application_material` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `application_id` BIGINT NOT NULL,
    `material_type` VARCHAR(50),
    `material_name` VARCHAR(100),
    `file_url` VARCHAR(255),
    `review_status` TINYINT DEFAULT 0,
    `remark` VARCHAR(255),
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `home_visit_record` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `application_id` BIGINT NOT NULL,
    `reviewer_id` BIGINT NOT NULL,
    `visit_date` DATE,
    `visit_result` VARCHAR(50),
    `visit_remark` TEXT,
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `match_record` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `application_id` BIGINT NOT NULL,
    `child_id` BIGINT NOT NULL,
    `reviewer_id` BIGINT NOT NULL,
    `match_score` INT,
    `status` TINYINT DEFAULT 0,
    `match_remark` VARCHAR(255),
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `approval_record` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `application_id` BIGINT NOT NULL,
    `node_name` VARCHAR(50),
    `approval_status` TINYINT DEFAULT 0,
    `approval_remark` VARCHAR(255),
    `approver_id` BIGINT,
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `adoption_agreement` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `agreement_no` VARCHAR(32) NOT NULL UNIQUE,
    `application_id` BIGINT NOT NULL,
    `child_id` BIGINT NOT NULL,
    `applicant_id` BIGINT NOT NULL,
    `sign_status` TINYINT DEFAULT 0,
    `sign_date` DATE,
    `agreement_content` TEXT,
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `follow_up_record` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `agreement_id` BIGINT NOT NULL,
    `application_id` BIGINT NOT NULL,
    `follow_date` DATE,
    `follow_status` VARCHAR(50),
    `follow_remark` TEXT,
    `next_follow_date` DATE,
    `reviewer_id` BIGINT NOT NULL,
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `system_notice` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `title` VARCHAR(100) NOT NULL,
    `type` VARCHAR(30),
    `content` TEXT,
    `status` TINYINT DEFAULT 1,
    `publish_time` DATETIME,
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO `sys_user` (`id`, `username`, `password`, `real_name`, `phone`, `role`, `status`) VALUES
(1, 'admin', '123456', '系统管理员', '13800000001', 'admin', 1),
(2, 'reviewer', '123456', '审核专员', '13800000002', 'reviewer', 1),
(3, 'applicant', '123456', '李晓晴', '13800000003', 'applicant', 1),
(4, 'applicant02', '123456', '周文博', '13800000004', 'applicant', 1);

INSERT INTO `adopter_profile` (`id`, `user_id`, `gender`, `id_card`, `marital_status`, `birth_date`, `province`, `city`, `address`, `occupation`, `income_level`, `family_info`, `adoption_reason`, `status`) VALUES
(1, 3, 2, '420101199506180021', '已婚', '1995-06-18', '湖北省', '武汉市', '武昌区中北路88号', '教师', '15-25万', '夫妻二人稳定工作，有一套住房。', '希望给孩子一个温暖稳定的成长环境。', 1),
(2, 4, 1, '320101199302150019', '已婚', '1993-02-15', '江苏省', '南京市', '建邺区江东中路66号', '工程师', '20-30万', '家庭成员关系稳定，父母支持收养。', '愿意承担长期监护责任并提供教育资源。', 1);

INSERT INTO `child_profile` (`id`, `child_no`, `name`, `gender`, `birth_date`, `age`, `status`, `adoption_status`, `avatar_url`, `summary`, `guardian_info`, `admission_date`) VALUES
(1, 'CH20260001', '晨晨', 1, '2020-04-10', 5, 1, 0, 'https://images.unsplash.com/photo-1503454537195-1dcabb73ffb9?auto=format&fit=crop&w=800&q=80', '性格开朗，喜欢绘画和拼图。', '武汉市儿童福利院', '2021-01-15'),
(2, 'CH20260002', '小雨', 2, '2019-08-21', 6, 1, 2, 'https://images.unsplash.com/photo-1516627145497-ae6968895b74?auto=format&fit=crop&w=800&q=80', '适应能力强，热爱音乐。', '武汉市儿童福利院', '2020-03-08'),
(3, 'CH20260003', '乐乐', 1, '2021-02-11', 4, 1, 0, 'https://images.unsplash.com/photo-1519345182560-3f2917c472ef?auto=format&fit=crop&w=800&q=80', '活泼好动，喜欢户外活动。', '武汉市儿童福利院', '2022-06-20');

INSERT INTO `child_health_record` (`id`, `child_id`, `health_status`, `blood_type`, `allergy_info`, `vaccination_info`, `medical_history`) VALUES
(1, 1, '健康状况良好', 'O', '无', '已按计划完成', '无重大病史'),
(2, 2, '定期复查中', 'A', '花粉轻微过敏', '已按计划完成', '曾接受营养调理'),
(3, 3, '健康状况良好', 'B', '无', '已按计划完成', '无重大病史');

INSERT INTO `adoption_application` (`id`, `application_no`, `child_id`, `applicant_id`, `status`, `reason`, `expected_contact_date`, `review_remark`, `submit_time`, `review_time`) VALUES
(1, 'AP202603140001', 1, 3, 5, '希望为孩子提供稳定的学习与生活环境。', '2026-03-20', '材料通过，进入匹配阶段', '2026-03-14 09:30:00', '2026-03-14 16:10:00'),
(2, 'AP202603140002', 3, 4, 2, '家庭成员一致同意收养，并已做好长期规划。', '2026-03-22', '待材料初审', '2026-03-14 10:20:00', NULL);

INSERT INTO `application_material` (`id`, `application_id`, `material_type`, `material_name`, `file_url`, `review_status`, `remark`) VALUES
(1, 1, '身份证明', '身份证扫描件', 'https://example.com/material/idcard1.jpg', 1, '清晰可用'),
(2, 1, '收入证明', '收入证明', 'https://example.com/material/income1.jpg', 1, '材料完整'),
(3, 2, '身份证明', '身份证扫描件', 'https://example.com/material/idcard2.jpg', 0, '待审核');

INSERT INTO `home_visit_record` (`id`, `application_id`, `reviewer_id`, `visit_date`, `visit_result`, `visit_remark`) VALUES
(1, 1, 2, '2026-03-18', '通过', '居住环境整洁，家庭关系稳定。');

INSERT INTO `match_record` (`id`, `application_id`, `child_id`, `reviewer_id`, `match_score`, `status`, `match_remark`) VALUES
(1, 1, 1, 2, 92, 1, '综合条件与收养意愿匹配度较高。');

INSERT INTO `approval_record` (`id`, `application_id`, `node_name`, `approval_status`, `approval_remark`, `approver_id`) VALUES
(1, 1, '材料初审', 1, '材料齐全', 2),
(2, 1, '家访评估', 1, '评估通过', 2),
(3, 1, '匹配审批', 1, '同意进入签约', 1),
(4, 2, '材料初审', 0, '待处理', 2);

INSERT INTO `adoption_agreement` (`id`, `agreement_no`, `application_id`, `child_id`, `applicant_id`, `sign_status`, `sign_date`, `agreement_content`) VALUES
(1, 'AG202603140001', 1, 1, 3, 1, '2026-03-25', '双方确认完成收养意向与监护责任约定。');

INSERT INTO `follow_up_record` (`id`, `agreement_id`, `application_id`, `follow_date`, `follow_status`, `follow_remark`, `next_follow_date`, `reviewer_id`) VALUES
(1, 1, 1, '2026-04-25', '正常', '孩子适应情况良好，家庭反馈积极。', '2026-05-25', 2);

INSERT INTO `system_notice` (`id`, `title`, `type`, `content`, `status`, `publish_time`) VALUES
(1, '收养申请材料说明', '公告', '请申请人提前准备身份证明、收入证明、婚姻情况说明等材料。', 1, '2026-03-10 10:00:00'),
(2, '三月回访安排', '通知', '已签约家庭请关注本月回访时间安排，保持联系方式畅通。', 1, '2026-03-12 15:00:00');
