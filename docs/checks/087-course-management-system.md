# 087 课程管理系统检查报告

## 1. 检查结论

- 项目编号：`087`
- 项目名称：`课程管理系统`
- 检查日期：`2026-05-02`
- 当前状态：`已完成`
- 综合结论：`本轮已完成默认 H2 自举、MySQL `root / 1234` 非破坏验证入口、JDK 17 兼容、JWT Bearer 请求头、运行态 token 校验、登录响应 password 脱敏、真实 HTTP `401/403`、课程资源/选课/成绩/评教归属校验、前端代理与构建修复，并对默认 H2、mysql-verify 临时库以及前端 `3087` 代理联调完成真实链路复测。`

## 2. 项目形态

- 后端目录：`087-backend`
- 前端目录：`087-frontend`
- 后端技术栈：`Spring Boot 2.7.18 + MyBatis + PageHelper + JWT + H2/MySQL + Redis 依赖保留`
- 前端技术栈：`Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts`
- 默认后端端口：`8087`
- 前端开发端口：`3087`
- MySQL 账号密码：`root / 1234`

## 3. 本轮确认与修复

1. 默认后端环境已改为 H2 内存库自举，并补齐 `schema-h2.sql` 与 `data-h2.sql`。
2. 新增 `application-mysql.yml` 与 `application-mysql-verify.yml`，保留正式 MySQL 部署入口与非破坏性验证入口。
3. `pom.xml` 已补齐 JDK 17、H2、JAXB 与 MySQL 驱动兼容配置。
4. 新增 `RuntimeStoreService`，登录、登出与鉴权不再硬绑定 Redis。
5. `JwtInterceptor` 已兼容 `Authorization: Bearer <token>`，并统一回写 `userId/role`。
6. `AuthService` 已改为基于运行态 token 校验当前登录态，登出后旧 token 立即失效。
7. `SysUser.password` 已改为 Jackson `WRITE_ONLY`，登录响应和用户信息接口不再泄露密码。
8. `GlobalExceptionHandler` 已返回真实 HTTP 状态码，未登录 `401`、越权 `403`、参数错误 `400`。
9. 教师发布课程资源时即使请求体伪造 `teacherId`，实际仍强制绑定当前教师。
10. 学生更新教师资源、学生写成绩等越权写操作已确认返回真实 `403`。
11. 选课、成绩和评教链路已确认按当前登录学生或教师归属处理。
12. 前端请求头已统一补齐 Bearer token，Vite 端口和代理目标支持环境变量覆盖。

## 4. 验证结果

### 4.1 后端测试

- 执行命令：`087-backend/mvn test`
- 结果：`通过`
- 备注：新增 `CourseManagementApplicationSmokeTest`，覆盖匿名 `401`、登录脱敏、统计权限、课程资源归属、选课、成绩、评教和登出失效。

### 4.2 前端构建

- 执行命令：`087-frontend/npm.cmd install`
- 结果：`通过`
- 备注：已生成 `package-lock.json`。

- 执行命令：`087-frontend/npm.cmd run build`
- 结果：`通过`
- 备注：保留 Vite chunk 体积告警，不影响产物生成。

### 4.3 H2 启动与接口抽测

- 后端启动命令：`087-backend/mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=18090`
- 验证端口：`18090`
- 已验证链路：
  - 匿名访问 `/api/auth/info` 返回 `401`
  - 公开公告列表返回 `200`
  - 教师访问统计接口返回 `200`，学生访问返回 `403`
  - 教师新增课程资源时即使伪造 `teacherId`，实际仍绑定 `teacherId=2`
  - 学生更新教师资源返回 `403`
  - `student2` 选课、教师录入成绩、`student2` 提交评教通过
  - 登出后旧 token 失效
- 关键结果：`{"anonInfo":401,"publicNotice":2,"teacherStats":200,"resourceTeacherId":2,"selectionId":3,"logoutInvalidated":true}`

### 4.4 MySQL 启动与接口抽测

- MySQL 连接：`localhost:3306`，账号 `root / 1234`
- 准备方式：临时 helper `087-backend/target/MysqlPrepare087.java` 基于 `sql/init.sql` 创建 `course_manage_087_verify`
- 后端启动命令：`087-backend/mvn spring-boot:run -Dspring-boot.run.profiles=mysql-verify -Dspring-boot.run.arguments=--server.port=18091`
- 验证库：`course_manage_087_verify`
- 验证端口：`18091`
- 关键结果：`{"anonInfo":401,"publicNotice":200,"publicNoticeCount":2,"adminLogin":200,"teacherLogin":200,"studentLogin":200,"student2Login":200,"loginHasPassword":false,"teacherStats":200,"studentStats":403,"resourceAdd":200,"resourceTeacherId":2,"studentUpdateResource":403,"selectionAdd":200,"selectionId":3,"teacherScore":200,"studentScore":403,"evaluation":200,"logout":200,"afterLogout":401}`

### 4.5 前端代理联调

- 前端启动命令：`VITE_API_TARGET=http://127.0.0.1:18091 npm run dev -- --host 0.0.0.0 --port 3087`
- 前端端口：`3087`
- 结果：`通过`
- 关键结果：`{"home":200,"login":200,"code":200,"role":"admin","hasPassword":false}`

## 5. H2 与 MySQL 口径说明

默认使用 H2 是为了保证本机直接启动和自动化巡检不破坏正式库。原始 `087-backend/sql/init.sql` 包含 `DROP DATABASE IF EXISTS course_manage_db`，因此 MySQL 真实兼容性通过 `course_manage_087_verify` 临时库验证；正式部署仍使用 `application-mysql.yml`，账号密码为 `root / 1234`。

## 6. 默认账号

- 管理员：`admin / 123456`
- 教师：`teacher / 123456`
- 学生：`student / 123456`
- 学生：`student2 / 123456`

## 7. 仍有差距

1. 密码仍为明文存储，生产环境建议改为 BCrypt。
2. 当前运行态 token 采用进程内存存储，重启后登录态会全部失效；演示足够，但离分布式会话还有距离。
3. Redis 依赖仍保留在工程中，但当前默认演示链路并未验证真实 Redis 高可用、过期策略或多实例同步。
4. 前端生产包较大，建议后续拆分图表、后台和学生端路由模块。
5. 课程容量冲突、复杂排课冲突检测、导入导出和文件资源真实上传仍需后续补强。
