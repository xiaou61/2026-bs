# 图书馆座位预约系统 PRD

## 一、项目概述

### 1.1 项目背景
随着高校学生数量增加，图书馆座位资源日益紧张。学生抢座难、占座不坐、超时占用等问题突出，导致座位利用率低下。本系统通过在线预约、扫码签到、超时释放、违约处理等机制，提高座位利用率，优化资源配置。

### 1.2 技术栈
- 后端：SpringBoot 3.x + MyBatis-Plus + MySQL 8.0
- 前端：jQuery + Bootstrap 5 + Layer + ECharts
- 前后端一体化部署

### 1.3 核心功能
- 座位管理（楼层、区域、座位信息）
- 在线预约（选座、时段预约）
- 签到签退（扫码签到、自动签退）
- 超时释放（超时未签到自动释放）
- 违约管理（违约记录、信用分扣除、禁用处理）
- 数据统计（座位热力图、使用率分析）

## 二、用户角色

### 2.1 学生用户
- 浏览可用座位
- 预约座位
- 签到签退
- 查看预约记录
- 查看个人信用分

### 2.2 管理员
- 座位管理（楼层、区域、座位CRUD）
- 预约管理（查看所有预约）
- 违约处理（查看违约记录、人工处理）
- 用户管理（查看用户、禁用用户、重置信用分）
- 系统配置（预约规则、违约规则、信用分规则）
- 数据统计（座位使用率、热门座位、违约统计）

## 三、功能模块详细设计

### 3.1 用户模块

#### 3.1.1 用户注册
- 学号（唯一，作为用户名）
- 密码
- 姓名
- 性别
- 学院
- 专业
- 联系电话

#### 3.1.2 用户登录
- 学号密码登录
- Token鉴权

#### 3.1.3 个人信息管理
- 查看个人信息
- 修改密码
- 查看信用分
- 查看违约记录

#### 3.1.4 信用分系统
- 初始信用分：100分
- 正常使用：每次预约并正常签到签退 +2分（上限100分）
- 违约扣分规则：
  - 预约后未签到：-5分
  - 签到后未签退：-3分
  - 提前离开超过30分钟未签退：-3分
- 信用分惩罚：
  - 80-100分：正常预约
  - 60-79分：每天限制预约1次
  - 40-59分：每天限制预约1次，且不能预约热门座位
  - 0-39分：禁止预约3天
  - 负分：禁止预约7天

### 3.2 座位管理模块

#### 3.2.1 楼层管理
- 楼层名称（如：一楼、二楼）
- 楼层描述
- 排序

#### 3.2.2 区域管理
- 区域名称（如：自习区、考研区、安静区）
- 所属楼层
- 区域描述
- 是否需要更高信用分（热门区域）
- 排序

#### 3.2.3 座位管理
- 座位编号（如：A-101、B-205）
- 所属区域
- 座位类型（单人座、双人座、靠窗、靠墙）
- 是否有电源插座
- 是否有台灯
- 座位状态（可用、维修中、已禁用）
- 热门度（根据预约次数自动计算）

### 3.3 预约模块

#### 3.3.1 预约规则
- 预约时段：
  - 上午：08:00-12:00
  - 下午：14:00-18:00
  - 晚上：18:30-22:00
  - 全天：08:00-22:00
- 只能预约当天和未来2天的座位
- 同一时段只能预约1个座位
- 每天最多预约3个时段（信用分60分以下限制1个时段）
- 预约后15分钟内必须签到，否则自动释放并记录违约

#### 3.3.2 预约流程
1. 选择日期
2. 选择时段
3. 选择楼层和区域
4. 查看座位地图（可视化座位布局）
5. 选择座位（显示座位详情、当前状态）
6. 确认预约
7. 生成预约记录（含签到二维码）

#### 3.3.3 预约状态
- 待签到：预约成功，等待签到
- 使用中：已签到，正在使用
- 已完成：已签退，正常结束
- 已取消：用户主动取消
- 已释放：超时未签到自动释放
- 违约：未签到或未签退

