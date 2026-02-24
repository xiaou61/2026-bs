# 071 - 基于SpringBoot和Vue的共享单车系统

## 项目概述
共享单车管理与骑行服务平台，支持用户扫码骑行、站点管理、智能计费、信用体系等核心功能。系统包含三种角色：管理员、运维人员、普通用户，实现从骑行租借到运维管理的完整业务闭环。

### 技术栈
**后端**
- Spring Boot 2.7.18
- MyBatis 3.5.13（原生XML映射）
- PageHelper 1.4.7
- MySQL 8.0
- Redis
- JWT (jjwt 0.9.1)
- Lombok
- Hutool 5.8.25

**前端**
- Vue 3.4.0
- Element Plus 2.4.4
- Vue Router 4.2.5
- Pinia 2.1.7
- Axios 1.6.2
- ECharts 5.4.3

## 用户角色
- **admin（管理员）**：用户管理、站点管理、单车管理、计费规则、公告管理、数据统计、故障审核
- **operator（运维人员）**：单车管理、站点管理、故障处理、维修记录
- **user（普通用户）**：骑行服务、钱包充值、押金管理、故障上报、信用查看

## 默认账号
- admin / 123456（管理员，余额10000）
- operator / 123456（运维人员）
- user / 123456（普通用户，余额200，已缴押金）
- test / 123456（普通用户，余额50，未缴押金）

## 功能需求

### 1. 用户管理
- 注册登录（用户名+密码）
- JWT认证、角色权限控制
- 个人信息编辑
- 用户列表查询（管理员）
- 用户状态管理（启用/禁用）

### 2. 单车管理
- 单车信息CRUD（编号、类型、状态）
- 单车类型：普通单车、电动单车
- 单车状态：可用/使用中/维修中/报废
- 电动车电量监控
- 单车调度（更换站点）

### 3. 站点管理
- 站点信息CRUD
- 实时车辆数统计
- 站点状态管理（运营中/维护中/已关闭）

### 4. 骑行服务
- 租车：选择站点→选择单车→开始骑行
- 还车：选择还车站点→确认还车→自动计费
- 骑行记录查询
- 骑行中状态查看

### 5. 计费系统
- 阶梯计费：前30分钟1.5元，之后每30分钟1元
- 电动车加价：基础价×1.5倍
- 每日封顶：普通车20元，电动车30元

### 6. 钱包系统
- 余额充值、余额支付
- 流水记录
- 押金缴纳（99元）、押金退还

### 7. 故障上报
- 用户上报故障
- 故障类型：车锁故障/轮胎破损/刹车失灵/车身损坏/其他
- 运维人员处理

### 8. 信用体系
- 初始100分，正常还车+2，超时-5，违规停放-10，损坏-20
- 信用等级：优秀(90+)/良好(70-89)/一般(50-69)/较差(<50)
- 信用分<60禁止骑行

### 9. 公告管理
- 公告CRUD，类型：系统通知/活动公告/维护通知

### 10. 数据统计
- 总览：用户数、单车数、今日订单、今日收入
- 骑行趋势图、收入统计图、站点排行、车型分布

## 数据库设计（10张表）

### user 用户表
id, username, password, phone, real_name, avatar, role, credit_score, balance, deposit_paid, status, create_time, update_time

### bike 单车表
id, bike_no, type, status, station_id, battery_level, create_time, update_time

### station 站点表
id, name, address, longitude, latitude, capacity, current_count, status, create_time, update_time

### ride_order 骑行订单表
id, order_no, user_id, bike_id, start_station_id, end_station_id, start_time, end_time, duration, distance, amount, status, pay_status, create_time

### pricing_rule 计费规则表
id, name, bike_type, base_price, base_duration, extra_price, daily_cap, status, create_time

### wallet_record 钱包流水表
id, user_id, type, amount, balance_after, description, create_time

### fault_report 故障上报表
id, user_id, bike_id, type, description, status, handler_id, handle_result, handle_time, create_time

### credit_record 信用记录表
id, user_id, type, score_change, score_after, description, create_time

### announcement 公告表
id, title, content, type, status, create_time, update_time

### feedback 用户反馈表
id, user_id, type, content, reply, status, create_time

## API接口（50+个）

### /api/user - 用户(7个)
POST /login, POST /register, GET /info, PUT /info, PUT /password, GET /list, PUT /status/{id}

### /api/bike - 单车(7个)
GET /list, GET /{id}, POST /, PUT /{id}, DELETE /{id}, GET /available/{stationId}, PUT /dispatch

### /api/station - 站点(6个)
GET /list, GET /all, GET /{id}, POST /, PUT /{id}, DELETE /{id}

### /api/ride - 骑行(6个)
POST /start, POST /end, GET /current, GET /list, GET /all, GET /{id}

### /api/pricing - 计费(4个)
GET /list, POST /, PUT /{id}, DELETE /{id}

### /api/wallet - 钱包(4个)
POST /recharge, GET /records, POST /deposit, POST /deposit/refund

### /api/fault - 故障(4个)
POST /, GET /list, GET /my, PUT /handle/{id}

### /api/credit - 信用(3个)
GET /score, GET /records, POST /adjust

### /api/announcement - 公告(4个)
GET /list, POST /, PUT /{id}, DELETE /{id}

### /api/feedback - 反馈(4个)
POST /, GET /list, GET /my, PUT /reply/{id}

### /api/statistics - 统计(5个)
GET /overview, GET /ride-trend, GET /income-trend, GET /station-rank, GET /bike-type-ratio

## 前端页面（19个）
1. Login.vue - 登录注册
2. Layout.vue - 系统布局
3. Dashboard.vue - 数据看板
4. User/Profile.vue - 个人中心
5. User/UserList.vue - 用户管理
6. Bike/BikeList.vue - 单车管理
7. Station/StationList.vue - 站点管理
8. Ride/StartRide.vue - 开始骑行
9. Ride/RideStatus.vue - 骑行中
10. Ride/RideHistory.vue - 骑行记录
11. Ride/OrderList.vue - 全部订单
12. Wallet/MyWallet.vue - 我的钱包
13. Wallet/WalletRecords.vue - 流水记录
14. Fault/FaultReport.vue - 故障上报
15. Fault/FaultList.vue - 故障管理
16. Pricing/PricingList.vue - 计费规则
17. Credit/CreditInfo.vue - 信用信息
18. Announcement/AnnouncementList.vue - 公告管理
19. Feedback/FeedbackList.vue - 反馈管理
