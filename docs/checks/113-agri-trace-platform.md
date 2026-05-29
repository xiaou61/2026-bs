# 113 区块链农产品质量溯源与监管平台检查报告

## 检查范围

- 项目编号：`113`
- 项目名称：区块链农产品质量溯源与监管平台
- 检查时间：`2026-05-21`
- 目录：
  - 后端：`113-backend`
  - 前端：`113-frontend`
- 主要对照文档：
  - `113-backend/PRD.md`
  - `113-backend/PLAN.md`
  - `113-backend/sql/init.sql`
  - `113-backend/src/main/resources/application.yml`
  - `113-frontend/src/router/index.js`
  - `113-frontend/src/views/Layout.vue`

## 结论摘要

`113` 当前建议标记为 `检查中`。本轮静态对照未发现明显的 PRD 失真、主题串项或角色码断裂：

1. `PRD.md`、`PLAN.md`、`sql/init.sql` 与 `application.yml` 在数据库名、端口和默认账号口径上保持一致。
2. 前端路由、菜单已经覆盖种植基地、农户档案、产品分类、产品批次、种植记录、农资投入、质检报告、区块存证、流通节点、物流记录、召回事件、监管检查和操作日志等核心模块。
3. 后端角色断言与前端按钮动作基本对应，能静态对应基地认证/暂停、农户启停、批次提交/上链/完结、种植记录确认/上链、农资审核、质检报告发布、不合格上报、流通节点启停、物流发车/到达/签收、召回事件处置和监管检查闭环等动作。

当前主要缺口仍是运行验证：`PLAN.md` 继续把“不执行编译验证”列为完成标准，本轮也尚未执行 Maven / NPM / 启动验证，因此暂不直接标为 `已完成`。

## 主要结论

### 113-1 基础配置与 PRD 口径一致

- `PRD.md` 明确数据库名、前后端端口和默认账号：`113-backend/PRD.md:9-19`
- `PLAN.md` 明确创建 `agri_trace_113` 数据库和 `14` 张业务表：`113-backend/PLAN.md:9-23`
- 初始化 SQL 直接 `DROP/CREATE/USE agri_trace_113`：`113-backend/sql/init.sql:1-3`
- 实际运行配置使用后端端口 `8113`，数据源也指向 `agri_trace_113`：`113-backend/src/main/resources/application.yml:2`、`113-backend/src/main/resources/application.yml:6`

说明：

- 本轮未发现数据库串项、端口串项或默认账号口径错配。
- `113` 当前不像 `124` 那样存在基础配置硬伤。

### 113-2 前端路由与菜单基本覆盖 PRD 的 14 个核心模块

- 前端已定义四类角色首页：`ADMIN / REGULATOR / FARMER / INSPECTOR`：`113-frontend/src/router/index.js:4-8`
- 路由层已覆盖看板、账号权限、种植基地、农户档案、产品分类、产品批次、种植记录、农资投入、质检报告、区块存证、流通节点、物流记录、召回事件、监管检查和操作日志：`113-frontend/src/router/index.js:20-34`
- 侧边菜单与路由保持一致：`113-frontend/src/views/Layout.vue:24-39`
- 前端账号管理表单角色枚举也与默认账号口径一致：`113-frontend/src/views/SysUser.vue:11-13`

说明：

- 从页面入口看，`113` 的功能骨架与 PRD 列出的全链路溯源模块是对齐的。
- 本轮未发现前端仍沿用其他项目主题名或错用角色常量的明显串项。

### 113-3 后端权限骨架与关键状态动作已基本落地

