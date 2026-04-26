# 044 特色民宿预订平台检查报告

## 1. 检查结论

- 项目编号：`044`
- 项目名称：`特色民宿预订平台`
- 检查日期：`2026-04-26`
- 当前状态：`已完成`
- 综合结论：`本轮已修复默认环境强依赖 MySQL/Redis、后端端口未项目化、H2 自举缺失、MyBatis-Plus 分页方言固定 MySQL、JWT 认证 principal 与控制器 UserDetails 不匹配、前端代理仍指向 8080、前端 vue-tsc 与 TypeScript 版本不兼容以及页面无用变量导致构建失败等问题。当前后端可在默认 H2 环境下通过冒烟测试并真实启动，民宿列表/详情、用户登录、收藏、创建预订、我的订单和房东确认订单链路可用；前端可构建，并可通过 Vite 代理完成登录与民宿列表接口联调。`

## 2. 项目形态

- 后端目录：`044-backend`
- 前端目录：`044-frontend`
- 后端技术栈：`Spring Boot 3.2 + MyBatis-Plus + Spring Security + JWT + H2/MySQL`
- 前端技术栈：`Vue 3 + Vite + TypeScript + Pinia + Element Plus + Axios`
- 默认后端端口：`8044`
- 默认前端端口：`3044`

## 3. 本轮修复

### 3.1 默认运行环境修复

1. 新增 H2 依赖，并将默认 `application.yml` 改为 H2 内存库自举，避免巡检和演示必须预先安装 MySQL/Redis。
2. 新增 `application-mysql.yml`，保留 MySQL/Redis 部署配置，真实数据库模式通过 `-Dspring-boot.run.profiles=mysql` 启动。
3. 新增 `schema-h2.sql` 与 `data-h2.sql`，提供用户、民宿、房型、设施、收藏和预订等核心表结构与演示数据。
4. 将默认后端端口调整为 `8044`，前端开发端口调整为 `3044`，并将 Vite 代理目标改为 `http://localhost:8044`。
5. 将 MyBatis-Plus 分页方言改为通过 `app.mybatis.db-type` 配置控制，默认 H2，MySQL profile 中切换为 MySQL。
6. 放行 H2 控制台并设置 frame same-origin，方便默认演示环境查看内存库数据。

### 3.2 后端认证与业务链路修复

1. 修复 JWT 过滤器中认证 principal 只存 `Long userId` 的问题，改为兼容 `@AuthenticationPrincipal UserDetails` 的 principal，避免收藏、预订和房东接口运行时取不到用户。
2. 保留 `request` attribute 中的 `userId`、`username`、`role`，便于后续控制器扩展使用。
3. 新增后端测试依赖，补齐可执行的 Spring Boot 冒烟测试。
4. 新增冒烟测试覆盖管理员登录、公开民宿列表、民宿详情、游客登录、收藏、创建预订、我的订单、房东民宿列表和房东确认订单。

### 3.3 前端构建与联调修复

1. 生成 `044-frontend/package-lock.json`，保证前端依赖安装更可复现。
2. 将 `vue-tsc` 升级到兼容当前 TypeScript 的版本，并将 TypeScript 锁定到 `~5.9.3`，修复类型检查阶段直接崩溃的问题。
3. 修复 `HomestayEdit.vue` 中未使用的 `roomFormRef`，解决 `noUnusedLocals` 构建错误。
4. 将 Vite 开发端口调整为 `3044`，并将 `/api` 代理到 `http://localhost:8044`。

## 4. 当前实现与目标能力对照

### 4.1 已具备能力

1. 后端默认 H2 自举，可直接执行 `mvn spring-boot:run` 启动。
2. 管理员账号 `admin / admin123`、房东账号 `host1 / 123456`、游客账号 `guest1 / 123456` 可用于演示。
3. 民宿公开列表、民宿详情、房型展示、设施展示、收藏、预订创建、订单查询、房东民宿管理和房东确认订单已有基础后端闭环。
4. 前端可通过 `npm run build` 构建，并可在 `3044` 端口通过 Vite 代理访问后端 `/api`。
5. H2 演示数据覆盖 2 条上架民宿、3 个房型、7 个设施和民宿设施关联，适合快速展示游客预订与房东确认主流程。

### 4.2 仍有差距

