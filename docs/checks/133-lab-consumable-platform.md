# 133 实验室耗材采购审批与库存预警系统检查报告

## 检查范围

- 项目编号：`133`
- 项目名称：实验室耗材采购审批与库存预警系统
- 检查时间：`2026-05-20`
- 目录：
  - 后端：`133-backend`
  - 前端：`133-frontend`
- 主要对照文档：
  - `133-backend/PRD.md`
  - `133-backend/PLAN.md`

## 结论摘要

`133` 当前应标记为 `待修复`。本轮静态核对确认“库存台账”模块存在典型的权限模板偏差：

1. 前端只向 `ADMIN / KEEPER / APPROVER` 开放库存台账页面。
2. 后端 `/api/stock/page` 实际只校验“已登录”。
3. `StockItemService` 仍走通用 `BaseCrudService.page()`，没有按角色或业务归属过滤。

因此，前端本不应进入该页面的 `TEACHER` 角色，仍可通过接口直接读取库存台账。

## 主要问题

### 133-1 前端未给教师开放库存台账，但后端分页只要登录

- 前端路由仅向 `ADMIN / KEEPER / APPROVER` 开放“库存台账”：`133-frontend/src/router/index.js:25`
- 侧边栏菜单同样不包含 `TEACHER`：`133-frontend/src/views/Layout.vue:32`
- 后端分页接口却只做 `assertAuthenticated(role)`：`133-backend/src/main/java/com/labconsumable/controller/StockItemController.java:27-34`

影响：

- 教师角色虽然在前端被隐藏入口，但仍可绕过菜单直接请求 `/api/stock/page`。
- 当前实现和“采购审批、库存预警按职责分工”的 PRD 口径不一致。

### 133-2 服务层分页没有任何角色或本人过滤

- `StockItemService` 直接继承通用分页模板：`133-backend/src/main/java/com/labconsumable/service/StockItemService.java:11-22`
- 通用 `BaseCrudService.page()` 仅按关键字和状态分页：`133-backend/src/main/java/com/labconsumable/service/BaseCrudService.java:16-30`

影响：

- 后端并未实现“按实验室、按管理员、按审批职责”的数据可见性边界。
- 这说明风险点在服务层真实存在，不是单纯前端漏配。

## 静态核对记录

- 本轮已核对：
  - `PRD.md`
  - 前端 `src/router/index.js`
  - 前端 `src/views/Layout.vue`
  - `StockItemController`
  - `StockItemService`
  - 通用 `BaseCrudService`
- 本轮未执行编译、启动和接口实测，当前结论来自静态源码对照。

## 当前判断

`133` 当前状态应标记为：`待修复`

- 角色边界：`不通过`
- 数据过滤：`不通过`
- 编译与启动验证：`本轮未执行`

## 建议下一步

1. 将 `/api/stock/page` 至少收紧到 `ADMIN / KEEPER / APPROVER`。
2. 若 PRD 期望按实验室、库管范围分权，应在 mapper 或 service 层补齐维度过滤。
3. 复查 `warning`、`inbound`、`outbound`、`order` 等相邻模块是否也存在同类模板放权。
