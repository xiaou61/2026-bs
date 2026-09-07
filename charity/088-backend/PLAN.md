# 088项目实施计划

## 问题陈述
需要构建一个完整的孩童收养信息管理系统，实现儿童档案维护、申请人在线申请、审核评估、匹配审批、协议签订、回访记录和统计展示的一体化流程。

## 当前状态
- 根目录已有多个前后端项目模板可复用
- 088 项目目录尚未建立完整代码结构
- 已完成 088 项目方案确认，准备进入文档与实现阶段

## 实施方案

### 第一阶段: 后端开发
1. 基础架构
- 创建 pom.xml
- 创建 application.yml
- 创建启动类
- 创建 Result、异常处理、JWT 鉴权
- 创建 Redis、WebMvc、MyBatis-Plus 配置

2. 数据库脚本
- 创建 sql/init.sql
- 完成 12 张表与测试数据

3. 实体与 Mapper
- 创建用户、申请人、儿童、健康、申请、材料、家访、匹配、审批、协议、回访、公告实体
- 创建 Mapper 接口

4. Service 层
- 登录注册与角色权限
- 儿童档案管理
- 申请人资料管理
- 收养申请与材料审核
- 家访、匹配、审批、协议、回访
- 公告与统计

5. Controller 层
- AuthController
- UserController
- ChildController
- AdopterController
- ApplicationController
- HomeVisitController
- MatchController
- AgreementController
- FollowController
- NoticeController
- StatisticsController

### 第二阶段: 前端开发
1. 基础工程
- package.json、vite.config.js、main.js、App.vue
- 路由与权限守卫
- Axios 封装
- Pinia 用户状态

2. 页面开发
- 登录页
- 注册页
- 布局页
- 仪表盘页
- 儿童档案页
- 申请人资料页
- 收养申请页
- 审核评估页
- 匹配审批页
- 协议管理页
- 回访管理页
- 公告管理页
- 门户首页
- 我的申请页

3. 联调优化
- 按角色显示菜单
- 统一状态字段映射
- 表单校验与错误提示统一

### 第三阶段: 文档更新
1. 更新项目合集
- 更新 readme.md
- 更新 readme_simple.md

## 交付结果
- 088-backend 后端完整代码
- 088-frontend 前端完整代码
- 孩童收养信息管理系统数据库脚本与测试数据
- 088-backend/PRD.md
- 088-backend/PLAN.md
