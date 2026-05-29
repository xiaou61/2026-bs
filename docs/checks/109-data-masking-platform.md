# 109 数据脱敏与敏感信息识别平台检查报告

## 检查范围

- 项目编号：`109`
- 项目名称：数据脱敏与敏感信息识别平台
- 检查时间：`2026-05-20`
- 目录：
  - 后端：`109-backend`
  - 前端：`109-frontend`
- 主要对照文档：
  - `109-backend/PRD.md`
  - `109-backend/PLAN.md`
  - `109-backend/sql/init.sql`
  - `109-backend/src/main/resources/application.yml`
  - `109-frontend/src/router/index.js`
  - `109-frontend/src/views/Layout.vue`

## 结论摘要

`109` 当前建议标记为 `待修复`。本轮静态核对确认该项目的大框架与 PRD 基本一致，但已经存在一条会直接影响核心角色可用性的权限口径断裂：

1. `PRD.md`、`PLAN.md`、`sql/init.sql` 与 `application.yml` 在数据库名、端口和主体模块口径上基本一致。
2. 前端菜单、路由以及后端控制器已经覆盖数据源、数据集、敏感规则、识别任务、识别结果、脱敏策略、脱敏任务、访问申请、导出审批、风险告警和操作日志等核心模块。
3. 但“数据负责人”在种子数据和前端表单枚举里使用的是 `DATA_OWNER`，而前端路由/菜单守卫、角色首页和后端 `AuthService` 权限断言使用的却是 `OWNER`。这会让 `owner/123456` 登录后拿到 `DATA_OWNER` 角色，却无法命中前后端的 `OWNER` 权限链。

因此，`109` 不是主题串项项目，但也不能按“静态基本通过”放过，必须正式标记为 `待修复`。

## 主要问题

### 109-1 基础配置与 PRD 口径基本一致

- `PRD.md` 明确数据库名、前后端端口和默认账号：`109-backend/PRD.md:9-22`
- `PLAN.md` 明确创建 `data_masking_109` 数据库和 `14` 张业务表：`109-backend/PLAN.md:11`
- 初始化 SQL 直接 `DROP/CREATE/USE data_masking_109`：`109-backend/sql/init.sql:1-3`
- 实际运行配置使用后端端口 `8109`，数据源也指向 `data_masking_109`：`109-backend/src/main/resources/application.yml:2`、`109-backend/src/main/resources/application.yml:6`

说明：

- 本轮未发现数据库串项、端口串项或项目主题错位。
- `109` 的问题不在题目跑偏，而在角色权限骨架前后口径分裂。

### 109-2 前端路由、菜单与关键状态动作基本覆盖 PRD 模块

- 前端路由已覆盖看板、账号权限、数据源配置、数据集目录、敏感规则、识别任务、识别结果、脱敏策略、脱敏任务、脱敏记录、字段血缘、访问申请、导出审批、风险告警和操作日志：`109-frontend/src/router/index.js:20-34`
- 侧边菜单与路由保持一致：`109-frontend/src/views/Layout.vue:27-41`
- 识别任务已具备 `start / finish / fail` 闭环：`109-backend/src/main/java/com/datamasking/controller/RecognitionTaskController.java:58-73`；`109-frontend/src/views/RecognitionTask.vue:8-17`
- 脱敏任务已具备 `submit / start / finish / fail` 闭环：`109-backend/src/main/java/com/datamasking/controller/MaskingTaskController.java:58-80`；`109-frontend/src/views/MaskingTask.vue:8-18`
- 访问申请已具备 `submit / approve / reject / revoke`，导出审批已具备 `approve / reject / finish`，风险告警已具备 `ack / resolve / close`，识别结果已具备 `confirm / ignore`：`109-backend/src/main/java/com/datamasking/controller/AccessRequestController.java:58-80`、`109-backend/src/main/java/com/datamasking/controller/ExportApprovalController.java:58-73`、`109-backend/src/main/java/com/datamasking/controller/RiskAlertController.java:58-73`、`109-backend/src/main/java/com/datamasking/controller/RecognitionResultController.java:58-66`；`109-frontend/src/views/AccessRequest.vue:8-18`、`109-frontend/src/views/ExportApproval.vue:8-17`、`109-frontend/src/views/RiskAlert.vue:8-17`、`109-frontend/src/views/RecognitionResult.vue:8-16`
- 看板也已接入识别趋势和敏感类型分布：`109-backend/src/main/java/com/datamasking/controller/StatisticsController.java:21-24`；`109-frontend/src/views/Dashboard.vue:6-7`、`109-frontend/src/views/Dashboard.vue:24-25`

说明：

- 从静态结构看，`109` 的业务模块和状态动作并不空。
- 当前主要不是“功能缺页”，而是关键角色无法稳定落到正确权限链。

