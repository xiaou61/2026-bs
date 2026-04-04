# 005 在线问卷调查与数据分析系统巡检计划 Implementation Plan

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** 完成 `005` 在线问卷调查与数据分析系统的文档完整性、JDK 17 兼容性、构建测试、启动可用性巡检，并把结果纳入总台账与单项目检查报告。

**Architecture:** 以“Spring Boot + Thymeleaf 一体化项目”的方式巡检 `005`。先核对总览、PRD、README、账号文档、数据库初始化脚本与页面模板，再验证 Maven 构建、服务启动、核心页面和登录接口，记录文档与实现之间的差异以及数据库依赖风险。

**Tech Stack:** Spring Boot 3.2.0、Thymeleaf、MyBatis-Plus 3.5.5、MySQL 8、JWT 0.11.5、Hutool 5.8.23、Apache POI 5.2.3、Maven 3.9、JDK 17

---

### Task 1: 建立 005 巡检文档

**Files:**
- Create: `docs/checks/005-online-survey-system.md`
- Create: `docs/plans/2026-04-04-005-survey-system-check-plan.md`
- Modify: `docs/project-check-tracker.md`

**Step 1:** 在总台账中新增 `005` 项并更新统计值

**Step 2:** 创建 `005` 单项目检查报告，标明其为后端一体化项目，无独立前端目录

**Step 3:** 写入已知前置条件，包括端口、MySQL、初始化脚本和测试账号

### Task 2: 核对文档与源码一致性

**Files:**
- Modify: `docs/checks/005-online-survey-system.md`
- Read: `005-backend/PRD/1.md`
- Read: `005-backend/README.md`
- Read: `005-backend/ACCOUNTS.md`
- Read: `005-backend/src/main/**`

**Step 1:** 对照 PRD、README、账号文档与源码，确认用户、问卷、题目、答卷、统计模块是否齐全

**Step 2:** 检查项目究竟是“前后端分离”还是“一体化模板渲染”，记录文档描述偏差

**Step 3:** 将缺失项、疑似未完成项和文档空缺写回检查报告

### Task 3: 验证 JDK 17 与构建链路

**Files:**
- Modify: `docs/checks/005-online-survey-system.md`
- Read: `005-backend/pom.xml`
- Read: `005-backend/src/main/resources/application.yml`
- Read: `005-backend/src/main/resources/sql/init.sql`

**Step 1:** 检查后端编译参数、数据库、JWT、POI 依赖是否匹配 JDK 17

**Step 2:** 检查数据库配置、默认端口和初始化脚本是否完整

**Step 3:** 记录自动化测试缺口、文档与实现不一致项和潜在安全风险

### Task 4: 执行构建与启动验证

**Files:**
- Modify: `docs/checks/005-online-survey-system.md`
- Modify: `docs/project-check-tracker.md`
- Read/Modify: `005-backend/**`

**Step 1:** 运行后端构建或测试命令，确认 JDK 17 下能否通过

**Step 2:** 启动服务，抽测首页、登录页和登录接口响应

**Step 3:** 结合数据库账号与初始化脚本，确认核心链路是否可用

### Task 5: 根因排查与结果回填

**Files:**
- Modify: `docs/checks/005-online-survey-system.md`
- Modify: `docs/project-check-tracker.md`
- Modify: `005-backend/**`

**Step 1:** 对构建失败、启动失败、数据库依赖失败做根因分析

**Step 2:** 仅在必要时进行最小化修复，确保 `005` 能通过测试并启动

**Step 3:** 更新最终结论、问题清单、修复记录和整体统计
