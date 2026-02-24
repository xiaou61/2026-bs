# 070 - 基于SpringBoot和Vue的最优网络购票系统

## 项目概述

### 项目简介
一个功能完善的在线购票系统，支持电影、演出等多种票务场景，提供用户在线选座、购票、支付、评价等完整流程。系统分为用户端和管理端，实现票务的全生命周期管理。

### 核心功能
- 用户注册登录、个人中心管理
- 电影/演出信息浏览与搜索
- 在线选座与实时座位状态
- 订单创建与支付流程
- 票务管理与核销
- 评论评分系统
- 优惠券与促销活动
- 数据统计与报表分析

### 技术栈
**后端**
- Spring Boot 2.7.x
- MyBatis-Plus 3.5.x
- MySQL 8.0
- Redis 7.x
- JWT认证
- Lombok

**前端**
- Vue 3.4.0
- Element Plus 2.4.4
- Vue Router 4.2.5
- Pinia 2.1.7
- Axios 1.6.2
- ECharts 5.4.3

---

## 功能需求

### 1. 用户管理模块
**用户注册登录**
- 用户名密码注册
- 手机号注册
- 登录认证（JWT Token）
- 找回密码

**个人中心**
- 个人信息查看与修改
- 头像上传
- 密码修改
- 收货地址管理

**用户列表（管理端）**
- 用户列表查询（分页、搜索）
- 用户状态管理（启用/禁用）
- 用户详情查看

---

### 2. 电影/演出管理模块
**影片信息**
- 影片基本信息（标题、类型、时长、导演、演员、简介）
- 影片海报与剧照
- 影片状态（即将上映、热映中、已下线）
- 影片评分与评论数统计

**演出信息**
- 演出基本信息（名称、类型、时长、演出者、简介）
- 演出海报
- 演出状态

**管理功能**
- 影片/演出CRUD
- 上架/下架管理
- 推荐设置

---

### 3. 影院/场馆管理模块
**影院信息**
- 影院名称、地址、联系方式
- 营业时间
- 设施说明
- 影院状态

**影厅/演出厅**
- 影厅名称、类型（普通厅/IMAX/4D等）
- 座位总数
- 座位布局配置

**管理功能**
- 影院CRUD
- 影厅CRUD
- 座位布局可视化配置

---

### 4. 场次管理模块
**场次信息**
- 关联影片/演出
- 关联影厅
- 放映时间（开始时间、结束时间）
- 票价设置
- 场次状态（预售/售票中/已结束）

**管理功能**
- 场次CRUD
- 批量排期
- 场次状态管理

---

### 5. 座位管理模块
**座位信息**
- 座位编号（行号、列号）
- 座位类型（普通座/情侣座/VIP座）
- 座位状态（可售/已售/维护中）
- 价格差异

**座位布局**
- 可视化座位图
- 实时座位状态更新
- 座位锁定机制（15分钟）

---

### 6. 订单管理模块
**订单信息**
- 订单编号
- 用户信息
- 场次信息
- 座位信息
- 订单金额
- 订单状态（待支付/已支付/已取消/已退款/已完成）
- 创建时间、支付时间

**订单流程**
- 创建订单（锁定座位）
- 订单支付
- 订单取消（释放座位）
- 订单退款
- 订单完成（观影后）

**管理功能**
- 订单列表查询
- 订单详情查看
- 订单状态管理
- 退款审核

---

### 7. 支付管理模块
**支付方式**
- 余额支付
- 模拟第三方支付

**支付流程**
- 创建支付单
- 支付回调处理
- 支付状态同步

**余额管理**
- 用户余额充值
- 余额变动记录

---

### 8. 票务管理模块
**电子票**
- 票券编号
- 二维码生成
- 票券状态（未使用/已使用/已过期）

**核销功能**
- 扫码核销
- 手动核销
- 核销记录

---

### 9. 评论评分模块
**评论功能**
- 影片/演出评论
- 评分（1-5星）
- 评论内容
- 评论时间

**管理功能**
- 评论审核
- 评论删除
- 评论列表

---

### 10. 优惠券模块
**优惠券类型**
- 满减券
- 折扣券
- 通用券/指定券

**优惠券管理**
- 优惠券CRUD
- 优惠券发放
- 使用规则设置
- 有效期管理

**用户端**
- 优惠券领取
- 我的优惠券
- 优惠券使用

---

### 11. 活动管理模块
**活动信息**
- 活动标题、内容
- 活动时间
- 活动规则
- 关联优惠券

**管理功能**
- 活动CRUD
- 活动发布

---

