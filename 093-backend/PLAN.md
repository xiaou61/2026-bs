# 093 项目实施计划

## 问题陈述
需要构建一个聚焦设备零售与库存运营的自助售货管理系统，完成点位设备、商品货道、补货登记、顾客下单支付、出货追踪、故障处理和经营看板的一体化管理。

## 当前状态
- 093 项目前后端目录已创建
- 已基于既有 Spring Boot + Vue3 骨架完成结构搭建
- 已切换应用名、数据库名、包名与项目编号

## 实施方案

### 第一阶段：后端开发
1. 基础架构
- 调整 pom.xml、application.yml、启动类
- 保留 Result、异常处理、JWT、Redis、MyBatis-Plus 配置

2. 数据库脚本
- 完成 12 张表结构
- 写入管理员、补货员、顾客演示数据
- 准备点位、设备、商品、货道、补货、订单、支付、故障样例

3. 核心实体与 Mapper
- 用户、点位、设备、分类、商品、货道
- 补货、订单、支付、出货、故障、公告

4. Service 层
- 登录注册与用户资料维护
- 点位、设备、商品、货道管理
- 补货登记与库存回写
- 订单创建、余额支付、自动出货
- 故障处理、公告管理、统计分析

5. Controller 层
- AuthController
- UserController
- LocationController
- MachineController
- CategoryController
- ProductController
- SlotController
- ReplenishController
- OrderController
- PaymentController
- ShipmentController
- FaultController
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
- 自助售货首页
- 用户管理页
- 点位管理页
- 设备管理页
- 分类管理页
- 商品管理页
- 货道管理页
- 补货记录页
- 订单管理页
- 支付记录页
- 出货记录页
- 故障反馈页
- 公告通知页
- 统计分析页
- 个人中心页

3. 联调优化
- 统一角色文案为管理员、补货员、顾客
- 统一设备状态、订单状态、出货状态、故障状态展示
- 统一页面提示语和仪表盘指标

## 交付结果
- 093-backend 完整后端代码
- 093-frontend 完整前端代码
- 自助售货管理系统数据库脚本与测试数据
- 093-backend/PRD.md
- 093-backend/PLAN.md
