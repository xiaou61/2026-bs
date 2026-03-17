# 093 - 基于 Spring Boot 和 Vue 的自助售货管理系统

## 项目概述

### 项目简介
自助售货管理系统面向校园、园区、写字楼等设备零售场景，覆盖账号权限、点位设备、商品分类、货道库存、补货维护、顾客下单、余额支付、出货记录、故障反馈和经营看板等核心流程，帮助运营方完成设备管理与零售业务的一体化管理。

### 核心功能
- 管理员、补货员、顾客三角色登录与权限控制
- 点位信息、售货机设备与负责人管理
- 商品分类、商品资料、价格与库存预警维护
- 设备货道库存管理与补货记录登记
- 顾客下单、余额支付、自动出货与取货码查看
- 故障反馈、处理闭环与系统公告发布
- 销售趋势、热销商品、设备排行经营看板

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

### 1. 账号与权限模块
- 管理员、补货员、顾客三角色
- 登录、退出、注册、密码修改
- JWT + Redis 登录状态校验

### 2. 点位设备模块
- 点位信息维护
- 售货机编号、温区、在线状态、负责人管理
- 顾客查看可用设备列表

### 3. 商品库存模块
- 商品分类管理
- 商品资料、品牌、规格、售价、预警值维护
- 货道绑定商品、设置容量、库存、状态

### 4. 补货与出货模块
- 补货员按货道登记补货
- 系统自动扣减货道库存
- 出货记录成功、失败、异常状态跟踪

### 5. 订单支付模块
- 顾客选择设备货道下单
- 余额支付、订单取消、订单查询
- 支付成功生成取货码并写出货记录

### 6. 反馈与看板模块
- 故障反馈提交与处理
- 系统公告发布
- 销售概览、销售趋势、热销商品、设备排行

## 技术设计

### 数据库设计（12 张表）
1. sys_user
2. machine_location
3. vending_machine
4. product_category
5. product_info
6. machine_slot
7. replenish_record
8. sale_order
9. payment_record
10. shipment_record
11. fault_report
12. system_notice

### 关键表说明

#### sys_user
- 用户编号、账号密码、昵称、手机号、邮箱
- 钱包余额、累计消费、角色、状态
- 创建时间、更新时间

#### vending_machine
- 设备编号、点位 ID、设备名称
- 温区类型、在线状态、负责人信息
- 最近补货时间、最近在线时间

#### machine_slot
- 设备 ID、货道编号、商品 ID
- 容量、当前库存、货道状态
- 最近同步时间

#### sale_order
- 订单号、顾客 ID、设备 ID、货道 ID、商品 ID
- 购买数量、订单金额、实付金额、取货码
- 订单状态、支付时间

### API 接口设计

#### 1.1 登录
- 请求方式: POST
- 路径: /api/auth/login
- 参数: { "username": "customer", "password": "123456" }
- 返回: { "code": 200, "data": { "token": "...", "user": {} } }

#### 1.2 用户分页
- 请求方式: GET
- 路径: /api/user/list
- 参数: pageNum, pageSize, username, phone, role, status
- 返回: { "code": 200, "data": { "records": [], "total": 0 } }

#### 1.3 设备分页
- 请求方式: GET
- 路径: /api/machine/list
- 参数: pageNum, pageSize, name, status, locationId
- 返回: { "code": 200, "data": { "records": [], "total": 0 } }

#### 1.4 商品分页
- 请求方式: GET
- 路径: /api/product/list
- 参数: pageNum, pageSize, name, categoryId, status
- 返回: { "code": 200, "data": { "records": [], "total": 0 } }

#### 1.5 顾客创建订单
- 请求方式: POST
- 路径: /api/order/create
- 参数: { "machineId": 1, "slotId": 2, "quantity": 1 }
- 返回: { "code": 200, "data": { "orderId": 1, "orderNo": "SO..." } }

#### 1.6 余额支付
- 请求方式: POST
- 路径: /api/payment/balance
- 参数: { "orderId": 1 }
- 返回: { "code": 200, "message": "success" }

#### 1.7 补货登记
- 请求方式: POST
- 路径: /api/replenish/save
- 参数: { "machineId": 1, "slotId": 2, "quantity": 10, "remark": "常规补货" }
- 返回: { "code": 200, "message": "success" }

#### 1.8 经营看板
- 请求方式: GET
- 路径: /api/statistics/dashboard
- 参数: 无
- 返回: { "code": 200, "data": { "todaySales": 0, "todayOrders": 0, "totalMachines": 0 } }

## 项目结构

后端:
```text
093-backend/
├── sql/
│   └── init.sql
├── src/main/java/com/vending/
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
093-frontend/
├── package.json
├── vite.config.js
└── src/
    ├── api/
    ├── router/
    ├── store/
    └── views/
```

## 用户角色
- 管理员：admin，负责账号、设备、商品、公告和统计看板
- 补货员：staff，负责货道巡检、补货维护、故障处理和出货跟踪
- 顾客：customer，负责注册登录、浏览设备商品、下单支付和反馈问题

## 默认账号
- admin / 123456
- staff / 123456
- customer / 123456
