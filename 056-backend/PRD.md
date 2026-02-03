# 短文写作竞赛管理小程序

## 项目概述
### 项目简介
短文写作竞赛管理小程序是一个面向学校、机构或企业的写作竞赛管理平台，支持竞赛发布、作品提交、在线评分、成绩公示等功能，采用PC端后台管理+微信小程序前端的架构。

### 核心功能
- 竞赛管理：创建、发布、管理各类写作竞赛
- 作品管理：参赛者提交作品、查看作品状态
- 评分系统：评委在线评分、多维度评分标准
- 成绩公示：自动计算排名、成绩公示
- 通知公告：系统公告、竞赛通知

### 技术栈
**后端**
- Spring Boot 2.7.x
- MyBatis-Plus 3.5.x
- MySQL 8.0
- Redis
- JWT认证

**前端（管理端）**
- Vue 3.4.0
- Element Plus 2.4.4
- Vite
- Pinia
- Axios

**前端（小程序端）**
- uni-app
- Vue 3

## 功能需求

### 1. 用户管理模块
- 用户注册（小程序微信授权登录）
- 管理员/评委登录（PC端账号密码登录）
- 个人信息管理
- 密码修改
- 用户列表管理（管理员）

### 2. 竞赛管理模块
- 竞赛分类管理（CRUD）
- 竞赛创建/编辑/删除
- 竞赛发布/下架/结束
- 竞赛详情查看
- 竞赛列表（分页、筛选）

### 3. 作品管理模块
- 作品提交（标题、正文、附件）
- 作品修改/撤回
- 作品列表查看
- 作品详情查看
- 作品审核（管理员）

### 4. 评分管理模块
- 评分标准管理
- 评委分配
- 在线评分
- 评分查看/修改
- 评分统计

### 5. 成绩管理模块
- 成绩计算（去除最高最低分后平均）
- 排名生成
- 成绩公示
- 获奖名单

### 6. 公告通知模块
- 公告发布/编辑/删除
- 公告列表
- 系统通知

### 7. 数据统计模块
- 参赛人数统计
- 竞赛数量统计
- 评分完成进度
- 数据可视化展示

## 技术设计

### 数据库设计

**表1: user（用户表）**
- id: bigint, 主键
- username: varchar(50), 用户名
- password: varchar(100), 密码
- nickname: varchar(50), 昵称
- avatar: varchar(255), 头像
- phone: varchar(20), 手机号
- email: varchar(100), 邮箱
- role: tinyint, 角色(0管理员 1评委 2参赛者)
- openid: varchar(100), 微信openid
- status: tinyint, 状态(0禁用 1正常)
- create_time: datetime, 创建时间
- update_time: datetime, 更新时间

**表2: category（竞赛分类表）**
- id: bigint, 主键
- name: varchar(50), 分类名称
- sort: int, 排序
- status: tinyint, 状态
- create_time: datetime, 创建时间

**表3: competition（竞赛表）**
- id: bigint, 主键
- title: varchar(200), 竞赛标题
- category_id: bigint, 分类ID
- cover: varchar(255), 封面图
- description: text, 竞赛描述
- requirement: text, 参赛要求
- start_time: datetime, 开始时间
- end_time: datetime, 结束时间
- submit_deadline: datetime, 提交截止时间
- status: tinyint, 状态(0草稿 1进行中 2已结束 3已下架)
- max_words: int, 最大字数限制
- min_words: int, 最小字数限制
- publish_result: tinyint, 是否公布成绩(0否 1是)
- create_user: bigint, 创建人
- create_time: datetime, 创建时间
- update_time: datetime, 更新时间

**表4: work（作品表）**
- id: bigint, 主键
- competition_id: bigint, 竞赛ID
- user_id: bigint, 作者ID
- title: varchar(200), 作品标题
- content: text, 作品内容
- word_count: int, 字数
- attachment: varchar(255), 附件
- status: tinyint, 状态(0待审核 1已通过 2已退回 3已撤回)
- reject_reason: varchar(500), 退回原因
- final_score: decimal(5,2), 最终得分
- rank: int, 排名
- submit_time: datetime, 提交时间
- create_time: datetime, 创建时间
- update_time: datetime, 更新时间

**表5: score_standard（评分标准表）**
- id: bigint, 主键
- competition_id: bigint, 竞赛ID
- name: varchar(100), 评分项名称
- max_score: decimal(5,2), 最高分
- weight: decimal(3,2), 权重
- description: varchar(500), 评分说明
- sort: int, 排序
- create_time: datetime, 创建时间

**表6: judge_assignment（评委分配表）**
- id: bigint, 主键
- competition_id: bigint, 竞赛ID
- judge_id: bigint, 评委ID
- create_time: datetime, 创建时间

