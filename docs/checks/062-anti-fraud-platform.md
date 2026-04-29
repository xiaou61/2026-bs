# 062 反欺诈平台检查报告

## 1. 检查结论

- 项目编号：`062`
- 项目名称：`反欺诈平台`
- 检查日期：`2026-04-29`
- 当前状态：`已完成`
- 综合结论：`本轮已修复默认环境强依赖 MySQL/Redis/8080、H2 自举缺失、SQL 初始化中文编码、MySQL 驱动旧坐标、JWT 不兼容 Bearer token、Redis 不可用会阻断默认演示登录、未登录/越权仍返回 HTTP 200、用户密码响应泄露、前端代理仍指向 8080 等问题。当前后端可在默认 H2 环境下启动并通过自动化冒烟测试，前端可完成生产构建，真实 HTTP 核心链路抽测通过。MySQL profile 已按本机账号 `root / 1234` 保留。`

## 2. 项目形态

- 后端目录：`062-backend`
- 前端目录：`062-frontend`
- 后端技术栈：`Spring Boot 2.7.18 + MyBatis-Plus 3.5.5 + JWT + H2/MySQL + Redis 可选`
- 前端技术栈：`Vue 3 + Vite 5 + Element Plus + Pinia + ECharts`
- 默认后端端口：`8062`
- 前端开发端口：`3062`

## 3. 本轮修复

### 3.1 默认运行环境修复

1. 将后端默认端口从 `8080` 调整为 `8062`，避免合集内项目端口冲突。
2. 默认 `application.yml` 改为 H2 内存库自举，新增 `schema-h2.sql` 与 `data-h2.sql`。
3. SQL 初始化显式指定 `UTF-8` 编码，避免中文演示数据乱码。
4. 新增 `application-mysql.yml`，保留 MySQL 部署模式，通过 `-Dspring-boot.run.profiles=mysql` 切换。
5. MySQL profile 默认密码按当前机器约定调整为 `root / 1234`。
6. 修复 MySQL 驱动坐标为 `com.mysql:mysql-connector-j:8.0.33`。
7. 增加 H2 与 Spring Boot Test 依赖。
8. 默认演示环境使用本地 token 缓存兜底，MySQL profile 保留 Redis 扩展配置。

### 3.2 登录、鉴权与权限修复

1. `User.password` 改为 `WRITE_ONLY`，支持登录/新增写入但响应不再回传密码字段。
2. JWT 拦截器兼容 `Bearer` token。
3. JWT 拦截器兼容 `OPTIONS` 预检请求。
4. Redis token 缓存读写失败时不阻断默认演示登录、鉴权和退出。
5. 默认演示环境下登出后旧 token 会失效。
6. 未登录、token 无效和登录过期场景返回真实 HTTP `401`。
7. 普通用户访问管理员或风控接口返回真实 HTTP `403`。

### 3.3 前端修复

1. 前端 Vite 开发端口改为 `3062`。
2. 前端代理目标改为 `http://localhost:8062`。
3. 请求拦截器自动补齐 `Bearer` token。
4. 新增前端 `package-lock.json`，保证依赖安装可复现。

### 3.4 文档与测试修复

1. 新增后端冒烟测试 `AntiFraudPlatformApplicationSmokeTest`。
2. 新增 `062-backend/README.md`，补齐默认 H2 启动、MySQL profile、默认账号、前端端口和验证命令。
3. 新增 `062-backend/启动说明.txt`，便于答辩和本地演示。
4. 新增本巡检报告，并同步更新总台账。

## 4. 当前实现与目标能力对照

### 4.1 已具备能力

1. 后端默认 H2 自举，可直接执行 `mvn spring-boot:run` 启动。
2. 管理员、风控分析员和商户用户三类账号可登录。
3. 未登录访问受保护接口返回 HTTP `401`。
4. 普通用户访问用户管理返回 HTTP `403`。
5. 管理员可访问用户、公告、操作日志和看板接口。
6. 风控分析员可访问黑名单、规则、事件、预警、案件、申诉和看板接口。
7. 商户用户可上报风险事件、查看自己的事件和预警、提交申诉。
8. 风险事件上报会联动黑名单和规则计算风险分，并自动生成高风险预警。
9. 风控分析员可指派和处置预警，可创建案件并回复申诉。
10. 前端登录、代理和鉴权请求头已与后端主链路对齐。

