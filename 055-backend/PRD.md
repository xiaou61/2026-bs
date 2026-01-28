# 基于Java的企业OA管理系统设计与实现

## 项目概述

### 项目简介
企业OA（Office Automation）管理系统是一个面向企业内部的综合办公自动化平台，旨在提高企业办公效率，实现无纸化办公。系统涵盖员工管理、部门管理、考勤管理、请假审批、会议管理、公告通知、日程安排、文档管理等核心功能模块。

### 核心功能
1. 用户与权限管理
2. 部门组织架构
3. 考勤打卡管理
4. 请假审批流程
5. 会议室预约与会议管理
6. 公告通知发布
7. 个人日程管理
8. 文档资料管理
9. 工资薪酬管理
10. 数据统计面板

### 技术栈
**后端**
- Spring Boot 3.2.0
- MyBatis-Plus 3.5.5
- MySQL 8.0
- Redis
- JWT认证

**前端**
- Vue 3.4.0
- Element Plus 2.4.4
- Vite 5.0
- Pinia 2.1.7
- Axios 1.6.2
- ECharts 5.4.3

## 功能需求

### 1. 用户管理
- 用户登录/登出
- 个人信息查看与修改
- 密码修改
- 用户CRUD（管理员）
- 角色分配

### 2. 部门管理
- 部门树形结构
- 部门CRUD
- 部门负责人设置
- 部门员工查看

### 3. 考勤管理
- 上班打卡/下班打卡
- 打卡记录查询
- 考勤统计（迟到/早退/缺勤）
- 月度考勤报表

### 4. 请假管理
- 请假申请（事假/病假/年假/婚假/产假）
- 请假审批（部门经理/HR）
- 请假记录查询
- 假期余额管理

### 5. 会议管理
- 会议室管理
- 会议预约
- 会议参与人员邀请
- 会议记录
- 会议室状态查看

### 6. 公告通知
- 公告发布（管理员/HR）
- 公告列表查看
- 公告已读/未读状态
- 公告置顶

### 7. 日程管理
- 个人日程添加
- 日程提醒
- 日历视图
- 日程分类

### 8. 文档管理
- 文档上传
- 文档分类
- 文档共享
- 文档下载

### 9. 工资管理
- 工资条录入
- 工资条查看
- 工资统计

### 10. 数据统计
- 员工总数统计
- 部门分布统计
- 考勤数据统计
- 请假数据统计

## 技术设计

### 数据库设计（12张表）

#### 1. user - 用户表
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| username | varchar(50) | 用户名 |
| password | varchar(100) | 密码 |
| real_name | varchar(50) | 真实姓名 |
| gender | tinyint | 性别 1男 2女 |
| phone | varchar(20) | 手机号 |
| email | varchar(100) | 邮箱 |
| avatar | varchar(255) | 头像 |
| dept_id | bigint | 部门ID |
| position | varchar(50) | 职位 |
| role | varchar(20) | 角色 admin/manager/employee |
| entry_date | date | 入职日期 |
| status | tinyint | 状态 0禁用 1正常 |
| create_time | datetime | 创建时间 |
| update_time | datetime | 更新时间 |

#### 2. department - 部门表
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| name | varchar(50) | 部门名称 |
| parent_id | bigint | 父部门ID |
| leader_id | bigint | 部门负责人ID |
| sort | int | 排序 |
| status | tinyint | 状态 |
| create_time | datetime | 创建时间 |

#### 3. attendance - 考勤记录表
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| user_id | bigint | 用户ID |
| attendance_date | date | 考勤日期 |
| clock_in_time | datetime | 上班打卡时间 |
| clock_out_time | datetime | 下班打卡时间 |
| status | tinyint | 状态 0正常 1迟到 2早退 3缺勤 4迟到+早退 |
| remark | varchar(255) | 备注 |
| create_time | datetime | 创建时间 |

#### 4. leave_request - 请假申请表
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| user_id | bigint | 申请人ID |
| leave_type | tinyint | 请假类型 1事假 2病假 3年假 4婚假 5产假 |
| start_time | datetime | 开始时间 |
| end_time | datetime | 结束时间 |
| days | decimal(5,1) | 请假天数 |
| reason | varchar(500) | 请假原因 |
| status | tinyint | 状态 0待审批 1已通过 2已拒绝 3已撤销 |
| approver_id | bigint | 审批人ID |
| approve_time | datetime | 审批时间 |
| approve_remark | varchar(255) | 审批备注 |
| create_time | datetime | 创建时间 |

