# 071 基于SpringBoot和Vue的共享单车系统检查报告

## 1. 检查结论

- 项目编号：`071`
- 项目名称：`基于SpringBoot和Vue的共享单车系统`
- 检查日期：`2026-05-01`
- 当前状态：`已完成`
- 综合结论：`本轮已修复默认环境强依赖 MySQL/Redis/8080、H2 自举缺失、MySQL 驱动旧坐标、JDK 17 配置不足、PageHelper 固定 MySQL 方言、JWT 不兼容 Bearer token、Redis 不可用阻断登录、未登录/越权 HTTP 状态不准确、用户密码响应泄露、骑行订单详情可被其他用户越权查看、趋势统计 SQL 不兼容 H2、前端代理仍指向 8080 等问题。当前后端可在默认 H2 环境下启动并通过自动化冒烟测试，前端可完成生产构建，真实 HTTP 共享单车主链路抽测通过。MySQL profile 已按本机账号 root / 1234 保留。`

## 2. 项目形态

- 后端目录：`071-backend`
- 前端目录：`071-frontend`
- 后端技术栈：`Spring Boot 2.7.18 + MyBatis XML + PageHelper + JWT + H2/MySQL + Redis 可选 + Hutool`
- 前端技术栈：`Vue 3 + Vite 5 + Element Plus + Pinia + Axios + ECharts`
- 默认后端端口：`8071`
- 前端开发端口：`3071`

## 3. 本轮修复

### 3.1 默认运行环境修复

1. 将后端默认端口从 `8080` 调整为 `8071`，避免合集内项目端口冲突。
2. 默认 `application.yml` 改为 H2 内存库自举，新增 `schema-h2.sql` 与 `data-h2.sql`。
3. SQL 初始化显式指定 `UTF-8` 编码，避免中文演示数据乱码。
4. 新增 `application-mysql.yml`，保留 MySQL 部署模式，通过 `-Dspring-boot.run.profiles=mysql` 切换。
5. MySQL profile 默认密码按当前机器约定调整为 `root / 1234`。
6. 修复 MySQL 驱动坐标为 `com.mysql:mysql-connector-j:8.0.33`。
7. 项目编译目标改为 JDK 17，并增加 H2 与 Spring Boot Test 依赖。
8. PageHelper 默认方言改为 `h2`，MySQL profile 单独使用 MySQL 方言。
9. 默认演示环境使用本地运行态缓存兜底，MySQL profile 保留 Redis 扩展配置。

### 3.2 登录、鉴权与权限修复

1. `User.password` 改为 `WRITE_ONLY`，支持登录/新增写入但响应不再回传密码字段。
2. JWT 拦截器兼容 `Bearer` token。
3. 默认演示环境下登出后旧 token 会失效。
4. JWT 拦截器会重新读取用户状态与角色，普通用户访问用户管理、计费规则、运维管理接口返回真实 HTTP `403`。
5. 未登录、token 无效和登录过期场景返回真实 HTTP `401`。
6. 骑行订单详情加入归属校验，普通用户访问他人订单返回真实 HTTP `403`。

### 3.3 共享单车业务修复

1. 新增 H2 版初始化数据，覆盖管理员、运维人员、普通用户、站点、车辆、计费规则、骑行订单、钱包流水、故障、信用记录、公告和反馈。
2. 开始骑行前校验押金、信用分、进行中订单、车辆可用状态、站点可用状态以及车辆所在站点。
3. 结束骑行前校验还车站点可用性，并完成扣费、钱包流水、车辆归站、站点车辆数和信用奖励更新。
4. 故障上报会校验车辆存在，故障处理会校验记录存在，避免空指针变成 500。
5. 钱包充值增加正数校验，避免负数充值绕开业务语义。
6. 趋势统计查询移除 H2 不支持的 `DATE_SUB` 函数，保证默认演示看板可用。

### 3.4 前端修复

1. 前端 Vite 开发端口改为 `3071`。
2. 前端代理目标改为 `http://localhost:8071`，并支持 `VITE_API_TARGET` 覆盖。
3. 请求拦截器自动补齐 `Bearer` token。
4. HTTP `401` 响应会清理本地登录态并跳转登录页。
5. 新增前端 `package-lock.json`，保证依赖安装可复现。

### 3.5 文档与测试修复

1. 新增后端冒烟测试 `BikeSystemApplicationSmokeTest`。
2. 新增 `071-backend/README.md`，补齐默认 H2 启动、MySQL profile、默认账号、前端端口和验证命令。
3. 新增 `071-backend/启动说明.txt`，便于答辩和本地演示。
4. 新增本巡检报告，并同步更新总台账。

## 4. 当前实现与目标能力对照

### 4.1 已具备能力

