# 012 在线协作白板与笔记系统检查报告

## 1. 基本信息

- 项目编号：`012`
- 项目名称：在线协作白板与笔记系统
- 检查日期：`2026-04-05`
- 项目目录：
  - 后端：`012-backend`
- 主要资料：
  - 项目总览：`readme_simple.md`
  - 后端 PRD：`012-backend/PRD/PRD.md`
  - 账号文档：`012-backend/ACCOUNTS.md`
  - 前端说明：`012-backend/THYMELEAF_README.md`

## 2. 项目形态结论

- 当前仓库仅存在 `012-backend`，未发现独立 `012-frontend`
- 结合 `THYMELEAF_README.md`、模板目录和 `ViewController`，可确认该项目是 `Spring Boot + Thymeleaf` 一体化项目
- 页面入口主要为：
  - `/login`
  - `/dashboard`
  - `/documents`
  - `/document/{id}`
  - `/teams`
  - `/templates`
  - `/profile`
  - `/share/{shareLink}`

## 3. 文档与技术栈检查

### 3.1 技术栈

- Spring Boot `3.2.0`
- MyBatis-Plus `3.5.5`
- MySQL `8`
- Redis
- JWT `0.12.5`
- Thymeleaf
- Bootstrap 5
- jQuery + Axios
- WebSocket
- JDK `17`

### 3.2 运行前置

- HTTP 端口：`8012`
- WebSocket 路径：`/ws/collab/{documentId}`
- 数据库：`collab_board`
- 数据库账号：`root / 1234`
- 文件目录：`D:/collab-board-files/`
- 初始化 SQL：`012-backend/src/main/resources/sql/collab_board.sql`

### 3.3 已确认的文档问题

1. `PRD.md` 中管理员初始密码原写为 `admin123`
2. `ACCOUNTS.md` 实际写为 `123456`
3. `THYMELEAF_README.md` 原先同时存在两种相互矛盾说法：
   - 页面都是独立 HTML，不使用布局继承
   - 使用 `layout.html` 作为主模板
4. 当前模板目录并不存在 `layout.html`

以上文档冲突本轮已修正。

## 4. 当前源码覆盖情况

### 4.1 已落地模块

- 认证：`AuthController`
- 用户：`UserController`
- 文档：`DocumentController`
- 文件夹：`FolderController`
- 通知：`NotificationController`
- 团队：`TeamController`
- 模板：`TemplateController`
- 页面路由：`ViewController`
- WebSocket：`WebSocketConfig`、`CollabWebSocketHandler`
- 版本历史：`DocumentVersionController`、`DocumentVersionService`
- 协作管理：`CollaborationController`、`CollaborationService`
- 分享：`ShareController`、`share-view.html`
- 附件：`AttachmentController`、`AttachmentService`

### 4.2 当前仍未完全落地的 PRD 模块

- 管理员接口与管理员页面
- 导出接口

## 5. 本轮已修复问题

### 5.1 已修复

1. 修复 `PRD.md` 与 `ACCOUNTS.md` 中管理员默认密码不一致的问题
2. 修复 `THYMELEAF_README.md` 关于模板继承的自相矛盾描述
3. 修复“最近访问”接口语义错误：
   - 现在改为真正根据 `recent_visit` 返回最近访问文档
4. 修复“星标文档”接口语义错误：
   - 现在改为真正按 `is_starred = 1` 返回
5. 修复 WebSocket 允许来源与一体化页面来源不匹配的问题：
   - 已补齐 `localhost:8012` 与 `127.0.0.1:8012`
6. 修复文档读写权限缺失：
   - 文档读取、编辑、移动、复制、协作管理均已接入权限校验
7. 修复新建文档默认内容不合理：
   - `NOTE`、`BOARD`、`MINDMAP` 现在分别生成对应初始内容
8. 将 `BOARD` 从演示占位改为最小可用白板：
   - 支持手绘
   - 支持撤销
   - 支持清空
   - 支持自动保存与实时同步
9. 将 `MINDMAP` 从演示占位改为最小可用脑图：
   - 支持节点标题/说明编辑
   - 支持添加子节点 / 同级节点 / 删除节点
   - 支持自动保存与实时同步
