# 008 智能菜谱推荐系统检查报告

## 1. 基本信息

- 项目编号：`008`
- 项目名称：智能菜谱推荐系统
- 项目目录：
  - 后端：`008-backend`
- 项目来源：
  - 项目总览：`readme_simple.md`
  - 后端 PRD：`008-backend/PRD/PRD.md`
  - 后端说明：`008-backend/README.md`
  - 快速启动：`008-backend/QUICK_START.md`
  - 测试账号：`008-backend/ACCOUNTS.md`
- 当前检查日期：`2026-04-04`

## 2. 检查目标

- 核对 PRD、README、快速启动文档、账号文档与源码实现是否基本一致
- 检查是否满足 JDK 17 运行要求
- 验证后端构建、启动与静态页面/接口基础链路
- 对发现的可直接修复问题进行闭环修复并记录最终结论

## 3. 现状速览

### 3.1 文档与目录

- 项目当前仅包含 `008-backend`，未发现独立 `008-frontend`
- README 和 QUICK_START 明确说明该项目为 Spring Boot 一体化部署项目，前端位于 `src/main/resources/static`
- 源码已包含用户、食材、菜谱、推荐、营养、购物清单、打卡、管理端模块
- 静态页面已包含用户端和管理员端 HTML 页面及公共 JS/CSS 资源

### 3.2 环境前置

- Maven/JDK 公共环境已确认可使用 JDK 17
- MySQL `3306` 端口当前已监听
- 后端默认端口：`8008`
- 静态入口页：
  - 登录页：`/index.html`
  - 用户端：`/pages/user/index.html`
  - 管理端：`/pages/admin/index.html`
- 后端默认依赖本地 MySQL：
  - 数据库：`smart_recipe`
  - 用户名：`root`
  - 密码：`1234`
- 数据初始化脚本：`008-backend/src/main/resources/sql/schema.sql`

## 4. 文档完整性检查

| 检查项 | 结果 | 说明 |
| --- | --- | --- |
| 项目概述 | 通过 | PRD、README 与 QUICK_START 对一体化部署和核心目标描述基本一致 |
| 功能模块描述 | 基本通过 | 用户、食材、菜谱、推荐、营养、购物清单、打卡、管理端模块均有实现 |
| 账号说明 | 通过 | `ACCOUNTS.md` 与初始化 SQL、登录实测结果一致 |
| 启动说明 | 通过 | 数据库脚本、配置方式、端口和启动命令可复现 |
| 文档与源码一致性 | 基本通过 | 一体化结构、静态入口页和核心接口基本一致，但仍存在测试缺口与 MD5 密码风险 |

## 5. JDK 17 与依赖检查

| 检查项 | 结果 | 说明 |
| --- | --- | --- |
| `pom.xml` Java 版本 | 通过 | `maven.compiler.source`、`maven.compiler.target` 均为 `17` |
| Spring Boot 版本 | 通过 | 使用 `3.2.0` |
| MyBatis-Plus 依赖 | 通过 | 使用 `mybatis-plus-spring-boot3-starter 3.5.5` |
| JWT 依赖 | 通过 | 已引入 `java-jwt 4.4.0`，拦截器和工具类已接入 |
| 静态资源结构 | 通过 | 静态页面位于 `static/`，无需独立前端构建 |
| 数据库初始化脚本 | 通过 | `schema.sql` 提供建库建表、用户、食材、菜谱、步骤等初始化数据 |
| MySQL 驱动坐标 | 已修复 | 已将过时的 `mysql:mysql-connector-java` 坐标改为 `com.mysql:mysql-connector-j` |
| 密码存储方式 | 风险 | 登录与注册使用 MD5 哈希处理密码，安全性弱于更现代的密码散列方案 |

## 6. 实测记录

### 6.1 后端

| 项目 | 命令 | 结果 | 备注 |
| --- | --- | --- | --- |
| 环境检查 | `mvn -version` | 通过 | Maven 绑定到 JDK 17 |
| 测试/构建 | `mvn test -DskipTests=false` | 通过 | 成功编译并完成 Surefire 阶段，但仓库内暂无 `src/test` 自动化测试目录 |
| 启动验证 | `mvn spring-boot:run` | 通过 | 应用成功启动，监听 `8008`，日志显示 `Started SmartRecipeApplication` |
| 登录接口验证 | `POST /api/user/login` | 通过 | `admin/123456`、`user1/123456` 登录成功返回 `200` |
| 用户信息验证 | `GET /api/user/info` | 通过 | 携带合法 Bearer Token 返回 `200` |
| 公开菜谱详情验证 | `GET /api/recipe/1` | 通过 | 未登录状态可正常返回 `200` |
| 公开食材详情验证 | `GET /api/ingredient/1` | 通过 | 未登录状态可正常返回 `200` |
| 受保护接口未登录验证 | `GET /api/recommend/today` | 通过 | 修复后返回 HTTP `401` 与 JSON `code=401` |
| 页面验证 | `GET /`、`GET /index.html`、`GET /pages/user/index.html`、`GET /pages/admin/index.html` | 通过 | 静态入口页和用户/管理端页面均返回 `200` |

## 7. 当前问题清单

| 编号 | 问题 | 严重度 | 状态 |
| --- | --- | --- | --- |
| 008-1 | 项目未发现自动化测试目录，当前主要依赖构建验证与启动抽测 | 中 | 未修复 |
| 008-2 | 所有密码使用 MD5 存储与校验，安全性弱于更现代的密码散列方案 | 中 | 未修复 |
| 008-3 | 鉴权拦截器曾在未登录时抛出业务异常，导致受保护接口返回 HTTP `200` + `code=500`，不利于前端统一处理登录失效 | 中 | 已修复 |
| 008-4 | `pom.xml` 曾使用已迁移的 MySQL 驱动坐标 `mysql:mysql-connector-java`，构建时出现 relocation 告警 | 低 | 已修复 |

## 8. 修复记录

- 已完成后端 `mvn test`、服务启动、静态登录页访问、用户端/管理端页面访问、登录接口、用户信息接口和公开详情接口抽测。
- 已将 `pom.xml` 中的 MySQL 驱动坐标从 `mysql:mysql-connector-java` 更新为 `com.mysql:mysql-connector-j`，消除 Maven relocation 告警。
- 已修复未登录访问受保护接口时返回 HTTP `200` + `code=500` 的问题：在 `AuthInterceptor` 中改为直接返回 HTTP `401` 和标准 JSON 响应，复测 `/api/recommend/today` 已返回 `401`。
- 巡检结束后将清理本次验证产生的日志、进程与构建产物，避免影响后续项目检查。

## 9. 当前结论

- `008` 已完成首轮巡检与可直接问题修复。
- 项目后端面向 JDK 17 的依赖配置正常，后端已通过 `mvn test`，应用可在默认端口 `8008` 正常启动。
- 静态登录页、用户端页面、管理端页面、登录接口、用户信息接口、公开详情接口均已验证通过。
- 当前可判定 `008` 在现有环境下“可通过测试并启动”。
- 但从“项目完整性”角度看，仍存在待改进项，主要是自动化测试缺失以及密码仍使用 MD5 存储。
