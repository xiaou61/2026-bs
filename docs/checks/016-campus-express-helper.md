# 016 校园快递代领服务平台检查报告

## 1. 基本信息

- 项目编号：`016`
- 项目名称：校园快递代领服务平台
- 检查日期：`2026-04-05`
- 项目目录：
  - 后端：`016-backend`
  - 前端：`016-frontend`
- 主要资料：
  - 项目总览：`readme_simple.md`
  - 后端 PRD：`016-backend/PRD/PRD.md`
  - 账号文档：`016-backend/ACCOUNTS.md`
  - 后端配置：`016-backend/src/main/resources/application.yml`
  - 初始化脚本：`016-backend/src/main/resources/init.sql`
  - 前端说明：`016-frontend/README.md`

## 2. 项目形态结论

- 当前仓库同时存在 `016-backend` 与 `016-frontend`
- 已确认该项目为 Spring Boot + Vue 3 的前后端分离项目
- 本轮已完成源码核对、问题修复、测试、启动与关键业务链路复测

## 3. 文档与技术栈检查

### 3.1 技术栈

- 后端：
  - Spring Boot `3.2.0`
  - MyBatis-Plus `3.5.5`
  - JWT `0.11.5`
  - Hutool Crypto
  - JDK `17`
- 前端：
  - Vue `3`
  - Vue Router `4`
  - Pinia
  - Element Plus
  - Axios
  - Vite `4`

### 3.2 运行前置

- 服务端口：`8080`
- 数据库：`campus_express`
- 数据库账号：`root / 1234`
- 前端开发端口：`5173`

### 3.3 已确认的文档冲突

1. PRD 与前端 `README.md` 写管理员默认密码为 `admin123`
2. `016-backend/ACCOUNTS.md` 原先写管理员默认密码为 `123456`
3. `016-backend/ACCOUNTS.md` 原先示例数据库密码为 `root`
4. 初始化 SQL 原先密码哈希与文档口径不匹配，导致默认账号无法按文档登录

结论：`016` 初始文档存在冲突，但本轮已以源码、初始化脚本和实测结果为准完成统一。

## 4. 当前源码覆盖情况

### 4.1 已核对的核心源码

- 配置与鉴权：
  - `config/WebConfig.java`
  - `config/MybatisPlusConfig.java`
  - `interceptor/AuthInterceptor.java`
  - `util/JwtUtil.java`
  - `util/UserContext.java`
- 核心控制器：
  - `AuthController.java`
  - `UserController.java`
  - `OrderController.java`
  - `WalletController.java`
  - `ReviewController.java`
  - `NotificationController.java`
  - `ComplaintController.java`
  - `AdminController.java`
- 核心服务：
  - `AuthService.java`
  - `UserService.java`
  - `OrderService.java`
  - `WalletService.java`
  - `ReviewService.java`
  - `NotificationService.java`
  - `ComplaintService.java`
  - `AdminService.java`
- 前端关键文件：
  - `src/router/index.js`
  - `src/utils/request.js`
  - `src/stores/user.js`
  - `src/api/*.js`
  - `src/views/*`
  - `src/layout/UserLayout.vue`
  - `src/layout/AdminLayout.vue`

### 4.2 已确认的源码问题

1. 后台权限存在越权风险
   - `AuthInterceptor` 原先只校验 token，不校验 `/api/admin/**` 必须为管理员 token。

2. MyBatis-Plus 分页插件缺失
   - 项目多处使用 `Page<>` + `selectPage`，但原先没有 `PaginationInnerInterceptor`。

3. 后台接口与前端接线不一致
   - 前端已调用的 `PUT /api/admin/orders/{id}/handle` 与 `PUT /api/admin/complaints/{id}/handle` 原先不存在。
   - `GET /api/complaints/my` 原先不存在。
   - 管理端用户、订单、投诉、交易列表原先没有实现筛选参数，也没有补齐 `publisherName`、`complainantName`、`userName` 等页面所需字段。
   - 管理端 `updateUserStatus()` 原先发送纯文本，后端要求 JSON。

4. 用户资料页只有前端壳子，没有真实后端能力
   - 原项目没有 `UserController`，`Profile.vue` 只能弹成功提示，无法真正更新资料和密码。

5. 钱包消费流水缺失
   - 原项目发单时只做余额冻结，没有生成消费交易记录和通知，钱包流水不完整。

6. 默认密码与账号文档不一致
   - 初始化 SQL 中默认哈希与文档密码不匹配，`PasswordTest` 已明确验证失败。

