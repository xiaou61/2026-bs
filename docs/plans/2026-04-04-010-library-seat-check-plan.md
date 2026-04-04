# 010 图书馆座位预约系统巡检计划 Implementation Plan

**Goal:** 完成 `010` 图书馆座位预约系统的文档完整性、JDK 17 兼容性、构建测试、启动可用性巡检，并把结果纳入总台账与单项目检查报告；对巡检过程中发现的可直接修复问题进行闭环修复与复测。

**Architecture:** 以“Spring Boot + Thymeleaf 一体化部署项目”的方式巡检 `010`。先核对项目总览、PRD、账号文档、数据库初始化脚本与静态页面，再验证 Maven 构建、服务启动、登录链路、预约链路、管理员链路和关键页面访问，记录文档与实现的差异以及遗留风险。

**Tech Stack:** Spring Boot 3.2.0、MyBatis-Plus 3.5.5、MySQL 8、java-jwt 4.4.0、Thymeleaf、Hutool 5.8.23、fastjson2 2.0.43、jQuery、Bootstrap 5、Layer、ECharts、Maven 3.9、JDK 17

---

### Task 1: 建立 010 巡检文档

**Files:**
- Create: `docs/checks/010-library-seat-system.md`
- Create: `docs/plans/2026-04-04-010-library-seat-check-plan.md`
- Modify: `docs/project-check-tracker.md`

**Step 1:** 在总台账中新增 `010` 项并更新统计值为“检查中”

**Step 2:** 创建 `010` 单项目检查报告，说明该项目为 Spring Boot 一体化部署，前端页面位于 `static/`

**Step 3:** 写入端口、数据库、初始化脚本、默认账号和资料缺口（无 `README`/`QUICK_START`）等前置信息

### Task 2: 核对文档与源码一致性

**Files:**
- Modify: `docs/checks/010-library-seat-system.md`
- Read: `010-backend/PRD/PRD.md`
- Read: `010-backend/ACCOUNTS.md`
- Read: `010-backend/src/main/**`

**Step 1:** 对照 PRD、账号文档与源码，确认用户、座位、预约、违约、信用分、通知、管理员后台模块是否齐全

**Step 2:** 核对静态页面入口、请求封装、拦截器白名单与后端控制器是否匹配

**Step 3:** 将缺失项、资料不足项和文档空缺写回检查报告

### Task 3: 验证 JDK 17 与构建链路

**Files:**
- Modify: `docs/checks/010-library-seat-system.md`
- Read: `010-backend/pom.xml`
- Read: `010-backend/src/main/resources/application.yml`
- Read: `010-backend/src/main/resources/sql/library_seat.sql`

**Step 1:** 检查后端编译参数、数据库/JWT/Thymeleaf 依赖是否匹配 JDK 17

**Step 2:** 检查数据库配置、默认端口、静态资源和初始化脚本是否完整

**Step 3:** 记录自动化测试缺口、密码存储方式、旧依赖坐标或鉴权设计风险

### Task 4: 执行构建与启动验证

**Files:**
- Modify: `docs/checks/010-library-seat-system.md`
- Modify: `docs/project-check-tracker.md`
- Read/Modify: `010-backend/**`

**Step 1:** 运行后端测试命令，确认 JDK 17 下可编译通过

**Step 2:** 启动服务，抽测登录接口、当前用户信息、座位列表/预约列表、管理员接口和静态页面

**Step 3:** 结合初始化 SQL 与测试账号，确认基础链路是否可用

### Task 5: 修复与结果回填

**Files:**
- Modify: `docs/checks/010-library-seat-system.md`
- Modify: `docs/project-check-tracker.md`
- Modify: `010-backend/**`

**Step 1:** 对构建失败、启动失败、文档与实现不一致、鉴权缺陷等问题做根因分析

**Step 2:** 对可直接闭环的问题进行最小化修复，并完成复测

**Step 3:** 更新最终结论、问题清单、修复记录和整体统计
