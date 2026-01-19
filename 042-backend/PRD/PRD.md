# 房屋租赁管理系统 PRD

## 一、项目概述

### 1.1 项目名称

基于 Spring Boot + Vue 的房屋租赁管理系统

### 1.2 项目背景

随着城市化进程加快，房屋租赁市场需求日益增长。传统租房模式存在信息不对称、沟通效率低、合同管理混乱、租金支付不透明等问题。本系统旨在构建一个集房源发布、在线预约看房、合同签署、租金管理、报修服务于一体的综合性房屋租赁平台，为房东、租客和平台管理员提供便捷高效的服务。

## 二、项目目标

1. 提供一个功能完善的房屋租赁在线管理平台
2. 支持房东发布房源、租客浏览预约、管理员监管的完整业务流程
3. 实现房源管理、预约看房、合同管理、租金账单、报修服务五大核心功能
4. 使用 JWT 登录认证机制，简洁安全
5. 前后端分离架构，界面美观，易部署易展示

## 三、系统架构与技术栈

| 层级   | 技术栈                                      |
| ------ | ------------------------------------------- |
| 前端   | Vue 3 + Element Plus + Axios + Vue Router + Pinia |
| 后端   | Spring Boot 3.2 + MyBatis-Plus + JWT        |
| 数据库 | MySQL 8.0                                   |
| 工具   | Maven + Node.js + Vite                      |
| 部署   | 单机部署（本地或云服务器）                  |

**架构图：**
```
[Vue 前端] → [Spring Boot 后端] → [MyBatis-Plus ORM] → [MySQL 数据库]
```

## 四、系统角色与权限

| 角色     | 权限说明                                                     |
| -------- | ------------------------------------------------------------ |
| 管理员   | 用户管理、房源审核、合同监管、数据统计、系统配置             |
| 房东     | 发布/编辑房源、查看预约、签署合同、管理租金账单、处理报修    |
| 租客     | 浏览房源、收藏房源、预约看房、签署合同、支付租金、提交报修、评价房源 |

**登录认证方案：**
- 用户登录成功后由后端签发 JWT Token
- 前端保存 token（localStorage）
- 请求接口时携带 token（Header: Authorization: Bearer xxx）
- 拦截器验证 token 有效性与角色

## 五、系统主要功能模块

### 5.1 用户模块

**功能描述：** 实现用户注册、登录、信息管理与角色分配。

**功能点：**
- 用户注册（选择房东/租客角色）
- 用户登录（JWT 签发）
- 修改个人信息（头像、手机号、身份证实名认证）
- 管理员管理用户账号（启用/禁用）
- 用户列表分页、搜索

**数据字段：**

| 字段          | 类型     | 说明                           |
| ------------- | -------- | ------------------------------ |
| id            | bigint   | 主键                           |
| username      | varchar  | 用户名                         |
| password      | varchar  | 密码（BCrypt加密）             |
| role          | varchar  | 角色（admin/landlord/tenant）  |
| real_name     | varchar  | 真实姓名                       |
| phone         | varchar  | 手机号                         |
| id_card       | varchar  | 身份证号                       |
| avatar        | varchar  | 头像地址                       |
| status        | int      | 状态（0禁用、1正常）           |
| create_time   | datetime | 注册时间                       |
| update_time   | datetime | 更新时间                       |

### 5.2 房源管理模块

**功能描述：** 房东发布房源信息，管理员审核，租客浏览筛选。

**功能点：**
- 房东发布房源（标题、描述、图片、价格、位置、面积、户型等）
- 房源编辑与删除
- 房源上架/下架
- 管理员审核房源
- 房源列表（分页、筛选、搜索）
- 房源详情展示
- 房源收藏功能

**数据字段：**

