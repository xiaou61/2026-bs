# 090 戏曲文化苑系统检查报告

## 1. 检查结论

- 项目编号：`090`
- 项目名称：`戏曲文化苑系统`
- 检查日期：`2026-05-03`
- 当前状态：`已完成`
- 综合结论：`本轮已完成默认 H2 自举、MySQL `root / 1234` 非破坏验证入口、JDK 17 兼容、JWT Bearer 请求头、运行态 token 校验、登录响应 password 脱敏、真实 HTTP `401/403`、公开剧目/排期放行、会员预约权限、资源归属、艺术家评分权限、赏析互动预约前置校验、前端代理与构建修复，并对默认 H2、mysql-verify 临时库以及前端 `3090` 代理联调完成真实链路复测。`

## 2. 项目形态

- 后端目录：`090-backend`
- 前端目录：`090-frontend`
- 后端技术栈：`Spring Boot 2.7.18 + MyBatis + PageHelper + JWT + H2/MySQL + Redis 依赖保留`
- 前端技术栈：`Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts`
- 默认后端端口：`8090`
- 前端开发端口：`3090`
- MySQL 账号密码：`root / 1234`

## 3. 本轮确认与修复

1. 默认后端环境已改为 H2 内存库自举，并补齐 `schema-h2.sql` 与 `data-h2.sql`。
2. 新增 `application-mysql.yml` 与 `application-mysql-verify.yml`，保留正式 MySQL 部署入口与非破坏性验证入口。
3. `pom.xml` 已升级到 JDK 17，并补齐 H2、JAXB 与新 MySQL 驱动坐标。
4. 新增 `RuntimeStoreService`，登录态不再硬绑定 Redis。
5. `JwtInterceptor` 已兼容 `Authorization: Bearer <token>`，并统一回写 `userId/role`。
6. `WebMvcConfig` 已放行公开剧目分页、启用列表、公告公开列表与排期分页，前台浏览链路可直接访问。
7. `SysUser.password` 已改为 Jackson `WRITE_ONLY`，登录响应和用户信息接口不再泄露密码。
8. `GlobalExceptionHandler` 已返回真实 HTTP 状态码，未登录 `401`、越权 `403`、参数错误 `400`、资源不存在 `404`。
9. 会员预约接口已限制为会员角色，艺术家和管理员不能冒充会员预约。
10. 资源更新与删除已补齐归属校验，艺术家不能修改或删除他人数字资源。
11. 研学评分已校验排期归属，艺术家不能为非本人排期录入成绩。
12. 赏析互动已补齐“先预约后评论”约束，未预约会员不能直接提交互动评论。
13. 前端请求头已统一补齐 Bearer token，Vite 端口和代理目标支持环境变量覆盖。

## 4. 验证结果

### 4.1 后端测试

- 执行命令：`090-backend/mvn test`
- 结果：`通过`
- 备注：新增 `OperaCultureApplicationSmokeTest`，覆盖匿名 `401`、公开剧目/公告、登录脱敏、统计越权、艺术家预约越权、会员预约、资源新增/管理员更新/会员删除越权、评分权限、赏析互动预约前置和登出失效。

### 4.2 前端构建

- 执行命令：`090-frontend/npm.cmd install`
- 结果：`通过`

- 执行命令：`090-frontend/npm.cmd run build`
- 结果：`通过`
- 备注：保留 Vite chunk 体积告警，不影响产物生成。

### 4.3 H2 启动与接口抽测

- 后端启动命令：`090-backend/mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=18097`
- 验证端口：`18097`
- 关键结果：`{"anonInfo":401,"publicNoticeCount":2,"publicRepertoireTotal":3,"loginHasPassword":false,"memberStats":403,"artistBooking":403,"memberBooking":200,"selectionId":3,"adminUpdate":200,"memberDelete":403,"review":200,"memberComment":200,"member2Comment":403,"logout":200,"afterLogout":401}`

### 4.4 MySQL 启动与接口抽测

- MySQL 连接：`localhost:3306`，账号 `root / 1234`
- 准备方式：临时 helper `090-backend/target/MysqlPrepare090.java` 基于 `sql/init.sql` 创建 `opera_culture_090_verify`
- 后端启动命令：`090-backend/mvn spring-boot:run -Dspring-boot.run.profiles=mysql-verify -Dspring-boot.run.arguments=--server.port=18098`
- 验证库：`opera_culture_090_verify`
- 验证端口：`18098`
- 关键结果：`{"anonInfo":401,"publicNoticeCount":2,"publicRepertoireTotal":3,"loginHasPassword":false,"memberStats":403,"artistBooking":403,"memberBooking":200,"selectionId":3,"adminUpdate":200,"memberDelete":403,"review":200,"memberComment":200,"member2Comment":403,"logout":200,"afterLogout":401}`

### 4.5 前端代理联调

- 前端启动命令：`VITE_API_TARGET=http://127.0.0.1:18098 npm run dev -- --host 0.0.0.0 --port 3090`
- 前端端口：`3090`
- 结果：`通过`
- 关键结果：`{"home":200,"loginCode":200,"role":"admin","hasPassword":false}`

## 5. H2 与 MySQL 口径说明

默认使用 H2 是为了保证本机直接启动和自动化巡检不破坏正式库。原始 `090-backend/sql/init.sql` 包含 `DROP DATABASE IF EXISTS opera_culture_db`，因此 MySQL 真实兼容性通过 `opera_culture_090_verify` 临时库验证；正式部署仍使用 `application-mysql.yml`，账号密码为 `root / 1234`。

## 6. 默认账号

- 管理员：`admin / 123456`
- 艺术家：`artist / 123456`
- 会员：`member / 123456`

## 7. 仍有差距

1. 密码仍为明文存储，生产环境建议改为 BCrypt。
2. 当前运行态 token 采用进程内存存储，重启后登录态会全部失效；演示足够，但离分布式会话还有距离。
3. Redis 依赖仍保留在工程中，但当前默认演示链路并未验证真实 Redis 高可用、过期策略或多实例同步。
4. 预约、签到、评分、赏析互动等模块仍沿用“课程/选课”命名痕迹，和戏曲场景术语不完全一致。
5. 真实文件上传、资源存储、活动图片和更细粒度操作日志能力仍未落地。
