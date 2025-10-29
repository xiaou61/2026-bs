# 高校自助点餐系统 PRD

## 一、项目概述

### 1.1 项目背景
高校食堂就餐高峰期排队时间长，点餐效率低，学生用餐体验差。本系统通过线上点餐、线下取餐的模式，解决食堂排队拥挤问题，提升用餐效率。学生可以提前在线点餐支付，到点直接取餐，避免排队等候。同时支持多商家入驻，提供丰富的餐品选择。

### 1.2 项目名称
高校自助点餐系统（Campus Self-Service Ordering System）

### 1.3 技术栈
- 后端：Spring Boot 3 + MyBatis-Plus + MySQL 8.0 + JWT
- 前端：Thymeleaf + jQuery + Bootstrap 5 + Ajax
- 支付：模拟余额支付
- 图片：URL存储（可对接OSS）
- 架构：前后端一体化（MVC架构）

### 1.4 核心功能
- 在线点餐（学生浏览菜品、下单支付）
- 商家管理（菜品管理、订单处理）
- 多商家入驻（食堂多档口模式）
- 取餐号管理（排队叫号）
- 评价系统（订单完成后评价）
- 优惠活动（满减、折扣）
- 后台管理（商家审核、数据统计）

## 二、用户角色

### 2.1 学生用户
- 注册登录
- 浏览商家和菜品
- 购物车管理
- 下单支付
- 查看订单
- 取餐
- 评价商家和菜品
- 收藏夹

### 2.2 商家用户
- 商家入驻申请
- 店铺信息管理
- 菜品管理（上架/下架/编辑）
- 订单管理（接单、出餐、完成）
- 营业状态管理
- 数据统计
- 评价查看

### 2.3 系统管理员
- 商家审核
- 用户管理
- 订单监控
- 分类管理
- 数据统计
- 系统配置

## 三、核心功能模块

### 3.1 用户模块

#### 3.1.1 学生注册
- 学号（唯一）
- 姓名
- 手机号
- 密码
- 院系/专业
- 宿舍楼栋

#### 3.1.2 用户登录
- 手机号/学号登录
- JWT Token鉴权
- 记住登录状态

#### 3.1.3 个人信息
- 头像、昵称
- 常用收货地址
- 手机号
- 偏好设置

### 3.2 商家模块

#### 3.2.1 商家入驻
- 商家名称
- 营业执照号
- 联系人姓名
- 联系电话
- 商家地址（食堂位置）
- 营业时间
- 店铺图片
- 经营类别（中餐/西餐/小吃/饮品等）

#### 3.2.2 商家信息
- 店铺名称
- 店铺公告
- 营业状态（营业中/打烊）
- 店铺评分
- 月销量
- 配送费
- 起送价
- 预计出餐时间

#### 3.2.3 营业管理
- 营业时间设置
- 休息日设置
- 临时打烊
- 繁忙状态（暂停接单）

### 3.3 菜品模块

#### 3.3.1 菜品分类
- 系统分类（主食/荤菜/素菜/汤/饮品等）
- 商家自定义分类

#### 3.3.2 菜品信息
- 菜品名称
- 菜品图片
- 菜品描述
- 价格
- 原价（用于显示折扣）
- 分类
- 库存（份数）
- 月销量
- 是否推荐
- 是否上架
- 口味规格（辣度、甜度、加料等）

#### 3.3.3 菜品管理
- 新增菜品
- 编辑菜品
- 上架/下架
- 删除菜品
- 批量操作
- 库存管理

### 3.4 点餐模块

#### 3.4.1 浏览菜品
- 商家列表（支持搜索、筛选）
- 商家详情页
- 菜品列表（按分类展示）
- 菜品详情
- 推荐菜品
- 热销菜品

#### 3.4.2 购物车
- 添加菜品
- 修改数量
- 删除菜品
- 清空购物车
- 计算总价
- 备注说明
- 优惠券选择

#### 3.4.3 下单流程
- 选择取餐方式（堂食/打包）
- 选择取餐时间（立即/预约）
- 填写备注
- 确认订单信息
- 选择支付方式
- 完成支付
- 获取取餐号

### 3.5 订单模块

