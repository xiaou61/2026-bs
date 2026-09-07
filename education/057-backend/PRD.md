# 招生管理系统

## 项目概述
### 项目简介
高校招生管理系统，实现招生计划制定、考生报名、成绩录入、录取管理等全流程招生业务管理。

### 核心功能
- 招生计划管理
- 专业管理
- 考生信息管理
- 成绩管理
- 录取管理
- 统计分析

### 技术栈
**后端**: Spring Boot 2.7.0 + MyBatis-Plus 3.5.2 + MySQL 8.0 + Redis + JWT
**前端**: Vue 3.4.0 + Element Plus 2.4.4 + Vite + Pinia + Axios + ECharts

## 功能需求

### 1. 系统管理
- 管理员登录/退出
- 密码修改
- 个人信息维护

### 2. 招生计划管理
- 年度招生计划制定
- 各专业招生人数设置
- 计划状态管理(草稿/发布/关闭)

### 3. 专业管理
- 院系管理
- 专业信息维护
- 专业招生要求设置

### 4. 考生管理
- 考生信息登记
- 考生信息审核
- 考生状态跟踪

### 5. 成绩管理
- 成绩批量导入
- 成绩录入修改
- 成绩查询统计

### 6. 录取管理
- 录取分数线设置
- 自动/手动录取
- 录取结果公示
- 录取通知书管理

### 7. 统计分析
- 报名统计
- 录取率分析
- 各专业录取情况
- 生源地区分布

### 8. 通知公告
- 公告发布
- 公告管理

## 技术设计

### 数据库设计

**表1: admin (管理员表)**
- id: bigint, 主键
- username: varchar(50), 用户名
- password: varchar(100), 密码
- name: varchar(50), 姓名
- phone: varchar(20), 电话
- role: varchar(20), 角色(super_admin/admin)
- status: tinyint, 状态(0禁用/1启用)
- create_time: datetime
- update_time: datetime

**表2: department (院系表)**
- id: bigint, 主键
- name: varchar(100), 院系名称
- code: varchar(50), 院系代码
- leader: varchar(50), 负责人
- phone: varchar(20), 联系电话
- description: text, 简介
- sort: int, 排序
- status: tinyint, 状态
- create_time: datetime

**表3: major (专业表)**
- id: bigint, 主键
- department_id: bigint, 所属院系
- name: varchar(100), 专业名称
- code: varchar(50), 专业代码
- degree: varchar(20), 学位类型(本科/专科)
- duration: int, 学制(年)
- tuition: decimal(10,2), 学费
- description: text, 专业介绍
- requirement: text, 报考要求
- status: tinyint, 状态
- create_time: datetime

**表4: enrollment_plan (招生计划表)**
- id: bigint, 主键
- year: int, 招生年份
- major_id: bigint, 专业ID
- plan_count: int, 计划招生人数
- actual_count: int, 实际录取人数
- min_score: int, 最低分数线
- status: tinyint, 状态(0草稿/1发布/2关闭)
- create_time: datetime
- update_time: datetime

**表5: student (考生表)**
- id: bigint, 主键
- exam_no: varchar(50), 准考证号
- name: varchar(50), 姓名
- gender: tinyint, 性别(0女/1男)
- id_card: varchar(20), 身份证号
- birthday: date, 出生日期
- phone: varchar(20), 联系电话
- email: varchar(100), 邮箱
- province: varchar(50), 省份
- city: varchar(50), 城市
- address: varchar(200), 详细地址
- high_school: varchar(100), 毕业高中
- photo: varchar(200), 照片URL
- status: tinyint, 状态(0待审核/1已审核/2已录取/3未录取)
- create_time: datetime
- update_time: datetime

**表6: application (报名申请表)**
- id: bigint, 主键
- student_id: bigint, 考生ID
- plan_id: bigint, 招生计划ID
- first_major_id: bigint, 第一志愿专业
- second_major_id: bigint, 第二志愿专业
- adjust: tinyint, 是否服从调剂(0否/1是)
- status: tinyint, 状态(0待审核/1通过/2拒绝)
- remark: varchar(500), 备注
- create_time: datetime
- update_time: datetime

**表7: score (成绩表)**
- id: bigint, 主键
- student_id: bigint, 考生ID
- year: int, 年份
- chinese: int, 语文
- math: int, 数学
- english: int, 英语
- comprehensive: int, 综合
- total_score: int, 总分
- create_time: datetime

**表8: admission (录取表)**
- id: bigint, 主键
- student_id: bigint, 考生ID
- plan_id: bigint, 招生计划ID
- major_id: bigint, 录取专业
- score: int, 录取分数
- admission_no: varchar(50), 录取编号
- status: tinyint, 状态(0待确认/1已确认/2放弃)
- admit_time: datetime, 录取时间
- confirm_time: datetime, 确认时间
- create_time: datetime

