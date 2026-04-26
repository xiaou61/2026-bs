# 087 课程管理系统设计文档

## 项目定位

087 项目定位为高校教务型课程管理系统，覆盖管理员、教师、学生三类核心角色，围绕组织管理、课程开设、选课排课、教学过程管理与结果管理形成完整业务闭环。

## 用户角色

- 管理员：院系、专业、班级、学期、课程、排课、公告、统计、用户维护
- 教师：查看授课课程、发布课程资源、登记考勤、录入成绩、查看评教结果
- 学生：查看可选课程、提交选课、查看课表、查看课程资源、查看考勤成绩、提交评教

## 核心模块

### 基础组织模块

- 院系管理
- 专业管理
- 班级管理
- 学期管理
- 用户管理

### 教务模块

- 课程管理
- 排课管理
- 选课管理
- 我的课表

### 教学过程模块

- 课程资源管理
- 考勤管理
- 成绩管理
- 评教管理

### 运营模块

- 公告管理
- 数据统计看板

## 技术架构

### 后端

- Spring Boot 2.7.x
- MyBatis 3.5.x + XML
- PageHelper
- MySQL 8.0
- Redis 7.x
- JWT

### 前端

- Vue 3.4
- Vue Router 4
- Pinia
- Axios
- Element Plus
- ECharts

## 数据模型

共规划 14 张核心表：

1. `sys_user`
2. `department_info`
3. `major_info`
4. `class_info`
5. `academic_term`
6. `course_info`
7. `course_schedule`
8. `course_selection`
9. `course_resource`
10. `attendance_record`
11. `score_record`
12. `course_evaluation`
13. `system_notice`
14. `operation_log`

## 业务规则

- 管理员拥有全部维护权限
- 教师仅能处理自己授课课程的数据
- 学生仅能处理自己的选课、考勤、成绩和评教记录
- 同一学生同一排课记录只能选课一次
- 课程资源仅允许授课教师发布
- 成绩录入后学生可查看
- 同一学生同一排课记录只能评教一次
- 公告可面向全部角色统一展示

## 交付范围

- `087-backend` 完整后端项目
- `087-frontend` 完整前端项目
- `087-backend/sql/init.sql`
- `087-backend/PRD.md`
- `087-backend/PLAN.md`
- `docs/plans/2026-03-14-087-course-management-design.md`
- `docs/plans/2026-03-14-087-course-management.md`
