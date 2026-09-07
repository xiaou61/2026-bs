# 实现计划

## 问题陈述

构建一个围绕高校文本作业和论文初稿的 AI 生成文本检测与学术诚信预警系统，要求覆盖多角色登录、基础资料管理、文本提交、检测任务、检测结果、预警、整改、申诉和统计审计。

## 当前状态

`100-backend` 和 `100-frontend` 目录已创建，当前需要按仓库规则补齐 PRD、后端、前端和合集 README。

## 实施方案

### 第一阶段：后端开发
1. 基础架构
   - Spring Boot 2.7.18
   - MyBatis 2.3.1
   - PageHelper 1.4.7
   - Redis 登录态
   - JWT 鉴权

2. 数据库
   - 创建 `academic_integrity_100`
   - 13 张数据表
   - 初始化课程、班级、学生、作业、提交、检测、预警和默认账号

3. 通用模块
   - 统一返回 Result
   - 业务异常和全局异常处理
   - JWT 拦截器、Redis 配置和跨域配置

4. 数据访问
   - MyBatis 注解 SQL
   - CommonSqlProvider 生成分页、插入、更新、删除 SQL
   - CommonMapper 负责通用 CRUD 和业务查询

5. 业务服务
   - AuthService 登录与权限判断
   - CrudService 通用分页和保存
   - IntegrityService 检测、复核、预警、整改和统计逻辑
   - OperationLogService 操作审计

6. 控制器
   - AuthController
   - UserController
   - CourseController
   - ClassController
   - StudentController
   - AssignmentController
   - SubmissionController
   - RuleController
   - TaskController
   - ResultController
   - WarningController
   - RectificationController
   - AppealController
   - StatisticsController
   - LogController

### 第二阶段：前端开发
1. 项目结构
   - Vue3 + Vite + Element Plus
   - Axios 请求封装
   - Pinia 用户状态
   - Vue Router 路由守卫

2. 页面开发
   - 登录页
   - 布局页
   - 首页看板
   - 13 个业务管理页面

3. 交互动作
   - 检测任务启动、完成、驳回
   - 检测结果人工复核
   - 预警处理
   - 整改审核
   - 申诉处理

### 第三阶段：文档更新
1. 更新 `readme.md` 项目总数和 100 详情
2. 更新 `readme_simple.md` 项目速览
3. 更新候选清单 100 状态

## 完成标准

- 后端目录包含 PRD、PLAN、pom、application.yml、SQL、通用配置、Mapper、Service、Controller
- 前端目录包含 package、vite、router、api、store、通用 DataPage 和所有页面
- 合集 README 中 100 标记为最新
- 不执行编译验证

