# 070 基于SpringBoot和Vue的最优网络购票系统检查报告

## 1. 检查结论

- 项目编号：`070`
- 项目名称：`基于SpringBoot和Vue的最优网络购票系统`
- 检查日期：`2026-04-30`
- 当前状态：`已完成`
- 综合结论：`本轮已修复默认环境强依赖 MySQL/Redis/8080、H2 自举缺失、MySQL 驱动旧坐标、JDK 17 配置不足、MyBatis-Plus 分页方言固定 MySQL、Redis 不可用阻断登录和座位锁、JWT 不兼容 Bearer token、未登录/越权仍返回 HTTP 200、用户密码响应泄露、前端代理仍指向 8080 等问题。当前后端可在默认 H2 环境下启动并通过自动化冒烟测试，前端可完成生产构建，真实 HTTP 核心购票链路抽测通过。MySQL profile 已按本机账号 root / 1234 保留。`

## 2. 项目形态

- 后端目录：`070-backend`
- 前端目录：`070-frontend`
- 后端技术栈：`Spring Boot 2.7.18 + MyBatis-Plus 3.5.5 + JWT + H2/MySQL + Redis 可选 + Hutool`
- 前端技术栈：`Vue 3 + Vite 5 + Element Plus + Pinia + Axios + ECharts`
- 默认后端端口：`8070`
- 前端开发端口：`3070`

## 3. 本轮修复

### 3.1 默认运行环境修复

1. 将后端默认端口从 `8080` 调整为 `8070`，避免合集内项目端口冲突。
2. 默认 `application.yml` 改为 H2 内存库自举，新增 `schema-h2.sql` 与 `data-h2.sql`。
3. SQL 初始化显式指定 `UTF-8` 编码，避免中文演示数据乱码。
4. 新增 `application-mysql.yml`，保留 MySQL 部署模式，通过 `-Dspring-boot.run.profiles=mysql` 切换。
5. MySQL profile 默认密码按当前机器约定调整为 `root / 1234`。
6. 修复 MySQL 驱动坐标为 `com.mysql:mysql-connector-j:8.0.33`。
7. 项目编译目标改为 JDK 17，并增加 H2 与 Spring Boot Test 依赖。
8. MyBatis-Plus 分页方言默认改为 H2，MySQL profile 单独使用 MySQL 方言。
9. 默认演示环境使用本地运行态缓存兜底，MySQL profile 保留 Redis 扩展配置。

### 3.2 登录、鉴权与权限修复

1. `User.password` 改为 `WRITE_ONLY`，支持登录/新增写入但响应不再回传密码字段。
2. JWT 拦截器兼容 `Bearer` token。
3. 默认演示环境下登出后旧 token 会失效。
4. JWT 拦截器会重新读取用户状态与角色，普通用户访问管理接口返回真实 HTTP `403`。
5. 未登录、token 无效和登录过期场景返回真实 HTTP `401`。
6. 订单详情加入归属校验，普通用户访问他人订单返回真实 HTTP `403`。

### 3.3 票务业务修复

1. 新增 H2 版初始化数据，覆盖管理员、普通用户、影片、影院、影厅、场次、座位、优惠券、订单、支付、票券和评论。
2. 演示场次日期调整到 `2026-05`，避免毕业设计答辩期间默认数据过期。
3. 座位锁从 Redis 强依赖改为 `RuntimeStoreService`，默认环境可用本地缓存锁座。
4. 普通用户可完成座位查询、锁座、创建订单、优惠券抵扣、创建支付、余额支付和出票。
5. 管理员可完成票券核销、评论审核、用户/影片/影院/影厅/场次/支付/票券/评论分页和统计看板查询。
6. 登出后旧 token 再访问用户信息会返回 HTTP `401`。

### 3.4 前端修复

1. 前端 Vite 开发端口改为 `3070`。
2. 前端代理目标改为 `http://localhost:8070`。
3. 请求拦截器自动补齐 `Bearer` token。
4. HTTP `401` 响应会清理本地登录态并跳转登录页。
5. 新增前端 `package-lock.json`，保证依赖安装可复现。

### 3.5 文档与测试修复

1. 新增后端冒烟测试 `TicketSystemApplicationSmokeTest`。
2. 新增 `070-backend/README.md`，补齐默认 H2 启动、MySQL profile、默认账号、前端端口和验证命令。
3. 新增 `070-backend/启动说明.txt`，便于答辩和本地演示。
4. 新增本巡检报告，并同步更新总台账。

## 4. 当前实现与目标能力对照

### 4.1 已具备能力

1. 后端默认 H2 自举，可直接执行 `mvn spring-boot:run` 启动。
2. 管理员和普通用户账号可登录。
3. 登录响应不回传 `password` 字段。
4. 未登录访问受保护接口返回 HTTP `401`。
5. 普通用户访问用户管理等管理接口返回 HTTP `403`。
6. 普通用户访问他人订单返回 HTTP `403`。
7. 管理员可访问用户、影片、影院、影厅、场次、支付、票券、评论和统计接口。
8. 普通用户可查看座位、锁定座位、创建订单、使用优惠券、创建支付并完成余额支付。
9. 支付成功后可生成票券，管理员可核销票券。
10. 普通用户可完成订单并提交评论，管理员可审核评论。
11. 登出后旧 token 失效，重新访问用户信息返回 HTTP `401`。
12. 前端登录、代理和鉴权请求头已与后端主链路对齐。