#### 5. meeting_room - 会议室表
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| name | varchar(50) | 会议室名称 |
| location | varchar(100) | 位置 |
| capacity | int | 容纳人数 |
| equipment | varchar(255) | 设备（投影仪/白板等） |
| status | tinyint | 状态 0禁用 1可用 |
| create_time | datetime | 创建时间 |

#### 6. meeting - 会议表
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| title | varchar(100) | 会议主题 |
| room_id | bigint | 会议室ID |
| organizer_id | bigint | 组织者ID |
| start_time | datetime | 开始时间 |
| end_time | datetime | 结束时间 |
| participants | varchar(500) | 参会人员IDs |
| content | text | 会议内容 |
| status | tinyint | 状态 0待开始 1进行中 2已结束 3已取消 |
| create_time | datetime | 创建时间 |

#### 7. notice - 公告表
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| title | varchar(100) | 标题 |
| content | text | 内容 |
| publisher_id | bigint | 发布人ID |
| is_top | tinyint | 是否置顶 |
| status | tinyint | 状态 0草稿 1发布 |
| publish_time | datetime | 发布时间 |
| create_time | datetime | 创建时间 |

#### 8. notice_read - 公告阅读记录表
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| notice_id | bigint | 公告ID |
| user_id | bigint | 用户ID |
| read_time | datetime | 阅读时间 |

#### 9. schedule - 日程表
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| user_id | bigint | 用户ID |
| title | varchar(100) | 日程标题 |
| content | varchar(500) | 日程内容 |
| start_time | datetime | 开始时间 |
| end_time | datetime | 结束时间 |
| category | varchar(20) | 分类 work/meeting/personal |
| remind | tinyint | 是否提醒 |
| status | tinyint | 状态 0未完成 1已完成 |
| create_time | datetime | 创建时间 |

#### 10. document - 文档表
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| name | varchar(100) | 文档名称 |
| path | varchar(255) | 存储路径 |
| size | bigint | 文件大小 |
| type | varchar(50) | 文件类型 |
| category | varchar(50) | 文档分类 |
| uploader_id | bigint | 上传人ID |
| is_shared | tinyint | 是否共享 |
| download_count | int | 下载次数 |
| create_time | datetime | 创建时间 |

#### 11. salary - 工资表
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| user_id | bigint | 用户ID |
| year_month | varchar(7) | 年月 2026-01 |
| basic_salary | decimal(10,2) | 基本工资 |
| bonus | decimal(10,2) | 奖金 |
| allowance | decimal(10,2) | 津贴 |
| deduction | decimal(10,2) | 扣款 |
| social_security | decimal(10,2) | 社保 |
| actual_salary | decimal(10,2) | 实发工资 |
| status | tinyint | 状态 0未发放 1已发放 |
| create_time | datetime | 创建时间 |

#### 12. work_log - 工作日志表
| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键 |
| user_id | bigint | 用户ID |
| log_date | date | 日志日期 |
| content | text | 工作内容 |
| plan | text | 明日计划 |
| create_time | datetime | 创建时间 |

### API接口设计

#### 用户模块
- POST /api/user/login - 登录
- GET /api/user/info - 获取当前用户信息
- PUT /api/user/password - 修改密码
- GET /api/user/page - 用户分页列表
- POST /api/user - 新增用户
- PUT /api/user - 修改用户
- DELETE /api/user/{id} - 删除用户

#### 部门模块
- GET /api/dept/tree - 部门树
- GET /api/dept/list - 部门列表
- POST /api/dept - 新增部门
- PUT /api/dept - 修改部门
- DELETE /api/dept/{id} - 删除部门

#### 考勤模块
- POST /api/attendance/clockIn - 上班打卡
- POST /api/attendance/clockOut - 下班打卡
- GET /api/attendance/today - 今日打卡状态
- GET /api/attendance/page - 考勤记录分页
- GET /api/attendance/stats - 考勤统计

#### 请假模块
- POST /api/leave - 提交请假申请
- GET /api/leave/page - 请假记录分页
- GET /api/leave/pending - 待审批列表
- PUT /api/leave/{id}/approve - 审批请假
- PUT /api/leave/{id}/cancel - 撤销请假

