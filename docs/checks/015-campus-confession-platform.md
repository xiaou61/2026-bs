# 015 校园表白墙与匿名社交平台检查报告

## 1. 基本信息

- 项目编号：`015`
- 项目名称：校园表白墙与匿名社交平台
- 检查日期：`2026-04-05`
- 项目目录：
  - 后端：`015-backend`
  - 前端：待确认，预计集成在 `015-backend/src/main/resources/static`
- 主要资料：
  - 项目总览：`readme_simple.md`
  - 后端 PRD：`015-backend/PRD/PRD.md`
  - 账号文档：`015-backend/ACCOUNTS.md`
  - 项目说明：`015-backend/README.md`
  - 后端配置：`015-backend/src/main/resources/application.yml`

## 2. 项目形态结论

- 当前仓库仅存在 `015-backend`
- 结合 `README.md`、`application.yml` 和文档描述，初步判断该项目为 Spring Boot + 静态页面一体化项目
- 是否所有静态页均真实存在、是否已接入后端接口，仍需继续核对

## 3. 文档与技术栈检查

### 3.1 技术栈

- 后端：
  - Spring Boot `3.2.0`
  - MyBatis-Plus `3.5.5`
  - JWT `0.12.3`
  - BCrypt
  - JDK `17`
- 前端：
  - HTML + CSS + JavaScript
  - jQuery
  - Bootstrap

### 3.2 运行前置

- 服务端口：`8080`
- 数据库：`campus_confession`
- 数据库账号：`root / 1234`
- 上传目录：`uploads/`

### 3.3 当前已发现的文档冲突

1. PRD 写技术栈包含 Redis
2. `pom.xml` 当前未见 Redis 依赖
3. PRD 写管理员登录接口为 `/api/admin/login`
4. `ACCOUNTS.md` 写管理员登录接口为 `/api/auth/admin/login`
5. `application.yml` 原先写数据库密码 `root`，但当前机器实际可用凭据为 `root / 1234`

结论：`015` 当前文档口径已出现冲突，后续需以源码和实测结果作为最终依据。

## 4. 当前源码覆盖情况

### 4.1 已核对的核心源码

- 配置与鉴权：
  - `config/WebConfig.java`
  - `interceptor/JwtInterceptor.java`
  - `config/SensitiveWordConfig.java`
- 核心控制器：
  - `AuthController.java`
  - `PostController.java`
  - `CommentController.java`
  - `LikeController.java`
  - `MessageController.java`
  - `ReportController.java`
  - `UserController.java`
  - `AdminController.java`
- 核心服务：
  - `AuthService.java`
  - `PostService.java`
  - `CommentService.java`
  - `LikeService.java`
  - `MessageService.java`
  - `ReportService.java`
  - `UserService.java`
  - `AdminService.java`
- 数据脚本：
  - `src/main/resources/sql/schema.sql`
  - `src/main/resources/sql/init_data.sql`
- 静态页面与脚本：
  - `login.html`
  - `home.html`
  - `create-post.html`
  - `post-detail.html`
  - `messages.html`
  - `chat.html`
  - `profile.html`
  - `my-posts.html`
  - `my-comments.html`
  - `my-reports.html`
  - `search.html`
  - `admin-login.html`
  - `admin-dashboard.html`
  - `js/common.js`

### 4.2 已确认的源码问题

1. 管理员接口存在越权风险
   - `JwtInterceptor` 只校验 token 合法性，不校验 `/api/admin/**` 必须为管理员 token。
   - 结果是普通用户理论上可直接访问后台接口。

2. 敏感词高等级审核逻辑失效
   - `PostService.containsHighLevelSensitiveWord()` 当前固定返回 `false`。
   - 结果是触发中度/重度敏感词的帖子不会进入待审核，而是直接发布。

3. MyBatis-Plus 分页插件缺失
   - 项目使用 `Page<>` + `selectPage`，但未配置 `MybatisPlusInterceptor`。
   - 管理端和用户端多处分页接口存在分页失真风险。

