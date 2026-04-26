# 039 民歌民谣交流平台检查报告

## 1. 检查结论

- 项目编号：`039`
- 项目名称：`民歌民谣交流平台`
- 检查日期：`2026-04-26`
- 当前状态：`已完成`
- 综合结论：`本轮已修复默认环境强依赖 PostgreSQL、Spring Data JDBC 实体字段映射与 SQL 小写下划线字段不匹配、H2 初始化中文乱码、Spring Security 默认表单/Basic 干扰、初始化管理员密码哈希不可用、前后端端口与代理未项目化等问题。当前后端可在默认 H2 环境下通过冒烟测试并真实启动，管理员登录、公开分类、民歌列表、后台用户列表接口抽测通过；前端可构建并通过 Vite 代理完成登录接口联调。`

## 2. 项目形态

- 后端目录：`039-backend`
- 前端目录：`039-frontend`
- 后端技术栈：`Spring Boot 3.2 + Spring Data JDBC + Spring Security + JWT + H2/PostgreSQL + Knife4j`
- 前端技术栈：`Vue 3 + Vite + Pinia + Element Plus`
- 默认后端端口：`8039`
- 默认前端端口：`3039`

## 3. 本轮修复

### 3.1 默认运行环境修复

1. 新增 H2 依赖，并将默认 `application.yml` 改为 H2 内存库自举，避免巡检和演示必须预先安装 PostgreSQL。
2. 新增 `application-postgresql.yml`，保留 PostgreSQL 部署配置，真实数据库模式通过 `-Dspring-boot.run.profiles=postgresql` 启动。
3. 新增 `sql/schema-h2.sql` 与 `sql/data-h2.sql`，提供用户、分类、民歌、评论、点赞收藏与公告演示数据。
4. 为 SQL 初始化显式设置 `UTF-8` 编码，并在测试中断言中文分类名，防止 Windows/Maven 环境下初始化数据乱码。
5. 修复原 `sql/init.sql` 中管理员 bcrypt 哈希不可用的问题，统一默认演示密码为 `123456`。
6. 将默认后端端口改为 `8039`、前端开发端口改为 `3039`，降低与其他项目巡检时的端口冲突概率。

### 3.2 Spring Data JDBC 与安全配置修复

1. 为 `User`、`Category`、`FolkSong`、`Comment`、`Favorite`、`Like`、`Announcement` 实体补齐显式 `@Column` 映射，修复默认生成大写字段导致 H2/PostgreSQL 小写字段查询失败的问题。
2. 放行 H2 控制台，并设置 frame option 为 same-origin，方便默认演示环境排查数据。
3. 禁用默认表单登录和 HTTP Basic，排除默认用户自动配置，避免启动时生成默认密码并干扰 API 演示口径。
4. 补充后端冒烟测试，覆盖默认 H2 启动、管理员登录、公开分类、民歌列表和后台用户列表。

### 3.3 文档与联调修复

1. 新增 `039-backend/README.md`，说明默认 H2、默认账号、测试命令和 PostgreSQL profile。
2. 新增 `039-backend/启动说明.txt`，便于毕业设计演示时按步骤启动。
3. 更新 `039-frontend/vite.config.js`，将 `/api` 与 `/uploads` 代理到 `http://localhost:8039`。
4. 生成 `039-frontend/package-lock.json`，保证前端依赖安装更可复现。

## 4. 当前实现与目标能力对照

### 4.1 已具备能力

1. 后端默认 H2 自举，可直接执行 `mvn spring-boot:run` 启动。
2. 管理员账号 `admin / 123456`、普通用户账号 `user / 123456` 可用于演示。
3. 公开分类、民歌列表、热门/最新民歌、民歌详情、评论、点赞收藏、公告、用户中心与后台管理接口已有基础闭环。
4. 前端可通过 `npm run build` 构建，并可在 `3039` 端口通过 Vite 代理访问后端 `/api`。
5. H2 演示数据覆盖首页、分类页、详情页、公告页与后台待审核场景。

### 4.2 仍有差距

1. 后台统计面板仍偏简化，尚未形成完整数据看板接口。
2. 民歌审核接口只有审核状态，README/PRD 中提到的审核理由字段未单独落库。
3. 文件上传当前为本地目录存储，缺少更完整的对象存储、文件清理和类型安全策略。
4. 前端构建存在 Vite CJS API 废弃提示、Sass legacy API 警告和主包体积超过 500KB 的告警。
5. 当前接口鉴权可满足演示，但细粒度权限、审计日志和生产级 JWT 刷新机制仍可继续增强。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`039-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0`
- 覆盖内容：
  1. 默认 H2 上下文可启动
  2. `POST /api/auth/login` 使用 `admin / 123456` 返回 `code=200`
  3. `GET /api/categories` 返回 `code=200` 且中文分类包含 `山歌`
  4. `GET /api/songs?pageNum=1&pageSize=5` 返回 `code=200`
  5. 携带管理员 token 访问 `GET /api/admin/users?pageNum=1&pageSize=5` 返回 `code=200`

### 5.2 前端构建

- 执行命令：`039-frontend/npm run build`
- 结果：`通过`
- 关键结果：`vite build` 成功生成 `dist`
- 构建告警：
  1. `The CJS build of Vite's Node API is deprecated`
  2. `DEPRECATION WARNING [legacy-js-api]`
  3. `Some chunks are larger than 500 kB after minification`

### 5.3 启动与联调抽测

- 后端启动命令：`039-backend/mvn spring-boot:run`
- 前端启动命令：`039-frontend/npm run dev -- --host 127.0.0.1`
- 结果：`通过`
- 抽测地址：
  1. 后端登录：`http://localhost:8039/api/auth/login`
  2. 后端分类：`http://localhost:8039/api/categories`
  3. 后端民歌列表：`http://localhost:8039/api/songs?pageNum=1&pageSize=5`
  4. 前端页面：`http://127.0.0.1:3039/login`
  5. 前端代理：`http://127.0.0.1:3039/api/auth/login`
- 关键业务结果：
  1. 后端登录接口返回 `HTTP 200`，业务码 `200`
  2. 分类接口返回 `山歌`，中文数据正常
  3. 民歌列表接口返回已审核民歌总数 `2`
  4. 前端登录页返回 `HTTP 200`
  5. 经 Vite 代理调用登录接口返回 `HTTP 200`，用户为 `admin`

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- PostgreSQL 兼容配置：`039-backend/src/main/resources/application-postgresql.yml`
- 默认账号：
  - 管理员：`admin / 123456`
  - 普通用户：`user / 123456`
- 说明：
  1. 直接执行 `039-backend/mvn spring-boot:run` 即可使用默认 H2 环境启动后端。
  2. 后端接口默认不带额外 context-path，例如 `POST http://localhost:8039/api/auth/login`。
  3. 如需切换 PostgreSQL，可先导入 `039-backend/sql/init.sql`，再运行 `mvn spring-boot:run -Dspring-boot.run.profiles=postgresql`。
  4. 前端执行 `npm install` 后使用 `npm run dev` 启动，默认访问 `http://localhost:3039`。

## 7. 备注

1. 本轮后端真实启动使用端口 `8039`，前端真实启动使用端口 `3039`。
2. `mvn` 输出中的 `rdc` profile 警告来自外部 Maven 环境，不影响本项目测试与启动结论。
3. 前端 `dist/`、`node_modules/`、后端 `target/`、临时日志目录均不纳入提交。
