# 002 在线选课与成绩管理系统巡检计划 Implementation Plan

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** 完成 `002` 项目的文档完整性、JDK 17 兼容性、构建测试、启动可用性巡检，并将结果持续回填到总台账与单项目检查报告中。

**Architecture:** 延续“总台账 + 单项目检查报告”的巡检机制。先比对 PRD、README、源码目录、SQL 与配置的一致性，再分别验证后端 Maven 构建、前端 npm 构建与实际启动链路；若出现失败，则按根因排查并记录最小修复方案。

**Tech Stack:** Spring Boot 3.2.0、MyBatis Spring Boot Starter 3.0.3、MySQL 8、JWT 0.12.3、Vue 3、Vite 5、Element Plus、Pinia、Axios、Node.js 22、Maven 3.9、JDK 17

---

### Task 1: 建立 002 巡检文档

**Files:**
- Create: `docs/checks/002-course-selection-system.md`
- Create: `docs/plans/2026-04-04-002-course-selection-check-plan.md`
- Modify: `docs/project-check-tracker.md`

**Step 1:** 在总台账中新增 `002` 行并更新统计值

**Step 2:** 创建 `002` 单项目检查报告，写入项目来源、目录结构与检查目标

**Step 3:** 记录已知运行前置条件，包括数据库、端口、JDK 与 Node 环境

### Task 2: 核对文档与源码一致性

**Files:**
- Modify: `docs/checks/002-course-selection-system.md`
- Read: `002-backend/PRD/readme.md`
- Read: `002-backend/README.md`
- Read: `002-frontend/README.md`
- Read: `002-backend/src/main/**`
- Read: `002-frontend/src/**`

**Step 1:** 对照 PRD、README 与源码目录，确认学生端、教师端、管理员端和基础公共模块是否齐全

**Step 2:** 核对 SQL 表结构、接口命名和路由页面是否与文档描述基本一致

**Step 3:** 将一致性结论与缺漏项写回单项目报告

### Task 3: 验证 JDK 17 与构建链路

**Files:**
- Modify: `docs/checks/002-course-selection-system.md`
- Read/Modify: `002-backend/pom.xml`
- Read/Modify: `002-backend/src/main/resources/application.yml`
- Read/Modify: `002-frontend/package.json`
- Read/Modify: `002-frontend/vite.config.js`

**Step 1:** 检查后端依赖与编译参数是否面向 JDK 17

**Step 2:** 检查数据库连接、端口配置、JWT 和 MyBatis 配置是否可运行

**Step 3:** 记录自动化测试缺口、环境变量与端口冲突等风险

### Task 4: 执行构建与启动验证

**Files:**
- Modify: `docs/checks/002-course-selection-system.md`
- Modify: `docs/project-check-tracker.md`
- Read/Modify: `002-backend/**`
- Read/Modify: `002-frontend/**`

**Step 1:** 运行后端构建或测试命令，确认 JDK 17 下能否通过

**Step 2:** 运行前端构建命令，确认依赖可用并能产出打包结果

**Step 3:** 启动后端与前端，抽测关键接口或页面链路

### Task 5: 根因排查与结果回填

**Files:**
- Modify: `docs/checks/002-course-selection-system.md`
- Modify: `docs/project-check-tracker.md`
- Modify: `002-backend/**`
- Modify: `002-frontend/**`

**Step 1:** 对构建失败、接口失败或启动失败问题做根因分析

**Step 2:** 仅在必要时进行最小化修复，确保 `002` 能通过测试并启动

**Step 3:** 更新最终结论、问题清单、修复记录和台账统计
