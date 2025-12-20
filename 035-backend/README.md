# 035 乡村振兴水稻收割预约系统（后端）

Spring Boot 3 / MyBatis-Plus / MySQL / Redis / JWT / Knife4j 的后端实现，支持农户预约收割、机手设备管理、调度流转与评价。

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
- `sql/init.sql`：数据库初始化脚本（含 admin/123456 种子）

## 快速启动
1. 准备环境：JDK17、MySQL8、Redis6、Maven3.8+
2. 导入数据库：`mysql -u root -p < sql/init.sql`
3. 配置 `src/main/resources/application.yml`：
   - `spring.datasource.*` 数据库连接
   - `spring.data.redis.*` Redis
   - `rice.jwt.secret` 设置为长度≥32 的随机串
4. 启动：`mvn spring-boot:run`
5. 接口文档：运行后访问 `http://localhost:8080/swagger-ui/index.html`（或 `/doc.html`）。

## 主要接口
- 认证：`POST /api/auth/register`，`POST /api/auth/login`，`GET /api/auth/me`
- 地块：`/api/plots` 列表/新增/修改/删除（需登录农户）
- 设备：`/api/machines` 机手设备 CRUD（需登录机手）
- 预约：`/api/bookings` 列表/创建；`/{id}/assign` 指派设备；`/{id}/start` 开工；`/{id}/finish` 完工；`/{id}/cancel` 取消
- 健康检查：`GET /api/health`（无需登录）

## 鉴权
- 登录成功返回 Bearer Token，前端在请求头携带 `Authorization: Bearer <token>`。
- 安全链无状态，除 `/api/auth/**` 与 `/api/health` 外均需登录。

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
