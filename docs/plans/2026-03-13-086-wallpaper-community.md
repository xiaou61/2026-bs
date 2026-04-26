# 086 Wallpaper Community Implementation Plan

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** 构建一个包含前台展示、用户收藏投稿、管理员审核运营能力的壁纸社区网站。

**Architecture:** 后端使用 Spring Boot + MyBatis-Plus 提供统一 REST API、JWT 鉴权、Redis 会话缓存和本地图片上传能力；前端使用 Vue 3 + Element Plus 构建前台站点与后台管理两套路由界面，共享统一用户状态与请求封装。

**Tech Stack:** Spring Boot 2.7、MyBatis-Plus、MySQL、Redis、JWT、Vue 3、Vue Router、Pinia、Axios、Element Plus、ECharts

---

### Task 1: 项目文档与目录初始化

**Files:**
- Create: `086-backend/PRD.md`
- Create: `086-backend/PLAN.md`
- Create: `086-backend/sql/init.sql`
- Create: `086-frontend/package.json`

**Step 1: 写项目文档与目录结构**

- 明确壁纸网站产品边界、角色、模块、表设计、接口设计
- 创建后端和前端项目目录

**Step 2: 写数据库初始化脚本**

- 建表
- 初始化默认账号、分类、标签、壁纸、公告、轮播数据

**Step 3: 写基础工程配置**

- 后端 `pom.xml`
- 前端 `package.json`
- Vite 基础配置

### Task 2: 后端基础设施

**Files:**
- Create: `086-backend/src/main/java/com/wallpaper/WallpaperApplication.java`
- Create: `086-backend/src/main/java/com/wallpaper/common/*`
- Create: `086-backend/src/main/java/com/wallpaper/config/*`
- Create: `086-backend/src/main/java/com/wallpaper/utils/JwtUtils.java`
- Create: `086-backend/src/main/resources/application.yml`

**Step 1: 建立统一返回、异常处理、JWT 工具**

- Result
- BusinessException
- GlobalExceptionHandler
- JwtUtils

**Step 2: 建立配置层**

- RedisConfig
- MybatisPlusConfig
- WebMvcConfig
- JwtInterceptor

**Step 3: 配置静态文件访问**

- 配置上传目录访问映射
- 放行公开接口

### Task 3: 后端领域模型与数据访问

**Files:**
- Create: `086-backend/src/main/java/com/wallpaper/entity/*`
- Create: `086-backend/src/main/java/com/wallpaper/mapper/*`

**Step 1: 创建 11 个实体**

- 用户
- 分类
- 标签
- 壁纸
- 标签关联
- 收藏
- 下载
- 审核
- 轮播
- 公告
- 日志

**Step 2: 创建 MyBatis-Plus Mapper**

- 所有实体对应 `BaseMapper`

### Task 4: 后端业务与接口

**Files:**
- Create: `086-backend/src/main/java/com/wallpaper/service/*`
- Create: `086-backend/src/main/java/com/wallpaper/controller/*`
- Create: `086-backend/src/main/java/com/wallpaper/dto/*`

**Step 1: 认证与注册**

- 登录
- 注册
- 用户信息
- 退出登录

**Step 2: 内容管理**

- 分类管理
- 标签管理
- 壁纸管理
- 审核管理
- 收藏与下载
- 轮播管理
- 公告管理
- 统计看板

**Step 3: 上传接口**

- 图片类型校验
- 大小校验
- 本地保存
- 返回访问路径

### Task 5: 前端基础工程

**Files:**
- Create: `086-frontend/index.html`
- Create: `086-frontend/vite.config.js`
- Create: `086-frontend/src/main.js`
- Create: `086-frontend/src/App.vue`
- Create: `086-frontend/src/router/index.js`
- Create: `086-frontend/src/api/*`
- Create: `086-frontend/src/store/user.js`

**Step 1: 建立请求层与用户状态**

- Axios 拦截器
- token 持久化
- 登录状态恢复

**Step 2: 建立双布局路由**

- 前台公共布局
- 后台管理布局
- 登录注册页
- 路由守卫

### Task 6: 前端页面开发

**Files:**
- Create: `086-frontend/src/views/public/*`
- Create: `086-frontend/src/views/admin/*`
- Create: `086-frontend/src/views/Login.vue`
- Create: `086-frontend/src/views/Register.vue`

**Step 1: 前台页面**

- 首页
- 发现页
- 详情页
- 我的收藏
- 我的投稿

**Step 2: 后台页面**

- 数据看板
- 分类管理
- 标签管理
- 壁纸管理
- 审核管理
- 轮播管理
- 公告管理

### Task 7: 联调与收尾

**Files:**
- Modify: `086-backend/PRD.md`
- Modify: `086-backend/PLAN.md`

**Step 1: 对齐接口字段**

- 前后端请求参数一致
- 状态枚举一致

**Step 2: 检查关键流程**

- 登录
- 注册
- 上传
- 投稿审核
- 收藏
- 下载
- 前台筛选浏览

**Step 3: 输出交付说明**

- 说明已完成内容
- 说明未执行编译验证
