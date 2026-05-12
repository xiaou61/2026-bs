# 099 AIGC 图片内容审核与版权存证平台检查报告

## 检查范围

- 项目编号：`099`
- 项目名称：`AIGC 图片内容审核与版权存证平台`
- 检查时间：`2026-05-11`
- 目录：
  - 后端：`099-backend`
  - 前端：`099-frontend`
- 主要对照文档：
  - `099-backend/PRD.md`
  - `099-backend/PLAN.md`

## 结论摘要

当前 `099` 能编译、前端也能打包，但**默认环境不可直接演示，且角色边界存在明显收口失败**。本轮确认的主要问题如下：

1. **默认环境不可用**：后端默认强依赖本地 MySQL/Redis，数据库未导入时登录直接 `500`。
2. **管理员/审核员侧数据对创作者暴露**：前端隐藏了规则、标签、审核结果入口，但后端分页接口没有相应角色校验。
3. **审核员可越权修改创作者资源**：后端把 `AUDITOR` 也算进了 `assertCreator(...)`，导致审核员能编辑创作者作品。
4. **审核结果可被重复复核**：复核接口没有终态保护，第二次复核可以直接覆盖第一次意见。

## 主要问题

### 099-1 默认环境不可用，数据库未导入时登录直接 500

- 默认配置硬编码本地 MySQL：`099-backend/src/main/resources/application.yml:7-15`
- 真实启动日志显示：
  - Tomcat 已在 `18099` 成功启动：`startup-099.log:74`
  - 应用已完成启动：`startup-099.log:1130`
  - 首次登录时抛出 `Unknown database 'aigc_copyright_099'`：`startup-099.log:1150,1260`
- 实测：
  - 导库前 `POST /api/auth/login` 返回 `500`
  - 手工执行 `099-backend/sql/init.sql` 导入后，登录恢复 `200`

这说明项目没有提供默认可运行的本地演示环境。

### 099-2 管理员/审核员侧数据对创作者暴露

- 前端只在菜单层隐藏这些入口：
  - 审核规则：`099-frontend/src/views/Layout.vue:9`
  - 审核结果：`099-frontend/src/views/Layout.vue:10-11`
  - 风险标签：`099-frontend/src/views/Layout.vue:12`
- 路由层没有角色守卫，登录后可直达全部页面：`099-frontend/src/router/index.js:10-24,33-42`
- 后端以下分页接口没有任何角色限制：
  - 审核规则：`099-backend/src/main/java/com/aigccopyright/controller/RuleController.java:34-41`
  - 风险标签：`099-backend/src/main/java/com/aigccopyright/controller/TagController.java:34-41`
  - 审核结果：`099-backend/src/main/java/com/aigccopyright/controller/AuditResultController.java:31-38`
- 但对应写操作分别要求管理员或审核员：
  - `RuleController.java:43-64`
  - `TagController.java:43-64`
  - `AuditResultController.java:40-45`
- 实测结果：
  - 创作者 token 访问 `/api/rule/page` 返回 `200`
  - 创作者 token 访问 `/api/tag/page` 返回 `200`
  - 创作者 token 访问 `/api/result/page` 返回 `200`

这表明管理员/审核员侧审核元数据和审核结果已对创作者真实泄露。

### 099-3 审核员可越权修改创作者作品

- `assertCreator(...)` 把 `ADMIN`、`AUDITOR`、`CREATOR` 全部视为“创作者侧允许角色”：`099-backend/src/main/java/com/aigccopyright/service/AuthService.java:75-78`
- 图片作品写接口直接复用这个判断：
  - 新增：`099-backend/src/main/java/com/aigccopyright/controller/AssetController.java:43-49`
  - 编辑：`AssetController.java:51-56`
  - 删除：`AssetController.java:59-64`
- 作品保存逻辑只校验标题和图片地址，更新时不会校验记录归属：`099-backend/src/main/java/com/aigccopyright/service/ImageAssetService.java:26-36`
- 实测结果：
  - 审核员使用 `/api/asset` 更新作品 `id=1`
  - 接口返回 `200`
  - 数据库中 `image_asset.id=1.description` 被改成 `审核员越权修改创作者作品`

这不是“审核员可辅助处理流程”，而是审核员直接拥有了创作者资源的编辑权限。

### 099-4 审核结果可被重复复核，终态不受保护

- 复核入口只要求角色为审核员：`099-backend/src/main/java/com/aigccopyright/controller/AuditResultController.java:40-45`
- 服务层复核逻辑只按 `id` 取记录并覆盖 `reviewStatus/reviewComment`：`099-backend/src/main/java/com/aigccopyright/service/AuditResultService.java:25-34`
- 实测结果：
  - 审核员对结果 `id=2` 第一次复核返回 `200`
  - 随后第二次复核仍返回 `200`
  - 数据库中 `review_comment` 最终被改成 `第二次改判`

这会破坏审核结果的审计稳定性。

## 验证记录

### 构建与测试

- 后端：在 `099-backend` 执行 `mvn.cmd test`
  - 结果：`通过`
  - 备注：`No tests to run`，当前没有自动化测试
- 前端：在 `099-frontend` 执行 `npm.cmd install`、`npm.cmd run build`
  - 结果：`通过`
  - 备注：存在大 chunk 告警，主包和 `Dashboard` chunk 超过 `500 kB`

### 运行验证

- 后端启动：
  - 命令：`mvn.cmd spring-boot:run -Dspring-boot.run.arguments=--server.port=18099`
  - 结果：启动成功
- 默认登录：
  - 导库前：`500`
  - 原因：`aigc_copyright_099` 不存在
- 数据库准备：
  - 使用 `D:\mySql\mysql-8.0.26-winx64\bin\mysql.exe -uroot -p1234 -e "source init.sql"` 导入
- 导库后：
  - 管理员、审核员、创作者登录均返回 `200`
  - 登录和 `/api/auth/info` 返回对象均不含 `password` 字段

### 越权验证结果

- 创作者访问 `/api/rule/page`：`200`
- 创作者访问 `/api/tag/page`：`200`
- 创作者访问 `/api/result/page`：`200`
- 审核员更新作品 `id=1`：`200`
- 审核员第一次复核结果 `id=2`：`200`
- 审核员第二次复核结果 `id=2`：`200`

## 当前判断

`099` 当前状态应标记为：`待修复`

- 后端可编译：`通过`
- 前端构建：`通过`
- 默认环境可用性：`不通过`
- 角色边界：`不通过`
- 数据归属保护：`不通过`
- 自动化测试覆盖：`缺失`

