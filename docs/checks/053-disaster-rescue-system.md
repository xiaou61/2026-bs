# 053 基于 SpringBoot 的救灾物资调度与救援系统检查报告

## 1. 检查结论

- 项目编号：`053`
- 项目名称：`基于 SpringBoot 的救灾物资调度与救援系统`
- 检查日期：`2026-04-28`
- 当前状态：`已完成`
- 综合结论：`本轮已修复默认环境强依赖 MySQL/8080、H2 自举缺失、分页方言固定 MySQL、后端缺少自动化测试、登录响应存在密码泄露风险、统计/用户管理接口缺少服务端管理员校验、前端登录 token 写入错误、Authorization 请求头缺少 Bearer、前端代理仍指向 8080、前端 API 路径与后端控制器不一致、灾情字段前后端口径不一致、任务状态接口缺失等问题。当前后端可在默认 H2 环境下启动并通过自动化冒烟测试，前端可完成生产构建，真实 HTTP 核心链路抽测通过。`

## 2. 项目形态

- 后端目录：`053-backend`
- 前端目录：`053-frontend`
- 后端技术栈：`Spring Boot 3.2.0 + MyBatis-Plus 3.5.5 + JWT + H2/MySQL`
- 前端技术栈：`Vue 3 + Vite 5 + Element Plus + ECharts`
- 默认后端端口：`8053`
- 前端开发端口：`3053`

## 3. 本轮修复

### 3.1 默认运行环境修复

1. 将后端默认端口从 `8080` 调整为 `8053`，避免合集内项目端口冲突。
2. 默认 `application.yml` 改为 H2 内存库自举，新增 `schema-h2.sql` 与 `data-h2.sql`。
3. 新增 `application-mysql.yml`，保留 MySQL 部署模式，通过 `-Dspring-boot.run.profiles=mysql` 切换。
4. 增加 H2 和 Spring Boot Test 依赖。
5. MyBatis-Plus 分页插件改为自动识别数据库方言，兼容默认 H2 与 MySQL profile。

### 3.2 登录、鉴权与接口修复

1. `User.password` 增加 JSON 脱敏，登录和用户信息响应不再返回密码字段。
2. 登录返回前清理用户密码字段，降低敏感字段外泄风险。
3. 修改密码补充用户不存在校验，避免空指针异常。
4. JWT 拦截器兼容 `OPTIONS` 大小写。
5. JWT 拦截器补充管理员接口权限校验，普通信息员访问 `/api/stats/**` 和用户管理接口返回 HTTP `403`。
6. 补齐用户管理新增、更新和删除接口，匹配前端页面操作。
7. 补齐救援任务更新和通用状态变更接口，匹配前端页面操作。

### 3.3 前端修复

1. 前端 Vite 开发端口改为 `3053`。
2. 前端代理目标改为 `http://localhost:8053`。
3. 请求拦截器自动补齐 `Bearer` token。
4. 登录页改为保存 `res.data.token`，不再把整个 data 对象当成 token。
5. 前端 API 适配后端真实接口路径：`/list`、`/all`、`/add`、`/update`、`/report`、`/create` 等。
6. 前端灾情数据适配后端字段：`title/address/reportTime` 映射到页面使用的 `name/location/happenTime`。
7. 新增前端 `package-lock.json`，保证依赖安装可复现。

### 3.4 文档与测试修复

1. 新增后端冒烟测试 `DisasterApplicationSmokeTest`。
2. 新增 `053-backend/README.md`，补齐默认 H2 启动、MySQL profile、默认账号、前端端口和验证命令。
3. 新增 `053-backend/启动说明.txt`，便于答辩和本地演示。
4. 新增本巡检报告，并同步更新总台账。

## 4. 当前实现与目标能力对照

### 4.1 已具备能力

1. 后端默认 H2 自举，可直接执行 `mvn spring-boot:run` 启动。
2. 默认管理员、仓库管理员、救援人员和信息员账号可登录。
3. 管理员可查看统计概览、用户、物资分类、物资、仓库、库存、灾情、调度、救援任务和公告。
4. 信息员可上报灾情。
5. 可新增库存入库记录。
6. 可创建调度单并审批，审批通过后自动扣减库存。
7. 可创建救援任务并更新任务状态。
8. 前端登录、代理、API 路径和灾情字段已与后端主链路对齐。

### 4.2 仍有差距

