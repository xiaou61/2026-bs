# 087项目实施计划

## 问题陈述
需要构建一个完整的课程管理系统，实现基础组织维护、课程排课、学生选课、教学过程管理和结果统计分析的一体化流程。

## 当前状态
- 根目录已有多个前后端项目模板可复用
- 087 项目目录已创建
- 已完成 087 项目设计文档与 PRD 初稿

## 实施方案

### 第一阶段: 后端开发
1. 基础架构
- 创建 pom.xml
- 创建 application.yml
- 创建启动类
- 创建 Result、异常处理、JWT 鉴权
- 创建 Redis 与 WebMvc 配置

2. 数据库脚本
- 创建 sql/init.sql
- 完成 14 张表与测试数据

3. 实体与 Mapper
- 创建组织、课程、排课、选课、资源、考勤、成绩、评教、公告、日志实体
- 创建 Mapper 接口与 XML

4. Service 层
- 登录与角色权限
- 组织管理
- 课程与排课管理
- 选课与课表
- 资源、考勤、成绩、评教
- 公告与统计

5. Controller 层
- AuthController
- DepartmentController
- MajorController
- ClassController
- TermController
- CourseController
- ScheduleController
- SelectionController
- ResourceController
- AttendanceController
- ScoreController
- EvaluationController
- NoticeController
- StatisticsController

### 第二阶段: 前端开发
1. 基础工程
- package.json、vite.config.js、main.js、App.vue
- 路由与权限守卫
- Axios 封装
- Pinia 用户状态

2. 页面开发
- 登录页
- 布局页
- 仪表盘页
- 组织管理页
- 课程管理页
- 排课管理页
- 选课中心页
- 课表页
- 资源页
- 考勤页
- 成绩页
- 评教页
- 公告页

3. 联调优化
- 按角色显示菜单
- 字段映射与状态字典统一
- 表单校验与错误提示统一

## 交付结果
- 087-backend 后端完整代码
- 087-frontend 前端完整代码
- 课程管理系统数据库脚本与测试数据
- 087-backend/PRD.md
- 087-backend/PLAN.md
