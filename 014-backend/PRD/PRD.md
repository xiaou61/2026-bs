# 校园社团与兴趣圈层平台 PRD

## 一、项目概述

### 1.1 项目背景
校园社团与兴趣圈层平台是一个专注于校园文化建设的综合性社交平台，旨在促进学生社团发展、兴趣交流和校园文化建设。

### 1.2 项目目标
- 提供社团招新、活动发布、成员管理的一站式解决方案
- 通过兴趣匹配和圈子社交促进学生交流
- 建立积分勋章和成长体系，激励用户参与
- 打造活跃的校园文化社区

### 1.3 用户角色
- **学生用户**：浏览社团、加入社团、参与活动、话题讨论
- **社团管理员**：发布招新、管理成员、组织活动
- **系统管理员**：平台管理、社团审核、数据统计

## 二、功能模块

### 2.1 用户模块
- 用户注册/登录（学号、邮箱）
- 个人资料管理（头像、昵称、专业、年级、兴趣标签）
- 积分查看、勋章墙
- 个人主页（加入的社团、参与的活动、发表的话题）

### 2.2 社团模块
- 社团列表（分类浏览、搜索、筛选）
- 社团详情（简介、成员数、活动、招新状态）
- 社团创建（需要审核）
- 社团招新（发布招新、报名管理、审核通过）
- 成员管理（成员列表、角色管理、移除成员）
- 社团主页（动态、活动、成员展示）

### 2.3 活动模块
- 活动发布（标题、时间、地点、人数限制、封面图）
- 活动列表（时间轴、分类、搜索）
- 活动详情（介绍、报名人数、活动相册）
- 活动报名/取消报名
- 活动签到（二维码/签到码）
- 活动评价

### 2.4 兴趣圈子模块
- 圈子分类（学术、艺术、运动、科技、公益等）
- 圈子创建/加入
- 圈子广场（热门圈子、推荐圈子）
- 兴趣标签管理
- 智能匹配推荐（基于兴趣标签）

### 2.5 话题讨论模块
- 话题发布（标题、内容、图片、关联社团/圈子）
- 话题广场（热门、最新、关注）
- 话题详情（浏览、评论、点赞）
- 评论互动（一级评论、二级回复）
- 话题搜索

### 2.6 积分与勋章模块
- 积分规则
  - 注册：+10
  - 完善资料：+20
  - 加入社团：+5
  - 发布话题：+3
  - 评论：+1
  - 参加活动：+10
  - 活动签到：+5
- 勋章体系
  - 新人勋章（注册即获得）
  - 社团达人（加入3个社团）
  - 活跃分子（参加10次活动）
  - 意见领袖（发布50个话题）
  - 热心肠（评论100次）
  - 社交达人（获得100个点赞）
- 等级系统
  - 根据积分划分等级（萌新、活跃、达人、精英、大神）

### 2.7 消息通知模块
- 系统通知（审核结果、活动提醒）
- 互动通知（点赞、评论、回复）
- 社团通知（招新、活动、公告）
- 消息已读/未读状态

### 2.8 管理后台模块
- 用户管理（查询、禁用、删除）
- 社团审核（待审核列表、通过/拒绝）
- 活动管理（查询、推荐、下架）
- 话题管理（审核、删除、置顶）
- 数据统计（用户数、社团数、活动数、话题数、活跃度）

## 三、数据库设计

### 3.1 用户表 (user)
```
id: bigint (主键)
username: varchar(50) (用户名)
password: varchar(100) (密码)
student_id: varchar(20) (学号)
real_name: varchar(50) (真实姓名)
email: varchar(100) (邮箱)
avatar: varchar(255) (头像)
major: varchar(50) (专业)
grade: int (年级)
phone: varchar(20) (手机号)
points: int (积分，默认0)
level: int (等级，默认1)
status: tinyint (状态：0-正常，1-禁用)
create_time: datetime (创建时间)
update_time: datetime (更新时间)
```

### 3.2 兴趣标签表 (interest_tag)
```
id: bigint (主键)
name: varchar(50) (标签名称)
category: varchar(50) (分类：学术、艺术、运动、科技等)
icon: varchar(255) (图标)
user_count: int (使用人数)
create_time: datetime (创建时间)
```

### 3.3 用户兴趣关联表 (user_interest)
```
id: bigint (主键)
user_id: bigint (用户ID)
interest_id: bigint (兴趣标签ID)
create_time: datetime (创建时间)
```

