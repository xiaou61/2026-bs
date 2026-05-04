# 088 孩童收养信息管理系统检查报告

## 1. 检查结论

- 项目编号：`088`
- 项目名称：`孩童收养信息管理系统`
- 检查日期：`2026-05-02`
- 当前状态：`已完成`
- 综合结论：`本轮已完成默认 H2 自举、MySQL `root / 1234` 非破坏验证入口、JDK 17 兼容、JWT Bearer 请求头、运行态 token 校验、登录响应 password 脱敏、真实 HTTP `401/403`、申请人与审核员权限边界、申请材料归属校验、前端代理与构建修复，并对默认 H2、mysql-verify 临时库以及前端 `3088` 代理联调完成真实链路复测。`

## 2. 项目形态

- 后端目录：`088-backend`
- 前端目录：`088-frontend`
- 后端技术栈：`Spring Boot 2.7.18 + MyBatis-Plus + JWT + H2/MySQL + Redis 依赖保留`
- 前端技术栈：`Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts`
- 默认后端端口：`8088`
- 前端开发端口：`3088`
- MySQL 账号密码：`root / 1234`

## 3. 本轮确认与修复

1. 默认后端环境已改为 H2 内存库自举，并补齐 `schema-h2.sql` 与 `data-h2.sql`。
2. 新增 `application-mysql.yml` 与 `application-mysql-verify.yml`，保留正式 MySQL 部署入口与非破坏性验证入口。
3. `pom.xml` 已升级到 JDK 17，并补齐 H2、JAXB 与新 MySQL 驱动坐标。
4. 修复 `AgreementService` 缺失 `Wrappers` 导入导致的后端编译失败。
5. 新增 `RuntimeStoreService`，登录、登出与鉴权不再硬绑定 Redis。
6. `JwtInterceptor` 已兼容 `Authorization: Bearer <token>`，并统一回写 `userId/role`。
7. `SysUser.password` 已改为 Jackson `WRITE_ONLY`，登录响应和用户信息接口不再泄露密码。
8. `GlobalExceptionHandler` 已返回真实 HTTP 状态码，未登录 `401`、越权 `403`、参数错误 `400`、资源不存在 `404`。
9. 申请列表、申请提交和材料上传已补齐申请人角色限制，审核员不能伪装申请人发起收养申请。
10. 申请材料接口已补齐归属校验，申请人不能向他人的收养申请追加材料。
11. 登出后旧 token 会立即失效，H2 和 MySQL 双环境都已复测通过。
12. 前端请求头已统一补齐 Bearer token，Vite 端口和代理目标支持环境变量覆盖。

## 4. 验证结果

### 4.1 后端测试

- 执行命令：`088-backend/mvn test`
- 结果：`通过`
- 备注：新增 `ChildAdoptionApplicationSmokeTest`，覆盖匿名 `401`、公开儿童列表、登录脱敏、统计、审核员越权申请、申请人资料维护、申请创建、材料归属校验、协议列表越权和登出失效。

### 4.2 前端构建

- 执行命令：`088-frontend/npm.cmd install`
- 结果：`通过`
- 备注：已生成 `package-lock.json`。

- 执行命令：`088-frontend/npm.cmd run build`
- 结果：`通过`
- 备注：保留 Vite chunk 体积告警，不影响产物生成。

### 4.3 H2 启动与接口抽测

- 后端启动命令：`088-backend/mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=18093`
- 验证端口：`18093`
- 关键结果：`{"anonInfo":401,"publicChild":200,"publicChildCount":2,"adminLogin":200,"reviewerLogin":200,"applicantLogin":200,"loginHasPassword":false,"adminStats":200,"reviewerAddApplication":403,"crossApplicantMaterialAdd":403}`

### 4.4 MySQL 启动与接口抽测

- MySQL 连接：`localhost:3306`，账号 `root / 1234`
- 准备方式：临时 helper `088-backend/target/MysqlPrepare088.java` 基于 `sql/init.sql` 创建 `adoption_088_verify`
- 后端启动命令：`088-backend/mvn spring-boot:run -Dspring-boot.run.profiles=mysql-verify -Dspring-boot.run.arguments=--server.port=18094`
- 验证库：`adoption_088_verify`
- 验证端口：`18094`
- 关键结果：`{"anonInfo":401,"publicChild":200,"publicChildCount":2,"adminLogin":200,"reviewerLogin":200,"applicantLogin":200,"newApplicantRegister":200,"loginHasPassword":false,"adminStats":200,"profileUpdate":200,"reviewerAddApplication":403,"newApplication":200,"applicationId":3,"review":200,"crossApplicantMaterialAdd":403,"ownMaterialAdd":200,"logout":200,"afterLogout":401}`

### 4.5 前端代理联调

- 前端启动命令：`VITE_API_TARGET=http://127.0.0.1:18094 npm run dev -- --host 0.0.0.0 --port 3088`
- 前端端口：`3088`
- 结果：`通过`
- 关键结果：`{"home":200,"login":200,"code":200,"role":"admin","hasPassword":false}`

## 5. H2 与 MySQL 口径说明

默认使用 H2 是为了保证本机直接启动和自动化巡检不破坏正式库。原始 `088-backend/sql/init.sql` 包含 `DROP DATABASE IF EXISTS 088_child_adoption`，因此 MySQL 真实兼容性通过 `adoption_088_verify` 临时库验证；正式部署仍使用 `application-mysql.yml`，账号密码为 `root / 1234`。

## 6. 默认账号

- 管理员：`admin / 123456`
- 审核员：`reviewer / 123456`
- 申请人：`applicant / 123456`
- 申请人：`applicant02 / 123456`

## 7. 仍有差距

1. 密码仍为明文存储，生产环境建议改为 BCrypt。
2. 当前运行态 token 采用进程内存存储，重启后登录态会全部失效；演示足够，但离分布式会话还有距离。
3. Redis 依赖仍保留在工程中，但当前默认演示链路并未验证真实 Redis 高可用、过期策略或多实例同步。
4. 家访、匹配、协议和回访流程仍缺少更细粒度的状态流转约束。
5. 材料上传、协议附件和回访图片仍未接入真实文件存储与内容安全校验。
