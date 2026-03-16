# 铁路订票平台

## 项目概述

### 项目简介
铁路订票平台面向管理员、调度员与乘客三类角色，提供车站维护、列车档案、车厢模板、班次排期、余票查询、在线选座购票、支付出票、退改签处理、乘车人管理、电子客票核验与数据统计等功能，形成从班次发布到售后处理的完整业务闭环。

### 核心功能
- 用户登录注册与角色权限控制
- 车站、列车、车厢模板、班次管理
- 余票查询与座位锁定
- 常用乘车人维护
- 在线下单、余额支付、电子出票
- 退票改签申请与后台审核处理
- 公告发布与运营统计看板

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

### 1. 用户与权限模块
- 管理员、调度员、乘客三角色
- 账号注册、登录、退出、个人资料维护
- 基于 JWT + Redis 的登录校验

### 2. 基础资源模块
- 车站信息管理
- 列车档案管理
- 车厢模板与座席布局管理
- 公告信息发布与展示

### 3. 班次与余票模块
- 班次排期新增、编辑、上下线
- 出发站、到达站、出发日期条件查询
- 座位清单初始化与余票统计
- Redis 座位锁定防止超卖

### 4. 乘客服务模块
- 常用乘车人资料维护
- 车票订单创建
- 余额支付与电子票生成
- 我的订单、我的车票查询

### 5. 售后处理模块
- 退票申请
- 改签申请
- 调度员审核处理
- 退票释放座位、改签结果记录

### 6. 运营统计模块
- 今日销售额与订单数统计
- 近7天销售趋势
- 热门车次排行
- 站点与班次运营概览

## 技术设计

### 数据库设计（12张表）
1. user
2. station
3. train_info
4. carriage_template
5. train_schedule
6. schedule_seat
7. passenger_profile
8. ticket_order
9. train_ticket
10. payment
11. after_sale_record
12. system_notice

### 关键业务规则
- 乘客创建订单前必须先锁定座位
- 座位锁定有效期 15 分钟，未支付订单可取消释放
- 订单支付成功后生成电子票并扣减余票
- 退票审核通过后恢复座位状态
- 改签申请记录原订单信息与目标班次信息，调度员审核后完成流转

### API接口设计

#### 1.1 用户登录
- 请求方式: POST
- 路径: /api/auth/login
- 参数: { "username": "user", "password": "123456" }
- 返回: { "code": 200, "data": { "token": "...", "user": {} } }

#### 1.2 列车分页查询
- 请求方式: GET
- 路径: /api/train/list
- 参数: pageNum, pageSize, trainCode, trainType, status
- 返回: { "code": 200, "data": { "records": [], "total": 0 } }

#### 1.3 班次查询
- 请求方式: GET
- 路径: /api/schedule/list
- 参数: pageNum, pageSize, departureStationId, arrivalStationId, travelDate, status
- 返回: { "code": 200, "data": { "records": [], "total": 0 } }

#### 1.4 座位锁定
- 请求方式: POST
- 路径: /api/seat/lock
- 参数: { "scheduleId": 1, "seatIds": [1, 2] }
- 返回: { "code": 200, "message": "success" }

#### 1.5 创建订单
- 请求方式: POST
- 路径: /api/order/create
- 参数: { "scheduleId": 1, "seatIds": [1, 2], "passengerIds": [1, 2], "contactPhone": "13800000001" }
- 返回: { "code": 200, "data": { "orderId": 1, "orderNo": "RT202603160001" } }

#### 1.6 余额支付
- 请求方式: POST
- 路径: /api/payment/balance
- 参数: { "orderId": 1 }
- 返回: { "code": 200, "data": { "payNo": "PAY202603160001" } }

#### 1.7 提交退改签
- 请求方式: POST
- 路径: /api/after-sale/apply
- 参数: { "orderId": 1, "serviceType": "REFUND", "targetScheduleId": null, "reason": "行程调整" }
- 返回: { "code": 200, "message": "success" }

#### 1.8 退改签审核
- 请求方式: PUT
- 路径: /api/after-sale/review
- 参数: { "id": 1, "reviewStatus": "APPROVED", "reviewRemark": "审核通过" }
- 返回: { "code": 200, "message": "success" }

#### 1.9 电子票核验
- 请求方式: POST
- 路径: /api/ticket/verify
- 参数: { "ticketNo": "T202603160001" }
- 返回: { "code": 200, "message": "success" }

#### 1.10 数据看板
- 请求方式: GET
- 路径: /api/statistics/dashboard
- 参数: 无
- 返回: { "code": 200, "data": { "todaySales": 0, "todayOrders": 0, "totalSchedules": 0, "totalUsers": 0 } }

### 项目结构

后端:
```text
089-backend/
├── sql/
│   └── init.sql
├── src/main/java/com/railway/
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
089-frontend/
├── package.json
├── vite.config.js
├── index.html
└── src/
    ├── main.js
    ├── App.vue
    ├── router/
    ├── api/
    ├── store/
    └── views/
```

## 用户角色
- 管理员：admin，负责用户、车站、列车、车厢模板、公告与统计
- 调度员：dispatcher，负责班次排期、电子票核验、退改签审核与运营查看
- 乘客：user，负责余票查询、选座购票、乘车人维护、订单支付与退改签申请

## 默认账号
- admin / 123456
- dispatcher / 123456
- user / 123456
