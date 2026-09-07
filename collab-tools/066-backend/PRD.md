# 精简博客系统

## 项目概述
基于 Spring Boot + Vue3 的精简博客系统，提供博客文章发布、分类标签管理、评论互动、公告通知与后台看板能力，支持管理员与普通用户双角色协同。

## 技术栈
后端：Spring Boot 2.7.18 + MyBatis-Plus + MySQL 8.0 + Redis + JWT + Hutool  
前端：Vue 3.4.0 + Element Plus 2.4.4 + Vite + Pinia + Axios + ECharts 5.4.3

## 用户角色
- ADMIN：系统管理员，负责用户管理、分类标签、文章审核发布、评论处理、公告发布、看板分析
- USER：普通用户，负责浏览文章、发布个人文章、评论互动、维护个人资料

## 默认账号
- admin / 123456
- user / 123456

## 功能需求

### 1. 用户与认证
- 登录注册、JWT 鉴权、退出登录
- 个人资料维护、密码修改
- 管理员分页管理用户、角色与状态

### 2. 分类管理
- 分类新增、编辑、删除、启停
- 分类排序与列表展示

### 3. 标签管理
- 标签新增、编辑、删除、启停
- 标签列表维护

### 4. 文章管理
- 文章新增、编辑、删除
- 草稿/发布状态切换
- 置顶设置、浏览量统计
- 文章与标签关联

### 5. 评论管理
- 用户对文章发表评论
- 管理员审核、回复、删除评论
- 评论状态管理（待审核/通过/驳回）

### 6. 公告中心
- 管理员发布公告、停用公告、删除公告
- 全员查看已发布公告

### 7. 博客前台
- 文章分页浏览、关键词搜索
- 按分类筛选
- 文章详情页阅读与评论展示

### 8. 运营看板
- 统计文章总数、发布文章数、评论总数、用户总数
- 近7日发文趋势

## 数据库设计

### sys_user
- id BIGINT PK
- username VARCHAR(50) UNIQUE
- password VARCHAR(100)
- nickname VARCHAR(50)
- avatar VARCHAR(255)
- email VARCHAR(100)
- role VARCHAR(20)
- status INT
- last_login_time DATETIME
- create_time DATETIME
- update_time DATETIME

### blog_category
- id BIGINT PK
- name VARCHAR(50)
- sort INT
- status INT
- create_time DATETIME
- update_time DATETIME

### blog_tag
- id BIGINT PK
- name VARCHAR(50)
- status INT
- create_time DATETIME
- update_time DATETIME

### blog_post
- id BIGINT PK
- title VARCHAR(150)
- summary VARCHAR(300)
- content TEXT
- cover VARCHAR(255)
- category_id BIGINT
- author_id BIGINT
- status INT
- is_top INT
- view_count BIGINT
- comment_count INT
- create_time DATETIME
- update_time DATETIME

### blog_post_tag
- id BIGINT PK
- post_id BIGINT
- tag_id BIGINT

### blog_comment
- id BIGINT PK
- post_id BIGINT
- user_id BIGINT
- content VARCHAR(500)
- status INT
- reply_content VARCHAR(500)
- create_time DATETIME
- update_time DATETIME

### blog_notice
- id BIGINT PK
- title VARCHAR(100)
- content TEXT
- status INT
- admin_id BIGINT
- create_time DATETIME
- update_time DATETIME

## API 设计
- POST /api/auth/login
- POST /api/auth/register
- GET /api/auth/info
- PUT /api/auth/password
- POST /api/auth/logout

- GET /api/user/page
- POST /api/user
- PUT /api/user
- PUT /api/user/status
- PUT /api/user/profile
- DELETE /api/user/{id}

- GET /api/category/list
- GET /api/category/page
- POST /api/category
- PUT /api/category
- DELETE /api/category/{id}

- GET /api/tag/list
- GET /api/tag/page
- POST /api/tag
- PUT /api/tag
- DELETE /api/tag/{id}

- GET /api/post/public-page
- GET /api/post/page
- GET /api/post/detail/{id}
- POST /api/post
- PUT /api/post
- PUT /api/post/status
- PUT /api/post/top
- DELETE /api/post/{id}

- GET /api/comment/post-page
- GET /api/comment/page
- POST /api/comment
- PUT /api/comment/review
- DELETE /api/comment/{id}

- GET /api/notice/list
- GET /api/notice/page
- POST /api/notice
- PUT /api/notice
- DELETE /api/notice/{id}

- GET /api/dashboard/stats
- GET /api/dashboard/trend
