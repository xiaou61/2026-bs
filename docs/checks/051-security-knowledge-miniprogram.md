# 051 基于微信小程序的网络安全知识科普 APP 检查报告

## 1. 检查结论

- 项目编号：`051`
- 项目名称：`基于微信小程序的网络安全知识科普 APP`
- 检查日期：`2026-04-27`
- 当前状态：`已完成`
- 综合结论：`本轮已修复默认环境强依赖 MySQL/Redis/微信接口、后端端口未项目化、JJWT 在 JDK 11+ 运行缺 JAXB、MyBatis-Plus 分页方言固定 MySQL、公开接口鉴权规则误配置、普通用户 token 可访问管理端接口、小程序学习记录和收藏请求体与后端参数不匹配、管理端/小程序仍指向 8080、小程序 pages/qa 声明缺页、本地 static 图片资源断链以及后端缺少可执行冒烟测试等问题。当前后端可在默认 H2 环境下启动并通过自动化冒烟测试，管理端可构建，小程序页面声明和资源引用静态核查通过。`

## 2. 项目形态

- 后端目录：`051-backend`
- 管理端目录：`051-frontend/admin`
- 小程序目录：`051-frontend/miniprogram`
- 后端技术栈：`Spring Boot 2.7.18 + MyBatis-Plus 3.5.4 + JWT + H2/MySQL`
- 管理端技术栈：`Vue 3 + Vite 5 + Element Plus`
- 小程序技术栈：`原生微信小程序`
- 默认后端端口：`8051`
- 管理端开发端口：`3051`
- 小程序默认后端地址：`http://localhost:8051`

## 3. 本轮修复

### 3.1 默认运行环境修复

1. 将后端默认端口从 `8080` 调整为 `8051`，避免和其他项目冲突。
2. 默认 `application.yml` 改为 H2 内存库自举，新增 `schema-h2.sql` 与 `data-h2.sql`。
3. 新增 `application-mysql.yml`，保留 MySQL 部署模式，通过 `-Dspring-boot.run.profiles=mysql` 切换。
4. 将 MySQL 驱动坐标更新为 `com.mysql:mysql-connector-j`，消除 relocation 告警。
5. 增加 `jaxb-api` 与 `jaxb-runtime`，修复旧版 JJWT 在 JDK 11+ 下生成/解析 token 的运行时缺类风险。
6. 将 MyBatis-Plus 分页插件改为自动识别数据库方言，兼容默认 H2 与 MySQL profile。
7. 将 Redis 点赞、每日题目缓存改为本地缓存服务兜底，默认启动和核心接口不再依赖外部 Redis。

### 3.2 登录、鉴权与接口修复

1. 小程序登录默认启用模拟微信登录，将任意 `code` 映射为本地 openid，便于本地演示。
2. JWT 拦截器改为内部识别公开接口，避免 `/api/article/*` 误放行 `/api/article/learn` 等受保护接口。
3. JWT 拦截器补充管理端权限校验，普通用户 token 访问 `/api/admin/**` 返回业务码 `403`。
4. 公开文章、资讯、问答详情接口支持可选 token，登录用户访问文章详情时仍能返回收藏/点赞状态。
5. 修复 `/api/article/learn` 与 `/api/favorite/add` 后端使用 `@RequestParam`、小程序发送 JSON body 导致的参数绑定失败。
6. 文章点赞、收藏和学习记录补充文章存在性校验。
7. 用户统计和积分更新补充用户存在性校验，避免空指针异常。

### 3.3 前端与小程序修复

1. 管理端 Vite 开发端口改为 `3051`，代理目标改为 `http://localhost:8051`。
2. 小程序请求工具默认地址改为 `http://localhost:8051`。
3. 小程序请求工具兼容业务码与 HTTP 状态的 `401/403`，未授权时清理登录态。
4. 补齐 `pages/qa` 的 `.js/.json/.wxml/.wxss` 四件套，修复 `app.json` 页面声明断链。
5. 移除 `app.json` 中缺失的 tabBar 本地图片引用。
6. 将页面中的 `/static/**` 缺图引用改为样式占位，避免微信开发者工具或运行时出现本地资源断链。
7. MySQL 初始化脚本中的演示图片路径同步改为空，避免切换 MySQL 后再次返回不存在的本地静态资源。

### 3.4 文档与测试修复

1. 新增后端冒烟测试 `SecurityKnowledgeApplicationSmokeTest`。
2. 新增 `051-backend/README.md`，补齐默认 H2 启动、MySQL profile、默认账号、模拟微信登录和验证命令。
3. 新增 `051-backend/启动说明.txt`，便于答辩和本地演示。
4. 新增本巡检报告，并同步更新总台账。
5. 新增管理端 `package-lock.json`，保证依赖安装可复现。

## 4. 当前实现与目标能力对照

### 4.1 已具备能力

1. 后端默认 H2 自举，可直接执行 `mvn spring-boot:run` 启动。
2. 默认管理员账号 `admin / 123456` 可登录管理端接口。
3. 小程序端支持模拟微信登录，可生成本地演示用户 token。
4. 演示数据覆盖 6 个知识分类、6 篇文章、6 道题目、3 条安全资讯、问答帖子与回复。
5. 游客可浏览知识分类、文章列表、文章详情、安全资讯、问答列表和排行榜。
6. 登录用户可查看个人信息、每日答题、提交答案、记录学习进度、收藏文章、发布问答。
7. 管理员可查看统计、用户、分类、文章、题库、资讯和问答管理接口。
8. 小程序 `app.json` 声明的 14 个页面均具备 `.js/.json/.wxml/.wxss`。