1. 密码仍使用 MD5 存储，生产环境建议改为 BCrypt。
2. Redis 配置仍保留为生产扩展项，默认演示链路不依赖 Redis。
3. 上传图片、地图定位、调度路线和更细粒度角色权限仍可继续增强。
4. 前端部分表格展示字段仍偏基础，例如物资分类名、仓库负责人等未做复杂联表展示。
5. 前端构建存在 Vite chunk 体积提示，当前不影响构建产物生成。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`053-backend/mvn clean test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0, Skipped: 0`
- 覆盖内容：
  1. 默认 H2 上下文可启动
  2. 管理员和信息员登录成功且响应不包含密码字段
  3. 信息员访问统计接口返回 HTTP `403`
  4. 管理员访问统计接口成功
  5. 用户信息、物资分类、物资列表、仓库列表返回演示数据
  6. 信息员可上报灾情
  7. 管理员可创建救援任务并更新任务状态
  8. 管理员可执行物资入库
  9. 管理员可创建调度单并审批
  10. 已发布公告列表返回演示数据

### 5.2 前端构建

- 执行命令：`053-frontend/npm install --no-audit --no-fund`
- 结果：`通过`
- 执行命令：`053-frontend/npm run build`
- 结果：`通过`
- 备注：Vite 输出 chunk 体积提示和 CJS API 弃用提示，不影响构建产物生成。

### 5.3 启动与接口抽测

- 后端启动命令：`053-backend/mvn spring-boot:run`
- 结果：`通过`
- 抽测地址：
  1. 管理员登录：`POST http://localhost:8053/api/user/login`
  2. 信息员登录：`POST http://localhost:8053/api/user/login`
  3. 统计概览：`GET http://localhost:8053/api/stats/overview`
  4. 用户信息：`GET http://localhost:8053/api/user/info`
  5. 分类列表：`GET http://localhost:8053/api/category/list`
  6. 物资列表：`GET http://localhost:8053/api/material/list`
  7. 仓库列表：`GET http://localhost:8053/api/warehouse/list`
  8. 库存列表：`GET http://localhost:8053/api/stock/list`
  9. 灾情上报：`POST http://localhost:8053/api/disaster/report`
  10. 灾情列表：`GET http://localhost:8053/api/disaster/list`
  11. 物资入库：`POST http://localhost:8053/api/stock/in`
  12. 创建任务：`POST http://localhost:8053/api/task/create`
  13. 更新任务状态：`PUT http://localhost:8053/api/task/{id}/status`
  14. 创建调度：`POST http://localhost:8053/api/dispatch/create`
  15. 审批调度：`PUT http://localhost:8053/api/dispatch/{id}/approve`
  16. 已发布公告：`GET http://localhost:8053/api/notice/published`
- 关键业务结果：
  1. 管理员和信息员登录均返回业务码 `200`，且未返回密码字段
  2. 信息员访问统计接口返回 HTTP `403`
  3. 统计、用户信息、分类、物资、仓库、库存接口均返回业务码 `200`
  4. 灾情上报、任务创建、任务状态更新、物资入库、调度创建和调度审批均返回业务码 `200`
  5. 已发布公告接口返回业务码 `200`
  6. 联调结束后 `8053` 无 LISTENING 残留端口

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- MySQL 兼容配置：`053-backend/src/main/resources/application-mysql.yml`
- MySQL 初始化脚本：`053-backend/sql/init.sql`
- 默认管理员：`admin / 123456`
- 默认仓库管理员：`warehouse / 123456`
- 默认救援人员：`rescuer / 123456`
- 默认信息员：`reporter / 123456`
- 说明：
  1. 直接执行 `053-backend/mvn spring-boot:run` 即可使用默认 H2 环境启动后端。
  2. H2 控制台地址为 `http://localhost:8053/h2-console`，JDBC URL 为 `jdbc:h2:mem:disaster_rescue`。
  3. 如需切换 MySQL，可先导入 `053-backend/sql/init.sql`，再运行 `mvn spring-boot:run -Dspring-boot.run.profiles=mysql`。
  4. 前端执行 `npm run dev` 后默认访问 `http://localhost:3053`。

## 7. 备注

1. 本轮后端真实启动使用端口 `8053`。
2. `mvn` 输出中的 `rdc` profile 警告来自外部 Maven 环境，不影响本项目测试和启动结论。
3. `target/`、`node_modules/`、`dist/` 和 `.tmp/` 临时日志等生成物不纳入提交。
