# 096 线上医院挂号系统检查报告

## 1. 检查结论

- 项目编号：`096`
- 项目名称：`线上医院挂号系统`
- 检查日期：`2026-05-03`
- 当前状态：`已完成`
- 综合结论：`本轮已完成 096 最终项目的默认 H2 自举、MySQL root / 1234 验证入口、运行态 token 存储、Bearer 请求头兼容、登录 password 脱敏、真实 HTTP 401/403、公开医生/排班查询、患者挂号支付、医生完成就诊、患者评价、前端代理和构建能力。后端自动化测试、默认 H2 真实启动、MySQL 临时库真实 HTTP 验收和前端 3096 代理联调均已通过。`

## 2. 项目形态

- 后端目录：`096-backend`
- 前端目录：`096-frontend`
- 后端技术栈：`Spring Boot 2.7.18 + MyBatis + PageHelper + JWT + H2/MySQL + Redis 依赖保留`
- 前端技术栈：`Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts`
- 默认后端端口：`8096`
- 前端开发端口：`3096`
- MySQL 账号密码：`root / 1234`

## 3. 本轮确认与修复

1. 后端已使用 JDK 17、新版 MySQL Connector/J、H2 与 JAXB 兼容依赖。
2. 默认环境已切换为 H2 内存库自举，并提供 `schema-h2.sql` / `data-h2.sql`。
3. `application-mysql.yml` 保留正式 MySQL 部署入口，数据库为 `online_hospital_registration_db`，账号密码为 `root / 1234`。
4. `application-mysql-verify.yml` 使用 `online_hospital_registration_096_verify` 临时库做非破坏验证。
5. `schema-mysql.sql` / `data-mysql.sql` 可在 MySQL 验证库中重建 12 张业务表和演示数据。
6. 登录态使用运行态 token 存储，默认演示链路不再强依赖 Redis 可用性。
7. JWT 拦截器兼容 `Authorization: Bearer <token>`，登出后旧 token 失效。
8. 登录响应和用户信息接口不输出 `password` 字段。
9. 全局异常处理可返回真实 HTTP 状态，未登录为 `401`，越权为 `403`。
10. 公开接口可匿名访问科室、医生、医生评价、排班、公告、轮播和热门医生。
11. 修复统计 SQL 的 H2/MySQL 兼容性：
    - `value` 关键字别名改为 `rank_value`，服务层规范回 `value`
    - 趋势统计去除 MySQL 专属 `date_format/date_sub/curdate`，改为通用日期聚合并由 Java 格式化
12. 收紧业务归属校验：他人订单支付、他人预约取消、非本人预约评价、医生完成非本人排班预约均返回 `403`。
13. 前端 Vite 端口为 `3096`，代理目标支持 `VITE_API_TARGET` 覆盖。
14. 前端请求头统一补齐 Bearer token。
15. 新增 `OnlineHospitalRegistrationApplicationSmokeTest`，覆盖公开接口、三角色登录、登录脱敏、权限边界、挂号支付、就诊完成、评价和登出失效。

## 4. 验证结果

### 4.1 后端测试

- 执行命令：在 `096-backend` 下执行 `mvn.cmd test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0`
- 覆盖内容：匿名用户信息 `401`、公开公告/轮播/医生/排班/热门医生、管理员/医生/患者登录、登录脱敏、患者访问用户管理越权、统计接口、患者挂号生成订单、他人支付越权、本人支付、医生完成就诊、患者评价和登出失效。

### 4.2 前端构建

- 执行命令：`096-frontend/npm.cmd run build`
- 结果：`通过`
- 备注：保留 Vite CJS API 和 chunk 体积提示，不影响产物生成。

### 4.3 H2 启动与接口抽测

- 后端启动命令：`mvn.cmd spring-boot:run -Dspring-boot.run.arguments=--server.port=18109`
- 验证端口：`18109`
- 结果：`通过`
- 关键结果：`{"anon":401,"publicNotice":200,"publicBanner":200,"publicDoctor":200,"publicSchedule":200,"publicHotDoctor":200,"adminLogin":200,"doctorLogin":200,"patientLogin":200,"loginHasPassword":false,"patientUserPage":403,"adminStats":200,"departmentRank":200,"appointmentTrend":200,"createAppointment":200,"otherPay":403,"patientPay":200,"doctorFinish":200,"patientReview":200,"logout":200,"afterLogoutCard":401,"createdOrderId":3,"createdAppointmentId":3}`

### 4.4 MySQL 启动与接口抽测

- MySQL 连接：`localhost:3306`，账号 `root / 1234`
- 准备方式：使用 Connector/J 创建 `online_hospital_registration_096_verify`，由 `mysql-verify` profile 执行 schema/data 初始化
- 后端启动命令：`mvn.cmd spring-boot:run -Dspring-boot.run.profiles=mysql-verify -Dspring-boot.run.arguments=--server.port=18110`
- 验证库：`online_hospital_registration_096_verify`
- 验证端口：`18110`
- 验证后处理：已删除临时库 `online_hospital_registration_096_verify`
- 关键结果：`{"anon":401,"publicNotice":200,"publicBanner":200,"publicDoctor":200,"publicSchedule":200,"publicHotDoctor":200,"adminLogin":200,"doctorLogin":200,"patientLogin":200,"loginHasPassword":false,"patientUserPage":403,"adminStats":200,"departmentRank":200,"appointmentTrend":200,"createAppointment":200,"otherPay":403,"patientPay":200,"doctorFinish":200,"patientReview":200,"logout":200,"afterLogoutCard":401,"createdOrderId":3,"createdAppointmentId":3}`

### 4.5 前端代理联调

- 后端启动端口：`18110`
- 前端启动命令：`VITE_API_TARGET=http://127.0.0.1:18110 npm.cmd run dev -- --host 0.0.0.0 --port 3096`
- 前端端口：`3096`
- 结果：`通过`
- 关键结果：`{"homeStatus":200,"proxyLoginStatus":200,"proxyLoginCode":200,"proxyLoginHasToken":true,"proxyLoginHasPassword":false,"adminRole":"ADMIN"}`

## 5. H2 与 MySQL 口径说明

默认使用 H2 是为了保证本机直接启动和自动化巡检不破坏正式库。正式部署仍使用 `application-mysql.yml`，数据库账号密码为 `root / 1234`；本轮 MySQL 兼容性通过 `online_hospital_registration_096_verify` 临时库完成验证，验证结束后已删除临时库。

## 6. 默认账号

- 管理员：`admin / 123456`
- 医生：`doctor01 / 123456`
- 医生：`doctor02 / 123456`
- 患者：`patient01 / 123456`
- 患者：`patient02 / 123456`

## 7. 仍有差距

1. 密码仍为明文存储，生产环境建议改为 BCrypt。
2. 当前运行态 token 为进程内存存储，演示足够，但分布式部署仍需接入 Redis 或集中式会话存储。
3. 当前支付为演示级在线支付接口，未接入真实第三方支付、退款和对账。
4. 排班号源扣减已可跑通，但高并发锁号、取消超时释放和防重复挂号仍需生产级增强。
5. 前端仍有大包告警，后续可继续拆分图表和管理页依赖。
