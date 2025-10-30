# 校园运动健康管理平台 PRD

## 项目概述

校园运动打卡与健康管理系统，帮助学生记录运动数据、制定健身计划、组织运动活动、预约场馆，提升校园运动氛围。

## 功能模块

### 1. 用户管理
- 用户注册登录
- 个人信息管理
- 角色：学生、教练、管理员

### 2. 运动打卡
- 跑步打卡（GPS轨迹、配速、距离、时长）
- 健身房打卡
- 球类运动打卡
- 运动轨迹记录与回放

### 3. 健身计划
- 个人健身计划制定
- 教练指导计划
- 计划执行进度跟踪
- 计划模板库

### 4. 约球活动
- 发起约球活动
- 参与活动报名
- 活动组队管理
- 活动状态跟踪

### 5. 排行榜
- 每日步数排行
- 运动时长排行
- 积分排行
- 周榜、月榜

### 6. 健康档案
- BMI指数计算
- 体脂率记录
- 饮食记录
- 健康数据趋势分析

### 7. 场馆预约
- 场馆信息展示
- 时段预约
- 预约记录管理
- 签到核销

## 数据库设计

### 1. 用户表 (user)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| username | VARCHAR(50) | 用户名 |
| password | VARCHAR(200) | 密码(加密) |
| nickname | VARCHAR(50) | 昵称 |
| avatar | VARCHAR(200) | 头像URL |
| gender | VARCHAR(10) | 性别 |
| phone | VARCHAR(20) | 手机号 |
| email | VARCHAR(50) | 邮箱 |
| role | VARCHAR(20) | 角色(student/coach/admin) |
| height | DECIMAL(5,2) | 身高(cm) |
| weight | DECIMAL(5,2) | 体重(kg) |
| points | INT | 积分 |
| status | INT | 状态(0正常1禁用) |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |
| deleted | INT | 逻辑删除 |

### 2. 运动记录表 (sport_record)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| sport_type | VARCHAR(20) | 运动类型(running/gym/basketball/football/badminton) |
| duration | INT | 运动时长(分钟) |
| distance | DECIMAL(8,2) | 距离(公里) |
| calories | INT | 消耗卡路里 |
| steps | INT | 步数 |
| avg_speed | DECIMAL(5,2) | 平均配速(分钟/公里) |
| points_earned | INT | 获得积分 |
| remark | VARCHAR(500) | 备注 |
| sport_date | DATE | 运动日期 |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |
| deleted | INT | 逻辑删除 |

### 3. 运动轨迹表 (sport_track)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| record_id | BIGINT | 运动记录ID |
| latitude | DECIMAL(10,7) | 纬度 |
| longitude | DECIMAL(10,7) | 经度 |
| altitude | DECIMAL(8,2) | 海拔(米) |
| speed | DECIMAL(5,2) | 瞬时速度(km/h) |
| track_time | DATETIME | 轨迹时间 |
| create_time | DATETIME | 创建时间 |

### 4. 健身计划表 (fitness_plan)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| coach_id | BIGINT | 教练ID(可为空) |
| plan_name | VARCHAR(100) | 计划名称 |
| plan_type | VARCHAR(20) | 计划类型(strength/cardio/lose_weight/gain_muscle) |
| target_desc | VARCHAR(500) | 目标描述 |
| duration_days | INT | 计划天数 |
| plan_content | TEXT | 计划详情(JSON) |
| start_date | DATE | 开始日期 |
| end_date | DATE | 结束日期 |
| completed_days | INT | 已完成天数 |
| status | VARCHAR(20) | 状态(active/completed/abandoned) |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |
| deleted | INT | 逻辑删除 |

### 5. 约球活动表 (team_activity)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| creator_id | BIGINT | 发起人ID |
| activity_name | VARCHAR(100) | 活动名称 |
| sport_type | VARCHAR(20) | 运动类型 |
| venue_id | BIGINT | 场馆ID(可为空) |
| activity_time | DATETIME | 活动时间 |
| max_participants | INT | 最大人数 |
| current_participants | INT | 当前人数 |
| description | VARCHAR(500) | 活动描述 |
| level_requirement | VARCHAR(20) | 水平要求(beginner/intermediate/advanced) |
| status | VARCHAR(20) | 状态(recruiting/full/ongoing/completed/cancelled) |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |
| deleted | INT | 逻辑删除 |

### 6. 活动参与者表 (activity_participant)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| activity_id | BIGINT | 活动ID |
| user_id | BIGINT | 用户ID |
| join_time | DATETIME | 加入时间 |
| status | VARCHAR(20) | 状态(joined/cancelled/completed) |
| rating | INT | 评分(1-5) |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |

