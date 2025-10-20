# 校园快递代领服务平台 PRD

## 一、项目概述

### 1.1 项目背景
校园快递代领服务平台是一个解决大学生取快递痛点的校园服务平台。很多学生因为课程、社团活动等原因无法及时取快递，而快递站点通常距离宿舍较远，取快递耗时耗力。本平台通过"众包代取"模式，让有时间的学生帮助没时间的学生代领快递，同时赚取跑腿费。

### 1.2 项目名称
校园快递代领服务平台（Campus Express Helper）

### 1.3 技术栈
- 后端：Spring Boot 3 + MyBatis-Plus + MySQL 8.0 + JWT
- 前端：Vue 3 + Element Plus + Axios + Pinia
- 支付：模拟余额支付

### 1.4 核心功能
- 快递代领发布（用户发布代领需求）
- 代领接单（骑手接单代领）
- 订单管理（订单状态流转）
- 余额钱包（充值、提现、交易）
- 评价系统（订单完成后评价）
- 通知提醒（订单状态通知）
- 后台管理（用户、订单、交易管理）

## 二、用户角色

### 2.1 发单用户
- 注册登录
- 发布代领需求
- 支付跑腿费
- 确认收货
- 评价代领员
- 查看我的订单

### 2.2 接单用户（代领员）
- 注册登录
- 接单代领
- 上传凭证
- 完成送达
- 查看收入
- 提现

### 2.3 系统管理员
- 用户管理
- 订单管理
- 交易流水查看
- 数据统计

## 三、核心功能模块

### 3.1 用户模块

#### 3.1.1 用户注册
- 学号
- 姓名
- 手机号
- 密码
- 宿舍楼栋+房间号

#### 3.1.2 用户登录
- 手机号/学号登录
- JWT Token鉴权

#### 3.1.3 个人信息
- 头像、昵称
- 宿舍地址
- 信用分（默认100分）
- 累计发单数
- 累计接单数

### 3.2 快递发布模块

#### 3.2.1 发布代领需求
- 快递公司（菜鸟驿站、京东、顺丰等）
- 取件码
- 物品类型（文件、服饰、食品、数码、日用品、其他）
- 物品重量（小件、中件、大件）
- 取件地址（快递站点）
- 送达地址（宿舍楼栋+房间号）
- 跑腿费（系统推荐价格，可调整）
- 期望送达时间
- 备注说明
- 联系电话

#### 3.2.2 跑腿费定价
- 基础价格：2元
- 重量加价：中件+1元，大件+2元
- 距离加价：根据取件点到宿舍距离
- 时效加价：加急+1-3元

#### 3.2.3 订单广场
- 最新订单列表
- 筛选条件（物品类型、重量、跑腿费范围）
- 订单详情查看
- 快速接单

### 3.3 代领接单模块

#### 3.3.1 接单流程
- 浏览订单广场
- 查看订单详情
- 确认接单
- 前往取件
- 上传取件凭证（拍照）
- 联系发单人
- 送达目的地
- 上传送达凭证
- 等待确认收货

#### 3.3.2 接单限制
- 同时最多接3单
- 信用分低于60分不可接单
- 超时未完成扣信用分

### 3.4 订单管理模块

#### 3.4.1 订单状态
- 待接单：发布后等待代领员接单
- 待取件：已接单，代领员前往取件中
- 配送中：已取件，正在送往目的地
- 待确认：已送达，等待发单人确认收货
- 已完成：发单人确认收货，订单完成
- 已取消：超时未接单或发单人取消
- 已退款：异常情况退款

#### 3.4.2 我的订单
- 我发布的订单
- 我接的订单
- 订单筛选（状态、时间）
- 订单详情
- 订单操作（取消、确认收货、评价、投诉）

#### 3.4.3 订单超时规则
- 发布后30分钟无人接单自动取消
- 接单后60分钟未取件自动取消（扣信用分）
- 取件后120分钟未送达超时提醒
- 送达后24小时未确认自动确认收货

### 3.5 评价模块

#### 3.5.1 评价功能
- 评分（1-5星）
- 评价标签（速度快、态度好、包装好、准时送达）
- 评价内容（文字）
- 匿名评价（可选）

#### 3.5.2 信用分规则
- 好评（5星）：+2分
- 中评（3-4星）：+1分
- 差评（1-2星）：-5分
- 超时未完成：-10分
- 投诉属实：-20分

### 3.6 钱包模块

#### 3.6.1 余额功能
- 余额查看
- 充值（模拟支付）
- 提现
- 冻结金额（订单进行中）

#### 3.6.2 交易记录
- 充值记录
- 消费记录（发单支付）
- 收入记录（接单收入）
- 提现记录
- 退款记录

