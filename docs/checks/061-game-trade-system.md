# 061 游戏交易系统检查报告

## 1. 检查结论

- 项目编号：`061`
- 项目名称：`游戏交易系统`
- 检查日期：`2026-04-29`
- 当前状态：`已完成`
- 综合结论：`本轮已修复默认环境强依赖 MySQL/Redis/8080、H2 自举缺失、MySQL 驱动旧坐标、分页方言固定 MySQL、JWT 不兼容 Bearer token、Redis 不可用会阻断默认演示登录、未登录/越权仍返回 HTTP 200、用户密码响应泄露、前端代理仍指向 8080 等问题。当前后端可在默认 H2 环境下启动并通过自动化冒烟测试，前端可完成生产构建，真实 HTTP 核心链路抽测通过。MySQL profile 已按本机账号 `root / 1234` 保留。`

## 2. 项目形态

- 后端目录：`061-backend`
- 前端目录：`061-frontend`
- 后端技术栈：`Spring Boot 2.7.18 + MyBatis-Plus 3.5.5 + JWT + H2/MySQL + Redis 可选`
- 前端技术栈：`Vue 3 + Vite 5 + Element Plus + Pinia + ECharts`
- 默认后端端口：`8061`
- 前端开发端口：`3061`

## 3. 本轮修复

### 3.1 默认运行环境修复

1. 将后端默认端口从 `8080` 调整为 `8061`，避免合集内项目端口冲突。
2. 默认 `application.yml` 改为 H2 内存库自举，新增 `schema-h2.sql` 与 `data-h2.sql`。
3. 新增 `application-mysql.yml`，保留 MySQL 部署模式，通过 `-Dspring-boot.run.profiles=mysql` 切换。
4. MySQL profile 默认密码按当前机器约定调整为 `root / 1234`。
5. 修复 MySQL 驱动坐标为 `com.mysql:mysql-connector-j:8.0.33`。
6. 增加 H2 与 Spring Boot Test 依赖。
7. MyBatis-Plus 分页插件改为自动识别数据库方言，兼容默认 H2 与 MySQL profile。
8. 将演示订单和公告日期更新到 2026 年 4 月/5 月，避免样例数据明显过期。

### 3.2 登录、鉴权与权限修复

1. `User.password` 改为 `WRITE_ONLY`，支持登录/新增写入但响应不再回传密码字段。
2. JWT 拦截器兼容 `Bearer` token。
3. JWT 拦截器兼容 `OPTIONS` 预检请求。
4. JWT 拦截器补充 token 对应用户存在和启用状态校验。
5. Redis token 缓存读写失败时不阻断默认演示登录、鉴权和退出。
6. 未登录、token 无效和账号禁用场景返回真实 HTTP `401`。
7. 普通用户访问管理员接口返回真实 HTTP `403`。
8. 商品、订单、评价等归属校验中的无权限场景统一返回 HTTP `403`。

### 3.3 前端修复

1. 前端 Vite 开发端口改为 `3061`。
2. 前端代理目标改为 `http://localhost:8061`。
3. 请求拦截器自动补齐 `Bearer` token。
4. 新增前端 `package-lock.json`，保证依赖安装可复现。

### 3.4 文档与测试修复

1. 新增后端冒烟测试 `GameTradeApplicationSmokeTest`。
2. 新增 `061-backend/README.md`，补齐默认 H2 启动、MySQL profile、默认账号、前端端口和验证命令。
3. 新增 `061-backend/启动说明.txt`，便于答辩和本地演示。
4. 新增本巡检报告，并同步更新总台账。

## 4. 当前实现与目标能力对照

### 4.1 已具备能力

1. 后端默认 H2 自举，可直接执行 `mvn spring-boot:run` 启动。
2. 默认管理员和普通用户账号可登录。
3. 未登录访问受保护接口返回 HTTP `401`。
4. 普通用户访问用户管理返回 HTTP `403`。
5. 管理员可访问用户、分类、商品、订单、评论、申诉、公告和看板接口。
6. 普通用户可浏览商品、收藏商品、创建订单、支付本人订单、查看买入/卖出订单、完成订单、评价和发起申诉。
7. 卖家可对自己的订单发货。
8. 管理员可回复申诉并恢复订单状态。
9. 前端登录、代理和鉴权请求头已与后端主链路对齐。