### 3.4 社团表 (club)
```
id: bigint (主键)
name: varchar(100) (社团名称)
category: varchar(50) (分类：学术、文艺、体育、公益、科技等)
logo: varchar(255) (社团Logo)
cover: varchar(255) (封面图)
description: text (简介)
president_id: bigint (社长用户ID)
member_count: int (成员数)
max_members: int (最大成员数)
status: tinyint (状态：0-待审核，1-正常，2-已拒绝，3-已解散)
is_recruiting: tinyint (是否招新：0-否，1-是)
recruit_info: text (招新信息)
create_time: datetime (创建时间)
update_time: datetime (更新时间)
```

### 3.5 社团成员表 (club_member)
```
id: bigint (主键)
club_id: bigint (社团ID)
user_id: bigint (用户ID)
role: tinyint (角色：0-普通成员，1-管理员，2-社长)
join_time: datetime (加入时间)
status: tinyint (状态：0-申请中，1-已通过，2-已拒绝，3-已退出)
```

### 3.6 活动表 (activity)
```
id: bigint (主键)
club_id: bigint (社团ID)
title: varchar(200) (活动标题)
cover: varchar(255) (封面图)
description: text (活动介绍)
location: varchar(200) (活动地点)
start_time: datetime (开始时间)
end_time: datetime (结束时间)
max_participants: int (最大参与人数)
current_participants: int (当前报名人数)
sign_code: varchar(20) (签到码)
status: tinyint (状态：0-报名中，1-进行中，2-已结束，3-已取消)
points: int (活动积分)
create_time: datetime (创建时间)
update_time: datetime (更新时间)
```

### 3.7 活动报名表 (activity_registration)
```
id: bigint (主键)
activity_id: bigint (活动ID)
user_id: bigint (用户ID)
status: tinyint (状态：0-已报名，1-已签到，2-已取消)
register_time: datetime (报名时间)
sign_time: datetime (签到时间)
rating: int (评分1-5)
comment: text (评价)
```

### 3.8 圈子表 (circle)
```
id: bigint (主键)
name: varchar(100) (圈子名称)
category: varchar(50) (分类)
cover: varchar(255) (封面图)
description: text (描述)
creator_id: bigint (创建者ID)
member_count: int (成员数)
topic_count: int (话题数)
create_time: datetime (创建时间)
```

### 3.9 圈子成员表 (circle_member)
```
id: bigint (主键)
circle_id: bigint (圈子ID)
user_id: bigint (用户ID)
join_time: datetime (加入时间)
```

### 3.10 话题表 (topic)
```
id: bigint (主键)
user_id: bigint (发布者ID)
club_id: bigint (关联社团ID，可为空)
circle_id: bigint (关联圈子ID，可为空)
title: varchar(200) (标题)
content: text (内容)
images: text (图片列表，JSON格式)
view_count: int (浏览量)
like_count: int (点赞数)
comment_count: int (评论数)
is_top: tinyint (是否置顶：0-否，1-是)
status: tinyint (状态：0-正常，1-已删除)
create_time: datetime (创建时间)
update_time: datetime (更新时间)
```

### 3.11 话题评论表 (topic_comment)
```
id: bigint (主键)
topic_id: bigint (话题ID)
user_id: bigint (评论者ID)
parent_id: bigint (父评论ID，0为一级评论)
reply_to_id: bigint (回复的用户ID)
content: text (评论内容)
like_count: int (点赞数)
create_time: datetime (创建时间)
```

### 3.12 点赞表 (like_record)
```
id: bigint (主键)
user_id: bigint (用户ID)
target_id: bigint (目标ID：话题ID/评论ID)
target_type: tinyint (类型：1-话题，2-评论)
create_time: datetime (创建时间)
```

### 3.13 勋章表 (badge)
```
id: bigint (主键)
name: varchar(50) (勋章名称)
description: varchar(200) (描述)
icon: varchar(255) (图标)
condition_type: varchar(50) (获取条件类型)
condition_value: int (条件值)
points: int (获得积分)
```

### 3.14 用户勋章表 (user_badge)
```
id: bigint (主键)
user_id: bigint (用户ID)
badge_id: bigint (勋章ID)
obtain_time: datetime (获得时间)
```

### 3.15 积分记录表 (points_record)
```
id: bigint (主键)
user_id: bigint (用户ID)
points: int (积分变化)
type: varchar(50) (类型：注册、发帖、活动等)
description: varchar(200) (描述)
create_time: datetime (创建时间)
```

### 3.16 消息通知表 (notification)
```
id: bigint (主键)
user_id: bigint (接收者ID)
type: tinyint (类型：1-系统，2-互动，3-社团)
title: varchar(200) (标题)
content: text (内容)
link_type: tinyint (关联类型：1-话题，2-活动，3-社团等)
link_id: bigint (关联ID)
is_read: tinyint (是否已读：0-未读，1-已读)
create_time: datetime (创建时间)
```

