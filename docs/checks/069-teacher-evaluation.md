# 069 科任教师考评系统检查报告

## 1. 检查结论

- 项目编号：`069`
- 项目名称：`科任教师考评系统`
- 检查日期：`2026-04-30`
- 当前状态：`已完成`
- 综合结论：`本轮已修复 Java 关键字包名导致的编译失败、068 周边游平台源码串项、默认环境强依赖 MySQL/Redis/8080、H2 自举缺失、MySQL 驱动旧坐标、MyBatis-Plus 分页方言固定 MySQL、JWT 不兼容 Bearer token、Redis 不可用会阻断默认演示登录、未登录/越权仍返回 HTTP 200、用户密码响应泄露、前端代理仍指向 8080 等问题。当前后端可在默认 H2 环境下启动并通过自动化冒烟测试，前端可完成生产构建，真实 HTTP 核心链路抽测通过。MySQL profile 已按本机账号 root / 1234 保留。`

## 2. 项目形态

- 后端目录：`069-backend`
- 前端目录：`069-frontend`
- 后端技术栈：`Spring Boot 2.7.18 + MyBatis-Plus 3.5.5 + JWT + H2/MySQL + Redis 可选 + Hutool`
- 前端技术栈：`Vue 3 + Vite 5 + Element Plus + Pinia + Axios + ECharts`
- 默认后端端口：`8069`
- 前端开发端口：`3069`

## 3. 本轮修复

### 3.1 工程结构修复

1. 将 `com.teacher.new` 迁移为 `com.teacher.eval`，避开 Java 关键字 `new` 导致的编译错误。
2. 删除混入的 `com.travel` 与旧 `com.teacher` 周边游平台源码，避免 068/069 业务串项。
3. 保留并整理 069 科任教师考评系统的控制器、实体、Mapper、服务和工具类。

### 3.2 默认运行环境修复

1. 将后端默认端口从 `8080` 调整为 `8069`，避免合集内项目端口冲突。
2. 默认 `application.yml` 改为 H2 内存库自举，新增 `schema-h2.sql` 与 `data-h2.sql`。
3. SQL 初始化显式指定 `UTF-8` 编码，避免中文演示数据乱码。
4. 新增 `application-mysql.yml`，保留 MySQL 部署模式，通过 `-Dspring-boot.run.profiles=mysql` 切换。
5. MySQL profile 默认密码按当前机器约定调整为 `root / 1234`。
6. 修复 MySQL 驱动坐标为 `com.mysql:mysql-connector-j:8.0.33`。
7. 项目编译目标改为 JDK 17，并增加 H2 与 Spring Boot Test 依赖。
8. MyBatis-Plus 分页方言默认改为 H2，MySQL profile 单独使用 MySQL 方言。
9. 默认演示环境使用本地 token 缓存兜底，MySQL profile 保留 Redis 扩展配置。

### 3.3 登录、鉴权与权限修复

1. `User.password` 改为 `WRITE_ONLY`，支持登录/新增写入但响应不再回传密码字段。
2. JWT 拦截器兼容 `Bearer` token。
3. 默认演示环境下登出后旧 token 会失效。
4. JWT 拦截器会重新读取用户状态，禁用或删除用户后旧 token 不再继续通过。
5. 未登录、token 无效和登录过期场景返回真实 HTTP `401`。
6. 学生访问用户管理等管理接口返回真实 HTTP `403`。

### 3.4 业务与数据修复

1. 新增 H2 版初始化数据，覆盖管理员、教师、学生、科目、班级、教师档案、指标、任务、评教记录、申诉和公告。
2. 管理员可访问用户、科目、班级、教师、指标和看板接口。
3. 学生可查看本班考评任务，并对开放任务提交五维评分。
4. 教师可查看本人评教记录，并对评教记录发起申诉。
5. 管理员可处理申诉并发布公告。
6. 看板统计和 7 天趋势接口可在默认 H2 环境下正常返回。

### 3.5 前端修复

1. 前端 Vite 开发端口改为 `3069`。
2. 前端代理目标改为 `http://localhost:8069`。
3. 请求拦截器自动补齐 `Bearer` token。
4. HTTP `401` 响应会清理本地登录态并跳转登录页。
5. 新增前端 `package-lock.json`，保证依赖安装可复现。

### 3.6 文档与测试修复

1. 新增后端冒烟测试 `TeacherEvaluationApplicationSmokeTest`。
2. 新增 `069-backend/README.md`，补齐默认 H2 启动、MySQL profile、默认账号、前端端口和验证命令。
3. 新增 `069-backend/启动说明.txt`，便于答辩和本地演示。
4. 新增本巡检报告，并同步更新总台账。

## 4. 当前实现与目标能力对照

### 4.1 已具备能力

