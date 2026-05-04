# 094 宠物咖啡馆平台检查报告

## 1. 检查结论

- 项目编号：`094`
- 项目名称：`SpringBoot 宠物咖啡馆平台设计与实现`
- 检查日期：`2026-05-03`
- 当前状态：`已完成`
- 综合结论：`本轮已完成 JDK 17 兼容、Java 源文件 BOM 清理、Mapper 扫描修复、默认 H2 自举、MySQL root / 1234 非破坏验证入口、运行态 token 存储替代 Redis、Bearer 请求头兼容、登录 password 脱敏、真实 HTTP 401/403/500、宠物公开列表放行、顾客/店长/管理员权限边界、前端代理与构建修复，并对默认 H2、mysql-verify 临时库以及前端 3094 代理联调完成真实链路复测。`

## 2. 项目形态

- 后端目录：`094-backend`
- 前端目录：`094-frontend`
- 后端技术栈：`Spring Boot 2.7.18 + MyBatis-Plus + JWT + H2/MySQL + Redis 依赖保留`
- 前端技术栈：`Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts`
- 默认后端端口：`8094`
- 前端开发端口：`3094`
- MySQL 账号密码：`root / 1234`

## 3. 本轮确认与修复

1. `pom.xml` 已升级到 JDK 17，并补齐 H2、Spring Boot Test 与新版 MySQL 驱动。
2. 清理了 `094-backend/src/main/java` 下 46 个 Java 源文件的 UTF-8 BOM，修复原项目在 `javac` 阶段直接报 `\ufeff` 非法字符的问题。
3. 启动类已补充 `@MapperScan("com.petcafe.mapper")`，避免 Mapper 接口未注册。
4. 默认后端环境已切换为 H2 内存库自举，并新增 `schema-h2.sql` / `data-h2.sql`。
5. 新增 `application-mysql.yml` 与 `application-mysql-verify.yml`，保留正式 MySQL 部署入口与非破坏性验证入口。
6. 新增 `RuntimeStoreService`，登录态不再硬绑定 Redis。
7. `JwtInterceptor` 已兼容 `Authorization: Bearer <token>`，并统一走运行态 token 校验。
8. `User.password` 已改为 Jackson `WRITE_ONLY`，登录响应和用户信息接口不再泄露密码。
9. `GlobalExceptionHandler` 已返回真实 HTTP 状态码，未登录 `401`、越权 `403`、系统异常 `500` 可被真实透出。
10. 拦截器已补放行 `/api/pet/public/**`，修复 PRD 口径中的店宠公开列表匿名访问被误拦截问题。
11. 权限边界已收紧：
    - `ADMIN`：用户、区域、公告、统计
    - `ADMIN/STAFF`：门店、店宠、菜单、座位、预约状态流转、评价回复
    - `CUSTOMER`：预约创建/取消、下单、余额支付、评价
12. 前端 Vite 端口和代理目标支持环境变量覆盖，请求头已统一补齐 Bearer token。
13. 新增 `PetCafePlatformApplicationSmokeTest`，覆盖匿名 `401`、公开店宠/菜单/公告、登录脱敏、统计越权、店长公告越权、顾客下单支付、店长下单越权和登出失效。

## 4. 验证结果

### 4.1 后端测试

- 执行命令：`094-backend/mvn.cmd test`
- 结果：`通过`
- 备注：新增 `PetCafePlatformApplicationSmokeTest`，覆盖公开接口、登录脱敏、角色权限、顾客下单支付和登出失效。

### 4.2 前端构建

- 执行命令：`094-frontend/npm.cmd install`
- 结果：`通过`

- 执行命令：`094-frontend/npm.cmd run build`
- 结果：`通过`
- 备注：保留 Vite chunk 体积告警，不影响产物生成。

### 4.3 H2 启动与接口抽测

- 后端启动命令：`cmd /c "mvn.cmd spring-boot:run -Dspring-boot.run.arguments=--server.port=18105"`
- 验证端口：`18105`
- 关键结果：`{"anonInfo":401,"pets":2,"menus":2,"notices":2,"adminRole":"ADMIN","adminPasswordHidden":true,"stats403":403,"staffNotice403":403}`

### 4.4 MySQL 启动与接口抽测

- MySQL 连接：`localhost:3306`，账号 `root / 1234`
- 准备方式：临时 helper `094-backend/target/MysqlPrepare094.java` 创建 `pet_cafe_platform_094_verify`
- 后端启动命令：`cmd /c "set SPRING_PROFILES_ACTIVE=mysql-verify&& mvn.cmd spring-boot:run -Dspring-boot.run.arguments=--server.port=18106"`
- 验证库：`pet_cafe_platform_094_verify`
- 验证端口：`18106`
- 关键结果：`{"anonInfo":401,"pets":2,"adminRole":"ADMIN","passwordHidden":true,"stats403":403,"staffOrder403":403}`

### 4.5 前端代理联调

- 前端启动命令：`cmd /c "set VITE_API_TARGET=http://127.0.0.1:18105&& npm.cmd run dev -- --host 0.0.0.0 --port 3094"`
- 前端端口：`3094`
- 结果：`通过`
- 关键结果：`{"home":200,"loginCode":200,"role":"ADMIN","hasPassword":false}`

## 5. H2 与 MySQL 口径说明

默认使用 H2 是为了保证本机直接启动和自动化巡检不破坏正式库。原始 `094-backend/sql/init.sql` 包含 `DROP DATABASE IF EXISTS pet_cafe_platform_094`，因此 MySQL 真实兼容性通过 `pet_cafe_platform_094_verify` 临时库验证；正式部署仍使用 `application-mysql.yml`，账号密码为 `root / 1234`。

## 6. 默认账号

- 管理员：`admin / 123456`
- 店长：`staff / 123456`
- 顾客：`customer / 123456`

## 7. 仍有差距

1. 密码仍为明文存储，生产环境建议改为 BCrypt。
2. 当前运行态 token 采用进程内存存储，重启后登录态会全部失效；演示足够，但离分布式会话还有距离。
3. Redis 依赖仍保留在工程中，但当前默认演示链路并未验证真实 Redis 高可用、缓存或多实例同步。
4. 订单支付后当前直接同步落为 `COMPLETED`，真实门店核销、退款回滚和并发库存保护仍未落地。
5. 打包后的 fat jar 在本机用 `java -jar` 直接拉起时出现过本地类加载异常；本轮真实启动验证使用 `mvn spring-boot:run` 已通过，后续如需独立交付包仍建议补一次纯 jar 启动排查。
6. 前端仍有明显大包告警，复杂运营看板、图片上传和更多会员互动能力还需要后续优化。
