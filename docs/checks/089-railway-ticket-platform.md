# 089 铁路购票平台检查报告

## 1. 检查结论

- 项目编号：`089`
- 项目名称：`铁路购票平台`
- 检查日期：`2026-05-03`
- 当前状态：`已完成`
- 综合结论：`本轮已完成默认 H2 自举、MySQL `root / 1234` 非破坏验证入口、JDK 17 兼容、JWT Bearer 请求头、运行态 token/座位锁存储、登录响应 password 脱敏、真实 HTTP `401/403`、公开班次放行、订单/车票归属校验、普通用户核销越权拦截、前端代理与构建修复，并对默认 H2、mysql-verify 临时库以及前端 `3089` 代理联调完成真实链路复测。`

## 2. 项目形态

- 后端目录：`089-backend`
- 前端目录：`089-frontend`
- 后端技术栈：`Spring Boot 2.7.18 + MyBatis-Plus + JWT + H2/MySQL + Redis 依赖保留`
- 前端技术栈：`Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts`
- 默认后端端口：`8089`
- 前端开发端口：`3089`
- MySQL 账号密码：`root / 1234`

## 3. 本轮确认与修复

1. 默认后端环境已改为 H2 内存库自举，并补齐 `schema-h2.sql` 与 `data-h2.sql`。
2. 新增 `application-mysql.yml` 与 `application-mysql-verify.yml`，保留正式 MySQL 部署入口与非破坏性验证入口。
3. `pom.xml` 已升级到 JDK 17，并补齐 H2、测试依赖和新 MySQL 驱动坐标。
4. 新增 `RuntimeStoreService`，登录态与座位锁不再硬绑定 Redis。
5. `JwtInterceptor` 已兼容 `Authorization: Bearer <token>`，并统一回写 `userId/role`。
6. `WebMvcConfig` 已放行公开班次、车站、车次、公告和座位查询接口，避免前台浏览链路被误拦截。
7. `User.password` 已改为 Jackson `WRITE_ONLY`，登录响应和用户信息接口不再泄露密码。
8. `GlobalExceptionHandler` 已返回真实 HTTP 状态码，未登录 `401`、越权 `403`、参数错误 `400`、资源不存在 `404`。
9. 座位锁、下单、支付、退票审核、登出失效均已在 H2 和 MySQL 双环境复测通过。
10. 普通用户访问统计接口、他人订单详情、车票核销接口均已返回真实 `403`。
11. 前端请求头已统一补齐 Bearer token，Vite 端口和代理目标支持环境变量覆盖。
12. 新增 `RailwayTicketApplicationSmokeTest`，覆盖匿名 `401`、公开班次/公告、下单支付、越权访问、售后审核和登出。

## 4. 验证结果

### 4.1 后端测试

- 执行命令：`089-backend/mvn test`
- 结果：`通过`
- 备注：新增 `RailwayTicketApplicationSmokeTest`，覆盖匿名 `401`、公开班次/公告、登录脱敏、统计越权、锁座下单、余额支付、订单与车票归属、售后申请/审核、核销权限和登出失效。

### 4.2 前端构建

- 执行命令：`089-frontend/npm.cmd run build`
- 结果：`通过`
- 备注：保留 Vite chunk 体积告警，不影响产物生成。

### 4.3 H2 启动与接口抽测

- 后端启动命令：`089-backend/mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=18095`
- 验证端口：`18095`
- 关键结果：`{"anonInfo":401,"publicScheduleCount":3,"publicNoticeCount":2,"loginHasPassword":false,"userStats":403,"orderId":2,"ticketId":2,"crossOrder":403,"verifyAsUser":403,"logout":200,"afterLogout":401}`

### 4.4 MySQL 启动与接口抽测

- MySQL 连接：`localhost:3306`，账号 `root / 1234`
- 准备方式：临时 helper `089-backend/target/MysqlPrepare089.java` 基于 `sql/init.sql` 创建 `railway_ticket_089_verify`
- 后端启动命令：`089-backend/mvn spring-boot:run -Dspring-boot.run.profiles=mysql-verify -Dspring-boot.run.arguments=--server.port=18096`
- 验证库：`railway_ticket_089_verify`
- 验证端口：`18096`
- 关键结果：`{"anonInfo":401,"publicScheduleCount":3,"publicNoticeCount":2,"registerCode":200,"loginHasPassword":false,"userStats":403,"orderId":3,"ticketId":3,"crossOrder":403,"verifyAsUser":403,"logout":200,"afterLogout":401}`

### 4.5 前端代理联调

- 前端启动命令：`VITE_API_TARGET=http://127.0.0.1:18096 npm run dev -- --host 0.0.0.0 --port 3089`
- 前端端口：`3089`
- 结果：`通过`
- 关键结果：`{"home":200,"loginCode":200,"role":"ADMIN","hasPassword":false}`

## 5. H2 与 MySQL 口径说明

默认使用 H2 是为了保证本机直接启动和自动化巡检不破坏正式库。原始 `089-backend/sql/init.sql` 包含 `DROP DATABASE IF EXISTS railway_ticket_089`，因此 MySQL 真实兼容性通过 `railway_ticket_089_verify` 临时库验证；正式部署仍使用 `application-mysql.yml`，账号密码为 `root / 1234`。

## 6. 默认账号

- 管理员：`admin / 123456`
- 调度员：`dispatcher / 123456`
- 乘客：`user / 123456`

## 7. 仍有差距

1. 密码仍为明文存储，生产环境建议改为 BCrypt。
2. 当前运行态 token 和座位锁采用进程内存存储，重启后登录态和锁状态会全部失效；演示足够，但离分布式会话/锁还有距离。
3. Redis 依赖仍保留在工程中，但当前默认演示链路并未验证真实 Redis 高可用、过期策略或多实例同步。
4. 车票分页在普通用户视角下仍采用分页后内存过滤，数据量大时统计总数会有偏差。
5. 改签流程当前只完成售后审核与状态流转，没有自动生成新订单和目标车次换座闭环。