| 字段           | 类型     | 说明                                   |
| -------------- | -------- | -------------------------------------- |
| id             | bigint   | 主键                                   |
| landlord_id    | bigint   | 房东ID                                 |
| title          | varchar  | 房源标题                               |
| description    | text     | 详细描述                               |
| province       | varchar  | 省                                     |
| city           | varchar  | 市                                     |
| district       | varchar  | 区                                     |
| address        | varchar  | 详细地址                               |
| price          | decimal  | 月租金（元）                           |
| deposit        | decimal  | 押金（元）                             |
| area           | decimal  | 面积（平方米）                         |
| room_type      | varchar  | 户型（如：2室1厅1卫）                  |
| floor          | int      | 楼层                                   |
| total_floor    | int      | 总楼层                                 |
| orientation    | varchar  | 朝向（东/南/西/北）                    |
| decoration     | varchar  | 装修（精装/简装/毛坯）                 |
| facilities     | varchar  | 配套设施（JSON数组：空调、冰箱等）     |
| images         | text     | 图片列表（JSON数组）                   |
| status         | int      | 状态（0待审核、1已上架、2已下架、3已出租） |
| view_count     | int      | 浏览次数                               |
| create_time    | datetime | 创建时间                               |
| update_time    | datetime | 更新时间                               |

### 5.3 预约看房模块

**功能描述：** 租客预约看房，房东确认/拒绝预约。

**功能点：**
- 租客提交预约申请（选择时间）
- 房东查看预约列表
- 房东确认/拒绝预约
- 预约状态跟踪
- 预约历史记录

**数据字段：**

| 字段             | 类型     | 说明                                 |
| ---------------- | -------- | ------------------------------------ |
| id               | bigint   | 主键                                 |
| house_id         | bigint   | 房源ID                               |
| tenant_id        | bigint   | 租客ID                               |
| landlord_id      | bigint   | 房东ID                               |
| appointment_time | datetime | 预约看房时间                         |
| contact_phone    | varchar  | 联系电话                             |
| remark           | varchar  | 备注                                 |
| status           | int      | 状态（0待确认、1已确认、2已拒绝、3已完成、4已取消） |
| reject_reason    | varchar  | 拒绝原因                             |
| create_time      | datetime | 创建时间                             |
| update_time      | datetime | 更新时间                             |

### 5.4 租赁合同模块

**功能描述：** 管理租赁合同的创建、签署、续签、终止。

**功能点：**
- 房东创建合同（关联房源和租客）
- 租客确认签署合同
- 合同详情查看
- 合同续签
- 合同提前终止
- 合同到期提醒

**数据字段：**

| 字段           | 类型     | 说明                                 |
| -------------- | -------- | ------------------------------------ |
| id             | bigint   | 主键                                 |
| contract_no    | varchar  | 合同编号                             |
| house_id       | bigint   | 房源ID                               |
| landlord_id    | bigint   | 房东ID                               |
| tenant_id      | bigint   | 租客ID                               |
| start_date     | date     | 租期开始日期                         |
| end_date       | date     | 租期结束日期                         |
| monthly_rent   | decimal  | 月租金                               |
| deposit        | decimal  | 押金                                 |
| payment_day    | int      | 每月付款日（1-28）                   |
| payment_method | varchar  | 付款方式（月付/季付/半年付/年付）    |
| terms          | text     | 合同条款                             |
| status         | int      | 状态（0待签署、1生效中、2已到期、3已终止） |
| sign_time      | datetime | 签署时间                             |
| create_time    | datetime | 创建时间                             |
| update_time    | datetime | 更新时间                             |

### 5.5 租金账单模块

**功能描述：** 管理租金账单的生成、支付、查询。

**功能点：**
- 根据合同自动生成每期账单
- 租客查看待支付账单
- 租客模拟支付（余额支付）
- 支付记录查询
- 账单统计（房东收入统计）
- 逾期账单提醒

**数据字段：**

| 字段          | 类型     | 说明                               |
| ------------- | -------- | ---------------------------------- |
| id            | bigint   | 主键                               |
| bill_no       | varchar  | 账单编号                           |
| contract_id   | bigint   | 合同ID                             |
| tenant_id     | bigint   | 租客ID                             |
| landlord_id   | bigint   | 房东ID                             |
| amount        | decimal  | 应付金额                           |
| bill_month    | varchar  | 账单月份（如：2026-01）            |
| due_date      | date     | 应付日期                           |
| paid_amount   | decimal  | 已付金额                           |
| paid_time     | datetime | 支付时间                           |
| status        | int      | 状态（0未支付、1已支付、2已逾期）  |
| create_time   | datetime | 创建时间                           |
| update_time   | datetime | 更新时间                           |