#### 3.5.1 订单状态
- 待支付：下单未支付
- 待接单：已支付，等待商家确认
- 制作中：商家已接单，正在制作
- 待取餐：餐品制作完成，等待取餐
- 已完成：用户已取餐
- 已取消：订单取消（超时/用户取消/商家取消）
- 已退款：异常退款

#### 3.5.2 订单信息
- 订单编号
- 取餐号
- 商家信息
- 菜品列表（名称、数量、价格、口味）
- 订单金额（菜品总价、配送费、优惠金额）
- 取餐方式
- 预约时间
- 下单时间
- 支付时间
- 完成时间
- 订单状态
- 用户备注
- 取消原因

#### 3.5.3 我的订单
- 订单列表（全部/待支付/待取餐/已完成）
- 订单详情
- 再来一单
- 取消订单（待接单状态）
- 催单（制作中状态）
- 确认取餐
- 评价订单
- 申请退款

#### 3.5.4 订单超时规则
- 下单后15分钟未支付自动取消
- 支付后商家5分钟内未接单可取消
- 商家接单后用户不可取消（特殊情况联系商家）
- 超过预约时间30分钟未取餐，系统自动完成

#### 3.5.5 取餐号规则
- 每日重置（当日001开始）
- 按商家独立编号
- 大屏幕显示当前可取餐号
- 短信/推送通知

### 3.6 评价模块

#### 3.6.1 评价功能
- 商家评分（1-5星）
- 菜品评分（1-5星）
- 评价标签（口味好、份量足、速度快、包装好）
- 评价内容（文字）
- 匿名评价
- 晒单图片（最多9张）

#### 3.6.2 评价展示
- 商家评分（综合分）
- 菜品评分
- 评价列表
- 好评率
- 评价筛选（全部/好评/中评/差评/有图）

#### 3.6.3 评价管理
- 商家回复评价
- 评价追加
- 违规评价举报

### 3.7 钱包模块

#### 3.7.1 余额功能
- 余额查看
- 充值（模拟支付）
- 消费记录
- 退款到账

#### 3.7.2 交易记录
- 充值记录
- 消费记录（点餐支付）
- 退款记录
- 余额明细

### 3.8 优惠模块

#### 3.8.1 优惠券
- 满减券（满30减5）
- 折扣券（8折券）
- 新用户券
- 领取优惠券
- 我的优惠券

#### 3.8.2 商家活动
- 限时特价
- 第二份半价
- 满减活动
- 新店优惠

### 3.9 收藏模块
- 收藏商家
- 收藏菜品
- 我的收藏
- 取消收藏

### 3.10 搜索模块
- 商家搜索
- 菜品搜索
- 搜索历史
- 热门搜索

### 3.11 商家端订单管理

#### 3.11.1 订单处理
- 新订单提醒（声音/消息）
- 订单列表（待接单/制作中/待取餐/已完成）
- 订单详情
- 接单操作
- 拒单操作（库存不足/休息时间）
- 出餐完成
- 取餐确认

#### 3.11.2 出餐管理
- 制作队列
- 预计出餐时间
- 叫号提醒
- 批量出餐

#### 3.11.3 订单统计
- 今日订单数
- 今日营业额
- 月度统计
- 热销菜品
- 高峰时段分析

### 3.12 管理后台模块

#### 3.12.1 商家管理
- 商家列表
- 商家审核（待审核/已通过/已拒绝）
- 商家详情
- 启用/禁用商家
- 评分管理

#### 3.12.2 用户管理
- 用户列表
- 用户详情
- 账号状态管理
- 消费统计

#### 3.12.3 分类管理
- 菜品分类管理
- 添加/编辑/删除分类
- 分类排序

#### 3.12.4 订单管理
- 订单列表
- 订单详情
- 异常订单处理
- 退款审核

#### 3.12.5 数据统计
- 平台总览（用户数、商家数、订单数、交易额）
- 订单统计（今日/本周/本月）
- 商家排行榜
- 菜品排行榜
- 用户活跃度

## 四、数据库设计（12张表）

