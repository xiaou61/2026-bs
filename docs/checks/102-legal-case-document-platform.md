# 102 法律咨询案件进度与智能文书管理系统检查报告

## 检查范围

- 项目编号：`102`
- 项目名称：法律咨询案件进度与智能文书管理系统
- 检查时间：`2026-05-12`
- 目录：
  - 后端：`102-backend`
  - 前端：`102-frontend`
- 主要对照文档：
  - `102-backend/PRD.md`
  - `102-backend/PLAN.md`

## 结论摘要

`102` 当前应判定为 `待修复`。它不是单一环境问题，而是同时存在“后端无法编译”和“权限收口缺失”两类阻断：

1. 后端 `mvn.cmd test` 在编译阶段失败，`DocumentTemplateService`、`FeeRecordService` 缺少控制器调用的 `saveEntity(...)`。
2. 前端可构建，但首次 `npm install` 后 `vite` 不可用，需要补装 devDependencies 才能成功打包。
3. 多个查询与文书相关接口只靠前端菜单隐藏，后端没有角色断言或按角色过滤，存在明显越权读取风险。
4. 默认配置仍强依赖本地 MySQL `legal_case_102` 与 Redis，且 `PLAN.md` 仍写着“不执行编译验证”。

## 已核对通过项

### 1. 项目基础资料齐全

- `PRD.md`、`PLAN.md`、`sql/init.sql`、前后端主工程均存在。
- SQL 内包含默认账号 `admin/lawyer/assistant/client` 及演示数据。

### 2. 登录返回已做密码脱敏

- `AuthService.login`、`AuthService.info` 都在响应前执行了 `user.setPassword(null)`。

### 3. 前端在补齐 devDependencies 后可以构建

- `npm.cmd install --include=dev`
- `npm.cmd run build`
- 构建通过，但有大体积 chunk 警告。

## 主要问题

### 102-1 后端编译失败，项目无法启动

- `DocumentTemplateController` 新增/编辑调用 `service.saveEntity(documentTemplate)`，但 `DocumentTemplateService` 未实现该方法。
- `FeeRecordController` 新增/编辑调用 `service.saveEntity(feeRecord)`，但 `FeeRecordService` 未实现该方法。
- 因此 `mvn.cmd test` 在 `compile` 阶段直接失败，后端无法启动，更无法继续做运行态验收。

影响：

- 文书模板和费用记录是 PRD 核心模块，当前属于基础功能直接缺失。
- `PLAN.md` 写“不执行编译验证”直接掩盖了这一问题。

### 102-2 文书、版本、费用分页接口存在越权读取风险

- `LegalDocumentController.page` 没有 `@RequestAttribute role` 参数，也没有任何 `authService` 断言。
- `DocumentVersionController.page` 同样没有角色断言。
- `FeeRecordController.page` 也没有角色断言，且服务层没有按当前用户做数据过滤。

影响：

- 任何已登录角色理论上都能直接请求这些分页接口。
- 这与前端“律师看文书/版本、助理看费用、委托人不看这些数据”的菜单限制不一致。
- 属于典型“前端藏菜单，后端未收口”问题。

### 102-3 多个写接口角色边界明显放宽

- `ConsultationRecordController` 使用 `authService.assertStaff(role)`，意味着助理也能直接新增、编辑、删除咨询记录。
- `LegalDocumentController` 对新增、编辑、生成、复核统一使用 `assertStaff(role)`，意味着助理可直接生成和复核法律文书。
- `DocumentVersionController` 也允许所有 staff 新增、编辑、删除版本。
- `EvidenceMaterialController` 允许所有 staff 核验和驳回证据材料。

影响：

- PRD 与前端菜单更接近“律师主导咨询/文书，助理做协同”的模型，但后端当前把多数高敏操作开放给了全部 staff。
- 即便项目编译修好，仍会存在业务权限过宽问题。

### 102-4 默认环境仍依赖手工准备 MySQL 与 Redis

- `application.yml` 写死数据库 `legal_case_102`、Redis `localhost:6379` / DB `5`。
- `sql/init.sql` 需要手工执行，没有内置自举逻辑。

影响：

- 项目修复编译错误后，默认环境仍不能保证开箱即用。

## 验证记录

### 构建验证

- 后端：`mvn.cmd test`
  - 结果：失败
  - 关键原因：`DocumentTemplateService`、`FeeRecordService` 缺少 `saveEntity(...)`
- 前端：`npm.cmd install`
  - 首次构建结果：失败，提示 `vite` 不存在
- 前端：`npm.cmd install --include=dev && npm.cmd run build`
  - 结果：通过

### 静态权限复核

- 已核对控制器与服务层的角色断言。
- 结论：存在未鉴权分页接口和“全部 staff 可写高敏文书/咨询/版本数据”的边界放宽问题。

## 当前判断

`102` 当前状态：

- JDK17 编译兼容：不通过
- 前端构建：通过
- 默认环境直启：不通过
- 角色权限：存在明显缺口

## 建议下一步

1. 先为 `DocumentTemplateService`、`FeeRecordService` 补齐 `saveEntity(...)`，恢复后端可编译。
2. 给 `/api/document/page`、`/api/version/page`、`/api/fee/page` 补上后端角色断言和按角色过滤。
3. 重新核对咨询记录、法律文书、文书版本、证据核验接口的最小权限范围，避免把律师专属操作开放给全部 staff。
4. 修改 `PLAN.md` 中“不执行编译验证”的完成标准，并增加最少量冒烟测试。
