# 096 项目实施计划

## 问题陈述
需要构建一个覆盖患者端预约挂号、医生端排班接诊、管理员端统一维护的线上医院挂号系统，完成医院基础资料、医生排班、在线挂号、订单支付、评价反馈、公告轮播和统计看板的一体化实现。

## 当前状态
- 096 项目前后端目录已创建
- 已选定 092-backend 作为 MyBatis 后端骨架
- 已选定 075-frontend 作为双端预约前端骨架

## 实施方案

### 第一阶段：后端开发
1. 基础架构
- 调整 pom.xml、application.yml、启动类、包名和数据库名
- 保留 Result、异常处理、JWT、Redis、PageHelper 配置

2. 数据库脚本
- 完成 12 张表结构
- 写入管理员、医生、患者演示数据
- 准备科室、医生、排班、订单、评价、公告、轮播完整样例

3. 核心实体与 Mapper
- 用户、患者档案、就诊卡
- 科室、医生、排班
- 预约记录、支付订单、医生评价
- 公告、轮播、操作日志

4. Service 层
- 登录注册与个人资料维护
- 用户、科室、医生、排班管理
- 挂号创建、取消、完成与支付
- 评价、公告、轮播和统计分析

5. Controller 层
- AuthController
- UserController
- DepartmentController
- DoctorController
- ScheduleController
- AppointmentController
- OrderController
- CardController
- ReviewController
- NoticeController
- BannerController
- StatisticsController

### 第二阶段：前端开发
1. 基础工程
- 保留 Vue3 + Element Plus + Pinia + Axios + ECharts
- 调整路由、接口、菜单和角色守卫
- 改造系统名称、模块名称和角色显示

2. 页面开发
- 登录页
- 布局页
- 医院首页
- 用户管理页
- 科室管理页
- 医生管理页
- 排班管理页
- 挂号管理页
- 我的挂号页
- 支付订单页
- 就诊卡页
- 评价管理页
- 公告页
- 轮播管理页
- 统计分析页
- 个人中心页

3. 联调优化
- 统一角色文案为管理员、医生、患者
- 统一预约状态、支付状态、排班状态展示
- 统一首页轮播、公告和提示语
