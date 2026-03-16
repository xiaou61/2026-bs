# 数学课程评价系统

## 项目概述

### 项目简介
数学课程评价系统面向高校数学教学质量改进场景，支持课程分类管理、课程管理、评价指标配置、评价任务发布、学生在线评分与评价结果统计分析，形成“任务发布-学生评价-结果分析-教学改进”的闭环。

### 核心功能
- 用户登录与角色权限控制（管理员/教师/学生）
- 课程分类与数学课程管理
- 评价指标配置（权重、排序、启停）
- 评价任务管理（按课程按学期发布）
- 学生课程评价提交（含多指标评分与建议）
- 公告发布与评价数据看板

### 技术栈

**后端**
- Spring Boot 2.7.18
- MyBatis 3.5.13（原生XML）
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
- 管理员、教师、学生三角色
- 账号登录、退出、个人信息查询
- 基于JWT+Redis的会话校验

### 2. 课程基础模块
- 课程分类管理（新增、编辑、删除、启停）
- 数学课程管理（课程编码、学分、学期、授课教师）
- 启用课程列表查询

### 3. 评价指标模块
- 指标维护（名称、权重、排序、说明）
- 指标启停控制
- 启用指标总权重校验（不超过100）

### 4. 评价任务模块
- 任务发布（关联课程、时间区间、学期）
- 任务启停与状态维护
- 当前有效任务列表查询

### 5. 课程评价模块
- 学生按任务提交评价（多指标评分）
- 同一学生同一任务防重复提交
- 评价详情查看
- 按任务查看评价记录与指标均分统计

### 6. 公告与统计模块
- 公告新增、编辑、删除、发布
- 首页看板：用户数、课程数、任务数、评价数、平均分、月度趋势、课程均分TOP5

## 技术设计

### 数据库设计（9张表）
1. sys_user
2. course_category
3. math_course
4. eval_indicator
5. eval_task
6. eval_record
7. eval_record_item
8. system_notice
9. operation_log

### API接口设计（节选）

#### 1.1 用户登录
- 请求方式: POST
- 路径: /api/auth/login
- 参数: { "username": "admin", "password": "123456" }
- 返回: { "code": 200, "data": { "token": "...", "userInfo": {} } }

#### 1.2 课程分页查询
- 请求方式: GET
- 路径: /api/course/list
- 参数: pageNum, pageSize, courseName, categoryId, term, status
- 返回: { "code": 200, "data": { "list": [], "total": 0 } }

#### 1.3 指标新增
- 请求方式: POST
- 路径: /api/indicator/add
- 参数: { "indicatorName": "教学内容清晰度", "weight": 30, "sort": 1 }
- 返回: { "code": 200, "message": "success" }

#### 1.4 评价任务新增
- 请求方式: POST
- 路径: /api/task/add
- 参数: EvalTask
- 返回: { "code": 200, "message": "success" }

#### 1.5 学生提交评价
- 请求方式: POST
- 路径: /api/evaluation/submit
- 参数: { "taskId": 1, "commentContent": "建议增加习题讲评", "items": [{"indicatorId":1,"score":90}] }
- 返回: { "code": 200, "message": "success" }

#### 1.6 统计看板
- 请求方式: GET
- 路径: /api/statistics/dashboard
- 参数: 无
- 返回: { "code": 200, "data": { "userCount": 0, "courseCount": 0, "taskCount": 0, "recordCount": 0 } }

### 项目结构

后端:
```
085-backend/
├── sql/
│   └── init.sql
├── src/main/java/com/teachres/
│   ├── TeachResApplication.java
│   ├── common/
│   ├── config/
│   ├── controller/
│   ├── entity/
│   ├── mapper/
│   ├── service/
│   └── utils/
└── src/main/resources/
    ├── application.yml
    └── mapper/
```

前端:
```
085-frontend/
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
        ├── category/
        ├── course/
        ├── indicator/
        ├── task/
        ├── evaluation/
        └── notice/
```

## 用户角色
- 管理员：admin，管理课程、指标、任务、公告与统计
- 教师：teacher，查看课程评价结果与统计数据
- 学生：student，参与课程评价并查看个人评价记录

## 默认账号
- admin / 123456
- teacher / 123456
- student / 123456
