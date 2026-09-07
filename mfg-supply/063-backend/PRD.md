# 进销存管理系统

## 项目概述
基于 Spring Boot + Vue3 的进销存管理系统，面向中小企业商品采购、销售、库存管理场景，支持供应商管理、客户管理、商品管理、采购入库、销售出库、库存流水、公告通知与经营看板。

## 技术栈
后端：Spring Boot 2.7.18 + MyBatis 3.0 + PageHelper + MySQL 8.0 + Redis + JWT + Hutool  
前端：Vue 3.4.0 + Element Plus 2.4.4 + Vite + Pinia + Axios + ECharts 5.4.3

## 用户角色
- ADMIN：系统管理员，拥有全部权限，负责账号配置、基础数据维护与运营管理
- STAFF：业务人员，负责采购、销售、库存与日常业务操作

## 默认账号
- admin / 123456
- staff / 123456

## 功能需求

### 1. 认证与用户
- 登录登出、JWT 鉴权
- 个人资料维护、密码修改
- 管理员分页管理用户与状态

### 2. 供应商管理
- 供应商新增、编辑、删除、状态控制
- 按名称、联系人、状态筛选

### 3. 客户管理
- 客户新增、编辑、删除、状态控制
- 按名称、联系人、状态筛选

### 4. 分类与商品管理
- 商品分类管理
- 商品新增、编辑、上下架
- 设置采购价、销售价、库存预警值

### 5. 采购管理
- 创建采购单
- 审核采购单后自动入库
- 采购金额统计

### 6. 销售管理
- 创建销售单
- 审核销售单后自动出库
- 库存不足校验

### 7. 库存流水
- 记录入库/出库流水
- 支持按业务号、商品、类型查询

### 8. 公告管理
- 管理员发布、编辑、删除公告
- 全员查看最新公告

### 9. 经营看板
- 商品总数、供应商总数、客户总数
- 库存预警数、今日采购金额、今日销售金额
- 近7日采购/销售趋势

## 数据库设计

### user
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

### supplier
- id BIGINT PK
- supplier_no VARCHAR(50) UNIQUE
- name VARCHAR(100)
- contact_person VARCHAR(50)
- phone VARCHAR(20)
- address VARCHAR(255)
- status INT
- create_time DATETIME
- update_time DATETIME

### customer
- id BIGINT PK
- customer_no VARCHAR(50) UNIQUE
- name VARCHAR(100)
- contact_person VARCHAR(50)
- phone VARCHAR(20)
- address VARCHAR(255)
- status INT
- create_time DATETIME
- update_time DATETIME

### category
- id BIGINT PK
- name VARCHAR(100)
- sort INT
- status INT
- create_time DATETIME
- update_time DATETIME

### product
- id BIGINT PK
- product_no VARCHAR(50) UNIQUE
- name VARCHAR(100)
- category_id BIGINT
- spec VARCHAR(100)
- unit VARCHAR(20)
- cost_price DECIMAL(10,2)
- sale_price DECIMAL(10,2)
- stock INT
- stock_warn INT
- status INT
- create_time DATETIME
- update_time DATETIME

### purchase_order
- id BIGINT PK
- order_no VARCHAR(50) UNIQUE
- supplier_id BIGINT
- product_id BIGINT
- quantity INT
- price DECIMAL(10,2)
- amount DECIMAL(12,2)
- status INT
- remark VARCHAR(255)
- creator_id BIGINT
- audit_time DATETIME
- create_time DATETIME
- update_time DATETIME

### sale_order
- id BIGINT PK
- order_no VARCHAR(50) UNIQUE
- customer_id BIGINT
- product_id BIGINT
- quantity INT
- price DECIMAL(10,2)
- amount DECIMAL(12,2)
- status INT
- remark VARCHAR(255)
- creator_id BIGINT
- audit_time DATETIME
- create_time DATETIME
- update_time DATETIME

### stock_record
- id BIGINT PK
- biz_type VARCHAR(20)
- biz_no VARCHAR(50)
- product_id BIGINT
- change_qty INT
- before_stock INT
- after_stock INT
- remark VARCHAR(255)
- operator_id BIGINT
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

- GET /api/supplier/page
- POST /api/supplier
- PUT /api/supplier
- DELETE /api/supplier/{id}
- GET /api/supplier/list

- GET /api/customer/page
- POST /api/customer
- PUT /api/customer
- DELETE /api/customer/{id}
- GET /api/customer/list

- GET /api/category/page
- POST /api/category
- PUT /api/category
- DELETE /api/category/{id}
- GET /api/category/list

- GET /api/product/page
- POST /api/product
- PUT /api/product
- DELETE /api/product/{id}
- GET /api/product/list

- GET /api/purchase/page
- POST /api/purchase
- PUT /api/purchase
- PUT /api/purchase/approve/{id}
- DELETE /api/purchase/{id}

- GET /api/sale/page
- POST /api/sale
- PUT /api/sale
- PUT /api/sale/approve/{id}
- DELETE /api/sale/{id}

- GET /api/stock/page

- GET /api/announcement/list
- GET /api/announcement/page
- POST /api/announcement
- PUT /api/announcement
- DELETE /api/announcement/{id}

- GET /api/dashboard/stats
- GET /api/dashboard/trend
