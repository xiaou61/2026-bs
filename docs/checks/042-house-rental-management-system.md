# 042 房屋租赁管理系统检查报告

## 1. 检查结论

- 项目编号：`042`
- 项目名称：`房屋租赁管理系统`
- 检查日期：`2026-04-26`
- 当前状态：`已完成`
- 综合结论：`本轮已修复默认环境强依赖 MySQL、后端端口未项目化、前端代理仍指向 8080、H2 自举缺失、初始化账号密码哈希不可用以及后端缺少自动化冒烟测试等问题。当前后端可在默认 H2 环境下通过冒烟测试并真实启动，登录、注册、发布房源、公开房源列表/详情、收藏和预约看房链路抽测通过；前端可构建，并可通过 Vite 代理完成登录接口联调。`

## 2. 项目形态

- 后端目录：`042-backend`
- 前端目录：`042-frontend`
- 后端技术栈：`Spring Boot 3.2 + MyBatis-Plus + JWT + H2/MySQL`
- 前端技术栈：`Vue 3 + Vite + Pinia + Element Plus + Axios`
- 默认后端端口：`8042`
- 默认前端端口：`3042`

## 3. 本轮修复

### 3.1 默认运行环境修复

1. 新增 H2 依赖，并将默认 `application.yml` 改为 H2 内存库自举，避免巡检和演示必须预先安装 MySQL。
2. 新增 `application-mysql.yml`，保留 MySQL 部署配置，真实数据库模式通过 `-Dspring-boot.run.profiles=mysql` 启动。
3. 新增 `schema-h2.sql` 与 `data-h2.sql`，提供用户、房源、预约、合同、账单、报修、评价和消息表结构及演示数据。
4. 将默认后端端口调整为 `8042`，前端开发端口调整为 `3042`，并将 Vite 代理目标改为 `http://localhost:8042`。
5. 将 MyBatis-Plus 分页方言改为通过 `app.mybatis.db-type` 配置控制，默认 H2，MySQL profile 中切换为 MySQL。
6. 修复原 `sql/init.sql` 中默认账号 bcrypt 哈希不可用的问题，确保 MySQL 模式下默认账号也可登录。

### 3.2 自动化验证补充

1. 新增后端冒烟测试，覆盖默认 H2 上下文启动、管理员登录、租客登录、用户注册、房东发布房源、公开房源列表、房源详情、收藏房源和预约看房。
2. 生成 `042-frontend/package-lock.json`，保证前端依赖安装更可复现。
3. 新增 `042-backend/README.md` 与 `042-backend/启动说明.txt`，补齐毕业设计演示启动步骤、默认账号和 MySQL profile 用法。

## 4. 当前实现与目标能力对照

### 4.1 已具备能力

1. 后端默认 H2 自举，可直接执行 `mvn spring-boot:run` 启动。
2. 管理员账号 `admin / admin123`、房东账号 `landlord / 123456`、租客账号 `tenant / 123456` 可用于演示。
3. 登录注册、用户资料、房源发布/审核/浏览、收藏、预约看房、合同签署、租金账单、报修、评价和消息通知已有基础后端闭环。
4. 前端可通过 `npm run build` 构建，并可在 `3042` 端口通过 Vite 代理访问后端 `/api`。
5. H2 演示数据覆盖管理员、房东、租客和 3 条已上架房源，适合快速展示租客浏览、收藏和预约流程。

### 4.2 仍有差距

1. 文件上传接口当前在拦截器中放行，适合演示但不够生产级，后续应补登录鉴权、文件类型校验和大小限制策略。
2. JWT 密钥仍写在配置文件中，真实部署应通过环境变量或密钥管理服务注入。
3. 租金支付为余额扣减模拟支付，未接真实支付渠道。
4. 合同签署、报修处理和消息通知已有基础闭环，但缺少电子签章、短信/邮件/站内实时提醒等增强能力。
5. 前端构建存在主包体积超过 500KB 的 Vite 告警。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`042-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0`
- 覆盖内容：
  1. 默认 H2 上下文可启动
  2. `POST /api/auth/login` 使用 `admin / admin123` 返回 `code=200`
  3. 管理员登录返回包含 token，且用户信息中不包含密码字段
  4. `POST /api/auth/login` 使用 `tenant / 123456` 返回 `code=200`
  5. `POST /api/auth/register` 可新增租客账号，验证 H2 用户自增主键不与演示数据冲突
  6. 携带房东 token 发布房源返回 `code=200`，验证 H2 房源自增主键不与演示数据冲突
  7. `GET /api/house/list` 返回 3 条已上架演示房源
  8. `GET /api/house/detail/1` 返回房东信息
  9. 携带租客 token 收藏房源返回 `code=200`
  10. 携带租客 token 创建预约看房返回 `code=200`
  11. 携带租客 token 查询预约列表返回已创建预约

### 5.2 前端构建

- 执行命令：`042-frontend/npm run build`
- 结果：`通过`
- 关键结果：`vite build` 成功生成 `dist`
- 构建告警：
  1. `Some chunks are larger than 500 kB after minification`

### 5.3 启动与联调抽测

- 后端启动命令：`042-backend/mvn spring-boot:run`
- 前端启动命令：`042-frontend/npm run dev -- --host 127.0.0.1`
- 结果：`通过`
- 抽测地址：
  1. 后端房源列表：`http://127.0.0.1:8042/api/house/list`
  2. 后端登录：`http://127.0.0.1:8042/api/auth/login`
  3. 前端登录页：`http://127.0.0.1:3042/login`
  4. 前端代理登录：`http://127.0.0.1:3042/api/auth/login`
- 关键业务结果：
  1. 后端房源列表接口返回 `HTTP 200`，业务码 `200`，包含 3 条演示房源
  2. 后端登录接口返回 `HTTP 200`，业务码 `200`，且包含 token
  3. 登录返回用户信息不包含密码字段
  4. 前端登录页返回 `HTTP 200`
  5. 经 Vite 代理调用登录接口返回 `HTTP 200`，业务码 `200`，且包含 token

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- MySQL 兼容配置：`042-backend/src/main/resources/application-mysql.yml`
- 默认账号：
  - 管理员：`admin / admin123`
  - 房东：`landlord / 123456`
  - 租客：`tenant / 123456`
- 说明：
  1. 直接执行 `042-backend/mvn spring-boot:run` 即可使用默认 H2 环境启动后端。
  2. 后端接口默认不带额外 context-path，例如 `POST http://localhost:8042/api/auth/login`。
  3. 如需切换 MySQL，可先导入 `042-backend/src/main/resources/sql/init.sql`，再运行 `mvn spring-boot:run -Dspring-boot.run.profiles=mysql`。
  4. 前端执行 `npm install` 后使用 `npm run dev` 启动，默认访问 `http://localhost:3042`。

## 7. 备注

1. 本轮后端真实启动使用端口 `8042`，前端真实启动使用端口 `3042`。
2. `mvn` 输出中的 `rdc` profile 警告来自外部 Maven 环境，不影响本项目测试与启动结论。
3. 前端 `dist/`、`node_modules/`、后端 `target/`、临时日志目录均不纳入提交。
