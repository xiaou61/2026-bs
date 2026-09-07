# 基于SpringBoot的救灾物资调度与救援系统 - 产品需求文档

## 1. 项目概述

### 1.1 项目背景
自然灾害发生时，高效的物资调度和救援响应至关重要。本系统旨在提供一个集灾情上报、物资管理、调度分配、救援任务管理于一体的综合平台，提高救灾效率。

### 1.2 项目目标
- 实现灾情信息快速上报与管理
- 实现救灾物资全流程管理（入库、出库、调度）
- 实现救援任务的分配与跟踪
- 提供数据统计与可视化分析

### 1.3 目标用户
- 系统管理员：系统配置、用户管理
- 物资管理员：物资入库、出库、库存管理
- 救援人员：执行救援任务、反馈任务进度
- 信息员：灾情上报、灾区信息更新

## 2. 技术架构

### 2.1 后端技术栈
- Spring Boot 3.2.0
- MyBatis-Plus 3.5.5
- MySQL 8.0
- Redis 6.0+
- JWT认证
- Hutool工具库

### 2.2 前端技术栈
- Vue 3
- Element Plus
- Vite
- Pinia
- Axios
- ECharts

## 3. 功能需求

### 3.1 用户管理模块
- 用户注册、登录、退出
- 角色管理（管理员/物资管理员/救援人员/信息员）
- 个人信息管理
- 密码修改

### 3.2 灾情管理模块
- 灾情上报（灾害类型、地点、等级、受灾人数、描述、图片）
- 灾情列表查询（支持多条件筛选）
- 灾情详情查看
- 灾情状态更新（待处理/处理中/已处理）
- 灾情等级调整

### 3.3 物资管理模块
- 物资分类管理
- 物资信息管理（名称、规格、单位、图片）
- 物资入库（来源、数量、入库时间）
- 物资出库（去向、数量、出库时间）
- 库存查询与预警（低于安全库存预警）
- 物资流水记录

### 3.4 仓库管理模块
- 仓库信息管理（名称、地址、容量、负责人）
- 仓库库存统计
- 仓库物资明细

### 3.5 调度管理模块
- 创建调度任务（源仓库、目标地点、物资清单）
- 调度任务审批
- 调度状态跟踪（待审批/已审批/运输中/已完成/已取消）
- 调度记录查询

### 3.6 救援任务模块
- 任务发布（关联灾情、任务内容、紧急程度）
- 任务分配（指派救援人员）
- 任务执行与反馈
- 任务状态管理（待分配/进行中/已完成/已取消）
- 任务评价

### 3.7 统计分析模块
- 灾情统计（按类型、等级、区域）
- 物资统计（入库、出库、库存）
- 救援统计（任务完成率、响应时间）
- 数据可视化（ECharts图表）

### 3.8 系统管理模块
- 系统公告管理
- 操作日志记录
- 数据字典管理

## 4. 数据库设计

### 4.1 用户表 (user)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| username | varchar(50) | 用户名 |
| password | varchar(100) | 密码 |
| real_name | varchar(50) | 真实姓名 |
| phone | varchar(20) | 手机号 |
| avatar | varchar(255) | 头像 |
| role | varchar(20) | 角色:ADMIN/WAREHOUSE/RESCUER/REPORTER |
| status | tinyint | 状态:0禁用1正常 |
| create_time | datetime | 创建时间 |
| update_time | datetime | 更新时间 |

### 4.2 灾情表 (disaster)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| title | varchar(100) | 灾情标题 |
| type | varchar(20) | 灾害类型:FLOOD/EARTHQUAKE/FIRE/TYPHOON/OTHER |
| level | tinyint | 灾情等级:1-5 |
| province | varchar(50) | 省份 |
| city | varchar(50) | 城市 |
| district | varchar(50) | 区县 |
| address | varchar(200) | 详细地址 |
| longitude | decimal(10,6) | 经度 |
| latitude | decimal(10,6) | 纬度 |
| affected_count | int | 受灾人数 |
| description | text | 灾情描述 |
| images | varchar(1000) | 图片(JSON数组) |
| status | tinyint | 状态:0待处理1处理中2已处理 |
| reporter_id | bigint | 上报人ID |
| report_time | datetime | 上报时间 |
| create_time | datetime | 创建时间 |
| update_time | datetime | 更新时间 |

### 4.3 物资分类表 (material_category)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| name | varchar(50) | 分类名称 |
| icon | varchar(255) | 图标 |
| sort | int | 排序 |
| status | tinyint | 状态 |
| create_time | datetime | 创建时间 |

