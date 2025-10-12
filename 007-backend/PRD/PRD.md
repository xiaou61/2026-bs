# 志愿活动管理与积分激励平台 PRD

## 项目概述
基于Spring Boot的志愿活动管理与积分激励平台，通过积分激励机制鼓励用户参与志愿活动，提供活动发布、报名、签到、积分兑换等功能。

## 技术栈
- 后端：Spring Boot 3 + MyBatis-Plus + MySQL
- 前端：Vue 3 + Element Plus（组件库一体化）
- 鉴权：简单的Token机制

## 角色定义
- 管理员（ADMIN）：管理活动、用户、积分规则、奖励商品
- 志愿者（VOLUNTEER）：报名活动、签到打卡、查看积分、兑换奖励

## 数据库设计（7张表）

### 1. user（用户表）
- id：主键
- username：用户名（唯一）
- password：密码
- name：真实姓名
- phone：手机号
- role：角色（ADMIN/VOLUNTEER）
- totalPoints：总积分
- availablePoints：可用积分
- volunteerHours：志愿时长（小时）
- status：状态（0禁用/1启用）
- createTime：创建时间
- updateTime：更新时间

### 2. activity（志愿活动表）
- id：主键
- title：活动标题
- description：活动描述
- location：活动地点
- startTime：开始时间
- endTime：结束时间
- maxParticipants：最大参与人数
- currentParticipants：当前报名人数
- points：活动积分
- hours：志愿时长
- status：状态（0草稿/1报名中/2进行中/3已结束/4已取消）
- publisherId：发布者ID
- publisherName：发布者姓名
- createTime：创建时间
- updateTime：更新时间

### 3. enrollment（活动报名表）
- id：主键
- activityId：活动ID
- activityTitle：活动标题
- userId：用户ID
- userName：用户姓名
- phone：联系电话
- status：状态（0待审核/1已通过/2已拒绝/3已取消）
- applyTime：报名时间
- updateTime：更新时间

### 4. attendance（签到记录表）
- id：主键
- activityId：活动ID
- activityTitle：活动标题
- userId：用户ID
- userName：用户姓名
- checkInTime：签到时间
- checkOutTime：签退时间
- actualHours：实际时长
- points：获得积分
- status：状态（0仅签到/1已完成）
- createTime：创建时间
- updateTime：更新时间

### 5. points_record（积分记录表）
- id：主键
- userId：用户ID
- userName：用户姓名
- type：类型（1活动获得/2兑换扣除/3管理员调整）
- points：积分变动（正数增加/负数减少）
- balance：变动后余额
- relatedId：关联ID（活动ID或兑换ID）
- relatedTitle：关联标题
- remark：备注
- createTime：创建时间

### 6. reward（奖励商品表）
- id：主键
- name：商品名称
- description：商品描述
- image：商品图片
- points：所需积分
- stock：库存数量
- exchangeCount：兑换次数
- status：状态（0下架/1上架）
- createTime：创建时间
- updateTime：更新时间

### 7. exchange_record（兑换记录表）
- id：主键
- rewardId：商品ID
- rewardName：商品名称
- userId：用户ID
- userName：用户姓名
- points：消耗积分
- status：状态（0待发放/1已发放/2已取消）
- phone：联系电话
- address：收货地址
- remark：备注
- exchangeTime：兑换时间
- updateTime：更新时间

## 功能模块

### 1. 用户模块
- 用户注册（默认为志愿者）
- 用户登录（返回Token）
- 个人信息查询
- 个人信息修改
- 密码修改
- 用户列表（管理员）
- 用户状态管理（管理员）

### 2. 活动模块
- 活动发布（管理员）
- 活动列表（支持筛选：状态、时间范围）
- 活动详情
- 活动编辑（管理员）
- 活动删除（管理员）
- 活动状态管理（管理员）
- 我发布的活动（管理员）
- 我报名的活动（志愿者）

### 3. 报名模块
- 活动报名（志愿者）
- 取消报名（志愿者）
- 报名列表查询
- 报名审核（管理员）
- 我的报名记录（志愿者）

### 4. 签到模块
- 签到（志愿者，活动开始后）
- 签退（志愿者，自动计算时长和积分）
- 签到记录查询
- 我的签到记录（志愿者）
- 管理员补签（管理员）

### 5. 积分模块
- 积分明细查询
- 积分排行榜
- 我的积分统计（志愿者）
- 积分调整（管理员）

