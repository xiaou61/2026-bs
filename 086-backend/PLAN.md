# 086项目实施计划

## 问题陈述
需要构建一个完整的壁纸社区网站，实现壁纸投稿、审核、发布、浏览、收藏、下载和后台运营管理的一体化流程。

## 当前状态
- 根目录已有多个前后端项目模板可复用
- 086 项目目录已创建
- 已完成 086 项目设计文档与 PRD 初稿

## 实施方案

### 第一阶段: 后端开发
1. 基础架构
- 创建 pom.xml
- 创建 application.yml
- 创建启动类
- 创建 Result、异常处理、JWT 鉴权
- 创建 MyBatis-Plus、Redis、上传访问配置

2. 数据库脚本
- 创建 sql/init.sql
- 完成 11 张表与测试数据

3. 实体与 Mapper
- 创建用户、分类、标签、壁纸、收藏、下载、审核、轮播、公告、日志实体
- 创建 MyBatis-Plus Mapper

4. Service 层
- 登录注册与用户信息
- 分类标签管理
- 壁纸管理与公开检索
- 收藏下载业务
- 审核业务
- 轮播与公告业务
- 统计看板
- 图片上传

5. Controller 层
- AuthController
- CategoryController
- TagController
- WallpaperController
- FavoriteController
- AuditController
- BannerController
- NoticeController
- StatisticsController
- UploadController

### 第二阶段: 前端开发
1. 基础工程
- package.json、vite.config.js、main.js、App.vue
- Axios 封装
- Pinia 用户状态
- 双布局路由与权限守卫

2. 页面开发
- 登录页
- 注册页
- 前台首页
- 发现页
- 详情页
- 我的收藏
- 我的投稿
- 后台仪表盘
- 分类管理
- 标签管理
- 壁纸管理
- 审核管理
- 轮播管理
- 公告管理

3. 联调优化
- 上传组件与接口对接
- 筛选条件与字段统一
- 前后台交互提示与分页统一

## 交付结果
- 086-backend 后端完整代码
- 086-frontend 前端完整代码
- 壁纸社区网站数据库脚本与测试数据
- 086-backend/PRD.md
- 086-backend/PLAN.md
