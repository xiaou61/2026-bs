# 067 大学生一体化服务平台

## 项目概述
- 项目简介：面向高校场景的一体化管理平台，覆盖课程选课、活动报名、实习岗位、失物招领、公告通知与个人中心。
- 核心功能：统一账号体系、学生端业务办理、教师端发布管理、管理员端运营治理。
- 技术栈：Spring Boot 2.7.18 + MyBatis + PageHelper + Redis + MySQL 8 + Vue3 + Element Plus + ECharts。

## 功能需求
### 1. 用户与权限模块
- 用户登录、注册、退出、修改密码、个人资料维护
- 角色划分：管理员 ADMIN、教师 TEACHER、学生 STUDENT
- 管理员可维护用户、角色、状态

### 2. 课程与选课模块
- 教师/管理员发布课程、维护课程信息
- 学生浏览课程并选课
- 学生查看我的选课记录，教师/管理员查看全量选课记录

### 3. 活动与报名模块
- 教师/管理员发布校园活动
- 学生报名活动，支持取消报名
- 教师/管理员查看报名列表

### 4. 实习岗位与投递模块
- 教师/管理员发布实习岗位
- 学生投递岗位，支持填写备注与简历链接
- 教师/管理员审核投递记录

### 5. 失物招领模块
- 学生发布寻物/招领信息
- 全体用户可查询，发布者可更新状态
- 管理员可删除违规信息

### 6. 公告与看板模块
- 教师/管理员发布公告
- 全体用户可查看公告
- 管理员/教师可查看看板统计与趋势

## 技术设计
### 数据库设计
1. `sys_user`
- 字段：id, username, password, nickname, phone, email, role, status, college, major, grade, last_login_time, create_time, update_time
- 索引：username 唯一索引

2. `campus_course`
- 字段：id, name, teacher_id, credit, location, max_student, selected_count, start_date, end_date, status, description, create_time, update_time

3. `course_enroll`
- 字段：id, course_id, student_id, status, score, remark, create_time, update_time
- 索引：course_id + student_id 唯一索引

4. `campus_activity`
- 字段：id, title, organizer_id, location, start_time, end_time, max_participant, participant_count, status, content, create_time, update_time

5. `activity_signup`
- 字段：id, activity_id, student_id, status, checkin_time, remark, create_time, update_time
- 索引：activity_id + student_id 唯一索引

6. `intern_job`
- 字段：id, title, company, city, salary, deadline, publisher_id, status, description, create_time, update_time

7. `job_apply`
- 字段：id, job_id, student_id, resume_url, status, apply_note, review_note, reviewer_id, create_time, update_time
- 索引：job_id + student_id 唯一索引

8. `lost_found`
- 字段：id, type, title, item_name, contact, location, happen_time, description, image_url, status, publisher_id, create_time, update_time

9. `campus_notice`
- 字段：id, title, content, publisher_id, status, create_time, update_time

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

#### 3. 课程与选课接口
- GET `/api/course/list`
- GET `/api/course/page`
- POST `/api/course`
- PUT `/api/course`
- DELETE `/api/course/{id}`
- GET `/api/enroll/page`
- GET `/api/enroll/my-page`
- POST `/api/enroll`
- PUT `/api/enroll/status`
- DELETE `/api/enroll/{id}`

#### 4. 活动与报名接口
- GET `/api/activity/list`
- GET `/api/activity/page`
- POST `/api/activity`
- PUT `/api/activity`
- DELETE `/api/activity/{id}`
- GET `/api/signup/page`
- GET `/api/signup/my-page`
- POST `/api/signup`
- PUT `/api/signup/status`
- DELETE `/api/signup/{id}`

#### 5. 岗位与投递接口
- GET `/api/job/list`
- GET `/api/job/page`
- POST `/api/job`
- PUT `/api/job`
- DELETE `/api/job/{id}`
- GET `/api/apply/page`
- GET `/api/apply/my-page`
- POST `/api/apply`
- PUT `/api/apply/review`
- DELETE `/api/apply/{id}`

#### 6. 失物招领与公告接口
- GET `/api/lost/page`
- POST `/api/lost`
- PUT `/api/lost`
- PUT `/api/lost/status`
- DELETE `/api/lost/{id}`
- GET `/api/notice/list`
- GET `/api/notice/page`
- POST `/api/notice`
- PUT `/api/notice`
- DELETE `/api/notice/{id}`

#### 7. 看板接口
- GET `/api/dashboard/stats`
- GET `/api/dashboard/trend`

### 项目结构
后端：
```
067-backend/
├── sql/
├── src/main/java/com/student/
│   ├── common/
│   ├── config/
│   ├── entity/
│   ├── mapper/
│   ├── service/
│   ├── controller/
│   ├── utils/
│   └── vo/
└── src/main/resources/
```

前端：
```
067-frontend/
├── src/
│   ├── views/
│   ├── api/
│   ├── router/
│   └── store/
```

## 用户角色
- 管理员：用户管理、全模块管理、看板查看
- 教师：课程/活动/岗位/公告管理、看板查看
- 学生：课程选课、活动报名、岗位投递、失物发布、公告查看

## 默认账号
- admin / 123456
- teacher / 123456
- student / 123456
