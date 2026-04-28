# 057 招生管理系统检查报告

## 1. 检查结论

- 项目编号：`057`
- 项目名称：`招生管理系统`
- 检查日期：`2026-04-28`
- 当前状态：`已完成`
- 综合结论：`本轮已修复默认环境强依赖 MySQL/8080、H2 自举缺失、分页方言固定 MySQL、JWT 不兼容 Bearer token、未登录/越权仍返回 HTTP 200、普通管理员可访问管理员管理接口、Redis 不可用会阻断默认演示登录、管理员密码响应泄露风险、管理端代理仍指向 8080 等问题。当前后端可在默认 H2 环境下启动并通过自动化冒烟测试，管理端可完成生产构建，真实 HTTP 核心链路抽测通过。`

## 2. 项目形态

- 后端目录：`057-backend`
- 前端目录：`057-frontend`
- 后端技术栈：`Spring Boot 2.7.0 + MyBatis-Plus 3.5.2 + JWT + H2/MySQL + Redis 可选`
- 前端技术栈：`Vue 3 + Vite 5 + Element Plus + Pinia + ECharts`
- 默认后端端口：`8057`
- 前端开发端口：`3057`

## 3. 本轮修复

### 3.1 默认运行环境修复

1. 将后端默认端口从 `8080` 调整为 `8057`，避免合集内项目端口冲突。
2. 默认 `application.yml` 改为 H2 内存库自举，新增 `schema-h2.sql` 与 `data-h2.sql`。
3. 新增 `application-mysql.yml`，保留 MySQL 部署模式，通过 `-Dspring-boot.run.profiles=mysql` 切换。
4. 更新 MySQL 驱动坐标为 `com.mysql:mysql-connector-j:8.0.33`。
5. 增加 H2 与 Spring Boot Test 依赖。
6. MyBatis-Plus 分页插件改为自动识别数据库方言，兼容默认 H2 与 MySQL profile。
7. H2 初始化数据年份更新至 2026/2027，便于当前演示。
8. Redis 不可用时不阻断登录和退出登录主流程，默认演示链路只依赖 JWT。

### 3.2 登录、鉴权与权限修复

1. `Admin.password` 改为 `WRITE_ONLY`，支持登录/改密写入但响应不再回传密码字段。
2. JWT 拦截器兼容 `Bearer` token。
3. JWT 拦截器兼容 `OPTIONS` 预检请求。
4. JWT 拦截器补充 token 对应管理员存在和启用状态校验。
5. 未登录、token 无效和账号禁用场景直接返回真实 HTTP `401`。
6. 普通管理员访问 `/api/admin/**` 返回真实 HTTP `403`。
7. 修改密码补充用户不存在和新密码为空校验。

### 3.3 管理端修复

1. 管理端 Vite 开发端口改为 `3057`。
2. 管理端代理目标改为 `http://localhost:8057`。
3. 请求拦截器自动补齐 `Bearer` token。
4. 新增管理端 `package-lock.json`，保证依赖安装可复现。

### 3.4 文档与测试修复

1. 新增后端冒烟测试 `EnrollmentApplicationSmokeTest`。
2. 新增 `057-backend/README.md`，补齐默认 H2 启动、MySQL profile、默认账号、前端端口和验证命令。
3. 新增 `057-backend/启动说明.txt`，便于答辩和本地演示。
4. 新增本巡检报告，并同步更新总台账。

## 4. 当前实现与目标能力对照

### 4.1 已具备能力

1. 后端默认 H2 自举，可直接执行 `mvn spring-boot:run` 启动。
2. 默认超级管理员和普通管理员账号可登录。
3. 未登录访问受保护接口返回 HTTP `401`。
4. 普通管理员访问管理员管理接口返回 HTTP `403`。
5. 超级管理员可访问管理员管理、统计概览、院系、专业、招生计划、考生、成绩、报名、录取和公告接口。
6. 可新增考生、审核考生、录入成绩、提交报名、审核报名、新增录取和确认录取。
7. 前端登录、代理和鉴权请求头已与后端主链路对齐。

