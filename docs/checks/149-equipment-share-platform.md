# 149 高校实验设备共享预约与违规使用追踪系统检查报告

## 检查范围

- 项目编号：`149`
- 项目名称：高校实验设备共享预约与违规使用追踪系统
- 检查时间：`2026-05-20`
- 目录：
  - 后端：`149-backend`
  - 前端：`149-frontend`
- 主要对照文档：
  - `149-backend/PRD.md`
  - `149-backend/PLAN.md`

## 结论摘要

`149` 当前应标记为 `待修复`，且建议列为优先返修样板。本轮静态核对已确认“预约申请”模块存在高风险本人数据缺口：

1. PRD 明确包含“预约申请：预约编号、设备名称、申请人、预约时间、预约状态和状态维护”：`149-backend/PRD.md:14`
2. `ReservationRequestController` 的 `page / add / update / delete / submit` 五类动作全部只要求“已登录”。
3. `ReservationRequestService` 继承通用 `BaseCrudService`，分页和写操作都没有“申请人本人”校验。

这意味着任意登录用户理论上都能查看全量预约申请，并通过 `id` 修改、删除或提交不属于自己的预约单。

## 主要问题

### 149-1 预约申请分页没有按申请人本人过滤

- PRD 中“预约申请”天然带有申请人归属：`149-backend/PRD.md:14`
- 后端分页接口却只做 `assertAuthenticated(role)`：`149-backend/src/main/java/com/equipmentshare/controller/ReservationRequestController.java:27-34`
- 通用分页实现仅按关键字和状态查询：`149-backend/src/main/java/com/equipmentshare/service/BaseCrudService.java:17-35`

影响：

- 学生、教师、管理员、设备管理员进入预约申请页后，当前实现都会拿到全量申请记录。
- 这与“学生查看本人预约、教师/管理员处理流程”的典型业务边界不一致。

### 149-2 查、增、改、删、提交流程都只要求登录

- `add`、`update`、`delete`、`submit` 全部只做 `assertAuthenticated(role)`：`149-backend/src/main/java/com/equipmentshare/controller/ReservationRequestController.java:37-61`
- 只有 `approve` 才要求 `ADMIN / TEACHER / MANAGER`：`149-backend/src/main/java/com/equipmentshare/controller/ReservationRequestController.java:65-68`
- `ReservationRequestService` 本身只继承通用增删改模板：`149-backend/src/main/java/com/equipmentshare/service/ReservationRequestService.java:11-22`

影响：

- 任意登录用户只要知道一条预约申请 `id`，就可能改写、删除或提交流程中的他人记录。
- 这是明显的横向越权，不是单纯菜单显隐问题。

## 静态核对记录

- 本轮已核对：
  - `PRD.md`
  - `ReservationRequestController`
  - `ReservationRequestService`
  - 通用 `BaseCrudService`
  - 前端 `src/router/index.js`
  - 前端 `src/views/Layout.vue`
- 本轮未执行编译、启动和接口实测，当前结论来自静态源码对照。

## 当前判断

`149` 当前状态应标记为：`待修复`

- 本人数据边界：`不通过`
- 横向越权风险：`不通过`
- 编译与启动验证：`本轮未执行`

## 建议下一步

1. 为学生视角补齐“仅本人预约记录可见、可改、可删、可提交”的后端校验。
2. 将教师 / 设备管理员 / 管理员的审批与查看逻辑分开，避免继续共用同一套通用 CRUD。
3. 以 `149` 为样板，批量回查 `151-200` 中相同模板生成的申请类模块。
