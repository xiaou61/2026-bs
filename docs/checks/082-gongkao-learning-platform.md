# 082 公考学习平台检查报告

## 1. 检查结论

- 项目编号：`082`
- 项目名称：`公考学习平台`
- 检查日期：`2026-05-02`
- 当前状态：`已完成`
- 综合结论：`本轮已修复默认环境强依赖 MySQL root/root、Redis 和 8080、H2 自举缺失、MySQL 驱动旧坐标、JDK 17/JJWT 兼容、后端无自动化测试、登录响应 password 字段泄露、JWT Bearer 不兼容、未登录/越权 HTTP 状态不准确、登出后 token 不失效、管理员/讲师/学员权限边界缺失、学员可枚举他人学习计划与考试记录、讲师课程 teacherId 可伪造、前端代理端口写死和请求头不规范等问题。当前后端默认 H2 环境可通过冒烟测试，MySQL root/1234 的 mysql-verify 临时库真实 HTTP 验证通过；前端可完成生产构建和 3082 代理登录联调。`

## 2. 项目形态

- 后端目录：`082-backend`
- 前端目录：`082-frontend`
- 后端技术栈：`Spring Boot 2.7.14 + MyBatis-Plus 3.5.3 + JWT + H2/MySQL + Redis 依赖保留`
- 前端技术栈：`Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts`
- 默认后端端口：`8082`
- 前端开发端口：`3082`
- MySQL 账号密码：`root / 1234`

## 3. 本轮修复

1. 默认后端配置改为 H2 内存库自举，新增 `schema-h2.sql` 与 `data-h2.sql`。
2. 新增 `application-mysql.yml`，正式 MySQL 部署入口使用 `gongkao_db`、`root / 1234`。
3. 新增 `application-mysql-verify.yml`，用于 `gongkao_082_verify` 临时库的非破坏 MySQL 验证。
4. 修复 MySQL 驱动坐标为 `com.mysql:mysql-connector-j`，项目编译目标改为 JDK 17。
5. 增加 H2、JAXB 依赖，修复 JDK 17 下 JJWT 0.9.1 运行兼容问题。
6. JWT 拦截器兼容 `Authorization: Bearer <token>`，新增运行态 token 失效表实现登出失效。
7. 登录响应和用户信息接口不再输出 `password` 字段。
8. 全局异常处理返回真实 HTTP 状态：未登录/失效 `401`，越权 `403`，参数错误 `400`。
9. 用户列表、新增、修改、删除和公告后台管理接口补充管理员校验。
10. 学科管理仅允许管理员维护，讲师可查看后台列表和维护课程/题库/试卷。
11. 讲师新增课程时强制绑定当前 `teacherId`，更新/删除时校验本人归属。
12. 学员学习计划和考试记录接口改为按 JWT 当前用户绑定，阻止请求参数或请求体伪造 `userId`。
13. 学员更新/删除学习计划和考试记录时补充本人归属校验。
14. 学科、公告公开列表在 Redis 不可用时自动回落数据库查询，不再阻断默认演示链路。
15. 前端 Vite 端口改为 `3082`，代理目标支持 `VITE_API_TARGET` 覆盖。
16. 前端请求头统一补齐 `Bearer` token，并接入后端登出 API。
17. 新增后端冒烟测试 `GongkaoApplicationSmokeTest`。

## 4. 验证结果

### 4.1 后端测试

- 执行命令：`082-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0`
- 覆盖内容：登录脱敏、管理员统计、学员统计越权、学员访问用户列表越权、学员学习计划 userId 防伪造、讲师课程 teacherId 自动绑定、登出失效。

### 4.2 前端构建

- 执行命令：`082-frontend/npm install`
- 结果：`通过`
- 执行命令：`082-frontend/npm run build`
- 结果：`通过`
- 备注：Vite 输出 chunk 体积提示，不影响构建产物生成。

### 4.3 H2 启动与接口抽测

