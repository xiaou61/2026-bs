# 课程管理系统

## 项目概述

### 项目简介
课程管理系统面向高校教务与教学场景，支持管理员、教师、学生三角色协同完成组织管理、课程开设、排课选课、课程资源、考勤登记、成绩录入、评教反馈和统计分析，形成完整的教学管理闭环。

### 核心功能
- 用户登录与角色权限控制
- 院系、专业、班级、学期管理
- 课程与排课管理
- 学生选课与个人课表
- 教师课程资源、考勤、成绩管理
- 学生评教与公告展示
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

### 1. 用户与权限模块
- 管理员、教师、学生三角色
- 登录、退出、个人信息查询
- 基于 JWT + Redis 的登录校验

### 2. 基础组织模块
- 院系管理
- 专业管理
- 班级管理
- 学期管理

### 3. 教务管理模块
- 课程管理
- 排课管理
- 学生选课
- 个人课表查询

### 4. 教学过程模块
- 课程资源管理
- 考勤管理
- 成绩管理
- 课程评教

### 5. 运营统计模块
- 系统公告管理
- 首页数据看板

## 技术设计

### 数据库设计（14张表）
1. sys_user
2. department_info
3. major_info
4. class_info
5. academic_term
6. course_info
7. course_schedule
8. course_selection
9. course_resource
10. attendance_record
11. score_record
12. course_evaluation
13. system_notice
14. operation_log

### API接口设计

#### 1.1 用户登录
- 请求方式: POST
- 路径: /api/auth/login
- 参数: { "username": "admin", "password": "123456" }
- 返回: { "code": 200, "data": { "token": "...", "userInfo": {} } }

#### 1.2 课程分页查询
- 请求方式: GET
- 路径: /api/course/list
- 参数: pageNum, pageSize, courseName, teacherId, termId
- 返回: { "code": 200, "data": { "list": [], "total": 0 } }

#### 1.3 排课新增
- 请求方式: POST
- 路径: /api/schedule/add
- 参数: CourseSchedule
- 返回: { "code": 200, "message": "success" }

#### 1.4 学生选课
- 请求方式: POST
- 路径: /api/selection/select
- 参数: { "scheduleId": 1 }
- 返回: { "code": 200, "message": "success" }

#### 1.5 教师发布课程资源
- 请求方式: POST
- 路径: /api/resource/add
- 参数: CourseResource
- 返回: { "code": 200, "message": "success" }

#### 1.6 教师录入成绩
- 请求方式: PUT
- 路径: /api/score/update
- 参数: { "selectionId": 1, "usualScore": 90, "examScore": 92 }
- 返回: { "code": 200, "message": "success" }

#### 1.7 学生提交评教
- 请求方式: POST
- 路径: /api/evaluation/add
- 参数: { "scheduleId": 1, "score": 95, "content": "讲解清晰" }
- 返回: { "code": 200, "message": "success" }

#### 1.8 数据看板
- 请求方式: GET
- 路径: /api/statistics/dashboard
- 参数: 无
- 返回: { "code": 200, "data": { "courseCount": 0, "teacherCount": 0, "studentCount": 0 } }

### 项目结构

后端:
```
087-backend/
├── sql/
│   └── init.sql
├── src/main/java/com/course/
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
087-frontend/
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
- 管理员：admin，负责基础组织、课程、排课、公告与统计
- 教师：teacher，负责课程资源、考勤、成绩与评教结果查看
- 学生：student，负责选课、查看课表、查看资源、查看成绩、提交评教

## 默认账号
- admin / 123456
- teacher / 123456
- student / 123456
