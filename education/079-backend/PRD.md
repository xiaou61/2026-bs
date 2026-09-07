# 计算机学院校友网

## 项目概述
### 项目简介
计算机学院校友网是一个为计算机学院校友提供信息交流、资源共享、活动组织的综合性平台，帮助校友保持联系、促进合作。

### 核心功能
- 校友信息管理与查询
- 校友动态与新闻公告
- 校友活动组织与报名
- 班级届次管理
- 校友捐赠管理
- 校友企业与招聘信息
- 校友论坛交流
- 数据统计分析

### 技术栈
- 后端：Spring Boot 2.7.0 + MyBatis-Plus 3.5.2 + Redis + JWT
- 前端：Vue 3.4.0 + Element Plus 2.4.4 + Vite + Pinia
- 数据库：MySQL 8.0

## 功能需求

### 1. 用户管理模块
- 管理员登录/登出
- 校友注册/登录/登出
- 校友审核（管理员审核校友注册）
- 密码修改
- 个人信息维护

### 2. 校友信息模块
- 校友档案管理（基本信息、教育经历、工作经历）
- 校友信息查询（按姓名、届次、班级、公司等）
- 校友通讯录
- 校友地图分布

### 3. 新闻公告模块
- 新闻发布与管理
- 公告发布与管理
- 新闻分类管理
- 新闻评论

### 4. 校友活动模块
- 活动发布与管理
- 活动报名与取消
- 活动签到
- 活动相册
- 活动评价

### 5. 班级管理模块
- 届次管理
- 班级管理
- 班级校友列表
- 班级联络员

### 6. 捐赠管理模块
- 捐赠项目管理
- 捐赠记录管理
- 捐赠统计
- 捐赠证书

### 7. 企业招聘模块
- 校友企业登记
- 招聘信息发布
- 招聘信息审核
- 求职意向登记

### 8. 校友论坛模块
- 论坛板块管理
- 帖子发布与管理
- 帖子回复
- 帖子点赞

### 9. 系统管理模块
- 数据统计（校友分布、活跃度等）
- 系统日志
- 轮播图管理

## 技术设计

### 数据库设计

表1: user（用户表）
- id: bigint, 主键
- username: varchar(50), 用户名
- password: varchar(100), 密码
- name: varchar(50), 姓名
- role: varchar(20), 角色(admin/alumni)
- status: int, 状态(0待审核/1正常/2禁用)
- avatar: varchar(255), 头像
- phone: varchar(20), 电话
- email: varchar(100), 邮箱
- create_time: datetime
- update_time: datetime

表2: alumni_info（校友信息表）
- id: bigint, 主键
- user_id: bigint, 用户ID
- student_no: varchar(50), 学号
- grade_id: bigint, 届次ID
- class_id: bigint, 班级ID
- gender: int, 性别(0女/1男)
- birthday: date, 出生日期
- native_place: varchar(100), 籍贯
- political: varchar(50), 政治面貌
- degree: varchar(50), 学历
- major: varchar(100), 专业
- graduation_date: date, 毕业日期
- company: varchar(200), 工作单位
- position: varchar(100), 职位
- industry: varchar(100), 行业
- city: varchar(100), 所在城市
- introduction: text, 个人简介
- is_contact: int, 是否班级联络员
- create_time: datetime
- update_time: datetime

表3: grade（届次表）
- id: bigint, 主键
- name: varchar(50), 届次名称(如2020届)
- year: int, 入学年份
- description: varchar(500), 描述
- create_time: datetime

表4: class_info（班级表）
- id: bigint, 主键
- grade_id: bigint, 届次ID
- name: varchar(50), 班级名称
- major: varchar(100), 专业
- student_count: int, 学生人数
- contact_id: bigint, 联络员ID
- create_time: datetime

