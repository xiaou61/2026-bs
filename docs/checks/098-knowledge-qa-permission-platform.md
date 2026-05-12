# 098 企业知识库智能问答与文档权限管理系统检查报告

## 检查范围

- 项目编号：`098`
- 项目名称：`企业知识库智能问答与文档权限管理系统`
- 检查时间：`2026-05-11`
- 目录：
  - 后端：`098-backend`
  - 前端：`098-frontend`
- 主要对照文档：
  - `098-backend/PRD.md`
  - `098-backend/PLAN.md`

## 结论摘要

当前 `098` 已完成静态代码生成，后端可编译、前端可打包，但**默认环境不可直接演示，且“文档权限管理”核心能力并未真正落地到问答和会话访问控制**。本轮确认的主要问题如下：

1. **默认环境不可用**：后端默认强依赖本地 MySQL，数据库不存在时登录直接 `500`。
2. **管理员授权数据对普通用户暴露**：前端隐藏了“权限组/组成员/文档授权”菜单，但后端分页接口未做管理员校验。
3. **文档权限级别未真正参与问答鉴权**：编辑员可在仅有 `READ` 授权的制度库创建会话并成功提问。
4. **任意登录用户可操作他人会话和反馈**：编辑员已实测关闭员工会话、修改员工反馈成功。

## 主要问题

### 098-1 默认环境不可用，数据库未导入时登录直接 500

- 默认配置硬编码本地 MySQL：`098-backend/src/main/resources/application.yml:7-14`
- 真实启动日志显示：
  - Tomcat 已在 `18098` 成功启动：`startup-098.log:79`
  - 应用已完成启动：`startup-098.log:1127`
  - 首次登录时抛出 `Unknown database 'knowledge_qa_098'`：`startup-098.log:1147,1247`
- 实测：
  - 导库前 `POST /api/auth/login` 返回 `500`
  - 手工执行 `098-backend/sql/init.sql` 导入后，登录恢复 `200`

这说明项目没有提供默认可运行的本地演示环境。

### 098-2 管理员授权数据对普通用户暴露

- 前端仅通过菜单隐藏管理员侧入口：`098-frontend/src/views/Layout.vue:12-18`
- 路由层没有角色守卫，登录后可直达所有业务页：`098-frontend/src/router/index.js:10-24,33-42`
- 后端以下分页接口没有 `assertAdmin(...)`：
  - 权限组：`098-backend/src/main/java/com/knowledgeqa/controller/GroupController.java:36-42`
  - 组成员：`098-backend/src/main/java/com/knowledgeqa/controller/MemberController.java:36-43`
  - 文档授权：`098-backend/src/main/java/com/knowledgeqa/controller/PermissionController.java:36-45`
- 但对应新增/修改/删除却要求管理员：
  - `GroupController.java:44-66`
  - `MemberController.java:45-66`
  - `PermissionController.java:47-68`
- 实测结果：
  - 员工 token 访问 `/api/group/page` 返回 `200`
  - 员工 token 访问 `/api/permission/page` 返回 `200`
  - 编辑员 token 访问 `/api/member/page` 返回 `200`

这表明管理员侧权限结构和授权关系已对非管理员真实泄露。

### 098-3 文档权限级别没有真正用于问答鉴权

- 会话创建只做角色校验，不校验空间授权或权限级别：`098-backend/src/main/java/com/knowledgeqa/controller/SessionController.java:49-57`
- 提问接口只做角色校验：`098-backend/src/main/java/com/knowledgeqa/controller/RecordController.java:49-57`
- 问答服务只根据 `session.spaceId` 直接抓取该空间下已索引分段：
  - 查询会话：`098-backend/src/main/java/com/knowledgeqa/service/KnowledgeQaService.java:43-52`
  - 命中分段：`098-backend/src/main/java/com/knowledgeqa/mapper/CommonMapper.java:53-57`
- 整个过程没有任何 `document_permission`、`group_member`、`permission_level` 判断。
- 实测结果：
  - 编辑员可成功创建 `spaceId=2` 的会话，接口返回 `200`
  - 随后可成功调用 `/api/record/ask`，返回 `200`
  - 返回命中来源 `sourceChunkIds = "4"`，说明制度库内容已被真实问答读取

这直接推翻了“文档权限管理系统”的核心权限承诺。

### 098-4 任意登录用户可操作他人会话和反馈

- 会话分页/更新/关闭/删除都没有按 `userId` 过滤，也没有归属校验：
  - 分页：`098-backend/src/main/java/com/knowledgeqa/controller/SessionController.java:40-47`
  - 更新/关闭/删除：`098-backend/src/main/java/com/knowledgeqa/controller/SessionController.java:60-80`
  - 关闭服务只按主键更新：`098-backend/src/main/java/com/knowledgeqa/service/KnowledgeQaService.java:38-41`
- 反馈分页/更新同样没有归属校验：
  - 分页：`098-backend/src/main/java/com/knowledgeqa/controller/FeedbackController.java:38-46`
  - 更新：`098-backend/src/main/java/com/knowledgeqa/controller/FeedbackController.java:58-63`
  - `CrudService.save(...)` 更新分支只按 `id` 直接写库：`098-backend/src/main/java/com/knowledgeqa/service/CrudService.java:50-64`
- 实测结果：
  - 编辑员调用 `/api/session/close/1` 返回 `200`
  - 数据库中 `qa_session.id=1.status` 变为 `1`
  - 编辑员调用 `PUT /api/feedback` 返回 `200`
  - 数据库中 `qa_feedback.id=1.content` 被改成 `编辑员越权修改他人反馈`

这是明确的横向越权问题。

## 验证记录

### 构建与测试

- 后端：在 `098-backend` 执行 `mvn.cmd test`
  - 结果：`通过`
  - 备注：`No tests to run`，当前没有任何自动化测试
- 前端：在 `098-frontend` 执行 `npm.cmd install`、`npm.cmd run build`
  - 结果：`通过`
  - 备注：存在大 chunk 告警，`Dashboard` 和主包均超过 `500 kB`

### 运行验证

- 后端启动：
  - 命令：`mvn.cmd spring-boot:run -Dspring-boot.run.arguments=--server.port=18098`
  - 结果：启动成功
- 默认登录：
  - 导库前：`500`
  - 原因：`knowledge_qa_098` 不存在
- 数据库准备：
  - 使用 `D:\mySql\mysql-8.0.26-winx64\bin\mysql.exe -uroot -p1234 -e "source init.sql"` 导入
- 导库后：
  - 管理员、编辑员、员工登录均返回 `200`
  - 登录返回用户对象不含 `password` 字段

### 越权验证结果

- 员工访问 `/api/group/page`：`200`
- 员工访问 `/api/permission/page`：`200`
- 编辑员访问 `/api/member/page`：`200`
- 编辑员在 `spaceId=2` 创建会话：`200`
- 编辑员在 `spaceId=2` 发起问答：`200`
- 编辑员关闭员工会话 `id=1`：`200`
- 编辑员更新员工反馈 `id=1`：`200`

## 当前判断

`098` 当前状态应标记为：`待修复`

- 后端可编译：`通过`
- 前端构建：`通过`
- 默认环境可用性：`不通过`
- 文档权限模型：`不通过`
- 会话/反馈归属保护：`不通过`
- 自动化测试覆盖：`缺失`

