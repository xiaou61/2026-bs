# 校园实习招聘与求职平台 PRD

## 项目概述

大学生实习求职一站式平台，帮助学生找到合适的实习和校招机会，帮助企业高效招聘优秀人才。

## 用户角色

- 学生：浏览岗位、投递简历、预约面试、分享经验
- 企业HR：发布岗位、查看简历、安排面试
- 管理员：平台管理

## 核心功能模块

### 1. 用户模块
- 用户注册/登录
- 个人信息管理
- 角色权限管理

### 2. 企业模块
- 企业入驻/认证
- 企业信息管理
- 企业主页展示

### 3. 岗位模块
- 岗位发布/编辑/下架
- 岗位列表/详情
- 岗位搜索/筛选
- 智能推荐（基于专业、技能匹配）

### 4. 简历模块
- 在线简历创建
- 简历信息管理
- 简历预览/导出

### 5. 投递模块
- 在线投递简历
- 投递进度跟踪（待筛选、初筛通过、面试邀约、offer、已拒绝）
- 投递历史查询

### 6. 面试模块
- 面试邀约
- 面试时间预约
- 面试类型（线上/线下）
- 面试日程管理

### 7. 经验分享模块
- 发布面经/笔试题
- 经验浏览/搜索
- 点赞/收藏

### 8. 内推模块
- 校友发布内推机会
- 内推信息展示
- 内推码管理

## 数据库设计

### 1. 用户表 (user)

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | bigint | 主键 |
| username | varchar(50) | 用户名 |
| password | varchar(255) | 密码（加密） |
| real_name | varchar(50) | 真实姓名 |
| email | varchar(100) | 邮箱 |
| phone | varchar(20) | 手机号 |
| avatar | varchar(255) | 头像 |
| role | varchar(20) | 角色（student/company/admin） |
| status | int | 状态（0禁用 1正常） |
| company_id | bigint | 关联企业ID（HR用户） |
| created_at | datetime | 创建时间 |
| updated_at | datetime | 更新时间 |

### 2. 企业表 (company)

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | bigint | 主键 |
| name | varchar(100) | 企业名称 |
| logo | varchar(255) | 企业logo |
| industry | varchar(50) | 所属行业 |
| scale | varchar(50) | 企业规模 |
| location | varchar(100) | 企业地址 |
| description | text | 企业简介 |
| website | varchar(255) | 官网 |
| status | int | 认证状态（0待审核 1已认证 2已拒绝） |
| created_at | datetime | 创建时间 |
| updated_at | datetime | 更新时间 |

### 3. 岗位表 (job)

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | bigint | 主键 |
| company_id | bigint | 企业ID |
| title | varchar(100) | 岗位名称 |
| job_type | varchar(20) | 类型（internship实习/校招fulltime） |
| category | varchar(50) | 职位类别 |
| location | varchar(100) | 工作地点 |
| salary_min | int | 最低薪资 |
| salary_max | int | 最高薪资 |
| requirement | text | 任职要求 |
| description | text | 岗位描述 |
| major | varchar(100) | 专业要求 |
| skills | varchar(255) | 技能要求 |
| education | varchar(20) | 学历要求 |
| headcount | int | 招聘人数 |
| status | int | 状态（0下架 1发布中） |
| views | int | 浏览次数 |
| created_at | datetime | 创建时间 |
| updated_at | datetime | 更新时间 |

### 4. 简历表 (resume)

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | bigint | 主键 |
| user_id | bigint | 学生用户ID |
| name | varchar(50) | 姓名 |
| gender | varchar(10) | 性别 |
| birth_date | date | 出生日期 |
| phone | varchar(20) | 联系电话 |
| email | varchar(100) | 邮箱 |
| university | varchar(100) | 院校 |
| major | varchar(50) | 专业 |
| education | varchar(20) | 学历 |
| graduation_date | date | 毕业时间 |
| skills | varchar(255) | 技能 |
| internship_experience | text | 实习经历 |
| project_experience | text | 项目经历 |
| self_introduction | text | 自我介绍 |
| attachment | varchar(255) | 附件简历 |
| created_at | datetime | 创建时间 |
| updated_at | datetime | 更新时间 |

### 5. 投递记录表 (application)

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | bigint | 主键 |
| user_id | bigint | 学生用户ID |
| job_id | bigint | 岗位ID |
| resume_id | bigint | 简历ID |
| status | varchar(20) | 状态（pending/screening/interview/offer/rejected） |
| remark | varchar(255) | 备注 |
| created_at | datetime | 投递时间 |
| updated_at | datetime | 更新时间 |

