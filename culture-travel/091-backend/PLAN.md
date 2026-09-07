# 091项目实施计划

## 问题陈述
需要构建一个聚焦影院会员运营的管理系统，完成会员档案、储值积分、优惠券、消费订单、电子观影码和门店运营看板的一体化管理。

## 当前状态
- 091 项目前后端目录已创建
- 已基于既有票务模板完成基础骨架复制
- 已切换后端包名、数据库名与系统标识

## 实施方案

### 第一阶段：后端开发
1. 基础架构
- 调整 pom.xml、application.yml、启动类
- 统一 Result、异常处理、JWT、Redis 配置

2. 数据库脚本
- 完成 12 张表结构
- 写入管理员、员工、会员演示数据
- 准备影片、门店、场次、优惠券、订单、储值样例

3. 核心实体与 Mapper
- 用户、影片、影院、影厅、场次、座位
- 订单、观影码、支付、评价、优惠券、会员券包

4. Service 层
- 登录注册与会员资料维护
- 储值、积分、等级升级逻辑
- 会员订单与余额支付
- 优惠券领取与抵扣
- 观影码核销与统计分析

5. Controller 层
- AuthController
- UserController
- MovieController
- CinemaController
- HallController
- ShowtimeController
- SeatController
- OrderController
- PaymentController
- TicketController
- CouponController
- CommentController
- StatisticsController

### 第二阶段：前端开发
1. 基础工程
- 保留 Vue3 + Element Plus + Pinia + Axios + ECharts
- 调整路由和权限守卫
- 改造系统名称、菜单、角色显示

2. 页面开发
- 登录页
- 布局页
- 会员运营首页
- 会员档案页
- 影片、门店、影厅、场次页
- 会员订票页
- 消费订单页
- 储值记录页
- 电子观影码页
- 优惠券页
- 评价反馈页
- 统计分析页
- 个人中心页

3. 联调优化
- 统一角色文案为管理员、门店员工、会员
- 统一会员等级、订单状态、支付状态展示
- 统一页面提示语和仪表盘指标

## 交付结果
- 091-backend 完整后端代码
- 091-frontend 完整前端代码
- 电影院会员管理系统数据库脚本与测试数据
- 091-backend/PRD.md
- 091-backend/PLAN.md
