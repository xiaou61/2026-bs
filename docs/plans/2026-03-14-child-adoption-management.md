# Child Adoption Management Implementation Plan

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** 构建一个包含管理后台与申请人网页端的孩童收养信息管理系统，完成从儿童信息管理到申请审核、匹配审批、协议登记与回访跟踪的完整业务闭环。

**Architecture:** 后端采用 Spring Boot + MyBatis-Plus + MySQL + Redis + JWT 的前后端分离架构，围绕用户认证、儿童档案、申请流转和统计模块组织接口。前端采用 Vue3 + Element Plus 构建双角色界面，后台负责管理流程，门户负责信息浏览与申请提交。

**Tech Stack:** Spring Boot 2.7.18、MyBatis-Plus 3.5.3、MySQL 8.0、Redis 7.x、JWT、Vue 3.4.0、Element Plus 2.4.4、Pinia、Axios、ECharts 5.4.3

---

### Task 1: 后端基础工程

**Files:**
- Create: `088-backend/pom.xml`
- Create: `088-backend/src/main/java/com/adoption/ChildAdoptionApplication.java`
- Create: `088-backend/src/main/java/com/adoption/common/Result.java`
- Create: `088-backend/src/main/java/com/adoption/common/BusinessException.java`
- Create: `088-backend/src/main/java/com/adoption/common/GlobalExceptionHandler.java`
- Create: `088-backend/src/main/java/com/adoption/config/JwtInterceptor.java`
- Create: `088-backend/src/main/java/com/adoption/config/WebMvcConfig.java`
- Create: `088-backend/src/main/java/com/adoption/config/RedisConfig.java`
- Create: `088-backend/src/main/java/com/adoption/config/MybatisPlusConfig.java`
- Create: `088-backend/src/main/java/com/adoption/config/JacksonConfig.java`
- Create: `088-backend/src/main/java/com/adoption/utils/JwtUtils.java`
- Create: `088-backend/src/main/resources/application.yml`
- Test: `088-backend/src/test/java/com/adoption/common/ResultTest.java`
- Test: `088-backend/src/test/java/com/adoption/utils/JwtUtilsTest.java`

**Step 1: Write the failing test**

为 `Result.success` 和 `JwtUtils.generateToken/getUserId` 编写最小失败测试，先定义统一返回结构与 Token 基本行为。

**Step 2: Run test to verify it fails**

Run: `mvn -Dtest=ResultTest,JwtUtilsTest test`
Expected: FAIL，提示 `Result` 或 `JwtUtils` 不存在，或对应方法未定义。

**Step 3: Write minimal implementation**

创建基础工程、统一返回、异常处理、JWT 工具和基础配置，使测试通过并支撑后续模块开发。

**Step 4: Run test to verify it passes**

Run: `mvn -Dtest=ResultTest,JwtUtilsTest test`
Expected: PASS

**Step 5: Commit**

```bash
git add 088-backend
git commit -m "feat: initialize 088 backend foundation"
```

### Task 2: 数据库脚本与实体建模

**Files:**
- Create: `088-backend/sql/init.sql`
- Create: `088-backend/src/main/java/com/adoption/entity/SysUser.java`
- Create: `088-backend/src/main/java/com/adoption/entity/AdopterProfile.java`
- Create: `088-backend/src/main/java/com/adoption/entity/ChildProfile.java`
- Create: `088-backend/src/main/java/com/adoption/entity/ChildHealthRecord.java`
- Create: `088-backend/src/main/java/com/adoption/entity/AdoptionApplication.java`
- Create: `088-backend/src/main/java/com/adoption/entity/ApplicationMaterial.java`
- Create: `088-backend/src/main/java/com/adoption/entity/HomeVisitRecord.java`
- Create: `088-backend/src/main/java/com/adoption/entity/MatchRecord.java`
- Create: `088-backend/src/main/java/com/adoption/entity/ApprovalRecord.java`
- Create: `088-backend/src/main/java/com/adoption/entity/AdoptionAgreement.java`
- Create: `088-backend/src/main/java/com/adoption/entity/FollowUpRecord.java`
- Create: `088-backend/src/main/java/com/adoption/entity/SystemNotice.java`
- Test: `088-backend/src/test/java/com/adoption/entity/EntityMappingTest.java`

**Step 1: Write the failing test**

编写实体映射测试，至少校验关键实体存在、表名与主键注解正确，例如 `ChildProfile`、`AdoptionApplication`、`FollowUpRecord`。

**Step 2: Run test to verify it fails**

