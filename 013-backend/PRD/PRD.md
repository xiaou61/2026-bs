# 校园共享经济平台 PRD

## 一、项目概述

### 1.1 项目背景
随着共享经济的深入发展，校园内存在大量闲置资源和未被充分利用的技能。学生需要临时使用单车、充电宝、雨伞等物品，也有相机、投影仪等设备的短期租赁需求，同时许多学生具备辅导、设计、摄影等技能但缺乏变现渠道。本平台旨在打造一个校园版的共享经济2.0平台，整合物品共享、闲置租赁、技能共享等多场景，构建校园资源循环生态。

### 1.2 项目名称
校园共享经济平台（Campus Share）

### 1.3 技术栈
- 后端：Spring Boot 3 + MyBatis-Plus + MySQL 8.0 + JWT + Redis
- 前端：Vue 3 + Element Plus + ECharts + Axios + Pinia
- 支付集成：支付宝沙箱/微信支付（模拟）
- 地图服务：高德地图API/百度地图API
- 定位服务：基于IP/GPS定位

### 1.4 核心功能
- 共享物品租借（单车、充电宝、雨伞）
- 闲置物品租赁（相机、投影仪、乐器等）
- 技能共享服务（辅导、设计、摄影等）
- 订单管理（租借、归还、支付、评价）
- 地图定位（查找附近物品/服务）
- 押金管理（冻结、退还）
- 信用体系（信用分、违约记录）
- 收益提现（服务提供者）

## 二、用户角色

### 2.1 普通用户（租用方）
- 注册登录、实名认证
- 浏览共享物品/闲置物品/技能服务
- 地图查找附近资源
- 下单租借/预约服务
- 支付押金和租金
- 归还物品/完成服务
- 评价与反馈
- 查看订单历史
- 押金管理

### 2.2 服务提供者（出租方/技能方）
- 发布闲置物品
- 发布技能服务
- 设置价格和可用时间
- 接单管理
- 收益查看
- 提现申请
- 查看评价
- 数据统计

### 2.3 平台运营方
- 投放共享物品（单车、充电宝、雨伞）
- 设备管理（位置、状态、维护）
- 订单监控
- 收益统计

### 2.4 系统管理员
- 用户管理（查看、禁用、信用管理）
- 物品审核（闲置物品发布审核）
- 订单管理（异常订单处理）
- 押金退还审核
- 提现审核
- 数据统计（订单数、收益、活跃度）
- 信用规则配置
- 平台费率设置

## 三、核心功能模块

### 3.1 用户模块

#### 3.1.1 用户注册
- 手机号
- 密码
- 昵称
- 头像
- 学校
- 学院
- 学号

#### 3.1.2 实名认证
- 真实姓名
- 身份证号
- 学生证照片
- 人脸识别（可选）
- 认证状态（未认证/审核中/已认证/未通过）

#### 3.1.3 用户登录
- 手机号/学号登录
- JWT Token鉴权
- 记住登录状态

#### 3.1.4 个人信息
- 基本信息（头像、昵称、学校、学号）
- 认证状态
- 信用分（初始100分）
- 押金余额
- 账户余额
- 统计数据（订单数、收益、评分）

#### 3.1.5 信用体系
- 信用分规则：
  - 初始信用分：100分
  - 按时归还：+2分
  - 超时归还：-5分
  - 损坏物品：-10分
  - 获得好评：+1分
  - 违约不还：-50分
  - 完成实名认证：+10分
- 信用等级：
  - 优秀（90-100分）：享受押金减免
  - 良好（70-89分）：正常使用
  - 一般（50-69分）：限制部分功能
  - 差（0-49分）：禁止租借

### 3.2 共享物品模块（单车、充电宝、雨伞）

#### 3.2.1 共享单车
- 单车编号
- 单车类型
- 当前位置（经纬度）
- 状态（可用/使用中/维护中/故障）
- 电池电量（电动车）
- 累计骑行距离
- 收费标准：2元/小时

#### 3.2.2 共享充电宝
- 充电宝编号
- 电量百分比
- 所在位置（归属柜机）
- 状态（可用/使用中/充电中/故障）
- 收费标准：1元/小时，封顶10元/天

#### 3.2.3 共享雨伞
- 雨伞编号
- 所在位置（归属点位）
- 状态（可用/使用中/维护中）
- 收费标准：0.5元/小时，封顶5元/天
- 押金：10元

#### 3.2.4 地图定位
- 显示附近的共享物品
- 根据距离排序
- 导航到物品位置
- 实时位置更新

#### 3.2.5 扫码租借
- 扫描二维码
- 显示物品详情
- 支付押金（首次）
- 确认租借
- 开始计费

#### 3.2.6 归还流程
- 扫码归还（指定归还点）
- 拍照确认
- 结算费用
- 自动扣款
- 完成订单

### 3.3 闲置物品租赁模块

#### 3.3.1 物品分类
- 数码设备（相机、镜头、摄像机、无人机、平板）
- 乐器（吉他、尤克里里、电子琴、架子鼓）
- 投影设备（投影仪、幕布、音响）
- 运动器材（滑板、羽毛球拍、帐篷）
- 服装道具（汉服、演出服、Cosplay服装）
- 图书教材（考研资料、专业书籍）
- 其他