### 4.1 用户表 (user)
```sql
id                  BIGINT          主键
student_id          VARCHAR(30)     学号（唯一）
username            VARCHAR(50)     用户名
phone               VARCHAR(20)     手机号（唯一）
password            VARCHAR(100)    密码（加密）
real_name           VARCHAR(50)     真实姓名
avatar              VARCHAR(255)    头像
department          VARCHAR(100)    院系
major               VARCHAR(100)    专业
dormitory           VARCHAR(100)    宿舍
balance             DECIMAL(10,2)   余额
status              TINYINT         状态（0正常/1禁用）
create_time         DATETIME        创建时间
update_time         DATETIME        更新时间
```

### 4.2 商家表 (merchant)
```sql
id                  BIGINT          主键
merchant_name       VARCHAR(100)    商家名称
license_no          VARCHAR(50)     营业执照号
contact_person      VARCHAR(50)     联系人
contact_phone       VARCHAR(20)     联系电话
address             VARCHAR(200)    商家地址
location            VARCHAR(100)    位置（食堂楼层）
category            VARCHAR(50)     经营类别
image               VARCHAR(255)    店铺图片
description         TEXT            店铺介绍
notice              VARCHAR(500)    店铺公告
business_hours      VARCHAR(100)    营业时间
delivery_fee        DECIMAL(10,2)   配送费
min_price           DECIMAL(10,2)   起送价
avg_prepare_time    INT             平均出餐时间（分钟）
rating              DECIMAL(3,2)    评分（0-5）
total_sales         INT             总销量
month_sales         INT             月销量
status              TINYINT         状态（0待审核/1营业中/2打烊/3已禁用）
is_busy             TINYINT         是否繁忙（0否/1是，暂停接单）
audit_status        TINYINT         审核状态（0待审核/1已通过/2已拒绝）
audit_remark        VARCHAR(200)    审核备注
create_time         DATETIME        创建时间
update_time         DATETIME        更新时间
```

### 4.3 菜品分类表 (category)
```sql
id                  BIGINT          主键
category_name       VARCHAR(50)     分类名称
parent_id           BIGINT          父分类ID（0为顶级）
sort                INT             排序
icon                VARCHAR(255)    图标
status              TINYINT         状态（0正常/1禁用）
create_time         DATETIME        创建时间
```

### 4.4 菜品表 (dish)
```sql
id                  BIGINT          主键
merchant_id         BIGINT          商家ID
dish_name           VARCHAR(100)    菜品名称
category_id         BIGINT          分类ID
image               VARCHAR(255)    菜品图片
description         VARCHAR(500)    菜品描述
price               DECIMAL(10,2)   现价
original_price      DECIMAL(10,2)   原价
stock               INT             库存（0为不限）
sales               INT             销量
month_sales         INT             月销量
rating              DECIMAL(3,2)    评分
is_recommend        TINYINT         是否推荐（0否/1是）
status              TINYINT         状态（0下架/1上架）
specs               TEXT            规格选项（JSON）
create_time         DATETIME        创建时间
update_time         DATETIME        更新时间
```

### 4.5 订单表 (orders)
```sql
id                  BIGINT          主键
order_no            VARCHAR(32)     订单编号
pickup_no           VARCHAR(20)     取餐号
user_id             BIGINT          用户ID
merchant_id         BIGINT          商家ID
merchant_name       VARCHAR(100)    商家名称
dish_amount         DECIMAL(10,2)   菜品金额
delivery_fee        DECIMAL(10,2)   配送费
discount_amount     DECIMAL(10,2)   优惠金额
total_amount        DECIMAL(10,2)   订单总额
pay_amount          DECIMAL(10,2)   实付金额
pickup_type         TINYINT         取餐方式（1堂食/2打包）
pickup_time         DATETIME        预约取餐时间
user_remark         VARCHAR(200)    用户备注
status              TINYINT         状态（0待支付/1待接单/2制作中/3待取餐/4已完成/5已取消/6已退款）
pay_time            DATETIME        支付时间
accept_time         DATETIME        接单时间
finish_time         DATETIME        完成时间
cancel_time         DATETIME        取消时间
cancel_reason       VARCHAR(200)    取消原因
is_refund           TINYINT         是否退款（0否/1是）
create_time         DATETIME        创建时间
update_time         DATETIME        更新时间
```

