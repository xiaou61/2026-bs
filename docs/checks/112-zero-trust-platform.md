# 112 零信任设备准入与访问控制管理系统检查报告

## 检查范围

- 项目编号：`112`
- 项目名称：零信任设备准入与访问控制管理系统
- 检查时间：`2026-05-21`
- 目录：
  - 后端：`112-backend`
  - 前端：`112-frontend`
- 主要对照文档：
  - `112-backend/PRD.md`
  - `112-backend/PLAN.md`
  - `112-backend/sql/init.sql`
  - `112-backend/src/main/resources/application.yml`
  - `112-frontend/src/router/index.js`
  - `112-frontend/src/views/Layout.vue`

## 结论摘要

`112` 当前建议标记为 `检查中`。本轮静态对照未发现明显的 PRD 失真、主题串项或角色码断裂：

1. `PRD.md`、`PLAN.md`、`sql/init.sql` 与 `application.yml` 在数据库名、端口和默认账号口径上保持一致。
2. 前端路由、菜单已经覆盖设备资产、员工账号、身份源、风险模型、风险评估、访问策略、策略规则、准入申请、访问会话、网络分区、设备证书、审计事件和操作日志等核心模块。
3. 后端角色断言与前端按钮动作基本对应，能静态对应设备准入/阻断/退役、员工账号启停、身份源连接/停用、风险模型发布/归档、风险评估复核/放行/阻断、访问策略启停、准入申请审批、会话终止/过期、证书吊销/续期和审计事件确认/关闭等闭环动作。

当前主要缺口仍是运行验证：`PLAN.md` 继续把“不执行编译验证”列为完成标准，本轮也尚未执行 Maven / NPM / 启动验证，因此暂不直接标为 `已完成`。

## 主要结论

### 112-1 基础配置与 PRD 口径一致

- `PRD.md` 明确数据库名、前后端端口和默认账号：`112-backend/PRD.md:9-19`
- `PLAN.md` 明确创建 `zero_trust_112` 数据库和 `14` 张业务表：`112-backend/PLAN.md:11`
- 初始化 SQL 直接 `DROP/CREATE/USE zero_trust_112`：`112-backend/sql/init.sql:1-3`
- 实际运行配置使用后端端口 `8112`，数据源也指向 `zero_trust_112`：`112-backend/src/main/resources/application.yml:2`、`112-backend/src/main/resources/application.yml:6`

说明：

- 本轮未发现数据库串项、端口串项或默认账号口径错配。
- `112` 当前不像 `124` 那样存在基础配置硬伤。

### 112-2 前端路由与菜单基本覆盖 PRD 的 14 个核心模块

- 前端已定义四类角色首页：`ADMIN / SECURITY / NETWORK / AUDITOR`：`112-frontend/src/router/index.js:4-8`
- 路由层已覆盖看板、账号权限、设备资产、员工账号、身份源、风险模型、风险评估、访问策略、策略规则、准入申请、访问会话、网络分区、设备证书、审计事件和操作日志：`112-frontend/src/router/index.js:20-34`
- 侧边菜单与路由保持一致：`112-frontend/src/views/Layout.vue:27-41`
- 前端账号管理表单角色枚举也与默认账号口径一致：`112-frontend/src/views/SysUser.vue:11-13`

说明：

- 从页面入口看，`112` 的功能骨架与 PRD 列出的零信任核心模块是对齐的。
- 本轮未发现前端仍沿用其他项目主题名或错用角色常量的明显串项。

### 112-3 后端权限骨架与关键状态动作已基本落地