#### 3.3.2 物品发布
- 物品标题
- 物品分类
- 物品描述
- 物品图片（最多9张）
- 新旧程度（全新/99新/95新/9成新/8成新以下）
- 原价
- 租金设置（按小时/按天/按周）
- 押金金额
- 可租时间段
- 取货方式（上门取/自取）
- 取货地址
- 联系方式

#### 3.3.3 物品审核
- 提交发布
- 平台审核
- 审核通过/拒绝
- 上架展示

#### 3.3.4 物品浏览
- 分类筛选
- 关键词搜索
- 排序（距离/价格/热度/最新）
- 物品详情页
- 出租者信息
- 租借记录
- 用户评价

#### 3.3.5 预约租借
- 选择租借时间段
- 计算租金和押金
- 确认订单
- 支付押金和首期租金
- 联系出租者
- 约定取货时间

#### 3.3.6 归还流程
- 归还物品
- 拍照验收
- 出租者确认
- 结算尾款
- 退还押金
- 互相评价

### 3.4 技能共享模块

#### 3.4.1 技能分类
- 学业辅导（数学、英语、专业课、考研辅导）
- 设计服务（平面设计、UI设计、PPT制作、视频剪辑）
- 摄影服务（证件照、写真、活动摄影、后期修图）
- 音乐教学（乐器教学、声乐指导）
- 健身指导（减脂、增肌、瑜伽）
- 代做服务（代购、代取、代排队）
- 其他技能

#### 3.4.2 技能发布
- 技能标题
- 技能分类
- 详细描述
- 展示图片/作品集
- 服务时长（按小时）
- 服务价格
- 可预约时间段
- 服务地点（上门/指定地点/线上）
- 个人简介
- 资质证明（可选）

#### 3.4.3 技能展示
- 技能列表
- 分类筛选
- 搜索功能
- 技能详情
- 服务者信息
- 成交记录
- 用户评价
- 作品展示

#### 3.4.4 预约流程
- 选择预约时间
- 填写需求描述
- 确认价格
- 支付订金（30%）
- 联系服务者
- 确认服务时间地点

#### 3.4.5 服务流程
- 开始服务（服务者点击开始）
- 服务进行中
- 完成服务（服务者点击完成）
- 用户确认
- 支付尾款（70%）
- 双方评价

### 3.5 订单管理模块

#### 3.5.1 订单类型
- 共享物品订单（单车/充电宝/雨伞）
- 闲置租赁订单
- 技能服务订单

#### 3.5.2 订单状态
- 待支付
- 进行中
- 待归还/待完成
- 待评价
- 已完成
- 已取消
- 异常订单（超时/损坏/纠纷）

#### 3.5.3 订单信息
- 订单编号
- 订单类型
- 物品/服务信息
- 租用者/服务者信息
- 开始时间
- 结束时间
- 租金/服务费
- 押金
- 实付金额
- 订单状态
- 创建时间

#### 3.5.4 订单操作
- 查看订单详情
- 取消订单（未开始前）
- 延长租期
- 提前归还
- 申请退款
- 发起投诉

#### 3.5.5 异常处理
- 超时提醒
- 超时费用（1.5倍计费）
- 物品损坏赔偿
- 纠纷仲裁
- 押金扣除
- 信用分扣除

### 3.6 支付模块

#### 3.6.1 押金管理
- 押金充值
- 押金冻结
- 押金解冻
- 押金扣除（损坏/违约）
- 押金退还
- 押金记录

#### 3.6.2 余额管理
- 余额充值
- 余额支付
- 余额退款
- 余额明细

#### 3.6.3 支付方式
- 支付宝（模拟）
- 微信支付（模拟）
- 余额支付
- 押金抵扣

#### 3.6.4 费用计算
- 按时长计费（小时/天）
- 超时费用
- 平台服务费（10%）
- 优惠券抵扣
- 积分抵扣

### 3.7 收益提现模块

#### 3.7.1 收益统计
- 今日收益
- 本月收益
- 累计收益
- 已提现金额
- 可提现金额
- 收益明细

#### 3.7.2 提现申请
- 提现金额（最低10元）
- 提现方式（支付宝/微信/银行卡）
- 账户信息
- 提交申请
- 平台审核
- 到账通知

#### 3.7.3 提现规则
- 最低提现金额：10元
- 提现手续费：2%
- 到账时间：1-3个工作日
- 每日提现次数限制：3次

### 3.8 评价模块

#### 3.8.1 评价类型
- 物品评价（质量、描述相符度）
- 服务评价（专业度、态度、效果）
- 用户评价（守时、爱护物品、沟通）

#### 3.8.2 评价内容
- 评分（1-5星）
- 文字评价
- 图片上传
- 标签选择（好评/中评/差评标签）
- 匿名评价（可选）

#### 3.8.3 评价展示
- 评价列表
- 评分统计
- 好评率
- 标签词云
- 追加评价

### 3.9 消息通知模块

#### 3.9.1 通知类型
- 订单通知（下单、开始、完成、超时）
- 支付通知（支付成功、退款到账）
- 审核通知（认证审核、物品审核、提现审核）
- 预约通知（预约成功、服务提醒）
- 系统通知（公告、活动、违规警告）

