# 009 校园快递代收管理系统巡检计划 Implementation Plan

**Goal:** 完成 `009` 校园快递代收管理系统的文档完整性、JDK 17 兼容性、构建测试、启动可用性巡检，并把结果纳入总台账与单项目检查报告；对巡检过程中发现的可直接修复问题进行闭环修复与复测。

**Architecture:** 以“Spring Boot 后端 + Vue 3 / Vite 前端”的前后端分离项目方式巡检 `009`。先核对项目总览、PRD、README、快速启动文档、账号文档、数据库初始化脚本与前端路由/请求层，再验证 Maven 构建、Node 构建、前后端启动、登录链路、鉴权行为和核心业务接口，记录文档与实现差异及遗留风险。

**Tech Stack:** Spring Boot 3.2.0、MyBatis-Plus 3.5.5、MySQL 8、java-jwt 4.4.0、Spring Validation、POI 5.2.5、Vue 3、Vite 5、Element Plus、Pinia、Axios、ECharts、Maven 3.9、JDK 17、Node.js

---

### Task 1: 建立 009 巡检文档

**Files:**
- Create: `docs/checks/009-campus-express-system.md`
- Create: `docs/plans/2026-04-04-009-express-system-check-plan.md`
- Modify: `docs/project-check-tracker.md`

**Step 1:** 在总台账中新增 `009` 项并更新统计值为“检查中”

**Step 2:** 创建 `009` 单项目检查报告，写明后端、前端目录和默认端口

**Step 3:** 写入数据库、初始化脚本、默认账号文档差异等前置信息

### Task 2: 核对文档与源码一致性

**Files:**
- Modify: `docs/checks/009-campus-express-system.md`
- Read: `009-backend/PRD/PRD.md`
- Read: `009-backend/README.md`
- Read: `009-backend/QUICK_START.md`
- Read: `009-backend/ACCOUNTS.md`
- Read: `009-backend/src/main/**`
- Read: `009-frontend/src/**`

**Step 1:** 对照 PRD、README、账号文档与源码，确认管理员、快递站点、学生、快递、统计等模块是否齐全

**Step 2:** 核对后端拦截器白名单、登录接口、用户信息接口与前端路由守卫/请求封装是否匹配

**Step 3:** 将缺失项、疑似未完成项和文档空缺写回检查报告

### Task 3: 验证 JDK 17 与构建链路

**Files:**
- Modify: `docs/checks/009-campus-express-system.md`
- Read: `009-backend/pom.xml`
- Read: `009-backend/src/main/resources/application.yml`
- Read: `009-backend/src/main/resources/sql/express_system.sql`
- Read: `009-frontend/package.json`
- Read: `009-frontend/vite.config.js`

**Step 1:** 检查后端编译参数、数据库/JWT 依赖是否匹配 JDK 17

**Step 2:** 检查数据库配置、默认端口、代理配置和初始化脚本是否完整

**Step 3:** 记录自动化测试缺口、密码存储方式和鉴权设计风险

### Task 4: 执行构建与启动验证

**Files:**
- Modify: `docs/checks/009-campus-express-system.md`
- Modify: `docs/project-check-tracker.md`
- Read/Modify: `009-backend/**`
- Read/Modify: `009-frontend/**`

**Step 1:** 运行后端测试命令与前端构建命令，确认基础构建链路可用

**Step 2:** 启动前后端，抽测登录接口、用户信息接口、受保护接口和前端首页

**Step 3:** 结合初始化 SQL 与测试账号，确认基础链路是否可用

### Task 5: 修复与结果回填

**Files:**
- Modify: `docs/checks/009-campus-express-system.md`
- Modify: `docs/project-check-tracker.md`
- Modify: `009-backend/**`
- Modify: `009-frontend/**`

**Step 1:** 对构建失败、启动失败、鉴权错误、文档与实现不一致等问题做根因分析

**Step 2:** 对可直接闭环的问题进行最小化修复，并完成复测

**Step 3:** 更新最终结论、问题清单、修复记录和整体统计
