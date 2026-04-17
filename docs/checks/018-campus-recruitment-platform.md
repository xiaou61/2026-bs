# 018 校园实习招聘与求职平台检查报告

## 1. 基本信息

- 项目编号：`018`
- 项目名称：校园实习招聘与求职平台
- 检查日期：`2026-04-06`
- 项目目录：
  - 后端：`018-backend`
  - 前端：`018-frontend`
- 主要资料：
  - 项目总览：`readme_simple.md`
  - 后端 PRD：`018-backend/PRD/PRD.md`
  - 账号文档：`018-backend/ACCOUNTS.md`
  - 后端配置：`018-backend/pom.xml`
  - 应用配置：`018-backend/src/main/resources/application.yml`
  - 初始化脚本：`018-backend/src/main/resources/schema.sql`
  - 前端说明：`018-frontend/README.md`
  - 前端依赖：`018-frontend/package.json`

## 2. 项目形态结论

- 当前仓库已确认存在 `018-backend` 与 `018-frontend`
- 项目形态为 Spring Boot + Vue 3 + Vite 的前后端分离项目
- 后端运行口径：
  - JDK：`17`
  - Spring Boot：`3.2.0`
  - MyBatis-Plus：`3.5.5`
  - 数据库：`campus_recruitment`
  - 端口：`8080`
- 前端运行口径：
  - Vue：`3`
  - Vite：`5`
  - Element Plus：`2.5.4`
  - 开发端口：`5173`

## 3. 文档与源码一致性结论

- PRD 中的学生端、企业端、投递、面试、经验分享、内推等核心模块在源码中均能找到对应控制器、API 模块和页面
- 本轮已补齐并验证管理员端：
  - 后端新增 `AdminController`
  - 前端新增 `AdminLayout` 和 `admin/Dashboard.vue`
- 账号文档、应用配置、数据库脚本与实际默认账号口径一致：
  - `admin / 123456`
  - `student1 / 123456`
  - `company1 / 123456`
- 整体结论为“基本通过”，主要因为仍存在前端包体偏大和自动化测试覆盖不足的问题，但不阻塞启动和主链路使用

## 4. 本轮发现并修复的问题

### 4.1 权限与业务链路

1. 已修复学生可直接发布岗位的问题
2. 已修复学生可直接修改企业信息的问题
3. 已修复学生可查看企业收到投递的问题
4. 已修复企业端面试列表接口设计错误，新增 `/api/interview/company`
5. 已修复简历详情缺少归属校验的问题，企业只能查看已投递学生的简历
6. 已修复企业注册后未自动创建企业资料、`companyId` 为空的问题
7. 已补齐管理员后台概览、企业列表与企业审核能力

### 4.2 基础设施与测试

1. 已新增 `MybatisPlusConfig`，补齐分页插件，修复 `total/pages` 异常
2. 已将 `PasswordTest` 从“只打印”改为有断言的真实测试
3. 已新增 `PublicRoutePatternTest`，防止公开详情路由再次误放行写接口

### 4.3 本轮新增定位并修复的问题

1. `WebConfig` 中使用 `/api/job/{id}`、`/api/company/{id}` 作为公开排除规则时，会误把 `/api/job/publish`、`/api/company/update` 一并排除，导致写接口绕过拦截器
2. 本轮已改为仅匹配数字 ID 的规则：
   - `/api/job/{id:\\d+}`
   - `/api/company/{id:\\d+}`
   - `/api/experience/{id:\\d+}`
   - `/api/referral/{id:\\d+}`
3. 前端岗位类型展示已兼容 `intern` / `internship` 两种值，避免实习岗显示成“校招”

## 5. 构建、测试与启动结果

### 5.1 后端

- `mvn -version`：已确认使用 JDK `17`
- `mvn test -DskipTests=false`：通过
- `mvn spring-boot:run`：通过
- 启动日志确认：
  - `Started RecruitmentApplication`
  - 监听端口：`8080`

### 5.2 前端

- `npm run build`：通过
- `npm run dev -- --host 127.0.0.1 --port 5173`：通过
- Vite 页面访问与浏览器渲染验证：通过

## 6. 核心业务链路复测

### 6.1 登录与公开访问

- 管理员登录：通过
- 学生登录：通过
- 企业登录：通过
- 无登录访问企业详情 `GET /api/company/1`：通过
- 无登录访问岗位详情 `GET /api/job/1`：通过

### 6.2 注册与账号能力

- 新注册企业账号后再次登录，`companyId` 为非空：通过
- 新注册学生账号后可正常登录：通过

### 6.3 学生端与企业端主链路

1. 企业发布岗位：通过
2. 学生创建简历：通过
3. 学生投递岗位：通过
4. 企业查看收到的投递：通过
5. 企业安排面试：通过
6. 学生查看我的面试：通过
7. 企业查看企业面试：通过
8. 企业查看已投递学生的简历：通过
9. 其他学生查看该简历：返回 `403`，符合预期
10. 学生发布岗位：返回 `403`，符合预期
11. 学生更新企业信息：返回 `403`，符合预期

### 6.4 管理员链路

1. `GET /api/admin/overview`：通过
2. `GET /api/admin/company/list`：通过
3. `PUT /api/admin/company/review`：通过

### 6.5 浏览器页面验证

- `/login`：页面正常渲染
- `/register`：页面正常渲染
- `/student/jobs`：页面正常渲染并展示岗位数据
- `/company/jobs`：页面正常渲染并展示企业岗位数据
- `/company/applications`：页面正常渲染并展示收到的投递
- `/company/interviews`：页面正常渲染并展示企业面试
- `/admin/dashboard`：页面正常渲染并展示后台统计与企业审核数据

## 7. 最终结论

- `018` 已完成巡检
- 项目在当前机器环境下已确认兼容 JDK `17`
- 后端测试、前端构建、前后端启动和核心招聘求职链路均已通过
- 当前可归类为：`已通过测试并可启动`

## 8. 剩余风险

1. 前端 `npm run build` 仍有大包告警，但不影响当前功能正确性
2. Vite 开发环境存在 `favicon.ico` `404`，属于静态资源缺失，不影响主流程
3. 自动化测试目前仍偏轻，核心链路主要依赖本轮接口与浏览器复测
