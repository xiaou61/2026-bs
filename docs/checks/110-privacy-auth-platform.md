# 110 个人数据隐私授权与访问审计平台检查报告

## 检查范围

- 项目编号：`110`
- 项目名称：个人数据隐私授权与访问审计平台
- 检查时间：`2026-05-21`
- 目录：
  - 后端：`110-backend`
  - 前端：`110-frontend`
- 主要对照文档：
  - `110-backend/PRD.md`
  - `110-backend/PLAN.md`
  - `110-backend/sql/init.sql`
  - `110-backend/src/main/resources/application.yml`
  - `110-frontend/src/router/index.js`
  - `110-frontend/src/views/Layout.vue`

## 结论摘要

`110` 当前建议标记为 `待修复`。本轮静态核对确认该项目的大框架与 PRD 基本一致，但已经存在第二类会直接影响核心角色可用性的权限口径断裂：

1. `PRD.md`、`PLAN.md`、`sql/init.sql` 与 `application.yml` 在数据库名、端口和主体模块口径上基本一致。
2. 前端菜单、路由以及后端控制器已经覆盖数据主体、个人数据项、授权目的、授权策略、授权记录、访问申请、访问授权、撤销申请、风险预警和审计报告等核心模块。
3. 但 `隐私官 / 数据使用人` 在种子数据和前端账号表单里使用的是 `PRIVACY_OFFICER / DATA_USER`，而前端路由首页、菜单角色守卫和后端 `AuthService` 权限断言使用的却是 `PRIVACY / DATAUSER`。这会让 `privacy/123456`、`datauser/123456` 登录后拿到的真实角色值无法命中前后端权限骨架。

因此，`110` 虽然不是主题串项项目，但也不能按“静态基本通过”放过，必须正式标记为 `待修复`。

## 主要问题

### 110-1 基础配置与 PRD 口径基本一致

- `PRD.md` 明确数据库名、前后端端口和默认账号：`110-backend/PRD.md:9-22`
- `PLAN.md` 明确创建 `privacy_auth_110` 数据库和 `14` 张业务表：`110-backend/PLAN.md:11`
- 初始化 SQL 直接 `DROP/CREATE/USE privacy_auth_110`：`110-backend/sql/init.sql:1-3`
- 实际运行配置使用后端端口 `8110`，数据源也指向 `privacy_auth_110`：`110-backend/src/main/resources/application.yml:2`、`110-backend/src/main/resources/application.yml:6`

说明：

- 本轮未发现数据库串项、端口串项或项目主题错位。
- `110` 的问题同样不在题目跑偏，而在角色权限骨架前后口径分裂。

### 110-2 前端路由、菜单与关键状态动作基本覆盖 PRD 模块

- 前端路由已覆盖看板、账号权限、数据主体、个人数据项、授权目的、授权策略、授权记录、授权范围、访问申请、访问授权、访问日志、撤销申请、风险预警、审计报告和操作日志：`110-frontend/src/router/index.js:20-34`
- 侧边菜单与路由保持一致：`110-frontend/src/views/Layout.vue:27-41`
- 数据主体已具备 `verify / disable`：`110-backend/src/main/java/com/privacyauth/controller/DataSubjectController.java:58-66`；`110-frontend/src/views/DataSubject.vue:8-16`
- 授权目的已具备 `publish / archive`，授权策略已具备 `submit / approve / archive`：`110-backend/src/main/java/com/privacyauth/controller/ConsentPurposeController.java:58-66`、`110-backend/src/main/java/com/privacyauth/controller/ConsentPolicyController.java:58-73`；`110-frontend/src/views/ConsentPurpose.vue:8-16`、`110-frontend/src/views/ConsentPolicy.vue:8-17`
- 授权记录已具备 `activate / expire / revoke`，访问申请已具备 `submit / approve / reject`，访问授权已具备 `activate / revoke`：`110-backend/src/main/java/com/privacyauth/controller/AuthorizationRecordController.java:58-73`、`110-backend/src/main/java/com/privacyauth/controller/AccessApplicationController.java:58-73`、`110-backend/src/main/java/com/privacyauth/controller/AccessGrantController.java:58-66`；`110-frontend/src/views/AuthorizationRecord.vue:8-17`、`110-frontend/src/views/AccessApplication.vue:8-17`、`110-frontend/src/views/AccessGrant.vue:8-16`
- 撤销申请已具备 `approve / reject / finish`，风险预警已具备 `ack / resolve / close`，审计报告已具备 `publish / archive`：`110-backend/src/main/java/com/privacyauth/controller/RevocationRequestController.java:58-73`、`110-backend/src/main/java/com/privacyauth/controller/RiskWarningController.java:58-73`、`110-backend/src/main/java/com/privacyauth/controller/AuditReportController.java:58-66`；`110-frontend/src/views/RevocationRequest.vue:8-17`、`110-frontend/src/views/RiskWarning.vue:8-17`、`110-frontend/src/views/AuditReport.vue:8-16`

说明：