10. 补齐前端 WebSocket 协作闭环：
    - 编辑页现在会真实建立 `/ws/collab/{documentId}` 连接
    - 支持在线人数显示
    - 支持跨窗口内容同步
11. 补齐版本历史模块：
    - 已实现版本保存、列表查看、版本恢复
12. 补齐协作管理模块：
    - 已实现用户搜索、协作者邀请、权限调整、移除协作者
13. 补齐分享模块：
    - 已实现分享链接生成、密码校验、只读分享页访问
14. 补齐附件模块：
    - 已实现附件上传、列表、下载、删除
15. 新增只读分享页面：
    - `/share/{shareLink}` 可直接打开分享文档
16. 统一跨来源 CORS 允许范围：
    - 已同步放行 `8012 / 127.0.0.1:8012 / 5173 / 5174`

### 5.2 已修复落点

- `012-backend/src/main/java/com/xiaou/collabboard/service/DocumentService.java`
- `012-backend/src/main/java/com/xiaou/collabboard/controller/DocumentController.java`
- `012-backend/src/main/java/com/xiaou/collabboard/controller/DocumentVersionController.java`
- `012-backend/src/main/java/com/xiaou/collabboard/controller/CollaborationController.java`
- `012-backend/src/main/java/com/xiaou/collabboard/controller/AttachmentController.java`
- `012-backend/src/main/java/com/xiaou/collabboard/controller/ShareController.java`
- `012-backend/src/main/java/com/xiaou/collabboard/controller/UserController.java`
- `012-backend/src/main/java/com/xiaou/collabboard/controller/ViewController.java`
- `012-backend/src/main/java/com/xiaou/collabboard/service/DocumentVersionService.java`
- `012-backend/src/main/java/com/xiaou/collabboard/service/AttachmentService.java`
- `012-backend/src/main/java/com/xiaou/collabboard/service/UserService.java`
- `012-backend/src/main/java/com/xiaou/collabboard/config/WebConfig.java`
- `012-backend/src/main/java/com/xiaou/collabboard/websocket/CollabWebSocketHandler.java`
- `012-backend/src/main/resources/templates/document-edit.html`
- `012-backend/src/main/resources/templates/share-view.html`
- `012-backend/src/main/resources/static/css/document-edit.css`
- `012-backend/src/main/resources/static/css/share-view.css`
- `012-backend/src/main/resources/static/js/document-edit.js`
- `012-backend/src/main/resources/static/js/share-view.js`
- `012-backend/src/main/resources/application.yml`
- `012-backend/PRD/PRD.md`
- `012-backend/THYMELEAF_README.md`

## 6. 构建、启动与抽测结果

### 6.1 构建与测试

- 命令：`mvn -version`
  - 结果：JDK `17`
- 命令：`mvn test -DskipTests=false`
  - 结果：通过

补充说明：

- 当前项目没有 `src/test` 测试用例
- 因此 `mvn test` 目前主要是编译校验

### 6.2 启动验证

- 服务已成功启动在 `8012`
- `/login` 页面访问 `200`
- `/dashboard` 页面访问 `200`

### 6.3 接口抽测

使用账号：`test1 / 123456`

验证通过：

- `POST /api/auth/login`
- `GET /api/auth/info`
- `POST /api/document/create`
- `GET /api/document/{id}`
- `PUT /api/document/{id}`
- `POST /api/document/{id}/star`
- `GET /api/document/list`
- `GET /api/document/recent`
- `GET /api/document/starred`
- `GET /api/user/stats`
- `GET /api/user/search`
- `GET /api/template/list`
- `GET /api/team/list`
- `GET /api/notification/list`
- `POST /api/document/{id}/versions`
- `GET /api/document/{id}/versions`
- `POST /api/document/{id}/versions/{versionId}/restore`
- `POST /api/document/{id}/collaborators`
- `GET /api/document/{id}/collaborators`
- `PUT /api/document/{id}/collaborators/{collaborationId}`
- `DELETE /api/document/{id}/collaborators/{collaborationId}`
- `POST /api/document/{id}/share`
- `GET /api/share/{shareLink}/info`
- `POST /api/share/{shareLink}/access`
- `POST /api/document/{id}/attachments`
- `GET /api/document/{id}/attachments`
- `GET /api/attachment/{attachmentId}/download`