### 12. 数据统计模块
**销售统计**
- 今日销售额、订单数
- 近7天/30天销售趋势
- 影片销售排行
- 影院销售排行

**用户统计**
- 用户总数、新增用户
- 用户活跃度
- 用户地区分布

**票房统计**
- 实时票房
- 影片票房排行
- 场次上座率

---

## 技术设计

### 数据库设计

**表1: user (用户表)**
```sql
id BIGINT PRIMARY KEY AUTO_INCREMENT
username VARCHAR(50) UNIQUE NOT NULL
password VARCHAR(100) NOT NULL
nickname VARCHAR(50)
phone VARCHAR(20) UNIQUE
email VARCHAR(100)
avatar VARCHAR(255)
balance DECIMAL(10,2) DEFAULT 0
role VARCHAR(20) DEFAULT 'user'
status TINYINT DEFAULT 1
create_time DATETIME
update_time DATETIME
```
索引: username, phone, role

**表2: movie (电影/演出表)**
```sql
id BIGINT PRIMARY KEY AUTO_INCREMENT
title VARCHAR(100) NOT NULL
type VARCHAR(20)
category VARCHAR(50)
duration INT
director VARCHAR(100)
actors TEXT
poster VARCHAR(255)
description TEXT
rating DECIMAL(3,1) DEFAULT 0
comment_count INT DEFAULT 0
status VARCHAR(20)
is_recommend TINYINT DEFAULT 0
release_date DATE
create_time DATETIME
update_time DATETIME
```
索引: title, status, is_recommend

**表3: cinema (影院表)**
```sql
id BIGINT PRIMARY KEY AUTO_INCREMENT
name VARCHAR(100) NOT NULL
address VARCHAR(255)
phone VARCHAR(20)
business_hours VARCHAR(50)
facilities TEXT
status TINYINT DEFAULT 1
create_time DATETIME
update_time DATETIME
```
索引: name, status

**表4: hall (影厅表)**
```sql
id BIGINT PRIMARY KEY AUTO_INCREMENT
cinema_id BIGINT NOT NULL
name VARCHAR(50) NOT NULL
type VARCHAR(20)
total_seats INT
seat_layout JSON
status TINYINT DEFAULT 1
create_time DATETIME
update_time DATETIME
```
索引: cinema_id, status

**表5: showtime (场次表)**
```sql
id BIGINT PRIMARY KEY AUTO_INCREMENT
movie_id BIGINT NOT NULL
hall_id BIGINT NOT NULL
cinema_id BIGINT NOT NULL
start_time DATETIME NOT NULL
end_time DATETIME NOT NULL
price DECIMAL(10,2) NOT NULL
status VARCHAR(20)
available_seats INT
create_time DATETIME
update_time DATETIME
```
索引: movie_id, hall_id, start_time, status

**表6: seat (座位表)**
```sql
id BIGINT PRIMARY KEY AUTO_INCREMENT
showtime_id BIGINT NOT NULL
row_num INT NOT NULL
col_num INT NOT NULL
seat_type VARCHAR(20)
price DECIMAL(10,2)
status VARCHAR(20)
create_time DATETIME
update_time DATETIME
```
索引: showtime_id, status

**表7: orders (订单表)**
```sql
id BIGINT PRIMARY KEY AUTO_INCREMENT
order_no VARCHAR(50) UNIQUE NOT NULL
user_id BIGINT NOT NULL
showtime_id BIGINT NOT NULL
movie_title VARCHAR(100)
cinema_name VARCHAR(100)
hall_name VARCHAR(50)
show_time DATETIME
seat_info VARCHAR(255)
total_amount DECIMAL(10,2)
discount_amount DECIMAL(10,2) DEFAULT 0
pay_amount DECIMAL(10,2)
coupon_id BIGINT
status VARCHAR(20)
pay_time DATETIME
create_time DATETIME
update_time DATETIME
```
索引: order_no, user_id, status, create_time

**表8: ticket (票务表)**
```sql
id BIGINT PRIMARY KEY AUTO_INCREMENT
order_id BIGINT NOT NULL
ticket_no VARCHAR(50) UNIQUE NOT NULL
qr_code VARCHAR(255)
status VARCHAR(20)
use_time DATETIME
create_time DATETIME
update_time DATETIME
```
索引: ticket_no, order_id, status

**表9: payment (支付表)**
```sql
id BIGINT PRIMARY KEY AUTO_INCREMENT
order_id BIGINT NOT NULL
pay_no VARCHAR(50) UNIQUE NOT NULL
pay_type VARCHAR(20)
pay_amount DECIMAL(10,2)
status VARCHAR(20)
pay_time DATETIME
create_time DATETIME
update_time DATETIME
```
索引: pay_no, order_id, status

