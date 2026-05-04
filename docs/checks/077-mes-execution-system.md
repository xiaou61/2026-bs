# 077 MES生产制造执行系统检查报告

## 1. 检查结论

- 项目编号：`077`
- 项目名称：`基于Vue的MES生产制造执行系统`
- 检查日期：`2026-05-02`
- 当前状态：`已完成`
- 综合结论：`本轮已修复默认环境强依赖 MySQL/Redis/8080、H2 自举缺失、MySQL 驱动旧坐标、后端无自动化测试、登录响应 password 字段泄露、JWT Bearer 不兼容、未登录/越权 HTTP 状态不准确、登出后 token 不失效、普通用户可访问管理接口、生产主管/计划员执行工单和评价申诉归属校验不足、前端代理仍指向 8080、前端请求头缺少 Bearer token 等问题。当前后端可在默认 H2 环境下启动并通过自动化冒烟测试，前端可完成生产构建，真实 HTTP MES 工单主链路和前端代理登录复测通过。`

## 2. 项目形态

- 后端目录：`077-backend`
- 前端目录：`077-frontend`
- 后端技术栈：`Spring Boot 2.7.18 + MyBatis-Plus 3.5.5 + JWT + H2/MySQL + Redis 依赖保留`
- 前端技术栈：`Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts`
- 默认后端端口：`8077`
- 前端开发端口：`3077`

## 3. 本轮修复

1. 后端默认端口从 `8080` 调整为 `8077`。
2. 默认 `application.yml` 改为 H2 内存库自举，新增 `db/schema-h2.sql` 与 `db/data-h2.sql`。
3. 新增 `application-mysql.yml`，保留原 MySQL 部署入口。
4. 修复 MySQL 驱动坐标为 `com.mysql:mysql-connector-j`。
5. 项目编译目标改为 JDK 17，并增加 H2 与 Spring Boot Test 依赖。
6. MyBatis-Plus 分页方言改为配置化，默认使用 `H2`，MySQL profile 使用 `MYSQL`。
7. `User.password` 改为 Jackson `WRITE_ONLY`，登录响应、用户信息响应和前端代理登录均不再回传密码字段。
8. JWT 拦截器兼容 `Authorization: Bearer <token>`。
9. 新增本地运行态 token 失效表，默认演示环境不再依赖 Redis 才能完成登录/登出闭环。
10. 全局异常处理改为真实 HTTP 状态：未登录/失效 `401`，越权 `403`，参数错误 `400`。
11. 管理型接口补充服务端管理员校验。
12. 生产任务编辑/删除、执行工单支付/确认/完成/取消、评价和申诉补充本人归属校验。
13. 前端 Vite 开发端口改为 `3077`，代理目标改为 `http://localhost:8077`。
14. 前端请求拦截器自动补齐 `Bearer` token，并在 HTTP `401` 时清理登录态。
15. 新增后端冒烟测试 `MesExecutionApplicationSmokeTest`。

## 4. 已具备能力

1. 后端默认 H2 自举，可直接执行 `mvn spring-boot:run` 启动。
2. 管理员、生产主管和计划员账号可登录。
3. 登录响应不回传 `password` 字段。
4. 未登录访问受保护接口返回 HTTP `401`。
5. 计划员访问管理看板返回 HTTP `403`。
6. 管理员可访问看板、申诉分页等后台接口。
7. 计划员可浏览生产任务、工艺分类和公告。
8. 计划员可创建执行工单、支付工单、完成工单、评价、收藏和发起申诉。
9. 非工单所属生产主管无法确认工单，返回 HTTP `403`。
10. 工单所属生产主管可确认工单。
11. 登出后旧 token 失效，重新访问用户信息返回 HTTP `401`。
12. 前端构建和代理登录已与后端主链路对齐。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`077-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0, Skipped: 0`
- 覆盖内容：登录脱敏、未登录 `401`、计划员看板越权 `403`、生产任务/分类/公告列表、执行工单创建、支付、生产主管确认归属、完成、评价、收藏、申诉、管理员申诉分页和登出失效。

### 5.2 前端构建

- 执行命令：`077-frontend/npm install`
- 结果：`通过`
- 执行命令：`077-frontend/npm run build`
- 结果：`通过`
- 备注：Vite 输出 CJS API 过期提示和 chunk 体积提示，不影响构建产物生成。

### 5.3 启动与接口抽测

- 后端启动命令：`077-backend/mvn spring-boot:run`
- 结果：`通过`
- 后端端口：`8077`
- 真实 HTTP 抽测结果：
  1. `GET /api/auth/info` 未登录返回 HTTP `401`
  2. `POST /api/auth/login` 管理员、生产主管、计划员登录均返回 HTTP `200`，且无 `password` 字段
  3. 计划员访问 `GET /api/dashboard/stats` 返回 HTTP `403`
  4. 管理员访问 `GET /api/dashboard/stats` 返回 HTTP `200`
  5. 计划员访问生产任务、分类和公告列表返回 HTTP `200`
  6. 计划员创建执行工单、支付工单返回 HTTP `200`
  7. `planner1` 对 `planner2` 生产任务工单确认返回 HTTP `403`
  8. `planner2` 对本人生产任务工单确认返回 HTTP `200`
  9. 计划员确认完成、评价、收藏和申诉返回 HTTP `200`
  10. 管理员访问申诉分页返回 HTTP `200`
  11. 管理员登出返回 HTTP `200`
  12. 登出后访问 `GET /api/auth/info` 返回 HTTP `401`

### 5.4 前端代理联调

- 后端启动端口：`8077`
- 前端启动命令：`077-frontend/npm run dev -- --host 127.0.0.1`
- 前端端口：`3077`
- 结果：`通过`
- 关键结果：
  1. `GET http://127.0.0.1:3077/` 返回 HTTP `200`
  2. `POST http://127.0.0.1:3077/api/auth/login` 返回 HTTP `200`，业务码 `200`，角色为 `ADMIN`，token 存在，且无 `password` 字段

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- MySQL 兼容配置：`077-backend/src/main/resources/application-mysql.yml`
- MySQL 初始化脚本：`077-backend/sql/init.sql`
- 默认管理员：`admin / 123456`
- 默认生产主管一：`planner1 / 123456`
- 默认生产主管二：`planner2 / 123456`
- 默认计划员：`operator1 / 123456`

## 7. 仍有差距

1. 密码仍为明文存储，生产环境建议改为 BCrypt。
2. 默认演示模式使用 H2 内存库，MySQL profile 需要按目标机器单独导入 `sql/init.sql`。
3. Redis 依赖保留但当前默认演示链路未使用分布式会话。
4. 支付、退款、排产和工艺执行为模拟状态流转，未接入真实 MES 设备或第三方支付。
5. 图片上传和对象存储能力仍为基础实现。
6. 产能扣减未实现高并发锁库存策略。
7. 前端构建存在 chunk 体积提示，当前不影响构建产物生成。
