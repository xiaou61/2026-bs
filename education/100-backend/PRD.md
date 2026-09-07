# AI 生成文本检测与学术诚信预警系统

## 项目概述

本项目面向高校课程作业、论文初稿、实验报告和竞赛材料的学术诚信管理，提供文本提交、检测规则、AI 生成概率评估、人工复核、预警通知、整改跟踪和统计审计能力。系统采用 Spring Boot + MyBatis + PageHelper + MySQL + Redis + Vue3 技术栈，围绕“提交-检测-复核-预警-整改-归档”形成完整闭环。

## 功能需求

### 1. 用户与权限
- 管理员维护教师、学生和复核员账号
- JWT 登录鉴权和 Redis 登录态缓存
- 不同角色看到不同业务菜单和操作入口

### 2. 课程与班级
- 维护课程信息、授课教师、学期和启停状态
- 维护班级信息、专业、年级、人数和负责教师

### 3. 学生档案
- 管理学生学号、姓名、班级、专业、联系方式和状态
- 支持按班级、专业和关键词筛选学生

### 4. 作业任务
- 教师发布论文、实验报告、课程作业等任务
- 设置截止日期、检测阈值、说明和任务状态

### 5. 文本提交
- 学生提交标题、正文、引用说明和附件地址
- 系统记录字数、提交状态和提交时间

### 6. 检测规则
- 管理 AI 生成特征、重复片段、引用缺失、异常风格等规则
- 配置权重、风险等级、关键词和启停状态

### 7. 检测任务
- 为提交文本创建检测任务
- 支持启动检测、完成检测和驳回检测

### 8. 检测结果
- 生成 AI 概率、重复率、引用风险、综合风险和检测结论
- 复核员可人工复核结果并填写意见

### 9. 诚信预警
- 对高风险提交生成预警记录
- 支持处理、关闭和记录整改要求

### 10. 整改跟踪
- 学生提交整改说明和修订稿地址
- 教师或复核员审核整改状态

### 11. 申诉管理
- 学生对检测结果或预警记录提交申诉
- 复核员处理申诉并记录结论

### 12. 统计审计
- 首页展示提交数、检测数、高风险数、待处理预警等指标
- 操作日志记录核心业务行为

## 技术设计

### 数据库设计
- sys_user：系统用户
- course_info：课程信息
- class_info：班级信息
- student_profile：学生档案
- assignment_task：作业任务
- text_submission：文本提交
- detection_rule：检测规则
- detection_task：检测任务
- detection_result：检测结果
- integrity_warning：诚信预警
- rectification_record：整改记录
- appeal_record：申诉记录
- operation_log：操作日志

### API 接口设计
- POST /api/auth/login：登录
- GET /api/auth/info：当前用户信息
- POST /api/auth/logout：退出登录
- GET /api/user/page：用户分页
- GET /api/course/page：课程分页
- GET /api/class/page：班级分页
- GET /api/student/page：学生分页
- GET /api/assignment/page：作业分页
- GET /api/submission/page：提交分页
- GET /api/rule/page：规则分页
- GET /api/task/page：检测任务分页
- PUT /api/task/run/{id}：启动检测
- PUT /api/task/finish/{id}：完成检测
- GET /api/result/page：检测结果分页
- PUT /api/result/review：人工复核
- GET /api/warning/page：预警分页
- PUT /api/warning/handle：处理预警
- GET /api/rectification/page：整改分页
- PUT /api/rectification/review：整改审核
- GET /api/appeal/page：申诉分页
- PUT /api/appeal/handle：处理申诉
- GET /api/statistics/dashboard：统计看板

## 项目结构

后端:
```text
100-backend/
├── sql/init.sql
├── src/main/java/com/textintegrity/
│   ├── common/
│   ├── config/
│   ├── controller/
│   ├── entity/
│   ├── mapper/
│   ├── service/
│   └── utils/
└── src/main/resources/application.yml
```

前端:
```text
100-frontend/
├── package.json
├── vite.config.js
├── index.html
└── src/
    ├── api/
    ├── components/
    ├── router/
    ├── store/
    └── views/
```

## 用户角色

- 管理员：维护基础资料、检测规则、账号和操作日志
- 教师：发布作业、查看提交、发起检测、处理整改
- 学生：提交文本、查看预警、提交整改和申诉
- 复核员：复核检测结果、处理预警和申诉

## 默认账号

- admin/123456
- teacher/123456
- student/123456
- reviewer/123456

