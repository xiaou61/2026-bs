# 094 - SpringBoot 宠物咖啡馆平台设计与实现

## 项目概述

### 项目简介
宠物咖啡馆平台面向宠物友好消费场景，围绕区域门店、店宠展示、菜单管理、座位预约、到店消费、余额支付、评价回复和运营看板等核心流程，构建一个兼顾用户体验与门店经营管理的一体化系统。

### 核心功能
- 管理员、店长、顾客三角色登录与权限控制
- 区域、门店、店宠、菜单、座位等基础资料管理
- 顾客在线查看店宠信息、预约座位、创建消费订单
- 钱包充值、余额支付、订单取消、消费统计
- 顾客评价、门店回复、系统公告和经营看板

### 技术栈

后端
- Spring Boot 2.7.18
- MyBatis-Plus 3.5.5
- MySQL 8.0
- Redis 7.x
- JWT

前端
- Vue 3.4.0
- Element Plus 2.4.4
- Vue Router 4.2.5
- Pinia 2.1.7
- Axios 1.6.2
- ECharts 5.4.3

## 功能需求

### 1. 账号与权限模块
- 管理员、店长、顾客三角色
- 登录、注册、退出、密码修改
- 个人资料与钱包余额维护

### 2. 区域与门店模块
- 区域资料维护
- 门店主题、营业时间、负责人、客单价管理
- 顾客查看营业中门店列表

### 3. 店宠与菜单模块
- 店宠档案管理与互动状态维护
- 菜单分类与菜单资料管理
- 顾客查看店宠和菜单推荐

### 4. 座位预约模块
- 门店座位与分区维护
- 顾客按日期时段预约座位
- 店长查看预约并完成接待

### 5. 消费支付模块
- 顾客选择门店菜单下单
- 钱包充值、余额支付、订单取消
- 门店查看订单明细和消费记录

### 6. 评价公告与统计模块
- 顾客订单评价
- 店长回复评价
- 系统公告发布
- 销售趋势、热门菜单、门店排行看板

## 技术设计

### 数据库设计
1. sys_user
2. cafe_area
3. cafe_shop
4. resident_pet
5. menu_category
6. menu_item
7. seat_info
8. reservation_record
9. consume_order
10. payment_record
11. review_record
12. system_notice

### 关键表说明

#### sys_user
- 用户编号、账号密码、昵称、手机号、邮箱
- 钱包余额、累计消费、角色、状态

#### cafe_shop
- 门店编号、所属区域、门店名称
- 门店主题、营业时间、状态、负责人

#### resident_pet
- 店宠编号、所属门店、名称、类型、品种
- 性格、健康状态、互动状态、星级

#### seat_info
- 门店 ID、座位编号、分区名称
- 容量、最低消费、座位状态、预约状态

#### reservation_record
- 预约号、顾客 ID、门店 ID、座位 ID
- 到店日期、时段、人数、携宠说明、预约状态

#### consume_order
- 订单号、顾客 ID、门店 ID、菜单 ID
- 数量、用餐方式、金额、订单状态、支付时间

### API 接口设计

#### 登录
- 请求方式: POST
- 路径: /api/auth/login
- 参数: { "username": "customer", "password": "123456" }

#### 门店分页
- 请求方式: GET
- 路径: /api/shop/list
- 参数: pageNum, pageSize, name, status, areaId

#### 店宠公开列表
- 请求方式: GET
- 路径: /api/pet/public/list

#### 菜单公开列表
- 请求方式: GET
- 路径: /api/menu/public/list

#### 创建预约
- 请求方式: POST
- 路径: /api/reservation/create
- 参数: { "shopId": 1, "seatId": 2, "visitDate": "2026-03-18", "visitTime": "14:00-15:30", "peopleCount": 2 }

#### 创建订单
- 请求方式: POST
- 路径: /api/order/create
- 参数: { "shopId": 1, "menuId": 1, "quantity": 2, "dineType": "IN_STORE" }

#### 余额支付
- 请求方式: POST
- 路径: /api/payment/balance
- 参数: { "orderId": 1 }

#### 评价回复
- 请求方式: PUT
- 路径: /api/review/reply/{id}
- 参数: { "replyContent": "感谢反馈" }

#### 经营看板
- 请求方式: GET
- 路径: /api/statistics/dashboard

## 项目结构

后端
```text
094-backend/
├── sql/
│   └── init.sql
├── src/main/java/com/petcafe/
│   ├── common/
│   ├── config/
│   ├── controller/
│   ├── dto/
│   ├── entity/
│   ├── mapper/
│   ├── service/
│   └── utils/
└── src/main/resources/
    └── application.yml
```

前端
```text
094-frontend/
├── package.json
├── vite.config.js
└── src/
    ├── api/
    ├── router/
    ├── store/
    └── views/
```

## 用户角色
- 管理员：admin，负责用户、区域、门店、公告和统计
- 店长：staff，负责门店、店宠、菜单、座位、预约和评价回复
- 顾客：customer，负责预约、下单、支付、评价和查看店宠门店

## 默认账号
- admin / 123456
- staff / 123456
- customer / 123456
