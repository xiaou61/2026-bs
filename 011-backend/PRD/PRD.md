# 校园短视频创作与分享平台 PRD

## 一、项目概述

### 1.1 项目背景
短视频已成为年轻人主要的内容消费和创作方式。校园场景下，学生希望记录和分享校园生活、才艺展示、学习经验等内容，但缺乏一个专属的校园短视频社区。本平台旨在打造一个校园版的短视频创作与分享平台，提供视频上传、社交互动、话题挑战、创作激励等功能。

### 1.2 项目名称
校园短视频创作与分享平台（Campus Video）

### 1.3 技术栈
- 后端：Spring Boot 3 + MyBatis-Plus + MySQL 8.0 + JWT + Redis
- 前端：Vue 3 + Element Plus + Video.js + Axios + Pinia
- 文件存储：本地存储 / 阿里云OSS（可选）
- 视频处理：FFmpeg（视频压缩、截取封面）

### 1.4 核心功能
- 短视频发布（拍摄/上传、封面选择、话题标签）
- 视频浏览（推荐流、关注流、话题流）
- 社交互动（点赞、评论、转发、收藏）
- 用户关系（关注、粉丝、互关）
- 话题挑战（热门话题、话题视频聚合）
- 创作激励（积分系统、等级勋章、创作排行）
- 推荐算法（热度排序、个性化推荐）

## 二、用户角色

### 2.1 普通用户
- 注册登录、个人主页
- 发布短视频
- 浏览视频（推荐页、关注页、话题页）
- 互动操作（点赞、评论、转发、收藏）
- 关注其他用户
- 参与话题挑战
- 查看创作数据
- 积分商城兑换

### 2.2 管理员
- 用户管理（查看、禁用）
- 视频审核（违规视频下架）
- 话题管理（创建、编辑、推荐）
- 评论管理（违规评论删除）
- 数据统计（用户数、视频数、活跃度）
- 积分规则配置
- 系统公告发布

## 三、核心功能模块

### 3.1 用户模块

#### 3.1.1 用户注册
- 手机号/学号
- 密码
- 昵称
- 头像
- 性别
- 学校
- 学院/专业

#### 3.1.2 用户登录
- 手机号/学号登录
- JWT Token鉴权
- 记住登录状态

#### 3.1.3 个人主页
- 基本信息（头像、昵称、签名、学校）
- 统计数据（获赞数、粉丝数、关注数、作品数）
- 作品列表（公开/私密）
- 喜欢列表
- 收藏列表
- 等级勋章展示

#### 3.1.4 用户等级系统
- 等级划分：
  - Lv1 萌新（0-99分）
  - Lv2 新星（100-499分）
  - Lv3 达人（500-1999分）
  - Lv4 红人（2000-4999分）
  - Lv5 大V（5000+分）
- 积分获取：
  - 发布视频：+10分
  - 视频被点赞：+2分
  - 视频被评论：+3分
  - 视频被转发：+5分
  - 每日登录：+2分
  - 完成任务：+10-50分

#### 3.1.5 用户关系
- 关注用户
- 取消关注
- 查看粉丝列表
- 查看关注列表
- 互相关注标识

### 3.2 视频发布模块

#### 3.2.1 视频上传
- 支持格式：MP4、MOV、AVI
- 文件大小限制：最大100MB
- 时长限制：15秒-5分钟
- 视频预览
- 自动生成封面（取第一帧）
- 手动选择封面（视频任意帧）

#### 3.2.2 视频信息编辑
- 视频标题（必填，1-50字）
- 视频描述（选填，最多500字）
- 话题标签（最多3个）
- 地理位置（可选）
- 权限设置（公开/私密/仅粉丝可见）
- @提及用户

#### 3.2.3 视频处理
- 视频压缩（FFmpeg）
- 生成缩略图
- 提取视频时长、分辨率
- 视频转码（统一格式）

#### 3.2.4 草稿箱
- 保存未发布的视频
- 编辑草稿
- 发布草稿
- 删除草稿

