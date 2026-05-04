# 073 基于SpringBoot和Vue的人事管理系统检查报告

## 1. 检查结论

- 项目编号：`073`
- 项目名称：`基于SpringBoot和Vue的人事管理系统`
- 检查日期：`2026-05-01`
- 当前状态：`已完成`
- 综合结论：`本轮已修复默认环境强依赖 MySQL/Redis/8080、H2 自举缺失、MySQL 驱动旧坐标、后端无自动化测试、JWT 工具静态调用导致编译失败、登录放行路径错误、Bearer token 不兼容、登录响应密码泄露、未登录/越权 HTTP 状态不准确、登出后 token 不失效、员工可越权访问人事/薪资/合同/考勤/请假等接口、前端代理仍指向 8080、前端请求头缺少 Bearer token 等问题。当前后端可在默认 H2 环境下启动并通过自动化冒烟测试，前端可完成生产构建，真实 HTTP 人事主链路和前端代理登录复测通过。`

## 2. 项目形态

- 后端目录：`073-backend`
- 前端目录：`073-frontend`
- 后端技术栈：`Spring Boot 2.7.18 + MyBatis XML + PageHelper + JWT + H2/MySQL + Redis 依赖保留`
- 前端技术栈：`Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts`
- 默认后端端口：`8073`
- 前端开发端口：`3073`

## 3. 本轮修复

### 3.1 默认运行环境修复

1. 将后端默认端口从 `8080` 调整为 `8073`。
2. 默认 `application.yml` 改为 H2 内存库自举，新增 `db/schema-h2.sql` 与 `db/data-h2.sql`。
3. 新增 `application-mysql.yml`，保留原 MySQL 部署入口。
4. 修复 MySQL 驱动坐标为 `com.mysql:mysql-connector-j`。
5. 项目编译目标改为 JDK 17，并增加 H2 与 Spring Boot Test 依赖。
6. PageHelper 默认方言改为 `h2`，MySQL profile 使用 MySQL 方言。
7. 默认演示数据覆盖部门、职位、员工、用户、薪资、招聘、简历、培训、合同和公告。

### 3.2 登录、鉴权与权限修复

1. 修复 `UserService` 静态调用 `JwtUtils.generateToken` 导致的编译失败。
2. `User.password` 改为 `WRITE_ONLY`，登录响应和用户信息响应不再回传密码。
3. JWT 拦截器兼容 `Authorization: Bearer <token>`。
4. 修复登录放行路径，从错误的 `/api/auth/login` 改为真实的 `/api/user/login`。
5. 新增本地运行态 token 失效表，登出后旧 token 返回 HTTP `401`。
6. 全局异常处理改为真实 HTTP 状态：未登录/失效 `401`，越权 `403`，参数错误 `400`，资源不存在 `404`。
7. 用户、员工、部门、职位、招聘、简历、培训、公告等管理接口增加服务端 admin/hr 校验。
8. 考勤、请假、薪资、合同接口增加员工本人归属校验，普通员工不能枚举或操作他人数据。
9. 请假申请会强制使用当前员工档案的 `employeeId`，避免通过请求体冒充他人请假。

### 3.3 人事业务修复

1. 员工访问用户列表、人事统计、部门创建、薪资创建、考勤统计等管理接口均返回 HTTP `403`。
2. HR 可访问用户列表、员工统计和部门树。
3. 员工可正常访问本人今日考勤、上班打卡、下班打卡和请假申请。
4. 员工访问其他员工合同记录返回 HTTP `403`。
5. 用户删除、密码重置、修改密码等操作补充基础角色和输入校验。

### 3.4 前端修复

1. 前端 Vite 开发端口改为 `3073`。
2. 前端代理目标改为 `http://localhost:8073`，并支持 `VITE_API_TARGET` 覆盖。
3. 请求拦截器自动补齐 `Bearer` token，HTTP `401` 会清理登录态并跳转登录页。
4. 新增 `package-lock.json`，保证依赖安装可复现。

### 3.5 文档与测试修复

1. 新增后端冒烟测试 `HrmApplicationSmokeTest`。
2. 新增 `073-backend/README.md`，补齐默认启动、MySQL profile、默认账号、前端联调和验证命令。
3. 新增 `073-backend/启动说明.txt`，便于答辩和本地演示。
4. 新增本巡检报告，并同步更新总台账。

## 4. 当前实现与目标能力对照

### 4.1 已具备能力

