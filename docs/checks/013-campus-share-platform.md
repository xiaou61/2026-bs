# 013 校园共享经济平台检查报告

## 1. 基本信息

- 项目编号：`013`
- 项目名称：校园共享经济平台
- 检查日期：`2026-04-05`
- 项目目录：
  - 后端：`013-backend`
  - 前端：`013-frontend`
- 主要资料：
  - 项目总览：`readme_simple.md`
  - 后端 PRD：`013-backend/PRD/PRD.md`
  - 账号文档：`013-backend/ACCOUNTS.md`
  - 后端配置：`013-backend/src/main/resources/application.yml`
  - 数据脚本：`013-backend/src/main/resources/sql/schema.sql`
  - 数据脚本：`013-backend/src/main/resources/sql/init_data.sql`
  - 前端说明：`013-frontend/README.md`

## 2. 项目形态结论

- 当前仓库同时存在 `013-backend` 与 `013-frontend`
- 结合 `pom.xml`、`package.json`、`vite.config.js` 和前端路由，可确认该项目是前后端分离项目
- 当前用户端、管理端、钱包、信用、收藏、收益等页面均已存在，主体骨架比较完整

## 3. 文档与技术栈检查

### 3.1 技术栈

- 后端：
  - Spring Boot `3.2.0`
  - MyBatis-Plus `3.5.5`
  - MySQL `8`
  - Redis
  - JWT `0.12.3`
  - JDK `17`
- 前端：
  - Vue 3
  - Vite
  - Element Plus
  - Pinia
  - Axios

### 3.2 运行前置

- 后端端口：`8013`
- 前端端口：`5013`
- 数据库：`campus_share`
- 数据库账号：`root / 1234`
- Redis：`localhost:6379`
- 前端代理：`/api -> http://localhost:8013`

### 3.3 本轮修正的文档冲突

1. `PRD.md` 原写管理员账号为 `admin / admin123`
2. 实际初始化数据与登录实测为 `admin / 123456`
3. `ACCOUNTS.md` 原写数据库密码为 `root`
4. `application.yml` 实际数据库密码为 `1234`

以上冲突本轮已修正为真实口径。

## 4. 当前源码覆盖情况

### 4.1 已落地模块

- 认证：`AuthController`
- 用户与认证：`UserController`
- 共享物品：`SharedItemController`
- 闲置物品：`IdleItemController`
- 技能服务：`SkillServiceController`
- 订单：`OrderController`
- 支付与钱包：`PaymentController`
- 评价：`ReviewController`
- 通知：`NotificationController`
- 管理端：`AdminController`
- 收藏：`FavoriteController`
- 提现：`WithdrawalController`

### 4.2 本轮已修复的明确问题

1. 修复 `Map.vue` 模板结束标签错误，恢复前端生产构建
2. 补齐 MyBatis-Plus 分页插件，修复所有分页接口 `total/pages` 错误
3. 修复管理员接口无后端角色校验的问题：
   - 普通用户不再能直接访问 `/api/admin/**`
4. 修复管理端共享物品管理页面只有空壳的问题：
   - 已补齐后端列表、添加、状态更新接口
   - 已补齐前端真实 API 接线和分页加载
5. 修复管理端统计卡片固定写死的问题：
   - 已改为真实显示订单数、平台收益、共享物品数
6. 修复管理员用户列表泄露密码哈希的问题：
   - 管理端用户列表已去除 `password`
7. 补齐收藏最小可用闭环：
   - 已新增收藏状态、收藏切换、收藏列表接口
   - 已新增闲置详情、技能详情收藏按钮
   - 已将 `/favorite` 页面改为真实列表
8. 补齐收益提现最小可用闭环：
   - 已新增提现申请、提现记录接口
   - 已将 `/income` 页面改为真实记录列表
   - 提现会同步扣减账户余额并写入余额流水
9. 修复前后端角色口径不一致：
   - 管理端路由已允许 `ADMIN / OPERATOR`
   - 管理端菜单已按角色显示可访问项

