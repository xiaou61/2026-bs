# 001 校园事务管理系统巡检计划 Implementation Plan

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** 完成 `001` 项目的文档完整性、JDK 17 兼容性、构建测试、启动可用性巡检，并把结果沉淀到总台账和单项目检查报告中。

**Architecture:** 以“总台账 + 单项目检查报告”双文档机制管理巡检过程。先核对 PRD、README、源码与运行配置的一致性，再分别验证后端 Maven 构建、前端 npm 构建和实际启动路径，出现问题时按根因排查并修复。

**Tech Stack:** Spring Boot 3.2.0、MyBatis-Plus 3.5.5、JWT 0.11.5、MySQL 8.0、Vue 3、Vite 5、Node.js 22、Maven 3.9、JDK 17

---

### Task 1: 建立巡检文档骨架

**Files:**
- Create: `docs/project-check-tracker.md`
- Create: `docs/checks/001-campus-affairs-system.md`
- Create: `docs/plans/2026-04-04-001-campus-affairs-check-plan.md`

**Step 1:** 创建总台账，定义统计口径、状态字段和项目进度汇总格式

**Step 2:** 创建 `001` 单项目检查报告，记录项目背景、目录结构、文档来源和检查维度

**Step 3:** 将已知事实写入文档，包括项目编号、技术栈、PRD 位置和默认运行端口

### Task 2: 核对文档与源码一致性

**Files:**
- Modify: `docs/checks/001-campus-affairs-system.md`
- Read: `001-backend/PRD/PRD.md`
- Read: `001-backend/README.md`
- Read: `001-frontend/README.md`
- Read: `001-backend/src/main/**`
- Read: `001-frontend/src/main/**`

**Step 1:** 对照 PRD、前后端 README 与实际源码目录，确认功能模块是否齐全

**Step 2:** 标记缺失项、不一致项和疑似占位内容

**Step 3:** 将文档完整性结论写回 `001` 检查报告

### Task 3: 验证 JDK 17 与构建链路

**Files:**
- Modify: `docs/checks/001-campus-affairs-system.md`
- Read/Modify: `001-backend/pom.xml`
- Read/Modify: `001-backend/src/main/resources/application.yml`
- Read/Modify: `001-frontend/package.json`
- Read/Modify: `001-frontend/vite.config.js`

**Step 1:** 检查后端 `pom.xml`、依赖版本和插件配置是否面向 JDK 17

**Step 2:** 检查本机 Java、Maven、Node、npm 环境与项目要求是否匹配

**Step 3:** 记录运行前置条件，包括数据库、端口、环境变量和 PATH 风险

### Task 4: 执行后端与前端验证

**Files:**
- Modify: `docs/checks/001-campus-affairs-system.md`
- Modify: `docs/project-check-tracker.md`
- Read/Modify: `001-backend/**`
- Read/Modify: `001-frontend/**`

**Step 1:** 运行后端测试或构建命令，确认能否在 JDK 17 下通过

**Step 2:** 运行前端构建命令，确认依赖可安装、页面可打包

**Step 3:** 尝试启动前后端服务，记录成功路径或失败根因

### Task 5: 根因修复与结论回填

**Files:**
- Modify: `docs/checks/001-campus-affairs-system.md`
- Modify: `docs/project-check-tracker.md`
- Modify: `001-backend/**`
- Modify: `001-frontend/**`

**Step 1:** 对启动失败、测试失败或配置不兼容问题做根因排查

**Step 2:** 只做支撑 `001` 通过测试并可启动所需的最小修复

**Step 3:** 回填最终结论、修复记录、剩余风险和下一项目接续方式
