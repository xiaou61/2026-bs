# 085 数学课程评价系统检查报告

## 1. 检查结论

- 项目编号：`085`
- 项目名称：`数学课程评价系统`
- 检查日期：`2026-05-02`
- 当前状态：`已完成`
- 综合结论：`本轮已确认并完成默认环境 H2 自举、MySQL `root / 1234` 兼容、JDK 17 与 JJWT 运行兼容、JWT Bearer 请求头、登录响应 password 字段脱敏、真实 HTTP `401/403`、登出失效、管理员/教师/学生角色边界、教师课程归属绑定、评价详情/任务记录权限、学生评价提交链路和前端代理联调验证。当前后端默认 H2 环境与 mysql-verify 临时库环境均可稳定启动并通过核心接口抽测，前端可完成生产构建和 `3085` 代理登录联调。`

## 2. 项目形态

- 后端目录：`085-backend`
- 前端目录：`085-frontend`
- 后端技术栈：`Spring Boot 2.7.18 + MyBatis XML + PageHelper + JWT + H2/MySQL + Redis 依赖保留`
- 前端技术栈：`Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts`
- 默认后端端口：`8085`
- 前端开发端口：`3085`
- MySQL 账号密码：`root / 1234`

## 3. 本轮确认与修复

1. 默认后端配置已切到 H2 内存库自举，并包含 `schema-h2.sql` 与 `data-h2.sql`。
2. 正式 MySQL 部署入口已保留在 `application-mysql.yml`，验证环境入口为 `application-mysql-verify.yml`。
3. MySQL 驱动、JDK 17、H2 和 JAXB/JJWT 兼容依赖已配置到位。
4. JWT 拦截器已兼容 `Authorization: Bearer <token>`。
5. 运行态 token 表已接入登录/登出校验，旧 token 在登出后会失效。
6. 登录响应与用户信息接口已确认不会返回 `password` 字段。
7. 全局异常处理已返回真实 HTTP 状态，未登录为 `401`，越权为 `403`。
8. 统计接口仅允许管理员和教师访问，学生访问返回 `403`。
9. 课程管理接口已限制为管理员和教师；教师新增/修改课程时 `teacherId` 会强制绑定当前教师，不能伪造。
10. 分类、指标、任务、公告管理接口已限制管理员权限。
11. 评价详情、任务记录、任务汇总等接口已按学生本人/授课教师/管理员做访问控制。
12. 学生评价提交已防重复提交，并按当前登录学生绑定 `studentId`。
13. 前端请求头已统一补齐 `Bearer` token，Vite 代理目标支持 `VITE_API_TARGET` 覆盖。
14. 后端已存在冒烟测试 `MathEvalApplicationSmokeTest`，覆盖登录、权限、课程归属、评价提交和登出。

## 4. 验证结果

### 4.1 后端测试

- 执行命令：`085-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0`
- 覆盖内容：未登录 `401`、公开公告、四账号登录、登录脱敏、管理员/教师统计访问、学生统计越权、教师课程 `teacherId` 防伪造、学生任务记录/详情越权、教师任务汇总和学生评价提交、登出失效。

### 4.2 前端构建

- 执行命令：`085-frontend/npm run build`
- 结果：`通过`
- 备注：Vite 输出 chunk 体积提示，不影响构建产物生成。

### 4.3 H2 启动与接口抽测

- 后端启动命令：`085-backend/mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=18086`
- 验证端口：`18086`
- 关键结果：`{"anon":401,"publicNotice":200,"adminLogin":200,"loginHasPassword":false,"teacherStats":200,"studentStats":403,"teacherCourseAdd":200,"teacherCourseTeacherId":2,"studentCourseAdd":403,"student2Submit":200,"logout":200,"afterLogout":401}`

### 4.4 MySQL 启动与接口抽测

- MySQL 连接：`localhost:3306`，账号 `root / 1234`
- 准备方式：通过 Connector/J 在 MySQL 中重建并导入 `math_eval_085_verify`
- 后端启动命令：`085-backend/mvn spring-boot:run -Dspring-boot.run.profiles=mysql-verify -Dspring-boot.run.arguments=--server.port=18087`
- 验证库：`math_eval_085_verify`
- 验证端口：`18087`
- 关键结果：`{"anon":401,"publicNotice":200,"adminLogin":200,"teacherLogin":200,"studentLogin":200,"loginHasPassword":false,"adminStats":200,"studentStats":403,"taskAdd":200,"taskCreatorId":1,"studentTaskRecords":403,"student2Detail":403,"teacherSummary":200,"student2Submit":200,"logout":200,"afterLogout":401}`

### 4.5 前端代理联调

- 后端启动端口：`18087`
- 前端启动命令：`VITE_API_TARGET=http://127.0.0.1:18087 npm run dev -- --host 127.0.0.1 --port 3085`
- 前端端口：`3085`
- 结果：`通过`
- 关键结果：`{"homeStatus":200,"proxyLoginCode":200,"proxyRole":"admin","proxyHasPassword":false}`

## 5. H2 与 MySQL 口径说明

默认使用 H2 是为了自动化测试和默认演示不破坏正式库。原始 `085-backend/sql/init.sql` 包含 `DROP DATABASE IF EXISTS math_eval_db`，因此巡检默认使用 H2，MySQL 真实兼容性通过 `math_eval_085_verify` 临时库验证；正式部署仍使用 `application-mysql.yml`，账号密码为 `root / 1234`。

## 6. 默认账号

- 管理员：`admin / 123456`
- 教师：`teacher / 123456`
- 学生：`student / 123456`
- 学生2：`student2 / 123456`

## 7. 仍有差距

1. 密码仍为明文存储，生产环境建议改为 BCrypt。
2. 默认演示模式使用 H2 内存库，正式 MySQL profile 仍需目标机器导入 `sql/init.sql`。
3. Redis 依赖保留但当前默认演示链路未使用分布式 token 或真实缓存淘汰策略。
4. 当前评价结果以管理统计和记录查询为主，真实匿名化分析、教师改进闭环和复杂指标模型仍偏演示级。
5. 前端单包体积较大，后续可拆分路由与图表依赖。
