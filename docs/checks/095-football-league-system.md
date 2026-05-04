# 095 足球联赛管理系统检查报告

## 1. 检查结论

- 项目编号：`095`
- 项目名称：`SpringBoot 足球联赛管理系统设计与实现`
- 检查日期：`2026-05-03`
- 当前状态：`已完成`
- 综合结论：`本轮确认 095 已具备 JDK 17、默认 H2 自举、MySQL root / 1234 正式与 mysql-verify 验证入口、运行态 token 存储、Bearer 请求头兼容、登录 password 脱敏、真实 HTTP 401/403、足球联赛公开查询、角色权限边界、前端代理和构建能力。后端自动化测试、默认 H2 真实启动、MySQL 临时库真实 HTTP 验收和前端 3095 代理联调均已通过。`

## 2. 项目形态

- 后端目录：`095-backend`
- 前端目录：`095-frontend`
- 后端技术栈：`Spring Boot 2.7.18 + MyBatis-Plus + JWT + H2/MySQL + Redis 依赖保留`
- 前端技术栈：`Vue 3 + Vite + Element Plus + Pinia + Axios + ECharts`
- 默认后端端口：`8095`
- 前端开发端口：`3095`
- MySQL 账号密码：`root / 1234`

## 3. 本轮确认与修复

1. 后端已使用 JDK 17、MyBatis-Plus、H2 与新版 MySQL Connector/J。
2. 默认环境已切换为 H2 内存库自举，并提供 `schema-h2.sql` / `data-h2.sql`。
3. `application-mysql.yml` 保留正式 MySQL 部署入口，数据库为 `football_league_management_095`。
4. `application-mysql-verify.yml` 使用 `football_league_management_095_verify` 临时库做非破坏验证。
5. `schema-mysql.sql` / `data-mysql.sql` 可在 MySQL 验证库中重建 12 张业务表和演示数据。
6. 登录态使用运行态 token 存储，默认演示链路不再强依赖 Redis 可用性。
7. JWT 拦截器兼容 `Authorization: Bearer <token>`，登出后旧 token 失效。
8. 登录响应和用户信息接口不输出 `password` 字段。
9. 全局异常处理可返回真实 HTTP 状态，未登录为 `401`，越权为 `403`。
10. 公开接口可匿名访问赛程、资讯和积分榜。
11. 权限边界已覆盖管理员、俱乐部经理和球迷：
    - `ADMIN`：用户管理、管理员级后台能力
    - `MANAGER`：统计看板和赛事运营管理能力
    - `FAN`：公开查询、球队关注，禁止访问统计和后台管理
12. 前端 Vite 端口为 `3095`，代理目标支持 `VITE_API_TARGET` 覆盖。
13. 前端请求头统一补齐 Bearer token。
14. 新增 `FootballLeagueApplicationSmokeTest`，覆盖公开接口、登录脱敏、角色权限、关注球队和登出失效。

## 4. 验证结果

### 4.1 后端测试

- 执行命令：`095-backend/mvn.cmd test`
- 结果：`通过`
- 关键结果：`Tests run: 1, Failures: 0, Errors: 0`
- 覆盖内容：匿名用户信息 `401`、公开赛程/资讯/积分榜、三角色登录、登录脱敏、球迷统计越权、经理访问用户管理越权、经理统计看板、球迷关注球队和登出失效。

### 4.2 前端构建

- 执行命令：`095-frontend/npm.cmd run build`
- 结果：`通过`
- 备注：保留 Vite CJS API 和 chunk 体积提示，不影响产物生成。

### 4.3 H2 启动与接口抽测

- 后端启动命令：`mvn.cmd spring-boot:run -Dspring-boot.run.arguments=--server.port=18107`
- 验证端口：`18107`
- 结果：`通过`
- 关键结果：`{"anon":401,"publicMatch":200,"adminLogin":200,"managerLogin":200,"fanLogin":200,"loginHasPassword":false,"fanStats":403,"managerStats":200,"followToggle":200,"logout":200,"afterLogout":401}`

### 4.4 MySQL 启动与接口抽测

- MySQL 连接：`localhost:3306`，账号 `root / 1234`
- 准备方式：使用 Connector/J 创建 `football_league_management_095_verify`，由 `mysql-verify` profile 执行 schema/data 初始化
- 后端启动命令：`mvn.cmd spring-boot:run -Dspring-boot.run.profiles=mysql-verify -Dspring-boot.run.arguments=--server.port=18108`
- 验证库：`football_league_management_095_verify`
- 验证端口：`18108`
- 验证后处理：已删除临时库 `football_league_management_095_verify`
- 关键结果：`{"anon":401,"publicMatch":200,"publicNews":200,"standing":200,"adminLogin":200,"managerLogin":200,"fanLogin":200,"loginHasPassword":false,"adminRole":"ADMIN","fanStats":403,"managerUserList":403,"managerStats":200,"followToggle":200,"followData":true,"logout":200,"afterLogoutFollowList":401}`

### 4.5 前端代理联调

- 后端启动端口：`18108`
- 前端启动命令：`VITE_API_TARGET=http://127.0.0.1:18108 npm.cmd run dev -- --host 127.0.0.1`
- 前端端口：`3095`
- 结果：`通过`
- 关键结果：`{"homeStatus":200,"proxyLoginStatus":200,"proxyLoginHasToken":true,"proxyLoginHasPassword":false,"adminRole":"ADMIN"}`

## 5. H2 与 MySQL 口径说明

默认使用 H2 是为了保证本机直接启动和自动化巡检不破坏正式库。正式部署仍使用 `application-mysql.yml`，数据库账号密码为 `root / 1234`；本轮 MySQL 兼容性通过 `football_league_management_095_verify` 临时库完成验证。

## 6. 默认账号

- 管理员：`admin / 123456`
- 俱乐部经理：`manager / 123456`
- 球迷：`fan / 123456`

## 7. 仍有差距

1. 密码仍为明文存储，生产环境建议改为 BCrypt。
2. 当前运行态 token 为进程内存存储，演示足够，但分布式部署仍需接入 Redis 或集中式会话存储。
3. 积分榜重算、赛果并发录入和赛程冲突检测仍偏演示级。
4. 球队关注、资讯公告和统计看板已可跑通，但图片上传、消息通知和更细颗粒度运营权限仍需后续增强。
5. 前端仍有大包告警，后续可继续拆分图表和管理页依赖。
