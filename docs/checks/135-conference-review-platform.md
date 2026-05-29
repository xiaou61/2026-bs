# 135 学术会议投稿评审与日程管理系统检查报告

## 检查范围

- 项目编号：`135`
- 项目名称：学术会议投稿评审与日程管理系统
- 检查时间：`2026-05-20`
- 目录：
  - 后端：`135-backend`
  - 前端：`135-frontend`
- 主要对照文档：
  - `135-backend/PRD.md`
  - `135-backend/PLAN.md`

## 结论摘要

`135` 当前应标记为 `待修复`。本轮静态核对不仅发现了权限边界缺口，还发现“论文投稿”模块本体仍保留库存模板结构：

1. 前端只向 `ADMIN / MANAGER / REVIEWER` 开放论文投稿页，但后端 `/api/paper/page` 只校验“已登录”。
2. `PaperSubmissionService` 仍使用通用库存类字段做关键字分页。
3. `PaperSubmission` 实体直接映射到 `stock_item`，字段仍是 `stockNo / consumableName / labName / currentQty`。

这已经不是轻微命名残留，而是核心业务对象仍未切换到“论文投稿”语义。

## 主要问题

### 135-1 前端未给秘书开放投稿页，后端分页却只要登录

- 前端路由仅向 `ADMIN / MANAGER / REVIEWER` 开放“论文投稿”：`135-frontend/src/router/index.js:25`
- 侧边栏菜单同样不含 `SECRETARY`：`135-frontend/src/views/Layout.vue:32`
- 后端分页接口仅做 `assertAuthenticated(role)`：`135-backend/src/main/java/com/conferencereview/controller/PaperSubmissionController.java:27-34`

影响：

- `SECRETARY` 等非前端授权角色可直接调用 `/api/paper/page` 读取数据。
- 当前权限控制停留在前端显隐层，没有落实到后端。

### 135-2 “论文投稿”实体仍落在库存模板上

- `PaperSubmission` 仍映射 `stock_item` 表：`135-backend/src/main/java/com/conferencereview/entity/PaperSubmission.java:10`
- 实体字段仍是库存语义：`stockNo`、`consumableName`、`labName`、`currentQty`：`135-backend/src/main/java/com/conferencereview/entity/PaperSubmission.java:13-19`
- `PaperSubmissionService` 的关键字字段同样还是库存模板：`135-backend/src/main/java/com/conferencereview/service/PaperSubmissionService.java:11-21`

影响：

- “论文投稿评审系统”的核心对象并未真正建模为投稿记录。
- 即便接口能跑通，返回的数据结构和业务含义也与 PRD 明显不符。

## 静态核对记录

- 本轮已核对：
  - `PRD.md`
  - 前端 `src/router/index.js`
  - 前端 `src/views/Layout.vue`
  - `PaperSubmissionController`
  - `PaperSubmissionService`
  - `PaperSubmission` 实体
  - 通用 `BaseCrudService`
- 本轮未执行编译、启动和接口实测，当前结论来自静态源码对照。

## 当前判断

`135` 当前状态应标记为：`待修复`

- 角色边界：`不通过`
- 核心业务建模：`不通过`
- 编译与启动验证：`本轮未执行`

## 建议下一步

1. 将 `/api/paper/page` 至少收紧到真正需要的角色集合。
2. 重建 `PaperSubmission` 的表映射、字段命名和关键字搜索列，去掉库存模板残留。
3. 复查 `author`、`reviewer`、`assignment` 等相关模块，确认是否也残留 `stock` / `consumable` 领域字段。
