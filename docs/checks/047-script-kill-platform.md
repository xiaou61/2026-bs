# 047 剧本杀创作与预约平台检查报告

## 1. 检查结论

- 项目编号：`047`
- 项目名称：`剧本杀创作与预约平台`
- 检查日期：`2026-04-26`
- 当前状态：`已完成`
- 综合结论：`本轮已修复默认环境强依赖 MySQL/Redis、默认端口未项目化、H2 自举缺失、MyBatis-Plus 分页方言固定 MySQL、H2 控制台未放行、JWT 角色映射沿用上一项目语义、后端缺少可执行冒烟测试、前端 package 名称仍为旧项目以及前端代理仍指向旧端口等问题。当前后端可在默认 H2 环境下通过冒烟测试并真实启动，玩家预约、店家确认完成、玩家评价收藏、作者创作剧本、管理员审核上架、公告与基础统计链路可用；前端可构建，并可通过 Vite 代理完成登录与剧本列表接口联调。`

## 2. 项目形态

- 后端目录：`047-backend`
- 前端目录：`047-frontend`
- 后端技术栈：`Spring Boot 3.2.1 + MyBatis-Plus 3.5.5 + Spring Security + JWT + H2/MySQL`
- 前端技术栈：`Vue 3 + Vite + TypeScript + Pinia + Element Plus + Axios`
- 默认后端端口：`8047`
- 默认前端端口：`3047`

## 3. 本轮修复

### 3.1 默认运行环境修复

1. 新增 H2 依赖，并将默认 `application.yml` 改为 H2 内存库自举，避免巡检和演示必须预先安装 MySQL/Redis。
2. 新增 `application-mysql.yml`，保留 MySQL/Redis 部署配置，真实数据库模式通过 `-Dspring-boot.run.profiles=mysql` 启动。
3. 新增 `schema-h2.sql` 与 `data-h2.sql`，提供用户、店铺、房间、剧本分类、剧本、剧本内容、店铺剧本、预约、评价、收藏和公告等核心表结构与演示数据。
4. 将默认后端端口调整为 `8047`，前端开发端口调整为 `3047`，并将 Vite 代理目标改为 `http://localhost:8047`。
5. 将 MyBatis-Plus 分页方言改为通过 `app.mybatis.db-type` 配置控制，默认 H2，MySQL profile 中切换为 MySQL。
6. 放行 H2 控制台并设置 frame same-origin，方便默认演示环境查看内存库数据。

### 3.2 后端认证与测试修复

1. 将 JWT 过滤器中的角色映射调整为当前项目语义：`3 -> ROLE_ADMIN`、`2 -> ROLE_AUTHOR`、`1 -> ROLE_OWNER`、`0 -> ROLE_USER`。
2. 在 JWT 过滤器中补充 `request` attribute：`userId` 和 `role`，方便控制器读取当前登录上下文。
3. 新增 Spring Boot 测试依赖，补齐可执行的后端冒烟测试。
4. 新增冒烟测试覆盖管理员、玩家、店家、作者四角色登录，公告列表、管理员统计、剧本分类、剧本列表、店铺列表、房间列表、玩家预约、店家确认完成、玩家收藏评价、作者创建剧本和管理员审核上架。
5. 修复测试读取 MockMvc 响应时的中文编码问题，统一使用 UTF-8 解析 JSON。
6. 删除无业务意义的 `Main.java`，避免项目入口存在 `Hello world` 杂散类。

### 3.3 前端联调修复

1. 将 `package.json` 和 `package-lock.json` 的项目名从旧项目名称修正为 `047-frontend`。
2. 将 Vite 开发端口调整为 `3047`，避免与其他项目端口冲突。
3. 将 API 基础路径调整为 `/api`，并通过 Vite 代理到 `http://localhost:8047`，使前端默认开发环境可以直接联调当前后端。

### 3.4 文档修复

1. 更新 `047-backend/README.md`，补齐默认 H2 启动、H2 控制台、MySQL profile、前端端口、验证命令和默认账号说明。
2. 新增 `047-backend/启动说明.txt`，便于答辩或本地演示时快速启动。
3. 新增本巡检报告，并同步更新总台账。

## 4. 当前实现与目标能力对照

### 4.1 已具备能力

1. 后端默认 H2 自举，可直接执行 `mvn spring-boot:run` 启动。
2. 默认账号覆盖管理员 `admin / 123456`、店家 `shop1 / 123456`、作者 `author1 / 123456`、玩家 `user1 / 123456`。
3. 演示数据覆盖 2 个店铺、多个房间、5 个剧本分类、5 个已审核剧本、公告、预约、收藏和评价基础数据。
4. 玩家可浏览公告、剧本、店铺和房间，并创建预约。
5. 店家可查看自己的店铺，确认预约并标记预约完成。
6. 玩家可对已完成预约进行评价，也可以收藏剧本或店铺。
7. 作者可创建包含章节和角色内容的剧本，并进入待审核状态。
8. 管理员可查看统计数据并审核作者提交的剧本，审核通过后玩家侧可检索到该剧本。
9. 前端可通过 `npm run build` 构建，并可在 `3047` 端口通过 Vite 代理访问后端 `/api`。

### 4.2 仍有差距

