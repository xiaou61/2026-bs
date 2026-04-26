# 041 在线心理评测与咨询系统检查报告

## 1. 检查结论

- 项目编号：`041`
- 项目名称：`在线心理评测与咨询系统`
- 检查日期：`2026-04-26`
- 当前状态：`已完成`
- 综合结论：`本轮已修复 Spring Boot 3 与旧 MyBatis-Plus starter 不兼容、默认环境强依赖 MySQL/Redis、H2 自举缺失、初始化账号密码哈希不可用、登录返回密码哈希泄露、MyBatis-Plus 自动时间填充失效以及前端依赖锁缺失等问题。当前后端可在默认 H2 环境下通过冒烟测试并真实启动，登录、量表、题目、咨询师、文章、测评提交和预约创建接口抽测通过；前端可构建并通过 Vite 代理完成登录接口联调。`

## 2. 项目形态

- 后端目录：`041-backend`
- 前端目录：`041-frontend`
- 后端技术栈：`Spring Boot 3.2 + MyBatis-Plus + JWT + WebSocket + H2/MySQL + Redis`
- 前端技术栈：`Vue 3 + Vite + Pinia + Element Plus`
- 默认后端端口：`8041`
- 默认前端端口：`3041`

## 3. 本轮修复

### 3.1 默认运行环境修复

1. 将 `mybatis-plus-boot-starter` 替换为 `mybatis-plus-spring-boot3-starter`，修复 Spring Boot 3 启动时报 `factoryBeanObjectType` 类型错误的问题。
2. 新增 H2 依赖，并将默认 `application.yml` 改为 H2 内存库自举，避免巡检和演示必须预先安装 MySQL/Redis。
3. 新增 `application-mysql.yml`，保留 MySQL 与 Redis 部署配置，真实数据库模式通过 `-Dspring-boot.run.profiles=mysql` 启动。
4. 新增 `schema-h2.sql` 与 `data-h2.sql`，提供用户、咨询师、量表、题目、心理文章和可预约时段演示数据。
5. 为 H2 初始化显式设置 `UTF-8` 编码，并在 JDBC URL 中处理 `user` 表名关键字兼容。
6. 修复原 `init.sql` 中默认账号 bcrypt 哈希不可用的问题，统一默认演示密码为 `123456`。

### 3.2 MyBatis-Plus 与安全修复

1. 将分页方言改为通过 `app.mybatis.db-type` 配置控制，默认 H2，MySQL profile 中切换为 MySQL。
2. 新增 `MybatisPlusMetaObjectHandler`，修复 `@TableField(fill = ...)` 无处理器导致新增测评/预约记录时间字段为 `null` 的问题。
3. 登录和注册返回用户信息前清空 `password`，避免把 bcrypt 哈希返回给前端和写入 `localStorage`。
4. 新增后端冒烟测试，覆盖默认 H2 启动、管理员登录、密码脱敏、量表列表、题目列表、咨询师列表、文章列表、测评提交和预约创建。

### 3.3 文档与联调修复

1. 新增 `041-backend/README.md`，说明默认 H2、默认账号、测试命令和 MySQL profile。
2. 新增 `041-backend/启动说明.txt`，便于毕业设计演示时按步骤启动。
3. 生成 `041-frontend/package-lock.json`，保证前端依赖安装更可复现。
4. 确认前端开发端口 `3041`，并通过 Vite 代理将 `/api` 转发到 `http://localhost:8041`。

## 4. 当前实现与目标能力对照

### 4.1 已具备能力

1. 后端默认 H2 自举，可直接执行 `mvn spring-boot:run` 启动。
2. 管理员账号 `admin / 123456`、咨询师账号 `counselor1 / 123456`、普通用户账号 `user1 / 123456` 可用于演示。
3. 登录注册、心理量表、题目列表、咨询师列表、咨询师时段、心理文章、测评提交和咨询预约已有基础闭环。
4. 前端可通过 `npm run build` 构建，并可在 `3041` 端口通过 Vite 代理访问后端 `/api`。
5. H2 演示数据覆盖管理员、咨询师、普通用户、量表、题目、文章和预约时段场景。

