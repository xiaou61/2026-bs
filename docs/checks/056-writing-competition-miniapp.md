# 056 短文写作竞赛管理小程序检查报告

## 1. 检查结论

- 项目编号：`056`
- 项目名称：`短文写作竞赛管理小程序`
- 检查日期：`2026-04-28`
- 当前状态：`已完成`
- 综合结论：`本轮已修复默认环境强依赖 MySQL/8080、H2 自举缺失、分页方言固定 MySQL、游客分类接口被 JWT 拦截、登录响应密码字段泄露、基础管理员/评委/参赛者权限缺失、评分标准保存时 List<Map> 强转实体导致的运行时错误、管理端代理端口错误、小程序请求端口错误以及小程序缺失静态图片资源引用等问题。当前后端可在默认 H2 环境下启动并通过自动化冒烟测试，管理端可完成生产构建，小程序页面声明和静态资源引用核查通过，真实 HTTP 核心链路抽测通过。`

## 2. 项目形态

- 后端目录：`056-backend`
- 管理端目录：`056-frontend`
- 小程序目录：`056-miniapp`
- 后端技术栈：`Spring Boot 2.7.18 + MyBatis-Plus 3.5.4 + JWT + H2/MySQL`
- 管理端技术栈：`Vue 3 + Vite 5 + Element Plus + Pinia + ECharts`
- 小程序技术栈：`uni-app / Vue`
- 默认后端端口：`8056`
- 管理端开发端口：`3056`

## 3. 本轮修复

### 3.1 默认运行环境修复

1. 将后端默认端口从 `8080` 调整为 `8056`，避免合集内项目端口冲突。
2. 默认 `application.yml` 改为 H2 内存库自举，新增 `schema-h2.sql` 与 `data-h2.sql`。
3. 新增 `application-mysql.yml`，保留 MySQL 部署模式，通过 `-Dspring-boot.run.profiles=mysql` 切换。
4. 更新 MySQL 驱动坐标为 `com.mysql:mysql-connector-j`。
5. 增加 H2 与 Spring Boot Test 依赖。
6. MyBatis-Plus 分页插件改为自动识别数据库方言，兼容默认 H2 与 MySQL profile。
7. 文件上传目录改为 `./.tmp/056-uploads/`，避免默认写入不可控路径。
8. H2 初始化数据日期更新至 2026 年，避免投稿截止日期在当前巡检日期前过期。

### 3.2 登录、鉴权与权限修复

1. `User.password` 改为 `WRITE_ONLY`，支持登录/改密写入但响应不再回传密码字段。
2. JWT 拦截器兼容 `Bearer` token。
3. JWT 拦截器兼容 `OPTIONS` 预检请求。
4. JWT 拦截器补充 token 对应用户存在和启用状态校验。
5. 放行 `/api/category/all`，保证小程序游客首页可加载分类。
6. 补充基础管理员权限拦截，普通参赛者访问统计、用户管理、竞赛管理、公告管理、评委管理、评分标准管理和作品审核等接口返回 HTTP `403`。
7. 补充评委评分权限拦截，普通参赛者访问评委待评分、提交评分和我的评分接口返回 HTTP `403`。
8. 修改密码补充用户不存在和新密码为空校验。

### 3.3 业务接口修复

1. 修复评分标准保存接口将 `List<Map>` 直接强转为 `List<ScoreStandard>` 导致的运行时异常。
2. 使用 `ObjectMapper.convertValue` 将请求体中的评分标准列表转换为实体列表。
3. 新增后端冒烟测试 `WritingCompetitionApplicationSmokeTest`，覆盖公开接口、登录脱敏、401/403、投稿、审核、评委评分、评分详情和评分标准保存。

### 3.4 管理端与小程序修复

1. 管理端 Vite 开发端口改为 `3056`。
2. 管理端代理目标改为 `http://localhost:8056`。
3. 新增管理端 `package-lock.json`，保证依赖安装可复现。
4. 小程序请求地址改为 `http://localhost:8056`。
5. 小程序 `pages.json` 移除不存在的 tabBar 图标路径，改为文字型 tabBar。
6. 小程序首页、竞赛列表、竞赛详情和我的页面使用渐变占位替代缺失的 `/static/default-cover.png` 与 `/static/default-avatar.png`。

### 3.5 文档修复

1. 新增 `056-backend/README.md`，补齐默认 H2 启动、MySQL profile、默认账号、管理端端口、小程序端口和验证命令。
2. 新增 `056-backend/启动说明.txt`，便于答辩和本地演示。
3. 新增本巡检报告，并同步更新总台账。

## 4. 当前实现与目标能力对照

### 4.1 已具备能力

