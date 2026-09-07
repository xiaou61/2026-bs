
# 基于SpringBoot和Vue的贫困地区儿童资助网站

## 项目概述

### 项目简介
本项目是一个面向贫困地区儿童的资助管理平台，旨在连接爱心人士与需要帮助的儿童，实现透明化、规范化的资助管理。系统支持儿童信息管理、资助申请、捐赠管理、资金追踪等核心功能。

### 核心功能
- 儿童信息管理与展示
- 资助申请与审核
- 捐赠人管理与捐赠记录
- 资金使用追踪
- 项目进度管理
- 数据统计与报表
- 公示公告管理

### 技术栈
**后端**
- Spring Boot 2.7.14
- MyBatis-Plus 3.5.3
- MySQL 8.0
- Redis 7.0
- JWT认证
- Lombok

**前端**
- Vue 3.4.0
- Element Plus 2.4.4
- Vite 5.0.0
- Pinia 2.1.7
- Axios 1.6.2
- ECharts 5.4.3

## 功能需求

### 1. 用户管理模块
- 用户注册与登录（管理员、捐赠人、志愿者）
- 用户信息管理
- 角色权限管理
- 密码修改与找回

### 2. 儿童信息管理模块
- 儿童档案录入（基本信息、家庭情况、教育状况）
- 儿童信息展示与搜索
- 儿童照片与成长记录
- 资助状态管理

### 3. 资助申请模块
- 资助需求发布
- 申请表单填写
- 申请审核流程
- 审核结果通知

### 4. 捐赠管理模块
- 捐赠人信息管理
- 捐赠意向登记
- 捐赠记录管理
- 捐赠证书生成

### 5. 资金管理模块
- 资金收入记录
- 资金支出管理
- 资金使用追踪
- 财务报表生成

### 6. 项目管理模块
- 资助项目创建
- 项目进度跟踪
- 项目成果展示
- 项目评估报告

### 7. 反馈与互动模块
- 儿童感谢信
- 捐赠人留言
- 成长日记
- 照片分享

### 8. 统计分析模块
- 资助数据统计
- 捐赠趋势分析
- 地区分布统计
- 数据可视化展示

### 9. 公告管理模块
- 公告发布与管理
- 政策文件上传
- 新闻动态发布

## 技术设计

### 数据库设计

#### 表1: user (用户表)
- id (BIGINT, 主键)
- username (VARCHAR(50), 用户名)
- password (VARCHAR(100), 密码)
- real_name (VARCHAR(50), 真实姓名)
- phone (VARCHAR(20), 手机号)
- email (VARCHAR(100), 邮箱)
- role (VARCHAR(20), 角色: admin/donor/volunteer)
- avatar (VARCHAR(255), 头像)
- status (INT, 状态: 0禁用/1启用)
- create_time (DATETIME, 创建时间)
- update_time (DATETIME, 更新时间)

#### 表2: child (儿童信息表)
- id (BIGINT, 主键)
- name (VARCHAR(50), 姓名)
- gender (VARCHAR(10), 性别)
- birth_date (DATE, 出生日期)
- id_card (VARCHAR(18), 身份证号)
- province (VARCHAR(50), 省份)
- city (VARCHAR(50), 城市)
- district (VARCHAR(50), 区县)
- address (VARCHAR(255), 详细地址)
- school (VARCHAR(100), 就读学校)
- grade (VARCHAR(20), 年级)
- family_situation (TEXT, 家庭情况)
- health_status (VARCHAR(100), 健康状况)
- photo (VARCHAR(255), 照片)
- sponsor_status (INT, 资助状态: 0未资助/1已资助/2资助中)
- create_time (DATETIME, 创建时间)
- update_time (DATETIME, 更新时间)

#### 表3: donor (捐赠人表)
- id (BIGINT, 主键)
- user_id (BIGINT, 用户ID)
- donor_type (VARCHAR(20), 捐赠人类型: personal/enterprise)
- company_name (VARCHAR(100), 企业名称)
- contact_person (VARCHAR(50), 联系人)
- contact_phone (VARCHAR(20), 联系电话)
- total_amount (DECIMAL(10,2), 累计捐赠金额)
- donation_count (INT, 捐赠次数)
- create_time (DATETIME, 创建时间)
- update_time (DATETIME, 更新时间)

