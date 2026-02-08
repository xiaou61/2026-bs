# 制造装备物联及生产管理ERP系统

## 项目概述
- 项目简介：集成物联网设备监控与生产管理的制造业ERP系统，实现设备实时监控、生产工单管理、物料库存追踪、质量检测和维保调度
- 核心功能：设备物联监控、生产工单管理、物料进出库、质量检测、维保管理、数据看板
- 技术栈：Spring Boot 2.7.x + MyBatis-Plus + Redis + JWT + Vue 3 + Element Plus + ECharts

## 功能需求

### 1. 系统管理
- 用户登录/退出（JWT认证）
- 用户增删改查、角色分配
- 角色权限控制（管理员/生产经理/设备操作员/质检员）

### 2. 设备管理
- 设备分类管理（增删改查）
- 设备台账管理（设备注册、编辑、删除、状态变更）
- 设备状态：运行中/停机/维修中/报废

### 3. 物联监控
- 传感器数据采集记录（温度、压力、振动、转速等）
- 实时数据展示（ECharts图表）
- 告警规则触发与告警记录管理
- 告警级别：一般/重要/紧急

### 4. 生产管理
- 产品档案管理
- 生产工单创建、排产、进度跟踪
- 工单状态：待排产/生产中/已完工/已取消

### 5. 物料管理
- 物料档案管理（名称、规格、单位、库存量）
- 物料入库/出库记录
- 库存预警

### 6. 质量管理
- 质量检测记录（关联工单和产品）
- 检测结果：合格/不合格/待复检
- 不良品统计

### 7. 维保管理
- 维保计划制定（周期性保养）
- 维保记录登记
- 维保状态：待执行/执行中/已完成

### 8. 数据看板
- 设备状态总览（饼图）
- 生产工单统计（柱状图）
- 今日告警数量
- 质量合格率趋势（折线图）
- 关键指标卡片

## 技术设计

### 数据库设计

表1: user（用户表）
- id BIGINT 主键自增
- username VARCHAR(50) 用户名
- password VARCHAR(100) 密码
- real_name VARCHAR(50) 真实姓名
- phone VARCHAR(20) 手机号
- role VARCHAR(20) 角色(admin/manager/operator/inspector)
- status INT 状态(0禁用/1启用)
- create_time DATETIME

表2: equipment_category（设备分类表）
- id BIGINT 主键自增
- name VARCHAR(100) 分类名称
- description VARCHAR(500) 描述
- create_time DATETIME

表3: equipment（设备表）
- id BIGINT 主键自增
- code VARCHAR(50) 设备编号
- name VARCHAR(100) 设备名称
- category_id BIGINT 分类ID
- model VARCHAR(100) 规格型号
- manufacturer VARCHAR(100) 制造商
- purchase_date DATE 购买日期
- location VARCHAR(200) 安装位置
- status VARCHAR(20) 状态(running/stopped/repairing/scrapped)
- create_time DATETIME

表4: sensor_data（传感器数据表）
- id BIGINT 主键自增
- equipment_id BIGINT 设备ID
- temperature DECIMAL(10,2) 温度
- pressure DECIMAL(10,2) 压力
- vibration DECIMAL(10,2) 振动值
- speed DECIMAL(10,2) 转速
- collect_time DATETIME 采集时间

表5: alert_record（告警记录表）
- id BIGINT 主键自增
- equipment_id BIGINT 设备ID
- alert_type VARCHAR(50) 告警类型
- alert_level VARCHAR(20) 告警级别(normal/important/urgent)
- content VARCHAR(500) 告警内容
- status INT 状态(0未处理/1已处理)
- create_time DATETIME
- handle_time DATETIME 处理时间

表6: product（产品表）
- id BIGINT 主键自增
- code VARCHAR(50) 产品编号
- name VARCHAR(100) 产品名称
- spec VARCHAR(200) 规格
- unit VARCHAR(20) 单位
- create_time DATETIME

表7: production_order（生产工单表）
- id BIGINT 主键自增
- order_no VARCHAR(50) 工单号
- product_id BIGINT 产品ID
- equipment_id BIGINT 设备ID
- plan_quantity INT 计划数量
- actual_quantity INT 实际数量
- status VARCHAR(20) 状态(pending/producing/completed/cancelled)
- plan_start_date DATE 计划开始日期
- plan_end_date DATE 计划结束日期
- actual_start_date DATE 实际开始日期
- actual_end_date DATE 实际结束日期
- create_time DATETIME

表8: material（物料表）
- id BIGINT 主键自增
- code VARCHAR(50) 物料编号
- name VARCHAR(100) 物料名称
- spec VARCHAR(200) 规格
- unit VARCHAR(20) 单位
- stock_quantity DECIMAL(10,2) 库存数量
- safe_stock DECIMAL(10,2) 安全库存
- create_time DATETIME

表9: material_stock_record（物料出入库记录表）
- id BIGINT 主键自增
- material_id BIGINT 物料ID
- type VARCHAR(10) 类型(in/out)
- quantity DECIMAL(10,2) 数量
- reason VARCHAR(200) 原因
- operator VARCHAR(50) 操作人
- create_time DATETIME

表10: quality_inspection（质量检测表）
- id BIGINT 主键自增
- order_id BIGINT 工单ID
- product_id BIGINT 产品ID
- inspect_quantity INT 检测数量
- qualified_quantity INT 合格数量
- unqualified_quantity INT 不合格数量
- result VARCHAR(20) 结果(passed/failed/recheck)
- inspector VARCHAR(50) 检测员
- remark VARCHAR(500) 备注
- inspect_time DATETIME

