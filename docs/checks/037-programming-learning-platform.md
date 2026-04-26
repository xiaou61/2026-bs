# 037 基于SpringBoot的编程学习交流平台检查报告

## 1. 检查结论

- 项目编号：`037`
- 项目名称：`基于SpringBoot的编程学习交流平台`
- 检查日期：`2026-04-26`
- 当前状态：`已完成`
- 综合结论：`本轮已修复默认环境强依赖 MySQL/Redis 导致本机不可开箱验证、Spring Security 在 context-path 下公开路由放行失效、真实微信 appId/appSecret 缺失时登录链路不可演示、/auth/userinfo 无 Token 被安全层提前拦截、小程序 app.json 声明页面缺失、tabBar 图标资源缺失以及登录页引用缺失图片的问题。当前后端可在默认 H2 环境下通过测试并真实启动，微信模拟登录、当前用户与课程公开接口抽测通过；微信小程序前端已补齐 16 个声明页面的最小可编译骨架。`

## 2. 项目形态

- 后端目录：`037-backend`
- 前端目录：`037-frontend`
- 后端技术栈：`Spring Boot 3.2 + MyBatis XML + Spring Security + JWT + H2/MySQL + 微信小程序 SDK`
- 前端技术栈：`原生微信小程序`
- 默认后端上下文路径：`/api`
- 默认后端端口：`8037`

## 3. 本轮修复

### 3.1 默认运行环境修复

1. 在 `pom.xml` 中补充 H2 与 Spring Boot JDBC 相关依赖，保留 MySQL 驱动。
2. 将默认 `application.yml` 改为 H2 内存库自举，使用 Hikari 数据源，避免默认启动直接依赖本机 MySQL。
3. 新增 `application-mysql.yml`，保留 MySQL 部署入口。
4. 新增 `sql/schema-h2.sql` 与 `sql/data-h2.sql`，提供用户、课程、章节的最小演示数据。
5. 默认 SQL 初始化显式使用 `UTF-8`，确保中文种子数据可正确加载。

### 3.2 认证与公开接口修复

1. 修复 `server.servlet.context-path=/api` 下 Spring Security 匹配路径错误的问题，将公开路径从 `/api/auth/**` 等改为实际应用内路径 `/auth/**`、`/courses/**` 等。
2. 为微信登录增加 `mock_user1`、`mock_teacher`、`mock_admin` 以及 `demo_*` 演示 code，真实 appId/appSecret 未配置时也能跑通毕业设计演示链路。
3. `/auth/userinfo` 在缺少 `Authorization` 时返回业务响应 `code=401`，避免被安全层直接返回 403。
4. 新增 `CourseController` 与 `CourseService`，提供课程分页、热门课程与课程详情公开接口。
5. 修复课程按分类筛选时 `total` 仍统计全部已发布课程的问题。
6. 新增后端集成测试，覆盖模拟微信登录、当前用户、未登录业务 401、课程列表、分类分页、热门课程与课程详情。

### 3.3 微信小程序前端修复

1. 补齐 `app.json` 声明的 16 个页面目录与 `.js/.wxml/.wxss` 文件。
2. 移除缺失的 tabBar 图标配置，避免微信开发者工具因资源缺失报错。
3. 移除登录页缺失图片引用，改用文字 Logo 与样式承载。
4. 新增 `sitemap.json`，匹配 `app.json` 中的 `sitemapLocation`。
5. 为课程、问答、文章、代码、打卡、个人中心与发布类页面补充最小可导航占位界面。

## 4. 当前实现与目标能力对照

### 4.1 已具备能力

1. 后端默认 H2 自举，支持本机直接测试与启动。
2. 微信小程序模拟 code 登录，返回 JWT 与用户基础信息。
3. `/auth/userinfo` 可通过 JWT 获取当前用户信息。
4. 课程分页列表、分类筛选、热门课程与课程详情公开访问。
5. 小程序页面声明与实际文件一一对应，基础编译入口完整。

### 4.2 仍有差距

1. README/PRD 中提到的 `80+ API` 与当前实际后端实现差距明显，本轮可验证接口主要集中在认证和课程公开接口。
2. 问答、文章、代码、打卡等小程序页面当前为可编译/可导航占位，尚未接入完整后端业务接口。
3. Redis 配置仍保留，但本轮默认演示链路未覆盖真实缓存、排行榜或消息能力。
4. 真实微信登录仍需要正式 `appId` / `appSecret`，本轮通过 `mock_*` / `demo_*` code 保证本地演示可走通。
5. H2 演示数据仅覆盖认证、课程与章节基础场景，未等价替代完整生产数据。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`037-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 3, Failures: 0, Errors: 0`
- 覆盖内容：
  1. `mock_user1` 微信登录成功并返回 JWT
  2. 使用 JWT 调用 `/auth/userinfo` 成功返回 `user1_open_id`
  3. 无 Token 调用 `/auth/userinfo` 返回业务 `code=401`
  4. 课程分页列表返回 2 条数据，总数为 4
  5. 课程分类 `数据库` 返回 1 条数据，总数为 1
  6. 热门课程与课程详情接口返回种子数据

### 5.2 后端启动与接口抽测

- 执行命令：`037-backend/mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8037`
- 结果：`通过`
- 抽测地址：`http://127.0.0.1:8037/api`
- 抽测链路：
  1. `POST /auth/wxlogin`
  2. `GET /auth/userinfo`
  3. `GET /courses/list?pageNum=1&pageSize=2`
  4. `GET /courses/list?pageNum=1&pageSize=5&category=数据库`
  5. `GET /courses/hot?limit=2`
  6. `GET /courses/1`
- 关键业务结果：
  - `mock_user1` 登录返回 `code=200`、`userId=3` 且 token 非空
  - 带 token 查询当前用户返回 `openId=user1_open_id`
  - 无 token 查询当前用户返回业务 `code=401`
  - 课程列表返回 2 条数据，总数为 4
  - 分类 `数据库` 返回总数 1
  - 热门课程返回 2 条数据
  - 课程详情标题为 `Java零基础入门`

### 5.3 小程序静态核查

- 执行方式：检查 `app.json` 声明页面对应的 `.js/.wxml/.wxss` 文件，并扫描缺失图片/图标引用。
- 结果：`通过`
- 关键结果：
  1. `all declared pages exist`
  2. `rg "images/|iconPath|selectedIconPath|sitemap.json" 037-frontend` 仅剩 `sitemapLocation` 正常引用

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- MySQL 兼容配置：`037-backend/src/main/resources/application-mysql.yml`
- 演示微信 code：
  - `mock_user1` / `demo_user1`：普通用户
  - `mock_teacher` / `demo_teacher`：教师用户
  - `mock_admin` / `demo_admin`：管理员
- 说明：
  1. 直接执行 `mvn spring-boot:run` 即可使用默认 H2 环境启动后端。
  2. 后端接口默认带 `/api` 上下文路径，例如 `POST http://localhost:8037/api/auth/wxlogin`。
  3. 如需切换 MySQL，可先创建 `programming_learning` 数据库，再运行 `mvn spring-boot:run -Dspring-boot.run.profiles=mysql`。
  4. 微信开发者工具打开 `037-frontend` 时，当前页面文件已满足基础编译入口要求。

## 7. 备注

1. 本轮后端真实启动使用端口 `8037`。
2. `mvn` 输出中的 `rdc` profile 警告来自外部 Maven 环境，不影响本项目测试与启动结论。
3. 控制台启动 banner 中中文出现乱码，属于控制台编码显示问题；应用配置、SQL 与接口响应均按 UTF-8 输出。
