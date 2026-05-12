# 097 大模型提示词资产管理与效果评测平台检查报告

## 检查范围

- 项目编号：`097`
- 项目名称：`大模型提示词资产管理与效果评测平台`
- 检查时间：`2026-05-11`
- 目录：
  - 后端：`097-backend`
  - 前端：`097-frontend`
- 主要对照文档：
  - `097-backend/PRD.md`
  - `097-backend/PLAN.md`

## 结论摘要

当前 `097` 可以编译、前端也能打包，但**默认环境不可直接演示，且存在多处真实越权**，暂时不能标记为“已完成”。本轮确认的主要问题如下：

1. **默认环境不可用**：后端默认强依赖本地 MySQL/Redis，数据库未手工导入时，登录接口直接 `500`。
2. **管理员参考数据对非管理员暴露**：前端只是隐藏菜单，后端 `team/category/model/rule` 的分页接口未做管理员校验。
3. **任意登录用户可修改他人反馈**：反馈更新接口没有用户归属校验，工程师已实测改写他人反馈成功。
4. **评测结果可被重复复核**：结果复核接口没有状态幂等/终态保护，已审核结果可被二次改判。

## 主要问题

### 097-1 默认环境不可用，数据库未导入时登录直接 500

- 默认配置硬编码本地 MySQL：`097-backend/src/main/resources/application.yml:7-15`
- `PLAN.md` 仍把“默认账号可用于演示系统功能”写成验收标准：`097-backend/PLAN.md:115-121`
- 实际运行结果：
  - 后端能启动 Tomcat，但首次登录时报错 `Unknown database 'prompt_asset_097'`
  - 证据见 `startup-097.log`
  - 手工执行 `097-backend/sql/init.sql` 导入库表后，`POST /api/auth/login` 才恢复 `200`

这说明项目并没有提供“开箱即用”的默认演示环境，文档口径和真实行为不一致。

### 097-2 管理员侧参考数据对非管理员暴露

- 前端菜单仅通过 `v-if="role === 'ADMIN'"` 隐藏管理员入口：`097-frontend/src/views/Layout.vue:7-18`
- 路由层没有角色元信息和角色守卫：`097-frontend/src/router/index.js:4-28`
- 后端以下分页接口没有任何 `assertAdmin(...)`：
  - 团队空间：`097-backend/src/main/java/com/promptops/controller/TeamController.java:34-40`
  - Prompt 分类：`097-backend/src/main/java/com/promptops/controller/CategoryController.java:34-40`
  - 模型配置：`097-backend/src/main/java/com/promptops/controller/ModelController.java:34-40`
  - 评分规则：`097-backend/src/main/java/com/promptops/controller/ScoreRuleController.java:34-40`
- 实测结果：
  - 工程师 token 可访问 `/api/team/page`
  - 工程师 token 可访问 `/api/model/page`
  - 评审员 token 可访问 `/api/rule/page`
  - 接口均返回 `200` 和真实分页数据

这不是单纯的前端菜单漏收口，而是后端真实数据暴露。

### 097-3 任意登录用户可改写他人反馈

- 反馈更新接口没有角色或归属限制：`097-backend/src/main/java/com/promptops/controller/FeedbackController.java:50-55`
- 保存逻辑只校验 `assetId/content`，更新时不会校验原记录归属：`097-backend/src/main/java/com/promptops/service/PromptFeedbackService.java:26-38`
- 实测结果：
  - 工程师账号对反馈 `id=1` 执行 `PUT /api/feedback`
  - 接口返回 `200`
  - 数据库内反馈内容被改成 `工程师越权改写他人反馈`

这是明确的横向越权。

### 097-4 评测结果可被重复复核，终态不受保护

- 复核入口只校验“评测结果存在”：`097-backend/src/main/java/com/promptops/controller/ResultController.java:39-45`
- 服务层复核逻辑没有判断“是否已经复核过”：`097-backend/src/main/java/com/promptops/service/EvaluationResultService.java:24-33`
- 实测结果：
  - 评审员对结果 `id=1` 连续调用两次 `/api/result/review`
  - 第二次请求仍返回 `200`
  - 数据库最终值被改成第二次提交的 `review_comment=第二次改判`

这会破坏评测复核链路的审计可信度。

## 验证记录

### 构建与测试

- 后端：在 `097-backend` 执行 `mvn.cmd test`
  - 结果：`通过`
  - 备注：`No tests to run`，当前没有自动化测试覆盖
- 前端：在 `097-frontend` 执行 `npm.cmd install`、`npm.cmd run build`
  - 结果：`通过`
  - 备注：存在 Vite chunk 体积告警，不阻断构建

### 运行验证

- 后端启动：
  - 命令：`mvn.cmd spring-boot:run -Dspring-boot.run.arguments=--server.port=18097`
  - 结果：Tomcat 启动成功
- 默认登录：
  - 导库前：`POST /api/auth/login` 返回 `500`
  - 原因：数据库 `prompt_asset_097` 不存在
- 数据库准备：
  - 使用 `D:\mySql\mysql-8.0.26-winx64\bin\mysql.exe -uroot -p1234 -e "source init.sql"` 导入
- 导库后：
  - `POST /api/auth/login` 返回 `200`
  - 返回用户对象不含 `password` 字段

### 越权验证

- 工程师访问管理员分页接口：`200`
- 评审员访问评分规则分页接口：`200`
- 工程师更新他人反馈：`200`，数据库已落库
- 评审员二次复核同一条评测结果：`200`，数据库被第二次结果覆盖

## 当前判断

`097` 当前状态应标记为：`待修复`

- 可编译性：`通过`
- 前端构建：`通过`
- 默认环境可用性：`不通过`
- 权限边界：`不通过`
- 数据归属保护：`不通过`

