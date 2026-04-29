# 063 进销存管理系统检查报告

## 1. 检查结论

- 项目编号：`063`
- 项目名称：`进销存管理系统`
- 检查日期：`2026-04-29`
- 当前状态：`已完成`
- 综合结论：`本轮已修复默认环境强依赖 MySQL/Redis/8080、H2 自举缺失、MySQL 驱动旧坐标、PageHelper 固定 MySQL 方言、JWT 不兼容 Bearer token、Redis 不可用会阻断默认演示登录、未登录/越权仍返回 HTTP 200、用户密码响应泄露、前端代理仍指向 8080 等问题。当前后端可在默认 H2 环境下启动并通过自动化冒烟测试，前端可完成生产构建，真实 HTTP 核心链路抽测通过。MySQL profile 已按本机账号 `root / 1234` 保留。`

## 2. 项目形态

- 后端目录：`063-backend`
- 前端目录：`063-frontend`
- 后端技术栈：`Spring Boot 2.7.18 + MyBatis 2.3.1 + PageHelper 1.4.7 + JWT + H2/MySQL + Redis 可选`
- 前端技术栈：`Vue 3 + Vite 5 + Element Plus + Pinia + ECharts`
- 默认后端端口：`8063`
- 前端开发端口：`3063`

## 3. 本轮修复

### 3.1 默认运行环境修复

1. 将后端默认端口从 `8080` 调整为 `8063`，避免合集内项目端口冲突。
2. 默认 `application.yml` 改为 H2 内存库自举，新增 `schema-h2.sql` 与 `data-h2.sql`。
3. SQL 初始化显式指定 `UTF-8` 编码，避免中文演示数据乱码。
4. 新增 `application-mysql.yml`，保留 MySQL 部署模式，通过 `-Dspring-boot.run.profiles=mysql` 切换。
5. MySQL profile 默认密码按当前机器约定调整为 `root / 1234`。
6. 修复 MySQL 驱动坐标为 `com.mysql:mysql-connector-j:8.0.33`。
7. 项目编译目标改为 JDK 17，并增加 H2 与 Spring Boot Test 依赖。
8. PageHelper 默认方言改为 H2，MySQL profile 单独使用 MySQL 方言。
9. 默认演示环境使用本地 token 缓存兜底，MySQL profile 保留 Redis 扩展配置。

### 3.2 登录、鉴权与权限修复

1. `User.password` 改为 `WRITE_ONLY`，支持登录/新增写入但响应不再回传密码字段。
2. JWT 拦截器兼容 `Bearer` token。
3. JWT 拦截器兼容 `OPTIONS` 预检请求。
4. Redis token 缓存读写失败时不阻断默认演示登录、鉴权和退出。
5. 默认演示环境下登出后旧 token 会失效。
6. JWT 拦截器会重新读取用户状态，禁用或删除用户后旧 token 不再继续通过。
7. 未登录、token 无效和登录过期场景返回真实 HTTP `401`。
8. 普通业务员访问管理员接口返回真实 HTTP `403`。

### 3.3 前端修复

1. 前端 Vite 开发端口改为 `3063`。
2. 前端代理目标改为 `http://localhost:8063`。
3. 请求拦截器自动补齐 `Bearer` token。
4. HTTP `401` 响应会清理本地登录态并跳转登录页。
5. 新增前端 `package-lock.json`，保证依赖安装可复现。

### 3.4 文档与测试修复

1. 新增后端冒烟测试 `InventoryManagementApplicationSmokeTest`。
2. 新增 `063-backend/README.md`，补齐默认 H2 启动、MySQL profile、默认账号、前端端口和验证命令。
3. 新增 `063-backend/启动说明.txt`，便于答辩和本地演示。
4. 新增本巡检报告，并同步更新总台账。

## 4. 当前实现与目标能力对照

### 4.1 已具备能力

1. 后端默认 H2 自举，可直接执行 `mvn spring-boot:run` 启动。
2. 管理员和业务员账号可登录。
3. 未登录访问受保护接口返回 HTTP `401`。
4. 业务员访问用户管理返回 HTTP `403`。
5. 管理员可访问用户管理和公告管理接口。
6. 管理员与业务员可访问看板、供应商、客户、分类、商品、采购、销售和库存流水接口。
7. 采购单审核会增加商品库存并生成采购入库流水。
8. 销售单审核会扣减商品库存并生成销售出库流水。
9. 销售审核会校验库存不足场景，避免负库存。
10. 前端登录、代理和鉴权请求头已与后端主链路对齐。

