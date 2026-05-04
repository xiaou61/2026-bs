# 网络钓鱼邮件演练与安全意识培训平台实施计划

## 问题陈述
企业员工面对钓鱼邮件时缺少可量化的演练、培训和复盘机制，需要通过模拟邮件、行为追踪、课程考试和风险评分形成安全意识提升闭环。

## 当前状态
创建 111-backend 和 111-frontend 两个工程目录，后端使用 Spring Boot + MyBatis-Plus，前端使用 Vue3 + Element Plus。

## 第一阶段：后端开发
1. 创建 MyBatis-Plus、Redis、JWT、统一返回和异常处理基础架构。
2. 创建 phishing_training_111 数据库和14张业务表。
3. 创建14个实体、Mapper、Service和Controller。
4. 实现分页查询、CRUD、状态筛选和关键状态流转动作。
5. 实现登录、Redis Token 校验和首页统计接口。

## 第二阶段：前端开发
1. 创建 Vite、Vue、Pinia、Axios、路由和请求封装。
2. 创建登录页、布局页、首页看板和14个业务页面。
3. 使用通用 DataPage 组件实现搜索、表格、分页、弹窗和状态动作。
4. 使用 ECharts 展示演练趋势和风险分布。

## 第三阶段：合集登记
1. 更新 readme.md、readme_simple.md 和候选选题清单。
2. 核对 phishing_training_111、root/1234、8111、3111 和 Redis database 14。
3. 核对无注释、待办标记、乱码和旧项目串。