### 5.6 报修管理模块

**功能描述：** 租客提交报修申请，房东处理报修。

**功能点：**
- 租客提交报修（类型、描述、图片）
- 房东查看报修列表
- 房东处理报修（接单、完成）
- 报修状态跟踪
- 报修记录查询

**数据字段：**

| 字段          | 类型     | 说明                                 |
| ------------- | -------- | ------------------------------------ |
| id            | bigint   | 主键                                 |
| house_id      | bigint   | 房源ID                               |
| contract_id   | bigint   | 合同ID                               |
| tenant_id     | bigint   | 租客ID                               |
| landlord_id   | bigint   | 房东ID                               |
| type          | varchar  | 报修类型（水电/家具/家电/其他）      |
| description   | text     | 问题描述                             |
| images        | text     | 图片列表（JSON数组）                 |
| status        | int      | 状态（0待处理、1处理中、2已完成）    |
| result        | varchar  | 处理结果                             |
| create_time   | datetime | 创建时间                             |
| update_time   | datetime | 更新时间                             |

### 5.7 评价模块

**功能描述：** 租客对房源和房东进行评价。

**功能点：**
- 合同结束后租客可评价
- 多维度评分（房源真实度、房东服务、设施完善度）
- 评价内容展示在房源详情
- 评价统计（平均评分）

**数据字段：**

| 字段           | 类型     | 说明                 |
| -------------- | -------- | -------------------- |
| id             | bigint   | 主键                 |
| house_id       | bigint   | 房源ID               |
| contract_id    | bigint   | 合同ID               |
| tenant_id      | bigint   | 租客ID               |
| landlord_id    | bigint   | 房东ID               |
| house_score    | int      | 房源评分（1-5）      |
| service_score  | int      | 服务评分（1-5）      |
| facility_score | int      | 设施评分（1-5）      |
| content        | text     | 评价内容             |
| images         | text     | 评价图片（JSON数组） |
| create_time    | datetime | 创建时间             |

### 5.8 消息通知模块

**功能描述：** 系统消息推送，保证信息及时传达。

**功能点：**
- 预约通知（租客预约、房东确认）
- 合同通知（待签署、即将到期）
- 账单通知（待支付、已逾期）
- 报修通知（新报修、已处理）
- 消息已读/未读状态

**数据字段：**

| 字段        | 类型     | 说明                                   |
| ----------- | -------- | -------------------------------------- |
| id          | bigint   | 主键                                   |
| user_id     | bigint   | 接收用户ID                             |
| title       | varchar  | 消息标题                               |
| content     | text     | 消息内容                               |
| type        | varchar  | 消息类型（appointment/contract/bill/repair） |
| is_read     | int      | 是否已读（0未读、1已读）               |
| create_time | datetime | 创建时间                               |

### 5.9 数据统计模块

**功能描述：** 管理员和房东查看统计数据。

**功能点：**
- 房源统计（总数、状态分布）
- 合同统计（签约数、租金收入）
- 用户统计（房东数、租客数）
- 图表可视化展示（ECharts）

## 六、前端页面设计

| 页面名称       | 功能描述                         |
| -------------- | -------------------------------- |
| 登录/注册页    | 用户登录、注册（选择角色）       |
| 首页           | 房源推荐、搜索入口               |
| 房源列表页     | 房源筛选、搜索、分页展示         |
| 房源详情页     | 房源信息、图片、预约看房、评价   |
| 发布房源页     | 房东发布/编辑房源                |
| 我的房源页     | 房东管理自己的房源               |
| 预约管理页     | 查看/处理预约                    |
| 合同管理页     | 合同列表、详情、签署             |
| 账单管理页     | 账单列表、支付                   |
| 报修管理页     | 报修提交、查看、处理             |
| 收藏列表页     | 租客收藏的房源                   |
| 消息中心页     | 系统消息列表                     |
| 个人中心页     | 个人信息、修改密码               |
| 管理后台       | 用户管理、房源审核、数据统计     |