#### 3.9.2 通知方式
- 站内消息
- 短信通知（重要通知）
- 邮件通知（可选）

#### 3.9.3 消息管理
- 消息列表
- 未读消息数
- 消息已读
- 消息删除
- 通知设置

### 3.10 地图服务模块

#### 3.10.1 位置展示
- 地图中心（用户当前位置）
- 附近共享物品标记
- 物品类型图标
- 点击查看详情
- 导航到目标位置

#### 3.10.2 距离计算
- 计算直线距离
- 距离排序
- 附近范围筛选（500m/1km/3km）

#### 3.10.3 位置上报
- 用户位置定位
- 物品位置更新
- 骑行轨迹记录（单车）

### 3.11 数据统计模块

#### 3.11.1 个人数据（用户）
- 租借次数
- 消费金额
- 信用分变化
- 收藏物品
- 常用类别

#### 3.11.2 收益数据（服务提供者）
- 订单统计（总数、完成数、取消数）
- 收益趋势图
- 物品/服务排行
- 评分统计
- 用户画像

#### 3.11.3 平台数据（管理员）
- 用户总数（租用方/服务方）
- 实名认证率
- 订单总数（各类型）
- 交易总额
- 平台收益
- 物品分布热力图
- 活跃度统计（DAU/MAU）
- 信用分分布
- 违约率统计

## 四、数据库设计

### 4.1 用户表 (user)
```sql
id                BIGINT          主键
username          VARCHAR(50)     用户名（唯一）
password          VARCHAR(100)    密码（加密）
nickname          VARCHAR(50)     昵称
avatar            VARCHAR(200)    头像URL
phone             VARCHAR(20)     手机号
student_id        VARCHAR(30)     学号
school            VARCHAR(50)     学校
college           VARCHAR(50)     学院
real_name         VARCHAR(30)     真实姓名
id_card           VARCHAR(20)     身份证号
auth_status       TINYINT         认证状态（0未认证/1审核中/2已认证/3未通过）
credit_score      INT             信用分（初始100）
deposit_balance   DECIMAL(10,2)   押金余额
account_balance   DECIMAL(10,2)   账户余额
total_orders      INT             订单总数
total_income      DECIMAL(10,2)   累计收益
role              VARCHAR(20)     角色（USER/PROVIDER/OPERATOR/ADMIN）
status            TINYINT         状态（0禁用/1正常）
last_login_time   DATETIME        最后登录时间
create_time       DATETIME        创建时间
update_time       DATETIME        更新时间
```

### 4.2 实名认证表 (user_auth)
```sql
id                BIGINT          主键
user_id           BIGINT          用户ID
real_name         VARCHAR(30)     真实姓名
id_card           VARCHAR(20)     身份证号
student_card_img  VARCHAR(200)    学生证照片
face_img          VARCHAR(200)    人脸照片
auth_status       TINYINT         认证状态（0待审核/1通过/2拒绝）
reject_reason     VARCHAR(200)    拒绝原因
audit_admin_id    BIGINT          审核管理员ID
audit_time        DATETIME        审核时间
create_time       DATETIME        创建时间
update_time       DATETIME        更新时间
```

### 4.3 信用记录表 (credit_log)
```sql
id                BIGINT          主键
user_id           BIGINT          用户ID
change_type       VARCHAR(20)     变动类型（ON_TIME/OVERTIME/DAMAGE/GOOD_REVIEW/VIOLATION）
change_score      INT             变动分数（正数增加/负数减少）
before_score      INT             变动前分数
after_score       INT             变动后分数
reason            VARCHAR(200)    原因
related_order_id  BIGINT          关联订单ID
create_time       DATETIME        创建时间
```

### 4.4 共享物品表 (shared_item)
```sql
id                BIGINT          主键
item_no           VARCHAR(50)     物品编号（唯一）
item_type         VARCHAR(20)     物品类型（BIKE/CHARGER/UMBRELLA）
item_model        VARCHAR(50)     型号
latitude          DECIMAL(10,6)   纬度
longitude         DECIMAL(10,6)   经度
location_name     VARCHAR(100)    位置名称
battery_level     INT             电量百分比
status            TINYINT         状态（0可用/1使用中/2维护中/3故障）
hourly_price      DECIMAL(10,2)   小时租金
daily_max_price   DECIMAL(10,2)   每日封顶价格
deposit_amount    DECIMAL(10,2)   押金金额
total_usage_count INT             累计使用次数
total_distance    DECIMAL(10,2)   累计距离（单车）
last_maintain_time DATETIME       最后维护时间
create_time       DATETIME        创建时间
update_time       DATETIME        更新时间
```

