# 048 博物馆文物数字化管理平台检查报告

## 1. 检查结论

- 项目编号：`048`
- 项目名称：`博物馆文物数字化管理平台`
- 检查日期：`2026-04-26`
- 当前状态：`已完成`
- 综合结论：`本轮已修复默认 application.yml 缩进错误、默认环境强依赖 MySQL/Redis、默认端口未项目化、H2 自举缺失、MyBatis-Plus 分页方言固定 MySQL、H2 控制台未放行、JWT 角色映射沿用上一项目语义、后端缺少可执行冒烟测试、前端 package 名称仍为旧项目以及前端代理仍指向旧端口等问题。当前后端可在默认 H2 环境下通过冒烟测试并真实启动，文物浏览、展览列表、参观预约、讲解预约、讲解员确认完成、研究员投稿、管理员审核、收藏和公告链路可用；前端可构建，并可通过 Vite 代理完成登录与文物列表接口联调。`

## 2. 项目形态

- 后端目录：`048-backend`
- 前端目录：`048-frontend`
- 后端技术栈：`Spring Boot 3.2.1 + MyBatis-Plus 3.5.5 + Spring Security + JWT + H2/MySQL`
- 前端技术栈：`Vue 3 + Vite + TypeScript + Pinia + Element Plus + Axios`
- 默认后端端口：`8048`
- 默认前端端口：`3048`

## 3. 本轮修复

### 3.1 默认运行环境修复

1. 修复 `application.yml` 缩进错误，并将默认配置改为 H2 内存库自举，避免巡检和演示必须预先安装 MySQL/Redis。
2. 新增 `application-mysql.yml`，保留 MySQL/Redis 部署配置，真实数据库模式通过 `-Dspring-boot.run.profiles=mysql` 启动。
3. 新增 `schema-h2.sql` 与 `data-h2.sql`，提供用户、文物分类、朝代、展厅、文物、文物图片、研究成果、展览、参观预约、讲解预约、收藏、评论和公告等核心表结构与演示数据。
4. 将默认后端端口调整为 `8048`，前端开发端口调整为 `3048`，并将 Vite 代理目标改为 `http://localhost:8048`。
5. 将 MyBatis-Plus 分页方言改为通过 `app.mybatis.db-type` 配置控制，默认 H2，MySQL profile 中切换为 MySQL。
6. 放行 H2 控制台并设置 frame same-origin，方便默认演示环境查看内存库数据。

### 3.2 后端认证与测试修复

1. 将 JWT 过滤器中的角色映射调整为当前项目语义：`3 -> ROLE_ADMIN`、`2 -> ROLE_RESEARCHER`、`1 -> ROLE_GUIDE`、`0 -> ROLE_USER`。
2. 在 JWT 过滤器中补充 `request` attribute：`userId` 和 `role`，方便控制器读取当前登录上下文。
3. 新增 Spring Boot 测试依赖，补齐可执行的后端冒烟测试。
4. 新增冒烟测试覆盖管理员、游客、讲解员、研究员四角色登录，公告列表、管理员统计、基础数据、文物列表/详情/图片/点赞、展览、研究成果、参观预约、管理员确认完成、收藏、讲解预约、讲解员确认完成、研究员投稿和管理员审核发布。
5. 删除无业务意义的 `Main.java`，避免项目入口存在 `Hello world` 杂散类。

### 3.3 前端联调修复

1. 将 `package.json` 和 `package-lock.json` 的项目名从旧项目名称修正为 `048-frontend`。
2. 将 Vite 开发端口调整为 `3048`，避免与其他项目端口冲突。
3. 将 API 基础路径调整为 `/api`，并通过 Vite 代理到 `http://localhost:8048`，使前端默认开发环境可以直接联调当前后端。

### 3.4 文档修复

1. 更新 `048-backend/README.md`，补齐默认 H2 启动、H2 控制台、MySQL profile、前端端口、验证命令和默认账号说明。
2. 新增 `048-backend/启动说明.txt`，便于答辩或本地演示时快速启动。
3. 新增本巡检报告，并同步更新总台账。

## 4. 当前实现与目标能力对照

### 4.1 已具备能力

1. 后端默认 H2 自举，可直接执行 `mvn spring-boot:run` 启动。
2. 默认账号覆盖管理员 `admin / 123456`、讲解员 `guide1 / 123456`、研究员 `researcher1 / 123456`、游客 `user1 / 123456`。
3. 演示数据覆盖 8 类文物分类、10 个朝代、6 个展厅、8 件文物、3 个展览、3 条研究成果、3 条公告和参观/讲解预约基础数据。
4. 游客可浏览文物、文物图片、展览、公告和已发布研究成果。
5. 游客可创建参观预约，管理员可确认并完成参观预约。
6. 游客可创建讲解预约，讲解员可确认并完成讲解预约。
7. 游客可收藏和取消收藏文物。
8. 研究员可提交研究成果，管理员可审核发布，发布后游客侧可检索。
9. 前端可通过 `npm run build` 构建，并可在 `3048` 端口通过 Vite 代理访问后端 `/api`。

### 4.2 仍有差距