4. 静态前端与后端接口接线多处断裂
   - `login.html` 读取 `data.userInfo`，真实返回字段为 `user`
   - `admin-login.html` 读取 `data.adminInfo`，真实返回字段为 `admin`
   - `profile.html` 请求 `GET /api/user/profile`，真实接口为 `GET /api/user/info`
   - `post-detail.html` 请求 `/api/posts/{id}/comments` 和 `POST /api/posts/{id}/comments`，真实接口分别为 `GET /api/comments/post/{postId}` 和 `POST /api/comments`
   - `my-comments.html` 请求 `GET /api/comments/my`，后端当前未实现

5. 文档声明与真实实现不完全一致
   - PRD 写后端依赖 Redis，但当前代码并未接入 Redis
   - PRD 写管理员登录为 `/api/admin/login`，真实实现是 `/api/auth/admin/login`
   - PRD/README 声称图片上传、标签接口、收藏、通知、关注排序等能力已完成，但当前源码未见完整闭环

### 4.3 本轮已完成修复

1. 修复后台越权与公开接口放行错误
   - 收紧了 JWT 拦截器的公开 GET 放行范围
   - 为 `/api/admin/**` 增加管理员角色校验
   - 修复后未登录访问受保护接口返回 `401`，普通用户访问后台接口返回 `403`

2. 修复分页与敏感词审核能力
   - 新增 `MybatisPlusConfig`，补齐分页插件
   - 修复 `containsHighLevelSensitiveWord()`，让中高等级敏感词帖子进入待审核

3. 修复静态页和接口接线
   - 补齐 `GET /api/comments/my`
   - 新增 `/api/posts/{postId}/comments` 兼容读写接口
   - 为 `GET /api/user/profile` 和用户/管理员登录返回字段补齐兼容口径
   - 修复登录态、评论回复、点赞提示、管理员审核菜单等前端断点

4. 修复 JDK 17 编译与初始化数据问题
   - 修正 `SensitiveWordFilter` 泛型声明
   - 修正初始化 SQL 中管理员 / 用户密码哈希
   - 修正数据库配置为当前环境真实可用的 `root / 1234`

## 5. 构建、启动与抽测结果

### 5.1 构建与启动

- `mvn -version`
  - 已确认 JDK：`17`
- `mvn test -DskipTests=false`
  - 已通过，共 `7` 个测试：
    - `JwtInterceptorTest`
    - `PasswordUtilTest`
    - `PostServiceTest`
- 应用启动
  - 已在 `8080` 成功启动
  - 静态页面 `/`、`/login.html`、`/home.html`、`/create-post.html`、`/admin-login.html`、`/admin-dashboard.html` 返回 `200`

### 5.2 主链路抽测结果

1. 登录与鉴权
   - 用户 `20210001 / 123456` 登录成功
   - 用户 `20210002 / 123456` 登录成功
   - 管理员 `admin / admin123` 登录成功
   - 未登录访问 `/api/posts/my` 返回 `401`
   - 普通用户访问 `/api/admin/users` 返回 `403`

2. 用户侧主链路
   - 公开帖子列表查询成功
   - 用户信息别名接口 `/api/user/profile` 查询成功
   - 创建正常帖子成功，返回 `status=1`
   - 评论、回复、我的评论查询成功
   - 点赞第一次返回 `liked=true`，第二次返回 `liked=false`
   - 举报提交成功
   - 私信发送成功，会话列表可见

3. 管理侧主链路
   - 用户列表查询成功，返回结果不含 `password`
   - 举报列表查询成功，举报处理成功
   - 发包含“政治”的帖子后，帖子返回 `status=0`
   - 管理端待审核帖子列表可见该帖子
   - 新用户注册后提交实名认证，管理端待审核认证列表可见并审核通过

## 6. 当前阶段结论

- `015` 已完成修复、测试、启动和关键链路复测
- 当前可以在 JDK `17` + MySQL `root / 1234` 环境下通过测试并正常启动
- 公开列表、登录、发帖、评论/回复、点赞、举报、私信、敏感词待审、实名认证审核和后台权限链路已形成可复测闭环

## 7. 剩余风险

1. PRD / README 中提到的图片上传、标签接口、收藏、通知、关注排序、私信入口等能力仍未完整实现
2. 控制台日志中的敏感词中文输出存在编码乱码，但不影响数据库存储和业务逻辑
3. 当前管理员前端仍以单页后台为主，统计图表、帖子列表管理、评论管理等高级能力未完全展开