1. 后端默认 H2 自举，可直接执行 `mvn spring-boot:run` 启动。
2. 管理员、教师和学生账号可登录。
3. 登录响应不回传 `password` 字段。
4. 未登录访问受保护接口返回 HTTP `401`。
5. 学生访问用户管理返回 HTTP `403`。
6. 管理员可访问用户、科目、班级、教师、指标和看板接口。
7. 学生可查看本班任务并提交评教。
8. 教师可查询本人被评记录并发起申诉。
9. 管理员可处理教师申诉。
10. 管理员可发布公告，全角色可查看有效公告。
11. 看板统计和 7 天趋势接口可正常返回数据。
12. 登出后旧 token 失效，重新访问用户信息返回 HTTP `401`。
13. 前端登录、代理和鉴权请求头已与后端主链路对齐。

### 4.2 仍有差距

1. 密码仍为明文存储，生产环境建议改为 BCrypt。
2. Redis 配置仍保留为生产扩展项，默认演示链路不依赖 Redis。
3. 评教指标权重当前偏静态演示，复杂权重模型和统计维度仍可继续增强。
4. 任务发布、评教截止和申诉处理尚未接入消息通知。
5. Excel 导入导出、批量用户/任务维护和更细粒度权限仍未完整落地。
6. 前端构建存在 Vite CJS API 过时提示和 chunk 体积提示，当前不影响构建产物生成。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`069-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0, Skipped: 0`
- 覆盖内容：
  1. 默认 H2 上下文可启动
  2. SQL 初始化中文数据为 UTF-8
  3. 未登录访问看板返回 HTTP `401`
  4. 管理员、教师、学生登录成功且密码字段不回传
  5. 学生访问用户管理返回 HTTP `403`
  6. 管理员用户、科目、班级、教师、指标和看板接口成功
  7. 学生任务列表接口成功
  8. 学生提交评教成功
  9. 教师查看本人评教记录成功
  10. 教师发起申诉成功
  11. 管理员处理申诉成功
  12. 管理员发布公告成功
  13. 管理员登出后旧 token 返回 HTTP `401`

### 5.2 前端构建

- 执行命令：`069-frontend/npm run build`
- 结果：`通过`
- 备注：Vite 输出 CJS API 过时提示和 chunk 体积提示，不影响构建产物生成。

### 5.3 启动与接口抽测

- 后端启动命令：`069-backend/mvn spring-boot:run`
- 结果：`通过`
- 抽测地址：
  1. 未登录看板：`GET http://localhost:8069/api/dashboard/stats`
  2. 管理员登录：`POST http://localhost:8069/api/auth/login`
  3. 教师登录：`POST http://localhost:8069/api/auth/login`
  4. 学生登录：`POST http://localhost:8069/api/auth/login`
  5. 学生越权用户管理：`GET http://localhost:8069/api/user/page`
  6. 用户、科目、班级、教师、指标和看板查询接口
  7. 学生任务列表：`GET http://localhost:8069/api/task/my-page`
  8. 学生提交评教：`POST http://localhost:8069/api/record`
  9. 教师评教记录：`GET http://localhost:8069/api/record/my-page`
  10. 教师发起申诉：`POST http://localhost:8069/api/appeal`
  11. 管理员处理申诉：`PUT http://localhost:8069/api/appeal/handle`
  12. 管理员发布公告：`POST http://localhost:8069/api/notice`
  13. 管理员登出：`POST http://localhost:8069/api/auth/logout`
- 关键业务结果：
  1. 未登录看板返回 HTTP `401`
  2. 管理员、教师、学生登录业务码 `200`，登录响应未包含 `password` 字段
  3. 学生访问用户管理返回 HTTP `403`
  4. 用户、科目、班级、教师、指标、任务、评教、申诉、公告和看板相关接口均返回业务码 `200`
  5. 学生提交评教成功，抽测记录 ID 为 `2`
  6. 教师发起申诉成功，抽测申诉 ID 为 `2`
  7. 管理员处理申诉成功
  8. 管理员发布公告成功
  9. 管理员登出业务码 `200`，旧 token 再访问用户信息返回 HTTP `401`

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- MySQL 兼容配置：`069-backend/src/main/resources/application-mysql.yml`
- MySQL 初始化脚本：`069-backend/sql/init.sql`
- 默认管理员：`admin / 123456`
- 默认教师：`teacher1 / 123456`
- 默认学生：`student1 / 123456`
- 说明：
  1. 直接执行 `069-backend/mvn spring-boot:run` 即可使用默认 H2 环境启动后端。
  2. H2 控制台地址为 `http://localhost:8069/h2-console`，JDBC URL 为 `jdbc:h2:mem:teacher_eval_069`。
  3. 如需切换 MySQL，请先确认 `sql/init.sql` 中的建库/删库语句可接受，再运行 `mysql -uroot -p1234 < sql/init.sql`。
  4. 当前 MySQL profile 默认使用 `root / 1234`。
  5. 前端执行 `npm run dev` 后默认访问 `http://localhost:3069`。

## 7. 备注

1. 本轮后端真实启动使用端口 `8069`。
2. 本机 `3306` 可用于 MySQL profile，但原始 SQL 脚本可能包含建库/删库语句，为避免破坏本机数据，默认验证采用 H2 自举链路。
3. `mvn` 输出中的 `rdc` profile 警告来自外部 Maven 环境，不影响本项目测试和启动结论。
4. `target/`、`node_modules/`、`dist/` 和 `.tmp/` 临时日志等生成物不纳入提交。
