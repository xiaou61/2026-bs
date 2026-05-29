# 145 城市噪声投诉监测与执法协同平台检查报告

## 检查范围

- 项目编号：`145`
- 项目名称：城市噪声投诉监测与执法协同平台
- 检查时间：`2026-05-20`
- 目录：
  - 后端：`145-backend`
  - 前端：`145-frontend`
- 主要对照文档：
  - `145-backend/PRD.md`
  - `145-backend/PLAN.md`

## 结论摘要

`145` 当前应标记为 `待修复`。本轮静态核对确认“处罚决定”模块存在明显的前后端权限脱节：

1. 前端只向 `ADMIN / OFFICER / SUPERVISOR` 开放“处罚决定”页面。
2. 后端 `/api/penalty/page` 却只校验“已登录”。
3. `PenaltyDecisionService` 仍走通用分页模板，没有任何按市民、案件归属或执法范围的过滤。

因此，`CITIZEN` 虽然前端没有处罚决定入口，但拿到 token 后仍可直接读取处罚决定分页数据。

## 主要问题

### 145-1 前端未给市民开放处罚决定，后端分页却只要登录

- 前端路由仅向 `ADMIN / OFFICER / SUPERVISOR` 开放“处罚决定”：`145-frontend/src/router/index.js:30`
- 侧边栏菜单同样未给 `CITIZEN` 开口：`145-frontend/src/views/Layout.vue:38`
- 后端分页接口仅做 `assertAuthenticated(role)`：`145-backend/src/main/java/com/noisemonitor/controller/PenaltyDecisionController.java:27-34`

影响：

- 市民角色虽然前端无入口，但仍可绕过页面直接请求 `/api/penalty/page`。
- 执法处罚结果属于相对敏感的业务数据，当前后端权限边界明显偏宽。

### 145-2 服务层分页没有任何案件或角色过滤

- `PenaltyDecisionService` 直接继承通用分页服务：`145-backend/src/main/java/com/noisemonitor/service/PenaltyDecisionService.java:11-22`
- 通用 `BaseCrudService.page()` 仅按关键字和状态查询：`145-backend/src/main/java/com/noisemonitor/service/BaseCrudService.java:17-33`

影响：

- 当前处罚决定分页返回的是全量数据，而不是“与当前投诉人相关”或“与当前执法角色相关”的子集。
- 这类实现与投诉处理、执法审核应有的最小可见原则不一致。

## 静态核对记录

- 本轮已核对：
  - `PRD.md`
  - 前端 `src/router/index.js`
  - 前端 `src/views/Layout.vue`
  - `PenaltyDecisionController`
  - `PenaltyDecisionService`
  - 通用 `BaseCrudService`
- 本轮未执行编译、启动和接口实测，当前结论来自静态源码对照。

## 当前判断

`145` 当前状态应标记为：`待修复`

- 角色边界：`不通过`
- 数据可见范围：`不通过`
- 编译与启动验证：`本轮未执行`

## 建议下一步

1. 将 `/api/penalty/page` 至少收紧到 `ADMIN / OFFICER / SUPERVISOR`。
2. 如市民确需查看结果，应单独提供“仅本人投诉关联结果”的接口或过滤逻辑。
3. 继续复查 `task`、`rectify`、`retest`、`feedback` 等投诉链路模块。
