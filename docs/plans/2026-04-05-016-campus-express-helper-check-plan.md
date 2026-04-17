# 016 校园快递代领服务平台巡检计划 Implementation Plan

**Goal:** 完成 `016` 校园快递代领服务平台的文档完整性、JDK 17 兼容性、前后端测试、启动可用性与关键业务链路巡检，并把结果纳入总台账与单项目检查报告；对巡检过程中发现的可直接修复问题进行闭环修复与复测。

**Architecture:** 按“Spring Boot 后端 + Vue 3 前端”的前后端分离结构巡检 `016`。先核对项目总览、PRD、账号文档、应用配置、初始化 SQL 和前端说明，再验证鉴权拦截器、核心控制器、服务层、前端路由、状态管理和页面接线，最后执行 Maven 测试、前端构建、应用启动与关键链路抽测。

**Tech Stack:** Spring Boot 3.2.0、MyBatis-Plus 3.5.5、MySQL 8、JWT 0.11.5、Vue 3、Vue Router 4、Pinia、Element Plus、Axios、Vite 4、Maven 3.9、JDK 17

---

### Task 1: 建立 016 巡检文档

**Files:**
- Create: `docs/checks/016-campus-express-helper.md`
- Create: `docs/plans/2026-04-05-016-campus-express-helper-check-plan.md`
- Modify: `docs/project-check-tracker.md`

**Step 1:** 在总台账中新增 `016` 项并更新统计值为“检查中”

**Step 2:** 创建 `016` 单项目检查报告，明确该项目为前后端分离项目

**Step 3:** 写入端口、数据库、默认账号和资料范围等前置信息

### Task 2: 核对文档与源码一致性

**Files:**
- Modify: `docs/checks/016-campus-express-helper.md`
- Read: `016-backend/PRD/PRD.md`
- Read: `016-backend/ACCOUNTS.md`
- Read: `016-backend/src/main/**`
- Read: `016-frontend/src/**`

**Step 1:** 对照 PRD、账号文档、前端 README 与源码，确认注册登录、发布订单、接单、凭证上传、确认收货、评价、投诉、通知、钱包和后台模块是否齐全

**Step 2:** 核对接口路径、权限拦截器、前端路由、页面脚本和 API 调用是否匹配

**Step 3:** 将缺失项、资料冲突项和实现不足项写回检查报告

### Task 3: 验证 JDK 17 与构建链路

**Files:**
- Modify: `docs/checks/016-campus-express-helper.md`
- Read: `016-backend/pom.xml`
- Read: `016-backend/src/main/resources/application.yml`
- Read: `016-backend/src/test/java/**`
- Read: `016-frontend/package.json`

**Step 1:** 检查后端编译参数、数据库/JWT/文件上传配置与 JDK 17 是否匹配

**Step 2:** 检查自动化测试现状、前端构建状态和潜在兼容性风险

**Step 3:** 记录文档与真实环境依赖差异

### Task 4: 执行测试、启动与抽测

**Files:**
- Modify: `docs/checks/016-campus-express-helper.md`
- Modify: `docs/project-check-tracker.md`
- Read/Modify: `016-backend/**`
- Read/Modify: `016-frontend/**`

**Step 1:** 运行后端测试、前端构建，确认在当前环境下可以通过

**Step 2:** 启动前后端，抽测登录、发布订单、接单、上传取件/送达凭证、确认收货、评价、钱包、通知、投诉和后台管理链路

**Step 3:** 结合初始化 SQL 与测试账号，确认关键业务链路是否可用

### Task 5: 修复与结果回填

**Files:**
- Modify: `docs/checks/016-campus-express-helper.md`
- Modify: `docs/project-check-tracker.md`
- Modify: `016-backend/**`
- Modify: `016-frontend/**`

**Step 1:** 对构建失败、启动失败、接口不一致、鉴权缺陷、页面断链等问题做根因分析

**Step 2:** 对可直接闭环的问题进行最小化修复，并完成复测

**Step 3:** 更新最终结论、问题清单、修复记录和整体统计

---

## Execution Outcome

- 已完成 Task 1 ~ Task 5
- 已修复的关键问题：
  - 后台越权与订单详情访问边界问题
  - MyBatis-Plus 分页插件缺失
  - 用户资料修改 / 密码修改后端接口缺失
  - 管理端订单处理 / 投诉处理接口缺失
  - 投诉列表、订单列表、交易列表返回字段与筛选逻辑缺失
  - 投诉列表 `/api/complaints/my` 缺失
  - 钱包消费流水与通知缺失
  - 默认密码哈希错误和 `ACCOUNTS.md` 数据库口径错误
  - 前端管理端 payload、登录态 `userType`、路由守卫与资料页调用断链
- 已验证结果：
  - `mvn test -DskipTests=false` 通过
  - `npm run build` 通过
  - 后端 `8080`、前端 `5173` 成功启动
  - 登录、注册、资料修改、密码修改、发单、接单、上传凭证、确认收货、评价、投诉、通知、后台筛选与管理员强制取消链路通过抽测
