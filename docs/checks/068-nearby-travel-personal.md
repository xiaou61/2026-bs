# 068 周边游平台个人管理模块检查报告

## 1. 检查结论

- 项目编号：`068`
- 项目名称：`周边游平台个人管理模块`
- 检查日期：`2026-04-30`
- 当前状态：`已完成`
- 综合结论：`本轮已修复默认环境强依赖 MySQL/Redis/8080、H2 自举缺失、MySQL 驱动旧坐标、MyBatis-Plus 分页方言固定 MySQL、JWT 不兼容 Bearer token、Redis 不可用会阻断默认演示登录、未登录/越权仍返回 HTTP 200、用户密码响应泄露、前端代理仍指向 8080 等问题。当前后端可在默认 H2 环境下启动并通过自动化冒烟测试，前端可完成生产构建，真实 HTTP 核心链路抽测通过。MySQL profile 已按本机账号 `root / 1234` 保留。`

## 2. 项目形态

- 后端目录：`068-backend`
- 前端目录：`068-frontend`
- 后端技术栈：`Spring Boot 2.7.18 + MyBatis-Plus 3.5.5 + JWT + H2/MySQL + Redis 可选 + Hutool`
- 前端技术栈：`Vue 3 + Vite 5 + Element Plus + Pinia + Axios + ECharts`
- 默认后端端口：`8068`
- 前端开发端口：`3068`

## 3. 本轮修复

### 3.1 默认运行环境修复

1. 将后端默认端口从 `8080` 调整为 `8068`，避免合集内项目端口冲突。
2. 默认 `application.yml` 改为 H2 内存库自举，新增 `schema-h2.sql` 与 `data-h2.sql`。
3. SQL 初始化显式指定 `UTF-8` 编码，避免中文演示数据乱码。
4. 新增 `application-mysql.yml`，保留 MySQL 部署模式，通过 `-Dspring-boot.run.profiles=mysql` 切换。
5. MySQL profile 默认密码按当前机器约定调整为 `root / 1234`。
6. 修复 MySQL 驱动坐标为 `com.mysql:mysql-connector-j:8.0.33`。
7. 项目编译目标改为 JDK 17，并增加 H2 与 Spring Boot Test 依赖。
8. MyBatis-Plus 分页方言默认改为 H2，MySQL profile 单独使用 MySQL 方言。
9. 默认演示环境使用本地 token 缓存兜底，MySQL profile 保留 Redis 扩展配置。

### 3.2 登录、鉴权与权限修复

1. `User.password` 改为 `WRITE_ONLY`，支持登录/新增写入但响应不再回传密码字段。
2. JWT 拦截器兼容 `Bearer` token。
3. 默认演示环境下登出后旧 token 会失效。
4. JWT 拦截器会重新读取用户状态，禁用或删除用户后旧 token 不再继续通过。
5. 未登录、token 无效和登录过期场景返回真实 HTTP `401`。
6. 普通用户访问用户管理等管理接口返回真实 HTTP `403`。

### 3.3 业务与数据修复

1. 新增 H2 版初始化数据，覆盖管理员、普通用户、景点、出行人、收藏、订单、评价和投诉。
2. 管理员可访问用户管理、景点管理和看板接口。
3. 普通用户可浏览景点、管理出行人、收藏景点、创建订单、支付并完成订单。
4. 普通用户可对已完成订单评价，管理员可回复已通过评价。
5. 普通用户可提交投诉，管理员可处理投诉。
6. 订单导出接口和 7 天趋势看板接口可在默认 H2 环境下正常返回。

### 3.4 前端修复

1. 前端 Vite 开发端口改为 `3068`。
2. 前端代理目标改为 `http://localhost:8068`。
3. 请求拦截器自动补齐 `Bearer` token。
4. HTTP `401` 响应会清理本地登录态并跳转登录页。
5. 新增前端 `package-lock.json`，保证依赖安装可复现。

### 3.5 文档与测试修复

1. 新增后端冒烟测试 `NearbyTravelApplicationSmokeTest`。
2. 新增 `068-backend/README.md`，补齐默认 H2 启动、MySQL profile、默认账号、前端端口和验证命令。
3. 新增 `068-backend/启动说明.txt`，便于答辩和本地演示。
4. 新增本巡检报告，并同步更新总台账。

## 4. 当前实现与目标能力对照

### 4.1 已具备能力

1. 后端默认 H2 自举，可直接执行 `mvn spring-boot:run` 启动。
2. 管理员和普通用户账号可登录。
3. 登录响应不回传 `password` 字段。
4. 未登录访问受保护接口返回 HTTP `401`。
5. 普通用户访问用户管理返回 HTTP `403`。
6. 管理员可访问用户管理、景点管理和看板接口。
7. 普通用户可查询景点、维护出行人、收藏景点和查询收藏列表。
8. 普通用户可创建订单、支付订单、完成订单、查询我的订单和导出订单。
9. 普通用户可对已完成订单提交评价，管理员可回复评价。
10. 普通用户可提交投诉，管理员可处理投诉。
11. 看板统计和 7 天趋势接口可正常返回数据。
12. 登出后旧 token 失效，重新访问用户信息返回 HTTP `401`。
13. 前端登录、代理和鉴权请求头已与后端主链路对齐。

