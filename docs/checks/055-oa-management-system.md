# 055 基于 Java 的企业 OA 管理系统检查报告

## 1. 检查结论

- 项目编号：`055`
- 项目名称：`基于 Java 的企业 OA 管理系统`
- 检查日期：`2026-04-28`
- 当前状态：`已完成`
- 综合结论：`本轮已修复后端 Maven 编译失败、默认环境强依赖 MySQL/8080、H2 自举缺失、JDK 17 下旧版 JJWT 兼容风险、分页方言固定 MySQL、用户密码响应泄露风险、JWT Bearer 兼容缺失、用户启用状态未校验、基础管理员接口缺少服务端权限拦截、前后端考勤/会议室/工作日志/请假审批路径不一致、前端代理仍指向 8080 等问题。当前后端可在默认 H2 环境下启动并通过自动化冒烟测试，前端可完成生产构建，真实 HTTP 核心链路抽测通过。`

## 2. 项目形态

- 后端目录：`055-backend`
- 前端目录：`055-frontend`
- 后端技术栈：`Spring Boot 3.2.0 + MyBatis-Plus 3.5.5 + JWT + H2/MySQL`
- 前端技术栈：`Vue 3 + Vite 4 + Element Plus + Pinia + ECharts`
- 默认后端端口：`8055`
- 前端开发端口：`3055`

## 3. 本轮修复

### 3.1 默认运行环境修复

1. 将后端默认端口从 `8080` 调整为 `8055`，避免合集内项目端口冲突。
2. 默认 `application.yml` 改为 H2 内存库自举，新增 `schema-h2.sql` 与 `data-h2.sql`。
3. 新增 `application-mysql.yml`，保留 MySQL 部署模式，通过 `-Dspring-boot.run.profiles=mysql` 切换。
4. 增加 H2 和 Spring Boot Test 依赖。
5. JJWT 从旧版 `0.9.1` 升级到 `0.12.3`，修复 JDK 17 下 JAXB 与旧 API 兼容风险。
6. MyBatis-Plus 分页插件改为自动识别数据库方言，兼容默认 H2 与 MySQL profile。
7. 文件上传目录改为 `./.tmp/055-uploads/`，避免默认写入不可控路径。

### 3.2 后端编译与接口修复

1. 修复 `LeaveController` 与 `LeaveService` 列表、我的请假和审批方法签名不一致导致的编译失败。
2. 修复 `DepartmentController` 调用不存在分页方法导致的编译失败。
3. 修复 `UserController.login` 与 `UserService.login` 返回类型不一致导致的编译失败。
4. 修复 `UserController.updateProfile` 与 `UserService.updateProfile` 参数不一致导致的编译失败。
5. `UserService` 改为注入式调用 `JwtUtils`，适配新版 JWT 工具类。
6. 请假列表补充 `keyword` 查询支持，匹配前端筛选参数。

### 3.3 登录、鉴权与权限修复

1. `User.password` 改为 `WRITE_ONLY`，支持写入但响应不再回传密码字段。
2. 登录成功返回 token 和脱敏用户信息。
3. 修改密码补充用户不存在和新密码为空校验。
4. JWT 拦截器兼容 `Bearer` token。
5. JWT 拦截器兼容 `OPTIONS` 预检请求。
6. JWT 拦截器补充 token 对应用户存在和启用状态校验。
7. JWT 拦截器补充基础管理员权限校验，普通员工访问用户管理等管理员接口返回 HTTP `403`。

### 3.4 前端联调修复

1. 前端 Vite 开发端口改为 `3055`。
2. 前端代理目标改为 `http://localhost:8055`。
3. 请求拦截器自动补齐 `Bearer` token。
4. 考勤打卡接口兼容 `/attendance/clock-in` 与 `/attendance/clock-out`。
5. 会议室接口兼容 `/meeting-room/**`。
6. 工作日志接口兼容 `/work-log/**`。
7. 请假审批前端改为请求 `/leave/approve/{id}`，匹配后端真实路由。
8. 新增前端 `package-lock.json`，保证依赖安装可复现。

### 3.5 文档与测试修复

1. 新增后端冒烟测试 `OaApplicationSmokeTest`。
2. 新增 `055-backend/README.md`，补齐默认 H2 启动、MySQL profile、默认账号、前端端口和验证命令。
3. 新增 `055-backend/启动说明.txt`，便于答辩和本地演示。
4. 新增本巡检报告，并同步更新总台账。

## 4. 当前实现与目标能力对照

### 4.1 已具备能力