### 3.3 视频浏览模块

#### 3.3.1 推荐页（首页）
- 推荐算法排序
- 沉浸式全屏播放
- 上下滑动切换视频
- 自动播放、循环播放
- 视频信息展示（作者、标题、话题、音乐）
- 右侧交互按钮（头像、点赞、评论、转发、收藏）

#### 3.3.2 关注页
- 关注用户的视频流
- 按发布时间倒序
- 视频卡片列表展示

#### 3.3.3 话题页
- 话题视频聚合
- 话题介绍、参与人数、视频数
- 话题视频列表
- 参与话题挑战入口

#### 3.3.4 视频详情页
- 视频播放
- 作者信息
- 视频标题、描述
- 点赞、评论、转发、收藏数
- 评论列表
- 相关推荐视频

#### 3.3.5 视频搜索
- 关键词搜索（标题、描述、话题）
- 搜索历史记录
- 热门搜索推荐
- 搜索结果按热度/时间排序

### 3.4 互动模块

#### 3.4.1 点赞功能
- 点赞/取消点赞
- 点赞动画效果
- 点赞数实时更新
- 点赞记录持久化

#### 3.4.2 评论功能
- 发布评论（文字+表情）
- 回复评论（二级评论）
- 点赞评论
- 删除自己的评论
- 评论列表（按热度/时间排序）
- 评论消息通知
- @提及用户

#### 3.4.3 转发功能
- 转发到个人主页
- 添加转发语
- 转发数统计

#### 3.4.4 收藏功能
- 收藏视频
- 取消收藏
- 收藏夹管理
- 查看收藏列表

### 3.5 话题挑战模块

#### 3.5.1 话题信息
- 话题名称（带#号）
- 话题描述
- 话题封面图
- 话题创建时间
- 参与人数
- 视频数量
- 总播放量
- 话题状态（进行中/已结束）

#### 3.5.2 话题分类
- 校园生活
- 学习打卡
- 才艺展示
- 美食探店
- 运动健身
- 搞笑段子
- 情感故事
- 技能教学

#### 3.5.3 热门话题
- 话题热度排行
- 今日热门话题
- 推荐话题
- 新话题

#### 3.5.4 参与话题
- 发布视频时选择话题
- 查看话题下所有视频
- 话题排行榜（贡献最多的用户）

### 3.6 消息通知模块

#### 3.6.1 通知类型
- 点赞通知（谁点赞了你的视频）
- 评论通知（谁评论了你的视频）
- 回复通知（谁回复了你的评论）
- 关注通知（谁关注了你）
- @提及通知（谁在视频/评论中@了你）
- 系统通知（审核结果、活动通知）

#### 3.6.2 消息管理
- 消息列表
- 未读消息数
- 消息已读标记
- 消息删除
- 消息免打扰设置

### 3.7 创作激励模块

#### 3.7.1 创作任务
- 每日任务：
  - 发布1个视频：+10分
  - 获得10个赞：+5分
  - 获得5条评论：+5分
- 每周任务：
  - 发布5个视频：+50分
  - 获得100个赞：+30分
- 成就任务：
  - 发布首个视频：+20分
  - 粉丝数达100：+50分
  - 粉丝数达1000：+200分
  - 单个视频点赞数破1000：+100分

#### 3.7.2 积分商城
- 积分兑换虚拟勋章
- 积分兑换专属头像框
- 积分兑换视频推荐权
- 积分兑换实物奖品（可选）

#### 3.7.3 创作排行榜
- 本周涨粉榜
- 本月获赞榜
- 创作量排行
- 积分排行

### 3.8 推荐算法模块

#### 3.8.1 推荐策略
- 热度权重（点赞数、评论数、转发数、播放量）
- 时效性权重（发布时间越近权重越高）
- 用户兴趣标签匹配
- 协同过滤（喜欢相似视频的用户）
- 多样性（避免推荐重复内容）