**表7: score（评分表）**
- id: bigint, 主键
- work_id: bigint, 作品ID
- judge_id: bigint, 评委ID
- standard_id: bigint, 评分标准ID
- score: decimal(5,2), 得分
- comment: varchar(500), 评语
- create_time: datetime, 创建时间
- update_time: datetime, 更新时间

**表8: score_record（评分记录汇总表）**
- id: bigint, 主键
- work_id: bigint, 作品ID
- judge_id: bigint, 评委ID
- total_score: decimal(5,2), 总分
- comment: text, 综合评语
- status: tinyint, 状态(0未评 1已评)
- create_time: datetime, 创建时间
- update_time: datetime, 更新时间

**表9: notice（公告表）**
- id: bigint, 主键
- title: varchar(200), 公告标题
- content: text, 公告内容
- type: tinyint, 类型(0系统公告 1竞赛通知)
- competition_id: bigint, 关联竞赛ID
- status: tinyint, 状态(0草稿 1已发布)
- top: tinyint, 是否置顶
- create_user: bigint, 创建人
- create_time: datetime, 创建时间
- update_time: datetime, 更新时间

**表10: award（获奖表）**
- id: bigint, 主键
- competition_id: bigint, 竞赛ID
- work_id: bigint, 作品ID
- user_id: bigint, 用户ID
- award_level: varchar(50), 奖项级别
- certificate: varchar(255), 证书
- create_time: datetime, 创建时间

### API接口设计

#### 1. 认证模块
**1.1 管理端登录**
- 请求方式: POST
- 路径: /api/auth/login
- 参数: { username, password }
- 返回: { token, userInfo }

**1.2 小程序登录**
- 请求方式: POST
- 路径: /api/auth/wxLogin
- 参数: { code }
- 返回: { token, userInfo }

#### 2. 用户模块
**2.1 用户列表**
- 请求方式: GET
- 路径: /api/user/list
- 参数: { pageNum, pageSize, keyword, role }
- 返回: { records, total }

**2.2 用户详情**
- 请求方式: GET
- 路径: /api/user/{id}
- 返回: { userInfo }

**2.3 新增/编辑用户**
- 请求方式: POST
- 路径: /api/user/save
- 参数: { id?, username, nickname, role, status }

**2.4 删除用户**
- 请求方式: DELETE
- 路径: /api/user/{id}

#### 3. 竞赛分类模块
**3.1 分类列表**
- 请求方式: GET
- 路径: /api/category/list
- 参数: { pageNum, pageSize, keyword }
- 返回: { records, total }

**3.2 全部分类**
- 请求方式: GET
- 路径: /api/category/all
- 返回: [ { id, name } ]

**3.3 新增/编辑分类**
- 请求方式: POST
- 路径: /api/category/save
- 参数: { id?, name, sort, status }

**3.4 删除分类**
- 请求方式: DELETE
- 路径: /api/category/{id}

#### 4. 竞赛模块
**4.1 竞赛列表**
- 请求方式: GET
- 路径: /api/competition/list
- 参数: { pageNum, pageSize, keyword, categoryId, status }
- 返回: { records, total }

**4.2 竞赛详情**
- 请求方式: GET
- 路径: /api/competition/{id}
- 返回: { competitionInfo }

**4.3 新增/编辑竞赛**
- 请求方式: POST
- 路径: /api/competition/save
- 参数: { id?, title, categoryId, cover, description, requirement, startTime, endTime, submitDeadline, maxWords, minWords }

**4.4 删除竞赛**
- 请求方式: DELETE
- 路径: /api/competition/{id}

**4.5 发布/下架竞赛**
- 请求方式: PUT
- 路径: /api/competition/status/{id}
- 参数: { status }

**4.6 公布成绩**
- 请求方式: PUT
- 路径: /api/competition/publish/{id}

#### 5. 作品模块
**5.1 作品列表**
- 请求方式: GET
- 路径: /api/work/list
- 参数: { pageNum, pageSize, competitionId, status, keyword }
- 返回: { records, total }

**5.2 我的作品**
- 请求方式: GET
- 路径: /api/work/my
- 参数: { pageNum, pageSize }
- 返回: { records, total }

**5.3 作品详情**
- 请求方式: GET
- 路径: /api/work/{id}
- 返回: { workInfo }

**5.4 提交作品**
- 请求方式: POST
- 路径: /api/work/submit
- 参数: { competitionId, title, content, attachment }

**5.5 修改作品**
- 请求方式: PUT
- 路径: /api/work/update
- 参数: { id, title, content, attachment }

**5.6 撤回作品**
- 请求方式: PUT
- 路径: /api/work/withdraw/{id}

**5.7 审核作品**
- 请求方式: PUT
- 路径: /api/work/audit
- 参数: { id, status, rejectReason }

#### 6. 评分模块
**6.1 评分标准列表**
- 请求方式: GET
- 路径: /api/score-standard/list/{competitionId}
- 返回: [ { id, name, maxScore, weight } ]