#### 表4: application (资助申请表)
- id (BIGINT, 主键)
- child_id (BIGINT, 儿童ID)
- apply_reason (TEXT, 申请理由)
- required_amount (DECIMAL(10,2), 所需金额)
- apply_status (INT, 申请状态: 0待审核/1已通过/2已拒绝)
- reviewer_id (BIGINT, 审核人ID)
- review_time (DATETIME, 审核时间)
- review_comment (TEXT, 审核意见)
- create_time (DATETIME, 创建时间)
- update_time (DATETIME, 更新时间)

#### 表5: donation (捐赠记录表)
- id (BIGINT, 主键)
- donor_id (BIGINT, 捐赠人ID)
- child_id (BIGINT, 儿童ID)
- project_id (BIGINT, 项目ID)
- amount (DECIMAL(10,2), 捐赠金额)
- donation_type (VARCHAR(20), 捐赠类型: money/material)
- material_desc (TEXT, 物资描述)
- donation_date (DATE, 捐赠日期)
- payment_method (VARCHAR(20), 支付方式)
- certificate_no (VARCHAR(50), 证书编号)
- status (INT, 状态: 0待确认/1已确认)
- create_time (DATETIME, 创建时间)
- update_time (DATETIME, 更新时间)

#### 表6: project (资助项目表)
- id (BIGINT, 主键)
- project_name (VARCHAR(100), 项目名称)
- project_desc (TEXT, 项目描述)
- target_amount (DECIMAL(10,2), 目标金额)
- current_amount (DECIMAL(10,2), 当前金额)
- start_date (DATE, 开始日期)
- end_date (DATE, 结束日期)
- project_status (INT, 项目状态: 0筹备中/1进行中/2已完成/3已终止)
- cover_image (VARCHAR(255), 封面图片)
- create_time (DATETIME, 创建时间)
- update_time (DATETIME, 更新时间)

#### 表7: fund_record (资金记录表)
- id (BIGINT, 主键)
- record_type (VARCHAR(20), 记录类型: income/expense)
- amount (DECIMAL(10,2), 金额)
- related_id (BIGINT, 关联ID)
- related_type (VARCHAR(20), 关联类型: donation/project)
- purpose (VARCHAR(255), 用途)
- operator_id (BIGINT, 操作人ID)
- record_date (DATE, 记录日期)
- remark (TEXT, 备注)
- create_time (DATETIME, 创建时间)

#### 表8: feedback (反馈互动表)
- id (BIGINT, 主键)
- feedback_type (VARCHAR(20), 反馈类型: letter/message/diary)
- child_id (BIGINT, 儿童ID)
- donor_id (BIGINT, 捐赠人ID)
- content (TEXT, 内容)
- images (TEXT, 图片列表)
- create_time (DATETIME, 创建时间)

#### 表9: growth_record (成长记录表)
- id (BIGINT, 主键)
- child_id (BIGINT, 儿童ID)
- record_date (DATE, 记录日期)
- record_type (VARCHAR(20), 记录类型: study/life/health)
- content (TEXT, 内容)
- images (TEXT, 图片)
- recorder_id (BIGINT, 记录人ID)
- create_time (DATETIME, 创建时间)

#### 表10: announcement (公告表)
- id (BIGINT, 主键)
- title (VARCHAR(200), 标题)
- content (TEXT, 内容)
- announcement_type (VARCHAR(20), 公告类型: news/policy/notice)
- cover_image (VARCHAR(255), 封面图)
- publish_status (INT, 发布状态: 0草稿/1已发布)
- view_count (INT, 浏览次数)
- publisher_id (BIGINT, 发布人ID)
- publish_time (DATETIME, 发布时间)
- create_time (DATETIME, 创建时间)
- update_time (DATETIME, 更新时间)