1. 订单支付仍为简化流程，当前创建订单后直接进入待确认状态，未接真实支付渠道。
2. 民宿审核、管理员后台、财务结算、退款和入住核销等能力仍需进一步完善。
3. 房态库存未按日期维度扣减，当前更适合毕业设计演示，不适合作为真实库存系统。
4. 图片上传仍以 URL/字符串字段为主，缺少完整文件存储、图片校验和 CDN 策略。
5. 前端构建存在主包体积超过 500KB 的 Vite 告警，并有 Sass legacy JS API 废弃告警。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`044-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0`
- 覆盖内容：
  1. 默认 H2 上下文可启动
  2. `POST /api/auth/login` 使用 `admin / admin123` 返回 `code=200`
  3. `GET /api/homestay/list` 返回 2 条上架演示民宿
  4. `GET /api/homestay/detail/1` 返回民宿、房型、设施和评价分页结构
  5. `POST /api/auth/login` 使用 `guest1 / 123456` 返回 token
  6. 携带游客 token 收藏民宿返回 `code=200`
  7. 携带游客 token 检查收藏状态返回 `true`
  8. 携带游客 token 创建预订返回 `code=200`
  9. 携带游客 token 查询订单详情返回状态 `1`、总价 `776.0`
  10. 携带房东 token 查询房东民宿列表并确认订单返回 `code=200`

### 5.2 前端构建

- 执行命令：`044-frontend/npm run build`
- 结果：`通过`
- 关键结果：`vue-tsc` 与 `vite build` 均成功，生成 `dist`
- 构建告警：
  1. `Some chunks are larger than 500 kB after minification`
  2. `DEPRECATION WARNING [legacy-js-api]` 来自 Sass 旧 JS API

### 5.3 启动与联调抽测

- 后端启动命令：`044-backend/mvn spring-boot:run`
- 前端启动命令：`044-frontend/npm run dev -- --host 127.0.0.1`
- 结果：`通过`
- 抽测地址：
  1. 后端民宿列表：`http://127.0.0.1:8044/api/homestay/list`
  2. 后端民宿详情：`http://127.0.0.1:8044/api/homestay/detail/1`
  3. 后端登录：`http://127.0.0.1:8044/api/auth/login`
  4. 后端收藏与创建预订：`http://127.0.0.1:8044/api/favorite/add/1`、`http://127.0.0.1:8044/api/booking/create`
  5. 前端登录页：`http://127.0.0.1:3044/login`
  6. 前端代理登录：`http://127.0.0.1:3044/api/auth/login`
  7. 前端代理民宿列表：`http://127.0.0.1:3044/api/homestay/list`
- 关键业务结果：
  1. 后端民宿列表接口返回 `HTTP 200`，业务码 `200`，包含 2 条演示民宿
  2. 后端民宿详情接口返回 `HTTP 200`，业务码 `200`，包含 2 个房型和设施数据
  3. 游客登录接口返回 `HTTP 200`，业务码 `200`，角色为 `0`，且包含 token
  4. 收藏、创建预订和我的订单接口均返回 `HTTP 200`，业务码 `200`
  5. 真实预订抽测订单状态为 `1`，总价为 `776.0`
  6. 前端登录页返回 `HTTP 200`
  7. 经 Vite 代理调用登录与民宿列表接口均返回 `HTTP 200`，业务码 `200`

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- MySQL 兼容配置：`044-backend/src/main/resources/application-mysql.yml`
- MySQL 初始化脚本：`044-backend/sql/init.sql`
- 默认账号：
  - 管理员：`admin / admin123`
  - 房东：`host1 / 123456`
  - 游客：`guest1 / 123456`
- 说明：
  1. 直接执行 `044-backend/mvn spring-boot:run` 即可使用默认 H2 环境启动后端。
  2. 后端接口默认不带额外 context-path，例如 `POST http://localhost:8044/api/auth/login`。
  3. 如需切换 MySQL，可先导入 `044-backend/sql/init.sql`，再运行 `mvn spring-boot:run -Dspring-boot.run.profiles=mysql`。
  4. 前端执行 `npm install` 后使用 `npm run dev` 启动，默认访问 `http://localhost:3044`。

## 7. 备注

1. 本轮后端真实启动使用端口 `8044`，前端真实启动使用端口 `3044`。
2. `mvn` 输出中的 `rdc` profile 警告来自外部 Maven 环境，不影响本项目测试与启动结论。
3. 前端 `dist/`、`node_modules/`、后端 `target/`、临时日志目录均不纳入提交。