**6.2 保存评分标准**
- 请求方式: POST
- 路径: /api/score-standard/save
- 参数: { competitionId, standards: [] }

**6.3 评委分配列表**
- 请求方式: GET
- 路径: /api/judge/list/{competitionId}
- 返回: [ { judgeId, judgeName } ]

**6.4 分配评委**
- 请求方式: POST
- 路径: /api/judge/assign
- 参数: { competitionId, judgeIds: [] }

**6.5 待评作品列表**
- 请求方式: GET
- 路径: /api/score/pending
- 参数: { pageNum, pageSize, competitionId }
- 返回: { records, total }

**6.6 提交评分**
- 请求方式: POST
- 路径: /api/score/submit
- 参数: { workId, scores: [ { standardId, score } ], comment }

**6.7 作品评分详情**
- 请求方式: GET
- 路径: /api/score/detail/{workId}
- 返回: { scores, averageScore }

#### 7. 公告模块
**7.1 公告列表**
- 请求方式: GET
- 路径: /api/notice/list
- 参数: { pageNum, pageSize, type, status }
- 返回: { records, total }

**7.2 公告详情**
- 请求方式: GET
- 路径: /api/notice/{id}
- 返回: { noticeInfo }

**7.3 新增/编辑公告**
- 请求方式: POST
- 路径: /api/notice/save
- 参数: { id?, title, content, type, competitionId, status, top }

**7.4 删除公告**
- 请求方式: DELETE
- 路径: /api/notice/{id}

#### 8. 统计模块
**8.1 首页统计**
- 请求方式: GET
- 路径: /api/stats/dashboard
- 返回: { userCount, competitionCount, workCount, judgeCount }

**8.2 竞赛统计**
- 请求方式: GET
- 路径: /api/stats/competition/{id}
- 返回: { participantCount, workCount, scoredCount, avgScore }

**8.3 评分进度**
- 请求方式: GET
- 路径: /api/stats/score-progress/{competitionId}
- 返回: [ { judgeName, total, completed } ]

#### 9. 获奖模块
**9.1 获奖列表**
- 请求方式: GET
- 路径: /api/award/list/{competitionId}
- 返回: [ { workId, workTitle, userName, score, rank, awardLevel } ]

**9.2 生成获奖名单**
- 请求方式: POST
- 路径: /api/award/generate/{competitionId}
- 参数: { awards: [ { rank, level } ] }

### 项目结构

**后端:**
```
056-backend/
├── sql/
│   └── init.sql
├── src/main/java/com/xiaou/
│   ├── WritingCompetitionApplication.java
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
│   │   ├── Category.java
│   │   ├── Competition.java
│   │   ├── Work.java
│   │   ├── ScoreStandard.java
│   │   ├── JudgeAssignment.java
│   │   ├── Score.java
│   │   ├── ScoreRecord.java
│   │   ├── Notice.java
│   │   └── Award.java
│   ├── mapper/
│   ├── service/
│   ├── controller/
│   └── dto/
└── src/main/resources/
    └── application.yml
```

**前端（管理端）:**
```
056-frontend/
├── package.json
├── vite.config.js
├── index.html
└── src/
    ├── main.js
    ├── App.vue
    ├── router/index.js
    ├── api/
    │   ├── request.js
    │   └── index.js
    ├── store/user.js
    └── views/
        ├── Login.vue
        ├── Layout.vue
        ├── Dashboard.vue
        ├── user/UserList.vue
        ├── category/CategoryList.vue
        ├── competition/
        │   ├── CompetitionList.vue
        │   ├── CompetitionForm.vue
        │   └── CompetitionDetail.vue
        ├── work/
        │   ├── WorkList.vue
        │   └── WorkDetail.vue
        ├── score/
        │   ├── ScoreList.vue
        │   └── ScoreForm.vue
        ├── notice/NoticeList.vue
        └── award/AwardList.vue
```

**前端（小程序端）:**
```
056-miniapp/
├── package.json
├── manifest.json
├── pages.json
├── App.vue
├── main.js
├── api/
│   └── index.js
├── utils/
│   └── request.js
├── store/
│   └── user.js
└── pages/
    ├── index/index.vue
    ├── login/login.vue
    ├── competition/
    │   ├── list.vue
    │   └── detail.vue
    ├── work/
    │   ├── submit.vue
    │   ├── list.vue
    │   └── detail.vue
    ├── notice/
    │   ├── list.vue
    │   └── detail.vue
    ├── award/list.vue
    └── my/
        ├── index.vue
        └── info.vue
```

## 用户角色
- **管理员**: 系统全部权限，管理用户、竞赛、审核作品、查看统计
- **评委**: 对分配的竞赛作品进行评分、查看评分记录
- **参赛者**: 浏览竞赛、提交作品、查看成绩、查看公告

## 默认账号
- 管理员: admin / 123456
- 评委: judge / 123456
- 参赛者: user / 123456
