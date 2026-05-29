# 108 云原生成本分析与资源优化平台检查报告

## 检查范围

- 项目编号：`108`
- 项目名称：云原生成本分析与资源优化平台
- 检查时间：`2026-05-20`
- 目录：
  - 后端：`108-backend`
  - 前端：`108-frontend`
- 主要对照文档：
  - `108-backend/PRD.md`
  - `108-backend/PLAN.md`
  - `108-backend/sql/init.sql`
  - `108-backend/src/main/resources/application.yml`
  - `108-frontend/src/router/index.js`
  - `108-frontend/src/views/Layout.vue`

## 结论摘要

`108` 当前建议标记为 `检查中`。本轮静态对照未发现明显的 PRD 失真、主题串项或高风险权限脱节：

1. `PRD.md`、`PLAN.md`、`sql/init.sql` 与 `application.yml` 在数据库名、端口和默认账号口径上保持一致。
2. 前端路由、菜单已经覆盖云账号、资源命名空间、成本账单、预算策略、成本分摊、闲置资源、优化规则、优化建议、节省计划、成本异常、报告快照和操作日志等核心模块。
3. 后端角色断言与前端按钮动作基本对应，能静态对应云账号启停、预算策略启停/归档、成本分摊提交/审批、账单确认/支付/关闭、闲置资源处置、优化建议采纳/完成/拒绝、节省计划生命周期和报告快照发布/归档等闭环动作。

当前主要缺口仍是运行验证：`PLAN.md` 继续把“不执行编译验证”列为完成标准，本轮也尚未执行 Maven / NPM / 启动验证，因此暂不直接标为 `已完成`。

## 主要结论

### 108-1 基础配置与 PRD 口径一致

- `PRD.md` 明确数据库名、前后端端口和默认账号：`108-backend/PRD.md:9-23`
- `PLAN.md` 明确创建 `cloud_cost_108` 数据库和 `14` 张业务表：`108-backend/PLAN.md:11`
- 初始化 SQL 直接 `DROP/CREATE/USE cloud_cost_108`：`108-backend/sql/init.sql:1-3`
- 实际运行配置使用后端端口 `8108`，数据源也指向 `cloud_cost_108`：`108-backend/src/main/resources/application.yml:2`、`108-backend/src/main/resources/application.yml:6`

说明：

- 本轮未发现数据库串项、端口串项或默认账号口径错配。
- `108` 当前不像 `124` 那样存在基础配置硬伤。

### 108-2 前端路由与菜单基本覆盖 PRD 的核心模块

- 前端已定义四类默认账号对应角色：`ADMIN / FINOPS / DEVOPS / MANAGER`：`108-backend/PRD.md:20-23`
- 路由层已覆盖看板、云账号、资源命名空间、成本账单、预算策略、成本分摊、闲置资源、优化规则、优化建议、节省计划、成本异常、报告快照和操作日志：`108-frontend/src/router/index.js:20-34`
- 侧边菜单与路由保持一致：`108-frontend/src/views/Layout.vue:27-41`

说明：

- 从页面入口看，`108` 的功能骨架与 PRD 列出的 FinOps 核心模块是对齐的。
- 本轮未发现前端仍沿用其他项目主题模块名的明显串项。

### 108-3 后端权限骨架与关键状态动作已基本落地

- `AuthService` 已抽出 `assertAdmin`、`assertAdminOrFinops`、`assertAdminOrDevops`、`assertAdminOrManager`、`assertAdminOrFinopsOrManager`、`assertAdminOrDevopsOrManager` 与 `assertAuthenticated`：`108-backend/src/main/java/com/cloudcost/service/AuthService.java:60-88`
- 云账号已覆盖 `activate / disable`：`108-backend/src/main/java/com/cloudcost/controller/CloudAccountController.java:58-66`
- 预算策略已覆盖 `enable / archive`：`108-backend/src/main/java/com/cloudcost/controller/BudgetPolicyController.java:58-66`
- 成本分摊已覆盖 `submit / approve / reject`：`108-backend/src/main/java/com/cloudcost/controller/CostAllocationController.java:58-73`
- 成本账单已覆盖 `confirm / pay / close`：`108-backend/src/main/java/com/cloudcost/controller/CostBillController.java:58-73`
- 闲置资源已覆盖 `confirm / ignore / clean`：`108-backend/src/main/java/com/cloudcost/controller/IdleResourceController.java:58-73`
- 优化建议已覆盖 `accept / finish / reject`：`108-backend/src/main/java/com/cloudcost/controller/OptimizationAdviceController.java:58-73`
- 优化规则已覆盖 `enable / pause`：`108-backend/src/main/java/com/cloudcost/controller/OptimizationRuleController.java:58-66`
- 报告快照已覆盖 `publish / archive`：`108-backend/src/main/java/com/cloudcost/controller/ReportSnapshotController.java:58-66`
- 节省计划已覆盖 `activate / finish / archive`：`108-backend/src/main/java/com/cloudcost/controller/SavingPlanController.java:58-73`
- 成本异常已覆盖 `confirm / resolve / close`：`108-backend/src/main/java/com/cloudcost/controller/AnomalyEventController.java:58-73`

