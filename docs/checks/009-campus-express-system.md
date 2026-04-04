# 009 校园快递代收管理系统检查报告

## 1. 基本信息

- 项目编号：`009`
- 项目名称：校园快递代收管理系统
- 项目目录：
  - 后端：`009-backend`
  - 前端：`009-frontend`
- 项目来源：
  - 项目总览：`readme_simple.md`
  - 后端 PRD：`009-backend/PRD/PRD.md`
  - 后端说明：`009-backend/README.md`
  - 快速启动：`009-backend/QUICK_START.md`
  - 测试账号：`009-backend/ACCOUNTS.md`
  - 前端说明：`009-frontend/README.md`
- 当前检查日期：`2026-04-04`

## 2. 检查目标

- 核对 PRD、README、快速启动文档、账号文档与源码实现是否基本一致
- 检查项目是否满足 JDK 17 运行要求
- 验证前后端构建、启动与关键链路可用性
- 对发现的可直接修复问题进行闭环修复并记录最终结论

## 3. 现状速览

### 3.1 文档与目录

- 项目包含 `009-backend` 与 `009-frontend`，为标准前后端分离结构
- 后端已提供 PRD、README、快速启动、功能说明、账号说明等文档
- 前端包含 Vue 3 + Vite 工程，具备独立 `package.json` 与开发服务器配置
- 后端源码已覆盖认证、快递、代收点、快递公司、通知、日志、统计、系统配置、超期记录等核心模块
- 前端已覆盖学生端、快递员工作台、管理后台及登录注册流程

### 3.2 环境前置

- 后端默认端口：`8009`
- 前端默认端口：`5009`
- 后端数据库：`express_system`
- 当前 `application.yml` 默认数据库连接：`root / 1234`
- 前端代理目标：`http://localhost:8009`
- 数据初始化脚本：`009-backend/src/main/resources/sql/express_system.sql`
- 初始化 SQL 默认账号：
  - 管理员：`admin / 123456`
  - 代收点管理员：`station1 / 123456`
  - 快递员：`courier1 / 123456`
  - 学生：`student1 / 123456`

## 4. 文档完整性检查

| 检查项 | 结果 | 说明 |
| --- | --- | --- |
| 项目概述 | 通过 | PRD、README、QUICK_START 与前后端目录结构基本一致，均描述为校园快递代收管理系统 |
| 功能模块描述 | 基本通过 | 认证、快递入库、取件核销、批量导入、通知、统计、用户管理、代收点/公司管理、系统配置等模块均有实现 |
| 账号说明 | 已修复 | 初始化 SQL、`ACCOUNTS.md`、登录实测均显示管理员密码为 `123456`，已修正 PRD 中错误的 `admin123` |
| 启动说明 | 已修复 | `README.md`、`QUICK_START.md` 原先把数据库默认密码写成 `root`，已改为与 `application.yml` 一致的 `1234` |
| 文档与源码一致性 | 基本通过 | 前后端端口、代理配置、默认账号、初始化数据、核心接口已基本对齐，仍存在自动化测试缺失与 MD5 密码风险 |

## 5. JDK 17 与依赖检查

| 检查项 | 结果 | 说明 |
| --- | --- | --- |
| `pom.xml` Java 版本 | 通过 | `maven.compiler.source`、`maven.compiler.target` 均为 `17` |
| Spring Boot 版本 | 通过 | 使用 `3.2.0` |
| MyBatis-Plus 依赖 | 通过 | 使用 `mybatis-plus-spring-boot3-starter 3.5.5` |
| MySQL 驱动坐标 | 通过 | 使用 `com.mysql:mysql-connector-j` |
| JWT / Validation / POI | 通过 | 已引入 `java-jwt 4.4.0`、`spring-boot-starter-validation`、`poi-ooxml 5.2.5` |
| 前端技术栈 | 通过 | Vue 3、Vite 5、Element Plus、Pinia、Axios、ECharts 可正常安装与构建 |
| 分页能力 | 已修复 | 原缺少 MyBatis-Plus 分页拦截器，导致分页接口 `total/pages` 为 `0`，现已补充配置类修复 |
| 服务端权限边界 | 已修复 | 原仅校验 token 不校验角色，学生 token 可越权访问管理接口，现已在拦截器中补充角色校验 |
| 自动化测试覆盖 | 风险 | 后端未发现 `src/test`，当前 `mvn test` 主要验证编译与启动前置链路 |
| 密码存储方式 | 风险 | 用户密码仍使用 MD5 存储与校验，安全性弱于现代密码散列方案 |

## 6. 实测记录

### 6.1 环境与构建

| 项目 | 命令 | 结果 | 备注 |
| --- | --- | --- | --- |
| Maven / JDK | `mvn -version` | 通过 | Maven 绑定 JDK 17 |
| Node.js | `node -v` / `npm -v` | 通过 | Node `v22.17.0`、npm `10.9.2` |
| 后端测试 | `mvn test -DskipTests=false` | 通过 | `BUILD SUCCESS`，但无实际测试用例执行 |
| 前端依赖安装 | `npm install` | 通过 | 依赖安装成功 |
| 前端构建 | `npm run build` | 通过 | 构建成功，存在大包体积告警 |

### 6.2 启动与接口抽测