### 4.5 闲置物品表 (idle_item)
```sql
id                BIGINT          主键
user_id           BIGINT          发布者ID
category          VARCHAR(20)     分类
title             VARCHAR(100)    标题
description       TEXT            描述
images            TEXT            图片URL（JSON数组）
condition_level   TINYINT         新旧程度（1全新/2九九新/3九五新/4九成新/5八成以下）
original_price    DECIMAL(10,2)   原价
hourly_price      DECIMAL(10,2)   小时租金
daily_price       DECIMAL(10,2)   日租金
weekly_price      DECIMAL(10,2)   周租金
deposit_amount    DECIMAL(10,2)   押金
available_time    TEXT            可租时间段（JSON）
pickup_method     TINYINT         取货方式（0上门/1自取/2均可）
pickup_address    VARCHAR(200)    取货地址
latitude          DECIMAL(10,6)   纬度
longitude         DECIMAL(10,6)   经度
contact_phone     VARCHAR(20)     联系电话
view_count        INT             浏览次数
order_count       INT             订单次数
rating            DECIMAL(3,2)    评分
status            TINYINT         状态（0待审核/1已上架/2已下架/3审核拒绝）
audit_reason      VARCHAR(200)    审核原因
is_deleted        TINYINT         是否删除（0否/1是）
create_time       DATETIME        创建时间
update_time       DATETIME        更新时间
```

### 4.6 技能服务表 (skill_service)
```sql
id                BIGINT          主键
user_id           BIGINT          服务者ID
category          VARCHAR(20)     分类
title             VARCHAR(100)    标题
description       TEXT            描述
images            TEXT            展示图片（JSON数组）
portfolio         TEXT            作品集（JSON数组）
service_duration  INT             服务时长（分钟）
hourly_price      DECIMAL(10,2)   小时价格
available_time    TEXT            可预约时间（JSON）
service_location  VARCHAR(200)    服务地点
location_type     TINYINT         地点类型（0上门/1指定地点/2线上）
latitude          DECIMAL(10,6)   纬度
longitude         DECIMAL(10,6)   经度
introduction      TEXT            个人简介
certificate       TEXT            资质证明（JSON数组）
view_count        INT             浏览次数
order_count       INT             成交次数
rating            DECIMAL(3,2)    评分
status            TINYINT         状态（0下架/1上架）
is_deleted        TINYINT         是否删除（0否/1是）
create_time       DATETIME        创建时间
update_time       DATETIME        更新时间
```

### 4.7 订单表 (order_info)
```sql
id                BIGINT          主键
order_no          VARCHAR(50)     订单编号（唯一）
order_type        VARCHAR(20)     订单类型（SHARED/IDLE/SKILL）
user_id           BIGINT          租用者/购买者ID
provider_id       BIGINT          服务提供者ID（闲置物品/技能）
item_id           BIGINT          物品/服务ID
item_title        VARCHAR(100)    物品/服务标题
start_time        DATETIME        开始时间
end_time          DATETIME        结束时间
planned_duration  INT             计划时长（分钟）
actual_duration   INT             实际时长（分钟）
rental_fee        DECIMAL(10,2)   租金/服务费
deposit_amount    DECIMAL(10,2)   押金
platform_fee      DECIMAL(10,2)   平台服务费
total_amount      DECIMAL(10,2)   总金额
paid_amount       DECIMAL(10,2)   已支付金额
deposit_status    TINYINT         押金状态（0未支付/1已冻结/2已退还/3已扣除）
payment_status    TINYINT         支付状态（0待支付/1已支付/2已退款）
order_status      TINYINT         订单状态（0待支付/1进行中/2待归还/3待评价/4已完成/5已取消/6异常）
cancel_reason     VARCHAR(200)    取消原因
is_overtime       TINYINT         是否超时（0否/1是）
overtime_fee      DECIMAL(10,2)   超时费用
is_damaged        TINYINT         是否损坏（0否/1是）
damage_fee        DECIMAL(10,2)   损坏赔偿
pickup_address    VARCHAR(200)    取货地址
return_address    VARCHAR(200)    归还地址
requirement       TEXT            需求描述（技能订单）
payment_time      DATETIME        支付时间
complete_time     DATETIME        完成时间
create_time       DATETIME        创建时间
update_time       DATETIME        更新时间
```

### 4.8 评价表 (review)
```sql
id                BIGINT          主键
order_id          BIGINT          订单ID
reviewer_id       BIGINT          评价者ID
reviewed_id       BIGINT          被评价者ID
review_type       VARCHAR(20)     评价类型（ITEM/SERVICE/USER）
rating            TINYINT         评分（1-5）
content           TEXT            评价内容
images            TEXT            图片（JSON数组）
tags              VARCHAR(200)    标签（逗号分隔）
is_anonymous      TINYINT         是否匿名（0否/1是）
additional_content TEXT           追加评价
additional_time   DATETIME        追加时间
create_time       DATETIME        创建时间
```

### 4.9 收益提现表 (withdrawal)
```sql
id                BIGINT          主键
user_id           BIGINT          用户ID
withdrawal_no     VARCHAR(50)     提现单号
amount            DECIMAL(10,2)   提现金额
fee               DECIMAL(10,2)   手续费
actual_amount     DECIMAL(10,2)   实际到账金额
withdraw_type     VARCHAR(20)     提现方式（ALIPAY/WECHAT/BANK）
account_name      VARCHAR(50)     账户名
account_no        VARCHAR(100)    账户号
status            TINYINT         状态（0待审核/1审核通过/2已打款/3审核拒绝）
reject_reason     VARCHAR(200)    拒绝原因
audit_admin_id    BIGINT          审核管理员ID
audit_time        DATETIME        审核时间
transfer_time     DATETIME        打款时间
create_time       DATETIME        创建时间
update_time       DATETIME        更新时间
```