- `AuthService` 已抽出 `assertAdmin`、`assertAdminOrRegulator`、`assertAdminOrFarmer`、`assertAdminOrInspector`、`assertAdminOrRegulatorOrInspector` 与 `assertAuthenticated`：`113-backend/src/main/java/com/agritrace/service/AuthService.java:53-74`
- 种植基地已覆盖 `certify / suspend`：`113-backend/src/main/java/com/agritrace/controller/FarmBaseController.java:58-66`
- 农户档案已覆盖 `activate / freeze`：`113-backend/src/main/java/com/agritrace/controller/FarmerProfileController.java:58-66`
- 产品批次已覆盖 `submit / chain / close`：`113-backend/src/main/java/com/agritrace/controller/ProductBatchController.java:58-73`
- 种植记录已覆盖 `confirm / chain`：`113-backend/src/main/java/com/agritrace/controller/PlantingRecordController.java:58-66`
- 农资投入已覆盖 `approve / reject`：`113-backend/src/main/java/com/agritrace/controller/InputMaterialController.java:58-66`
- 质检报告已覆盖 `publish / fail`：`113-backend/src/main/java/com/agritrace/controller/QualityInspectionController.java:58-66`
- 区块存证已覆盖 `confirm / fail`：`113-backend/src/main/java/com/agritrace/controller/ChainBlockController.java:58-66`
- 流通节点已覆盖 `enable / disable`：`113-backend/src/main/java/com/agritrace/controller/TraceNodeController.java:58-66`
- 物流记录已覆盖 `depart / arrive / sign`：`113-backend/src/main/java/com/agritrace/controller/LogisticsRecordController.java:58-73`
- 召回事件已覆盖 `process / close`，监管检查已覆盖 `publish / rectify / close`：`113-backend/src/main/java/com/agritrace/controller/RecallEventController.java:58-66`、`113-backend/src/main/java/com/agritrace/controller/RegulationCheckController.java:58-73`

说明：

- 从控制器静态结构看，`113` 已具备农产品批次追溯、质检、上链和监管处置的基本闭环动作。
- 本轮未抓到像 `105` 那样“计划写了动作，但前后端都没落”的硬缺口。

### 113-4 前端按钮与后端动作基本对应

- 种植基地页面已接 `认证 / 暂停`：`113-frontend/src/views/FarmBase.vue:8-16`
- 农户档案页面已接 `启用 / 冻结`：`113-frontend/src/views/FarmerProfile.vue:8-16`
- 产品分类页面已接 `启用 / 停用`：`113-frontend/src/views/ProductCategory.vue:8-16`
- 产品批次页面已接 `提交 / 上链 / 完结`：`113-frontend/src/views/ProductBatch.vue:8-17`
- 种植记录页面已接 `确认 / 上链`，农资投入页面已接 `通过 / 驳回`：`113-frontend/src/views/PlantingRecord.vue:8-16`、`113-frontend/src/views/InputMaterial.vue:8-16`
- 质检报告页面已接 `发布 / 不合格`，区块存证页面已接 `确认 / 失败`：`113-frontend/src/views/QualityInspection.vue:8-16`、`113-frontend/src/views/ChainBlock.vue:8-16`
- 流通节点页面已接 `启用 / 停用`，物流记录页面已接 `发车 / 到达 / 签收`：`113-frontend/src/views/TraceNode.vue:8-16`、`113-frontend/src/views/LogisticsRecord.vue:8-17`
- 召回事件页面已接 `处理中 / 关闭`，监管检查页面已接 `发布 / 整改 / 闭环`：`113-frontend/src/views/RecallEvent.vue:8-16`、`113-frontend/src/views/RegulationCheck.vue:8-17`

说明：

- 静态上可以确认“页面按钮 -> API -> controller 状态动作”这一链路基本打通。
- 当前没有发现前端开放了动作但后端缺接口，或后端有关键动作但前端完全没接的明显断层。

### 113-5 看板趋势能力已具备静态落点

- 统计接口已提供 `dashboard`：`113-backend/src/main/java/com/agritrace/controller/StatisticsController.java:21-24`
- 前端看板已接入上链趋势和产品风险分布，其中趋势图标题为“近7日上链趋势”：`113-frontend/src/views/Dashboard.vue:5`、`113-frontend/src/views/Dashboard.vue:24-25`

说明：

- `113` 不只是列表 CRUD，PRD 要求的上链趋势与风险分布也有明确静态落点。
- 趋势和风险统计是否与真实批次/质检/物流/监管数据联动正确，仍需后续联调验证。

### 113-6 当前缺口仍是编译与启动验证

- `PLAN.md` 完成标准仍明确写着“不执行编译验证”：`113-backend/PLAN.md:31`

影响：

- 当前只能说明 `113` 在文档、配置、页面入口和动作接口层面基本对齐。
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

`113` 当前状态建议标记为：`检查中`

- 数据库、端口、默认账号口径：`通过`
- 路由、菜单、角色骨架：`基本通过`
- 溯源/质检/上链/监管闭环动作：`基本通过`
- 编译与启动验证：`本轮未执行`

## 建议下一步

1. 先补 `113-backend` 的 `mvn.cmd test` 或至少一次编译验证。
2. 再补 `113-frontend` 的 `npm.cmd run build` 与一次基础登录/页面冒烟验证。
3. 若编译、启动和核心状态接口联调通过，再将 `113` 从 `检查中` 转为 `已完成`。
