# 基于SpringBoot+Vue+uniapp的电器维修系统小程序

## 项目概述

### 项目简介
本项目是一个面向家电售后场景的电器维修管理系统，采用后台管理端（Vue）+ 小程序端（uniapp）+ SpringBoot 后端的三端架构，支持用户报修、平台派单、技师处理、进度跟踪、评价反馈等完整闭环。

### 核心功能
- 用户注册登录与角色管理
- 电器分类管理
- 维修工单全流程管理
- 技师管理与派单
- 维修进度记录
- 备件管理
- 公告通知
- 维修评价与统计分析

### 技术栈

**后端**
- Spring Boot 2.7.14
- MyBatis-Plus 3.5.3
- MySQL 8.0
- Redis 7.x
- JWT

**管理端**
- Vue 3.4.0
- Element Plus 2.4.4
- Vite 5
- Pinia 2.1.7
- Axios 1.6.2

**小程序端**
- uni-app
- Vue 3
- Pinia 2.1.7

## 功能需求

### 1. 用户与权限
- 管理员、技师、普通用户三类角色
- 账号密码登录
- 用户列表与状态管理

### 2. 电器分类管理
- 分类新增/编辑/删除
- 分类启停状态
- 小程序端可获取可用分类

### 3. 工单管理
- 用户提交报修工单
- 平台查看工单并派单
- 工单状态流转（待受理/已派单/上门中/待支付/已完成/已取消）
- 用户查看并取消本人工单

### 4. 技师管理
- 技师信息维护
- 技能标签、服务区域、等级、状态管理

### 5. 维修进度
- 记录工单节点日志
- 前后端展示工单处理时间线

### 6. 备件管理
- 备件台账维护
- 库存、单价、状态管理

### 7. 公告通知
- 管理端发布公告
- 小程序端查看已发布公告与详情

### 8. 评价与统计
- 用户提交工单评价
- 管理端查看评价列表
- 仪表盘统计工单、技师与评分数据

## 数据库设计

### 核心表
1. user（用户）
2. appliance_category（电器分类）
3. technician（技师）
4. repair_order（维修工单）
5. repair_process（维修进度）
6. spare_part（备件）
7. notice（公告）
8. evaluate（评价）

## 关键接口

### 用户
- POST /api/user/login
- POST /api/user/register
- GET /api/user/info
- GET /api/user/list

### 分类
- GET /api/category/list
- GET /api/category/public/list
- POST /api/category/add
- PUT /api/category/update
- DELETE /api/category/delete/{id}

### 工单
- GET /api/order/list
- GET /api/order/detail/{id}
- PUT /api/order/assign
- PUT /api/order/status
- GET /api/order/user/list
- POST /api/order/user/create
- PUT /api/order/user/cancel/{id}

### 技师
- GET /api/technician/list
- POST /api/technician/add
- PUT /api/technician/update
- DELETE /api/technician/delete/{id}

### 进度
- GET /api/process/list/{orderId}
- POST /api/process/add

### 备件
- GET /api/part/list
- POST /api/part/add
- PUT /api/part/update
- DELETE /api/part/delete/{id}

### 公告
- GET /api/notice/list
- GET /api/notice/public/list
- GET /api/notice/public/detail/{id}
- POST /api/notice/add

### 评价
- GET /api/evaluate/list
- POST /api/evaluate/user/submit
- GET /api/evaluate/user/my

### 统计
- GET /api/statistics/dashboard
- GET /api/statistics/order-trend

## 默认账号
- 管理员：admin / 123456
- 技师：tech1 / 123456
- 用户：user1 / 123456
