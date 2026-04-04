# 004 实时聊天与通知系统巡检计划 Implementation Plan

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** 完成 `004` 实时聊天与通知系统的文档完整性、JDK 17 兼容性、构建测试、启动可用性巡检，并把结果纳入总台账与单项目检查报告。

**Architecture:** 以“前后端分离 + WebSocket/Redis 依赖项目”的方式巡检 `004`。先核对 PRD、账号文档、前端 README、后端配置、数据库与 Redis 前置，再分别验证后端 Maven 构建、前端 Vite 构建、服务启动和关键登录链路，必要时记录环境依赖对启动的影响。

**Tech Stack:** Spring Boot 3.2.0、WebSocket、Redis、MyBatis-Plus 3.5.5、MySQL 8、JWT 0.11.5、Vue 3、Vite 5、Pinia、Element Plus、SockJS、Stomp、Maven 3.9、JDK 17

---

### Task 1: 建立 004 巡检文档

**Files:**
- Create: `docs/checks/004-chat-system.md`
- Create: `docs/plans/2026-04-04-004-chat-system-check-plan.md`
- Modify: `docs/project-check-tracker.md`

**Step 1:** 在总台账中新增 `004` 项并更新统计值

**Step 2:** 创建 `004` 单项目检查报告，记录其为前后端分离且依赖 Redis/WebSocket

**Step 3:** 写入已知运行前置条件，包括端口、MySQL、Redis 和默认账号

### Task 2: 核对文档与源码一致性

**Files:**
- Modify: `docs/checks/004-chat-system.md`
- Read: `004-backend/PRD/1.md`
- Read: `004-backend/ACCOUNTS.md`
- Read: `004-frontend/README.md`
- Read: `004-backend/src/main/**`
- Read: `004-frontend/src/**`

**Step 1:** 对照 PRD、账号文档与源码，确认用户、好友、聊天、通知、个人设置等模块是否齐全

**Step 2:** 核对 WebSocket、好友分组、通知、历史消息等接口或页面是否有实际实现

**Step 3:** 将缺失项、疑似未完成项和文档空缺写回检查报告

### Task 3: 验证 JDK 17 与构建链路

**Files:**
- Modify: `docs/checks/004-chat-system.md`
- Read/Modify: `004-backend/pom.xml`
- Read/Modify: `004-backend/src/main/resources/application.yml`
- Read/Modify: `004-frontend/package.json`
- Read/Modify: `004-frontend/vite.config.js`

**Step 1:** 检查后端编译参数、WebSocket、Redis 与 JWT 依赖是否匹配 JDK 17

**Step 2:** 检查前端端口、代理、SockJS/Stomp 依赖是否合理

**Step 3:** 记录 Redis、MySQL、自动化测试缺口与端口冲突风险

### Task 4: 执行构建与启动验证

**Files:**
- Modify: `docs/checks/004-chat-system.md`
- Modify: `docs/project-check-tracker.md`
- Read/Modify: `004-backend/**`
- Read/Modify: `004-frontend/**`

**Step 1:** 运行后端构建或测试命令，确认 JDK 17 下能否通过

**Step 2:** 运行前端构建命令，确认依赖可用并能产出构建结果

**Step 3:** 启动后端与前端，抽测登录接口和首页/登录页响应

### Task 5: 根因排查与结果回填

**Files:**
- Modify: `docs/checks/004-chat-system.md`
- Modify: `docs/project-check-tracker.md`
- Modify: `004-backend/**`
- Modify: `004-frontend/**`

**Step 1:** 对构建失败、启动失败、Redis/数据库依赖失败做根因分析

**Step 2:** 仅在必要时进行最小化修复，确保 `004` 能通过测试并启动

**Step 3:** 更新最终结论、问题清单、修复记录和整体统计