### 6. 面试预约表 (interview)

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | bigint | 主键 |
| application_id | bigint | 投递记录ID |
| user_id | bigint | 学生用户ID |
| job_id | bigint | 岗位ID |
| interview_type | varchar(20) | 面试类型（online/offline） |
| interview_time | datetime | 面试时间 |
| location | varchar(255) | 面试地点/线上链接 |
| interviewer | varchar(50) | 面试官 |
| status | varchar(20) | 状态（scheduled/completed/cancelled） |
| feedback | text | 面试反馈 |
| created_at | datetime | 创建时间 |
| updated_at | datetime | 更新时间 |

### 7. 经验分享表 (experience)

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | bigint | 主键 |
| user_id | bigint | 发布用户ID |
| company_name | varchar(100) | 公司名称 |
| job_title | varchar(100) | 岗位名称 |
| type | varchar(20) | 类型（interview面经/written笔试） |
| title | varchar(200) | 标题 |
| content | text | 内容 |
| tags | varchar(255) | 标签 |
| likes | int | 点赞数 |
| views | int | 浏览数 |
| created_at | datetime | 创建时间 |
| updated_at | datetime | 更新时间 |

### 8. 内推机会表 (referral)

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | bigint | 主键 |
| user_id | bigint | 发布用户ID（校友） |
| company_name | varchar(100) | 公司名称 |
| job_title | varchar(100) | 岗位名称 |
| description | text | 内推说明 |
| requirement | text | 要求 |
| referral_code | varchar(50) | 内推码 |
| contact_way | varchar(255) | 联系方式 |
| status | int | 状态（0已关闭 1开放中） |
| views | int | 浏览数 |
| created_at | datetime | 创建时间 |
| updated_at | datetime | 更新时间 |

## 技术栈

### 后端
- Spring Boot 3.x
- MyBatis-Plus
- MySQL 8.0
- Hutool（工具类）
- JWT（权限认证）

### 前端
- Vue 3
- Element Plus / Ant Design Vue
- Axios
- Pinia

## 接口设计

### 用户接口
- POST /api/user/register - 用户注册
- POST /api/user/login - 用户登录
- GET /api/user/info - 获取用户信息
- PUT /api/user/update - 更新用户信息

### 企业接口
- POST /api/company/register - 企业入驻
- GET /api/company/{id} - 获取企业详情
- PUT /api/company/update - 更新企业信息
- GET /api/company/list - 企业列表

### 岗位接口
- POST /api/job/publish - 发布岗位
- PUT /api/job/update - 更新岗位
- DELETE /api/job/{id} - 删除岗位
- GET /api/job/{id} - 岗位详情
- GET /api/job/list - 岗位列表
- GET /api/job/recommend - 智能推荐岗位

### 简历接口
- POST /api/resume/create - 创建简历
- PUT /api/resume/update - 更新简历
- GET /api/resume/my - 我的简历
- GET /api/resume/{id} - 查看简历

### 投递接口
- POST /api/application/submit - 投递简历
- GET /api/application/my - 我的投递记录
- GET /api/application/received - 收到的简历（HR）
- PUT /api/application/updateStatus - 更新投递状态

### 面试接口
- POST /api/interview/create - 创建面试邀约
- PUT /api/interview/update - 更新面试信息
- GET /api/interview/my - 我的面试安排
- PUT /api/interview/cancel - 取消面试

### 经验分享接口
- POST /api/experience/publish - 发布经验
- GET /api/experience/list - 经验列表
- GET /api/experience/{id} - 经验详情
- POST /api/experience/like - 点赞

### 内推接口
- POST /api/referral/publish - 发布内推
- GET /api/referral/list - 内推列表
- GET /api/referral/{id} - 内推详情
- PUT /api/referral/close - 关闭内推

## 特色功能

### 智能推荐算法
根据学生的专业、技能、期望岗位类型，匹配相似度高的岗位推荐给学生。

匹配规则：
- 专业匹配：专业完全匹配或相关专业
- 技能匹配：简历技能与岗位要求技能的交集
- 学历匹配：学生学历满足岗位要求

### 投递进度跟踪
- pending：待筛选
- screening：简历筛选中
- interview：面试邀约
- offer：发放offer
- rejected：已拒绝

## 页面规划

### 学生端
1. 首页：岗位推荐、热门企业
2. 岗位列表页：搜索、筛选
3. 岗位详情页
4. 我的简历
5. 我的投递
6. 我的面试
7. 经验广场
8. 内推广场

### 企业端
1. 岗位管理：发布、编辑、下架
2. 简历管理：收到的简历、筛选
3. 面试管理：安排面试、面试日程
4. 企业信息管理

### 管理端
1. 用户管理
2. 企业审核
3. 数据统计