**表10: comment (评论表)**
```sql
id BIGINT PRIMARY KEY AUTO_INCREMENT
movie_id BIGINT NOT NULL
user_id BIGINT NOT NULL
order_id BIGINT
rating INT
content TEXT
status VARCHAR(20)
create_time DATETIME
update_time DATETIME
```
索引: movie_id, user_id, status

**表11: coupon (优惠券表)**
```sql
id BIGINT PRIMARY KEY AUTO_INCREMENT
name VARCHAR(100) NOT NULL
type VARCHAR(20)
discount_type VARCHAR(20)
discount_value DECIMAL(10,2)
min_amount DECIMAL(10,2)
total_count INT
used_count INT DEFAULT 0
start_time DATETIME
end_time DATETIME
status TINYINT DEFAULT 1
create_time DATETIME
update_time DATETIME
```
索引: type, status

**表12: user_coupon (用户优惠券表)**
```sql
id BIGINT PRIMARY KEY AUTO_INCREMENT
user_id BIGINT NOT NULL
coupon_id BIGINT NOT NULL
status VARCHAR(20)
use_time DATETIME
expire_time DATETIME
create_time DATETIME
update_time DATETIME
```
索引: user_id, coupon_id, status

---

### API接口设计

#### 1. 用户模块

**1.1 用户注册**
- 请求方式: POST
- 路径: /api/user/register
- 参数: {username, password, phone}
- 返回: {code, message, data}

**1.2 用户登录**
- 请求方式: POST
- 路径: /api/user/login
- 参数: {username, password}
- 返回: {code, message, data: {token, userInfo}}

**1.3 获取用户信息**
- 请求方式: GET
- 路径: /api/user/info
- 参数: Header: Authorization
- 返回: {code, message, data}

**1.4 更新用户信息**
- 请求方式: PUT
- 路径: /api/user/update
- 参数: {nickname, phone, email}
- 返回: {code, message, data}

**1.5 用户列表**
- 请求方式: GET
- 路径: /api/user/list
- 参数: {pageNum, pageSize, username, phone}
- 返回: {code, message, data: {records, total}}

---

#### 2. 电影模块

**2.1 电影列表**
- 请求方式: GET
- 路径: /api/movie/list
- 参数: {pageNum, pageSize, title, status, category}
- 返回: {code, message, data: {records, total}}

**2.2 电影详情**
- 请求方式: GET
- 路径: /api/movie/{id}
- 参数: 路径参数 id
- 返回: {code, message, data}

**2.3 新增电影**
- 请求方式: POST
- 路径: /api/movie/add
- 参数: {title, type, category, duration, director, actors, poster, description, releaseDate}
- 返回: {code, message, data}

**2.4 更新电影**
- 请求方式: PUT
- 路径: /api/movie/update
- 参数: {id, ...}
- 返回: {code, message, data}

**2.5 删除电影**
- 请求方式: DELETE
- 路径: /api/movie/{id}
- 参数: 路径参数 id
- 返回: {code, message, data}

**2.6 推荐电影列表**
- 请求方式: GET
- 路径: /api/movie/recommend
- 参数: 无
- 返回: {code, message, data}

---

#### 3. 影院模块

**3.1 影院列表**
- 请求方式: GET
- 路径: /api/cinema/list
- 参数: {pageNum, pageSize, name}
- 返回: {code, message, data: {records, total}}

**3.2 影院详情**
- 请求方式: GET
- 路径: /api/cinema/{id}
- 参数: 路径参数 id
- 返回: {code, message, data}

**3.3 影院CRUD**
- 新增: POST /api/cinema/add
- 更新: PUT /api/cinema/update
- 删除: DELETE /api/cinema/{id}

---

#### 4. 影厅模块

**4.1 影厅列表**
- 请求方式: GET
- 路径: /api/hall/list
- 参数: {pageNum, pageSize, cinemaId}
- 返回: {code, message, data: {records, total}}

**4.2 影厅CRUD**
- 新增: POST /api/hall/add
- 更新: PUT /api/hall/update
- 删除: DELETE /api/hall/{id}

---

#### 5. 场次模块

**5.1 场次列表**
- 请求方式: GET
- 路径: /api/showtime/list
- 参数: {pageNum, pageSize, movieId, cinemaId, date}
- 返回: {code, message, data: {records, total}}

**5.2 场次详情**
- 请求方式: GET
- 路径: /api/showtime/{id}
- 参数: 路径参数 id
- 返回: {code, message, data}

