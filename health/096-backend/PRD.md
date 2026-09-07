# 096 - 线上医院挂号系统设计与实现

## 项目概述

### 项目简介
线上医院挂号系统面向综合医院门诊预约场景，围绕患者线上预约、医生排班出诊、管理员统一维护三类核心角色，提供科室医生展示、排班号源管理、在线挂号、订单支付、就诊评价、公告轮播和统计看板等一体化能力。

### 核心功能
- 管理员、医生、患者三角色登录与权限控制
- 科室、医生、轮播、公告等基础信息维护
- 医生排班与剩余号源管理
- 患者在线选科室、选医生、选日期挂号
- 挂号订单支付、取消、完成与查看
- 患者就诊卡与个人档案维护
- 就诊评价与平台统计看板

### 技术栈

后端
- Spring Boot 2.7.18
- MyBatis 3.5.13
- PageHelper 1.4.7
- PageHelper 1.4.7
- MySQL 8.0
- Redis 7.x
- JWT

前端
- Vue 3.4.0
- Element Plus 2.4.4
- Vue Router 4.2.5
- Pinia 2.1.7
- Axios 1.6.2
- ECharts 5.4.3

## 功能需求

### 1. 账号与权限模块
- 管理员、医生、患者三角色登录
- 患者在线注册
- 个人资料与密码修改
- 登录态校验与权限拦截

### 2. 科室医生模块
- 科室新增、编辑、启停
- 医生资料维护
- 医生按科室展示与搜索
- 患者查看医生简介、职称、擅长方向

### 3. 排班号源模块
- 医生按日期发布排班
- 设置出诊时段、挂号费、总号源与剩余号源
- 管理员查看全院排班
- 医生查看个人排班

### 4. 在线挂号模块
- 患者选择科室、医生和日期进行预约
- 生成预约记录与订单
- 支持预约取消、状态跟踪
- 就诊完成后允许评价

### 5. 支付订单模块
- 模拟在线支付
- 支付状态、支付时间记录
- 患者查看我的挂号订单
- 管理员查看全量订单

### 6. 就诊服务模块
- 患者档案维护
- 就诊卡维护
- 医生查看预约患者
- 医生更新接诊状态

### 7. 内容展示模块
- 公告发布与展示
- 首页轮播推荐
- 患者首页展示医院公告、热门医生、轮播内容

### 8. 统计分析模块
- 用户总数、医生总数、预约总数、支付金额统计
- 科室预约排行
- 近七日预约趋势
- 医生热度排行

## 技术设计

### 数据库设计
1. sys_user
2. patient_profile
3. medical_card
4. department_info
5. doctor_info
6. doctor_schedule
7. appointment_record
8. payment_order
9. doctor_review
10. news_notice
11. banner_info
12. operation_log

### API 接口设计

#### 登录
- 请求方式: POST
- 路径: /api/auth/login
- 参数: { "username": "patient01", "password": "123456" }

#### 注册
- 请求方式: POST
- 路径: /api/auth/register
- 参数: { "username": "newuser", "password": "123456", "nickname": "张三", "phone": "13800000000" }

#### 科室列表
- 请求方式: GET
- 路径: /api/department/list
- 参数: pageNum, pageSize, name, status

#### 医生大厅
- 请求方式: GET
- 路径: /api/doctor/public/list
- 参数: departmentId, keyword

#### 排班列表
- 请求方式: GET
- 路径: /api/schedule/public/list
- 参数: doctorId, scheduleDate

#### 创建挂号
- 请求方式: POST
- 路径: /api/appointment/create
- 参数: { "scheduleId": 1, "cardId": 1, "remark": "复诊" }

#### 支付订单
- 请求方式: PUT
- 路径: /api/order/pay/{id}

#### 我的挂号
- 请求方式: GET
- 路径: /api/appointment/my

#### 医生评价
- 请求方式: POST
- 路径: /api/review/save
- 参数: { "appointmentId": 1, "doctorId": 2, "rating": 5, "content": "医生很耐心" }

#### 统计看板
- 请求方式: GET
- 路径: /api/statistics/dashboard

## 用户角色
- 管理员：admin，负责用户、科室、医生、排班、公告、轮播、订单和统计
- 医生：doctor，负责个人资料、个人排班、预约患者和接诊状态
- 患者：patient，负责注册登录、挂号预约、支付、评价、查看公告和维护就诊信息

## 默认账号
- admin / 123456
- doctor01 / 123456
- patient01 / 123456