- 后端启动命令：`082-backend/mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=18082`
- 验证端口：`18082`
- 真实 HTTP 抽测结果：
  1. `GET /api/user/info` 未登录返回 HTTP `401`
  2. `POST /api/user/login` 管理员登录返回 HTTP `200`，且无 `password` 字段
  3. `GET /api/statistics/dashboard` 学员访问返回 HTTP `403`
  4. `POST /api/course/add` 讲师新增课程返回 HTTP `200`
  5. `POST /api/user/logout` 后旧 token 再访问用户信息返回 HTTP `401`

### 4.4 MySQL 启动与接口抽测

- MySQL 连接：`localhost:3306`，账号 `root / 1234`
- MySQL 版本：`8.0.26`
- 准备方式：使用 Connector/J 在 `mysql` 库下重建并导入 `gongkao_082_verify`
- 后端启动命令：`082-backend/mvn spring-boot:run -Dspring-boot.run.profiles=mysql-verify -Dspring-boot.run.arguments=--server.port=18083`
- 验证库：`gongkao_082_verify`
- 验证端口：`18083`
- 验证后处理：已删除临时库 `gongkao_082_verify`
- 真实 HTTP 抽测结果：
  1. `GET /api/user/info` 未登录返回 HTTP `401`
  2. `GET /api/notice/public/list` 返回 HTTP `200`
  3. 管理员登录返回 HTTP `200`，角色为 `admin`，且无 `password` 字段
  4. 学员访问 `GET /api/statistics/dashboard` 返回 HTTP `403`
  5. 学员访问 `GET /api/user/list` 返回 HTTP `403`
  6. 学员新增学习计划时传入伪造 `userId=1`，后端按当前 JWT 绑定为 `userId=3`
  7. 讲师新增课程时传入伪造 `teacherId=1`，后端按当前 JWT 绑定为 `teacherId=2`
  8. 管理员访问 `GET /api/statistics/dashboard` 返回 HTTP `200`
  9. 管理员登出返回 HTTP `200`，旧 token 再访问用户信息返回 HTTP `401`
- 本轮复测关键结果：`{"anon":401,"publicNotice":200,"adminLogin":200,"loginHasPassword":false,"adminRole":"admin","studentStats":403,"studentUserList":403,"planAdd":200,"planList":200,"planUserIds":"3","courseAdd":200,"courseList":200,"courseTeacherIds":"2","adminStats":200,"logout":200,"afterLogout":401}`

### 4.5 前端代理联调

- 后端启动端口：`18082`
- 前端启动命令：`082-frontend/npm run dev -- --host 127.0.0.1 --port 3082`
- 前端端口：`3082`
- 结果：`通过`
- 关键结果：
  1. `GET http://127.0.0.1:3082/` 返回 HTTP `200`
  2. `POST http://127.0.0.1:3082/api/user/login` 返回 HTTP `200`，业务码 `200`，角色为 `admin`，token 存在，且无 `password` 字段

## 5. H2 与 MySQL 口径说明

默认使用 H2 是为了自动化测试和默认演示不破坏正式库。原始 `082-backend/sql/init.sql` 包含 `DROP DATABASE IF EXISTS gongkao_db`，因此巡检默认使用 H2，MySQL 真实兼容性通过 `gongkao_082_verify` 临时库验证；正式部署仍使用 `application-mysql.yml`，账号密码为 `root / 1234`。

## 6. 默认账号

- 管理员：`admin / 123456`
- 讲师：`teacher / 123456`
- 学员：`user / 123456`

## 7. 仍有差距

1. 密码仍为明文存储，生产环境建议改为 BCrypt。
2. 默认演示模式使用 H2 内存库，正式 MySQL profile 需要按目标机器导入 `sql/init.sql`。
3. Redis 依赖保留但当前默认演示链路未使用分布式 token 或真实缓存淘汰策略。
4. 课程章节、试卷题目关联和学员考试提交流程仍偏管理后台视角，前台完整做题交卷体验有待继续补齐。
5. 前端单包体积较大，后续可拆分路由与图表依赖。
