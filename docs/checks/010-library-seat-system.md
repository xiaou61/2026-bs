# 010 图书馆座位预约系统检查报告

## 1. 基本信息

- 项目编号：`010`
- 项目名称：图书馆座位预约系统
- 项目目录：
  - 后端：`010-backend`
- 项目来源：
  - 项目总览：`readme_simple.md`
  - 后端 PRD：`010-backend/PRD/PRD.md`
  - 测试账号：`010-backend/ACCOUNTS.md`
- 当前检查日期：`2026-04-04`

## 2. 检查目标

- 核对 PRD、账号文档与源码实现是否基本一致
- 检查项目是否满足 JDK 17 运行要求
- 验证后端构建、启动与核心页面/接口可用性
- 对发现的可直接修复问题进行闭环修复并记录最终结论

## 3. 现状速览

### 3.1 文档与目录

- 项目当前仅包含 `010-backend`，未发现独立 `010-frontend`
- 项目实际为 Spring Boot + 静态页面一体化部署，学生端与后台页面位于 `src/main/resources/static/`
- 当前页面入口包括：
  - 学生端：`login.html`、`index.html`、`booking.html`、`my-bookings.html`、`profile.html`
  - 管理端：`admin/index.html`、`admin/bookings.html`、`admin/users.html`、`admin/violations.html`
- 当前未发现独立 `README.md` 与 `QUICK_START.md`

### 3.2 环境前置

- 后端默认端口：`8010`
- 后端数据库：`library_seat`
- 当前 `application.yml` 默认数据库连接：`root / 1234`
- 初始化脚本：`010-backend/src/main/resources/sql/library_seat.sql`
- 初始化 SQL 默认账号：
  - 管理员：`admin / 123456`
  - 学生：`20240001` ~ `20240005` / `123456`

## 4. 文档完整性检查

| 检查项 | 结果 | 说明 |
| --- | --- | --- |
| 项目概述 | 基本通过 | PRD 与源码均对应图书馆座位预约系统，但项目资料缺少 `README.md`、`QUICK_START.md` |
| 功能模块描述 | 基本通过 | 用户登录、座位列表、预约、签到签退、通知、违约、信用分、管理员统计等模块在控制器、服务和静态页面中均可对应 |
| 账号说明 | 已修复 | PRD 原写管理员密码 `admin123`，已根据 `ACCOUNTS.md`、初始化 SQL 与登录实测修正为 `123456` |
| 接口路径说明 | 已修复 | PRD 原将楼层/区域/统计接口写成 `/api/floor/list`、`/api/area/list`、`/api/stats/overview`，已改为与源码一致的 `/api/seat/floor/list`、`/api/seat/area/list`、`/api/admin/stats/overview` |
| 启动说明 | 已修复 | PRD 原写学生端和管理端访问地址为 `8080`，已修正为实际运行端口 `8010` |
| 文档与源码一致性 | 基本通过 | 端口、账号、核心接口和静态页面现已基本对齐，剩余不足主要是缺少 README/快速启动文档和自动化测试说明 |

## 5. JDK 17 与依赖检查

| 检查项 | 结果 | 说明 |
| --- | --- | --- |
| `pom.xml` Java 版本 | 通过 | `java.version` 为 `17` |
| Spring Boot 版本 | 通过 | 使用 `3.2.0` |
| MyBatis-Plus 依赖 | 通过 | 使用 `mybatis-plus-spring-boot3-starter 3.5.5` |
| MySQL 驱动坐标 | 已修复 | 已从旧坐标 `mysql:mysql-connector-java` 调整为 `com.mysql:mysql-connector-j`，`mvn test` 不再出现 relocation 告警 |
| JWT / Thymeleaf / Hutool 依赖 | 通过 | `java-jwt 4.4.0`、Thymeleaf、Hutool 等依赖在 JDK 17 下可正常构建 |
| 服务端管理员权限校验 | 已修复 | 原仅校验 token、不校验 `/api/admin/**` 角色，现已在拦截器中限制仅 `ADMIN` 可访问 |
| 预约返回字段完整性 | 已修复 | 原预约创建/列表/详情等返回中缺少 `seatNo`，导致前端页面展示断链，现已补齐 |
| 未登录返回编码 | 已修复 | 原 `token` 为空时返回 `401` 但中文消息乱码，现已统一走 UTF-8 JSON 输出 |
| 自动化测试覆盖 | 风险 | 未发现 `src/test`，当前 `mvn test` 主要验证编译链路 |
| 密码存储方式 | 风险 | 默认账号密码仍使用 MD5 存储与校验，安全性弱于现代密码散列方案 |

## 6. 实测记录

### 6.1 环境与构建

| 项目 | 命令 | 结果 | 备注 |
| --- | --- | --- | --- |
| Maven / JDK | `mvn -version` | 通过 | Maven 绑定 JDK 17 |
| 后端测试 | `mvn test -DskipTests=false` | 通过 | `BUILD SUCCESS`，未发现 MySQL relocation 告警，但无实际测试用例执行 |
| 后端重启 | `mvn spring-boot:run` | 通过 | 成功监听 `8010`，当前为一体化启动模式 |

### 6.2 启动与接口抽测

