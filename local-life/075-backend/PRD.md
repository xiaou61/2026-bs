# 075 - 汽车维修预约服务系统

## 项目概述
基于 Spring Boot + Vue3 的汽车维修预约服务系统，面向维修门店与车主，提供维修项目发布、在线预约、支付结算、评价申诉、公告通知与运营看板能力。

## 技术栈
后端：Spring Boot 2.7.18 + MyBatis-Plus 3.5.5 + MySQL 8.0 + Redis + JWT + Hutool
前端：Vue 3.4.0 + Element Plus 2.4.4 + Vite + Pinia + Axios + ECharts 5.4.3

## 用户角色
- ADMIN：平台管理员，管理用户、分类、维修项目、预约单、申诉、公告和看板
- USER：普通用户（可作为门店服务方或车主），发布维修项目、创建预约、支付评价、发起申诉

## 默认账号
- admin / 123456
- seller1 / 123456
- seller2 / 123456
- buyer1 / 123456

## 功能需求

### 1. 认证与用户
- 登录注册
- JWT 鉴权
- 个人资料维护
- 管理员分页管理用户、状态与余额

### 2. 维修分类
- 分类新增、编辑、删除
- 分类启停与排序

### 3. 维修项目
- 维修项目发布与编辑
- 按标题、分类、服务名称筛选
- 项目库存名额、预约量、上下架管理

### 4. 预约交易
- 创建预约（选择预约日期、预约时间、车牌号、车型、故障描述）
- 预约支付
- 服务方确认预约
- 车主确认完成
- 预约取消（含管理员介入）
- 车主查看我的预约，服务方查看预约工单

### 5. 评价与收藏
- 完成预约后评价
- 维修项目收藏/取消收藏
- 收藏状态检测与我的收藏列表

### 6. 售后申诉
- 用户发起申诉
- 管理员回复处理
- 预约单申诉状态联动

### 7. 公告中心
- 公告发布管理
- 公告列表展示

### 8. 数据看板
- 平台统计卡片
- 维修分类分布统计
- 预约趋势分析
- 热门维修项目排行

## 预约单状态
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

### repair_category
- id BIGINT PK
- name VARCHAR(50)
- icon VARCHAR(255)
- sort INT
- status INT
- create_time DATETIME

### repair_service
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

### repair_appointment
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
