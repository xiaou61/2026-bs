# 095 足球联赛管理系统 Implementation Plan

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** 基于仓库现有 Spring Boot + Vue3 骨架完成 095 足球联赛管理系统的前后端实现，并同步项目总览文档。

**Architecture:** 以后端 `MyBatis-Plus + Redis + JWT` 和前端 `Vue3 + Element Plus + Pinia + Axios + ECharts` 为基础，复制最近项目骨架生成 095 项目，再将领域对象替换为联赛、赛季、球队、球员、赛程、积分榜、资讯和关注业务对象。整体保持统一的分页查询、保存、删除、状态切换和看板展示模式。

**Tech Stack:** Spring Boot 2.7.18、MyBatis-Plus 3.5.5、MySQL 8.0、Redis、Vue 3.4.0、Element Plus 2.4.4、Pinia 2.1.7、Axios 1.6.2、ECharts 5.4.3

---

### Task 1: 准备 095 项目骨架

**Files:**
- Create: `095-backend/**`
- Create: `095-frontend/**`
- Modify: `docs/plans/2026-03-18-095-football-league-design.md`

**Step 1:** 复制最近项目骨架到 `095-backend` 和 `095-frontend`

**Step 2:** 将项目编号、数据库名、应用名、包名切换为 095 足球联赛系统

**Step 3:** 删除不再需要的宠物咖啡馆领域文件引用

### Task 2: 完成后端基础配置与 SQL

**Files:**
- Modify: `095-backend/pom.xml`
- Modify: `095-backend/src/main/resources/application.yml`
- Modify: `095-backend/sql/init.sql`
- Modify: `095-backend/src/main/java/com/football/FootballLeagueApplication.java`

**Step 1:** 调整 Maven 工程名、数据库连接与应用名

**Step 2:** 重写 12 张表的 SQL 与测试数据

**Step 3:** 确认 Redis 前缀、JWT 语义和包名统一为 football

### Task 3: 完成后端领域建模

**Files:**
- Modify/Create: `095-backend/src/main/java/com/football/entity/*.java`
- Modify/Create: `095-backend/src/main/java/com/football/mapper/*.java`
- Modify/Create: `095-backend/src/main/java/com/football/dto/*.java`

**Step 1:** 创建用户、联赛、赛季、俱乐部、球队、教练、球员、球场、比赛、积分榜、资讯、关注实体

**Step 2:** 为所有实体创建对应 Mapper

**Step 3:** 补齐赛果录入、关注操作等 DTO

### Task 4: 完成后端业务服务

**Files:**
- Modify/Create: `095-backend/src/main/java/com/football/service/*.java`
- Modify/Create: `095-backend/src/main/java/com/football/controller/*.java`

**Step 1:** 改造登录、注册、用户分页和资料维护

**Step 2:** 实现联赛、赛季、俱乐部、球队、教练、球员、球场 CRUD

**Step 3:** 实现比赛创建、赛果录入、积分榜重算、球队关注和资讯管理

**Step 4:** 实现统计看板、赛程趋势和积分排行接口

### Task 5: 完成前端系统改造

**Files:**
- Modify: `095-frontend/package.json`
- Modify: `095-frontend/src/router/index.js`
- Modify: `095-frontend/src/api/index.js`
- Modify: `095-frontend/src/views/**/*.vue`

**Step 1:** 替换项目名称、登录页文案和全局菜单

**Step 2:** 重建 API 封装与路由

**Step 3:** 改造首页、用户、联赛、赛季、俱乐部、球队、教练、球员、球场、赛程、积分榜、资讯、关注、统计、个人中心页面

**Step 4:** 统一角色文案、比赛状态文案和积分榜展示

### Task 6: 更新仓库总览

**Files:**
- Modify: `readme.md`
- Modify: `readme_simple.md`

**Step 1:** 将项目总数从 94 更新到 95

**Step 2:** 新增 095 项目简介与详细介绍

**Step 3:** 确认 `094` 的 `🔥最新` 标签切换到 `095`