表11: maintenance_plan（维保计划表）
- id BIGINT 主键自增
- equipment_id BIGINT 设备ID
- plan_name VARCHAR(100) 计划名称
- cycle_days INT 周期天数
- last_maintain_date DATE 上次维保日期
- next_maintain_date DATE 下次维保日期
- content VARCHAR(500) 维保内容
- status VARCHAR(20) 状态(active/inactive)
- create_time DATETIME

表12: maintenance_record（维保记录表）
- id BIGINT 主键自增
- plan_id BIGINT 计划ID
- equipment_id BIGINT 设备ID
- maintain_type VARCHAR(20) 维保类型(regular/repair/emergency)
- content VARCHAR(500) 维保内容
- maintainer VARCHAR(50) 维保人
- cost DECIMAL(10,2) 费用
- status VARCHAR(20) 状态(pending/processing/completed)
- start_time DATETIME
- end_time DATETIME
- create_time DATETIME

### API接口设计

#### 1. 认证模块
- POST /api/login 登录
- POST /api/logout 退出

#### 2. 用户管理
- GET /api/user/page 分页查询
- POST /api/user 新增
- PUT /api/user 修改
- DELETE /api/user/{id} 删除
- GET /api/user/info 当前用户信息

#### 3. 设备分类
- GET /api/equipmentCategory/page 分页查询
- GET /api/equipmentCategory/list 全部列表
- POST /api/equipmentCategory 新增
- PUT /api/equipmentCategory 修改
- DELETE /api/equipmentCategory/{id} 删除

#### 4. 设备管理
- GET /api/equipment/page 分页查询
- POST /api/equipment 新增
- PUT /api/equipment 修改
- DELETE /api/equipment/{id} 删除
- PUT /api/equipment/status 状态变更

#### 5. 传感器数据
- GET /api/sensorData/page 分页查询
- POST /api/sensorData 上报数据
- GET /api/sensorData/latest/{equipmentId} 最新数据
- GET /api/sensorData/trend/{equipmentId} 趋势数据

#### 6. 告警管理
- GET /api/alert/page 分页查询
- PUT /api/alert/handle/{id} 处理告警
- GET /api/alert/stats 告警统计

#### 7. 产品管理
- GET /api/product/page 分页查询
- GET /api/product/list 全部列表
- POST /api/product 新增
- PUT /api/product 修改
- DELETE /api/product/{id} 删除

#### 8. 生产工单
- GET /api/productionOrder/page 分页查询
- POST /api/productionOrder 新增
- PUT /api/productionOrder 修改
- DELETE /api/productionOrder/{id} 删除
- PUT /api/productionOrder/status 状态变更

#### 9. 物料管理
- GET /api/material/page 分页查询
- POST /api/material 新增
- PUT /api/material 修改
- DELETE /api/material/{id} 删除

#### 10. 物料出入库
- GET /api/materialStock/page 分页查询
- POST /api/materialStock/in 入库
- POST /api/materialStock/out 出库

#### 11. 质量检测
- GET /api/qualityInspection/page 分页查询
- POST /api/qualityInspection 新增
- PUT /api/qualityInspection 修改
- DELETE /api/qualityInspection/{id} 删除

#### 12. 维保计划
- GET /api/maintenancePlan/page 分页查询
- POST /api/maintenancePlan 新增
- PUT /api/maintenancePlan 修改
- DELETE /api/maintenancePlan/{id} 删除

#### 13. 维保记录
- GET /api/maintenanceRecord/page 分页查询
- POST /api/maintenanceRecord 新增
- PUT /api/maintenanceRecord/status 状态变更

#### 14. 数据看板
- GET /api/dashboard/equipmentStatus 设备状态统计
- GET /api/dashboard/orderStats 工单统计
- GET /api/dashboard/alertStats 今日告警
- GET /api/dashboard/qualityTrend 质量趋势
- GET /api/dashboard/overview 总览指标

### 项目结构

后端:
```
059-backend/
├── sql/
│   └── init.sql
├── src/main/java/com/mfg/
│   ├── MfgApplication.java
│   ├── common/
│   │   ├── Result.java
│   │   ├── BusinessException.java
│   │   └── GlobalExceptionHandler.java
│   ├── config/
│   │   ├── JwtInterceptor.java
│   │   ├── WebMvcConfig.java
│   │   ├── MybatisPlusConfig.java
│   │   └── RedisConfig.java
│   ├── utils/
│   │   └── JwtUtils.java
│   ├── entity/
│   ├── mapper/
│   ├── service/
│   └── controller/
└── src/main/resources/
    └── application.yml
```

前端:
```
059-frontend/
├── package.json
├── vite.config.js
├── index.html
└── src/
    ├── main.js
    ├── App.vue
    ├── router/
    │   └── index.js
    ├── api/
    │   ├── request.js
    │   └── index.js
    ├── store/
    │   └── user.js
    └── views/
        ├── Login.vue
        ├── Layout.vue
        ├── Dashboard.vue
        ├── user/
        │   └── index.vue
        ├── equipment/
        │   ├── Category.vue
        │   └── index.vue
        ├── iot/
        │   ├── SensorData.vue
        │   └── AlertRecord.vue
        ├── production/
        │   ├── Product.vue
        │   └── Order.vue
        ├── material/
        │   ├── index.vue
        │   └── StockRecord.vue
        ├── quality/
        │   └── index.vue
        └── maintenance/
            ├── Plan.vue
            └── Record.vue
```

## 用户角色
- admin（管理员）：全部功能权限
- manager（生产经理）：生产管理、物料管理、质量管理、数据看板
- operator（设备操作员）：设备管理、物联监控、维保管理
- inspector（质检员）：质量管理、生产工单查看

## 默认账号
- admin/123456（管理员）
- manager/123456（生产经理）
- operator/123456（设备操作员）
- inspector/123456（质检员）