### 4.6 订单明细表 (order_detail)
```sql
id                  BIGINT          主键
order_id            BIGINT          订单ID
order_no            VARCHAR(32)     订单编号
dish_id             BIGINT          菜品ID
dish_name           VARCHAR(100)    菜品名称
dish_image          VARCHAR(255)    菜品图片
price               DECIMAL(10,2)   单价
quantity            INT             数量
amount              DECIMAL(10,2)   小计
specs               VARCHAR(200)    规格（口味等）
```

### 4.7 评价表 (review)
```sql
id                  BIGINT          主键
order_id            BIGINT          订单ID
order_no            VARCHAR(32)     订单编号
user_id             BIGINT          用户ID
merchant_id         BIGINT          商家ID
merchant_rating     INT             商家评分（1-5）
dish_rating         INT             菜品评分（1-5）
tags                VARCHAR(200)    评价标签（逗号分隔）
content             TEXT            评价内容
images              TEXT            图片（JSON数组）
is_anonymous        TINYINT         是否匿名（0否/1是）
merchant_reply      VARCHAR(500)    商家回复
reply_time          DATETIME        回复时间
create_time         DATETIME        创建时间
```

### 4.8 收藏表 (favorite)
```sql
id                  BIGINT          主键
user_id             BIGINT          用户ID
target_type         TINYINT         收藏类型（1商家/2菜品）
target_id           BIGINT          目标ID
create_time         DATETIME        创建时间
```

### 4.9 购物车表 (cart)
```sql
id                  BIGINT          主键
user_id             BIGINT          用户ID
merchant_id         BIGINT          商家ID
dish_id             BIGINT          菜品ID
dish_name           VARCHAR(100)    菜品名称
dish_image          VARCHAR(255)    菜品图片
price               DECIMAL(10,2)   价格
quantity            INT             数量
specs               VARCHAR(200)    规格
create_time         DATETIME        创建时间
update_time         DATETIME        更新时间
```

### 4.10 优惠券表 (coupon)
```sql
id                  BIGINT          主键
coupon_name         VARCHAR(100)    优惠券名称
coupon_type         TINYINT         类型（1满减/2折扣）
discount_amount     DECIMAL(10,2)   优惠金额（满减用）
discount_rate       DECIMAL(3,2)    折扣率（折扣用，0.8表示8折）
min_amount          DECIMAL(10,2)   最低消费
max_discount        DECIMAL(10,2)   最高优惠（折扣券用）
total_quantity      INT             发行总量
received_quantity   INT             已领取数量
merchant_id         BIGINT          商家ID（0为平台券）
valid_days          INT             有效天数
status              TINYINT         状态（0禁用/1启用）
create_time         DATETIME        创建时间
```

### 4.11 用户优惠券表 (user_coupon)
```sql
id                  BIGINT          主键
user_id             BIGINT          用户ID
coupon_id           BIGINT          优惠券ID
coupon_name         VARCHAR(100)    优惠券名称
coupon_type         TINYINT         类型
discount_amount     DECIMAL(10,2)   优惠金额
discount_rate       DECIMAL(3,2)    折扣率
min_amount          DECIMAL(10,2)   最低消费
merchant_id         BIGINT          商家ID
status              TINYINT         状态（0未使用/1已使用/2已过期）
expire_time         DATETIME        过期时间
use_time            DATETIME        使用时间
receive_time        DATETIME        领取时间
```

### 4.12 管理员表 (admin)
```sql
id                  BIGINT          主键
username            VARCHAR(50)     用户名（唯一）
password            VARCHAR(100)    密码（加密）
real_name           VARCHAR(50)     真实姓名
phone               VARCHAR(20)     手机号
role                TINYINT         角色（1超级管理员/2普通管理员）
status              TINYINT         状态（0正常/1禁用）
create_time         DATETIME        创建时间
```

## 五、API接口设计

### 5.1 认证接口
- POST /api/auth/register - 用户注册
- POST /api/auth/login - 用户登录
- GET /api/auth/info - 获取当前用户信息
- POST /api/auth/logout - 退出登录

### 5.2 用户接口
- GET /api/user/profile - 获取个人信息
- PUT /api/user/profile - 更新个人信息
- POST /api/user/recharge - 充值
- GET /api/user/balance - 获取余额

### 5.3 商家接口
- GET /api/merchants - 商家列表
- GET /api/merchants/{id} - 商家详情
- GET /api/merchants/search - 搜索商家
- POST /api/merchants/apply - 商家入驻申请

