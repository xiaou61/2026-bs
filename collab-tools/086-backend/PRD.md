# 高清壁纸社区网站

## 项目概述

### 项目简介
高清壁纸社区网站面向桌面端与移动端壁纸浏览下载场景，支持游客浏览、用户注册收藏与投稿、管理员审核运营，实现从内容上传、审核发布到浏览下载和统计分析的完整业务闭环。

### 核心功能
- 用户登录注册与角色权限控制
- 壁纸分类、标签、色系、分辨率筛选
- 壁纸上传投稿、审核上架、前台展示
- 收藏与下载记录
- 轮播图与公告运营
- 数据统计看板

### 技术栈

**后端**
- Spring Boot 2.7.18
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
- 管理员与普通用户双角色
- 账号登录、注册、退出、个人信息查询
- 基于 JWT + Redis 的登录校验

### 2. 壁纸分类与标签模块
- 分类新增、编辑、删除、启停、排序
- 标签新增、编辑、删除、启停、颜色配置
- 分类标签启用列表查询

### 3. 壁纸内容模块
- 壁纸新增、编辑、删除
- 封面图、原图、预览图上传
- 壁纸类型、分辨率、色系、作者、描述维护
- 公开列表查询、详情展示、推荐列表

### 4. 投稿审核模块
- 用户投稿后进入待审核
- 管理员审核通过或驳回
- 审核备注记录查询
- 审核通过后可上架展示

### 5. 收藏与下载模块
- 用户收藏、取消收藏
- 我的收藏查询
- 下载记录写入
- 下载次数累计

### 6. 运营模块
- 轮播图管理
- 系统公告管理
- 首页看板统计

## 技术设计

### 数据库设计（11张表）
1. sys_user
2. wallpaper_category
3. wallpaper_tag
4. wallpaper_info
5. wallpaper_tag_rel
6. wallpaper_favorite
7. wallpaper_download
8. wallpaper_audit
9. carousel_banner
10. system_notice
11. operation_log

### API 接口设计

#### 1.1 用户登录
- 请求方式: POST
- 路径: /api/auth/login
- 参数: { "username": "admin", "password": "123456" }
- 返回: { "code": 200, "data": { "token": "...", "userInfo": {} } }

#### 1.2 用户注册
- 请求方式: POST
- 路径: /api/auth/register
- 参数: { "username": "user01", "password": "123456", "realName": "用户01" }
- 返回: { "code": 200, "message": "success" }

#### 1.3 公开壁纸列表
- 请求方式: GET
- 路径: /api/wallpaper/public/list
- 参数: pageNum, pageSize, title, categoryId, tagId, wallpaperType, colorHex, resolution
- 返回: { "code": 200, "data": { "list": [], "total": 0 } }

#### 1.4 壁纸投稿新增
- 请求方式: POST
- 路径: /api/wallpaper/add
- 参数: { "title": "银河夜景", "categoryId": 1, "imageUrl": "/files/image/20260313/a.jpg" }
- 返回: { "code": 200, "message": "success" }

#### 1.5 壁纸审核
- 请求方式: PUT
- 路径: /api/audit/submit
- 参数: { "wallpaperId": 1, "auditStatus": 1, "auditRemark": "审核通过" }
- 返回: { "code": 200, "message": "success" }

#### 1.6 收藏壁纸
- 请求方式: POST
- 路径: /api/favorite/add/1
- 参数: 无
- 返回: { "code": 200, "message": "success" }

#### 1.7 图片上传
- 请求方式: POST
- 路径: /api/upload/image
- 参数: multipart/form-data
- 返回: { "code": 200, "data": { "url": "/files/image/20260313/xxx.png" } }

#### 1.8 数据看板
- 请求方式: GET
- 路径: /api/statistics/dashboard
- 参数: 无
- 返回: { "code": 200, "data": { "wallpaperCount": 0, "downloadCount": 0, "favoriteCount": 0 } }

### 项目结构

后端:
```
086-backend/
├── sql/
│   └── init.sql
├── src/main/java/com/wallpaper/
│   ├── common/
│   ├── config/
│   ├── controller/
│   ├── dto/
│   ├── entity/
│   ├── mapper/
│   ├── service/
│   └── utils/
└── src/main/resources/
    └── application.yml
```

前端:
```
086-frontend/
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
        ├── public/
        ├── admin/
        ├── Login.vue
        └── Register.vue
```

## 用户角色
- 管理员：admin，负责内容审核、分类标签运营、公告和轮播维护
- 普通用户：user，负责浏览、收藏、下载、投稿

## 默认账号
- admin / 123456
- user / 123456
