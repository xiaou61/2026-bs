# 孩童收养信息管理系统

## 项目概述

### 项目简介
孩童收养信息管理系统面向儿童福利机构与社会申请人场景，提供儿童档案管理、收养申请、资格审核、家访评估、匹配审批、协议签订、回访跟踪和公告展示等功能，形成从信息发布到收养后回访的完整业务闭环。

### 核心功能
- 用户登录注册与角色权限控制
- 儿童档案、健康信息与收养状态管理
- 申请人资料维护与在线申请提交
- 材料审核、家访评估与审批流转
- 儿童与申请家庭匹配推荐
- 收养协议登记与回访记录管理
- 公告资讯与数据统计看板

### 技术栈

**后端**
- Spring Boot 2.7.18
- MyBatis-Plus 3.5.3
- MySQL 8.0
- Redis 7.x
- JWT

**前端**
- Vue 3.4.0
- Element Plus 2.4.4
- Vue Router 4.2.5
- Pinia 2.1.7
- Axios 1.6.2
- ECharts 5.4.3

## 功能需求

### 1. 用户与权限模块
- 管理员、审核员、申请人三角色
- 账号登录、注册、退出、个人信息查询
- 基于 JWT + Redis 的登录校验

### 2. 儿童档案模块
- 儿童基础档案新增、编辑、删除
- 儿童健康信息、监护信息、可收养状态维护
- 儿童公开展示列表与详情查询

### 3. 申请人档案模块
- 申请人基本资料、婚姻信息、居住信息维护
- 家庭情况、收入情况、收养意愿登记
- 材料上传信息管理

### 4. 收养申请模块
- 申请人在线提交收养申请
- 申请状态流转查询
- 申请记录分页管理

### 5. 审核评估模块
- 材料初审通过与驳回
- 家访评估记录登记
- 审批意见与审批节点记录

### 6. 匹配与协议模块
- 儿童与申请家庭匹配管理
- 匹配结果确认与审批
- 收养协议登记、签署状态维护

### 7. 回访与运营模块
- 回访计划与回访记录管理
- 系统公告发布与展示
- 首页统计看板

## 技术设计

### 数据库设计（12张表）
1. sys_user
2. adopter_profile
3. child_profile
4. child_health_record
5. adoption_application
6. application_material
7. home_visit_record
8. match_record
9. approval_record
10. adoption_agreement
11. follow_up_record
12. system_notice

### API接口设计

#### 1.1 用户登录
- 请求方式: POST
- 路径: /api/auth/login
- 参数: { "username": "admin", "password": "123456" }
- 返回: { "code": 200, "data": { "token": "...", "userInfo": {} } }

#### 1.2 用户注册
- 请求方式: POST
- 路径: /api/auth/register
- 参数: { "username": "user01", "password": "123456", "realName": "张三", "phone": "13800000001" }
- 返回: { "code": 200, "message": "success" }

#### 1.3 儿童公开列表
- 请求方式: GET
- 路径: /api/child/public/list
- 参数: pageNum, pageSize, name, gender, ageMin, ageMax, adoptionStatus
- 返回: { "code": 200, "data": { "list": [], "total": 0 } }

#### 1.4 收养申请提交
- 请求方式: POST
- 路径: /api/application/add
- 参数: { "childId": 1, "reason": "愿意为孩子提供稳定家庭环境", "expectedContactDate": "2026-03-20" }
- 返回: { "code": 200, "message": "success" }

#### 1.5 材料审核
- 请求方式: PUT
- 路径: /api/application/review
- 参数: { "applicationId": 1, "reviewStatus": 1, "reviewRemark": "材料齐全" }
- 返回: { "code": 200, "message": "success" }

#### 1.6 家访评估新增
- 请求方式: POST
- 路径: /api/home-visit/add
- 参数: { "applicationId": 1, "visitDate": "2026-03-22", "visitResult": "通过", "visitRemark": "家庭环境良好" }
- 返回: { "code": 200, "message": "success" }

#### 1.7 匹配审批提交
- 请求方式: POST
- 路径: /api/match/add
- 参数: { "applicationId": 1, "childId": 1, "matchScore": 92, "matchRemark": "综合条件匹配" }
- 返回: { "code": 200, "message": "success" }

#### 1.8 统计看板
- 请求方式: GET
- 路径: /api/statistics/dashboard
- 参数: 无
- 返回: { "code": 200, "data": { "childCount": 0, "applicationCount": 0, "matchedCount": 0, "followCount": 0 } }

### 项目结构

后端:
```
088-backend/
├── sql/
│   └── init.sql
├── src/main/java/com/adoption/
│   ├── common/
│   ├── config/
│   ├── controller/
│   ├── dto/
│   ├── entity/
│   ├── mapper/
│   ├── service/
│   └── utils/
└── src/main/resources/
    └── application.yml
```

前端:
```
088-frontend/
├── package.json
├── vite.config.js
├── index.html
└── src/
    ├── main.js
    ├── App.vue
    ├── router/
    ├── api/
    ├── store/
    └── views/
        ├── admin/
        ├── portal/
        ├── Login.vue
        └── Register.vue
```

## 用户角色
- 管理员：admin，负责儿童档案、公告、统计、协议与全局配置
- 审核员：reviewer，负责申请审核、家访评估、匹配审批、回访管理
- 申请人：applicant，负责资料维护、浏览儿童信息、提交申请、查看进度

## 默认账号
- admin / 123456
- reviewer / 123456
- applicant / 123456
