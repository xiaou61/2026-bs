# 校园自习室座位预约系统 PRD

## 📋 项目概述

### 项目名称
校园自习室座位预约管理系统

### 项目背景
- **痛点分析**：校园图书馆、自习室占座现象严重，座位资源利用不合理
- **市场需求**：学生需要高效、公平的座位分配机制
- **技术机遇**：结合移动互联网、二维码技术实现智能化管理

### 项目目标
- 提高座位资源利用率，解决占座难题
- 提供公平、透明的预约机制
- 培养学生良好的自习习惯
- 为管理员提供数据统计和决策支持

---

## 🎯 功能需求

### 核心功能模块

#### 1. 用户管理模块
**功能描述**：学生账号管理、权限控制

**详细功能**：
- 用户注册/登录（学号验证）
- 个人信息管理（姓名、院系、年级）
- 密码修改、忘记密码
- 用户状态管理（正常/禁用）

**业务规则**：
- 仅限在校学生注册
- 学号唯一性验证
- 初始信用分100分

#### 2. 自习室管理模块
**功能描述**：自习室和座位信息维护

**详细功能**：
- 自习室信息管理���名称、楼层、容纳人数）
- 座位信息维护（座位编号、类型、状态）
- 座位分类（普通座位、电源座位、静音区等）
- 座位状态实时更新（空闲、占用、维修）

**业务规则**：
- 每个自习室可设置不同开放时间
- 座位类型支持多样化配置
- 座位状态支持批量更新

#### 3. 预约管理模块
**功能描述**：座位预约、签到、释放流程

**详细功能**：
- 座位查询与筛选（按楼层、区域、类型）
- 在线预约座位（选择时间段）
- 预约记录管理（查看、取消）
- 二维码签到核销
- 自动释放机制

**业务规则**：
- 提前最多2天预约，最少提前1小时
- 单次预约最长4小时
- 预约后15分钟内签到有效
- 超时未签到自动释放并扣信用分

#### 4. 信用体系模块
**功能描述**：用户信用分管理和约束机制

**详细功能**：
- 信用分初始设置（100分）
- 信���分变动记录（增加/减少原因）
- 信用等级划分（优秀/良好/一般/受限）
- 约束机制（低信用分限制预约）

**业务规则**：
- 正常签到+2分，提前释放+1分
- 违约扣分：超时未到-5分，提前离开-2分
- 信用分<60分时，每天只能预约1次
- 信用分<30分时，暂停预约权限3天

---

## 🗄️ 数据库设计

### 表结构设计

