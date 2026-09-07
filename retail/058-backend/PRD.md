# 鲜牛奶订购系统

## 项目概述
- **项目简介**: 一个面向社区的鲜牛奶订购配送管理系统，支持用户在线订购牛奶、管理订阅、查看配送状态，配送员管理配送任务，管理员统一管理系统
- **核心功能**: 产品展示、订阅管理、订单处理、配送管理、用户反馈
- **技术栈**: Spring Boot + MyBatis-Plus + Redis + Vue3 + Element Plus

## 功能需求

### 1. 用户端功能
- 用户注册登录
- 浏览牛奶产品
- 创建订阅计划（日订/周订/月订）
- 管理收货地址
- 查看订单状态
- 查看配送记录
- 暂停/恢复订阅
- 提交投诉反馈

### 2. 配送员端功能
- 查看今日配送任务
- 确认配送完成
- 查看配送路线
- 查看历史配送记录

### 3. 管理端功能
- 用户管理（用户/配送员）
- 牛奶分类管理
- 牛奶产品管理
- 订阅订单管理
- 配送区域管理
- 配送路线管理
- 投诉处理
- 数据统计报表

## 技术设计

### 数据库设计

表1: user（用户表）
- id: bigint, 主键
- username: varchar(50), 用户名
- password: varchar(100), 密码
- nickname: varchar(50), 昵称
- phone: varchar(20), 手机号
- avatar: varchar(255), 头像
- role: varchar(20), 角色(ADMIN/DELIVERY/USER)
- status: int, 状态(0禁用/1正常)
- create_time: datetime, 创建时间

表2: milk_category（牛奶分类表）
- id: bigint, 主键
- name: varchar(50), 分类名称
- sort: int, 排序
- status: int, 状态

表3: milk_product（牛奶产品表）
- id: bigint, 主键
- category_id: bigint, 分类ID
- name: varchar(100), 产品名称
- image: varchar(255), 产品图片
- price: decimal(10,2), 单价
- unit: varchar(20), 单位(瓶/盒/袋)
- spec: varchar(50), 规格
- description: text, 产品描述
- stock: int, 库存
- status: int, 状态(0下架/1上架)
- create_time: datetime, 创建时间

表4: address（地址表）
- id: bigint, 主键
- user_id: bigint, 用户ID
- contact_name: varchar(50), 联系人
- contact_phone: varchar(20), 联系电话
- province: varchar(50), 省
- city: varchar(50), 市
- district: varchar(50), 区
- detail: varchar(200), 详细地址
- is_default: int, 是否默认(0否/1是)

表5: delivery_area（配送区域表）
- id: bigint, 主键
- name: varchar(100), 区域名称
- province: varchar(50), 省
- city: varchar(50), 市
- district: varchar(50), 区
- status: int, 状态

表6: subscription（订阅表）
- id: bigint, 主键
- user_id: bigint, 用户ID
- product_id: bigint, 产品ID
- address_id: bigint, 地址ID
- type: varchar(20), 订阅类型(DAILY/WEEKLY/MONTHLY)
- quantity: int, 每次数量
- delivery_time: varchar(20), 配送时段(MORNING/AFTERNOON)
- week_days: varchar(50), 配送日(周订时使用,如1,3,5)
- start_date: date, 开始日期
- end_date: date, 结束日期
- status: int, 状态(0暂停/1生效/2已结束)
- create_time: datetime, 创建时间

表7: milk_order（订单表）
- id: bigint, 主键
- order_no: varchar(50), 订单号
- user_id: bigint, 用户ID
- subscription_id: bigint, 订阅ID
- product_id: bigint, 产品ID
- quantity: int, 数量
- total_price: decimal(10,2), 总金额
- address: varchar(300), 配送地址
- contact_name: varchar(50), 联系人
- contact_phone: varchar(20), 联系电话
- delivery_date: date, 配送日期
- delivery_time: varchar(20), 配送时段
- status: int, 状态(0待配送/1配送中/2已完成/3已取消)
- create_time: datetime, 创建时间

表8: delivery_route（配送路线表）
- id: bigint, 主键
- name: varchar(100), 路线名称
- area_id: bigint, 区域ID
- delivery_user_id: bigint, 配送员ID
- description: varchar(500), 路线描述
- status: int, 状态

表9: delivery_record（配送记录表）
- id: bigint, 主键
- order_id: bigint, 订单ID
- delivery_user_id: bigint, 配送员ID
- route_id: bigint, 路线ID
- status: int, 状态(0待配送/1已配送/2异常)
- remark: varchar(200), 备注
- delivery_time: datetime, 配送时间
- create_time: datetime, 创建时间

表10: complaint（投诉反馈表）
- id: bigint, 主键
- user_id: bigint, 用户ID
- order_id: bigint, 关联订单ID
- type: varchar(20), 类型(COMPLAINT/FEEDBACK)
- content: text, 内容
- images: varchar(500), 图片
- status: int, 状态(0待处理/1已处理)
- reply: text, 回复内容
- reply_time: datetime, 回复时间
- create_time: datetime, 创建时间

