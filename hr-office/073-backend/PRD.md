# 基于SpringBoot和Vue的人事管理系统

## 项目概述
### 项目简介
人事管理系统是一套面向企业的人力资源管理解决方案，涵盖员工信息管理、部门组织架构、考勤打卡、薪资核算、招聘管理、培训管理、绩效考核等核心功能，帮助企业实现人事管理的数字化、规范化。

### 核心功能
- 员工档案管理（入职、离职、转正、调岗）
- 部门与职位管理
- 考勤打卡与请假审批
- 薪资核算与发放记录
- 招聘需求与简历管理
- 培训计划与记录
- 绩效考核评估
- 合同管理

### 技术栈
**后端**
- Spring Boot 2.7.18
- MyBatis 3.5.13（原生XML映射）
- PageHelper 1.4.7
- MySQL 8.0
- Redis 6.0+
- JWT 0.9.1
- Hutool 5.8.25

**前端**
- Vue 3.4.0
- Element Plus 2.4.4
- Pinia 2.1.7
- Vue Router 4.2.5
- Axios 1.6.2
- ECharts 5.4.3
- Vite 5.0

## 功能需求

### 1. 用户与权限
- 用户登录/登出
- 三种角色：管理员、HR、员工
- 权限控制：管理员全权限、HR人事权限、员工查看个人信息

### 2. 部门管理
- 部门增删改查
- 部门树形结构
- 部门负责人设置

### 3. 职位管理
- 职位增删改查
- 职位等级设置
- 薪资范围配置

### 4. 员工管理
- 员工档案CRUD
- 入职办理（自动生成工号）
- 离职办理
- 员工状态管理（在职/试用/离职）
- 员工转正
- 调岗调薪

### 5. 考勤管理
- 每日打卡（上班/下班）
- 打卡记录查询
- 考勤统计（迟到/早退/缺勤）
- 月度考勤汇总

### 6. 请假管理
- 请假申请（年假/事假/病假/婚假等）
- 请假审批流程
- 请假记录查询
- 假期余额统计

### 7. 薪资管理
- 薪资结构设置（基本工资+绩效+津贴）
- 月度薪资核算
- 薪资发放记录
- 薪资条查询

### 8. 招聘管理
- 招聘需求发布
- 简历投递与管理
- 面试安排
- 录用审批

### 9. 培训管理
- 培训计划制定
- 培训报名
- 培训记录

### 10. 绩效考核
- 考核周期设置
- 绩效评分
- 绩效结果查询

### 11. 合同管理
- 合同信息录入
- 合同到期提醒
- 合同续签

### 12. 公告管理
- 公告发布
- 公告查看

### 13. 数据统计
- 员工人数统计
- 部门人员分布
- 薪资统计
- 考勤统计看板

## 技术设计

### 数据库设计（12张表）

**1. user 用户表**
- id, username, password, name, avatar, role(admin/hr/employee), employee_id, status, create_time

**2. department 部门表**
- id, name, parent_id, leader_id, sort, status, create_time

**3. position 职位表**
- id, name, level, min_salary, max_salary, description, status, create_time

**4. employee 员工表**
- id, emp_no, name, gender, phone, email, id_card, birthday, address, department_id, position_id, entry_date, leave_date, status(trial/regular/resigned), create_time

**5. attendance 考勤记录表**
- id, employee_id, date, clock_in, clock_out, status(normal/late/early/absent), remark, create_time

**6. leave_request 请假申请表**
- id, employee_id, type(annual/personal/sick/marriage/maternity), start_date, end_date, days, reason, status(pending/approved/rejected), approver_id, approve_time, create_time

**7. salary 薪资记录表**
- id, employee_id, year_month, base_salary, performance, allowance, deduction, actual_salary, status(pending/paid), pay_date, create_time

**8. recruitment 招聘需求表**
- id, position_id, department_id, count, salary_range, requirements, status(open/closed), create_time

**9. resume 简历表**
- id, recruitment_id, name, phone, email, education, experience, status(pending/interview/passed/rejected), create_time

**10. training 培训计划表**
- id, title, content, trainer, start_time, end_time, location, max_count, status, create_time

**11. contract 合同表**
- id, employee_id, contract_no, type, start_date, end_date, status(active/expired/terminated), create_time

**12. announcement 公告表**
- id, title, content, publisher_id, is_top, status, create_time

### 项目结构

**后端**
```
073-backend/
├── sql/init.sql
├── pom.xml
└── src/main/
    ├── java/com/hrm/
    │   ├── HrmApplication.java
    │   ├── common/
    │   ├── config/
    │   ├── utils/
    │   ├── entity/
    │   ├── mapper/
    │   ├── service/
    │   └── controller/
    └── resources/
        ├── application.yml
        └── mapper/*.xml
```

**前端**
```
073-frontend/
├── package.json
├── vite.config.js
├── index.html
└── src/
    ├── main.js
    ├── App.vue
    ├── router/index.js
    ├── api/
    ├── store/
    └── views/
        ├── Login.vue
        ├── Layout.vue
        ├── Dashboard.vue
        ├── department/
        ├── position/
        ├── employee/
        ├── attendance/
        ├── leave/
        ├── salary/
        ├── recruitment/
        ├── training/
        ├── contract/
        └── announcement/
```

## 用户角色
- **管理员(admin)**: 全部权限，系统配置
- **HR(hr)**: 人事管理权限（员工、考勤、薪资、招聘、培训等）
- **员工(employee)**: 查看个人信息、打卡、请假申请、查看工资条

## 默认账号
- admin / 123456（管理员）
- hr / 123456（HR）
- zhangsan / 123456（员工，工号EMP001）