### 4.10 支付记录表 (payment_record)
```sql
id                BIGINT          主键
user_id           BIGINT          用户ID
order_id          BIGINT          订单ID
payment_no        VARCHAR(50)     支付单号
payment_type      VARCHAR(20)     支付类型（DEPOSIT/RENTAL/SERVICE）
payment_method    VARCHAR(20)     支付方式（ALIPAY/WECHAT/BALANCE/DEPOSIT）
amount            DECIMAL(10,2)   金额
status            TINYINT         状态（0待支付/1已支付/2已退款）
pay_time          DATETIME        支付时间
refund_time       DATETIME        退款时间
create_time       DATETIME        创建时间
```

### 4.11 押金记录表 (deposit_record)
```sql
id                BIGINT          主键
user_id           BIGINT          用户ID
order_id          BIGINT          订单ID
record_type       VARCHAR(20)     记录类型（CHARGE/FREEZE/UNFREEZE/DEDUCT/REFUND）
amount            DECIMAL(10,2)   金额
before_balance    DECIMAL(10,2)   变动前余额
after_balance     DECIMAL(10,2)   变动后余额
reason            VARCHAR(200)    原因
create_time       DATETIME        创建时间
```

### 4.12 余额记录表 (balance_record)
```sql
id                BIGINT          主键
user_id           BIGINT          用户ID
record_type       VARCHAR(20)     记录类型（CHARGE/PAY/REFUND/INCOME/WITHDRAW）
amount            DECIMAL(10,2)   金额
before_balance    DECIMAL(10,2)   变动前余额
after_balance     DECIMAL(10,2)   变动后余额
related_order_id  BIGINT          关联订单ID
remark            VARCHAR(200)    备注
create_time       DATETIME        创建时间
```

### 4.13 消息通知表 (notification)
```sql
id                BIGINT          主键
user_id           BIGINT          接收用户ID
type              VARCHAR(20)     类型（ORDER/PAYMENT/AUDIT/APPOINTMENT/SYSTEM）
title             VARCHAR(100)    标题
content           VARCHAR(500)    内容
related_id        BIGINT          关联ID
related_type      VARCHAR(20)     关联类型
is_read           TINYINT         是否已读（0未读/1已读）
create_time       DATETIME        创建时间
```

### 4.14 位置轨迹表 (location_track)
```sql
id                BIGINT          主键
order_id          BIGINT          订单ID
item_id           BIGINT          物品ID
latitude          DECIMAL(10,6)   纬度
longitude         DECIMAL(10,6)   经度
speed             DECIMAL(10,2)   速度（km/h）
create_time       DATETIME        创建时间
```

### 4.15 投诉举报表 (complaint)
```sql
id                BIGINT          主键
order_id          BIGINT          订单ID
complainant_id    BIGINT          投诉人ID
respondent_id     BIGINT          被投诉人ID
complaint_type    VARCHAR(20)     投诉类型（DAMAGE/FRAUD/ATTITUDE/OTHER）
description       TEXT            投诉描述
images            TEXT            证据图片（JSON数组）
status            TINYINT         状态（0待处理/1处理中/2已解决/3已驳回）
handle_result     TEXT            处理结果
handle_admin_id   BIGINT          处理管理员ID
handle_time       DATETIME        处理时间
create_time       DATETIME        创建时间
```

### 4.16 收藏表 (favorite)
```sql
id                BIGINT          主键
user_id           BIGINT          用户ID
target_id         BIGINT          目标ID
target_type       VARCHAR(20)     目标类型（IDLE/SKILL）
create_time       DATETIME        创建时间
```

## 五、接口设计

### 5.1 认证接口

#### POST /api/auth/register
用户注册

#### POST /api/auth/login
用户登录

#### POST /api/auth/logout
用户登出

#### GET /api/auth/info
获取当前用户信息

### 5.2 用户接口

#### GET /api/user/profile
获取个人信息

#### PUT /api/user/profile
更新个人信息

#### POST /api/user/auth/submit
提交实名认证

#### GET /api/user/auth/status
获取认证状态

#### GET /api/user/credit
获取信用信息

#### GET /api/user/credit/log
获取信用记录

### 5.3 共享物品接口

#### GET /api/shared/nearby
获取附近共享物品

#### GET /api/shared/{id}
获取物品详情

#### POST /api/shared/scan
扫码获取物品信息

#### POST /api/shared/rent
租借物品

#### POST /api/shared/return
归还物品

#### GET /api/shared/bikes
获取附近单车

#### GET /api/shared/chargers
获取附近充电宝

#### GET /api/shared/umbrellas
获取附近雨伞

### 5.4 闲置物品接口

#### POST /api/idle/publish
发布闲置物品

#### GET /api/idle/list
获取物品列表

#### GET /api/idle/{id}
获取物品详情

#### PUT /api/idle/{id}
更新物品信息

#### DELETE /api/idle/{id}
删除物品

#### GET /api/idle/my
获取我的发布

#### GET /api/idle/search
搜索物品

#### POST /api/idle/{id}/rent
预约租借

### 5.5 技能服务接口

#### POST /api/skill/publish
发布技能服务

#### GET /api/skill/list
获取服务列表

#### GET /api/skill/{id}
获取服务详情

#### PUT /api/skill/{id}
更新服务信息