1. 后端默认 H2 自举，可直接执行 `mvn spring-boot:run` 启动。
2. 默认管理员、评委和参赛者账号可登录。
3. 游客可访问分类、公开竞赛列表和公开公告。
4. 管理员可查看统计、维护竞赛分类、竞赛、公告、评委、评分标准和作品审核。
5. 参赛者可登录、查看个人信息、提交作品并查询我的作品。
6. 管理员可审核投稿作品。
7. 评委可查看待评分作品并提交评分。
8. 评分详情可查询，评分标准保存链路可运行。
9. 管理端代理已与后端端口对齐，小程序页面声明和静态资源引用已对齐。

### 4.2 仍有差距

1. 密码仍使用 MD5，生产环境建议改为 BCrypt。
2. Redis 配置仍保留为生产扩展项，默认演示链路不依赖 Redis。
3. 当前权限拦截为基础角色模型，更细粒度的评委分配、管理员菜单权限仍可继续增强。
4. 文件上传使用本地目录，生产环境建议切换到持久化对象存储或专用文件服务器。
5. 小程序真实微信登录仍需正式 `appId` / `appSecret` 与线上接口域名配置。
6. 管理端构建存在 Vite CJS API 弃用提示和 chunk 体积提示，当前不影响构建产物生成。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`056-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0, Skipped: 0`
- 覆盖内容：
  1. 默认 H2 上下文可启动
  2. 分类、竞赛和公告公开接口可访问
  3. 管理员、评委和参赛者登录成功且密码字段不回传
  4. 未携带 token 访问用户信息返回 HTTP `401`
  5. 普通参赛者访问统计接口返回 HTTP `403`
  6. 管理员访问统计接口成功
  7. 参赛者可提交作品并查询我的作品
  8. 管理员可审核作品
  9. 评委可查询待评分作品并提交评分
  10. 评分详情和评分标准保存接口可运行

### 5.2 管理端构建

- 执行命令：`056-frontend/npm run build`
- 结果：`通过`
- 备注：Vite 输出 CJS API 弃用提示和 chunk 体积提示，不影响构建产物生成。

### 5.3 小程序静态核查

- 执行内容：检查 `pages.json` 声明页面是否存在，检查 tabBar icon 是否缺失，搜索断链静态资源和旧端口。
- 结果：`通过`
- 关键结果：
  1. `pages.json` 声明页面均存在
  2. 无缺失 tabBar 图标
  3. 无 `/static/default-cover.png`、`/static/default-avatar.png` 和 `localhost:8080` 残留引用

### 5.4 启动与接口抽测

- 后端启动命令：`056-backend/mvn spring-boot:run`
- 结果：`通过`
- 抽测地址：
  1. 分类公开接口：`GET http://localhost:8056/api/category/all`
  2. 竞赛公开列表：`GET http://localhost:8056/api/competition/public/list`
  3. 公告公开列表：`GET http://localhost:8056/api/notice/public/list`
  4. 未登录用户信息：`GET http://localhost:8056/api/user/info`
  5. 管理员登录：`POST http://localhost:8056/api/user/login`
  6. 评委登录：`POST http://localhost:8056/api/user/login`
  7. 参赛者登录：`POST http://localhost:8056/api/user/login`
  8. 管理员统计：`GET http://localhost:8056/api/stats/dashboard`
  9. 作品投稿与我的作品：`/api/work/**`
  10. 作品审核：`POST http://localhost:8056/api/work/audit`
  11. 评委待评分与提交评分：`/api/score/**`
- 关键业务结果：
  1. 分类、竞赛、公告公开接口均返回业务码 `200`
  2. 未登录访问用户信息返回 HTTP `401`
  3. 管理员、评委和参赛者登录均返回业务码 `200`
  4. 普通参赛者访问统计接口返回 HTTP `403`
  5. 管理员统计、投稿、审核、待评分、提交评分和评分详情接口均返回业务码 `200`
  6. 联调结束后 `8056` 无 LISTENING 残留端口

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- MySQL 兼容配置：`056-backend/src/main/resources/application-mysql.yml`
- MySQL 初始化脚本：`056-backend/sql/init.sql`
- 默认管理员：`admin / 123456`
- 默认评委：`judge / 123456`
- 默认参赛者：`user / 123456`
- 说明：
  1. 直接执行 `056-backend/mvn spring-boot:run` 即可使用默认 H2 环境启动后端。
  2. H2 控制台地址为 `http://localhost:8056/h2-console`，JDBC URL 为 `jdbc:h2:mem:writing_competition`。
  3. 如需切换 MySQL，可先导入 `056-backend/sql/init.sql`，再运行 `mvn spring-boot:run -Dspring-boot.run.profiles=mysql`。
  4. 管理端执行 `npm run dev` 后默认访问 `http://localhost:3056`。
  5. 小程序请求地址已调整为 `http://localhost:8056`。

## 7. 备注

1. 本轮后端真实启动使用端口 `8056`。
2. `mvn` 输出中的 `rdc` profile 警告来自外部 Maven 环境，不影响本项目测试和启动结论。
3. `target/`、`node_modules/`、`dist/` 和 `.tmp/` 临时日志等生成物不纳入提交。