#### 3.6.3 资金流转
- 发单时冻结跑腿费
- 确认收货后解冻并转给代领员
- 订单取消退回发单人
- 平台不收取手续费（实际项目可收5%-10%）

### 3.7 通知模块

#### 3.7.1 系统通知
- 订单状态变更通知
- 账户余额变动通知
- 信用分变动通知

#### 3.7.2 订单通知
- 有人接单通知
- 代领员已取件通知
- 快递已送达通知
- 订单超时提醒
- 待评价提醒

### 3.8 管理后台模块

#### 3.8.1 数据统计
- 用户总数
- 订单总数（今日、本周、本月）
- 交易金额统计
- 订单完成率
- 用户活跃度

#### 3.8.2 用户管理
- 用户列表
- 用户详情
- 信用分调整
- 账号禁用/启用

#### 3.8.3 订单管理
- 订单列表
- 订单详情
- 异常订单处理
- 强制完成/取消

#### 3.8.4 投诉处理
- 投诉列表
- 投诉详情
- 处理结果（退款、扣信用分等）

## 四、数据库设计（8张表）

### 4.1 用户表 (user)
```sql
id                  BIGINT          主键
student_id          VARCHAR(30)     学号（唯一）
username            VARCHAR(50)     用户名
phone               VARCHAR(20)     手机号（唯一）
password            VARCHAR(100)    密码（加密）
real_name           VARCHAR(50)     真实姓名
avatar              VARCHAR(255)    头像
dormitory_building  VARCHAR(50)     宿舍楼栋
dormitory_room      VARCHAR(20)     房间号
credit_score        INT             信用分（默认100）
total_orders        INT             累计发单数
total_takes         INT             累计接单数
status              TINYINT         状态（0正常/1禁用）
create_time         DATETIME        创建时间
update_time         DATETIME        更新时间
```

### 4.2 订单表 (order)
```sql
id                  BIGINT          主键
order_no            VARCHAR(32)     订单编号
publisher_id        BIGINT          发单人ID
taker_id            BIGINT          接单人ID
express_company     VARCHAR(50)     快递公司
pickup_code         VARCHAR(50)     取件码
item_type           VARCHAR(20)     物品类型
item_weight         VARCHAR(20)     物品重量（SMALL/MEDIUM/LARGE）
pickup_address      VARCHAR(200)    取件地址
delivery_address    VARCHAR(200)    送达地址
delivery_building   VARCHAR(50)     宿舍楼栋
delivery_room       VARCHAR(20)     房间号
fee                 DECIMAL(10,2)   跑腿费
expect_time         DATETIME        期望送达时间
contact_phone       VARCHAR(20)     联系电话
remark              VARCHAR(500)    备注说明
pickup_image        VARCHAR(255)    取件凭证
delivery_image      VARCHAR(255)    送达凭证
status              TINYINT         状态（0待接单/1待取件/2配送中/3待确认/4已完成/5已取消/6已退款）
accept_time         DATETIME        接单时间
pickup_time         DATETIME        取件时间
delivery_time       DATETIME        送达时间
finish_time         DATETIME        完成时间
cancel_reason       VARCHAR(200)    取消原因
create_time         DATETIME        创建时间
update_time         DATETIME        更新时间
```

### 4.3 评价表 (review)
```sql
id                  BIGINT          主键
order_id            BIGINT          订单ID
order_no            VARCHAR(32)     订单编号
reviewer_id         BIGINT          评价人ID
reviewee_id         BIGINT          被评价人ID
rating              INT             评分（1-5）
tags                VARCHAR(200)    评价标签（逗号分隔）
content             TEXT            评价内容
is_anonymous        TINYINT         是否匿名（0否/1是）
create_time         DATETIME        创建时间
```

### 4.4 钱包表 (wallet)
```sql
id                  BIGINT          主键
user_id             BIGINT          用户ID（唯一）
balance             DECIMAL(10,2)   可用余额
frozen_amount       DECIMAL(10,2)   冻结金额
total_income        DECIMAL(10,2)   累计收入
total_expense       DECIMAL(10,2)   累计支出
create_time         DATETIME        创建时间
update_time         DATETIME        更新时间
```

### 4.5 交易记录表 (transaction)
```sql
id                  BIGINT          主键
transaction_no      VARCHAR(32)     交易流水号
user_id             BIGINT          用户ID
order_id            BIGINT          订单ID（可为空）
type                TINYINT         类型（1充值/2消费/3收入/4提现/5退款）
amount              DECIMAL(10,2)   金额
balance_before      DECIMAL(10,2)   交易前余额
balance_after       DECIMAL(10,2)   交易后余额
description         VARCHAR(200)    描述
create_time         DATETIME        创建时间
```