1. 后端默认 H2 自举，可直接执行 `mvn spring-boot:run` 启动。
2. 管理员、运维人员和普通用户账号可登录。
3. 登录响应不回传 `password` 字段。
4. 未登录访问受保护接口返回 HTTP `401`。
5. 普通用户访问用户管理、运维管理和后台看板接口返回 HTTP `403`。
6. 普通用户访问他人骑行订单返回 HTTP `403`。
7. 管理员可访问用户、计费、信用调整和统计接口。
8. 运维人员可访问车辆、站点、故障、反馈、公告和订单管理接口。
9. 普通用户可查看可用站点/车辆、开始骑行、结束骑行、扣费、查询钱包流水、上报故障和查看信用分。
10. 登出后旧 token 失效，重新访问用户信息返回 HTTP `401`。
11. 前端登录、代理和鉴权请求头已与后端主链路对齐。

### 4.2 仍有差距

1. 密码仍为明文存储，生产环境建议改为 BCrypt。
2. 默认演示模式使用本地运行态缓存，Redis 生产能力需在 MySQL profile 下继续联调。
3. 钱包、押金和骑行费用均为本地模拟记账，真实支付、退款和对账仍未接入。
4. 扫码骑行当前以 `bikeId` 和站点参数模拟，真实二维码、定位和防作弊策略仍未实现。
5. 前端构建存在 Vite CJS API 过时提示和 chunk 体积提示，当前不影响构建产物生成。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`071-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0, Skipped: 0`
- 覆盖内容：
  1. 默认 H2 上下文可启动
  2. 未登录访问看板返回 HTTP `401`
  3. 管理员、运维人员和普通用户登录成功且密码字段不回传
  4. 普通用户访问用户管理返回 HTTP `403`
  5. 普通用户访问他人骑行订单返回 HTTP `403`
  6. 管理员和运维人员管理接口成功
  7. 普通用户查询站点与可用车辆成功
  8. 普通用户开始骑行、查询当前骑行、结束骑行和钱包扣费成功
  9. 普通用户上报故障，运维人员处理故障成功
  10. 管理员调整信用分成功
  11. 普通用户登出后旧 token 返回 HTTP `401`

### 5.2 前端构建

- 执行命令：`071-frontend/npm run build`
- 结果：`通过`
- 备注：Vite 输出 CJS API 过时提示和 chunk 体积提示，不影响构建产物生成。

### 5.3 启动与接口抽测

- 后端启动命令：`071-backend/mvn spring-boot:run`
- 结果：`通过`
- 抽测地址：
  1. 未登录看板：`GET http://localhost:8071/api/statistics/overview`
  2. 管理员登录：`POST http://localhost:8071/api/user/login`
  3. 普通用户登录：`POST http://localhost:8071/api/user/login`
  4. 管理员看板：`GET http://localhost:8071/api/statistics/overview`
  5. 普通用户站点列表：`GET http://localhost:8071/api/station/all`
  6. 开始骑行：`POST http://localhost:8071/api/ride/start`
  7. 结束骑行：`POST http://localhost:8071/api/ride/end`
  8. 登出：`POST http://localhost:8071/api/user/logout`
- 关键业务结果：
  1. 未登录看板返回 HTTP `401`
  2. 管理员和普通用户登录业务码 `200`
  3. 看板接口业务码 `200`
  4. 普通用户完成开锁、还车和扣费，结束订单状态为 `3`
  5. 本轮真实抽测结果：`HTTP_SMOKE_OK rideId=3 amount=1.5`

### 5.4 前端代理联调

- 后端启动端口：`8071`
- 前端启动命令：`071-frontend/npm run dev -- --host 127.0.0.1 --port 3071`
- 结果：`通过`
- 关键结果：`POST http://127.0.0.1:3071/api/user/login` 返回业务码 `200`，角色为 `admin`

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- MySQL 兼容配置：`071-backend/src/main/resources/application-mysql.yml`
- MySQL 初始化脚本：`071-backend/sql/init.sql`
- 默认管理员：`admin / 123456`
- 默认运维人员：`operator / 123456`
- 默认普通用户：`user / 123456`
- 默认测试用户：`test / 123456`
- 说明：
  1. 直接执行 `071-backend/mvn spring-boot:run` 即可使用默认 H2 环境启动后端。
  2. H2 控制台地址为 `http://localhost:8071/h2-console`，JDBC URL 为 `jdbc:h2:mem:bike_system_071`。
  3. 如需切换 MySQL，请先确认 `sql/init.sql` 中的建库/删库语句可接受，再运行 `mysql -uroot -p1234 < sql/init.sql`。
  4. 当前 MySQL profile 默认使用 `root / 1234`，并启用 Redis。
  5. 前端执行 `npm run dev` 后默认访问 `http://localhost:3071`。

## 7. 备注

1. 本轮后端真实启动使用端口 `8071`。
2. 本机 `3306` 可用于 MySQL profile，但原始 SQL 脚本包含 `DROP DATABASE`，为避免破坏本机数据，默认验证采用 H2 自举链路。
3. `mvn` 输出中的 `rdc` profile 警告来自外部 Maven 环境，不影响本项目测试和启动结论。
4. `target/`、`node_modules/`、`dist/` 和临时日志等生成物不纳入提交。