### 4.3 当前仍未完全落地的功能

- 闲置物品详情页“立即租借”仍为占位提示，尚未形成真实订单闭环
- 技能服务详情页“立即预约”仍为占位提示，尚未形成真实订单闭环
- 地图页地图区域仍是静态占位，未接入真实地图 SDK
- 管理端图表区仍是占位容器，未接入 ECharts

## 5. 构建、启动与抽测结果

### 5.1 构建与测试

- 命令：`mvn -version`
  - 结果：JDK `17`
- 命令：`mvn test -DskipTests=false`
  - 结果：通过
- 命令：`npm run build`
  - 结果：通过

补充说明：

- 当前后端没有真正的 JUnit 用例，`mvn test` 主要是编译校验
- 前端构建已从最初失败修复为可稳定通过

### 5.2 启动验证

- 后端成功启动在 `8013`
- 前端成功启动在 `5013`
- 页面访问验证：
  - `http://127.0.0.1:5013/` 返回 `200`
  - `http://127.0.0.1:5013/login` 返回 `200`

### 5.3 接口抽测

登录账号实测：

- 管理员：`admin / 123456`
- 运营：`operator / 123456`
- 普通用户：`test1 / 123456`

验证通过：

- `POST /api/auth/login`
- `GET /api/auth/info`
- `GET /api/admin/stats/overview`
- `GET /api/admin/user/list`
- `GET /api/admin/shared/list`
- `POST /api/admin/shared/add`
- `PUT /api/admin/shared/{id}`
- `GET /api/favorite/status`
- `POST /api/favorite/toggle`
- `GET /api/favorite/list`
- `POST /api/withdrawal/apply`
- `GET /api/withdrawal/list`
- `POST /api/payment/balance/charge`
- `POST /api/payment/deposit/charge`
- `GET /api/balance/record`
- `GET /api/deposit/record`
- `POST /api/shared/rent`
- `POST /api/shared/return`
- `GET /api/order/{id}`

关键复测结果：

- 文档口径确认：
  - `admin / 123456` 登录成功
  - `admin / admin123` 登录失败
- 管理员权限确认：
  - `ADMIN` 可访问管理统计
  - `OPERATOR` 可访问管理统计与共享物品管理接口
  - `USER` 访问管理统计返回 `code = 403`
- 共享物品管理确认：
  - 可分页查询共享物品
  - 可新增 `BIKE999`
  - 可把新增物品状态更新为“维护中”
- 分页插件确认：
  - `shared/list?page=1&size=5` 返回 `total = 21`、`pages = 5`
  - `user/list?page=1&size=5` 返回 `total = 10`、`pages = 2`
- 收藏链路确认：
  - `test1` 对闲置物品 `ID=1` 收藏前状态为 `false`
  - 切换后状态为 `true`
  - 收藏列表可返回真实闲置物品数据
- 提现链路确认：
  - `test1` 成功申请 `10` 元提现
  - 生成提现单号
  - 手续费为 `0.2`
  - 实际到账为 `9.8`
  - 账户余额从 `100` 先前变更为 `90`
- 钱包链路确认：
  - 余额充值 `20` 元成功
  - 押金充值 `10` 元成功
  - 余额/押金流水均可返回
- 共享租借链路确认：
  - `test1` 可租借共享单车 `ID=1`
  - 创建共享订单成功
  - 归还成功后订单状态变为 `4`

## 6. 结论

- `013` 已通过 JDK 17 编译、前端构建和前后端启动验证
- 本轮已修复管理端权限缺失、共享物品管理空壳、分页失效、文档口径冲突、收藏空页、收益提现空页等明确问题
- 当前可以确认该项目已达到“可构建、可启动、核心链路可抽测”的状态
- 仍需后续继续完善的主要部分是：
  - 闲置租借真实下单闭环
  - 技能预约真实下单闭环
  - 地图 SDK 与管理端图表
  - 更细致的业务支付建模与自动化测试
