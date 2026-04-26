# 045 养老院管理系统检查报告

## 1. 检查结论

- 项目编号：`045`
- 项目名称：`养老院管理系统`
- 检查日期：`2026-04-26`
- 当前状态：`已完成`
- 综合结论：`本轮已修复默认环境强依赖 MySQL/Redis、默认端口未项目化、H2 自举缺失、MyBatis-Plus 分页方言固定 MySQL、H2 控制台未放行、前端代理仍指向旧端口以及后端缺少可执行集成测试等问题。当前后端可在默认 H2 环境下通过冒烟测试并真实启动，管理员统计、老人列表、护工健康记录、家属账单缴费、探访申请审批和公告列表链路可用；前端可构建，并可通过 Vite 代理完成登录与公告接口联调。`

## 2. 项目形态

- 后端目录：`045-backend`
- 前端目录：`045-frontend`
- 后端技术栈：`Spring Boot 3.2.1 + MyBatis-Plus 3.5.5 + Spring Security + JWT + H2/MySQL`
- 前端技术栈：`Vue 3 + Vite + TypeScript + Pinia + Element Plus + Axios`
- 默认后端端口：`8045`
- 默认前端端口：`3045`

## 3. 本轮修复

### 3.1 默认运行环境修复

1. 新增 H2 依赖，并将默认 `application.yml` 改为 H2 内存库自举，避免巡检和演示必须预先安装 MySQL/Redis。
2. 新增 `application-mysql.yml`，保留 MySQL/Redis 部署配置，真实数据库模式通过 `-Dspring-boot.run.profiles=mysql` 启动。
3. 新增 `schema-h2.sql` 与 `data-h2.sql`，提供用户、楼栋、房间、床位、老人、健康记录、护理计划、费用项目、账单和公告等核心表结构与演示数据。
4. 将默认后端端口调整为 `8045`，前端开发端口调整为 `3045`，并将 Vite 代理目标改为 `http://localhost:8045`。
5. 将 MyBatis-Plus 分页方言改为通过 `app.mybatis.db-type` 配置控制，默认 H2，MySQL profile 中切换为 MySQL。
6. 放行 H2 控制台并设置 frame same-origin，方便默认演示环境查看内存库数据。

### 3.2 后端认证与测试修复

1. JWT 过滤器继续保持 `Long userId` 作为认证 principal，兼容当前控制器中的 `@AuthenticationPrincipal Long userId` 用法。
2. 在 JWT 过滤器中补充 `request` attribute：`userId` 和 `role`，方便后续接口扩展时读取当前登录上下文。
3. 新增 Spring Boot 测试依赖，补齐可执行的后端冒烟测试。
4. 新增冒烟测试覆盖管理员登录、公告列表、管理员统计、老人列表、护工登录、健康记录新增/查询、家属登录、账单查询/缴费、探访申请和探访审批。

### 3.3 前端联调修复

1. 将 Vite 开发端口调整为 `3045`，避免与其他项目端口冲突。
2. 将 `/api` 代理到 `http://localhost:8045`，使前端默认开发环境可以直接联调当前后端。

### 3.4 文档修复

1. 更新 `045-backend/README.md`，补齐默认 H2 启动、H2 控制台、MySQL profile、前端端口和默认账号说明。
2. 新增 `045-backend/启动说明.txt`，便于答辩或本地演示时快速启动。
3. 新增本巡检报告，并同步更新总台账。

## 4. 当前实现与目标能力对照

### 4.1 已具备能力

1. 后端默认 H2 自举，可直接执行 `mvn spring-boot:run` 启动。
2. 管理员账号 `admin / admin123`、护工账号 `nurse1 / admin123`、家属账号 `family1 / admin123` 可用于演示。
3. 演示数据覆盖楼栋、房间、床位、老人、健康记录、护理计划、费用账单和公告。
4. 管理员可查看统计数据和老人列表。
5. 护工可新增并查看老人健康记录。
6. 家属可查询账单、缴费、提交探访申请。
7. 管理员可审批家属探访申请。
8. 前端可通过 `npm run build` 构建，并可在 `3045` 端口通过 Vite 代理访问后端 `/api`。

### 4.2 仍有差距

