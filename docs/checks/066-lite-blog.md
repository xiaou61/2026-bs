# 066 精简博客系统检查报告

## 1. 检查结论

- 项目编号：`066`
- 项目名称：`精简博客系统`
- 检查日期：`2026-04-30`
- 当前状态：`已完成`
- 综合结论：`本轮已修复默认环境强依赖 MySQL/Redis/8080、H2 自举缺失、MySQL 驱动旧坐标、JWT 不兼容 Bearer token、Redis 不可用会阻断默认演示登录、未登录/越权仍返回 HTTP 200、用户密码响应泄露、H2 下文章趋势统计 SQL 别名冲突、前端代理仍指向 8080 等问题。当前后端可在默认 H2 环境下启动并通过自动化冒烟测试，前端可完成生产构建，真实 HTTP 核心链路抽测通过。MySQL profile 已按本机账号 `root / 1234` 保留。`

## 2. 项目形态

- 后端目录：`066-backend`
- 前端目录：`066-frontend`
- 后端技术栈：`Spring Boot 2.7.18 + MyBatis-Plus 3.5.5 + JWT + H2/MySQL + Redis 可选 + Hutool`
- 前端技术栈：`Vue 3 + Vite 5 + Element Plus + Pinia + Axios + ECharts`
- 默认后端端口：`8066`
- 前端开发端口：`3066`

## 3. 本轮修复

### 3.1 默认运行环境修复

1. 将后端默认端口从 `8080` 调整为 `8066`，避免合集内项目端口冲突。
2. 默认 `application.yml` 改为 H2 内存库自举，新增 `schema-h2.sql` 与 `data-h2.sql`。
3. SQL 初始化显式指定 `UTF-8` 编码，避免中文演示数据乱码。
4. 新增 `application-mysql.yml`，保留 MySQL 部署模式，通过 `-Dspring-boot.run.profiles=mysql` 切换。
5. MySQL profile 默认密码按当前机器约定调整为 `root / 1234`。
6. 修复 MySQL 驱动坐标为 `com.mysql:mysql-connector-j:8.0.33`。
7. 项目编译目标改为 JDK 17，并增加 H2 与 Spring Boot Test 依赖。
8. MyBatis-Plus 分页拦截器不再固定 MySQL 方言，兼容默认 H2 演示环境。
9. 默认演示环境使用本地 token 缓存兜底，MySQL profile 保留 Redis 扩展配置。

### 3.2 登录、鉴权与权限修复

1. `User.password` 改为 `WRITE_ONLY`，支持登录/新增写入但响应不再回传密码字段。
2. JWT 拦截器兼容 `Bearer` token。
3. 默认演示环境下登出后旧 token 会失效。
4. JWT 拦截器会重新读取用户状态，禁用或删除用户后旧 token 不再继续通过。
5. 未登录、token 无效和登录过期场景返回真实 HTTP `401`。
6. 普通用户访问用户管理等管理接口返回真实 HTTP `403`。

### 3.3 业务与 SQL 兼容修复

1. 新增 H2 版初始化数据，覆盖管理员、普通用户、作者、分类、标签、文章、评论和公告。
2. 公开文章分页、文章详情、公开评论和公告列表可在默认 H2 环境直接访问。
3. 管理员可访问用户、分类、标签、公告、评论审核和看板接口。
4. 普通用户可发布文章、查看本人文章并发表评论。
5. 管理员可置顶文章、审核评论并查看看板趋势。
6. 文章趋势统计别名从 `day` 调整为 `biz_day`，避免 H2 保留字冲突。

### 3.4 前端修复

1. 前端 Vite 开发端口改为 `3066`。
2. 前端代理目标改为 `http://localhost:8066`。
3. 请求拦截器自动补齐 `Bearer` token。
4. HTTP `401` 响应会清理本地登录态并跳转登录页。
5. 新增前端 `package-lock.json`，保证依赖安装可复现。

### 3.5 文档与测试修复

1. 新增后端冒烟测试 `LiteBlogApplicationSmokeTest`。
2. 新增 `066-backend/README.md`，补齐默认 H2 启动、MySQL profile、默认账号、前端端口和验证命令。
3. 新增 `066-backend/启动说明.txt`，便于答辩和本地演示。
4. 新增本巡检报告，并同步更新总台账。

## 4. 当前实现与目标能力对照

### 4.1 已具备能力

1. 后端默认 H2 自举，可直接执行 `mvn spring-boot:run` 启动。
2. 管理员、普通用户和作者账号可登录。
3. 登录响应不回传 `password` 字段。
4. 未登录访问受保护接口返回 HTTP `401`。
5. 普通用户访问用户管理返回 HTTP `403`。
6. 管理员可访问用户管理、分类、标签、公告、评论审核和看板接口。
7. 普通用户可发布文章、查看本人文章并发表评论。
8. 公开文章分页、详情、评论和公告接口可正常访问。
9. 管理员可置顶文章并审核评论。
10. 看板统计和 7 天文章趋势接口可正常返回数据。
11. 登出后旧 token 失效，重新访问用户信息返回 HTTP `401`。
12. 前端登录、代理和鉴权请求头已与后端主链路对齐。

