# 030 统计分析模块 Implementation Plan

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** 为 `030` 居民智能健康管理平台补齐真实可用的统计分析模块，让管理员端和医生端仪表盘不再是占位数据。

**Architecture:** 后端新增一个轻量级统计服务，直接聚合现有 `User`、`DoctorInfo`、`Consultation`、`HealthData`、`HealthKnowledge`、`HealthAlert`、`HealthAssessment` 等实体的数据，不引入新的复杂报表体系。前端优先接入管理员端和医生端现有 Dashboard 页面，展示总览指标、趋势数据和服务排行，先形成“真实统计闭环”，不扩展到导出文件。

**Tech Stack:** Spring Boot 3.2、Spring Data JPA、React 18、Vite、Ant Design、Recharts

---

### Task 1: 定义统计接口和聚合查询

**Files:**
- Create: `030-backend/src/main/java/com/xiaou/health/service/StatisticsService.java`
- Create: `030-backend/src/main/java/com/xiaou/health/controller/StatisticsController.java`
- Modify: `030-backend/src/main/java/com/xiaou/health/repository/UserRepository.java`
- Modify: `030-backend/src/main/java/com/xiaou/health/repository/DoctorInfoRepository.java`
- Modify: `030-backend/src/main/java/com/xiaou/health/repository/ConsultationRepository.java`
- Modify: `030-backend/src/main/java/com/xiaou/health/repository/HealthDataRepository.java`
- Modify: `030-backend/src/main/java/com/xiaou/health/repository/HealthKnowledgeRepository.java`
- Modify: `030-backend/src/main/java/com/xiaou/health/repository/HealthAlertRepository.java`
- Modify: `030-backend/src/main/java/com/xiaou/health/repository/HealthAssessmentRepository.java`

**Step 1: 先补 repository 聚合方法**

- 用户总数、患者数、医生数、管理员数
- 已审核医生数、待审核医生数
- 咨询总数、待回复数、已回复数、已评价数
- 健康知识总数、已发布数
- 预警总数、未读预警数
- 健康评估总数

**Step 2: 为趋势统计定义最小查询范围**

- 近 7 天咨询量趋势
- 近 7 天健康数据录入趋势
- 近 7 天预警数量趋势
- 医生服务量排行

**Step 3: 在 `StatisticsService` 中统一组装管理员统计和医生统计**

- 管理员统计返回：
  - 系统基础指标
  - 近 7 天趋势数组
  - 医生服务排行
- 医生统计返回：
  - 自己的咨询总量、待回复、已回复、平均评分
  - 最近咨询列表

**Step 4: 在 `StatisticsController` 中暴露接口**

- `GET /api/statistics/admin`
- `GET /api/statistics/doctor`

**Step 5: 基础权限校验**

- 管理员接口只允许 `ADMIN`
- 医生接口只允许 `DOCTOR`

### Task 2: 用测试锁定统计行为

**Files:**
- Modify: `030-backend/src/test/java/com/xiaou/health/HealthManagementApplicationTests.java`

**Step 1: 新增管理员统计测试**

- 登录管理员
- 调 `GET /api/statistics/admin`
- 断言：
  - 返回 `200`
  - 用户、医生、患者、知识、咨询等指标存在
  - 趋势数组存在

**Step 2: 新增医生统计测试**

- 登录默认医生
- 调 `GET /api/statistics/doctor`
- 断言：
  - 返回 `200`
  - 咨询统计字段存在
  - 最近咨询列表字段存在

**Step 3: 新增越权测试**

- 患者访问管理员统计接口返回 `403`
- 管理员访问医生统计接口返回 `403`

### Task 3: 接入前端统计 API

**Files:**
- Create: `030-frontend/src/api/statistics.js`

**Step 1: 添加管理员统计接口封装**

- `getAdminStatistics()`

**Step 2: 添加医生统计接口封装**

- `getDoctorStatistics()`

### Task 4: 改造管理员 Dashboard

**Files:**
- Modify: `030-frontend/src/pages/admin/Dashboard.jsx`

**Step 1: 用真实接口替换静态数字**

- 用户总数
- 医生数量
- 患者数量
- 待审核医生
- 咨询总量
- 未读预警数

**Step 2: 增加至少一个趋势图**

- 优先使用近 7 天咨询量趋势

**Step 3: 增加医生服务量排行**

- 让管理员端不仅有总数，也有服务对比

### Task 5: 改造医生 Dashboard

**Files:**
- Modify: `030-frontend/src/pages/doctor/Dashboard.jsx`

**Step 1: 接入医生统计接口**

- 总咨询量
- 待回复
- 已回复
- 平均评分

**Step 2: 保留并强化待处理咨询列表**

- 展示最近待处理咨询
- 展示最近已完成咨询摘要

### Task 6: 验证与文档回填

**Files:**
- Modify: `docs/checks/030-smart-health-management-platform.md`
- Modify: `docs/project-check-tracker.md`
- Modify: `task_plan.md`
- Modify: `findings.md`
- Modify: `progress.md`

**Step 1: 跑后端测试**

Run: `mvn test`
Expected: `BUILD SUCCESS`

**Step 2: 跑前端构建**

Run: `npm run build`
Expected: `built in ...`

**Step 3: 做启动抽测**

- 后端启动后调管理员/医生统计接口
- 前端启动后验证 `/admin/dashboard`、`/doctor/dashboard` 页面可访问

**Step 4: 更新巡检报告**

- 明确“统计分析”已从占位升级为基础闭环
- 明确剩余缺口只保留“报表导出、自定义报表、系统管理”等更深层模块
