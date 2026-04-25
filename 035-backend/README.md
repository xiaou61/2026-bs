# 035 乡村振兴水稻收割预约系统（后端）

Spring Boot 3 / MyBatis-Plus / H2 / MySQL / JWT / Knife4j 的后端实现，支持农户预约收割、机手设备管理、调度流转与评价。

## 目录结构
- `src/main/java/com/xiaou/rice`：业务代码
  - `modules/auth`：注册、登录、当前用户
  - `modules/farm`：地块管理
  - `modules/booking`：预约、状态日志
  - `modules/machine`：收割机管理
  - `modules/user`：用户实体与服务
  - `security`：JWT、过滤器、UserDetails
  - `config`：安全、跨域、MyBatis-Plus、Swagger
- `src/main/resources`：配置与日志
- `sql/init.sql`：MySQL 初始化脚本（含 admin/123456 种子）
- `src/main/resources/schema-h2.sql` / `data-h2.sql`：默认 H2 自举脚本

## 快速启动
1. 默认开箱即用：仅需 JDK17、Maven3.8+。
2. 直接启动：`mvn spring-boot:run`
3. 默认环境使用 H2 内存库，内置演示账号：
   - 管理员：`admin / 123456`
   - 农户：`farmer_demo / 123456`
   - 机手：`driver_demo / 123456`
4. 如需切回 MySQL，先导入数据库：`mysql -u root -p < sql/init.sql`
5. 再用 MySQL 配置启动：`mvn spring-boot:run -Dspring-boot.run.profiles=mysql`
6. 接口文档：运行后访问 `http://localhost:8080/swagger-ui/index.html`（或 `/doc.html`）。

## 主要接口
- 认证：`POST /api/auth/register`，`POST /api/auth/login`，`GET /api/auth/me`
- 地块：`/api/plots` 列表/新增/修改/删除（仅农户）
- 设备：`/api/machines` 机手设备 CRUD（仅机手）
- 预约：`/api/bookings` 列表/创建/取消（仅农户）；`/{id}/assign` 指派设备（仅管理员）；`/{id}/start`、`/{id}/finish` 由指派机手或管理员执行
- 健康检查：`GET /api/health`（无需登录）

## 鉴权
- 登录成功返回 Bearer Token，前端在请求头携带 `Authorization: Bearer <token>`。
- 安全链无状态，除 `/api/auth/**` 与 `/api/health` 外均需登录。
- `/api/auth/me` 已移除密码字段，返回当前用户的基础信息。

## 前端配套
- 位置：`../035-frontend`
- 技术栈：Vite + Vue3 + Element Plus + Pinia + axios
- 启动：`npm install && npm run dev`，登录页 `/login`，默认代理到 `http://localhost:8080/api`。

## 开发者提示
- 日志：`logs/` 默认滚动；可在 `logback-spring.xml` 调整。
- MyBatis-Plus：分页拦截器、逻辑删除、自动填充时间字段。
- 状态日志：`BookingStatusLog` 记录预约的创建/取消/指派/开工/完工。

## 后续计划
- 机手抢单与调度规则优化
- 结算与评价完善（对接支付占位）
- 通知/消息推送