#### 表11: sponsor_relation (资助关系表)
- id (BIGINT, 主键)
- child_id (BIGINT, 儿童ID)
- donor_id (BIGINT, 捐赠人ID)
- sponsor_type (VARCHAR(20), 资助类型: one_time/monthly/yearly)
- sponsor_amount (DECIMAL(10,2), 资助金额)
- start_date (DATE, 开始日期)
- end_date (DATE, 结束日期)
- status (INT, 状态: 0待确认/1进行中/2已完成/3已终止)
- create_time (DATETIME, 创建时间)
- update_time (DATETIME, 更新时间)

#### 表12: statistics (统计数据表)
- id (BIGINT, 主键)
- stat_date (DATE, 统计日期)
- total_children (INT, 儿童总数)
- sponsored_children (INT, 已资助儿童数)
- total_donors (INT, 捐赠人总数)
- total_amount (DECIMAL(10,2), 总捐赠金额)
- total_projects (INT, 项目总数)
- create_time (DATETIME, 创建时间)

### API接口设计

#### 1. 用户管理接口

##### 1.1 用户登录
- 请求方式: POST
- 路径: /api/user/login
- 参数: {username, password}
- 返回: {token, userInfo}

##### 1.2 用户注册
- 请求方式: POST
- 路径: /api/user/register
- 参数: {username, password, realName, phone, email, role}
- 返回: {success}

##### 1.3 获取用户信息
- 请求方式: GET
- 路径: /api/user/info
- 参数: 无
- 返回: {userInfo}

##### 1.4 用户列表
- 请求方式: GET
- 路径: /api/user/list
- 参数: {pageNum, pageSize, username, role}
- 返回: {records, total}

##### 1.5 更新用户
- 请求方式: PUT
- 路径: /api/user/update
- 参数: {id, ...userInfo}
- 返回: {success}

##### 1.6 删除用户
- 请求方式: DELETE
- 路径: /api/user/delete/{id}
- 参数: 路径参数id
- 返回: {success}

#### 2. 儿童信息接口

##### 2.1 儿童列表
- 请求方式: GET
- 路径: /api/child/list
- 参数: {pageNum, pageSize, name, province, city, sponsorStatus}
- 返回: {records, total}

##### 2.2 儿童详情
- 请求方式: GET
- 路径: /api/child/detail/{id}
- 参数: 路径参数id
- 返回: {childInfo}

##### 2.3 添加儿童
- 请求方式: POST
- 路径: /api/child/add
- 参数: {childInfo}
- 返回: {success}

##### 2.4 更新儿童信息
- 请求方式: PUT
- 路径: /api/child/update
- 参数: {id, ...childInfo}
- 返回: {success}

##### 2.5 删除儿童
- 请求方式: DELETE
- 路径: /api/child/delete/{id}
- 参数: 路径参数id
- 返回: {success}

#### 3. 捐赠人接口

##### 3.1 捐赠人列表
- 请求方式: GET
- 路径: /api/donor/list
- 参数: {pageNum, pageSize, donorType}
- 返回: {records, total}

##### 3.2 添加捐赠人
- 请求方式: POST
- 路径: /api/donor/add
- 参数: {donorInfo}
- 返回: {success}

##### 3.3 更新捐赠人
- 请求方式: PUT
- 路径: /api/donor/update
- 参数: {id, ...donorInfo}
- 返回: {success}

##### 3.4 删除捐赠人
- 请求方式: DELETE
- 路径: /api/donor/delete/{id}
- 参数: 路径参数id
- 返回: {success}

#### 4. 资助申请接口

##### 4.1 申请列表
- 请求方式: GET
- 路径: /api/application/list
- 参数: {pageNum, pageSize, applyStatus}
- 返回: {records, total}

##### 4.2 提交申请
- 请求方式: POST
- 路径: /api/application/submit
- 参数: {childId, applyReason, requiredAmount}
- 返回: {success}

##### 4.3 审核申请
- 请求方式: PUT
- 路径: /api/application/review
- 参数: {id, applyStatus, reviewComment}
- 返回: {success}

#### 5. 捐赠记录接口

