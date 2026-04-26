# Online Hospital Registration Implementation Plan

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** Build project 096 as a complete online hospital registration system with admin, doctor, and patient workflows.

**Architecture:** Copy the stable MyBatis backend scaffold from 092 and the appointment-oriented Vue frontend scaffold from 075, then replace domain modules with hospital registration modules. Keep one frontend project with role-based menus and one backend project with JWT, Redis, and PageHelper.

**Tech Stack:** Spring Boot 2.7.18, MyBatis 3.5.13, PageHelper 1.4.7, MySQL 8.0, Redis, JWT, Vue 3.4.0, Element Plus 2.4.4, Pinia, Axios, ECharts

---

### Task 1: Write planning artifacts

**Files:**
- Create: `docs/plans/2026-04-17-online-hospital-registration-design.md`
- Create: `docs/plans/2026-04-17-online-hospital-registration.md`
- Modify: `096-backend/PRD.md`
- Modify: `096-backend/PLAN.md`

**Step 1:** Replace the copied PRD and PLAN with hospital registration content.

**Step 2:** Save a concise design doc and implementation plan under `docs/plans/`.

**Step 3:** Confirm the plan matches the approved direction: admin + doctor + patient, one frontend project, MyBatis backend.

### Task 2: Rebuild backend domain model

**Files:**
- Modify: `096-backend/pom.xml`
- Modify: `096-backend/src/main/resources/application.yml`
- Modify: `096-backend/sql/init.sql`
- Modify: `096-backend/src/main/java/com/**`
- Modify: `096-backend/src/main/resources/mapper/*.xml`

**Step 1:** Rename package and application identifiers to hospital registration naming.

**Step 2:** Replace copied kindergarten modules with hospital modules for users, departments, doctors, schedules, appointments, orders, cards, reviews, notices, banners, and statistics.

**Step 3:** Write mapper XML and service logic for list, save, delete, create appointment, pay order, cancel appointment, finish appointment, and dashboard statistics.

### Task 3: Rebuild frontend modules

**Files:**
- Modify: `096-frontend/package.json`
- Modify: `096-frontend/src/router/index.js`
- Modify: `096-frontend/src/api/index.js`
- Modify: `096-frontend/src/views/**`

**Step 1:** Rename project branding and route structure to hospital registration naming.

**Step 2:** Replace repair modules with department, doctor, schedule, appointment, order, card, notice, banner, and statistics modules.

**Step 3:** Adjust role menus for admin, doctor, and patient in a shared layout.

### Task 4: Update repository documentation

**Files:**
- Modify: `readme.md`
- Modify: `readme_simple.md`

**Step 1:** Add the 096 project entry before the quick-start section.

**Step 2:** Update the project count and short summary entry.

### Task 5: Final structure review

**Files:**
- Review: `096-backend/**`
- Review: `096-frontend/**`
- Review: `readme.md`
- Review: `readme_simple.md`

**Step 1:** Check package names, route names, endpoint names, and titles for old copied project traces.

**Step 2:** Check SQL table names and default accounts match the PRD.

**Step 3:** Summarize the delivered files and note that no compile verification was run by project rule.