### 4.4 物资表 (material)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| category_id | bigint | 分类ID |
| name | varchar(100) | 物资名称 |
| spec | varchar(100) | 规格型号 |
| unit | varchar(20) | 单位 |
| image | varchar(255) | 图片 |
| safe_stock | int | 安全库存 |
| description | varchar(500) | 描述 |
| status | tinyint | 状态 |
| create_time | datetime | 创建时间 |
| update_time | datetime | 更新时间 |

### 4.5 仓库表 (warehouse)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| name | varchar(100) | 仓库名称 |
| code | varchar(50) | 仓库编码 |
| address | varchar(200) | 地址 |
| longitude | decimal(10,6) | 经度 |
| latitude | decimal(10,6) | 纬度 |
| capacity | int | 容量 |
| manager_id | bigint | 负责人ID |
| phone | varchar(20) | 联系电话 |
| status | tinyint | 状态 |
| create_time | datetime | 创建时间 |
| update_time | datetime | 更新时间 |

### 4.6 库存表 (stock)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| warehouse_id | bigint | 仓库ID |
| material_id | bigint | 物资ID |
| quantity | int | 数量 |
| update_time | datetime | 更新时间 |

### 4.7 物资流水表 (stock_record)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| warehouse_id | bigint | 仓库ID |
| material_id | bigint | 物资ID |
| type | tinyint | 类型:1入库2出库 |
| quantity | int | 数量 |
| before_quantity | int | 变更前数量 |
| after_quantity | int | 变更后数量 |
| source | varchar(200) | 来源/去向 |
| operator_id | bigint | 操作人ID |
| remark | varchar(500) | 备注 |
| create_time | datetime | 创建时间 |

### 4.8 调度任务表 (dispatch)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| dispatch_no | varchar(50) | 调度单号 |
| disaster_id | bigint | 关联灾情ID |
| from_warehouse_id | bigint | 源仓库ID |
| to_address | varchar(200) | 目的地址 |
| to_longitude | decimal(10,6) | 目的经度 |
| to_latitude | decimal(10,6) | 目的纬度 |
| priority | tinyint | 优先级:1普通2紧急3特急 |
| status | tinyint | 状态:0待审批1已审批2运输中3已完成4已取消 |
| applicant_id | bigint | 申请人ID |
| approver_id | bigint | 审批人ID |
| approve_time | datetime | 审批时间 |
| approve_remark | varchar(500) | 审批备注 |
| complete_time | datetime | 完成时间 |
| remark | varchar(500) | 备注 |
| create_time | datetime | 创建时间 |
| update_time | datetime | 更新时间 |

### 4.9 调度物资明细表 (dispatch_item)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| dispatch_id | bigint | 调度ID |
| material_id | bigint | 物资ID |
| quantity | int | 数量 |
| create_time | datetime | 创建时间 |

### 4.10 救援任务表 (rescue_task)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| task_no | varchar(50) | 任务编号 |
| disaster_id | bigint | 关联灾情ID |
| title | varchar(100) | 任务标题 |
| content | text | 任务内容 |
| priority | tinyint | 优先级:1普通2紧急3特急 |
| status | tinyint | 状态:0待分配1进行中2已完成3已取消 |
| assignee_id | bigint | 执行人ID |
| publisher_id | bigint | 发布人ID |
| start_time | datetime | 开始时间 |
| end_time | datetime | 结束时间 |
| feedback | text | 反馈内容 |
| feedback_images | varchar(1000) | 反馈图片 |
| feedback_time | datetime | 反馈时间 |
| create_time | datetime | 创建时间 |
| update_time | datetime | 更新时间 |

### 4.11 系统公告表 (notice)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| title | varchar(100) | 标题 |
| content | text | 内容 |
| type | tinyint | 类型:1通知2公告 |
| status | tinyint | 状态:0草稿1发布 |
| publisher_id | bigint | 发布人ID |
| publish_time | datetime | 发布时间 |
| create_time | datetime | 创建时间 |
| update_time | datetime | 更新时间 |

### 4.12 操作日志表 (operation_log)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| user_id | bigint | 用户ID |
| username | varchar(50) | 用户名 |
| module | varchar(50) | 模块 |
| operation | varchar(100) | 操作 |
| method | varchar(200) | 方法 |
| params | text | 参数 |
| ip | varchar(50) | IP地址 |
| create_time | datetime | 创建时间 |

## 5. 接口设计

