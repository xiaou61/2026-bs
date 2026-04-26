# 033 婚纱写真馆管理系统检查报告

## 1. 检查结论

- 项目编号：`033`
- 项目名称：`婚纱写真馆管理系统`
- 检查日期：`2026-04-22`
- 当前状态：`已完成`
- 综合结论：`已修复默认 MySQL 强依赖、初始化账号口径不清和异常吞错问题，补齐 H2 默认自举、MySQL 兼容配置与后端自动化测试；当前项目可在默认 H2 环境下通过测试并启动，管理员登录、套餐、客户、预约、订单、统计链路与前端开发代理联调均已验证通过。`

## 2. 项目形态

- 后端目录：`033-backend`
- 前端目录：`033-frontend`
- PRD/说明文档：`033-backend/PRD/033-婚纱写真馆管理系统-PRD.md`
- 后端技术栈：`Spring Boot 3.2 + MyBatis + JWT + H2/MySQL`
- 前端技术栈：`React 18 + Ant Design + Vite`

## 3. 本轮修复

### 3.1 默认环境与启动问题

1. 将默认数据源从强依赖本机 `MySQL(root/root)` 改为 `H2` 内存库自举，解决“应用能起但首个查询即 JDBC 失败”的伪启动问题。
2. 新增 `application-mysql.yml`，保留显式切回 MySQL 的兼容入口。
3. 新增 `schema-h2.sql`、`data-h2.sql`，适配 `user/order/package` 等保留字和 MySQL 方言差异，保证默认环境能自动建表和注入演示数据。
4. 关闭默认 `Redis repositories` 自动装配，避免未使用 Redis 时引入无意义噪音。

### 3.2 默认账号与异常可观测性

1. 统一默认初始化账号口径，并同步到 H2 与 MySQL 初始化脚本：
   - `admin / admin123`
   - `manager / manager123`
   - `finance / finance123`
   - `photographer1 / photo123`
   - `photographer2 / photo123`
   - `customer1 / customer123`
   - `customer2 / customer123`
2. 修复全局异常处理只返回 `System error: null` 的问题，改为优先回传最内层根因信息，便于定位数据库和业务异常。

### 3.3 自动化测试补齐

1. 新增后端集成测试，覆盖：
   - 管理员登录
   - 套餐列表查询
   - 客户创建与检索
   - 预约创建与冲突检查链路
   - 订单创建与状态更新
   - 仪表盘统计汇总

## 4. 当前实现与目标能力对照

### 4.1 已具备能力

1. 管理端登录与 JWT 鉴权
2. 套餐、客户、摄影师、影棚、服装、配置等管理类列表/维护能力
3. 预约创建、预约列表与时间冲突校验
4. 订单创建、订单状态更新与统计汇总
5. 前端后台页面构建、启动与 `/api` 代理联调

### 4.2 仍有差距

1. PRD 中强调的客户端预约、在线选片、支付、退款、交付进度等完整用户侧流程尚未完整落地。
2. 照片上传、真实文件存储、短信/微信通知、日志管理、备份恢复等扩展模块仍偏占位或未接入。
3. 当前权限模型以统一登录拦截为主，距离 PRD 中细颗粒度 RBAC 仍有差距。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`033-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0`

### 5.2 后端启动与接口抽测

- 执行命令：`033-backend/mvn spring-boot:run`
- 结果：`通过（默认端口 8080）`
- 抽测链路：
  1. `POST /api/auth/login`
  2. `GET /api/package/list`
  3. `POST /api/customer`
  4. `GET /api/customer/list`
  5. `POST /api/appointment`
  6. `GET /api/appointment/list`
  7. `POST /api/order`
  8. `GET /api/order/list`
  9. `PUT /api/order/{id}/status`
  10. `GET /api/stats/dashboard`
- 关键业务结果：
  - 管理员 `admin / admin123` 登录成功
  - 套餐列表成功返回 `7` 条演示数据
  - 新客户、预约、订单均可创建
  - 订单状态可推进到 `PAID`
  - 仪表盘统计成功汇总订单金额与当日预约数

### 5.3 前端验证

- 执行命令：
  - `033-frontend/npm run build`
  - `033-frontend/npm run dev -- --host 127.0.0.1 --port 5133`
- 结果：`通过`
- 关键结果：
  1. 构建产物生成成功
  2. 前端开发服务成功启动在 `5133`
  3. `GET http://127.0.0.1:5133/login` 返回 `200`
  4. 通过前端 dev server 代理调用 `POST /api/auth/login` 成功返回令牌

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- MySQL 兼容配置：`033-backend/src/main/resources/application-mysql.yml`
- 默认账号：
  - `admin / admin123`
  - `manager / manager123`
  - `finance / finance123`
  - `photographer1 / photo123`
  - `photographer2 / photo123`
  - `customer1 / customer123`
  - `customer2 / customer123`

## 7. 备注

1. `033-backend/sql/init_data.sql` 已同步为与 H2 默认环境一致的账号口径，切回 MySQL 时不再出现“脚本账号可见但默认密码不清”的问题。
2. 前端 `npm run build` 仍有 `chunk size` 告警，但不影响当前构建和启动结论。
3. Windows + Maven 控制台下 MyBatis 打印的中文日志仍可能出现乱码，但接口实际 JSON 数据与页面联调结果正常。
4. `mvn` 输出中的 `rdc` profile 警告来自外部 Maven 环境，不影响本项目测试与启动结论。
