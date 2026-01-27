# 网课在线学习观看系统 PRD

## 1. 项目概述
基于Java+SpringBoot+Vue前后端分离的网课在线学习观看系统，为用户提供在线课程学习平台。

## 2. 技术架构
- 后端：Spring Boot 2.7.18 + MyBatis-Plus 3.5.4 + Redis + JWT
- 前端用户端：Vue3 + Element Plus + Vite
- 前端管理端：Vue3 + Element Plus + Vite
- 数据库：MySQL 8.0

## 3. 功能需求

### 3.1 用户端功能
| 模块 | 功能 |
|------|------|
| 用户认证 | 注册、登录、退出、修改密码、个人信息 |
| 首页 | 轮播图、推荐课程、热门课程、最新课程 |
| 课程浏览 | 分类筛选、关键词搜索、排序(热度/最新/评分) |
| 课程详情 | 课程介绍、章节列表、教师信息、评价列表 |
| 视频学习 | 视频播放、章节切换、学习进度保存 |
| 互动功能 | 课程评分、发表评论、收藏课程 |
| 学习中心 | 我的课程、学习记录、收藏列表 |

### 3.2 管理端功能
| 模块 | 功能 |
|------|------|
| 数据统计 | 用户总数、课程总数、学习人次、今日新增 |
| 用户管理 | 用户列表、状态管理、角色管理 |
| 分类管理 | 分类CRUD |
| 课程管理 | 课程CRUD、章节管理、视频管理 |
| 评论管理 | 评论列表、审核、删除 |
| 轮播图管理 | 轮播图CRUD |

## 4. 数据库设计

### 4.1 用户表 (user)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| username | varchar(50) | 用户名 |
| password | varchar(100) | 密码(MD5) |
| nickname | varchar(50) | 昵称 |
| avatar | varchar(255) | 头像 |
| email | varchar(100) | 邮箱 |
| phone | varchar(20) | 手机号 |
| role | tinyint | 角色:0学生1教师2管理员 |
| status | tinyint | 状态:0禁用1正常 |
| create_time | datetime | 创建时间 |
| update_time | datetime | 更新时间 |

### 4.2 课程分类表 (category)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| name | varchar(50) | 分类名称 |
| icon | varchar(255) | 图标 |
| sort | int | 排序 |
| status | tinyint | 状态 |
| create_time | datetime | 创建时间 |

### 4.3 课程表 (course)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| title | varchar(100) | 课程标题 |
| cover | varchar(255) | 封面图 |
| description | text | 课程描述 |
| category_id | bigint | 分类ID |
| teacher_id | bigint | 教师ID |
| price | decimal(10,2) | 价格(0为免费) |
| is_free | tinyint | 是否免费 |
| learn_count | int | 学习人数 |
| chapter_count | int | 章节数 |
| duration | int | 总时长(秒) |
| score | decimal(3,1) | 评分 |
| status | tinyint | 状态:0下架1上架 |
| create_time | datetime | 创建时间 |
| update_time | datetime | 更新时间 |

### 4.4 章节表 (chapter)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| course_id | bigint | 课程ID |
| title | varchar(100) | 章节标题 |
| sort | int | 排序 |
| create_time | datetime | 创建时间 |

### 4.5 视频表 (video)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| chapter_id | bigint | 章节ID |
| course_id | bigint | 课程ID |
| title | varchar(100) | 视频标题 |
| url | varchar(500) | 视频地址 |
| duration | int | 时长(秒) |
| sort | int | 排序 |
| is_free | tinyint | 是否免费试看 |
| create_time | datetime | 创建时间 |

### 4.6 学习记录表 (learn_record)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| user_id | bigint | 用户ID |
| course_id | bigint | 课程ID |
| video_id | bigint | 当前视频ID |
| progress | int | 观看进度(秒) |
| duration | int | 已学时长(秒) |
| finished_count | int | 已完成视频数 |
| last_learn_time | datetime | 最后学习时间 |
| create_time | datetime | 创建时间 |
| update_time | datetime | 更新时间 |

### 4.7 评论表 (comment)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| user_id | bigint | 用户ID |
| course_id | bigint | 课程ID |
| content | text | 评论内容 |
| score | int | 评分(1-5) |
| status | tinyint | 状态:0待审1通过2拒绝 |
| create_time | datetime | 创建时间 |

### 4.8 收藏表 (favorite)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| user_id | bigint | 用户ID |
| course_id | bigint | 课程ID |
| create_time | datetime | 创建时间 |

### 4.9 轮播图表 (banner)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| title | varchar(100) | 标题 |
| image | varchar(255) | 图片地址 |
| url | varchar(255) | 跳转链接 |
| sort | int | 排序 |
| status | tinyint | 状态 |
| create_time | datetime | 创建时间 |