## 七、数据库设计总览

| 表名             | 说明       |
| ---------------- | ---------- |
| user             | 用户表     |
| house            | 房源表     |
| house_favorite   | 房源收藏表 |
| appointment      | 预约看房表 |
| contract         | 租赁合同表 |
| bill             | 租金账单表 |
| repair           | 报修表     |
| review           | 评价表     |
| message          | 消息通知表 |

## 八、系统实现要点

### 8.1 登录认证
- Spring Boot 使用 JWT + 拦截器实现
- 登录时生成 token，返回给前端
- 前端请求携带 Authorization Header 验证

### 8.2 文件上传
- Spring Boot + MultipartFile 实现图片上传
- 存储路径配置在 application.yml
- 支持多图上传（房源图片、报修图片等）

### 8.3 接口安全
- 基于角色的简单判断
- 房东只能操作自己的房源
- 租客只能操作自己的预约、合同、账单

### 8.4 接口风格
- RESTful API 设计
- 统一响应格式 `{ code, message, data }`

### 8.5 定时任务
- 账单自动生成（每月根据合同生成）
- 合同到期提醒
- 账单逾期状态更新

## 九、API 接口概览

### 认证接口
- `POST /api/auth/register` - 用户注册
- `POST /api/auth/login` - 用户登录
- `GET /api/auth/userinfo` - 获取当前用户信息
- `PUT /api/auth/password` - 修改密码

### 房源接口
- `POST /api/house` - 发布房源
- `PUT /api/house/{id}` - 编辑房源
- `DELETE /api/house/{id}` - 删除房源
- `GET /api/house/list` - 房源列表（公开）
- `GET /api/house/{id}` - 房源详情
- `GET /api/house/my` - 我的房源（房东）
- `PUT /api/house/{id}/status` - 上架/下架
- `POST /api/house/{id}/favorite` - 收藏/取消收藏
- `GET /api/house/favorites` - 我的收藏

### 预约接口
- `POST /api/appointment` - 提交预约
- `GET /api/appointment/list` - 预约列表
- `PUT /api/appointment/{id}/confirm` - 确认预约
- `PUT /api/appointment/{id}/reject` - 拒绝预约
- `PUT /api/appointment/{id}/complete` - 完成预约
- `PUT /api/appointment/{id}/cancel` - 取消预约

### 合同接口
- `POST /api/contract` - 创建合同
- `GET /api/contract/list` - 合同列表
- `GET /api/contract/{id}` - 合同详情
- `PUT /api/contract/{id}/sign` - 签署合同
- `PUT /api/contract/{id}/terminate` - 终止合同

### 账单接口
- `GET /api/bill/list` - 账单列表
- `GET /api/bill/{id}` - 账单详情
- `POST /api/bill/{id}/pay` - 支付账单
- `GET /api/bill/statistics` - 账单统计

### 报修接口
- `POST /api/repair` - 提交报修
- `GET /api/repair/list` - 报修列表
- `PUT /api/repair/{id}/process` - 处理报修
- `PUT /api/repair/{id}/complete` - 完成报修

### 评价接口
- `POST /api/review` - 提交评价
- `GET /api/review/house/{houseId}` - 房源评价列表

### 消息接口
- `GET /api/message/list` - 消息列表
- `PUT /api/message/{id}/read` - 标记已读
- `PUT /api/message/read-all` - 全部已读
- `GET /api/message/unread-count` - 未读数量

### 管理员接口
- `GET /api/admin/user/list` - 用户列表
- `PUT /api/admin/user/{id}/status` - 禁用/启用用户
- `GET /api/admin/house/list` - 房源审核列表
- `PUT /api/admin/house/{id}/audit` - 审核房源
- `GET /api/admin/statistics` - 数据统计
