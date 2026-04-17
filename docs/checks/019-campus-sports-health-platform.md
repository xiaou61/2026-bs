# 019 校园运动健康管理平台检查报告

## 1. 基本信息

- 项目编号：`019`
- 项目名称：校园运动健康管理平台
- 检查日期：`2026-04-06`
- 项目目录：
  - 后端：`019-backend`
  - 前端：`019-frontend`
- 主要资料：
  - 项目总览：`readme_simple.md`
  - 后端 PRD：`019-backend/PRD/PRD.md`
  - 账号文档：`019-backend/ACCOUNTS.md`
  - 后端配置：`019-backend/pom.xml`
  - 应用配置：`019-backend/src/main/resources/application.yml`
  - 初始化脚本：`019-backend/src/main/resources/schema.sql`
  - 前端说明：`019-frontend/README.md`
  - 前端依赖：`019-frontend/package.json`

## 2. 项目形态结论

- 项目为前后端分离形态：
  - 后端：Spring Boot 3.2.0 + MyBatis-Plus 3.5.5 + MySQL + JWT
  - 前端：Vue 3 + Vite 4 + Vue Router + Pinia + Element Plus + ECharts
- JDK 口径与源码一致，后端 `pom.xml` 已明确为 JDK `17`
- 数据库口径与配置一致：
  - DB：`campus_sport`
  - 用户名：`root`
  - 密码：`1234`
  - 端口：`3306`

## 3. PRD 与源码核对结论

### 3.1 已落地模块

- 用户注册登录、个人资料
- 运动打卡、运动记录列表、运动统计
- 健身计划创建、列表、进度更新、删除
- 约球活动创建、列表、参加、取消
- 健康档案记录、趋势图展示
- 场馆列表、场馆预约、我的预约、签到、取消
- 积分排行榜

### 3.2 本轮补齐项

- 补齐默认账号真实 BCrypt 哈希，修复账号文档与初始化 SQL 冲突
- 补齐排行榜接口：
  - `GET /api/rank/steps`
  - `GET /api/rank/duration`
- 补齐场馆排期接口：
  - `GET /api/venue/{id}/schedule`

## 4. 已修复问题

1. 修复 `schema.sql` 默认账号密码为占位值的问题，改为可直接登录的真实 BCrypt 哈希。
2. 修复 `PasswordTest` 只是打印结果的问题，改为真实断言测试，并新增规则工具测试。
3. 修复健康档案 BMI 公式错误的问题，改为使用用户身高计算，不再把历史体重误当身高。
4. 修复运动打卡只记录 `pointsEarned` 但不累计用户总积分的问题，排行榜和个人积分现在会同步变化。
5. 修复健身计划更新进度不累计积分的问题，按 PRD 规则补上每完成一天加 `3` 分。
6. 修复约球活动创建时未写入发起人参与记录的问题，避免创建人重复参加导致人数失真。
7. 修复约球活动参加 / 取消时未同步积分的问题，加入加 `5` 分，取消会回退对应积分。
8. 修复场馆预约创建后状态一直为 `pending`，导致前端无法签到的问题，改为校验通过后直接进入 `confirmed`。
9. 修复场馆预约缺少时间先后、营业时段、冲突时段校验的问题，避免无效预约和重复占用。
10. 修复首页“总积分”卡片实际绑定总步数的问题，改为展示后端返回的真实总积分。

## 5. 测试、启动与抽测结果

### 5.1 构建与测试

- 后端环境：
  - `mvn -version` 通过
  - JDK：`17`
- 后端测试：
  - 执行命令：`mvn test -DskipTests=false`
  - 结果：通过
  - 本轮通过测试数：`8`
- 前端构建：
  - 执行命令：`npm run build`
  - 结果：通过
  - 备注：存在 Vite 大包告警，但不影响构建与启动

### 5.2 启动验证

- 后端启动：
  - 执行命令：`mvn spring-boot:run`
  - 结果：通过
  - 实际日志：`2026-04-06 15:30:50` 启动成功，监听 `8080`
- 前端启动：
  - 执行命令：`npm run dev -- --host 127.0.0.1 --port 5173`
  - 结果：通过
  - 实际日志：Vite 就绪，访问地址 `http://127.0.0.1:5173/`

### 5.3 核心接口抽测

- 默认账号登录：
  - 接口：`POST /api/auth/login`
  - 账号：`admin / admin123`
  - 结果：成功返回 token
- 运动打卡：
  - 接口：`POST /api/sport/record`
  - 抽测数据：跑步 `5.2km / 35min`
  - 结果：成功创建，`/api/sport/stats` 返回 `totalRecords = 1`、`totalPoints = 55`
- 健身计划：
  - 接口：`POST /api/plan/create`、`PUT /api/plan/{id}/progress`
  - 结果：进度更新到 `2` 天后，用户总积分提升到 `61`
- 健康档案：
  - 接口：`POST /api/health/record`
  - 结果：记录成功，BMI 实际返回 `22.86`
- 场馆预约：
  - 接口：`POST /api/booking/create`、`GET /api/booking/my`、`PUT /api/booking/{id}/checkin`
  - 结果：创建后状态为 `confirmed`，签到后状态变为 `checked_in`
- 约球活动：
  - 接口：`POST /api/activity/create`、`POST /api/activity/{id}/join`
  - 结果：创建人再次参加会被拒绝，返回 `加入失败`，活动人数保持 `1`

## 6. 当前结论

- `019` 已完成本轮巡检、修复、复测和文档回填。
- 当前项目在 JDK `17` 下可通过后端测试、前端构建，并可成功启动。
- 默认账号、积分、BMI、活动人数、预约状态这几条关键业务链路已形成可复测闭环。

## 7. 剩余风险

1. 排行榜页面上的日榜 / 周榜 / 月榜切换当前仍未区分真实统计周期，前端按钮与 PRD 的周期榜单能力还有差距。
2. 前端构建存在大包告警，后续可继续拆分 ECharts 和页面 chunk。
3. 本轮以接口抽测和启动验证为主，尚未补浏览器端的完整自动化 E2E。
