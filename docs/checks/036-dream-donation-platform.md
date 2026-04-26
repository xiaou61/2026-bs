# 036 小梦想全球公益捐赠平台检查报告

## 1. 检查结论

- 项目编号：`036`
- 项目名称：`小梦想全球公益捐赠平台`
- 检查日期：`2026-04-26`
- 当前状态：`已完成`
- 综合结论：`本轮已修复源码 HTML 实体转义导致的前后端无法正常编译、默认环境强依赖 PostgreSQL、默认密码哈希不可用、JWT 未真正参与当前用户识别、接口通过 userId 请求头默认冒充、登录/用户信息泄露密码、普通用户可越权创建项目、捐赠金额缺少下限校验、JPA 懒加载代理序列化失败、前端日期格式提交不兼容以及外部占位图/缺失 favicon 导致的浏览器资源错误。当前项目可在默认 H2 环境下通过后端测试、后端真实启动、核心接口抽测、前端构建与浏览器联调。`

## 2. 项目形态

- 后端目录：`036-backend`
- 前端目录：`036-frontend`
- 说明文档：`036-backend/启动说明.txt`、`036-backend/036-项目总结.txt`
- 后端技术栈：`Spring Boot 3.2 + Spring Data JPA + JWT + H2/PostgreSQL`
- 前端技术栈：`Vite + Vue 3 + Element Plus + Pinia + axios`

## 3. 本轮修复

### 3.1 默认运行环境修复

1. 对 `036-backend/src/main/java` 与 `036-frontend/src` 中被 HTML 实体转义的源码做限定范围解码，恢复 Java/Vue/HTML 语法。
2. 将默认 `application.yml` 改为 H2 内存库自举，新增 `schema-h2.sql` 与 `data-h2.sql`。
3. 新增 `application-postgresql.yml`，保留 PostgreSQL 运行入口。
4. 修正 `schema.sql` 中不兼容 PostgreSQL 的 `CREATE DATABASE IF NOT EXISTS`。
5. 统一默认账号哈希，确保 `admin / 123456`、`user1 / 123456`、`org1 / 123456` 可真实登录。
6. 为 SQL 初始化显式指定 `UTF-8`，修复中文种子数据乱码风险。

### 3.2 安全与权限修复

1. 将 JWT 密钥改为配置项 `app.jwt.secret`，支持通过 `JWT_SECRET` 覆盖。
2. `/api/user/info` 改为解析 `Authorization` 中的 JWT，不再硬编码返回 `1L` 管理员。
3. 项目、捐赠、进度等需要登录的接口改为使用 JWT 当前用户，不再接受默认 `userId=1` 请求头冒充。
4. 对 `User.password` 增加 JSON 忽略，登录、注册、当前用户、项目创建与捐赠响应均不再暴露密码字段。
5. `User.password` 同时排除 JSON 序列化与 Lombok `toString()` 输出，避免接口响应和 DEBUG 日志泄露密码哈希。
6. 项目创建限制为 `ORGANIZATION` 或 `ADMIN`，普通用户创建项目返回 `code=403`。
7. 项目状态更新、删除与进度发布/删除增加项目创建者或管理员校验。
8. 捐赠接口增加登录校验与金额下限校验，未登录返回 `code=401`，金额小于 `0.01` 返回 `code=400`。
9. 捐赠响应忽略 `Donation.user`，避免匿名捐赠泄露用户对象。

### 3.3 前端联调修复

1. 为 Vite dev 代理新增 `VITE_API_TARGET` 覆盖能力。
2. 创建项目时将日期格式化为 `yyyy-MM-dd HH:mm:ss`，与后端 DTO 保持一致。
3. 新增本地 `favicon.svg` 与 `cover-placeholder.svg`，替换缺失的 `/vite.svg` 和外部 `via.placeholder.com` 占位图。
4. 修复 Element Plus 控制台警告：`el-input` 的 `rows` 改为数字绑定，`el-radio` 改用 `value`。

## 4. 当前实现与目标能力对照

### 4.1 已具备能力

1. 用户注册、登录、当前用户查询
2. 公益项目列表、分类、详情、最新项目
3. 组织/管理员创建公益项目
4. 项目状态更新与删除的基础权限校验
5. 登录用户捐赠、匿名捐赠、个人捐赠记录查询
6. 项目进度发布与查询
7. 默认 H2 演示环境与前端代理联调

### 4.2 仍有差距

1. 支付流程仍为模拟成功，未接真实支付网关或回调验签。
2. 前端未提供管理员审核/状态流转页面，项目状态更新接口主要停留在后端能力。
3. 项目进度管理前端入口不足，当前页面更偏用户端浏览与捐赠。
4. 登录态仍保存在 `localStorage`，仅适合毕业设计演示环境。
5. 前端打包主包约 `1.02 MB`，仍有大包告警。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`036-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 4, Failures: 0, Errors: 0`
- 覆盖内容：
  1. 默认账号登录与密码脱敏
  2. `/api/user/info` 返回 JWT 对应用户
  3. 普通用户创建项目被拒绝
  4. 组织账号创建项目成功
  5. 未登录捐赠被拒绝
  6. 捐赠金额校验
  7. 登录用户捐赠成功且响应不暴露用户密码/匿名用户对象

### 5.2 后端启动与接口抽测

- 执行命令：`036-backend/mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8036`
- 结果：`通过`
- 抽测链路：
  1. `GET /api/projects/latest?limit=2`
  2. `POST /api/user/login`
  3. `GET /api/user/info`
  4. `POST /api/donations`
  5. `POST /api/projects`
- 关键业务结果：
  - `user1 / 123456` 登录成功
  - `/api/user/info` 返回 `user1 / 张三`
  - 未登录捐赠返回 `code=401`
  - 登录用户捐赠 `66.66` 成功
  - 普通用户创建项目返回 `code=403`
  - 组织账号创建项目成功

### 5.3 前端验证

- 执行命令：
  - `036-frontend/npm install`
  - `036-frontend/npm run build`
  - `VITE_API_TARGET=http://127.0.0.1:8036 npm run dev -- --host 127.0.0.1 --port 3036`
- 结果：`通过`
- 关键结果：
  1. `npm run build` 成功完成，仅保留大包告警
  2. 浏览器访问首页可加载 6 个公益项目
  3. 使用 `user1 / 123456` 可登录并显示用户菜单
  4. 项目详情页捐赠成功后金额从 `¥35000` 更新为 `¥35018`，人数从 `120` 更新为 `121`
  5. 个人中心可看到浏览器联调创建的捐赠记录
  6. 浏览器控制台无错误和 Element Plus 警告

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- PostgreSQL 兼容配置：`036-backend/src/main/resources/application-postgresql.yml`
- 默认账号：
  - `admin / 123456`
  - `user1 / 123456`
  - `org1 / 123456`
- 说明：
  1. 直接执行 `mvn spring-boot:run` 即可使用默认 H2 环境启动后端。
  2. 如需切换 PostgreSQL，可先创建 `dream_donation` 数据库，再运行 `mvn spring-boot:run -Dspring-boot.run.profiles=postgresql`。
  3. 前端 dev 默认代理到 `http://localhost:8036`；如需临时切换后端地址，可通过 `VITE_API_TARGET` 覆盖。

## 7. 备注

1. 本轮浏览器验证使用后端 `8036`、前端 `3036`。
2. `mvn` 输出中的 `rdc` profile 警告来自外部 Maven 环境，不影响本项目测试与启动结论。
3. Playwright CLI 首选浏览器包安装超时，因此浏览器验证改用本机 `msedge` 通道完成。
