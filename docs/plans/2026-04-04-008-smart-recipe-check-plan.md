# 008 智能菜谱推荐系统巡检计划 Implementation Plan

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** 完成 `008` 智能菜谱推荐系统的文档完整性、JDK 17 兼容性、构建测试、启动可用性巡检，并把结果纳入总台账与单项目检查报告；对巡检过程中发现的可直接修复问题进行闭环修复与复测。

**Architecture:** 以“Spring Boot 一体化部署项目”的方式巡检 `008`。先核对总览、PRD、README、快速启动文档、账号文档、数据库初始化脚本与静态前端页面，再验证 Maven 构建、服务启动、登录链路、公开接口与页面访问，记录文档与实现的差异以及安全风险。

**Tech Stack:** Spring Boot 3.2.0、MyBatis-Plus 3.5.5、MySQL 8、java-jwt 4.4.0、Hutool 5.8.23、fastjson2 2.0.43、jQuery 3.7.1、Bootstrap 5.3、ECharts 5、Layer 3.5、Maven 3.9、JDK 17

---

### Task 1: 建立 008 巡检文档

**Files:**
- Create: `docs/checks/008-smart-recipe-system.md`
- Create: `docs/plans/2026-04-04-008-smart-recipe-check-plan.md`
- Modify: `docs/project-check-tracker.md`

**Step 1:** 在总台账中新增 `008` 项并更新统计值

**Step 2:** 创建 `008` 单项目检查报告，标明其为一体化部署项目，前端位于 `static/`

**Step 3:** 写入端口、数据库、默认账号、静态入口页和初始化脚本等前置条件

### Task 2: 核对文档与源码一致性

**Files:**
- Modify: `docs/checks/008-smart-recipe-system.md`
- Read: `008-backend/PRD/PRD.md`
- Read: `008-backend/README.md`
- Read: `008-backend/QUICK_START.md`
- Read: `008-backend/ACCOUNTS.md`
- Read: `008-backend/src/main/**`

**Step 1:** 对照 PRD、README、账号文档与源码，确认用户、食材、菜谱、推荐、营养、购物清单、打卡、管理端模块是否齐全

**Step 2:** 核对静态页面入口、前端 JS 调用和后端控制器是否匹配

**Step 3:** 将缺失项、疑似未完成项和文档空缺写回检查报告

### Task 3: 验证 JDK 17 与构建链路

**Files:**
- Modify: `docs/checks/008-smart-recipe-system.md`
- Read: `008-backend/pom.xml`
- Read: `008-backend/src/main/resources/application.yml`
- Read: `008-backend/src/main/resources/sql/schema.sql`

**Step 1:** 检查后端编译参数、JWT、数据库依赖是否匹配 JDK 17

**Step 2:** 检查数据库配置、默认端口和初始化脚本是否完整

**Step 3:** 记录自动化测试缺口、MD5 密码风险和公开接口鉴权配置风险

### Task 4: 执行构建与启动验证

**Files:**
- Modify: `docs/checks/008-smart-recipe-system.md`
- Modify: `docs/project-check-tracker.md`
- Read/Modify: `008-backend/**`

**Step 1:** 运行后端构建或测试命令，确认 JDK 17 下能否通过

**Step 2:** 启动服务，抽测静态登录页、登录接口、用户信息接口和公开详情接口

**Step 3:** 结合数据库账号与初始化脚本，确认基础链路是否可用

### Task 5: 修复与结果回填

**Files:**
- Modify: `docs/checks/008-smart-recipe-system.md`
- Modify: `docs/project-check-tracker.md`
- Modify: `008-backend/**`

**Step 1:** 对构建失败、启动失败、数据库依赖失败或公开接口鉴权缺陷做根因分析

**Step 2:** 对可直接闭环的问题进行最小化修复，并完成复测

**Step 3:** 更新最终结论、问题清单、修复记录和整体统计
