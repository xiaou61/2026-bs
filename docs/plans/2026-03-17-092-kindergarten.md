# 蓝天幼儿园管理系统 Implementation Plan

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** 将 092 项目从课程管理系统改造成蓝天幼儿园管理系统，交付可直接用于毕设展示的前后端代码与项目文档。

**Architecture:** 以后端 087 模板为基础，沿用 Spring Boot + MyBatis + PageHelper + Redis 的分层架构，前端沿用 Vue3 + Element Plus 中后台结构。通过“结构映射 + 重点模块重写”的方式，在保留成熟通用能力的前提下快速切换业务场景。

**Tech Stack:** Spring Boot 2.7.18、MyBatis 3.5.13、PageHelper 1.4.7、Redis、MySQL 8.0、Vue 3.4.0、Element Plus 2.4.4、Pinia、Axios、ECharts

---

### Task 1: 文档与配置口径统一

**Files:**
- Modify: `092-backend/PRD.md`
- Modify: `092-backend/PLAN.md`
- Modify: `092-backend/src/main/resources/application.yml`
- Create: `docs/plans/2026-03-17-092-kindergarten-design.md`
- Create: `docs/plans/2026-03-17-092-kindergarten.md`

**Step 1:** 重写 PRD 与 PLAN，明确角色、模块和数据库表。

**Step 2:** 修改应用名、数据库名、包扫描路径和类型别名包。

**Step 3:** 保存设计文档与实施计划，统一 092 项目的业务口径。

### Task 2: 后端语义重构

**Files:**
- Modify: `092-backend/sql/init.sql`
- Modify: `092-backend/src/main/java/com/kindergarten/**`
- Modify: `092-backend/src/main/resources/mapper/*.xml`

**Step 1:** 完成包名、主类和实体类的课程语义替换。

**Step 2:** 重写关键 Mapper XML、Service 和 Controller 的字段与接口。

**Step 3:** 调整角色权限、统计逻辑和用户下拉逻辑。

### Task 3: 前端页面改造

**Files:**
- Modify: `092-frontend/src/router/index.js`
- Modify: `092-frontend/src/api/index.js`
- Modify: `092-frontend/src/utils/index.js`
- Modify: `092-frontend/src/views/**/*.vue`

**Step 1:** 更新路由、菜单和接口映射。

**Step 2:** 重写仪表盘、布局页和业务页面文案、字段、表单。

**Step 3:** 调整角色显示逻辑，确保管理员、教师、家长菜单不同。

### Task 4: 项目合集更新

**Files:**
- Modify: `readme.md`
- Modify: `readme_simple.md`

**Step 1:** 将项目总数从 91 更新为 92。

**Step 2:** 新增 092 蓝天幼儿园管理系统项目简介与详细展示。