1. 后端默认 H2 自举，可直接执行 `mvn spring-boot:run` 启动。
2. 默认管理员、经理、HR 和普通员工账号可登录。
3. 管理员可查看统计概览、用户、部门、会议室、工资等管理接口。
4. 员工可查看个人信息、公告、个人请假、个人考勤、个人会议、个人日程、工资和工作日志。
5. 可提交请假申请并由管理员审批。
6. 可进行当天上班打卡并查询当天考勤。
7. 公告、会议室、工作日志、日程、文档、工资等核心接口可访问。
8. 前端登录、代理、鉴权请求头和主要接口路径已与后端主链路对齐。

### 4.2 仍有差距

1. 密码仍为明文存储，生产环境建议改为 BCrypt。
2. Redis 配置仍保留为生产扩展项，默认演示链路不依赖 Redis。
3. 当前管理员权限拦截为基础版本，复杂部门经理/HR 审批权限仍可继续细化。
4. 文档上传使用本地目录，生产环境建议切换到持久化对象存储或专用文件服务器。
5. 前端构建存在 Vite chunk 体积提示，当前不影响构建产物生成。

## 5. 验证结果

### 5.1 后端测试

- 执行命令：`055-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0, Skipped: 0`
- 覆盖内容：
  1. 默认 H2 上下文可启动
  2. 管理员和普通员工登录成功
  3. 未携带 token 访问用户信息返回 HTTP `401`
  4. 管理员访问统计接口成功
  5. 普通员工访问用户管理接口返回 HTTP `403`
  6. 管理员访问部门树成功
  7. 普通员工访问已发布公告成功
  8. 普通员工可提交请假申请和查询我的请假
  9. 管理员可审批请假
  10. 普通员工可上班打卡并查询当天考勤

### 5.2 前端构建

- 执行命令：`055-frontend/npm run build`
- 结果：`通过`
- 备注：Vite 输出 chunk 体积提示，不影响构建产物生成。

### 5.3 启动与接口抽测

- 后端启动命令：`055-backend/mvn spring-boot:run`
- 结果：`通过`
- 抽测地址：
  1. 未登录用户信息：`GET http://localhost:8055/api/user/info`
  2. 管理员登录：`POST http://localhost:8055/api/user/login`
  3. 普通员工登录：`POST http://localhost:8055/api/user/login`
  4. 统计概览：`GET http://localhost:8055/api/statistics`
  5. 用户分页越权验证：`GET http://localhost:8055/api/user/list`
  6. 部门树：`GET http://localhost:8055/api/department/tree`
  7. 已发布公告：`GET http://localhost:8055/api/notice/published`
  8. 请假申请与我的请假：`/api/leave/**`
  9. 请假审批：`PUT http://localhost:8055/api/leave/approve/{id}`
  10. 考勤打卡与当天考勤：`/api/attendance/**`
  11. 会议室列表：`GET http://localhost:8055/api/meeting-room/all`
  12. 工作日志：`GET http://localhost:8055/api/work-log/my`
- 关键业务结果：
  1. 未登录访问用户信息返回 HTTP `401`
  2. 管理员和普通员工登录均返回业务码 `200`
  3. 普通员工访问用户管理接口返回 HTTP `403`
  4. 统计、部门、公告、请假、审批、考勤、会议室和工作日志接口均返回业务码 `200`
  5. 联调结束后 `8055` 无 LISTENING 残留端口

## 6. 默认账号与运行说明

- 默认运行模式：`H2`
- MySQL 兼容配置：`055-backend/src/main/resources/application-mysql.yml`
- MySQL 初始化脚本：`055-backend/sql/init.sql`
- 默认管理员：`admin / admin123`
- 默认经理：`manager / manager123`
- 默认员工：`employee / employee123`
- 默认 HR：`hr / hr123456`
- 说明：
  1. 直接执行 `055-backend/mvn spring-boot:run` 即可使用默认 H2 环境启动后端。
  2. H2 控制台地址为 `http://localhost:8055/h2-console`，JDBC URL 为 `jdbc:h2:mem:oa_system`。
  3. 如需切换 MySQL，可先导入 `055-backend/sql/init.sql`，再运行 `mvn spring-boot:run -Dspring-boot.run.profiles=mysql`。
  4. 前端执行 `npm run dev` 后默认访问 `http://localhost:3055`。

## 7. 备注

1. 本轮后端真实启动使用端口 `8055`。
2. `mvn` 输出中的 `rdc` profile 警告来自外部 Maven 环境，不影响本项目测试和启动结论。
3. `target/`、`node_modules/`、`dist/` 和 `.tmp/` 临时日志等生成物不纳入提交。