- 从静态结构看，`110` 的业务模块和状态动作并不空。
- 当前主要不是“功能缺页”，而是 `隐私官 / 数据使用人` 两条主角色无法稳定落到正确权限链。

### 110-3 `PRIVACY_OFFICER / DATA_USER` 与 `PRIVACY / DATAUSER` 双口径并存，核心角色链断裂

- `PRD.md` 默认账号里明确存在 `privacy/123456` 和 `datauser/123456`，对应角色分别是“隐私官”和“数据使用人”：`110-backend/PRD.md:16-22`
- 初始化 SQL 把这两个账号分别写成角色 `PRIVACY_OFFICER` 与 `DATA_USER`：`110-backend/sql/init.sql:188-189`
- 前端账号管理表单也把角色选项写成 `PRIVACY_OFFICER` 与 `DATA_USER`：`110-frontend/src/views/SysUser.vue:11-13`
- 但前端角色首页映射写的是 `PRIVACY` 与 `DATAUSER`：`110-frontend/src/router/index.js:4-8`
- 前端路由守卫按 `ROLE_HOME[role]` 和 `to.meta.roles` 做拦截，而菜单角色列表也统一使用 `PRIVACY` 与 `DATAUSER`：`110-frontend/src/router/index.js:20-34`、`110-frontend/src/router/index.js:42-49`、`110-frontend/src/views/Layout.vue:27-41`
- `userStore.setLogin` 直接把登录返回的 `user.role` 原样写入本地，不存在 `PRIVACY_OFFICER -> PRIVACY` 或 `DATA_USER -> DATAUSER` 的中间转换：`110-frontend/src/store/user.js:7-12`
- 登录页登录成功后固定跳 `'/dashboard'`，若 `role=PRIVACY_OFFICER` 或 `DATA_USER`，会在路由守卫阶段再按 `PRIVACY / DATAUSER` 规则二次判定：`110-frontend/src/views/Login.vue:32-36`、`110-frontend/src/router/index.js:44-49`
- 后端 `AuthService` 的 `assertAdminOrPrivacy`、`assertAdminOrDatauser`、`assertAdminOrPrivacyOrAuditor`、`assertAdminOrPrivacyOrDatauserOrAuditor` 和 `assertAuthenticated` 也只认 `PRIVACY / DATAUSER`，不认 `PRIVACY_OFFICER / DATA_USER`：`110-backend/src/main/java/com/privacyauth/service/AuthService.java:64-85`
- 访问申请、访问授权、访问日志、撤销申请、风险预警、审计报告、授权记录等本应由隐私官或数据使用人参与的控制器，全部依赖这些缩写角色断言：`110-backend/src/main/java/com/privacyauth/controller/AccessApplicationController.java:39-74`、`110-backend/src/main/java/com/privacyauth/controller/AccessGrantController.java:39-67`、`110-backend/src/main/java/com/privacyauth/controller/AccessLogController.java:33-46`、`110-backend/src/main/java/com/privacyauth/controller/RevocationRequestController.java:39-74`、`110-backend/src/main/java/com/privacyauth/controller/RiskWarningController.java:33-74`、`110-backend/src/main/java/com/privacyauth/controller/AuditReportController.java:33-67`

影响：

- 按当前种子数据登录的 `privacy` 与 `datauser` 账号，会拿到 `PRIVACY_OFFICER / DATA_USER` 角色值，但前后端权限骨架只识别 `PRIVACY / DATAUSER`。
- 这意味着 PRD 中“隐私官维护授权目的、授权策略、风险预警和审计报告”“数据使用人提交访问申请并在授权范围内访问数据”的两条主角色链，在当前实现里极可能被路由守卫拦回登录页，或被后端直接判定“无权限操作”。
- 这同样不是文案不统一，而是核心业务角色不可用的实现缺陷。

### 110-4 当前仍缺少编译与启动验证

- `PLAN.md` 完成标准仍明确写着“不执行编译验证”：`110-backend/PLAN.md:32`

影响：

- 即使先不考虑 `PRIVACY_OFFICER / DATA_USER` 与 `PRIVACY / DATAUSER` 的角色断裂，本轮也还没有证明 `110` 在当前环境下能通过 JDK17 编译、前端构建和基础登录联调。
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

`110` 当前状态建议标记为：`待修复`

- 数据库、端口、主体模块口径：`通过`
- 路由、菜单、关键业务动作：`基本通过`
- 隐私官与数据使用人角色链：`不通过`
- 编译与启动验证：`本轮未执行`

## 建议下一步

1. 统一 `PRIVACY_OFFICER / DATA_USER` 与 `PRIVACY / DATAUSER` 的角色编码，前后端、SQL 种子数据、表单枚举、路由守卫和 `AuthService` 断言只能保留一套口径。
2. 统一后，优先复测 `privacy/123456` 与 `datauser/123456` 的登录跳转、菜单显示、访问申请、访问授权、撤销申请、风险预警和审计报告链路。
3. 再补一次后端编译、前端构建和基础登录/页面联调验证，确认修复不是停留在静态层面。
