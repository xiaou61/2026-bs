# 蓝天幼儿园管理系统

## 项目概述

### 项目简介
蓝天幼儿园管理系统面向幼儿园园务管理、教师日常执行和家长协同服务场景，支持管理员、教师、家长三角色围绕园区基础资料、幼儿档案、课程活动、考勤晨检、接送反馈、食谱公告和统计看板开展业务协同，形成完整的园务管理闭环。

### 核心功能
- 用户登录与角色权限控制
- 园区、年级、班级、学期管理
- 幼儿档案与家长关联维护
- 课程活动与活动安排管理
- 每周食谱发布与查看
- 入园考勤、健康晨检、接送登记
- 家园反馈与系统公告
- 数据统计看板

### 技术栈

**后端**
- Spring Boot 2.7.18
- MyBatis 3.5.13
- PageHelper 1.4.7
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
- 管理员、教师、家长三角色登录
- 登录、退出、个人信息查询
- 基于 JWT + Redis 的登录校验

### 2. 基础资料模块
- 园区管理
- 年级管理
- 班级管理
- 学期管理

### 3. 幼儿成长模块
- 幼儿档案维护
- 课程活动管理
- 活动安排管理
- 每周食谱发布

### 4. 日常服务模块
- 入园考勤登记
- 健康晨检记录
- 接送记录管理
- 家园反馈互动

### 5. 运营展示模块
- 系统公告管理
- 首页数据看板

## 技术设计

### 数据库设计（14张表）
1. sys_user
2. campus_info
3. grade_info
4. class_info
5. school_term
6. activity_info
7. activity_schedule
8. child_profile
9. weekly_recipe
10. attendance_record
11. health_record
12. parent_feedback
13. system_notice
14. pickup_record

### API接口设计

#### 1.1 用户登录
- 请求方式: POST
- 路径: /api/auth/login
- 参数: { "username": "admin", "password": "123456" }
- 返回: { "code": 200, "data": { "token": "...", "userInfo": {} } }

#### 1.2 园区分页查询
- 请求方式: GET
- 路径: /api/campus/list
- 参数: pageNum, pageSize, name, status
- 返回: { "code": 200, "data": { "list": [], "total": 0 } }

#### 1.3 幼儿档案新增
- 请求方式: POST
- 路径: /api/child/add
- 参数: ChildProfile
- 返回: { "code": 200, "message": "success" }

#### 1.4 活动安排维护
- 请求方式: POST
- 路径: /api/schedule/add
- 参数: ActivitySchedule
- 返回: { "code": 200, "message": "success" }

#### 1.5 每周食谱发布
- 请求方式: POST
- 路径: /api/recipe/add
- 参数: WeeklyRecipe
- 返回: { "code": 200, "message": "success" }

#### 1.6 晨检记录保存
- 请求方式: POST
- 路径: /api/health/save
- 参数: HealthRecord
- 返回: { "code": 200, "message": "success" }

#### 1.7 家园反馈提交
- 请求方式: POST
- 路径: /api/feedback/add
- 参数: { "childId": 1, "feedbackScore": 95, "feedbackContent": "今天表现很好" }
- 返回: { "code": 200, "message": "success" }

#### 1.8 数据看板
- 请求方式: GET
- 路径: /api/statistics/dashboard
- 参数: 无
- 返回: { "code": 200, "data": { "childCount": 0, "teacherCount": 0, "classCount": 0 } }

### 项目结构

后端:
```
092-backend/
├── sql/
│   └── init.sql
├── src/main/java/com/kindergarten/
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
092-frontend/
├── package.json
├── vite.config.js
├── index.html
└── src/
    ├── main.js
    ├── App.vue
    ├── router/
    ├── api/
    ├── store/
    └── views/
```

## 用户角色
- 管理员：admin，负责园区、年级、班级、学期、活动、公告与统计
- 教师：teacher，负责幼儿档案、活动安排、食谱、考勤、晨检、接送与反馈处理
- 家长：parent，负责查看孩子档案、活动安排、食谱、考勤晨检和提交反馈

## 默认账号
- admin / 123456
- teacher / 123456
- parent / 123456
