# 043 宠物寄养服务系统检查报告

## 1. 检查结论

- 项目编号：`043`
- 项目名称：`宠物寄养服务系统`
- 检查日期：`2026-04-26`
- 当前状态：`已完成`
- 综合结论：`本轮已修复后端编译失败、Boot 3 与 MyBatis-Plus starter 不兼容、默认环境强依赖 MySQL/Redis、H2 自举缺失、登录角色字段类型不一致、预约金额/状态字段断链、公开接口放行路径错误、JWT userId 未写入 request attribute、前端 API 地址写死 8080、前端构建类型错误以及预约状态展示不匹配等问题。当前后端可在默认 H2 环境下通过冒烟测试并真实启动，服务商列表、服务项目、登录、宠物新增、预约创建与预约列表链路可用；前端可构建，并可通过 Vite 代理完成登录与服务商列表接口联调。`

## 2. 项目形态

- 后端目录：`043-backend`
- 前端目录：`043-frontend`
- 后端技术栈：`Spring Boot 3.2 + MyBatis-Plus + Spring Security + JWT + H2/MySQL`
- 前端技术栈：`Vue 3 + Vite + TypeScript + Pinia + Element Plus + Axios`
- 默认后端端口：`8043`
- 默认前端端口：`3043`

## 3. 本轮修复

### 3.1 后端运行与数据自举修复

1. 将 MyBatis-Plus 依赖切换为 `mybatis-plus-spring-boot3-starter`，修复 Spring Boot 3.2 启动兼容问题。
2. 新增 H2 依赖，并将默认 `application.yml` 改为 H2 内存库自举，避免巡检和演示必须预先安装 MySQL/Redis。
3. 新增 `application-mysql.yml`，保留 MySQL/Redis 部署配置，真实数据库模式通过 `-Dspring-boot.run.profiles=mysql` 启动。
4. 新增 `schema-h2.sql` 与 `data-h2.sql`，提供用户、宠物、服务商和服务项目演示数据。
5. 重写 `sql/init.sql`，补齐 MySQL 模式下的完整表结构、默认账号和演示服务数据。
6. 将默认后端端口调整为 `8043`，并将启动提示中的接口文档地址同步为 `http://localhost:8043/doc.html`。
7. 将 MyBatis-Plus 分页方言改为通过 `app.mybatis.db-type` 配置控制，默认 H2，MySQL profile 中切换为 MySQL。

### 3.2 后端业务链路修复

1. 为 `User` 实体补充 `nickname` 字段，为 `Provider` 实体补充 `businessHours` 字段，修复登录返回与服务商展示字段断链。
2. 将登录返回 `role` 改为字符串类型，并将注册默认角色改为 `USER`，与数据库角色枚举保持一致。
3. 修复预约创建写入字段，将 `totalPrice` 对齐为实体中的 `totalAmount`，并写入 `days` 与字符串状态 `PENDING`。
4. 将预约查询、取消等状态参数从数字状态改为字符串状态，并修复取消预约的状态校验与状态写回。
5. 修复服务商公开列表仅展示 `APPROVED` 服务商，同时移除服务项目表中不存在的 `status` 过滤条件。
6. 修复 Spring Security 放行路径，覆盖 `/api/auth/**`、服务商公开接口、H2 控制台和 Swagger/Knife4j 文档。
7. 修复 JWT 过滤器只写 Security Context、不写 request attribute 的问题，使控制器可通过 `request.getAttribute("userId")` 获取当前用户。
8. 将 `PetDTO.weight` 改为 `BigDecimal`，避免宠物档案新增时类型转换不一致。

### 3.3 前端联调与构建修复

1. 将 Vite 开发端口调整为 `3043`，并将 `/api` 代理到 `http://localhost:8043`。
2. 将 Axios `baseURL` 改为 `/api`，避免前端写死 `http://localhost:8080/api`。
3. 修复 `ProviderDetail.vue` 日期范围类型保护，解决 `npm run build` 的 TypeScript 错误。
4. 将预约状态筛选和状态展示改为 `PENDING`、`CONFIRMED`、`IN_PROGRESS`、`COMPLETED`、`CANCELLED`。
5. 将预约列表总价展示字段从 `totalPrice` 改为 `totalAmount`，并按字符串状态控制取消按钮显示。
6. 修复首页活跃预约统计与个人资料角色展示，使其兼容当前后端返回的字符串角色和状态。

### 3.4 自动化验证补充

1. 新增后端冒烟测试，覆盖默认 H2 上下文启动、管理员登录、服务商公开列表、服务项目、用户注册、宠物新增、预约创建和预约列表。
2. 新增 `043-backend/README.md` 与 `043-backend/启动说明.txt`，补齐毕业设计演示启动步骤、默认账号和 MySQL profile 用法。

