# 069 科任教师考评系统

## 项目概述
- 项目简介：面向学校教学管理场景的科任教师考评系统，支持管理员发布考评任务、学生在线评教、教师查看结果并发起申诉。
- 核心功能：账号权限、基础档案、考评任务、评教打分、申诉处理、公告发布、数据看板。
- 技术栈：Spring Boot 2.7.18 + MyBatis-Plus 3.5.5 + Redis + MySQL 8 + Vue3 + Element Plus + Vite。

## 功能需求
### 1. 账号与权限模块
- 登录、注册、退出、获取当前用户、修改密码
- 用户角色：管理员 ADMIN、教师 TEACHER、学生 STUDENT
- 管理员可维护用户账号、角色和状态

### 2. 基础档案模块
- 科目管理：科目名称、编码、状态
- 班级管理：年级、班级名称、班主任、状态
- 教师档案：关联系统用户、工号、职称、所属科目、状态
- 评价指标管理：指标名称、维度、权重、状态、排序

### 3. 考评任务模块
- 管理员创建考评任务并关联班级和教师
- 任务包含学期、开始时间、结束时间、状态
- 学生按任务提交评教结果
- 教师查看自己被评任务和评分结果

### 4. 评教记录模块
- 学生按任务提交五维评分与评语
- 评分维度：教学态度、教学内容、教学方法、课堂管理、作业反馈
- 系统自动计算总分
- 限制同一学生对同一任务只能提交一次

### 5. 申诉模块
- 教师针对评教记录发起申诉
- 管理员审核并回复申诉结果
- 申诉状态流转：待处理、已通过、已驳回

### 6. 公告与看板模块
- 管理员发布、停用公告
- 全角色可查看有效公告
- 看板支持统计指标、近7日评教趋势、任务状态分布

## 技术设计
### 数据库设计
1. `sys_user`
- 字段：id, username, password, nickname, phone, email, role, class_id, status, last_login_time, create_time, update_time
- 索引：username 唯一索引，class_id 索引

2. `subject_info`
- 字段：id, subject_name, subject_code, status, create_time, update_time
- 索引：subject_code 唯一索引

3. `teaching_class`
- 字段：id, grade_name, class_name, head_teacher, status, create_time, update_time
- 索引：grade_name + class_name 唯一索引

4. `teacher_profile`
- 字段：id, user_id, teacher_no, subject_id, title_name, status, create_time, update_time
- 索引：teacher_no 唯一索引，user_id 唯一索引

5. `evaluation_indicator`
- 字段：id, indicator_name, dimension_name, weight_value, sort_no, status, create_time, update_time
- 索引：dimension_name，sort_no

6. `evaluation_task`
- 字段：id, task_name, term_name, class_id, teacher_id, start_time, end_time, status, creator_id, create_time, update_time
- 索引：class_id，teacher_id，status

7. `evaluation_record`
- 字段：id, task_id, evaluator_id, teacher_id, class_id, attitude_score, content_score, method_score, manage_score, homework_score, total_score, comment_text, create_time, update_time
- 索引：task_id + evaluator_id 唯一索引，teacher_id，class_id

8. `evaluation_appeal`
- 字段：id, record_id, task_id, teacher_id, reason_text, reply_text, status, handle_by, handle_time, create_time, update_time
- 索引：teacher_id，status

9. `evaluation_notice`
- 字段：id, title, content_text, status, publish_time, creator_id, create_time, update_time
- 索引：status，publish_time

### API接口设计
#### 1. 认证接口
- POST `/api/auth/login`
- POST `/api/auth/register`
- GET `/api/auth/info`
- PUT `/api/auth/password`
- POST `/api/auth/logout`

#### 2. 用户接口
- GET `/api/user/page`
- POST `/api/user`
- PUT `/api/user`
- PUT `/api/user/status`
- PUT `/api/user/profile`
- DELETE `/api/user/{id}`

#### 3. 基础档案接口
- 科目：`/api/subject/list`、`/api/subject/page`、`/api/subject`、`/api/subject/status`
- 班级：`/api/class/list`、`/api/class/page`、`/api/class`、`/api/class/status`
- 教师：`/api/teacher/list`、`/api/teacher/page`、`/api/teacher`、`/api/teacher/status`
- 指标：`/api/indicator/list`、`/api/indicator/page`、`/api/indicator`、`/api/indicator/status`

#### 4. 业务接口
- 任务：`/api/task/page`、`/api/task/my-page`、`/api/task`、`/api/task/status`
- 记录：`/api/record/page`、`/api/record/my-page`、`/api/record`、`/api/record/{id}`
- 申诉：`/api/appeal/page`、`/api/appeal/my-page`、`/api/appeal`、`/api/appeal/handle`
- 公告：`/api/notice/list`、`/api/notice/page`、`/api/notice`、`/api/notice/status`

#### 5. 看板接口
- GET `/api/dashboard/stats`
- GET `/api/dashboard/trend`

### 项目结构
后端：
```
069-backend/
├── sql/
├── src/main/java/com/teacher/
│   ├── common/
│   ├── config/
│   ├── entity/
│   ├── mapper/
│   ├── service/
│   ├── controller/
│   └── utils/
└── src/main/resources/
```

前端：
```
069-frontend/
├── src/
│   ├── views/
│   ├── api/
│   ├── router/
│   └── store/
```

## 用户角色
- 管理员：用户、科目、班级、教师、指标、任务、公告管理；申诉处理；全局看板
- 教师：查看被评记录、发起申诉、查看公告、维护个人资料
- 学生：参与任务评教、查看我的记录、查看公告、维护个人资料

## 默认账号
- admin / 123456
- teacher1 / 123456
- student1 / 123456