### 4.2 仍有差距

1. 密码仍为明文存储，生产环境建议改为 BCrypt。
2. 默认演示模式使用本地运行态缓存，Redis 生产能力需在 MySQL profile 下继续联调。
3. 当前支付为余额支付模拟，真实第三方支付、退款和对账仍未接入。
4. 座位锁为单机演示能力，并发购票场景建议使用 Redis 原子锁与超时释放机制。
5. 前端构建存在 Vite CJS API 过时提示和 chunk 体积提示，当前不影响构建产物生成。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`070-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0, Skipped: 0`
- 覆盖内容：
  1. 默认 H2 上下文可启动
  2. SQL 初始化中文数据为 UTF-8
  3. 未登录访问看板返回 HTTP `401`
  4. 管理员和普通用户登录成功且密码字段不回传
  5. 普通用户访问用户管理返回 HTTP `403`
  6. 普通用户访问他人订单返回 HTTP `403`
  7. 管理员管理接口和统计接口成功
  8. 普通用户查询座位、锁座、创建订单、优惠券抵扣、创建支付和余额支付成功
  9. 支付成功后生成票券
  10. 管理员核销票券成功
  11. 普通用户完成订单并提交评论成功
  12. 管理员审核评论成功
  13. 普通用户登出后旧 token 返回 HTTP `401`

### 5.2 前端构建

- 执行命令：`070-frontend/npm run build`
- 结果：`通过`
- 备注：Vite 输出 CJS API 过时提示和 chunk 体积提示，不影响构建产物生成。

### 5.3 启动与接口抽测

- 后端启动命令：`070-backend/mvn spring-boot:run`
- 结果：`通过`
- 抽测地址：
  1. 公开影片：`GET http://localhost:8070/api/movie/public/list?pageNum=1&pageSize=10`
  2. 未登录看板：`GET http://localhost:8070/api/statistics/dashboard`
  3. 管理员登录：`POST http://localhost:8070/api/auth/login`
  4. 普通用户登录：`POST http://localhost:8070/api/auth/login`
  5. 普通用户越权用户管理：`GET http://localhost:8070/api/user/list?pageNum=1&pageSize=10`
  6. 普通用户越权他人订单：`GET http://localhost:8070/api/order/2`
  7. 用户、影片、影院、影厅、场次、支付、票券、评论和统计查询接口
  8. 座位查询：`GET http://localhost:8070/api/seat/showtime/1`
  9. 锁座：`POST http://localhost:8070/api/seat/lock`
  10. 下单：`POST http://localhost:8070/api/order/create`
  11. 创建支付：`POST http://localhost:8070/api/payment/create`
  12. 余额支付：`POST http://localhost:8070/api/payment/balance`
  13. 我的票券：`GET http://localhost:8070/api/ticket/my?pageNum=1&pageSize=10`
  14. 票券核销：`POST http://localhost:8070/api/ticket/verify`
  15. 完成订单：`PUT http://localhost:8070/api/order/finish/{id}`
  16. 添加评论：`POST http://localhost:8070/api/comment/add`
  17. 审核评论：`PUT http://localhost:8070/api/comment/audit/{id}/APPROVED`
  18. 普通用户登出：`POST http://localhost:8070/api/auth/logout`
- 关键业务结果：
  1. 未登录看板返回 HTTP `401`
  2. 管理员和普通用户登录业务码 `200`，登录响应未包含 `password` 字段
  3. 普通用户访问用户管理返回 HTTP `403`
  4. 普通用户访问他人订单返回 HTTP `403`
  5. 管理员管理接口和统计接口均返回业务码 `200`
  6. 普通用户锁座、下单、支付、出票和评论链路成功
  7. 管理员核销票券和审核评论成功
  8. 普通用户登出业务码 `200`，旧 token 再访问用户信息返回 HTTP `401`
  9. 本轮真实抽测结果：`HTTP_SMOKE_OK orderId=3 ticketId=4 commentId=3`

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- MySQL 兼容配置：`070-backend/src/main/resources/application-mysql.yml`
- MySQL 初始化脚本：`070-backend/sql/init.sql`
- 默认管理员：`admin / 123456`
- 默认普通用户：`user / 123456`
- 默认测试用户：`test / 123456`
- 说明：
  1. 直接执行 `070-backend/mvn spring-boot:run` 即可使用默认 H2 环境启动后端。
  2. H2 控制台地址为 `http://localhost:8070/h2-console`，JDBC URL 为 `jdbc:h2:mem:ticket_system_070`。
  3. 如需切换 MySQL，请先确认 `sql/init.sql` 中的建库/删库语句可接受，再运行 `mysql -uroot -p1234 < sql/init.sql`。
  4. 当前 MySQL profile 默认使用 `root / 1234`，并启用 Redis。
  5. 前端执行 `npm run dev` 后默认访问 `http://localhost:3070`。

## 7. 备注

1. 本轮后端真实启动使用端口 `8070`。
2. 本机 `3306` 可用于 MySQL profile，但原始 SQL 脚本可能包含建库/删库语句，为避免破坏本机数据，默认验证采用 H2 自举链路。
3. `mvn` 输出中的 `rdc` profile 警告来自外部 Maven 环境，不影响本项目测试和启动结论。
4. `target/`、`node_modules/`、`dist/` 和 `.tmp/` 临时日志等生成物不纳入提交。
