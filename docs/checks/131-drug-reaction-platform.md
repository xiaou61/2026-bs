# 131 药品不良反应上报与随访管理系统检查报告

## 检查范围

- 项目编号：`131`
- 项目名称：药品不良反应上报与随访管理系统
- 检查时间：`2026-05-20`
- 目录：
  - 后端：`131-backend`
  - 前端：`131-frontend`
- 主要对照文档：
  - `131-backend/PRD.md`
  - `131-backend/PLAN.md`

## 结论摘要

`131` 当前应标记为 `待修复`。本轮静态核对确认“随访计划”模块存在明显的前后端权限脱节：

1. 前端只给 `ADMIN / DOCTOR` 显示“随访计划”页面入口。
2. 后端 `/api/plan/page` 实际只校验“已登录”。
3. `FollowupPlanService` 继承通用分页模板，没有按医生本人、患者本人或数据归属做任何过滤。

这意味着 `REPORTER`、`REVIEWER` 等非前端授权角色，仍可直接通过接口读取全部随访计划。

## 主要问题

### 131-1 前端只向医生开放，后端分页却只要求登录

- 前端路由仅向 `ADMIN / DOCTOR` 开放“随访计划”：`131-frontend/src/router/index.js:28`
- 侧边栏菜单同样只给 `ADMIN / DOCTOR` 展示：`131-frontend/src/views/Layout.vue:35`
- 后端分页接口却只做 `assertAuthenticated(role)`：`131-backend/src/main/java/com/drugreaction/controller/FollowupPlanController.java:27-34`

影响：

- 当前权限边界主要停留在前端菜单层。
- 任何已登录角色只要直接调用 `/api/plan/page`，就有机会拿到随访计划数据。

### 131-2 服务层没有按本人或角色做数据过滤

- `FollowupPlanService` 直接继承通用 `BaseCrudService`：`131-backend/src/main/java/com/drugreaction/service/FollowupPlanService.java:11-22`
- 通用分页实现只按 `keyword` 和 `status` 拼查询条件：`131-backend/src/main/java/com/drugreaction/service/BaseCrudService.java:16-30`

影响：

- 后端并没有“医生只看自己负责的计划”或“按病例归属过滤”的实现。
- 当前风险不是单纯“前端少一道限制”，而是服务层真实返回全量数据。

## 静态核对记录

- 本轮已核对：
  - `PRD.md`
  - 前端 `src/router/index.js`
  - 前端 `src/views/Layout.vue`
  - `FollowupPlanController`
  - `FollowupPlanService`
  - 通用 `BaseCrudService`
- 本轮未执行编译、启动和接口实测，当前结论来自静态源码对照。

## 当前判断

`131` 当前状态应标记为：`待修复`

- 角色边界：`不通过`
- 数据归属过滤：`不通过`
- 编译与启动验证：`本轮未执行`

## 建议下一步

1. 将 `/api/plan/page` 至少收紧到 `ADMIN / DOCTOR`。
2. 在 service 或 mapper 层补齐“按当前医生 / 当前业务归属过滤”的查询条件。
3. 复查同项目中的 `followup`、`risk`、`report` 等相邻模块，确认是否存在同类“前端收口、后端放开”的模板问题。
