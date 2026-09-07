# 077 - 基于Vue的MES生产制造执行系统

## 项目概述
基于 Spring Boot + Vue3 的 MES 生产制造执行系统，面向生产主管与计划员，提供生产任务发布、执行工单流转、支付结算、评价申诉、公告通知与运营看板能力。

## 技术栈
后端：Spring Boot 2.7.18 + MyBatis-Plus 3.5.5 + MySQL 8.0 + Redis + JWT + Hutool
前端：Vue 3.4.0 + Element Plus 2.4.4 + Vite + Pinia + Axios + ECharts 5.4.3

## 用户角色
- ADMIN：平台管理员，管理用户、工艺分类、生产任务、执行工单、申诉、公告和看板
- USER：普通用户（可作为生产方或发起人），发布生产任务、创建执行工单、支付评价、发起申诉

## 默认账号
- admin / 123456
- planner1 / 123456
- planner2 / 123456
- operator1 / 123456

## 功能需求

### 1. 认证与用户
- 登录注册
- JWT 鉴权
- 个人资料维护
- 管理员分页管理用户、状态与余额

### 2. 工艺分类
- 分类新增、编辑、删除
- 分类启停与排序

### 3. 生产任务
- 生产任务发布与编辑
- 按任务标题、分类、任务编码筛选
- 产能上限、完工量、上下架管理

### 4. 执行工单
- 创建执行工单（填写计划日期、计划时间、批次号、产品型号、工艺说明）
- 执行工单支付
- 生产方确认工单
- 发起人确认完成
- 执行工单取消（含管理员介入）
- 发起人查看我的工单，生产方查看工单处理

### 5. 评价与收藏
- 完成工单后评价
- 生产任务收藏/取消收藏
- 收藏状态检测与我的收藏列表

### 6. 售后申诉
- 用户发起申诉
- 管理员回复处理
- 执行工单申诉状态联动

### 7. 公告中心
- 公告发布管理
- 公告列表展示

### 8. 数据看板
- 平台统计卡片
- 工艺分类分布统计
- 工单趋势分析
- 热门生产任务排行

## 执行工单状态
- 0：待支付
- 1：已支付
- 2：已确认
- 3：已完成
- 4：已取消
- 5：申诉中

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

### mes_category
- id BIGINT PK
- name VARCHAR(50)
- icon VARCHAR(255)
- sort INT
- status INT
- create_time DATETIME

### mes_task
- id BIGINT PK
- category_id BIGINT
- seller_id BIGINT
- title VARCHAR(120)
- service_name VARCHAR(80)
- store_name VARCHAR(80)
- vehicle_type VARCHAR(40)
- price DECIMAL(10,2)
- stock INT
- cover VARCHAR(255)
- description TEXT
- booking_type VARCHAR(30)
- view_count INT
- booked_count INT
- status INT
- create_time DATETIME
- update_time DATETIME

### mes_work_order
- id BIGINT PK
- order_no VARCHAR(50) UNIQUE
- user_id BIGINT
- service_id BIGINT
- seller_id BIGINT
- quantity INT
- total_price DECIMAL(10,2)
- status INT
- remark VARCHAR(255)
- appointment_date DATE
- appointment_time TIME
- plate_no VARCHAR(20)
- vehicle_model VARCHAR(80)
- fault_desc VARCHAR(500)
- pay_time DATETIME
- finish_time DATETIME
- create_time DATETIME
- update_time DATETIME

### review
- id BIGINT PK
- order_id BIGINT
- user_id BIGINT
- service_id BIGINT
- rating INT
- content TEXT
- status INT
- create_time DATETIME

### favorite
- id BIGINT PK
- user_id BIGINT
- service_id BIGINT
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

- GET /api/review/item/{serviceId}
- GET /api/review/page
- POST /api/review
- DELETE /api/review/{id}

- POST /api/favorite/{serviceId}
- GET /api/favorite/my
- GET /api/favorite/check/{serviceId}

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
- GET /api/dashboard/hotServices
- POST /api/upload
