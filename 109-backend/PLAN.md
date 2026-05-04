# 数据脱敏与敏感信息识别平台实施计划

## 问题陈述
企业数据资产中存在身份证、手机号、银行卡、邮箱、地址等敏感字段，需要通过识别、复核、脱敏、审批和告警闭环降低数据泄露风险。

## 当前状态
创建 109-backend 和 109-frontend 两个工程目录，后端使用 Spring Boot + MyBatis-Plus，前端使用 Vue3 + Element Plus。

## 第一阶段：后端开发
1. 创建 MyBatis-Plus、Redis、JWT、统一返回和异常处理基础架构。
2. 创建 data_masking_109 数据库和14张业务表。
3. 创建14个实体、Mapper、Service和Controller。
4. 实现分页查询、CRUD、状态筛选和关键状态流转动作。
5. 实现登录、Redis Token 校验和首页统计接口。

## 第二阶段：前端开发
1. 创建 Vite、Vue、Pinia、Axios、路由和请求封装。
2. 创建登录页、布局页、首页看板和14个业务页面。
3. 使用通用 DataPage 组件实现搜索、表格、分页、弹窗和状态动作。
4. 使用 ECharts 展示识别趋势和风险类型分布。

## 第三阶段：合集登记
1. 更新 readme.md、readme_simple.md 和候选选题清单。
2. 核对 data_masking_109、root/1234、8109、3109 和 Redis database 12。
3. 核对无注释、待办标记、乱码和旧项目串。