#### 3.8.2 热度计算公式
```
热度分数 = 
  点赞数 × 4 + 
  评论数 × 6 + 
  转发数 × 8 + 
  收藏数 × 5 + 
  播放量 × 0.5 + 
  时间衰减因子
```

#### 3.8.3 个性化推荐
- 基于用户浏览历史
- 基于用户点赞记录
- 基于用户关注的创作者
- 基于用户参与的话题

### 3.9 数据统计模块

#### 3.9.1 创作者数据
- 视频播放量
- 视频完播率
- 点赞数、评论数、转发数
- 粉丝增长趋势
- 视频数据对比（按日期）

#### 3.9.2 平台数据（管理员）
- 用户总数
- 视频总数
- 今日新增用户
- 今日新增视频
- 日活跃用户（DAU）
- 月活跃用户（MAU）
- 视频总播放量
- 热门话题TOP10
- 热门视频TOP10

## 四、数据库设计

### 4.1 用户表 (user)
```sql
id                BIGINT          主键
username          VARCHAR(50)     用户名（唯一）
password          VARCHAR(100)    密码（加密）
nickname          VARCHAR(50)     昵称
avatar            VARCHAR(200)    头像URL
gender            TINYINT         性别（0未知/1男/2女）
phone             VARCHAR(20)     手机号
student_id        VARCHAR(30)     学号
school            VARCHAR(50)     学校
college           VARCHAR(50)     学院
major             VARCHAR(50)     专业
signature         VARCHAR(200)    个性签名
level             INT             等级
points            INT             积分
like_count        INT             获赞总数
fans_count        INT             粉丝数
follow_count      INT             关注数
video_count       INT             作品数
role              VARCHAR(20)     角色（USER/ADMIN）
status            TINYINT         状态（0禁用/1正常）
last_login_time   DATETIME        最后登录时间
create_time       DATETIME        创建时间
update_time       DATETIME        更新时间
```

### 4.2 视频表 (video)
```sql
id                BIGINT          主键
user_id           BIGINT          用户ID
title             VARCHAR(100)    视频标题
description       TEXT            视频描述
video_url         VARCHAR(200)    视频URL
cover_url         VARCHAR(200)    封面URL
duration          INT             视频时长（秒）
width             INT             视频宽度
height            INT             视频高度
file_size         BIGINT          文件大小（字节）
location          VARCHAR(100)    地理位置
permission        TINYINT         权限（0私密/1公开/2仅粉丝）
play_count        INT             播放量
like_count        INT             点赞数
comment_count     INT             评论数
share_count       INT             转发数
collect_count     INT             收藏数
heat_score        DECIMAL(10,2)   热度分数
status            TINYINT         状态（0审核中/1已发布/2审核不通过/3已删除）
audit_reason      VARCHAR(200)    审核原因
is_top            TINYINT         是否置顶（0否/1是）
publish_time      DATETIME        发布时间
create_time       DATETIME        创建时间
update_time       DATETIME        更新时间
```

### 4.3 视频话题关联表 (video_topic)
```sql
id                BIGINT          主键
video_id          BIGINT          视频ID
topic_id          BIGINT          话题ID
create_time       DATETIME        创建时间
```

### 4.4 话题表 (topic)
```sql
id                BIGINT          主键
topic_name        VARCHAR(50)     话题名称（带#号）
description       TEXT            话题描述
cover_url         VARCHAR(200)    话题封面
category          VARCHAR(20)     话题分类
video_count       INT             视频数量
view_count        BIGINT          浏览量
participant_count INT             参与人数
heat_score        DECIMAL(10,2)   热度分数
status            TINYINT         状态（0禁用/1启用）
is_hot            TINYINT         是否热门（0否/1是）
sort_order        INT             排序
create_time       DATETIME        创建时间
update_time       DATETIME        更新时间
```

### 4.5 点赞表 (video_like)
```sql
id                BIGINT          主键
user_id           BIGINT          用户ID
video_id          BIGINT          视频ID
create_time       DATETIME        创建时间
```