1. 剧本内容管理仍以基础章节/角色文本为主，缺少复杂协作创作、版本比对和草稿恢复能力。
2. 预约库存校验相对简化，尚未形成完整的房间时间段冲突、支付锁单和超时释放机制。
3. 店家结算、真实支付、退款和财务对账能力未完整落地。
4. 图片上传和剧本封面仍偏基础，缺少完整文件存储、压缩和安全校验策略。
5. 前端构建存在主包体积超过 500KB 的 Vite 告警。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`047-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0`
- 覆盖内容：
  1. 默认 H2 上下文可启动
  2. `POST /api/auth/login` 使用 `admin / 123456` 返回 `code=200`，角色为 `3`
  3. `GET /api/notice/list` 返回公告分页数据
  4. `GET /api/admin/statistics` 返回用户、店铺和剧本统计
  5. `POST /api/auth/login` 使用 `user1 / 123456` 返回 `code=200`，角色为 `0`
  6. 玩家查询剧本分类、剧本列表、店铺列表和店铺房间成功
  7. 玩家创建预约成功，并可在我的预约中查询到该预约
  8. `POST /api/auth/login` 使用 `shop1 / 123456` 返回 `code=200`，角色为 `1`
  9. 店家查询自己的店铺成功
  10. 店家确认预约并完成预约成功
  11. 玩家查询预约详情时状态为 `3`
  12. 玩家收藏剧本或店铺成功
  13. 玩家新增评价成功
  14. `POST /api/auth/login` 使用 `author1 / 123456` 返回 `code=200`，角色为 `2`
  15. 作者创建包含内容章节的剧本成功
  16. 管理员审核该剧本通过成功
  17. 玩家侧按关键词检索可看到审核后的剧本

### 5.2 前端构建

- 执行命令：`047-frontend/npm run build`
- 结果：`通过`
- 关键结果：`vue-tsc -b && vite build` 成功，生成 `dist`
- 构建告警：
  1. `Some chunks are larger than 500 kB after minification`

### 5.3 启动与联调抽测

- 后端启动命令：`047-backend/mvn spring-boot:run`
- 前端启动命令：`047-frontend/npm run dev -- --host 127.0.0.1`
- 结果：`通过`
- 抽测地址：
  1. 后端公告列表：`http://localhost:8047/api/notice/list`
  2. 后端四角色登录：`http://localhost:8047/api/auth/login`
  3. 后端管理员统计：`http://localhost:8047/api/admin/statistics`
  4. 后端剧本列表：`http://localhost:8047/api/script/list`
  5. 后端店铺列表：`http://localhost:8047/api/shop/list`
  6. 后端房间列表：`http://localhost:8047/api/shop/1/rooms`
  7. 后端店家店铺：`http://localhost:8047/api/owner/shop`
  8. 后端作者剧本：`http://localhost:8047/api/author/scripts`
  9. 前端登录页：`http://127.0.0.1:3047/login`
  10. 前端代理登录：`http://127.0.0.1:3047/api/auth/login`
  11. 前端代理剧本列表：`http://127.0.0.1:3047/api/script/list`
- 关键业务结果：
  1. 后端公告列表接口返回业务码 `200`
  2. 管理员、玩家、店家、作者登录均返回业务码 `200`，角色分别为 `3`、`0`、`1`、`2`
  3. 管理员统计返回用户数量 `2`、店铺数量 `2`、剧本数量 `5`
  4. 剧本列表返回 `5` 条演示记录
  5. 店铺列表返回 `2` 条演示记录
  6. 店铺 `1` 返回 `3` 个可用房间
  7. 店家查询自己的店铺返回店铺 ID `1`
  8. 作者作品列表返回 `3` 条演示记录
  9. 前端登录页返回 `HTTP 200`
  10. 经 Vite 代理调用登录和剧本列表均返回业务码 `200`
  11. 联调结束后 `8047` 和 `3047` 无 LISTENING 残留端口

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- MySQL 兼容配置：`047-backend/src/main/resources/application-mysql.yml`
- MySQL 初始化脚本：`047-backend/sql/init.sql`
- 默认账号：
  - 管理员：`admin / 123456`
  - 店家：`shop1 / 123456`
  - 作者：`author1 / 123456`
  - 玩家：`user1 / 123456`
- 说明：
  1. 直接执行 `047-backend/mvn spring-boot:run` 即可使用默认 H2 环境启动后端。
  2. 后端接口默认不带额外 context-path，例如 `POST http://localhost:8047/api/auth/login`。
  3. H2 控制台地址为 `http://localhost:8047/h2-console`，JDBC URL 为 `jdbc:h2:mem:script_kill`。
  4. 如需切换 MySQL，可先导入 `047-backend/sql/init.sql`，再运行 `mvn spring-boot:run -Dspring-boot.run.profiles=mysql`。
  5. 前端执行 `npm install` 后使用 `npm run dev` 启动，默认访问 `http://localhost:3047`。

## 7. 备注

1. 本轮后端真实启动使用端口 `8047`，前端真实启动使用端口 `3047`。
2. `mvn` 输出中的 `rdc` profile 警告来自外部 Maven 环境，不影响本项目测试与启动结论。
3. 前端 `dist/`、`node_modules/`、后端 `target/`、临时日志目录均不纳入提交。