### 5.4 菜品接口
- GET /api/dishes - 菜品列表（按商家）
- GET /api/dishes/{id} - 菜品详情
- GET /api/dishes/recommend - 推荐菜品
- GET /api/dishes/hot - 热销菜品
- GET /api/dishes/search - 搜索菜品

### 5.5 购物车接口
- GET /api/cart - 获取购物车
- POST /api/cart/add - 添加到购物车
- PUT /api/cart/{id} - 修改数量
- DELETE /api/cart/{id} - 删除商品
- DELETE /api/cart/clear - 清空购物车

### 5.6 订单接口
- POST /api/orders - 创建订单
- GET /api/orders - 我的订单列表
- GET /api/orders/{id} - 订单详情
- POST /api/orders/{id}/pay - 支付订单
- POST /api/orders/{id}/cancel - 取消订单
- POST /api/orders/{id}/confirm - 确认取餐
- POST /api/orders/{id}/remind - 催单

### 5.7 评价接口
- POST /api/reviews - 发表评价
- GET /api/reviews/merchant/{merchantId} - 商家评价列表
- GET /api/reviews/dish/{dishId} - 菜品评价

### 5.8 收藏接口
- POST /api/favorites - 添加收藏
- DELETE /api/favorites/{id} - 取消收藏
- GET /api/favorites - 我的收藏

### 5.9 优惠券接口
- GET /api/coupons - 可领取优惠券
- POST /api/coupons/{id}/receive - 领取优惠券
- GET /api/coupons/my - 我的优惠券

### 5.10 分类接口
- GET /api/categories - 分类列表

### 5.11 商家端接口
- GET /api/merchant/info - 商家信息
- PUT /api/merchant/info - 更新商家信息
- PUT /api/merchant/status - 更新营业状态
- GET /api/merchant/dishes - 我的菜品
- POST /api/merchant/dishes - 添加菜品
- PUT /api/merchant/dishes/{id} - 编辑菜品
- DELETE /api/merchant/dishes/{id} - 删除菜品
- GET /api/merchant/orders - 订单管理
- POST /api/merchant/orders/{id}/accept - 接单
- POST /api/merchant/orders/{id}/reject - 拒单
- POST /api/merchant/orders/{id}/finish - 完成出餐
- GET /api/merchant/statistics - 数据统计

### 5.12 管理后台接口
- POST /api/admin/login - 管理员登录
- GET /api/admin/statistics - 数据统计
- GET /api/admin/users - 用户列表
- PUT /api/admin/users/{id}/status - 修改用户状态
- GET /api/admin/merchants - 商家列表
- POST /api/admin/merchants/{id}/audit - 商家审核
- PUT /api/admin/merchants/{id}/status - 修改商家状态
- GET /api/admin/orders - 订单列表
- GET /api/admin/categories - 分类管理
- POST /api/admin/categories - 添加分类
- PUT /api/admin/categories/{id} - 编辑分类
- DELETE /api/admin/categories/{id} - 删除分类

## 六、前端页面设计

### 6.1 学生端页面

#### 6.1.1 登录注册页
- 登录表单
- 注册表单

#### 6.1.2 首页
- 商家分类导航
- 推荐商家
- 热门菜品
- 搜索入口

#### 6.1.3 商家列表页
- 商家卡片（图片、名称、评分、月销量、配送费、起送价）
- 筛选（分类、评分、销量）
- 排序（综合、销量、评分、配送费）

#### 6.1.4 商家详情页
- 商家信息栏
- 菜品分类Tab
- 菜品列表
- 购物车悬浮
- 店铺评价

#### 6.1.5 购物车页
- 购物车商品列表
- 数量调整
- 总价计算
- 提交订单

#### 6.1.6 订单确认页
- 订单信息
- 取餐方式选择
- 预约时间选择
- 优惠券选择
- 备注填写
- 支付按钮

#### 6.1.7 订单列表页
- Tab切换（全部/待支付/待取餐/已完成）
- 订单卡片
- 状态筛选

#### 6.1.8 订单详情页
- 订单信息
- 取餐号
- 订单状态
- 菜品明细
- 操作按钮（支付/取消/确认取餐/评价）