### 4.6 评论表 (comment)
```sql
id                BIGINT          主键
video_id          BIGINT          视频ID
user_id           BIGINT          用户ID
parent_id         BIGINT          父评论ID（0表示一级评论）
reply_to_user_id  BIGINT          回复的用户ID
content           TEXT            评论内容
like_count        INT             点赞数
reply_count       INT             回复数
status            TINYINT         状态（0待审核/1已发布/2已删除）
create_time       DATETIME        创建时间
update_time       DATETIME        更新时间
```

### 4.7 评论点赞表 (comment_like)
```sql
id                BIGINT          主键
user_id           BIGINT          用户ID
comment_id        BIGINT          评论ID
create_time       DATETIME        创建时间
```

### 4.8 转发表 (video_share)
```sql
id                BIGINT          主键
user_id           BIGINT          用户ID
video_id          BIGINT          视频ID
original_user_id  BIGINT          原作者ID
share_text        VARCHAR(200)    转发语
create_time       DATETIME        创建时间
```

### 4.9 收藏表 (video_collect)
```sql
id                BIGINT          主键
user_id           BIGINT          用户ID
video_id          BIGINT          视频ID
folder_id         BIGINT          收藏夹ID（默认0）
create_time       DATETIME        创建时间
```

### 4.10 关注表 (user_follow)
```sql
id                BIGINT          主键
user_id           BIGINT          用户ID（关注者）
follow_user_id    BIGINT          被关注用户ID
create_time       DATETIME        创建时间
```

### 4.11 草稿表 (video_draft)
```sql
id                BIGINT          主键
user_id           BIGINT          用户ID
title             VARCHAR(100)    视频标题
description       TEXT            视频描述
video_url         VARCHAR(200)    视频URL
cover_url         VARCHAR(200)    封面URL
topic_ids         VARCHAR(100)    话题ID（逗号分隔）
location          VARCHAR(100)    地理位置
create_time       DATETIME        创建时间
update_time       DATETIME        更新时间
```

### 4.12 消息通知表 (notification)
```sql
id                BIGINT          主键
user_id           BIGINT          接收用户ID
from_user_id      BIGINT          发送用户ID
type              VARCHAR(20)     类型（LIKE/COMMENT/FOLLOW/MENTION/SYSTEM）
content           VARCHAR(200)    通知内容
related_id        BIGINT          关联ID（视频ID/评论ID）
is_read           TINYINT         是否已读（0未读/1已读）
create_time       DATETIME        创建时间
```

### 4.13 用户积分记录表 (user_points_log)
```sql
id                BIGINT          主键
user_id           BIGINT          用户ID
change_type       VARCHAR(20)     变动类型（TASK/VIDEO/LIKE/COMMENT）
change_points     INT             变动积分（正数增加/负数减少）
before_points     INT             变动前积分
after_points      INT             变动后积分
reason            VARCHAR(200)    原因
related_id        BIGINT          关联ID
create_time       DATETIME        创建时间
```

### 4.14 播放历史表 (play_history)
```sql
id                BIGINT          主键
user_id           BIGINT          用户ID
video_id          BIGINT          视频ID
play_duration     INT             播放时长（秒）
play_percentage   INT             播放进度（百分比）
create_time       DATETIME        创建时间
update_time       DATETIME        更新时间
```

### 4.15 视频举报表 (video_report)
```sql
id                BIGINT          主键
user_id           BIGINT          举报用户ID
video_id          BIGINT          视频ID
report_type       VARCHAR(20)     举报类型（色情/暴力/诈骗/其他）
report_reason     TEXT            举报原因
status            TINYINT         状态（0待处理/1已处理/2已驳回）
handle_result     VARCHAR(200)    处理结果
handle_admin_id   BIGINT          处理管理员ID
handle_time       DATETIME        处理时间
create_time       DATETIME        创建时间
```

## 五、接口设计

### 5.1 认证接口

#### POST /api/auth/register
用户注册

