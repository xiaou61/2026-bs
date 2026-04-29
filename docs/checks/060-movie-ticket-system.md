# 060 电影订票及评论网站检查报告

## 1. 检查结论

- 项目编号：`060`
- 项目名称：`电影订票及评论网站`
- 检查日期：`2026-04-29`
- 当前状态：`已完成`
- 综合结论：`本轮已修复 Maven 依赖不可解析、默认环境强依赖 MySQL/Redis/8080、H2 自举缺失、JWT 不兼容 Bearer token、未登录/越权仍返回 HTTP 200、用户密码响应泄露、订单可被其他用户操作、前端代理仍指向 8080、演示排片日期过期等问题。当前后端可在默认 H2 环境下启动并通过自动化冒烟测试，前端可完成生产构建，真实 HTTP 核心链路抽测通过。MySQL profile 已按本机账号 `root / 1234` 保留。`

## 2. 项目形态

- 后端目录：`060-backend`
- 前端目录：`060-frontend`
- 后端技术栈：`Spring Boot 2.7.18 + MyBatis + PageHelper + JWT + H2/MySQL + Redis 可选`
- 前端技术栈：`Vue 3 + Vite 5 + Element Plus + Pinia + ECharts`
- 默认后端端口：`8060`
- 前端开发端口：`3060`

## 3. 本轮修复

### 3.1 默认运行环境修复

1. 将后端默认端口从 `8080` 调整为 `8060`，避免合集内项目端口冲突。
2. 默认 `application.yml` 改为 H2 内存库自举，新增 `schema-h2.sql` 与 `data-h2.sql`。
3. 新增 `application-mysql.yml`，保留 MySQL 部署模式，通过 `-Dspring-boot.run.profiles=mysql` 切换。
4. MySQL profile 默认密码按当前机器约定调整为 `root / 1234`。
5. 修复 MySQL 驱动坐标为 `com.mysql:mysql-connector-j:8.0.33`。
6. 将 MyBatis Spring Boot Starter 版本修正为 `2.3.2`，兼容 Spring Boot 2.7。
7. 增加 H2 与 Spring Boot Test 依赖。
8. PageHelper 默认方言改为 `h2`，MySQL profile 保持 `mysql`。
9. 新增 MyBatis databaseId 配置，为 H2 适配排片日期和订单趋势 SQL。
10. 将演示排片日期更新到 2026 年 5 月，避免当前日期 `2026-04-29` 下普通用户查不到可购场次。

### 3.2 登录、鉴权与权限修复

1. `User.password` 改为 `WRITE_ONLY`，支持登录/新增写入但响应不再回传密码字段。
2. JWT 拦截器兼容 `Bearer` token。
3. JWT 拦截器兼容 `OPTIONS` 预检请求。
4. JWT 拦截器补充 token 对应用户存在和启用状态校验。
5. 未登录、token 无效和账号禁用场景直接返回真实 HTTP `401`。
6. 普通用户访问用户管理和数据看板接口返回真实 HTTP `403`。
7. 管理员可访问全部管理接口，普通用户仅可访问电影浏览、排片、评论发布、收藏、个人订单等用户侧接口。
8. 订单支付和取消补充归属校验，普通用户不能操作他人订单。
9. 订单创建补充必要字段和余座校验，库存扣减失败时事务回滚。

### 3.3 前端修复

1. 前端 Vite 开发端口改为 `3060`。
2. 前端代理目标改为 `http://localhost:8060`。
3. 请求拦截器自动补齐 `Bearer` token。
4. 普通用户登录后跳转电影浏览页，管理员登录后跳转数据看板。
5. 普通用户菜单隐藏管理员数据看板入口，并补充路由角色兜底拦截。
6. 新增前端 `package-lock.json`，保证依赖安装可复现。

### 3.4 文档与测试修复

1. 新增后端冒烟测试 `MovieApplicationSmokeTest`。
2. 新增 `060-backend/README.md`，补齐默认 H2 启动、MySQL profile、默认账号、前端端口和验证命令。
3. 新增 `060-backend/启动说明.txt`，便于答辩和本地演示。
4. 新增本巡检报告，并同步更新总台账。

## 4. 当前实现与目标能力对照

### 4.1 已具备能力