说明：

- 从控制器静态结构看，`108` 已具备成本治理、异常处置和优化执行的基本闭环动作。
- 本轮未抓到像 `105` 那样“计划写了动作，但前后端都没落”的硬缺口。

### 108-4 前端按钮与后端动作基本对应

- 云账号页面已接 `启用 / 停用`：`108-frontend/src/views/CloudAccount.vue:8-16`
- 预算策略页面已接 `启用 / 归档`：`108-frontend/src/views/BudgetPolicy.vue:8-16`
- 成本分摊页面已接 `提交 / 通过 / 驳回`：`108-frontend/src/views/CostAllocation.vue:8-17`
- 成本账单页面已接 `确认 / 支付 / 关闭`：`108-frontend/src/views/CostBill.vue:8-17`
- 闲置资源页面已接 `确认闲置 / 忽略 / 已清理`：`108-frontend/src/views/IdleResource.vue:8-17`
- 优化建议页面已接 `采纳 / 完成 / 拒绝`：`108-frontend/src/views/OptimizationAdvice.vue:8-17`
- 优化规则页面已接 `启用 / 暂停`：`108-frontend/src/views/OptimizationRule.vue:8-16`
- 成本异常页面已接 `确认 / 解决 / 关闭`：`108-frontend/src/views/AnomalyEvent.vue:8-17`
- 报告快照页面已接 `发布 / 归档`：`108-frontend/src/views/ReportSnapshot.vue:8-16`
- 节省计划页面已接 `生效 / 完成 / 归档`：`108-frontend/src/views/SavingPlan.vue:8-17`

说明：

- 静态上可以确认“页面按钮 -> API -> controller 状态动作”这一链路基本打通。
- 目前没有发现前端开放了动作但后端缺接口，或后端有关键动作但前端完全没接的明显断层。

### 108-5 看板趋势能力已具备静态落点

- 统计接口已提供 `dashboard`：`108-backend/src/main/java/com/cloudcost/controller/StatisticsController.java:21-24`
- 前端看板已接入趋势图和饼图，其中趋势图标题为“近7月成本趋势”：`108-frontend/src/views/Dashboard.vue:9-11`、`108-frontend/src/views/Dashboard.vue:24-26`

说明：

- `108` 不只是列表 CRUD，PRD 要求的成本趋势与分析看板也有明确静态落点。
- 趋势数据是否与真实账单联动正确，仍需后续联调验证。

### 108-6 当前缺口仍是编译与启动验证

- `PLAN.md` 完成标准仍明确写着“不执行编译验证”：`108-backend/PLAN.md:35`

影响：

- 当前只能说明 `108` 在文档、配置、页面入口和动作接口层面基本对齐。
- 还不能证明它在当前环境下能通过 JDK17 编译、前端构建和基础登录/页面联调。

## 静态核对记录

- 本轮已核对：
  - `PRD.md`
  - `PLAN.md`
  - `sql/init.sql`
  - `application.yml`
  - 前端 `router/index.js`
  - 前端 `Layout.vue`
  - 前端关键业务页
  - 后端 `AuthService`
  - 后端关键 controller
- 静态结构计数：
  - SQL 建表：`14`
  - Controller：`16`
  - 前端视图：`17`
- 本轮未执行 Maven / NPM 编译与启动验证。

## 当前判断

`108` 当前状态建议标记为：`检查中`

- 数据库、端口、默认账号口径：`通过`
- 路由、菜单、角色骨架：`基本通过`
- 成本治理与优化闭环动作：`基本通过`
- 编译与启动验证：`本轮未执行`

## 建议下一步

1. 先补 `108-backend` 的 `mvn.cmd test` 或至少一次编译验证。
2. 再补 `108-frontend` 的 `npm.cmd run build` 与一次基础登录/页面冒烟验证。
3. 若编译、启动和核心状态接口联调通过，再将 `108` 从 `检查中` 转为 `已完成`。