#### 6.1.9 评价页
- 商家评分
- 菜品评分
- 评价标签
- 评价内容
- 上传图片
- 匿名选项

#### 6.1.10 我的页面
- 用户信息卡片
- 余额展示
- 功能入口（我的订单、我的收藏、优惠券、充值）

#### 6.1.11 收藏页
- 收藏的商家
- 收藏的菜品

### 6.2 商家端页面

#### 6.2.1 商家登录页

#### 6.2.2 商家首页
- 今日数据概览
- 订单统计
- 快捷入口

#### 6.2.3 订单管理页
- 新订单提醒
- 订单列表（待接单/制作中/待取餐/已完成）
- 订单操作

#### 6.2.4 菜品管理页
- 菜品列表
- 添加菜品
- 编辑菜品
- 上架/下架

#### 6.2.5 店铺管理页
- 店铺信息
- 营业状态
- 营业时间

#### 6.2.6 数据统计页
- 营业额统计
- 订单统计
- 热销菜品

### 6.3 管理端页面

#### 6.3.1 管理员登录页

#### 6.3.2 数据概览页
- 平台数据卡片
- 统计图表

#### 6.3.3 用户管理页
- 用户列表
- 用户详情
- 状态管理

#### 6.3.4 商家管理页
- 商家列表
- 商家审核
- 商家详情

#### 6.3.5 订单管理页
- 订单列表
- 订单详情

#### 6.3.6 分类管理页
- 分类列表
- 添加/编辑分类

## 七、业务流程

### 7.1 点餐流程
```
用户登录
→ 浏览商家
→ 选择商家进入详情
→ 浏览菜品
→ 添加菜品到购物车
→ 查看购物车
→ 提交订单
→ 选择取餐方式和时间
→ 选择优惠券
→ 确认订单
→ 支付
→ 等待商家接单
→ 等待出餐
→ 收到取餐通知
→ 到店取餐
→ 确认取餐
→ 评价
```

### 7.2 商家接单流程
```
收到新订单通知
→ 查看订单详情
→ 确认接单/拒单
→ 开始制作
→ 制作完成
→ 点击出餐完成
→ 系统生成取餐号
→ 等待用户取餐
→ 订单完成
```

### 7.3 商家入驻流程
```
提交入驻申请
→ 填写商家信息
→ 上传证件
→ 提交审核
→ 等待审核
→ 审核通过
→ 完善店铺信息
→ 添加菜品
→ 开始营业
```

## 八、特色功能

### 8.1 取餐号系统
- 自动生成取餐号
- 大屏显示
- 短信/推送通知
- 防止拥挤

### 8.2 预约点餐
- 提前下单
- 指定取餐时间
- 避开高峰期

### 8.3 多商家购物车
- 支持同时在多个商家购物车
- 分别结算

### 8.4 智能推荐
- 基于历史订单推荐
- 热销菜品推荐
- 个性化推荐

### 8.5 优惠系统
- 新用户优惠
- 满减活动
- 优惠券
- 限时特价

### 8.6 评价系统
- 真实评价
- 商家回复
- 晒单功能

## 九、非功能需求

### 9.1 性能要求
- 页面响应时间 < 2秒
- 支持1000+并发用户
- 高峰期稳定运行

### 9.2 安全要求
- 密码加密（BCrypt）
- JWT Token鉴权
- 支付安全
- 数据加密传输

### 9.3 可靠性要求
- 服务可用性 99%
- 数据备份
- 异常恢复

### 9.4 可用性要求
- 界面简洁直观
- 操作流程简单
- 移动端适配

## 十、开发优先级

### P0 核心功能
- 用户注册登录
- 商家列表和详情
- 菜品浏览
- 购物车
- 下单支付
- 订单管理

### P1 重要功能
- 商家入驻
- 商家端订单管理
- 菜品管理
- 评价功能
- 取餐号系统

### P2 增强功能
- 优惠券
- 收藏功能
- 搜索功能
- 数据统计
- 管理后台

## 十一、初始数据

### 11.1 管理员账户
- 用户名：admin
- 密码：admin123

### 11.2 测试商家
- 商家1：食堂一楼中餐，密码：123456
- 商家2：食堂二楼西餐，密码：123456

### 11.3 测试用户
- 学号：20210001，密码：123456
- 学号：20210002，密码：123456

