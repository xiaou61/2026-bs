# 091 - 基于 Spring Boot 和 Vue 的电影院会员管理系统

## 项目概述

### 项目简介
电影院会员管理系统面向影院会员运营、门店服务和观影消费场景，覆盖会员档案、影片与门店资源、会员储值、优惠券、消费订单、电子观影码、评价反馈和数据看板等核心流程，帮助影院完成会员拉新、留存、复购和服务管理的一体化运营。

### 核心功能
- 会员注册登录与角色权限控制
- 会员档案、等级、积分、储值余额管理
- 影片、门店、影厅、场次基础资料维护
- 会员优惠券领取与订单抵扣
- 会员消费订单、余额支付、电子观影码管理
- 会员评价反馈与门店审核
- 运营看板与消费趋势分析

### 技术栈

**后端**
- Spring Boot 2.7.18
- MyBatis-Plus 3.5.5
- MySQL 8.0
- Redis 7.x
- JWT

**前端**
- Vue 3.4.0
- Element Plus 2.4.4
- Vue Router 4.2.5
- Pinia 2.1.7
- Axios 1.6.2
- ECharts 5.4.3

## 功能需求

### 1. 账号与权限模块
- 管理员、门店员工、会员三角色
- 登录、退出、注册、密码修改
- JWT + Redis 登录状态校验

### 2. 会员档案模块
- 会员编号、昵称、联系方式管理
- 会员等级、积分、累计储值、累计消费记录
- 会员状态启停与角色维护

### 3. 影院资源模块
- 影片资料维护
- 影院门店维护
- 影厅维护
- 场次排期维护

### 4. 会员运营模块
- 会员储值
- 优惠券模板管理
- 会员券包领取
- 会员订单创建、余额支付、取消、退款、完成
- 电子观影码生成与核销

### 5. 互动与统计模块
- 会员评价反馈
- 管理端审核与删除
- 首页运营看板
- 近 7/15 天消费趋势
- 影片消费热度排行

## 技术设计

### 数据库设计（12 张表）
1. user
2. movie
3. cinema
4. hall
5. showtime
6. seat
7. ticket_order
8. ticket
9. payment
10. comment
11. coupon
12. user_coupon

### 关键表说明

#### user
- 会员编号、账号密码、昵称、手机号、邮箱
- 余额、会员等级、积分、累计储值、累计消费
- 角色、状态、创建时间、更新时间

#### ticket_order
- 订单号、会员 ID、场次 ID
- 影片名称、门店名称、影厅名称、场次时间
- 订单金额、优惠金额、实付金额
- 订单状态、支付时间

#### payment
- 会员 ID、订单 ID、支付流水号
- 支付类型、金额、状态、支付时间
- 同时承载储值记录与订单支付记录

### API 接口设计

#### 1.1 登录
- 请求方式: POST
- 路径: /api/auth/login
- 参数: { "username": "member", "password": "123456" }
- 返回: { "code": 200, "data": { "token": "...", "user": {} } }

#### 1.2 会员分页
- 请求方式: GET
- 路径: /api/user/list
- 参数: pageNum, pageSize, username, phone, role, status
- 返回: { "code": 200, "data": { "records": [], "total": 0 } }

#### 1.3 影片分页
- 请求方式: GET
- 路径: /api/movie/list
- 参数: pageNum, pageSize, title, category, status
- 返回: { "code": 200, "data": { "records": [], "total": 0 } }

#### 1.4 会员创建订单
- 请求方式: POST
- 路径: /api/order/create
- 参数: { "showtimeId": 1, "seatIds": [1, 2], "couponId": 3 }
- 返回: { "code": 200, "data": { "orderId": 1, "orderNo": "TK..." } }

#### 1.5 会员储值
- 请求方式: POST
- 路径: /api/payment/recharge
- 参数: { "amount": 500.00 }
- 返回: { "code": 200, "message": "success" }

#### 1.6 观影码核销
- 请求方式: POST
- 路径: /api/ticket/verify
- 参数: { "ticketNo": "ET202603160001" }
- 返回: { "code": 200, "message": "success" }

#### 1.7 会员评价
- 请求方式: POST
- 路径: /api/comment/add
- 参数: { "movieId": 1, "orderId": 1, "rating": 9, "content": "观影体验很好" }
- 返回: { "code": 200, "message": "success" }

#### 1.8 运营看板
- 请求方式: GET
- 路径: /api/statistics/dashboard
- 参数: 无
- 返回: { "code": 200, "data": { "todaySales": 0, "todayRecharge": 0, "totalMembers": 0 } }

## 项目结构

后端:
```text
091-backend/
├── sql/
│   └── init.sql
├── src/main/java/com/cinema/
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

前端:
```text
091-frontend/
├── package.json
├── vite.config.js
└── src/
    ├── api/
    ├── router/
    ├── store/
    └── views/
```

## 用户角色
- 管理员：admin，负责全局会员运营、基础资料、统计分析
- 门店员工：staff，负责门店日常维护、订单处理、观影码核销、评价审核
- 会员：member，负责注册、储值、领券、订票、支付、查看订单和评价反馈

## 默认账号
- admin / 123456
- staff / 123456
- member / 123456
