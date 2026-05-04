# 092 蓝天幼儿园管理系统检查报告

## 1. 检查结论

- 项目编号：`092`
- 项目名称：`蓝天幼儿园管理系统`
- 检查日期：`2026-05-03`
- 当前状态：`已完成`
- 综合结论：`本轮已完成默认 H2 自举、MySQL \`root / 1234\` 非破坏验证入口、JDK 17 兼容、JWT Bearer 请求头、运行态 token 存储、登录响应 password 脱敏、真实 HTTP \`401/403/500\`、教师/家长归属权限、前端代理与构建修复，并对默认 H2、mysql-verify 临时库以及前端 \`3092\` 代理联调完成真实链路复测。`

## 2. 项目形态

- 后端目录：`092-backend`
- 前端目录：`092-frontend`
- 后端技术栈：`Spring Boot 2.7.18 + MyBatis + PageHelper + JWT + H2/MySQL + Redis 依赖保留`
- 前端技术栈：`Vue 3 + Vite + Element Plus + Axios`
- 默认后端端口：`8092`
- 前端开发端口：`3092`
- MySQL 账号密码：`root / 1234`

## 3. 本轮确认与修复

1. 默认后端环境已改为 H2 内存库自举，并补齐 `schema-h2.sql` 与 `data-h2.sql`。
2. 新增 `application-mysql.yml` 与 `application-mysql-verify.yml`，保留正式 MySQL 部署入口与非破坏性验证入口。
3. `pom.xml` 已升级到 JDK 17，并补齐 H2、JAXB 和新 MySQL 驱动坐标。
4. 新增 `RuntimeStoreService`，登录态不再硬绑定 Redis。
5. `JwtInterceptor` 已兼容 `Authorization: Bearer <token>`，并统一回写 `userId/role`。
6. `SysUser.password` 已改为 Jackson `WRITE_ONLY`，登录响应和用户信息接口不再泄露密码。
7. `GlobalExceptionHandler` 已返回真实 HTTP 状态码，未登录 `401`、越权 `403`、系统异常 `500` 可被真实透出。
8. 家长查看幼儿档案、课表、反馈等接口已按本人孩子收口，不能跨家庭查看。
9. 教师修改考勤、晨检、反馈回复和食谱删除时，已补齐“只能操作自己负责班级/自己记录”的归属校验。
10. 家长反馈新增已限制为 `parent` 角色，且必须绑定本人孩子，不能伪造其他幼儿反馈。
11. 前端请求头已统一补齐 Bearer token，Vite 端口和代理目标支持环境变量覆盖。
12. 新增 `KindergartenApplicationSmokeTest`，覆盖匿名 `401`、公开公告、统计越权、家长课表收口、教师跨班考勤/晨检越权、家长反馈归属、教师回复归属、食谱删除归属和登出失效。

## 4. 验证结果

### 4.1 后端测试

- 执行命令：`092-backend/mvn.cmd test`
- 结果：`通过`
- 备注：新增 `KindergartenApplicationSmokeTest`，覆盖匿名 `401`、公开公告、统计越权、家长课表/幼儿档案收口、教师跨班操作越权、反馈归属和登出失效。

### 4.2 前端构建

- 执行命令：`092-frontend/npm.cmd install`
- 结果：`通过`

- 执行命令：`092-frontend/npm.cmd run build`
- 结果：`通过`
- 备注：保留 Vite chunk 体积告警，不影响产物生成。

### 4.3 H2 启动与接口抽测

- 后端启动命令：`092-backend/mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=18101`
- 验证端口：`18101`
- 关键结果：`{"anonInfo":401,"publicNotice":2,"passwordHidden":true,"parentStats":403,"parentChildren":1,"parentSchedules":1,"crossAttendance":403,"ownAttendance":200,"crossHealth":403,"ownHealth":200,"crossFeedback":403,"ownFeedback":200,"crossReply":403,"ownReply":200,"crossRecipeDelete":403,"logout":200,"afterLogout":401}`

### 4.4 MySQL 启动与接口抽测

- MySQL 连接：`localhost:3306`，账号 `root / 1234`
- 准备方式：临时 helper `092-backend/target/MysqlPrepare092.java` 创建 `kindergarten_092_verify`
- 后端启动命令：`092-backend/mvn spring-boot:run -Dspring-boot.run.profiles=mysql-verify -Dspring-boot.run.arguments=--server.port=18102`
- 验证库：`kindergarten_092_verify`
- 验证端口：`18102`
- 关键结果：`{"anonInfo":401,"publicNotice":2,"passwordHidden":true,"parentStats":403,"parentChildren":1,"parentSchedules":1,"crossAttendance":403,"ownAttendance":200,"crossHealth":403,"ownHealth":200,"crossFeedback":403,"ownFeedback":200,"crossReply":403,"ownReply":200,"crossRecipeDelete":403,"logout":200,"afterLogout":401}`

### 4.5 前端代理联调

- 前端启动命令：`VITE_API_TARGET=http://127.0.0.1:18101 npm run dev -- --host 0.0.0.0 --port 3092`
- 前端端口：`3092`
- 结果：`通过`
- 关键结果：`{"home":200,"loginCode":200,"role":"admin","hasPassword":false}`

## 5. H2 与 MySQL 口径说明

默认使用 H2 是为了保证本机直接启动和自动化巡检不破坏正式库。原始 `092-backend/sql/init.sql` 包含 `DROP DATABASE IF EXISTS blue_sky_kindergarten_db`，因此 MySQL 真实兼容性通过 `kindergarten_092_verify` 临时库验证；正式部署仍使用 `application-mysql.yml`，账号密码为 `root / 1234`。

## 6. 默认账号

- 管理员：`admin / 123456`
- 教师：`teacher / 123456`
- 家长：`parent / 123456`

## 7. 仍有差距

1. 密码仍为明文存储，生产环境建议改为 BCrypt。
2. 当前运行态 token 采用进程内存存储，重启后登录态会全部失效；演示足够，但离分布式会话还有距离。
3. `attendance_record.student_id`、`department_id/major_id` 等字段仍保留课程系统模板命名痕迹，当前已按现有表意兼容处理，但数据模型并不干净。
4. Redis 依赖仍保留在工程中，但当前默认演示链路并未验证真实 Redis 高可用、缓存或多实例同步。
5. 接送记录、更多园务统计、上传存储、消息通知和前端大包体积仍需后续优化。