| 项目 | 命令/接口 | 结果 | 备注 |
| --- | --- | --- | --- |
| 后端启动 | `mvn spring-boot:run` | 通过 | 成功监听 `8009` |
| 前端启动 | `npm run dev -- --host 127.0.0.1` | 通过 | 成功监听 `5009` |
| 管理员登录 | `POST /api/auth/login` | 通过 | `admin / 123456` 返回 `200`，角色为 `ADMIN` |
| 代收点管理员登录 | `POST /api/auth/login` | 通过 | `station1 / 123456` 返回 `200`，角色为 `STATION_ADMIN` |
| 学生登录 | `POST /api/auth/login` | 通过 | `student1 / 123456` 返回 `200`，角色为 `STUDENT` |
| 错误密码验证 | `POST /api/auth/login` | 通过 | `admin / admin123` 返回业务错误，证明 PRD 旧密码无效 |
| 用户信息接口 | `GET /api/auth/info` | 通过 | 携带 token 返回当前用户信息 |
| 未登录鉴权 | `GET /api/express/my-packages` | 通过 | 未登录返回 HTTP `401` |
| 前端首页 | `GET http://127.0.0.1:5009/` | 通过 | 返回 `200` |
| 前端代理登录 | `POST http://127.0.0.1:5009/api/auth/login` | 通过 | Vite 代理联通后端成功 |
| 公开基础数据 | `GET /api/company/all`、`GET /api/station/all` | 通过 | 无 token 可访问，分别返回 `10` 家快递公司与 `3` 个代收点 |
| 模板下载 | `GET /api/express/download-template` | 通过 | 携带管理员 token 返回 `xlsx` 文件 |
| 学生个人快递 | `GET /api/express/my-packages` | 通过 | 学生 token 可正常访问并返回自己的包裹 |

### 6.3 修复项回归

| 项目 | 接口 | 结果 | 备注 |
| --- | --- | --- | --- |
| 分页回归 | `GET /api/user/list?page=1&size=5` | 通过 | `total=7`、`pages=2`，分页元数据恢复正常 |
| 快递分页回归 | `GET /api/express/list?page=1&size=5` | 通过 | `total=4`、`pages=1` |
| 权限回归 | 学生 token 访问 `/api/user/list`、`/api/stats/overview` | 通过 | 均返回 HTTP `403` |
| 学生改密权限 | `PUT /api/user/5/password`、`PUT /api/user/6/password` | 通过 | 修改自己密码返回 `200`，修改他人密码返回 `403` |

## 7. 当前问题清单

| 编号 | 问题 | 严重度 | 状态 |
| --- | --- | --- | --- |
| 009-1 | 后端未发现 `src/test` 自动化测试目录，当前主要依赖构建与启动抽测验证 | 中 | 未修复 |
| 009-2 | 用户密码仍使用 MD5 存储与校验 | 中 | 未修复 |
| 009-3 | 前端构建产物存在较大 chunk 告警，后续可考虑按页面或模块继续拆包 | 低 | 未修复 |
| 009-4 | PRD 中管理员默认密码写成 `admin123`，与初始化 SQL 和实测不一致 | 中 | 已修复 |
| 009-5 | `README.md`、`QUICK_START.md` 中数据库默认密码写成 `root`，与实际配置不一致 | 中 | 已修复 |
| 009-6 | 批量导入快递时通知在保存快递前写库，导致通知 `expressId` 为空 | 中 | 已修复 |
| 009-7 | 后端未启用 MyBatis-Plus 分页拦截器，导致分页接口 `total/pages` 异常 | 高 | 已修复 |
| 009-8 | 后端仅校验 token、不校验角色，学生 token 可越权访问管理接口 | 高 | 已修复 |

## 8. 修复记录

- 已修正文档不一致：
  - `009-backend/PRD/PRD.md` 中管理员默认密码改为 `123456`
  - `009-backend/README.md`、`009-backend/QUICK_START.md` 中数据库默认密码改为 `1234`
- 已修复批量导入通知关联问题：
  - `009-backend/src/main/java/com/xiaou/controller/ExpressController.java`
  - 调整批量导入逻辑为先保存快递，再根据生成的 `expressId` 写入通知，同时补齐代收点名称
- 已修复分页能力缺失：
  - 新增 `009-backend/src/main/java/com/xiaou/config/MybatisPlusConfig.java`
  - 注入 `MybatisPlusInterceptor` + `PaginationInnerInterceptor(DbType.MYSQL)`，恢复分页总数与页数计算
- 已修复服务端权限缺失：
  - 更新 `009-backend/src/main/java/com/xiaou/interceptor/JwtInterceptor.java`
  - 在验证 token 后，根据接口路径限制 `ADMIN` / `STATION_ADMIN` / `COURIER` 的访问边界
  - 对 `/api/user/{id}/password` 增加“本人或管理角色”校验，避免学生越权修改他人密码
- 修复完成后已重新执行后端 `mvn test`、重启应用并完成分页、权限、登录、前端代理等回归验证。

## 9. 当前结论

- `009` 已完成首轮巡检、问题修复与复测。
- 项目前后端在当前环境下均可正常构建，后端通过 `mvn test`，前端通过 `npm run build`，并已成功分别在 `8009` 与 `5009` 启动。
- 管理员、代收点管理员、学生登录链路、用户信息接口、未登录 `401`、公开基础数据接口、模板下载、前端首页与 Vite 代理链路均已验证通过。
- 当前可判定 `009` 在现有环境下“可通过测试并启动”。
- 仍需后续持续改进的方向主要是：补充自动化测试、替换 MD5 密码方案、优化前端大包体积。