### 4.2 仍有差距

1. 密码仍为明文存储，生产环境建议改为 BCrypt。
2. Redis 配置仍保留为生产扩展项，默认演示链路不依赖 Redis。
3. 风险评分仍为规则 + 黑名单的简化模型，尚未接入机器学习或实时流式风控。
4. 设备指纹、地理位置、行为序列等复杂反欺诈特征仍需后续增强。
5. 报表导出、告警通知和审计追踪可继续细化。
6. 前端构建存在 Vite chunk 体积提示，当前不影响构建产物生成。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`062-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0, Skipped: 0`
- 覆盖内容：
  1. 默认 H2 上下文可启动
  2. SQL 初始化中文数据为 UTF-8
  3. 未登录访问看板返回 HTTP `401`
  4. 管理员、风控分析员和商户用户登录成功且密码字段不回传
  5. 普通用户访问用户管理返回 HTTP `403`
  6. 管理员访问用户管理成功
  7. 风控分析员访问看板、黑名单和规则接口成功
  8. 商户上报高风险事件，命中黑名单和金额规则并自动生成预警
  9. 风控分析员指派、处置预警并创建案件成功
  10. 商户提交申诉、风控分析员回复申诉成功
  11. 管理员发布公告、查看操作日志成功
  12. 管理员登出后旧 token 返回 HTTP `401`

### 5.2 前端构建

- 执行命令：`062-frontend/npm run build`
- 结果：`通过`
- 备注：Vite 输出 CJS API 过时提示和 chunk 体积提示，不影响构建产物生成。

### 5.3 启动与接口抽测

- 后端启动命令：`062-backend/mvn spring-boot:run`
- 结果：`通过`
- 抽测地址：
  1. 管理员登录：`POST http://localhost:8062/api/auth/login`
  2. 商户登录：`POST http://localhost:8062/api/auth/login`
  3. 管理员看板：`GET http://localhost:8062/api/dashboard/stats`
  4. 商户上报风险事件：`POST http://localhost:8062/api/event/report`
  5. 商户预警列表：`GET http://localhost:8062/api/alert/my`
  6. 普通用户越权用户管理：`GET http://localhost:8062/api/user/page`
- 关键业务结果：
  1. 管理员登录业务码 `200`，角色返回 `ADMIN`
  2. 登录响应未包含 `password` 字段
  3. 管理员看板业务码 `200`
  4. 高风险事件上报业务码 `200`
  5. 风险等级返回 `HIGH`，风险分返回 `145`
  6. 商户预警列表返回 `2` 条记录
  7. 普通用户访问用户管理返回 HTTP `403`
  8. 管理员登出业务码 `200`，旧 token 再访问用户信息返回 HTTP `401`
  9. 联调结束后 `8062` 无 LISTENING 残留端口

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- MySQL 兼容配置：`062-backend/src/main/resources/application-mysql.yml`
- MySQL 初始化脚本：`062-backend/sql/init.sql`
- 默认管理员：`admin / 123456`
- 默认风控分析员：`analyst / 123456`
- 默认商户用户：`user1 / 123456`
- 默认商户用户：`user2 / 123456`
- 说明：
  1. 直接执行 `062-backend/mvn spring-boot:run` 即可使用默认 H2 环境启动后端。
  2. H2 控制台地址为 `http://localhost:8062/h2-console`，JDBC URL 为 `jdbc:h2:mem:anti_fraud`。
  3. 如需切换 MySQL，请先确认 `sql/init.sql` 中的 `DROP DATABASE` 可接受，再运行 `mysql -uroot -p1234 < sql/init.sql`。
  4. 当前 MySQL profile 默认使用 `root / 1234`。
  5. 前端执行 `npm run dev` 后默认访问 `http://localhost:3062`。

## 7. 备注

1. 本轮后端真实启动使用端口 `8062`。
2. 本机 `3306` 存在 MySQL 监听，但原始 SQL 脚本包含 `DROP DATABASE`，为避免破坏本机数据，默认验证采用 H2 自举链路。
3. `mvn` 输出中的 `rdc` profile 警告来自外部 Maven 环境，不影响本项目测试和启动结论。
4. `target/`、`node_modules/`、`dist/` 和 `.tmp/` 临时日志等生成物不纳入提交。