| 项目 | 命令/接口 | 结果 | 备注 |
| --- | --- | --- | --- |
| 学生端页面 | `GET /login.html`、`GET /index.html` | 通过 | 页面均返回 HTTP `200` |
| 管理端页面 | `GET /admin/index.html` | 通过 | 返回 HTTP `200` |
| 学生登录 | `POST /api/user/login` | 通过 | `20240001 / 123456` 返回 `200`，角色为 `USER` |
| 管理员登录 | `POST /api/user/login` | 通过 | `admin / 123456` 返回 `200`，角色为 `ADMIN` |
| 错误密码验证 | `POST /api/user/login` | 通过 | `admin / admin123` 返回业务错误，证明 PRD 旧密码无效 |
| 用户信息接口 | `GET /api/user/info` | 通过 | 学生 token 返回当前用户信息 |
| 座位列表接口 | `GET /api/seat/list` | 通过 | 返回 `40` 个座位 |
| 时间段接口 | `GET /api/timeslot/list` | 通过 | 返回 `4` 个时间段 |
| 通知列表接口 | `GET /api/notification/list` | 通过 | 学生 token 返回 `2` 条通知 |
| 管理统计接口 | `GET /api/admin/stats/overview` | 通过 | 管理员 token 返回 `seatCount`、`bookingCount`、`userCount`、`violationCount` |
| 未登录鉴权 | `GET /api/user/info` | 通过 | 未登录返回 HTTP `401`，消息为 `未登录` |

### 6.3 修复项回归

| 项目 | 接口 | 结果 | 备注 |
| --- | --- | --- | --- |
| 管理员权限回归 | 学生 token 访问 `GET /api/admin/user/list` | 通过 | 返回 HTTP `403`，消息为 `无权访问管理员接口` |
| 预约列表展示回归 | `GET /api/booking/my-list` | 通过 | 学生历史预约已带回 `seatNo`，实测返回 `A-001`、`A-002` |
| 预约创建返回回归 | `POST /api/booking/create` | 通过 | 使用 `20240005 / 123456` 创建测试预约成功，返回 `seatNo=A-003` |
| 预约取消回归 | `PUT /api/booking/{id}/cancel` | 通过 | 对新建测试预约执行取消，返回 `200`，已回收测试数据 |
| 未登录编码回归 | `GET /api/user/info`（无 token） | 通过 | 返回体中文不再乱码 |
| 依赖告警回归 | `mvn test -DskipTests=false` | 通过 | 不再出现 MySQL 驱动迁移告警 |

## 7. 当前问题清单

| 编号 | 问题 | 严重度 | 状态 |
| --- | --- | --- | --- |
| 010-1 | 项目缺少 `README.md` 与 `QUICK_START.md`，资料完整性不足 | 中 | 未修复 |
| 010-2 | 后端未发现 `src/test` 自动化测试目录，当前主要依赖构建与启动抽测 | 中 | 未修复 |
| 010-3 | 用户密码仍使用 MD5 存储与校验 | 中 | 未修复 |
| 010-4 | 服务端原仅校验 token，不校验 `/api/admin/**` 角色，学生可越权访问管理员接口 | 高 | 已修复 |
| 010-5 | 预约创建/列表/详情返回缺少 `seatNo`，导致前端页面无法正确展示座位编号 | 高 | 已修复 |
| 010-6 | PRD 中管理员密码、端口、楼层/区域接口、统计接口与实际实现不一致 | 中 | 已修复 |
| 010-7 | MySQL 驱动使用旧坐标，构建阶段出现 relocation 告警 | 低 | 已修复 |
| 010-8 | 未登录 `401` 分支未设置 UTF-8 JSON 输出，中文消息会乱码 | 低 | 已修复 |

## 8. 修复记录

- 已修正文档不一致：
  - `010-backend/PRD/PRD.md`
  - 将楼层、区域、统计接口路径修正为实际实现
  - 将默认访问端口从 `8080` 修正为 `8010`
  - 将管理员默认密码修正为 `123456`
- 已修复 MySQL 依赖坐标：
  - `010-backend/pom.xml`
  - 将 `mysql:mysql-connector-java` 调整为 `com.mysql:mysql-connector-j`
- 已修复服务端权限与未登录响应：
  - `010-backend/src/main/java/com/xiaou/interceptor/AuthInterceptor.java`
  - 对 `/api/admin/**` 增加 `ADMIN` 角色限制
  - 统一使用 UTF-8 JSON 输出 `401/403` 响应，修复未登录消息乱码
- 已修复预约返回字段缺失：
  - `010-backend/src/main/java/com/xiaou/entity/Booking.java`
  - `010-backend/src/main/java/com/xiaou/service/BookingService.java`
  - 为预约对象增加非表字段 `seatNo`
  - 在创建预约、预约列表、预约详情、当前预约、管理员预约列表等返回中补齐座位编号
- 修复完成后已重新执行后端 `mvn test`、重启服务，并完成登录、页面访问、权限、预约创建/取消、预约列表、统计接口等回归验证。

## 9. 当前结论

- `010` 已完成首轮巡检、问题修复与复测。
- 项目在当前环境下可通过 JDK 17 构建测试，后端 `mvn test` 通过，服务可通过 `mvn spring-boot:run` 正常启动并监听 `8010`。
- 学生端页面、管理端页面、学生/管理员登录、用户信息、座位列表、时间段、通知、管理员统计、未登录 `401`、管理员权限控制、预约创建/取消与预约列表展示均已验证通过。
- 当前可判定 `010` 在现有环境下“可通过测试并启动”。
- 仍需后续继续补强的方向主要是：补齐 `README.md` / `QUICK_START.md`、补充自动化测试、替换 MD5 密码方案。