#### 会议模块
- GET /api/room/list - 会议室列表
- POST /api/room - 新增会议室
- PUT /api/room - 修改会议室
- DELETE /api/room/{id} - 删除会议室
- POST /api/meeting - 预约会议
- GET /api/meeting/page - 会议列表
- PUT /api/meeting/{id}/cancel - 取消会议

#### 公告模块
- GET /api/notice/page - 公告分页
- GET /api/notice/{id} - 公告详情
- POST /api/notice - 发布公告
- PUT /api/notice - 修改公告
- DELETE /api/notice/{id} - 删除公告
- POST /api/notice/{id}/read - 标记已读

#### 日程模块
- GET /api/schedule/list - 日程列表
- POST /api/schedule - 新增日程
- PUT /api/schedule - 修改日程
- DELETE /api/schedule/{id} - 删除日程
- PUT /api/schedule/{id}/complete - 完成日程

#### 文档模块
- GET /api/doc/page - 文档分页
- POST /api/doc/upload - 上传文档
- DELETE /api/doc/{id} - 删除文档
- GET /api/doc/{id}/download - 下载文档

#### 工资模块
- GET /api/salary/page - 工资记录分页
- GET /api/salary/my - 我的工资条
- POST /api/salary - 录入工资
- PUT /api/salary/{id}/release - 发放工资

#### 工作日志模块
- GET /api/worklog/page - 工作日志分页
- POST /api/worklog - 提交工作日志
- PUT /api/worklog - 修改工作日志

#### 统计模块
- GET /api/stats/overview - 首页统计概览
- GET /api/stats/attendance - 考勤统计
- GET /api/stats/dept - 部门人员统计

### 项目结构

**后端**
```
055-backend/
├── sql/
│   └── init.sql
├── src/main/java/com/oa/
│   ├── OaApplication.java
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
│   │   ├── Department.java
│   │   ├── Attendance.java
│   │   ├── LeaveRequest.java
│   │   ├── MeetingRoom.java
│   │   ├── Meeting.java
│   │   ├── Notice.java
│   │   ├── NoticeRead.java
│   │   ├── Schedule.java
│   │   ├── Document.java
│   │   ├── Salary.java
│   │   └── WorkLog.java
│   ├── mapper/
│   ├── service/
│   └── controller/
└── src/main/resources/
    └── application.yml
```

**前端**
```
055-frontend/
├── package.json
├── vite.config.js
├── index.html
└── src/
    ├── main.js
    ├── App.vue
    ├── styles/
    │   └── index.scss
    ├── router/
    │   └── index.js
    ├── api/
    │   ├── request.js
    │   └── modules/
    ├── store/
    │   └── user.js
    └── views/
        ├── Login.vue
        ├── Layout.vue
        ├── Dashboard.vue
        ├── user/
        ├── dept/
        ├── attendance/
        ├── leave/
        ├── meeting/
        ├── notice/
        ├── schedule/
        ├── document/
        ├── salary/
        └── worklog/
```

## 用户角色

### 管理员（admin）
- 用户管理（CRUD）
- 部门管理（CRUD）
- 公告发布
- 工资管理
- 所有数据查看权限
- 系统配置

### 部门经理（manager）
- 部门员工查看
- 请假审批
- 会议组织
- 考勤查看（本部门）
- 工作日志查看（本部门）

### 普通员工（employee）
- 个人信息管理
- 考勤打卡
- 请假申请
- 会议参与
- 日程管理
- 文档上传下载
- 工作日志提交
- 工资条查看

## 默认账号

| 角色 | 用户名 | 密码 | 部门 |
|------|--------|------|------|
| 管理员 | admin | admin123 | 总经办 |
| 部门经理 | manager | manager123 | 技术部 |
| 普通员工 | employee | employee123 | 技术部 |

## 业务规则

### 考勤规则
- 上班时间：09:00，下班时间：18:00
- 09:00之后打卡算迟到
- 18:00之前打卡算早退
- 未打卡算缺勤

### 请假规则
- 请假天数 ≤ 3天，部门经理审批
- 请假天数 > 3天，需要管理员审批
- 审批通过后不可撤销

### 会议规则
- 会议时间不能重叠
- 会议开始前30分钟不能取消
