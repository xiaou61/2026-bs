# 017 高校自助点餐系统检查报告

## 1. 基本信息

- 项目编号：`017`
- 项目名称：高校自助点餐系统
- 检查日期：`2026-04-06`
- 项目目录：
  - 后端：`017-backend`
- 主要资料：
  - 项目总览：`readme_simple.md`
  - 后端 PRD：`017-backend/PRD/PRD.md`
  - 账号文档：`017-backend/ACCOUNTS.md`
  - 后端说明：`017-backend/README.md`
  - 后端配置：`017-backend/src/main/resources/application.yml`
  - 初始化脚本：
    - `017-backend/src/main/resources/sql/schema.sql`
    - `017-backend/src/main/resources/sql/data.sql`

## 2. 项目形态结论

- 当前仓库仅存在 `017-backend`
- 已确认该项目为 Spring Boot + Thymeleaf + jQuery + Bootstrap 5 的前后端一体化项目
- 本轮已完成源码核对、问题修复、测试、启动与关键链路复测

## 3. 文档与技术栈检查

### 3.1 技术栈

- 后端：
  - Spring Boot `3.2.0`
  - MyBatis-Plus `3.5.5`
  - JWT `0.11.5`
  - Hutool Crypto
  - JDK `17`
- 前端：
  - Thymeleaf
  - jQuery `3.7.0`
  - Bootstrap `5.3.0`

### 3.2 运行前置

- 服务端口：`8080`
- 数据库：`campus_ordering`
- 数据库账号：`root / 1234`

### 3.3 已确认的文档冲突

1. `application.yml`、`README.md`、`ACCOUNTS.md` 原先数据库密码写为 `root`，与当前机器实际可用的 `root / 1234` 不一致。
2. `README.md` 原先把购物车、订单、商家端和管理端能力描述得比真实源码更完整。
3. `README.md` 原先写 `GET /api/cart/list/{userId}` 与 `DELETE /api/cart/clear/{userId}`，与 PRD 的当前用户态接口口径不一致。
4. `ACCOUNTS.md` 原先用同一段 BCrypt 哈希同时描述 `123456` 与 `admin123`，实际不可能成立。

结论：`017` 初始文档存在明显口径错误，本轮已按实测环境与修复后的源码完成统一。

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
  - `CartController.java`
  - `OrderController.java`
  - `MerchantController.java`
  - `MerchantManageController.java`
  - `AdminController.java`
  - `IndexController.java`
- 核心服务：
  - `AuthService.java`
  - `CartService.java`
  - `OrderService.java`
  - `MerchantService.java`
  - `MerchantManageService.java`
  - `AdminService.java`
- 模板页面：
  - `templates/index.html`
  - `templates/user/login.html`
  - `templates/user/register.html`
  - `templates/user/cart.html`
  - `templates/user/orders.html`
  - `templates/merchant/list.html`
  - `templates/merchant/detail.html`
  - `templates/merchant/login.html`
  - `templates/merchant/manage.html`
  - `templates/admin/login.html`
  - `templates/admin/dashboard.html`

### 4.2 已确认的源码问题

1. 商家端与管理端存在登录后断链
   - 商家登录成功后跳转 `/merchant/manage`，原项目没有页面。
   - 管理员登录成功后跳转 `/admin/dashboard`，原项目没有页面。

2. 购物车链路是假功能
   - 商家详情页原先只在浏览器内存数组里加购，点击购物车只会弹出“开发中”。
   - `CartController` 原先直接信任前端传入 `userId`，没有任何归属校验。

3. JWT 只生成不消费
   - 原项目虽然有 `JwtUtil` 与 `UserContext`，但没有拦截器接入 token 解析、角色识别和上下文注入。

4. 订单主链路完全缺失
   - 虽然已有 `orders`、`order_detail` 实体和数据库表设计，但原项目没有下单、订单列表、订单详情、取消订单、确认取餐、商家接单、出餐完成等核心能力。

5. 测试是“假通过”
   - 原先仅有 `PasswordTest`，且只打印哈希结果，不做任何断言。
   - 打印结果已经明确显示默认种子密码哈希不匹配文档账号。

6. 异常响应语义不清
   - 原先业务异常统一返回 `HTTP 200 + code=500`，前后端对未登录 / 越权的处理语义不清晰。