Run: `mvn -Dtest=EntityMappingTest test`
Expected: FAIL，提示实体类不存在或注解缺失。

**Step 3: Write minimal implementation**

补齐 SQL 脚本、12 张表结构、初始化数据和所有实体类，保证字段与表结构一致。

**Step 4: Run test to verify it passes**

Run: `mvn -Dtest=EntityMappingTest test`
Expected: PASS

**Step 5: Commit**

```bash
git add 088-backend/sql 088-backend/src/main/java/com/adoption/entity 088-backend/src/test/java/com/adoption/entity
git commit -m "feat: add 088 database schema and entities"
```

### Task 3: 认证与基础资料接口

**Files:**
- Create: `088-backend/src/main/java/com/adoption/dto/LoginDTO.java`
- Create: `088-backend/src/main/java/com/adoption/dto/RegisterDTO.java`
- Create: `088-backend/src/main/java/com/adoption/dto/UserInfoDTO.java`
- Create: `088-backend/src/main/java/com/adoption/mapper/SysUserMapper.java`
- Create: `088-backend/src/main/java/com/adoption/mapper/AdopterProfileMapper.java`
- Create: `088-backend/src/main/java/com/adoption/service/AuthService.java`
- Create: `088-backend/src/main/java/com/adoption/service/UserService.java`
- Create: `088-backend/src/main/java/com/adoption/controller/AuthController.java`
- Create: `088-backend/src/main/java/com/adoption/controller/UserController.java`
- Test: `088-backend/src/test/java/com/adoption/service/AuthServiceTest.java`
- Test: `088-backend/src/test/java/com/adoption/service/UserServiceTest.java`

**Step 1: Write the failing test**

先为登录成功、注册成功、重复用户名拦截、申请人资料查询与更新编写失败测试。

**Step 2: Run test to verify it fails**

Run: `mvn -Dtest=AuthServiceTest,UserServiceTest test`
Expected: FAIL，提示服务类不存在或行为未实现。

**Step 3: Write minimal implementation**

实现用户登录注册、Token 生成、角色信息返回、申请人资料查询与保存。

**Step 4: Run test to verify it passes**

Run: `mvn -Dtest=AuthServiceTest,UserServiceTest test`
Expected: PASS

**Step 5: Commit**

```bash
git add 088-backend/src/main/java/com/adoption/dto 088-backend/src/main/java/com/adoption/mapper 088-backend/src/main/java/com/adoption/service 088-backend/src/main/java/com/adoption/controller 088-backend/src/test/java/com/adoption/service
git commit -m "feat: add auth and adopter profile modules"
```

### Task 4: 儿童档案与公开查询接口

**Files:**
- Create: `088-backend/src/main/java/com/adoption/mapper/ChildProfileMapper.java`
- Create: `088-backend/src/main/java/com/adoption/mapper/ChildHealthRecordMapper.java`
- Create: `088-backend/src/main/java/com/adoption/service/ChildService.java`
- Create: `088-backend/src/main/java/com/adoption/controller/ChildController.java`
- Test: `088-backend/src/test/java/com/adoption/service/ChildServiceTest.java`

**Step 1: Write the failing test**

编写儿童分页查询、公开列表过滤、新增儿童档案、更新收养状态的失败测试。

**Step 2: Run test to verify it fails**

Run: `mvn -Dtest=ChildServiceTest test`
Expected: FAIL

**Step 3: Write minimal implementation**

实现儿童档案 CRUD、健康信息联动查询、公开列表与详情接口。

**Step 4: Run test to verify it passes**

Run: `mvn -Dtest=ChildServiceTest test`
Expected: PASS

**Step 5: Commit**

```bash
git add 088-backend/src/main/java/com/adoption/mapper/ChildProfileMapper.java 088-backend/src/main/java/com/adoption/mapper/ChildHealthRecordMapper.java 088-backend/src/main/java/com/adoption/service/ChildService.java 088-backend/src/main/java/com/adoption/controller/ChildController.java 088-backend/src/test/java/com/adoption/service/ChildServiceTest.java
git commit -m "feat: add child profile management module"
```

### Task 5: 收养申请、审核、家访、匹配、协议与回访接口