### 4.2 仍有差距

1. 真实微信登录、openid 绑定和线上合法域名配置需在微信开发者平台补充。
2. Redis 生产级分布式缓存能力已保留依赖，但默认演示链路使用本地缓存兜底，生产部署可再切回 Redis 实现。
3. 管理端页面仍偏基础，适合答辩演示和数据维护，复杂运营能力仍可扩展。
4. 密码仍使用 MD5 存储，生产环境建议改为 BCrypt。
5. 问答内容审核、推送通知、图片上传和更细粒度权限仍未完整落地。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`051-backend/mvn clean test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0, Skipped: 0`
- 覆盖内容：
  1. 默认 H2 上下文可启动
  2. 分类、文章、资讯返回演示数据
  3. 管理员登录成功且响应不包含密码字段
  4. 管理员统计接口返回文章数和题目数
  5. 模拟微信登录成功且响应不包含密码字段
  6. 普通用户 token 访问管理端统计返回业务码 `403`
  7. 登录用户可查询个人信息和每日题目
  8. 登录用户可提交答案并获得积分
  9. 登录用户可提交学习记录和收藏文章
  10. 文章详情可返回登录用户收藏状态
  11. 登录用户可发布问答
  12. 用户统计返回学习数、收藏数和排名

### 5.2 管理端构建

- 执行命令：`051-frontend/admin/npm install --no-audit --no-fund`
- 结果：`通过`
- 执行命令：`051-frontend/admin/npm run build`
- 结果：`通过`
- 备注：Vite 输出 chunk 体积提示和 CJS API 弃用提示，不影响构建产物生成。

### 5.3 小程序静态核查

- 执行内容：检查 `app.json` 声明页面与 `.js/.json/.wxml/.wxss` 文件匹配情况，并搜索陈旧资源与端口引用。
- 结果：`通过`
- 关键结果：`PageCount=14, MissingFiles=0, StaleRefs=0`

### 5.4 启动与接口抽测

- 后端启动命令：`051-backend/mvn spring-boot:run`
- 结果：`通过`
- 抽测地址：
  1. 分类列表：`GET http://localhost:8051/api/category/list`
  2. 文章列表：`GET http://localhost:8051/api/article/list`
  3. 资讯列表：`GET http://localhost:8051/api/news/list`
  4. 管理员登录：`POST http://localhost:8051/api/admin/login`
  5. 管理端统计：`GET http://localhost:8051/api/admin/stats`
  6. 模拟微信登录：`POST http://localhost:8051/api/user/login`
  7. 用户信息：`GET http://localhost:8051/api/user/info`
  8. 每日题目：`GET http://localhost:8051/api/question/daily`
  9. 提交答案：`POST http://localhost:8051/api/answer/submit`
  10. 学习记录：`POST http://localhost:8051/api/article/learn`
  11. 添加收藏：`POST http://localhost:8051/api/favorite/add`
  12. 问答发布：`POST http://localhost:8051/api/qa/post`
- 关键业务结果：
  1. 分类返回 `6` 条演示数据
  2. 文章返回 `6` 条演示数据
  3. 资讯返回 `3` 条演示数据
  4. 管理端统计返回用户 `2` 个、文章 `6` 篇、题目 `6` 道
  5. 模拟用户昵称返回 `接口抽测用户`
  6. 普通用户访问管理端统计返回业务码 `403`
  7. 每日题目返回 `5` 道
  8. 提交第 1 题答案 `C` 返回正确并获得 `5` 分
  9. 学习记录和收藏均返回业务码 `200`
  10. 文章详情返回 `isFavorite=true`
  11. 问答列表返回 `3` 条
  12. 用户统计返回学习数 `1`、收藏数 `1`、排名 `3`
  13. 联调结束后 `8051` 无 LISTENING 残留端口

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- MySQL 兼容配置：`051-backend/src/main/resources/application-mysql.yml`
- MySQL 初始化脚本：`051-backend/sql/init.sql`
- 默认管理员：`admin / 123456`
- 默认小程序登录：模拟微信登录，任意 `code` 可生成本地演示用户
- 说明：
  1. 直接执行 `051-backend/mvn spring-boot:run` 即可使用默认 H2 环境启动后端。
  2. H2 控制台地址为 `http://localhost:8051/h2-console`，JDBC URL 为 `jdbc:h2:mem:security_knowledge`。
  3. 如需切换 MySQL，可先导入 `051-backend/sql/init.sql`，再运行 `mvn spring-boot:run -Dspring-boot.run.profiles=mysql`。
  4. 使用微信开发者工具打开 `051-frontend/miniprogram`，后端默认地址已配置为 `http://localhost:8051`。

## 7. 备注

1. 本轮后端真实启动使用端口 `8051`。
2. `mvn` 输出中的 `rdc` profile 警告来自外部 Maven 环境，不影响本项目测试和启动结论。
3. `target/`、`node_modules/`、`dist/` 和 `.tmp/` 临时日志等生成物不纳入提交。