1. 缴费仍为简化记账流程，未接真实支付渠道。
2. 护理计划和护理记录已有基础后端结构，但完整排班、提醒和移动端执行闭环仍需进一步完善。
3. 老人退住、床位释放、费用结算等复杂业务规则仍偏演示化。
4. 文件上传、头像和老人照片等资源管理能力仍以字段预留为主，缺少完整存储策略。
5. 前端构建存在主包体积超过 500KB 的 Vite 告警。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`045-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0`
- 覆盖内容：
  1. 默认 H2 上下文可启动
  2. `POST /api/auth/login` 使用 `admin / admin123` 返回 `code=200`，角色为 `2`
  3. `GET /api/notice/list` 返回公告分页数据
  4. `GET /api/admin/statistics` 返回老人数量和床位统计
  5. `GET /api/elder/list` 返回老人分页数据
  6. `POST /api/auth/login` 使用 `nurse1 / admin123` 返回 token
  7. 护工携带 token 新增健康记录并查询老人健康记录成功
  8. `POST /api/auth/login` 使用 `family1 / admin123` 返回 token 和绑定老人 ID
  9. 家属携带 token 查询账单、缴费并查看账单状态更新成功
  10. 家属提交探访申请，管理员审批探访申请成功

### 5.2 前端构建

- 执行命令：`045-frontend/npm run build`
- 结果：`通过`
- 关键结果：`vue-tsc -b && vite build` 成功，生成 `dist`
- 构建告警：
  1. `Some chunks are larger than 500 kB after minification`

### 5.3 启动与联调抽测

- 后端启动命令：`045-backend/mvn spring-boot:run`
- 前端启动命令：`045-frontend/npm run dev -- --host 127.0.0.1`
- 结果：`通过`
- 抽测地址：
  1. 后端公告列表：`http://127.0.0.1:8045/api/notice/list`
  2. 后端登录：`http://127.0.0.1:8045/api/auth/login`
  3. 后端管理员统计：`http://127.0.0.1:8045/api/admin/statistics`
  4. 后端老人列表：`http://127.0.0.1:8045/api/elder/list`
  5. 后端健康记录：`http://127.0.0.1:8045/api/health`
  6. 后端账单缴费：`http://127.0.0.1:8045/api/bill/pay`
  7. 后端探访申请与审批：`http://127.0.0.1:8045/api/visit/apply`、`http://127.0.0.1:8045/api/visit/approve/{id}`
  8. 前端登录页：`http://127.0.0.1:3045/login`
  9. 前端代理登录：`http://127.0.0.1:3045/api/auth/login`
  10. 前端代理公告列表：`http://127.0.0.1:3045/api/notice/list`
- 关键业务结果：
  1. 后端公告列表接口返回 `HTTP 200`，业务码 `200`，包含 2 条演示公告
  2. 管理员登录返回 `HTTP 200`，业务码 `200`，角色为 `2`
  3. 管理员统计返回老人数量 `2`、床位总数 `4`
  4. 老人列表返回 2 条演示老人
  5. 护工新增健康记录返回业务码 `200`，健康记录分页可查询到新增数据
  6. 家属账单列表返回 1 条账单，缴费后账单状态为 `1`
  7. 家属探访申请返回业务码 `200`，管理员审批后探访状态为 `1`
  8. 前端登录页返回 `HTTP 200`
  9. 经 Vite 代理调用登录与公告列表接口均返回 `HTTP 200`，业务码 `200`

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- MySQL 兼容配置：`045-backend/src/main/resources/application-mysql.yml`
- MySQL 初始化脚本：`045-backend/sql/init.sql`
- 默认账号：
  - 管理员：`admin / admin123`
  - 护工：`nurse1 / admin123`
  - 家属：`family1 / admin123`
- 说明：
  1. 直接执行 `045-backend/mvn spring-boot:run` 即可使用默认 H2 环境启动后端。
  2. 后端接口默认不带额外 context-path，例如 `POST http://localhost:8045/api/auth/login`。
  3. H2 控制台地址为 `http://localhost:8045/h2-console`，JDBC URL 为 `jdbc:h2:mem:nursing_home`。
  4. 如需切换 MySQL，可先导入 `045-backend/sql/init.sql`，再运行 `mvn spring-boot:run -Dspring-boot.run.profiles=mysql`。
  5. 前端执行 `npm install` 后使用 `npm run dev` 启动，默认访问 `http://localhost:3045`。

## 7. 备注

1. 本轮后端真实启动使用端口 `8045`，前端真实启动使用端口 `3045`。
2. `mvn` 输出中的 `rdc` profile 警告来自外部 Maven 环境，不影响本项目测试与启动结论。
3. 前端 `dist/`、`node_modules/`、后端 `target/`、临时日志目录均不纳入提交。
4. PowerShell 抽测探访审批时曾触发一次命令字符串插值问题，根因是 `$visitId?status` 被解析为变量名；改用 `${visitId}` 后真实后端审批链路验证通过。
