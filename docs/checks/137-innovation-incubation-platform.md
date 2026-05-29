# 137 大学生创新创业项目孵化管理平台检查报告

## 检查范围

- 项目编号：`137`
- 项目名称：大学生创新创业项目孵化管理平台
- 检查时间：`2026-05-20`
- 目录：
  - 后端：`137-backend`
  - 前端：`137-frontend`
- 主要对照文档：
  - `137-backend/PRD.md`
  - `137-backend/PLAN.md`

## 结论摘要

`137` 当前应标记为 `待修复`。本轮静态核对确认“路演评分”模块仍明显保留入库模板残留，且当前角色边界过宽：

1. 前端已把 `STUDENT` 也放入“路演评分”页面 roles 列表。
2. 后端 `/api/score/page` 仅校验“已登录”，没有按评委、项目归属或评分记录归属过滤。
3. `RoadshowScore` 实体仍映射 `inbound_record`，字段仍是 `inboundNo / orderNo / consumableName / inboundQty`。

这说明当前模块并不是“路演评分”真实落地，而是批量模板换壳后仍未完成语义切换。

## 主要问题

### 137-1 路演评分结果对登录用户普遍开放

- 前端路由把 `ADMIN / MENTOR / STUDENT / JUDGE` 都纳入“路演评分”：`137-frontend/src/router/index.js:29`
- 侧边栏菜单同样把 `STUDENT` 放进“路演评分”：`137-frontend/src/views/Layout.vue:36`
- 后端分页接口仅做 `assertAuthenticated(role)`：`137-backend/src/main/java/com/innovationhub/controller/RoadshowScoreController.java:27-34`

影响：

- 当前实现没有体现“评委评分、导师查看、学生查看本人结果”这类细粒度边界。
- 只要有有效 token，就可以读取评分分页，和 PRD 场景下应有的数据范围不匹配。

### 137-2 路演评分实体仍映射入库记录模板

- `RoadshowScore` 仍直接映射 `inbound_record`：`137-backend/src/main/java/com/innovationhub/entity/RoadshowScore.java:10`
- 实体字段仍是 `inboundNo / orderNo / consumableName / inboundQty`：`137-backend/src/main/java/com/innovationhub/entity/RoadshowScore.java:13-19`
- `RoadshowScoreService` 的关键字字段也仍是入库模板：`137-backend/src/main/java/com/innovationhub/service/RoadshowScoreService.java:11-21`

影响：

- 当前评分模块没有落在项目编号、评委、评分项、总分、意见等创业路演语义上。
- 这是核心业务对象仍未切换完成的硬性错配。

## 静态核对记录

- 本轮已核对：
  - `PRD.md`
  - 前端 `src/router/index.js`
  - 前端 `src/views/Layout.vue`
  - `RoadshowScoreController`
  - `RoadshowScoreService`
  - `RoadshowScore` 实体
- 本轮未执行编译、启动和接口实测，当前结论来自静态源码对照。

## 当前判断

`137` 当前状态应标记为：`待修复`

- 角色边界：`不通过`
- 核心业务建模：`不通过`
- 编译与启动验证：`本轮未执行`

## 建议下一步

1. 重新定义路演评分的角色边界，至少区分评委录入、导师查看、学生查看本人结果。
2. 重建 `RoadshowScore` 对应的数据表、字段和关键字搜索列，清理 `inbound_record` 模板残留。
3. 继续复查 `funding`、`achievement`、`milestone` 等邻近模块，确认是否也残留仓储模板字段。