**表9: notice (通知公告表)**
- id: bigint, 主键
- title: varchar(200), 标题
- content: text, 内容
- type: tinyint, 类型(1公告/2通知/3政策)
- top: tinyint, 是否置顶
- status: tinyint, 状态(0草稿/1发布)
- publish_time: datetime, 发布时间
- create_time: datetime
- admin_id: bigint, 发布人

**表10: score_line (分数线表)**
- id: bigint, 主键
- year: int, 年份
- major_id: bigint, 专业ID
- province: varchar(50), 省份
- category: varchar(20), 科类(文科/理科)
- batch: varchar(20), 批次(一本/二本/专科)
- score: int, 分数线
- create_time: datetime

### API接口设计

#### 1. 认证模块
**1.1 登录**
- POST /api/auth/login
- 参数: { username, password }
- 返回: { token, admin }

**1.2 获取当前用户**
- GET /api/auth/info
- 返回: { admin }

**1.3 退出登录**
- POST /api/auth/logout

**1.4 修改密码**
- PUT /api/auth/password
- 参数: { oldPassword, newPassword }

#### 2. 管理员模块
**2.1 分页查询**
- GET /api/admin/page
- 参数: { pageNum, pageSize, username, name }

**2.2 新增管理员**
- POST /api/admin

**2.3 修改管理员**
- PUT /api/admin

**2.4 删除管理员**
- DELETE /api/admin/{id}

#### 3. 院系模块
**3.1 分页查询**
- GET /api/department/page

**3.2 全部列表**
- GET /api/department/list

**3.3 新增院系**
- POST /api/department

**3.4 修改院系**
- PUT /api/department

**3.5 删除院系**
- DELETE /api/department/{id}

#### 4. 专业模块
**4.1 分页查询**
- GET /api/major/page

**4.2 按院系查询**
- GET /api/major/list/{departmentId}

**4.3 新增/修改/删除**
- POST/PUT/DELETE /api/major

#### 5. 招生计划模块
**5.1 分页查询**
- GET /api/plan/page

**5.2 新增/修改/删除计划**
- POST/PUT/DELETE /api/plan

**5.3 发布计划**
- PUT /api/plan/publish/{id}

**5.4 关闭计划**
- PUT /api/plan/close/{id}

#### 6. 考生模块
**6.1 分页查询**
- GET /api/student/page

**6.2 考生详情**
- GET /api/student/{id}

**6.3 新增/修改/删除考生**
- POST/PUT/DELETE /api/student

**6.4 审核考生**
- PUT /api/student/audit/{id}

#### 7. 报名模块
**7.1 分页查询**
- GET /api/application/page

**7.2 审核报名**
- PUT /api/application/audit/{id}
- 参数: { status, remark }

#### 8. 成绩模块
**8.1 分页查询**
- GET /api/score/page

**8.2 录入成绩**
- POST /api/score

**8.3 批量导入**
- POST /api/score/import

**8.4 修改成绩**
- PUT /api/score

#### 9. 录取模块
**9.1 分页查询**
- GET /api/admission/page

**9.2 录取操作**
- POST /api/admission

**9.3 批量录取**
- POST /api/admission/batch

**9.4 确认录取**
- PUT /api/admission/confirm/{id}

#### 10. 分数线模块
**10.1 分页查询**
- GET /api/scoreline/page

**10.2 新增/修改/删除**
- POST/PUT/DELETE /api/scoreline

#### 11. 通知公告模块
**11.1 分页查询**
- GET /api/notice/page

**11.2 公告详情**
- GET /api/notice/{id}

**11.3 新增/修改/删除**
- POST/PUT/DELETE /api/notice

**11.4 发布公告**
- PUT /api/notice/publish/{id}

#### 12. 统计模块
**12.1 首页统计**
- GET /api/stats/dashboard

**12.2 报名统计**
- GET /api/stats/application

**12.3 录取统计**
- GET /api/stats/admission

**12.4 专业录取率**
- GET /api/stats/major

**12.5 生源分布**
- GET /api/stats/province

### 项目结构

后端:
```
057-backend/
├── sql/
│   └── init.sql
├── src/main/java/com/enrollment/
│   ├── EnrollmentApplication.java
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
057-frontend/
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
        ├── admin/
        ├── department/
        ├── major/
        ├── plan/
        ├── student/
        ├── application/
        ├── score/
        ├── admission/
        ├── scoreline/
        └── notice/
```

## 用户角色
- **超级管理员(super_admin)**: 全部权限，可管理其他管理员
- **普通管理员(admin)**: 业务管理权限

## 默认账号
- admin / 123456 (超级管理员)
- user / 123456 (普通管理员)