#### POST /api/auth/login
用户登录

#### POST /api/auth/logout
用户登出

#### GET /api/auth/info
获取当前用户信息

### 5.2 用户接口

#### GET /api/user/{id}
获取用户信息

#### PUT /api/user/profile
更新个人信息

#### PUT /api/user/avatar
更新头像

#### GET /api/user/{id}/videos
获取用户视频列表

#### GET /api/user/{id}/likes
获取用户点赞视频列表

#### GET /api/user/{id}/collects
获取用户收藏列表

#### POST /api/user/follow/{userId}
关注用户

#### DELETE /api/user/follow/{userId}
取消关注

#### GET /api/user/{id}/followers
获取粉丝列表

#### GET /api/user/{id}/following
获取关注列表

#### GET /api/user/points/log
获取积分变动记录

### 5.3 视频接口

#### POST /api/video/upload
上传视频

#### POST /api/video/publish
发布视频

#### GET /api/video/recommend
获取推荐视频流

#### GET /api/video/following
获取关注视频流

#### GET /api/video/{id}
获取视频详情

#### DELETE /api/video/{id}
删除视频

#### PUT /api/video/{id}
更新视频信息

#### GET /api/video/search
搜索视频

#### POST /api/video/{id}/like
点赞视频

#### DELETE /api/video/{id}/like
取消点赞

#### POST /api/video/{id}/collect
收藏视频

#### DELETE /api/video/{id}/collect
取消收藏

#### POST /api/video/{id}/share
转发视频

#### POST /api/video/{id}/report
举报视频

#### POST /api/video/{id}/play
记录播放

#### GET /api/video/{id}/related
获取相关推荐视频

### 5.4 草稿接口

#### POST /api/draft/save
保存草稿

#### GET /api/draft/list
获取草稿列表

#### GET /api/draft/{id}
获取草稿详情

#### DELETE /api/draft/{id}
删除草稿

### 5.5 评论接口

#### POST /api/comment/publish
发布评论

#### GET /api/comment/list
获取评论列表

#### DELETE /api/comment/{id}
删除评论

#### POST /api/comment/{id}/like
点赞评论

#### DELETE /api/comment/{id}/like
取消点赞评论

#### GET /api/comment/{id}/replies
获取评论回复

### 5.6 话题接口

#### GET /api/topic/hot
获取热门话题

#### GET /api/topic/recommend
获取推荐话题

#### GET /api/topic/{id}
获取话题详情

#### GET /api/topic/{id}/videos
获取话题视频列表

#### GET /api/topic/search
搜索话题

### 5.7 通知接口

#### GET /api/notification/list
获取通知列表

#### GET /api/notification/unread-count
获取未读数量

#### PUT /api/notification/{id}/read
标记已读

#### PUT /api/notification/read-all
全部标记已读

### 5.8 统计接口

#### GET /api/stats/creator
获取创作者数据

#### GET /api/stats/video/{id}
获取视频数据

### 5.9 管理员接口

#### GET /api/admin/user/list
用户列表

#### PUT /api/admin/user/{id}/status
修改用户状态

#### GET /api/admin/video/list
视频列表

#### PUT /api/admin/video/{id}/audit
审核视频

#### DELETE /api/admin/video/{id}
删除视频

#### GET /api/admin/topic/list
话题列表

#### POST /api/admin/topic
创建话题

#### PUT /api/admin/topic/{id}
更新话题

#### DELETE /api/admin/topic/{id}
删除话题

#### GET /api/admin/comment/list
评论列表

#### DELETE /api/admin/comment/{id}
删除评论

#### GET /api/admin/report/list
举报列表

#### PUT /api/admin/report/{id}/handle
处理举报

#### GET /api/admin/stats/overview
数据概览

## 六、前端页面设计

### 6.1 用户端页面

#### 6.1.1 登录注册页
- 登录表单
- 注册表单
- 忘记密码