### 4.2 仍有差距

1. 密码仍为明文存储，生产环境建议改为 BCrypt。
2. Redis 配置仍保留为生产扩展项，默认演示链路不依赖 Redis。
3. 文章内容编辑仍偏基础，缺少富文本图片上传、草稿版本和审计日志。
4. 评论审核缺少敏感词、举报、通知和批量处理能力。
5. 看板统计仍为基础聚合，缺少更完整的访问来源、阅读时长和用户增长分析。
6. 前端构建存在 Vite chunk 体积提示，当前不影响构建产物生成。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`066-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0, Skipped: 0`
- 覆盖内容：
  1. 默认 H2 上下文可启动
  2. SQL 初始化中文数据为 UTF-8
  3. 公开文章分页、详情、评论和公告接口成功
  4. 未登录访问看板返回 HTTP `401`
  5. 管理员和普通用户登录成功且密码字段不回传
  6. 普通用户访问用户管理返回 HTTP `403`
  7. 用户管理、分类、标签、公告和看板接口成功
  8. 管理员发布公告成功
  9. 普通用户发布文章成功
  10. 管理员置顶文章成功
  11. 普通用户发表评论成功
  12. 管理员审核评论成功
  13. 看板趋势接口成功
  14. 普通用户登出后旧 token 返回 HTTP `401`

### 5.2 前端构建

- 执行命令：`066-frontend/npm run build`
- 结果：`通过`
- 备注：Vite 输出 CJS API 过时提示和 chunk 体积提示，不影响构建产物生成。

### 5.3 启动与接口抽测

- 后端启动命令：`066-backend/mvn spring-boot:run`
- 结果：`通过`
- 抽测地址：
  1. 公开文章分页：`GET http://localhost:8066/api/post/public-page`
  2. 文章详情：`GET http://localhost:8066/api/post/detail/{id}`
  3. 公开评论：`GET http://localhost:8066/api/comment/post-page`
  4. 公告列表：`GET http://localhost:8066/api/notice/list`
  5. 未登录看板：`GET http://localhost:8066/api/dashboard/stats`
  6. 管理员登录：`POST http://localhost:8066/api/auth/login`
  7. 普通用户登录：`POST http://localhost:8066/api/auth/login`
  8. 普通用户越权用户管理：`GET http://localhost:8066/api/user/page`
  9. 分类、标签、用户和看板查询接口
  10. 管理员发布公告：`POST http://localhost:8066/api/notice`
  11. 普通用户发文：`POST http://localhost:8066/api/post`
  12. 管理员置顶文章：`PUT http://localhost:8066/api/post/top`
  13. 普通用户发表评论：`POST http://localhost:8066/api/comment`
  14. 管理员审核评论：`PUT http://localhost:8066/api/comment/review`
  15. 普通用户登出：`POST http://localhost:8066/api/auth/logout`
- 关键业务结果：
  1. 未登录看板返回 HTTP `401`
  2. 管理员、普通用户登录业务码 `200`，登录响应未包含 `password` 字段
  3. 普通用户访问用户管理返回 HTTP `403`
  4. 公开文章、详情、评论、公告、分类、标签和看板查询均返回业务码 `200`
  5. 管理员发布公告成功
  6. 普通用户发文成功，抽测文章 ID 为 `4`
  7. 管理员置顶文章成功
  8. 普通用户发表评论成功，管理员审核评论成功，抽测评论 ID 为 `4`
  9. 看板趋势返回 7 天数据
  10. 普通用户登出业务码 `200`，旧 token 再访问用户信息返回 HTTP `401`

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- MySQL 兼容配置：`066-backend/src/main/resources/application-mysql.yml`
- MySQL 初始化脚本：`066-backend/sql/init.sql`
- 默认管理员：`admin / 123456`
- 默认普通用户：`user / 123456`
- 默认作者用户：`writer / 123456`
- 说明：
  1. 直接执行 `066-backend/mvn spring-boot:run` 即可使用默认 H2 环境启动后端。
  2. H2 控制台地址为 `http://localhost:8066/h2-console`，JDBC URL 为 `jdbc:h2:mem:lite_blog_066`。
  3. 如需切换 MySQL，请先确认 `sql/init.sql` 中的 `DROP DATABASE` 可接受，再运行 `mysql -uroot -p1234 < sql/init.sql`。
  4. 当前 MySQL profile 默认使用 `root / 1234`。
  5. 前端执行 `npm run dev` 后默认访问 `http://localhost:3066`。

## 7. 备注

1. 本轮后端真实启动使用端口 `8066`。
2. 本机 `3306` 可用于 MySQL profile，但原始 SQL 脚本包含 `DROP DATABASE`，为避免破坏本机数据，默认验证采用 H2 自举链路。
3. `mvn` 输出中的 `rdc` profile 警告来自外部 Maven 环境，不影响本项目测试和启动结论。
4. `target/`、`node_modules/`、`dist/` 和 `.tmp/` 临时日志等生成物不纳入提交。