表5: news（新闻公告表）
- id: bigint, 主键
- title: varchar(200), 标题
- content: text, 内容
- cover: varchar(255), 封面图
- category: varchar(50), 分类(news/notice)
- view_count: int, 浏览量
- is_top: int, 是否置顶
- status: int, 状态(0草稿/1发布)
- author_id: bigint, 作者ID
- create_time: datetime
- update_time: datetime

表6: news_comment（新闻评论表）
- id: bigint, 主键
- news_id: bigint, 新闻ID
- user_id: bigint, 用户ID
- content: varchar(500), 评论内容
- create_time: datetime

表7: activity（活动表）
- id: bigint, 主键
- title: varchar(200), 活动标题
- content: text, 活动内容
- cover: varchar(255), 封面图
- address: varchar(500), 活动地点
- start_time: datetime, 开始时间
- end_time: datetime, 结束时间
- sign_deadline: datetime, 报名截止时间
- max_count: int, 最大人数
- current_count: int, 当前报名人数
- status: int, 状态(0报名中/1进行中/2已结束)
- organizer_id: bigint, 组织者ID
- create_time: datetime
- update_time: datetime

表8: activity_sign（活动报名表）
- id: bigint, 主键
- activity_id: bigint, 活动ID
- user_id: bigint, 用户ID
- sign_time: datetime, 报名时间
- check_time: datetime, 签到时间
- status: int, 状态(0已报名/1已签到/2已取消)

表9: activity_photo（活动相册表）
- id: bigint, 主键
- activity_id: bigint, 活动ID
- url: varchar(255), 图片地址
- description: varchar(200), 描述
- upload_user_id: bigint, 上传者ID
- create_time: datetime

表10: donation_project（捐赠项目表）
- id: bigint, 主键
- name: varchar(200), 项目名称
- description: text, 项目描述
- target_amount: decimal(12,2), 目标金额
- current_amount: decimal(12,2), 当前金额
- cover: varchar(255), 封面图
- status: int, 状态(0进行中/1已完成/2已关闭)
- start_time: datetime, 开始时间
- end_time: datetime, 结束时间
- create_time: datetime

表11: donation_record（捐赠记录表）
- id: bigint, 主键
- project_id: bigint, 项目ID
- user_id: bigint, 用户ID
- amount: decimal(12,2), 捐赠金额
- message: varchar(500), 留言
- is_anonymous: int, 是否匿名
- certificate_no: varchar(100), 证书编号
- status: int, 状态(0待确认/1已确认)
- create_time: datetime

表12: alumni_company（校友企业表）
- id: bigint, 主键
- user_id: bigint, 校友ID
- name: varchar(200), 企业名称
- logo: varchar(255), 企业logo
- industry: varchar(100), 行业
- scale: varchar(50), 规模
- address: varchar(500), 地址
- website: varchar(255), 官网
- introduction: text, 企业简介
- status: int, 状态(0待审核/1正常)
- create_time: datetime
- update_time: datetime

表13: job_post（招聘信息表）
- id: bigint, 主键
- company_id: bigint, 企业ID
- title: varchar(200), 职位名称
- salary: varchar(100), 薪资范围
- city: varchar(100), 工作城市
- experience: varchar(50), 经验要求
- education: varchar(50), 学历要求
- description: text, 职位描述
- contact: varchar(100), 联系方式
- status: int, 状态(0待审核/1发布中/2已下架)
- create_time: datetime
- update_time: datetime

表14: forum_category（论坛板块表）
- id: bigint, 主键
- name: varchar(100), 板块名称
- description: varchar(500), 描述
- icon: varchar(255), 图标
- sort: int, 排序
- post_count: int, 帖子数
- create_time: datetime

表15: forum_post（帖子表）
- id: bigint, 主键
- category_id: bigint, 板块ID
- user_id: bigint, 发帖人ID
- title: varchar(200), 标题
- content: text, 内容
- view_count: int, 浏览量
- reply_count: int, 回复数
- like_count: int, 点赞数
- is_top: int, 是否置顶
- is_essence: int, 是否精华
- status: int, 状态(0正常/1删除)
- last_reply_time: datetime, 最后回复时间
- create_time: datetime
- update_time: datetime