**5.3 场次CRUD**
- 新增: POST /api/showtime/add
- 更新: PUT /api/showtime/update
- 删除: DELETE /api/showtime/{id}

---

#### 6. 座位模块

**6.1 获取场次座位**
- 请求方式: GET
- 路径: /api/seat/showtime/{showtimeId}
- 参数: 路径参数 showtimeId
- 返回: {code, message, data}

**6.2 锁定座位**
- 请求方式: POST
- 路径: /api/seat/lock
- 参数: {showtimeId, seatIds}
- 返回: {code, message, data}

---

#### 7. 订单模块

**7.1 创建订单**
- 请求方式: POST
- 路径: /api/order/create
- 参数: {showtimeId, seatIds, couponId}
- 返回: {code, message, data: {orderId, orderNo}}

**7.2 订单列表**
- 请求方式: GET
- 路径: /api/order/list
- 参数: {pageNum, pageSize, status}
- 返回: {code, message, data: {records, total}}

**7.3 订单详情**
- 请求方式: GET
- 路径: /api/order/{id}
- 参数: 路径参数 id
- 返回: {code, message, data}

**7.4 取消订单**
- 请求方式: PUT
- 路径: /api/order/cancel/{id}
- 参数: 路径参数 id
- 返回: {code, message, data}

**7.5 我的订单**
- 请求方式: GET
- 路径: /api/order/my
- 参数: {pageNum, pageSize, status}
- 返回: {code, message, data: {records, total}}

---

#### 8. 支付模块

**8.1 创建支付**
- 请求方式: POST
- 路径: /api/payment/create
- 参数: {orderId, payType}
- 返回: {code, message, data}

**8.2 余额支付**
- 请求方式: POST
- 路径: /api/payment/balance
- 参数: {orderId}
- 返回: {code, message, data}

**8.3 余额充值**
- 请求方式: POST
- 路径: /api/payment/recharge
- 参数: {amount}
- 返回: {code, message, data}

---

#### 9. 票务模块

**9.1 我的票券**
- 请求方式: GET
- 路径: /api/ticket/my
- 参数: {pageNum, pageSize, status}
- 返回: {code, message, data: {records, total}}

**9.2 票券详情**
- 请求方式: GET
- 路径: /api/ticket/{id}
- 参数: 路径参数 id
- 返回: {code, message, data}

**9.3 核销票券**
- 请求方式: POST
- 路径: /api/ticket/verify
- 参数: {ticketNo}
- 返回: {code, message, data}

---

#### 10. 评论模块

**10.1 电影评论列表**
- 请求方式: GET
- 路径: /api/comment/movie/{movieId}
- 参数: {pageNum, pageSize}
- 返回: {code, message, data: {records, total}}

**10.2 添加评论**
- 请求方式: POST
- 路径: /api/comment/add
- 参数: {movieId, orderId, rating, content}
- 返回: {code, message, data}

**10.3 删除评论**
- 请求方式: DELETE
- 路径: /api/comment/{id}
- 参数: 路径参数 id
- 返回: {code, message, data}

---

#### 11. 优惠券模块

**11.1 优惠券列表**
- 请求方式: GET
- 路径: /api/coupon/list
- 参数: {pageNum, pageSize, status}
- 返回: {code, message, data: {records, total}}

**11.2 可用优惠券**
- 请求方式: GET
- 路径: /api/coupon/available
- 参数: 无
- 返回: {code, message, data}

**11.3 领取优惠券**
- 请求方式: POST
- 路径: /api/coupon/receive/{id}
- 参数: 路径参数 id
- 返回: {code, message, data}

**11.4 我的优惠券**
- 请求方式: GET
- 路径: /api/coupon/my
- 参数: {status}
- 返回: {code, message, data}

**11.5 优惠券CRUD (管理端)**
- 新增: POST /api/coupon/add
- 更新: PUT /api/coupon/update
- 删除: DELETE /api/coupon/{id}

---

#### 12. 统计模块

**12.1 首页统计**
- 请求方式: GET
- 路径: /api/statistics/dashboard
- 参数: 无
- 返回: {code, message, data: {todaySales, todayOrders, totalUsers, totalMovies}}

**12.2 销售趋势**
- 请求方式: GET
- 路径: /api/statistics/sales-trend
- 参数: {days}
- 返回: {code, message, data}

**12.3 票房排行**
- 请求方式: GET
- 路径: /api/statistics/box-office
- 参数: 无
- 返回: {code, message, data}

---

### 项目结构