1. 文物 3D 模型、高清图片、音频讲解仍以 URL 字段为主，缺少完整文件存储与预览链路。
2. 参观预约和讲解预约缺少库存/容量冲突校验、支付锁单和超时释放机制。
3. 评论能力已有实体和表结构，但前端与控制器闭环仍不完整。
4. 研究成果缺少附件上传、版本管理和学术审核流程细化。
5. 前端构建存在主包体积超过 500KB 的 Vite 告警。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`048-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0`
- 覆盖内容：
  1. 默认 H2 上下文可启动
  2. `POST /api/auth/login` 使用 `admin / 123456` 返回 `code=200`，角色为 `3`
  3. `GET /api/notice/list` 返回公告分页数据
  4. `GET /api/admin/statistics` 返回文物和展览统计
  5. `POST /api/auth/login` 使用 `user1 / 123456` 返回 `code=200`，角色为 `0`
  6. 游客查询分类、朝代、展厅和讲解员基础数据成功
  7. 游客查询文物列表、文物详情、文物图片并点赞成功
  8. 游客查询展览列表和已发布研究成果列表成功
  9. 游客创建参观预约成功，并可在我的预约中查询到该预约
  10. 管理员确认并完成参观预约成功，预约状态变为 `2`
  11. 游客收藏文物、查询收藏、取消收藏成功
  12. 游客创建讲解预约成功，并可在我的讲解预约中查询到该预约
  13. `POST /api/auth/login` 使用 `guide1 / 123456` 返回 `code=200`，角色为 `1`
  14. 讲解员查询自己的讲解预约、确认并完成讲解预约成功，预约状态变为 `3`
  15. `POST /api/auth/login` 使用 `researcher1 / 123456` 返回 `code=200`，角色为 `2`
  16. 研究员创建研究成果成功
  17. 管理员审核研究成果通过成功
  18. 游客侧按关键词检索可看到审核后的研究成果

### 5.2 前端构建

- 执行命令：`048-frontend/npm run build`
- 结果：`通过`
- 关键结果：`vue-tsc -b && vite build` 成功，生成 `dist`
- 构建告警：
  1. `Some chunks are larger than 500 kB after minification`

### 5.3 启动与联调抽测

- 后端启动命令：`048-backend/mvn spring-boot:run`
- 前端启动命令：`048-frontend/npm run dev -- --host 127.0.0.1`
- 结果：`通过`
- 抽测地址：
  1. 后端公告列表：`http://localhost:8048/api/notice/list`
  2. 后端四角色登录：`http://localhost:8048/api/auth/login`
  3. 后端管理员统计：`http://localhost:8048/api/admin/statistics`
  4. 后端基础分类：`http://localhost:8048/api/base/categories`
  5. 后端文物列表：`http://localhost:8048/api/relic/list`
  6. 后端展览列表：`http://localhost:8048/api/exhibition/list`
  7. 后端研究成果列表：`http://localhost:8048/api/research/list`
  8. 后端讲解员预约：`http://localhost:8048/api/guide/bookings`
  9. 后端研究员成果：`http://localhost:8048/api/researcher/researches`
  10. 前端登录页：`http://127.0.0.1:3048/login`
  11. 前端代理登录：`http://127.0.0.1:3048/api/auth/login`
  12. 前端代理文物列表：`http://127.0.0.1:3048/api/relic/list`
- 关键业务结果：
  1. 后端公告列表接口返回业务码 `200`
  2. 管理员、游客、讲解员、研究员登录均返回业务码 `200`，角色分别为 `3`、`0`、`1`、`2`
  3. 管理员统计返回文物数量 `8`、展览数量 `3`
  4. 基础分类返回 `8` 条演示数据
  5. 文物列表返回 `8` 条演示数据
  6. 展览列表返回 `3` 条演示数据
  7. 已发布研究成果列表返回 `2` 条演示数据
  8. 讲解员预约列表返回 `1` 条演示数据
  9. 研究员个人成果列表返回 `2` 条演示数据
  10. 前端登录页返回 `HTTP 200`
  11. 经 Vite 代理调用登录和文物列表均返回业务码 `200`
  12. 联调结束后 `8048` 和 `3048` 无 LISTENING 残留端口

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- MySQL 兼容配置：`048-backend/src/main/resources/application-mysql.yml`
- MySQL 初始化脚本：`048-backend/sql/init.sql`
- 默认账号：
  - 管理员：`admin / 123456`
  - 讲解员：`guide1 / 123456`
  - 研究员：`researcher1 / 123456`
  - 游客：`user1 / 123456`
- 说明：
  1. 直接执行 `048-backend/mvn spring-boot:run` 即可使用默认 H2 环境启动后端。
  2. 后端接口默认不带额外 context-path，例如 `POST http://localhost:8048/api/auth/login`。
  3. H2 控制台地址为 `http://localhost:8048/h2-console`，JDBC URL 为 `jdbc:h2:mem:museum_relic`。
  4. 如需切换 MySQL，可先导入 `048-backend/sql/init.sql`，再运行 `mvn spring-boot:run -Dspring-boot.run.profiles=mysql`。
  5. 前端执行 `npm install` 后使用 `npm run dev` 启动，默认访问 `http://localhost:3048`。

## 7. 备注

1. 本轮后端真实启动使用端口 `8048`，前端真实启动使用端口 `3048`。
2. `mvn` 输出中的 `rdc` profile 警告来自外部 Maven 环境，不影响本项目测试与启动结论。
3. 前端 `dist/`、`node_modules/`、后端 `target/`、临时日志目录均不纳入提交。