### 4.6 通知表 (notification)
```sql
id                  BIGINT          主键
user_id             BIGINT          接收用户ID
type                TINYINT         类型（1系统/2订单/3交易）
title               VARCHAR(100)    标题
content             VARCHAR(500)    内容
link_type           VARCHAR(20)     关联类型（order/transaction）
link_id             BIGINT          关联ID
is_read             TINYINT         是否已读（0否/1是）
create_time         DATETIME        创建时间
```

### 4.7 投诉表 (complaint)
```sql
id                  BIGINT          主键
order_id            BIGINT          订单ID
complainant_id      BIGINT          投诉人ID
respondent_id       BIGINT          被投诉人ID
type                VARCHAR(50)     投诉类型（未取件/未送达/态度恶劣/其他）
reason              TEXT            投诉原因
evidence            TEXT            证据图片（JSON）
status              TINYINT         状态（0待处理/1已处理/2已驳回）
handle_result       TEXT            处理结果
handle_admin_id     BIGINT          处理管理员ID
handle_time         DATETIME        处理时间
create_time         DATETIME        创建时间
```

### 4.8 管理员表 (admin)
```sql
id                  BIGINT          主键
username            VARCHAR(50)     用户名（唯一）
password            VARCHAR(100)    密码（加密）
real_name           VARCHAR(50)     真实姓名
role                TINYINT         角色（1超级管理员/2普通管理员）
status              TINYINT         状态（0正常/1禁用）
create_time         DATETIME        创建时间
```

## 五、API接口设计

### 5.1 认证接口
- POST /api/auth/register - 用户注册
- POST /api/auth/login - 用户登录
- GET /api/auth/info - 获取当前用户信息
- POST /api/auth/logout - 退出登录

### 5.2 用户接口
- GET /api/user/profile - 获取个人信息
- PUT /api/user/profile - 更新个人信息
- POST /api/user/avatar - 上传头像
- PUT /api/user/password - 修改密码

### 5.3 订单接口
- POST /api/orders - 发布代领订单
- GET /api/orders - 订单广场（待接单列表）
- GET /api/orders/{id} - 订单详情
- POST /api/orders/{id}/accept - 接单
- POST /api/orders/{id}/pickup - 上传取件凭证
- POST /api/orders/{id}/deliver - 上传送达凭证
- POST /api/orders/{id}/confirm - 确认收货
- POST /api/orders/{id}/cancel - 取消订单
- GET /api/orders/my/published - 我发布的订单
- GET /api/orders/my/accepted - 我接的订单

### 5.4 评价接口
- POST /api/reviews - 发表评价
- GET /api/reviews/user/{userId} - 查看用户评价列表

### 5.5 钱包接口
- GET /api/wallet/balance - 获取余额
- POST /api/wallet/recharge - 充值
- POST /api/wallet/withdraw - 提现
- GET /api/wallet/transactions - 交易记录

### 5.6 通知接口
- GET /api/notifications - 获取通知列表
- PUT /api/notifications/{id}/read - 标记已读
- GET /api/notifications/unread-count - 未读数量

### 5.7 投诉接口
- POST /api/complaints - 提交投诉
- GET /api/complaints/my - 我的投诉

### 5.8 文件上传接口
- POST /api/upload/image - 上传图片

### 5.9 管理后台接口
- POST /api/admin/login - 管理员登录
- GET /api/admin/statistics - 数据统计
- GET /api/admin/users - 用户列表
- PUT /api/admin/users/{id}/status - 修改用户状态
- GET /api/admin/orders - 订单列表
- PUT /api/admin/orders/{id}/handle - 处理异常订单
- GET /api/admin/complaints - 投诉列表
- PUT /api/admin/complaints/{id}/handle - 处理投诉
- GET /api/admin/transactions - 交易流水

## 六、前端页面设计

### 6.1 用户端页面

#### 6.1.1 登录注册页
- 登录表单
- 注册表单

#### 6.1.2 首页（订单广场）
- 顶部导航（首页、发单、订单、我的）
- 筛选条件（物品类型、重量、跑腿费）
- 订单列表
- 发布订单入口（悬浮按钮）

#### 6.1.3 订单卡片
- 订单编号
- 物品信息（类型、重量）
- 取件地址
- 送达地址
- 跑腿费
- 期望送达时间
- 接单按钮

#### 6.1.4 发布订单页
- 快递公司选择
- 取件码
- 物品类型选择
- 物品重量选择
- 取件地址
- 送达地址（默认当前用户宿舍）
- 跑腿费设置
- 期望送达时间
- 备注说明
- 提交按钮