- `AuthService` 已抽出 `assertAdmin`、`assertAdminOrSecurity`、`assertAdminOrNetwork`、`assertAdminOrAuditor`、`assertAdminOrSecurityOrAuditor`、`assertAdminOrNetworkOrAuditor` 与 `assertAuthenticated`：`112-backend/src/main/java/com/zerotrust/service/AuthService.java:60-85`
- 设备资产已覆盖 `approve / block / retire`：`112-backend/src/main/java/com/zerotrust/controller/DeviceAssetController.java:58-73`
- 员工账号已覆盖 `enable / disable`：`112-backend/src/main/java/com/zerotrust/controller/EmployeeAccountController.java:58-66`
- 身份源已覆盖 `connect / disable`：`112-backend/src/main/java/com/zerotrust/controller/IdentityProviderController.java:58-66`
- 风险模型已覆盖 `publish / archive`：`112-backend/src/main/java/com/zerotrust/controller/RiskModelController.java:58-66`
- 风险评估已覆盖 `review / clear / block`：`112-backend/src/main/java/com/zerotrust/controller/RiskAssessmentController.java:58-73`
- 访问策略已覆盖 `enable / disable`：`112-backend/src/main/java/com/zerotrust/controller/AccessPolicyController.java:58-66`
- 准入申请已覆盖 `submit / approve / reject`：`112-backend/src/main/java/com/zerotrust/controller/AccessApplicationController.java:58-73`
- 访问会话已覆盖 `terminate / expire`：`112-backend/src/main/java/com/zerotrust/controller/AccessSessionController.java:58-66`
- 设备证书已覆盖 `revoke / renew`：`112-backend/src/main/java/com/zerotrust/controller/DeviceCertificateController.java:58-66`
- 审计事件已覆盖 `ack / close`：`112-backend/src/main/java/com/zerotrust/controller/AuditEventController.java:58-66`

说明：

- 从控制器静态结构看，`112` 已具备准入控制、风险处置和访问审计的基本闭环动作。
- 本轮未抓到像 `105` 那样“计划写了动作，但前后端都没落”的硬缺口。

### 112-4 前端按钮与后端动作基本对应

- 设备资产页面已接 `准入 / 阻断 / 退役`：`112-frontend/src/views/DeviceAsset.vue:8-17`
- 员工账号页面已接 `启用 / 停用`：`112-frontend/src/views/EmployeeAccount.vue:8-16`
- 身份源页面已接 `连接 / 停用`：`112-frontend/src/views/IdentityProvider.vue:8-16`
- 风险模型页面已接 `发布 / 归档`，风险评估页面已接 `复核 / 放行 / 阻断`：`112-frontend/src/views/RiskModel.vue:8-16`、`112-frontend/src/views/RiskAssessment.vue:8-17`
- 访问策略页面已接 `启用 / 停用`，准入申请页面已接 `提交 / 通过 / 驳回`：`112-frontend/src/views/AccessPolicy.vue:8-16`、`112-frontend/src/views/AccessApplication.vue:8-17`
- 访问会话页面已接 `终止 / 过期`，设备证书页面已接 `吊销 / 续期`：`112-frontend/src/views/AccessSession.vue:8-16`、`112-frontend/src/views/DeviceCertificate.vue:8-16`
- 审计事件页面已接 `确认 / 关闭`：`112-frontend/src/views/AuditEvent.vue:8-16`

说明：

- 静态上可以确认“页面按钮 -> API -> controller 状态动作”这一链路基本打通。
- 当前没有发现前端开放了动作但后端缺接口，或后端有关键动作但前端完全没接的明显断层。

### 112-5 看板趋势能力已具备静态落点

- 统计接口已提供 `dashboard`：`112-backend/src/main/java/com/zerotrust/controller/StatisticsController.java:21-24`
- 前端看板已接入准入趋势和风险等级分布，其中趋势图标题为“近7日准入趋势”：`112-frontend/src/views/Dashboard.vue:5`、`112-frontend/src/views/Dashboard.vue:24-25`

说明：

- `112` 不只是列表 CRUD，PRD 要求的准入趋势与风险分布也有明确静态落点。
- 趋势和风险统计是否与真实申请/评估/策略数据联动正确，仍需后续联调验证。

### 112-6 当前缺口仍是编译与启动验证

- `PLAN.md` 完成标准仍明确写着“不执行编译验证”：`112-backend/PLAN.md:32`

影响：

- 当前只能说明 `112` 在文档、配置、页面入口和动作接口层面基本对齐。
- 还不能证明它在当前环境下能通过 JDK17 编译、前端构建和基础登录/页面联调。

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

`112` 当前状态建议标记为：`检查中`

- 数据库、端口、默认账号口径：`通过`
- 路由、菜单、角色骨架：`基本通过`
- 准入/策略/审计闭环动作：`基本通过`
- 编译与启动验证：`本轮未执行`

## 建议下一步

1. 先补 `112-backend` 的 `mvn.cmd test` 或至少一次编译验证。
2. 再补 `112-frontend` 的 `npm.cmd run build` 与一次基础登录/页面冒烟验证。
3. 若编译、启动和核心状态接口联调通过，再将 `112` 从 `检查中` 转为 `已完成`。
