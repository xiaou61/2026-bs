# 107 云服务器资产监控与告警平台检查报告

## 检查范围

- 项目编号：`107`
- 项目名称：云服务器资产监控与告警平台
- 检查时间：`2026-05-20`
- 目录：
  - 后端：`107-backend`
  - 前端：`107-frontend`
- 主要对照文档：
  - `107-backend/PRD.md`
  - `107-backend/PLAN.md`
  - `107-backend/sql/init.sql`
  - `107-backend/src/main/resources/application.yml`
  - `107-frontend/src/router/index.js`
  - `107-frontend/src/views/Layout.vue`

## 结论摘要

`107` 当前建议标记为 `检查中`。本轮静态对照未发现明显的 PRD 失真或主题串项：

1. `PRD.md`、`PLAN.md`、`sql/init.sql` 与 `application.yml` 在数据库名、端口和默认账号口径上保持一致。
2. 前端路由、菜单已经覆盖云区域、主机资产、资源分组、指标、告警、通知、工单、维护窗口、容量规划、看板组件和操作日志等核心模块。
3. 后端和前端都能静态对应告警确认/恢复/关闭、工单分配/解决/关闭、维护窗口开始/完成、容量规划发布/归档等关键动作，基本符合 PRD 闭环。

当前主要缺口仍是运行验证：`PLAN.md` 继续把“不执行编译验证”列为完成标准，本轮也尚未执行 Maven / NPM / 启动验证，因此暂不直接标为 `已完成`。

## 主要结论

### 107-1 基础配置与 PRD 口径一致

- `PRD.md` 明确数据库名、前后端端口和默认账号：`107-backend/PRD.md:25-33`
- `PLAN.md` 明确创建 `cloud_monitor_107` 数据库和 14 张业务表：`107-backend/PLAN.md:15`
- 初始化 SQL 直接 `DROP/CREATE/USE cloud_monitor_107`：`107-backend/sql/init.sql:1-3`
- 实际运行配置使用后端端口 `8107`，数据源也指向 `cloud_monitor_107`：`107-backend/src/main/resources/application.yml:2`、`107-backend/src/main/resources/application.yml:6`

说明：

- 本轮未发现数据库串项、端口串项或默认账号口径错配。
- `107` 当前不像高风险模板段那样存在主题错位。

### 107-2 前端路由与菜单基本覆盖 PRD 的 14 个业务模块

- 前端已定义四类角色首页：`ADMIN / OPS / SRE / MANAGER`：`107-frontend/src/router/index.js:4-8`
- 路由层已覆盖看板、账号权限、云区域、主机资产、资源分组、指标定义、指标采样、告警规则、告警事件、通知记录、处置工单、维护窗口、容量规划、看板组件、操作日志：`107-frontend/src/router/index.js:13-30`
- 侧边菜单与路由保持一致：`107-frontend/src/views/Layout.vue:26-40`

说明：

- 从页面入口看，`107` 的功能骨架与 PRD 列出的核心模块是对齐的。
- 本轮未发现前端仍沿用其他题目模块命名的明显串项。

### 107-3 后端权限骨架与关键状态动作已基本落地

- `AuthService` 已抽出 `assertAdmin`、`assertAdminOrOps`、`assertAdminOrOpsOrSre`、`assertAdminOrSreOrManager` 与 `assertAuthenticated`：`107-backend/src/main/java/com/cloudmonitor/service/AuthService.java:48-69`
- 告警事件已覆盖关闭动作；前端也已接入 `确认 / 恢复 / 关闭`：`107-backend/src/main/java/com/cloudmonitor/controller/AlertEventController.java:78`；`107-frontend/src/views/AlertEvent.vue:7-16`
- 处置工单已覆盖 `分配 / 关闭`，前端也已接入 `分配 / 解决 / 关闭`：`107-backend/src/main/java/com/cloudmonitor/controller/IncidentTicketController.java:62`、`107-backend/src/main/java/com/cloudmonitor/controller/IncidentTicketController.java:78`；`107-frontend/src/views/IncidentTicket.vue:7-16`
- 维护窗口已覆盖 `start / finish`，前端也已接入 `开始 / 完成`：`107-backend/src/main/java/com/cloudmonitor/controller/MaintenanceWindowController.java:61-70`；`107-frontend/src/views/MaintenanceWindow.vue:7-15`
- 容量规划已覆盖 `archive`，前端也已接入 `发布 / 归档`：`107-backend/src/main/java/com/cloudmonitor/controller/CapacityPlanController.java:69-70`；`107-frontend/src/views/CapacityPlan.vue:7-15`
- 看板页已有“近七次指标趋势”趋势图实现，可对应 `PLAN.md` 中的“查看趋势”：`107-frontend/src/views/Dashboard.vue:11`、`107-frontend/src/views/Dashboard.vue:24`

说明：

- 从静态结构看，`107` 已经具备监控告警、工单处置、维护窗口和容量分析的基本闭环动作。
- 当前没有抓到像 `105` 那样明确的“计划写了动作，但前后端都没落”的硬缺口。

### 107-4 当前缺口仍是编译与启动验证

- `PLAN.md` 完成标准仍明确写着“不执行编译验证”：`107-backend/PLAN.md:40`

影响：

- 当前只能说明 `107` 在文档、配置、页面入口和动作接口层面基本对齐。
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
  - 后端 `AuthService`
  - 后端关键 controller
- 静态结构计数：
  - SQL 建表：`14`
  - Controller：`16`
  - 前端视图：`17`
- 本轮未执行 Maven / NPM 编译与启动验证。

## 当前判断

`107` 当前状态建议标记为：`检查中`

- 数据库、端口、默认账号口径：`通过`
- 路由、菜单、角色骨架：`基本通过`
- 告警、工单、维护和容量动作：`基本通过`
- 编译与启动验证：`本轮未执行`

## 建议下一步

1. 先补 `107-backend` 的 `mvn.cmd test` 或至少一次编译验证。
2. 再补 `107-frontend` 的 `npm.cmd run build` 与一次基础登录/页面冒烟验证。
3. 若编译、启动和核心状态接口联调通过，再将 `107` 从 `检查中` 转为 `已完成`。