### 4.2 仍有差距

1. 密码仍为明文存储，生产环境建议改为 BCrypt。
2. Redis 配置仍保留为生产扩展项，默认演示链路不依赖 Redis。
3. 支付仍为余额模拟，尚未对接真实支付渠道。
4. 虚拟商品交付仍为状态流转，自动发货和验号流程可继续增强。
5. 上传文件为本地目录存储，生产环境建议接入对象存储和鉴权下载策略。
6. 前端构建存在 Vite chunk 体积提示，当前不影响构建产物生成。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`061-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0, Skipped: 0`
- 覆盖内容：
  1. 默认 H2 上下文可启动
  2. 未登录访问用户信息返回 HTTP `401`
  3. 管理员、卖家、买家登录成功且密码字段不回传
  4. 普通用户访问用户管理返回 HTTP `403`
  5. 管理员访问看板、分类、订单、评论、申诉和公告接口成功
  6. 普通用户访问分类、商品、收藏和公告接口成功
  7. 买家创建订单、支付、查看我的订单成功
  8. 卖家查看售出订单并发货成功
  9. 买家确认完成、评价和发起申诉成功
  10. 管理员回复申诉和删除评论成功

### 5.2 前端构建

- 执行命令：`061-frontend/npm run build`
- 结果：`通过`
- 备注：Vite 输出 CJS API 过时提示和 chunk 体积提示，不影响构建产物生成。

### 5.3 启动与接口抽测

- 后端启动命令：`061-backend/mvn spring-boot:run`
- 结果：`通过`
- 抽测地址：
  1. 管理员登录：`POST http://localhost:8061/api/auth/login`
  2. 买家登录：`POST http://localhost:8061/api/auth/login`
  3. 商品详情：`GET http://localhost:8061/api/item/1`
  4. 市场列表：`GET http://localhost:8061/api/item/list`
  5. 买家下单：`POST http://localhost:8061/api/order`
  6. 普通用户越权用户管理：`GET http://localhost:8061/api/user/page`
  7. 管理员看板：`GET http://localhost:8061/api/dashboard/stats`
- 关键业务结果：
  1. 管理员和买家登录均成功
  2. 商品详情返回 `王者荣耀 国服百星号`
  3. 市场商品总数返回 `8`
  4. 买家创建订单返回业务码 `200`
  5. 普通用户访问用户管理返回 HTTP `403`
  6. 管理员看板返回用户数 `4`、商品数 `8`
  7. 联调结束后 `8061` 无 LISTENING 残留端口

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- MySQL 兼容配置：`061-backend/src/main/resources/application-mysql.yml`
- MySQL 初始化脚本：`061-backend/sql/init.sql`
- 默认管理员：`admin / 123456`
- 默认普通用户：`user1 / 123456`
- 默认普通用户：`user2 / 123456`
- 默认普通用户：`user3 / 123456`
- 说明：
  1. 直接执行 `061-backend/mvn spring-boot:run` 即可使用默认 H2 环境启动后端。
  2. H2 控制台地址为 `http://localhost:8061/h2-console`，JDBC URL 为 `jdbc:h2:mem:game_trade`。
  3. 如需切换 MySQL，请先确认 `sql/init.sql` 中的 `DROP DATABASE` 可接受，再运行 `mysql -uroot -p1234 < sql/init.sql`。
  4. 当前 MySQL profile 默认使用 `root / 1234`。
  5. 前端执行 `npm run dev` 后默认访问 `http://localhost:3061`。

## 7. 备注

1. 本轮后端真实启动使用端口 `8061`。
2. 本机 `3306` 存在 MySQL 监听，但 `mysql` CLI 未在 PATH 中；同时原始 SQL 脚本包含 `DROP DATABASE`，为避免破坏本机数据，默认验证采用 H2 自举链路。
3. `mvn` 输出中的 `rdc` profile 警告来自外部 Maven 环境，不影响本项目测试和启动结论。
4. `target/`、`node_modules/`、`dist/` 和 `.tmp/` 临时日志等生成物不纳入提交。