**Files:**
- Create: `088-backend/src/main/java/com/adoption/mapper/AdoptionApplicationMapper.java`
- Create: `088-backend/src/main/java/com/adoption/mapper/ApplicationMaterialMapper.java`
- Create: `088-backend/src/main/java/com/adoption/mapper/HomeVisitRecordMapper.java`
- Create: `088-backend/src/main/java/com/adoption/mapper/MatchRecordMapper.java`
- Create: `088-backend/src/main/java/com/adoption/mapper/ApprovalRecordMapper.java`
- Create: `088-backend/src/main/java/com/adoption/mapper/AdoptionAgreementMapper.java`
- Create: `088-backend/src/main/java/com/adoption/mapper/FollowUpRecordMapper.java`
- Create: `088-backend/src/main/java/com/adoption/service/ApplicationService.java`
- Create: `088-backend/src/main/java/com/adoption/service/HomeVisitService.java`
- Create: `088-backend/src/main/java/com/adoption/service/MatchService.java`
- Create: `088-backend/src/main/java/com/adoption/service/AgreementService.java`
- Create: `088-backend/src/main/java/com/adoption/service/FollowService.java`
- Create: `088-backend/src/main/java/com/adoption/controller/ApplicationController.java`
- Create: `088-backend/src/main/java/com/adoption/controller/HomeVisitController.java`
- Create: `088-backend/src/main/java/com/adoption/controller/MatchController.java`
- Create: `088-backend/src/main/java/com/adoption/controller/AgreementController.java`
- Create: `088-backend/src/main/java/com/adoption/controller/FollowController.java`
- Test: `088-backend/src/test/java/com/adoption/service/ApplicationServiceTest.java`
- Test: `088-backend/src/test/java/com/adoption/service/MatchServiceTest.java`
- Test: `088-backend/src/test/java/com/adoption/service/FollowServiceTest.java`

**Step 1: Write the failing test**

编写申请提交、状态流转、审核通过/驳回、家访登记、匹配审批、协议登记、回访新增的失败测试。

**Step 2: Run test to verify it fails**

Run: `mvn -Dtest=ApplicationServiceTest,MatchServiceTest,FollowServiceTest test`
Expected: FAIL

**Step 3: Write minimal implementation**

实现申请主流程服务，保证状态转换合法，并将审核意见、家访结果、匹配记录、协议和回访记录串联起来。

**Step 4: Run test to verify it passes**

Run: `mvn -Dtest=ApplicationServiceTest,MatchServiceTest,FollowServiceTest test`
Expected: PASS

**Step 5: Commit**

```bash
git add 088-backend/src/main/java/com/adoption/mapper 088-backend/src/main/java/com/adoption/service 088-backend/src/main/java/com/adoption/controller 088-backend/src/test/java/com/adoption/service
git commit -m "feat: add adoption workflow modules"
```

### Task 6: 公告与统计接口

**Files:**
- Create: `088-backend/src/main/java/com/adoption/mapper/SystemNoticeMapper.java`
- Create: `088-backend/src/main/java/com/adoption/service/NoticeService.java`
- Create: `088-backend/src/main/java/com/adoption/service/StatisticsService.java`
- Create: `088-backend/src/main/java/com/adoption/controller/NoticeController.java`
- Create: `088-backend/src/main/java/com/adoption/controller/StatisticsController.java`
- Test: `088-backend/src/test/java/com/adoption/service/StatisticsServiceTest.java`

**Step 1: Write the failing test**

编写公告分页、新增公告、统计看板聚合数据的失败测试。

**Step 2: Run test to verify it fails**

Run: `mvn -Dtest=StatisticsServiceTest test`
Expected: FAIL

**Step 3: Write minimal implementation**

实现公告管理与仪表盘聚合统计接口。

**Step 4: Run test to verify it passes**

Run: `mvn -Dtest=StatisticsServiceTest test`
Expected: PASS

**Step 5: Commit**

```bash
git add 088-backend/src/main/java/com/adoption/mapper/SystemNoticeMapper.java 088-backend/src/main/java/com/adoption/service/NoticeService.java 088-backend/src/main/java/com/adoption/service/StatisticsService.java 088-backend/src/main/java/com/adoption/controller/NoticeController.java 088-backend/src/main/java/com/adoption/controller/StatisticsController.java 088-backend/src/test/java/com/adoption/service/StatisticsServiceTest.java
git commit -m "feat: add notice and dashboard modules"
```

### Task 7: 前端基础工程与认证

**Files:**
- Create: `088-frontend/package.json`
- Create: `088-frontend/vite.config.js`
- Create: `088-frontend/index.html`
- Create: `088-frontend/src/main.js`
- Create: `088-frontend/src/App.vue`
- Create: `088-frontend/src/router/index.js`
- Create: `088-frontend/src/api/request.js`
- Create: `088-frontend/src/api/index.js`
- Create: `088-frontend/src/store/user.js`
- Create: `088-frontend/src/views/Login.vue`
- Create: `088-frontend/src/views/Register.vue`
- Test: `088-frontend/src/__tests__/router.test.js`
- Test: `088-frontend/src/__tests__/user-store.test.js`

