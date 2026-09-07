# 090项目实施计划

## 问题陈述
需要构建一个完整的戏曲文化苑系统，实现基础组织维护、剧目排期、会员预约、传承过程管理和结果统计分析的一体化流程。

## 当前状态
- 根目录已有多个前后端项目模板可复用
- 090 项目目录已创建
- 已完成 090 项目设计文档与 PRD 初稿

## 实施方案

### 第一阶段: 后端开发
1. 基础架构
- 创建 pom.xml
- 创建 application.yml
- 创建启动类
- 创建 Result、异常处理、JWT 鉴权
- 创建 Redis 与 WebMvc 配置

2. 数据库脚本
- 创建 sql/init.sql
- 完成 14 张表与测试数据

3. 实体与 Mapper
- 创建组织、剧目、排期、预约、资源、签到、研学评分、赏析互动、公告、日志实体
- 创建 Mapper 接口与 XML

4. Service 层
- 登录与角色权限
- 组织管理
- 剧目与排期管理
- 预约与行程
- 资源、签到、研学评分、赏析互动
- 公告与统计

5. Controller 层
- AuthController
- CategoryController
- ArtistController
- VenueController
- SeasonController
- RepertoireController
- PerformanceController
- BookingController
- MediaResourceController
- CheckinController
- ReviewController
- CommentController
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
- 布局页
- 仪表盘页
- 组织管理页
- 剧目管理页
- 排期管理页
- 预约中心页
- 行程页
- 资源页
- 签到页
- 研学评分页
- 赏析互动页
- 公告页

3. 联调优化
- 按角色显示菜单
- 字段映射与状态字典统一
- 表单校验与错误提示统一

## 交付结果
- 090-backend 后端完整代码
- 090-frontend 前端完整代码
- 戏曲文化苑系统数据库脚本与测试数据
- 090-backend/PRD.md
- 090-backend/PLAN.md