##### 5.1 捐赠列表
- 请求方式: GET
- 路径: /api/donation/list
- 参数: {pageNum, pageSize, donorId, childId}
- 返回: {records, total}

##### 5.2 添加捐赠
- 请求方式: POST
- 路径: /api/donation/add
- 参数: {donationInfo}
- 返回: {success}

##### 5.3 确认捐赠
- 请求方式: PUT
- 路径: /api/donation/confirm/{id}
- 参数: 路径参数id
- 返回: {success}

#### 6. 项目管理接口

##### 6.1 项目列表
- 请求方式: GET
- 路径: /api/project/list
- 参数: {pageNum, pageSize, projectStatus}
- 返回: {records, total}

##### 6.2 添加项目
- 请求方式: POST
- 路径: /api/project/add
- 参数: {projectInfo}
- 返回: {success}

##### 6.3 更新项目
- 请求方式: PUT
- 路径: /api/project/update
- 参数: {id, ...projectInfo}
- 返回: {success}

##### 6.4 删除项目
- 请求方式: DELETE
- 路径: /api/project/delete/{id}
- 参数: 路径参数id
- 返回: {success}

#### 7. 资金记录接口

##### 7.1 资金记录列表
- 请求方式: GET
- 路径: /api/fund/list
- 参数: {pageNum, pageSize, recordType, startDate, endDate}
- 返回: {records, total}

##### 7.2 添加资金记录
- 请求方式: POST
- 路径: /api/fund/add
- 参数: {fundRecordInfo}
- 返回: {success}

#### 8. 反馈互动接口

##### 8.1 反馈列表
- 请求方式: GET
- 路径: /api/feedback/list
- 参数: {pageNum, pageSize, feedbackType, childId}
- 返回: {records, total}

##### 8.2 添加反馈
- 请求方式: POST
- 路径: /api/feedback/add
- 参数: {feedbackInfo}
- 返回: {success}

#### 9. 成长记录接口

##### 9.1 成长记录列表
- 请求方式: GET
- 路径: /api/growth/list
- 参数: {pageNum, pageSize, childId}
- 返回: {records, total}

##### 9.2 添加成长记录
- 请求方式: POST
- 路径: /api/growth/add
- 参数: {growthRecordInfo}
- 返回: {success}

#### 10. 公告接口

##### 10.1 公告列表
- 请求方式: GET
- 路径: /api/announcement/list
- 参数: {pageNum, pageSize, announcementType}
- 返回: {records, total}

##### 10.2 添加公告
- 请求方式: POST
- 路径: /api/announcement/add
- 参数: {announcementInfo}
- 返回: {success}

##### 10.3 更新公告
- 请求方式: PUT
- 路径: /api/announcement/update
- 参数: {id, ...announcementInfo}
- 返回: {success}

##### 10.4 删除公告
- 请求方式: DELETE
- 路径: /api/announcement/delete/{id}
- 参数: 路径参数id
- 返回: {success}

#### 11. 资助关系接口

##### 11.1 资助关系列表
- 请求方式: GET
- 路径: /api/sponsor/list
- 参数: {pageNum, pageSize, childId, donorId}
- 返回: {records, total}

##### 11.2 建立资助关系
- 请求方式: POST
- 路径: /api/sponsor/add
- 参数: {sponsorInfo}
- 返回: {success}

##### 11.3 更新资助关系
- 请求方式: PUT
- 路径: /api/sponsor/update
- 参数: {id, ...sponsorInfo}
- 返回: {success}

#### 12. 统计分析接口

##### 12.1 首页统计
- 请求方式: GET
- 路径: /api/statistics/dashboard
- 参数: 无
- 返回: {totalChildren, sponsoredChildren, totalDonors, totalAmount, totalProjects}

##### 12.2 捐赠趋势
- 请求方式: GET
- 路径: /api/statistics/donation-trend
- 参数: {startDate, endDate}
- 返回: {dateList, amountList}

##### 12.3 地区分布
- 请求方式: GET
- 路径: /api/statistics/region-distribution
- 参数: 无
- 返回: {provinceList, countList}