#### 3.3.4 取消预约
- 只能取消"待签到"状态的预约
- 提前30分钟以上取消：不扣信用分
- 提前30分钟以内取消：扣2分
- 预约开始后不能取消

### 3.4 签到签退模块

#### 3.4.1 签到功能
- 扫描座位二维码签到（座位编号）
- 签到时间限制：预约开始时间前15分钟 至 预约开始时间后15分钟
- 签到成功后座位状态变为"使用中"
- 超时未签到自动释放座位并记录违约

#### 3.4.2 签退功能
- 扫描座位二维码签退
- 手动签退
- 签退时间：预约结束时间前后均可
- 提前签退：正常结束
- 超时未签退：预约结束后自动签退（超过30分钟记录违约）

#### 3.4.3 临时离开
- 离开时标记"临时离开"
- 临时离开不超过30分钟：座位保留
- 超过30分钟：自动签退并扣信用分

### 3.5 违约管理模块

#### 3.5.1 违约类型
- 未签到违约：预约后超过15分钟未签到
- 未签退违约：预约结束后超过30分钟未签退
- 临时离开超时：标记离开后超过30分钟未返回

#### 3.5.2 违约记录
- 用户信息
- 预约信息
- 违约类型
- 违约时间
- 扣除信用分
- 处理状态（系统自动/人工处理）
- 申诉状态

#### 3.5.3 违约申诉
- 学生可对违约记录提交申诉
- 申诉理由（文字+图片）
- 管理员审核申诉
- 审核通过：恢复信用分
- 审核不通过：维持原处罚

### 3.6 数据统计模块

#### 3.6.1 座位使用统计
- 座位总数
- 今日预约数
- 当前使用中
- 今日签到率
- 今日违约率
- 座位利用率（预约时长/可用时长）

#### 3.6.2 座位热力图
- 按座位显示预约热度
- 颜色深浅表示热门程度
- 可按时段筛选
- 可按日期范围统计

#### 3.6.3 趋势分析
- 每日预约量趋势（折线图）
- 各时段预约分布（柱状图）
- 各区域使用率对比（柱状图）
- 违约趋势（折线图）

#### 3.6.4 排行榜
- 最热门座位TOP10
- 最活跃用户TOP10（按预约次数）
- 信用分排行榜

### 3.7 系统配置模块

#### 3.7.1 预约配置
- 预约提前天数（默认2天）
- 每日最多预约次数（默认3次）
- 每人同时预约上限（默认1个）
- 签到宽限时间（默认15分钟）
- 签退宽限时间（默认30分钟）

#### 3.7.2 信用分配置
- 初始信用分（默认100）
- 正常完成加分（默认+2）
- 未签到扣分（默认-5）
- 未签退扣分（默认-3）
- 临时离开超时扣分（默认-3）
- 信用分等级规则

#### 3.7.3 时段配置
- 时段名称
- 开始时间
- 结束时间
- 是否启用

### 3.8 通知模块

#### 3.8.1 通知类型
- 预约成功通知
- 预约开始提醒（提前10分钟）
- 签到超时提醒
- 签退提醒（预约结束前10分钟）
- 违约通知
- 申诉处理结果通知

#### 3.8.2 通知方式
- 站内消息
- 系统公告（管理员发布）

## 四、数据库设计

### 4.1 用户表 (user)
```sql
id              BIGINT          主键
student_no      VARCHAR(20)     学号（唯一）
password        VARCHAR(100)    密码（加密）
name            VARCHAR(50)     姓名
gender          TINYINT         性别 0女 1男
college         VARCHAR(50)     学院
major           VARCHAR(50)     专业
phone           VARCHAR(20)     联系电话
credit_score    INT             信用分（默认100）
role            VARCHAR(20)     角色 USER/ADMIN
status          TINYINT         状态 0禁用 1正常 2临时禁用
ban_until       DATETIME        禁用到期时间
create_time     DATETIME        创建时间
update_time     DATETIME        更新时间
```

