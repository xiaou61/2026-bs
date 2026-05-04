# 云原生成本分析与资源优化平台实施计划

## 问题陈述
企业上云后账单来源分散、资源归属不清、闲置资源发现滞后，需要一个前后端分离的 FinOps 管理系统完成云成本透明化、预算控制、优化建议执行和报告归档。

## 当前状态
创建 108-backend 和 108-frontend 两个工程目录，后端使用 Spring Boot + MyBatis + PageHelper，前端使用 Vue3 + Element Plus。

## 第一阶段：后端开发
1. 创建 pom.xml、application.yml、启动类、统一返回、异常处理、JWT拦截、Redis Token 服务。
2. 创建 cloud_cost_108 数据库和14张业务表。
3. 创建 账号权限、云账号、资源命名空间、成本账单、成本明细、预算策略、成本分摊、闲置资源、优化规则、优化建议、节省计划、成本异常、报告快照、操作日志 实体类。
4. 创建 MyBatis 注解 Mapper，实现分页、筛选、CRUD、计数和状态变更。
5. 创建 Service 层封装分页、保存、删除和状态流转。
6. 创建 Controller 层暴露 REST API。

## 第二阶段：前端开发
1. 创建 Vite、Vue、Pinia、Axios、路由和请求封装。
2. 创建登录页、布局页、首页看板和14个业务页面。
3. 使用通用 DataPage 组件实现搜索、表格、分页、弹窗和状态动作。
4. 使用 ECharts 展示成本趋势和优化节省分布。

## 第三阶段：合集登记
1. 更新 readme.md 项目总数和108完整介绍。
2. 更新 readme_simple.md 项目总数和108速览。
3. 更新 docs/topic-candidates-097-146.md，将108标记为已实现。

## 静态核对
1. 核对 root/1234、8108、3108、cloud_cost_108 和 Redis database 11。
2. 核对无注释、待办标记、乱码和旧项目串。
3. 核对14个实体、14个Mapper、17个前端页面齐全。
