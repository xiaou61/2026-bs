# 名城小区物业管理系统

## 项目概述
基于 Spring Boot + Vue3 的名城小区物业管理系统，面向小区物业公司、物业人员与业主，提供住户档案、房屋管理、物业缴费、报修工单、投诉建议、访客登记、车位管理、公告通知与运营看板等核心功能，实现物业业务全流程线上化。

## 技术栈
后端：Spring Boot 2.7.18 + MyBatis + PageHelper + MySQL 8.0 + Redis + JWT + Hutool  
前端：Vue 3.4.0 + Element Plus 2.4.4 + Vite + Pinia + Axios + ECharts 5.4.3

## 用户角色
- ADMIN：系统管理员，负责账号与基础数据全局管理
- STAFF：物业人员，负责收费、报修、投诉、访客等业务处理
- OWNER：业主，负责查看房屋、缴费、报修、投诉与访客申请

## 默认账号
- admin / 123456
- staff / 123456
- owner / 123456

## 功能需求

### 1. 用户与认证
- 登录注册、JWT 鉴权、退出登录
- 个人资料维护、密码修改
- 管理员分页维护用户、角色、状态

### 2. 楼栋管理
- 楼栋新增、编辑、删除、状态控制
- 按楼栋名称、状态分页查询

### 3. 房屋管理
- 房屋档案维护（楼栋、单元、门牌、面积）
- 房屋与业主绑定
- 按楼栋/业主/状态分页筛选

### 4. 物业费管理
- 物业费账单创建、编辑、删除
- 物业人员查看全部账单并筛选状态
- 业主查看个人账单并缴费

### 5. 报修工单
- 业主发起报修
- 物业人员接单、处理中、已完成、已取消
- 支持处理回复与进度追踪

### 6. 投诉建议
- 业主提交投诉建议
- 物业人员回复处理结果
- 支持按状态和时间查询

### 7. 访客登记
- 业主提交访客预约登记
- 物业人员审批通过或驳回
- 记录来访时间与审批结果

### 8. 车位管理
- 车位档案新增、编辑、删除
- 车位分配给业主并维护状态

### 9. 公告中心
- 管理员发布、编辑、删除公告
- 全员查看公开公告列表

### 10. 物业看板
- 统计用户数、房屋数、待缴费数、待处理工单数
- 展示近7日收费订单趋势与报修状态分布

## 数据库设计

### sys_user
- id BIGINT PK
- username VARCHAR(50) UNIQUE
- password VARCHAR(100)
- nickname VARCHAR(50)
- phone VARCHAR(20)
- email VARCHAR(100)
- role VARCHAR(20)
- status INT
- last_login_time DATETIME
- create_time DATETIME
- update_time DATETIME

### building
- id BIGINT PK
- name VARCHAR(100)
- floors INT
- status INT
- create_time DATETIME
- update_time DATETIME

### house
- id BIGINT PK
- building_id BIGINT
- unit_no VARCHAR(20)
- room_no VARCHAR(20)
- area DECIMAL(10,2)
- owner_id BIGINT
- status INT
- create_time DATETIME
- update_time DATETIME

### fee_order
- id BIGINT PK
- order_no VARCHAR(50) UNIQUE
- house_id BIGINT
- owner_id BIGINT
- amount DECIMAL(10,2)
- fee_month VARCHAR(20)
- status INT
- pay_time DATETIME
- remark VARCHAR(255)
- creator_id BIGINT
- create_time DATETIME
- update_time DATETIME

### repair_order
- id BIGINT PK
- order_no VARCHAR(50) UNIQUE
- house_id BIGINT
- owner_id BIGINT
- title VARCHAR(120)
- content VARCHAR(500)
- status INT
- assignee_id BIGINT
- reply VARCHAR(255)
- create_time DATETIME
- update_time DATETIME

### complaint
- id BIGINT PK
- order_no VARCHAR(50) UNIQUE
- owner_id BIGINT
- title VARCHAR(120)
- content VARCHAR(500)
- status INT
- reply VARCHAR(255)
- create_time DATETIME
- update_time DATETIME

### visitor_record
- id BIGINT PK
- order_no VARCHAR(50) UNIQUE
- owner_id BIGINT
- visitor_name VARCHAR(50)
- visitor_phone VARCHAR(20)
- visit_time DATETIME
- status INT
- remark VARCHAR(255)
- create_time DATETIME
- update_time DATETIME

### parking_slot
- id BIGINT PK
- slot_no VARCHAR(50) UNIQUE
- location VARCHAR(100)
- owner_id BIGINT
- status INT
- create_time DATETIME
- update_time DATETIME

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

- GET /api/building/list
- GET /api/building/page
- POST /api/building
- PUT /api/building
- DELETE /api/building/{id}

- GET /api/house/list
- GET /api/house/page
- POST /api/house
- PUT /api/house
- DELETE /api/house/{id}

- GET /api/fee/my-page
- GET /api/fee/page
- POST /api/fee
- PUT /api/fee
- PUT /api/fee/pay/{id}
- DELETE /api/fee/{id}

- GET /api/repair/my-page
- GET /api/repair/page
- POST /api/repair
- PUT /api/repair/status
- DELETE /api/repair/{id}

- GET /api/complaint/my-page
- GET /api/complaint/page
- POST /api/complaint
- PUT /api/complaint/reply
- DELETE /api/complaint/{id}

- GET /api/visitor/my-page
- GET /api/visitor/page
- POST /api/visitor
- PUT /api/visitor/status
- DELETE /api/visitor/{id}

- GET /api/parking/list
- GET /api/parking/page
- POST /api/parking
- PUT /api/parking
- DELETE /api/parking/{id}

- GET /api/announcement/list
- GET /api/announcement/page
- POST /api/announcement
- PUT /api/announcement
- DELETE /api/announcement/{id}

- GET /api/dashboard/stats
- GET /api/dashboard/trend