### 4.2 楼层表 (floor)
```sql
id              BIGINT          主键
name            VARCHAR(50)     楼层名称
description     VARCHAR(200)    描述
sort_order      INT             排序
status          TINYINT         状态 0禁用 1正常
create_time     DATETIME        创建时间
update_time     DATETIME        更新时间
```

### 4.3 区域表 (area)
```sql
id              BIGINT          主键
floor_id        BIGINT          所属楼层
name            VARCHAR(50)     区域名称
description     VARCHAR(200)    描述
is_hot          TINYINT         是否热门区域 0否 1是
min_credit      INT             最低信用分要求
sort_order      INT             排序
status          TINYINT         状态 0禁用 1正常
create_time     DATETIME        创建时间
update_time     DATETIME        更新时间
```

### 4.4 座位表 (seat)
```sql
id              BIGINT          主键
area_id         BIGINT          所属区域
seat_no         VARCHAR(20)     座位编号
seat_type       VARCHAR(20)     座位类型
has_power       TINYINT         是否有电源 0否 1是
has_lamp        TINYINT         是否有台灯 0否 1是
position_x      INT             X坐标（用于座位地图）
position_y      INT             Y坐标（用于座位地图）
hot_score       INT             热门度分数
total_book_count INT            总预约次数
status          TINYINT         状态 0禁用 1可用 2维修中
create_time     DATETIME        创建时间
update_time     DATETIME        更新时间
```

### 4.5 预约记录表 (booking)
```sql
id              BIGINT          主键
user_id         BIGINT          用户ID
seat_id         BIGINT          座位ID
booking_date    DATE            预约日期
time_slot       VARCHAR(20)     时段（MORNING/AFTERNOON/EVENING/ALLDAY）
start_time      DATETIME        开始时间
end_time        DATETIME        结束时间
check_in_time   DATETIME        签到时间
check_out_time  DATETIME        签退时间
status          VARCHAR(20)     状态
leave_time      DATETIME        临时离开时间
cancel_time     DATETIME        取消时间
cancel_reason   VARCHAR(200)    取消原因
create_time     DATETIME        创建时间
update_time     DATETIME        更新时间
```

### 4.6 违约记录表 (violation)
```sql
id              BIGINT          主键
user_id         BIGINT          用户ID
booking_id      BIGINT          预约ID
violation_type  VARCHAR(20)     违约类型
deduct_score    INT             扣除信用分
violation_time  DATETIME        违约时间
appeal_status   VARCHAR(20)     申诉状态 NULL未申诉/PENDING待审核/APPROVED通过/REJECTED拒绝
appeal_reason   TEXT            申诉理由
appeal_images   VARCHAR(500)    申诉图片（JSON数组）
appeal_time     DATETIME        申诉时间
handle_admin_id BIGINT          处理管理员ID
handle_result   VARCHAR(200)    处理结果
handle_time     DATETIME        处理时间
create_time     DATETIME        创建时间
```

### 4.7 信用分变动记录表 (credit_log)
```sql
id              BIGINT          主键
user_id         BIGINT          用户ID
change_type     VARCHAR(20)     变动类型
change_score    INT             变动分数（正数加分，负数扣分）
before_score    INT             变动前分数
after_score     INT             变动后分数
related_id      BIGINT          关联ID（预约ID或违约ID）
reason          VARCHAR(200)    原因
create_time     DATETIME        创建时间
```

### 4.8 系统配置表 (system_config)
```sql
id              BIGINT          主键
config_key      VARCHAR(50)     配置键（唯一）
config_value    VARCHAR(200)    配置值
config_name     VARCHAR(50)     配置名称
config_desc     VARCHAR(200)    配置描述
create_time     DATETIME        创建时间
update_time     DATETIME        更新时间
```

### 4.9 通知消息表 (notification)
```sql
id              BIGINT          主键
user_id         BIGINT          用户ID（NULL表示全体）
title           VARCHAR(100)    标题
content         TEXT            内容
type            VARCHAR(20)     类型
is_read         TINYINT         是否已读 0未读 1已读
create_time     DATETIME        创建时间
```