#### 1. 用户表 (user)
```sql
CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL COMMENT '学号',
    password VARCHAR(255) NOT NULL COMMENT '密码',
    real_name VARCHAR(50) NOT NULL COMMENT '真实姓名',
    department VARCHAR(100) COMMENT '院系',
    grade VARCHAR(20) COMMENT '年级',
    phone VARCHAR(20) COMMENT '手机号',
    credit_score INT DEFAULT 100 COMMENT '信用分数',
    status TINYINT DEFAULT 1 COMMENT '状态：1-正常，0-禁用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

#### 2. 自习室表 (study_room)
```sql
CREATE TABLE study_room (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    room_name VARCHAR(100) NOT NULL COMMENT '自习室名称',
    floor_number INT NOT NULL COMMENT '楼层',
    capacity INT NOT NULL COMMENT '容纳人数',
    open_time TIME NOT NULL COMMENT '开放时间',
    close_time TIME NOT NULL COMMENT '关闭时间',
    description TEXT COMMENT '描述信息',
    status TINYINT DEFAULT 1 COMMENT '状态：1-开放，0-关闭'
);
```

#### 3. 座位表 (seat)
```sql
CREATE TABLE seat (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    room_id BIGINT NOT NULL COMMENT '自习室ID',
    seat_number VARCHAR(20) NOT NULL COMMENT '座位编号',
    seat_type TINYINT DEFAULT 1 COMMENT '座位类型：1-普通，2-电源，3-静音区',
    seat_status TINYINT DEFAULT 1 COMMENT '座位状态：1-空闲，2-占用，3-维修',
    x_coordinate DECIMAL(10,2) COMMENT 'X坐标',
    y_coordinate DECIMAL(10,2) COMMENT 'Y坐标',
    FOREIGN KEY (room_id) REFERENCES study_room(id)
);
```

#### 4. 预约记录表 (reservation)
```sql
CREATE TABLE reservation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    seat_id BIGINT NOT NULL COMMENT '座位ID',
    reservation_time DATETIME NOT NULL COMMENT '预约时间',
    start_time DATETIME NOT NULL COMMENT '使用开始时间',
    end_time DATETIME NOT NULL COMMENT '使用结束时间',
    check_in_time DATETIME COMMENT '实际签到时间',
    actual_end_time DATETIME COMMENT '实际结束时间',
    status TINYINT DEFAULT 1 COMMENT '状态：1-已预约，2-已签到，3-已完成，4-已取消，5-违约',
    qrcode_content VARCHAR(255) COMMENT '二维码内容',
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (seat_id) REFERENCES seat(id)
);
```

#### 5. 信用记录表 (credit_record)
```sql
CREATE TABLE credit_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    score_change INT NOT NULL COMMENT '分数变动',
    change_reason VARCHAR(100) NOT NULL COMMENT '变动原因',
    related_type VARCHAR(50) COMMENT '关联类型：reservation/check_in等',
    related_id BIGINT COMMENT '关联ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id)
);
```

#### 6. 使用统计表 (usage_statistics)
```sql
CREATE TABLE usage_statistics (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    room_id BIGINT NOT NULL COMMENT '自习室ID',
    stat_date DATE NOT NULL COMMENT '统计日期',
    total_reservations INT DEFAULT 0 COMMENT '总预约数',
    actual_checkins INT DEFAULT 0 COMMENT '实际签到数',
    peak_hour TIME COMMENT '高峰时段',
    usage_rate DECIMAL(5,2) DEFAULT 0 COMMENT '使用率',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (room_id) REFERENCES study_room(id)
);
```

#### 7. 系统配置表 (system_config)
```sql
CREATE TABLE system_config (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    config_key VARCHAR(100) UNIQUE NOT NULL COMMENT '配置键',
    config_value TEXT NOT NULL COMMENT '配置值',
    description VARCHAR(255) COMMENT '配置描述',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

---

## 🎨 界面需求

### 学生端界面
1. **登录页面** - 学号密码登录
2. **首页** - 座位概览、快速预约入口
3. **座位选择页** - 楼层选择、座位平面图展示
4. **预约详情页** - 预约信息、二维码展示
5. **个人中心** - 预约记录、信用分查看
6. **座位状态页** - 实时座位占用情况

### 管理端界面
1. **仪表盘** - 数据统计图表、关键指标
2. **自习室管理** - 自习室信息维护
3. **座位管理** - 座位信息批量操作
4. **预约监控** - 实时预约状态、异常处理
5. **用户管理** - 用户信息、信用分调整
6. **数据报表** - 使用率统计、趋势分析

---

## 🔧 技术架构

### 后端技术栈
- **框架**：Spring Boot 3.2.0
- **数据库**：MySQL 8.0
- **ORM**：MyBatis-Plus 3.5.5
- **认证**：JWT 0.12.3
- **缓存**：Redis（可选）
- **定时任务**：Spring Task
- **工具库**：Hutool

### 前端技术栈
- **框架**：Vue 3.4.0
- **UI组件**：Element Plus 2.5.1
- **状态管理**：Pinia 2.1.7
- **路由**：Vue Router 4.2.5
- **HTTP客户端**：Axios 1.6.2
- **构建工具**：Vite 5.0.8

---

## ⚙️ 系统规则

### 预约规则
1. **时间限制**：可提前2天预约，最少提前1小时
2. **时长限制**：单次预约最长4小时，每天最多8小时
3. **数量限制**：同时只能有一个有效预约
4. **签到规则**：预约开始后15分钟内签到有效

### 信用分规则
1. **初始分数**：新生注册100分
2. **加分规则**：
   - 正常签到使用完毕 +2分
   - 提前30分钟以上结束 +1分
3. **扣分规则**：
   - 预约后未签到 -5分
   - 提前离开（少于预约时长50%） -2分
   - 违规行为举报核实 -10分

### 自动化规则
1. **超时释放**：预约开始15分钟后未签到自动释放
2. **强制结束**：预约时间结束后自动标记为完成
3. **信用恢复**：每完成10次正常预约，信用分+5分（上限120分）

---

## 📊 数据统计需求

### 实时数据
- 当前各楼层座位占用率
- 实时预约人数
- 在线用户数

### 统计报表
1. **日统计**：每日预约数、签到率、违约率
2. **周统计**：高峰时段分析、热门座位排行
3. **月统计**：使用趋势分析、用户活跃度
4. **年度统计**：年度总结报告、资源利用率

---

## 🔒 安全需求

### 数据安全
- 用户密码MD5加密存储
- 敏感操作日志记录
- 定期数据备份

### 业务安全
- 防止恶意预约（同一设备限制）
- 二维码有效期控制
- 异常行为监控

---

## 📱 移动端适配

### 响应式设计
- 支持手机、平板、PC端访问
- 自适应布局调整
- 触控友好的交互设计

### 移动端特性
- 扫码签到功能
- 推送通知（预约提醒）
- 离线模式支持

---

## 🚀 项目里程碑

### 第一阶段（2周）
- 基础框架搭建
- 用户管理模块
- 自习室管理模块

### 第二阶段（2周）
- 预约管理模块
- 二维码生成与验证
- 基础前端页面

### 第三阶段（1周）
- 信用体系模块
- 数据统计功能
- 系统优化测试

---

## 📋 验收标准

### 功能验收
- 所有核心功能正常运行
- 用户体验流畅
- 数据准确性100%

### 性能验收
- 页面加载时间<2秒
- 并发用户数>1000
- 系统可用性>99.9%

### 安全验收
- 通过安全测试
- 无明显安全漏洞
- 数据保护完善

---

**文档版本**：v1.0
**创建日期**：2024-11-16
**更新日期**：2024-11-16
**文档状态**：待审核