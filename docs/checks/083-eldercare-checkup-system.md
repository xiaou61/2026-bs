# 083 基于B/S的老年人体检管理系统检查报告

## 1. 检查结论

- 项目编号：`083`
- 项目名称：`基于B/S的老年人体检管理系统`
- 检查日期：`2026-05-02`
- 当前状态：`已完成`
- 综合结论：`本轮已修复默认环境强依赖 MySQL root/root、Redis 和 8080、H2 自举缺失、MySQL 驱动旧坐标、JDK 17/JJWT 兼容、后端有效自动化测试缺失、登录响应 password 字段泄露、JWT Bearer 不兼容、未登录/越权 HTTP 状态不准确、登出后 token 不失效、管理员/医生/护士/前台权限边界不足、体检结果 elderId/doctorId 可伪造、预约 createBy 可伪造、前端代理端口写死和请求头不规范等问题。当前后端默认 H2 环境可通过冒烟测试，MySQL root/1234 的 mysql-verify 临时库真实 HTTP 验证通过；前端可完成生产构建和 3083 代理登录联调。`

## 2. 项目形态

- 后端目录：`083-backend`
- 前端目录：`083-frontend`
- 后端技术栈：`Spring Boot 2.7.14 + MyBatis-Plus 3.5.3 + JWT + H2/MySQL + Redis 依赖保留`
- 前端技术栈：`Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts`
- 默认后端端口：`8083`
- 前端开发端口：`3083`
- MySQL 账号密码：`root / 1234`

## 3. 本轮修复

1. 默认后端配置改为 H2 内存库自举，新增 `schema-h2.sql` 与 `data-h2.sql`。
2. 新增 `application-mysql.yml`，正式 MySQL 部署入口使用 `eldercare_db`、`root / 1234`。
3. 新增 `application-mysql-verify.yml`，用于 `eldercare_083_verify` 临时库的非破坏 MySQL 验证。
4. 修复 MySQL 驱动坐标为 `com.mysql:mysql-connector-j`，项目编译目标改为 JDK 17。
5. 增加 H2、JAXB 依赖，修复 JDK 17 下 JJWT 0.9.1 运行兼容问题。
6. JWT 拦截器兼容 `Authorization: Bearer <token>`，并用运行态 token 表实现登出失效。
7. 登录响应和用户信息接口不再输出 `password` 字段。
8. 全局异常处理返回真实 HTTP 状态：未登录/失效 `401`，越权 `403`，参数错误 `400`。
9. 老人档案、体检项目、体检套餐、预约、结果、预警、随访、公告等后台接口补充角色校验。
10. 体检结果新增/更新按预约单自动绑定 `elderId`，并按当前医生绑定 `doctorId`。
11. 预约新增按当前前台账号绑定 `createBy`，阻止请求体伪造。
12. 预警状态处理仅允许管理员/医生，前台、护士越权返回 `403`。
13. 公告公开接口保留匿名访问能力。
14. 前端 Vite 端口改为 `3083`，代理目标支持 `VITE_API_TARGET` 覆盖。
15. 前端请求头统一补齐 `Bearer` token。
16. 新增后端冒烟测试 `ElderCareApplicationSmokeTest`。

## 4. 验证结果

### 4.1 后端测试

- 执行命令：`083-backend/mvn test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0`
- 覆盖内容：未登录 `401`、登录脱敏、管理员统计、前台录入结果越权、医生录入结果、登出失效。

### 4.2 前端构建

- 执行命令：`083-frontend/npm run build`
- 结果：`通过`
- 备注：Vite 输出 chunk 体积提示，不影响构建产物生成。

### 4.3 MySQL 启动与接口抽测

- MySQL 连接：`localhost:3306`，账号 `root / 1234`
- MySQL 版本：`8.0.26`
- 准备方式：使用 Connector/J 在 `mysql` 库下重建并导入 `eldercare_083_verify`
- 导入结果：`executedStatements=27, users=4, sampleName=张护士`
- 后端启动命令：`083-backend/mvn spring-boot:run -Dspring-boot.run.profiles=mysql-verify -Dspring-boot.run.arguments=--server.port=18084`
- 验证库：`eldercare_083_verify`
- 验证端口：`18084`
- 验证后处理：已删除临时库 `eldercare_083_verify`
- 本轮复测关键结果：`{"anon":401,"publicNotice":200,"adminLogin":200,"loginHasPassword":false,"adminRole":"admin","adminRealName":"系统管理员","doctorLogin":200,"nurseLogin":200,"receptionLogin":200,"adminStats":200,"receptionStats":200,"receptionResultAdd":403,"doctorResultAdd":200,"resultList":200,"resultElderId":3,"resultDoctorId":2,"nurseWarningStatus":403,"doctorWarningStatus":200,"appointmentAdd":200,"appointmentCreatedBy":4,"logout":200,"afterLogout":401}`

### 4.4 前端代理联调

- 后端启动端口：`18084`
- 前端启动命令：`083-frontend/npm run dev -- --host 127.0.0.1 --port 3083`
- 前端端口：`3083`
- 结果：`通过`
- 关键结果：`{"pageStatus":200,"loginStatus":200,"code":200,"role":"admin","realName":"系统管理员","hasToken":true,"hasPassword":false}`

## 5. H2 与 MySQL 口径说明

默认使用 H2 是为了自动化测试和默认演示不破坏正式库。原始 `083-backend/sql/init.sql` 包含 `DROP DATABASE IF EXISTS eldercare_db`，因此巡检默认使用 H2，MySQL 真实兼容性通过 `eldercare_083_verify` 临时库验证；正式部署仍使用 `application-mysql.yml`，账号密码为 `root / 1234`。

## 6. 默认账号

- 管理员：`admin / 123456`
- 医生：`doctor / 123456`
- 护士：`nurse / 123456`
- 前台：`reception / 123456`

## 7. 仍有差距

1. 密码仍为明文存储，生产环境建议改为 BCrypt。
2. 默认演示模式使用 H2 内存库，正式 MySQL profile 需要按目标机器导入 `sql/init.sql`。
3. Redis 依赖保留但当前默认演示链路未使用分布式 token 或真实缓存淘汰策略。
4. 体检报告导出、批量导入、短信通知和医学参考值规则仍偏演示级。
5. 前端单包体积较大，后续可拆分路由与图表依赖。