#### 6.1.5 订单详情页
- 订单信息
- 状态进度条
- 发单人/接单人信息
- 取件凭证/送达凭证
- 操作按钮（接单、上传凭证、确认收货、取消、评价、投诉）

#### 6.1.6 我的订单页
- Tab切换（我发布的、我接的）
- 订单列表
- 状态筛选

#### 6.1.7 钱包页
- 余额展示
- 冻结金额
- 充值按钮
- 提现按钮
- 交易记录

#### 6.1.8 个人中心
- 用户信息卡片
- 信用分
- 累计发单/接单数
- 功能入口（我的订单、我的钱包、我的评价、设置）

#### 6.1.9 评价页
- 评分选择
- 评价标签
- 评价内容
- 匿名选项
- 提交按钮

### 6.2 管理端页面

#### 6.2.1 管理员登录页

#### 6.2.2 数据概览页
- 数据卡片（用户数、订单数、交易额）
- 订单状态统计图
- 用户活跃度趋势图

#### 6.2.3 用户管理页
- 用户列表
- 搜索筛选
- 用户详情
- 操作按钮（禁用、启用）

#### 6.2.4 订单管理页
- 订单列表
- 搜索筛选
- 订单详情
- 异常订单处理

#### 6.2.5 投诉管理页
- 投诉列表
- 投诉详情
- 处理操作

#### 6.2.6 交易流水页
- 交易记录列表
- 金额统计

## 七、业务流程

### 7.1 发单流程
```
用户登录
→ 点击"发单"按钮
→ 填写快递信息（快递公司、取件码、物品类型等）
→ 设置取件和送达地址
→ 设置跑腿费
→ 确认发布
→ 冻结跑腿费
→ 订单发布到广场
```

### 7.2 接单流程
```
代领员浏览订单广场
→ 查看订单详情
→ 点击"接单"
→ 系统确认（未超接单上限、信用分达标）
→ 接单成功
→ 前往快递站点取件
→ 上传取件凭证（拍照）
→ 送往目的地
→ 上传送达凭证
→ 通知发单人确认收货
```

### 7.3 确认收货流程
```
发单人收到送达通知
→ 查看订单详情
→ 确认收到快递
→ 解冻跑腿费
→ 转账给代领员
→ 订单完成
→ 引导评价
```

### 7.4 评价流程
```
订单完成
→ 发单人评价代领员
→ 评分（1-5星）
→ 选择标签
→ 填写评价内容
→ 提交评价
→ 更新代领员信用分
```

### 7.5 退款流程
```
订单异常（超时未接单/代领员取消/投诉成立）
→ 系统判断或管理员处理
→ 解冻跑腿费
→ 退回发单人账户
→ 生成退款交易记录
→ 发送通知
```

## 八、特色功能

### 8.1 智能定价
- 根据物品重量、距离、时效自动推荐价格
- 用户可自定义调整
- 价格合理，吸引代领员接单

### 8.2 信用分体系
- 约束用户行为
- 防止恶意取消、超时等
- 信用分低不可接单

### 8.3 凭证上传
- 取件凭证（快递照片）
- 送达凭证（门牌照片）
- 防止纠纷

### 8.4 资金安全
- 冻结机制
- 订单完成后自动结算
- 退款保障

### 8.5 订单超时机制
- 自动取消无人接单的订单
- 超时未完成扣信用分
- 保障服务质量

### 8.6 实时通知
- 订单状态变更及时通知
- 短信/站内信提醒
- 提升用户体验

## 九、非功能需求

### 9.1 性能要求
- 页面响应时间 < 2秒
- 订单发布响应 < 500ms
- 支持1000+并发用户

### 9.2 安全要求
- 密码加密（BCrypt）
- JWT Token鉴权
- 支付密码（可选）
- 敏感信息脱敏

### 9.3 可靠性要求
- 服务可用性 99%
- 数据备份
- 异常恢复机制

### 9.4 可用性要求
- 界面简洁直观
- 操作流程简单
- 移动端适配

## 十、开发优先级

### P0 核心功能
- 用户注册登录
- 发布订单
- 订单广场
- 接单功能
- 订单状态流转
- 钱包余额

### P1 重要功能
- 确认收货
- 评价功能
- 通知提醒
- 我的订单

### P2 增强功能
- 投诉处理
- 管理后台
- 数据统计

## 十一、初始数据

### 11.1 管理员账户
- 用户名：admin
- 密码：admin123

### 11.2 测试用户
- 学号：20210001，密码：123456（发单用户）
- 学号：20210002，密码：123456（代领员）