### 3.17 管理员表 (admin)
```
id: bigint (主键)
username: varchar(50) (用户名)
password: varchar(100) (密码)
real_name: varchar(50) (真实姓名)
role: tinyint (角色：1-超级管理员，2-普通管理员)
create_time: datetime (创建时间)
```

## 四、API接口设计

### 4.1 用户接口
- POST /api/user/register - 用户注册
- POST /api/user/login - 用户登录
- GET /api/user/info - 获取用户信息
- PUT /api/user/profile - 更新个人资料
- POST /api/user/avatar - 上传头像
- GET /api/user/points - 获取积分记录
- GET /api/user/badges - 获取勋章列表
- POST /api/user/interests - 添加兴趣标签
- DELETE /api/user/interests/{id} - 删除兴趣标签

### 4.2 社团接口
- GET /api/clubs - 获取社团列表
- GET /api/clubs/{id} - 获取社团详情
- POST /api/clubs - 创建社团
- PUT /api/clubs/{id} - 更新社团信息
- POST /api/clubs/{id}/join - 申请加入社团
- PUT /api/clubs/{id}/members/{userId}/approve - 审核成员申请
- GET /api/clubs/{id}/members - 获取社团成员列表
- DELETE /api/clubs/{id}/members/{userId} - 移除成员
- POST /api/clubs/{id}/recruiting - 发布招新
- GET /api/clubs/my - 我加入的社团

### 4.3 活动接口
- GET /api/activities - 获取活动列表
- GET /api/activities/{id} - 获取活动详情
- POST /api/activities - 创建活动
- PUT /api/activities/{id} - 更新活动
- DELETE /api/activities/{id} - 取消活动
- POST /api/activities/{id}/register - 报名活动
- DELETE /api/activities/{id}/register - 取消报名
- POST /api/activities/{id}/sign - 活动签到
- POST /api/activities/{id}/rate - 评价活动
- GET /api/activities/my - 我的活动

### 4.4 圈子接口
- GET /api/circles - 获取圈子列表
- GET /api/circles/{id} - 获取圈子详情
- POST /api/circles - 创建圈子
- POST /api/circles/{id}/join - 加入圈子
- DELETE /api/circles/{id}/leave - 退出圈子
- GET /api/circles/recommend - 推荐圈子
- GET /api/circles/my - 我的圈子

### 4.5 话题接口
- GET /api/topics - 获取话题列表
- GET /api/topics/{id} - 获取话题详情
- POST /api/topics - 发布话题
- PUT /api/topics/{id} - 编辑话题
- DELETE /api/topics/{id} - 删除话题
- POST /api/topics/{id}/like - 点赞话题
- DELETE /api/topics/{id}/like - 取消点赞
- GET /api/topics/{id}/comments - 获取话题评论
- POST /api/topics/{id}/comments - 发表评论
- POST /api/topics/comments/{id}/like - 点赞评论

### 4.6 兴趣标签接口
- GET /api/interests - 获取所有兴趣标签
- GET /api/interests/categories - 获取兴趣分类

### 4.7 通知接口
- GET /api/notifications - 获取通知列表
- PUT /api/notifications/{id}/read - 标记已读
- PUT /api/notifications/read-all - 全部标记已读
- GET /api/notifications/unread-count - 未读数量

### 4.8 文件上传接口
- POST /api/upload/image - 上传图片

### 4.9 管理后台接口
- POST /api/admin/login - 管理员登录
- GET /api/admin/users - 用户管理列表
- PUT /api/admin/users/{id}/status - 更新用户状态
- GET /api/admin/clubs/pending - 待审核社团列表
- PUT /api/admin/clubs/{id}/approve - 审核社团
- GET /api/admin/topics - 话题管理列表
- PUT /api/admin/topics/{id}/top - 置顶话题
- DELETE /api/admin/topics/{id} - 删除话题
- GET /api/admin/statistics - 数据统计

## 五、技术栈

### 5.1 后端技术
- Spring Boot 3.x
- MyBatis-Plus
- MySQL 8.0
- JWT认证
- BCrypt密码加密

### 5.2 前端技术
- jQuery
- Bootstrap 或其他UI组件库
- Ajax 异步请求

## 六、非功能需求

### 6.1 性能要求
- 页面响应时间 < 2秒
- 支持1000+并发用户

### 6.2 安全要求
- 密码加密存储
- Token认证
- XSS防护
- SQL注入防护

### 6.3 可用性要求
- 界面简洁友好
- 操作流程清晰
- 响应式设计