## 4. 当前实现与目标能力对照

### 4.1 已具备能力

1. 后端默认 H2 自举，可直接执行 `mvn spring-boot:run` 启动。
2. 管理员账号 `admin / admin123`、普通用户账号 `petuser / 123456`、服务商账号 `provider / 123456` 可用于演示。
3. 登录注册、宠物档案、公开服务商列表、服务项目查询、预约创建、预约取消和预约分页已有基础后端闭环。
4. 前端可通过 `npm run build` 构建，并可在 `3043` 端口通过 Vite 代理访问后端 `/api`。
5. H2 演示数据覆盖管理员、普通用户、服务商、宠物“豆包”、服务商“暖爪宠物寄养中心”和两项寄养服务，适合快速展示寄养预约主流程。

### 4.2 仍有差距

1. 真实支付、线下核销、寄养过程视频/图片反馈和评价闭环仍偏演示化。
2. 服务商入驻审核、服务商后台运营和管理员后台统计能力仍需进一步完善。
3. JWT 密钥仍写在配置文件中，真实部署应通过环境变量或密钥管理服务注入。
4. Redis 配置仅保留在 MySQL profile 中，默认 H2 演示环境未覆盖缓存、验证码或限流等能力。
5. 前端构建存在主包体积超过 500KB 的 Vite 告警。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`043-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0`
- 覆盖内容：
  1. 默认 H2 上下文可启动
  2. `POST /api/auth/login` 使用 `admin / admin123` 返回 `code=200`
  3. 管理员登录返回角色 `ADMIN` 和昵称 `系统管理员`
  4. `GET /api/provider/page` 返回已审核服务商
  5. `GET /api/provider/1/services` 返回服务项目
  6. `POST /api/auth/register` 可新增普通用户
  7. 新用户登录返回 token
  8. 携带 token 新增宠物返回 `code=200`
  9. 携带 token 创建寄养预约返回 `code=200`
  10. 携带 token 查询预约分页返回状态 `PENDING` 和金额 `totalAmount=204.0`

### 5.2 前端构建

- 执行命令：`043-frontend/npm run build`
- 结果：`通过`
- 关键结果：`vite build` 成功生成 `dist`
- 构建告警：
  1. `Some chunks are larger than 500 kB after minification`

### 5.3 启动与联调抽测

- 后端启动命令：`043-backend/mvn spring-boot:run`
- 前端启动命令：`043-frontend/npm run dev -- --host 127.0.0.1`
- 结果：`通过`
- 抽测地址：
  1. 后端服务商列表：`http://127.0.0.1:8043/api/provider/page`
  2. 后端服务项目：`http://127.0.0.1:8043/api/provider/1/services`
  3. 后端登录：`http://127.0.0.1:8043/api/auth/login`
  4. 前端登录页：`http://127.0.0.1:3043/login`
  5. 前端代理登录：`http://127.0.0.1:3043/api/auth/login`
  6. 前端代理服务商列表：`http://127.0.0.1:3043/api/provider/page`
- 关键业务结果：
  1. 后端服务商列表接口返回 `HTTP 200`，业务码 `200`，包含 1 条已审核演示服务商
  2. 后端服务项目接口返回 `HTTP 200`，业务码 `200`，包含 2 条寄养服务
  3. 后端登录接口返回 `HTTP 200`，业务码 `200`，且包含 token
  4. 登录返回角色为字符串 `USER`，并包含昵称 `寄养用户`
  5. 前端登录页返回 `HTTP 200`
  6. 经 Vite 代理调用登录与服务商列表接口均返回 `HTTP 200`，业务码 `200`

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- MySQL 兼容配置：`043-backend/src/main/resources/application-mysql.yml`
- MySQL 初始化脚本：`043-backend/sql/init.sql`
- 默认账号：
  - 管理员：`admin / admin123`
  - 普通用户：`petuser / 123456`
  - 服务商：`provider / 123456`
- 说明：
  1. 直接执行 `043-backend/mvn spring-boot:run` 即可使用默认 H2 环境启动后端。
  2. 后端接口默认不带额外 context-path，例如 `POST http://localhost:8043/api/auth/login`。
  3. 如需切换 MySQL，可先导入 `043-backend/sql/init.sql`，再运行 `mvn spring-boot:run -Dspring-boot.run.profiles=mysql`。
  4. 前端执行 `npm install` 后使用 `npm run dev` 启动，默认访问 `http://localhost:3043`。

## 7. 备注

1. 本轮后端真实启动使用端口 `8043`，前端真实启动使用端口 `3043`。
2. `mvn` 输出中的 `rdc` profile 警告来自外部 Maven 环境，不影响本项目测试与启动结论。
3. 前端 `dist/`、`node_modules/`、后端 `target/`、临时日志目录均不纳入提交。
