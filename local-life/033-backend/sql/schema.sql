CREATE DATABASE IF NOT EXISTS wedding_photo_studio DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE wedding_photo_studio;

CREATE TABLE `user` (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(200) NOT NULL COMMENT '密码',
    real_name VARCHAR(50) COMMENT '真实姓名',
    phone VARCHAR(20) COMMENT '手机号',
    email VARCHAR(100) COMMENT '邮箱',
    avatar VARCHAR(500) COMMENT '头像URL',
    role VARCHAR(20) NOT NULL DEFAULT 'CUSTOMER' COMMENT '角色',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT NOT NULL DEFAULT 0,
    INDEX idx_username (username),
    INDEX idx_phone (phone),
    INDEX idx_role (role)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE customer (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT COMMENT '关联用户ID',
    name VARCHAR(50) NOT NULL COMMENT '客户姓名',
    gender TINYINT COMMENT '性别',
    age INT COMMENT '年龄',
    phone VARCHAR(20) NOT NULL COMMENT '联系电话',
    email VARCHAR(100) COMMENT '邮箱',
    address VARCHAR(200) COMMENT '地址',
    customer_type VARCHAR(20) COMMENT '客户类型',
    source VARCHAR(50) COMMENT '客户来源',
    tags VARCHAR(500) COMMENT '标签',
    birthday DATE COMMENT '生日',
    remark TEXT COMMENT '备注',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT NOT NULL DEFAULT 0,
    INDEX idx_phone (phone),
    INDEX idx_customer_type (customer_type),
    INDEX idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE customer_follow_up (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    customer_id BIGINT NOT NULL COMMENT '客户ID',
    follow_user_id BIGINT NOT NULL COMMENT '跟进人ID',
    follow_type VARCHAR(20) COMMENT '跟进方式',
    content TEXT COMMENT '跟进内容',
    next_follow_time DATETIME COMMENT '下次跟进时间',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_customer_id (customer_id),
    INDEX idx_follow_user_id (follow_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE package (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL COMMENT '套餐名称',
    category VARCHAR(50) NOT NULL COMMENT '套餐分类',
    price DECIMAL(10,2) NOT NULL COMMENT '价格',
    description TEXT COMMENT '套餐描述',
    content TEXT COMMENT '套餐内容',
    costume_count INT COMMENT '服装数量',
    refined_photo_count INT COMMENT '精修照片数量',
    shooting_duration INT COMMENT '拍摄时长',
    makeup_count INT COMMENT '造型数量',
    scene_count INT COMMENT '场景数量',
    gift TEXT COMMENT '赠品',
    cover_image VARCHAR(500) COMMENT '封面图片',
    images TEXT COMMENT '图片列表',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态',
    sort_order INT DEFAULT 0 COMMENT '排序',
    sales_count INT DEFAULT 0 COMMENT '销量',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT NOT NULL DEFAULT 0,
    INDEX idx_category (category),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE photographer (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT COMMENT '关联用户ID',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    phone VARCHAR(20) COMMENT '手机号',
    photo VARCHAR(500) COMMENT '照片',
    level VARCHAR(20) COMMENT '等级',
    specialty TEXT COMMENT '擅长风格',
    portfolio TEXT COMMENT '作品集',
    introduction TEXT COMMENT '个人简介',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态',
    commission_rate DECIMAL(5,2) COMMENT '提成比例',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT NOT NULL DEFAULT 0,
    INDEX idx_user_id (user_id),
    INDEX idx_level (level)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE photographer_schedule (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    photographer_id BIGINT NOT NULL COMMENT '摄影师ID',
    work_date DATE NOT NULL COMMENT '工作日期',
    time_slot VARCHAR(20) NOT NULL COMMENT '时段',
    status VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE' COMMENT '状态',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_photographer_date (photographer_id, work_date),
    INDEX idx_work_date (work_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE studio (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL COMMENT '影棚名称',
    area DECIMAL(10,2) COMMENT '面积',
    style VARCHAR(50) COMMENT '风格',
    equipment TEXT COMMENT '设备',
    description TEXT COMMENT '描述',
    images TEXT COMMENT '图片',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE studio_booking (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    studio_id BIGINT NOT NULL COMMENT '影棚ID',
    booking_date DATE NOT NULL COMMENT '预约日期',
    time_slot VARCHAR(20) NOT NULL COMMENT '时段',
    status VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE' COMMENT '状态',
    order_id BIGINT COMMENT '订单ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_studio_date (studio_id, booking_date),
    INDEX idx_booking_date (booking_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE costume (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(50) NOT NULL UNIQUE COMMENT '服装编号',
    name VARCHAR(100) NOT NULL COMMENT '服装名称',
    category VARCHAR(50) COMMENT '类别',
    size VARCHAR(20) COMMENT '尺码',
    color VARCHAR(50) COMMENT '颜色',
    images TEXT COMMENT '图片',
    status VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE' COMMENT '状态',
    purchase_date DATE COMMENT '采购日期',
    purchase_price DECIMAL(10,2) COMMENT '采购价格',
    remark TEXT COMMENT '备注',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT NOT NULL DEFAULT 0,
    INDEX idx_code (code),
    INDEX idx_category (category),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE costume_borrow (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    costume_id BIGINT NOT NULL COMMENT '服装ID',
    order_id BIGINT NOT NULL COMMENT '订单ID',
    customer_id BIGINT NOT NULL COMMENT '客户ID',
    borrow_date DATETIME NOT NULL COMMENT '借出日期',
    return_date DATETIME COMMENT '归还日期',
    status VARCHAR(20) NOT NULL DEFAULT 'BORROWED' COMMENT '状态',
    remark TEXT COMMENT '备注',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_costume_id (costume_id),
    INDEX idx_order_id (order_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE prop (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(50) NOT NULL UNIQUE COMMENT '道具编号',
    name VARCHAR(100) NOT NULL COMMENT '道具名称',
    category VARCHAR(50) COMMENT '类别',
    quantity INT NOT NULL DEFAULT 1 COMMENT '数量',
    status VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE' COMMENT '状态',
    images TEXT COMMENT '图片',
    remark TEXT COMMENT '备注',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT NOT NULL DEFAULT 0,
    INDEX idx_code (code),
    INDEX idx_category (category)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE appointment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    customer_id BIGINT NOT NULL COMMENT '客户ID',
    appointment_type VARCHAR(20) NOT NULL COMMENT '预约类型',
    package_id BIGINT COMMENT '套餐ID',
    photographer_id BIGINT COMMENT '摄影师ID',
    studio_id BIGINT COMMENT '影棚ID',
    appointment_date DATE NOT NULL COMMENT '预约日期',
    time_slot VARCHAR(20) NOT NULL COMMENT '时段',
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING' COMMENT '状态',
    remark TEXT COMMENT '备注',
    created_by BIGINT COMMENT '创建人ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_customer_id (customer_id),
    INDEX idx_appointment_date (appointment_date),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `order` (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_no VARCHAR(50) NOT NULL UNIQUE COMMENT '订单号',
    customer_id BIGINT NOT NULL COMMENT '客户ID',
    package_id BIGINT NOT NULL COMMENT '套餐ID',
    photographer_id BIGINT COMMENT '摄影师ID',
    appointment_id BIGINT COMMENT '预约ID',
    total_amount DECIMAL(10,2) NOT NULL COMMENT '总金额',
    deposit_amount DECIMAL(10,2) COMMENT '定金',
    balance_amount DECIMAL(10,2) COMMENT '尾款',
    paid_amount DECIMAL(10,2) NOT NULL DEFAULT 0 COMMENT '已付金额',
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING' COMMENT '订单状态',
    payment_method VARCHAR(20) COMMENT '支付方式',
    shooting_date DATE COMMENT '拍摄日期',
    photo_selection_date DATE COMMENT '选片日期',
    pickup_date DATE COMMENT '取片日期',
    remark TEXT COMMENT '备注',
    created_by BIGINT COMMENT '创建人ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT NOT NULL DEFAULT 0,
    INDEX idx_order_no (order_no),
    INDEX idx_customer_id (customer_id),
    INDEX idx_photographer_id (photographer_id),
    INDEX idx_status (status),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE photo (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL COMMENT '订单ID',
    customer_id BIGINT NOT NULL COMMENT '客户ID',
    photographer_id BIGINT COMMENT '摄影师ID',
    file_name VARCHAR(200) NOT NULL COMMENT '文件名',
    file_path VARCHAR(500) NOT NULL COMMENT '文件路径',
    file_size BIGINT COMMENT '文件大小',
    file_type VARCHAR(50) COMMENT '文件类型',
    is_selected TINYINT NOT NULL DEFAULT 0 COMMENT '是否选片',
    is_refined TINYINT NOT NULL DEFAULT 0 COMMENT '是否精修',
    refined_status VARCHAR(20) COMMENT '精修状态',
    upload_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上传时间',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT NOT NULL DEFAULT 0,
    INDEX idx_order_id (order_id),
    INDEX idx_customer_id (customer_id),
    INDEX idx_photographer_id (photographer_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE photo_selection (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    photo_id BIGINT NOT NULL COMMENT '照片ID',
    order_id BIGINT NOT NULL COMMENT '订单ID',
    customer_id BIGINT NOT NULL COMMENT '客户ID',
    selected_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '选片时间',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_photo_id (photo_id),
    INDEX idx_order_id (order_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE financial_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    record_type VARCHAR(20) NOT NULL COMMENT '记录类型',
    order_id BIGINT COMMENT '订单ID',
    amount DECIMAL(10,2) NOT NULL COMMENT '金额',
    payment_method VARCHAR(20) COMMENT '支付方式',
    category VARCHAR(50) COMMENT '分类',
    description TEXT COMMENT '描述',
    record_date DATE NOT NULL COMMENT '记录日期',
    created_by BIGINT COMMENT '创建人ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT NOT NULL DEFAULT 0,
    INDEX idx_record_type (record_type),
    INDEX idx_order_id (order_id),
    INDEX idx_record_date (record_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE operation_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT COMMENT '操作用户ID',
    username VARCHAR(50) COMMENT '用户名',
    operation VARCHAR(100) COMMENT '操作',
    method VARCHAR(200) COMMENT '方法',
    params TEXT COMMENT '参数',
    ip VARCHAR(50) COMMENT 'IP地址',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_user_id (user_id),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE system_config (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    config_key VARCHAR(100) NOT NULL UNIQUE COMMENT '配置键',
    config_value TEXT COMMENT '配置值',
    config_type VARCHAR(20) COMMENT '配置类型',
    description VARCHAR(200) COMMENT '描述',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_config_key (config_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
