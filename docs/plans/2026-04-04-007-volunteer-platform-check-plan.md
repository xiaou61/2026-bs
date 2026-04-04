# 007 志愿活动管理与积分激励平台巡检计划 Implementation Plan

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** 完成 `007` 志愿活动管理与积分激励平台的文档完整性、JDK 17 兼容性、构建测试、启动可用性巡检，并把结果纳入总台账与单项目检查报告。

**Architecture:** 以“Spring Boot + Vue 3 前后端分离项目”的方式巡检 `007`。先核对总览、PRD、账号文档、后端配置、数据库初始化脚本、前端路由与接口，再分别验证后端 Maven 构建、前端 Vite 构建、服务启动和基础登录链路，记录 PRD 与实现的差异及安全风险。

**Tech Stack:** Spring Boot 3.2.0、MyBatis-Plus 3.5.5、MySQL 8、Hutool 5.8.23、java-jwt 4.4.0、Vue 3、Vue Router 4、Pinia 2、Element Plus 2、Vite 5、Maven 3.9、JDK 17

---

### Task 1: 建立 007 巡检文档

**Files:**
- Create: `docs/checks/007-volunteer-platform.md`
- Create: `docs/plans/2026-04-04-007-volunteer-platform-check-plan.md`
- Modify: `docs/project-check-tracker.md`

**Step 1:** 在总台账中新增 `007` 项并更新统计值

**Step 2:** 创建 `007` 单项目检查报告，标明其为前后端分离项目

**Step 3:** 写入端口、数据库、默认账号、前端代理和初始化脚本等前置条件

### Task 2: 核对文档与源码一致性

**Files:**
- Modify: `docs/checks/007-volunteer-platform.md`
- Read: `007-backend/PRD/PRD.md`
- Read: `007-backend/ACCOUNTS.md`
- Read: `007-backend/src/main/**`
- Read: `007-frontend/src/**`

**Step 1:** 对照 PRD、账号文档与源码，确认认证、用户、活动、报名、签到、积分、奖励、兑换、统计模块是否齐全

**Step 2:** 核对前端路由和后端控制器是否能覆盖管理员与志愿者核心页面/接口

**Step 3:** 将缺失项、疑似未完成项和文档空缺写回检查报告

### Task 3: 验证 JDK 17 与构建链路

**Files:**
- Modify: `docs/checks/007-volunteer-platform.md`
- Read: `007-backend/pom.xml`
- Read: `007-backend/src/main/resources/application.yml`
- Read: `007-backend/src/main/resources/sql/init.sql`
- Read: `007-frontend/package.json`
- Read: `007-frontend/vite.config.js`

**Step 1:** 检查后端编译参数、JWT、数据库依赖是否匹配 JDK 17

**Step 2:** 检查前端端口、代理和打包配置是否合理

**Step 3:** 记录自动化测试缺口、后端 README 缺失和密码加密风险

### Task 4: 执行构建与启动验证

**Files:**
- Modify: `docs/checks/007-volunteer-platform.md`
- Modify: `docs/project-check-tracker.md`
- Read/Modify: `007-backend/**`
- Read/Modify: `007-frontend/**`

**Step 1:** 运行后端构建或测试命令，确认 JDK 17 下能否通过

**Step 2:** 运行前端构建命令，确认依赖可用并能产出构建结果

**Step 3:** 启动后端与前端，抽测登录接口和页面响应

### Task 5: 根因排查与结果回填

**Files:**
- Modify: `docs/checks/007-volunteer-platform.md`
- Modify: `docs/project-check-tracker.md`
- Modify: `007-backend/**`
- Modify: `007-frontend/**`

**Step 1:** 对构建失败、启动失败、数据库依赖失败做根因分析

**Step 2:** 仅在必要时进行最小化修复，确保 `007` 能通过测试并启动

**Step 3:** 更新最终结论、问题清单、修复记录和整体统计