表16: forum_reply（帖子回复表）
- id: bigint, 主键
- post_id: bigint, 帖子ID
- user_id: bigint, 回复人ID
- content: text, 回复内容
- reply_to_id: bigint, 回复目标ID
- create_time: datetime

表17: forum_like（帖子点赞表）
- id: bigint, 主键
- post_id: bigint, 帖子ID
- user_id: bigint, 用户ID
- create_time: datetime

表18: banner（轮播图表）
- id: bigint, 主键
- title: varchar(200), 标题
- image: varchar(255), 图片
- url: varchar(255), 链接
- sort: int, 排序
- status: int, 状态(0禁用/1启用)
- create_time: datetime

表19: operation_log（操作日志表）
- id: bigint, 主键
- user_id: bigint, 用户ID
- username: varchar(50), 用户名
- operation: varchar(200), 操作内容
- method: varchar(200), 请求方法
- params: text, 请求参数
- ip: varchar(50), IP地址
- create_time: datetime

### API接口设计

#### 1. 认证模块
1.1 登录
- POST /api/auth/login
- 参数: {username, password}
- 返回: {token, userInfo}

1.2 注册
- POST /api/auth/register
- 参数: {username, password, name, phone, email}
- 返回: {id}

1.3 获取当前用户
- GET /api/auth/info
- 返回: {userInfo}

1.4 修改密码
- PUT /api/auth/password
- 参数: {oldPassword, newPassword}

#### 2. 用户管理
2.1 用户列表
- GET /api/user/list
- 参数: {pageNum, pageSize, name, role, status}
- 返回: {records, total}

2.2 审核用户
- PUT /api/user/audit/{id}
- 参数: {status}

2.3 更新用户状态
- PUT /api/user/status/{id}
- 参数: {status}

#### 3. 校友信息
3.1 校友列表
- GET /api/alumni/list
- 参数: {pageNum, pageSize, name, gradeId, classId, company, city}
- 返回: {records, total}

3.2 校友详情
- GET /api/alumni/{id}
- 返回: {alumniInfo}

3.3 更新校友信息
- PUT /api/alumni
- 参数: {alumniInfo}

3.4 校友通讯录
- GET /api/alumni/contacts
- 参数: {gradeId, classId}
- 返回: {list}

3.5 校友分布统计
- GET /api/alumni/distribution
- 返回: {cityDistribution, industryDistribution}

#### 4. 届次班级
4.1 届次列表
- GET /api/grade/list
- 返回: {list}

4.2 添加届次
- POST /api/grade
- 参数: {name, year, description}

4.3 班级列表
- GET /api/class/list
- 参数: {gradeId}
- 返回: {list}

4.4 添加班级
- POST /api/class
- 参数: {gradeId, name, major}

#### 5. 新闻公告
5.1 新闻列表
- GET /api/news/list
- 参数: {pageNum, pageSize, category, title}
- 返回: {records, total}

5.2 新闻详情
- GET /api/news/{id}
- 返回: {news}

5.3 发布新闻
- POST /api/news
- 参数: {title, content, cover, category}

5.4 更新新闻
- PUT /api/news
- 参数: {id, title, content, cover, category, status}

5.5 删除新闻
- DELETE /api/news/{id}

5.6 新闻评论列表
- GET /api/news/{id}/comments
- 返回: {list}

5.7 添加评论
- POST /api/news/{id}/comment
- 参数: {content}

#### 6. 校友活动
6.1 活动列表
- GET /api/activity/list
- 参数: {pageNum, pageSize, title, status}
- 返回: {records, total}

6.2 活动详情
- GET /api/activity/{id}
- 返回: {activity, signList}

6.3 发布活动
- POST /api/activity
- 参数: {title, content, address, startTime, endTime, signDeadline, maxCount}

6.4 更新活动
- PUT /api/activity
- 参数: {id, ...}

6.5 活动报名
- POST /api/activity/{id}/sign

