# 083项目实施计划

## 问题陈述
需要落地一个基于B/S架构的老年人体检管理系统，实现从老人建档到体检报告与异常随访的完整业务闭环。

## 当前状态
- 根目录已存在001-082项目模板可复用
- 083项目目录尚未完整初始化
- 已完成PRD编写

## 实施方案

### 第一阶段: 后端开发
1. 基础架构
- 创建pom.xml
- 创建application.yml
- 创建启动类
- 创建通用返回、异常处理、JWT拦截
- 创建MyBatis与Redis配置

2. 数据库脚本
- 创建sql/init.sql
- 完成12张表结构与初始数据

3. 实体与Mapper
- 创建12个实体类
- 创建Mapper接口与XML映射

4. Service层
- 登录鉴权服务
- 老人档案服务
- 套餐项目服务
- 预约服务
- 结果录入与预警服务
- 统计服务

5. Controller层
- AuthController
- ElderController
- PackageController
- AppointmentController
- ResultController
- WarningController
- StatisticsController

### 第二阶段: 前端开发
1. 基础工程
- package.json
- vite.config.js
- main.js与App.vue
- 路由与权限守卫
- Axios请求封装
- Pinia用户状态

2. 页面开发
- 登录页
- 布局页
- 仪表盘
- 老人档案管理页
- 套餐项目管理页
- 预约管理页
- 结果录入页
- 异常预警与随访页
- 通知管理页

3. 联调与细节完善
- 接口字段对齐
- 错误提示统一
- 表格分页与弹窗表单行为统一

## 交付结果
- 083-backend完整后端代码
- 083-frontend完整前端代码
- 根目录readme.md与readme_simple.md新增083项目说明
