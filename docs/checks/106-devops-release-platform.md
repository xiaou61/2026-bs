# 106 DevOps 发布审批与回滚管理系统检查报告

## 检查范围

- 项目编号：`106`
- 项目名称：DevOps 发布审批与回滚管理系统
- 检查时间：`2026-05-20`
- 目录：
  - 后端：`106-backend`
  - 前端：`106-frontend`
- 主要对照文档：
  - `106-backend/PRD.md`
  - `106-backend/PLAN.md`
  - `106-backend/sql/init.sql`
  - `106-backend/src/main/resources/application.yml`
  - `106-frontend/src/router/index.js`
  - `106-frontend/src/views/Layout.vue`

## 结论摘要

`106` 当前建议标记为 `检查中`。本轮静态对照未发现明显的 PRD 失真或主题串项：

1. `PRD.md`、`PLAN.md`、`sql/init.sql` 与 `application.yml` 在数据库名、端口和默认账号口径上保持一致。
2. 前端路由、菜单已经覆盖发布环境、应用服务、流水线、发布计划、发布单、审批流程、审批记录、版本制品、部署任务、回滚预案、回滚记录、变更检查和操作日志等核心模块。
3. 后端角色断言与关键状态动作已经落地，能静态对应发布单提交/审批/排期、部署启动/完成/失败、回滚启动/完成/失败、审批记录通过/驳回、计划发布/归档等闭环动作。

当前主要缺口仍是运行验证：`PLAN.md` 继续把“不执行编译验证”列为完成标准，本轮也尚未执行 Maven / NPM / 启动验证，因此暂不适合直接标为 `已完成`。

## 主要结论

### 106-1 基础配置与 PRD 口径一致

- `PRD.md` 明确数据库名、前后端端口和默认账号：`106-backend/PRD.md:25-33`
- `PLAN.md` 明确创建 `devops_release_106` 数据库和 14 张业务表：`106-backend/PLAN.md:15`
- 初始化 SQL 直接 `DROP/CREATE/USE devops_release_106`：`106-backend/sql/init.sql:1-3`
- 实际运行配置使用后端端口 `8106`，数据源也指向 `devops_release_106`：`106-backend/src/main/resources/application.yml:2`、`106-backend/src/main/resources/application.yml:6`

说明：

- 本轮未发现数据库串项、端口串项或默认账号口径错配。
- `106` 当前不像 `124` 那样存在基础配置硬伤。

### 106-2 前端路由与菜单基本覆盖 PRD 的 14 个业务模块

- 前端已定义四类角色首页：`ADMIN / RELEASE / OPS / AUDITOR`：`106-frontend/src/router/index.js:4-8`
- 路由层已覆盖看板、账号权限、发布环境、应用服务、流水线、发布计划、发布单、审批流程、审批记录、版本制品、部署任务、回滚预案、回滚记录、变更检查、操作日志：`106-frontend/src/router/index.js:20-35`
- 侧边菜单与路由保持一致：`106-frontend/src/views/Layout.vue:26-40`

说明：

- 从页面入口看，`106` 的功能骨架与 PRD 列出的核心模块是对齐的。
- 本轮未发现前端模块名称仍沿用其他项目主题的情况。

### 106-3 后端权限收口与关键状态动作已基本落地

