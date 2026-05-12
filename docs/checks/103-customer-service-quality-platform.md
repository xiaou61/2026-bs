# 103 智能客服工单质检与知识推荐系统检查报告

## 检查范围

- 项目编号：`103`
- 项目名称：智能客服工单质检与知识推荐系统
- 检查时间：`2026-05-12`
- 目录：
  - 后端：`103-backend`
  - 前端：`103-frontend`
- 主要对照文档：
  - `103-backend/PRD.md`
  - `103-backend/PLAN.md`

## 结论摘要

`103` 当前应判定为 `待修复`。它不像 `102` 那样卡在编译阶段，但存在明显的权限边界缺口，默认环境也仍依赖手工准备：

1. 后端 `mvn.cmd test` 能通过，但没有任何自动化测试，日志显示 `No tests to run`。
2. 前端最终可构建，但第一次安装依赖后执行 `npm.cmd run build` 会报 `vite` 不存在，需要重新补装后再构建。
3. 前端路由和侧边栏完全没有做角色控制，所有登录用户都能看到全部业务页面。
4. 后端多个查询接口同样没有角色断言，导致看板、日志、工单、绩效、质检结果等数据存在越权读取风险。
5. 默认配置仍强依赖本地 MySQL `customer_service_103` 与 Redis。

## 已核对通过项

### 1. 构建链可通过

- 后端执行 `mvn.cmd test` 通过。
- 前端在补齐依赖后执行 `npm.cmd run build` 通过。

### 2. 登录返回已做密码脱敏

- `AuthService.login` 和 `AuthService.info` 均在返回前执行了 `user.setPassword(null)`。

### 3. 基础工程和 SQL 演示数据完整

- `PRD.md`、`PLAN.md`、`application.yml`、`sql/init.sql` 均存在。
- SQL 中已包含 `admin/supervisor/agent/qa` 四个默认账号和完整演示数据。

## 主要问题

### 103-1 前端没有任何角色路由或菜单收口

- `src/router/index.js` 只有“未登录跳登录”的判断，没有任何基于角色的路由限制。
- `src/views/Layout.vue` 直接把 `dashboard/user/customer/channel/category/article/order/message/assignment/rule/quality-task/quality-result/recommend/performance/log` 全量展示给所有登录用户。

影响：

- `AGENT`、`QA`、`SUPERVISOR`、`ADMIN` 登录后看到的页面集合完全相同。
- 这与 PRD 中“管理员、主管、坐席、质检员”的职责边界明显不一致。

### 103-2 后端多个分页和看板接口缺少角色断言，存在越权读取

- `StatisticsController.dashboard()` 没有 `@RequestAttribute role`，也没有任何 `authService` 断言。
- `LogController.page()` 没有角色断言。
- `AgentPerformanceController.page()` 没有角色断言。
- `WorkOrderController.page()` 没有角色断言。
- `CustomerProfileController.page()` 没有角色断言。
- `KnowledgeArticleController.page()` 没有角色断言。
- `QualityTaskController.page()`、`QualityResultController.page()`、`RecommendRecordController.page()` 也都没有角色断言。

影响：

- 只要拿到有效 token，任意角色理论上都能直接请求这些接口。
- 在前端已经全开放菜单的前提下，实际越权读取风险更高。

### 103-3 多个高敏写接口授权范围明显过宽

- `KnowledgeArticleController` 的新增、编辑、发布、下线对全部 `staff` 开放。
- `WorkOrderController` 的新增、编辑、受理、解决、关闭对全部 `staff` 开放。
- `RecommendRecordController` 的新增、编辑、采纳、不采纳对全部 `staff` 开放。
- `AuthService.assertQa()` 把 `ADMIN`、`SUPERVISOR`、`QA` 全部视为可操作质检规则/任务/结果。

影响：

- PRD 更接近“主管负责渠道/派单/绩效，坐席处理工单与知识采纳，质检员负责规则/任务/复核”的模型，但当前实现把不少关键动作向上或横向放宽了。
- 即便后端接口本意允许管理员兜底，也缺少更细的数据归属和最小权限约束。

### 103-4 质检任务执行缺少幂等与状态约束

- `QualityTaskService.run()` 每次调用都会直接插入一条新的 `QualityResult`。
- 方法内部没有检查任务当前状态，也没有判断该任务是否已生成过结果。

影响：

- 同一任务可被重复启动并反复生成结果，容易形成重复质检记录。
- 这类问题与 `097-100` 中已经发现过的“重复复核/重复结果”风险模式一致。

### 103-5 默认环境仍不可开箱即用

- `application.yml` 写死 MySQL `customer_service_103`、Redis `localhost:6379` / DB `6`。
- `sql/init.sql` 需要手工执行，项目本身没有数据库自举能力。
- `PLAN.md` 仍把“不执行编译验证”写入完成标准。

影响：

- 编译通过并不等于可直接验收。
- 交付质量仍偏静态验证，不足以覆盖真实运行问题。

## 验证记录

### 构建验证

- 后端：`mvn.cmd test`
  - 结果：通过
  - 补充：无自动化测试，日志为 `No tests to run`
- 前端：`npm.cmd install --include=dev`
  - 结果：完成
- 前端：首次 `npm.cmd run build`
  - 结果：失败，提示 `vite` 不存在
- 前端：再次执行 `npm.cmd run build`
  - 结果：通过

### 静态权限复核

- 已核对前端路由、菜单、后端控制器断言与 `AuthService`。
- 结论：存在“前端无角色隔离 + 后端查询接口未收口 + 高敏写操作授权过宽”的组合性风险。

## 当前判断

`103` 当前状态：

- JDK17 编译兼容：通过
- 前端构建：通过
- 默认环境直启：不通过
- 角色权限：存在明显缺口
- 自动化测试：缺失

## 建议下一步

1. 先为前端路由和侧边栏补齐按角色展示与跳转限制。
2. 给看板、日志、工单、绩效、质检结果、推荐等查询接口补上后端角色断言和按角色过滤。
3. 收紧知识文章、工单、推荐、质检模块的写权限，避免全部 `staff` 可横向操作。
4. 为 `QualityTaskService.run()` 增加状态机和幂等校验，防止重复生成质检结果。
5. 修改 `PLAN.md` 中“不执行编译验证”的完成标准，并增加最少量后端冒烟测试。
