# 教学资料管理系统

## 项目概述

### 项目简介
教学资料管理系统面向高校与培训机构的教学资源数字化管理场景，支持教学资料上传、分类、审核、检索、下载、收藏与统计分析，提升资料流通效率与教学协同能力。

### 核心功能
- 用户登录与角色权限控制
- 资料分类与标签管理
- 教学资料上传、编辑、审核、上下架
- 资料检索、预览、下载与收藏
- 下载记录与行为统计
- 公告发布与看板分析

### 技术栈

**后端**
- Spring Boot 2.7.18
- MyBatis 3.5.13
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
- 用户状态启停与角色分配

### 2. 资料分类模块
- 分类树管理（新增、编辑、删除）
- 分类排序与启停控制
- 分类公开列表查询

### 3. 教学资料模块
- 资料基础信息管理（标题、简介、学科、年级、关键词）
- 文件上传记录（文件名、路径、大小、类型）
- 审核流转（待审核、已通过、已驳回）
- 上下架管理（上架可检索、下架不可见）

### 4. 资料检索与下载模块
- 多条件检索（标题、分类、标签、上传者）
- 资料详情查看与下载
- 下载次数统计与热门资料排行

### 5. 收藏与学习清单模块
- 学生收藏资料
- 收藏列表与取消收藏
- 个人学习资料清单

### 6. 公告与看板模块
- 公告发布、修改、删除
- 首页统计看板（资料总数、下载总量、审核通过率）
- 分类分布与下载趋势图

## 技术设计

### 数据库设计
1. sys_user
2. material_category
3. material_tag
4. material_info
5. material_file
6. material_tag_rel
7. material_audit
8. material_download
9. material_favorite
10. study_list
11. system_notice
12. operation_log

### API接口设计

#### 1.1 用户登录
- 请求方式: POST
- 路径: /api/auth/login
- 参数: { "username": "admin", "password": "123456" }
- 返回: { "code": 200, "data": { "token": "...", "userInfo": {} } }

#### 1.2 资料分页查询
- 请求方式: GET
- 路径: /api/material/list
- 参数: pageNum, pageSize, title, categoryId, auditStatus, publishStatus
- 返回: { "code": 200, "data": { "records": [], "total": 0 } }

#### 1.3 资料新增
- 请求方式: POST
- 路径: /api/material/add
- 参数: MaterialDTO
- 返回: { "code": 200, "message": "success" }

#### 1.4 资料审核
- 请求方式: PUT
- 路径: /api/material/audit
- 参数: { "materialId": 1, "auditStatus": 1, "auditRemark": "通过" }
- 返回: { "code": 200, "message": "success" }

#### 1.5 下载统计看板
- 请求方式: GET
- 路径: /api/statistics/dashboard
- 参数: 无
- 返回: { "code": 200, "data": { "materialCount": 0, "downloadCount": 0, "passRate": 0 } }

### 项目结构

后端:
```
084-backend/
├── sql/
│   └── init.sql
├── src/main/java/com/teachres/
│   ├── TeachResApplication.java
│   ├── common/
│   ├── config/
│   ├── controller/
│   ├── dto/
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
084-frontend/
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
        ├── material/
        ├── audit/
        ├── favorite/
        └── notice/
```

## 用户角色
- 管理员：admin，管理用户、分类、审核、公告与统计
- 教师：teacher，上传资料、维护本人资料、处理驳回
- 学生：student，检索下载资料、收藏与学习清单管理

## 默认账号
- admin / 123456
- teacher / 123456
- student / 123456
