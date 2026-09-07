# 095 项目实施计划

## 问题陈述
需要构建一个聚焦联赛组织、球队维护、比赛管理和球迷查询的足球联赛管理系统，完成联赛赛季、俱乐部、球队、教练、球员、球场、赛程、积分榜、公告和统计看板的一体化管理。

## 当前状态
- 095 项目前后端目录已创建
- 已基于既有 Spring Boot + Vue3 骨架完成结构复制
- 已切换应用名、数据库名、包名与项目编号

## 实施方案

### 第一阶段：后端开发
1. 基础架构
- 调整 pom.xml、application.yml、启动类
- 保留 Result、异常处理、JWT、Redis、MyBatis-Plus 配置

2. 数据库脚本
- 完成 12 张表结构
- 写入管理员、俱乐部经理、球迷演示数据
- 准备联赛、赛季、俱乐部、球队、教练、球员、球场、比赛、积分榜、资讯、关注样例

3. 核心实体与 Mapper
- 用户、联赛、赛季、俱乐部、球队
- 教练、球员、球场、比赛
- 积分榜、资讯、关注

4. Service 层
- 登录注册与用户资料维护
- 联赛、赛季、俱乐部、球队、教练、球员、球场管理
- 比赛维护、赛果录入与积分榜重算
- 球迷关注、资讯管理、统计分析

5. Controller 层
- AuthController
- UserController
- LeagueController
- SeasonController
- ClubController
- TeamController
- CoachController
- PlayerController
- VenueController
- MatchController
- StandingController
- FollowController
- NewsController
- StatisticsController

### 第二阶段：前端开发
1. 基础工程
- 保留 Vue3 + Element Plus + Pinia + Axios + ECharts
- 调整路由、接口、菜单和角色守卫
- 改造系统名称、模块名称和角色显示

2. 页面开发
- 登录页
- 布局页
- 足球联赛首页
- 用户管理页
- 联赛管理页
- 赛季管理页
- 俱乐部管理页
- 球队管理页
- 教练管理页
- 球员管理页
- 球场管理页
- 赛程管理页
- 积分榜页
- 球队关注页
- 资讯公告页
- 统计分析页
- 个人中心页

3. 联调优化
- 统一角色文案为管理员、俱乐部经理、球迷
- 统一联赛状态、赛季状态、比赛状态展示
- 统一首页指标、公告信息和提示语
