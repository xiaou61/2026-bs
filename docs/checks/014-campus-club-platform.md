# 014 校园社团与兴趣圈层平台检查报告

## 1. 基本信息

- 项目编号：`014`
- 项目名称：校园社团与兴趣圈层平台
- 检查日期：`2026-04-05`
- 项目目录：
  - 后端：`014-backend`
  - 前端：集成在 `014-backend/src/main/resources/static`
- 主要资料：
  - 项目总览：`readme_simple.md`
  - 后端 PRD：`014-backend/PRD/PRD.md`
  - 账号文档：`014-backend/ACCOUNTS.md`
  - 快速启动：`014-backend/QUICK_START.md`
  - 项目总结：`014-backend/PROJECT_SUMMARY.md`
  - 后端配置：`014-backend/src/main/resources/application.yml`
  - 数据脚本：`014-backend/src/main/resources/sql/schema.sql`
  - 数据脚本：`014-backend/src/main/resources/sql/init_data.sql`
  - 前端说明：`014-backend/src/main/resources/static/README.md`

## 2. 项目形态结论

- 当前仓库仅存在 `014-backend`
- 结合 `pom.xml`、`application.yml`、`static/` 目录和页面脚本，可确认该项目为 Spring Boot + 静态页面一体化项目
- 前端由 jQuery + Bootstrap 编写，通过 Spring Boot 直接提供静态资源服务

## 3. 文档与技术栈检查

### 3.1 技术栈

- 后端：
  - Spring Boot `3.2.0`
  - MyBatis-Plus `3.5.5`
  - MySQL `8`
  - JWT `0.11.5`
  - jBCrypt `0.4`
  - JDK `17`
- 前端：
  - jQuery `3.7.1`
  - Bootstrap `5.3.2`
  - Bootstrap Icons `1.11.2`

### 3.2 运行前置

- 服务端口：`8080`
- 数据库：`campus_club`
- 数据库账号：`root / 1234`
- 上传目录：`D:/uploads/campus-club/`
- 前端访问入口：
  - 用户端：`/index.html`
  - 管理端登录：`/admin-login.html`

### 3.3 本轮处理的文档冲突

1. `PROJECT_SUMMARY.md` 把多个前端页面描述为已完成
2. `src/main/resources/static/README.md` 却把 `club-detail.html`、`activities.html`、`topics.html`、`profile.html`、`my-clubs.html`、`my-activities.html` 等页面写成“待创建”
3. 实际静态目录中这些页面文件已经存在，且本轮还补齐了：
   - `create-club.html`
   - `create-circle.html`
   - `create-topic.html`

本轮已更新 `static/README.md`，将页面说明修正为当前真实状态。

## 4. 当前源码覆盖情况

### 4.1 已落地模块

- 页面文件：
  - `index.html`
  - `login.html`
  - `register.html`
  - `clubs.html`
  - `club-detail.html`
  - `create-club.html`
  - `activities.html`
  - `activity-detail.html`
  - `topics.html`
  - `create-topic.html`
  - `topic-detail.html`
  - `circles.html`
  - `create-circle.html`
  - `profile.html`
  - `my-clubs.html`
  - `my-activities.html`
  - `admin-login.html`
  - `admin-dashboard.html`
- 控制器：
  - `UserController`
  - `ClubController`
  - `ActivityController`
  - `CircleController`
  - `TopicController`
  - `InterestController`
  - `NotificationController`
  - `UploadController`
  - `AdminController`

### 4.2 本轮已修复的明确问题

1. 修复公开广场页接口被错误鉴权拦截的问题：
   - 未登录时可正常访问社团、活动、话题、圈子和兴趣标签的公开 GET 接口
2. 修复后台接口无角色权限校验的问题：
   - 学生 token 访问 `/api/admin/**` 现在返回 `403`
3. 修复分页插件缺失导致 `total/pages` 错误的问题：
   - 新增 `MybatisPlusConfig`
4. 修复管理员用户列表返回密码字段的问题：
   - `User` / `Admin` 的密码字段已从 JSON 响应中彻底移除
5. 修复管理员前端登录态误判的问题：
   - 普通学生登录态不再自动跳转后台页
6. 修复个人中心勋章墙占位的问题：
   - 已新增 `/api/user/badges`
   - 已接入真实勋章渲染
7. 修复社团创建入口断链的问题：
   - 已新增 `create-club.html`