### 4.2 仍有差距

1. PRD 中 WebSocket 实时咨询、聊天消息持久化和在线状态管理仍未形成完整前后端闭环。
2. Redis 缓存策略在 PRD 中有描述，但当前默认演示环境未依赖 Redis，业务中也缺少完整缓存读写逻辑。
3. 后台用户管理、咨询师审核、量表管理、文章管理、预约订单管理和数据统计仍偏简化。
4. 心理档案成长曲线、收藏、评价、通知、找回密码等扩展能力尚未完整落地。
5. 当前接口鉴权可满足演示，但业务接口仍通过 `userId` 请求头识别当前用户，尚不是生产级 JWT 鉴权上下文。
6. 前端构建存在主包体积超过 500KB 的 Vite 告警。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`041-backend/mvn clean test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0`
- 覆盖内容：
  1. 默认 H2 上下文可启动
  2. `POST /api/auth/login` 使用 `admin / 123456` 返回 `code=200`
  3. 登录返回包含 token，且 `user.password` 为 `null`
  4. `GET /api/assessments/scales` 返回 `焦虑自评量表(SAS)`
  5. `GET /api/assessments/scales/1/questions` 返回 `code=200`
  6. `GET /api/counselors` 返回 `code=200`
  7. `GET /api/articles` 返回 `code=200`
  8. 携带 `userId=3` 提交测评返回 `code=200` 且 `createdAt` 非空
  9. 携带 `userId=3` 创建预约返回 `code=200` 且 `createdAt` 非空

### 5.2 前端构建

- 执行命令：`041-frontend/npm run build`
- 结果：`通过`
- 关键结果：`vite build` 成功生成 `dist`
- 构建告警：
  1. `Some chunks are larger than 500 kB after minification`

### 5.3 启动与联调抽测

- 后端启动命令：`041-backend/mvn spring-boot:run`
- 前端启动命令：`041-frontend/npm run dev -- --host 127.0.0.1`
- 结果：`通过`
- 抽测地址：
  1. 后端量表：`http://127.0.0.1:8041/api/assessments/scales`
  2. 后端登录：`http://127.0.0.1:8041/api/auth/login`
  3. 前端页面：`http://127.0.0.1:3041/login`
  4. 前端代理：`http://127.0.0.1:3041/api/auth/login`
- 关键业务结果：
  1. 后端量表接口返回 `HTTP 200`，业务码 `200`
  2. 后端登录接口返回 `HTTP 200`，业务码 `200`，且包含 token
  3. 登录返回用户信息中密码字段为 `null`
  4. 前端登录页返回 `HTTP 200`
  5. 经 Vite 代理调用登录接口返回 `HTTP 200`，业务码 `200`，且包含 token

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- MySQL 兼容配置：`041-backend/src/main/resources/application-mysql.yml`
- 默认账号：
  - 管理员：`admin / 123456`
  - 咨询师：`counselor1 / 123456`
  - 普通用户：`user1 / 123456`
- 说明：
  1. 直接执行 `041-backend/mvn spring-boot:run` 即可使用默认 H2 环境启动后端。
  2. 后端接口默认不带额外 context-path，例如 `POST http://localhost:8041/api/auth/login`。
  3. 如需切换 MySQL，可先导入 `041-backend/src/main/resources/init.sql`，再运行 `mvn spring-boot:run -Dspring-boot.run.profiles=mysql`。
  4. 前端执行 `npm install` 后使用 `npm run dev` 启动，默认访问 `http://localhost:3041`。

## 7. 备注

1. 本轮后端真实启动使用端口 `8041`，前端真实启动使用端口 `3041`。
2. `mvn` 输出中的 `rdc` profile 警告来自外部 Maven 环境，不影响本项目测试与启动结论。
3. 前端 `dist/`、`node_modules/`、后端 `target/`、临时日志目录均不纳入提交。
