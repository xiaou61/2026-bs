# 074 手工艺品销售系统检查报告

## 1. 检查结论

- 项目编号：`074`
- 项目名称：`手工艺品销售系统`
- 检查日期：`2026-05-02`
- 当前状态：`已完成`
- 综合结论：`本轮已修复默认环境强依赖 MySQL/Redis/8080、H2 自举缺失、MySQL 驱动旧坐标、后端无自动化测试、登录响应 password 字段泄露、JWT Bearer 不兼容、未登录/越权 HTTP 状态不准确、登出后 token 不失效、普通用户可访问管理接口、卖家/买家订单和评价申诉归属校验不足、前端代理仍指向 8080、前端请求头缺少 Bearer token 等问题。当前后端可在默认 H2 环境下启动并通过自动化冒烟测试，前端可完成生产构建，真实 HTTP 交易主链路和前端代理登录复测通过。`

## 2. 项目形态

- 后端目录：`074-backend`
- 前端目录：`074-frontend`
- 后端技术栈：`Spring Boot 2.7.18 + MyBatis-Plus 3.5.5 + JWT + H2/MySQL + Redis 依赖保留`
- 前端技术栈：`Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts`
- 默认后端端口：`8074`
- 前端开发端口：`3074`

## 3. 本轮修复

### 3.1 默认运行环境修复

1. 将后端默认端口从 `8080` 调整为 `8074`。
2. 默认 `application.yml` 改为 H2 内存库自举，新增 `db/schema-h2.sql` 与 `db/data-h2.sql`。
3. 新增 `application-mysql.yml`，保留原 MySQL 部署入口。
4. 修复 MySQL 驱动坐标为 `com.mysql:mysql-connector-j`。
5. 项目编译目标改为 JDK 17，并增加 H2 与 Spring Boot Test 依赖。
6. MyBatis-Plus 分页方言改为配置化，默认使用 `H2`，MySQL profile 使用 `MYSQL`。
7. 默认演示数据覆盖管理员、卖家、买家、分类、商品、订单、收藏、评价、申诉和公告。

### 3.2 登录、鉴权与权限修复

1. `User.password` 改为 Jackson `WRITE_ONLY`，登录响应、用户信息响应和前端代理登录均不再回传密码字段。
2. JWT 拦截器兼容 `Authorization: Bearer <token>`。
3. 新增本地运行态 token 失效表，默认演示环境不再依赖 Redis 才能完成登录/登出闭环。
4. 登出后旧 token 再访问用户信息返回 HTTP `401`。
5. 全局异常处理改为真实 HTTP 状态：未登录/失效 `401`，越权 `403`，参数错误 `400`，资源不存在 `404`。
6. 用户、分类、商品、订单、评价、申诉、公告和看板等管理接口增加服务端管理员校验。
7. 买家访问后台看板返回 HTTP `403`。

### 3.3 交易与归属校验修复

1. 商品编辑和删除增加卖家本人或管理员校验。
2. 订单支付、发货、完成和取消增加买家/卖家/管理员归属校验。
3. 非商品所属卖家发货返回 HTTP `403`。
4. 评价提交要求订单归属当前用户且订单已完成。
5. 申诉提交要求订单归属当前用户，避免用户枚举他人订单发起申诉。
6. 申诉分页管理接口仅允许管理员访问。

### 3.4 前端修复

1. 前端 Vite 开发端口改为 `3074`。
2. 前端代理目标改为 `http://localhost:8074`，并支持 `VITE_API_TARGET` 覆盖。
3. 请求拦截器自动补齐 `Bearer` token。
4. HTTP `401` 会清理登录态并跳转登录页。
5. 新增 `package-lock.json`，保证依赖安装可复现。

### 3.5 文档与测试修复

1. 新增后端冒烟测试 `CraftSalesApplicationSmokeTest`。
2. 新增 `074-backend/README.md`，补齐默认启动、MySQL profile、默认账号、前端联调和验证命令。
3. 新增 `074-backend/启动说明.txt`，便于答辩和本地演示。
4. 新增本巡检报告，并同步更新总台账。

## 4. 当前实现与目标能力对照

### 4.1 已具备能力

1. 后端默认 H2 自举，可直接执行 `mvn spring-boot:run` 启动。
2. 管理员、卖家和买家账号可登录。
3. 登录响应不回传 `password` 字段。
4. 未登录访问受保护接口返回 HTTP `401`。
5. 买家访问管理看板返回 HTTP `403`。
6. 管理员可访问看板、申诉分页等后台接口。
7. 买家可浏览商品、分类和公告。
8. 买家可创建订单、支付订单、完成订单、评价、收藏和发起申诉。
9. 非订单所属卖家无法发货，返回 HTTP `403`。
10. 订单所属卖家可发货。
11. 登出后旧 token 失效，重新访问用户信息返回 HTTP `401`。
12. 前端构建和代理登录已与后端主链路对齐。