### 4.3 本轮已完成修复

1. 修复鉴权底座与角色边界
   - 新增 `AuthInterceptor`，把 JWT 真正接入 `/api/**`
   - 区分学生、商家、管理员角色
   - 普通用户访问 `/api/admin/**` 现在返回 `403`
   - 为 `User` / `Merchant` / `Admin` 密码字段增加 JSON 忽略

2. 修复购物车真接线
   - 商家详情页改为调用真实 `/api/cart/add`
   - 新增 `user/cart.html`
   - 购物车接口改为使用当前登录用户，不再信任前端 `userId`
   - 支持查看购物车、改数量、删项、清空购物车

3. 补齐订单核心闭环
   - 新增 `OrderDetailMapper`
   - 新增 `OrderService` 与 `OrderController`
   - 实现下单、订单列表、订单详情、取消订单、确认取餐
   - 下单时同步扣减用户余额、扣减库存、写入订单明细、生成订单号与取餐号
   - 取消订单时回滚余额与库存
   - 新增 `user/orders.html`

4. 补齐商家端与管理端落点
   - 新增 `merchant/manage.html`
   - 新增商家工作台统计与订单处理接口
   - 实现商家接单、标记待取餐
   - 新增 `admin/dashboard.html`
   - 新增后台统计接口

5. 修复种子数据与配置口径
   - `application.yml` 数据库密码改为 `1234`
   - 修正 `data.sql` 中学生 / 商家默认密码哈希为 `123456`
   - 修正管理员默认密码哈希为 `admin123`
   - 修正 `README.md` 与 `ACCOUNTS.md` 的数据库口径和接口说明

6. 修复测试与异常语义
   - `PasswordTest` 改为真实断言测试
   - `GlobalExceptionHandler` 改为根据错误类型返回 `401 / 403 / 400 / 500`

## 5. 构建、启动与抽测结果

### 5.1 构建与启动

- `mvn -version`
  - 已确认 JDK：`17`
- `mvn test -DskipTests=false`
  - 已通过，共 `1` 个真实断言测试：
    - `PasswordTest`
- 应用启动
  - 后端已在 `8080` 成功启动
  - 页面 `/`、`/merchant/list`、`/merchant/detail/1`、`/user/cart`、`/user/orders`、`/merchant/manage`、`/admin/dashboard` 返回 `200`

### 5.2 主链路抽测结果

1. 认证与页面
   - 学生 `20210001 / 123456` 登录成功
   - 商家 `13900000001 / 123456` 登录成功
   - 管理员 `admin / admin123` 登录成功
   - 学生 / 商家 / 管理端页面均可访问

2. 学生点餐链路
   - 商家详情页添加菜品到购物车成功
   - 我的购物车查询成功
   - 下单成功，订单 `ID=1`，取餐号 `001`
   - 我的订单查询成功
   - 订单详情查询成功
   - 用户确认取餐成功，最终状态为 `4`

3. 商家处理链路
   - 商家工作台统计接口成功
   - 商家订单列表查询成功
   - 商家接单成功
   - 商家标记待取餐成功

4. 后台统计链路
   - 管理后台统计接口成功
   - 当前统计订单数为 `1`
   - 学生访问后台统计接口被正确拦截，HTTP 状态码为 `403`

## 6. 当前阶段结论

- `017` 已完成修复、测试、启动和关键链路复测
- 当前可以在 JDK `17` + MySQL `root / 1234` 环境下通过测试并正常启动
- 学生登录、商家登录、管理员登录、购物车、下单、商家接单 / 标记待取餐、用户确认取餐和后台统计已形成可复测闭环

## 7. 剩余风险

1. 用户资料、余额充值、评价、收藏、优惠券、搜索等 PRD 能力仍未落地
2. 商家端目前只补了工作台与订单处理，菜品管理、营业时间设置、完整数据统计仍未实现
3. 管理后台目前只补了统计概览，用户管理、商家审核、分类管理、订单异常处理仍未实现
4. 当前自动化测试覆盖仍偏少，主要依赖 `PasswordTest` + 人工接口抽测
5. 导库目前仍依赖外部 SQL 执行，不是应用启动即自动初始化