#### DELETE /api/skill/{id}
删除服务

#### GET /api/skill/my
获取我的服务

#### GET /api/skill/search
搜索服务

#### POST /api/skill/{id}/book
预约服务

### 5.6 订单接口

#### POST /api/order/create
创建订单

#### GET /api/order/list
获取订单列表

#### GET /api/order/{id}
获取订单详情

#### PUT /api/order/{id}/cancel
取消订单

#### PUT /api/order/{id}/extend
延长租期

#### PUT /api/order/{id}/complete
完成订单

#### PUT /api/order/{id}/confirm
确认完成

#### GET /api/order/ongoing
获取进行中订单

#### GET /api/order/history
获取历史订单

#### POST /api/order/{id}/complaint
发起投诉

### 5.7 支付接口

#### POST /api/payment/pay
支付订单

#### POST /api/payment/deposit/charge
充值押金

#### POST /api/payment/balance/charge
充值余额

#### POST /api/payment/refund
申请退款

#### GET /api/payment/record
支付记录

### 5.8 押金接口

#### GET /api/deposit/balance
获取押金余额

#### GET /api/deposit/record
押金记录

#### POST /api/deposit/refund
申请退还押金

### 5.9 余额接口

#### GET /api/balance/info
获取余额信息

#### GET /api/balance/record
余额明细

### 5.10 收益提现接口

#### GET /api/income/stats
收益统计

#### GET /api/income/detail
收益明细

#### POST /api/withdrawal/apply
申请提现

#### GET /api/withdrawal/list
提现记录

### 5.11 评价接口

#### POST /api/review/submit
提交评价

#### GET /api/review/list
获取评价列表

#### POST /api/review/{id}/append
追加评价

#### GET /api/review/my
我的评价

### 5.12 收藏接口

#### POST /api/favorite/add
添加收藏

#### DELETE /api/favorite/{id}
取消收藏

#### GET /api/favorite/list
收藏列表

### 5.13 消息通知接口

#### GET /api/notification/list
获取通知列表

#### GET /api/notification/unread-count
获取未读数量

#### PUT /api/notification/{id}/read
标记已读

#### PUT /api/notification/read-all
全部标记已读

#### DELETE /api/notification/{id}
删除通知

### 5.14 地图服务接口

#### GET /api/map/nearby
获取附近资源

#### POST /api/map/location
上报位置

#### GET /api/map/track
获取轨迹

#### GET /api/map/distance
计算距离

### 5.15 统计接口

#### GET /api/stats/user
用户统计

#### GET /api/stats/provider
服务提供者统计

#### GET /api/stats/item/{id}
物品统计

### 5.16 管理员接口

#### GET /api/admin/user/list
用户列表

#### PUT /api/admin/user/{id}/status
修改用户状态

#### GET /api/admin/auth/list
实名认证列表

#### PUT /api/admin/auth/{id}/audit
审核实名认证

#### GET /api/admin/idle/list
闲置物品列表

#### PUT /api/admin/idle/{id}/audit
审核闲置物品

#### GET /api/admin/order/list
订单列表

#### GET /api/admin/complaint/list
投诉列表

#### PUT /api/admin/complaint/{id}/handle
处理投诉

#### GET /api/admin/withdrawal/list
提现列表

#### PUT /api/admin/withdrawal/{id}/audit
审核提现

#### GET /api/admin/shared/list
共享物品列表

#### POST /api/admin/shared/add
添加共享物品

#### PUT /api/admin/shared/{id}
更新物品状态

#### GET /api/admin/stats/overview
数据概览

#### GET /api/admin/stats/revenue
收益统计

#### GET /api/admin/config/credit
信用规则配置

#### PUT /api/admin/config/credit
更新信用规则

#### GET /api/admin/config/fee
费率配置

#### PUT /api/admin/config/fee
更新费率配置

## 六、前端页面设计

### 6.1 用户端页面

#### 6.1.1 登录注册页
- 登录表单
- 注册表单
- 忘记密码

#### 6.1.2 首页
- 顶部导航（首页、订单、消息、我的）
- 实名认证提示
- 功能入口（单车、充电宝、雨伞、闲置租赁、技能服务）
- 附近资源（距离显示）
- 热门推荐
- Banner轮播

#### 6.1.3 地图页
- 地图显示
- 当前位置标记
- 物品标记（不同图标）
- 筛选按钮（类型、距离）
- 物品详情弹窗
- 导航按钮

#### 6.1.4 扫码租借页
- 扫码界面
- 物品信息展示
- 押金状态
- 收费标准说明
- 确认租借按钮

#### 6.1.5 闲置物品列表页
- 分类导航
- 物品卡片（图片、标题、价格、距离）
- 筛选排序
- 搜索框

#### 6.1.6 闲置物品详情页
- 图片轮播
- 物品信息（标题、描述、新旧程度、原价）
- 出租者信息（头像、昵称、信用分、评分）
- 价格信息（时租/日租/周租、押金）
- 取货方式和地址
- 可租时间段
- 预约租借按钮
- 联系出租者
- 评价列表
- 收藏按钮

#### 6.1.7 技能服务列表页
- 分类导航
- 服务卡片（图片、标题、价格、评分）
- 筛选排序
- 搜索框