**Step 1: Write the failing test**

编写登录路由守卫和用户状态存储的失败测试，先约束未登录跳转与登录信息持久化行为。

**Step 2: Run test to verify it fails**

Run: `npm test -- --runInBand router.test.js user-store.test.js`
Expected: FAIL

**Step 3: Write minimal implementation**

完成前端工程初始化、请求封装、Pinia 用户状态、登录注册页面和路由守卫。

**Step 4: Run test to verify it passes**

Run: `npm test -- --runInBand router.test.js user-store.test.js`
Expected: PASS

**Step 5: Commit**

```bash
git add 088-frontend
git commit -m "feat: initialize 088 frontend foundation"
```

### Task 8: 后台管理页面

**Files:**
- Create: `088-frontend/src/views/Layout.vue`
- Create: `088-frontend/src/views/Dashboard.vue`
- Create: `088-frontend/src/views/admin/child/index.vue`
- Create: `088-frontend/src/views/admin/adopter/index.vue`
- Create: `088-frontend/src/views/admin/application/index.vue`
- Create: `088-frontend/src/views/admin/review/index.vue`
- Create: `088-frontend/src/views/admin/match/index.vue`
- Create: `088-frontend/src/views/admin/agreement/index.vue`
- Create: `088-frontend/src/views/admin/follow/index.vue`
- Create: `088-frontend/src/views/admin/notice/index.vue`
- Test: `088-frontend/src/__tests__/dashboard.test.js`

**Step 1: Write the failing test**

编写仪表盘和后台菜单可见性的失败测试，先定义角色菜单与统计展示行为。

**Step 2: Run test to verify it fails**

Run: `npm test -- --runInBand dashboard.test.js`
Expected: FAIL

**Step 3: Write minimal implementation**

实现后台布局、仪表盘、儿童管理、申请审核、匹配协议、回访和公告页面。

**Step 4: Run test to verify it passes**

Run: `npm test -- --runInBand dashboard.test.js`
Expected: PASS

**Step 5: Commit**

```bash
git add 088-frontend/src/views 088-frontend/src/router/index.js
git commit -m "feat: add admin pages for 088"
```

### Task 9: 申请人门户页面

**Files:**
- Create: `088-frontend/src/views/portal/Home.vue`
- Create: `088-frontend/src/views/portal/ChildList.vue`
- Create: `088-frontend/src/views/portal/ChildDetail.vue`
- Create: `088-frontend/src/views/portal/Profile.vue`
- Create: `088-frontend/src/views/portal/Application.vue`
- Create: `088-frontend/src/views/portal/Progress.vue`
- Create: `088-frontend/src/views/portal/Notice.vue`
- Test: `088-frontend/src/__tests__/portal-flow.test.js`

**Step 1: Write the failing test**

编写门户浏览儿童、提交申请、查看进度的失败测试。

**Step 2: Run test to verify it fails**

Run: `npm test -- --runInBand portal-flow.test.js`
Expected: FAIL

**Step 3: Write minimal implementation**

实现门户首页、儿童列表详情、我的资料、申请提交、进度查询和公告页面。

**Step 4: Run test to verify it passes**

Run: `npm test -- --runInBand portal-flow.test.js`
Expected: PASS

**Step 5: Commit**

```bash
git add 088-frontend/src/views/portal 088-frontend/src/router/index.js 088-frontend/src/api/index.js
git commit -m "feat: add applicant portal pages"
```

### Task 10: 文档更新与最终核对

**Files:**
- Modify: `readme.md`
- Modify: `readme_simple.md`
- Modify: `088-backend/PLAN.md`
- Modify: `088-backend/PRD.md`

**Step 1: Write the failing test**

列出最终交付核对项，确保项目编号、项目总数、功能模块和技术栈描述一致。

**Step 2: Run test to verify it fails**

人工核对当前文档，Expected: 至少存在项目总数未更新或新项目简介缺失。

**Step 3: Write minimal implementation**

更新项目合集说明，补充 088 项目简介、亮点、模块、规模与演示说明。

**Step 4: Run test to verify it passes**

人工核对 `readme.md` 与 `readme_simple.md`，Expected: 088 项目信息完整且格式一致。

**Step 5: Commit**

```bash
git add readme.md readme_simple.md 088-backend/PLAN.md 088-backend/PRD.md
git commit -m "docs: add 088 project documentation"
```
