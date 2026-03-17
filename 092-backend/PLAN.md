# 092项目实施计划

## 问题陈述
需要构建一个完整的蓝天幼儿园管理系统，实现园区基础资料、幼儿成长档案、活动安排、食谱发布、考勤晨检、接送反馈和统计分析的一体化管理流程。

## 当前状态
- 根目录已有多个前后端项目模板可复用
- 092 项目目录已创建，当前内容已完成幼儿园系统场景改造
- 需要将数据库、后端接口、前端页面和项目总览全部切换为幼儿园业务场景

## 实施方案

### 第一阶段: 后端开发
1. 基础架构
- 调整 pom.xml、application.yml、启动类、包名
- 保留 Result、异常处理、JWT 鉴权、Redis 配置
- 将角色权限统一为管理员/教师/家长

2. 数据库脚本
- 重写 sql/init.sql
- 完成 14 张表与测试数据

3. 实体与 Mapper
- 创建园区、年级、班级、学期、幼儿档案、活动、活动安排、食谱、考勤、晨检、反馈、公告、接送记录实体
- 创建 Mapper 接口与 XML

4. Service 层
- 登录与角色权限
- 园区、年级、班级、学期管理
- 幼儿档案与活动管理
- 食谱、考勤、晨检、反馈、公告与统计

5. Controller 层
- AuthController
- CampusController
- GradeController
- ClassInfoController
- TermController
- ActivityController
- ScheduleController
- ChildController
- RecipeController
- AttendanceController
- HealthController
- FeedbackController
- NoticeController
- StatisticsController
- UserController

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
- 园区管理页
- 年级管理页
- 班级管理页
- 学期管理页
- 幼儿档案页
- 课程活动页
- 活动安排页
- 每周食谱页
- 入园考勤页
- 健康晨检页
- 家园反馈页
- 公告页

3. 联调优化
- 按角色显示菜单
- 字段映射与状态字典统一
- 表单校验与错误提示统一

## 交付结果
- 092-backend 后端完整代码
- 092-frontend 前端完整代码
- 蓝天幼儿园管理系统数据库脚本与测试数据
- 092-backend/PRD.md
- 092-backend/PLAN.md