#### 6.1.8 技能服务详情页
- 服务展示图
- 服务信息（标题、描述、时长、价格）
- 服务者信息（头像、昵称、评分、成交数）
- 个人简介
- 作品展示
- 可预约时间
- 服务地点
- 预约按钮
- 评价列表
- 收藏按钮

#### 6.1.9 发布物品/服务页
- Tab切换（闲置物品/技能服务）
- 表单填写
- 图片上传
- 价格设置
- 时间段选择
- 地址选择（地图）
- 提交发布

#### 6.1.10 订单列表页
- Tab切换（全部/进行中/待评价/已完成）
- 订单卡片
- 订单状态
- 操作按钮（取消/延长/归还/评价）

#### 6.1.11 订单详情页
- 订单信息
- 物品/服务信息
- 时间信息
- 费用明细
- 对方信息
- 操作按钮
- 联系对方
- 发起投诉

#### 6.1.12 支付页
- 订单信息
- 费用明细
- 支付方式选择
- 余额显示
- 确认支付按钮

#### 6.1.13 个人中心页
- 用户信息卡片
- 信用分显示
- 实名认证状态
- 押金余额
- 账户余额
- 功能入口（我的发布、收藏、收益、提现、设置）
- 退出登录

#### 6.1.14 实名认证页
- 认证表单
- 证件照片上传
- 提交审核
- 审核状态

#### 6.1.15 我的发布页
- Tab切换（闲置物品/技能服务）
- 发布列表
- 状态标识
- 编辑/删除/上下架

#### 6.1.16 收益中心页
- 收益统计卡片
- 收益趋势图
- 收益明细列表
- 提现按钮

#### 6.1.17 提现页
- 可提现金额
- 提现金额输入
- 提现方式选择
- 账户信息填写
- 手续费说明
- 提交申请

#### 6.1.18 评价页
- 评分选择
- 评价标签
- 评价内容输入
- 图片上传
- 匿名评价选项
- 提交评价

#### 6.1.19 消息通知页
- Tab切换（订单/支付/审核/系统）
- 消息列表
- 未读标识
- 消息跳转

#### 6.1.20 收藏页
- Tab切换（闲置物品/技能服务）
- 收藏列表
- 取消收藏

#### 6.1.21 信用中心页
- 信用分展示
- 信用等级
- 信用记录
- 信用规则说明

#### 6.1.22 押金管理页
- 押金余额
- 充值按钮
- 押金记录
- 冻结明细

#### 6.1.23 钱包页
- 账户余额
- 充值按钮
- 余额明细
- 押金入口

### 6.2 管理员端页面

#### 6.2.1 数据概览页
- 数据卡片（用户数、订单数、交易额、收益）
- 订单趋势图
- 收益趋势图
- 分类统计图
- 热力图

#### 6.2.2 用户管理页
- 用户列表
- 搜索筛选
- 信用分显示
- 状态管理
- 详细信息

#### 6.2.3 实名认证审核页
- 待审核列表
- 认证信息展示
- 证件照片查看
- 审核操作（通过/拒绝）

#### 6.2.4 物品审核页
- 待审核物品列表
- 物品详情
- 图片查看
- 审核操作

#### 6.2.5 订单管理页
- 订单列表
- 搜索筛选
- 状态统计
- 异常订单标识
- 订单详情

#### 6.2.6 投诉管理页
- 投诉列表
- 投诉详情
- 证据查看
- 处理操作

#### 6.2.7 提现审核页
- 提现申请列表
- 申请详情
- 用户信息验证
- 审核操作

#### 6.2.8 共享物品管理页
- 物品列表
- 地图分布
- 状态管理
- 添加物品
- 维护记录

#### 6.2.9 系统配置页
- 信用规则配置
- 费率设置
- 超时规则
- 押金设置
- 提现规则

#### 6.2.10 财务统计页
- 收益报表
- 提现统计
- 平台费统计
- 分类收益占比

## 七、业务流程

### 7.1 共享物品租借流程
```
用户登录
→ 地图查找附近物品
→ 选择目标物品
→ 扫码/点击租借
→ 检查实名认证（未认证则引导认证）
→ 检查信用分（低于50分禁止租借）
→ 检查押金（未支付则引导充值）
→ 冻结押金
→ 开始计费
→ 使用物品
→ 扫码归还（指定归还点）
→ 拍照确认
→ 结束计费
→ 计算费用
→ 自动扣款（余额/押金）
→ 解冻押金
→ 信用分+2
→ 完成订单
→ 引导评价
```

### 7.2 闲置物品租借流程
```
出租方：
→ 登录并实名认证
→ 发布闲置物品
→ 填写详细信息
→ 上传图片
→ 设置价格和押金
→ 提交审核
→ 审核通过上架

租用方：
→ 浏览物品列表
→ 查看物品详情
→ 选择租借时间段
→ 下单预约
→ 支付押金+首期租金
→ 联系出租者
→ 约定取货时间地点
→ 取货确认
→ 开始使用
→ 归还物品
→ 拍照验收
→ 出租者确认
→ 结算尾款
→ 退还押金
→ 双方互评
```

