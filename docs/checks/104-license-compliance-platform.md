# 104 开源许可证合规扫描与项目台账系统检查报告

## 检查范围

- 项目编号：`104`
- 项目名称：开源许可证合规扫描与项目台账系统
- 检查时间：`2026-05-20`
- 目录：
  - 后端：`104-backend`
  - 前端：`104-frontend`
- 主要对照文档：
  - `104-backend/PRD.md`
  - `104-backend/PLAN.md`
  - `104-backend/sql/init.sql`
  - `104-backend/src/main/resources/application.yml`
  - `104-frontend/src/router/index.js`
  - `104-frontend/src/views/Layout.vue`

## 结论摘要

`104` 当前建议标记为 `检查中`。本轮静态对照未发现明显的 PRD 失真：

1. `PRD.md`、`PLAN.md`、`sql/init.sql` 与 `application.yml` 在数据库名、端口和默认账号口径上保持一致。
2. 前端菜单、路由和角色首页已经覆盖 PRD 定义的 14 个业务模块与 4 类角色。
3. 后端控制器提供了与 PRD 闭环相符的状态动作，例如扫描任务启动/完成/失败、风险问题确认/忽略、整改任务指派/完成/重开、审计报告发布/归档和审批通过/驳回。

当前主要缺口不是主题串项或权限失真，而是 `PLAN.md` 仍把“不执行编译验证”列为完成标准，本轮也未执行 Maven / NPM / 启动验证，因此暂不能上升为“已完成”。

## 主要结论

### 104-1 PRD、PLAN、SQL 与运行配置口径一致

- `PRD.md` 明确数据库名、前后端端口和默认账号：`104-backend/PRD.md:25-33`
- `PLAN.md` 明确创建 `license_compliance_104` 数据库和 14 张业务表：`104-backend/PLAN.md:15`
- 初始化 SQL 直接 `DROP/CREATE/USE license_compliance_104`：`104-backend/sql/init.sql:1-3`
- 实际运行配置使用后端端口 `8104`，数据源也指向 `license_compliance_104`：`104-backend/src/main/resources/application.yml:2`、`104-backend/src/main/resources/application.yml:6`

说明：

- 本轮未发现 `104` 存在类似 `124` 那种数据库串到其他项目的硬性配置错配。
- PRD、PLAN、SQL 和 `application.yml` 的基础口径目前是对齐的。

### 104-2 前端模块与角色入口基本覆盖 PRD 的 14 个业务模块

- 路由层已定义四类角色首页：`ADMIN`、`COMPLIANCE`、`DEVELOPER`、`AUDITOR`：`104-frontend/src/router/index.js:4-8`
- 路由层已覆盖看板、账号权限、组织团队、项目台账、分支版本、依赖台账、许可证策略、合规基线、扫描任务、扫描结果、风险问题、整改任务、审计报告、审批记录、操作日志：`104-frontend/src/router/index.js:20-34`
- 侧边菜单与路由的角色边界保持一致：`104-frontend/src/views/Layout.vue:28-41`

说明：

- `账号权限` 只向 `ADMIN` 开放。
- `许可证策略`、`审计报告`、`审批记录`、`操作日志` 仅向 `ADMIN / COMPLIANCE / AUDITOR` 开放。
- `组织团队` 开放给 `ADMIN / COMPLIANCE / DEVELOPER`，其余台账与过程页按 PRD 场景向四角色或三角色开放，整体没有出现明显的前端角色缺页。

### 104-3 后端权限收口与状态动作基本对齐 PRD 闭环

- `JwtInterceptor` 会校验 token 有效性，并把 `userId / username / role` 写入请求上下文：`104-backend/src/main/java/com/licensecheck/config/JwtInterceptor.java:24-35`
- `AuthService` 已抽出 `assertAdmin`、`assertAdminOrCompliance`、`assertAdminOrComplianceOrDeveloper`、`assertAdminOrComplianceOrAuditor` 和 `assertAuthenticated` 五类角色断言：`104-backend/src/main/java/com/licensecheck/service/AuthService.java:60-80`
- 账号权限后端仅允许管理员访问：`104-backend/src/main/java/com/licensecheck/controller/SysUserController.java:33-54`
- 组织团队后端允许 `ADMIN / COMPLIANCE / DEVELOPER` 查看，写操作收口到 `ADMIN / COMPLIANCE`：`104-backend/src/main/java/com/licensecheck/controller/OrganizationTeamController.java:33-54`
- 扫描任务已覆盖 `start / finish / fail` 状态动作：`104-backend/src/main/java/com/licensecheck/controller/ScanTaskController.java:62`、`104-backend/src/main/java/com/licensecheck/controller/ScanTaskController.java:70`、`104-backend/src/main/java/com/licensecheck/controller/ScanTaskController.java:78`
- 风险问题已覆盖 `confirm / ignore`：`104-backend/src/main/java/com/licensecheck/controller/RiskIssueController.java:62`、`104-backend/src/main/java/com/licensecheck/controller/RiskIssueController.java:70`
- 整改任务已覆盖 `assign / finish / reopen`：`104-backend/src/main/java/com/licensecheck/controller/RectificationTaskController.java:62`、`104-backend/src/main/java/com/licensecheck/controller/RectificationTaskController.java:70`、`104-backend/src/main/java/com/licensecheck/controller/RectificationTaskController.java:78`
- 审计报告已覆盖 `publish / archive`：`104-backend/src/main/java/com/licensecheck/controller/AuditReportController.java:62`、`104-backend/src/main/java/com/licensecheck/controller/AuditReportController.java:70`
- 审批记录已覆盖 `approve / reject`：`104-backend/src/main/java/com/licensecheck/controller/ApprovalRecordController.java:62`、`104-backend/src/main/java/com/licensecheck/controller/ApprovalRecordController.java:70`

说明：

- 从静态结构看，`104` 已经把 PRD 提到的“扫描、风险、整改、报告、审批”闭环动作落到了控制器层。
- 本轮未发现明显“前端只收口、后端却全角色放开”的典型模板偏差。

### 104-4 当前剩余缺口是验证链路，而不是主题失真

- `PLAN.md` 完成标准仍明确写着“不执行编译验证”：`104-backend/PLAN.md:40`

影响：

- 即使本轮静态对照基本通过，也还不能证明 `104` 在当前环境下可通过 JDK17 编译、前端构建和默认配置启动。
- 当前更适合把 `104` 视为“静态一致，待复验”的项目，而不是直接标为“已完成”。

## 静态核对记录

- 本轮已核对：
  - `PRD.md`
  - `PLAN.md`
  - `sql/init.sql`
  - `application.yml`
  - 前端 `router/index.js`
  - 前端 `Layout.vue`
  - 后端 `JwtInterceptor`
  - 后端 `AuthService`
  - 后端关键 controller
- 本轮未执行 Maven / NPM 编译与启动验证。

## 当前判断

`104` 当前状态建议标记为：`检查中`

- PRD / PLAN / 路由 / controller 静态对照：`基本通过`
- 数据库、端口、默认账号口径：`通过`
- JDK17 兼容性、前端构建和启动验证：`本轮未执行`

## 建议下一步

1. 先补 `104-backend` 的 `mvn.cmd test` 或至少一次编译验证。
2. 再补 `104-frontend` 的 `npm.cmd run build` 与一次登录冒烟验证。
3. 若编译、启动和基础登录链路通过，再将 `104` 从 `检查中` 转为 `已完成`。