### 6. 奖励模块
- 商品发布（管理员）
- 商品列表（上架商品）
- 商品详情
- 商品编辑（管理员）
- 商品删除（管理员）
- 商品上下架（管理员）

### 7. 兑换模块
- 积分兑换（志愿者）
- 兑换记录查询
- 我的兑换记录（志愿者）
- 兑换状态管理（管理员）

### 8. 统计模块
- 个人志愿统计（总时长、总积分、参与活动数）
- 平台数据概览（管理员：总用户数、总活动数、总志愿时长）
- 活动参与排行
- 积分排行

## API接口规范

### 统一响应格式
```json
{
  "code": 200,
  "message": "success",
  "data": {}
}
```

### 接口列表

#### 认证接口
- POST /api/auth/register - 注册
- POST /api/auth/login - 登录
- POST /api/auth/logout - 登出
- GET /api/auth/info - 获取当前用户信息

#### 用户接口
- GET /api/users - 用户列表（管理员）
- GET /api/users/{id} - 用户详情
- PUT /api/users/{id} - 更新用户信息
- PUT /api/users/{id}/status - 更新用户状态（管理员）
- PUT /api/users/{id}/password - 修改密码
- GET /api/users/{id}/stats - 用户志愿统计

#### 活动接口
- GET /api/activities - 活动列表
- GET /api/activities/{id} - 活动详情
- POST /api/activities - 创建活动（管理员）
- PUT /api/activities/{id} - 更新活动（管理员）
- DELETE /api/activities/{id} - 删除活动（管理员）
- PUT /api/activities/{id}/status - 更新活动状态（管理员）
- GET /api/activities/my/published - 我发布的活动（管理员）
- GET /api/activities/my/enrolled - 我报名的活动（志愿者）

#### 报名接口
- POST /api/enrollments - 报名活动
- DELETE /api/enrollments/{id} - 取消报名
- GET /api/enrollments - 报名列表
- GET /api/enrollments/my - 我的报名记录
- PUT /api/enrollments/{id}/status - 审核报名（管理员）

#### 签到接口
- POST /api/attendances/check-in - 签到
- PUT /api/attendances/{id}/check-out - 签退
- GET /api/attendances - 签到记录
- GET /api/attendances/my - 我的签到记录
- POST /api/attendances/manual - 管理员补签（管理员）

#### 积分接口
- GET /api/points/records - 积分记录
- GET /api/points/my - 我的积分记录
- GET /api/points/ranking - 积分排行榜
- POST /api/points/adjust - 积分调整（管理员）

#### 奖励接口
- GET /api/rewards - 奖励列表
- GET /api/rewards/{id} - 奖励详情
- POST /api/rewards - 创建奖励（管理员）
- PUT /api/rewards/{id} - 更新奖励（管理员）
- DELETE /api/rewards/{id} - 删除奖励（管理员）
- PUT /api/rewards/{id}/status - 上下架（管理员）

#### 兑换接口
- POST /api/exchanges - 兑换奖励
- GET /api/exchanges - 兑换记录
- GET /api/exchanges/my - 我的兑换记录
- PUT /api/exchanges/{id}/status - 更新兑换状态（管理员）

#### 统计接口
- GET /api/stats/overview - 平台概览（管理员）
- GET /api/stats/my - 我的统计
- GET /api/stats/activity-ranking - 活动参与排行

## 前端页面

### 公共页面
- 登录页
- 注册页

### 志愿者页面
- 首页（活动列表）
- 活动详情
- 我的活动
- 我的积分
- 积分商城
- 我的兑换
- 个人中心
- 排行榜

### 管理员页面
- 数据概览
- 活动管理
- 报名管理
- 签到管理
- 用户管理
- 商品管理
- 兑换管理
- 积分管理
- 个人中心

## 业务规则

### 活动规则
- 活动报名需在活动开始前
- 活动人数达到上限后不可报名
- 活动开始后可以签到
- 签到后必须签退才能获得积分

### 积分规则
- 签退时根据实际参与时长计算积分
- 积分 = 活动设定积分 * (实际时长 / 计划时长)
- 兑换商品时扣除相应积分
- 积分不足无法兑换

### 库存规则
- 兑换时检查库存
- 兑换成功后扣减库存
- 取消兑换后恢复库存

## 初始数据
- 管理员账号：admin / admin123
- 测试志愿者：volunteer / 123456
- 预设3-5个志愿活动
- 预设5-8个积分商品

