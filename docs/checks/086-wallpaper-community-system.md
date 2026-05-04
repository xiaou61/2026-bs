# 086 高清壁纸社区网站检查报告

## 1. 检查结论

- 项目编号：`086`
- 项目名称：`高清壁纸社区网站`
- 检查日期：`2026-05-02`
- 当前状态：`已完成`
- 综合结论：`本轮已完成默认 H2 自举、MySQL `root / 1234` 验证入口、JDK 17 兼容、JWT Bearer 请求头、运行态 token 校验、登录响应 password 脱敏、真实 HTTP `401/403/404`、前端代理与构建修复，并对壁纸投稿归属、审核上架、统计权限、收藏、详情和登出失效完成真实链路抽测。默认 H2 环境、mysql-verify 临时库环境和前端 `3086` 代理联调均已通过。`

## 2. 项目形态

- 后端目录：`086-backend`
- 前端目录：`086-frontend`
- 后端技术栈：`Spring Boot 2.7.18 + MyBatis-Plus + JWT + H2/MySQL + Redis 依赖保留`
- 前端技术栈：`Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts`
- 默认后端端口：`8086`
- 前端开发端口：`3086`
- MySQL 账号密码：`root / 1234`

## 3. 本轮确认与修复

1. 默认后端环境已改为 H2 内存库自举，并新增 `schema-h2.sql` 与 `data-h2.sql`。
2. 新增 `application-mysql.yml` 与 `application-mysql-verify.yml`，保留正式 MySQL 部署入口与非破坏性验证入口。
3. `pom.xml` 已升级到 JDK 17，并补齐 H2、JAXB 与新 MySQL 驱动坐标。
4. 新增 `RuntimeStoreService`，登录、登出与鉴权不再硬绑定 Redis。
5. `JwtInterceptor` 已兼容 `Authorization: Bearer <token>`，并统一回写 `userId/role`。
6. `AuthService` 已改为基于运行态 token 校验可选登录态，公开详情和下载接口可安全识别匿名用户。
7. `SysUser.password` 已改为 Jackson `WRITE_ONLY`，登录响应和用户信息接口不再泄露密码。
8. `GlobalExceptionHandler` 已返回真实 HTTP 状态码，未登录 `401`，越权 `403`，参数错误 `400`，资源不存在 `404`。
9. 壁纸新增已强制绑定当前登录用户，不能伪造 `uploaderId`。
10. 非本人/非管理员删除壁纸时已返回真实 `403`。
11. 审核与上架链路已确认只允许管理员操作。
12. 统计接口已确认普通用户访问返回 `403`，管理员访问返回 `200`。
13. 前端请求头已统一补齐 Bearer token，Vite 端口和代理目标支持环境变量覆盖。

## 4. 验证结果

### 4.1 后端测试

- 执行命令：`086-backend/mvn test`
- 结果：`通过`
- 备注：当前仓库无现成测试类，`Surefire` 输出 `No tests to run`，但编译与依赖解析已通过。

### 4.2 前端构建

- 执行命令：`086-frontend/npm.cmd install`
- 结果：`通过`
- 备注：已生成 `package-lock.json`。

- 执行命令：`086-frontend/npm.cmd run build`
- 结果：`通过`
- 备注：保留 Vite chunk 体积告警，不影响产物生成。

### 4.3 H2 启动与接口抽测

- 后端启动命令：`086-backend/mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=18088`
- 验证端口：`18088`
- 已验证链路：
  - 匿名访问 `/api/auth/info` 返回 `401`
  - 公开公告与公开壁纸列表返回 `200`
  - 管理员/普通用户/第二普通用户登录返回 `200`，且响应中无 `password`
  - 普通用户访问统计接口返回 `403`，管理员访问返回 `200`
  - 普通用户新增壁纸时即使伪造 `uploaderId`，实际仍绑定当前用户 `uploaderId=2`
  - 其他普通用户删除该壁纸返回 `403`
  - 管理员可在待审核列表中看到该壁纸，随后完成审核与上架
  - 第二普通用户收藏后，详情接口 `hasFavorite=true`
  - 下载、登出和旧 token 失效链路通过

### 4.4 MySQL 启动与接口抽测

- MySQL 连接：`localhost:3306`，账号 `root / 1234`
- 准备方式：临时 helper `086-backend/target/MysqlPrepare086.java` 基于 `sql/init.sql` 创建 `wallpaper_086_verify`
- 后端启动命令：`086-backend/mvn spring-boot:run -Dspring-boot.run.profiles=mysql-verify -Dspring-boot.run.arguments=--server.port=18089`
- 验证库：`wallpaper_086_verify`
- 验证端口：`18089`
- 关键结果：`{"anonInfo":401,"publicNotice":2,"adminStats":200,"createdWallpaperId":5,"createdUploaderId":2,"favoriteState":true,"logoutInvalidated":true}`
- 补充说明：原始 MySQL 初始化脚本只有 `admin` 和 `user`，本轮验证时通过注册接口补建了 `user2` 后继续完成越权、审核、收藏和登出链路。

### 4.5 前端代理联调

- 前端启动命令：`VITE_API_TARGET=http://127.0.0.1:18088 npm run dev -- --host 0.0.0.0 --port 3086`
- 前端端口：`3086`
- 结果：`通过`
- 关键结果：`{"status":200,"code":200,"role":"admin","passwordField":null}`

## 5. H2 与 MySQL 口径说明

默认使用 H2 是为了保证本机直接启动和自动化巡检不破坏正式库。原始 `086-backend/sql/init.sql` 包含 `DROP DATABASE IF EXISTS wallpaper_db`，因此 MySQL 真实兼容性通过 `wallpaper_086_verify` 临时库验证；正式部署仍使用 `application-mysql.yml`，账号密码为 `root / 1234`。

## 6. 默认账号

- 管理员：`admin / 123456`
- 普通用户：`user / 123456`
- 复测附加用户：`user2 / 123456`（MySQL 验证阶段通过注册接口创建）

## 7. 仍有差距

1. 密码仍为明文存储，生产环境建议改为 BCrypt。
2. 当前运行态 token 采用进程内存存储，重启后登录态会全部失效；演示足够，但离分布式会话还有距离。
3. Redis 依赖仍保留在工程中，但当前默认演示链路并未验证真实 Redis 高可用、过期策略或多实例同步。
4. 前端生产包较大，建议后续拆分图表与管理页模块。
5. 上传文件仍是本地目录存储，正式部署需补充对象存储、清理策略和更严格的内容校验。
