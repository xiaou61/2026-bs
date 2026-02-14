# 游戏交易系统

## 项目概述
基于 Spring Boot + Vue3 的游戏交易系统，支持游戏道具与账号商品发布、下单购买、收藏评价、申诉处理、公告通知和运营看板。

## 技术栈
后端：Spring Boot 2.7.18 + MyBatis-Plus 3.5.5 + MySQL 8.0 + Redis + JWT + Hutool
前端：Vue 3.4.0 + Element Plus 2.4.4 + Vite + Pinia + Axios + ECharts 5.4.3

## 用户角色
- ADMIN：平台管理员，管理用户、分类、商品、订单、申诉、公告
- USER：普通用户，浏览商品、下单、支付、收藏、评价、发起申诉

## 默认账号
- admin / 123456
- user1 / 123456
- user2 / 123456

## 功能需求

### 1. 认证与用户
- 登录注册
- JWT 鉴权
- 用户资料维护
- 管理员分页管理用户与状态控制

### 2. 游戏分类
- 分类新增、编辑、删除
- 分类启停与排序

### 3. 交易商品
- 商品发布与编辑
- 按分类、关键词、游戏名筛选
- 商品库存、销量、上下架管理

### 4. 订单交易
- 购物下单
- 订单支付、发货、完成、取消
- 用户查看我的订单
- 管理员查看全量订单

### 5. 评价与收藏
- 订单完成后评价
- 商品收藏/取消收藏
- 收藏状态检测与我的收藏列表

### 6. 交易申诉
- 用户发起申诉
- 管理员回复处理
- 订单申诉状态联动

### 7. 公告与看板
- 公告发布管理
- 统计卡片、分类分布、订单趋势、热销商品

## 数据库设计

### user
- id BIGINT PK
- username VARCHAR(50) UNIQUE
- password VARCHAR(100)
- nickname VARCHAR(50)
- avatar VARCHAR(255)
- phone VARCHAR(20)
- email VARCHAR(100)
- role VARCHAR(20)
- status INT
- balance DECIMAL(10,2)
- create_time DATETIME
- update_time DATETIME

### game_category
- id BIGINT PK
- name VARCHAR(50)
- icon VARCHAR(255)
- sort INT
- status INT
- create_time DATETIME

### game_item
- id BIGINT PK
- category_id BIGINT
- seller_id BIGINT
- title VARCHAR(120)
- game_name VARCHAR(80)
- server_name VARCHAR(80)
- item_type VARCHAR(40)
- price DECIMAL(10,2)
- stock INT
- cover VARCHAR(255)
- description TEXT
- trade_mode VARCHAR(30)
- view_count INT
- sold_count INT
- status INT
- create_time DATETIME
- update_time DATETIME

### trade_order
- id BIGINT PK
- order_no VARCHAR(50) UNIQUE
- user_id BIGINT
- item_id BIGINT
- seller_id BIGINT
- quantity INT
- total_price DECIMAL(10,2)
- status INT
- remark VARCHAR(255)
- pay_time DATETIME
- finish_time DATETIME
- create_time DATETIME
- update_time DATETIME

### review
- id BIGINT PK
- order_id BIGINT
- user_id BIGINT
- item_id BIGINT
- rating INT
- content TEXT
- status INT
- create_time DATETIME

### favorite
- id BIGINT PK
- user_id BIGINT
- item_id BIGINT
- create_time DATETIME

### complaint
- id BIGINT PK
- order_id BIGINT
- user_id BIGINT
- target_user_id BIGINT
- order_status_snapshot INT
- type VARCHAR(30)
- content TEXT
- images VARCHAR(500)
- status INT
- reply TEXT
- reply_admin_id BIGINT
- reply_time DATETIME
- create_time DATETIME

### announcement
- id BIGINT PK
- title VARCHAR(100)
- content TEXT
- admin_id BIGINT
- status INT
- create_time DATETIME
- update_time DATETIME

## API 设计
- POST /api/auth/login
- POST /api/auth/register
- GET /api/auth/info
- PUT /api/auth/password
- POST /api/auth/logout

- GET /api/user/page
- POST /api/user
- PUT /api/user
- PUT /api/user/status
- PUT /api/user/profile
- DELETE /api/user/{id}

- GET /api/category/list
- GET /api/category/page
- POST /api/category
- PUT /api/category
- DELETE /api/category/{id}

- GET /api/item/list
- GET /api/item/page
- GET /api/item/{id}
- POST /api/item
- PUT /api/item
- PUT /api/item/status
- DELETE /api/item/{id}

- POST /api/order
- GET /api/order/page
- GET /api/order/my
- GET /api/order/sale
- PUT /api/order/pay/{id}
- PUT /api/order/deliver/{id}
- PUT /api/order/complete/{id}
- PUT /api/order/cancel/{id}

- GET /api/review/item/{itemId}
- GET /api/review/page
- POST /api/review
- DELETE /api/review/{id}

- POST /api/favorite/{itemId}
- GET /api/favorite/my
- GET /api/favorite/check/{itemId}

- GET /api/complaint/page
- GET /api/complaint/my
- POST /api/complaint
- PUT /api/complaint/reply

- GET /api/announcement/list
- GET /api/announcement/page
- POST /api/announcement
- PUT /api/announcement
- DELETE /api/announcement/{id}

- GET /api/dashboard/stats
- GET /api/dashboard/categoryStats
- GET /api/dashboard/orderTrend
- GET /api/dashboard/hotItems
- POST /api/upload