### 项目结构

#### 后端结构
```
080-backend/
├── sql/
│   └── init.sql
├── src/main/java/com/charity/
│   ├── CharityApplication.java
│   ├── common/
│   │   ├── Result.java
│   │   ├── BusinessException.java
│   │   └── GlobalExceptionHandler.java
│   ├── config/
│   │   ├── JwtInterceptor.java
│   │   ├── WebMvcConfig.java
│   │   ├── MybatisPlusConfig.java
│   │   └── RedisConfig.java
│   ├── utils/
│   │   └── JwtUtils.java
│   ├── entity/
│   │   ├── User.java
│   │   ├── Child.java
│   │   ├── Donor.java
│   │   ├── Application.java
│   │   ├── Donation.java
│   │   ├── Project.java
│   │   ├── FundRecord.java
│   │   ├── Feedback.java
│   │   ├── GrowthRecord.java
│   │   ├── Announcement.java
│   │   ├── SponsorRelation.java
│   │   └── Statistics.java
│   ├── mapper/
│   │   ├── UserMapper.java
│   │   ├── ChildMapper.java
│   │   ├── DonorMapper.java
│   │   ├── ApplicationMapper.java
│   │   ├── DonationMapper.java
│   │   ├── ProjectMapper.java
│   │   ├── FundRecordMapper.java
│   │   ├── FeedbackMapper.java
│   │   ├── GrowthRecordMapper.java
│   │   ├── AnnouncementMapper.java
│   │   ├── SponsorRelationMapper.java
│   │   └── StatisticsMapper.java
│   ├── service/
│   │   ├── UserService.java
│   │   ├── ChildService.java
│   │   ├── DonorService.java
│   │   ├── ApplicationService.java
│   │   ├── DonationService.java
│   │   ├── ProjectService.java
│   │   ├── FundRecordService.java
│   │   ├── FeedbackService.java
│   │   ├── GrowthRecordService.java
│   │   ├── AnnouncementService.java
│   │   ├── SponsorRelationService.java
│   │   └── StatisticsService.java
│   └── controller/
│       ├── UserController.java
│       ├── ChildController.java
│       ├── DonorController.java
│       ├── ApplicationController.java
│       ├── DonationController.java
│       ├── ProjectController.java
│       ├── FundRecordController.java
│       ├── FeedbackController.java
│       ├── GrowthRecordController.java
│       ├── AnnouncementController.java
│       ├── SponsorRelationController.java
│       └── StatisticsController.java
└── src/main/resources/
    └── application.yml
```

#### 前端结构
```
080-frontend/
├── package.json
├── vite.config.js
├── index.html
└── src/
    ├── main.js
    ├── App.vue
    ├── router/
    │   └── index.js
    ├── api/
    │   ├── request.js
    │   └── index.js
    ├── store/
    │   └── user.js
    └── views/
        ├── Login.vue
        ├── Layout.vue
        ├── Dashboard.vue
        ├── child/
        │   └── index.vue
        ├── donor/
        │   └── index.vue
        ├── application/
        │   └── index.vue
        ├── donation/
        │   └── index.vue
        ├── project/
        │   └── index.vue
        ├── fund/
        │   └── index.vue
        ├── feedback/
        │   └── index.vue
        ├── growth/
        │   └── index.vue
        ├── announcement/
        │   └── index.vue
        ├── sponsor/
        │   └── index.vue
        └── user/
            └── index.vue
```

## 用户角色

### 管理员 (admin)
- 系统管理权限
- 用户管理
- 儿童信息管理
- 资助申请审核
- 项目管理
- 资金管理
- 数据统计
- 公告管理

### 捐赠人 (donor)
- 查看儿童信息
- 发起捐赠
- 查看捐赠记录
- 查看资助关系
- 查看反馈信息
- 个人信息管理

### 志愿者 (volunteer)
- 儿童信息录入
- 成长记录添加
- 反馈信息管理
- 查看项目信息

## 默认账号

- admin/123456 (管理员)
- donor/123456 (捐赠人)
- volunteer/123456 (志愿者)
