# 006 校园失物招领系统巡检计划 Implementation Plan

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** 完成 `006` 校园失物招领系统的文档完整性、JDK 17 兼容性、构建测试、启动可用性巡检，并把结果纳入总台账与单项目检查报告。

**Architecture:** 以“Spring Boot + Thymeleaf 一体化项目”的方式巡检 `006`。先核对总览、PRD、账号文档、数据库初始化脚本、页面模板与控制器，再验证 Maven 构建、服务启动、核心页面和登录接口，记录鉴权方式、文档差异和未落地能力。

**Tech Stack:** Spring Boot 3.2.0、Thymeleaf、MyBatis 3.0.3、MySQL 8、Hutool 5.8.23、Commons Codec、Maven 3.9、JDK 17

---

### Task 1: 建立 006 巡检文档

**Files:**
- Create: `docs/checks/006-lost-found-system.md`
- Create: `docs/plans/2026-04-04-006-lost-found-check-plan.md`
- Modify: `docs/project-check-tracker.md`

**Step 1:** 在总台账中新增 `006` 项并更新统计值

**Step 2:** 创建 `006` 单项目检查报告，标明其为一体化项目且无独立前端目录

**Step 3:** 写入端口、数据库、账号、上传目录等前置条件

### Task 2: 核对文档与源码一致性

**Files:**
- Modify: `docs/checks/006-lost-found-system.md`
- Read: `006-backend/PRD/PRD.md`
- Read: `006-backend/ACCOUNTS.md`
- Read: `006-backend/src/main/**`

**Step 1:** 对照 PRD、账号文档与源码，确认用户、分类、失物、招领、认领、收藏、通知等模块是否齐全

**Step 2:** 检查鉴权到底是 JWT 还是 Session，记录与 PRD 的偏差

**Step 3:** 将缺失项、疑似未完成项和文档空缺写回检查报告

### Task 3: 验证 JDK 17 与构建链路

**Files:**
- Modify: `docs/checks/006-lost-found-system.md`
- Read: `006-backend/pom.xml`
- Read: `006-backend/src/main/resources/application.yml`
- Read: `006-backend/src/main/resources/sql/init.sql`

**Step 1:** 检查后端编译参数、MyBatis、文件上传、密码加密依赖是否匹配 JDK 17

**Step 2:** 检查数据库配置、默认端口和初始化脚本是否完整

**Step 3:** 记录自动化测试缺口、鉴权实现差异和安全风险

### Task 4: 执行构建与启动验证

**Files:**
- Modify: `docs/checks/006-lost-found-system.md`
- Modify: `docs/project-check-tracker.md`
- Read/Modify: `006-backend/**`

**Step 1:** 运行后端构建或测试命令，确认 JDK 17 下能否通过

**Step 2:** 启动服务，抽测首页、登录页和登录接口响应

**Step 3:** 结合数据库账号与初始化脚本，确认基础链路是否可用

### Task 5: 根因排查与结果回填

**Files:**
- Modify: `docs/checks/006-lost-found-system.md`
- Modify: `docs/project-check-tracker.md`
- Modify: `006-backend/**`

**Step 1:** 对构建失败、启动失败、数据库依赖失败做根因分析

**Step 2:** 仅在必要时进行最小化修复，确保 `006` 能通过测试并启动

**Step 3:** 更新最终结论、问题清单、修复记录和整体统计
