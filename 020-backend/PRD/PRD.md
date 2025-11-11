# 校园学习资源共享平台 PRD

## 项目概述

校园知识库与学习资源共享平台是一个面向大学生的学习资源分享和协作学习平台，支持资源上传分享、知识付费、学习小组、题库系统、在线答疑等功能。

## 核心功能模块

### 1. 用户系统
- 用户注册/登录
- 用户信息管理
- 积分系统
- 用户等级

### 2. 资源管理
- 资源上传（课件、笔记、考试资料）
- 资源分类（编程、数学、算法等）
- 资源搜索和筛选
- 资源详情查看
- 资源下载（积分兑换）
- 资源评价和评分
- 下载量和浏览量统计

### 3. 学习小组
- 创建学习小组
- 加入/退出小组
- 小组成员管理
- 小组分类
- 小组搜索

### 4. 题库系统
- 题目管理（选择题、编程题、算法题等）
- 题目分类（学科、难度、类型）
- 随机刷题
- 智能组卷
- 错题本功能
- 错题统计

### 5. 答疑系统
- 提问功能
- 悬赏提问（积分悬赏）
- 回答问题
- 采纳答案
- 点赞功能
- 问题分类和搜索

### 6. 笔记系统
- Markdown笔记编辑
- 笔记分类和标签
- 公开/私密笔记
- 笔记点赞
- 笔记浏览统计

### 7. 积分系统
- 上传资源获得积分
- 下载资源消耗积分
- 回答问题获得积分
- 积分记录查询

## 技术架构

### 后端技术栈
- Spring Boot 3.2.0
- MyBatis Plus 3.5.5
- MySQL 8.0
- JWT 认证
- Hutool 工具库

### 前端技术栈
- Vue 3
- Element Plus
- Axios
- Vue Router
- Pinia

## 数据库设计

### 主要数据表
1. user - 用户表
2. resource - 资源表
3. study_group - 学习小组表
4. group_member - 小组成员表
5. question - 题目表
6. wrong_question - 错题表
7. question_answer - 提问表
8. answer - 回答表
9. note - 笔记表
10. resource_rating - 资源评价表
11. points_record - 积分记录表

## API 接口设计

### 用户模块
- POST /api/user/register - 用户注册
- POST /api/user/login - 用户登录
- GET /api/user/info - 获取用户信息
- PUT /api/user/update - 更新用户信息
- GET /api/user/points - 获取用户积分

### 资源模块
- POST /api/resource/upload - 上传资源
- GET /api/resource/list - 资源列表
- GET /api/resource/{id} - 资源详情
- POST /api/resource/download/{id} - 下载资源
- POST /api/resource/rate - 评价资源
- GET /api/resource/ratings/{resourceId} - 获取评价
- GET /api/resource/my - 我的资源

### 学习小组模块
- POST /api/group/create - 创建小组
- GET /api/group/list - 小组列表
- GET /api/group/{id} - 小组详情
- POST /api/group/join - 加入小组
- POST /api/group/leave - 退出小组
- GET /api/group/members/{groupId} - 小组成员
- GET /api/group/my - 我的小组

### 题库模块
- POST /api/question/add - 添加题目
- GET /api/question/list - 题目列表
- GET /api/question/{id} - 题目详情
- POST /api/question/random - 随机获取题目
- POST /api/question/generate-paper - 生成试卷
- POST /api/question/wrong/add - 添加错题
- GET /api/question/wrong/list - 错题列表
- DELETE /api/question/wrong/{id} - 删除错题

### 答疑模块
- POST /api/qa/ask - 提问
- GET /api/qa/list - 问题列表
- GET /api/qa/{id} - 问题详情
- GET /api/qa/my - 我的提问
- POST /api/qa/answer - 回答问题
- GET /api/qa/answers/{questionId} - 获取回答
- POST /api/qa/accept - 采纳答案
- POST /api/qa/like/{answerId} - 点赞回答

### 笔记模块
- POST /api/note/create - 创建笔记
- PUT /api/note/update - 更新笔记
- GET /api/note/list - 笔记列表
- GET /api/note/{id} - 笔记详情
- GET /api/note/my - 我的笔记
- DELETE /api/note/{id} - 删除笔记
- POST /api/note/like/{id} - 点赞笔记

## 项目亮点

1. **完善的积分系统** - 上传资源获得积分，下载消耗积分，形成良性循环
2. **知识付费** - 优质资源可设置积分下载，激励内容创作
3. **协作学习** - 学习小组功能促进学生交流合作
4. **智能题库** - 支持随机组卷、错题本，帮助学生高效复习
5. **悬赏答疑** - 付费提问快速获得优质答案
6. **Markdown笔记** - 支持Markdown格式，方便记录和分享学习笔记

