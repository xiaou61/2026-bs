# 095 - SpringBoot 足球联赛管理系统设计与实现

## 项目概述

### 项目简介
足球联赛管理系统面向校园联赛、城市业余联赛和协会赛事组织场景，围绕联赛、赛季、俱乐部、球队、教练、球员、赛程、赛果、积分榜、公告资讯和统计看板等核心流程，构建一个兼顾赛事组织与球迷查询体验的一体化平台。

### 核心功能
- 管理员、俱乐部经理、球迷三角色登录与权限控制
- 联赛、赛季、俱乐部、球队、教练、球员、球场等基础资料管理
- 比赛赛程维护、比分录入和积分榜自动统计
- 球迷查看赛程、球队、球员、积分榜与资讯公告
- 球迷关注球队，管理员查看数据看板

### 技术栈

后端
- Spring Boot 2.7.18
- MyBatis-Plus 3.5.5
- MySQL 8.0
- Redis 7.x
- JWT

前端
- Vue 3.4.0
- Element Plus 2.4.4
- Vue Router 4.2.5
- Pinia 2.1.7
- Axios 1.6.2
- ECharts 5.4.3

## 功能需求

### 1. 账号与权限模块
- 管理员、俱乐部经理、球迷三角色
- 登录、注册、退出、密码修改
- 个人资料维护

### 2. 联赛与赛季模块
- 联赛基础资料维护
- 赛季配置、状态切换和轮次数配置
- 联赛与赛季联动查询

### 3. 俱乐部与球队模块
- 俱乐部资料维护
- 球队归属赛季、主场和状态管理
- 球迷查看球队基础资料

### 4. 教练与球员模块
- 教练档案管理
- 球员号码、位置、年龄、国籍、进球和助攻统计
- 球员所属球队展示

### 5. 球场与赛程模块
- 球场资料管理
- 比赛赛程、轮次、主客队、时间和裁判维护
- 比分录入与比赛状态流转

### 6. 积分榜与资讯模块
- 按赛季查看积分榜
- 管理员发布公告资讯
- 球迷查看公告并关注球队

### 7. 统计分析模块
- 总比赛数、已完赛数、总进球数、总球队数统计
- 近 7 天赛程趋势
- 积分榜排行和射手榜展示

## 技术设计

### 数据库设计
1. sys_user
2. league_info
3. season_info
4. club_info
5. team_info
6. coach_info
7. player_info
8. venue_info
9. match_schedule
10. standing_record
11. news_notice
12. fan_follow

### API 接口设计

#### 登录
- 请求方式: POST
- 路径: /api/auth/login
- 参数: { "username": "fan", "password": "123456" }

#### 联赛分页
- 请求方式: GET
- 路径: /api/league/list
- 参数: pageNum, pageSize, name, status

#### 球队公开列表
- 请求方式: GET
- 路径: /api/team/public/list

#### 球员公开列表
- 请求方式: GET
- 路径: /api/player/public/list

#### 比赛分页
- 请求方式: GET
- 路径: /api/match/list
- 参数: pageNum, pageSize, seasonId, status, teamId

#### 录入赛果
- 请求方式: PUT
- 路径: /api/match/result/{id}
- 参数: { "homeScore": 2, "awayScore": 1, "referee": "张磊", "remark": "下半场补时绝杀" }

#### 积分榜
- 请求方式: GET
- 路径: /api/standing/list
- 参数: pageNum, pageSize, seasonId, teamName

#### 公告资讯
- 请求方式: GET
- 路径: /api/news/public/list

#### 统计看板
- 请求方式: GET
- 路径: /api/statistics/dashboard

## 用户角色
- 管理员：admin，负责用户、联赛、赛季、公告和统计
- 俱乐部经理：manager，负责俱乐部、球队、教练、球员、球场和比赛维护
- 球迷：fan，负责查看赛程、球队、积分榜、资讯并关注球队

## 默认账号
- admin / 123456
- manager / 123456
- fan / 123456