### 109-3 `DATA_OWNER` 与 `OWNER` 双口径并存，数据负责人角色链断裂

- `PRD.md` 默认账号里明确存在 `owner/123456`，对应角色是“数据负责人”：`109-backend/PRD.md:16`、`109-backend/PRD.md:22`
- 初始化 SQL 把该账号写成角色 `DATA_OWNER`：`109-backend/sql/init.sql:185-188`
- 前端账号管理表单也把角色选项写成 `DATA_OWNER`：`109-frontend/src/views/SysUser.vue:11`
- 前端脱敏策略“审批角色”枚举同样写的是 `DATA_OWNER`：`109-frontend/src/views/MaskingStrategy.vue:11`
- 但前端路由首页映射写的是 `OWNER`，不是 `DATA_OWNER`：`109-frontend/src/router/index.js:4-8`
- 前端路由守卫按 `ROLE_HOME[role]` 和 `to.meta.roles` 做拦截，而菜单角色列表也全部使用 `OWNER`：`109-frontend/src/router/index.js:42-49`、`109-frontend/src/router/index.js:20-34`、`109-frontend/src/views/Layout.vue:27-41`
- `userStore.setLogin` 直接把登录返回的 `user.role` 原样写入本地，不存在中间角色转换：`109-frontend/src/store/user.js:5-9`
- 登录页登录成功后固定跳 `'/dashboard'`，若 `role=DATA_OWNER`，会在路由守卫阶段再按 `OWNER` 规则二次判定：`109-frontend/src/views/Login.vue:35-36`、`109-frontend/src/router/index.js:44-49`
- 后端 `AuthService` 的 `assertAdminOrOwner`、`assertAdminOrOwnerOrAuditor` 和 `assertAuthenticated` 也只认 `OWNER`，不认 `DATA_OWNER`：`109-backend/src/main/java/com/datamasking/service/AuthService.java:61-78`
- 访问申请、导出审批、字段血缘、风险告警等本应由数据负责人参与的控制器，全部依赖这些 `OWNER` 断言：`109-backend/src/main/java/com/datamasking/controller/AccessRequestController.java:67-74`、`109-backend/src/main/java/com/datamasking/controller/ExportApprovalController.java:60-74`、`109-backend/src/main/java/com/datamasking/controller/FieldLineageController.java:33-46`、`109-backend/src/main/java/com/datamasking/controller/RiskAlertController.java:74`

影响：

- 按当前种子数据登录的 `owner` 账号，会拿到 `DATA_OWNER` 角色值，但前后端权限骨架只识别 `OWNER`。
- 这意味着“数据负责人审批访问申请、导出审批和字段血缘”的 PRD 主角色，在当前实现里极可能被路由守卫拦回登录页，或被后端直接判定“无权限操作”。
- 这已经不是文案不统一，而是核心业务角色不可用的实现缺陷。

### 109-4 当前仍缺少编译与启动验证

- `PLAN.md` 完成标准仍明确写着“不执行编译验证”：`109-backend/PLAN.md:32`

影响：

- 即使先不考虑 `OWNER / DATA_OWNER` 的角色断裂，本轮也还没有证明 `109` 在当前环境下能通过 JDK17 编译、前端构建和基础登录联调。
- 当前结论仍以静态结构和代码口径核对为主。

## 静态核对记录

- 本轮已核对：
  - `PRD.md`
  - `PLAN.md`
  - `sql/init.sql`
  - `application.yml`
  - 前端 `router/index.js`
  - 前端 `Layout.vue`
  - 前端 `store/user.js`
  - 前端 `Login.vue`
  - 前端关键业务页
  - 前端 `api/index.js`
  - 后端 `AuthService`
  - 后端关键 controller
- 静态结构计数：
  - SQL 建表：`14`
  - Controller：`16`
  - 前端视图：`17`
- 本轮未执行 Maven / NPM 编译与启动验证。

## 当前判断

`109` 当前状态建议标记为：`待修复`

- 数据库、端口、主体模块口径：`通过`
- 路由、菜单、关键业务动作：`基本通过`
- 数据负责人角色链：`不通过`
- 编译与启动验证：`本轮未执行`

## 建议下一步

1. 统一 `OWNER` 与 `DATA_OWNER` 的角色编码，前后端、SQL 种子数据、表单枚举、路由守卫和 `AuthService` 断言只能保留一套口径。
2. 统一后，优先复测 `owner/123456` 的登录跳转、菜单显示、访问申请、导出审批、字段血缘和风险告警等数据负责人链路。
3. 再补一次后端编译、前端构建和基础登录/页面联调验证，确认修复不是停留在静态层面。
