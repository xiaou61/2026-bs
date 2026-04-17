# 012 在线协作白板与笔记系统巡检计划 Implementation Plan

**Goal:** 完成 `012` 在线协作白板与笔记系统的文档完整性、JDK 17 兼容性、构建测试、启动可用性巡检，并把结果纳入总台账与单项目检查报告；对巡检过程中发现的可直接修复问题进行闭环修复与复测。

**Architecture:** 以“Spring Boot 后端 + Thymeleaf 一体化页面”的思路巡检 `012`。先核对项目总览、PRD、账号文档、Thymeleaf 说明、应用配置和初始化 SQL，再验证 Maven 构建、服务启动、登录链路、工作台、文档管理、团队、模板和管理员能力实现情况，记录文档与实现的差异以及遗留风险。

**Tech Stack:** Spring Boot 3.2.0、MyBatis-Plus 3.5.5、MySQL 8、Redis、JWT 0.12.5、Thymeleaf、Bootstrap 5、jQuery、Axios、WebSocket、Maven 3.9、JDK 17

---

### Task 1: 建立 012 巡检文档

**Files:**
- Create: `docs/checks/012-online-collab-whiteboard-notes.md`
- Create: `docs/plans/2026-04-05-012-collab-board-check-plan.md`
- Modify: `docs/project-check-tracker.md`

**Step 1:** 在总台账中新增 `012` 项并更新统计值为“检查中”

**Step 2:** 创建 `012` 单项目检查报告，说明该项目为 Spring Boot + Thymeleaf 一体化项目

**Step 3:** 写入端口、数据库、文件存储路径、默认账号和资料范围等前置信息

### Task 2: 核对文档与源码一致性

**Files:**
- Modify: `docs/checks/012-online-collab-whiteboard-notes.md`
- Read: `012-backend/PRD/PRD.md`
- Read: `012-backend/ACCOUNTS.md`
- Read: `012-backend/THYMELEAF_README.md`
- Read: `012-backend/src/main/**`

**Step 1:** 对照 PRD、账号文档、Thymeleaf 说明与源码，确认用户、文档、文件夹、团队、模板、通知、WebSocket、管理员模块是否齐全

**Step 2:** 核对页面路由、控制器路径、模板文件、鉴权拦截器和资源配置是否匹配

**Step 3:** 将缺失项、资料冲突项和实现不足项写回检查报告

### Task 3: 验证 JDK 17 与构建链路

**Files:**
- Modify: `docs/checks/012-online-collab-whiteboard-notes.md`
- Read: `012-backend/pom.xml`
- Read: `012-backend/src/main/resources/application.yml`
- Read: `012-backend/src/main/resources/sql/collab_board.sql`

**Step 1:** 检查后端编译参数、数据库/Redis/JWT/WebSocket/文件存储依赖是否匹配 JDK 17

**Step 2:** 检查 Thymeleaf 页面、静态资源和模板继承说明是否完整

**Step 3:** 记录自动化测试缺口、MD5 密码、Redis 依赖和前端说明冲突等风险

### Task 4: 执行构建与启动验证

**Files:**
- Modify: `docs/checks/012-online-collab-whiteboard-notes.md`
- Modify: `docs/project-check-tracker.md`
- Read/Modify: `012-backend/**`

**Step 1:** 运行后端测试命令，确认 JDK 17 下可编译通过

**Step 2:** 启动服务，抽测登录、工作台、文档列表、文档编辑、团队、模板、个人中心和部分管理员接口

**Step 3:** 结合初始化 SQL 与测试账号，确认基础链路是否可用

### Task 5: 修复与结果回填

**Files:**
- Modify: `docs/checks/012-online-collab-whiteboard-notes.md`
- Modify: `docs/project-check-tracker.md`
- Modify: `012-backend/**`

**Step 1:** 对构建失败、启动失败、接口不一致、权限缺陷、页面断链等问题做根因分析

**Step 2:** 对可直接闭环的问题进行最小化修复，并完成复测

**Step 3:** 更新最终结论、问题清单、修复记录和整体统计
