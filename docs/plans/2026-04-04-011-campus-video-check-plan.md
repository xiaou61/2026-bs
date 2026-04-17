# 011 校园短视频创作与分享平台巡检计划 Implementation Plan

**Goal:** 完成 `011` 校园短视频创作与分享平台的文档完整性、JDK 17 兼容性、前后端构建测试、启动可用性巡检，并把结果纳入总台账与单项目检查报告；对巡检过程中发现的可直接修复问题进行闭环修复与复测。

**Architecture:** 以“Spring Boot 后端 + Vue 3 前端”的前后端分离项目方式巡检 `011`。先核对项目总览、PRD、账号文档、后端历史总结资料与前端 README，再验证 Maven 构建、前端安装构建、前后端启动、登录链路、视频推荐/发布/互动链路和管理能力实现情况，记录文档与实现的差异以及遗留风险。

**Tech Stack:** Spring Boot 3.2.0、MyBatis-Plus 3.5.5、MySQL 8、Redis、JWT 0.12.5、Vue 3、Vite 5、Element Plus 2、Pinia 2、Axios、Video.js、Maven 3.9、JDK 17、Node.js

---

### Task 1: 建立 011 巡检文档

**Files:**
- Create: `docs/checks/011-campus-video-platform.md`
- Create: `docs/plans/2026-04-04-011-campus-video-check-plan.md`
- Modify: `docs/project-check-tracker.md`

**Step 1:** 在总台账中新增 `011` 项并更新统计值为“检查中”

**Step 2:** 创建 `011` 单项目检查报告，说明该项目为前后端分离短视频平台

**Step 3:** 写入后端端口、前端端口、数据库、文件存储路径、默认账号和资料范围等前置信息

### Task 2: 核对文档与源码一致性

**Files:**
- Modify: `docs/checks/011-campus-video-platform.md`
- Read: `011-backend/PRD/PRD.md`
- Read: `011-backend/ACCOUNTS.md`
- Read: `011-backend/src/main/**`
- Read: `011-frontend/src/**`

**Step 1:** 对照 PRD、账号文档、历史总结文档与源码，确认用户、视频、评论、通知、话题、搜索、草稿、管理员模块是否齐全

**Step 2:** 核对前端页面、API 封装、路由守卫、Vite 代理和后端控制器路径是否匹配

**Step 3:** 将缺失项、资料冲突项和实现不足项写回检查报告

### Task 3: 验证 JDK 17 与构建链路

**Files:**
- Modify: `docs/checks/011-campus-video-platform.md`
- Read: `011-backend/pom.xml`
- Read: `011-backend/src/main/resources/application.yml`
- Read: `011-backend/src/main/resources/sql/campus_video.sql`
- Read: `011-frontend/package.json`

**Step 1:** 检查后端编译参数、数据库/Redis/JWT/文件存储依赖是否匹配 JDK 17

**Step 2:** 检查前端依赖、代理配置和构建命令是否完整

**Step 3:** 记录自动化测试缺口、MD5 密码、Redis 依赖、管理员前端缺失等风险

### Task 4: 执行构建与启动验证

**Files:**
- Modify: `docs/checks/011-campus-video-platform.md`
- Modify: `docs/project-check-tracker.md`
- Read/Modify: `011-backend/**`
- Read/Modify: `011-frontend/**`

**Step 1:** 运行后端测试命令和前端构建命令，确认 JDK 17 / 当前 Node 环境下可编译通过

**Step 2:** 启动前后端，抽测登录、推荐视频、搜索、通知、个人中心和部分管理员接口

**Step 3:** 结合初始化 SQL 与测试账号，确认基础链路是否可用

### Task 5: 修复与结果回填

**Files:**
- Modify: `docs/checks/011-campus-video-platform.md`
- Modify: `docs/project-check-tracker.md`
- Modify: `011-backend/**`
- Modify: `011-frontend/**`

**Step 1:** 对构建失败、启动失败、接口不一致、权限缺陷、前端断链等问题做根因分析

**Step 2:** 对可直接闭环的问题进行最小化修复，并完成复测

**Step 3:** 更新最终结论、问题清单、修复记录和整体统计
