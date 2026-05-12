# 多模态招聘材料解析与岗位匹配系统

## 项目概述

本项目面向企业校园招聘、社会招聘和实习生招聘流程，提供候选人档案、简历材料、证书材料、岗位需求、解析任务、匹配任务、面试排期、面试反馈和统计审计能力。系统采用 Spring Boot + MyBatis-Plus + MySQL + Redis + Vue3 技术栈，围绕“候选人建档-材料上传-解析提取-岗位匹配-面试安排-反馈归档”形成完整闭环。

## 功能需求

### 1. 用户与权限
- 管理员维护HR、候选人和面试官账号
- JWT 登录鉴权和 Redis 登录态缓存
- 不同角色控制不同菜单和操作入口

### 2. 候选人档案
- 维护候选人姓名、学历、工作年限、目标岗位、技能标签和期望薪资
- 支持按姓名、岗位、技能和状态筛选

### 3. 简历材料
- 上传简历文件地址、简历文本、学历、技能和工作年限
- 支持解析状态管理和简历信息维护

### 4. 证书材料
- 管理证书名称、类型、发证机构、材料地址和核验状态
- HR 可核验证书通过或驳回

### 5. 岗位管理
- 维护岗位名称、部门、类型、地点、薪资范围和发布状态
- 支持发布、关闭和删除岗位
- 岗位状态通过发布、关闭动作流转，不通过普通编辑直接改状态

### 6. 岗位要求
- 按岗位配置技能、学历、经验、证书等要求
- 设置权重和启用状态

### 7. 解析任务
- 为简历创建解析任务
- 启动后生成解析结果，提取学历、技能、经验和综合分
- 任务状态通过启动、完成、驳回动作流转，不通过普通编辑直接改状态

### 8. 解析结果
- 查看提取字段、解析分数和结论
- HR 可人工复核解析结果

### 9. 匹配任务
- 为候选人和岗位创建匹配任务
- 启动后生成匹配技能、缺失技能、匹配分和推荐等级
- 任务状态通过启动、完成、驳回动作流转，不通过普通编辑直接改状态

### 10. 匹配结果
- 查看候选人与岗位的匹配情况
- HR 可人工复核推荐等级和说明

### 11. 面试排期
- 按候选人和岗位安排面试官、时间、方式和地址
- 支持确认、取消和完成面试
- 排期状态通过确认、取消、完成动作流转，不通过普通编辑直接改状态

### 12. 面试反馈
- 面试官填写评分、优势、不足、建议和结果状态
- 支持按面试计划和结果筛选

### 13. 统计审计
- 首页看板仅管理员查看，用于汇总候选人、岗位、解析、匹配、面试和待处理数据
- 操作日志记录关键业务动作

## 技术设计

### 数据库设计
- sys_user
- candidate_profile
- resume_file
- certificate_record
- job_position
- job_requirement
- parsing_task
- parsing_result
- match_task
- match_result
- interview_plan
- interview_feedback
- operation_log

### API接口设计
- POST /api/auth/login
- GET /api/auth/info
- POST /api/auth/logout
- GET /api/user/page
- GET /api/candidate/page
- GET /api/resume/page
- GET /api/certificate/page
- PUT /api/certificate/verify/{id}
- GET /api/job/page
- PUT /api/job/publish/{id}
- PUT /api/job/close/{id}
- GET /api/requirement/page
- GET /api/parse-task/page
- PUT /api/parse-task/run/{id}
- GET /api/parse-result/page
- PUT /api/parse-result/review
- GET /api/match-task/page
- PUT /api/match-task/run/{id}
- GET /api/match-result/page
- PUT /api/match-result/review
- GET /api/interview/page
- PUT /api/interview/confirm/{id}
- GET /api/feedback/page
- GET /api/statistics/dashboard
- GET /api/log/page

## 用户角色

- 管理员：维护账号、首页看板和操作日志
- HR：维护候选人档案、简历、证书核验、岗位、岗位要求、解析任务、解析结果、匹配任务、匹配结果和面试安排
- 候选人：仅维护自己的候选人档案、简历和证书材料
- 面试官：仅查看分配给自己的面试排期并填写自己的反馈

## 默认账号

- admin/123456
- hr/123456
- candidate/123456
- interviewer/123456
