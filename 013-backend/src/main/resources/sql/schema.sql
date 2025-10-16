CREATE DATABASE IF NOT EXISTS campus_share CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE campus_share;

CREATE TABLE `user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `username` VARCHAR(50) NOT NULL COMMENT '用户名',
    `password` VARCHAR(100) NOT NULL COMMENT '密码',
    `nickname` VARCHAR(50) NOT NULL COMMENT '昵称',
    `avatar` VARCHAR(200) DEFAULT NULL COMMENT '头像URL',
    `phone` VARCHAR(20) NOT NULL COMMENT '手机号',
    `student_id` VARCHAR(30) DEFAULT NULL COMMENT '学号',
    `school` VARCHAR(50) DEFAULT NULL COMMENT '学校',
    `college` VARCHAR(50) DEFAULT NULL COMMENT '学院',
    `real_name` VARCHAR(30) DEFAULT NULL COMMENT '真实姓名',
    `id_card` VARCHAR(20) DEFAULT NULL COMMENT '身份证号',
    `auth_status` TINYINT DEFAULT 0 COMMENT '认证状态：0未认证 1审核中 2已认证 3未通过',
    `credit_score` INT DEFAULT 100 COMMENT '信用分',
    `deposit_balance` DECIMAL(10,2) DEFAULT 0.00 COMMENT '押金余额',
    `account_balance` DECIMAL(10,2) DEFAULT 0.00 COMMENT '账户余额',
    `total_orders` INT DEFAULT 0 COMMENT '订单总数',
    `total_income` DECIMAL(10,2) DEFAULT 0.00 COMMENT '累计收益',
    `role` VARCHAR(20) DEFAULT 'USER' COMMENT '角色：USER/PROVIDER/OPERATOR/ADMIN',
    `status` TINYINT DEFAULT 1 COMMENT '状态：0禁用 1正常',
    `last_login_time` DATETIME DEFAULT NULL COMMENT '最后登录时间',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`),
    UNIQUE KEY `uk_phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

CREATE TABLE `user_auth` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `real_name` VARCHAR(30) NOT NULL COMMENT '真实姓名',
    `id_card` VARCHAR(20) NOT NULL COMMENT '身份证号',
    `student_card_img` VARCHAR(200) DEFAULT NULL COMMENT '学生证照片',
    `face_img` VARCHAR(200) DEFAULT NULL COMMENT '人脸照片',
    `auth_status` TINYINT DEFAULT 0 COMMENT '认证状态：0待审核 1通过 2拒绝',
    `reject_reason` VARCHAR(200) DEFAULT NULL COMMENT '拒绝原因',
    `audit_admin_id` BIGINT DEFAULT NULL COMMENT '审核管理员ID',
    `audit_time` DATETIME DEFAULT NULL COMMENT '审核时间',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='实名认证表';

CREATE TABLE `credit_log` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `change_type` VARCHAR(20) NOT NULL COMMENT '变动类型',
    `change_score` INT NOT NULL COMMENT '变动分数',
    `before_score` INT NOT NULL COMMENT '变动前分数',
    `after_score` INT NOT NULL COMMENT '变动后分数',
    `reason` VARCHAR(200) DEFAULT NULL COMMENT '原因',
    `related_order_id` BIGINT DEFAULT NULL COMMENT '关联订单ID',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='信用记录表';

CREATE TABLE `shared_item` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `item_no` VARCHAR(50) NOT NULL COMMENT '物品编号',
    `item_type` VARCHAR(20) NOT NULL COMMENT '物品类型：BIKE/CHARGER/UMBRELLA',
    `item_model` VARCHAR(50) DEFAULT NULL COMMENT '型号',
    `latitude` DECIMAL(10,6) DEFAULT NULL COMMENT '纬度',
    `longitude` DECIMAL(10,6) DEFAULT NULL COMMENT '经度',
    `location_name` VARCHAR(100) DEFAULT NULL COMMENT '位置名称',
    `battery_level` INT DEFAULT NULL COMMENT '电量百分比',
    `status` TINYINT DEFAULT 0 COMMENT '状态：0可用 1使用中 2维护中 3故障',
    `hourly_price` DECIMAL(10,2) NOT NULL COMMENT '小时租金',
    `daily_max_price` DECIMAL(10,2) DEFAULT NULL COMMENT '每日封顶价格',
    `deposit_amount` DECIMAL(10,2) DEFAULT 0.00 COMMENT '押金金额',
    `total_usage_count` INT DEFAULT 0 COMMENT '累计使用次数',
    `total_distance` DECIMAL(10,2) DEFAULT 0.00 COMMENT '累计距离',
    `last_maintain_time` DATETIME DEFAULT NULL COMMENT '最后维护时间',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_item_no` (`item_no`),
    KEY `idx_item_type` (`item_type`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='共享物品表';

CREATE TABLE `idle_item` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` BIGINT NOT NULL COMMENT '发布者ID',
    `category` VARCHAR(20) NOT NULL COMMENT '分类',
    `title` VARCHAR(100) NOT NULL COMMENT '标题',
    `description` TEXT COMMENT '描述',
    `images` TEXT COMMENT '图片URL（JSON数组）',
    `condition_level` TINYINT DEFAULT NULL COMMENT '新旧程度：1全新 2九九新 3九五新 4九成新 5八成以下',
    `original_price` DECIMAL(10,2) DEFAULT NULL COMMENT '原价',
    `hourly_price` DECIMAL(10,2) DEFAULT NULL COMMENT '小时租金',
    `daily_price` DECIMAL(10,2) DEFAULT NULL COMMENT '日租金',
    `weekly_price` DECIMAL(10,2) DEFAULT NULL COMMENT '周租金',
    `deposit_amount` DECIMAL(10,2) DEFAULT 0.00 COMMENT '押金',
    `available_time` TEXT COMMENT '可租时间段（JSON）',
    `pickup_method` TINYINT DEFAULT NULL COMMENT '取货方式：0上门 1自取 2均可',
    `pickup_address` VARCHAR(200) DEFAULT NULL COMMENT '取货地址',
    `latitude` DECIMAL(10,6) DEFAULT NULL COMMENT '纬度',
    `longitude` DECIMAL(10,6) DEFAULT NULL COMMENT '经度',
    `contact_phone` VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
    `view_count` INT DEFAULT 0 COMMENT '浏览次数',
    `order_count` INT DEFAULT 0 COMMENT '订单次数',
    `rating` DECIMAL(3,2) DEFAULT 5.00 COMMENT '评分',
    `status` TINYINT DEFAULT 0 COMMENT '状态：0待审核 1已上架 2已下架 3审核拒绝',
    `audit_reason` VARCHAR(200) DEFAULT NULL COMMENT '审核原因',
    `is_deleted` TINYINT DEFAULT 0 COMMENT '是否删除：0否 1是',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_category` (`category`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='闲置物品表';

CREATE TABLE `skill_service` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` BIGINT NOT NULL COMMENT '服务者ID',
    `category` VARCHAR(20) NOT NULL COMMENT '分类',
    `title` VARCHAR(100) NOT NULL COMMENT '标题',
    `description` TEXT COMMENT '描述',
    `images` TEXT COMMENT '展示图片（JSON数组）',
    `portfolio` TEXT COMMENT '作品集（JSON数组）',
    `service_duration` INT DEFAULT NULL COMMENT '服务时长（分钟）',
    `hourly_price` DECIMAL(10,2) NOT NULL COMMENT '小时价格',
    `available_time` TEXT COMMENT '可预约时间（JSON）',
    `service_location` VARCHAR(200) DEFAULT NULL COMMENT '服务地点',
    `location_type` TINYINT DEFAULT NULL COMMENT '地点类型：0上门 1指定地点 2线上',
    `latitude` DECIMAL(10,6) DEFAULT NULL COMMENT '纬度',
    `longitude` DECIMAL(10,6) DEFAULT NULL COMMENT '经度',
    `introduction` TEXT COMMENT '个人简介',
    `certificate` TEXT COMMENT '资质证明（JSON数组）',
    `view_count` INT DEFAULT 0 COMMENT '浏览次数',
    `order_count` INT DEFAULT 0 COMMENT '成交次数',
    `rating` DECIMAL(3,2) DEFAULT 5.00 COMMENT '评分',
    `status` TINYINT DEFAULT 1 COMMENT '状态：0下架 1上架',
    `is_deleted` TINYINT DEFAULT 0 COMMENT '是否删除：0否 1是',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_category` (`category`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='技能服务表';

CREATE TABLE `order_info` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `order_no` VARCHAR(50) NOT NULL COMMENT '订单编号',
    `order_type` VARCHAR(20) NOT NULL COMMENT '订单类型：SHARED/IDLE/SKILL',
    `user_id` BIGINT NOT NULL COMMENT '租用者/购买者ID',
    `provider_id` BIGINT DEFAULT NULL COMMENT '服务提供者ID',
    `item_id` BIGINT NOT NULL COMMENT '物品/服务ID',
    `item_title` VARCHAR(100) DEFAULT NULL COMMENT '物品/服务标题',
    `start_time` DATETIME DEFAULT NULL COMMENT '开始时间',
    `end_time` DATETIME DEFAULT NULL COMMENT '结束时间',
    `planned_duration` INT DEFAULT NULL COMMENT '计划时长（分钟）',
    `actual_duration` INT DEFAULT NULL COMMENT '实际时长（分钟）',
    `rental_fee` DECIMAL(10,2) DEFAULT 0.00 COMMENT '租金/服务费',
    `deposit_amount` DECIMAL(10,2) DEFAULT 0.00 COMMENT '押金',
    `platform_fee` DECIMAL(10,2) DEFAULT 0.00 COMMENT '平台服务费',
    `total_amount` DECIMAL(10,2) DEFAULT 0.00 COMMENT '总金额',
    `paid_amount` DECIMAL(10,2) DEFAULT 0.00 COMMENT '已支付金额',
    `deposit_status` TINYINT DEFAULT 0 COMMENT '押金状态：0未支付 1已冻结 2已退还 3已扣除',
    `payment_status` TINYINT DEFAULT 0 COMMENT '支付状态：0待支付 1已支付 2已退款',
    `order_status` TINYINT DEFAULT 0 COMMENT '订单状态：0待支付 1进行中 2待归还 3待评价 4已完成 5已取消 6异常',
    `cancel_reason` VARCHAR(200) DEFAULT NULL COMMENT '取消原因',
    `is_overtime` TINYINT DEFAULT 0 COMMENT '是否超时：0否 1是',
    `overtime_fee` DECIMAL(10,2) DEFAULT 0.00 COMMENT '超时费用',
    `is_damaged` TINYINT DEFAULT 0 COMMENT '是否损坏：0否 1是',
    `damage_fee` DECIMAL(10,2) DEFAULT 0.00 COMMENT '损坏赔偿',
    `pickup_address` VARCHAR(200) DEFAULT NULL COMMENT '取货地址',
    `return_address` VARCHAR(200) DEFAULT NULL COMMENT '归还地址',
    `requirement` TEXT COMMENT '需求描述（技能订单）',
    `payment_time` DATETIME DEFAULT NULL COMMENT '支付时间',
    `complete_time` DATETIME DEFAULT NULL COMMENT '完成时间',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_order_no` (`order_no`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_provider_id` (`provider_id`),
    KEY `idx_order_type` (`order_type`),
    KEY `idx_order_status` (`order_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

CREATE TABLE `review` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `order_id` BIGINT NOT NULL COMMENT '订单ID',
    `reviewer_id` BIGINT NOT NULL COMMENT '评价者ID',
    `reviewed_id` BIGINT NOT NULL COMMENT '被评价者ID',
    `review_type` VARCHAR(20) NOT NULL COMMENT '评价类型：ITEM/SERVICE/USER',
    `rating` TINYINT NOT NULL COMMENT '评分（1-5）',
    `content` TEXT COMMENT '评价内容',
    `images` TEXT COMMENT '图片（JSON数组）',
    `tags` VARCHAR(200) DEFAULT NULL COMMENT '标签（逗号分隔）',
    `is_anonymous` TINYINT DEFAULT 0 COMMENT '是否匿名：0否 1是',
    `additional_content` TEXT COMMENT '追加评价',
    `additional_time` DATETIME DEFAULT NULL COMMENT '追加时间',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_order_id` (`order_id`),
    KEY `idx_reviewer_id` (`reviewer_id`),
    KEY `idx_reviewed_id` (`reviewed_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评价表';

CREATE TABLE `withdrawal` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `withdrawal_no` VARCHAR(50) NOT NULL COMMENT '提现单号',
    `amount` DECIMAL(10,2) NOT NULL COMMENT '提现金额',
    `fee` DECIMAL(10,2) DEFAULT 0.00 COMMENT '手续费',
    `actual_amount` DECIMAL(10,2) NOT NULL COMMENT '实际到账金额',
    `withdraw_type` VARCHAR(20) NOT NULL COMMENT '提现方式：ALIPAY/WECHAT/BANK',
    `account_name` VARCHAR(50) NOT NULL COMMENT '账户名',
    `account_no` VARCHAR(100) NOT NULL COMMENT '账户号',
    `status` TINYINT DEFAULT 0 COMMENT '状态：0待审核 1审核通过 2已打款 3审核拒绝',
    `reject_reason` VARCHAR(200) DEFAULT NULL COMMENT '拒绝原因',
    `audit_admin_id` BIGINT DEFAULT NULL COMMENT '审核管理员ID',
    `audit_time` DATETIME DEFAULT NULL COMMENT '审核时间',
    `transfer_time` DATETIME DEFAULT NULL COMMENT '打款时间',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_withdrawal_no` (`withdrawal_no`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收益提现表';

CREATE TABLE `payment_record` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `order_id` BIGINT DEFAULT NULL COMMENT '订单ID',
    `payment_no` VARCHAR(50) NOT NULL COMMENT '支付单号',
    `payment_type` VARCHAR(20) NOT NULL COMMENT '支付类型：DEPOSIT/RENTAL/SERVICE',
    `payment_method` VARCHAR(20) NOT NULL COMMENT '支付方式：ALIPAY/WECHAT/BALANCE/DEPOSIT',
    `amount` DECIMAL(10,2) NOT NULL COMMENT '金额',
    `status` TINYINT DEFAULT 0 COMMENT '状态：0待支付 1已支付 2已退款',
    `pay_time` DATETIME DEFAULT NULL COMMENT '支付时间',
    `refund_time` DATETIME DEFAULT NULL COMMENT '退款时间',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_payment_no` (`payment_no`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付记录表';

CREATE TABLE `deposit_record` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `order_id` BIGINT DEFAULT NULL COMMENT '订单ID',
    `record_type` VARCHAR(20) NOT NULL COMMENT '记录类型：CHARGE/FREEZE/UNFREEZE/DEDUCT/REFUND',
    `amount` DECIMAL(10,2) NOT NULL COMMENT '金额',
    `before_balance` DECIMAL(10,2) NOT NULL COMMENT '变动前余额',
    `after_balance` DECIMAL(10,2) NOT NULL COMMENT '变动后余额',
    `reason` VARCHAR(200) DEFAULT NULL COMMENT '原因',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='押金记录表';

CREATE TABLE `balance_record` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `record_type` VARCHAR(20) NOT NULL COMMENT '记录类型：CHARGE/PAY/REFUND/INCOME/WITHDRAW',
    `amount` DECIMAL(10,2) NOT NULL COMMENT '金额',
    `before_balance` DECIMAL(10,2) NOT NULL COMMENT '变动前余额',
    `after_balance` DECIMAL(10,2) NOT NULL COMMENT '变动后余额',
    `related_order_id` BIGINT DEFAULT NULL COMMENT '关联订单ID',
    `remark` VARCHAR(200) DEFAULT NULL COMMENT '备注',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='余额记录表';

CREATE TABLE `notification` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` BIGINT NOT NULL COMMENT '接收用户ID',
    `type` VARCHAR(20) NOT NULL COMMENT '类型：ORDER/PAYMENT/AUDIT/APPOINTMENT/SYSTEM',
    `title` VARCHAR(100) NOT NULL COMMENT '标题',
    `content` VARCHAR(500) DEFAULT NULL COMMENT '内容',
    `related_id` BIGINT DEFAULT NULL COMMENT '关联ID',
    `related_type` VARCHAR(20) DEFAULT NULL COMMENT '关联类型',
    `is_read` TINYINT DEFAULT 0 COMMENT '是否已读：0未读 1已读',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_is_read` (`is_read`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息通知表';

CREATE TABLE `location_track` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `order_id` BIGINT NOT NULL COMMENT '订单ID',
    `item_id` BIGINT DEFAULT NULL COMMENT '物品ID',
    `latitude` DECIMAL(10,6) NOT NULL COMMENT '纬度',
    `longitude` DECIMAL(10,6) NOT NULL COMMENT '经度',
    `speed` DECIMAL(10,2) DEFAULT NULL COMMENT '速度（km/h）',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='位置轨迹表';

CREATE TABLE `complaint` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `order_id` BIGINT NOT NULL COMMENT '订单ID',
    `complainant_id` BIGINT NOT NULL COMMENT '投诉人ID',
    `respondent_id` BIGINT NOT NULL COMMENT '被投诉人ID',
    `complaint_type` VARCHAR(20) NOT NULL COMMENT '投诉类型：DAMAGE/FRAUD/ATTITUDE/OTHER',
    `description` TEXT COMMENT '投诉描述',
    `images` TEXT COMMENT '证据图片（JSON数组）',
    `status` TINYINT DEFAULT 0 COMMENT '状态：0待处理 1处理中 2已解决 3已驳回',
    `handle_result` TEXT COMMENT '处理结果',
    `handle_admin_id` BIGINT DEFAULT NULL COMMENT '处理管理员ID',
    `handle_time` DATETIME DEFAULT NULL COMMENT '处理时间',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_order_id` (`order_id`),
    KEY `idx_complainant_id` (`complainant_id`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='投诉举报表';

CREATE TABLE `favorite` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `target_id` BIGINT NOT NULL COMMENT '目标ID',
    `target_type` VARCHAR(20) NOT NULL COMMENT '目标类型：IDLE/SKILL',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_target` (`target_id`, `target_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收藏表';

