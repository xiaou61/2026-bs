# 电影订票及评论网站

## 项目概述
基于SpringBoot+Vue3的电影订票及评论网站，提供电影浏览、在线选座购票、影评发布、收藏管理等功能。管理员可管理电影、影院、排片、公告等信息，用户可在线订票和发表评论。

## 技术栈
**后端**: Spring Boot 2.7.18 + MyBatis 3.5.13 + MySQL 8.0 + Redis + JWT + Hutool
**前端**: Vue 3.4.0 + Element Plus 2.4.4 + Vite + Pinia + Axios + ECharts 5.4.3

## 用户角色
- admin（管理员）：管理电影、影院、影厅、排片、订单、评论、公告、用户
- user（普通用户）：浏览电影、选座购票、发表评论、收藏电影、管理订单

## 默认账号
- admin / 123456（管理员）
- user1 / 123456（普通用户）
- user2 / 123456（普通用户）

## 功能需求

### 1. 用户管理
- 注册、登录（JWT认证）
- 个人信息修改
- 管理员管理用户列表（启用/禁用）

### 2. 电影分类
- 分类CRUD（动作、喜剧、科幻、恐怖、爱情、动画、纪录片等）
- 分类排序

### 3. 电影管理
- 电影CRUD（标题、海报、导演、演员、分类、时长、上映日期、简介、评分）
- 电影上下架
- 按分类/关键词搜索

### 4. 影院管理
- 影院CRUD（名称、地址、电话、简介）
- 影院启用/禁用

### 5. 影厅管理
- 影厅CRUD（所属影院、名称、座位行列数、影厅类型）
- 类型：普通厅/IMAX/巨幕/VIP

### 6. 排片管理
- 排片CRUD（电影、影院、影厅、日期、时间、票价）
- 可用座位自动计算
- 按日期/电影/影院筛选

### 7. 订票管理
- 用户选座下单
- 订单号自动生成
- 订单状态流转（待支付→已支付→已完成/已取消）
- 管理员查看所有订单

### 8. 评论管理
- 用户对电影评分+评论
- 管理员审核/删除评论
- 电影评分自动计算

### 9. 收藏管理
- 用户收藏/取消收藏电影
- 收藏列表查看

### 10. 公告管理
- 管理员发布/编辑/删除公告
- 用户查看公告列表

### 11. 数据看板
- 电影分类统计饼图
- 订单趋势折线图
- 票房排行柱状图
- 总用户数、总电影数、总订单数、总收入

## 数据库设计

### user 用户表
- id BIGINT PK AUTO_INCREMENT
- username VARCHAR(50) UNIQUE
- password VARCHAR(100)
- nickname VARCHAR(50)
- avatar VARCHAR(255)
- phone VARCHAR(20)
- email VARCHAR(100)
- role VARCHAR(20) DEFAULT 'user'
- status INT DEFAULT 1
- create_time DATETIME
- update_time DATETIME

### movie_category 电影分类表
- id BIGINT PK AUTO_INCREMENT
- name VARCHAR(50)
- sort INT DEFAULT 0
- status INT DEFAULT 1
- create_time DATETIME

### movie 电影表
- id BIGINT PK AUTO_INCREMENT
- title VARCHAR(100)
- poster VARCHAR(255)
- director VARCHAR(50)
- actors VARCHAR(255)
- category_id BIGINT
- duration INT
- release_date DATE
- description TEXT
- score DECIMAL(3,1) DEFAULT 0
- status INT DEFAULT 1
- create_time DATETIME
- update_time DATETIME

### cinema 影院表
- id BIGINT PK AUTO_INCREMENT
- name VARCHAR(100)
- address VARCHAR(255)
- phone VARCHAR(20)
- description VARCHAR(500)
- status INT DEFAULT 1
- create_time DATETIME
- update_time DATETIME

### hall 影厅表
- id BIGINT PK AUTO_INCREMENT
- cinema_id BIGINT
- name VARCHAR(50)
- seat_rows INT
- seat_cols INT
- hall_type VARCHAR(20)
- status INT DEFAULT 1
- create_time DATETIME

### showtime 排片表
- id BIGINT PK AUTO_INCREMENT
- movie_id BIGINT
- cinema_id BIGINT
- hall_id BIGINT
- show_date DATE
- start_time VARCHAR(10)
- end_time VARCHAR(10)
- price DECIMAL(10,2)
- available_seats INT
- status INT DEFAULT 1
- create_time DATETIME

### ticket_order 订单表
- id BIGINT PK AUTO_INCREMENT
- order_no VARCHAR(50) UNIQUE
- user_id BIGINT
- showtime_id BIGINT
- seats VARCHAR(255)
- seat_count INT
- total_price DECIMAL(10,2)
- status INT DEFAULT 0
- pay_time DATETIME
- create_time DATETIME
- update_time DATETIME

