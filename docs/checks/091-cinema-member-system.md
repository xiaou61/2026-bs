# 091 电影院会员管理系统检查报告

## 1. 检查结论

- 项目编号：`091`
- 项目名称：`电影院会员管理系统`
- 检查日期：`2026-05-03`
- 当前状态：`已完成`
- 综合结论：`本轮已完成默认 H2 自举、MySQL `root / 1234` 非破坏验证入口、JDK 17 兼容、JWT Bearer 请求头、运行态 token/座位锁存储、登录响应 password 脱敏、真实 HTTP `401/403`、会员/员工/管理员权限边界、评论购票前置约束、前端代理与构建修复，并对默认 H2、mysql-verify 临时库以及前端 `3091` 代理联调完成真实链路复测。`

## 2. 项目形态

- 后端目录：`091-backend`
- 前端目录：`091-frontend`
- 后端技术栈：`Spring Boot 2.7.18 + MyBatis-Plus + JWT + H2/MySQL + Redis 依赖保留`
- 前端技术栈：`Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts`
- 默认后端端口：`8091`
- 前端开发端口：`3091`
- MySQL 账号密码：`root / 1234`

## 3. 本轮确认与修复

1. 默认后端环境已改为 H2 内存库自举，并补齐 `schema-h2.sql` 与 `data-h2.sql`。
2. 新增 `application-mysql.yml` 与 `application-mysql-verify.yml`，保留正式 MySQL 部署入口与非破坏性验证入口。
3. `pom.xml` 已升级到 JDK 17，并补齐 H2、测试依赖和新 MySQL 驱动坐标。
4. 新增 `RuntimeStoreService`，登录态与座位锁不再硬绑定 Redis。
5. `JwtInterceptor` 已兼容 `Authorization: Bearer <token>`，并统一回写 `userId/role`。
6. `User.password` 已改为 Jackson `WRITE_ONLY`，登录响应和用户信息接口不再泄露密码。
7. `GlobalExceptionHandler` 已返回真实 HTTP 状态码，未登录 `401`、越权 `403`、参数错误 `400/500` 能被真实透出。
8. 会员下单、支付、充值、领券、评论、我的订单/票券接口已限制为 `MEMBER` 角色，员工和管理员不能冒充会员消费。
9. 评论新增已补齐“必须绑定本人已购订单且影片匹配”的前置约束，未购票会员不能直接评论。
10. 观影码核销已确认只允许管理员/门店员工执行，普通会员核销会返回真实 `403`。
11. 前端请求头已统一补齐 Bearer token，Vite 端口和代理目标支持环境变量覆盖。
12. 新增 `CinemaMemberApplicationSmokeTest`，覆盖匿名 `401`、公开列表、统计越权、锁座下单支付、跨用户订单/票券越权、评论前置、核销权限和登出失效。

## 4. 验证结果

### 4.1 后端测试

- 执行命令：`091-backend/mvn test`
- 结果：`通过`
- 备注：新增 `CinemaMemberApplicationSmokeTest`，覆盖匿名 `401`、公开影片/场次、登录脱敏、统计越权、会员锁座下单支付、员工下单越权、跨用户订单/票券越权、评论购票前置、核销权限和登出失效。

### 4.2 前端构建

- 执行命令：`091-frontend/npm.cmd install`
- 结果：`通过`

- 执行命令：`091-frontend/npm.cmd run build`
- 结果：`通过`
- 备注：保留 Vite chunk 体积告警，不影响产物生成。

### 4.3 H2 启动与接口抽测

- 后端启动命令：`091-backend/mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=18099`
- 验证端口：`18099`
- 关键结果：`{"anonInfo":401,"publicMovieTotal":4,"publicShowtimeTotal":5,"loginHasPassword":false,"memberStats":403,"orderId":5,"staffCreateOrder":403,"crossOrder":403,"ticketId":6,"crossTicket":403,"fakeComment":403,"memberVerify":403,"staffVerify":200,"logout":200,"afterLogout":401}`

### 4.4 MySQL 启动与接口抽测

- MySQL 连接：`localhost:3306`，账号 `root / 1234`
- 准备方式：临时 helper `091-backend/target/MysqlPrepare091.java` 创建 `cinema_member_system_091_verify`
- 后端启动命令：`091-backend/mvn spring-boot:run -Dspring-boot.run.profiles=mysql-verify -Dspring-boot.run.arguments=--server.port=18100`
- 验证库：`cinema_member_system_091_verify`
- 验证端口：`18100`
- 关键结果：`{"anonInfo":401,"publicMovieTotal":4,"publicShowtimeTotal":5,"loginHasPassword":false,"memberStats":403,"orderId":4,"staffCreateOrder":403,"crossOrder":403,"ticketId":4,"crossTicket":403,"fakeComment":403,"memberVerify":403,"staffVerify":200,"logout":200,"afterLogout":401}`

### 4.5 前端代理联调

- 前端启动命令：`VITE_API_TARGET=http://127.0.0.1:18099 npm run dev -- --host 0.0.0.0 --port 3091`
- 前端端口：`3091`
- 结果：`通过`
- 关键结果：`{"home":200,"loginCode":200,"role":"ADMIN","hasPassword":false}`

## 5. H2 与 MySQL 口径说明

默认使用 H2 是为了保证本机直接启动和自动化巡检不破坏正式库。原始 `091-backend/sql/init.sql` 包含 `DROP DATABASE IF EXISTS cinema_member_system_091`，因此 MySQL 真实兼容性通过 `cinema_member_system_091_verify` 临时库验证；正式部署仍使用 `application-mysql.yml`，账号密码为 `root / 1234`。

## 6. 默认账号

- 管理员：`admin / 123456`
- 门店员工：`staff / 123456`
- 会员：`member / 123456`

## 7. 仍有差距

1. 密码仍为明文存储，生产环境建议改为 BCrypt。
2. 当前运行态 token 和座位锁采用进程内存存储，重启后登录态和锁状态会全部失效；演示足够，但离分布式会话/锁还有距离。
3. Redis 依赖仍保留在工程中，但当前默认演示链路并未验证真实 Redis 高可用、过期策略或多实例同步。
4. 订单支付、退款、观影码核销和评论链路已可演示，但真实支付渠道、短信/消息通知和文件上传仍未落地。
5. 前端打包存在较大的 chunk 告警，后续仍建议拆分路由和按需加载。
