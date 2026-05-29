# 105 API 接口文档生成与测试用例管理平台检查报告

## 检查范围

- 项目编号：`105`
- 项目名称：API 接口文档生成与测试用例管理平台
- 检查时间：`2026-05-20`
- 目录：
  - 后端：`105-backend`
  - 前端：`105-frontend`
- 主要对照文档：
  - `105-backend/PRD.md`
  - `105-backend/PLAN.md`
  - `105-backend/sql/init.sql`
  - `105-backend/src/main/resources/application.yml`
  - `105-frontend/src/router/index.js`
  - `105-frontend/src/views/Layout.vue`

## 结论摘要

`105` 当前建议标记为 `待修复`。本轮静态核对确认该项目的大框架与 PRD 基本一致，但仍存在明确的功能闭环缺口：

1. `PRD.md`、`PLAN.md`、`sql/init.sql` 与 `application.yml` 在数据库名、端口和默认账号口径上保持一致。
2. 前端菜单、路由和后端控制器已经覆盖接口项目、分组、定义、参数、Mock、测试用例、执行记录、结果明细、文档快照和日志等核心模块。
3. 但 `PLAN.md` 明写“Mock 规则启停”和“业务按钮支持执行、复制、发布、启停和查看结果”，当前只确认到了执行、发布、查看结果、测试用例启停；`Mock规则启停` 和 `复制` 仍未落地。

因此，`105` 不属于 `124` 那种主题串项项目，但也不能按“静态基本通过”直接放过，必须正式挂为 `待修复`。

## 主要问题

### 105-1 基础配置与 PRD 口径一致

- `PRD.md` 明确数据库名、前后端端口和默认账号：`105-backend/PRD.md:25-33`
- `PLAN.md` 明确创建 `api_testcase_105` 数据库和 14 张业务表：`105-backend/PLAN.md:15`
- 初始化 SQL 直接 `DROP/CREATE/USE api_testcase_105`：`105-backend/sql/init.sql:1-3`
- 实际运行配置使用后端端口 `8105`，数据源也指向 `api_testcase_105`：`105-backend/src/main/resources/application.yml:2`、`105-backend/src/main/resources/application.yml:6`

说明：

- 本轮未发现数据库串项、端口串项或默认账号口径错配。
- `105` 的问题不在主题错位，而在功能闭环没有做满。

### 105-2 前端路由、菜单与后端角色骨架基本覆盖 PRD 模块

- 前端已定义四类角色首页：`ADMIN / PRODUCT / TESTER / DEVELOPER`：`105-frontend/src/router/index.js:4-8`
- 路由层已覆盖 14 个核心业务模块：`105-frontend/src/router/index.js:20-34`
- 侧边菜单与路由保持一致：`105-frontend/src/views/Layout.vue:26-41`
- JWT 拦截器会写入 `userId / username / role`：`105-backend/src/main/java/com/apitestcase/config/JwtInterceptor.java:24-31`
- `AuthService` 已抽出 `assertAdmin`、`assertAdminOrProduct`、`assertAdminOrProductOrDeveloper`、`assertAdminOrTesterOrDeveloper`、`assertAdminOrProductOrTester` 与 `assertAuthenticated`：`105-backend/src/main/java/com/apitestcase/service/AuthService.java:54-79`

说明：

- 从静态骨架看，`105` 的角色体系和 PRD 题目是对得上的。
- 本轮未发现像 `149`、`150` 那样“前端收口、后端只认已登录”的明显越权模板问题。

### 105-3 `Mock规则启停` 没有落地，和 PLAN 明确冲突

- `PLAN.md` 明确写了“实现用例执行、结果回写、Mock 规则启停和文档快照管理”：`105-backend/PLAN.md:18`
- `PLAN.md` 还写明“业务按钮支持执行、复制、发布、启停和查看结果”：`105-backend/PLAN.md:25`
- 当前 `MockRuleController` 只有 `page / add / update / delete`，没有 `enable / disable / start / stop / publish / archive` 等启停动作：`105-backend/src/main/java/com/apitestcase/controller/MockRuleController.java:23-54`
- 前端 `MockRule.vue` 只接了 CRUD API，且 `rowActions` 为空：`105-frontend/src/views/MockRule.vue:8-12`
- 前端 API 也只有 `get / add / update / delete`，没有对应启停接口：`105-frontend/src/api/index.js:34-37`

影响：

- PRD/PLAN 已经把 Mock 规则视为动态测试环节的一部分，但当前实现还停留在静态台账维护。
- 这属于明确功能缺口，不是可忽略的文档细节差异。

### 105-4 `复制` 动作未见实现，业务按钮闭环不完整

- `PLAN.md` 写明“业务按钮支持执行、复制、发布、启停和查看结果”：`105-backend/PLAN.md:25`
- 当前 `TestExecution.vue` 已实现执行相关动作 `start / finish / fail`：`105-frontend/src/views/TestExecution.vue:12-17`；对应 API 也已提供：`105-frontend/src/api/index.js:56-58`
- 当前 `DocumentSnapshot.vue` 已实现 `publish / archive`：`105-frontend/src/views/DocumentSnapshot.vue:12-16`；对应 API 也已提供：`105-frontend/src/api/index.js:69-70`
- 当前 `TestCase.vue` 只实现了 `enable / disable`，没有“复制”动作：`105-frontend/src/views/TestCase.vue:12-16`
- 本轮对 `105-frontend/src` 与 `105-backend/src/main/java` 做关键字静态扫描，未发现 `copy / 复制` 相关实现。

影响：

- 计划要求的按钮能力目前只完成了其中一部分，页面和接口的交付口径还不完整。
- 如果后续要按“接口文档+测试用例协同平台”验收，这个缺口会直接影响复用和快速编排场景。

### 105-5 当前仍缺少编译与启动验证

- `PLAN.md` 完成标准仍明确写着“不执行编译验证”：`105-backend/PLAN.md:40`

影响：

- 即使本轮静态结构大体可用，也还不能证明 `105` 在当前环境下能完成 JDK17 编译、前端构建和基础登录。
- 这意味着它当前既有功能缺口，也缺少运行层面的闭环证据。

## 静态核对记录

- 本轮已核对：
  - `PRD.md`
  - `PLAN.md`
  - `sql/init.sql`
  - `application.yml`
  - 前端 `router/index.js`
  - 前端 `Layout.vue`
  - 前端 `MockRule.vue`
  - 前端 `TestCase.vue`
  - 前端 `TestExecution.vue`
  - 前端 `DocumentSnapshot.vue`
  - 前端 `api/index.js`
  - 后端 `JwtInterceptor`
  - 后端 `AuthService`
  - 后端关键 controller
- 静态结构计数：
  - SQL 建表：`14`
  - Controller：`16`
  - 前端视图：`17`
- 本轮未执行 Maven / NPM 编译与启动验证。

## 当前判断

`105` 当前状态建议标记为：`待修复`

- 数据库、端口、默认账号口径：`通过`
- 路由、菜单、角色骨架：`基本通过`
- Mock 规则启停闭环：`不通过`
- 复制动作：`不通过`
- 编译与启动验证：`本轮未执行`

## 建议下一步

1. 优先补齐 `MockRule` 的启停动作，并同步前端 `rowActions` 与 API 封装。
2. 明确“复制”动作应该落在测试用例、接口定义还是文档快照，并补齐对应前后端实现。
3. 功能补齐后，再补一次后端编译、前端构建和基础登录/页面联调验证。
