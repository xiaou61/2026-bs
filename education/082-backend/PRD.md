# 公考学习平台

## 项目概述

### 项目简介
公考学习平台面向公务员考试备考场景，提供课程学习、题库刷题、模拟考试、学习计划与公告管理的一体化能力，支持管理员、讲师、学员三类角色协同。

### 核心功能
- 用户登录注册与角色权限管理
- 学科与课程管理
- 题库与试题管理
- 试卷与模拟考试管理
- 学习计划管理
- 公告通知与数据看板

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
- 管理员、讲师、学员三角色
- 账号登录、注册、个人信息查询
- 后台用户分页管理

### 2. 学科与课程模块
- 学科分类管理
- 课程信息管理（标题、学时、难度、状态）
- 学员端课程公开列表

### 3. 题库与试题模块
- 题库管理（按学科与题型）
- 试题管理（单选/多选/判断）
- 题量统计与筛选查询

### 4. 试卷与考试模块
- 模拟试卷管理
- 试卷题目关联管理
- 考试记录管理（成绩、耗时、通过状态）

### 5. 学习计划模块
- 学员学习计划制定
- 学习周期与每日目标维护
- 完成进度跟踪

### 6. 公告与看板模块
- 平台公告发布、上下线
- 公告公开列表与详情查看
- 首页统计看板

## 技术设计

### 数据库设计
1. user
2. subject
3. course
4. course_chapter
5. question_bank
6. question
7. exam_paper
8. exam_paper_question
9. exam_record
10. exam_record_answer
11. study_plan
12. notice

### API接口设计

#### 1.1 用户登录
- 请求方式: POST
- 路径: /api/user/login
- 参数: { "username": "admin", "password": "123456" }
- 返回: { "code": 200, "data": { "token": "...", "userInfo": {} } }

#### 1.2 课程分页列表
- 请求方式: GET
- 路径: /api/course/list
- 参数: pageNum, pageSize, title, subjectId, status
- 返回: { "code": 200, "data": { "records": [], "total": 0 } }

#### 1.3 试题新增
- 请求方式: POST
- 路径: /api/question/add
- 参数: Question对象
- 返回: { "code": 200, "message": "success" }

#### 1.4 仪表盘统计
- 请求方式: GET
- 路径: /api/statistics/dashboard
- 参数: 无
- 返回: { "code": 200, "data": { "userCount": 0, "courseCount": 0 } }

### 项目结构

后端:
```
082-backend/
├── sql/
│   └── init.sql
├── src/main/java/com/gongkao/
│   ├── common/
│   ├── config/
│   ├── controller/
│   ├── entity/
│   ├── mapper/
│   ├── service/
│   └── utils/
└── src/main/resources/
    └── application.yml
```

前端:
```
082-frontend/
├── src/
│   ├── views/
│   ├── api/
│   ├── router/
│   └── store/
```

## 用户角色
- 管理员：admin，管理全量业务数据
- 讲师：teacher，维护课程与题库内容
- 学员：student，学习、做题、考试、制定计划

## 默认账号
- admin / 123456
- teacher / 123456
- user / 123456
