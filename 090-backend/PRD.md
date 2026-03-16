# 戏曲文化苑系统

## 项目概述

### 项目简介
戏曲文化苑系统面向戏曲数字化传承、文化活动运营与公众体验场景，支持管理员、艺术家、会员三角色协同完成剧种资料维护、名家档案展示、剧目排期预约、资源学习、签到研学与赏析互动，形成完整的戏曲文化服务闭环。

### 核心功能
- 用户登录与角色权限控制
- 剧种分类、名家档案、场馆、文化专题管理
- 剧目与排期管理
- 会员预约与个人行程
- 艺术家数字资源、签到、研学评分管理
- 会员赏析互动与公告展示
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
- 管理员、艺术家、会员三角色
- 登录、退出、个人信息查询
- 基于 JWT + Redis 的登录校验

### 2. 基础组织模块
- 剧种分类管理
- 名家档案管理
- 场馆管理
- 文化专题管理

### 3. 文化运营模块
- 剧目管理
- 排期管理
- 会员预约
- 个人行程查询

### 4. 传承过程模块
- 数字资源管理
- 签到管理
- 研学评分管理
- 剧目赏析互动

### 5. 运营统计模块
- 系统公告管理
- 首页数据看板

## 技术设计

### 数据库设计（14张表）
1. sys_user
2. opera_category
3. artist_archive
4. venue_info
5. cultural_season
6. repertoire_info
7. performance_schedule
8. booking_record
9. media_resource
10. checkin_record
11. review_record
12. appreciation_comment
13. system_notice
14. operation_log

### API接口设计

#### 1.1 用户登录
- 请求方式: POST
- 路径: /api/auth/login
- 参数: { "username": "admin", "password": "123456" }
- 返回: { "code": 200, "data": { "token": "...", "userInfo": {} } }

#### 1.2 剧目分页查询
- 请求方式: GET
- 路径: /api/repertoire/list
- 参数: pageNum, pageSize, courseName, artistId, termId
- 返回: { "code": 200, "data": { "list": [], "total": 0 } }

#### 1.3 排期新增
- 请求方式: POST
- 路径: /api/performance/add
- 参数: PerformanceSchedule
- 返回: { "code": 200, "message": "success" }

#### 1.4 会员预约
- 请求方式: POST
- 路径: /api/booking/select
- 参数: { "scheduleId": 1 }
- 返回: { "code": 200, "message": "success" }

#### 1.5 艺术家发布数字资源
- 请求方式: POST
- 路径: /api/resource/add
- 参数: MediaResource
- 返回: { "code": 200, "message": "success" }

#### 1.6 艺术家录入研学评分
- 请求方式: PUT
- 路径: /api/review/update
- 参数: { "selectionId": 1, "usualScore": 90, "examScore": 92 }
- 返回: { "code": 200, "message": "success" }

#### 1.7 会员提交赏析互动
- 请求方式: POST
- 路径: /api/comment/add
- 参数: { "scheduleId": 1, "score": 95, "content": "讲解清晰" }
- 返回: { "code": 200, "message": "success" }

#### 1.8 数据看板
- 请求方式: GET
- 路径: /api/statistics/dashboard
- 参数: 无
- 返回: { "code": 200, "data": { "repertoireCount": 0, "artistCount": 0, "memberCount": 0 } }

### 项目结构

后端:
```
090-backend/
├── sql/
│   └── init.sql
├── src/main/java/com/opera/
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
090-frontend/
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
- 管理员：admin，负责基础组织、剧目、排期、公告与统计
- 艺术家：artist，负责数字资源、签到、研学评分与赏析互动结果查看
- 会员：member，负责预约、查看行程、查看资源、查看研学评分、提交赏析互动

## 默认账号
- admin / 123456
- artist / 123456
- member / 123456