6.6 取消报名
- DELETE /api/activity/{id}/sign

6.7 活动签到
- POST /api/activity/{id}/check

6.8 活动相册
- GET /api/activity/{id}/photos
- 返回: {list}

6.9 上传活动照片
- POST /api/activity/{id}/photo
- 参数: {url, description}

#### 7. 捐赠管理
7.1 捐赠项目列表
- GET /api/donation/project/list
- 参数: {pageNum, pageSize, status}
- 返回: {records, total}

7.2 创建捐赠项目
- POST /api/donation/project
- 参数: {name, description, targetAmount, cover, startTime, endTime}

7.3 捐赠
- POST /api/donation/donate
- 参数: {projectId, amount, message, isAnonymous}

7.4 捐赠记录
- GET /api/donation/record/list
- 参数: {pageNum, pageSize, projectId}
- 返回: {records, total}

7.5 确认捐赠
- PUT /api/donation/record/{id}/confirm

#### 8. 企业招聘
8.1 企业列表
- GET /api/company/list
- 参数: {pageNum, pageSize, name, industry, status}
- 返回: {records, total}

8.2 登记企业
- POST /api/company
- 参数: {name, logo, industry, scale, address, website, introduction}

8.3 审核企业
- PUT /api/company/{id}/audit
- 参数: {status}

8.4 招聘信息列表
- GET /api/job/list
- 参数: {pageNum, pageSize, title, city, status}
- 返回: {records, total}

8.5 发布招聘
- POST /api/job
- 参数: {companyId, title, salary, city, experience, education, description, contact}

8.6 审核招聘
- PUT /api/job/{id}/audit
- 参数: {status}

#### 9. 校友论坛
9.1 板块列表
- GET /api/forum/category/list
- 返回: {list}

9.2 帖子列表
- GET /api/forum/post/list
- 参数: {pageNum, pageSize, categoryId, title}
- 返回: {records, total}

9.3 帖子详情
- GET /api/forum/post/{id}
- 返回: {post, replies}

9.4 发帖
- POST /api/forum/post
- 参数: {categoryId, title, content}

9.5 回复帖子
- POST /api/forum/post/{id}/reply
- 参数: {content, replyToId}

9.6 点赞帖子
- POST /api/forum/post/{id}/like

9.7 取消点赞
- DELETE /api/forum/post/{id}/like

9.8 管理板块
- POST /api/forum/category
- 参数: {name, description, icon, sort}

#### 10. 系统管理
10.1 轮播图列表
- GET /api/banner/list
- 返回: {list}

10.2 添加轮播图
- POST /api/banner
- 参数: {title, image, url, sort, status}

10.3 更新轮播图
- PUT /api/banner
- 参数: {id, title, image, url, sort, status}

10.4 删除轮播图
- DELETE /api/banner/{id}

10.5 操作日志
- GET /api/log/list
- 参数: {pageNum, pageSize, username, startTime, endTime}
- 返回: {records, total}

10.6 统计数据
- GET /api/stats/overview
- 返回: {alumniCount, activityCount, newsCount, donationTotal}

10.7 文件上传
- POST /api/upload
- 参数: file
- 返回: {url}

### 项目结构

后端:
```
079-backend/
├── sql/
│   └── init.sql
├── src/main/java/com/alumni/
│   ├── AlumniApplication.java
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
│   ├── mapper/
│   ├── service/
│   └── controller/
└── src/main/resources/
    └── application.yml
```

前端:
```
079-frontend/
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
        ├── user/
        ├── alumni/
        ├── grade/
        ├── news/
        ├── activity/
        ├── donation/
        ├── company/
        ├── forum/
        └── system/
```

## 用户角色
- 管理员(admin): 系统全部权限，用户审核、内容管理、数据统计
- 校友(alumni): 查看信息、参与活动、发帖交流、捐赠等

## 默认账号
- admin/123456 (管理员)
- user/123456 (校友，已审核通过)
- user2/123456 (校友，待审核)