#### 6.1.2 首页（推荐页）
- 顶部导航（推荐、关注）
- 沉浸式全屏视频播放
- 视频信息展示
- 右侧交互按钮（头像、点赞、评论、转发、收藏）
- 底部视频描述、话题标签
- 上下滑动切换视频

#### 6.1.3 视频发布页
- 视频上传区域
- 封面选择
- 标题输入
- 描述输入
- 话题选择
- 地理位置
- 权限设置
- 发布/保存草稿按钮

#### 6.1.4 视频详情页
- 视频播放器
- 作者信息（头像、昵称、关注按钮）
- 视频标题、描述
- 点赞、评论、转发、收藏按钮
- 评论列表
- 相关推荐视频

#### 6.1.5 个人主页
- 用户信息卡片
- 统计数据（获赞、粉丝、关注、作品）
- Tab切换（作品、喜欢）
- 视频网格展示
- 编辑资料按钮（自己）
- 关注按钮（他人）

#### 6.1.6 话题页
- 话题信息（名称、描述、封面）
- 统计数据（参与人数、视频数、播放量）
- 参与话题按钮
- 视频列表（瀑布流）
- 贡献排行榜

#### 6.1.7 搜索页
- 搜索框
- 搜索历史
- 热门搜索
- 搜索结果（用户、视频、话题）

#### 6.1.8 消息页
- Tab切换（点赞、评论、关注、系统）
- 消息列表
- 未读标识
- 消息内容
- 跳转链接

#### 6.1.9 我的页面
- 个人信息卡片
- 等级勋章
- 积分显示
- 功能入口（收藏、草稿、数据、任务）
- 设置入口

#### 6.1.10 创作中心
- 数据概览
- 视频列表
- 视频数据详情
- 粉丝增长趋势图
- 创作任务
- 创作排行

#### 6.1.11 积分商城
- 积分余额
- 商品列表
- 兑换记录
- 商品详情

### 6.2 管理员端页面

#### 6.2.1 数据统计页
- 数据概览卡片
- 用户增长趋势图
- 视频发布趋势图
- 热门话题TOP10
- 热门视频TOP10

#### 6.2.2 用户管理页
- 用户列表
- 搜索筛选
- 状态管理
- 详细信息

#### 6.2.3 视频管理页
- 视频列表
- 搜索筛选
- 视频审核
- 视频删除
- 视频详情

#### 6.2.4 话题管理页
- 话题列表
- 创建话题
- 编辑话题
- 热门推荐设置

#### 6.2.5 评论管理页
- 评论列表
- 搜索筛选
- 评论删除

#### 6.2.6 举报管理页
- 举报列表
- 举报详情
- 处理举报

#### 6.2.7 系统配置页
- 积分规则配置
- 上传限制配置
- 推荐算法参数

## 七、业务流程

### 7.1 视频发布流程
```
用户登录
→ 点击发布按钮
→ 选择视频文件上传
→ 视频上传到服务器
→ 服务器处理视频（压缩、生成封面）
→ 填写视频信息（标题、描述、话题）
→ 选择封面
→ 设置权限
→ 点击发布/保存草稿
→ 视频审核（自动/人工）
→ 审核通过后发布
→ 通知粉丝
```

### 7.2 视频推荐流程
```
用户进入推荐页
→ 加载推荐算法
→ 计算视频热度分数
→ 过滤已看过的视频
→ 根据用户兴趣标签匹配
→ 多样性打散
→ 返回推荐视频列表
→ 用户上下滑动切换
→ 记录用户行为（播放、点赞、完播）
→ 更新推荐模型
```

### 7.3 互动流程
```
用户观看视频
→ 点击点赞按钮
→ 点赞动画效果
→ 点赞数+1
→ 创作者获得积分+2
→ 发送点赞通知给创作者
→ 记录点赞行为
→ 更新视频热度分数
```

### 7.4 关注流程
```
用户访问他人主页
→ 点击关注按钮
→ 建立关注关系
→ 用户关注数+1
→ 被关注用户粉丝数+1
→ 发送关注通知
→ 可以看到关注用户的视频
```