### 4.10 时段配置表 (time_slot)
```sql
id              BIGINT          主键
slot_name       VARCHAR(20)     时段名称
slot_code       VARCHAR(20)     时段代码（MORNING/AFTERNOON/EVENING/ALLDAY）
start_time      TIME            开始时间
end_time        TIME            结束时间
status          TINYINT         状态 0禁用 1启用
sort_order      INT             排序
create_time     DATETIME        创建时间
update_time     DATETIME        更新时间
```

## 五、接口设计

### 5.1 用户接口

#### POST /api/user/register
注册

#### POST /api/user/login
登录

#### GET /api/user/info
获取当前用户信息（含信用分）

#### PUT /api/user/password
修改密码

#### GET /api/user/credit-log
获取信用分变动记录

#### GET /api/user/violation
获取我的违约记录

#### POST /api/user/violation/appeal
提交违约申诉

### 5.2 座位接口

#### GET /api/floor/list
获取楼层列表

#### GET /api/area/list
获取区域列表（可按楼层筛选）

#### GET /api/seat/list
获取座位列表（可按区域筛选）

#### GET /api/seat/{id}
获取座位详情

#### GET /api/seat/available
获取可用座位（按日期、时段筛选）

#### GET /api/seat/map
获取座位地图数据（含座位坐标和状态）

### 5.3 预约接口

#### POST /api/booking/create
创建预约

#### GET /api/booking/my-list
获取我的预约记录

#### GET /api/booking/{id}
获取预约详情

#### PUT /api/booking/{id}/cancel
取消预约

#### POST /api/booking/{id}/check-in
签到（传入座位编号验证）

#### POST /api/booking/{id}/check-out
签退

#### POST /api/booking/{id}/leave
标记临时离开

#### POST /api/booking/{id}/return
标记返回

#### GET /api/booking/current
获取当前正在使用的预约

### 5.4 统计接口

#### GET /api/stats/overview
数据概览（座位总数、今日预约数、使用中、签到率等）

#### GET /api/stats/heat-map
座位热力图数据

#### GET /api/stats/trend
预约趋势数据（按日期范围）

#### GET /api/stats/ranking
排行榜（热门座位、活跃用户）

### 5.5 通知接口

#### GET /api/notification/list
获取我的通知列表

#### PUT /api/notification/{id}/read
标记已读

#### PUT /api/notification/read-all
全部标记已读

#### GET /api/notification/unread-count
获取未读数量

### 5.6 管理员接口

#### GET /api/admin/user/list
用户列表

#### PUT /api/admin/user/{id}/status
修改用户状态（启用/禁用）

#### PUT /api/admin/user/{id}/credit
重置用户信用分

#### GET /api/admin/floor/list
楼层管理列表

#### POST /api/admin/floor
添加楼层

#### PUT /api/admin/floor/{id}
编辑楼层

#### DELETE /api/admin/floor/{id}
删除楼层

#### GET /api/admin/area/list
区域管理列表

#### POST /api/admin/area
添加区域

#### PUT /api/admin/area/{id}
编辑区域

#### DELETE /api/admin/area/{id}
删除区域

#### GET /api/admin/seat/list
座位管理列表

#### POST /api/admin/seat
添加座位

#### PUT /api/admin/seat/{id}
编辑座位

#### DELETE /api/admin/seat/{id}
删除座位

#### POST /api/admin/seat/batch
批量添加座位

#### GET /api/admin/booking/list
所有预约记录

#### GET /api/admin/violation/list
违约记录列表

#### PUT /api/admin/violation/{id}/handle
处理违约申诉

#### GET /api/admin/config/list
系统配置列表

#### PUT /api/admin/config/{id}
更新配置

#### POST /api/admin/notification/publish
发布系统公告

#### GET /api/admin/stats/detail
详细数据统计

## 六、前端页面设计

### 6.1 学生端页面

#### 6.1.1 登录注册页
- 登录表单
- 注册表单

#### 6.1.2 首页
- 导航栏（首页、我的预约、个人中心）
- 快速预约入口
- 今日预约统计卡片
- 公告轮播