### 4.2 仍有差距

1. 密码仍为明文存储，生产环境建议改为 BCrypt。
2. Redis 配置仍保留为生产扩展项，默认演示链路不依赖 Redis。
3. 当前权限拦截为基础角色模型，更细粒度的菜单和操作权限仍可继续增强。
4. 批量成绩导入当前接收 JSON 数组，尚不是 Excel 文件上传导入。
5. 录取流程偏简化，自动录取、通知书生成和复杂志愿规则仍可继续完善。
6. 前端构建存在 Vite chunk 体积提示，当前不影响构建产物生成。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`057-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0, Skipped: 0`
- 覆盖内容：
  1. 默认 H2 上下文可启动
  2. 未登录访问用户信息返回 HTTP `401`
  3. 超级管理员和普通管理员登录成功且密码字段不回传
  4. 普通管理员访问管理员管理接口返回 HTTP `403`
  5. 超级管理员访问管理员管理和统计接口成功
  6. 普通管理员访问院系列表成功
  7. 专业分页和招生计划分页成功
  8. 考生新增和审核成功
  9. 成绩录入、报名新增、报名审核成功
  10. 录取新增、录取确认和公告分页成功

### 5.2 前端构建

- 执行命令：`057-frontend/npm run build`
- 结果：`通过`
- 备注：Vite 输出 chunk 体积提示，不影响构建产物生成。

### 5.3 启动与接口抽测

- 后端启动命令：`057-backend/mvn spring-boot:run`
- 结果：`通过`
- 抽测地址：
  1. 未登录用户信息：`GET http://localhost:8057/api/auth/info`
  2. 超级管理员登录：`POST http://localhost:8057/api/auth/login`
  3. 普通管理员登录：`POST http://localhost:8057/api/auth/login`
  4. 管理员分页越权验证：`GET http://localhost:8057/api/admin/page`
  5. 统计概览：`GET http://localhost:8057/api/stats/dashboard`
  6. 院系列表：`GET http://localhost:8057/api/department/list`
  7. 专业分页：`GET http://localhost:8057/api/major/page`
  8. 招生计划分页：`GET http://localhost:8057/api/plan/page`
  9. 考生新增和审核：`/api/student/**`
  10. 成绩、报名、录取和公告：`/api/score/**`、`/api/application/**`、`/api/admission/**`、`/api/notice/**`
- 关键业务结果：
  1. 未登录访问用户信息返回 HTTP `401`
  2. 超级管理员和普通管理员登录均返回业务码 `200`
  3. 普通管理员访问管理员管理接口返回 HTTP `403`
  4. 统计、院系、专业、计划、考生、成绩、报名、录取和公告接口均返回业务码 `200`
  5. 联调结束后 `8057` 无 LISTENING 残留端口

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- MySQL 兼容配置：`057-backend/src/main/resources/application-mysql.yml`
- MySQL 初始化脚本：`057-backend/sql/init.sql`
- 默认超级管理员：`admin / 123456`
- 默认普通管理员：`user / 123456`
- 说明：
  1. 直接执行 `057-backend/mvn spring-boot:run` 即可使用默认 H2 环境启动后端。
  2. H2 控制台地址为 `http://localhost:8057/h2-console`，JDBC URL 为 `jdbc:h2:mem:enrollment_db`。
  3. 如需切换 MySQL，可先导入 `057-backend/sql/init.sql`，再运行 `mvn spring-boot:run -Dspring-boot.run.profiles=mysql`。
  4. 前端执行 `npm run dev` 后默认访问 `http://localhost:3057`。

## 7. 备注

1. 本轮后端真实启动使用端口 `8057`。
2. `mvn` 输出中的 `rdc` profile 警告来自外部 Maven 环境，不影响本项目测试和启动结论。
3. `target/`、`node_modules/`、`dist/` 和 `.tmp/` 临时日志等生成物不纳入提交。