关键复测结果：

- 新建 2 个文档后：
  - `document/list.total = 2`
  - 访问其中 1 个文档后，`document/recent.total = 1`
  - 星标另 1 个文档后，`document/starred.total = 1`
- 白板文档 `ID=6`：
  - 已保存版本 `ID=1`
  - 浏览器手绘后，内容 `strokes.Count = 2`
  - 恢复版本后，内容 `strokes.Count = 1`
- 脑图文档 `ID=7`：
  - 节点标题成功改为 `毕设总览`
  - 子节点数量成功增加到 `2`
- 协作管理复测：
  - 成功搜索到 `test2`
  - 成功邀请 `test2` 进入白板文档
  - 协作者列表数量为 `2`
  - `test2` 可读取文档，但编辑会返回 `无权限编辑此文档`
- 分享模块复测：
  - 已生成分享码 `4a380dd3`
  - 密码 `4321` 校验通过
  - 只读分享页可读取白板内容
- 附件模块复测：
  - 已上传 `attachment-sample.txt`
  - 附件列表数量为 `1`
  - 下载接口返回 `200`

### 6.4 浏览器页面验证

使用 Playwright 验证通过：

- `/login`
- `/dashboard`
- `/documents`
- `/document/6`
- `/document/7`
- `/document/8`
- `/share/4a380dd3`

浏览器结果：

- 登录成功后可跳转到工作台
- 工作台可以显示最近文档
- 文档列表可以显示新建文档
- `NOTE` 类型编辑页可以正常显示 Markdown 编辑器
- `BOARD` 类型编辑页已支持手绘、撤销、清空和自动保存
- `MINDMAP` 类型编辑页已支持节点编辑、增删与结构展示
- 同一文档双标签页打开后，在线人数可显示为 `2`
- 同一 `NOTE` 文档在两个标签页之间可以实时同步内容
- 只读分享页可展示带密码访问的白板文档
- 页面验证过程中无控制台错误、无失败请求、无脚本异常
- 分享页仅存在浏览器关于密码输入框未包裹表单的提示，不影响功能

## 7. 当前重大风险

### 7.1 管理员模块与导出能力仍未落地

- 当前用户侧核心链路已经闭环
- 但 PRD 中仍有管理员接口/页面以及导出能力未看到实际控制器与页面落地
- 如果后续要按“PRD 全量功能”验收，这两块仍需要继续补齐

### 7.2 自动化测试仍然缺失

- 当前项目没有 `src/test`
- `mvn test` 仍然主要承担编译校验，不是业务回归测试
- 本轮结论主要建立在接口抽测与 Playwright 浏览器复测之上

### 7.3 接口异常仍采用 `HTTP 200 + 业务 code=500` 风格

- 例如无权限编辑时，HTTP 层仍可能返回 `200`
- 这不影响当前页面提示和本轮巡检结论
- 但从接口规范角度看，后续仍建议补齐更标准的 HTTP 状态码返回

## 8. 当前阶段结论

`012` 当前结论为：`已完成，已通过测试并可启动`。

综合判断：

- 项目已经满足：
  - JDK 17 构建通过
  - 服务可以启动
  - 登录、工作台、文档、模板等基础链路可用
  - 白板、脑图、笔记三类核心编辑能力可用
  - WebSocket 实时协作闭环可用
  - 版本历史、协作管理、分享、附件模块已补齐并通过复测
  - 本轮发现的明确问题已完成修复并回归验证
- 当前仍有剩余风险：
  - 管理员模块与导出模块未落地
  - 自动化测试缺失
  - 异常返回码语义不够标准

按当前巡检口径，可将 `012` 判定为：

- 文档完整性：`基本通过`
- JDK 17 兼容性：`通过`
- 测试结果：`通过`
- 启动结果：`通过`