#### 6.1.3 座位预约页
- 日期选择器（只能选今天+未来2天）
- 时段选择（卡片式）
- 楼层选择（Tab切换）
- 区域选择（下拉或卡片）
- 座位地图（可视化布局，颜色区分状态）
  - 绿色：可预约
  - 灰色：已预约
  - 红色：维修中
  - 黄色：需要更高信用分
- 座位筛选（类型、电源、台灯）
- 确认预约弹窗

#### 6.1.4 我的预约页
- 当前预约（卡片展示，含倒计时、签到签退按钮、取消按钮）
- 预约历史（列表，状态标签）
- 筛选（按状态、日期）

#### 6.1.5 签到页面
- 扫码签到（显示摄像头或输入座位编号）
- 签到成功提示
- 显示预约信息和倒计时

#### 6.1.6 个人中心页
- 个人信息展示
- 信用分展示（进度条+分数）
- 信用分变动记录（列表）
- 违约记录（可申诉）
- 修改密码
- 通知消息列表

#### 6.1.7 违约申诉页
- 违约详情
- 申诉表单（文字+图片上传）
- 提交申诉

### 6.2 管理员端页面

#### 6.2.1 管理后台首页
- 数据统计卡片（用户数、座位数、今日预约、违约率等）
- 预约趋势图（ECharts折线图）
- 各区域使用率图（ECharts柱状图）
- 快捷操作入口

#### 6.2.2 座位管理页
- 楼层管理（列表+添加编辑）
- 区域管理（列表+添加编辑）
- 座位管理（列表+添加编辑+批量添加）
- 座位地图可视化编辑

#### 6.2.3 预约管理页
- 预约记录列表
- 搜索筛选（用户、座位、日期、状态）
- 导出数据

#### 6.2.4 违约管理页
- 违约记录列表
- 申诉审核（查看申诉理由、处理）
- 违约统计

#### 6.2.5 用户管理页
- 用户列表
- 搜索筛选（学号、姓名、学院）
- 修改状态（禁用/启用）
- 重置信用分
- 查看用户详情（预约记录、违约记录）

#### 6.2.6 系统配置页
- 预约规则配置
- 信用分规则配置
- 时段配置
- 保存配置

#### 6.2.7 数据统计页
- 座位热力图（按时段、日期）
- 使用率趋势（折线图）
- 各时段预约分布（柱状图）
- 热门座位排行（表格）
- 活跃用户排行（表格）
- 导出报表

#### 6.2.8 通知管理页
- 发布系统公告
- 通知历史记录

## 七、前端技术选型

### 7.1 核心框架
- **Bootstrap 5**：响应式布局、基础组件、栅格系统
- **jQuery 3.x**：DOM操作、事件处理、AJAX请求

### 7.2 UI组件
- **Layer**：弹层组件（消息提示、确认框、加载层、弹窗）
- **Bootstrap Icons**：图标库
- **Flatpickr**：日期时间选择器
- **DataTables**：表格插件（管理端列表）

### 7.3 数据可视化
- **ECharts 5.x**：图表库（折线图、柱状图、饼图、热力图）

### 7.4 其他插件
- **jQuery Validation**：表单验证
- **Dropzone.js**：图片上传（申诉上传）
- **QRCode.js**：生成二维码（座位签到码）
- **html5-qrcode**：扫描二维码（签到扫码）
- **Moment.js**：日期时间处理

### 7.5 页面模板引擎
- **Thymeleaf**：服务端渲染（SpringBoot集成）

## 八、业务流程设计

### 8.1 预约流程
```
学生登录 
→ 选择日期/时段 
→ 选择楼层/区域 
→ 查看座位地图 
→ 选择座位 
→ 确认预约 
→ 预约成功（生成签到码）
```

### 8.2 签到流程
```
预约开始前15分钟 
→ 学生到达图书馆 
→ 扫描座位二维码（或输入座位编号）
→ 验证预约信息 
→ 签到成功 
→ 座位状态变为"使用中"
```

### 8.3 违约处理流程
```
系统检测违约（定时任务）
→ 创建违约记录 
→ 扣除信用分 
→ 记录信用分变动 
→ 发送违约通知 
→ 学生可申诉 
→ 管理员审核 
→ 处理结果通知
```