表11: notification（通知表）
- id: bigint, 主键
- user_id: bigint, 用户ID
- title: varchar(100), 标题
- content: text, 内容
- type: varchar(20), 类型
- is_read: int, 是否已读(0否/1是)
- create_time: datetime, 创建时间

表12: payment（支付记录表）
- id: bigint, 主键
- order_id: bigint, 订单ID
- user_id: bigint, 用户ID
- amount: decimal(10,2), 金额
- pay_type: varchar(20), 支付方式
- pay_no: varchar(100), 支付流水号
- status: int, 状态(0待支付/1已支付/2已退款)
- pay_time: datetime, 支付时间
- create_time: datetime, 创建时间

### API接口设计

#### 认证模块
- POST /api/auth/login - 登录
- POST /api/auth/register - 注册
- GET /api/auth/info - 获取用户信息

#### 用户管理
- GET /api/user/page - 分页查询用户
- POST /api/user - 新增用户
- PUT /api/user - 修改用户
- DELETE /api/user/{id} - 删除用户

#### 牛奶分类
- GET /api/category/list - 分类列表
- GET /api/category/page - 分页查询
- POST /api/category - 新增分类
- PUT /api/category - 修改分类
- DELETE /api/category/{id} - 删除分类

#### 牛奶产品
- GET /api/product/list - 产品列表(用户端)
- GET /api/product/page - 分页查询
- GET /api/product/{id} - 产品详情
- POST /api/product - 新增产品
- PUT /api/product - 修改产品
- DELETE /api/product/{id} - 删除产品

#### 地址管理
- GET /api/address/list - 我的地址列表
- POST /api/address - 新增地址
- PUT /api/address - 修改地址
- DELETE /api/address/{id} - 删除地址
- PUT /api/address/default/{id} - 设为默认地址

#### 配送区域
- GET /api/area/list - 区域列表
- GET /api/area/page - 分页查询
- POST /api/area - 新增区域
- PUT /api/area - 修改区域
- DELETE /api/area/{id} - 删除区域

#### 订阅管理
- GET /api/subscription/my - 我的订阅
- GET /api/subscription/page - 分页查询(管理端)
- POST /api/subscription - 创建订阅
- PUT /api/subscription - 修改订阅
- PUT /api/subscription/pause/{id} - 暂停订阅
- PUT /api/subscription/resume/{id} - 恢复订阅
- DELETE /api/subscription/{id} - 取消订阅

#### 订单管理
- GET /api/order/my - 我的订单
- GET /api/order/page - 分页查询
- GET /api/order/{id} - 订单详情
- PUT /api/order/cancel/{id} - 取消订单

#### 配送路线
- GET /api/route/list - 路线列表
- GET /api/route/page - 分页查询
- POST /api/route - 新增路线
- PUT /api/route - 修改路线
- DELETE /api/route/{id} - 删除路线

#### 配送任务(配送员)
- GET /api/delivery/today - 今日配送任务
- GET /api/delivery/history - 历史配送记录
- PUT /api/delivery/complete/{id} - 确认配送完成
- PUT /api/delivery/exception/{id} - 标记异常

#### 投诉反馈
- GET /api/complaint/my - 我的反馈
- GET /api/complaint/page - 分页查询(管理端)
- POST /api/complaint - 提交反馈
- PUT /api/complaint/reply - 回复反馈

#### 通知
- GET /api/notification/my - 我的通知
- PUT /api/notification/read/{id} - 标记已读
- PUT /api/notification/readAll - 全部已读

#### 统计
- GET /api/stats/dashboard - 仪表盘数据
- GET /api/stats/order - 订单统计
- GET /api/stats/delivery - 配送统计

### 项目结构

后端:
```
058-backend/
├── sql/
│   └── init.sql
├── src/main/java/com/milk/
│   ├── MilkApplication.java
│   ├── common/
│   │   ├── Result.java
│   │   ├── BusinessException.java
│   │   └── GlobalExceptionHandler.java
│   ├── config/
│   │   ├── JwtInterceptor.java
│   │   ├── WebMvcConfig.java
│   │   ├── MybatisPlusConfig.java
│   │   └── RedisConfig.java
│   ├── utils/
│   │   └── JwtUtils.java
│   ├── entity/
│   ├── mapper/
│   ├── service/
│   └── controller/
└── src/main/resources/
    └── application.yml
```

前端:
```
058-frontend/
├── package.json
├── vite.config.js
├── index.html
└── src/
    ├── main.js
    ├── App.vue
    ├── router/
    │   └── index.js
    ├── api/
    │   ├── request.js
    │   └── index.js
    ├── store/
    │   └── user.js
    └── views/
        ├── Login.vue
        ├── Layout.vue
        ├── Dashboard.vue
        ├── user/
        ├── category/
        ├── product/
        ├── subscription/
        ├── order/
        ├── delivery/
        ├── area/
        ├── route/
        ├── complaint/
        └── my/
```

## 用户角色
- **ADMIN(管理员)**: 系统全部功能权限
- **DELIVERY(配送员)**: 配送任务管理、配送记录查看
- **USER(普通用户)**: 产品浏览、订阅管理、订单查看、地址管理、投诉反馈

## 默认账号
- admin/123456 (管理员)
- delivery/123456 (配送员)
- user/123456 (普通用户)
