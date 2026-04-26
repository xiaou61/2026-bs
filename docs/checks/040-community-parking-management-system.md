# 040 社区车辆违停处理系统检查报告

## 1. 检查结论

- 项目编号：`040`
- 项目名称：`社区车辆违停处理系统`
- 检查日期：`2026-04-26`
- 当前状态：`已完成`
- 综合结论：`本轮已修复 QueryDSL 重复生成 Q 类、启动类参数错误、默认环境强依赖 MySQL、初始化管理员密码哈希不可用、Hibernate 6 浮点字段 scale 建模失败以及默认演示数据缺失等问题。当前后端可在默认 H2 环境下通过冒烟测试并真实启动，管理员登录、违停类型、举报提交、举报审核接口抽测通过；前端可构建并通过 Vite 代理完成登录接口联调。`

## 2. 项目形态

- 后端目录：`040-backend`
- 前端目录：`040-frontend`
- 后端技术栈：`Spring Boot 3.2 + JPA/Hibernate + QueryDSL + JWT + H2/MySQL`
- 前端技术栈：`Vue 3 + Vite + Pinia + Element Plus`
- 默认后端端口：`8040`
- 默认前端端口：`3040`

## 3. 本轮修复

### 3.1 默认运行环境修复

1. 新增 H2 依赖，并将默认 `application.yml` 改为 H2 内存库自举，避免巡检和演示必须预先安装 MySQL。
2. 新增 `application-mysql.yml`，保留 MySQL 部署配置，真实数据库模式通过 `-Dspring-boot.run.profiles=mysql` 启动。
3. 新增 `data-h2.sql`，提供用户、车辆、违停类型、举报、车位与系统配置演示数据。
4. 为 SQL 初始化显式设置 `UTF-8` 编码，并使用 `ddl-auto=create-drop` + `defer-datasource-initialization=true` 保证 H2 表结构先建后灌入数据。
5. 修复原 `init.sql` 中默认用户 bcrypt 哈希不可用的问题，统一默认演示密码为 `123456`。
6. 将默认上传目录改为项目相对路径 `./uploads/parking`，降低本机固定盘符依赖。

### 3.2 编译与实体建模修复

1. 删除旧 `com.mysema.maven:apt-maven-plugin`，改由 Maven compiler 注解处理 QueryDSL，避免 `target/generated-sources/java` 与 `target/generated-sources/annotations` 重复生成 Q 类导致编译失败。
2. 修复 `ParkingManagementApplication` 中 `SpringApplication.run(ParkingManagementApplication.java, args)` 的错误写法，改为传入 `.class`。
3. 将 `Report` 与 `ReportDTO` 的经纬度字段从 `Double` 改为 `BigDecimal`，使 Java 类型与 SQL `DECIMAL(10,6)` 一致，并修复 Hibernate 6 对浮点类型设置 `scale` 的启动失败。
4. 新增后端冒烟测试，覆盖默认 H2 启动、管理员登录、违停类型查询、居民提交举报和管理员审核举报。

### 3.3 文档与联调修复

1. 新增 `040-backend/README.md`，说明默认 H2、默认账号、测试命令和 MySQL profile。
2. 新增 `040-backend/启动说明.txt`，便于毕业设计演示时按步骤启动。
3. 生成 `040-frontend/package-lock.json`，保证前端依赖安装更可复现。
4. 确认前端开发端口 `3040`，并通过 Vite 代理将 `/api` 转发到 `http://localhost:8040`。

## 4. 当前实现与目标能力对照

### 4.1 已具备能力

1. 后端默认 H2 自举，可直接执行 `mvn spring-boot:run` 启动。
2. 管理员账号 `admin / 123456`、普通用户账号 `user1 / 123456`、物业账号 `manager1 / 123456` 可用于演示。
3. 登录注册、违停类型、违停举报、我的举报、待审核列表、举报审核、车辆和车位等后端核心接口已有基础闭环。
4. 前端可通过 `npm run build` 构建，并可在 `3040` 端口通过 Vite 代理访问后端 `/api`。
5. H2 演示数据覆盖管理员、普通用户、物业账号、车辆、违停类型、举报记录与车位场景。

### 4.2 仍有差距

1. PRD 中申诉、通知、积分兑换、公告、违停处理记录等扩展模块在当前实现中仍偏基础，完整闭环有待继续增强。
2. 部分业务接口通过 `userId` 请求头识别当前用户，尚不是生产级 JWT 鉴权上下文。
3. 图片上传当前为本地目录存储，缺少更完整的文件类型校验、清理策略和对象存储方案。
4. 地图选点、车牌 OCR、短信通知等 PRD 描述能力在当前演示版本中仍为简化实现。
5. 前端构建存在主包体积超过 500KB 的 Vite 告警。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`040-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0`
- 覆盖内容：
  1. 默认 H2 上下文可启动
  2. `POST /api/auth/login` 使用 `admin / 123456` 返回 `code=200`
  3. `GET /api/violation-types` 返回 `code=200` 且中文分类包含 `占用消防通道`
  4. 携带 `userId=2` 提交 `POST /api/reports` 返回 `code=200`
  5. 携带 `userId=1` 调用 `POST /api/reports/{id}/audit` 返回 `code=200`

### 5.2 前端构建

- 执行命令：`040-frontend/npm run build`
- 结果：`通过`
- 关键结果：`vite build` 成功生成 `dist`
- 构建告警：
  1. `Some chunks are larger than 500 kB after minification`

### 5.3 启动与联调抽测

- 后端启动命令：`040-backend/mvn spring-boot:run`
- 前端启动命令：`040-frontend/npm run dev -- --host 127.0.0.1`
- 结果：`通过`
- 抽测地址：
  1. 后端违停类型：`http://127.0.0.1:8040/api/violation-types`
  2. 后端登录：`http://127.0.0.1:8040/api/auth/login`
  3. 前端页面：`http://127.0.0.1:3040/login`
  4. 前端代理：`http://127.0.0.1:3040/api/auth/login`
- 关键业务结果：
  1. 后端违停类型接口返回 `HTTP 200`，业务码 `200`
  2. 后端登录接口返回 `HTTP 200`，业务码 `200`，且包含 token
  3. 前端登录页返回 `HTTP 200`
  4. 经 Vite 代理调用登录接口返回 `HTTP 200`，业务码 `200`，且包含 token

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- MySQL 兼容配置：`040-backend/src/main/resources/application-mysql.yml`
- 默认账号：
  - 管理员：`admin / 123456`
  - 普通用户：`user1 / 123456`
  - 物业账号：`manager1 / 123456`
- 说明：
  1. 直接执行 `040-backend/mvn spring-boot:run` 即可使用默认 H2 环境启动后端。
  2. 后端接口默认不带额外 context-path，例如 `POST http://localhost:8040/api/auth/login`。
  3. 如需切换 MySQL，可先导入 `040-backend/src/main/resources/init.sql`，再运行 `mvn spring-boot:run -Dspring-boot.run.profiles=mysql`。
  4. 前端执行 `npm install` 后使用 `npm run dev` 启动，默认访问 `http://localhost:3040`。

## 7. 备注

1. 本轮后端真实启动使用端口 `8040`，前端真实启动使用端口 `3040`。
2. `mvn` 输出中的 `rdc` profile 警告来自外部 Maven 环境，不影响本项目测试与启动结论。
3. 前端 `dist/`、`node_modules/`、后端 `target/`、临时日志目录均不纳入提交。