### 8.4 座位自动释放流程
```
预约开始时间 + 15分钟 
→ 检查是否已签到 
→ 未签到则自动释放座位 
→ 记录违约 
→ 扣除信用分 
→ 发送通知
```

## 九、定时任务设计

### 9.1 签到超时检查
- 执行频率：每1分钟
- 任务内容：检查所有"待签到"状态的预约，如果超过签到时间15分钟未签到，则自动释放并记录违约

### 9.2 签退超时检查
- 执行频率：每5分钟
- 任务内容：检查所有"使用中"状态的预约，如果预约结束时间超过30分钟仍未签退，则自动签退并记录违约

### 9.3 临时离开超时检查
- 执行频率：每5分钟
- 任务内容：检查所有标记"临时离开"的预约，如果离开超过30分钟，则自动签退并记录违约

### 9.4 信用分处罚检查
- 执行频率：每小时
- 任务内容：检查所有用户的信用分，根据信用分等级设置禁用状态

### 9.5 预约提醒
- 执行频率：每1分钟
- 任务内容：预约开始前10分钟发送签到提醒；预约结束前10分钟发送签退提醒

## 十、特色功能

### 10.1 座位热力图
- 基于ECharts热力图组件
- 颜色深浅表示座位热门程度（预约次数）
- 可按时段、日期范围筛选
- 帮助学生选择冷门座位，提高预约成功率

### 10.2 信用分系统
- 激励学生遵守预约规则
- 多级惩罚机制（限制预约、禁止预约）
- 正向激励（正常使用加分）
- 公平合理的申诉机制

### 10.3 智能推荐
- 根据学生历史偏好推荐座位
- 根据学生信用分推荐可预约座位
- 根据当前人流量推荐冷门座位

### 10.4 座位地图可视化
- 直观展示座位布局
- 实时显示座位状态
- 点击座位查看详情
- 支持筛选和搜索

### 10.5 二维码签到
- 每个座位生成唯一二维码
- 扫码签到验证
- 防止代签（验证座位编号）

## 十一、数据统计与分析

### 11.1 座位使用率分析
- 计算公式：座位使用率 = 实际使用时长 / (座位数 × 开放时长) × 100%
- 按日期、时段、区域统计
- ECharts折线图展示趋势

### 11.2 预约签到率分析
- 计算公式：签到率 = 实际签到数 / 总预约数 × 100%
- 按日期统计
- 找出签到率低的时段和原因

### 11.3 违约分析
- 违约类型分布（饼图）
- 违约趋势（折线图）
- 违约率 = 违约次数 / 总预约次数 × 100%

### 11.4 热门座位分析
- TOP10热门座位（预约次数最多）
- 分析热门原因（位置、设施）
- 帮助管理员优化座位配置

## 十二、开发优先级

### P0（核心功能 - 第1周）
- 用户注册登录
- 座位管理（楼层、区域、座位CRUD）
- 预约功能（创建、取消、查看）
- 签到签退功能

### P1（重要功能 - 第2周）
- 违约管理（记录、扣分、申诉）
- 信用分系统
- 定时任务（自动释放、超时检查）
- 座位地图可视化

### P2（增强功能 - 第3周）
- 数据统计（座位使用率、热力图）
- 通知系统
- 系统配置管理
- 管理员后台完善

### P3（优化功能 - 第4周）
- 智能推荐
- 数据导出
- 性能优化
- 界面美化

## 十三、部署说明

### 13.1 一体化部署
- 前端页面放在 `src/main/resources/templates/` 目录
- 静态资源（CSS/JS/图片）放在 `src/main/resources/static/` 目录
- 使用Thymeleaf模板引擎渲染页面
- 打包成jar文件，一键启动

### 13.2 访问地址
- 学生端：http://localhost:8080/
- 管理员端：http://localhost:8080/admin/

### 13.3 初始账户
- 管理员：admin / admin123
- 学生测试账户：20240001 / 123456

