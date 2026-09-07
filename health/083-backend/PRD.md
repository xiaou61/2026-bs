# 基于B/S的老年人体检管理系统

## 项目概述

### 项目简介
基于B/S架构构建老年人体检管理系统，服务于体检中心、社区卫生服务机构与养老机构，实现老人档案管理、体检预约、体检执行、结果汇总、异常预警、随访管理与统计分析的一体化闭环。

### 核心功能
- 账号登录与角色权限控制
- 老人健康档案管理
- 体检套餐与体检项目管理
- 体检预约与到检登记
- 体检结果录入与报告生成
- 异常指标预警与随访管理
- 数据统计看板

### 技术栈

**后端**
- Spring Boot 2.7.14
- MyBatis-Plus 3.5.3
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

### 1. 用户与权限模块
- 管理员、医生、护士、前台四类角色
- 用户登录、退出、个人信息查询
- 用户分页管理与角色分配

### 2. 老人档案模块
- 老人基础信息管理（姓名、性别、年龄、身份证、联系方式）
- 慢病史、过敏史、既往病史记录
- 紧急联系人维护

### 3. 体检项目与套餐模块
- 体检项目维护（项目编码、名称、参考值、单位、预警阈值）
- 体检套餐维护（套餐名称、价格、适用人群）
- 套餐与项目关联管理

### 4. 预约登记模块
- 体检预约创建、改期、取消
- 到检签到与状态流转（待检、检查中、已完成）
- 预约列表分页查询与筛选

### 5. 体检执行与结果模块
- 按预约单录入各项目检查结果
- 自动判定异常指标
- 检查完成后生成体检报告

### 6. 异常预警与随访模块
- 异常结果自动生成预警记录
- 随访任务创建与处理记录
- 随访状态管理（待随访、随访中、已完成）

### 7. 通知与看板模块
- 系统通知发布与查询
- 首页统计指标（老人总数、今日预约、异常人数、完成率）
- 月度体检趋势图与异常分布图

## 技术设计

### 数据库设计
1. sys_user
2. elder_profile
3. elder_medical_history
4. elder_contact
5. check_item
6. check_package
7. check_package_item
8. check_appointment
9. check_result
10. abnormal_warning
11. follow_up_record
12. system_notice

### API接口设计

#### 1.1 用户登录
- 请求方式: POST
- 路径: /api/auth/login
- 参数: { "username": "admin", "password": "123456" }
- 返回: { "code": 200, "data": { "token": "...", "userInfo": {} } }

#### 1.2 老人档案分页查询
- 请求方式: GET
- 路径: /api/elder/list
- 参数: pageNum, pageSize, name, phone
- 返回: { "code": 200, "data": { "records": [], "total": 0 } }

#### 1.3 创建体检预约
- 请求方式: POST
- 路径: /api/appointment/add
- 参数: AppointmentDTO
- 返回: { "code": 200, "message": "success" }

#### 1.4 录入体检结果
- 请求方式: POST
- 路径: /api/result/add
- 参数: ResultDTO
- 返回: { "code": 200, "message": "success" }

#### 1.5 首页统计
- 请求方式: GET
- 路径: /api/statistics/dashboard
- 参数: 无
- 返回: { "code": 200, "data": { "elderCount": 0, "todayAppointmentCount": 0, "abnormalCount": 0 } }

### 项目结构

后端:
```
083-backend/
├── sql/
│   └── init.sql
├── src/main/java/com/eldercare/
│   ├── ElderCareApplication.java
│   ├── common/
│   ├── config/
│   ├── controller/
│   ├── entity/
│   ├── mapper/
│   ├── service/
│   ├── dto/
│   └── utils/
└── src/main/resources/
    ├── application.yml
    └── mapper/
```

前端:
```
083-frontend/
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
        ├── elder/
        ├── package/
        ├── appointment/
        ├── result/
        └── warning/
```

## 用户角色
- 管理员：admin，维护全量数据与系统配置
- 医生：doctor，负责体检结果录入与报告确认
- 护士：nurse，负责到检登记与基础体征录入
- 前台：reception，负责预约与档案维护

## 默认账号
- admin / 123456
- doctor / 123456
- nurse / 123456
- reception / 123456