## 5. 接口设计

### 5.1 用户端接口

#### 用户模块
- POST /api/user/register - 注册
- POST /api/user/login - 登录
- GET /api/user/info - 获取用户信息
- PUT /api/user/info - 更新用户信息
- PUT /api/user/password - 修改密码

#### 首页模块
- GET /api/banner/list - 轮播图列表
- GET /api/course/recommend - 推荐课程
- GET /api/course/hot - 热门课程
- GET /api/course/latest - 最新课程

#### 课程模块
- GET /api/category/list - 分类列表
- GET /api/course/list - 课程列表(分页/筛选/搜索)
- GET /api/course/{id} - 课程详情
- GET /api/course/{id}/chapters - 课程章节列表

#### 学习模块
- POST /api/learn/start - 开始学习课程
- GET /api/learn/video/{id} - 获取视频播放信息
- POST /api/learn/progress - 保存学习进度
- GET /api/learn/my-courses - 我的课程列表
- GET /api/learn/records - 学习记录

#### 互动模块
- GET /api/comment/list - 课程评论列表
- POST /api/comment/add - 发表评论
- POST /api/favorite/add - 添加收藏
- DELETE /api/favorite/{courseId} - 取消收藏
- GET /api/favorite/list - 收藏列表
- GET /api/favorite/check/{courseId} - 检查是否收藏

### 5.2 管理端接口

#### 管理员认证
- POST /api/admin/login - 管理员登录
- GET /api/admin/stats - 数据统计

#### 用户管理
- GET /api/admin/user/list - 用户列表
- PUT /api/admin/user/status - 修改用户状态

#### 分类管理
- GET /api/admin/category/list - 分类列表
- POST /api/admin/category/add - 添加分类
- PUT /api/admin/category/update - 更新分类
- DELETE /api/admin/category/{id} - 删除分类

#### 课程管理
- GET /api/admin/course/list - 课程列表
- POST /api/admin/course/add - 添加课程
- PUT /api/admin/course/update - 更新课程
- DELETE /api/admin/course/{id} - 删除课程
- PUT /api/admin/course/status - 修改课程状态
- GET /api/admin/course/{id}/chapters - 获取课程章节
- POST /api/admin/chapter/add - 添加章节
- PUT /api/admin/chapter/update - 更新章节
- DELETE /api/admin/chapter/{id} - 删除章节
- POST /api/admin/video/add - 添加视频
- PUT /api/admin/video/update - 更新视频
- DELETE /api/admin/video/{id} - 删除视频

#### 评论管理
- GET /api/admin/comment/list - 评论列表
- PUT /api/admin/comment/status - 审核评论
- DELETE /api/admin/comment/{id} - 删除评论

#### 轮播图管理
- GET /api/admin/banner/list - 轮播图列表
- POST /api/admin/banner/add - 添加轮播图
- PUT /api/admin/banner/update - 更新轮播图
- DELETE /api/admin/banner/{id} - 删除轮播图

## 6. 项目结构

### 6.1 后端结构
```
052-backend/
├── sql/
│   └── init.sql
├── src/main/java/com/online/
│   ├── OnlineCourseApplication.java
│   ├── controller/
│   │   ├── UserController.java
│   │   ├── CourseController.java
│   │   ├── LearnController.java
│   │   ├── CommentController.java
│   │   ├── FavoriteController.java
│   │   └── AdminController.java
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

### 6.2 前端用户端结构
```
052-frontend/user/
├── src/
│   ├── views/
│   │   ├── Home.vue
│   │   ├── CourseList.vue
│   │   ├── CourseDetail.vue
│   │   ├── VideoPlayer.vue
│   │   ├── Login.vue
│   │   ├── Register.vue
│   │   ├── MyCourse.vue
│   │   ├── Favorite.vue
│   │   └── Profile.vue
│   ├── components/
│   ├── api/
│   ├── router/
│   ├── stores/
│   └── App.vue
├── index.html
├── vite.config.js
└── package.json
```

### 6.3 前端管理端结构
```
052-frontend/admin/
├── src/
│   ├── views/
│   │   ├── Login.vue
│   │   ├── Layout.vue
│   │   ├── Dashboard.vue
│   │   ├── User.vue
│   │   ├── Category.vue
│   │   ├── Course.vue
│   │   ├── Comment.vue
│   │   └── Banner.vue
│   ├── api/
│   ├── router/
│   └── main.js
├── index.html
├── vite.config.js
└── package.json
```

## 7. 默认账号
- 管理员：admin / 123456
- 测试教师：teacher / 123456
- 测试学生：student / 123456