1. 后端默认 H2 自举，可直接执行 `mvn spring-boot:run` 启动。
2. 管理员、HR 和员工账号可登录。
3. 登录响应不回传 `password` 字段。
4. 未登录访问受保护接口返回 HTTP `401`。
5. 普通员工访问人事管理接口返回 HTTP `403`。
6. HR 可访问用户列表、员工统计、部门树等人事管理接口。
7. 员工可进行本人考勤打卡和请假申请。
8. 员工提交请假时无法通过请求体篡改 `employeeId`。
9. 员工不能访问其他员工合同记录。
10. 登出后旧 token 失效，重新访问用户信息返回 HTTP `401`。
11. 前端构建和代理登录已与后端主链路对齐。

### 4.2 仍有差距

1. 密码仍为 MD5 存储，生产环境建议改为 BCrypt。
2. 默认演示模式使用 H2 内存库，MySQL profile 需要按目标机器单独导入 `sql/init.sql`。
3. Redis 依赖保留但当前默认演示链路未使用分布式会话。
4. 薪资、招聘、培训、合同等模块为基础 CRUD，复杂审批、通知和导入导出仍未完整实现。
5. 前端仪表盘和管理页面仍是简化管理台，员工端个性化首页仍可继续优化。
6. 前端构建存在 chunk 体积提示，当前不影响构建产物生成。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`073-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0, Skipped: 0`
- 覆盖内容：
  1. 默认 H2 上下文可启动
  2. 未登录访问用户信息返回 HTTP `401`
  3. 管理员、HR 和员工登录成功且密码字段不回传
  4. 员工访问用户列表返回 HTTP `403`
  5. HR 访问用户列表、员工统计和部门树成功
  6. 员工访问公告、今日考勤、上班打卡和下班打卡成功
  7. 员工请假申请成功，且请求体中的他人 `employeeId` 被覆盖为本人
  8. 管理员登出后旧 token 返回 HTTP `401`

### 5.2 前端构建

- 执行命令：`073-frontend/npm run build`
- 结果：`通过`
- 备注：Vite 输出 chunk 体积提示，不影响构建产物生成。

### 5.3 启动与接口抽测

- 后端启动命令：`073-backend/mvn spring-boot:run`
- 结果：`通过`
- 后端端口：`8073`
- 真实 HTTP 抽测结果：
  1. `GET /api/user/info` 未登录返回 HTTP `401`
  2. `POST /api/user/login` 管理员、HR、员工登录均返回 HTTP `200`，且无 `password` 泄露
  3. 员工访问 `GET /api/user/list` 返回 HTTP `403`
  4. 员工创建部门、薪资和访问考勤统计均返回 HTTP `403`
  5. 员工访问其他员工合同返回 HTTP `403`
  6. HR 访问用户列表、员工统计和部门树返回 HTTP `200`
  7. 员工访问今日考勤、上班打卡、下班打卡返回 HTTP `200`
  8. 员工提交请假时请求体 `employeeId=2` 被服务端覆盖为本人 `employeeId=3`
  9. 管理员登出返回 HTTP `200`
  10. 登出后访问 `GET /api/user/info` 返回 HTTP `401`

### 5.4 前端代理联调

- 后端启动端口：`8073`
- 前端启动命令：`073-frontend/npm run dev -- --host 127.0.0.1`
- 前端端口：`3073`
- 结果：`通过`
- 关键结果：
  1. `GET http://127.0.0.1:3073/` 返回 HTTP `200`，页面包含 `#app` 挂载点
  2. `POST http://127.0.0.1:3073/api/user/login` 返回 HTTP `200`，业务码 `200`，角色为 `admin`，token 存在

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- MySQL 兼容配置：`073-backend/src/main/resources/application-mysql.yml`
- MySQL 初始化脚本：`073-backend/sql/init.sql`
- 默认管理员：`admin / 123456`
- 默认 HR：`hr / 123456`
- 默认员工：`zhangsan / 123456`
- 说明：
  1. 直接执行 `073-backend/mvn spring-boot:run` 即可使用默认 H2 环境启动后端。
  2. H2 控制台地址为 `http://localhost:8073/h2-console`，JDBC URL 为 `jdbc:h2:mem:hrm_system_073`。
  3. 如需切换 MySQL，请先确认 `sql/init.sql` 中的建库/删库语句可接受，再导入脚本并使用 `mysql` profile 启动。
  4. 前端执行 `npm run dev` 后默认访问 `http://localhost:3073`。

## 7. 备注

1. 本轮后端真实启动使用端口 `8073`，前端真实启动使用端口 `3073`。
2. 原始 SQL 脚本包含 `DROP DATABASE`，为避免破坏本机数据，默认验证采用 H2 自举链路。
3. `mvn` 输出中的 `rdc` profile 警告来自外部 Maven 环境，不影响本项目测试和启动结论。
4. 浏览器 DevTools MCP 因已有 Chrome profile 锁定无法进行交互截图，本轮前端验收以真实 HTTP 页面响应、生产构建和代理登录为准。
5. `target/`、`node_modules/`、`dist/` 和临时日志等生成物不纳入提交。