8. 修复圈子创建与话题发布仅为占位提示的问题：
   - 已新增 `create-circle.html`
   - 已新增 `create-topic.html`
   - `circles.html`、`topics.html` 已接入真实创建流程
9. 修复前端说明文档不一致的问题：
   - `static/README.md` 已更新为真实页面清单

### 4.3 当前仍未完全落地的功能

- 用户端暂无“积分流水”独立页面，当前只展示积分总数与勋章墙
- 通知模块后端存在，但没有独立前端页面入口
- 管理员角色仍是统一后台权限，未继续细分“超级管理员 / 普通管理员”差异
- 自动化测试仍仅有 `PasswordTest`，缺少真正的接口/页面测试
- 前端依赖 CDN，离线环境下静态资源仍会受影响

## 5. 构建、启动与抽测结果

### 5.1 构建与测试

- 命令：`mvn -version`
  - 结果：JDK `17`
- 命令：`mvn test -DskipTests=false`
  - 结果：通过

补充说明：

- 当前后端测试仍以 `PasswordTest` 为主，`mvn test` 更偏向编译与 Spring 容器启动校验

### 5.2 启动验证

- 后端成功启动在 `8080`
- 页面访问验证：
  - `/index.html` 返回 `200`
  - `/clubs.html` 返回 `200`
  - `/create-club.html` 返回 `200`
  - `/create-circle.html` 返回 `200`
  - `/create-topic.html` 返回 `200`
  - `/admin-login.html` 返回 `200`

### 5.3 接口抽测

登录账号实测：

- 管理员：`admin / 123456`
- 学生：`student1 / 123456`
- 学生：`student2 / 123456`

验证通过：

- `POST /api/user/login`
- `POST /api/admin/login`
- `GET /api/clubs`
- `GET /api/activities`
- `GET /api/topics`
- `GET /api/circles`
- `GET /api/user/info`
- `GET /api/user/badges`
- `GET /api/admin/statistics`
- `GET /api/admin/users`
- `GET /api/admin/clubs/pending`
- `PUT /api/admin/clubs/5/approve`
- `POST /api/circles`
- `POST /api/circles/5/join`
- `GET /api/circles/my`
- `POST /api/topics`
- `POST /api/topics/5/like`
- `POST /api/topics/5/comments`
- `GET /api/topics/5`
- `GET /api/topics/5/comments`
- `POST /api/clubs`
- `GET /api/clubs/my`
- `POST /api/activities/1/register`
- `POST /api/activities/1/sign`
- `POST /api/activities/1/rate`
- `GET /api/activities/my`

关键复测结果：

- 公开访问验证：
  - 未登录访问 `/api/clubs?page=1&size=5`、`/api/activities?page=1&size=5&status=0`、`/api/topics?page=1&size=5`、`/api/circles?page=1&size=5` 均成功返回分页数据
- 后台权限验证：
  - `student1` token 访问 `/api/admin/statistics` 返回 `HTTP 403`
- 密码字段验证：
  - `/api/admin/users?page=1&size=5` 的响应键中不再包含 `password`
- 勋章验证：
  - `student1` 的 `/api/user/badges` 返回 `1` 条勋章数据
- 创建与审核验证：
  - `student1` 创建圈子成功，生成新圈子
  - `student1` 发布话题成功，生成新话题
  - `student1` 创建社团成功，进入待审核列表
  - 管理员审核通过后，新社团出现在前台社团列表
- 互动链路验证：
  - `student2` 成功加入新圈子
  - `student2` 成功点赞并评论新话题
- 活动链路验证：
  - `student2` 成功报名活动 `ID=1`
  - 使用签到码 `CODE2024` 签到成功
  - 评分 `5` 分成功

## 6. 结论

- `014` 已通过 JDK 17 编译、后端测试和整体启动验证
- 本轮已修复公开页被错误鉴权拦截、后台越权、分页失效、密码字段泄露、勋章墙占位和创建入口缺失/占位等明确问题
- 当前可以确认该项目已达到“可构建、可启动、核心链路可抽测、主要前后台入口可闭环”的状态
- 仍需后续继续完善的主要部分是：
  - 积分流水和通知的独立前端页面
  - 管理员更细粒度的角色权限
  - 自动化接口/页面测试
  - 前端脱离 CDN 的本地静态资源方案