### 4.2 仍有差距

1. 密码仍为明文存储，生产环境建议改为 BCrypt。
2. Redis 配置仍保留为生产扩展项，默认演示链路不依赖 Redis。
3. 采购单与销售单目前是一单一商品模型，尚未扩展为多明细行单据。
4. 库存未做并发扣减锁，生产环境高并发出库需增加乐观锁或数据库事务约束。
5. 报表导出、库存盘点、审批流和消息通知仍可继续增强。
6. 前端构建存在 Vite chunk 体积提示，当前不影响构建产物生成。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`063-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0, Skipped: 0`
- 覆盖内容：
  1. 默认 H2 上下文可启动
  2. SQL 初始化中文数据为 UTF-8
  3. 未登录访问看板返回 HTTP `401`
  4. 管理员和业务员登录成功且密码字段不回传
  5. 业务员访问用户管理返回 HTTP `403`
  6. 管理员访问用户管理成功
  7. 业务员访问看板、供应商、客户、分类和商品接口成功
  8. 管理员发布公告成功
  9. 业务员创建并审核采购单后库存增加
  10. 业务员创建并审核销售单后库存减少
  11. 库存不足销售审核返回错误并包含库存不足提示
  12. 管理员登出后旧 token 返回 HTTP `401`

### 5.2 前端构建

- 执行命令：`063-frontend/npm run build`
- 结果：`通过`
- 备注：Vite 输出 CJS API 过时提示和 chunk 体积提示，不影响构建产物生成。

### 5.3 启动与接口抽测

- 后端启动命令：`063-backend/mvn spring-boot:run`
- 结果：`通过`
- 抽测地址：
  1. 管理员登录：`POST http://localhost:8063/api/auth/login`
  2. 业务员登录：`POST http://localhost:8063/api/auth/login`
  3. 未登录看板：`GET http://localhost:8063/api/dashboard/stats`
  4. 业务员越权用户管理：`GET http://localhost:8063/api/user/page`
  5. 创建采购单：`POST http://localhost:8063/api/purchase`
  6. 审核采购单：`PUT http://localhost:8063/api/purchase/approve/{id}`
  7. 创建销售单：`POST http://localhost:8063/api/sale`
  8. 审核销售单：`PUT http://localhost:8063/api/sale/approve/{id}`
- 关键业务结果：
  1. 未登录看板返回 HTTP `401`
  2. 管理员登录业务码 `200`，登录响应未包含 `password` 字段
  3. 业务员访问用户管理返回 HTTP `403`
  4. 业务员看板业务码 `200`
  5. 采购审核后商品库存从 `51` 增加到 `53`
  6. 销售审核后商品库存从 `53` 减少到 `52`
  7. 管理员登出业务码 `200`，旧 token 再访问用户信息返回 HTTP `401`
  8. 联调结束后 `8063` 无 LISTENING 残留端口

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- MySQL 兼容配置：`063-backend/src/main/resources/application-mysql.yml`
- MySQL 初始化脚本：`063-backend/sql/init.sql`
- 默认管理员：`admin / 123456`
- 默认业务员：`staff / 123456`
- 说明：
  1. 直接执行 `063-backend/mvn spring-boot:run` 即可使用默认 H2 环境启动后端。
  2. H2 控制台地址为 `http://localhost:8063/h2-console`，JDBC URL 为 `jdbc:h2:mem:inventory_system`。
  3. 如需切换 MySQL，请先确认 `sql/init.sql` 中的 `DROP DATABASE` 可接受，再运行 `mysql -uroot -p1234 < sql/init.sql`。
  4. 当前 MySQL profile 默认使用 `root / 1234`。
  5. 前端执行 `npm run dev` 后默认访问 `http://localhost:3063`。

## 7. 备注

1. 本轮后端真实启动使用端口 `8063`。
2. 本机 `3306` 可用于 MySQL profile，但原始 SQL 脚本包含 `DROP DATABASE`，为避免破坏本机数据，默认验证采用 H2 自举链路。
3. `mvn` 输出中的 `rdc` profile 警告来自外部 Maven 环境，不影响本项目测试和启动结论。
4. `target/`、`node_modules/`、`dist/` 和 `.tmp/` 临时日志等生成物不纳入提交。