### 5.1 用户接口
- POST /api/user/register - 注册
- POST /api/user/login - 登录
- GET /api/user/info - 获取当前用户信息
- PUT /api/user/info - 更新用户信息
- PUT /api/user/password - 修改密码
- GET /api/user/list - 用户列表(管理员)
- PUT /api/user/status - 修改用户状态(管理员)

### 5.2 灾情接口
- POST /api/disaster/report - 上报灾情
- GET /api/disaster/list - 灾情列表
- GET /api/disaster/{id} - 灾情详情
- PUT /api/disaster/{id} - 更新灾情
- PUT /api/disaster/{id}/status - 更新灾情状态
- DELETE /api/disaster/{id} - 删除灾情
- GET /api/disaster/stats - 灾情统计

### 5.3 物资分类接口
- GET /api/category/list - 分类列表
- POST /api/category/add - 添加分类
- PUT /api/category/update - 更新分类
- DELETE /api/category/{id} - 删除分类

### 5.4 物资接口
- GET /api/material/list - 物资列表
- GET /api/material/{id} - 物资详情
- POST /api/material/add - 添加物资
- PUT /api/material/update - 更新物资
- DELETE /api/material/{id} - 删除物资

### 5.5 仓库接口
- GET /api/warehouse/list - 仓库列表
- GET /api/warehouse/{id} - 仓库详情
- POST /api/warehouse/add - 添加仓库
- PUT /api/warehouse/update - 更新仓库
- DELETE /api/warehouse/{id} - 删除仓库
- GET /api/warehouse/{id}/stock - 仓库库存

### 5.6 库存接口
- GET /api/stock/list - 库存列表
- POST /api/stock/in - 入库
- POST /api/stock/out - 出库
- GET /api/stock/record - 流水记录
- GET /api/stock/warning - 库存预警

### 5.7 调度接口
- POST /api/dispatch/create - 创建调度
- GET /api/dispatch/list - 调度列表
- GET /api/dispatch/{id} - 调度详情
- PUT /api/dispatch/{id}/approve - 审批调度
- PUT /api/dispatch/{id}/status - 更新状态
- DELETE /api/dispatch/{id} - 删除调度

### 5.8 救援任务接口
- POST /api/task/create - 创建任务
- GET /api/task/list - 任务列表
- GET /api/task/{id} - 任务详情
- PUT /api/task/{id}/assign - 分配任务
- PUT /api/task/{id}/feedback - 提交反馈
- PUT /api/task/{id}/complete - 完成任务
- DELETE /api/task/{id} - 删除任务
- GET /api/task/my - 我的任务

### 5.9 公告接口
- GET /api/notice/list - 公告列表
- GET /api/notice/{id} - 公告详情
- POST /api/notice/add - 添加公告
- PUT /api/notice/update - 更新公告
- DELETE /api/notice/{id} - 删除公告

### 5.10 统计接口
- GET /api/stats/overview - 总览数据
- GET /api/stats/disaster - 灾情统计
- GET /api/stats/material - 物资统计
- GET /api/stats/task - 任务统计

## 6. 项目结构

### 6.1 后端结构
```
053-backend/
├── sql/
│   └── init.sql
├── src/main/java/com/disaster/
│   ├── DisasterApplication.java
│   ├── controller/
│   │   ├── UserController.java
│   │   ├── DisasterController.java
│   │   ├── MaterialController.java
│   │   ├── WarehouseController.java
│   │   ├── StockController.java
│   │   ├── DispatchController.java
│   │   ├── TaskController.java
│   │   ├── NoticeController.java
│   │   └── StatsController.java
│   ├── service/
│   ├── mapper/
│   ├── entity/
│   ├── dto/
│   ├── vo/
│   ├── config/
│   ├── utils/
│   └── common/
└── src/main/resources/
    └── application.yml
```

### 6.2 前端结构
```
053-frontend/
├── src/
│   ├── views/
│   │   ├── Login.vue
│   │   ├── Layout.vue
│   │   ├── Dashboard.vue
│   │   ├── disaster/
│   │   ├── material/
│   │   ├── warehouse/
│   │   ├── dispatch/
│   │   ├── task/
│   │   ├── user/
│   │   └── system/
│   ├── components/
│   ├── api/
│   ├── router/
│   ├── stores/
│   └── App.vue
├── index.html
├── vite.config.js
└── package.json
```

## 7. 默认账号
- 管理员：admin / 123456
- 物资管理员：warehouse / 123456
- 救援人员：rescuer / 123456
- 信息员：reporter / 123456
