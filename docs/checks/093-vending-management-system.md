# 093 自助售货管理系统检查报告

## 1. 检查结论

- 项目编号：`093`
- 项目名称：`基于 Spring Boot 和 Vue 的自助售货管理系统`
- 检查日期：`2026-05-03`
- 当前状态：`已完成`
- 综合结论：`本轮已完成默认 H2 自举、MySQL root / 1234 非破坏验证入口、JDK 17 兼容、Mapper 扫描修复、JWT Bearer 请求头、运行态 token 存储、登录响应 password 脱敏、真实 HTTP 401/403/404/500、顾客/补货员/管理员权限边界、前端代理与构建修复，并对默认 H2、mysql-verify 临时库以及前端 3093 代理联调完成真实链路复测。`

## 2. 项目形态

- 后端目录：`093-backend`
- 前端目录：`093-frontend`
- 后端技术栈：`Spring Boot 2.7.18 + MyBatis-Plus + JWT + H2/MySQL + Redis 依赖保留`
- 前端技术栈：`Vue 3 + Vite + Element Plus + Axios`
- 默认后端端口：`8093`
- 前端开发端口：`3093`
- MySQL 账号密码：`root / 1234`

## 3. 本轮确认与修复

1. 默认后端环境已改为 H2 内存库自举，并补齐 `schema-h2.sql` 与 `data-h2.sql`。
2. 新增 `application-mysql.yml` 与 `application-mysql-verify.yml`，保留正式 MySQL 部署入口与非破坏性验证入口。
3. `pom.xml` 已升级到 JDK 17，并补齐 H2、JAXB、Spring Boot Test 与新版 MySQL 驱动。
4. `VendingManagementApplication` 已补充 `@MapperScan("com.vending.mapper")`，修复原项目启动时 Mapper 未注册的问题。
5. 新增 `RuntimeStoreService`，登录态不再硬绑定 Redis。
6. `JwtInterceptor` 已兼容 `Authorization: Bearer <token>`，并统一回写 `userId/role`。
7. `User.password` 已改为 Jackson `WRITE_ONLY`，登录响应和用户信息接口不再泄露密码。
8. `GlobalExceptionHandler` 已返回真实 HTTP 状态码，未登录 `401`、越权 `403`、不存在 `404`、系统异常 `500` 可被真实透出。
9. 订单创建、余额支付、充值、故障上报已限制为 `CUSTOMER`，补货处理链路限制为 `STAFF/ADMIN`，统计和基础资料管理限制为 `ADMIN`。
10. `OrderService` 与 `FaultService` 已补齐本人订单归属校验，禁止跨用户支付和伪造他人订单故障。
11. 前端请求头已统一补齐 Bearer token，Vite 端口和代理目标支持环境变量覆盖。
12. 新增 `VendingManagementApplicationSmokeTest`，覆盖匿名 `401`、公开设备/商品/公告、登录脱敏、统计越权、员工下单越权、顾客下单支付、跨订单越权、故障归属和登出失效。

## 4. 验证结果

### 4.1 后端测试

- 执行命令：`093-backend/mvn.cmd test`
- 结果：`通过`
- 备注：新增 `VendingManagementApplicationSmokeTest`，覆盖公开接口、登录脱敏、角色权限、订单支付、故障归属和登出失效。

### 4.2 前端构建

- 执行命令：`093-frontend/npm.cmd install`
- 结果：`通过`

- 执行命令：`093-frontend/npm.cmd run build`
- 结果：`通过`
- 备注：保留 Vite chunk 体积告警，不影响产物生成。

### 4.3 H2 启动与接口抽测

- 后端启动命令：`093-backend/mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=18103`
- 验证端口：`18103`
- 关键结果：`{"anonInfo":401,"publicNotice":2,"adminRole":"ADMIN","passwordHidden":true,"customerStats":403}`

### 4.4 MySQL 启动与接口抽测

- MySQL 连接：`localhost:3306`，账号 `root / 1234`
- 准备方式：临时 helper `093-backend/target/MysqlPrepare093.java` 创建 `vending_093_verify`
- 后端启动命令：`093-backend/mvn spring-boot:run -Dspring-boot.run.profiles=mysql-verify -Dspring-boot.run.arguments=--server.port=18104`
- 验证库：`vending_093_verify`
- 验证端口：`18104`
- 关键结果：`{"anonInfo":401,"publicNotice":2,"adminRole":"ADMIN","passwordHidden":true,"customerStats":403,"staffOrder":403}`

### 4.5 前端代理联调

- 前端启动命令：`VITE_API_TARGET=http://127.0.0.1:18103 npm run dev -- --host 0.0.0.0 --port 3093`
- 前端端口：`3093`
- 结果：`通过`
- 关键结果：`{"home":200,"loginCode":200,"role":"ADMIN","hasPassword":false}`

## 5. H2 与 MySQL 口径说明

默认使用 H2 是为了保证本机直接启动和自动化巡检不破坏正式库。原始 `093-backend/sql/init.sql` 包含 `DROP DATABASE IF EXISTS vending_management_system_093`，因此 MySQL 真实兼容性通过 `vending_093_verify` 临时库验证；正式部署仍使用 `application-mysql.yml`，账号密码为 `root / 1234`。

## 6. 默认账号

- 管理员：`admin / 123456`
- 补货员：`staff / 123456`
- 顾客：`customer / 123456`

## 7. 仍有差距

1. 密码仍为明文存储，生产环境建议改为 BCrypt。
2. 当前运行态 token 采用进程内存存储，重启后登录态会全部失效；演示足够，但离分布式会话还有距离。
3. Redis 依赖仍保留在工程中，但当前默认演示链路并未验证真实 Redis 高可用、缓存或多实例同步。
4. 自动出货、补货、支付都还是单机演示链路，缺少真实支付回调、设备回执和并发扣库存保护。
5. 前端仍有明显大包告警，设备大屏、图片上传和经营分析深度能力还需要后续优化。