## 八、推荐算法设计

### 8.1 热度分数计算
```java
heatScore = 
  likeCount * 4 +           // 点赞权重
  commentCount * 6 +        // 评论权重
  shareCount * 8 +          // 转发权重
  collectCount * 5 +        // 收藏权重
  playCount * 0.5 +         // 播放权重
  timeDecay                 // 时间衰减
```

### 8.2 时间衰减因子
```java
hoursSincePublish = (当前时间 - 发布时间) / 3600
timeDecay = 1 / (1 + hoursSincePublish / 24)
```

### 8.3 个性化推荐
- 用户兴趣标签（从点赞、收藏、完播视频中提取）
- 话题偏好（参与过的话题）
- 创作者偏好（关注的创作者）
- 协同过滤（相似用户喜欢的视频）

### 8.4 推荐策略
- 70% 基于热度排序
- 20% 基于个性化推荐
- 10% 随机推荐（新视频冷启动）

## 九、特色功能

### 9.1 沉浸式播放体验
- 全屏播放
- 上下滑动切换
- 自动播放下一个
- 循环播放
- 双击点赞
- 拖动进度条

### 9.2 智能封面生成
- 自动截取第一帧
- 用户可选择任意帧
- 智能识别精彩画面（可选）

### 9.3 视频压缩优化
- FFmpeg自动压缩
- 保持画质的同时减小文件
- 多分辨率适配（480p、720p、1080p）

### 9.4 话题挑战机制
- 热门话题推荐
- 话题排行榜
- 参与话题可获得额外曝光

### 9.5 创作激励体系
- 积分系统
- 等级勋章
- 创作任务
- 积分商城
- 创作排行榜

### 9.6 社交互动
- 点赞、评论、转发、收藏
- 关注、粉丝
- @提及
- 私信（可选）

### 9.7 数据看板
- 播放量、完播率
- 粉丝增长趋势
- 视频数据对比
- 热门时段分析

## 十、非功能需求

### 10.1 性能要求
- 视频上传速度：支持断点续传
- 视频加载速度：< 2秒
- 推荐接口响应：< 500ms
- 支持并发上传：> 20路
- CDN加速（可选）

### 10.2 安全要求
- 密码MD5加密
- JWT Token鉴权
- 文件类型校验
- 文件大小限制
- 视频内容审核（敏感词过滤）
- 防刷接口限流

### 10.3 存储要求
- 视频存储：本地存储 / OSS对象存储
- 图片存储：本地存储 / OSS对象存储
- 数据库备份：每日自动备份

### 10.4 视频处理
- FFmpeg视频转码
- 视频压缩
- 视频截图
- 视频水印（可选）

## 十一、开发优先级

### P0（核心功能 - 第1-2周）
- 用户注册登录
- 视频上传发布
- 视频列表浏览
- 视频播放
- 点赞评论功能

### P1（重要功能 - 第3周）
- 关注粉丝系统
- 话题系统
- 推荐算法
- 消息通知
- 个人主页

### P2（增强功能 - 第4周）
- 创作激励系统
- 数据统计
- 视频搜索
- 草稿箱
- 收藏功能

### P3（优化功能 - 第5周）
- 管理员后台
- 视频审核
- 举报功能
- 积分商城
- 性能优化

## 十二、部署说明

### 12.1 环境要求
- JDK 17+
- MySQL 8.0+
- Redis 6.0+
- FFmpeg 4.0+
- Nginx（视频CDN）

### 12.2 存储规划
- 视频文件：/data/videos/
- 封面图片：/data/covers/
- 用户头像：/data/avatars/
- 数据库备份：/data/backup/

### 12.3 初始账户
- 管理员：admin / admin123
- 测试用户1：test1 / 123456
- 测试用户2：test2 / 123456

### 12.4 测试数据
- 预置10个用户
- 预置30个视频
- 预置10个话题
- 预置100条评论