### review 评论表
- id BIGINT PK AUTO_INCREMENT
- user_id BIGINT
- movie_id BIGINT
- rating INT
- content TEXT
- status INT DEFAULT 1
- create_time DATETIME

### favorite 收藏表
- id BIGINT PK AUTO_INCREMENT
- user_id BIGINT
- movie_id BIGINT
- create_time DATETIME

### announcement 公告表
- id BIGINT PK AUTO_INCREMENT
- title VARCHAR(100)
- content TEXT
- admin_id BIGINT
- status INT DEFAULT 1
- create_time DATETIME
- update_time DATETIME

## API接口设计

### 认证接口
- POST /api/auth/login 登录
- POST /api/auth/register 注册
- GET /api/auth/info 获取当前用户信息
- PUT /api/auth/password 修改密码

### 用户管理
- GET /api/user/page 分页查询用户
- PUT /api/user 修改用户
- PUT /api/user/status 修改用户状态
- DELETE /api/user/{id} 删除用户

### 电影分类
- GET /api/movieCategory/page 分页查询
- GET /api/movieCategory/list 全部列表
- POST /api/movieCategory 新增
- PUT /api/movieCategory 修改
- DELETE /api/movieCategory/{id} 删除

### 电影管理
- GET /api/movie/page 分页查询
- GET /api/movie/{id} 电影详情
- POST /api/movie 新增
- PUT /api/movie 修改
- DELETE /api/movie/{id} 删除
- GET /api/movie/hot 热映电影列表

### 影院管理
- GET /api/cinema/page 分页查询
- GET /api/cinema/list 全部列表
- POST /api/cinema 新增
- PUT /api/cinema 修改
- DELETE /api/cinema/{id} 删除

### 影厅管理
- GET /api/hall/page 分页查询
- GET /api/hall/list 根据影院查询影厅
- POST /api/hall 新增
- PUT /api/hall 修改
- DELETE /api/hall/{id} 删除

### 排片管理
- GET /api/showtime/page 分页查询
- GET /api/showtime/movie/{movieId} 根据电影查排片
- POST /api/showtime 新增
- PUT /api/showtime 修改
- DELETE /api/showtime/{id} 删除

### 订票管理
- POST /api/order 创建订单
- GET /api/order/page 分页查询
- GET /api/order/my 我的订单
- PUT /api/order/pay/{id} 支付订单
- PUT /api/order/cancel/{id} 取消订单
- PUT /api/order/complete/{id} 完成订单

### 评论管理
- GET /api/review/movie/{movieId} 电影评论列表
- POST /api/review 发表评论
- DELETE /api/review/{id} 删除评论
- GET /api/review/page 管理员分页查询

### 收藏管理
- POST /api/favorite/{movieId} 收藏/取消收藏
- GET /api/favorite/my 我的收藏
- GET /api/favorite/check/{movieId} 检查是否收藏

### 公告管理
- GET /api/announcement/page 分页查询
- GET /api/announcement/list 最新公告
- POST /api/announcement 新增
- PUT /api/announcement 修改
- DELETE /api/announcement/{id} 删除

### 数据看板
- GET /api/dashboard/stats 统计数据
- GET /api/dashboard/categoryStats 分类统计
- GET /api/dashboard/orderTrend 订单趋势
- GET /api/dashboard/boxOfficeRank 票房排行

## 项目结构

### 后端
```
060-backend/
├── pom.xml
├── sql/init.sql
└── src/main/
    ├── java/com/movie/
    │   ├── MovieApplication.java
    │   ├── common/
    │   │   ├── Result.java
    │   │   ├── BusinessException.java
    │   │   └── GlobalExceptionHandler.java
    │   ├── config/
    │   │   ├── JwtInterceptor.java
    │   │   ├── WebMvcConfig.java
    │   │   ├── MybatisConfig.java
    │   │   └── RedisConfig.java
    │   ├── utils/
    │   │   └── JwtUtils.java
    │   ├── entity/
    │   ├── mapper/
    │   ├── service/
    │   └── controller/
    └── resources/
        ├── application.yml
        └── mapper/
```

### 前端
```
060-frontend/
├── package.json
├── vite.config.js
├── index.html
└── src/
    ├── main.js
    ├── App.vue
    ├── router/index.js
    ├── api/
    │   ├── request.js
    │   └── index.js
    ├── store/user.js
    └── views/
        ├── Login.vue
        ├── Layout.vue
        ├── Dashboard.vue
        ├── user/index.vue
        ├── movie/Category.vue
        ├── movie/index.vue
        ├── movie/Detail.vue
        ├── cinema/index.vue
        ├── cinema/Hall.vue
        ├── showtime/index.vue
        ├── order/index.vue
        ├── order/MyOrder.vue
        ├── review/index.vue
        ├── favorite/index.vue
        └── announcement/index.vue
```
