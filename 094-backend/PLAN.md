# 094 项目实施计划

## 问题陈述
需要构建一个聚焦宠物友好消费与门店经营协同的宠物咖啡馆平台，完成区域门店、店宠展示、座位预约、菜单消费、余额支付、评价回复和运营看板的一体化管理。

## 当前状态
- 094 项目前后端目录已创建
- 已基于既有 Spring Boot + Vue3 骨架完成结构迁移
- 已切换应用名、数据库名、包名与项目编号

## 实施方案

### 第一阶段：后端开发
1. 基础架构
- 调整 pom.xml、application.yml、启动类
- 保留 Result、异常处理、JWT、Redis、MyBatis-Plus 配置

2. 数据库脚本
- 完成 12 张表结构
- 写入管理员、店长、顾客演示数据
- 准备区域、门店、店宠、菜单、座位、预约、订单、支付、评价、公告样例

3. 核心实体与 Mapper
- 用户、区域、门店、店宠
- 分类、菜单、座位、预约
- 订单、支付、评价、公告

4. Service 层
- 登录注册与用户资料维护
- 区域、门店、店宠、菜单、座位管理
- 预约创建、状态流转与座位释放
- 订单创建、余额支付、菜单库存扣减
- 评价回复、公告管理、统计分析

5. Controller 层
- AuthController
- UserController
- AreaController
- ShopController
- PetController
- CategoryController
- MenuController
- SeatController
- ReservationController
- OrderController
- PaymentController
- ReviewController
- NoticeController
- StatisticsController

### 第二阶段：前端开发
1. 基础工程
- 保留 Vue3 + Element Plus + Pinia + Axios + ECharts
- 调整路由、接口、菜单和角色守卫
- 改造系统名称、模块名称和角色显示

2. 页面开发
- 登录页
- 布局页
- 宠物咖啡馆首页
- 用户管理页
- 区域管理页
- 门店管理页
- 店宠管理页
- 菜单分类页
- 菜单管理页
- 座位管理页
- 预约管理页
- 订单管理页
- 钱包支付页
- 评价管理页
- 公告通知页
- 统计分析页
- 个人中心页

3. 联调优化
- 统一角色文案为管理员、店长、顾客
- 统一门店状态、座位状态、预约状态、订单状态、评价状态展示
- 统一首页指标、公告信息和页面提示语

## 交付结果
- 094-backend 完整后端代码
- 094-frontend 完整前端代码
- 宠物咖啡馆平台数据库脚本与测试数据
- 094-backend/PRD.md
- 094-backend/PLAN.md