### 4.2 仍有差距

1. 密码仍为明文存储，生产环境建议改为 BCrypt。
2. 默认演示模式使用 H2 内存库，MySQL profile 需要按目标机器单独导入 `sql/init.sql`。
3. Redis 依赖保留但当前默认演示链路未使用分布式会话。
4. 支付、退款和物流为模拟状态流转，未接入真实第三方支付或物流。
5. 图片上传和对象存储能力仍为基础实现。
6. 库存扣减未实现高并发锁库存策略。
7. 前端构建存在 chunk 体积提示，当前不影响构建产物生成。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`074-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0, Skipped: 0`
- 覆盖内容：
  1. 默认 H2 上下文可启动
  2. 未登录访问用户信息返回 HTTP `401`
  3. 管理员、卖家和买家登录成功且密码字段不回传
  4. 买家访问看板返回 HTTP `403`
  5. 管理员访问看板成功
  6. 买家创建订单、支付订单成功
  7. 非订单所属卖家发货返回 HTTP `403`
  8. 订单所属卖家发货成功
  9. 买家确认完成、评价、收藏和申诉成功
  10. 管理员访问申诉分页成功
  11. 管理员登出后旧 token 返回 HTTP `401`

### 5.2 前端构建

- 执行命令：`074-frontend/npm install`
- 结果：`通过`
- 执行命令：`074-frontend/npm run build`
- 结果：`通过`
- 备注：Vite 输出 CJS API 过期提示和 chunk 体积提示，不影响构建产物生成。

### 5.3 启动与接口抽测

- 后端启动命令：`074-backend/mvn spring-boot:run`
- 结果：`通过`
- 后端端口：`8074`
- 真实 HTTP 抽测结果：
  1. `GET /api/auth/info` 未登录返回 HTTP `401`
  2. `POST /api/auth/login` 管理员、卖家、买家登录均返回 HTTP `200`，且无 `password` 字段
  3. 买家访问 `GET /api/dashboard/stats` 返回 HTTP `403`
  4. 管理员访问 `GET /api/dashboard/stats` 返回 HTTP `200`
  5. 买家访问商品、分类和公告列表返回 HTTP `200`
  6. 买家创建订单、支付订单返回 HTTP `200`
  7. `seller1` 对 `seller2` 商品订单发货返回 HTTP `403`
  8. `seller2` 对本人商品订单发货返回 HTTP `200`
  9. 买家确认完成、评价、收藏和申诉返回 HTTP `200`
  10. 管理员访问申诉分页返回 HTTP `200`
  11. 管理员登出返回 HTTP `200`
  12. 登出后访问 `GET /api/auth/info` 返回 HTTP `401`

### 5.4 前端代理联调

- 后端启动端口：`8074`
- 前端启动命令：`074-frontend/npm run dev -- --host 127.0.0.1`
- 前端端口：`3074`
- 结果：`通过`
- 关键结果：
  1. `GET http://127.0.0.1:3074/` 返回 HTTP `200`，页面包含 `#app` 挂载点
  2. `POST http://127.0.0.1:3074/api/auth/login` 返回 HTTP `200`，业务码 `200`，角色为 `ADMIN`，token 存在，且无 `password` 字段

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- MySQL 兼容配置：`074-backend/src/main/resources/application-mysql.yml`
- MySQL 初始化脚本：`074-backend/sql/init.sql`
- 默认管理员：`admin / 123456`
- 默认卖家一：`seller1 / 123456`
- 默认卖家二：`seller2 / 123456`
- 默认买家：`buyer1 / 123456`
- 说明：
  1. 直接执行 `074-backend/mvn spring-boot:run` 即可使用默认 H2 环境启动后端。
  2. H2 控制台地址为 `http://localhost:8074/h2-console`，JDBC URL 为 `jdbc:h2:mem:craft_sales_074`。
  3. 如需切换 MySQL，请先确认 `sql/init.sql` 中的建库/删库语句可接受，再导入脚本并使用 `mysql` profile 启动。
  4. 前端执行 `npm run dev` 后默认访问 `http://localhost:3074`。

## 7. 备注

1. 本轮后端真实启动使用端口 `8074`，前端真实启动使用端口 `3074`。
2. 原始 SQL 脚本包含 `DROP DATABASE`，为避免破坏本机数据，默认验证采用 H2 自举链路。
3. `mvn` 输出中的 `rdc` profile 警告来自外部 Maven 环境，不影响本项目测试和启动结论。
4. `target/`、`node_modules/`、`dist/` 和临时日志等生成物不纳入提交。

