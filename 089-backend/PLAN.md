# 089项目实施计划

## 问题陈述
需要构建一个面向铁路场景的在线订票平台，实现列车班次发布、余票查询、在线选座购票、电子出票、退改签审核和统计分析的一体化流程。

## 当前状态
- 已复制 070 购票系统作为初始工程底座
- 089 项目前后端目录已创建
- 包名、工程名、数据库名已切换到 railway 场景

## 实施方案

### 第一阶段: 后端开发
1. 基础信息重构
- 调整 pom.xml 与 application.yml
- 更新启动类、JWT、Redis 前缀与公共配置

2. 数据库脚本
- 重写 sql/init.sql
- 创建 12 张铁路业务表与测试数据

3. 实体与 Mapper
- 重建车站、列车、车厢模板、班次、座位、乘车人、订单、车票、支付、售后、公告实体
- 调整 Mapper 接口名称与映射关系

4. Service 层
- 登录注册与角色权限
- 基础资料管理
- 余票查询与座位锁定
- 下单支付与电子出票
- 退改签审核与座位回滚
- 公告与统计

5. Controller 层
- AuthController
- UserController
- StationController
- TrainController
- CarriageController
- ScheduleController
- SeatController
- PassengerController
- OrderController
- PaymentController
- TicketController
- AfterSaleController
- NoticeController
- StatisticsController

### 第二阶段: 前端开发
1. 基础工程
- 改造路由、菜单、权限守卫
- 调整 Axios 封装与用户状态管理
- 统一状态字典与页面风格

2. 页面开发
- 登录页
- 数据看板
- 用户管理
- 车站管理
- 列车管理
- 车厢模板管理
- 班次管理
- 选座购票页
- 订单管理与我的订单
- 支付记录
- 电子票管理与我的车票
- 常用乘车人
- 退改签管理
- 公告中心
- 个人中心

3. 联动优化
- 按角色裁剪菜单
- 统一订单与售后状态展示
- 余票、票价、发车时间联动显示

### 第三阶段: 文档更新
1. 更新项目合集
- 更新 readme.md
- 更新 readme_simple.md

## 交付结果
- 089-backend 后端完整代码
- 089-frontend 前端完整代码
- 铁路订票平台数据库脚本与测试数据
- 089-backend/PRD.md
- 089-backend/PLAN.md