- JWT 拦截器会写入 `userId / username / role`：`106-backend/src/main/java/com/devopsrelease/config/JwtInterceptor.java:24-31`
- `AuthService` 已抽出 `assertAdmin`、`assertAdminOrRelease`、`assertAdminOrReleaseOrOps`、`assertAdminOrReleaseOrAuditor`、`assertAdminOrOps` 和 `assertAuthenticated`：`106-backend/src/main/java/com/devopsrelease/service/AuthService.java:54-79`
- 发布单已覆盖 `submit / approve / reject / schedule`：`106-backend/src/main/java/com/devopsrelease/controller/ReleaseOrderController.java:62`、`106-backend/src/main/java/com/devopsrelease/controller/ReleaseOrderController.java:70`、`106-backend/src/main/java/com/devopsrelease/controller/ReleaseOrderController.java:78`、`106-backend/src/main/java/com/devopsrelease/controller/ReleaseOrderController.java:86`
- 审批记录已覆盖 `approve / reject`：`106-backend/src/main/java/com/devopsrelease/controller/ApprovalRecordController.java:62`、`106-backend/src/main/java/com/devopsrelease/controller/ApprovalRecordController.java:70`
- 部署任务已覆盖 `start / finish / fail`：`106-backend/src/main/java/com/devopsrelease/controller/DeployTaskController.java:62`、`106-backend/src/main/java/com/devopsrelease/controller/DeployTaskController.java:70`、`106-backend/src/main/java/com/devopsrelease/controller/DeployTaskController.java:78`
- 回滚记录已覆盖 `start / finish / fail`：`106-backend/src/main/java/com/devopsrelease/controller/RollbackRecordController.java:62`、`106-backend/src/main/java/com/devopsrelease/controller/RollbackRecordController.java:70`、`106-backend/src/main/java/com/devopsrelease/controller/RollbackRecordController.java:78`
- 发布计划已覆盖 `publish / archive`：`106-backend/src/main/java/com/devopsrelease/controller/ReleasePlanController.java:62`、`106-backend/src/main/java/com/devopsrelease/controller/ReleasePlanController.java:70`
- 回滚预案已覆盖 `activate / archive`：`106-backend/src/main/java/com/devopsrelease/controller/RollbackPlanController.java:62`、`106-backend/src/main/java/com/devopsrelease/controller/RollbackPlanController.java:70`

说明：

- 从控制器静态结构看，`106` 已经具备发布审批、部署执行和回滚处理的基本闭环动作。
- 本轮未发现类似 `105` 那种“PLAN 写了动作，但前后端都没有落”的明确缺口。

### 106-4 前端按钮与后端动作基本对应

- 发布单页面已接 `提交 / 通过 / 驳回 / 排期`：`106-frontend/src/views/ReleaseOrder.vue:8-18`
- 部署任务页面已接 `启动 / 完成 / 失败`：`106-frontend/src/views/DeployTask.vue:8-17`
- 回滚记录页面已接 `启动 / 完成 / 失败`：`106-frontend/src/views/RollbackRecord.vue:8-17`
- 审批记录页面已接 `通过 / 驳回`：`106-frontend/src/views/ApprovalRecord.vue:8-16`
- 发布计划页面已接 `发布 / 归档`：`106-frontend/src/views/ReleasePlan.vue:8-16`
- 回滚预案页面已接 `启用 / 归档`：`106-frontend/src/views/RollbackPlan.vue:8-16`
- 对应前端 API 封装也都已存在：`106-frontend/src/api/index.js:24-33`、`106-frontend/src/api/index.js:40-58`、`106-frontend/src/api/index.js:65-74`

说明：

- 静态上可以确认“页面按钮 -> API -> controller 状态动作”这一链路基本打通。
- `PLAN.md` 中“确认”一词当前更像由审批通过、任务完成等动作覆盖，是否完全符合业务语义仍建议在联调时再确认。

### 106-5 当前缺口仍是编译与启动验证

- `PLAN.md` 完成标准仍明确写着“不执行编译验证”：`106-backend/PLAN.md:40`

影响：

- 当前只能说明 `106` 在文档、配置、页面入口和动作接口层面基本对齐。
- 还不能证明它在当前环境下能通过 JDK17 编译、前端构建和基础登录/页面联调。

## 静态核对记录

- 本轮已核对：
  - `PRD.md`
  - `PLAN.md`
  - `sql/init.sql`
  - `application.yml`
  - 前端 `router/index.js`
  - 前端 `Layout.vue`
  - 前端 `api/index.js`
  - 前端关键业务页
  - 后端 `JwtInterceptor`
  - 后端 `AuthService`
  - 后端关键 controller
- 静态结构计数：
  - SQL 建表：`14`
  - Controller：`16`
  - 前端视图：`17`
- 本轮未执行 Maven / NPM 编译与启动验证。

## 当前判断

`106` 当前状态建议标记为：`检查中`

- 数据库、端口、默认账号口径：`通过`
- 路由、菜单、角色骨架：`基本通过`
- 核心状态动作：`基本通过`
- 编译与启动验证：`本轮未执行`

## 建议下一步

1. 先补 `106-backend` 的 `mvn.cmd test` 或至少一次编译验证。
2. 再补 `106-frontend` 的 `npm.cmd run build` 与一次基础登录/页面冒烟验证。
3. 若编译、启动和核心状态接口联调通过，再将 `106` 从 `检查中` 转为 `已完成`。