### 7. 健康档案表 (health_profile)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| record_date | DATE | 记录日期 |
| weight | DECIMAL(5,2) | 体重(kg) |
| bmi | DECIMAL(4,2) | BMI指数 |
| body_fat | DECIMAL(4,2) | 体脂率(%) |
| muscle_mass | DECIMAL(5,2) | 肌肉量(kg) |
| water_intake | INT | 饮水量(ml) |
| sleep_hours | DECIMAL(3,1) | 睡眠时长(小时) |
| diet_calories | INT | 饮食卡路里 |
| diet_record | TEXT | 饮食记录(JSON) |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |
| deleted | INT | 逻辑删除 |

### 8. 场馆表 (venue)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| venue_name | VARCHAR(100) | 场馆名称 |
| venue_type | VARCHAR(20) | 场馆类型(basketball/badminton/tennis/gym) |
| location | VARCHAR(200) | 位置 |
| capacity | INT | 容纳人数 |
| facilities | VARCHAR(500) | 设施说明 |
| opening_time | TIME | 开放时间 |
| closing_time | TIME | 关闭时间 |
| price_per_hour | DECIMAL(8,2) | 每小时价格 |
| image_url | VARCHAR(200) | 图片URL |
| status | INT | 状态(0正常1维护) |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |
| deleted | INT | 逻辑删除 |

### 9. 场馆预约表 (venue_booking)
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| venue_id | BIGINT | 场馆ID |
| user_id | BIGINT | 用户ID |
| booking_date | DATE | 预约日期 |
| start_time | TIME | 开始时间 |
| end_time | TIME | 结束时间 |
| duration_hours | DECIMAL(3,1) | 时长(小时) |
| total_price | DECIMAL(8,2) | 总价格 |
| companion_count | INT | 同伴人数 |
| status | VARCHAR(20) | 状态(pending/confirmed/checked_in/completed/cancelled) |
| cancel_reason | VARCHAR(200) | 取消原因 |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |
| deleted | INT | 逻辑删除 |

## 接口设计

### 用户模块
- POST /api/auth/register - 注册
- POST /api/auth/login - 登录
- GET /api/user/info - 获取用户信息
- PUT /api/user/update - 更新用户信息

### 运动记录模块
- POST /api/sport/record - 创建运动记录
- GET /api/sport/records - 获取运动记录列表
- GET /api/sport/record/{id} - 获取记录详情
- POST /api/sport/track - 保存轨迹点
- GET /api/sport/track/{recordId} - 获取运动轨迹
- GET /api/sport/stats - 统计数据

### 健身计划模块
- POST /api/plan/create - 创建计划
- GET /api/plan/list - 获取计划列表
- GET /api/plan/{id} - 获取计划详情
- PUT /api/plan/{id}/progress - 更新进度
- DELETE /api/plan/{id} - 删除计划

### 约球活动模块
- POST /api/activity/create - 发起活动
- GET /api/activity/list - 活动列表
- GET /api/activity/{id} - 活动详情
- POST /api/activity/{id}/join - 参加活动
- POST /api/activity/{id}/cancel - 取消参加
- PUT /api/activity/{id}/status - 更新活动状态

### 排行榜模块
- GET /api/rank/steps - 步数排行
- GET /api/rank/duration - 运动时长排行
- GET /api/rank/points - 积分排行

### 健康档案模块
- POST /api/health/record - 创建健康记录
- GET /api/health/records - 健康记录列表
- GET /api/health/trend - 健康数据趋势

### 场馆预约模块
- GET /api/venue/list - 场馆列表
- GET /api/venue/{id} - 场馆详情
- GET /api/venue/{id}/schedule - 场馆时段
- POST /api/booking/create - 创建预约
- GET /api/booking/my - 我的预约
- PUT /api/booking/{id}/checkin - 签到
- PUT /api/booking/{id}/cancel - 取消预约

## 技术实现

### 后端技术栈
- Spring Boot 3.2.0
- MyBatis-Plus 3.5.5
- MySQL 8.0
- JWT认证
- Hutool工具库

### 核心功能实现
1. GPS轨迹记录：存储经纬度坐标点序列
2. 配速计算：距离/时间自动计算
3. 积分系统：运动打卡自动获得积分
4. 排行榜：定时任务每日统计
5. BMI计算：体重(kg) / 身高(m)²
6. 数据统计：ECharts前端可视化

### 积分规则
- 跑步：每公里10分，满30分钟额外5分
- 健身房：每30分钟10分
- 球类运动：每小时15分
- 参加约球活动：5分
- 完成健身计划一天：3分

### 定时任务
- 每日0点统计前一天运动数据
- 每周一生成周排行榜
- 每月1日生成月排行榜
- 超时未签到的预约自动取消

