# 003 助农精准扶贫平台巡检计划 Implementation Plan

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** 完成 `003` 一体化助农精准扶贫平台的文档完整性、JDK 17 兼容性、构建测试、页面与接口启动可用性巡检，并将结果纳入总台账。

**Architecture:** 以“一体化 Spring Boot 项目”方式巡检 `003`。先核对 PRD、README、安装文档、模板页面、SQL 与控制器的一致性，再验证 Maven 构建、Spring Boot 启动、Thymeleaf 页面访问和关键接口链路，出现问题时按根因记录并最小化处理。

**Tech Stack:** Spring Boot 3.2.0、MyBatis-Plus 3.5.5、MySQL 8、JWT 0.11.5、Thymeleaf、jQuery 3.6.0、Bootstrap 5.1.3、Maven 3.9、JDK 17

---

### Task 1: 建立 003 巡检文档

**Files:**
- Create: `docs/checks/003-farm-platform.md`
- Create: `docs/plans/2026-04-04-003-farm-platform-check-plan.md`
- Modify: `docs/project-check-tracker.md`

**Step 1:** 在总台账中新增 `003` 项并更新统计值

**Step 2:** 创建 `003` 单项目检查报告，注明其为一体化项目

**Step 3:** 记录现有文档、模板页面、数据库与端口前置条件

### Task 2: 核对文档与源码一致性

**Files:**
- Modify: `docs/checks/003-farm-platform.md`
- Read: `003-backend/PRD/1.md`
- Read: `003-backend/README.md`
- Read: `003-backend/INSTALL.md`
- Read: `003-backend/QUICK_START.md`
- Read: `003-backend/src/main/**`

**Step 1:** 对照 PRD 与 README，确认用户、商品、订单、评论、公告、统计等核心模块是否齐全

**Step 2:** 核对模板页面、控制器和接口路径是否与文档描述基本一致

**Step 3:** 将文档缺漏项与疑似未实现功能回填到检查报告

### Task 3: 验证 JDK 17 与构建链路

**Files:**
- Modify: `docs/checks/003-farm-platform.md`
- Read/Modify: `003-backend/pom.xml`
- Read/Modify: `003-backend/src/main/resources/application.yml`
- Read/Modify: `003-backend/src/main/resources/sql/init.sql`

**Step 1:** 检查 Java 版本、MyBatis-Plus、JWT 与 Thymeleaf 依赖是否匹配 JDK 17

**Step 2:** 检查数据库名、静态资源路径和模板配置是否可运行

**Step 3:** 记录自动化测试缺口、图片资源占位、数据库前置等风险

### Task 4: 执行构建与启动验证

**Files:**
- Modify: `docs/checks/003-farm-platform.md`
- Modify: `docs/project-check-tracker.md`
- Read/Modify: `003-backend/**`

**Step 1:** 运行后端构建或测试命令，确认 JDK 17 下能否通过

**Step 2:** 启动 Spring Boot 应用，验证默认端口或隔离端口的可用性

**Step 3:** 抽测首页模板或登录接口，确认一体化项目可访问

### Task 5: 根因排查与结果回填

**Files:**
- Modify: `docs/checks/003-farm-platform.md`
- Modify: `docs/project-check-tracker.md`
- Modify: `003-backend/**`

**Step 1:** 对构建失败、数据库失败、页面失败或端口失败做根因分析

**Step 2:** 仅在必要时进行最小化修复，确保 `003` 能通过测试并启动

**Step 3:** 更新最终结论、问题清单、修复记录和统计值