### 7.3 技能服务预约流程
```
服务方：
→ 登录并实名认证
→ 发布技能服务
→ 展示作品集
→ 设置价格和时间
→ 上架服务

预约方：
→ 浏览技能服务
→ 查看服务详情
→ 选择预约时间
→ 填写需求描述
→ 下单预约
→ 支付订金（30%）
→ 联系服务者
→ 确认服务时间地点

服务进行：
→ 服务者点击"开始服务"
→ 提供服务
→ 服务者点击"完成服务"
→ 用户确认完成
→ 支付尾款（70%）
→ 平台扣除服务费（10%）
→ 服务者获得收益
→ 双方互评
```

### 7.4 信用分变动流程
```
正向行为：
- 完成实名认证：+10分
- 按时归还：+2分
- 获得好评：+1分

负向行为：
- 超时归还：-5分
- 损坏物品：-10分
- 违约不还：-50分
- 获得差评：-3分

信用限制：
- 90-100分：享受押金减免（50%）
- 70-89分：正常使用
- 50-69分：限制高价值物品租借
- 0-49分：禁止租借
```

### 7.5 押金管理流程
```
首次租借：
→ 充值押金（根据物品类型）
→ 押金进入余额

租借时：
→ 冻结相应押金
→ 押金状态变为"已冻结"

正常归还：
→ 解冻押金
→ 押金状态变为"可用"

异常情况：
→ 损坏物品：扣除赔偿金额
→ 违约不还：全额扣除押金
→ 超时费用：从押金扣除

退还押金：
→ 申请退还
→ 检查无未完成订单
→ 原路退回
```

### 7.6 收益提现流程
```
服务提供者：
→ 完成订单获得收益
→ 平台扣除服务费（10%）
→ 收益进入账户

申请提现：
→ 进入收益中心
→ 选择提现金额（最低10元）
→ 填写提现方式和账户
→ 提交申请
→ 平台审核
→ 审核通过
→ 扣除手续费（2%）
→ 财务打款
→ 到账通知
```

## 八、特色功能

### 8.1 共享经济2.0模式
- 整合多场景（物品+技能）
- 平台自营+用户共享
- C2C+B2C混合模式
- 去中心化运营

### 8.2 智能定位导航
- 实时位置更新
- 附近资源推荐
- 距离计算排序
- 导航到目标位置
- 轨迹记录

### 8.3 信用体系
- 多维度信用评分
- 信用等级特权
- 违约惩罚机制
- 信用修复途径

### 8.4 灵活押金管理
- 统一押金账户
- 按需冻结/解冻
- 信用减免押金
- 快速退还

### 8.5 多元化收费模式
- 按小时/按天/按周计费
- 封顶价格
- 超时费用
- 损坏赔偿

### 8.6 双向评价机制
- 物品评价
- 服务评价
- 用户评价
- 信用关联

### 8.7 收益激励体系
- 服务提供者收益
- 低平台费率（10%）
- 快速提现
- 数据看板

### 8.8 安全保障
- 实名认证
- 押金保障
- 平台仲裁
- 保险保障（可选）

## 九、非功能需求

### 9.1 性能要求
- 地图加载时间：< 2秒
- 定位响应时间：< 1秒
- 订单创建响应：< 500ms
- 支付响应时间：< 3秒
- 支持并发订单：> 1000

### 9.2 安全要求
- 密码加密（BCrypt）
- JWT Token鉴权
- 实名认证
- 支付加密
- 隐私保护（脱敏处理）
- 防刷机制

### 9.3 可靠性要求
- 服务可用性：99.9%
- 数据备份：实时备份
- 异常恢复：自动重试
- 订单一致性保障

### 9.4 兼容性要求
- 浏览器：Chrome、Firefox、Safari、Edge
- 移动端适配
- 地图API兼容

## 十、开发优先级

### P0（核心功能 - 第1-2周）
- 用户注册登录
- 实名认证
- 共享物品（单车/充电宝/雨伞）
- 地图定位
- 扫码租借归还
- 订单管理
- 支付功能

### P1（重要功能 - 第3周）
- 闲置物品发布租赁
- 技能服务发布预约
- 押金管理
- 信用体系
- 评价系统
- 消息通知

### P2（增强功能 - 第4周）
- 收益提现
- 数据统计
- 收藏功能
- 搜索功能
- 投诉举报

### P3（优化功能 - 第5周）
- 管理员后台
- 审核系统
- 轨迹记录
- 热力图
- 推荐算法
- 性能优化

## 十一、部署说明

### 11.1 环境要求
- JDK 17+
- MySQL 8.0+
- Redis 6.0+
- Node.js 16+
- Nginx（可选）

### 11.2 第三方服务
- 地图服务（高德/百度）
- 支付接口（支付宝/微信）
- 短信服务（阿里云/腾讯云）

### 11.3 存储规划
- 用户头像：/data/avatars/
- 物品图片：/data/items/
- 认证照片：/data/auth/
- 评价图片：/data/reviews/
- 数据库备份：/data/backup/

### 11.4 初始账户
- 管理员：admin / admin123
- 运营方：operator / 123456
- 测试用户1：test1 / 123456
- 测试用户2：test2 / 123456

### 11.5 测试数据
- 预置10个用户
- 预置20个共享物品（单车10、充电宝5、雨伞5）
- 预置30个闲置物品
- 预置20个技能服务
- 预置50个订单
- 预置100条评价

