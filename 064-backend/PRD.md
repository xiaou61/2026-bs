# 经方药食两用服务平台

## 项目概述
经方药食两用服务平台面向亚健康调理与中医药食同源服务场景，提供经方知识库、药食食材管理、药膳方案推荐、调理服务预约、体质记录、收藏与公告通知等能力，支持用户自助查询与管理员运营管理。

## 技术栈
后端：Spring Boot 2.7.18 + MyBatis-Plus 3.5.5 + MySQL 8.0 + Redis + JWT + Hutool  
前端：Vue 3.4.0 + Element Plus 2.4.4 + Vite + Pinia + Axios + ECharts 5.4.3

## 用户角色
- ADMIN：平台管理员，维护基础数据、审核服务单、发布公告与查看看板
- USER：普通用户，浏览经方和药膳方案、提交体质记录、发起服务预约与收藏

## 默认账号
- admin / 123456
- user / 123456

## 功能需求

### 1. 用户与认证
- 登录、注册、退出登录
- JWT 鉴权和 Redis 会话校验
- 个人资料维护、密码修改
- 管理员分页管理用户

### 2. 食材分类管理
- 分类新增、编辑、删除、启停
- 分类列表查询与排序

### 3. 药食同源食材管理
- 食材档案维护（性味归经、功效、适宜人群、禁忌）
- 按名称、分类、状态筛选

### 4. 经方管理
- 经方信息维护（出处、主治、组成、用法）
- 支持经方公开展示与后台管理

### 5. 药膳方案管理
- 方案维护（适用体质、推荐时段、方案步骤）
- 绑定经方与核心食材

### 6. 调理服务订单
- 用户提交服务预约订单
- 管理员处理订单状态（待处理、已确认、已完成、已取消）
- 用户查看个人订单进度

### 7. 体质记录
- 用户提交体质评估记录与症状描述
- 管理员查看全量记录并给出建议

### 8. 收藏中心
- 用户收藏/取消收藏经方或药膳方案
- 查看个人收藏列表

### 9. 公告中心与数据看板
- 管理员发布公告，用户查看公开公告
- 看板统计用户数、食材数、经方数、方案数、服务单状态分布与7日订单趋势

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

### category
- id BIGINT PK
- name VARCHAR(100)
- sort INT
- status INT
- create_time DATETIME
- update_time DATETIME

### ingredient
- id BIGINT PK
- name VARCHAR(100)
- category_id BIGINT
- nature_taste VARCHAR(120)
- meridian VARCHAR(120)
- efficacy VARCHAR(500)
- suitable_people VARCHAR(255)
- taboo_people VARCHAR(255)
- status INT
- create_time DATETIME
- update_time DATETIME

### formula_info
- id BIGINT PK
- name VARCHAR(120)
- source VARCHAR(255)
- indication VARCHAR(500)
- composition TEXT
- usage_method TEXT
- status INT
- create_time DATETIME
- update_time DATETIME

### meal_plan
- id BIGINT PK
- name VARCHAR(120)
- formula_id BIGINT
- ingredient_summary VARCHAR(500)
- suitable_constitution VARCHAR(120)
- meal_time VARCHAR(60)
- steps TEXT
- status INT
- create_time DATETIME
- update_time DATETIME

### service_order
- id BIGINT PK
- order_no VARCHAR(50) UNIQUE
- user_id BIGINT
- plan_id BIGINT
- contact_name VARCHAR(50)
- contact_phone VARCHAR(20)
- appointment_date DATE
- status INT
- remark VARCHAR(255)
- admin_reply VARCHAR(255)
- create_time DATETIME
- update_time DATETIME

### constitution_record
- id BIGINT PK
- user_id BIGINT
- constitution_type VARCHAR(100)
- symptom_desc VARCHAR(500)
- suggestion VARCHAR(500)
- status INT
- create_time DATETIME
- update_time DATETIME

### favorite
- id BIGINT PK
- user_id BIGINT
- target_type VARCHAR(20)
- target_id BIGINT
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

- GET /api/ingredient/list
- GET /api/ingredient/page
- POST /api/ingredient
- PUT /api/ingredient
- DELETE /api/ingredient/{id}

- GET /api/formula/list
- GET /api/formula/page
- POST /api/formula
- PUT /api/formula
- DELETE /api/formula/{id}

- GET /api/plan/list
- GET /api/plan/page
- POST /api/plan
- PUT /api/plan
- DELETE /api/plan/{id}

- GET /api/service/my-page
- GET /api/service/page
- POST /api/service
- PUT /api/service/status
- DELETE /api/service/{id}

- GET /api/constitution/my-page
- GET /api/constitution/page
- POST /api/constitution
- PUT /api/constitution/reply

- GET /api/favorite/my-page
- POST /api/favorite/toggle

- GET /api/announcement/list
- GET /api/announcement/page
- POST /api/announcement
- PUT /api/announcement
- DELETE /api/announcement/{id}

- GET /api/dashboard/stats
- GET /api/dashboard/order-trend