1. 后端默认 H2 自举，可直接执行 `mvn spring-boot:run` 启动。
2. 默认管理员和普通用户账号可登录。
3. 未登录访问受保护接口返回 HTTP `401`。
4. 普通用户访问用户管理和数据看板返回 HTTP `403`。
5. 管理员可访问用户、电影分类、电影、影院、影厅、排片、订单、评论、公告和看板接口。
6. 普通用户可浏览电影、查询未来排片、收藏电影、创建订单、支付本人订单、查看本人订单和发表评论。
7. 用户密码字段不随登录和用户信息响应回传。
8. 前端登录、代理、请求头和角色跳转已与后端主链路对齐。

### 4.2 仍有差距

1. 密码仍为明文存储，生产环境建议改为 BCrypt。
2. Redis 配置仍保留为生产扩展项，默认演示链路不依赖 Redis。
3. 当前在线选座为文本座位输入，尚未实现可视化座位图和座位锁定。
4. 支付仍为状态流转模拟，尚未对接真实支付渠道。
5. 评论审核能力较简化，目前主要支持管理员删除。
6. 前端构建存在 Vite chunk 体积提示，当前不影响构建产物生成。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`060-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0, Skipped: 0`
- 覆盖内容：
  1. 默认 H2 上下文可启动
  2. 未登录访问用户信息返回 HTTP `401`
  3. 管理员和普通用户登录成功且密码字段不回传
  4. 普通用户访问用户管理和数据看板返回 HTTP `403`
  5. 管理员访问看板、用户、影厅、排片、订单和评论接口成功
  6. 普通用户访问电影分类、电影、影院、排片、公告、收藏和评论接口成功
  7. 普通用户创建订单、支付本人订单成功
  8. 普通用户支付他人订单返回 HTTP `403`
  9. 管理员完成订单和删除评论成功

### 5.2 前端构建

- 执行命令：`060-frontend/npm run build`
- 结果：`通过`
- 备注：Vite 输出 CJS API 过时提示和 chunk 体积提示，不影响构建产物生成。

### 5.3 启动与接口抽测

- 后端启动命令：`060-backend/mvn spring-boot:run`
- 结果：`通过`
- 抽测地址：
  1. 管理员登录：`POST http://localhost:8060/api/auth/login`
  2. 普通用户登录：`POST http://localhost:8060/api/auth/login`
  3. 电影详情：`GET http://localhost:8060/api/movie/1`
  4. 电影排片：`GET http://localhost:8060/api/showtime/movie/1`
  5. 用户下单：`POST http://localhost:8060/api/order`
  6. 普通用户越权用户管理：`GET http://localhost:8060/api/user/page`
  7. 管理员看板：`GET http://localhost:8060/api/dashboard/stats`
- 关键业务结果：
  1. 管理员和普通用户登录均成功
  2. 电影详情返回 `星际迷航：新纪元`
  3. 电影 `1` 返回未来排片数量 `2`
  4. 普通用户创建订单返回业务码 `200`
  5. 普通用户访问用户管理返回 HTTP `403`
  6. 管理员看板返回用户数 `3`、电影数 `6`
  7. 联调结束后 `8060` 无 LISTENING 残留端口

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- MySQL 兼容配置：`060-backend/src/main/resources/application-mysql.yml`
- MySQL 初始化脚本：`060-backend/sql/init.sql`
- 默认管理员：`admin / 123456`
- 默认普通用户：`user1 / 123456`
- 默认普通用户：`user2 / 123456`
- 说明：
  1. 直接执行 `060-backend/mvn spring-boot:run` 即可使用默认 H2 环境启动后端。
  2. H2 控制台地址为 `http://localhost:8060/h2-console`，JDBC URL 为 `jdbc:h2:mem:movie_ticket`。
  3. 如需切换 MySQL，请先确认 `sql/init.sql` 中的 `DROP DATABASE` 可接受，再运行 `mysql -uroot -p1234 < sql/init.sql`。
  4. 当前 MySQL profile 默认使用 `root / 1234`。
  5. 前端执行 `npm run dev` 后默认访问 `http://localhost:3060`。

## 7. 备注

1. 本轮后端真实启动使用端口 `8060`。
2. 本机 `3306` 存在 MySQL 监听，但 `mysql` CLI 未在 PATH 中；同时原始 SQL 脚本包含 `DROP DATABASE`，为避免破坏本机数据，默认验证采用 H2 自举链路。
3. `mvn` 输出中的 `rdc` profile 警告来自外部 Maven 环境，不影响本项目测试和启动结论。
4. `target/`、`node_modules/`、`dist/` 和 `.tmp/` 临时日志等生成物不纳入提交。