### 4.2 仍有差距

1. 密码仍为明文存储，生产环境建议改为 BCrypt。
2. Redis 配置仍保留为生产扩展项，默认演示链路不依赖 Redis。
3. 订单支付仍是演示状态流转，未接入真实支付和退款。
4. 景点库存、班期容量和并发锁单能力尚未完整落地。
5. 图片上传、附件存储和投诉材料管理仍偏基础。
6. 前端构建存在 Vite chunk 体积提示，当前不影响构建产物生成。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`068-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0, Skipped: 0`
- 覆盖内容：
  1. 默认 H2 上下文可启动
  2. SQL 初始化中文数据为 UTF-8
  3. 未登录访问看板返回 HTTP `401`
  4. 管理员和普通用户登录成功且密码字段不回传
  5. 普通用户访问用户管理返回 HTTP `403`
  6. 管理员用户管理、景点管理和看板接口成功
  7. 普通用户景点、出行人、收藏接口成功
  8. 普通用户创建订单、支付订单、完成订单成功
  9. 普通用户评价、管理员回复评价成功
  10. 普通用户投诉、管理员处理投诉成功
  11. 订单导出和看板趋势接口成功
  12. 普通用户登出后旧 token 返回 HTTP `401`

### 5.2 前端构建

- 执行命令：`068-frontend/npm run build`
- 结果：`通过`
- 备注：Vite 输出 CJS API 过时提示和 chunk 体积提示，不影响构建产物生成。

### 5.3 启动与接口抽测

- 后端启动命令：`068-backend/mvn spring-boot:run`
- 结果：`通过`
- 抽测地址：
  1. 未登录看板：`GET http://localhost:8068/api/dashboard/stats`
  2. 管理员登录：`POST http://localhost:8068/api/auth/login`
  3. 普通用户登录：`POST http://localhost:8068/api/auth/login`
  4. 普通用户越权用户管理：`GET http://localhost:8068/api/user/page`
  5. 用户、景点、出行人、收藏和看板查询接口
  6. 普通用户创建订单：`POST http://localhost:8068/api/order`
  7. 普通用户支付订单：`PUT http://localhost:8068/api/order/pay`
  8. 普通用户完成订单：`PUT http://localhost:8068/api/order/finish`
  9. 普通用户提交评价：`POST http://localhost:8068/api/review`
  10. 管理员回复评价：`PUT http://localhost:8068/api/review/reply`
  11. 普通用户提交投诉：`POST http://localhost:8068/api/complaint`
  12. 管理员处理投诉：`PUT http://localhost:8068/api/complaint/handle`
  13. 普通用户登出：`POST http://localhost:8068/api/auth/logout`
- 关键业务结果：
  1. 未登录看板返回 HTTP `401`
  2. 管理员和普通用户登录业务码 `200`，登录响应未包含 `password` 字段
  3. 普通用户访问用户管理返回 HTTP `403`
  4. 用户、景点、出行人、收藏、订单、评价、投诉和看板相关接口均返回业务码 `200`
  5. 普通用户创建订单成功，抽测订单 ID 为 `4`
  6. 普通用户提交评价成功，抽测评价 ID 为 `2`
  7. 普通用户提交投诉成功，抽测投诉 ID 为 `2`
  8. 看板趋势返回 7 天数据
  9. 普通用户登出业务码 `200`，旧 token 再访问用户信息返回 HTTP `401`

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- MySQL 兼容配置：`068-backend/src/main/resources/application-mysql.yml`
- MySQL 初始化脚本：`068-backend/sql/init.sql`
- 默认管理员：`admin / 123456`
- 默认普通用户：`user / 123456`
- 说明：
  1. 直接执行 `068-backend/mvn spring-boot:run` 即可使用默认 H2 环境启动后端。
  2. H2 控制台地址为 `http://localhost:8068/h2-console`，JDBC URL 为 `jdbc:h2:mem:nearby_travel_068`。
  3. 如需切换 MySQL，请先确认 `sql/init.sql` 中的 `DROP DATABASE` 可接受，再运行 `mysql -uroot -p1234 < sql/init.sql`。
  4. 当前 MySQL profile 默认使用 `root / 1234`。
  5. 前端执行 `npm run dev` 后默认访问 `http://localhost:3068`。

## 7. 备注

1. 本轮后端真实启动使用端口 `8068`。
2. 本机 `3306` 可用于 MySQL profile，但原始 SQL 脚本包含 `DROP DATABASE`，为避免破坏本机数据，默认验证采用 H2 自举链路。
3. `mvn` 输出中的 `rdc` profile 警告来自外部 Maven 环境，不影响本项目测试和启动结论。
4. `target/`、`node_modules/`、`dist/` 和 `.tmp/` 临时日志等生成物不纳入提交。
