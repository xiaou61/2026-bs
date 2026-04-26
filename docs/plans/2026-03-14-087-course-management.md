# 087 Course Management System Implementation Plan

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** 构建一个包含三角色、课程开设、选课、排课、资源、考勤、成绩、评教与统计能力的课程管理系统。

**Architecture:** 后端使用 Spring Boot + MyBatis XML 提供角色鉴权、分页查询和教务业务接口；前端使用 Vue 3 + Element Plus 构建登录后单页管理系统，通过不同角色显示不同菜单和业务操作。

**Tech Stack:** Spring Boot 2.7、MyBatis、PageHelper、MySQL、Redis、JWT、Vue 3、Vue Router、Pinia、Axios、Element Plus、ECharts

---

### Task 1: 项目文档与目录初始化

**Files:**
- Create: `087-backend/PRD.md`
- Create: `087-backend/PLAN.md`
- Create: `087-backend/sql/init.sql`
- Create: `087-frontend/package.json`

**Step 1: 写项目文档**
- 明确课程管理系统产品边界、角色、模块、表设计、接口设计

**Step 2: 建立目录结构**
- 创建前后端项目基础目录

**Step 3: 写数据库脚本**
- 建表并初始化默认账号、组织数据、课程数据、公告数据

### Task 2: 后端基础设施

**Files:**
- Create: `087-backend/src/main/java/com/course/CourseApplication.java`
- Create: `087-backend/src/main/java/com/course/common/*`
- Create: `087-backend/src/main/java/com/course/config/*`
- Create: `087-backend/src/main/java/com/course/utils/JwtUtils.java`
- Create: `087-backend/src/main/resources/application.yml`

**Step 1: 建立统一返回与异常处理**
- Result
- BusinessException
- GlobalExceptionHandler

**Step 2: 建立鉴权与配置**
- JWT 工具
- JWT 拦截器
- Redis 配置
- WebMvc 配置
- Jackson 配置

### Task 3: 后端领域模型与数据访问

**Files:**
- Create: `087-backend/src/main/java/com/course/entity/*`
- Create: `087-backend/src/main/java/com/course/mapper/*`
- Create: `087-backend/src/main/resources/mapper/*.xml`

**Step 1: 创建 14 个实体**
- 用户、组织、课程、排课、选课、资源、考勤、成绩、评教、公告、日志

**Step 2: 创建 Mapper 与 XML**
- 完成分页查询、条件查询、统计查询所需 SQL

### Task 4: 后端业务与接口

**Files:**
- Create: `087-backend/src/main/java/com/course/service/*`
- Create: `087-backend/src/main/java/com/course/controller/*`

**Step 1: 用户与权限**
- 登录、退出、个人信息

**Step 2: 基础组织**
- 院系、专业、班级、学期管理

**Step 3: 教务与教学**
- 课程、排课、选课、资源、考勤、成绩、评教

**Step 4: 公告与统计**
- 公告维护
- 数据看板

### Task 5: 前端基础工程

**Files:**
- Create: `087-frontend/index.html`
- Create: `087-frontend/vite.config.js`
- Create: `087-frontend/src/main.js`
- Create: `087-frontend/src/App.vue`
- Create: `087-frontend/src/router/index.js`
- Create: `087-frontend/src/api/*`
- Create: `087-frontend/src/store/user.js`

**Step 1: 建立请求层与用户状态**
- Axios 拦截器
- token 持久化

**Step 2: 建立角色化布局**
- 登录页
- 主布局页
- 菜单按角色显示

### Task 6: 前端页面开发

**Files:**
- Create: `087-frontend/src/views/*`

**Step 1: 基础页面**
- 登录页
- 数据看板
- 公告页

**Step 2: 管理员页面**
- 院系、专业、班级、学期、课程、排课

**Step 3: 教师页面**
- 我的课程、资源管理、考勤管理、成绩管理、评教结果

**Step 4: 学生页面**
- 选课中心、我的课表、课程资源、我的成绩、我的评教

### Task 7: 联调与收尾

**Files:**
- Modify: `087-backend/PRD.md`
- Modify: `087-backend/PLAN.md`

**Step 1: 对齐字段与状态**
- 保证前后端参数名与状态值一致

**Step 2: 检查关键流程**
- 登录
- 选课
- 发布资源
- 录入考勤
- 录入成绩
- 提交评教

**Step 3: 输出交付说明**
- 说明本轮已完成内容
- 说明未做编译验证