#### 后端结构
```
ticket-system-backend/
├── sql/
│   └── init.sql
├── src/main/java/com/ticket/
│   ├── TicketSystemApplication.java
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
│   │   ├── JwtUtils.java
│   │   └── RedisUtils.java
│   ├── entity/
│   │   ├── User.java
│   │   ├── Movie.java
│   │   ├── Cinema.java
│   │   ├── Hall.java
│   │   ├── Showtime.java
│   │   ├── Seat.java
│   │   ├── Order.java
│   │   ├── Ticket.java
│   │   ├── Payment.java
│   │   ├── Comment.java
│   │   ├── Coupon.java
│   │   └── UserCoupon.java
│   ├── mapper/
│   │   ├── UserMapper.java
│   │   ├── MovieMapper.java
│   │   ├── CinemaMapper.java
│   │   ├── HallMapper.java
│   │   ├── ShowtimeMapper.java
│   │   ├── SeatMapper.java
│   │   ├── OrderMapper.java
│   │   ├── TicketMapper.java
│   │   ├── PaymentMapper.java
│   │   ├── CommentMapper.java
│   │   ├── CouponMapper.java
│   │   └── UserCouponMapper.java
│   ├── service/
│   │   ├── UserService.java
│   │   ├── MovieService.java
│   │   ├── CinemaService.java
│   │   ├── HallService.java
│   │   ├── ShowtimeService.java
│   │   ├── SeatService.java
│   │   ├── OrderService.java
│   │   ├── TicketService.java
│   │   ├── PaymentService.java
│   │   ├── CommentService.java
│   │   ├── CouponService.java
│   │   └── StatisticsService.java
│   ├── controller/
│   │   ├── UserController.java
│   │   ├── MovieController.java
│   │   ├── CinemaController.java
│   │   ├── HallController.java
│   │   ├── ShowtimeController.java
│   │   ├── SeatController.java
│   │   ├── OrderController.java
│   │   ├── TicketController.java
│   │   ├── PaymentController.java
│   │   ├── CommentController.java
│   │   ├── CouponController.java
│   │   └── StatisticsController.java
│   └── dto/
│       ├── LoginDTO.java
│       ├── RegisterDTO.java
│       └── OrderCreateDTO.java
└── src/main/resources/
    └── application.yml
```

#### 前端结构
```
ticket-system-frontend/
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
    ├── views/
    │   ├── Login.vue
    │   ├── Layout.vue
    │   ├── Dashboard.vue
    │   ├── User/
    │   │   ├── Profile.vue
    │   │   └── UserList.vue
    │   ├── Movie/
    │   │   ├── MovieList.vue
    │   │   └── MovieDetail.vue
    │   ├── Cinema/
    │   │   ├── CinemaList.vue
    │   │   └── HallList.vue
    │   ├── Showtime/
    │   │   ├── ShowtimeList.vue
    │   │   └── SeatSelection.vue
    │   ├── Order/
    │   │   ├── OrderList.vue
    │   │   ├── MyOrders.vue
    │   │   └── OrderDetail.vue
    │   ├── Ticket/
    │   │   ├── MyTickets.vue
    │   │   └── TicketDetail.vue
    │   ├── Comment/
    │   │   └── CommentList.vue
    │   ├── Coupon/
    │   │   ├── CouponList.vue
    │   │   └── MyCoupons.vue
    │   └── Statistics/
    │       └── Dashboard.vue
    └── components/
        ├── SeatMap.vue
        └── MovieCard.vue
```

---

## 用户角色

### 普通用户 (user)
- 浏览电影/演出信息
- 在线选座购票
- 查看我的订单
- 查看我的票券
- 评论评分
- 领取使用优惠券
- 个人中心管理

### 管理员 (admin)
- 用户管理
- 电影/演出管理
- 影院/影厅管理
- 场次管理
- 订单管理
- 票务管理
- 评论管理
- 优惠券管理
- 数据统计查看

---

## 默认账号

**管理员账号**
- 用户名: admin
- 密码: 123456

**普通用户账号**
- 用户名: user
- 密码: 123456

**测试用户账号**
- 用户名: test
- 密码: 123456

---

## 特色功能

### 实时座位状态
利用Redis缓存实现座位实时状态更新，支持座位锁定机制，防止超卖。

### 智能推荐
基于用户观影历史和评分，推荐相似类型电影。

### 优惠策略
支持多种优惠券类型，灵活的优惠规则配置。

### 数据统计
实时票房统计，销售趋势分析，为运营决策提供数据支持。

### 电子票券
生成二维码电子票，支持扫码核销，无纸化购票体验。