7. 细节安全问题
   - `NotificationController.markAsRead()` 原先不校验通知归属。
   - 普通用户可查看非本人已接单 / 已完成订单详情中的敏感信息。

### 4.3 本轮已完成修复

1. 修复权限与安全边界
   - 为 `/api/admin/**` 增加管理员角色校验，普通用户访问后台接口返回 `403`
   - 收紧订单详情访问范围，避免无关用户查看非公开订单详情
   - 通知已读接口增加归属校验
   - 为 `User` / `Admin` 密码字段增加 JSON 忽略，避免密码泄露

2. 补齐分页、资料与后台处理能力
   - 新增 `MybatisPlusConfig`，补齐分页插件
   - 新增 `UserController`，实现资料查询、资料更新、密码修改
   - 补齐 `/api/complaints/my`
   - 新增管理员强制取消订单、处理投诉接口
   - 完善后台统计 VO 与后台用户 / 订单 / 投诉 / 交易筛选能力

3. 修复前后端接线
   - 修复管理端用户状态更新和订单处理请求体格式
   - 为路由守卫和本地登录态增加 `userType` 区分
   - 用户登录后补拉完整个人资料，解决发单页默认手机号 / 宿舍回填不完整
   - 个人中心改为真实调用后端资料更新与密码修改接口

4. 修复钱包与种子数据
   - 发单冻结余额时补写消费交易流水与通知
   - 修正初始化 SQL 中管理员 / 用户默认密码哈希
   - 统一 `ACCOUNTS.md` 的管理员密码和数据库密码说明

## 5. 构建、启动与抽测结果

### 5.1 构建与启动

- `mvn -version`
  - 已确认 JDK：`17`
- `mvn test -DskipTests=false`
  - 已通过，共 `4` 个测试：
    - `AuthInterceptorTest`
    - `PasswordTest`
- `npm run build`
  - 已通过，存在前端主包体积告警
- 应用启动
  - 后端已在 `8080` 成功启动
  - 前端 Vite 已在 `5173` 成功启动
  - 页面 `/`、`/login`、`/admin/login` 返回 `200`

### 5.2 主链路抽测结果

1. 认证与权限
   - 用户 `20210001 / 123456` 登录成功
   - 用户 `20210002 / 123456` 登录成功
   - 管理员 `admin / admin123` 登录成功
   - 普通用户访问 `/api/admin/users` 返回 `403`

2. 用户资料链路
   - 新注册测试用户成功
   - 个人资料修改成功
   - 密码修改成功
   - 使用新密码重新登录成功

3. 订单主链路
   - 发单成功
   - 订单广场按 `itemType` + `itemWeight` 筛选成功
   - 接单成功
   - 上传取件凭证成功
   - 上传送达凭证成功
   - 发单人确认收货成功
   - 发单人评价成功

4. 钱包与通知链路
   - 发单后用户1余额变为 `93.5`
   - 接单完成后用户2余额变为 `106.5`
   - 用户1交易流水包含消费类型 `2`
   - 用户2交易流水包含收入类型 `3`
   - 通知未读数读取成功，标记已读后数量正确下降

5. 投诉与后台链路
   - 用户投诉提交成功
   - 我的投诉列表查询成功
   - 管理员处理投诉成功
   - 管理后台用户筛选、订单筛选、交易筛选全部成功
   - 后台订单返回 `publisherName`
   - 后台投诉返回 `complainantName`
   - 后台交易返回 `userName`
   - 管理员强制取消订单成功，订单状态变为 `5`

## 6. 当前阶段结论

- `016` 已完成修复、测试、启动和关键链路复测
- 当前可以在 JDK `17` + MySQL `root / 1234` 环境下通过测试并正常启动
- 登录、注册、资料修改、密码修改、订单广场筛选、发单、接单、上传凭证、确认收货、评价、投诉、通知、后台统计、后台筛选与管理员强制取消订单已形成可复测闭环

## 7. 剩余风险

1. 图片上传目前仍是 URL 文本录入，不是真实文件上传 / OSS 流程
2. 投诉成立后的“已完成订单”当前只做状态处理与信用分扣减，未实现资金逆向冲正
3. 订单超时自动取消、超时扣分、自动确认收货等定时任务仍未落地
4. 当前自动化测试仍偏少，主要依赖单元测试 + 人工接口抽测
5. 前端主包体积较大，构建存在 chunk 体积告警
