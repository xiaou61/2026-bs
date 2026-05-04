# 084 教学资料管理系统检查报告

## 1. 检查结论

- 项目编号：`084`
- 项目名称：`教学资料管理系统`
- 检查日期：`2026-05-02`
- 当前状态：`已完成`
- 综合结论：`本轮已修复默认环境强依赖 MySQL root/root、Redis 和 8080、H2 自举缺失、MySQL 驱动旧坐标、JDK 17/JJWT 兼容、后端有效自动化测试缺失、登录响应 password 字段泄露、JWT Bearer 不兼容、未登录/越权 HTTP 状态不准确、登出后 token 不失效、统计/审核/分类/公告/资料接口权限边界不足、教师上传资料 uploaderId 可伪造、资料更新/删除/发布缺少本人或管理员校验、学生审核越权、收藏学习清单删除缺少本人校验、统计 SQL 使用 H2 保留字别名、前端代理端口写死和请求头不规范等问题。当前后端默认 H2 环境可通过冒烟测试，MySQL root/1234 的 mysql-verify 临时库真实 HTTP 验证通过；前端可完成生产构建和 3084 代理登录联调。`

## 2. 项目形态

- 后端目录：`084-backend`
- 前端目录：`084-frontend`
- 后端技术栈：`Spring Boot 2.7.18 + MyBatis 2.3.1 + PageHelper + JWT + H2/MySQL + Redis 依赖保留`
- 前端技术栈：`Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts`
- 默认后端端口：`8084`
- 前端开发端口：`3084`
- MySQL 账号密码：`root / 1234`

## 3. 本轮修复

1. 默认后端配置改为 H2 内存库自举，新增 `schema-h2.sql` 与 `data-h2.sql`。
2. 新增 `application-mysql.yml`，正式 MySQL 部署入口使用 `teachres_db`、`root / 1234`。
3. 新增 `application-mysql-verify.yml`，用于 `teachres_084_verify` 临时库的非破坏 MySQL 验证。
4. 修复 MySQL 驱动坐标为 `com.mysql:mysql-connector-j`，项目编译目标改为 JDK 17。
5. 增加 H2、JAXB 依赖，修复 JDK 17 下 JJWT 0.9.1 运行兼容问题。
6. JWT 拦截器兼容 `Authorization: Bearer <token>`，并用运行态 token 表实现登出失效。
7. 登录响应和用户信息接口不再输出 `password` 字段。
8. 全局异常处理返回真实 HTTP 状态：未登录/失效 `401`，越权 `403`，参数错误 `400`。
9. 统计、审核、分类、公告和资料管理接口补充角色校验。
10. 教师新增资料时强制使用当前登录教师作为 `uploaderId`，阻止请求体伪造。
11. 资料更新、删除、发布改为本人教师或管理员可操作。
12. 教师资料列表仅返回本人上传资料，管理员保留全量管理视角。
13. 学生访问统计、审核提交和管理接口时返回 `403`。
14. 收藏学习清单删除补充当前用户归属校验。
15. 修复统计 SQL 中 `month`、`value` 作为别名导致 H2 语法错误的问题，同时保持前端响应仍输出 `month/value`。
16. 前端 Vite 端口改为 `3084`，代理目标支持 `VITE_API_TARGET` 覆盖。
17. 前端请求头统一补齐 `Bearer` token。
18. 新增后端冒烟测试 `TeachResApplicationSmokeTest`。

## 4. 验证结果

### 4.1 后端测试

- 执行命令：`084-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0`
- 覆盖内容：未登录 `401`、公开资料、三角色登录、登录脱敏、管理员统计、学生统计越权、教师新增资料 uploaderId 防伪造、学生审核越权、管理员审核、下载、收藏和登出失效。

### 4.2 前端构建

- 执行命令：`084-frontend/npm run build`
- 结果：`通过`
- 备注：Vite 输出 chunk 体积提示，不影响构建产物生成。

### 4.3 MySQL 启动与接口抽测

- MySQL 连接：`localhost:3306`，账号 `root / 1234`
- 准备方式：使用 Connector/J 在 MySQL 中重建并导入 `teachres_084_verify`
- 后端启动命令：`084-backend/mvn spring-boot:run -Dspring-boot.run.profiles=mysql-verify -Dspring-boot.run.arguments=--server.port=18085`
- 验证库：`teachres_084_verify`
- 验证端口：`18085`
- 验证后处理：已删除临时库 `teachres_084_verify`
- 本轮复测关键结果：`{"anon":401,"publicMaterial":200,"publicNotice":200,"adminLogin":200,"teacherLogin":200,"studentLogin":200,"loginHasPassword":false,"adminRole":"admin","adminStats":200,"studentStats":403,"materialAdd":200,"teacherList":200,"teacherMaterialUploaderId":2,"studentAudit":403,"adminAudit":200,"studentDownload":200,"studentFavorite":200,"logout":200,"afterLogout":401}`

### 4.4 前端代理联调

- 后端启动端口：`18085`
- 前端启动命令：`VITE_API_TARGET=http://127.0.0.1:18085 npm run dev -- --host 127.0.0.1`
- 前端端口：`3084`
- 结果：`通过`
- 关键结果：`{"homeStatus":200,"proxyLoginStatus":200,"proxyLoginHasToken":true,"proxyLoginHasPassword":false}`

## 5. H2 与 MySQL 口径说明

默认使用 H2 是为了自动化测试和默认演示不破坏正式库。原始 `084-backend/sql/init.sql` 包含 `DROP DATABASE IF EXISTS teachres_db`，因此巡检默认使用 H2，MySQL 真实兼容性通过 `teachres_084_verify` 临时库验证；正式部署仍使用 `application-mysql.yml`，账号密码为 `root / 1234`。

## 6. 默认账号

- 管理员：`admin / 123456`
- 教师：`teacher / 123456`
- 学生：`student / 123456`

## 7. 仍有差距

1. 密码仍为明文存储，生产环境建议改为 BCrypt。
2. 默认演示模式使用 H2 内存库，正式 MySQL profile 需要按目标机器导入 `sql/init.sql`。
3. Redis 依赖保留但当前默认演示链路未使用分布式 token 或真实缓存淘汰策略。
4. 文件上传当前以路径元数据为主，真实对象存储、文件预览、下载鉴权和病毒扫描仍偏演示级。
5. 前端单包体积较大，后续可拆分路由与图表依赖。
