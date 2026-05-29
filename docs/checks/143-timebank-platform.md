# 143 社区公益时间银行互助服务平台检查报告

## 检查范围

- 项目编号：`143`
- 项目名称：社区公益时间银行互助服务平台
- 检查时间：`2026-05-20`
- 目录：
  - 后端：`143-backend`
  - 前端：`143-frontend`
- 主要对照文档：
  - `143-backend/PRD.md`
  - `143-backend/PLAN.md`

## 结论摘要

`143` 当前应标记为 `待修复`。本轮静态核对确认“互助兑换”模块存在明显的数据归属缺口：

1. `TimeExchangeController.page()` 只校验“已登录”。
2. `add / update / delete / submit` 对 `ADMIN / RESIDENT / VOLUNTEER` 全开放，但没有记录归属校验。
3. `TimeExchangeService` 仍继承通用 `BaseCrudService`，分页、保存、删除和状态流转都按主键直接操作。

这意味着普通居民或志愿者理论上可以查看、修改、删除乃至提交流程中不属于自己的互助兑换记录。

## 主要问题

### 143-1 互助兑换分页未按本人或角色范围收口

- 前端“互助兑换”页向 `ADMIN / RESIDENT / VOLUNTEER / MANAGER` 开放：`143-frontend/src/router/index.js:29`、`143-frontend/src/views/Layout.vue:37`
- 后端分页接口仅做 `assertAuthenticated(role)`：`143-backend/src/main/java/com/timebank/controller/TimeExchangeController.java:27-34`
- 通用分页实现只按关键字与状态查询：`143-backend/src/main/java/com/timebank/service/BaseCrudService.java:17-33`

影响：

- 居民和志愿者进入页面后，拿到的是全量互助兑换分页，而不是“本人发起 / 本人参与”的记录范围。
- 这和时间银行场景下天然需要的本人数据边界不一致。

### 143-2 居民和志愿者可直接改删提交流程记录

- `add`、`update`、`delete`、`submit` 都允许 `ADMIN / RESIDENT / VOLUNTEER`：`143-backend/src/main/java/com/timebank/controller/TimeExchangeController.java:37-61`
- `TimeExchangeService` 仅按主键保存、删除、改状态，没有校验当前登录人与记录归属：`143-backend/src/main/java/com/timebank/service/TimeExchangeService.java:11-22`、`143-backend/src/main/java/com/timebank/service/BaseCrudService.java:35-46`

影响：

- 任何居民或志愿者只要拿到一条 `id`，就有可能操作他人的兑换记录。
- 这是明确的横向越权风险。

## 静态核对记录

- 本轮已核对：
  - `PRD.md`
  - 前端 `src/router/index.js`
  - 前端 `src/views/Layout.vue`
  - `TimeExchangeController`
  - `TimeExchangeService`
  - 通用 `BaseCrudService`
- 本轮未执行编译、启动和接口实测，当前结论来自静态源码对照。

## 当前判断

`143` 当前状态应标记为：`待修复`

- 数据归属边界：`不通过`
- 横向越权风险：`不通过`
- 编译与启动验证：`本轮未执行`

## 建议下一步

1. 为 `page`、`update`、`delete`、`submit` 补齐“当前用户是否为记录所有者 / 审核人”的校验。
2. 将居民、志愿者可见的兑换记录收敛到本人相关数据。
3. 顺带复查 `booking`、`feedback`、`account` 等含本人台账属性的模块。
